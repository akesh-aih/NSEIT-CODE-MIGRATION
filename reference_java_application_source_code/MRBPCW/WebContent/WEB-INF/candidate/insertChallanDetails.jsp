<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link rel="stylesheet" href="css/jquery-ui.css">
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<script type="text/javascript">

document.onreadystatechange = function () {
	var state = document.readyState;
	//enableLoadingAnimation();
	if (state == 'complete') {
		//disabledLoadingAnimation();
	}
}

$(document).ready(function() {
	$("input[name$='paymentModeType']").prop("checked", true);
	enableTimer();
	reconcilePaymentAjaxckeck();
});


function reconcilePaymentAjaxckeck() {
	var payStatus = "";
	//alert('on ready status button ajax call initiated');
	var reconcilePaymentAjaxckeckButton = "";
	dataString = "";
	var currentUserId = $("#userID1").val();
	/* console.log("currentUserId :" + currentUserId); */
	dataString = "currentUserId=" + currentUserId;

	$.ajax({
		url : "PaymentOnlineAction_reconcilePaymentAjaxckeck.action",
		async : true,
		data : dataString,
		error : function(ajaxrequest) {
			//alert('Error checking latest payment details: '+ajaxrequest.responseText);
			alert('Please try again ! ');
		},
		success : function(responseText) {
			reconcilePaymentAjaxckeckButton = $.trim(responseText);
			/* console.log("reconcilePaymentAjaxckeckButton :"	+ reconcilePaymentAjaxckeckButton); */
			if (reconcilePaymentAjaxckeckButton == "INVDUSER") {
				/* console.log("Invalid SESSION User ID Found :" + reconcilePaymentAjaxckeckButton); */
				window.open("/MRBPCW/LoginAction_input.action","_self");
			} else if (reconcilePaymentAjaxckeckButton == "YE") {//present in db and enale but
				/* console.log("present in db and enable button:"+ reconcilePaymentAjaxckeckButton); */
				$("#reconcileDiv").show();
				$('#reconcilePayment').attr("disabled", false);
			} else if (reconcilePaymentAjaxckeckButton == "YD") {//present in db but as per time it should be disables
				/* console.log("present in db but as per time it should be disabled:"+ reconcilePaymentAjaxckeckButton); */
				//$("#reconcileDiv").hide();
				enableTimer1();
				//alert("Please revisit this page after 15 minutes to check status of transaction.");
			} else if (reconcilePaymentAjaxckeckButton == "H") {
				/* console.log("Hide Button:"+ reconcilePaymentAjaxckeckButton); */
				$("#reconcileDiv").hide();
			} else {
				$('#reconcilePayment').prop('disabled', true);
				/* console.log("code got landed to catch block"); */
			}//else isPaymentstatus
		}//Ajax Success check
	});
}

function enableTimer() {
	var createdDate = '<s:property value="createdDate"/>';
	if (createdDate != null && createdDate != '') {
		showTimer();
	} else {
		$('#reconcilePayment').attr("disabled", true);
	}
}

function showTimer() {
	var reconcileFlag = '<s:property value ="reconcile"/>';
	var paymentStatus = '<s:property value="opd_validated_status"/>';

	var expireTime = getExpireTime(1);
	var timer = setInterval(function() {
		var now = new Date().getTime(); //current time
		var diff = expireTime - now; // created time - current time
		if (diff > 0 && reconcileFlag == "true" && paymentStatus == '') {
			$('#makePayment').attr("disabled", true);
		} else if (diff > 0 && paymentStatus == "P") {
			$('#makePayment').attr("disabled", true); //this should be disabled
			clearInterval(timer);
			$("#countdownTimer").hide();
			return;
		} else if (diff > 0 && paymentStatus == "R") {
			$('#makePayment').attr("disabled", false); //this should be enabled
			clearInterval(timer);
			$("#countdownTimer").hide();
			return;
		}
		if (diff < 0) {
			if (diff < 0 && reconcileFlag == "true"	&& paymentStatus == '') {
				$('#makePayment').attr("disabled", false);
			} else if (diff < 0 && paymentStatus == "P") {
				$('#makePayment').attr("disabled", false);
			} else if (diff < 0 && paymentStatus == "R") {
				$('#makePayment').attr("disabled", false);
			}
			clearInterval(timer);
			$("#countdownTimer").hide();
			return;
		}
		var minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
		var seconds = Math.floor((diff % (1000 * 60)) / 1000);
		displayCountdown(minutes, seconds);
	}, 1);
}

