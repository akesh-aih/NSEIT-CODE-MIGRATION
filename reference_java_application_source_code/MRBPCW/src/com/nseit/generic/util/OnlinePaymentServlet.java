package com.nseit.generic.util;

import static com.nseit.generic.util.GenericConstants.SESSION_USER;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;

import com.nseit.generic.models.Users;
import com.nseit.generic.service.CommonService;
import com.nseit.payment.service.PaymentService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class OnlinePaymentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerHome.getLogger(getClass());
	CommonService commonService = null;
	PaymentService paymentService = null;
	private Map<String, Object> requestAttributes;
	
	public OnlinePaymentServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		Users loggedInUser = (Users) session.getAttribute(SESSION_USER);
		//logger.info("Online Payment Servlet calling.");
		try {
				ApplicationContext applicationContext = SpringUtil.getInstance().getApplicationContext();
				commonService = (CommonService) applicationContext.getBean("commonService");
				
				ApplicationContext applicationContext1 = SpringUtil.getInstance().getApplicationContext();
				paymentService = (PaymentService) applicationContext1.getBean("paymentService");

				String requestType = "T";
				String merchantCode = "";
				String merchantTxnRefNumberPrefix = "";
				String merchantTxnSeqNumber = "";
				String merchantTxnRefNumber = "";
				String schemeCode = "";
				String bankCode= "";
				String accountNumber = "";
				String encryptionKey = "";
				String encryptionIV = "";
				String internalPGWebServiceUrl = "";
				String additionInfo = "";
				boolean merchantTxnFlag = true;
				
				StringBuilder txnRefnumber = new StringBuilder();
				//Fetching Candidate data for URL for TP-PG  [Start] [Siddhesh]
				merchantCode = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MERCHANT_ID);
				merchantTxnRefNumberPrefix = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.TXN_REF_NUMBER_PREFIX);
				
				//Checking Merchant Txn number exist or not with opd status N
				String mercntTxnNo = "";//paymentService.getExistingMerchantTxnNumber(loggedInUser);
				if(mercntTxnNo!=null && !mercntTxnNo.equals(""))
				{
					merchantTxnRefNumber = mercntTxnNo;
					//merchantTxnFlag = false;
				}
				else
				{
					merchantTxnSeqNumber = commonService.getTransactionUniqueNumber();
					txnRefnumber.append(merchantTxnRefNumberPrefix);
					txnRefnumber.append("-");
					txnRefnumber.append(merchantTxnSeqNumber);
					merchantTxnRefNumber = txnRefnumber.toString();
				//	merchantTxnFlag = true;
				}
				
				bankCode = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.BANK_CODE);
				encryptionKey = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.PG_ENCRYPTION_KEY);
				encryptionIV = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.PG_ENCRYPTION_IV);
				accountNumber = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.ACCOUNT_NUMBER);
				schemeCode = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SCHEME_CODE);
				//internalPGWebServiceUrl = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_PAYMENT_WEB_SERVICE_URL);
				//Prerequisite for making URL for TP-PG  [End] [Siddhesh]
				
				//Fetching Candidate data for URL for TP-PG  [Start] [Siddhesh]
				
				String amount = "";
				String cartDetails = "";
				String txnDate = "";
				
				amount = paymentService.getFeesDetail(String.valueOf(loggedInUser.getUserId()));
				amount = "2.00";
				
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(schemeCode);
				stringBuilder.append("_");
				stringBuilder.append(amount);
				stringBuilder.append("_");
				stringBuilder.append("0.0");
				cartDetails = stringBuilder.toString();
				
				StringBuilder stringBuilder1 = new StringBuilder();
				stringBuilder1.append(loggedInUser.getUsername());
				stringBuilder1.append("-");
				stringBuilder1.append("26"); //module/job ID
				additionInfo = stringBuilder1.toString();
				
				SimpleDateFormat sdfmt1 = new SimpleDateFormat("dd-MM-yyyy");
				Date currentDate = new Date();
				txnDate = sdfmt1.format(currentDate);
				//Fetching Candidate data for URL for TP-PG  [End] [Siddhesh]
				
				//Calling NSEIT Payment Service [Start] [Siddhesh]
				JSONObject inputJsonObj = new JSONObject();
				inputJsonObj.put("RequestType", requestType);
				inputJsonObj.put("MerchantCode", merchantCode);
				inputJsonObj.put("MerchantTXNRefNo", merchantTxnRefNumber);
				inputJsonObj.put("Amount", amount);
				inputJsonObj.put("ShoppingCartDetails", cartDetails);
				inputJsonObj.put("TransactionDate", txnDate);
				inputJsonObj.put("BankCode", bankCode);
				inputJsonObj.put("AccountNumber", accountNumber);
				inputJsonObj.put("MobileNumber", loggedInUser.getMobile());
				inputJsonObj.put("EmailID", loggedInUser.getEmailAddress());
				inputJsonObj.put("EncryptionKey", encryptionKey);
				inputJsonObj.put("EncryptionIV", encryptionIV);
				inputJsonObj.put("ITC", additionInfo);
				//Calling NSEIT Payment Service [End] [Siddhesh]
				
				//First way calling WS 
				/*String userFk = loggedInUser.getUserFk().toString();
				internalPGWebServiceUrl = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_PAYMENT_WEB_SERVICE_URL);
				
				URL url = new URL(internalPGWebServiceUrl+"/"+userFk);
				HttpURLConnection connection = (HttpURLConnection)url.openConnection();
				connection.setRequestMethod("POST");
				connection.setDoOutput(true);
				connection.getOutputStream().write(userFk.getBytes("UTF-8"));
				connection.connect();
				System.out.println("Response Code : " + connection.getResponseCode());
	            System.out.println("Response Message : " + connection.getResponseMessage());
	            System.out.println("Content : " + connection.getInputStream());
	            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    			String strTemp = "";
    			while (null != (strTemp = br.readLine())) {
    				System.out.println("Token : "+strTemp);
    			}*/
				
				//Second way calling WS 
				Client client = Client.create();
				/*String userFk = "100";
				Form form = new Form();
				form.add("userId", userFk);*/
				System.out.println("calling http://172.25.18.38:8380/OESPaymentService/OESWebServices/TPPaymentServices/SingleURL");
				WebResource webResource = client.resource("http://172.25.18.38:8380/OESPaymentService/OESWebServices/TPPaymentServices/SingleURL");
				ClientResponse CResponse = webResource.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, inputJsonObj.toString());
				String output = "";
				if(CResponse.getStatus() == 200)
				{
					output = CResponse.getEntity(String.class);
					if(mercntTxnNo == null)
					{
						//paymentService.insertTransactionIDForCandidate(loggedInUser, merchantTxnSeqNumber, merchantTxnRefNumber);
					}
					response.sendRedirect(output);
				}
				else
				{
					output = "error";
					System.out.println("Web Service Error");
				}
				/*response.setContentType("text/plain");
		        response.getWriter().write(output);*/
			} 
			catch (Exception e) {
				logger.error(e, e);
			}
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
