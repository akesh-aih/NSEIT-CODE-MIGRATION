<%@page import="com.nseit.generic.util.GenericConstants"%>
<%@page import="com.nseit.generic.util.ConfigurationConstants"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.models.Users"%>
<%@page import="com.nseit.generic.action.LoginAction"%>
<script type="text/javascript">

$(document).ready(function() {

	$("#paymentDeclarationDiv").hide();

	$(".subNavBg").hide();
	getCandidateCourseData();
	//$("#receipt").hide();
 	$('#Payment').css('color','#ebcb52');
 	$('#Apply').css('color','white');

 	var payMode = $("#paymentModeType1").val();
 	console.log("payMode :["+payMode+"]");
 	$("[name=paymentModeType]").filter("[value='"+payMode+"']").prop("checked",true);
});

function showPaymentDeclaration()
{
	$("#paymentDeclarationDiv").show();
	$("#btn2").hide();

}

function ShowDialog(value) {
	
	
	if(value)
	{
		$("#overlay").show();
		$("#dialog").fadeIn(300);
	}
}

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

	if(categoryChecked == false)
	{
		//message = message + "Please select payment mode."+"##";
	}
	
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
function changeAction(value)
{
		if(value=="5")
		{
			//$("#receipt").show();
			$("#btn").hide();
		}
		else
		{
		//	$("#receipt").hide();
			$("#btn").show();
		}
}


function connectToSBIEPayAjax(){
	//alert("connectToSBIEPayAjax Call :::");
	var paymentModeType= $("input[name='paymentModeType']:checked").val();
	
	dataString = "paymentModeType="+paymentModeType+"&applicablefeeamt="+$("#applicablefeeamt").val();		
	$.ajax({
	url: "PaymentOnlineAction_connectToSBIEPay.action",
	async: false,
	data: dataString,
	type: 'POST',
	error:function(ajaxrequest)
	{
		//alert('Error connectToSBIEPayAjax() Server ajaxrequest Response: '+ajaxrequest.responseText);
		alert('Please try again !! ');
	},
	success:function(responseText)
	{
		//encryptedVal = $.trim(responseText);
		//alert("SBIEPay responseText : "+responseText);
		if(responseText != "")
		{
			//$("#msg").val($.trim(responseText));
			$("#EncryptTrans").val($.trim(responseText));
			<%String merchantId = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MERCHANT_ID);%>
			var merchantIdVal = '<%=merchantId%>';
			//$("#merchIdVal").val("1000003");
			$("#merchIdVal").val(merchantIdVal);
			
		}
	}
	});
	
}
function connectToSBIEPayBillingAjax(){
	//alert("connectToSBIEPayBillingAjax Call :::");
	var paymentModeType= $("input[name='paymentModeType']:checked").val();
	
	dataString = "paymentModeType="+paymentModeType+"&applicablefeeamt="+$("#applicablefeeamt").val();		
	$.ajax({
	url: "PaymentOnlineAction_connectToSBIEPayBilling.action",
	async: false,
	data: dataString,
	type: 'POST',
	error:function(ajaxrequest)
	{
		//alert('Error connectToSBIEPayBillingAjax() Server ajaxrequest Response: '+ajaxrequest.responseText);
		alert('Please try again !!! ');
	},
	success:function(responseText)
	{
		//encryptedVal = $.trim(responseText);
		//alert("SBIEPayBilling responseText : "+responseText);
		if(responseText != null && responseText != "")
		{
			$("#EncryptbillingDetails").val($.trim(responseText));
						
		}
	}
	});
	
}

