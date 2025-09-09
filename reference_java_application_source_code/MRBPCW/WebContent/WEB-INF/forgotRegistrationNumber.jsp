<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>

<script type="text/javascript">
	$(document).ready(function() {
		
		$("#forgotRegContainer").validationEngine({promptPosition : "bottomLeft", scroll: false});
		$("#btnForgotRegistrationNo").click(function() {
			
			if($("#forgotRegContainer").validationEngine('validate')==false)
				return;
			var dataString = "";
			var eMail= $("#eMail").val();
			var mobileNo = $("#mobileNo").val();
			var dateOfBirth = $("#dateOfBirth").val();
			dataString = "emailAddress="+eMail+"&dateOfBirth="+dateOfBirth+"&mobileNo="+mobileNo;

			$.ajax({
				url: "RegistrationAction_forgotRegistrationNumber.action",
				async: true,
				data: dataString,
				beforeSend: function(){
					//alert('before send');
				},
				error:function(ajaxrequest){
					alert('Error registering user. Server Response: '+ajaxrequest.responseText);
				},
				success:function(responseText){
					responseText = $.trim(responseText);
					textMessage = responseText.split(',');
					message = responseText.substring(2, responseText.length);
					$("#lblForgotRegistrationMessage").html(message);
					$("#btnCloseForgotRegResult").unbind();

					if(responseText.charAt(0) == "0")
					{
						$("#btnCloseForgotRegResult").bind('click', function() {
							HideAll();
						});
					}
					else if(textMessage[0] == "9")
					{
						alert(message);
						return false;
					}
					else
					{
						$("#btnCloseForgotRegResult").bind('click', function() {
							HideAll();
							ShowBlock('block0');
						});
					}

					HideAll();
					ShowBlock('block9');
				}
			});
		});
	});
	$(function() {
		$( ".datePicker" ).datepicker({
			maxDate: "-1D",
			changeMonth: true,
			changeYear: true,
			yearRange: range,
			showOn: "button",
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
			onSelect: function(selected) {
			$("#forgotRegContainer").validationEngine('hide');
		    }
		});
	});

	function numbersonly(e){
			var unicode=e.charCode? e.charCode : e.keyCode
			if (unicode!=8){
			if ((unicode<48||unicode>57) && unicode != 9 && unicode != 46) //if not a number
				return false //disable key press
			}
		}
		
		function numbersOnlyWithoutDot(e){
		var unicode=e.charCode? e.charCode : e.keyCode
		
		if ((unicode < 48 || unicode > 57) && unicode != 46 && unicode != 39 && unicode!=8 && unicode != 52){ //if not a number
			return false; //disable key press
		}
		// seperate cond for .
		if (unicode == 46){
			return false;
		}
		
	}
	
</script>

<!-- Forgot Registration No. Start -->

<div class="forgot-pass box-gradient" id="block0">
<div><a href="javascript:void(0);" onclick="forgotRegistrationNumber();"><img src="images/Close.png" align="right" border="0" title="Close"/></a></div>
<div class="pad12">
<div class="titleBox fl-left"><h1 class="pageTitle">&nbsp;<s:text name="forgotuserid.title"/></h1></div>
<div class="closebtnBox fl-rigt"></div>
<div class="hr-underline clear"></div>
<div class="ForgotText">Enter your Mobile No. , Email Address and Date of Birth to receive the User ID. 
(The E-mail Address entered during registration process must be active for successful receiving an email to your account.)<br /><br /></div>

<s:form action="RegistrationAction" id="forgotRegContainer">
<div class="fogot-cont">
<div class="field-label"><s:text name="forgotuserid.mobileno"/>&nbsp;<span class="manadetory-fields">*</span></div>

<div>
<s:textfield  name="mobileNo" id="mobileNo" maxlength="10" onkeypress="return numbersOnlyWithoutDot(event);" cssClass="inputField validate[required]" errRequired="<%=ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.ERROR_LABEL_MOBILENO)%>" size="10"/>
</div>

<!-- For validation messages -->
<br/>

<div class="errorMessage" id="f-email">Please enter mobile Number.</div>

<div class="field-label"><s:text name="forgotuserid.emailaddress"/>&nbsp;<span class="manadetory-fields">*</span></div>

<div>
<s:textfield  name="eMail" id="eMail" maxlength="50" cssClass="inputField validate[required,custom[email]]" errCustom="Invalid email ID" errRequired="<%=ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.ERROR_LABEL_EMAILADDRESS)%>" size="10"/>
</div>

<!-- For validation messages -->
<br/>
<br/>

<div class="errorMessage" id="f-email">Please enter email Number.</div>

<div class="field-label"><s:text name="forgotuserid.dateofbirth"/>&nbsp;<span class="manadetory-fields">*</span></div>
<div>
<s:textfield  name="dateOfBirth" id = "dateOfBirth" readonly="true"  cssClass='datePicker validate[required]' errRequired="<%=ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.ERROR_LABEL_DOB)%>" size="10"/>
</div>
<div class="errorMessage" id="f-email">Please enter Date Of Birth.</div>

<!-- For validation messages -->
<br/>

<div align="left">
	<input type="button" value="<s:text name="forgotuserid.submit"/>" class="submitBtn button-gradient ajaxRefresh" title="Submit" id="btnForgotRegistrationNo"/>&nbsp;&nbsp;
	</div>
</div>

</s:form>
</div>
</div>
<!-- Forgot Registration No. End -->

<!-- Forgot Registration No. Result Start -->
<div class="forgot-pass box-gradient" id="block9">
<div><a href="javascript:void(0);" onclick="HideAll();"><img src="images/Close.png" align="right" border="0" title="Close"/></a></div>
<br/>
<div class="pad12">
<div class="titleBox fl-left"><h4 id="lblForgotRegistrationMessage" class="pageTitle"></h4></div>
</div>
<br/><br/><br/><br/>
<div align="center">
	<input type="button" value="Close" class="submitBtn button-gradient" title="Close" id="btnCloseForgotRegResult"/>
</div>

</div>


<!-- Forgot Registration No. Result End -->