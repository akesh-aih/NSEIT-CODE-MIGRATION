package com.nseit.generic.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.spec.KeySpec;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.lowagie.text.pdf.codec.Base64;



public abstract class CommonUtil
{
	private static List<Map<String, Object>> menuList = null;
	private static String headerDisplayDate = null;
	private static String currentFormattedDate = null;
	private static Date currentDateOnly = null;
	private static long currentDateInMillis = 0;
	
	//[START NPCIL(VAPT)-XSS]
	private static String whiteListRegularExpression = "^[a-zA-Z0-9\\-\\.@_!\\ #\\$^\\&\\=\\+/,~`':\\?|\\\\(\\){}]*$";
	  private static Pattern pattern = Pattern.compile(whiteListRegularExpression);
	  //[END NPCIL(VAPT)-XSS]
	//[START NPCIL(VAPT)- Encryption/Decryption AES]
	  private static KeyGenerator keyGeneratorAES;
	  private static Cipher cipher;
	  private static SecretKey secretKey;

	  static
	  {
	    try
	      {
	        keyGeneratorAES = KeyGenerator.getInstance("AES");
	        secretKey = keyGeneratorAES.generateKey();
	        cipher = Cipher.getInstance("AES");
			headerDisplayDate = new SimpleDateFormat("EEEEEEEEE, MMM d, yyyy").format(new Date(System.currentTimeMillis()));
			currentFormattedDate = new SimpleDateFormat(GenericConstants.DATE_FORMAT_DEFAULT).format(new Date(System.currentTimeMillis()));
			currentDateOnly = new SimpleDateFormat(GenericConstants.DATE_FORMAT_DEFAULT).parse(currentFormattedDate);
			currentDateInMillis = currentDateOnly.getTime();
		}
		catch (ParseException e)
		{
			LoggerHome.getLogger(CommonUtil.class).fatal(e, e);
		}catch(Exception e1){
			e1.printStackTrace();
		}
		
		Timer timer = new Timer();
		Date date;
		date = new Date(getDateOnly().getTime() + GenericConstants.MILLISEC_IN_DAY);
		
	    timer.schedule(
	      new ChangeDate(),
	      date,
	      1000 * 60 * 60 * 24
	    );
		
		menuList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> subMenuList = null;
		Map<String, Object> menuItem = null;
		Map<String, Object> subMenuItem = null;
		
		menuItem = new HashMap<String, Object>();
		menuItem.put("NAME", "Dashboard");
		menuItem.put("ID", "Dashboard");
		menuItem.put("LINK", "#");
		menuList.add(menuItem);
		
		menuItem = new HashMap<String, Object>();
		menuItem.put("NAME", "Enrollment");
		menuItem.put("ID", "Enrollment");
		menuItem.put("LINK", "#");
		
		subMenuList = new ArrayList<Map<String, Object>>();
		subMenuItem = new HashMap<String, Object>();
		subMenuItem.put("NAME", "Test Enrollment");
		subMenuItem.put("ID", "TestEnrollment");
		subMenuItem.put("LINK", "#");
		subMenuList.add(subMenuItem);
		
		subMenuItem = new HashMap<String, Object>();
		subMenuItem.put("NAME", "Select Slot");
		subMenuItem.put("ID", "SelectSlot");
		subMenuItem.put("LINK", "#");
		subMenuList.add(subMenuItem);
		
		subMenuItem = new HashMap<String, Object>();
		subMenuItem.put("NAME", "Select Interview Slot");
		subMenuItem.put("ID", "SelectInterviewSlot");
		subMenuItem.put("LINK", "#");
		subMenuList.add(subMenuItem);
		
		subMenuItem = new HashMap<String, Object>();
		subMenuItem.put("NAME", "Request Retest");
		subMenuItem.put("ID", "RequestRetest");
		subMenuItem.put("LINK", "#");
		subMenuList.add(subMenuItem);
		
		subMenuItem = new HashMap<String, Object>();
		subMenuItem.put("NAME", "Request Change");
		subMenuItem.put("ID", "RequestChange");
		subMenuItem.put("LINK", "#");
		subMenuList.add(subMenuItem);
		menuItem.put("SubMenu", subMenuList);
		menuList.add(menuItem);
	}
	
