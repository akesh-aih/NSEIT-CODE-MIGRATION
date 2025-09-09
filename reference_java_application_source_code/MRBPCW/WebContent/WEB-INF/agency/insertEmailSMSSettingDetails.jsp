<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page
	import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<script type="text/javascript" src="js/AES.js"></script>
<script>
function frmLogin_onsubmit()
	{
		
		if($("#EligibilityCriteria").validationEngine('validate')==true)
		{
		$("#smsContent").val($.trim($("#smsContent").val()));
		$("#emailContents").val($.trim($("#emailContents").val()));
		
		
 
		if($("#smsContent").val() != "" )
		{
			//$("#smsContent").val(Aes.Ctr.encrypt($("#smsContent").val(), 'pass!dt@12-!e', 256));
			
		}
		if($("#emailContents").val() != "")
		{
		// $("#emailContents").val(Aes.Ctr.encrypt($("#emailContents").val(), 'pass!dt@12-!e', 256));
		}
		
	}
}

function emptySystemVariables(){
	$('#errorMessagesDiv').empty();
	$('#lbKeywords').empty();
}
function validateInput(){

	var message = "";
	
	var disciplineType = $("#disciplineType").val();
	var activityName = $("#activityName").val();;
	var lbKeywords = $("#activityName").val();
	var emailSmsSubject = $("#emailSmsSubject").val();

	

	if (activityName == ""){
		message = message + "Please select an activity"+"##";
	}
	
	if ((activityName!="3" || activityName!="4")&&(disciplineType == "")){
		message = message + "Please select a Post Name"+"##";
	}

	if (lbKeywords == ""){
		message = message + "Please select a system Variable"+"##";
	}


	

	if ($("#emailChkBox").attr('checked')){
		var emailContents = $("#emailContents").val();
		if (emailContents == ""){
			message = message + "Please enter the email contents"+"##";
		}
		var emailSmsCCAddress = $("#emailSmsCCAddress").val();
		if(emailSmsCCAddress != ""){
			var validEmail = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i.test(emailSmsCCAddress);
			if(validEmail == false)
				message = message + "Please enter correct Email address"+"##";
		}
		
		if (emailSmsSubject == ""){
		message = message + "Please enter the subject"+"##";
	}
	}

	if ($("#smsChkBox").attr('checked')){
		var smsContent = $("#smsContent").val();
		if (smsContent == ""){
			message = message + "Please enter the sms Content"+"##";
		}
	}
	
	
	
	if (!($("#emailChkBox").attr('checked') || $("#smsChkBox").attr('checked'))){
		message = message +" Please select either Email or SMS checkBox";
	}
	
	
	


	if(message != ""){
		var ulID = "errorMessages";
		var divID = "errorMessagesDiv";
		createErrorList(message, ulID, divID); 
		return false;
	}
	else{
		frmLogin_onsubmit();
		return true;
	}


	
}

 
	$(document).ready(function() {
		
		$("a:contains('Email/SMS Configuration')").html('<span class="fadeSubmenu">Email/SMS Configuration</span>');

		
		$("#activityName").change(
		function (){
		getActivityDetails($(this).val());
		//getEmailSMSDetails();
		}
		);

		$("#addEmailContent").click(
		function (){
		addEmailContents();
		}
		);

		$("#addSMSContent").click(
		function (){
			addSMSContents();
		}
		);
	
		$("#labelId").change(
		function (){
			getActivityDetails($(this).val());
//			getEmailSMSDetails();
			}
		);
});



	function imposeMaxLength(Event, Object, MaxLen)
	{
	    return (Object.value.length <= MaxLen)||(Event.keyCode == 8 ||Event.keyCode==46||(Event.keyCode>=35&&Event.keyCode<=40))
	}
	
	function addEmailContents(){
		var message = "";
		var select = $("#lbKeywords").val();
		if(select==  null || select == "null" || select == '' )
		{
			message = message+"Please select an activity"
		}
		if(message != ""){
			var ulID = "errorMessages";
			var divID = "errorMessagesDiv";
			createErrorList(message, ulID, divID); 
		}else{
			$("#emailContents").val($("#emailContents").val() + " " + select);
			$("#errorMessagesDiv").hide();
		}
	
}

	function checkValueForEmail(){
	var emailContents = $("#smsContent").val();

	var emailContentsTrim  = jQuery.trim(emailContents);
	if (emailContentsTrim == ""){
		return "Please enter text in textarea";
		}
	}
	
	function checkValueForSMS(){
	var smsContents = $("#smsContent").val();

	var smsContentsTrim  = jQuery.trim(smsContents);
	if (smsContentsTrim == ""){
		return "Please enter text in textarea";
	}
}

