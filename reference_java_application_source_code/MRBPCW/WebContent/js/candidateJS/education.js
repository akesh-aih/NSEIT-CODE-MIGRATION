document.onreadystatechange = function() {
	var state = document.readyState;
	enableLoadingAnimation();
	if (state == 'complete') {
		disabledLoadingAnimation();
	}
}

$(document).ready(function() {

	datePicker();
	errorField();
	setDegreeNameOtherBox(0);
	setDegreeNameOtherBox(1);
	setDegreeNameOtherBox(2);
	hideShowFieldsDipMarks(2);
	hideShowFieldsUG(3);
	hideShowFieldsPG(4);
	hideShowFieldsPgDip(4);
	hideShowFieldsPSTM(5);
	hideShowFieldsPSTMsub(5);

	jQuery(document).bind("keyup keydown", function(e) {
		if (e.ctrlKey && e.keyCode == 80) {
			return false;
		}
	});

	jQuery('#tamilMedium').on('change', function(event) {
		if ($("#tamilMedium").val() == '7') {
			showPopUpForMobile("#tamilMedium", "You are not eligible to claim PSTM");
			$("#pstmPreference").val("7");
			$("#tamilMed").hide();
			$("#uGtamilMedium").hide();
			$("#ugTamilMedium").val("");
		}
	});

	jQuery('#ugTamilMedium').on('change', function(event) {
		if ($("#ugTamilMedium").val() == '7') {
			showPopUpForMobile("#ugTamilMedium", "You are not eligible to claim PSTM");
			$("#pstmPreference").val("7");
			$("#tamilMed").hide();
			$("#tamilMedium").val("");
			$("#uGtamilMedium").hide();
		}
	});

	var msgFordegree = "Please enter only alphabets and spaces, maximum 50 characters.";

	function regexforAlphabetSpace(value) {
		var degreeSub = /^[a-zA-Z ]*$/i.test(value);
		return degreeSub
	}

	function alphanumericWithSpace(e) {
		var unicode = e.charCode ? e.charCode : e.keyCode;
		if ((unicode < 97 || unicode > 122) && (unicode < 48 || unicode > 57) && (unicode < 65 || unicode > 90) && unicode != 32 && unicode != 8 && unicode != 9)
			return false;
	}

	// Messages
	var msgForAlphaSpace = "Please enter only alphabets and spaces, maximum 100 characters.";
	var msgForAlphaNumericSpace = "Please enter only alphabets, numbers and spaces, maximum 100 characters.";
	var msgForAlphaNumericDotSpace = "Please enter only alphabets, numbers, dot and spaces, maximum 50 characters.";
	var msgForAlphaNumericDotCommaSpace = "Please enter only alphabets, numbers, dot, comma and spaces, maximum 100 characters."

	function regexforAlphaSpace(value) {
		var insti = /^[a-zA-Z ]*$/i.test(value);
		return insti
	}

	function regexforAlphaNumericSpace(value) {
		var degreeSub = /^[a-zA-Z0-9 ]*$/i.test(value);
		return degreeSub
	}

	function regexforAlphaNumericSpaceDot(value) {
		var degreeSub = /^[a-zA-Z0-9. ]*$/i.test(value);
		return degreeSub
	}

	function regexForAlphaNumericSpaceDotComma(value) {
		return /^[a-zA-Z0-9 .,]*$/i.test(value);
	}

	// Name of Board
	jQuery('#universityOth0').on('change', function(event) {
		var result = regexForAlphaNumericSpaceDotComma(this.value);
		if (!result) {
			showPopUpForMobile("#universityOth0", msgForAlphaNumericDotCommaSpace); // SSC Other board
		}
	});
	
	jQuery('#universityOth1').on('change',function(event) {
		var result = regexForAlphaNumericSpaceDotComma(this.value);
		if (!result) {
			showPopUpForMobile("#universityOth1", msgForAlphaNumericDotCommaSpace); // HSC Other board
		}
	});
	
	jQuery('#universityOth2').on('change',function(event) {
		var result = regexForAlphaNumericSpaceDotComma(this.value);
		if (!result) {
			showPopUpForMobile("#universityOth2", msgForAlphaNumericDotCommaSpace); // UG Other board
		}
	});

	// Name of University
	jQuery('#institution2').on('change',function(event) {
		var result = regexForAlphaNumericSpaceDotComma(this.value);
		if (!result) {
			showPopUpForMobile("#institution2", msgForAlphaNumericDotCommaSpace);
		}
	});

	// Specialization
	jQuery('#specialization3').on('change',function(event) {
		var result = regexForAlphaNumericSpaceDotComma(this.value);
		if (!result) {
			showPopUpForMobile("#specialization3",msgForAlphaNumericDotCommaSpace);
		}
	});

	jQuery('#pgDipSpecialization3').on('change',function(event) {
		var result = regexForAlphaNumericSpaceDotComma(this.value);
		if (!result) {
			showPopUpForMobile("#pgDipSpecialization3", msgForAlphaNumericDotCommaSpace);
		}
	});

	// Council Number
	jQuery('#councilNo2').on('change',function(event) {
		var result = regexforAlphaNumericSpaceDot(this.value);
		if (!result) {
			showPopUpForMobile("#councilNo2", msgForAlphaNumericDotSpace);
		}
	});
	
});
// end of $(document).ready

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
function alphanumericWithSpaceAndSomeSpec(e) { // , ' - : . & "//A-Z a-z 0-9 space horizontal tab backspace
	var unicode = e.charCode ? e.charCode : e.keyCode;
	return ((unicode >= 65 && unicode <= 90) || (unicode >= 97 && unicode <= 122) || (unicode >= 48 && unicode <= 57) || unicode == 8 || unicode == 9
			|| unicode == 32 || unicode == 44 || unicode == 39 || unicode == 45 || unicode == 58 || unicode == 46 || unicode == 34 || unicode == 38); 
}