	public static Date getDateOnly()
	{
		return currentDateOnly;
	}
	
	public static long getCurrentDateInMillis()
	{
		return currentDateInMillis;
	}
	
	public static String pad(String str, int size, char padChar)
	{
		StringBuilder padded = new StringBuilder(str);
		while (padded.length() < size)
		{
			padded.insert(0, padChar);
		}
		return padded.toString();
	}

	public static String getPasswordEncryptionKey()
	{
		return "P^a#s@s!";
	}

	interface Password
	{
		String getKey();
	}

	public static Timestamp getCurrentTimestamp()
	{
		return new Timestamp(System.currentTimeMillis());
	}

	public static Timestamp convertToTimestamp(String dateStr, String pattern)
			throws ParseException
	{
		if(dateStr==null || dateStr.compareTo("")==0)
			return null;
		
		return new Timestamp(getDate(dateStr, pattern).getTime());
	}
	
	

	public static Date getDate(String dateStr, String pattern)
			throws ParseException
	{
		return new SimpleDateFormat(pattern).parse(dateStr);
	}

	public static String formatTimeStamp(Timestamp timestamp, String pattern)
	{
		return new SimpleDateFormat(pattern).format(timestamp);
	}
	
	public static String formatDate(Date date, String pattern)
	{
		return new SimpleDateFormat(pattern).format(date);
	}

	public static String getParameter(String parameterValue)
	{
		return (parameterValue == null || parameterValue.equalsIgnoreCase("") || parameterValue
				.equalsIgnoreCase("null")) ? "" : parameterValue;
	}

	public static String getRandomPassword(int length)
	{
		final char[] SECURE_CHARS = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
				'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
				'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
				'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3',
				'4', '5', '6', '7', '8', '9', '=', '!',  '#', '/', '(', ')' };

