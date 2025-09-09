/**
 * Registration page validation
 */

// Global Declaration
document.onreadystatechange = function() {
	var state = document.readyState;
	enableLoadingAnimation();
	if (state == 'complete') {
		disabledLoadingAnimation();
	}
}

var checked1 = false;

$(document).ready(function() {
	errorField();
	errorField1();
	showDisType();
	showHideExServiceMan();
	disableCategoryField();
	hideFieldsOnOC();
	showWidowYesNo();
	showWidowFields();
	showWidowDistOther();
	
	var showForm = $('#signupFormShow').val();
	if (showForm == 'yes') {
		$('#signUpFormDiv').show();
	} else {
		$("#candidateFirstName").val("");
		$("#initials").val("");
		//$("#nationality").val("");
		$("#nativityCertDist").val("");
		$("#newRegPassword").val("");
		$("#confirmRegPassword").val("");
		$('#signUpFormDiv').hide();
	}
	/**
	 * OnReady Method Calls
	 */
	//selectGender('O');
	DrawCaptcha();
	/**
	 * Section For Input Validation
	 */

	function regxforalphabetsonly(value) {
		var alphabetsonly = /^[A-Za-z ]*$/i.test(value);
		return alphabetsonly
	}
	
	function regxforalphabetspacedotapostrophe(value) {
		var spacedotapostrophe = /^[A-Za-z.' ]*$/i.test(value);
		return spacedotapostrophe
	}

	function regxforalphabetsnumericdotonly(value) {
		var alphabetsonly = /^[A-Za-z0-9 .]*$/i.test(value);
		return alphabetsonly
	}
	
	function regxforalphabetsnumericspace(value) {
		var alphabetsonly = /^[A-Za-z0-9 ]*$/i.test(value);
		return alphabetsonly
	}
	
	function regxforalphanumdotslashcommaonly(value) {
		var alphabetsonly = /^[A-Za-z0-9\s,&¢\-\\/\.]*$/i.test(value);
		return alphabetsonly
	}

	jQuery('#candidateName').on('change', function(event) {
		var result = regxforalphabetspacedotapostrophe(this.value);
		if (!result) {
			showPopUpForMobile("#candidateName", "Please enter only alphabets, spaces and special characters such as dot & apostrophe, maximum 100 characters.");
		}
	});
	
	jQuery('#ppoNumber').on('change', function(event) {
		var result = regxforalphanumdotslashcommaonly(this.value);
		if (!result) {
			showPopUpForMobile("#ppoNumber", "Please enter only alphabets, numbers, spaces and special characters such as comma, " +
					"ambercent, hypen, dot, backward & forward slash, maximum 50 characters.");
		}
	});
	
	jQuery('#subCaste').on('change', function(event) {
		var result = regxforalphabetsonly(this.value);
		if (!result) {
			showPopUpForMobile("#subCaste", "Please enter only alphabets and spaces, maximum 50 characters.");
		}
	});
	
	jQuery('#issueAuthCommCert').on('change', function(event) {
		var result = regxforalphanumdotslashcommaonly(this.value);
		if (!result) {
			showPopUpForMobile("#issueAuthCommCert", "Please enter only alphabets, numbers, spaces and special characters such as comma, " +
					"ambercent, hypen, dot, backward & forward slash, maximum 100 characters.");
		}
//		else
//			showPopUpForConsecutiveDots("#issueAuthCommCert");
	});
	
	jQuery('#commCertNo').on('change', function(event) {
		var result = regxforalphanumdotslashcommaonly(this.value);
		if (!result) {
			showPopUpForMobile("#commCertNo", "Please enter only alphabets, numbers, spaces and special characters such as comma, " +
					"ambercent, hypen, dot, backward & forward slash, maximum 20 characters.");
		}
//		else
//			showPopUpForConsecutiveDots("#commCertNo");
	});
	
	jQuery('#commCertPlace').on('change', function(event) {
		var result = regxforalphanumdotslashcommaonly(this.value);
		if (!result) {
			showPopUpForMobile("#commCertPlace", "Please enter only alphabets, numbers, spaces and special characters such as comma, " +
					"ambercent, hypen, dot, backward & forward slash, maximum 50 characters.");
		}
//		else
//			showPopUpForConsecutiveDots("#commCertPlace");
	});

	jQuery('#initials').on('change', function(event) {
		var result = regxforalphabetsonly(this.value);
		if (!result) {
			showPopUpForMobile("#initials", "Please enter only alphabets and spaces, maximum 6 characters.");
		}
	});

	jQuery('#nationality').on('change', function(event) {
		if ($("#nationality").val() != '' && $("#nationality").val() != null) {
		    let selectedText = $("#nationality option:selected").text();
		    if(selectedText=="Other"){
			showPopUpForMobile("#nationality", "Only Indian, Nepal, Bhutan and Person of Indian Origin Nationals are allowed");
			$("#nationality").val("");
		    }
		    
		    if(selectedText=="Nepal"||selectedText=="Bhutan"||selectedText=="Person of Indian Origin"){
		    	showPopUpForNotIndianOrOtherNationality("#nationality", "Applicant belonging to categories Person of Indian Origin or a subject of Nepal or Bhutan should also obtain a certificate of eligibility given by the Government of Tamil Nadu");
			    }
		}
	});
	
	jQuery('#nativityCertDist').on('change', function(event) {
		if ($("#nativityCertDist").val() == '149') {
			showPopUpForMobile("#nativityCertDist", "Other district candidates are not eligible to apply for this post.");
			$("#nativityCertDist").val("");
		}
	});
	
	
	jQuery('#desWidowCertNo').on('change', function(event) {
		var result = regxforalphanumdotslashcommaonly(this.value);
		if (!result) {
			showPopUpForMobile("#desWidowCertNo", "Please enter only alphabets / numbers / spaces / special characters of comma, amber cent, hyphen, back and forward slash & dot , maximum 50 characters.");
		}
	});
	
	jQuery('#widowIssueAuth').on('change', function(event) {
		var result = regxforalphanumdotslashcommaonly(this.value);
		if (!result) {
			showPopUpForMobile("#widowIssueAuth", "Please enter only alphabets / numbers / spaces / special characters of comma, amber cent, hyphen, back and forward slash & dot , maximum 50 characters.");
		}
	});
	
	jQuery('#widowOtherDistrict').on('change', function(event) {
		var result = regxforalphanumdotslashcommaonly(this.value);
		if (!result) {
			showPopUpForMobile("#widowOtherDistrict", "Please enter only alphabets / numbers / spaces / special characters of comma, amber cent, hyphen, back and forward slash & dot , maximum 50 characters.");
		}
	});
	
	jQuery('#widowSubDivision').on('change', function(event) {
		var result = regxforalphanumdotslashcommaonly(this.value);
		if (!result) {
			showPopUpForMobile("#widowSubDivision", "Please enter only alphabets / numbers / spaces / special characters of comma, amber cent, hyphen, back and forward slash & dot , maximum 50 characters.");
		}
	});

	var msgForName = "Please enter only alphabets and spaces, maximum 50 characters."
	function regxforalphadotcommaslashamp(value) { // character allowed are 'A-z' ',' '.' '&'
		var cityregex = /^[A-z ]*$/i.test(value);
		return cityregex
	}

	// for number only
	function regxfornumberonly(value) { // character allowed are '0-9'
		var cityregex = /^[0-9]*$/i.test(value);
		return cityregex
	}
	
	function regxforMobNum(value) { // character allowed are '0-9'
		var numRegex = /^(5|6|7|8|9)\d{9}/i.test(value);
		return numRegex
	}

	jQuery('#disablityPercent').on('change', function(event) {
		var result = regxfornumberonly(this.value);
		var perc = $('#disablityPercent').val();

		if (!result) {
			var msg = "Please enter only Numbers.";
			showPopUpForMobile("#disablityPercent", msg);
		} else {
			if (perc < 40 || perc > 70) {
				showPopUpForMobile("#disablityPercent", "Your Percentage of Disability is not eligible to apply under Differently Abled Category for this post.");
				$("#disablityPercent").val("");
			}
		}
	});

	jQuery('#mobileNo').on('change', function(event) {
		var message;
		var mob = $("#mobileNo").val()
		if(mob !=null && mob !="" && mob != undefined){
			var result = regxforMobNum(this.value);
			if(result == false)
				message = "Please enter valid Mobile Numbers, must be 10 digits." + "##";
		}
		
		if (message != "") {
			var ulID = "error-ul2";
			var divID = "error-massage2";
			createErrorList(message, ulID, divID);
			return false;
		} else {
			$("#error-massage2").hide();
			return true;
		}
		
		var enteredMobileNo = $("#newmobileNo").val();
		var updatedMobNo = $("#mobileNo").val();
		if (enteredMobileNo != null && enteredMobileNo != "" && enteredMobileNo != undefined && updatedMobNo != null && updatedMobNo != "" && updatedMobNo != undefined) {
			if (enteredMobileNo != updatedMobNo) {
				sendOTPMob();
			}
		}
	});
	
	jQuery('#emailAddress').on('change', function(event) {
		var message;
		var emailId = $("#emailAddress").val().toLowerCase();
		if (emailId != "") {
			var validEmail = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(emailId);
			if (validEmail == false)
				message = "Please enter valid Email ID." + "##";
		}
		
		if (message != "") {
			var ulID = "error-ul4";
			var divID = "error-massage5";
			createErrorList(message, ulID, divID);
			return false;
		} else {
			$("#error-massage5").hide();
			return true;
		}

	});
	
	jQuery('#mobotp').on('change', function(event) {
		var result = regxfornumberonly(this.value);
		if (!result) {
			var msg = "Please enter only numbers.";
			showPopUpForMobile("#mobotp", msg);
		}
	});

	jQuery('#emailotp').on('change', function(event) {
		var result = regxfornumberonly(this.value);
		if (!result) {
			var msg = "Please enter only numbers.";
			showPopUpForMobile("#emailotp", msg);
		}
	});

	jQuery('#newRegPassword').on('change', function(event) {
		var message = "";
		var newPassword = $("#newRegPassword").val();
		if (newPassword != "") {
			var validNewPawd = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#&])[a-zA-Z0-9!@#&]{8,12}$/.test(newPassword);
			if (validNewPawd == false)
				message = "Please enter valid Set Password." + "##";
		}
		
		if (message != "") {
			var ulID = "error-ulPass1";
			var divID = "error-messagePass1";
			createErrorList(message, ulID, divID);
			return false;
		} else {
			$("#error-messagePass1").hide();
			return true;
		}
	});

	jQuery('#confirmRegPassword').on('change', function(event) {
		var message = "";
		var confirmPassword = $("#confirmRegPassword").val();
		if (confirmPassword != "") {
			var validConfPwd = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#&])[a-zA-Z0-9!@#&]{8,12}$/.test(confirmPassword);
			if (validConfPwd == false)
				message = "Please enter valid Confirm Password." + "##";
		}
		
		if (message != "") {
			var ulID = "error-ulPass2";
			var divID = "error-messagePass2";
			createErrorList(message, ulID, divID);
			return false;
		} else {
			$("#error-messagePass2").hide();
			return true;
		}
	});

	// for captcha
	jQuery('#registerCaptcha').on('change', function(event) {
		var result = regxfornumberonly(this.value);
		if (!result) {
			var msg = "Please enter only numbers.";
			showPopUpForMobile("#registerCaptcha", msg);
		}
	});
	
	var verifyMobileOTPFlag = $("#verifyMobileOTPFlag").val();
	if (verifyMobileOTPFlag == 'true') {
		$('#otpFailed').hide();
		$("#countdownTimerForMob").hide();
		$("#verifymobileOTP").hide();
		$("#sendOTPMobDiv").hide();
		$("#otpTrMob").show();
		$("#mobotp").attr("readOnly", true);
		$('#mobileNo').attr("readOnly", true);
		$('#otpSuccess').show();
		$('#verfiedStatus').val(true);
		showSignUpForm();
	} else {
		$("#otpTrMob").hide();
		$('#mobotp').val('');
		$('#verfiedStatus').val(false);
		showSignUpForm();
	}

	var verifyEmailOTPFlag = $("#verifyemailOTPFlag").val();
	if (verifyEmailOTPFlag == 'true') {
		$('#emailotpFailed').hide();
		$("#countdownTimer").hide();
		$("#verifyemailOTP").hide();
		$("#sendOTPEmailDiv").hide();
		$("#otpTr").show();
		$("#emailotp").attr("readOnly", true);
		$("#emailAddress").attr("readOnly", true);
		$('#emailotpSuccess').show();
		$('#verfiedEmail').val(true);
		showSignUpForm();
	} else {
		$("#otpTr").hide();
		$('#emailotp').val('');
		$('#verfiedEmail').val(false);
		showSignUpForm();
	}

});

function showWidowYesNo()
{
	var mariatalStatusVal = $("#gender").val();
	if (mariatalStatusVal == 2) {
		$("#widowDiv").show();
	} else {
		$("#widowDiv").hide();
		$("#widowYesNo").val('');
	}
}

function showWidowDistOther()
{
	var otherDist = $("#widowDistrict").val();
	if (otherDist == 585) {
		$("#widowOtherDiv").show();
	} else {
		$("#widowOtherDiv").hide();
		$("#widowOtherDistrict").val('');
	}
}

function showWidowFields()
{
	var mariatalStatusVal = $("#widowYesNo").val();
	if (mariatalStatusVal == 6) {
		$("#dependentWidowDiv").show();
		$("#widowChkBoxDiv").show();
		
	} else {
		$("#dependentWidowDiv").hide();
		$("#widowChkBoxDiv").hide();
		$("#desWidowCertNo").val('');
		$("#widowIssueDate").val('');
		$("#widowIssueAuth").val('');
		$("#widowDistrict").val('');
		$("#widowOtherDistrict").val('');
		$("#widowSubDivision").val('');
		//$("#widowChkBox").prop('checked', false);
	}
}


function genderPrompt() {
	var genderval = $("#gender option:selected").text();
	var gendervalUpr = genderval.toUpperCase();
	$("#genderConfTD").html("You have selected gender as <strong>" + gendervalUpr + ".</strong>");
	document.getElementById('genderConfTD').innerHTML = "You have selected gender as <strong>" + gendervalUpr + ".</strong>";

	if (genderval != null && genderval != '') {
		disableKeyEvent();
		flag = true;
		$("#overlay3").show();
		$("#genderConfDialog").fadeIn(300);
	} else {
		flag = false;
		enableKeyEvent();
	}
}

var enableKeyEvent = function() {
	flag = false;
}

var disableKeyEvent = function() {
	window.addEventListener('keydown', function(event) {
		if (flag == true) {
			event.preventDefault();
			return false;
		} else {
			return true;
		}
	});
}

function hideGenderConfDialog(btnVal) {
	var genderval = $("#gender option:selected").text();
	if (btnVal == '1') { // OK
		genderPromptAudit('OK', genderval)
	} else if (btnVal == '2') { //Cancel
		genderPromptAudit('CANCEL', genderval)
		$("#gender").val('');
	}
	$("#overlay3").hide();
	$("#genderConfDialog").fadeOut(300);
	enableKeyEvent();
}

function genderPromptAudit(btnVal, genderval) {
	dataString = "genderval=" + genderval + "&btnVal=" + btnVal + "&actionAudit=" + "Gender Confirmation";
	
	$.ajax({
		url : "CandidateAction_auditTrailForGenderConfirmation.action",
		async : true,
		data : dataString,
		type : 'POST',
		error : function(ajaxrequest) {
		},
		success : function(responseText) {
		}
	});
}

function hidePercConfDialog(btnVal) {
	var genderval = $("#gender option:selected").text();
	if (btnVal == '1') { // OK
		genderPromptAudit('OK', genderval)
	} else if (btnVal == '2') { //Cancel
		genderPromptAudit('CANCEL', genderval)
		$("#gender").val('');
	}
	$("#overlay3").hide();
	$("#genderConfDialog").fadeOut(300);
	enableKeyEvent();
}

function showPopUpForMobile(id, innerValue) {
	$(id).val('');
	$("#overlay1").show();
	$("#dialog1").fadeIn(300);
	document.getElementById("dynamicContent").innerHTML = innerValue;
}

function showPopUpForNotIndianOrOtherNationality(id, innerValue) {
	$("#overlay1").show();
	$("#dialog1").fadeIn(300);
	document.getElementById("dynamicContent").innerHTML = innerValue;
}

function HideDialog1() {
	document.getElementById("dynamicContent").innerHTML = "";
	$("#overlay1").hide();
	$("#dialog1").fadeOut(300);
}

/*function showPopUpForConsecutiveDots(id) {
	var value = $(id).val();
	if (value.includes("..") || value.includes("--")) {
		$(id).val('');
		$("#overlay5").show();
		$("#dialog5").fadeIn(300);
	}
}

function HideDialog5() {
	$("#overlay5").hide();
	$("#dialog5").fadeOut(300);
}*/

function alphabetsOnly(e)
{
	    var k;
	    document.all ? k = e.keyCode : k = e.which;
	    return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || k == 32);
	}

