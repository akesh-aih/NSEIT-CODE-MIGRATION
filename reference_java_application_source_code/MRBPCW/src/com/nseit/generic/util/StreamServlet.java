package com.nseit.generic.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import com.nseit.generic.aws.util.UploadPhotoSignInS3sdkV2;
import com.nseit.generic.models.Users;

public class StreamServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Logger logger;

	@Override
	public void init() throws ServletException {
		super.init();
		logger = LoggerHome.getLogger(getClass());
	}

	@Override
	public void destroy() {
		logger = null;
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String DocumentPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
		String userFk = CommonUtil.getParameter(request.getParameter("userFk")).trim();
		BufferedInputStream bis = null;
		OutputStream outputStream = null;

		try {
			response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
			response.setHeader("Pragma", "no-cache"); // HTTP 1.0
			response.setHeader("Expires", "-1");
			response.setDateHeader("Expires", 0); // Proxies.

			Users users = null;
			if (userFk.compareTo("") == 0) {
				users = (Users) request.getSession().getAttribute(GenericConstants.SESSION_USER);
			} else {
				users = new Users();
				users.setUserId(Long.parseLong(userFk));
			}

			String strSaveInS3 = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SAVE_IN_S3);
			if (strSaveInS3 != null && !Strings.isBlank(strSaveInS3) && "true".equals(strSaveInS3.toLowerCase())) {
				UploadPhotoSignInS3sdkV2 uploadPhotoSignInS3sdkV2 = new UploadPhotoSignInS3sdkV2();
				byte[] byteArrayOfPhotoSignFromCandFolderInS3 = uploadPhotoSignInS3sdkV2.getByteArrayOfPhotoSignFromCandFolderInS3(users, "_photo.jpg");
				if(byteArrayOfPhotoSignFromCandFolderInS3 != null) {
					response.setContentType("image/jpg");
					response.setHeader("Cache-Control", "no-cache");
					outputStream = response.getOutputStream();
					outputStream.write(byteArrayOfPhotoSignFromCandFolderInS3);
					outputStream.flush();	
				}

			} else {
				File filePath = new File(DocumentPath + File.separator + users.getUsername() + File.separator + users.getUsername() + "_photo.jpg");
						//logger.info("===== File Path for Retrieving Photo === "+filePath);
				if (filePath != null && filePath.exists()) {
					FileInputStream fi = new FileInputStream(filePath);
					bis = new BufferedInputStream(fi);
					outputStream = response.getOutputStream();
					int abyte;
					response.setContentType("image/jpg");
					response.setHeader("Cache-Control", "no-cache");
					while ((abyte = bis.read()) != -1) {
						outputStream.write(abyte);
					}
					outputStream.flush();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return;
		} finally {
			if (bis != null) {
				bis.close();
			}
		}
	}
}
