package com.nseit.generic.util;

import static com.nseit.generic.util.GenericConstants.CAPTCHA_STRING_LOGIN;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CaptchaLoginImage extends HttpServlet {

	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{
		// Expire response
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Max-Age", 0);

		List<Object> lstObjects = GenerateCaptchaImage.generateImage(); 
		
		if(lstObjects.size() == 2)
		{
			HttpSession session = request.getSession(true);		
			session.setAttribute(CAPTCHA_STRING_LOGIN, lstObjects.get(0));
			OutputStream outputStream = response.getOutputStream();
			ImageIO.write((BufferedImage)lstObjects.get(1), "jpeg", outputStream);
			outputStream.close();
		}
	}

}