function alphabetSpaceDotApostrophe(e) {
	var k;
    document.all ? k = e.keyCode : k = e.which;
    return ((k > 64 && k < 91) || (k > 96 && k < 123) ||  k == 8 || k === 32 || k === 46 || k === 39);   
}

function lettersOnly(e) {
	var charCode = e.charCode ? e.charCode : e.keyCode;
	if ((charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123) || charCode == 8 || charCode == 32)
		return true;
	else
		return false;
}

function validateEmail(e) {
	var charCode = e.charCode ? e.charCode : e.keyCode;
	if ((charCode > 63 && charCode < 91) || (charCode > 96 && charCode < 123) || (charCode > 47 && charCode < 58) || charCode == 46) {
		return true;
	} else {
		return false;
	}
}

function alphaNumericwithSplCharEmail(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 97 || unicode > 122) && (unicode < 65 || unicode > 90) && (unicode < 48 || unicode > 57) && unicode != 46 && unicode != 45 && unicode != 95 && unicode != 64)
		return false;
}

function numericOnly(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 48 || unicode > 57) && unicode != 8 && unicode != 9)
		return false;
}

function alpha_numeric_fbslash_com_dot_hyp_ampersand(e) {
    var unicode = e.charCode ? e.charCode : e.keyCode;
    if (!((unicode >= 48 && unicode <= 57) || // Numbers
          (unicode >= 65 && unicode <= 90) || // Uppercase letters
          (unicode >= 97 && unicode <= 122) || // Lowercase letters
          unicode === 44 || // Comma
          unicode === 45 || // Hyphen
          unicode === 46 || // Dot
          unicode === 47 || // Forward slash
          unicode === 92 || // Backslash
		  unicode === 38 || // Ampersand
          unicode === 8 || // Backspace
          unicode === 9 || // Tab
          unicode === 32)) { // Space
        return false;
    }
}


