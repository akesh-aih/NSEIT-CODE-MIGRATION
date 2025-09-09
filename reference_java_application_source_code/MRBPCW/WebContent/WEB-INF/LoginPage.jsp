<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ page language="java"
	import="com.nseit.generic.util.ConfigurationConstants"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<%@ page language="java" import="java.time.ZonedDateTime"%>
<%@ page language="java" import="com.nseit.generic.util.CommonUtil"%>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/common.css" rel="stylesheet" type="text/css"
	media="screen" />
<link rel="stylesheet" href="css/font-awesome.min.css" type="text/css"
	media="screen">
<link href="css/style.css" rel="stylesheet" type="text/css"
	media="screen" />
<link href="css/Flexgrid.css" rel="stylesheet" type="text/css"
	media="screen" />
<link type="text/css"
	href="css/ui-lightness/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
<link rel="stylesheet" href="css/validationEngine.jquery.css"
	type="text/css" />
<script src="js/jquery-3.6.3.min.js"></script>
<script type="text/javascript" src="js/login.js"></script>
<script type="text/javascript" src="js/paging.js"></script>
<script type="text/javascript" src="js/pagingForPayment.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/jcarousellite_1.0.1c4.js"></script>
<script src="js/languages/jquery.validationEngine-en.js"
	type="text/javascript" charset="utf-8"></script>
<script src="js/jquery.validationEngine.js" type="text/javascript"
	charset="utf-8"></script>
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

<script type="text/javascript">

function forgotPass() {
	var flag=validateFormForForgotPassword();
	
	if (flag == true) {
		var dataString = "";
		var userId = $("#userId").val();
		var mobileNo = $("#mobileNo2pass").val();
		var emailId= $("#eMail1pass").val();
		var choicepass = $("input[name='choicepass']:checked").val();
		dataString = "userId=" + userId.toUpperCase() + "&mobileNo=" + mobileNo + "&choicepass=" + choicepass + "&emailId=" + emailId;

		$.ajax({
			url : "RegistrationAction_forgotPasswordOTP.action",
			async : true,
			data : dataString,
			type : 'POST',
			beforeSend : function() {
				// alert('before send');
			},
			error : function(ajaxrequest) {
				alert('Error registering user. Server Response: ' + ajaxrequest.responseText);
			},
			success : function(responseText) {

				responseText = $.trim(responseText);
				//alert(responseText);

				textMessage = responseText.split(',');
				message = textMessage[1];
				if (textMessage.length > 2) {
					userName = textMessage[2];
					$("#usernameHidden").val(userName);
					mobile = textMessage[3];
					$("#mobileHidden").val(mobile);
					 genOtp = textMessage[4];
					$("#genOtpHidden").val(genOtp); 
				}

				$("#lblForgotPasswordMessage").html(message);
				$("#btnCloseForgotPasswordResult").unbind();

				if (responseText.charAt(0) == "0") {
					$("#btnCloseForgotPasswordResult").bind('click', function() {
						document.getElementById("block3").style.display = "none";
						clearFields();
						HideAll();
					});
				} else if (textMessage[0] == "9") {
					alert(message);
					return false;
				} else {
					$("#btnCloseForgotPasswordResult").bind('click', function() {
						clearFields();
						HideAll();
					});
				}
				if (message == 'Invalid User ID or Email ID.') {
					var ulID = "error-ull";
					var divID = "error-massage";
					createErrorList(message, ulID, divID);
				}
				if (message.indexOf('Invalid User ID or Mobile Number.') == 0) {
					var ulID = "error-ull";
					var divID = "error-massage";
					createErrorList(message, ulID, divID);
				}
				if (message.includes('Please try again.')) {
					var ulID = "error-ull";
					var divID = "error-massage";
					createErrorList(message, ulID, divID);
				}
				if (message.includes('Entered user ID is deactivated. Please check the communication sent on your registered email ID and proceed with active user ID.')) {
					var ulID = "error-ull";
					var divID = "error-massage";
					createErrorList(message, ulID, divID);
				}
				if (message == 'Please enter 10 digit Mobile number.') {
					var ulID = "error-ull";
					var divID = "error-massage";
					createErrorList(message, ulID, divID);
					
				}
				if (message == 'Please choose any one option.') {
					var ulID = "error-ull";
					var divID = "error-massage";
					createErrorList(message, ulID, divID);
					
				}
				if (message == 'OTP for verification sent to Registered Mobile Number.' || message == 'OTP for verification sent to Registered Email ID.') {
					$("#forgotPasswordDiv").hide();
					ShowFPOTP();
					var ulID = "error-ullFpOtp";
					var divID = "error-massageFpOtp";
					
					  <%
					String showOtpOnScreen = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SHOW_OTP_ON_SCREEN);
						if ("Y".equalsIgnoreCase(showOtpOnScreen)) {
					 %>
					message = message + " : " + genOtp;
					<%
						} else{  
					 %> 
					message = message + "##";// + " : " + genOtp
					  <%
						}
					%>  

					createErrorList(message, ulID, divID);
				}
			}
		});
} 
	
	else {
		if (message == 'Please choose any one option.') {
			var ulID = "error-ull";
			var divID = "error-massage";
			createErrorList(message, ulID, divID);
			
		}
		return false;
	}
}

function validateFormForPwd() {
	var choicepass = $("input[name='choicepass']:checked").val();
	var userId = $("#userId").val();
	var mobileNo = $("#mobileNo2pass").val();

	var message = "";
	if ($.trim(userId) == "") {
		message = "Please enter User ID." + "##";
	}
	if ($.trim(mobileNo) == "") {
		message = message + "Please enter Mobile Number." + "##";
	}

	if (message != "") {
		var ulID = "error-ull";
		var divID = "error-massage";
		createErrorList(message, ulID, divID);
		$("#userId").focus();
		return false;
	} else
		return true;
}

function validateFormForPwdEmail() {
	var userId = $("#userId").val();
	var emailid = $("#eMail1pass").val();

	var message = "";
	if ($.trim(userId) == "") {
		message = "Please enter User ID." + "##";
	}
	if ($.trim(emailid) == "") {
		message = message + "Please enter Email ID." + "##";
	}

	if (message != "") {
		var ulID = "error-ull";
		var divID = "error-massage";
		createErrorList(message, ulID, divID);
		$("#userId").focus();
		return false;
	} else
		return true;
}


function disableTextForgotUserId() {
	var choice = $("input[name='choice']:checked").val();
	if (choice == 'email') {
		$("#mobileNo2").attr('disabled', 'disabled');
		$("#eMail1").removeAttr('disabled', 'disabled');
		$("#eMail1").val("");
		$("#mobileNo2").val("");
	} else if (choice == 'mobile') {
		$("#eMail1").attr('disabled', 'disabled');
		$("#mobileNo2").removeAttr('disabled', 'disabled');
		$("#eMail1").val("");
		$("#mobileNo2").val("");
	}
}

function disableTextForgotPassword() {
	var choicepass = $("input[name='choicepass']:checked").val();
	if (choicepass == 'email') {
		$("#mobileNo2pass").attr('disabled', 'disabled');
		$("#eMail1pass").removeAttr('disabled', 'disabled');
		$("#eMail1pass").val("");
		$("#mobileNo2pass").val("");
		$("#otpflagfp").val("email");
		
	} else if (choicepass == 'mobile') {
		$("#eMail1pass").attr('disabled', 'disabled');
		$("#mobileNo2pass").removeAttr('disabled', 'disabled');
		$("#eMail1pass").val("");
		$("#mobileNo2pass").val("");
		$("#otpflagfp").val("mobile");
	}
}