function alphanumericWithSpaceDotComma(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 97 || unicode > 122) && // a-z
	(unicode < 48 || unicode > 57) && // 0-9
	(unicode < 65 || unicode > 90) && // A-Z
	unicode != 32 && // Space
	unicode != 46 && // Dot (.)
	unicode != 44 && // Comma (,)
	unicode != 8 && // Backspace
	unicode != 9 // Tab
	) {
		return false;
	}
}

function isOndropContainsSpecialCharRestrict(str, id) {
	var re = /[~!@#$%^*()_|+\=?;<>\{\}\[\]\\\/]/gi;
	var eee = str.value;
	var xyz = eee.replace(/[~!@#$%^*()_|+\=?;<>\{\}\[\]\\\/]/gi, '');
	document.getElementById(id).value = xyz;
}

function setDegreeNameOtherBox(e) {
	var degreeVal = $("#university" + e + " option:selected").val();
	if (degreeVal == 7 || degreeVal == 11 || degreeVal == 16) {
		$("#universityOther" + e).show();
	} else {
		$("#universityOther" + e).hide();
		$("#universityOth" + e).val("");
	}
}

function checkForDegree() {
	var ugdegreeName = $("#degreeSubject3").val();
	var pgdegreeName = $("#degreeSubject4").val();
	if (ugdegreeName != "" && ugdegreeName != null && ugdegreeName != undefined && (ugdegreeName == 156 || ugdegreeName == 157)) {
		if (pgdegreeName != "" && pgdegreeName != null && pgdegreeName != undefined && (pgdegreeName != 159 && pgdegreeName != 160)) {
			showPopUpForMobile("#degreeSubject4", "You are not eligible to apply for this post.");
		}
	}
}

function frmSubmit() {
	$("#register").attr('action', 'CandidateAction_updateCandidateStage.action');
	$("#register").submit();
}

function lettersAndNumbersOnly(e) {
	var charCode = e.charCode ? e.charCode : e.keyCode;
	// alert(charCode);
	if ((charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123)
			|| (charCode > 47 && charCode < 58) || charCode == 8
			|| charCode == 32)
		return true;
	else
		return false;
}

function alphanumeric(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 97 || unicode > 122) && (unicode < 65 || unicode > 90)
			&& (unicode < 48 || unicode > 57) && unicode != 8 && unicode != 9
			&& unicode != 46)
		return false;
}

function alphanumericCollege(e) {
	var charCode = e.charCode ? e.charCode : e.keyCode;
	// alert(charCode);
	if ((charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123)
			|| (charCode > 47 && charCode < 58) || charCode == 8
			|| charCode == 32)
		return true;
	else
		return false;
}

function alphanumericWithDotAddress(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 97 || unicode > 122) && (unicode < 48 || unicode > 57)
			&& (unicode < 65 || unicode > 90) && unicode != 32 && unicode != 38
			&& unicode != 8 && unicode != 9 && unicode != 46 && unicode != 39
			&& unicode != 44 && unicode != 45 && unicode != 47 && unicode != 92
			&& unicode != 13 || unicode == 45 || unicode == 39)
		return false;
}

function alphabets(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 97 || unicode > 122) && (unicode < 65 || unicode > 90)
			&& unicode != 8 && unicode != 32 && unicode != 9)
		return false;
}
function alphabetsforHsc(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 97 || unicode > 122) && (unicode < 65 || unicode > 90)
			&& unicode != 8 && unicode != 32 && unicode != 9)
		return false;
}

function alphaWithDotComSpace(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 97 || unicode > 122) && (unicode < 65 || unicode > 90)
			&& unicode != 8 && unicode != 32 && unicode != 9 && unicode != 46
			&& unicode != 44)
		return false;
}

