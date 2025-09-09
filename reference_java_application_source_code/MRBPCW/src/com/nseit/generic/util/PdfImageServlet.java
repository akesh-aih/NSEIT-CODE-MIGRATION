package com.nseit.generic.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nseit.generic.models.Users;

public class PdfImageServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public PdfImageServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	
        String userFk = CommonUtil.getParameter(request.getParameter("userFk")).trim();
        ByteArrayOutputStream baos1 = null;
        ByteArrayOutputStream baos = null;
       
        try
        {
        	response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
        	response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        	response.setHeader("Expires","-1");
        	response.setDateHeader("Expires", 0); // Proxies.
        	
        	Users users = null;
        	if(userFk.compareTo("")==0)
        	{
        		users = (Users)request.getSession().getAttribute(GenericConstants.SESSION_USER);
        	}
        	else
        	{
        		users = new Users();
        		users.setUserId(Long.parseLong(userFk));
        	}
        	
        	
        	
        	
        	 baos = new ByteArrayOutputStream();
                     
            if(request.getParameter("abc")!=null && !request.getParameter("abc").equals(""))
            {
            	users.setUsername(request.getParameter("abc"));
            }
            String DocumentPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
            File filePath = new File(DocumentPath+File.separator+users.getUsername()+File.separator+users.getUsername()+"_photo.jpg");
            OutputStream outputStream=null;
            
            BufferedImage originalImage=ImageIO.read(filePath);
			
			ImageIO.write(originalImage, "jpg", baos );
			byte[] imageInByte=baos.toByteArray();
			

			StringBuilder imageString = new StringBuilder();
			imageString.append("data:image/jpg;base64,");
//			BASE64Encoder base64Encoder = new BASE64Encoder();
//			imageString.append(base64Encoder.encode(imageInByte));
			imageString.append(Base64.getEncoder().encode(imageInByte));
			
			 baos1 = new ByteArrayOutputStream();
			 File filePath1 = new File(DocumentPath+File.separator+users.getUsername()+File.separator+users.getUsername()+"_sign.jpg");
	        
	            
	         BufferedImage originalImage1=ImageIO.read(filePath1);
			
			 ImageIO.write(originalImage1, "jpg", baos1 );
			 byte[] signInByte1=baos1.toByteArray();
				

			 StringBuilder singString1 = new StringBuilder();
			 singString1.append("data:image/jpg;base64,");
//			 BASE64Encoder base64Encoder1 = new BASE64Encoder();
//			 singString1.append(base64Encoder1.encode(signInByte1));
			 singString1.append(Base64.getEncoder().encode(imageInByte));
			
			 response.setContentType("text/plain");  
			 response.setCharacterEncoding("UTF-8"); 
			 response.getWriter().write(imageString.toString()+"||AND||"+singString1.toString());
          
    		
        }
        catch(Exception e)
        {
           //logger.error(e.getMessage(), e);
           return;
        }
        finally
        {

            if(baos1 != null) {
            	baos1.close();
            }
            if(baos != null) {
            	baos.close();
            }
        
            
        }
    }

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