function clearFields() {

	$("#myModal1").hide();
	$("#myModal").hide();
	$("#myModalChangePassword").hide();
	$("#userId").val("");
	$("#mobileNo").val('');
	$("#mobileNo1").val('');
	$('input[name="choice"]').prop('checked', false);
	$("#eMail1").removeAttr('disabled', 'disabled');
	$("#mobileNo2").removeAttr('disabled', 'disabled');
	$("#mobileNo2").val('');
	$("#question").val("");
	$("#secQuestionAnswer").val("");
	$("#secQuestionAnswer1").val("");
	$("#question1").val("");
	$("#newPwd").val("");
	$("#rePwd").val("");
	$("#eMail").val("");
	$("#eMail1").val("");
	$("#error-massageForgotUserId").hide();
	$("#error-massage").hide();// myModalChangePassword
	// $("#postApplied").val("");
	$("#eMail1pass").removeAttr('disabled', 'disabled');
	$("#mobileNo2pass").removeAttr('disabled', 'disabled');
	$("#mobileNo2pass").val('');
	$("#eMail1pass").val("");
	$('input[name="choicepass"]').prop('checked', false);
}

window.history.forward();
function noBack() {
	window.history.forward();
}

function menuLinks(link) {
	$("#menuForm").attr('action', link);
	$("#menuForm").submit();
}

function ShowDialog() {

	var modal = document.getElementById('myModal');
	modal.style.display = "block";

	$("#overlay").show();
	$("#dialog").fadeIn(300);
	$("#forgotPasswordDiv").show();
	$("#block101").hide();
	$("#username").attr('placeholder', "User ID");
	$("#password").attr('placeholder', "Password");
	if (modal) {
		$("#overlay").unbind("click");
	} else {
		$("#overlay").click(function(e) {
			HideDialog();
		});
	}
}

function ShowDialog1() {

	var modal = document.getElementById('myModal1');
	modal.style.display = "block";
	$("#secQuestionAnswer1").val('');
	$("#eMail1").val('');
	$("#question1").val('');
	$("#overlay").show();
	$("#dialog").fadeIn(300);
	$("#forgotUserIdDiv").show();
	$("#block10").hide();
	$("#username").attr('placeholder', "User ID");
	$("#password").attr('placeholder', "Password");
	if (modal) {
		$("#overlay").unbind("click");
	} else {
		$("#overlay").click(function(e) {
			HideDialog();
		});
	}
}

function CheckPasswordStrength(password) {

	var password_strength = document.getElementById("password_strength");
	var password_suggestion = document.getElementById("password_suggestion");
	// TextBox left blank.
	if (password.length == 0) {
		password_strength.innerHTML = "";
		password_suggestion.innerHTML = "";
		return;
	}

	// Regular Expressions.
	var regex = new Array();
	regex.push("[A-Z]"); // Uppercase Alphabet.
	regex.push("[a-z]"); // Lowercase Alphabet.
	regex.push("[0-9]"); // Digit.
	regex.push("[@!#&]"); //Special Character.

	var passed = 0;

	// Validate for each Regular Expression.
	for (var i = 0; i < regex.length; i++) {
		if (new RegExp(regex[i]).test(password)) {
			passed++;
		}
	}

	// Validate for length of Password.
	if (passed > 3 && password.length > 7) {
		passed++;
		password_suggestion.innerHTML = "";
	} else {
		password_suggestion.innerHTML = "Password must contain at least one Upper case character (A-Z), one Lower case character (a-z), one Number (0-9) and one Special character (!@#&)." 
	}

	// Display status.
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

function alphanumericsplCharForPWD(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 97 || unicode > 122) && (unicode < 65 || unicode > 90)
			&& (unicode < 48 || unicode > 57) && unicode != 8 && unicode != 9
			&& unicode != 33 && unicode != 64 && unicode != 35 //&& unicode != 36
			&& unicode != 38) {
		return false;
	}
}

function alphaNumericwithSplCharEmail(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 97 || unicode > 122) && (unicode < 65 || unicode > 90)
			&& (unicode < 48 || unicode > 57) && unicode != 46 && unicode != 45
			&& unicode != 95 && unicode != 64)
		return false;
}

function showPopUpForMobile(id, innerValue) {
	$(id).val('');
	$("#overlay1").show();
	$("#dialog1").fadeIn(300);
	document.getElementById("dynamicContent").innerHTML = innerValue;
}

function HideDialog1() {
	document.getElementById("dynamicContent").innerHTML = "";
	$("#overlay1").hide();
	$("#dialog1").fadeOut(300);

}