function proceedtopayment()
{	

	var value= $("#declaration").is(":checked");
	if(!value){
	alert(" Please select Check Box of Decleration. ");
	return false;
	}	
	else
	{
	var flag = validatePaymentModeType();
	var actionName="";
			if (flag == true)
			{
				var paymentModeType= $("input[name='paymentModeType']:checked").val();
				var dataString = "paymentModeType="+paymentModeType;
				if(paymentModeType == "1")
				{
					//Added By Bhushan
					var isPaymentStatusIncomplete =""; 
					var payStatus =""; 
					dataString = "";
					var currentUserId = $("#userID1").val();
					console.log("currentUserId :"+currentUserId);
					dataString = "currentUserId="+currentUserId;
					
					$.ajax({
					url: "PaymentOnlineAction_checkIncompletePaymentStatus.action",
					async: false,
					data: dataString,
					type: 'POST',
					error:function(ajaxrequest)
					{
						//alert('Error checking latest payment details: '+ajaxrequest.responseText);
						alert('Please try again ! ');
					},
					success:function(responseText)
					{
						//alert($.trim(responseText));
						isPaymentStatusIncomplete = $.trim(responseText);
						console.log("isPaymentStatusIncomplete :"+isPaymentStatusIncomplete);
						if(isPaymentStatusIncomplete == "INVDUSER") {
							console.log("Invalid SESSION User ID Found :"+isPaymentStatusIncomplete);
							//alert('Invalid User ID Found, Please try again!');
							window.open("/TNU/LoginAction_input.action","_self");
						}
						else if(isPaymentStatusIncomplete == "A") {
							alert('Your previous payment transaction is approved successfully, Please do not try again!');
						}
						else if(isPaymentStatusIncomplete == "CASHBOOKED") {
							//alert('Your previous Offline Cash transaction is pending. YOU ARE NOT ALLOWED TO INITIATE ANY NEW TRANSACTION (refer Instruction & brochure page number 5)');
							//showDialogPay();
						}
						else if(isPaymentStatusIncomplete == "REINITIATE") {
							alert('Your earlier transaction was successful, contact merchant for further details. PLEASE DO NOT RE-INITIATE TRANSACTION.');
						}
						else if(isPaymentStatusIncomplete == "Y") {
							alert('Your previous payment transaction is under process, wait till further confirmation, Please try again after 15 minutes !');
						}
						else {
							//connectToBillDeskAjax();
							//alert("isPaymentStatusIncomplete :"+isPaymentStatusIncomplete);
							connectToSBIEPayAjax();
							//Added Billing Detail for CASH Mode
							connectToSBIEPayBillingAjax();
							//Need to add ajax call insert payment detail
							
							dataString = "paymentModeType="+paymentModeType+"&applicablefeeamt="+$("#applicablefeeamt").val();		
							$.ajax({
							url: "PaymentOnlineAction_insertOnlinepaymentDetailsSBIEPay.action",
							async: false,
							data: dataString,
							type: 'POST',
							error:function(ajaxrequest)
							{
								//alert('Error proceedtopayment() Server ajaxrequest Response: '+ajaxrequest.responseText);
								console.log("insertOnlinepaymentDetailsSBIEPay Call Issues:"+ajaxrequest.responseText);
								alert('Please try again !!!! ');
							},
							success:function(responseText)
							{
								//encryptedVal = $.trim(responseText);
								//alert("insertOnlinepaymentDetailsSBIEPay responseText :["+responseText+"]");
								if(responseText != null && responseText != "")
								{
									//$("#msg").val($.trim(responseText));
									payStatus = responseText.trim();
								}
								//var request_url = "https://test.sbiepay.com/secure/AggregatorHostedListener";
								<%String str = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.REQUEST_URL);%>
								var request_url = '<%=str%>';
								//alert("SBIEPAY request_url : "+request_url);
								console.log("insertOnlinepaymentDetailsSBIEPay payStatus : "+payStatus);
								
								if(request_url != "" && payStatus == "PAYSUCC" )
								{
									//$("#paymentContainer").attr('action',"https://test.sbiepay.com/secure/AggregatorHostedListener");
									$("#paymentContainer").attr('action',request_url);
									$("#paymentContainer").submit();
									$("#proceedWaitSpan").show();
									$("#btn").addClass('payBtnDisabled'); 
								}
								else{
										alert("Payment Process Issues,Please try again later on!!");
									}
								}
							});	
						}//else isPaymentstatus
		
						
					}//Ajax Success check
					});

					// Show Offline Msg in case of CASH Payment
					if(isPaymentStatusIncomplete == "CASHBOOKED") {
						showDialogPay();
						return false;
					}
				}
				else if(paymentModeType == "4" || paymentModeType == "5")
				{
					$.ajax({
					url: "PaymentOnlineAction_redirectToPaymentMode.action",
					async: true,
					data: dataString,
					error:function(ajaxrequest)
					{
						window.reload();
					},
					success:function(responseText)
					{
						responseText = $.trim(responseText);
						if(responseText.length > 0)
						{
							actionName=responseText;
						}
					},
					complete:function()
					{
						$("#paymentContainer").attr('action',actionName); // "https://www.billdesk.com/pgidsk/PGIMerchantPayment"
						$("#paymentContainer").submit();
					}
					});
				}	
			}
  }
}
function getCandidateCourseData()
{	
	
	$('input:radio[name=paymentModeType]').attr('checked', false);
	 
	var courseId = $("#appliedCourseVal").val();

	<s:iterator value="candidateAppliedCourseList" status="stat" var="currentObject">
	var testFk = '<s:property value="%{testFK}"/>';
	if(courseId == testFk)
	{
		$("#job").text('<s:property value="%{disciplineDesc}"/>');
		$("#amount").text('<s:property value="%{amount}"/>');
		$("#testHidden").val('<s:property value="%{testFK}"/>');
		$("#applicationNumHidden").val('<s:property value="%{applicationNum}"/>');
	}	
	</s:iterator>
}

