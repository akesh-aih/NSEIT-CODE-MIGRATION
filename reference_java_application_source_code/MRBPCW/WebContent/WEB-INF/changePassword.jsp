<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="js/AES.js"></script>
<script type="text/javascript" src="js/login.js"></script>
<script type="text/javascript" src="js/paging.js"></script>
<script type="text/javascript" src="js/pagingForPayment.js"></script>
<script src="js/jquery-3.6.3.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/jcarousellite_1.0.1c4.js"></script>
<script src="js/languages/jquery.validationEngine-en.js" type="text/javascript" charset="utf-8"></script>
<script src="js/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="js/jquery.form.js"></script>
<script type="text/javascript" src="js/placeholders.jquery.js"></script>
<script src="js/core-min.js" type="text/javascript"></script>
<script src="js/sha1-min.js" type="text/javascript"></script>
<script src="js/enc-base64-min.js" type="text/javascript"></script>
<script src="js/cipher-core-min.js" type="text/javascript"></script>
<script src="js/hmac-min.js" type="text/javascript"></script>
<script src="js/pbkdf2-min.js" type="text/javascript"></script>
<script src="js/aes-min.js" type="text/javascript"></script>
<script src="js/md5-min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/bootstrap-select.min.js"></script>

<script type="text/javascript">

	var salt;
    function generateKey(p){
		salt = CryptoJS.lib.WordArray.random(128/8);
		return CryptoJS.PBKDF2(p, salt, { keySize: 128/32, iterations: 100 });  
	}			
	
	$(document).ready(function() {
		
		$("#oldPassword").val("");
		$("#newPassword").val("");
		$("#confirmPassword").val("");
		$("#error-massage2").hide();
		$("#oldPassword").focus();
		
		var msg = "Please enter only alphabets and numbers and special characters such as !@#& Must be between 8 and 12 characters.";     
       
		function forgotpasswd(value){
            var regexForPasswd = /^[A-Za-z0-9!@#&]*$/i.test(value); //!@#$&
            return regexForPasswd
        }    
		
        jQuery('#oldPassword').on('change', function (event) {
            var result = forgotpasswd(this.value);
            if(!result) {
                showPopUpForMobileCP("#oldPassword", msg);
            }
        });
        
        jQuery('#newPassword').on('change', function (event) {
            var result = forgotpasswd(this.value);
            if(!result) {
                showPopUpForMobileCP("#newPassword", msg);
            }
        });
        
        jQuery('#confirmPassword').on('change', function (event) {
            var result = forgotpasswd(this.value);
            if(!result) {
                showPopUpForMobileCP("#confirmPassword", msg);
            }
        });
        
		$("#btnChangePassword1").click(function() {
			
			$("#error-massage2").hide();
			var flag = validateChangePwd();
			
			if(flag == true){
				$("#oldPassword").val($.trim($("#oldPassword").val()));
				$("#newPassword").val($.trim($("#newPassword").val()));
				$("#confirmPassword").val($.trim($("#confirmPassword").val()));
			
				var iv  = CryptoJS.lib.WordArray.random(128/8);
				var key512Bits1000Iterations = generateKey(iv.toString());
				
				var oldPassword = $("#oldPassword").val();
				var newPassword = $("#newPassword").val();
				var encrypted1 = CryptoJS.AES.encrypt(oldPassword, key512Bits1000Iterations, {iv: iv}, { padding: CryptoJS.pad.Pkcs5, mode: CryptoJS.mode.CBC})
				var encrypted2= CryptoJS.AES.encrypt(newPassword, key512Bits1000Iterations, {iv: iv}, { padding: CryptoJS.pad.Pkcs5, mode: CryptoJS.mode.CBC})
				var lloginId=$("#loginId").text();
				
				var dataString = "oldPassword="+encodeURIComponent(encrypted1)+"&newPassword="+encodeURIComponent(encrypted2)+"&loginId="+lloginId
				+"&sa="+encodeURIComponent(salt)+"&iv="+encodeURIComponent(iv) ;

				$.ajax({
					url: "RegistrationAction_updatePasswordDetails.action",
					async: true,
					data: dataString,
					beforeSend: function(){
						
					},
					error:function(ajaxrequest){
						alert('Error registering user. Server Response: '+ajaxrequest.responseText);
					},
					success:function(responseText){
						responseText = $.trim(responseText);
						textMessage = responseText.split(',');
						message = responseText.substring(2, responseText.length);
						$("#lblChangePasswordMessage").html(message);
						$("#btnCloseChangePasswordResult").unbind();
	
						if(responseText.charAt(0) == "0") {	
							$("#btnCloseChangePasswordResult1").hide();
							$("#btnCloseChangePasswordResult").show();
							$("#btnCloseChangePasswordResult").bind('click', function() {
								PopHideAll();
							});
							
						} else if(textMessage[0] == "9")	{
							alert(message);
							return false;
							
						} else if(textMessage[1] == "Your old password is not correct.") {
							var password_suggestion = document.getElementById("password_suggestion");
					        password_suggestion.innerHTML = "";
							var ulID = "error-ul2";
							var divID = "error-massage2";
							createErrorList(message, ulID, divID);
							return false;
							
						} else	{
							$("#btnCloseChangePasswordResult1").hide();
							$("#btnCloseChangePasswordResult").show();
							$("#btnCloseChangePasswordResult").bind('click', function() {
								PopHideAll();
								ShowPop('pop2');
							});
						}
						
						if(message=="Your password has been changed successfully.") {
							PopHideAll();
							ShowPop('pop10');
							return;
						}
						
						if(message=="Session is Invalid.") {
							$("#btnCloseChangePasswordResult").hide();
							$("#btnCloseChangePasswordResult1").show();
							$("#btnCloseChangePasswordResult1").bind('click', function() {
								PopHideAll();
							});
							ShowPop('pop10');
						}
					},
					complete: function(){ 
						$("#oldPassword").val("");
						$("#newPassword").val("");
						$("#confirmPassword").val("");
					  }
				});
				
			} else
				return false;
		});
	});

	function focusOn(){
		$("#oldPassword").focus();
	}
	
	function validateChangePwd(){
		var oldPwd = $("#oldPassword").val();
		var newPwd = $("#newPassword").val();
		var confirmPwd = $("#confirmPassword").val();
		var message = "";
		
		if (oldPwd == "") {
			message = "Please enter old password."+"##";
		}
		
		if (newPwd == "") {
			message = message + "Please enter new password."+"##";
		}
		
		var regex = new Array();
        regex.push("[A-Z]"); //Uppercase Alphabet.
        regex.push("[a-z]"); //Lowercase Alphabet.
        regex.push("[0-9]"); //Digit.
       	regex.push("[!@#&]"); //Special Character.
       	
	    var passed = 0;
	   	 
	    //Validate for each Regular Expression.
	    for (var i = 0; i < regex.length; i++) {
			if(new RegExp(regex[i]).test(newPwd)) {
		    	 passed++;
		    }
	   	}
	   
		if (newPwd.length < 8) {
			message = message + "Password should be of minimum 8 characters."+"##";
		}
			
		if (newPwd.length > 7 && passed <= 3) {
			message = message + "Password must contain at least one Upper case character (A-Z), one Lower case character (a-z), one Number (0-9) and one Special character (!@#&)."+"##"; 
		} else {
			if (newPwd == oldPwd) {
				message = message + "Old password and new password should be different."+"##";
			}
			if (newPwd != confirmPwd) {
				message = message + "New Password and Confirm New Password should be same."+"##";
			}
		}
			
		var validPwd = "";
		if (newPwd != "") {
			validPwd = validatePassword(newPwd);
		}
		
		if (validPwd != true)
			message = message + validPwd;
			
		if (message != "") {
			var ulID = "error-ul2";
			var divID = "error-massage2";
			createErrorList(message, ulID, divID); 
			return false;
		}
		else
			return true;
	}
	
	function resetTextField() {
		
		$("#oldPassword").val("");
		$("#newPassword").val("");
		$("#confirmPassword").val("");
		$("#password_strength").html("");
		$("#password_suggestion").html("");
		$("#error-massage2").hide();
	}
	
	function closeChangePwd() {
		PopHideAll();
		resetTextField();
	}
	
	function isAlphaNumeric(propertyId)	{
		var fieldValue = document.getElementById(propertyId);
		val = fieldValue.value;
		return (/^[0-9A-Za-z]+$/.test(val));
	}
	
	/* ! 33 @ 64 # 35 $ 36 removed  % 37 & 38 removed * 42 as per req addde for pass*/
	
	function CheckPasswordStrength(password) {
		
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
       	regex.push("[!@#&]"); //Special Character.
 
        var passed = 0;
 
        //Validate for each Regular Expression.
        for (var i = 0; i < regex.length; i++) {
            if (new RegExp(regex[i]).test(password)) {
                passed++;
            }
        }
 
        //Validate for length of Password.
        if (passed > 3 && password.length > 7) {
            passed++;
            password_suggestion.innerHTML = "";
        } else {
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
	
	function alphanumericForPWD(e) {
		var unicode = e.charCode ? e.charCode : e.keyCode;
		if ((unicode < 97 || unicode > 122) && (unicode < 65 || unicode > 90)
				&& (unicode < 48 || unicode > 57) && unicode != 8 && unicode != 9
				&& unicode != 33 && unicode != 64 && unicode != 35
				&& unicode != 38) {
			// && unicode != 36 alert("Password should be alpha numeric only.");
			return false;
		}
}
	
	function showPopUpForMobileCP(id,innerValue) {
    	$(id).val('');
       	$("#overlay6").show();
       	$("#dialog6").fadeIn(300);
       	document.getElementById("dynamicContent1").innerHTML = innerValue;
   	}

   	function HideDialog2() {
       	document.getElementById("dynamicContent1").innerHTML = "";
       	$("#overlay6").hide();
       	$("#dialog6").fadeOut(300);
   	}

</script>

<!-- Change Password Start -->
<body>
	<div class="fullscreen-container" id="pop2">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close"
						onClick="resetTextField(); PopHideAll();" id="Close">&#215;</button>
					<h2 class="modal-title">
						<s:text name="changepassword.title" />
					</h2>
				</div>
				<div class="modal-body">
					<div id="error-massage2" style="display: none;"
						class="error-massage">
						<div class="error-massage-text">
							<ul style="margin: 0; margin-left: 20px; padding: 0; color: red !important;"
								id="error-ul2">
							</ul>
						</div>
					</div>
					<s:form method="post" name="changepwd" id='changepwd'>
						<div class="row">
							<div class="col-sm-10 col-sm-offset-1">
								<p class="orgNote">Following special characters are allowed
									!@#&</p>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-10 col-sm-offset-1">
								<div class="form-group">
									<label class="control-label"><s:text
											name="changepassword.oldpassword" /> <span
										class="manadetory-fields">*</span></label>
									<s:password name="oldPassword" id="oldPassword"
										cssClass="form-control " size="12" maxlength="12"
										ondragstart="return false;" ondrop="return false;"
										onpaste="return false;"
										onkeypress="return alphanumericForPWD(event);" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-10 col-sm-offset-1">
								<div class="form-group">
									<label class="control-label"><s:text
											name="changepassword.newpassword" /> <span
										class="manadetory-fields">*</span></label>
									<s:password name="newPassword" id="newPassword"
										cssClass="form-control " size="12" maxlength="12"
										onkeypress="return alphanumericForPWD(event);"
										ondragstart="return false;" ondrop="return false;"
										onpaste="return false;"
										onkeyup="CheckPasswordStrength(this.value)" />
									<span id="password_strength"></span> <span
										id="password_suggestion"></span>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-10 col-sm-offset-1">
								<div class="form-group">
									<label class="control-label"><s:text
											name="changepassword.confirmpassword" /> <span
										class="manadetory-fields">*</span></label>
									<s:password name="confirmPassword" id="confirmPassword"
										cssClass="form-control" maxlength="12" size="12"
										ondragstart="return false;" ondrop="return false;"
										onpaste="return false;"
										onkeypress="return alphanumericForPWD(event);" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6 col-sm-offset-3">
								<input type="button"
									value="<s:text name="changepassword.submit"/>"
									class="ripple1 btn btn-warning btn-block changePWDBTN ajaxRefresh"
									id="btnChangePassword1" />
							</div>
						</div>
						<s:token />
					</s:form>
				</div>
			</div>
		</div>
	</div>
	<!-- Change Password End -->
	<div class="clear"></div>
	<div id="overlay6" class="web_dialog_overlay_declr"></div>
	<div class="fullscreen-container" id="dialog6">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<!-- <button type="button" class="close" onclick="HideDialog2()">&times;</button> -->
					<!-- <h2 class="modal-title">Confirmation</h2> -->
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-lg-12 text-center">
							<h4 class="pageTitle" id="dynamicContent1"></h4>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="row">
						<div class="col-sm-6 col-sm-offset-3">
							<input type="button" name="btnCancel" value="OK"
								onclick="HideDialog2()"
								class="ripple1 btn btn-warning btn-block mt10" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Forgot Password Result Start -->
	<div class="clear"></div>
	<div id="overlay5" class="web_dialog_overlay_declr"></div>
	<div class="fullscreen-container" id="pop10">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<!-- <button type="button" class="close" onclick="HideDialog2()">&times;</button> -->
					<!-- <h2 class="modal-title">Confirmation</h2> -->
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-lg-12 text-center">
							<h4 class="pageTitle" id="lblChangePasswordMessage"></h4>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="row">
						<div align="center">
							<input type="button" value="Close"
								class="submitBtn btn btn-warning mt10"
								id="btnCloseChangePasswordResult" onClick="menuLinks('LoginAction_signout.action')"  /> <br /> <input
								type="button" value="Close" class="submitBtn btn btn-warning"
								id="btnCloseChangePasswordResult1"
								onClick="menuLinks('LoginAction_signout.action')" /><br />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Forgot Password Result End -->
</body>