function alphaNumericSh(e) {
	var k;
	document.all ? k = e.keyCode : k = e.which;
	return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || (k >= 48 && k <= 57) || k == 32);
}

function alphaSymbolic(e){

    var k;
    document.all ? k = e.keyCode : k = e.which;
    return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || (k >= 48 && k <= 57) || k == 32 || k == 44 || k == 38 || k == 45 || k == 47 || k == 92 || k == 46);
}

function isOndropContainsSpecialCharRestrict(str, id) {
	var re = /[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/]/gi;
	var eee = str.value;
	var xyz = eee.replace(/[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/]/gi, '');
	document.getElementById(id).value = xyz;
}

function alphanumericForPWD(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 97 || unicode > 122) && (unicode < 65 || unicode > 90) && (unicode < 48 || unicode > 57) && unicode != 8 && unicode != 9) {
		return false;
	}
}

function dateFormatCheck(e) {
	var k = e.charCode ? e.charCode : e.keyCode;
	return ((k > 64 && k < 91) || (k > 96 && k < 123) || (k >= 48 && k <= 57)); // || k == 45
}

function hideFieldsOnOC()
{
	var community = $("#community").val();
	if(community != null && community != undefined && community != "" && community==7){
		$("#chechCommunity").show();
		
		$("#dependentCommCertDiv").hide();
		
		$("#subCaste").val("");
		$("#issueAuthCommCert").val("");
		$("#commCertNo").val("");
		$("#commCertPlace").val("");
		$("#commIssueDate").val("");
		}
}

