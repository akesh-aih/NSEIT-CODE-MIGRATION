<%@ taglib prefix="s" uri="/struts-tags"%>

<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<script type="text/javascript">

	$(document).ready(function() {

		$("#userId").focus();		
		$("#btnForgotPassword").click(function() {

			var flag = validateFormForPwd();
			if(flag == true){
				//alert("validated");
				var dataString = "";
				var userId = $("#userId").val();
				var iPin = $("#ipin").val();
				var password = $("#newPwd").val();
				
				dataString = "userId="+userId+"&ipin="+iPin+"&newPwd="+encodeURIComponent(password);
				
				$.ajax({
					url: "RegistrationAction_forgotPassword.action",
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
						//alert(responseText);
						textMessage = responseText.split(',');
						message = responseText.substring(2, responseText.length);
						$("#lblForgotPasswordMessage").html(message);
						$("#btnCloseForgotPasswordResult").unbind();
	
						if(responseText.charAt(0) == "0")
						{	
							//alert('inside');
							$("#btnCloseForgotPasswordResult").bind('click', function() {
								clearFields();
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
							$("#btnCloseForgotPasswordResult").bind('click', function() {
								clearFields();
								HideAll();
								ShowBlock('block2');
							});
						}
	
						HideAll();
						//alert('before10');
						ShowBlock('block10');
						//alert('after10');
					}
				});
			}
			else
				return false;
		});
	});

	function focusOnUserID(){
		$("#userId").focus();
	}
	function validateFormForPwd(){
		//alert('inside');
		var userId = $("#userId").val();
		var iPin = $("#ipin").val();
		var password = $("#newPwd").val();
		var rePass = $("#rePwd").val();
		var userType = userId.substring(0,3);
		
		
		var message = "";
		if(userId == "")
			message = "Please enter user ID."+"##";
		
		if(userType == "MBA" || userType == "MCA" || userType == "MBC" || userType == "mba" || userType == "mca" || userType == "mbc"){
			if(iPin == ""){
				message = message + "Please enter IPIN."+"##";
			}
		}
		
		if(password == "")
			message = message + "Please enter password."+"##";
		//if(rePass == "")
			//message = message + "Please re-enter password."+"##";
		if(password != rePass){
			//alert('sdf');
			message = message + "Passwords do not match."+"##";
		}
		var validPwd = "";
		if(password != "")
			validPwd = validatePassword(password);

		if(validPwd != true)
			message = message + validPwd;

		if(message != ""){
			var ulID = "error-ul1";
			var divID = "error-massage1";
			createErrorList(message, ulID, divID); 
			$("#userId").focus();
			return false;
		}
		else
			return true;
	}

	function closeForgotPwd(){
		
		HideAll();
	}
	$(function() {
		$( "#datePicker1" ).datepicker({
			maxDate: "-1D",
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

	function alphaNumeric(e){
		var unicode=e.charCode? e.charCode : e.keyCode
		if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && (unicode<48||unicode>57) && unicode != 8 && unicode != 9 && unicode != 46  && unicode != 39)
			return false;
	}
	 function alphanumericForPWD(e)
  {
  var unicode=e.charCode? e.charCode : e.keyCode;
  if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && (unicode<48 || unicode>57) && unicode != 8 && unicode != 9  )
  {   
     alert("Password should be alpha numeric only.");
      return false;
  }
  } 
	
</script>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="error-massage1" style="display:none">
   	<div class="error-massage-text">
      <ul style="margin:0; margin-left:20px; padding:0;" id="error-ul1">
      </ul>
    </div>
</div>
<body>
<s:form name="forgotpwd" id="forgotpwd">

	<div class="fogot-cont">

		<table width="300" border="0" cellspacing="0" cellpadding="4">
		  <tr>
			<td width="140"><span class="manadetory-fields">*</span></td>
		    <td><s:textfield name="userId" id="userId" size="10" onkeypress="" maxlength="50" cssClass="inputField "/></td>
		  </tr>
		  <!-- <tr>
		    <td>Secret Question &nbsp;<span class="manadetory-fields">*</span></td>
		    <td>
		    	<s:select list = "questionList" name = "question" label = "Name" cssClass="s"
							headerKey="" headerValue = "Select Secret Question" id = "question" value="%{question}"/>	
		    </td>
		  </tr>-->
		  <tr>
		    <td><span class="manadetory-fields">*</span></td>
		    <td><s:password name="newPwd" id="newPwd" size="12" onkeypress="" maxlength="12" cssClass="inputField" onkeypress="return alphanumericForPWD(event);"/></td>
		  </tr>
		  <tr>
		    <td><span class="manadetory-fields">*</span></td>
		    <td><s:password name="rePwd" id="rePwd" size="12" onkeypress="" maxlength="12" cssClass="inputField" onkeypress="return alphanumericForPWD(event);"/></td>
		  </tr>
		  <tr>
		  	<td><span class="lighttext">Administrative User please ignore the IPIN.</span> </td>
		  </tr>
		  <tr>
		  	<td></td>
		  	<td><input type="button" value="<s:text name="forgotpassword.submit"/>" class="submitBtn button-gradient" id="btnForgotPassword" />&nbsp;&nbsp;</td>
		  </tr>
		</table>
	</div>
</s:form>
</body>