$(document).ready(function() {

	$('#password').bind("cut copy paste", function(e) {
		e.preventDefault();
	});

	$('#username').bind("cut copy paste", function(e) {
		e.preventDefault();
	});

	// Browser version check starts
	var txt = "<br><br>";

	var firefoxVersion = 16;
	var chromeVersion = 20;
	var ieVersion = 7;

	var nVer = navigator.appVersion;
	var nAgt = navigator.userAgent;
	var browserName = navigator.appName;
	var fullVersion = '' + parseFloat(navigator.appVersion);
	var majorVersion = parseInt(navigator.appVersion, 10);
	var nameOffset, verOffset, ix;

	<s:if test='%{changePasswordFlag=="true"}'>
		var modal = document.getElementById('myModalChangePassword');
		modal.style.display = "block";
	
		$("#overlay").show();
		$("#dialog").fadeIn(300);
	
		if (modal) {
			$("#overlay").unbind("click");
		} else {
			$("#overlay").click(function(e) {
				HideDialog();
			});
		}
	</s:if>

	function HideAll() {

		$("#myModal").hide();
		$("myModal1").removeAttr("style");
		$("#myModal1").hide();
		$("#myModalChangePassword").hide();
	}

	// $("#userId").focus();

	$("#FPSubmit").click(function() {

		var flag = validateFormForResetPwd();
		if (flag == true) {

			// alert("validated");
			var dataString = "";
			var fpOtp = $("#fpOtp").val();
			var uname = $("#usernameHidden").val();
			var mob = $("#mobileHidden").val();
			var password = $("#newPswd").val();
			var rePass = $("#confPswd").val();

			dataString = "newPswd=" + encodeURIComponent(password) + "&confirmPswd=" + encodeURIComponent(rePass) + "&verfOtp=" + fpOtp + "&userId=" + uname + "&mobile=" + mob;

			$.ajax({
				url : "RegistrationAction_forgotPassword.action",
				async : true,
				data : dataString,
				type : 'POST',
				beforeSend : function() {
					// alert('before send');
				},
				error : function(ajaxrequest) {
					alert('Error registering user. Server Response: ' + ajaxrequest.responseText);
				},
				success : function(responseText) {
					responseText = $.trim(responseText);
					// alert(responseText);

					textMessage = responseText.split(',');
					message = responseText.substring(2, responseText.length);
					$("#lblResetPasswordMessage").html(message);
					$("#btnCloseResetPasswordResult").unbind();

					if (responseText.charAt(0) == "0") {
						$("#btnCloseResetPasswordResult").bind('click', function() {
							document.getElementById("block3").style.display = "none";
							clearTimeOut();
							clearFields();
							HideAll();
						});
					} else if (textMessage[0] == "9") {
						alert(message);
						return false;
					} else {
						$("#btnCloseResetPasswordResult").bind('click', function() {
							clearTimeOut();
							clearFields();
							HideAll();
						});
					}

					if (message.includes('Please enter Verification OTP.')) {
						var ulID = "error-ul2";
						var divID = "error-massage2";
						createErrorList(message, ulID, divID);
					}
					if (message.includes('Please enter a valid 6 digit Verification OTP.')) {
						var ulID = "error-ul2";
						var divID = "error-massage2";
						createErrorList(message, ulID, divID);
					}
					if (message.includes('Invalid Verification OTP.')) {
						var ulID = "error-ul2";
						var divID = "error-massage2";
						createErrorList(message, ulID, divID);
					}
					if (message.includes('Please enter New Password.')) {
						var ulID = "error-ul2";
						var divID = "error-massage2";
						createErrorList(message, ulID, divID);
					}
					if (message.includes('Password should be between 8 to 12 characters.')) {
						var ulID = "error-ul2";
						var divID = "error-massage2";
						createErrorList(message, ulID, divID);
					}
					if (message.includes('Please enter valid New Password.')) {
						var ulID = "error-ul2";
						var divID = "error-massage2";
						createErrorList(message, ulID, divID);
					}
					if (message.includes('Please enter Confirm New Password.')) {
						var ulID = "error-ul2";
						var divID = "error-massage2";
						createErrorList(message, ulID, divID);
					}
					if (message.includes('Passwords do not match.')) {
						var ulID = "error-ul2";
						var divID = "error-massage2";
						createErrorList(message, ulID, divID);
					}
					if (message.includes('New Password cannot be same as Old Password.')) {
						var ulID = "error-ul2";
						var divID = "error-massage2";
						createErrorList(message, ulID, divID);
					}
					if (message == 'New Password set successfully.') {
						$("#error-ullFpOtp").html('');
						$("#error-ullFpOtpResent").html('');
						$("#error-ul2").html('');
						$("#blockUserPass").hide();
						$("#error-massageFpOtp").hide();
						$("#error-massageFpOtpResent").hide();
						$("#error-massage2").hide();
						$('#block100FP').show();
						$(".orgNote").hide();
						
					}
				}
			});
		} else
			return false;
	});

	$("#btnChangePassword").click(function() {
		enableLoadingAnimation();
		var flag = validateFormForCahngePwd();
		if (flag == true) {

			// alert("validated");
			var dataString = "";
			var userId = $("#userId").val();
			var password = $("#newPwd").val();
			var rePass = $("#rePwd").val();

			dataString = "newPwd=" + encodeURIComponent(password) + "&rePwd=" + encodeURIComponent(password);

			$.ajax({
				url : "LoginAction_changePassword.action",
				async : true,
				data : dataString,
				type : 'POST',
				beforeSend : function() {
					// alert('before send');
				},
				error : function(ajaxrequest) {
					alert('Error registering user. Server Response: ' + ajaxrequest.responseText);
				},
				success : function(responseText) {
					responseText = $.trim(responseText);
					// alert(responseText);
					textMessage = responseText.split(',');
					message = responseText.substring(2, responseText.length);
					$("#lblChangePasswordMessage").html(message);
					$("#btnCloseChangePasswordResult").unbind();
					disabledLoadingAnimation();
					if (responseText.charAt(0) == "0") {
						$("#btnCloseChangePasswordResult").bind('click', function() {
							document.getElementById("block3").style.display = "none";
							clearFields();
							HideAll();

						});
					} else if (textMessage[0] == "9") {
						alert(message);
						disabledLoadingAnimation();
						return false;
					} else {
						$("#btnCloseChangePasswordResult").bind('click', function() {
							clearFields();
							HideAll();
							// ShowBlock('block2');
						});
					}
					HideAll();
					ShowBlock('block100');
				}
			});
		} else
			return false;
	});

	function alphaNum(value) {
		var regexForSplChar = /^[a-zA-Z0-9]*$/i.test(value);
		return regexForSplChar
	}
	
	jQuery('#username').on('change', function(event) {
		var result = alphaNum(this.value);
		if (!result) {
			var msg = "Please enter only alphabets and numbers.";
			showPopUpForMobile("#username", msg);
		}
	});
	
	jQuery('#userId').on('change', function(event) {
		var result = alphaNum(this.value);
		if (!result) {
			var msg = "Please enter only alphabets and numbers.";
			showPopUpForMobile("#userId", msg);
		}
	});

	// for number only
	function regxfornumberonly(value) { // character allowed are '0-9'
		var cityregex = /^[0-9]*$/i.test(value);
		return cityregex
	}

	jQuery('#mobileNo').on('change', function(event) {
		var result = regxfornumberonly(this.value);
		if (!result) {
			var msg = "Please enter only numbers, must be 10 digits.";
			showPopUpForMobile("#mobileNo", msg);
		}
	});
	
	jQuery('#mobileNo2').on('change', function(event) {
		var result = regxfornumberonly(this.value);
		if (!result) {
			var msg = "Please enter only numbers, must be 10 digits.";
			showPopUpForMobile("#mobileNo2", msg);
		}
	});
	
	jQuery('#mobileNo2pass').on('change', function(event) {
		var result = regxfornumberonly(this.value);
		if (!result) {
			var msg = "Please enter only numbers, must be 10 digits.";
			showPopUpForMobile("#mobileNo2pass", msg);
		}
	});

	jQuery('#fpOtp').on('change', function(event) {
		var result = regxfornumberonly(this.value);
		if (!result) {
			var msg = "Please enter only numbers.";
			//showPopUpForMobile("#fpOtp", msg);
		}
	});

	function alphaNumericWithSplChar(value) {
		var regexForPasswd = /^[A-Za-z0-9!@#&]*$/i.test(value);
		return regexForPasswd
	}
	
	jQuery('#password').on('change', function(event) {
		var result = alphaNumericWithSplChar(this.value);
		if (!result) {
			var msgForPasswd = "Please enter only alphabets, numbers and special characters such as !@#& Must be between 8 and 12 characters.";
			showPopUpForMobile("#password", msgForPasswd);
		}
	});
	
	jQuery('#newPswd').on('change', function(event) {
		var result = alphaNumericWithSplChar(this.value);
		if (!result) {
			var msgForPasswd = "Please enter only alphabets, numbers and special characters such as !@#& Must be between 8 and 12 characters.";
			showPopUpForMobile("#newPswd", msgForPasswd);
		}
	});
	
	jQuery('#confPswd').on('change', function(event) {
		var result = alphaNumericWithSplChar(this.value);
		if (!result) {
			var msgForPasswd = "Please enter only alphabets, numbers and special characters such as !@#& Must be between 8 and 12 characters.";
			showPopUpForMobile("#confPswd", msgForPasswd);
		}
	});

	function regexforEmail(value) {
		var email = /^[a-zA-Z0-9@_.-]*$/i.test(value);
		return email
	}
	
	jQuery('#eMail').on('change', function(event) {
		var result = regexforEmail(this.value);
		if (!result) {
			var msgForEmail = "Please enter only alphabets, numbers and special characters such as @_.-";
			showPopUpForMobile("#eMail", msgForEmail);
		}
	});
	
	jQuery('#eMail1').on('change', function(event) {
		var result = regexforEmail(this.value);
		if (!result) {
			var msgForEmail = "Please enter only alphabets, numbers and special characters such as @_.-";
			showPopUpForMobile("#eMail1", msgForEmail);
		}
	});
	
	jQuery('#eMail1pass').on('change', function(event) {
		var result = regexforEmail(this.value);
		if (!result) {
			var msgForEmail = "Please enter only alphabets, numbers and special characters such as @_.-";
			showPopUpForMobile("#eMail1pass", msgForEmail);
		}
	});
});

var salt;
function generateKey(p) {
	salt = CryptoJS.lib.WordArray.random(128 / 8);
	return CryptoJS.PBKDF2(p, salt, {
		keySize : 128 / 32,
		iterations : 100
	});
}

function validateForm() {
	$("#invalid_msg").hide();
	var userName = $("#username").val();
	var password = $("#password").val();
	var message = "";
	if (userName == "") {
		message = "Please enter User ID" + "##";
	}
	if (password == "") {
		message = message + "Please enter Password";
	}

	if (message != "") {
		$("#login").hide();
		$("#error-massage").show();
		var ulID = "error-ul_user";
		var divID = "error-massage_user";
		createErrorList(message, ulID, divID);
		return false;
	}
	/*
	 * Addded for VAPT NPCIL(VAPT)[Start]
	 */
	var iv = CryptoJS.lib.WordArray.random(128 / 8);
	var key512Bits1000Iterations = generateKey(iv.toString());
	var encrypted = CryptoJS.AES.encrypt(password, key512Bits1000Iterations, {
		iv : iv
	}, {
		padding : CryptoJS.pad.Pkcs5,
		mode : CryptoJS.mode.CBC
	})
	// alert("encrypted="+encrypted);
	$('#password').val(encrypted);
	$('<input>').attr({
		type : 'hidden',
		id : 'sa',
		name : 'sa',
		value : salt
	}).appendTo('form');
	$('<input>').attr({
		type : 'hidden',
		id : 'iv',
		name : 'iv',
		value : iv
	}).appendTo('form');
	document.login.action = "LoginAction_authenticateUser.action";
	enableLoadingAnimation();
	document.login.submit();
}

function validateFormForResetPwd() {

	var newPass = $("#newPswd").val();
	var confirmPass = $("#confPswd").val();
	var otp = $("#fpOtp").val();
/*	var mob = $("#mobileHidden").val();*/
	var message = "";

	if ($.trim(otp).length == 0) {
		message = message + "Please enter Verification OTP." + "##";
	} else if ($.trim(otp).length < 6) {
		message = message + "Please enter 6 digit Verification OTP." + "##";
	}
	
	if (newPass == "") {
		message = message + "Please enter New Password." + "##";
	} else if ($.trim(newPass).length < 8 || $.trim(newPass).length > 12) {
		message = message + "New Password should be between 8 to 12 characters." + "##";
	}
	if (confirmPass == "") {
		message = message + "Please enter Confirm New Password." + "##";
	}
	if ($.trim(newPass).length > 1 && $.trim(confirmPass).length > 1 && newPass != confirmPass) {
		message = message + "Passwords do not match." + "##";
	}

	var regex = new Array();
	regex.push("[A-Z]"); // Uppercase Alphabet.
	regex.push("[a-z]"); // Lowercase Alphabet.
	regex.push("[0-9]"); // Digit.
	regex.push("[@!#&]"); //Special Character.
	var passed = 0;

	// Validate for each Regular Expression.
	for (var i = 0; i < regex.length; i++) {
		if (new RegExp(regex[i]).test(newPass)) {
			passed++;
		}
	}

	if (newPass.length > 7 && passed <= 3) {
		message = message + "Password must contain at least one Upper case character (A-Z), one Lower case character (a-z), one Number (0-9) and one Special character (!@#&)." + "##";
	}

	var validPwd = "";
	if (newPass != "") {
		validPwd = validatePassword(newPass);
	}

	if (validPwd != true)
		message = message + validPwd;

	if (message != "") {
		var ulID = "error-ul2";
		var divID = "error-massage2";
		createErrorList(message, ulID, divID);
		$("#fpOtp").focus();
		return false;
	} else
		return true;
}

function validateFormForCahngePwd() {
	var password = $("#newPwd").val();
	var rePass = $("#rePwd").val();

	var message = "";
	if (password == "") {
		message = message + "Please enter Password" + "##";
	}
	if ($.trim(password).length < 8) {
		message = message + "Password should be of minimum 8 characters" + "##";
	}
	if (rePass == "") {
		message = message + "Please enter Confirm Your Password" + "##";
	}
	if (password != rePass) {
		message = message + "Passwords do not match" + "##";
	}

	if (message != "") {
		var ulID = "error-ull";
		var divID = "error-massage-pwd";
		createErrorList(message, ulID, divID);
		$("#userId").focus();
		return false;
	} else
		return true;
}

function ShowFPOTP() {

	var modal = document.getElementById('myModal2');
	modal.style.display = "block";

	$("#overlay").show();
	$("#dialog").fadeIn(300);
	$("#blockUserPass").show();
	$("#fpInstructions").hide();
	$("#block100FP").hide();
	$("#fpOtp").val('');
	$("#newPswd").val('');
	$("#confPswd").val('');
	
	var password_strength = document.getElementById("password_strength");
	password_strength.innerHTML='';
	// $("#username").attr('placeholder',"Reference Number");
	/* $("#fpOtp").attr('placeholder',"Verification OTP"); */
	$('#sendOTPMobile').attr("disabled", true);
	$("#mobregister").hide();
	expireTime = getExpireTime(1);
	showOTPTimerForMob();
	timeout = setTimeout(function() {
		$('#sendOTPMobile').removeAttr("disabled");
	}, 120000);
	if (modal) {
		$("#overlay").unbind("click");
	} else {
		$("#overlay").click(function(e) {
			HideDialog();
		});
	}
}

function showOTPTimerForMob() {

	// var expireTime = getExpireTime(1);
	var timer = setInterval(function() {
		var now = new Date().getTime();
		var diff = expireTime - now;
		if (diff < 0) {
			clearInterval(timer);
			// elem.disabled = false;
			hideCountdownForMob();
			return;
		}
		var minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
		var seconds = Math.floor((diff % (1000 * 60)) / 1000);
		displayCountdownForMob(minutes, seconds);
	}, 1000);
}

function getExpireTime(minutes) {
	var dt = new Date().getTime();
	return new Date(dt + minutes * 120000).getTime();
}

function displayCountdownForMob(minutes, seconds) {
	document.getElementById("countdownTimerForMob").style.display = 'block';
	document.getElementById("countdownTimerForMob").style.color = 'red';
	document.getElementById("countdownTimerForMob").innerHTML = minutes + " minutes " + seconds + " seconds ";
}

function clearTimeOut() {
	document.getElementById("countdownTimerForMob").style.display = 'none';
}

function hideCountdownForMob() {
	$("#countdownTimerForMob").hide();
}

function sendFPOTPMob() {
	var mobFlag = "mobile";
	sendOtp(mobFlag);
	$("#otpSent").show();
}

var otpFlag;
function sendOtp(otpFlag) {

	$("#error-ul3").html('');
	$("#error-ullFpOtp").html('');
	$("#error-massageFpOtp").hide();
	$("#error-ullFpOtpResent").html('');
	$("#error-massageFpOtpResent").hide();
	var message = "";
	var uname = $("#usernameHidden").val();
	var mob = $("#mobileHidden").val();
	var choicepass = $("input[name='choicepass']:checked").val();
	$('#sendOTPMobile').attr("disabled", true);
	$.ajax({
		url : "RegistrationAction_generateFpOTP.action",
		async : false,
		data : "&mobile=" + mob + "&username=" + uname + "&choicepass=" + choicepass,
		type : 'POST',
		error : function(ajaxrequest) {
			// window.reload();
		},
		success : function(responseText) {

			responseText = $.trim(responseText);
			textMessage = responseText.split(',');
			message = textMessage[1];
			if (textMessage.length > 2) {
				genOtp = textMessage[2];
				$("#genOtpHidden").val(genOtp); 
			}
			if (responseText.length > 0) {
				if (responseText.indexOf('already exists') > -1) {
					$('#sendOTPMobile').removeAttr("disabled");
					$("#otpTrMob").hide()
					$("#mobregister").show();
					$("#verifyMobileOTPFlag").val('');
					var ulID = "error-ul3";
					var divID = "error-massage3";
					createErrorList(message, ulID, divID);
				} else if (responseText.indexOf('sent') > -1 || responseText.indexOf('resend') > -1) {
					$("#fpOtp").val("");
					$("#mobregister").hide();
					expireTime = getExpireTime(1);
					showOTPTimerForMob();
					timeout = setTimeout(function() {
						$('#sendOTPMobile').removeAttr("disabled");
					}, 120000);
					// $("#verifyMobileOTPFlag").val('true');
				}
				var ulID = "error-ul2";
				var divID = "error-massage2";
				 /* var generatedOtp = responseText.split('/ ')[1];
				$("#genOtpHidden").val($.trim(responseText.split('/ ')[1])); 
				message = responseText.split('/')[0] + " : " + generatedOtp + "##"; */
				if (message == 'OTP resend successfully') {
					var ulID = "error-ullFpOtpResent";
					var divID = "error-massageFpOtpResent";
				 
				 <%
					String showOtpOnScreenforResend = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SHOW_OTP_ON_SCREEN);
						if ("Y".equalsIgnoreCase(showOtpOnScreenforResend)) {
					 %>
					message = message + " : " + genOtp;
					<%
						} else{  
					 %> 
					message = message + "##";// + " : " + genOtp
					 <%
						}
					%> 
				createErrorList(message, ulID, divID);
				}
			}
		}
	});
}

