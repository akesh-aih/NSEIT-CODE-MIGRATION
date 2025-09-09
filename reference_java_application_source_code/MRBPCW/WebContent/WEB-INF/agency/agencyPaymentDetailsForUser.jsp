<%@ taglib prefix="s" uri="/struts-tags"%>

<link href="css/common.css" rel="stylesheet" type="text/css" media="screen" />
<link rel="stylesheet" href="css/font-awesome.min.css" type="text/css" media="screen">

<link href="css/Flexgrid.css" rel="stylesheet" type="text/css" media="screen"/>
<link type="text/css" href="css/ui-lightness/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
<link rel="stylesheet" href="css/validationEngine.jquery.css" type="text/css"/>
<link REL="SHORTCUT ICON" HREF="images/gcet.ico" >
<script type="text/javascript" src="js/login.js"></script>
<script src="js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/jcarousellite_1.0.1c4.js"></script>
<script src="js/languages/jquery.validationEngine-en.js" type="text/javascript" charset="utf-8"></script>
<script src="js/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="js/jquery.form.js"></script>

<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>

<script type="text/javascript">

$(document).ready(function() 
{
	$("#ddDate" ).datepicker({
	maxDate: 0,
	changeMonth: true,
	changeYear: true,
	//yearRange: range,
	showOn: "button",
	buttonImageOnly: true,
	buttonImage: "images/cale-img.gif",
	buttonImageOnly: true,
	dateFormat: 'dd-M-yy'
});

$("#ddDate" ).datepicker('disable'); 
$(".fieldDisable").each(function(currIndex, currElement) {
			$(currElement).attr('disabled', 'disabled');
		});
$(".subbutdisable").each(function(currIndex, currElement) {
			$(currElement).attr('disabled', 'disabled');
		});		
});

function onErrorMessage(id){
	$("#"+id).hide();
}

function noSpecialKeys(e) {
		var unicode=e.charCode? e.charCode : e.keyCode
		if (unicode!=8){
			if ((unicode > 64 && unicode < 91) || (unicode > 96 && unicode < 123) || (unicode > 47 && unicode < 58) || unicode == 46 || unicode == 9) 
	            return true;
	        else
	            return false;
    	}
}

function alphaNumeric(e)
{
	var unicode=e.charCode? e.charCode : e.keyCode
	if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && (unicode<48||unicode>57) && unicode != 8 && unicode != 9 && unicode != 46  && unicode != 39 && unicode != 38 && unicode != 45)
		return false;
}

function numberswithdot(e){
	var unicode=e.charCode? e.charCode : e.keyCode
	if (unicode!=8){
	if ((unicode<48||unicode>57) && unicode != 9 && unicode != 46) //if not a number
		return false //disable key press
	}
}

function enableAllFields() 
{
	
$(".fieldDisable").each(function(currIndex, currElement) {
		$(currElement).removeAttr('disabled');
		$(currElement).addClass("fieldDisable");
		$("#ddDate" ).datepicker('enable'); 
});

$(".subbutdisable").each(function(currIndex, currElement) {
		$(currElement).removeAttr('disabled');
		$(currElement).addClass("submitBtn button-gradient");
});

}

function validateDDDetails()
	{
	
	
	var messagedd = "";
	
	var ddNumber = $("#ddChalanReciptNO").val();

	if(ddNumber == "")
	{
		messagedd = messagedd + "Please enter DD Number"+"##";
	}
	
	var ddDate = $("#ddDate").val();

	if(ddDate == "")
	{
		messagedd = messagedd + "Please select DD Date"+"##";
	}

	var bankName = $("#bankName").val();

	if(bankName == "")
	{
		messagedd = messagedd + "Please enter Bank Name"+"##";
	}
	
	var ddCityName = $("#bankCity").val();

	if(ddCityName == "")
	{
		messagedd = messagedd + "Please enter City"+"##";
		
	}
	
	var challanFeesAmount = $("#amount").val();
	if(challanFeesAmount == ""){
		messagedd = messagedd + "Please enter Fee Amount"+"##";
	}
	challanFeesAmount = parseFloat(challanFeesAmount).toFixed(2);
	var applicableFees = document.forms["editPayment"]["ddFeeAmount"].value;
	
	
	
	
	
	if(challanFeesAmount!= "" && applicableFees!= "" && challanFeesAmount!=applicableFees){
		messagedd = messagedd + "Please enter correct Fee Amount"+"##";
	}
	
	if(messagedd != ""){
		var ulID = "error-ul3";
		var divID = "error-massage3";
		createErrorList(messagedd, ulID, divID); 
		return false;
	}
	else
		return true;
}