function disableCategoryField(){
	var commCertYesNo = $( 'input[name=commCertYesNo]:checked').val();
	if(commCertYesNo != null && commCertYesNo != undefined && commCertYesNo != "" && commCertYesNo==6){
		
		$("#chechCommunity").show();
		$("#generalCommunity").hide();
		$("#dependentCommCertDiv").show();
		
	}else if(commCertYesNo != null && commCertYesNo != undefined && commCertYesNo != "" && commCertYesNo==7){
		
		$("#chechCommunity").hide();
		$("#community").val("");
		$("#generalCommunity").show();
		
		$("#dependentCommCertDiv").hide();
		
		$("#subCaste").val("");
		$("#issueAuthCommCert").val("");
		$("#commCertNo").val("");
		$("#commCertPlace").val("");
		$("#commIssueDate").val("");
	}
	else
	{
		$("#chechCommunity").hide();
		$("#generalCommunity").hide();
		
		$("#dependentCommCertDiv").hide();
	
		$("#subCaste").val("");
		$("#issueAuthCommCert").val("");
		$("#commCertNo").val("");
		$("#commCertPlace").val("");
		$("#commIssueDate").val("");
	}
}


function showDisType(){
	var dis=$("#disableYesNo").val();
	if(dis==6){
		$("#disableTypeDiv").show();
		$("#disablityPercentDiv").show();
		$("#diffAbledChkBoxDiv").show();
	}else{
		$("#diffAbledChkBoxDiv").hide();	
		$('#diffAbledChkBox').prop('checked', false);
		$("#disableTypeDiv").hide();
		$("#disableType").val('');
		$("#disablityPercentDiv").hide();
		$("#disablityPercent").val('');
	}
}

function showHideExServiceMan()
{
	var exServiceMen = $("#exServiceMen").val();
	
	if(exServiceMen =="6")
	{
		$("#dischargeDiv").show();
		$("#ppoNumberDiv").show();
	}else
	{
		$("#dischargeDate").val('');
		$("#ppoNumber").val('');
		$("#dischargeDiv").hide();
		$("#ppoNumberDiv").hide();
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
	regex.push("[!@#&]"); //Special Character.
	// special chars not accepted as per BRS.

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

function alphanumericForPass(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 97 || unicode > 122) && (unicode < 65 || unicode > 90)
			&& (unicode < 48 || unicode > 57) && unicode != 8 && unicode != 9
			&& unicode != 33 && unicode != 64 && unicode != 35
			&& unicode != 38) {
		// && unicode != 36 alert("Password should be alpha numeric only.");
		return false;
	}
}

/**
 * End of Desktop key typed
 */

/**
 * Section For Business Logic
 */
function sendOTPMob() {
	var mobFlag = "mobile";
	$("#otpFailed").hide();
	sendOtp(mobFlag);
//	$("#otpSent").show();
}

function sendOTPEmail() {
	var emailFlag = "email";
	$("#emailotpFailed").hide();
	sendOtp(emailFlag);
}

