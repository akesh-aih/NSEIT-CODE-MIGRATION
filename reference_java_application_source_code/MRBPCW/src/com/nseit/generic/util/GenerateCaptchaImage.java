package com.nseit.generic.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class GenerateCaptchaImage
{
	public static List<Object> generateImage()
	{
		List<Object> lstObjects = new ArrayList<Object>();
		int height = 30;
		int width = 150;
		
		BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = image.createGraphics();
		graphics2D.setColor(Color.white);
		GradientPaint gp = new GradientPaint(0, 0, Color.WHITE, 5, 5, Color.LIGHT_GRAY,true);
		graphics2D.setPaint(gp);
		graphics2D.fillRect(0, 0, width, height);
		
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

	    rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
	    rh.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
	    rh.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
	    rh.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
	    
	    graphics2D.setRenderingHints(rh);
		
		Random r = new Random();
		String token = Long.toString(Math.abs(r.nextLong()), 36);
  		String ch = token.substring(0, 9);
		Color c = new Color(0.6662f, 0.4569f, 0.3232f);
		gp = new GradientPaint(0, 0, c, 5, 5, Color.black, true);
		graphics2D.setPaint(gp);
		
		Font font = new Font("Verdana", Font.CENTER_BASELINE, 20);
		graphics2D.setFont(font);
		graphics2D.drawString(ch, 2, 20);
		graphics2D.dispose();
		
		lstObjects.add(ch);
		lstObjects.add(image);
		
		return lstObjects;
	}
}