function HideAllPopups() {
	document.getElementById("myModal2").style.display = "none";
	document.getElementById("block10").style.display = "none";
	document.getElementById("block100").style.display = "none";
	document.getElementById("block100FP").style.display = "none";
	document.getElementById("myModal1").style.display = "none";
	document.getElementById("myModal").style.display = "none";
	document.getElementById("error-massageFpOtpResent").style.display = "none";
}

function forgotUserId() {
	// enableLoadingAnimation();
	var flag = validateFormForForgotUserId();

	if (flag) {
		var dataString = "";
		var choice = $("input[name='choice']:checked").val();
		var eMail = $("#eMail1").val();
		var mobileNo = $("#mobileNo2").val();
		dataString = "choice=" + choice + "&emailAddress=" + eMail + "&mobileNo=" + mobileNo;
		// var post= 1;
		// dataString = "&emailAddress="+eMail+"&post="+post;
		$.ajax({
			url : "RegistrationAction_forgotUserId.action",
			async : true,
			data : dataString,
			type : 'POST',
			beforeSend : function() {
				// alert('before send '+dataString);
			},
			error : function(ajaxrequest) {
				
				alert('Error registering user. Server Response: ' + ajaxrequest.responseText);
			},
			success : function(responseText) {

				responseText = $.trim(responseText);
				textMessage = responseText.split(',');
				message = responseText.substring(2, responseText.length);
				$("#lblForgotPasswordMessage1").html(message);
				$("#btnCloseForgotPasswordResult1").unbind();
				disabledLoadingAnimation();
				if (responseText.charAt(0) == "0") {
					$("#btnCloseForgotPasswordResult").bind('click', function() {
						// document.getElementById("block2").style.display = "none";
						clearFields();
						HideAll();
					});
				} else if (textMessage[0] == "9") {
					alert(message);
					disabledLoadingAnimation();
					return false;
				} else {
					$("#btnCloseForgotPasswordResult").bind('click', function() {
						clearFields();
						HideAll();
						// ShowBlock('block2');
					});
				}
				if (message == 'Please choose any one option.') {
					var ulID = "error-ullForgotUserId";
					var divID = "error-massageForgotUserId";
					createErrorList(message, ulID, divID);
					return false;
				}
				if (message == 'Please enter 10 digit Mobile number.') {
					var ulID = "error-ullForgotUserId";
					var divID = "error-massageForgotUserId";
					createErrorList(message, ulID, divID);
					return false;
				}
				if (message == 'Please enter valid Mobile Number.') {
					var ulID = "error-ullForgotUserId";
					var divID = "error-massageForgotUserId";
					createErrorList(message, ulID, divID);
					return false;
				}
				if (message == 'Please enter valid Email ID.') {
					var ulID = "error-ullForgotUserId";
					var divID = "error-massageForgotUserId";
					createErrorList(message, ulID, divID);
					return false;
				}
				if (message == 'Email ID does not exist.') {
					var ulID = "error-ullForgotUserId";
					var divID = "error-massageForgotUserId";
					createErrorList(message, ulID, divID);
					return false;
				}
				if (message == 'Invalid Credentials or Attempt Exceeded.') {
					var ulID = "error-ullForgotUserId";
					var divID = "error-massageForgotUserId";
					createErrorList(message, ulID, divID);
					return false;
				}
				if (message == 'User ID has been sent to your registered Email ID and Mobile Number.') {
					// $("#postApplied").val("");
					$("#forgotUserIdDiv").hide();
					$("#block10").show();
					$("#error-ullForgotUserId").html('');
					$("#eMail1").val("");
					// var flag = true;
				}
				if (message == null) {
					$("#forgotUserIdDiv").hide();
					$("#forgotUserIdDiv").show();

				}
			}
		});
	} else {
		return false;
	}
}