//var otpFlag;
var clearTime;
function sendOtp(otpFlag) {
	
	var emailId = $("#emailAddress").val().toLowerCase();
	/* $("#mobotp").val(""); */
	var mobileNo1 = $("#mobileNo").val();
	$("#error-ul3").html('');
	var message = "";
	/*
	 * var coursevalue = $("input[name='courses']:checked").val(); if(coursevalue==null || coursevalue==undefined || coursevalue==""){ message = message + "Please select Programme
	 * Applying for."+"##"; }
	 */
	if (otpFlag == 'email') {
		$('#sendOTP').attr("disabled", true);
		if ($.trim(emailId).length == 0) {
			message = message + "Please enter Email ID." + "##";
		}
		if (emailId != "") {
			var validEmail = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i
					.test(emailId);
			if (validEmail == false)
				message = message + "Please enter a valid Email ID." + "##";
		}
	}

	if (otpFlag == 'mobile') {
		$('#sendOTPMobile').attr("disabled", true);
		if ($.trim(mobileNo1).length == 0) {
			message = message + "Please enter Mobile Number." + "##";
		} else if ($.trim(mobileNo1).length < 10) {
			message = message + "Please enter valid 10 digit Mobile Number." + "##";
		}
	}

	if (message != "") {
		var ulID = "";
		var divID = "";
		if (otpFlag == 'email') {
			$('#sendOTP').attr("disabled", false);
			ulID = "error-ul4";
			divID = "error-massage5";
		} else {
			$('#sendOTPMobile').attr("disabled", false);
			$('#mobileNo').attr("readOnly", false);
			ulID = "error-ul2";
			divID = "error-massage2";
		}
		createErrorList(message, ulID, divID);
		return false;
	} else {
		$.ajax({
			url : "RegistrationAction_generateOTP.action",
			async : true,
			data : "emailId=" + emailId + "&otpFlag=" + otpFlag + "&mobileNo1=" + mobileNo1, // +"&courseVal="+coursevalue

			type : 'POST',
			error : function(ajaxrequest) {
				// window.reload();
			},
			success : function(responseText) {
				
				responseText = $.trim(responseText);
				if (responseText.length > 0) {				
					if (otpFlag == 'email') {
						if (responseText.indexOf('try again with a different') > -1) {
							$('#sendOTP').attr("disabled", false);
							$("#otpTr").hide();
							$("#verifyemailOTP").hide();
							$("#emailotp").val("");
							$("#verifyemailOTPFlag").val('');
						} else if (responseText.indexOf('sent') > -1 || responseText.indexOf('resent') > -1) {
							$("#emailAddress").attr("readOnly", true);
							$("#otpTr").show();
							$("#verifyemailOTP").show();
							$("#emailregister").hide();
							showOTPTimer();
							clearTime1 = setTimeout(function() {
								$('#sendOTP').removeAttr("disabled");
								$('#emailAddress').attr("readOnly", false);
							}, 120000);
							$("#verifyemailOTPFlag").val('true');
						} else if (responseText.indexOf('mail limit') > -1) {
							$("#otpTr").hide();
							$("#emailotp").val("");
						}else if (responseText.indexOf('enter valid Email ID') > -1) {
							$('#sendOTP').removeAttr("disabled");
						}
						var ulID = "error-ul4";
						var divID = "error-massage5";
						message = responseText;// .split('/')[0]
						createErrorList(message, ulID, divID);
					} else {
						if (responseText.indexOf('try again with a different') > -1) {
							$("#verifymobileOTP").hide();
							$("#otpTrMob").hide();
							$("#mobotp").val("");
							$("#verifyMobileOTPFlag").val('');
							$("#mobregister").show();
							$('#sendOTPMobile').attr("disabled", false);
							$('#mobileNo').attr("readOnly", false);
						} else if (responseText.indexOf('sent') > -1 || responseText.indexOf('resent') > -1) {

							$("#otpTrMob").show();
							$("#verifymobileOTP").show();
							$("#mobregister").hide();
							$('#mobileNo').attr("readonly", true);
							showOTPTimerForMob();

							clearTime = setTimeout(function() {
								$('#sendOTPMobile').removeAttr("disabled");
								$('#mobileNo').attr("readOnly", false);
							}, 120000);
							$("#verifyMobileOTPFlag").val('true');

						} else if (responseText.indexOf('SMS limit') > -1) {
							$("#otpTrMob").hide();
							$("#mobotp").val("");
						} else if (responseText.indexOf('enter Valid Mobile') > -1) {
							$('#sendOTPMobile').removeAttr("disabled");
						} else if (responseText.indexOf('select Post Applying for') > -1) {
							$('#sendOTPMobile').removeAttr("disabled");
						} else if (responseText.indexOf('Please enter valid Mobile Number') > -1) {
							$('#sendOTPMobile').removeAttr("disabled");
						}
						var ulID = "error-ul2";
						var divID = "error-massage2";
						var newmobileNo = $("#mobileNo").val();
						$("#newmobileNo").val($.trim(newmobileNo));
						message = responseText + "##"; // .split('/')[0]
						createErrorList(message, ulID, divID);
					}
				}
			}
		});

	}
}

