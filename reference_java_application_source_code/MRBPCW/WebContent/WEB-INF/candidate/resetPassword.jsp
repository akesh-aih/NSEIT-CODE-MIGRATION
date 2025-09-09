<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function() {
	$(".subNavBg").hide();
	$("#frmFirstLoginChangePassword_").removeClass('error-massage');

});


function validateFirstLoginForm(){
	$("#error-massage4").hide();
	var newPwd = $("#newPassword1").val();
	var conPwd = $("#confirmPassword1").val();
	
    var message = "";

	
	if($.trim(newPwd).length == 0){
		message = message + "Please enter New Password."+"##";
		$("#newPassword1").addClass('red-border');
		$("#newPassword1").val('');
	}
	else	if($.trim(newPwd).length < 8) {
			message = message + "New Password should be of minimum 8 characters."+"##";
			$("#newPassword1").addClass('red-border');
			$("#newPassword1").val('');
	    }
	
	else if($.trim(conPwd).length == 0){
		message = message + "Please enter Confirm Your Password"+"##";
		$("#confirmPassword1").addClass('red-border');
		$("#confirmPassword1").val('');
	}
	
	else if(conPwd != "" && newPwd != "")
	{
		var regex = new Array();
        regex.push("[A-Z]"); //Uppercase Alphabet.
        regex.push("[a-z]"); //Lowercase Alphabet.
        regex.push("[0-9]"); //Digit.
        regex.push("[$@$!%*#?&]"); //Special Character.
    	var passed = 0;
   	 
     //Validate for each Regular Expression.
    for (var i = 0; i < regex.length; i++) {
	     if(new RegExp(regex[i]).test(newPwd)) {
	    	 passed++;
	       }
   	 }
   
	if(newPwd.length<8){
		message = message + "Password should be of minimum 8 characters."+"##";
	}
		
	if(newPwd.length > 7 && passed <= 3){
		message = "Password must contain at least one Upper case character (A-Z), one Lower case character (a-z), one Number (0-9) and one Special character (!@#&)."+"##";
	 }else{
			if(newPwd != conPwd){
				message = message + "New Password and Confirm Your Password do not match."+"##";
				$("#confirmPassword1").addClass('red-border');
				$("#newPassword1").addClass('red-border');
				$("#confirmPassword1").val('');
				$("#newPassword1").val('');
			}
	 	}
	}
	
	
	if(message != ""){
		//$('#registerCaptcha').val("");
		var ulID = "error-ul3";
		var divID = "error-massage3";
		createErrorList(message, ulID, divID); 
		 
		
		return false;
	}
	else
	{
		
		return true;
	}
}




function validatePassword(fieldName,minNumberOfDigits, maxNumberOfDigits) {
	fieldName = $("#newPassword1").val();
	var alphaNumericPattern =  "^[a-z0-9/_/$]{" +minNumberOfDigits + "," +maxNumberOfDigits + "}";
	var regExpr = new RegExp(alphaNumericPattern,"i");
	var sourceField = event != null ? event.srcElement:e.target;
		
	if(fieldName != null && fieldName != "null" && fieldName != "undefined") {
	  sourceField = document.getElementById(fieldName);
	}
	var message = "Password must be a combination of alphabets and numbers";
	message = message + "\n and must be between " + minNumberOfDigits + 
	          " and " + maxNumberOfDigits + " chars. Only !@ special characters are allowed.";
	var sourceFieldValue = sourceField.value;
	if(sourceFieldValue.length < minNumberOfDigits || 
	           sourceFieldValue.length > maxNumberOfDigits){
	 alert(message);
	 sourceField.focus();
	 return false;
	}
	if (!regExpr.test(sourceFieldValue)) {
	 alert(message);
	 sourceField.focus();
	 return false;
	}
	regExpr = new RegExp("[a-z/_/$]{1}","i");
	if(!regExpr.test(sourceFieldValue)){
	 alert(message);
	 sourceField.focus();
	 return false;
	}
	regExpr = new RegExp("[0-9]{1}","i");
	if(!regExpr.test(sourceFieldValue)){
	  alert(message);
	  sourceField.focus();
	  return false;
	}
		
	regExpr = new RegExp("[!@#$^]{1}","i");
	if(!regExpr.test(sourceFieldValue)){
	  alert(message);
	  sourceField.focus();
	  return false;
	} 
	 return true;
}

/* ! 33
@ 64
# 35
$ 36
removed  % 37
& 38

removed	 * 42  asper req addde for pass*/