function validateChallanDetails()
	{
	
	var message = "";
	
	var scrollNumber = $("#ddChalanReciptNO").val();

	if(scrollNumber == "")
	{
		message = message + "Please enter Journal/Challan Number"+"##";
	}
	
	var challanDate = $("#ddDate").val();

	if(challanDate == "")
	{
		message = message + "Please select Challan Date"+"##";
	}

	var challanBranchCode = $("#branchCode").val();

	if(challanBranchCode == "")
	{
		message = message + "Please enter Branch Code"+"##";
		
	}
	
	var challanFeesAmount = $("#amount").val();

	if(challanFeesAmount == ""){
		message = message + "Please enter Fee Amount"+"##";
	}
	challanFeesAmount = parseFloat(challanFeesAmount).toFixed(2);
		
	var applicableFees = document.forms["editPayment"]["challanFeeAmount"].value;
	
	if(challanFeesAmount!= "" && applicableFees!= "" && challanFeesAmount!=applicableFees){
		message = message + "Please enter correct Fee Amount"+"##";
	}
	
	if(message != ""){
		var ulID = "error-ul3";
		var divID = "error-massage3";
		createErrorList(message, ulID, divID); 
		return false;
	}
	else
		return true;
}

function insertPaymentDetails()
{
$("#error-massage3").hide();
var paymentMode = document.forms["editPayment"]["paymentMode"].value;
if(paymentMode == '3' || paymentMode == "3")
{

	var flag = validateDDDetails();

		if (flag == true)
		{
			$("#editPayment").attr('action',"CandidateMgmtAction_insertDDDetailsForRegisteredID.action");
			$("#editPayment").submit();
			
			//var dataString = "";
			//$.ajax({
			//url: "CandidateMgmtAction_insertDDDetailsForRegisteredID.action",
			//async: true,
			//data: dataString,
			//beforeSend: function()
				//	{
				//		
					//},
					//error:function(ajaxrequest)
					//{
					//	alert('Error registering user. Server Response: '+ajaxrequest.responseText);
					//},
					//success:function(responseText)
					//{
						//responseText = $.trim(responseText);
						
						//if(responseText.length > 0)
						//{
							//message = responseText.substring(2, responseText.length);
							//if(responseText.charAt(0) == "0")
							//{
								//alert(message);
								//return false;
							//}
						//}
					//}
				//});
				
		}
}

if(paymentMode == '4' || paymentMode == "4")
{
	var flag = validateChallanDetails();

		if (flag == true)
		{
			$("#editPayment").attr('action',"CandidateMgmtAction_insertChallanDetailsForRegisteredID.action");
			$("#editPayment").submit();
		}
}		
}
</script>
<div class="container">
<s:form action="CandidateMgmtAction" id="editPayment" name="editPayment">
<input name="paymentMode" id="paymentMode" value='<s:property value="paymentDetailsForUserID.paymentMode"/>' type="hidden">
<s:hidden name="paymentDetailsForUserID.userPK" id="paymentDetailsForUserID.userPK"  value="%{paymentDetailsForUserID.userPK}"/>
<s:hidden name="paymentDetailsForUserID.enrollment_pk" id="paymentDetailsForUserID.enrollment_pk"  value="%{paymentDetailsForUserID.enrollment_pk}"/>

<div id="dashboard" style="display:block; min-height:250px; height:auto;">
<!-- Box Container Start -->
<div style="display:block; min-height:250px; height:auto;" >
    <div style="display:block; min-height:160px; height:auto;" id="showDiv">
    <div id="error-massage3" style="display:none" class="error-massage">
      		<div class="error-massage-text">
      		<ul style="margin:0; margin-left:20px; padding:0;" id="error-ul3">
      	
      		</ul>      	
      	 </div>