// OTP Timer Logic :Start for Mob
function showOTPTimerForMob() {
	var expireTime = getExpireTime(1);
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

function showOTPTimer() {
	var expireTime = getExpireTimeForEmail(1);
	emailTimer = setInterval(function() {
		var now = new Date().getTime();
		var diff = expireTime - now;
		if (diff < 0) {
			clearInterval(emailTimer);

			hideCountdown();
			return;
		}
		var minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
		var seconds = Math.floor((diff % (1000 * 60)) / 1000);
		displayCountdown(minutes, seconds);
	}, 1000);

}
function getExpireTime(minutes) {
	var dt = new Date().getTime();
	return new Date(dt + minutes * 120000).getTime();
}

function getExpireTimeForEmail(minutes) {
	var dt = new Date().getTime();
	return new Date(dt + minutes * 120000).getTime();
}

function hideCountdown() {
	$("#countdownTimer").hide();
}

function displayCountdownForMob(minutes, seconds) {
	document.getElementById("countdownTimerForMob").style.display = 'block';
	document.getElementById("countdownTimerForMob").style.color = 'red';
	document.getElementById("countdownTimerForMob").innerHTML = minutes + " minutes " + seconds + " seconds ";
}

function displayCountdown(minutes, seconds) {
	document.getElementById("countdownTimer").style.display = 'block';
	document.getElementById("countdownTimer").style.color = 'red';
	document.getElementById("countdownTimer").innerHTML = minutes + " minutes " + seconds + " seconds ";
}

function hideCountdownForMob() {
	$("#countdownTimerForMob").hide();
}

function verifyemailOTP() {
	var enteredOtp = $("#emailotp").val();
	var email = $("#emailAddress").val();
	var generatedOtp = null;
	$.ajax({
		url : "RegistrationAction_verifyOTP.action",
		async : false,
		data : "&enteredOtp=" + enteredOtp + "&mobileNo=" + email,
		type : 'POST',
		error : function(ajaxrequest) {
			// window.reload();
		},
		success : function(responseText) {
			responseText = $.trim(responseText);
			if (responseText.length > 0) {
				generatedOtp = responseText;
			}
		}
	});

	if (generatedOtp == "ok") {
		$("#emailAddress").attr("readOnly", true);
		$('#emailotpSuccess').show();
		clearInterval(clearTime1);
		$('#emailotpFailed').hide();
		$("#countdownTimer").hide();
		$("#sendOTPEmailDiv").hide();
		$('#verfiedEmail').val(true);
		$("#verifyemailOTP").hide();
		$("#emailotp").attr("readOnly", true);
		$("#sendOTP").hide();
		document.getElementById("sendOTP").setAttribute("disabled", 'disabled');
		$("#error-massage5").hide();
		showSignUpForm();
	} else if (generatedOtp == "attempt exceeded") {
		$('#emailotpSuccess').hide();
		$('#noBlockedEmail').show();
		$('#emailotpFailed').hide();
		$('#verfiedEmail').val(false);
	} else {
		$('#emailotpSuccess').hide();
		$('#emailotpFailed').show();
		$('#verfiedEmail').val(false);
	}
}

function verifyMobileOTP() {
	var enteredOtp = $("#mobotp").val();
	var mobileNo = $("#mobileNo").val();
	var generatedOtp = null;
	$.ajax({
		url : "RegistrationAction_verifyOTP.action",
		async : false,
		data : "&enteredOtp=" + enteredOtp + "&mobileNo=" + mobileNo,
		type : 'POST',
		error : function(ajaxrequest) {
			// window.reload();
		},
		success : function(responseText) {
			responseText = $.trim(responseText);
			if (responseText.length > 0) {
				generatedOtp = responseText;
			}
		}
	});
	if (generatedOtp == "ok") {
		$("#mobileNo").attr("readOnly", true);
		$('#otpSuccess').show();
		clearInterval(clearTime);
		$('#otpFailed').hide();
		$("#countdownTimerForMob").hide();
		$("#sendOTPMobDiv").hide();
		$('#verfiedStatus').val(true);
		$("#verifymobileOTP").hide();
		$("#mobotp").attr("readOnly", true);
		$("#sendOTPMobile").hide();
		document.getElementById("sendOTPMobile").setAttribute("disabled", 'disabled');
		$('#error-massage2').hide();
		showSignUpForm();
	} else if (generatedOtp == "attempt exceeded") {
		$('#otpSuccess').hide();
		$('#noBlockedMobile').show();
		$('#otpFailed').hide();
		$('#verfiedStatus').val(false);
	} else {
		$('#otpSuccess').hide();
		$('#otpFailed').show();
		$('#verfiedStatus').val(false);
	}
}

function showSignUpForm() {

	var generatedEmailOtp = $("#verfiedStatus").val();
	var verfiedEmail = $("#verfiedEmail").val();
	if (generatedEmailOtp == "true" && verfiedEmail == "true") {
		$('#signUpFormDiv').show();
		$('#signupFormShow').val('yes');
	} else {
		$('#candidateFirstName').val('');
		$('#initials').val('');
		//$('#nationality').val('');
		$('#nativityCertDist').val('');
		$('#newRegPassword').val('');
		$('#confirmRegPassword').val('');
		$('#signUpFormDiv').hide();
		$('#signupFormShow').val('no');
	}
}

function fullname() {
	var lastname = $("#candidateLastName").val();
	var firstname = $("#candidateFirstName").val();
	var middlename = $("#candidateMiddleName").val();
	var candidateName = firstname.concat(' ', middlename, ' ', lastname).trim();
	$("#candidateName").val(candidateName);
}

function selectGender(flag) {
	var titleval = $("#initials").val();
	var genderval = $("select[name='gender']");
	genderval.find('option[value="1"]').show();
	genderval.find('option[value="2"]').show();

	if (flag == "C") {
		$("#gender").val("");
	}
	if (titleval == "8") {// condition for male i.e. Mr.
		// value 1 : male 2: Female
		genderval.find('option[value="2"]').hide();

	} else if (titleval == "9" || titleval == "10") {// condition for female i.e. Ms. & Mrs.
		genderval.find('option[value="1"]').hide();
	}
	// for common title i.e. Dr. & for no gender selected
	else {
		genderval.find('option[value="1"]').show();
		genderval.find('option[value="2"]').show();
	}

}

function DrawCaptcha() {
	// var chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghijklmnopqrstuvwxyz";
	var chars = "0123456789";
	var string_length = 5;
	var randomstring = '';
	document.getElementById("registerCaptcha").value = "";
	for (var i = 0; i < string_length; i++) {
		var rnum = Math.floor(Math.random() * chars.length);
		randomstring += chars.substring(rnum, rnum + 1) + ' ';
	}
	$("#txtCaptcha").html(randomstring);
}

function ValidCaptcha() {
	var str1 = $.trim($("#txtCaptcha").text());
	var str2 = $.trim($("#registerCaptcha").val());
	if (str1 == str2)
		return "true";
	return "false";

}

function validateBothPwd(message) {

	var newPwd = $("#newRegPassword").val();
	var confirmPwd = $("#confirmRegPassword").val();
	var message = "";

	if(newPwd == ""){
		message = message + "Please Set Password."+"##";
		$("#newRegPassword").addClass('red-border');
	} else if ($.trim(newPwd).length < 8) {
		message = message + "Password should be of minimum 8 characters."+"##";
		$("#newRegPassword").addClass('red-border');
    }
	if(confirmPwd == ""){
		message = message + "Please Confirm your Password."+"##";
		$("#confirmRegPassword").addClass('red-border');
	}
	else if($.trim(confirmPwd).length < 8  ) {
		message = message + "Confirm Password should be of minimum 8 characters."+"##";
		$("#confirmRegPassword").addClass('red-border');
	}

	var regex = new Array();
	regex.push("[A-Z]"); // Uppercase Alphabet.
	regex.push("[a-z]"); // Lowercase Alphabet.
	regex.push("[0-9]"); // Digit.
	regex.push("[!@#&]"); //Special Character.
	var passed = 0;

	// Validate for each Regular Expression.
	for (var i = 0; i < regex.length; i++) {
		if (new RegExp(regex[i]).test(newPwd)) {
			passed++;
		}
	}

	if (newPwd != "") {
		
		if (newPwd.length > 7 && passed <= 3) {
			message = message + "Password must contain at least one Upper case character (A-Z), one Lower case character (a-z), one Number (0-9) and one Special character (!@#&)."; // , and one special character.
			$("#newRegPassword").addClass('red-border');
		} else {
			if (newPwd != confirmPwd) {
				message = message + "Password and Confirm Password should be same." + "##";
				$("#newRegPassword").addClass('red-border');
				$("#confirmRegPassword").addClass('red-border');
			}
		}
	}

	var validPwd = "";
	if (newPwd != "") {
		validPwd = validatePassword(newPwd);
	}

	if (validPwd != true)
		message = message + validPwd;

	return message;
}

function validateFirstLoginForm() {
	$("input").removeClass('red-border');
	$("select").removeClass('red-border');

	var registerCaptcha = $.trim($("#registerCaptcha").val());
	$("#registeredCaptchaHid").val(registerCaptcha);

	var generatedCaptcha = $.trim($("#txtCaptcha").text());
	$("#txtCaptchaHid").val(generatedCaptcha);

	$("html, body").animate({
		scrollTop : 0
	}, "slow");
	$("#error-massage4").hide();
	$("#error-massage5").hide();
	$("#error-massage2").hide();
	$('#emailotpFailed').hide();
	$('#otpFailed').hide();
	var diffAbledChkBox = $( 'input[name=diffAbledChkBox]:checked' ).val();
	var newPassword = $("#newRegPassword").val();
	var confirmPassword = $("#confirmRegPassword").val();
	var emailId = $("#emailAddress").val().toLowerCase();
	var confEmailId = $("#confirmEmailAddress").val();
	var mobileNo = $("#mobileNo").val();
	var mobotp = $("#mobotp").val();
	var emailotp = $("#emailotp").val();
	var message = "";
	var verifyMobileOTPFlag = $("#verifyMobileOTPFlag").val();
	var verifiedStatus = $("#verfiedStatus").val();
	var verfiedEmail = $("#verfiedEmail").val();
	var verifyEmailOTPFlag = $("#verifyemailOTPFlag").val();
	
	var candidateFirstName = $("#candidateName").val();
	var nationality = $("#nationality option:selected").text();
	var exServiceMen = $("#exServiceMen").val();
	var dischargeDate = $("#dischargeDate").val();
	var ppoNumber = $("#ppoNumber").val();
	var commCertYesNo = $( 'input[name=commCertYesNo]:checked' ).val();
	var community = $("#community").val();
	var subCaste = $("#subCaste").val();
	var issueAuthCommCertVal = $("#issueAuthCommCert").val();
	var commCertNoVal = $("#commCertNo").val();
	var commCertPlace = $("#commCertPlace").val();
	var commIssueDate = $("#commIssueDate").val();
	var disableYesNo = $("#disableYesNo").val();
	var disableType = $("#disableType").val();
	var disablityPercent = $("#disablityPercent").val();
	var DOB = $("#dateOfBirth").val();
	var gender = $("#gender").val();
	if ($.trim(candidateFirstName).length == 0) {
		message = message + "Please enter Name." + "##";
		$("#candidateName").addClass('red-border');
	}

	if (nationality == null || nationality == undefined || nationality == "Select Nationality" && nationality != "Indian") {
		message = message + "Please select Nationality." + "##";
		$("#nationality").addClass('red-border');
	}

	if(gender =="" || gender==null || gender==undefined)
	{
		message = message + "Please select Gender"+"##";
		$("#gender").addClass('red-border');
	}
	
	if (exServiceMen == null || exServiceMen == undefined || exServiceMen == "Select Are you an Ex-Servicemen" || exServiceMen == "") {
		message = message + "Please select Are you an Ex-Servicemen." + "##";
		$("#exServiceMen").addClass('red-border');
	}else if(exServiceMen=="6")
	{
		if (dischargeDate == null || dischargeDate == undefined || dischargeDate == "") {
			message = message + "Please select Date of Discharge / Probable Discharge." + "##";
			$("#dischargeDate").addClass('red-border');
		}
	
		if ($.trim(ppoNumber).length == 0) {
			message = message + "Please enter PPO Number." + "##";
			$("#ppoNumber").addClass('red-border');
		}
	}
	if(commCertYesNo == null || commCertYesNo == undefined || commCertYesNo == "") { 
		message = message + "Please select Do you have community certificate issued by Tamil Nadu Government?" + "##";
		$("#commCertYesNo6").addClass('red-border');
		$("#commCertYesNo7").addClass('red-border');
		}  

	if (commCertYesNo=="6" && (community == null || community == undefined || community == "Select Community" || community == "")) {
		message = message + "Please select Community." + "##";
		$("#community").addClass('red-border');
	}
	else if(community!=7 && community!="")
 		 {
			if(subCaste==null || subCaste =="")
 		 	{	message = message + "Please enter Sub Caste."+"##";
		 		$("#subCaste").addClass('red-border');
			}
 		 	if(issueAuthCommCertVal==null || issueAuthCommCertVal =="")
	 		{
				message = message + "Please enter Issuing Authority of Community Certificate."+"##";
				$("#issueAuthCommCert").addClass('red-border');
			}
 		 	if(commCertNoVal==null || commCertNoVal =="")
 		 	{	message = message + "Please enter Community Certificate Number."+"##";
				$("#commCertNo").addClass('red-border');
			}
		
 		 	if(commCertPlace==null || commCertPlace =="")
			{
 		 		message = message + "Please enter Community Certificate Place of Issue."+"##";
				$("#commCertPlace").addClass('red-border');
			}
			if(commIssueDate==null || commIssueDate =="")
			{
 		 		message = message + "Please select Community Certificate Issuing Date."+"##";
				$("#commIssueDate").addClass('red-border');
			}
 		 }
	
	if(disableYesNo == null || disableYesNo =="")
	 {
		message = message + "Please select Are you differently abled?"+"##";
		$("#disableYesNo").addClass('red-border');
	 }
 	if(disableYesNo != null && disableYesNo == "6")
	{
 		if (!diffAbledChkBox) 
 			{
 			message = message + "Please check 'I agree to provide Differently Abled Person Certificate at the time of Certificate Verification' declaration checkbox."+"##";
			$("#diffAbledChkBox").addClass('red-border');
 			}
		if(disableType ==null || disableType == "")
		{
			message = message + "Please select Differently Abled Category."+"##";
			$("#disableType").addClass('red-border');
		}
		if(disablityPercent ==null || disablityPercent == "" || disablityPercent == undefined)
		{
			message = message + "Please enter Percentage of Disability."+"##";
			$("#disablityPercent").addClass('red-border');
		}else{
			if ($.trim(disablityPercent) < 40 || $.trim(disablityPercent) > 70) {
				message = message + "Percentage of Disability should be between 40 to 70." + "##";
				$("#disablityPercent").addClass('red-border');
			}
		}
	}
	
	
	var destiWidow = $("#widowYesNo").val();
	//const widowChkBox = document.getElementById("widowCheckbox");
	var desWidowCertNo = $("#desWidowCertNo").val();
	var widowIssueDate = $("#widowIssueDate").val();
	var widowIssueAuth = $("#widowIssueAuth").val();
	var widowDistrict = $("#widowDistrict").val();
	var widowOtherDistrict = $("#widowOtherDistrict").val();
	var widowSubDivision = $("#widowSubDivision").val();
	
	if(gender != null && gender =="2")
	{
		if(destiWidow =="" || destiWidow==null || destiWidow==undefined)
		{
			message = message + "Please select Are you a Destitute Widow?"+"##";
			$("#widowYesNo").addClass('red-border');
		}
		else if(destiWidow !=null && destiWidow == "6")
		{
			/*if (!widowChkBox.checked) 
 			{
	 			message = message + "Please check 'I agree to provide Destitute Widow Certificate at the time of Certificate Verification' declaration checkbox."+"##";
				$("#widowChkBox").addClass('red-border');
 			}*/
			if(desWidowCertNo == "" || desWidowCertNo == null || desWidowCertNo == undefined)
			{
				message = message + "Please enter Destitute Widow Certificate No"+"##";
				$("#desWidowCertNo").addClass('red-border');
			}
			if(widowIssueDate == "" || widowIssueDate == null || widowIssueDate == undefined)
			{
				message = message + "Please select Destitute Widow Certificate Issuing Date"+"##";
				$("#widowIssueDate").addClass('red-border');
			}
			if(widowIssueAuth == "" || widowIssueAuth == null || widowIssueAuth == undefined)
			{
				message = message + "Please enter Issuing Authority of Destitute Widow Certificate"+"##";
				$("#widowIssueAuth").addClass('red-border');
			}
			if(widowDistrict == "" || widowDistrict == null || widowDistrict == undefined)
			{
				message = message + "Please select Destitute Widow Certificate Issued District"+"##";
				$("#widowDistrict").addClass('red-border');
			}
			else if(widowDistrict != null && widowDistrict == "585")
			{
				if(widowOtherDistrict == "" || widowOtherDistrict == null || widowOtherDistrict == undefined)
				{
					message = message + "Please enter Destitute Widow Certificate Issued Other District"+"##";
					$("#widowOtherDistrict").addClass('red-border');
				}
			}
			
			if(widowSubDivision == "" || widowSubDivision == null || widowSubDivision == undefined)
			{
				message = message + "Please enter Destitute Widow Certificate Issued Sub Division"+"##";
				$("#widowSubDivision").addClass('red-border');
			}
		}
	}
	if(DOB=="" || DOB==null || DOB==undefined){
		message = message + "Please select Date of Birth (as per SSLC mark Sheet)."+"##";
		$("#dateOfBirth").addClass('red-border');
	}

    if ($.trim(mobileNo).length == 0) {
		message = message + "Please enter Mobile Number." + "##";
		$("#mobileNo").addClass('red-border');
	} else if ($.trim(mobileNo).length < 10) {
		message = message + "Please enter valid 10 digit Mobile Number." + "##";
		$("#mobileNo").addClass('red-border');
	} else if (/^(5|6|7|8|9)\d{9}$/.test(mobileNo) == false) {
		message = message + "Please enter valid Mobile Number." + "##";
		$("#mobileNo").addClass('red-border');
	} else {
		if (verifyMobileOTPFlag == "true") {
			if ($.trim(mobotp).length == 0) {
				message = message + "Please enter Mobile OTP." + "##";
				$("#mobotp").addClass('red-border');
			} else if ($.trim(mobotp).length < 6) {
				message = message + "Please enter 6 digit Mobile OTP." + "##";
				$("#mobotp").addClass('red-border');
			}
		} else {
			message = message + "Mobile Number verification is pending. " + "##";
		}
	}
	
	var mobHidden = document.getElementById("otpSuccess").style.display == "none"; // success msg is hidden
	var mobVisible = document.getElementById("otpFailed").style.display == "block"; // failed msg is shown
	
	if (($.trim(mobotp).length == 6) && ($.trim(mobileNo).length == 10)) {
		if (mobHidden || mobVisible) {
			message = message + "Please verify Mobile Number by clicking the Verify button." + "##";
		}
	}
	
	if ($.trim(emailId).length == 0) {
		message = message + "Please enter Email ID." + "##";
		$("#emailAddress").addClass('red-border');
	}else {
		var validEmail = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(emailId);
		if (validEmail == false) {
			message = message + "Please enter valid Email ID." + "##";
			$("#emailAddress").addClass('red-border');
		} else {
			if (verifyEmailOTPFlag == "true") {
				if ($.trim(emailotp).length == 0) {
					message = message + "Please enter Email OTP." + "##";
					$("#emailotp").addClass('red-border');
				} else if ($.trim(emailotp).length < 6) {
					message = message + "Please enter 6 digit Email OTP." + "##";
					$("#emailotp").addClass('red-border');
				}
			} else {
				message = message + "Email ID verification is pending. " + "##";
			}
		}
	}
	
	var emailHidden = document.getElementById("emailotpSuccess").style.display == "none"; // success msg is hidden
	var emailVisible = document.getElementById("emailotpFailed").style.display == "block"; // failed msg is shown
	
	if ($.trim(emailotp).length == 6) {
		if (emailHidden || emailVisible) {
			message = message + "Please verify Email ID by clicking the Verify button." + "##";
		}
	}
	
	if ($.trim(confEmailId).length == 0) 
	{
		//message = message + "Please enter Confirm Email id."+"##";
		//$("#confirmEmailAddress").addClass('red-border');
	}
    if(validEmail == true)
	 {
		if((confEmailId != emailId) && (!($.trim(confEmailId).length == 0)) && (!($.trim(emailId).length == 0)))
		{
			//message = message + " Email ID and Confirm Email ID does not match."+"##";
			//$("#emailAddress").addClass('red-border');
			//$("#confirmEmailAddress").addClass('red-border');
		}
	 } 
    
    message = message + validateBothPwd(message);

	if (registerCaptcha == "") {
		message = message + "Please enter Image text." + "##";
		$("#registerCaptcha").addClass('red-border');
	}
	var str1 = $.trim($("#txtCaptcha").text());
	str1 = str1.split(' ').join('');
	var str2 = $.trim($("#registerCaptcha").val());
	if (registerCaptcha != "") {
		if (str1 != str2) {
			message = message + "Image text does not match." + "##";
			$("#registerCaptcha").addClass('red-border');
		}
	}
	
	if (message != "") {
		var ulID = "error-ul3";
		var divID = "error-massage3";
		createErrorList(message, ulID, divID);
		$("#registerCaptcha").val("");
		$('#submitBtn').attr("disabled", false);
		DrawCaptcha();
		return false;
	} else {
		$("#frmFirstLoginChangePassword").attr("action", "RegistrationAction_registerCandidate.action");
		$("#frmFirstLoginChangePassword").submit();
		return true;
	}
}

function reVerifyOtp() {
	$('#otpSuccess').show();
	$('#otpFailed').hide();
	$("#countdownTimerForMob").hide();
	$("#sendOTPMobDiv").hide();
	$("#verifymobileOTP").hide();
	$("#mobotp").attr("readOnly", true);
	$('#verfiedStatus').val(true);
	$('#error-massage2').hide();
}
/**
 * End of Business Logic
 */