function validateFormForForgotUserId() {

	var message = "";
	var eMail = $("#eMail1").val();
	var mobileNo = $("#mobileNo2").val();
	var choice = $("input[name='choice']:checked").val();

	// var post= $("#postApplied option:selected").val();
	/*
	 * if(post == "Select Post" || post == "") { message = message + "Please select Programme Applied For."+"##"; }
	 */
	if (choice == null || choice == "undefined" || choice == "") {
		message = message + "Please choose any one option." + "##";
	} else {
		if (choice == 'mobile') {
			if (mobileNo == null || mobileNo == "undefined" || mobileNo == "") {
				message = message + "Please enter Mobile Number." + "##";
			} else if (mobileNo.length != 10) {
				message = message + "Please enter 10 digit Mobile Number." + "##";
			}
		}
		if (choice == 'email') {

			if (eMail == null || eMail == "undefined" || eMail == "") {
				message = message + "Please enter Email ID." + "##";
			} else {
				var validEmail = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i
						.test(eMail);
				if (validEmail == false)
					message = message + "Please enter a valid Email ID." + "##";
			}
		}
	}
	if (message != "") {
		var ulID = "error-ullForgotUserId";
		var divID = "error-massageForgotUserId";
		createErrorList(message, ulID, divID);
		return false;
	} else {
		return true;
	}
}