function CheckPasswordStrength(password)
{
		 var password_strength = document.getElementById("password_strength");
		 var password_suggestion = document.getElementById("password_suggestion");
	        //TextBox left blank.
	        if (password.length == 0) {
	            password_strength.innerHTML = "";
	            password_suggestion.innerHTML = "";
	            return;
	        }
	 	
	        //Regular Expressions.
	        var regex = new Array();
	        regex.push("[A-Z]"); //Uppercase Alphabet.
	        regex.push("[a-z]"); //Lowercase Alphabet.
	        regex.push("[0-9]"); //Digit.
	        regex.push("[$@$!%*#?&]"); //Special Character.
	 
	        var passed = 0;
	 
	        //Validate for each Regular Expression.
	        for (var i = 0; i < regex.length; i++) {
	            if (new RegExp(regex[i]).test(password)) {
	                passed++;
	            }
	        }
	 
	        //Validate for length of Password.
	        if (passed > 3 && password.length > 8) {
	            passed++;
	            password_suggestion.innerHTML = "";
	        }else{
	        	 password_suggestion.innerHTML = "Password must contain at least one Upper case character (A-Z), one Lower case character (a-z), one Number (0-9) and one Special character (!@#&)."
	        }
	 
	        //Display status.
	        var color = "";
	        var strength = "";
	        switch (passed) {
	            case 0:
	            case 1:
	                strength = "Weak";
	                color = "red";
	                break;
	            case 2:
	                strength = "Good";
	                color = "darkorange";
	                break;
	            case 3:
	            case 4:
	                strength = "Strong";
	                color = "green";
	                break;
	            case 5:
	                strength = "Very Strong";
	                color = "darkgreen";
	                break;
	        }
	        password_strength.innerHTML = strength;
	        password_strength.style.color = color;
} 

function alphanumericForPWD(e)
{
  
	/*  !@#$&         %**/  
	var unicode=e.charCode? e.charCode : e.keyCode;
	  if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && (unicode<48 || unicode>57) && unicode != 8 && unicode != 9  && unicode !=33 && unicode !=64
			  && unicode !=35 && unicode !=36  && unicode !=38 )
	  {   
	     //alert("Password should be alpha numeric only.");
	      return false;
	  }
}  

</script>
<style>
.banner {
	height: 87px;
}
.accordions .borderBoxDiv {
	padding: 25px !important;
}
.headerMiddleBg {
	height: 105px;
}
</style>
<div class="headerTop" ></div>
<div class="headerBottom" ></div>
<div id="dashboard">
	<div class="accordions">
		<div  class="borderBoxDiv dash_forgetpass" >
			<div class="accordion">
				<h1 class="pageTitle" title="Reset Password">Reset Password</h1>
				<s:form id = "frmFirstLoginChangePassword" action="LoginAction_changePassword.action" method = "post">
		<s:hidden name="emailAddressHid" id="emailAddressHid"/>
		<s:hidden name="mobileNoHid" id="mobileNoHid"/>
		<s:hidden name="captchaHid" id="captchaHid"/>
		<s:hidden name="confirmPasswordHid" id="confirmPasswordHid"/>
		<s:hidden name="answerHid" id="answerHid"/>
		<s:hidden name="newPasswordHid" id="newPasswordHid"/>
		<s:hidden name="questionHid" id="questionHid"/>
		<input type="hidden" name="hidCaptchaID" value="<%= session.getId() %>"/>
					<div id="error-massage3" style="display:none" class="error-massage"><br/>
					<div class="error-massage-text" style="margin:0;   padding:0;">
						<ul style="margin:0; margin-left:20px; padding:0;" id="error-ul3">
						</ul>
					</div>
				</div>
					<s:if test="hasActionMessages()"><br/>
						<div id="error-massage4" class="error-massage">
							<div class="error-massage-text" style="margin:0;   padding:0;">
								<s:actionmessage escape="false" cssClass="error-massage"/>
							</div>
						</div>
					</s:if>
					<p class="orgNote">Following special characters are allowed<br>
					!@#$&
					</p>
					<div class="row mt20">
						<div class="col-sm-4">
					         <div class="form-group">
								<label class="control-label"><s:text name="forgot.newpassword"/> <span class="manadetory-fields">*</span></label>
								<s:password name="newPassword" id="newPassword1" cssClass="form-control" size="12" maxlength="12" onpaste="return false;" onkeypress="return alphanumericForPWD(event);" onkeyup="CheckPasswordStrength(this.value)" /><span id="password_strength"></span>  <span id="password_suggestion"></span>
								<div class="clear"></div>
								<span class="note"><s:text name="register.minchar"/></span>
					         </div>
						</div>
						<br><br>
						<div class="col-sm-4">
							 <div class="form-group">
								<label class="control-label"><s:text name="forgot.reenterpwd"/> <span class="manadetory-fields">*</span></label><!-- onkeypress="return alphanumericForPWD(event);" -->
								<s:password name="confirmPassword" id="confirmPassword1" cssClass="form-control" maxlength="12" onkeypress="return alphanumericForPWD(event);" size="12" onpaste="return false;"  ondragstart="return false;" ondrop="return false;"/>
							 </div>
						</div>
					</div>
					<br><br>
					<div class="row">
						<div class="col-sm-2">
							<s:submit value="Submit" cssClass="ripple1 btn btn-warning btn-block" title="Submit" onclick="return validateFirstLoginForm();"></s:submit>
						</div>
					</div>
					<s:token/>
				</s:form>
			</div>
		</div>
	</div>
</div>
