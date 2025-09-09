<%@ page contentType="text/html; charset=UTF-8"%>

<html>
<head>
<title>Merchant Page</title>
</head>
 <script>
            function submitPage()
            {
            	//alert("Response Payload");
            	document.forms.payload_form.submit();
            }
</script>
<body >
<h2>Merchant Page</h2>

<%
	String strRes=(String)request.getParameter("msg");
	System.out.println(" resrString ResponsePayload.jsp 12:"+strRes);
	String clnt_txn_ref=(String)request.getParameter("clnt_txn_ref");
	System.out.println(" resrString clnt_txn_ref.jsp 12:"+clnt_txn_ref);
	String tpsl_mrct_cd=(String)request.getParameter("tpsl_mrct_cd");
	System.out.println(" resrString tpsl_mrct_cd.jsp 12:"+tpsl_mrct_cd);
	com.tp.pg.util.TransactionResponseBean beanObj=new com.tp.pg.util.TransactionResponseBean();
	 String key="9784878061WERDLPXXY";
	 String iv="7581944913UFDFDPYSF";


	 String tranId=(String)session.getAttribute("mer_tran_id");
	if(session.getAttribute("KEY")!=null){
		key=(String)session.getAttribute("KEY");
		iv=(String)session.getAttribute("IV");
	}
	System.out.println(" resrString ResponsePayload.jsp key: "+key+" tranId :"+tranId);
	System.out.println(" resrString ResponsePayload.jsp iv:"+iv+" tranId :"+tranId);
	beanObj.setIv(iv.getBytes());
	beanObj.setKey(key.getBytes());
	beanObj.setResponsePayload(strRes);
	beanObj.setLogPath("D://payloadLog//MerchantResponseLog//OutTWC.log");
	String resrString=beanObj.getResponsePayload();
	System.out.println(" resrString DATA :"+resrString);
	String str[]=resrString.split("\\|");
	out.println("resrStringresrString  "+resrString);
%>
<form id="payload_form" action="" method="post" >
    <input type="text" name="pgResponse"  size="20" value="<%=resrString%>" />
</form>

<a href="https://www.tekprocess.co.in/MerchantIntegrationClient/merchant_client_req.jsp">Click here for try again</a>

</body>
</html>