function getExpireTime(minutes) {
	var dt = new Date('<s:property value="createdDate"/>').getTime();
	return new Date(dt + minutes * 2.7e+6).getTime();
}

function displayCountdown(minutes, seconds) {
	document.getElementById("countdownTimer").style.display = 'block';
	document.getElementById("countdownTimer").style.color = 'red';
	document.getElementById("countdownTimer").innerHTML = "Re-enable's In " + minutes + " minutes " + seconds + " seconds ";
}

function enableTimer1() {
	var updatedDate = '<s:property value="checkStatusDate"/>';
	if (updatedDate != null && updatedDate != '') {
		showTimer1();
	} else {
		$('#reconcilePayment').attr("disabled", true);
	}
}

function showTimer1() {
	var expireTime = getExpireTime1(1);
	var timer = setInterval(function() {
		var now = new Date().getTime(); //current time
		var diff = expireTime - now; // created time - current time
		if (diff < 0) {
			$('#reconcilePayment').attr("disabled", false);
			clearInterval(timer);
			$("#countdownTimer1").hide();
			return;
		}else{
			var minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
			var seconds = Math.floor((diff % (1000 * 60)) / 1000);
			displayCountdown1(minutes, seconds);
			$('#reconcilePayment').attr("disabled", true);
		}
	}, 1);
}
	
function getExpireTime1(minutes) {
	var dt = new Date('<s:property value="checkStatusDate"/>').getTime();
	return new Date(dt + minutes * 0.9e+6).getTime();
}

function displayCountdown1(minutes, seconds) {
	document.getElementById("countdownTimer1").style.display = 'block';
	document.getElementById("countdownTimer1").style.color = 'red';
	document.getElementById("countdownTimer1").innerHTML = "Re-enable's In " + minutes + " minutes " + seconds + " seconds ";
}

function downloadChallanReceipt() {
	window.open("PaymentOnlineAction_generateChallanReceipt.action");
}

function connectToSBIEPayAjax() {
	var paymentModeType = $("input[name='paymentModeType']:checked").val();
	dataString = "paymentModeType=" + paymentModeType + "&applicablefeeamt=" + $("#applicablefeeamt").val();
	$.ajax({
		url : "PaymentOnlineAction_connectToSBIEPay.action",
		async : false,
		data : dataString,
		error : function(ajaxrequest) {
			alert('Please try again !! ');
		},
		success : function(responseText) {
			if (responseText != "") {
				$("#encdata").val($.trim(responseText));
				<%String merchant_code = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MERCHANT_ID);%>
				var merchant_code = '<%=merchant_code%>';
				$("#merchant_code").val(merchant_code);
			}
		}
	});
}

function connectToSafexAjax(){
	
	var paymentModeType= $("input[name='paymentModeType']:checked").val();
	dataString = "paymentModeType="+paymentModeType+"&applicablefeeamt="+$("#applicablefeeamt").val();	
	$.ajax({
		url: "PaymentOnlineAction_connectToSafex.action",
		async: false,
		data: dataString,
		error:function(ajaxrequest)
		{
			alert('Please try again !! ');
		},
		success:function(responseText)
		{
			if(responseText != "") {
				var response = responseText.split("&Hash=");
				var merchant_request = response[0];
				var hash = response[1];
				$("#merchant_request").val($.trim(merchant_request));
				$("#hash").val($.trim(hash));
				<%String me_id = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MERCHANT_ID);%>
				var me_id = '<%=me_id%>';
				$("#me_id").val(me_id);
			}
		}
	});
}