function alphaWithSpace(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 97 || unicode > 122) && (unicode < 65 || unicode > 90)
			&& unicode != 32 && unicode != 9)
		return false;
}

function alphaNumericWithSpaceAndDot(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 97 || unicode > 122) && (unicode < 65 || unicode > 90)
			&& (unicode < 48 || unicode > 57) && unicode != 32 && unicode != 9
			&& unicode != 46)
		return false;
}

function alphabetsMajorSubject(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if (unicode == 32) {
		return true;
	}
	if ((unicode < 97 || unicode > 122) && (unicode < 65 || unicode > 90)
			&& unicode != 8 && unicode != 9)
		return false;
}

function alphabetsSchool(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 97 || unicode > 122) && (unicode < 65 || unicode > 90)
			&& unicode != 8 && unicode != 32 && unicode != 9)
		return false;
}

function numericdot(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 48 || unicode > 57) && unicode != 8 && unicode != 9
			&& unicode != 46)
		return false;
}

function gradePointObt(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 48 || unicode > 57) && (unicode < 65 || unicode > 90)
			&& (unicode < 97 || unicode > 122) && unicode != 8 && unicode != 9
			&& unicode != 43 && unicode != 46)
		return false;
}

function gradePointMax(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 48 || unicode > 57) && (unicode < 65 || unicode > 90)
			&& (unicode < 97 || unicode > 122) && unicode != 8 && unicode != 9
			&& unicode != 43)
		return false;
}

function numericOnly(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 48 || unicode > 57) && unicode != 8 && unicode != 9)
		return false;
}

function onlyAlphabets(event) {
	var charCode = (evt.which) ? evt.which : window.event.keyCode;

	if (charCode <= 13) {
		return true;
	} else {
		var keyChar = String.fromCharCode(charCode);
		var re = /^[a-z0-9]+$/i;
		return re.test(keyChar);
	}
}

function alphaNumericOnly(e) {
	var k;
	document.all ? k = e.keyCode : k = e.which;

	return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || (k >= 48 && k <= 57));
}

function alphabetsWithSpacenChars(e) {// a-z A-Z 0-9 spabe backspace .-/
	// 44->>,
	// 96->>`
	// 39-->> '
	// 45-->> -
	// 46-->> .
	// 38->> &
	// 58--->> :
	var unicode = e.charCode ? e.charCode : e.keyCode;
	// alert("unicode"+unicode);
	if ((unicode < 97 || unicode > 122) && (unicode < 48 || unicode > 57)
			&& (unicode < 65 || unicode > 90) && unicode != 32 && unicode != 8
			&& unicode != 9 && unicode != 46 && unicode != 45 && unicode != 47
			&& unicode != 44 && unicode != 96 && unicode != 39 && unicode != 38
			&& unicode != 58)
		return false;
}

function rollNoValidate(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if (unicode != 32 && unicode != 45 && unicode != 47 && unicode != 92
			&& (unicode < 48 || unicode > 57)
			&& (unicode < 97 || unicode > 122)
			&& (unicode < 65 || unicode > 90))
		return false;
}

function dateFormatCheck(e) {
	var k = e.charCode ? e.charCode : e.keyCode;
	return ((k > 64 && k < 91) || (k > 96 && k < 123) || (k >= 48 && k <= 57)); // || k == 45
}

function calcPerc(obtId, outofId, percId) {
	var d = $(obtId).val();
	var d1 = $(outofId).val();
	if (d1 != "" && d != "") {
		if (d1 != 0) {
			var pec = (d / d1) * 100;
			pec = pec.toFixed(2);
			$(percId).val(pec);
		} else {
			$(percId).val("0");
		}
	} else {
		$(percId).val("");
	}
}

function calculateDuration(id) {
	var frmYr = $("#prdOfStudyFrm_" + id).val();
	var toYr = $("#prdOfStudyTo_" + id).val();
	var noOfYrs = "";
	if (frmYr != "" && toYr != "") {
		noOfYrs = parseInt(toYr) - parseInt(frmYr);
	}
	$("#duration" + id).val(noOfYrs);
}

function showPopup() {
	var maths = $("#studiedMath").val();
	if (maths == 7) {
		$("#overlay").show();
		$("#dialog").fadeIn(300);
	}
}

function HideDialog() {
	$("#overlay").hide();
	$("#dialog").hide();
	$("#studiedMath").val('');
}

function validateAcademicDetails() {
	document.register.action = "CandidateAction_saveAcademicDetails.action";
	enableLoadingAnimation();
	document.register.submit();
}

