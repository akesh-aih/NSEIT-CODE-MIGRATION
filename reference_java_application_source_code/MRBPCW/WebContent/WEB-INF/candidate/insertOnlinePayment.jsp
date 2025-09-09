<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style>
div.submenu, .subNavBg {
	display: none;
}
</style>	

<script type="text/javascript">
var encryptedVal = "";
$(document).ready(function() {
//getCandidateCourseData();
    $('input:radio[name=paymentModeType]').attr('checked', 'checked');
	$("#payButton").attr("disabled",false);
	//$("#payButton").css("opacity", "0.56");
	//$("#payButton").css("cursor", "not-allowed");
});

function validatePaymentModeType()
{

	var message = "";
	
	var value = "";
	var categoryChecked = false;
	
	$(".categoryClass").each(function()
	{
		if($(this).is(':checked'))
		{
			var id = $(this).attr("id");
			categoryChecked = true;
	        value = $('label[for='+id+']').text();
		}
	});

	/*if(categoryChecked == false)
	{
		message = message + "Please select payment mode."+"##";
	}*/
	
	if(message != "")
	{
		var ulID = "error-ul3";
		var divID = "error-massage3";
		createErrorList(message, ulID, divID); 
		return false;
	}
	if (message =='' || message == "")
	{
		return true;
	}
}

