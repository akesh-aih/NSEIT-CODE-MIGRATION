package com.nseit.generic.action;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;



public class SMSTester {
	
	public static void main(String[] args) {
		try {
			SMSTester.sendSmsNew();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static List sendSmsNew() throws Exception{
		List l1=new ArrayList();
		//l1.add("9619067096");
		l1.add("9967238421");
		List<String> lstMobileNumbers =l1;
		String message ="Dear Applicant,OTP for validating your Mobile No. is 12587. Kindly enter this OTP to proceed with Basic Registration.\r\n" + 
				"- TNPCB" ;
		//List<SMSResponseBean> returnList = new ArrayList<SMSResponseBean>();
		

		try {
			boolean sendFlag = true;
			//SMSResponseBean smsResponseBean = null;
			String strMobileNo = "";
			String responseString = "";
			StringBuffer strXmlString = new StringBuffer("");
			StringBuffer strSmsUrl = new StringBuffer("");
			//ParseXml parseXml = new ParseXml();

			URL url = null;
			URLConnection urlConnection = null;
			BufferedReader bufferedReader = null;
			
			
			//  HttpURLConnection urlConnection = null;//(HttpURLConnection) url.openConnection();
			//  urlConnection.setRequestMethod("POST");

			//String smsURL = "http://bulkpush.mytoday.com/BulkSms/SingleMsgApi?";
			String smsURL = "http://www.myvaluefirst.com/smpp/sendsms?";

			
			
				//SMS_VALUEFIRST_URL=http://www.myvaluefirst.com/smpp/sendsms?
				//SMS_VALUEFIRST_USERNAME=nseithtp
				//SMS_VALUEFIRST_PASSWORD=abcdoiuy
				//SMS_VALUEFIRST_KEYWORD=Demo
				//SMS_VALUEFIRST_FEED_ID=292979
			 // String username ="democfehttp";
			//  String password = "sachin@123";
				
				
				String username ="nseithtp";
				String password = "abcdoiuy";
				String SMS_VALUEFIRST_KEYWORD="Demo";
				String SMS_VALUEFIRST_FEED_ID="292979";
			
			/*String username ="9820869777";
			String password = "atppd";
			String SMS_VALUEFIRST_FEED_ID="292979";*/
			  
			  
						
			String finalURL = null;

			//smsResponseBean = new SMSResponseBean();
			if (smsURL.compareTo("") == 0) {
				//smsResponseBean.setCode("system");
				//smsResponseBean.setDesc(smsResponseBean.getDesc() + " || "
						//+ " SMS Service URL not Proper ! ");
				sendFlag = false;
			}

			/*if (smsFeedId.compareTo("") == 0) {
				smsResponseBean.setCode("system");
				smsResponseBean.setDesc(smsResponseBean.getDesc() + " || "
						+ " SMS Feed Id not Proper ! ");
				sendFlag = false;
			}
*/
			if (username.compareTo("") == 0) {
				//smsResponseBean.setCode("system");
				//smsResponseBean.setDesc(smsResponseBean.getDesc() + " || "
						//+ " SMS UserName not Proper ! ");
				sendFlag = false;
			}

			if (password.compareTo("") == 0) {
				//smsResponseBean.setCode("system");
				//smsResponseBean.setDesc(smsResponseBean.getDesc() + " || "
					//	+ " SMS Password not Proper ! ");
				sendFlag = false;
			}

			if (message.compareTo("") == 0) {
				//smsResponseBean.setCode("system");
				//smsResponseBean.setDesc(smsResponseBean.getDesc() + " || "
						//+ " No Message available to be sent ! ");
				sendFlag = false;
			}

			/*if (senderId.compareTo("") == 0) {
				smsResponseBean.setCode("system");
				smsResponseBean.setDesc(smsResponseBean.getDesc() + " || "
						+ " SenderId unavailable ! ");
				sendFlag = false;
			}*/

			/*if (keyword.compareTo("") == 0) {
				smsResponseBean.setCode("system");
				smsResponseBean.setDesc(smsResponseBean.getDesc() + " || "
						+ " SMS Keyword not Proper ! ");
				sendFlag = false;
			}*/

			if (lstMobileNumbers == null || lstMobileNumbers.size() == 0) {
				//smsResponseBean.setCode("system");
				//smsResponseBean.setDesc(smsResponseBean.getDesc() + " || "
					//	+ " No Receipt for SMS ! ");
				sendFlag = false;
			}
			
			
				 

			if (sendFlag) {
				strSmsUrl.append(smsURL);
				
				strSmsUrl.append("&username=" + username);
				strSmsUrl.append("&password=" + password);
				//strSmsUrl.append("&to=" + time);//TNUSRB
				//strSmsUrl.append("&from=" + "TNUSRB");
				//strSmsUrl.append("&from=" + "TRBTET");//feedid=
				strSmsUrl.append("&feedid=" + SMS_VALUEFIRST_FEED_ID);
				strSmsUrl.append("&senderid=" + "TNPCBR"); //added by nikhil
				//strSmsUrl.append("&keyword=" + "demohtpp20");
				//strSmsUrl.append("&text=" + time);
			
				//strSmsUrl.append("&dlr_url=" + "");
				

				for (int currMobileNoIndex = 0; currMobileNoIndex < lstMobileNumbers
						.size(); currMobileNoIndex++) {
					

					strMobileNo = lstMobileNumbers.get(currMobileNoIndex);
					

					//smsResponseBean.setMobileNo(strMobileNo);

					if (sendFlag) {
						finalURL = strSmsUrl.toString() + "&to=" + strMobileNo
								+ "&text=" + URLEncoder.encode(message);
						
						//finalURL=finalURL + "&udh=0"+"&dlr_mask=19"+"&dlr_url=";

						System.out.println(finalURL);
						
						try {
							url = new URL(finalURL);
							// TODO Vijaya Remove this code from Live
							//System.setProperty("http.proxyHost", "172.25.18.6");

							//System.setProperty("http.proxyPort", "8080");

							// TODO Vijaya Remove this code from Live
							urlConnection = url.openConnection();
							
							//urlConnection = (HttpURLConnection) url.openConnection();
							//urlConnection.setRequestMethod("POST");
							urlConnection
									.setConnectTimeout(Integer
											.parseInt("1000000"));
							urlConnection
									.setReadTimeout(Integer
											.parseInt("1000000"));
							urlConnection.setDoOutput(true);

							bufferedReader = new BufferedReader(
									new InputStreamReader(urlConnection
											.getInputStream()));

							if (!strXmlString.toString().equals("")) {
								strXmlString.delete(0, strXmlString.toString()
										.length());
							}
							responseString = bufferedReader.readLine();

							while (responseString != null) {
								System.out.println(responseString);
								responseString=bufferedReader.readLine();
								
								strXmlString.append(responseString);
							}
						} catch (Exception e) {
							e.printStackTrace();
							strXmlString = new StringBuffer("");
						}

						// Parse Response XML String
						if (strXmlString.toString() != null
								&& !strXmlString.toString().equals("")
								&& !strXmlString.toString().equalsIgnoreCase(
										"null")) {
							//parseXml.processReturnResult(strXmlString
								//	.toString(), smsResponseBean);
						} else {
							//smsResponseBean.setCode("system");
							//.setDesc(smsResponseBean.getDesc()
							//		+ " || " + " SMS Response Not Received ! ");
						}
					}
					//returnList.add(smsResponseBean);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ArrayList<String>();
	}
	
	
}