function clearFields(id) {

	$("#university" + id).val("");
	$("#universityOth" + id).val("");
	$("#universityOther" + id).hide();

	$("#prdOfStudyFrm_" + id).val("");
	$("#prdOfStudyTo_" + id).val("");
	$("#duration" + id).val("");

	$("#institution" + id).val("");
	$("#dateOfPassing" + id).val("");

	$("#dipMarksYesNo" + id).val("");
	$("#totalMarks" + id).val("");
	$("#obtainedMarks" + id).val("");

	$("#percentage" + id).val("");

	$("#medOfInstruction" + id).val("");
	$("#tamilLang" + id).val("");

	$("#ugYesNo" + id).val("");
	$("#specialization" + id).val("");
	$("#ugSpecialization" + id).hide();

	$("#pgYesNo" + id).val("");
	$("#specialization" + id).val("");
	$("#pgSpecialization" + id).hide();

	$("#pgDipYesNo" + id).val("");
	$("#pgDipSpecialization" + id).val("");
	$("#pgDipSpecializ" + id).hide();
	$("#pgDipDateofpassing" + id).val("");
	$("#pgDipDateofPass" + id).hide();

	if (id == "3" || id == "4")
		$("#dateofpassing" + id).hide();

	if (id == 5) {
		$("#pstmPreference").val("");
		$("#tamilMedium").val("");
		$("#ugTamilMedium").val("");
		$("#tamilMed").hide();
		$("#uGtamilMedium").hide();
	}

}

function hideShowFieldsUG(e) {
	var ugYesNo = $("#ugYesNo" + e + " option:selected").val();
	if (ugYesNo == 'Yes' || ugYesNo == '6') {
		$("#ugSpecialization" + e).show();
		$("#dateofpassing" + e).show();
	} else {
		$("#ugSpecialization" + e).hide();
		$("#dateofpassing" + e).hide();
		$("#dateOfPassing" + e).val('');
		$("#specialization" + e).val('');
	}
}

function hideShowFieldsPG(e) {
	var pgYesNo = $("#pgYesNo" + e + " option:selected").val();
	if (pgYesNo == 'Yes' || pgYesNo == '6') {
		$("#pgSpecialization" + e).show();
		$("#dateofpassing" + e).show();
	} else {
		$("#pgSpecialization" + e).hide();
		$("#dateofpassing" + e).hide();
		$("#dateOfPassing" + e).val('');
		$("#specialization" + e).val('');
	}
}

function hideShowFieldsDipMarks(e) {
	var dipMarksYesNo = $("#dipMarksYesNo" + e + " option:selected").val();
	if (dipMarksYesNo == 'Yes' || dipMarksYesNo == '6') {
		$("#totalMarksDiv" + e).show();
		$("#obtainedMarksDiv" + e).show();
		$("#percMarksDiv" + e).show();
	} else {
		$("#totalMarksDiv" + e).hide();
		$("#obtainedMarksDiv" + e).hide();
		$("#percMarksDiv" + e).hide();
		$("#totalMarks" + e).val('');
		$("#obtainedMarks" + e).val('');
		$("#percentage" + e).val('');
	}
}

function hideShowFieldsPgDip(e) {
	var pgDipYesNo = $("#pgDipYesNo" + e + " option:selected").val();
	if (pgDipYesNo == 'Yes' || pgDipYesNo == '6') {
		$("#pgDipSpecializ" + e).show();
		$("#pgDipDateofPass" + e).show();
	} else {
		$("#pgDipSpecializ" + e).hide();
		$("#pgDipDateofPass" + e).hide();
		$("#pgDipSpecialization3").val('');
		$("#pgDipDateofpassing3").val('');

	}
}

function hideShowFieldsPSTM(e) {
	var medOfInstruction = $("#medOfInstruction" + e + " option:selected")
			.val();
	if ($("#medOfInstruction0").val() == '161'
			&& $("#medOfInstruction1").val() == '161'
			&& $("#medOfInstruction2").val() == '161') {
		$("#pstmheader").show();
		$("#pstmPref").show();
		$("#pstmClearBtn").show();
		$("#pstmMandatory").val("true");
		$("#noTamilPstm").hide();

	} else {
		$("#pstmheader").hide();
		$("#pstmPref").hide();
		$("#pstmClearBtn").hide();
		$("#tamilMed").hide();
		$("#uGtamilMedium").hide();
		$("#pstmPreference").val('');
		$("#tamilMedium").val('');
		$("#ugTamilMedium").val('');
		$("#pstmMandatory").val("");

		// show disabled pstm
		$("#noTamilPstm").show();
	}
}
function hideShowFieldsPSTMsub(e) {
	var pstmPreference = $("#pstmPreference" + " option:selected").val();
	if (pstmPreference == '6') {
		$("#tamilMed").show();
		$("#uGtamilMedium").show();
	} else {
		$("#tamilMed").hide();
		$("#uGtamilMedium").hide();
		$("#tamilMedium").val('');
		$("#ugTamilMedium").val('');
	}
}