<%-- function proceedtopayment()
{
		var paymentModeType= $("input[name='paymentModeType']:checked").val();
	
		
		if(paymentModeType == 4 || paymentModeType == 2)
		{
		var flag = validatePaymentModeType();
		var actionName=""; 
		if (flag == true)
		{
		//alert("challan mode");
			dataString = "transactionNo="+$("#transactionNo").val()+"&paymentModeType="+paymentModeType+"&applicationNum="+$("#applicationNumVal").text()+"&subTestFk="+$("#appliedCourseVal").val();		
			$.ajax({
			url: "PaymentOnlineAction_insertCandidateTransactionDetails.action",
			type:'POST',
			async: true,
			data: dataString,
			error:function(ajaxrequest)
			{
				alert('Error refreshing. Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText)
			{
				responseText = $.trim(responseText);

				if(responseText=="success")
				{		//alert("encryptedVal == "+encryptedVal);
				
					$("#paymentContainer").attr("method", "POST");
					$("#paymentContainer").attr('action',encryptedVal); 
					$("#paymentContainer").submit();
				}
				else if(responseText=="not_proceed")
				{
					alert("Please apply for Exam and then proceed with payment");
				}
				else if(responseText=="exception" || responseText=="fail")
				{
					alert("Can not process your payment request right now, Please try again after some time.");
				}
				else if(responseText=="challan")
				{
					actionName='PaymentOnlineAction_displayChallanScreen.action'
					$("#paymentContainer").attr('action',actionName); 
					$("#paymentContainer").submit();
				}
				else
				{
			//alert("challan response = "+responseText);
					actionName='PaymentOnlineAction_displayChallanScreen.action?challanReferenceNo='+responseText
					$("#paymentContainer").attr('action',actionName); 
					$("#paymentContainer").submit();
				}
				
			}
			});			
		}
		else
		{
			return false;
		}
		}
		
		if(paymentModeType == "1")
		{
			
			dataString = "transactionNo="+$("#transactionNo").val()+"&paymentModeType="+paymentModeType+"&applicationNum="+$("#applicationNumVal").text()+"&subTestFk="+$("#appliedCourseVal").val();
			$.ajax({
			url: "PaymentOnlineAction_insertCandidateTransactionDetails.action",
			type:'POST',
			async: true,
			data: dataString,
			error:function(ajaxrequest)
			{
				alert('Error refreshing. Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText)
			{
				responseText = $.trim(responseText);
				if(responseText=="success")
				{					
					<%String str = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.REQUEST_URL);%>
					var request_url = '<%=str%>';
				//alert("request_url"+request_url);
					var flag = validatePaymentModeType();
					if (flag == true){
						$("#paymentContainer").attr('action',request_url);
						$("#paymentContainer").submit();
					}
				}
				else if(responseText=="not_proceed")
				{
					alert("Please apply for Exam and then proceed with payment");
				}
				else if(responseText=="exception" || responseText=="fail")
				{
					alert("Can not process your payment request right now, Please try again after some time.");
				}
				
			}
			});
					
				
				
				
		}
} --%>

function EnablePayButton()
{
if(document.getElementById('TncChk').checked) {
//alert("hii");
 $('#procdPay').prop('disabled', false);
 $('#procdPay').addClass("procdPay button-gradient"); 
$('#procdPay').removeClass("submitBtn button-gradient buttonDisable");
} else {
    $('#procdPay').prop('disabled', true);
  $('#procdPay').addClass("submitBtn button-gradient buttonDisable"); 
  $('#procdPay').removeClass("submitBtn button-gradient")
}
}


function proceedtopayment()
{
	$("#payButton").attr("disabled",true);
	var flag = validatePaymentModeType();
	var actionName="";
	if (flag == true)
	{
		var paymentModeType= $("input[name='paymentModeType']:checked").val();
		var dataString = "paymentModeType="+paymentModeType;
		//if(paymentModeType == "1")
		//{
			/*$.ajax({
			type: 'POST',
			url: "OnlinePaymentServlet",
			async: true,
			data: dataString,
			error:function(ajaxrequest)
			{
				window.reload();
			},
			success:function(responseText)
			{
				alert(responseText);
				window.location.href = responseText;
			},
			});*/
			$("#paymentContainer").attr('action',"TechProcessOnlineAction_connetToTechProcessSingleURL.action"); // "https://www.billdesk.com/pgidsk/PGIMerchantPayment"
			$("#paymentContainer").submit();
		//}
	}
}

function iBConnect()
{
	//alert("challan");
	$('#payButton').attr('disabled', false);
	$("#payButton").removeAttr("style");
	
	var paymentModeType= $("input[name='paymentModeType']:checked").val();
	//if(paymentModeType == "2" || paymentModeType == "4")
	if(paymentModeType == "2" || paymentModeType == "4")
	{
	//alert("challan 111");
		dataString = "transactionNo="+$("#transactionNo").val()+"&paymentModeType="+paymentModeType+"&applicationNum="+$("#applicationNumVal").text()+"&subTestFk="+$("#appliedCourseVal").val();	
		$.ajax({
		url: "PaymentOnlineAction_connectToIndianBank.action",
		type:'POST',
		async: true,
		data: dataString,
		error:function(ajaxrequest)
		{
			alert('Error refreshing. Server Response: '+ajaxrequest.responseText);
		},
		success:function(responseText)
		{
		//	alert("ib Rspns: == "+responseText);
			encryptedVal = $.trim(responseText);
			
		}
		});	
	}
	if(paymentModeType == "1")
	{
	//alert("onlineBilldesk 111");
		dataString = "transactionNo="+$("#transactionNo").val()+"&applicationNum="+$("#applicationNumVal").text()+"&subTestFk="+$("#appliedCourseVal").val();		
		$.ajax({
		url: "PaymentOnlineAction_connectToBillDesk.action",
		type:'POST',
		async: true,
		data: dataString,
		error:function(ajaxrequest)
		{
			alert('Error refreshing. Server Response: '+ajaxrequest.responseText);
		},
		success:function(responseText)
		{
			//encryptedVal = $.trim(responseText);
			//alert("billdestkmsg: "+responseText);
			$("#msg").val($.trim(responseText));
			 
		}
		});
	}
}	

function getCandidateCourseData()
{	
	
	$('input:radio[name=paymentModeType]').attr('checked', false);
	 
	var courseId = $("#appliedCourseVal").val();
	<s:iterator value="candidateAppliedCourseList" status="stat" var="currentObject">
	var subId = '<s:property value="%{subTestFK}"/>';
	if(courseId == subId)
	{
		$("#applicationNumVal").text('<s:property value="%{applicationNum}"/>');
		$("#disciplineDescVal").text('<s:property value="%{disciplineDesc}"/>');
		$("#subDisciplineDescVal").empty();
		$("#subDisciplineDescVal").append('<s:property value="%{subDisciplineDesc}"/>');
		var amount = '<s:property value="%{fees}"/>';
		if(amount == '6500')
		{
			$('#foreignText').show()
		}
		$("#feesVal").text('<s:property value="%{fees}"/>');
	}	
	</s:iterator>
}

</script>
<div id="paymentscreen" class="container">
<s:form id="paymentContainer"  name="paymentContainer" method="post">
<s:hidden name="msg" id = "msg"></s:hidden>
<s:hidden name="encparam" id = "encparam"></s:hidden>
<s:hidden name="transactionNo" id="transactionNo"></s:hidden>
<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>

<h1 class="pageTitle paymentTitle" id ="pageTitleDiv" title="Payment">Payment</h1>
<div id="dashboard" class="common_dashboard">

<div class="tabDiv effect2" style="height: 350px;"><div class="accordions">
		<div id="CandidateDiv">
		 <div class="accordion pad20">	 
<div id="error-massage3" style="display:none" class="error-massage">
      		<div class="error-massage-text">
      		<ul style="margin:0; margin-left:10px; padding:0;" id="error-ul3">      	
      		</ul>      		
      		<div class="clear">&nbsp;</div>     	
      	 </div>
</div>
		<s:if test='%{StatusApp != "N"}' >
			<div class="row mt10">
						<div class="col-sm-8 mt10">
							<table width="100%" border="0" cellspacing="0" cellpadding="5" class="noborder" >
								<tr class="form-group">
									<td  class="control-label" width="290"><s:text name="Payment Mode"/>
										<span class="manadetory-fields">*</span></td>
									<td class="form-control"><div id="radioContainer">
											<s:radio list="paymentModeList" name="paymentModeType" label = "Value"  id = "paymentModeType" value="%{paymentModeType}" > </s:radio>
										</div>
										
										<!--<span class="lighttext">Note: Please select Payment Mode. </span> <span class="manadetory-fields"></span>
--></td>
								</tr>
								<tr id="applName" class="form-group">
									<td class="control-label"><s:text name="User ID"/></td>
									<td class="form-control"><s:text name="applicantName"/></td>
								</tr>
								<%-- <tr id ="applNum" class="form-group">
									<td class="control-label"><s:text name="Application Number"/></td>
									<td class="form-control"><s:property value="%{#session['SESSION_USER'].username}"/></td>
								</tr> --%>
								<%-- <tr id ="applMob" class="form-group">
									<td class="control-label"><s:text name="Mobile No"/></td>
									<td class="form-control"><s:text name="applicantMobile"/></td>
								</tr> --%>
								<tr id ="onlineFeeTR" class="form-group">
									<td class="control-label"><s:text name="Applicable Fees (INR)"/>insertOnlinePayment.jsp</td>
									<td class="form-control"><s:text name=""/>
										INR&nbsp;
										<s:property value="amount"/>
										<input name="applicablefeeamt" id="applicablefeeamt" value='<s:property value="amount"/>' type="hidden"></td>
								</tr>
								<tr>
									<td colspan="2"><span class="font14">
										<input type="checkbox" id="TncChk" onClick="return EnablePayButton();">
										I agree to <a href="../GPODOC/TnC_Instructions.pdf" title="Terms and Condition of Online Payment" target="_blank" class="WeightBold"> Terms and Condition of Online Payment</a>  <span class="manadetory-fields">*</span></span></td>
								</tr>
								<tr>
									<td colspan="2"><span class="orgNote">Note: Accept Online Payment Terms and Conditions before proceeding to pay.</span></td>
								</tr>
								<tr id ="ProceedPayTR" >
									<td colspan="2" class="text-left"><input type="button" id="procdPay" value="Make Payment"  title ="Make Payment" onClick="return proceedtopayment();" class=" btn btn-warning buttonDisable" disabled="true" /></td>
								</tr>
								<tr>
									<td colspan="2"><div class="desc"  style="display:none"> <br/>
											<br/>
											<a href="javascript:void(0);" onClick="changeAction();" title="Generate Challan Receipt">
											<s:text name="Please click here to generate receipt"/>
											</a> <br>
											<br>
											<a href="../MGVCLDOC/Challan_Instructions.pdf" title="Challan Instructions" target="_blank">Click here for Instructions for Challan Payment</a> </div>
										<div class="desc3"  style="display:none"> <br/>
											<br/>
											<a href="../MDMS/SBI_Collect_Instructions.pdf" title="SBI Collect Payment Instructions" target="_blank">Click here for Instructions for SBI Collect Payment Steps</a> <br/>
											<br/>
											For Payment, <a href="https://www.onlinesbi.com/prelogin/icollecthome.htm" target="_blank" title="SBI Collect Payment"> Click Here </a> to make the payment. After making the payment, enter payment details here. <br/>
										</div></td>
								</tr>
							</table>
						</div>
					</div>
			</s:if>
			<s:else>
				<s:label class="font16">No Applications Pending for payment.</s:label>
			</s:else>
			 </div>
			 <s:token/>
	</div>
	</div></div></div> </s:form>
</div>