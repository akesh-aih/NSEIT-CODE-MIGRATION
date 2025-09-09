package com.nseit.generic.util;

import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

public class MyCaptchaService {
	 private static ImageCaptchaService instance = new DefaultManageableImageCaptchaService();
	  public static ImageCaptchaService getInstance()
	  {
	    return instance;
	  }
}
