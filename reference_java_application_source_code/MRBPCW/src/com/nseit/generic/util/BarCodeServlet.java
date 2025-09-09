package com.nseit.generic.util;


import static com.nseit.generic.util.GenericConstants.SESSION_USER;

import java.awt.image.BufferedImage;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import com.nseit.generic.models.Users;
import com.nseit.otbs.model.EnrollmentDetailsBean;
import com.nseit.otbs.service.SchedulingTestService;



public class BarCodeServlet  extends HttpServlet{

//	public void init(ServletConfig config) throws ServletException
//	{
//		super.init(config);
//	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)			
	{
		// Expire response
//		response.setHeader("Cache-Control", "no-cache");
//		response.setDateHeader("Expires", 0);
//		response.setHeader("Pragma", "no-cache");
//		response.setDateHeader("Max-Age", 0);
//
//		List<Object> lstObjects = GenerateCaptchaImage.generateImage(); 
//		
//		if(lstObjects.size() == 2)
//		{
//			HttpSession session = request.getSession(true);		
//			session.setAttribute(CAPTCHA_STRING_REGISTRATION, lstObjects.get(0));
//			OutputStream outputStream = response.getOutputStream();
//			ImageIO.write((BufferedImage)lstObjects.get(1), "jpeg", outputStream);
//			outputStream.close();
//		}
		try{
			
			HttpSession session = request.getSession(true);
			Users loggedInUser = (Users) session.getAttribute(SESSION_USER);
			String userStage = null;
			String attempt = null;
			
			if (loggedInUser!=null){
				userStage = loggedInUser.getTempStage();
			}
			
			if (userStage!=null && !userStage.equals("")){
				String [] stageArr = userStage.split("\\.");
				if (stageArr!=null){
					attempt = stageArr[1];
				}
			}
			
			
 			SchedulingTestService schedulingService = (SchedulingTestService) SpringUtil.getInstance().getApplicationContext().getBean("schedulingTestService");
			EnrollmentDetailsBean schedulingTestBean; //= schedulingService.getEnrollmentDetailsBean(loggedInUser, 1);
			
			String userFk = CommonUtil.getParameter(request.getParameter("userFk")).trim();
			String batchAttempt = CommonUtil.getParameter(request.getParameter("bookingAttempt")).trim();
			
			Users users = null;
			
        	if(userFk.compareTo("")==0){
        		users = (Users)request.getSession().getAttribute(GenericConstants.SESSION_USER);
    			schedulingTestBean = schedulingService.getEnrollmentDetailsBean(users, Integer.parseInt(attempt));

        		/*if (Integer.parseInt(loggedInUser.getStage()) == ConfigurationConstants.getInstance().getStageKey(GenericConstants.STAGE_FIRST_ATTEMPT_SEAT_BOOKED)) {
        			schedulingTestBean = schedulingService.getEnrollmentDetailsBean(users, 1);
    			}else{
    				schedulingTestBean = schedulingService.getEnrollmentDetailsBean(users, 2);
    			}*/
        	}
        	else{
        		users = new Users();
        		users.setUserId(Long.parseLong(userFk));
        		schedulingTestBean = schedulingService.getEnrollmentDetailsBean(users, Integer.parseInt(batchAttempt));
        	}
			ServletOutputStream servletoutputstream = response.getOutputStream();
			//Code128 barcode = new Code128();
			//barcode.setData(schedulingTestBean.getEnrollmentPK());//loggedInUser.getUsername());

			BitmapCanvasProvider provider = new BitmapCanvasProvider(servletoutputstream, "image/x-png", 300, BufferedImage.TYPE_BYTE_GRAY, true, 0);
			org.krysalis.barcode4j.impl.code128.Code128 code128 = new org.krysalis.barcode4j.impl.code128.Code128();			
			code128.generateBarcode(provider, schedulingTestBean.getEnrollmentPK());
			
			provider.finish();
			
			response.setContentType("image/jpeg");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);

			// Generate Code-128 barcode & output to ServletOutputStream
			//barcode.drawBarcode(servletoutputstream);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
