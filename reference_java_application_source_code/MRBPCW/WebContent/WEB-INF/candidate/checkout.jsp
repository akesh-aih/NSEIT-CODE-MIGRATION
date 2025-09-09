<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
</head>
<s:hidden name = "environment"/>
<s:if test = '%{environment == "PROD"}'>
<script type="text/javascript" src="https://psa.atomtech.in/staticdata/ots/js/atomcheckout.js"></script>
</s:if>
<s:else>
<script type="text/javascript" src="https://pgtest.atomtech.in/staticdata/ots/js/atomcheckout.js"></script>
</s:else>
	<div align="center">
		<br><br><br><br><h2>Checkout</h2><br>
		<form>
			<table style="width: 20%" style="align-items: center;">
				<tr class="form-group">
					<td class="control-label">
					<s:hidden value = "%{aiPayToken}"/></td>
				</tr>
				<tr>
					<td><s:hidden value = "%{amount}"/></td>
				</tr>
			</table>
			<br> 
			<input 
				type="button"
				class="btn btn-primary"
				onclick="openPay()"
				value="Pay Now" 
			/>
			
			Please close this tab if payment was unsuccessful.
		</form>
	</div>
	<script type="text/javascript">
	$(document).ready(function(){
		if(document.URL.indexOf("#")==-1){
	        // Set the URL to whatever it was plus "#".
	        url = document.URL+"#";
	        location = "#";
	        //Reload the page
	        location.reload(true);
	    }
		openPay();
	});
	
	function openPay(){
	    /* console.log('openPay called'); */
	    const options = {
	      "atomTokenId": "<s:property value = 'aiPayToken' />",
	      "merchId": "<s:property value = 'merchantId' />",
	      "custEmail": "<s:property value = 'emailID' />",
	      "custMobile": "<s:property value = 'mobileNo' />",
		  "returnUrl":"<s:property value = 'returnUrl' />"
	    }
	    let atom = new AtomPaynetz(options);
	}
	</script>
	</html>