<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">

	$(document).ready(function() {
				$("#ddDate" ).datepicker({
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

	function noSpecialKey(e) 
	{
		var unicode=e.charCode? e.charCode : e.keyCode
		if (unicode!=8){
			if ((unicode > 64 && unicode < 91) || (unicode > 96 && unicode < 123) || (unicode > 47 && unicode < 58) || unicode == 46 || unicode == 9) 
	            return true;
	        else
	            return false;
    	}
	}
	
	function numberswithdot(e){
		var unicode=e.charCode? e.charCode : e.keyCode
		if (unicode!=8){
		if ((unicode<48||unicode>57) && unicode != 9 && unicode != 46) //if not a number
			return false //disable key press
		}
	}
	
	function alphaNumeric(e)
	{
		var unicode=e.charCode? e.charCode : e.keyCode
		if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && (unicode<48||unicode>57) && unicode != 8 && unicode != 9 && unicode != 46  && unicode != 39 && unicode != 38 && unicode != 45)
			return false;
	}

function validateDDDetails()
	{
	
	var message = "";
	
	var ddNumber = $("#ddNumber").val();

	if(ddNumber == "")
	{
		message = message + "Please enter DD Number / NEFT"+"##";
	}
	
	var ddDate = $("#ddDate").val();

	if(ddDate == "")
	{
		message = message + "Please select DD / NEFT Date"+"##";
	}

	var bankName = $("#bankName").val();

	if(bankName == "")
	{
		message = message + "Please enter Bank Name"+"##";
		
	}
	
	var ddCityName = $("#ddCityName").val();

	if(ddCityName == "")
	{
		message = message + "Please enter City"+"##";
		
	}
	
	var challanFeesAmount = $("#challanFeesAmount").val();
	var applicableFees = document.forms["ddContainer"]["applicablefeeamt"].value;
	challanFeesAmount = parseFloat(challanFeesAmount).toFixed(2);
	
	if(challanFeesAmount == ""){
		message = message + "Please enter Fee Amount"+"##";
	}
	
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

function insertDDDetails()
{
		
		var flag = validateDDDetails();

		if (flag == true)
		{
			$("#ddContainer").attr('action',"PaymentOnlineAction_insertDDDetails.action");
			$("#ddContainer").submit();
		}
}
function callFunc()
{
        $("#error-massage3").hide();
        
        $("#ddNumber").val("");

		$("#ddDate").val("");

		$("#bankName").val("");

		$("#ddCityName").val("");

		$("#challanFeesAmount").val("");
}	
function backToPreviousPage()
{
	$("#ddContainer").attr('action',"PaymentOnlineAction_showPaymentScreen.action");
	$("#ddContainer").submit();
}		
</script>

<div id="PersonalInfo">
<s:form id="ddContainer"  name="ddContainer">
<input type="hidden" name="menuKey" value='<%=request.getAttribute("menuKey")%>'/>
<s:hidden name="enrollmentFK" id="enrollmentFK"  value="%{ddpaymentBean.enrollmentFK}"/>
<s:hidden name="transactionNo" id="transactionNo"  value="%{ddpaymentBean.transactionNo}"/>
<s:hidden name="amount" id="amount" value="%{ddpaymentBean.amount}"/>
<s:hidden name="stage" id="stage" value="%{ddpaymentBean.stage}"/>
<s:hidden name="paymentModeDD" id="paymentModeDD" value="%{ddpaymentBean.paymentModeDD}"/>
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
<br/>
<h1 class="pageTitle" title="Payment Details"><s:text name="Payment Details"/></h1>
<div class="hr-underline2"></div>
<table>
<tr>
<td>
<div class="field-label"><s:text name="Registration ID"/></div>
<br/>
</td>
<td>
<div>
		<s:property value="%{#session['SESSION_USER'].username}"/>
</div>
<br/>
</td>
</tr>
<tr>
<td>
<div class="field-label"><s:text name="DD Number / NEFT"/>&nbsp;<span class="manadetory-fields">*</span></div>
<br/>
</td>
<td>
<div>
		<s:textfield name="ddNumber" id="ddNumber" size ="20" maxlength="20" onkeypress="return alphaNumeric(event);" value="%{ddpaymentBean.ddNumber}"/>	
		<span class="lighttext">Please enter Demand Draft / NEFT number.</span>
	</div>
	<br/>
</td>
</tr>
<tr>
<td>
<div class="field-label"><s:text name="DD / NEFT Date"/>&nbsp;<span class="manadetory-fields">*</span></div>
<br/>
</td>
<td>
<div>
		<s:textfield  name="ddDate" id="ddDate" value="%{ddpaymentBean.ddDate}"  
			readonly="true" 
			maxlength="11" size="15" />
	</div>
	<br/>
</td>
</tr>
<tr>
<td>
<div class="field-label"><s:text name="Bank Name"/>&nbsp;<span class="manadetory-fields">*</span></div>
<br/>
</td>
<td>
<div>
	<s:textfield name="bankName" id="bankName" maxlength="50" size="35" onkeypress="return alphabetsWithSpace(event);"
		value="%{ddpaymentBean.bankName}"/>
	
</div>
<br/>
</td>
</tr>

<tr>
<td>
<div class="field-label"><s:text name="City"/>&nbsp;<span class="manadetory-fields">*</span></div>
<br/>
</td>
<td>
<div>
	<s:textfield name="ddCityName" id="ddCityName" maxlength="15" 
		onkeypress="return alphabetsWithSpace(event);" value="%{ddpaymentBean.ddCityName}"/>
	
</div>
<br/>
</td>
</tr>

<tr>
<td>
<div class="field-label"><s:text name="Applicable Fees"/>&nbsp;</div>
<br/>
</td>
<td>
<div>
	<s:text name="INR "/><s:property value="amount"/>
	<input name="applicablefeeamt" id="applicablefeeamt" value='<s:property value="amount"/>' type="hidden">
</div>
<br/>
</td>
</tr>

<tr>
<td>
<div class="field-label"><s:text name="Fee Amount"/>&nbsp;<span class="manadetory-fields">*</span></div>
<br/>
</td>
<td>
<div>
	<s:text name="INR "/>
	<s:textfield name="challanFeesAmount" id="challanFeesAmount" maxlength="11" size="16" 
		onkeypress="return numberswithdot(event);" value="%{challanpaymentBean.challanFeesAmount}"/>
</div>
<br/>
</td>
</tr>

</table>
<br/>
<s:token />
</s:form>

<div class="clear" align="center">

<input type="button" name="back" id="back" value="Back" class="submitBtn button-gradient" onclick="backToPreviousPage()"/> &nbsp;
<input type="button" name="submit" id="submit" value="Submit" class="submitBtn button-gradient" onclick="insertDDDetails();"/> &nbsp;
<input type="reset" name="clear" id="Clear" value="Clear" class="submitBtn button-gradient" onclick="callFunc();"/> &nbsp;
</div>
</div>