function connectToSBIEPayBillingAjax() {
	var paymentModeType = $("input[name='paymentModeType']:checked").val();
	dataString = "paymentModeType=" + paymentModeType + "&applicablefeeamt=" + $("#applicablefeeamt").val();
	$.ajax({
		url : "PaymentOnlineAction_connectToSBIEPayBilling.action",
		async : false,
		data : dataString,
		error : function(ajaxrequest) {
			alert('Please try again !!! ');
		},
		success : function(responseText) {
			if (responseText != null && responseText != "") {
				$("#EncryptbillingDetails").val($.trim(responseText));
			}
		}
	});

}

function checkdata() {
	enableLoadingAnimation();
	var dataString = "";
	$.ajax({
		url : "CandidateAction_paymentValidation.action",
		async : true,
		data : dataString,
		error : function(ajaxrequest) {
			alert('Please try again !!!! ');
		},
		success : function(responseText) {
			if (responseText != null && responseText != "") {
				var result = responseText.trim();
				if (result == "null") {
					disabledLoadingAnimation();
					proceedtopayment();
				} else {
					disabledLoadingAnimation();
					alert(responseText);
				}
			}

		}
	});
}

function proceedtopayment() {
	$("#payButton").attr("disabled", true);
	var flag = true;
	var actionName = "";
	if (flag == true) {
		var paymentModeType = $("input[name='paymentModeType']:checked").val();
		var dataString = "paymentModeType=" + paymentModeType;
		$("#ChallanContainer").attr('action', "CandidateAction_paymentValidation.action"); // "https://www.billdesk.com/pgidsk/PGIMerchantPayment"
		$("#ChallanContainer").submit();
	}
}

function changeActionBulk() {
	$('#reconcilePayment').prop('disabled', true);
	$("#ChallanContainer").attr('action', "AtomResponseAction_getDoubleVerificationResponseBulkforCheckStatus.action");
	enableLoadingAnimation();
	$("#ChallanContainer").submit();
}

//Dummy Payment Start
function dummySuccess() {
	showDialog(true);
}

function showDialog(modal) {
	$("#overlay").show();
	$("#testPayment").fadeIn(300);

	if (modal) {
		$("#overlay").unbind("click");
	} else {
		$("#overlay").click(function(e) {
			hideDialog();
		});
	}
}

function hideDialog() {
	$("#overlay").hide();
	$("#testPayment").fadeOut(300);
}

function successPaymentTest() {
	window.location.replace("PaymentOnlineAction_successPayment.action");
}

function rejectPaymentTest() {
	window.location.replace("PaymentOnlineAction_rejectPayment.action");
}

function testPaymentAction() {
	window.location.replace("PaymentOnlineAction_proceesTestPayment.action");
}
	//End of dummy payment
</script>
<style type="text/css">
.msgg li:first-child {
	list-style: none;
}

div.submenu {
	display: none;
}

