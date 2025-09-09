package com.nseit.generic.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.octo.captcha.service.CaptchaServiceException;

public class CaptchaImage extends HttpServlet {
	String sImgType = null;
	public void init(ServletConfig config) throws ServletException{
		super.init(config);

	    // For this servlet, supported image types are PNG and JPG.
	    sImgType = config.getInitParameter( "ImageType" );
	    sImgType = sImgType==null ? "png" : sImgType.trim().toLowerCase();
	    if (!sImgType.equalsIgnoreCase("png") && !sImgType.equalsIgnoreCase("jpg") &&!sImgType.equalsIgnoreCase("jpeg")){
	      sImgType = "png";
	    }
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{/*
		ByteArrayOutputStream imgOutputStream = new ByteArrayOutputStream();
	    byte[] captchaBytes;

	    if ( request.getQueryString()!=null ){
	      response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET request should have no query string." );
	      return;
	    }
	    try{
	      // Session ID is used to identify the particular captcha.
	      String captchaId = request.getSession().getId();

	      // Generate the captcha image.
	      BufferedImage challengeImage = new DefaultManageableImageCaptchaService().getImageChallengeForID(captchaId, request.getLocale());
	      ImageIO.write( challengeImage, sImgType, imgOutputStream );
	      captchaBytes = imgOutputStream.toByteArray();

	      // Clear any existing flag.
	      request.getSession().removeAttribute( "PassedCaptcha" );
	    }catch(CaptchaServiceException  cse)  {
	       System.out.println( "CaptchaServiceException - " + cse.getMessage() );
	       response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Problem generating captcha image." );
	       return;
	    }
	    catch(IOException ioe){
	       System.out.println( "IOException - " + ioe.getMessage() );
	       response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Problem generating captcha image." );
	       return;
	    }

	    // Set appropriate http headers.
	    response.setHeader( "Cache-Control", "no-store" );
	    response.setHeader( "Pragma", "no-cache" );
	    response.setDateHeader( "Expires", 0 );
	    response.setContentType( "image/" + (sImgType.equalsIgnoreCase("png") ? "png" : "jpeg") );

	    // Write the image to the client.
	    ServletOutputStream outStream = response.getOutputStream();
	    outStream.write( captchaBytes );
	    outStream.flush();
	    outStream.close();
		
	*/
	doPost(request, response);	
	}
	
	 protected void doPost( HttpServletRequest request, HttpServletResponse response ) 
	  throws ServletException, IOException
	  {
		 
		 ByteArrayOutputStream imgOutputStream = new ByteArrayOutputStream();
		    byte[] captchaBytes;

		    if ( request.getQueryString()!=null )
		    {
		      response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET request should have no query string." );
		      return;
		    }
		    try
		    {
		      // Session ID is used to identify the particular captcha.
		      String captchaId = request.getSession().getId();

		      // Generate the captcha image.
		      BufferedImage challengeImage = MyCaptchaService.getInstance().getImageChallengeForID(
		      captchaId, request.getLocale() );
		      ImageIO.write( challengeImage, sImgType, imgOutputStream );
		      captchaBytes = imgOutputStream.toByteArray();

		      // Clear any existing flag.
		      request.getSession().removeAttribute( "PassedCaptcha" );
		    }
		    catch( CaptchaServiceException cse )
		    {
		       System.out.println( "CaptchaServiceException - " + cse.getMessage() );
		       response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Problem generating captcha image." );
		       return;
		    }
		    catch( IOException ioe )
		    {
		       System.out.println( "IOException - " + ioe.getMessage() );
		       response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Problem generating captcha image." );
		       return;
		    }

		    // Set appropriate http headers.
		    response.setHeader( "Cache-Control", "no-store" );
		    response.setHeader( "Pragma", "no-cache" );
		    response.setDateHeader( "Expires", 0 );
		    response.setContentType( "image/" + (sImgType.equalsIgnoreCase("png") ? "png" : "jpeg") );

		    // Write the image to the client.
		    ServletOutputStream outStream = response.getOutputStream();
		    outStream.write( captchaBytes );
		    outStream.flush();
		    outStream.close();
	  }
	 
}