function showDialogPay(){
	$("#overlayPay").show();
	$("#dialogPay").fadeIn(300);
}
function HideDialogPay() {
	$("#overlayPay").hide();
	$("#dialogPay").hide();
	  //enableKeyEvent();
}

</script>

<style>
.payBtnDisabled {pointer-events: none; opacity: 0.56; }

</style>

<div id="">
<div class="container"><br/>
<div id="error-massage3" style="display:none" class="error-massage">
				<div class="error-massage-text">
					<ul style="margin:0; margin-left:20px; padding:0;" id="error-ul3"></ul>      	
			    </div>
			</div>
</div>
	<div class="container common_dashboard" >
	<s:if test='%{jobPaymentFlag == "A"}'>
	
	
	<div id="paymentscreen">
		<s:form id="paymentContainer"  name="paymentContainer" method="post">
			<s:hidden name="msg" value='<s:property value="msg"/>'/>
			<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
			 
			<s:hidden name="EncryptTrans" id = "EncryptTrans"></s:hidden>
			<s:hidden name="EncryptbillingDetails" id = "EncryptbillingDetails"></s:hidden>
			<s:hidden name="merchIdVal" id = "merchIdVal"></s:hidden>
			<s:hidden name="applicablefeeamt" id = "applicablefeeamt" value="%{amount}"></s:hidden>
			<s:hidden name="userid" id = "userID1"  value="%{#session['SESSION_USER'].username}" ></s:hidden>
			
			<div id="netBank">
				<table width="42%" align="center" cellpadding="0" cellspacing="0" >
					<tr>
				    	<th align="left" width="150">Applied to Post :</th>
					    <td> 
					    	<s:select cssStyle="pointer-events: none;" cssClass="form-control" list="appliedCourseList" name="appliedCourseVal" id = "appliedCourseVal" onchange = "getCandidateCourseData(this);"></s:select>
					    	
					    </td>
				    </tr>
				    <tr>
				  		<td colspan="2">&nbsp;</td>
				  	</tr>
				    <tr>
		  				<th align="left"><b>Fee :</b></th>
		    			<td>
		    				<s:label value="%{amount}" id = "amount" cssClass="form-control"></s:label>
		    			</td>
		    			
				  	</tr>
				    <tr>
		    			<td>
		    			</td>
		    			<td height="50" valign="middle">
							<!-- <s:submit id="btn" value="Proceed to pay" onclick="proceedtopayment();" cssClass="submitBtn button-gradient"></s:submit> -->
							<input type="button"  id="btn2" value="Proceed to pay" onclick="showPaymentDeclaration();" class="submitBtn button-gradient"/>
							 
		    			</td>
				  	</tr>
					
				  <tr>
				  		<td>
		    			</td>
				  	<td colspan="2">&nbsp;
	    			<span id="proceedWaitSpan" style="display:none">Please wait for Payment Proceed. </span>
				  	</td>
				  </tr>
				  <tr style="display:none;" align="left">
						<td colspan="2">
							<strong>Select Payment Mode :</strong>
								<s:radio list="paymentModeList" name="paymentModeType" label = "Value"  id = "paymentModeType" value="%{paymentModeType}" onchange="changeAction(this.value);" cssClass="categoryClass">
							</s:radio>
						</td> 
				  </tr>
				  
				</table>
				
				
			</div>
			
			
			<s:token/>
	<div id="overlay" class="web_dialog_overlay_declr"
		style="display: none;"></div>		
	<div id="dialog" class="web_dialog_declr" style="display: none;height:165px; width: 503.99306px; margin-left: -230px; margin-top: -132px;">
		<table style="width: 100%; border: 0px;" cellpadding="3"
			cellspacing="0">
			<tbody>
					
				<tr>
					<td colspan="4" align="center">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td align="center" colspan="4">
						
						
					</td>
				</tr>
				
				
				<tr>
					<td colspan="4"></td>
				</tr>
				<tr>
					<td align="center" colspan="4" style="padding-top: 10px;">
						
					</td>
				</tr>
			</tbody>
		</table>
	</div>
			
			<div id="paymentDeclarationDiv" style="display:none;">
					
					<div id="terms&CondtionDiv" style="height: 205px;overflow-y: scroll;">
								<h1 class="pageTitle" style="margin-left: 400px;margin-top: 15px;"><u>TERMS AND CONDITIONS</u></h1>
								</br>
								
								<p><b>A. ONLINE APPLICATION</b></p>
										<p>I agree to the following terms and conditions</p>
										<ol>
											<li>Submission of Online application form does not assure admittance to Written Test.
											 My application will be accepted only if I meet all the eligibility criteria and 
											 the supporting documents furnished by me are found in order.</li>
											<li>I am responsible for furnishing genuine particulars and uploading correct 
											documents as per requirement specified by TNUSRB.</li>
										</ol>
										
								 <p><b>B. ONLINE PAYMENT</b></p>
								 		<p>I agree to the following terms and conditions before using the Online payment facility.</p>
										<ol>
											<li>I am liable for refusal or declination of payment by Net banking and by the 
											credit/debit card supplier for any reason including quoting of incorrect account number or incorrect personal details.</li>
											<li>I am responsible for checking of deduction of payment amount from my account and I understand that Tamil Nadu 
											Uniformed Services Recruitment Board is under no obligation to bring this fact to my attention.</li>
											<li>Refunds, if applicable will be at the discretion of the TNUSRB and same will only be made to the debit/credit card 
											used for the original transaction. For the avoidance of doubt nothing in this Policy shall require the Tamil Nadu 
											Uniformed Services Recruitment Board to refund amounts (or part thereof) unless such amounts (or part thereof) have previously been paid.</li>
										</ol>
								 <p><b>C. OFFLINE PAYMENT</b></p>
										<p>I agree to the following terms and conditions before using the offline payment facility</p>
										<ol>
											<li>I agree that I have to make cash payment at any State Bank of India Branch with 
											the printed challan generated while filling up of Online Application and entering the SBI e-pay payment portal.</li>
											<li>I understand that I have to make the offline payment within the date 
											mentioned in the challan generated, failing which the challan will become invalid</li>
										</ol>
										<br/>
										<br/>
										<br/>
										
								<p><b>GENERAL</b></p>
									<p>I also agree to the following terms and conditions as an applicant/candidate</p>
									<ol>
										<li>The candidate should not indulge in any attempt to canvass or bring
										 influence on TNUSRB personnel, personally or through anybody at any stage of the examination 
										 or write anything on the answer books which reveals his/her identity or indulge in malpractice 
										 during any stage of the examination failing which he/she will be disqualified.</li>
										<li>The candidate is liable to be barred from future examinations conducted by
										 TNUSRB for furnishing false particulars in the matter of qualification or the nature 
										 of pass in various subjects, religion, community, etc, or in case of tampering or 
										 alteration with documents / certificates.</li>
										 <li>The claims of the candidates with regard to the date of birth, educational/ technical 
										 qualifications and community are accepted only on the information furnished by them in their 
										 applications. Their candidature therefore will be provisional and subject to the Board satisfying
										  itself, about their age, educational / technical qualifications, community etc. Mere admission 
										  to the interview or inclusion of name in the list will not confer on the candidates any right
										   for appointment. The candidature is therefore, provisional at all stages and the Board reserves 
										   the right to reject any candidature at any stage, even after the selection has been made.</li>
										   <li>If the applicant attempts any tampering, alteration with the documents or certificates, 
										   he is liable to be debarred from appearing for any of the selections and examinations conducted
										    by the Board and consequently from entry into public service itself.</li>
										    <li>Making false or vexatious allegations against the Board in petitions addressed to it 
										    or any other authority. will be viewed seriously and that the candidate responsible for such act 
										    will be debarred from appearing for the examinations and selections held by this Board permanently 
										    or for such period of years as the Board may decide.</li>
										   <li>In no event will the Tamil Nadu Uniformed Services Recruitment Board be 
										   liable for any damages whatsoever arising out of the use, inability to use, or the 
										   results of use of this site, any websites linked to this site, or the materials or 
										   information contained at any or all such sites, whether based on warranty, contract, 
										   tort or any other legal theory and whether or not advised of the possibility of such damages.</li>
										   <li>Tamil Nadu Uniformed Services Recruitment Board reserves the entire right to modify/amend/remove 
										   this privacy statement anytime and without assigning any reason. Nothing contained herein creates or
										    is intended to create a contract/ agreement between Tamil Nadu Uniformed Services Recruitment Board 
										    and any user visiting the Tamil Nadu Uniformed Services Recruitment Board website or providing identifying 
										    information of any kind.</li> 
									</ol>
								
					 </div>
					<br/>
				<b>DECLARATION</b>
				<br/>
				<table>
					<tr>
						
						<td style="width:20px; margin-top:2px;">
						<s:checkbox name="declaration" id="declaration" />
						</td>
						<td>
						I submit that I have read and understood and that I agree to the <b>terms and conditions</b> 
						with regard to submission of my online application.
						</td>
					</tr>
								
				</table>
				<br/>
				 <s:submit id="btn" value="Submit" onclick="return proceedtopayment();" cssClass="submitBtn button-gradient"></s:submit> 
				 <!--<input type="button" id="btn" value="Submit" onclick="proceedtopayment();" class="submitBtn button-gradient">-->
				 </div>
		</s:form>
		<br /><br />
		
		
	</div>
	</s:if>
	<s:elseif test='%{jobPaymentFlag == "N"}'>
		<center><b><s:property value = "%{jobPaymentMsg}"/></b></center>
	</s:elseif>
	</div>

<div id="overlayPay" class="web_dialog_overlay_declr"></div>
<div id="dialogPay" class="web_dialog_declr" style="overflow: _scroll; width: 450px; height: 180px;display: none;left:68%">
		
   <table style="width: 100%; border: 0px;" cellpadding="3" cellspacing="0" >
				<tr>
         			<td align="center" class="web_dialog_title_declr" colspan="4">Payment Message</td>
      			</tr>
				<tr></tr>
				<tr>
				<td>
				<p align="center" id="offlineMsg" style="margin-top: 25px;"> 
				Your previous Offline Cash transaction is pending. YOU ARE NOT ALLOWED TO INITIATE ANY NEW TRANSACTION (refer Instruction & brochure)
				</p>
				</td>
				</tr>
				<tr ><td align="center" colspan="4" >
				<input type="button" name="btnOk" value="Ok" onclick="HideDialogPay();" class="submitBtn button-gradient"/>
				</td>
				 
				</tr>
    </table>
</div>
	
</div>