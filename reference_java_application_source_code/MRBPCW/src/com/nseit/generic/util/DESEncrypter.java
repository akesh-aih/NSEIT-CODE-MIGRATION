package com.nseit.generic.util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DESEncrypter
{
	Cipher ecipher;
	Cipher dcipher;

	public static DESEncrypter getInstance(String password) throws Exception
	{
		if (password.length() > 8)
		{
			password = password.substring(password.length() - 8);
		}
		else if (password.length() < 8)
		{
			password = CommonUtil.pad(password, 8, '0');
		}
		
		SecretKey key = new SecretKeySpec(password.getBytes(), "DES");
		return new DESEncrypter(key);
	}

	private DESEncrypter(SecretKey key) throws Exception
	{
		ecipher = Cipher.getInstance("DES");
		dcipher = Cipher.getInstance("DES");
		ecipher.init(Cipher.ENCRYPT_MODE, key);
		dcipher.init(Cipher.DECRYPT_MODE, key);
	}

	public String encrypt(String str) throws Exception
	{
		byte[] utf8 = str.getBytes("UTF8");
		byte[] enc = ecipher.doFinal(utf8);

		//String encoderString = new sun.misc.BASE64Encoder().encode(enc);	
		String encoderString = Base64.getEncoder().encodeToString(enc);
		return 	encoderString;
	}

	public String decrypt(String str) throws Exception
	{
		//byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
		byte[] dec = Base64.getDecoder().decode(str);
		byte[] utf8 = dcipher.doFinal(dec);
		
		return new String(utf8, "UTF8");
	}
	/*
	 * public static void main(String args[]) { System.out.println();
	 * System.out.println("----*--Encrypting string using DES--*----");
	 * System.out.println();
	 * 
	 * try {
	 * 
	 * // Generate a temporary key. In practice, you would save this key.
	 * 
	 * SecretKey key = new SecretKeySpec("Password".getBytes(),
	 * "DES");//KeyGenerator.getInstance("DES").generateKey();
	 * 
	 * // Create encrypter/decrypter class DESEncrypter encrypter = new
	 * DESEncrypter(key);
	 * 
	 * String s="Don't tell anybody!"; String d="Hello";
	 * 
	 * // Encrypt String encrypted = encrypter.encrypt(s);
	 * 
	 * // Decrypt String decrypted = encrypter.decrypt(encrypted);
	 * 
	 * System.out.println("Original string is :  " + s);
	 * System.out.println("Encrypted string is:  " + encrypted);
	 * System.out.println("Decrypted string is:  "+decrypted); } catch
	 * (Exception e) {} }
	 */
}