		RandomString randomString = new RandomString(SECURE_CHARS);
		return randomString.nextString(length);
	}

	static class RandomString
	{
		private char[] symbols = new char[36];
		private final Random random = new Random();
		private char[] buf;

		RandomString(final char[] secureCharacters)
		{
			symbols = secureCharacters;
		}

		public String nextString(int length)
		{
			buf = new char[length];
			for (int idx = 0; idx < length; ++idx)
				buf[idx] = symbols[random.nextInt(symbols.length)];
			return new String(buf);
		}
	}
	
	public static List<Map<String, Object>> getMenu()
	{
		return Collections.synchronizedList(menuList);
	}
	
	public static String generateMenu(String currentResult)
	{
		String finalMenuHtml = "";
		finalMenuHtml = "<div id=\"droplinemenu\" class=\"droplinebar\"><ul>";
		
		for(int currIndex=0; currIndex<menuList.size(); currIndex++)
		{
			
			Map<String, Object> menuItem = menuList.get(currIndex);
			
			finalMenuHtml += ("<li id='" + menuItem.get("ID") + "'><a href='" + menuItem.get("LINK") + "'>" + menuItem.get("NAME") + "</a></li>");
		}
		
		return finalMenuHtml;
	}
	
	 public static String getStringDate(Timestamp date){
			SimpleDateFormat outFmt = new SimpleDateFormat("HH:mm");
	        String out = null;
	        out = outFmt.format(date);	  
	        return out;
	    }
	 
	 public static String getStringDateStr(Timestamp date){
			SimpleDateFormat outFmt = new SimpleDateFormat("dd/MM/yyyy");
	        String out = null;
	        out = outFmt.format(date);	  
	        return out;
	    }
	 
	 public static String getStringDateStrForAdmin(Timestamp date){
			SimpleDateFormat outFmt = new SimpleDateFormat("dd-MMM-yyyy");
	        String out = null;
	        out = outFmt.format(date);	  
	        return out;
	    }
	 
	 
	 public static List<String> getYearList(){
		 Integer fromYear = Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.VALIDITY_OF_PASSING_FROM_YEAR));
		 Integer toYear = Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.VALIDITY_OF_PASSING_TO_YEAR));
		 List<String> yearList = new ArrayList<String>();
		 for (int i = toYear; i >= fromYear; i--) {
			 yearList.add("" + i);
	 	}
	 	 return yearList;
	 }
	 
	 public static String formatTimeStamp(Timestamp timestamp){
			return formatTimeStamp(timestamp,"dd-MMM-yyyy");
		}
	 
	 public static Image toImage(BufferedImage bufferedImage) {
		    return Toolkit.getDefaultToolkit().createImage(bufferedImage.getSource());
		}
	 
	 
	 public static String convertToString(Date date)throws ParseException{
		 String s = null;
		 try {
			 DateFormat formatter ; 
			 Date newDate ;  
			  formatter = new SimpleDateFormat("dd-MMM-yy");
			  s = formatter.format(date);
			  } catch (Exception e){
				  e.printStackTrace();  
		  }  
			 
			  return s;
			 }
	 
	 
	 
	 public static Double getDecimalValue(String str){
		 DecimalFormat decimalFormat = new DecimalFormat("0.00");
		 String string = decimalFormat.format(Double.parseDouble(str));
		 return Double.parseDouble(string);
	 }
	 
	public static String getHeaderDisplayDate() {
		return headerDisplayDate;
	}

	
	
	static class ChangeDate extends TimerTask
		{
			@Override
			public void run()
			{
				try
				{
					headerDisplayDate  = new SimpleDateFormat("EEEEEEEEE, MMM d, yyyy").format(new Date(System.currentTimeMillis()));
					currentFormattedDate = new SimpleDateFormat("dd-MMM-yyyy").format(new Date(System.currentTimeMillis()));
					currentDateOnly = new SimpleDateFormat(GenericConstants.DATE_FORMAT_DEFAULT).parse(currentFormattedDate);
					currentDateInMillis = currentDateOnly.getTime();
				}
				catch (Exception ex)
				{
					LoggerHome.getLogger(getClass()).fatal(ex.getMessage(), ex);
				}
			}
		}
	
	
	public static Integer getDecimalCount(String perc){
		Integer count = 0;
		for (char ch : perc.toCharArray()){
			if (ch=='.'){
				count++;
			}
		}
		return count;
	}
	
	

	public static String getBillDeskCheckSum(String msg){
		String strHash = null;
		//String temp="9820288130||MHDF09323432|20080710152109"; //Parameter string 
		String temp=msg; //Parameter string
		String commonstr=ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.CHECKSUMKEY); // Checksum Key
		try{
			strHash = checkSumSHA256(temp+"|"+commonstr);
		}
		catch(Exception error){
			System.out.println(error.toString());
		}
		System.out.println("strHash==="+strHash);
		return strHash;
	}

	public static String checkSumSHA256(String plaintext)  {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256"); //step 2
			md.update(plaintext.getBytes("UTF-8")); //step 3
		} catch (Exception e) {
			md=null;
		}

		StringBuffer ls_sb=new StringBuffer();
		byte raw[] = md.digest(); //step 4
		for(int i=0;i<raw.length;i++)
			ls_sb.append(char2hex(raw[i]));
		return ls_sb.toString(); //step 6
		}
	
	public static String char2hex(byte x){
		char arr[]={
		'0','1','2','3',
		'4','5','6','7',
		'8','9','A','B',
		'C','D','E','F'
		};
		char c[] = {arr[(x & 0xF0)>>4],arr[x & 0x0F]};
		return (new String(c));
	}

	public static List<String> getYearExperienceList() {
		List<String> yearExperienceList = new ArrayList<String>();
		 for (int i = 0; i <= 30; i++) {
			 yearExperienceList.add("" + i);
	 	}
	 	 return yearExperienceList;
	}

	public static List<String> getMonthExperienceList() {
		 List<String> monthExperienceList = new ArrayList<String>();
		 for (int i = 0; i < 12; i++) {
			 monthExperienceList.add("" + i);
	 	}
	 	 return monthExperienceList;
	}

	public static List<String> getYearOfExperienceList() {
		 List<String> yearList = new ArrayList<String>();
		 for (int i = Calendar.getInstance().get(Calendar.YEAR); i >= Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.YEAR_OF_EXPERIENCE_FROM)); i--) {
			 yearList.add("" + i);
	 	}
	 	 return yearList;
	}

	public static List<String> getMonthList() {
		 List<String> monthList = new ArrayList<String>();
		 for (int i = 1; i <= 12; i++) {
			 monthList.add("" + String.format("%02d",i));
	 	}
	 	 return monthList;
	}
	public static boolean isValidDate(String dateToValidate,String dateFormat){
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setLenient(false);
		try {
			Date date = sdf.parse(dateToValidate);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean validateRequestParameters(HttpServletRequest request)
	  {
	    StringBuilder allParameterValues = new StringBuilder(request.getParameterMap().size()*4);
	    Enumeration<String> parametersNamesEnum = request.getParameterNames();

	    while(parametersNamesEnum.hasMoreElements())
	      {
	        String[] parameterValues = request.getParameterValues(parametersNamesEnum.nextElement());

	        for(String parameterValue : parameterValues)
	          {
	        	parameterValue = parameterValue.replace("\n", " ");
	        	parameterValue = parameterValue.replace("\r", " ");
	        	if(!parameterValue.contains("<s:")||!parameterValue.contains("/>"))
	            allParameterValues.append(parameterValue==null ? "" : parameterValue);
	          }
	      }

	    boolean isValid = pattern.matcher(allParameterValues.toString().trim()).matches();
	    /*if(!isValid) commented by Vrushali for SOPs*/

	    return isValid;
	  }
	
	public static byte[] hexStringToByteArray(String s) {

		  int len = s.length();
		  byte[] data = new byte[len / 2];

		  for (int i = 0; i < len; i += 2) {
		     data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
		     + Character.digit(s.charAt(i+1), 16));
		  }

		   return data;

		 }
	
	public static SecretKey generateKeyFromPassword(String password, byte[] saltBytes) throws GeneralSecurityException {

		  KeySpec keySpec = new PBEKeySpec(password.toCharArray(), saltBytes, 100, 128);
		  SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		  SecretKey secretKey = keyFactory.generateSecret(keySpec);

		  return new SecretKeySpec(secretKey.getEncoded(), "AES");
		 }

	public static String getDecryptedPassword(String encryptedData, SecretKeySpec sKey, IvParameterSpec ivParameterSpec) throws Exception { 

 		 Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
 		 c.init(Cipher.DECRYPT_MODE, sKey, ivParameterSpec);
 		 byte[] decordedValue = Base64.decode(encryptedData);
 		 byte[] decValue = c.doFinal(decordedValue);
 		 String decryptedValue = new String(decValue);

 		 return decryptedValue;
 		}
	
	//Prevent Session Fixation Attack
	  public HttpSession renewSessionAttributes(HttpServletRequest request)
	  {
	    HttpSession newSession = null;
	    try
	      {
	        HttpSession oldSession = request.getSession();
	        String[] vals = oldSession.getValueNames();
	        Map<String, Object> map = new HashMap<String, Object>();
	        for(String val:vals)
	          map.put(val, oldSession.getAttribute(val));
	        oldSession.invalidate();
	        newSession = request.getSession(true);
	        for(String key:map.keySet())
	          newSession.setAttribute(key, map.get(key));
	      }
	    catch (Exception e)
	      {
	        e.printStackTrace();
	      }
	    return newSession;
	  }



	
}