function validateFormForForgotPassword() {
	var message = "";
	var eMail = $("#eMail1pass").val();
	var mobileNo = $("#mobileNo2pass").val();
	var choice = $("input[name='choicepass']:checked").val();
	var userId = $("#userId").val();
	if (userId == null || userId == "undefined" || userId == "") {
		message = message + "Please enter User Id." + "##";
	}
	if (choice == null || choice == "undefined" || choice == "") {
		message = message + "Please choose any one option." + "##";
	} else {
		if (choice == 'mobile') {
			if (mobileNo == null || mobileNo == "undefined" || mobileNo == "") {
				message = message + "Please enter Mobile Number." + "##";
			} else if (mobileNo.length != 10) {
				message = message + "Please enter 10 digit Mobile Number." + "##";
			}
		}
		if (choice == 'email') {

			if (eMail == null || eMail == "undefined" || eMail == "") {
				message = message + "Please enter Email ID." + "##";
			} else {
				var validEmail = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i
						.test(eMail);
				if (validEmail == false)
					message = message + "Please enter a valid Email ID." + "##";
			}
		}
	}
	if (message != "") {
		var ulID = "error-ull";
		var divID = "error-massage";
		createErrorList(message, ulID, divID);
		return false;
	} else {
		return true;
	}
}


function registerCandidate() {
	$("#frmLogin").attr('action', "LoginAction_registerCandidate.action");
	$("#frmLogin").submit();
}

function alphaNumeric(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 97 || unicode > 122) && (unicode < 65 || unicode > 90) && (unicode < 48 || unicode > 57) && (unicode != 8) && (unicode != 9))
		return false;
}
function alphanumericForPassword(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 97 || unicode > 122) && (unicode < 65 || unicode > 90)
			&& (unicode < 48 || unicode > 57) && unicode != 8 && unicode != 9
			&& unicode != 33 && unicode != 64 && unicode != 35 
			&& unicode != 38) {
		// alert("Password should be alpha numeric only."); //&& unicode != 36 --- $
		return false;
	}
}

function enableLoadingAnimation() {
	document.getElementById('loadingDialog').style.display = "block";
}
</script>

<style type="text/css">
.loginboxbg .btn-warning {
	width: 100% !important;
}

html {
	background: #f1f1f1 !important;
	height: 100%;
}

body, .container {
	background: none;
}

.headerTopBg, .headerMiddleBg {
	display: none;
}

.container .container {
	width: 1170px;
}