function addSMSContents(){
	var select = $("#lbKeywords").val();
	var message = ""; 
		
	if(select==  null || select == "null" || select == '' ){
		message = message +"Please select Activity";
	}
	if(message != ""){
		
		var ulID = "errorMessages";
		var divID = "errorMessagesDiv";
		createErrorList(message, ulID, divID); 
	}else{
		$("#smsContent").val($("#smsContent").val() + " " + select);
		$("#errorMessagesDiv").hide();
	}
	
}

	
	function getEmailSMSDetails()
	{
		$("#emailChkBox").attr("disabled",false);
				
		$("#emailSmsSubject").val("");
		$("#emailSmsCCAddress").val("");
		$("#emailContents").val("");
		$("#smsContent").val("");
		$("#emailChkBox").attr('checked', false);
		$("#smsChkBox").attr('checked', false);
		
		var discipline = $("#disciplineType").val();
		var activityName = $("#activityName").val();
		
		if (activityName==""){
			return ;
		} 

		if (activityName=="" && discipline==""){
			return ;
		}
		
		var dataString = "disciplineType="+discipline+"&activityId="+activityName;
		
		$.ajax({
			url: "SettingsAction_getEmailSMSDetailsForDiscipline.action",
			async: true,
			data: dataString,
			beforeSend: function()
			{
				
			},
			error:function(ajaxrequest)
			{
				alert('Error refreshing. Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText)
			{
				responseText = $.trim(responseText);
				
				
				
				
				if(responseText.length > 0)
				{
					var element = "";
					element  = responseText.split("||");
					
					if (element[1]!='null'){
						$("#emailSmsSubject").val(element[1]);
					}else{
						$("#emailSmsSubject").val("");
					}


					if(element[2]!='null'){
						$("#emailSmsCCAddress").val(element[2]);
					}else{
						$("#emailSmsCCAddress").val("");
					}

					
					if (element[3]!='null'){
					$("#emailContents").val(element[3]);
					}else{
						$("#emailContents").val("");
					}

					if (element[5]!='null'){
						$("#smsContent").val(element[5]);
					}else{
						$("#smsContent").val("");
					}
					

					if (element[0]=="Y") {
						$("#emailChkBox").attr('checked', true);
						$("#emailChkBox").attr("disabled",false);
						$("#emailContents").attr('disabled', false);
						$("#emailSmsCCAddress").attr('disabled', false);
						$("#emailSmsSubject").attr('disabled', false);
					} 
					if (element[0]=="N") {
						$("#emailChkBox").attr('checked', false);
						$("#emailChkBox").attr("disabled",false);
						$("#emailContents").attr('disabled',true);
						$("#emailSmsCCAddress").attr('disabled', true);
						$("#emailSmsSubject").attr('disabled', true);
					} 

					if (element[4]=="Y") {
						$("#smsChkBox").attr('checked', true);
						$("#smsContent").attr('disabled', false);
					} 
					if (element[4]=="N") {
						$("#smsChkBox").attr('checked', false);
						$("#smsContent").attr('disabled', true);
					} 
				}
			}
		});
	}
	
	function getActivityDetails(activityId){
	$("#emailChkBox").attr("disabled",false);
	if(activityId!="")
	{	
		var dataString = "activityId="+activityId;
	
		$.ajax({
			url: "SettingsAction_getActivityList.action",
			async: true,
			data: dataString,
			beforeSend: function()
			{
				
			},
			error:function(ajaxrequest)
			{
				alert('Error in refreshing . Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText)
			{
			
				if ($("#emailChkBox").attr("disabled")){
				}
				responseText = $.trim(responseText);
				textMessae = responseText.split(',');
				message = responseText.substring(2, responseText.length);
				if(textMessae[0]=='9'){
						alert(message);
						return false;
				}else{
				
				if(responseText.length > 0)
				{
					$('#lbKeywords').empty();
					
					 var element = responseText.split(",");

	
					 $.each(element, function(val) {
						    $('#lbKeywords').append(  
						            $('<option></option>').val(element[val]).html(element[val])
						     );
					 }); 
					 getEmailSMSDetails();
					 $("#emailChkBox").attr("disabled",false);
				}
			}
			}
		});
	
	}else{
		$('#lbKeywords').empty()
		$("#emailSmsSubject").val("");
		$("#emailSmsCCAddress").val("");
		$("#emailContents").val("");
		$("#smsContent").val("");
		$("#emailChkBox").attr('checked', false);
		$("#smsChkBox").attr('checked', false);
		$("#emailChkBox").attr("disabled",false);
	}
	
}


	function enableTextarea(){
			 if ($('#emailChkBox').attr('checked')){
				 $("#emailContents").removeAttr('disabled');
				 $("#emailSmsSubject").removeAttr('disabled');
				$("#emailSmsCCAddress").removeAttr('disabled');
			 }else{
				 $("#emailContents").attr('disabled', true);
				 $("#emailSmsSubject").attr('disabled', true);
				$("#emailSmsCCAddress").attr('disabled', true);
			 }
	}

	function enableTextareaForSms(){
			 if ($('#smsChkBox').attr('checked')){
				 $("#smsContent").removeAttr('disabled');
			 }else{
				 $("#smsContent").attr('disabled', true);
			 }
	}

	function disbledActivity(){
		var activityName=$("#activityName").val();
		if(activityName!="" && (activityName=="3" || activityName=="4"))
			$("#disciplineType").attr("disabled",true);
		else
			 $("#disciplineType").removeAttr('disabled');
	}
</script>
<style>
.onetime{
  	display: none;
  }
  </style>
<div class="container">
	<div class="container common_dashboard">
		<div class="fade" id="pop3"></div>
		<s:form action="SettingsAction_insertEmailSMSSettingDetails"
			name="EligibilityCriteria" id="EligibilityCriteria">
			<s:token />
			<s:hidden name="mailChkBoxFlag" id="mailChkBoxFlag"></s:hidden>
			<s:hidden name="smsChkBoxFlag" id="smsChkBoxFlag"></s:hidden>
			<div class="errorMessageActive" id="EmailSmsError">
				<s:actionmessage />
			</div>
			<div id="AgencyEligibility" style="height: 650px">

				<h1 class="pageTitle" title="Email / SMS Configuration">
					Email / SMS Configuration
				</h1>
				<!-- <div class="hr-underline2"></div> -->


				<div id="errorMessagesDiv" class="error-massage"
					style="display: none">
					<div class="error-massage-text">
						<ul style="margin: 0; margin-left: 20px; padding: 0;"
							id="errorMessages">
						</ul>

					</div>
				</div>
				<!-- Box Container Start -->



				<!-- Left Container Start -->


				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label"> <s:text name="agencyemaismsconf.test" /> &nbsp; <span class="manadetory-fields">*</span> </label>
							<s:select list="disciplineList" name="disciplineType"
								label="Post Name" cssClass="s form-control" headerKey=""
								headerValue="Select Post" id="disciplineType"
								value="%{disciplineType}" onchange="getEmailSMSDetails();"
								 />
							<div class="feedbackerrorMsg" id="Error2"></div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label"> <s:text name="agencyemaismsconf.activity" /> &nbsp; <span class="manadetory-fields">*</span> </label>
							<s:select list="activityDetailsMap" name="activityId" label="Name" headerKey="" headerValue="Select Activity" id="activityName" onchange="return disbledActivity();" cssClass="form-control" />
							<div class="feedbackerrorMsg" id="Error1"></div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<s:checkbox name="emailChkBox" id="emailChkBox" onclick="enableTextarea();" />
							<label class="control-label"><s:text name="agencyemaismsconf.emailcontent" /> <span class="manadetory-fields">*</span></label> 
							<label class="control-label"><s:text name="agencyemaismsconf.cc" /></label>
							<s:textfield cssClass="form-control" name="emailSmsCCAddress" id="emailSmsCCAddress" maxlength="500" disabled="true"/>
						</div>
					</div>
					<div class="col-md-6">	
						<div class="form-group">
							<label class="control-label mb5"><s:text name="agencyemaismsconf.subject" /></label>
							<s:textfield cssClass="form-control" name="emailSmsSubject" id="emailSmsSubject" disabled="true" maxlength="500" />
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-5">
						<div class="form-group">
							<div id="activityId">
								<div class="control-label">
									<s:text name="register.systemVariables" />
									&nbsp;
									<span class="manadetory-fields">*</span>
								</div>
								<select class="fieldDropdown" id="lbKeywords" name="kyewords" size="4" style="height: 270px; width: 100%;"></select>
								<div id="activityError" style='color: red; display: none'>
									Please select an Activity
								</div>
							</div>
							<div class="feedbackerrorMsg" id="Error3"></div>
						</div>
					</div>
					<div class="col-md-2 text-center">
						<div class="buttonCont">
							<div class="emailButton1">
								<input type="button" class="submitBtn button-gradient"
									title=">>" value=">>" id="addEmailContent" />
							</div>
							<div class="emailButton2">
								<input type="button" class="submitBtn button-gradient"
									title=">>" value=">>" id="addSMSContent" />
							</div>

						</div>
					</div>
					<div class="col-md-5">
						<div class="row">
							<div class="col-md-12">
								<div class="control-label">&nbsp;</div>
								<s:textarea name="emailContent" rows="5" cols="45"
											id="emailContents" cssClass="AddressAreaForEmail text-input"
											disabled="true"
											onkeypress="return imposeMaxLength(event,this,3000);" cssStyle="width:100%;" />
								<div class="feedbackerrorMsg" id="Error4"></div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<s:checkbox name="smsChkBox" id="smsChkBox" fieldValue="true"
											label="Check Me for testing"
											onclick="enableTextareaForSms();" />

								<span class="control-label"><s:text
										name="agencyemaismsconf.smscontent" /><span
									class="manadetory-fields">*</span> </span>

								<br /> 

								<s:textarea name="smsContent" rows="5" cols="45"
									id="smsContent" cssClass="AddressAreaForEmail "
									onkeypress="return imposeMaxLength(event,this,150);"
									disabled="true"
									onpaste="return imposeMaxLength(event,this,150);" cssStyle="width:100%;"></s:textarea>

								<div class="feedbackerrorMsg" id="Error5"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-2">
						<s:submit key="agencyemaismsconf.submit" cssClass="submitBtn button-gradient btn-block"
							onclick="return validateInput();"></s:submit>
					</div>
					<div class="col-md-2">
						<input type="reset" class="submitBtn button-gradient btn-block" value="<s:text name="agencyemaismsconf.reset" />"
							title="Reset" onclick="emptySystemVariables()" />
					</div>
				</div>
					<!--<tr>
						<td colspan="2">
							<table width="100%" border="0">
								<tr>
									 <td valign="top" width="30%">
										<div id="activityId" style="float: left; width: auto;">
											<div class="field-label">
												<s:text name="register.systemVariables" />
												&nbsp;
												<span class="manadetory-fields">*</span>
											</div>
											<select class="fieldDropdown" id="lbKeywords" name="kyewords"
												size="4">
											</select>
											<div id="activityError" style='color: red; display: none'>
												Please select an Activity
											</div>
										</div>
										<div class="feedbackerrorMsg" id="Error3"></div>
									</td> 
									<td valign="top" width="50px">
										<div class="buttonCont fl-left">

											<div class="emailButton1">

												<input type="button" class="submitBtn button-gradient"
													title=">>" value=">>" id="addEmailContent"  ;" />
											</div>

											<div class="emailButton2">

												<input type="button" class="submitBtn button-gradient"
													title=">>" value=">>" id="addSMSContent"   />
											</div>

										</div>
									</td>

									<td valign="top" style="padding-top: 18px; text-align:right;">







										

										<br />
										<br />


										<strong><s:text name="agencyemaismsconf.subject" />
										</strong>&nbsp;&nbsp;
										<s:textfield cssClass="inputField" name="emailSmsSubject"
											id="emailSmsSubject" disabled="true" maxlength="500"
											style="width:560px;" />

										<br />
										<br />
										<s:textarea name="emailContent" rows="5" cols="45"
											id="emailContents" cssClass="AddressAreaForEmail text-input"
											disabled="true"
											onkeypress="return imposeMaxLength(event,this,3000);" cssStyle="width:100%;" />
										<div class="feedbackerrorMsg" id="Error4"></div>

										<br />
										<br />

										<s:checkbox name="smsChkBox" id="smsChkBox" fieldValue="true"
											label="Check Me for testing"
											onclick="enableTextareaForSms();" />

										<span class="SMSLabel"><s:text
												name="agencyemaismsconf.smscontent" /><span
											class="manadetory-fields">*</span> </span>

										<br /> 

										<s:textarea name="smsContent" rows="5" cols="45"
											id="smsContent" cssClass="AddressAreaForEmail "
											onkeypress="return imposeMaxLength(event,this,150);"
											disabled="true"
											onpaste="return imposeMaxLength(event,this,150);" cssStyle="width:100%;"></s:textarea>

										<div class="feedbackerrorMsg" id="Error5"></div>


									</td>
								</tr>
								<tr>
									<td colspan="2">
										&nbsp;
									</td>
									<td>

										<s:submit key="agencyemaismsconf.submit"
											cssClass="submitBtn button-gradient"
											onclick="return validateInput();"></s:submit>
										&nbsp;&nbsp;
										<input type="reset" class="submitBtn button-gradient"
											value="<s:text name="agencyemaismsconf.reset" />"
											title="Reset" onclick="emptySystemVariables()" />
										</div>
									</td>
								</tr>

							</table>
						</td>
					</tr>


				</table>-->








				<!-- Left Container End -->

				<!-- Button Container Start -->

				<!-- Button Container End -->

			</div>

		</s:form>
	</div>
</div>