.form-control {
	height: auto;
}
</style>
<div class="container-fluid ViewpageLabel">
	<div id="dashboard">
		<div class="titlebg container">
			<h1 class="pageTitle">
				<strong> Payment </strong> <span class="userid_txt"> <s:if
						test="%{#session['SESSION_USER'] != null}">
						<strong>User ID </strong> -	<s:label
							value="%{#session['SESSION_USER'].username}" />
					</s:if>
				</span>
			</h1>
		</div>
		<div class="padding_leftright">
			<div class="container common_dashboard tabDiv effect2">
				<div class="accordions">
					<div id="PersonalInfo">
						<s:form id="requestForm" name="requestForm" method="post">
							<s:hidden name="menuKey" value="%{#session['menuKey']}" />
							<s:hidden name="me_id" id="me_id" />
							<s:hidden name="merchant_request" id="merchant_request" />
							<s:hidden name="hash" id="hash" />
						</s:form>
						<s:form id="ChallanContainer" name="ChallanContainer"
							method="post">
							<s:hidden name="menuKey" value="%{#session['menuKey']}" />
							<s:hidden name="me_id" id="me_id" />
							<s:hidden name="merchant_request" id="merchant_request" />
							<s:hidden name="hash" id="hash" />
							<s:hidden name="userid" id="userID1"
								value="%{#session['SESSION_USER'].username}" />
							<s:hidden name="PAID" id="PAID" />
							<div class="accordion">
								<s:actionmessage escape="false" cssClass="msgg" />
								<div class="clear"></div>
								<div class="row">
									<div class="col-sm-8 table-responsive">
										<table width="100%" border="0" cellspacing="0" cellpadding="5"
											class="noborder">
											<tr id="applName" class="form-group">
												<td class="control-label">User ID <span
													class="manadetory-fields">*</span>
												</td>
												<td class="form-control mt5"><s:label
														name="applicantName" /></td>
											</tr>
											<tr id="applName" class="form-group">
												<td class="control-label">Post Applied For <span
													class="manadetory-fields">*</span></td>
												<td class="form-control mt5"><s:label name="post1" /></td>
											</tr>
											<tr class="form-group">
												<td class="control-label" width="290">Payment Mode <span
													class="manadetory-fields">*</span></td>
												<td class="form-control mt5"><div id="radioContainer"
														class="radioBtn">
														<s:radio list="paymentModeList" name="paymentModeType"
															label="Value" id="paymentModeType"
															value="%{paymentModeType}">
														</s:radio>
													</div></td>
											</tr>
											<tr id="onlineFeeTR" class="form-group">
												<td class="control-label">Applicable Fees (INR) <span
													class="manadetory-fields">*</span></td>
												<td class="form-control mt5"><s:text name="" />
													INR&nbsp; <s:property value="amount" /> <input
													name="applicablefeeamt" id="applicablefeeamt"
													value='<s:property value="amount"/>' type="hidden"></td>
											</tr>
											<tr>
												<td></td>
											</tr>
											<tr id="ProceedPayTR">
												<!-- onClick="checkdata();" -->
												<td class="text-left"><input type="button"
													id="makePayment" value="Proceed to Payment"
													title="Proceed to Payment" onClick="proceedtopayment();"
													class=" btn btn-warning" /> <span id="countdownTimer"
													style="color: red;"></span></td>
												<td id="reconcileDiv"><input type="button"
													name="reconcilePayment"
													onClick="return changeActionBulk();" id="reconcilePayment"
													value="Check Status of Transaction" class="btn btn-warning" />
													<span id="countdownTimer1" style="color: red;"></span></td>
											</tr>
											<!-- <tr id ="ProceedPayTR" >
									<td class="text-left"><input type="button" id="successPayment" value="Success"  title ="Payment Success" onClick="successPaymentTest();" class=" btn btn-warning" />
									<td class="text-left"><input type="button" id="rejectPayment" value="Reject"  title ="Payment Reject" onClick="rejectPaymentTest();" class=" btn btn-warning" />
									
								</tr> -->
										</table>
									</div>
								</div>
							</div>
							<s:token></s:token>
						</s:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="fullscreen-container" id="testPayment" style="display: none">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" onClick="okFunction();">X</button>
				<h2 class="modal-title">Test Payment</h2>
			</div>
			<div class="modal-footer">
				<div class="row">
					<div class="col-sm-6 col-sm-offset-3">
						<h5>Please Select Appropriate to Proceed</h5>
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="contenttable form-group">
							<tr class="form-group">
								<td><input type="button" value="Success"
									Class="ripple1 btn btn-warning btn-block"
									onClick="testPaymentAction();" /></td>
								<td><input type="button" name="reject" id="reject"
									value="Failure" class="ripple1 btn btn-warning btn-block"
									onClick="hideDialog();" /></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>