.center {
	display: block;
	position: fixed;
	left: 50%;
	top: 35%;
	z-index: 1000;
	height: 31px;
	width: 31px;
}
</style>
<div class="login-main">
	<div align="center">
		<div id="browserErrorMessage"
			style="display: none; color: red; font-weight: bold; font-size: 12pt;"></div>
	</div>
	<div class="fullscreen-container" id="myModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close"
						onclick="clearFields(); HideAll();">&#215;</button>
					<h2 class="modal-title">Forgot Password</h2>
				</div>
				<div class="modal-body">
					<s:form name="forgotpwd" id="forgotpwd" autocomplete="off">
						<div id="error-massage" style="display: none">
							<div class="error-massage-text"
								style="margin: 0; margin-left: -20px; padding: 0;">
								<ul style="margin: 0; margin-left: 20px; padding: 0;"
									id="error-ull">
								</ul>
							</div>
						</div>
						<s:hidden name="otpflagfp" id="otpflagfp" />
						<div id="forgotPasswordDiv">
							<div class="row">
								<div class="col-sm-10 col-sm-offset-1">
									<div class="form-group">
										<label class="control-label"> User ID <%-- <s:text name="reference"/> --%>
											&nbsp;<span class="manadetory-fields">*</span></label>
										<s:textfield name="userId" id="userId" size="20"
											maxlength="20" onpaste="return false;"
											autocomplete="new-password"
											onkeypress="return alphaNumeric(event);"
											cssClass="form-control" ondragstart="return false;"
											ondrop="return false;" />
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-10 col-sm-offset-1">
									<div class="form-group">
										<label class="control-label"> Choose any one from the
											following: <span class="manadetory-fields">*</span>
										</label></br>
										<s:radio list="#{'email':'Email ID','mobile':'Mobile Number'}"
											name="choicepass" id="choicepass" size="12" onpaste="return false;"
											onchange="disableTextForgotPassword();" autocomplete="off" style="height:11px; margin-right:5px;" />
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-10 col-sm-offset-1">
									<div class="form-group">
										<label class="control-label">Email ID <span
											class="manadetory-fields">*</span></label>
										<s:textfield name="eMail" id="eMail1pass" size="12"
											onpaste="return false;" maxlength="100"
											onkeypress="return alphaNumericwithSplCharEmail(event)"
											cssClass="form-control" autocomplete="off" />
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-10 col-sm-offset-1">
									<div class="form-group">
										<label class="control-label">Mobile Number <span
											class="manadetory-fields">*</span></label>
										<s:textfield name="mobileNo2pass" id="mobileNo2pass" size="12"
											onkeypress="return numbersonly(event);"
											onpaste="return false;" maxlength="10"
											cssClass="form-control" autocomplete="off" />
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-6 col-sm-offset-3">
									<input type="button" value="Submit"
										class="ripple1 btn btn-warning btn-block"
										onclick="return forgotPass()" id="btnForgotPassword" />
								</div>
							</div>
						</div>
						<div id="block101" style="display: block;">
							<div class="row">
								<div class="col-lg-12 text-center">
									<h4 id="lblForgotPasswordMessage" class="pageTitle"
										title="Password Changed Successfully"></h4>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-6 col-sm-offset-3">
									<input type="button" value="Close"
										class="ripple1 btn btn-warning btn-block"
										id="btnCloseForgotPasswordResult" onclick="HideAllPopups()" />
								</div>
							</div>
						</div>
						<s:token />
					</s:form>
				</div>
			</div>
		</div>
	</div>
	<!-- Forgot Password OTP Start -->
	<div class="fullscreen-container" id="myModal2">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close"
						onclick="clearTimeOut(); clearFields(); HideAllPopups();">&#215;</button>
					<h2 class="modal-title">Forgot Password</h2>
				</div>
				<div class="modal-body">
					<s:form name="fpotpForm" id='fpotpForm' autocomplete="off">
						<div id="error-massageFpOtp" style="display: none">
							<div class="error-massage-text"
								style="margin: 0; margin-left: 27px; padding-bottom: 10px;">
								<ul style="margin: 0; margin-left: 20px; padding: 0;"
									id="error-ullFpOtp">
								</ul>
							</div>
						</div>
						<div id="error-massageFpOtpResent" style="display: none">
							<div class="error-massage-text"
								style="margin: 0; margin-left: 27px; padding-bottom: 10px;">
								<ul style="margin: 0; margin-left: 20px; padding: 0;"
									id="error-ullFpOtpResent">
								</ul>
							</div>
						</div>
						<div id="error-massage2" class="error-massage"
							style="margin: 0; margin-left: 27px; padding-bottom: 10px;">
							<div class="error-massage-text">
								<ul style="margin: 0; margin-left: 20px; padding: 0;"
									id="error-ul2">
								</ul>
							</div>
						</div>
						<div id="fpInstructions">
							<div class="row">
								<div class="col-sm-10 col-sm-offset-1">
									<p class="orgNote">Following special characters are allowed
										!@#&</p>
								</div>
							</div>
						</div>
						</br>
						<div id="blockUserPass">
							<div class="row">
								<div class="col-sm-10 col-sm-offset-1">
									<div class="form-group">
										<label class="control-label"> Verification OTP <span
											class="manadetory-fields">*</span>
										</label>
										<s:textfield name="fpOtp" id="fpOtp" size="6" maxlength="6"
											onpaste="return false;"
											onkeypress="return numbersonly(event);"
											cssClass="form-control" ondragstart="return false;"
											ondrop="return false;" autocomplete="new-password" />
									</div>
								</div>
								<s:hidden name="username" id="usernameHidden"></s:hidden>
								<s:hidden name="mobile" id="mobileHidden"></s:hidden>
								<s:hidden name="email" id="emailHidden"></s:hidden>
								<s:hidden name="genOtp" id="genOtpHidden"></s:hidden>
							</div>
							<div class="row">
								<div class="col-sm-10 col-sm-offset-1">
									<div class="form-group">
										<label class="control-label">New Password <span
											class="manadetory-fields">*</span>
										</label>
										<s:password name="newPswd" id="newPswd"
											onpaste="return false;" size="12" maxlength="12"
											cssClass="form-control" autocomplete="new-password"
											onkeyup="CheckPasswordStrength(this.value)"
											ondragstart="return false;" ondrop="return false;"
											onkeypress="return alphanumericsplCharForPWD(event);" />
										<span id="password_strength"></span> <span
											id="password_suggestion"></span>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-10 col-sm-offset-1">
									<div class="form-group">
										<label class="control-label">Confirm New Password <span
											class="manadetory-fields">*</span>
										</label>
										<s:password name="confPswd" id="confPswd"
											onpaste="return false;" size="12"
											onkeypress="return alphanumericsplCharForPWD(event);"
											maxlength="12" cssClass="form-control"
											ondragstart="return false;" ondrop="return false;"
											autocomplete="new-password" />
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-4 col-sm-offset-2 mb10">
									<s:hidden name="fpPageReq" value="Y" />
									<input type="button" value="Submit"
										class="btn-warning btn-block" title="Submit" id="FPSubmit" />
									<!-- onclick="return validateOtpForm();"  -->
								</div>
								<div class="col-sm-4" id="sendOTPMobDiv">
									<input type="button" value="Resend OTP"
										class="btn btn-xs btn-block btn-warning" id="sendOTPMobile"
										onClick="sendFPOTPMob();" /> <span id="resendSpan"
										style="display: none">Please wait for Resend OTP.</span> <span
										id="mobregister" style="color: red;"></span> <span
										id="countdownTimerForMob" style="color: red;"></span>
								</div>
							</div>
						</div>
						<div id="block100FP" style="display: block;">
							<div class="row">
								<div class="col-lg-12 text-center">
									<h4 id="lblResetPasswordMessage" class="pageTitle"
										title="New Password set successfully."></h4>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-6 col-sm-offset-3">
									<input type="button" value="Close"
										class="ripple1 btn btn-warning btn-block"
										id="btnCloseResetPasswordResult" onclick="HideAllPopups()" />
								</div>
							</div>
						</div>
						<s:token />
					</s:form>
				</div>
			</div>
		</div>
	</div>
	<!-- Forgot Password OTP End -->
	<div id="myModalChangePassword" class="modal">
		<div class="modal-content">
			<div>
				<a href="javascript:void(0);" onclick="clearFields(); HideAll();"><img
					src="images/Close.png" align="right" border="0" /></a>
			</div>
			<div class="pad12">
				<div class="titleBox fl-left">
					<h1 class="pageTitle">&nbsp;Change Password</h1>
				</div>
				<div class="closebtnBox fl-rigt"></div>
				<div class="hr-underline clear"></div>
				<br />
				<s:form name="chngepwd" id="chngepwd" autocomplete="off">
					<div id="error-massage-pwd" style="display: none">
						<div class="error-massage-text"
							style="margin: 0; margin-left: -20px; padding: 0;">
							<ul style="margin: 0; margin-left: 20px; padding: 0;"
								id="error-ull">
							</ul>
						</div>
					</div>
					<br />
					<div class="fogot-cont">
						<table width="100%" border="0" cellspacing="3" cellpadding="3">
							<tr>
								<td><s:text name="forgot.newpassword" /> &nbsp;<span
									class="manadetory-fields">*</span></td>
								<td><s:password name="newPwd" id="newPwd"
										onpaste="return false;" size="12" onkeypress="" maxlength="12"
										cssClass="inputField" autocomplete="new-password" /></td>
							</tr>
							<tr>
								<td><s:text name="forgot.confirmpwd" /> &nbsp;<span
									class="manadetory-fields">*</span></td>
								<td><s:password name="rePwd" id="rePwd" size="12"
										onpaste="return false;" onkeypress="" maxlength="12"
										cssClass="inputField" autocomplete="new-password" /></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td align="left"><input type="button" value="Submit"
									class="submitBtn button-gradient" id="btnChangePassword" />
									&nbsp;&nbsp;</td>
							</tr>
						</table>
					</div>
				</s:form>
			</div>
		</div>
	</div>
	<div class="fullscreen-container" id="myModal1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close"
						onclick="clearFields(); HideAll();">&#215;</button>
					<h2 class="modal-title">Forgot User ID</h2>
				</div>
				<div class="modal-body">
					<s:form name="forgotpwd1" id="forgotpwd1" autocomplete="off">
						<div id="error-massageForgotUserId" style="display: none">
							<div class="error-massage-text"
								style="margin: 0; margin-left: -5px; padding: 0;">
								<ul style="margin: 0; margin-left: 20px; padding: 0;"
									id="error-ullForgotUserId">
								</ul>
							</div>
						</div>
						<div id="forgotUserIdDiv">
							<div class="row">
								<div class="col-sm-10 col-sm-offset-1">
									<div class="form-group">
										<label class="control-label"> Choose any one from the
											following: <span class="manadetory-fields">*</span>
										</label></br>
										<s:radio list="#{'email':'Email ID','mobile':'Mobile Number'}"
											name="choice" id="choice" size="12" onpaste="return false;"
											onchange="disableTextForgotUserId();" autocomplete="new-password" style="height: 11px; margin-right: 5px;"/>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-10 col-sm-offset-1">
									<div class="form-group">
										<label class="control-label">Email ID <span
											class="manadetory-fields">*</span></label>
										<s:textfield name="eMail" id="eMail1" size="12"
											onpaste="return false;" maxlength="100"
											onkeypress="return alphaNumericwithSplCharEmail(event)"
											cssClass="form-control" autocomplete="new-password" />
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-10 col-sm-offset-1">
									<div class="form-group">
										<label class="control-label">Mobile Number <span
											class="manadetory-fields">*</span></label>
										<s:textfield name="mobileNo2" id="mobileNo2" size="10"
											onkeypress="return numbersonly(event);"
											onpaste="return false;" maxlength="10"
											cssClass="form-control" autocomplete="new-password" />
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-6 col-sm-offset-3">
									<input type="button" value="Submit"
										class="ripple1 btn btn-warning btn-block"
										onclick="forgotUserId();" />
								</div>
							</div>
						</div>
						<div id="block10" style="display: block;">
							<div class="row">
								<div class="col-lg-12 text-center">
									<h4 id="lblForgotPasswordMessage1" class="pageTitle"
										title="User ID has been sent to your registered Email ID and Mobile Number."></h4>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-6 col-sm-offset-3">
									<input type="button" value="Close"
										class="ripple1 btn btn-warning btn-block"
										id="btnCloseForgotPasswordResult1"
										onclick="clearFields(); HideAllPopups();" />
								</div>
							</div>
						</div>
						<s:token />
					</s:form>
				</div>
			</div>
		</div>
	</div>

	<!-- Forgot End -->
	<!-- Forgot Password Result Start -->
	<div class="fullscreen-container" id="block100">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close"
						onclick="clearFields(); HideAll();">&#215;</button>
					<h2 class="modal-title">Alert</h2>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-lg-12 text-center">
							<h4 id="lblChangePasswordMessage" class="pageTitle"
								title="Password Changed Successfully"></h4>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6 col-sm-offset-3">
							<input type="button" value="Close"
								class="ripple1 btn btn-warning btn-block"
								id="btnCloseChangePasswordResult" onclick="HideAllPopups()" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Forgot Password Result End -->
	<div class="fade" id="block7"></div>
	<!-- Login Container Start -->
	<div class="container">
		<div class="row">
			<div class="col-md-12 col-xs-12 loginDetails">

				<%
					String prodServer = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.Is_this_Live_Server);
					if (prodServer != null && !prodServer.equals("")) {
						if (prodServer.equals("N")) {
				%>
				<div class="clear"></div>
				<div class="text-center mt20">
					<img src="images/demo_test.gif" alt="This is Demo/Test Instance" />
				</div>
				<%
					}
					}
				%>
			</div>
		</div>
		<div class="row mb20">
			<div class="col-md-6 col-xs-12 loginDetails">
				<img src="images/logo.png" class="logo img-responsive" />
				<div class="admission-open">
					<h1>
						<span>Recruitment of Prosthetic Craftsman 2025 Online Application Registration </span>
					</h1>

					<hr>

					<h3 style="color: #1a7acc">Online Application Registration</h3>

					<!--<h3>Admissions 2024-25 Open for - <span>Diploma in Canine Forensics </span>(Last Date: 15/02/2024)</h3> -->
				</div>
			</div>
			<div class="col-sm-6  col-xs-12">
				<!-- Login Box Start -->
				<s:if test='%{#userError}'>this is error</s:if>
				<s:form name="login" method="post" id='frmLogin' autocomplete="off" class="mb50">
					<div class="loginboxbg">
						<div id="login"></div>

						<div class="row">
							<div class="col-sm-3 col-sm-offset-9 text-right col-xs-12">
								<%
									String currentUrl = request.getRequestURL().toString();
										String[] urlParts = currentUrl.split("/WEB");
								%>
								<h3 class="closeBtn">
									<a href=<%=urlParts[0]%>>&#8617; Back</a>
								</h3>
							</div>
						</div>
						<div id="error-massage_user" style="display: none"
							class="error-massage">
							<div class="error-massage-text">
								<ul style="margin: 0; margin-left: 20px; padding: 0;"
									id="error-ul_user">
								</ul>
							</div>
						</div>
						<div class="row" id="invalid_msg">
							<div class="col-sm-12 col-xs-12">
								<s:actionmessage escape="false" cssClass="msgg" />
							</div>
						</div>
						<div class="loginForm">
							<div class="row">
								<div class="col-sm-12 col-xs-12">
									<div class="form-group">
										<label class="control-label">User ID</label>
										<s:textfield name="username" id="username" maxlength="14"
											size="14"
											cssClass=" form-control loginInput userNme validate[required,maxSize[50]]"
											errRequired="Please enter User ID."
											autocomplete="new-password"
											onkeypress="return alphaNumeric(event);" />
										<s:hidden name="encryptedusername" id="encryptedusername" />

									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12 col-xs-12">
									<div class="form-group">
										<label class="control-label">Password </label>
										<s:password name="password" id="password" size="12"
											maxlength="12"
											cssClass="form-control loginInput passwrd alidate[required,maxSize[50]]"
											errRequired="Please Enter Password."
											ondragstart="return false;" ondrop="return false;"
											autocomplete="new-password"
											onkeypress="return alphanumericForPassword(event);" />
										<s:hidden name="encryptedpassword" id="encryptedpassword" />
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12">
									<p class="text-right">
										Forgot <a href="javascript:void(0);" onClick="ShowDialog1();"
											class="WeightBold">User ID</a> | <a
											href="javascript:void(0);" onClick="ShowDialog();"
											class="WeightBold">Password</a>
									</p>
								</div>
							</div>
							<div class="row mt20">
								<div class="col-md-12 col-xs-12">
									<s:hidden name="loginPageReq" value="Y" />
									<s:submit method="authenticateUser" name="loginPageBtn"
										value="LOGIN" key="login.submit"
										cssClass="btn-warning btn-block" id="loginSubmit"
										onclick="return validateForm();" />
								</div>
							</div>
						</div>
					</div>
					<s:hidden name="registrationActive"/>
					<s:if test='%{registrationActive}'>
						<div class="loginboxbg">
							<div class="row">
								<div class="col-sm-12 col-xs-12">
									<p class="font16 WeightBold">New User Sign Up</p>
								</div>
							</div>
							<div class="row mt20">
								<div class="col-sm-12 col-xs-12">
									<a type="button" href="../MRBPCW/instructions.jsp"
										class="btn btn-warning btn-block text-uppercase">Register
										Now</a>
								</div>
							</div>
						</div>
					</s:if>
					<s:else>
						<div class="loginboxbg">
							<div class="row">
								<div class="col-sm-12 col-xs-12">
									<%
										Map<Integer, List<Long>> dateWindowMap = ConfigurationConstants.getInstance().getDateWindowMap();
												List<Long> dateList = dateWindowMap.get(7);
												Long startDate = dateList.get(0);
												Long endDate = dateList.get(1);
												long today = ZonedDateTime.now().toInstant().toEpochMilli();
												String endDateString = CommonUtil.formatDate(new Date(endDate), GenericConstants.DATE_FORMAT_DEFAULT1);
												String newStartDate = CommonUtil.formatDate(new Date(startDate), GenericConstants.DATE_FORMAT_DEFAULT1);
												String[] endDateTime1 = endDateString.split(" ");
												String[] startDateFinal = newStartDate.split(" ");
									%>
									<div>
										<p>
											<b style="color: black"> New User creation window is not
												open, New User Creation Window - <%=startDateFinal[0]%> at <%=startDateFinal[1]%>
												hrs to <%=endDateTime1[0]%> at <%=endDateTime1[1]%> hrs.
											</b>
										</p>
									</div>

								</div>
							</div>
						</div>
					</s:else>
					<s:token />
				</s:form>
				<!-- Login Box End -->
			</div>
			<div align="right" class="WeightBold mb20 mr10">
				<strong>Version 1.7</strong>
			</div>
		</div>
	</div>

	<div class="clear"></div>
	<div id="overlay1" class="web_dialog_overlay_declr"></div>
	<div class="fullscreen-container" id="dialog1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onclick="HideDialog1()">&times;</button>
					<!-- <h2 class="modal-title">Confirmation</h2> -->
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-lg-12 text-center">
							<h4 class="pageTitle" id="dynamicContent"></h4>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="row">
						<div class="col-sm-6 col-sm-offset-3">
							<input type="button" name="btnCancel" value="OK"
								onclick="HideDialog1()"
								class="ripple1 btn btn-default btn-block mt10" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</div>
<div class="loading-container" id="loadingDialog"></div>