</div>
<br/>				<h1 class="pageTitle" title="Dashboard">Payment Details</h1>
					<div class="hr-underline2"></div>
					<s:if test="hasActionMessages()">
		   				<div>
	      					<div>
	      						<s:actionmessage escape="false"/>
	      	 			</div>
      					</div>
					</s:if>
					
					<s:if test='%{paymentDetailsForUserID.paymentMode=="4"}'> 
					<div>
					<table class="contenttable">
						<tr>
							      <td width="286">User ID : </td>
						    	  <td >
							    	  	<strong>
							    	  			<s:property value="paymentDetailsForUserID.enrollment_pk"/>
							    	  	</strong>
						      	  </td>
						</tr>
						<tr>
							      <td width="286">Journal Number : </td>
						    	  <td >
							    	  	<s:textfield name="paymentDetailsForUserID.ddChalanReciptNO" id = "ddChalanReciptNO" cssClass="fieldDisable" size ="20" 
										maxlength="20" onkeypress="return alphaNumeric(event);"></s:textfield>
							    	  	
						      	  </td>
						</tr>		
						<tr>
							      <td width="286">Challan Date : </td>
						    	  <td >
							    	  	<s:textfield name="paymentDetailsForUserID.ddDate" readonly="true" id = "ddDate" cssClass="fieldDisable" maxlength="11" size="15"></s:textfield>
							    	  	
						      	  </td>
						</tr>	
						<tr>
							      <td width="286">Branch Name : </td>
						    	  <td >
							    	  	<s:textfield name="paymentDetailsForUserID.branchName" id = "branchName" cssClass="fieldDisable" maxlength="50" size="35" onkeypress="return alphabetsWithSpace(event);"></s:textfield>
						      	  </td>
						</tr>
						<tr>
							      <td width="286">Branch Code : </td>
						    	  <td >
							    	  	<s:textfield name="paymentDetailsForUserID.branchCode" id = "branchCode" cssClass="fieldDisable" maxlength="15" onkeypress="return noSpecialKeys(event);"></s:textfield>
							    	  	
						      	  </td>
						</tr>
						<tr>
							      <td width="286">Applicable Fee : </td>
						    	  <td >
						    	  <s:text name="INR "/>
							    	  	<strong>
							    	  			<s:property value="paymentDetailsForUserID.applicableFee"/>
							    	  			<input name="challanFeeAmount" id="challanFeeAmount" value='<s:property value="paymentDetailsForUserID.applicableFee"/>' type="hidden">
							    	  	</strong>
						      	  </td>
						</tr>
						<tr>
							      <td width="286">Amount : </td>
						    	  <td >
						    	  <s:text name="INR "/>
							    	  	<s:textfield name="paymentDetailsForUserID.amount" id = "amount" cssClass="fieldDisable" onkeypress="return numberswithdot(event);"></s:textfield>
							    	  	
						      	  </td>
						</tr>		
					</table>
					</div>
					</s:if>
					<s:if test='%{paymentDetailsForUserID.paymentMode=="3"}'> 
					<div>
					<table class="contenttable">
						<tr>
							      <td width="286">User ID : </td>
						    	  <td >
							    	  	<strong>
							    	  			<s:property value="paymentDetailsForUserID.enrollment_pk"/>
							    	  	</strong>
						      	  </td>
						</tr>
						<tr>
							      <td width="286">DD Number : </td>
						    	  <td >
							    	  	<s:textfield name="paymentDetailsForUserID.ddChalanReciptNO" size ="20" maxlength="20" onkeypress="return alphaNumeric(event);"  id = "ddChalanReciptNO" cssClass="fieldDisable"></s:textfield>
							    	  	
						      	  </td>
						</tr>	
						<tr>
							      <td width="286">DD Date : </td>
						    	  <td >
							    	  	<s:textfield name="paymentDetailsForUserID.ddDate" id = "ddDate" readonly="true" cssClass="fieldDisable" maxlength="11" size="15"></s:textfield>
							    	  	
						      	  </td>
						</tr>		
						<tr>
							      <td width="286">Bank Name : </td>
						    	  <td >
							    	  	<s:textfield name="paymentDetailsForUserID.bankName" id = "bankName" cssClass="fieldDisable" onkeypress="return alphabetsWithSpace(event);"></s:textfield>
							    	  	
						      	  </td>
						</tr>
						<tr>
							      <td width="286">City : </td>
						    	  <td >
							    	  	<strong>
							    	  			<!--<s:property value="paymentDetailsForUserID.bankCity"/>
							    	  			--><s:textfield name="paymentDetailsForUserID.bankCity" id = "bankCity" cssClass="fieldDisable" maxlength="15"  onkeypress="return alphabetsWithSpace(event);"></s:textfield>
							    	  	</strong>
						      	  </td>
						</tr>
						<tr>
							      <td width="286">Applicable Fee : </td>
						    	  <td >
						    	  <s:text name="INR "/>
							    	  	<strong>
							    	  			<s:property value="paymentDetailsForUserID.applicableFee"/>
							    	  			<input name="ddFeeAmount" id="ddFeeAmount" value='<s:property value="paymentDetailsForUserID.applicableFee"/>' type="hidden">
							    	  	</strong>
						      	  </td>
						</tr>
						<tr>
							      <td width="286">Amount : </td>
						    	  <td >
						    	  <s:text name="INR "/>
							    	  	<s:textfield name="paymentDetailsForUserID.amount" id = "amount" cssClass="fieldDisable" onkeypress="return numberswithdot(event);"></s:textfield>
							    	  	
						      	  </td>
						</tr>		
					</table>
					</div>
					</s:if>
							
	</div>
</div>
</div>
</s:form>

<div class="clear" align="center">
 	  		<s:if test='%{paymentDetailsForUserID.flagForEdit=="true"}'>
 	  					<input type="button" value="Edit" class="submitBtn button-gradient" onclick="enableAllFields()" id = "editBtn"/> &nbsp;&nbsp;&nbsp;&nbsp;
      	  				<input type="button" name="submit" id="submit" value="Submit" class="submitBtn button-gradient subbutdisable" onclick="insertPaymentDetails()"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    </s:if>	
<input type="button" value="Close" onclick="window.close()" class="submitBtn button-gradient">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<br/>
<br/>
</div>
</div>
