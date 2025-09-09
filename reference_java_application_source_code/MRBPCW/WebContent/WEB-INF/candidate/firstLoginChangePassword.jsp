<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function() {

});

function validateFirstLoginForm(){

	var userName = $("#userName").html();
	var oldPwd = $("#oldPassword").val();
	var newPwd = $("#newPassword").val();
	var conPwd = $("#confirmPassword").val();
	var message = "";
//alert(oldPwd + " - " + newPwd + " - " + conPwd);
	if(oldPwd == "")
		message = "Please enter old password."+"##";
	if(newPwd == "")
		message = message + "Please enter new password."+"##";
	if(oldPwd == newPwd)
		message = message + "Old password and new password should be different."+"##";
	if(newPwd != conPwd)
		message = message + "Passwords do not match."+"##";

	var validPwd = "";
	if(newPwd != "")
		validPwd = validatePassword(newPwd);

	if(validPwd != true)
		message = message + validPwd;

	if(message != ""){
		var ulID = "error-ul3";
		var divID = "error-massage3";
		createErrorList(message, ulID, divID); 
		return false;
	}
	else
		return true;
}

		
		</script>


<!-- Change Password Box Start -->
<div class="login-container">

<s:form id = "frmFirstLoginChangePassword" action="RegistrationAction_changePasswordForFirstTime">
	<s:hidden name="userName" value="%{#request[\'userName\']}"></s:hidden>
	<div style="margin-left:200px;" >
		<h1 class="pageTitle" title="Change Password">Change Password</h1>
		
		<div class="loginbox box-gradient">
		<div id="error-massage3" style="display:none" class="error-massage">
      		<div class="error-massage-text">
      		<ul style="margin:0; margin-left:20px; padding:0;" id="error-ul3">
      	
      		</ul>      	
      	 </div>
      </div>
			<table align="center" width="300" border="0" cellspacing="0" cellpadding="4">
			  <tr>
					<div class="msgg"><s:actionmessage/></div>
			    <td width="140"><s:text name="login.username"/></td>
			    <td width="160"><strong><s:label id="userName" name="userName" value='%{#request["userName"]}' /></strong></td>
			  </tr>
			  <tr>
			    <td><s:text name="changepassword.oldpassword"/> <span class="manadetory-fields">*</span></td>
			    <td><s:password name="oldPassword" id="oldPassword" cssClass="inputField" size="15" maxlength="30"/></td>
			  </tr>
			  <tr>
			    <td><s:text name="changepassword.newpassword" /> <span class="manadetory-fields">*</span></td>
			    <td><s:password name="newPassword" id="newPassword" cssClass="inputField" size="12" maxlength="12"/></td>
			  </tr>
			  <tr>
			    <td><s:text name="forgot.reenterpwd"/> <span class="manadetory-fields">*</span></td>
			    <td><s:password name="confirmPassword" id="confirmPassword"	cssClass="inputField " maxlength="12" size="12" /></td>
			  </tr>
			  <tr>
			    <td>&nbsp;</td>
			    <td><s:submit value="Submit" cssClass="submitBtn button-gradient" title="Change Password" onclick="return validateFirstLoginForm();"></s:submit></td>
			  </tr>
			</table>
			
			<div></div>
		</div>
	</div>
</s:form>
</div>

<!-- Change Password Box End -->




