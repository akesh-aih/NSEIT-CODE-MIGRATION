<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
function validateToGenerateReceipt(){

	var disciplineType = $("#ePostCode").val();
	var message = "";

	if(disciplineType == ""){
		message = message + "Please select Bank Name"+"##";
	}
	else
	{
		$("#error-massage3").hide();
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
	
	$(document).ready(function() {
				$("#ePostPaymentDate" ).datepicker({
				maxDate: 0,
				changeMonth: true,
				changeYear: true,
				yearRange: range,
				showOn: "button",
				buttonImageOnly: true,
				buttonImage: "images/cale-img.gif",
				buttonImageOnly: true,
				dateFormat: 'dd-M-yy'
			});
			});

	function noSpecialKeys(e) {
		var unicode=e.charCode? e.charCode : e.keyCode
		if (unicode!=8){
			if ((unicode > 64 && unicode < 91) || (unicode > 96 && unicode < 123) || (unicode > 47 && unicode < 58) || unicode == 46 || unicode == 9) 
	            return true;
	        else
	            return false;
    	}
	}


	function changeAction(){
		
		var flag = validateToGenerateReceipt();

		if (flag == true)
		{
			//$("#ChallanContainer").attr('action',"PaymentOfflineAction_generateChallanReceipt.action");
			//$("#ChallanContainer").submit();
			
			window.open("PaymentOnlineAction_generateChallanReceipt.action?ePostCode="+$("#ePostCode").val());
		}
	}
	
function validateEPostDetails()
	{
	
	var message = "";
	
	var disciplineType = $("#ePostCode").val();
	if(disciplineType == ""){
		message = message + "Please select Bank Name"+"##";
	}
	
	var scrollNumber = $("#scrollNumber").val();

	if(scrollNumber == "")
	{
		message = message + "Please enter Reference Number"+"##";
	}
	
	var ePostPaymentDate = $("#ePostPaymentDate").val();

	if(ePostPaymentDate == "")
	{
		message = message + "Please select e-Post Payment Date"+"##";
	}

	var challanBranchCode = $("#ePostBranchCode").val();

	if(challanBranchCode == "")
	{
		message = message + "Please enter Branch Code"+"##";
		
	}
	
	var challanFeesAmount = $("#ePostFeesAmount").val();

	if(ePostFeesAmount == ""){
		message = message + "Please enter Fee Amount"+"##";
		
	}
	
	var applicableFees = document.forms["EPostContainer"]["applicablefeeamt"].value;
	var ePostFeesAmount = $("#ePostFeesAmount").val();
	ePostFeesAmount = parseFloat(ePostFeesAmount).toFixed(2);
	
	if(ePostFeesAmount == ""){
		message = message + "Please enter Fee Amount"+"##";
	}
	
	if(ePostFeesAmount!= "" && applicableFees!= "" && ePostFeesAmount!=applicableFees){
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

function insertEPostDetails()
{
		
		//var flag = validateEPostDetails();

		//if (flag == true)
		//{
			$("#EPostContainer").attr('action',"PaymentOnlineAction_insertEPostDetails.action");
			$("#EPostContainer").submit();
			
			//window.open("PaymentOnlineAction_generateChallanReceipt.action?ePostCode="+$("#ePostCode").val());
		//}
}
	
function callFunc()
{
        $("#error-massage_user").hide();
        
        $("#scrollNumber").val("");

		$("#ePostPaymentDate").val("");

		$("#ePostBranchName").val("");

		$("#ePostBranchCode").val("");

		$("#ePostFeesAmount").val("");
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
	
function backToPreviousPage()
{
	$("#EPostContainer").attr('action',"PaymentOnlineAction_showPaymentScreen.action");
	$("#EPostContainer").submit();
}
</script>

<div id="PersonalInfo">
<s:form id="EPostContainer" name="EPostContainer">
<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
<s:hidden name="enrollmentFK" id="enrollmentFK"  value="%{challanpaymentBean.enrollmentFK}"/>
<s:hidden name="transactionNo" id="transactionNo"  value="%{challanpaymentBean.transactionNo}"/>
<s:hidden name="amount" id="amount" value="%{challanpaymentBean.amount}"/>
<s:hidden name="stage" id="stage" value="%{challanpaymentBean.stage}"/>
<s:hidden name="paymentModeCH" id="paymentModeCH" value="%{challanpaymentBean.paymentModeCH}"/>
<s:hidden name="paymentPK" id="paymentPK" value="%{challanpaymentBean.paymentPK + ddpaymentBean.paymentPK}"/>
<s:hidden name="hddDisciplineId" id="hddDisciplineId" value="%{challanpaymentBean.disciplineId + ddpaymentBean.disciplineId}"></s:hidden>
<s:hidden name="enrollmentFK" id="enrollmentFK" value="%{challanpaymentBean.enrollmentFK + ddpaymentBean.enrollmentFK}"></s:hidden>
<s:hidden name="submitVal" id="submitVal" value="true"></s:hidden>

<div id="error-massage3" style="display:none" class="error-massage">
      		<div class="error-massage-text">
      		<ul style="margin:0; margin-left:20px; padding:0;" id="error-ul3">
      	
      		</ul>      	
      	 </div>
</div>

<s:if test="errMsg!=null">
	 			<div id="error-massage_user" class="error-massage">
		      		<div class="error-massage-text">
		      			<ul style="margin:0; margin-left:20px; padding:0;" id="error-ul_user">
		      			<s:property value="errMsg" escape="false"/>
		      			</ul>
		      	 	</div>
	      		</div>
			</s:if>

 
<div style="display:none">
<h1 class="pageTitle" title="Generate e-Post Receipt"><s:text name="ePost.receiptHeader"/></h1>
<div class="hr-underline2"></div>
<table>
<tr>
<td>
<div class="field-label"><s:text name="challan.bankName"/>&nbsp;<span class="manadetory-fields">*</span></div>
</td>
<td>
<div>
       <s:select name="ePostCode" id="ePostCode" list="challanBankList" listValue="labelValue" listKey="labelValue"  value="%{challanpaymentBean.ePostCode}"/>
	<!-- remove for testing from above select tag (headerKey="" 
	       headerValue="")  --> 
</div>
</td>
</tr>
<tr>
<td>
 
<a href="javascript:void(0);" onclick="changeAction();" title="Generate e-Post Receipt"><s:text name="payment.generateReceipt"/></a>
</td>
</tr>
</table>
</div>


 
<h1 class="pageTitle" title="Payment Details"><s:text name="payment.paymentDetails"/></h1>
<div class="hr-underline2"></div>
<table cellspacing="3" cellpadding="2" border="0" >
<tr>
<td>
<div class="field-label"><s:text name="payment.registrationId"/></div>
 
</td>
<td>
<div>
		<s:property value="%{#session['SESSION_USER'].username}"/>
</div>
 
</td>
</tr>
<tr>
<td>
<div class="field-label"><s:text name="ePost.scrollNumber"/>&nbsp;<span class="manadetory-fields">*</span></div>
 
</td>
<td>
<div>
		<s:textfield name="scrollNumber" id="scrollNumber" value="%{scrollNumber}" size ="20" 
			maxlength="20" onpaste='return false' onkeypress="return alphaNumeric(event);" />
			
		<span class="lighttext">Please enter the reference number.</span>
	</div>
 
</td>
</tr>
<tr>
<td>
<div class="field-label"><s:text name="ePost.ePostPaymentDate"/>&nbsp;<span class="manadetory-fields">*</span></div>
 
</td>
<td>
<div>
		<s:textfield  name="ePostPaymentDate" onpaste='return false' id="ePostPaymentDate" value="%{ePostPaymentDate}"  
			readonly="true" 
			maxlength="11" size="15" />
	</div>
	 
</td>
</tr>
<tr>
<td>
<div class="field-label"><s:text name="ePost.ePostBranchName"/>&nbsp;</div>
 
</td>
<td>
<div>
	<s:textfield name="ePostBranchName" onpaste='return false' id="ePostBranchName" maxlength="50" size="35" 
		value="%{ePostBranchName}"/>
	
</div>
 
</td>
</tr>

<tr>
<td>
<div class="field-label"><s:text name="ePost.ePostBranchCode"/>&nbsp;<span class="manadetory-fields">*</span></div>
 
</td>
<td>
<div>
	<s:textfield name="ePostBranchCode" onpaste='return false' id="ePostBranchCode" maxlength="15" 
		onkeypress="return noSpecialKeys(event);" value="%{ePostBranchCode}"/>
	
</div>
 
</td>
</tr>

<tr>
<td>
<div class="field-label"><s:text name="payment.applicableFees"/>&nbsp;</div>
 
</td>
<td>
<div>
	<s:text name="payment.INR"/>&nbsp;<s:property value="amount"/>
	<s:hidden name="applicablefeeamt" id="applicablefeeamt" value='<s:property value="amount"/>' />
</div>
 
</td>
</tr>

<tr>
<td>
<div class="field-label"><s:text name="payment.feeAmount"/>&nbsp;<span class="manadetory-fields">*</span></div>
 
</td>
<td>
<div>
<s:text name="payment.INR"/>&nbsp;
	<s:textfield name="ePostFeesAmount" onpaste='return false' id="ePostFeesAmount" maxlength="11" size="16"
		onkeypress="return numberswithdot(event);" value="%{ePostFeesAmount}"/>
</div>
 
</td>
</tr>
</table>
<s:token />
</s:form>
 <table width="100%">
<tr>
<td width="15%"  >&nbsp;</td>
<td align="left">
<input type="button" name="back" id="back" value="Back" class="submitBtn button-gradient" onclick="backToPreviousPage();"/> &nbsp;
<input type="button" name="submit" id="submit" value="Submit" class="submitBtn button-gradient" onclick="insertEPostDetails();"/> &nbsp;
<input type="reset" name="clear" id="Clear" value="Clear" class="submitBtn button-gradient" onclick="callFunc();"/> &nbsp;
 </td>
 </tr>
 </table>


<div class="clear" align="center">

</div>
</div>