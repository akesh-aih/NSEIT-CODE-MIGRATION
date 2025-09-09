document.onreadystatechange = function() {
	var state = document.readyState;
	enableLoadingAnimation();
	if (state == 'complete') {
		disabledLoadingAnimation();
	}
}

var timeOutVal;
var deletedItem1 = null;
var deletedItem2 = null;
var deletedItem3 = null;
var deletedItem4 = null;
var deletedItem5 = null;

$(document).ready(function() {
	
	errorField();
	showPopUpForGovt();
	/*showHideDisability();
	showHideCerebralpalsy();
	showHideDominantwriting();
	showHideSpouseDetails();
	showHideTwinsCheck();*/
	
	jQuery('#domicileUp').on('change', function(event) {		
		if ($("#domicileUp").val()=='7') {
			showPopUpForMobile("#domicileUp", "You are not eligible to apply for this post.");
			$("#domicileUp").val();
		}
	});

	jQuery('#nationality').on('change', function(event) {
		if ($("#nationality").val() == '5') {
			showPopUpForMobile("#nationality", "Other Nation Citizens are not allowed.");
			$("#nationality").val("");
		}
	});
	
	// Character allowed are A-Z a-z and space
	var msgForInitial = "Please enter only alphabets and spaces, maximum 6 characters.";
	var msgForOnlyAlpha = "Please enter only alphabets and spaces, maximum 75 characters";
	var msgForOnlyAlphaSpaceDotApostrophe = "Please enter only alphabets, dots, apostrophe and spaces, maximum 75 characters";
	var msgForAlphaSpaceOnly = "Please enter only alphabets and spaces, maximum 50 characters.";
	var msgForAlphaNumSpaceComDotUnderScHyphen = "Please enter only alphabets, numbers, spaces and special characters such as comma, dot, underscore, hyphen, maximum 50 characters.";
	var msgForAlphaNumericDotSpace = "Please enter only alphabets, numbers, dot and spaces, maximum 50 characters.";
	
	function regxforalphabetsonly(value){
		var alphabetsonly = /^[A-Za-z ]*$/i.test(value);
		return alphabetsonly
	}
	
	function regxforAlphaNumSpaceComDotUnderScHyphen(value) {
	    var alphanumspacecomdotunderschyphen = /^[A-Za-z0-9 .,_-]*$/.test(value);
	    return alphanumspacecomdotunderschyphen;
	}
	
	function regxforalphadotspaceapostrope(value){
		var alphabetsSpaceDotApostrophe = /^[a-zA-Z.'\s]*$/i.test(value);
		return alphabetsSpaceDotApostrophe
	}
	
	function regexforAlphaNumericSpaceDot(value) {
		var degreeSub = /^[a-zA-Z0-9. ]*$/i.test(value);
		return degreeSub
	}
	
	jQuery('#fathersName').on('change', function(event) {
		var result = regxforalphadotspaceapostrope(this.value);
		if (!result) {
			showPopUpForMobile("#fathersName", msgForOnlyAlphaSpaceDotApostrophe);
		}
	});
	
	jQuery('#mothersName').on('change', function(event) {
		var result = regxforalphadotspaceapostrope(this.value);
		if (!result) {
			showPopUpForMobile("#mothersName", msgForOnlyAlphaSpaceDotApostrophe);
		}
	});
	
	jQuery('#guardianName').on('change', function(event) {
		var result = regxforalphadotspaceapostrope(this.value);
		if (!result) {
			showPopUpForMobile("#guardianName", msgForOnlyAlphaSpaceDotApostrophe);
		}
	});
	
	jQuery('#spouseName').on('change', function(event) {
		var result = regxforalphadotspaceapostrope(this.value);
		if (!result) {
			showPopUpForMobile("#spouseName", msgForOnlyAlphaSpaceDotApostrophe);
		}
	});
	
	jQuery('#religionBeliefOthers').on('change', function(event) {
		var result = regxforalphabetsonly(this.value);
		if (!result) {
			showPopUpForMobile("#religionBeliefOthers", "Please enter only alphabets and spaces, maximum 20 characters");
		}
	});
	
	jQuery('#orgName').on('change', function(event) {
		var result = regexforAlphaNumericSpaceDot(this.value);
		if (!result) {
			showPopUpForMobile("#orgName", msgForAlphaNumericDotSpace);
		}
	});
	
	jQuery('#currentDesig').on('change', function(event) {
		var result = regexforAlphaNumericSpaceDot(this.value);
		if (!result) {
			showPopUpForMobile("#currentDesig", msgForAlphaNumericDotSpace);
		}
	});
	
	jQuery('#placeOfWork').on('change', function(event) {
		var result = regexforAlphaNumericSpaceDot(this.value);
		if (!result) {
			showPopUpForMobile("#placeOfWork", msgForAlphaNumericDotSpace);
		}
	});
	
	jQuery('#cityName').on('change', function(event) {
		var result = regxforAlphaNumSpaceComDotUnderScHyphen(this.value);
		if (!result) {
			showPopUpForMobile("#cityName", msgForAlphaNumSpaceComDotUnderScHyphen);
		}
	});
	
	jQuery('#alternateCityother').on('change', function(event) {
		var result = regxforAlphaNumSpaceComDotUnderScHyphen(this.value);
		if (!result) {
			showPopUpForMobile("#alternateCityother", msgForAlphaNumSpaceComDotUnderScHyphen);
		}
	});
	
	jQuery('#districtValother').on('change', function(event) {
		var result = regxforalphabetsonly(this.value);
		if (!result) {
			showPopUpForMobile("#districtValother", msgForAlphaSpaceOnly);
		}
	});
	
	jQuery('#altDistrictValOthers').on('change', function(event) {
		var result = regxforalphabetsonly(this.value);
		if (!result) {
			showPopUpForMobile("#altDistrictValOthers", msgForAlphaSpaceOnly);
		}
	})
	
	var msgForAddress = "Please enter only alphabets, numbers, spaces and special characters such as comma, " +
			"ambercent, hyphen, forward & backward slash, dot, maximum 250 characters.";
	
	var msgForAlphaNumSpaceComAmberHyphenFbSlashDot = "Please enter only alphabets, numbers, spaces and special characters such as comma, " +
	"ambercent, hyphen, forward & backward slash, dot, maximum 50 characters.";

	function regexForAlphaNumSpaceComAmberHyphenFbSlashDot(value) {
	    var res = /^[A-Za-z0-9 ,&./\\-]*$/.test(value);
	    return res;
	}
	
	jQuery('#photoIDProof1Val').on('change', function(event) {
		var result = regexforAlphaNumSpaceComAmberHyphenFbSlashDot(this.value);
		if (!result) {
			showPopUpForMobile("#photoIDProof1Val", msgForAlphaNumSpaceComAmberHyphenFbSlashDot);
		}
	});
	
	jQuery('#addressFiled1').on('change', function(event) {
		var result = regexForAlphaNumSpaceComAmberHyphenFbSlashDot(this.value);
		if (!result) {
			showPopUpForMobile("#addressFiled1", msgForAddress);
		} 
	});

	jQuery('#alternateAddressFiled1').on('change', function(event) {
		var result = regexForAlphaNumSpaceComAmberHyphenFbSlashDot(this.value);
		if (!result) {
			showPopUpForMobile("#alternateAddressFiled1", msgForAddress);
		} 
	});
	
	// for pincode only
	var msgForPincode = "Please enter only numbers, must be 6 digits.";

	function regxfornumberonly(value) { // character allowed are '0-9'
		var pincode = /^[0-9]*$/i.test(value);
		return pincode
	}

	jQuery('#pinCode').on('change', function(event) {
		var result = regxfornumberonly(this.value);
		if (!result) {
			showPopUpForMobile("#pinCode", msgForPincode);
		}
	});

	jQuery('#alternatePinCode').on('change', function(event) {
		var result = regxfornumberonly(this.value);
		if (!result) {
			showPopUpForMobile("#alternatePinCode", msgForPincode);
		}
	});
	
	/*..................................................................................................................*/

	/*var perferenceVal = $("select[name='testCenter1']");
	perferenceVal.find('option[value="20"]').remove();*/
	/*populateDistrictForState();
	populateAltDistrictForState();*/

	var di1 = '<s:property value="di1"/>';
	var di2 = '<s:property value="di2"/>';
	
	setCommunity();
	showOtherReligion();
	showSpouseName();
	enableotherNativity();
	showGovtFields();
	showParentOrGuardian();
	setCategoryValues();
	
	var categoryVal=$("#category").val();
	
	/*var $testCenter1 = $("select[name='testCenter1']");
	var $testCenter2 = $("select[name='testCenter2']");
	var $testCenter3 = $("select[name='testCenter3']");

	$testCenter1.change(function() {
		var selectedItem = $(this).val();
		if (deletedItem1 !== undefined && deletedItem1 !== null) {
			$testCenter2.find('option[value="' + deletedItem1 + '"]').prop("disabled", false);
			$testCenter3.find('option[value="' + deletedItem1 + '"]').prop("disabled", false);
		}
		if (selectedItem) {
			deletedItem1 = $(this).val();
			$testCenter2.find('option[value="' + selectedItem + '"]').prop("disabled", true);
			$testCenter3.find('option[value="' + selectedItem + '"]').prop("disabled", true);
		}
	});

	$testCenter2.change(function() {
		var selectedItem = $(this).val();
		if (deletedItem2 !== undefined && deletedItem2 !== null) {
			$testCenter1.find('option[value="' + deletedItem2 + '"]').prop("disabled", false);
			$testCenter3.find('option[value="' + deletedItem2 + '"]').prop("disabled", false);
		}
		if (selectedItem) {
			deletedItem2 = $(this).val();
			$testCenter1.find('option[value="' + selectedItem + '"]').prop("disabled", true);
			$testCenter3.find('option[value="' + selectedItem + '"]').prop("disabled", true);
		}
	});

	$testCenter3.change(function() {
		var selectedItem = $(this).val();
		if (deletedItem3 !== undefined && deletedItem3 !== null) {
			$testCenter1.find('option[value="' + deletedItem3 + '"]').prop("disabled", false);
			$testCenter2.find('option[value="' + deletedItem3 + '"]').prop("disabled", false);
		}
		if (selectedItem) {
			deletedItem3 = $(this).val();
			$testCenter1.find('option[value="' + selectedItem + '"]').prop("disabled", true);
			$testCenter2.find('option[value="' + selectedItem + '"]').prop("disabled", true);
		}
	});

	var selectedTest1 = $("#testCenter1").val();
	if (selectedTest1) {
		$testCenter2.find('option[value="' + selectedTest1 + '"]').prop("disabled", true);
		$testCenter3.find('option[value="' + selectedTest1 + '"]').prop("disabled", true);
		deletedItem1 = selectedTest1;
	}

	var selectedTest2 = $("#testCenter2").val();
	if (selectedTest2) {
		$testCenter1.find('option[value="' + selectedTest2 + '"]').prop("disabled", true);
		$testCenter3.find('option[value="' + selectedTest2 + '"]').prop("disabled", true);
		deletedItem2 = selectedTest2;
	}

	var selectedTest3 = $("#testCenter3").val();
	if (selectedTest3) {
		$testCenter1.find('option[value="' + selectedTest3 + '"]').prop("disabled", true);
		$testCenter2.find('option[value="' + selectedTest3 + '"]').prop("disabled", true);
		deletedItem3 = selectedTest3;
	}*/
	 
	var minyear = "<%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_DATE_OF_BIRTH_YEAR)%>";
	var maxyear = "<%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_DATE_OF_BIRTH_YEAR)%>";
	var minyearnew = "-";
	minyearnew = minyearnew + minyear + "Y";
	var maxyearnew = "-";
	var fortyFiveYrsBack = new Date().getFullYear() - maxyear;
	var lStartDateDOB = new Date("07/01" + "/" + fortyFiveYrsBack);
	var eithTeenYeersBack = new Date().getFullYear() - minyear;
	var lEndDateDOB = new Date("07/01" + "/" + eithTeenYeersBack);

	maxyearnew = maxyearnew + maxyear + "Y";
	
	if ($('#addressChkBox').attr('checked')) {
		copyPermenantAddress();
	}
    
}); //$(document).ready End

function alphanumericCollege(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	// alert(unicode);
	if ((unicode < 97 || unicode > 122) && (unicode < 65 || unicode > 90) && (unicode < 48 || unicode > 57) && unicode != 8 && unicode != 9 && unicode != 32 && unicode != 44
			&& unicode != 46 && unicode != 47 && unicode != 92 && unicode != 38)
		return false;
}

function onlyAlphabets(evt) {
	var charCode = (evt.which) ? evt.which : window.event.keyCode;
	if (charCode <= 13) {
		return true;
	} else {
		var keyChar = String.fromCharCode(charCode);
		var re = /^[a-zA-Z ]+$/
		return re.test(keyChar);
	}
}

function convertMonthStringToNumber(value) {
	var month = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
	return month.indexOf(value);
}

var flag = false;

// function for certificate number
function alphanumericWithDot(e) {
	var k = e.charCode ? e.charCode : e.keyCode;
	// document.all ? k = e.keyCode : k = e.which;
	return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || (k >= 48 && k <= 57) || k == 45 || k == 47 || k == 46);
}

// function for Address city
function alphaNumSpaceComDotUnderScHyphen(e) {
	var k = e.charCode ? e.charCode : e.keyCode;
	// document.all ? k = e.keyCode : k = e.which;
	return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || (k >= 48 && k <= 57) || k == 32 || k == 44 || k == 46 || k == 45 || k == 95);
}

function setCommunity() {
	var yesnoval = $("#categorycertificate").val();
	if (yesnoval == 6) {
		$('#categoryValDesc').attr('readonly', false);
		$('#categoryValDesc').attr("style", "pointer-events: ;");
	} else {
		$("#categoryValDesc").val(7);
		$("#categoryValDesc").attr('readonly', true);
		$('#categoryValDesc').attr("style", "pointer-events: none;");
	}
}

function showDomicile() {
	var yesnoval = $("#domicileUp").val();
	if (yesnoval == 6) {
		$("#domicile").show();
		$("#domicile1").show();
		$('#categoryValDesc').attr('readonly', false);
		$('#categoryValDesc').attr("style", "pointer-events: ;");
	} else {
		$("#domicile").hide();
		$("#domicile1").hide();
		$("#domicileCertificateUp").val('');
		$("#domicileCertificate").val('');
		$("#domicileSerial").val('');
		$("#categoryValDesc").val(1);
		$("#appNoDomCert").val('');
		// $('#categoryValDesc').attr('disabled',true);
		$("#categoryValDesc").attr('readonly', 'true');
		$('#categoryValDesc').attr("style", "pointer-events: none;");
		$("#catefileds").hide();
		$("#catevalfileds").hide();
		$("#catappno").hide();
		$('.clear1').hide();
		// $('.category_cast label').addClass('mb20');
		$("#categoryCertNo").val('');
		$("#categoryCertIssDt").val('');
		$("#categoryCertValDt").val('');
		clearCategoryFields();
	}
}

function showHomeGuard() {
	var yesnoval = $("#homeGuard").val();
	if (yesnoval == 6) {
		$("#homeGuard1").show();
		$("#homeGuard2").show();
	} else {
		$("#homeGuard1").hide();
		$("#homeGuard2").hide();
		$("#homeGuardDt").val('');
		$("#homeGuardSerial").val('');
	}
}
 
function showFreedomfighter() {
	var yesnoval = $("#freedomFighter").val();
	if (yesnoval == 6) {
		$("#Freedom1").show();
		$("#Freedom2").show();
		$("#Freedom3").show();
	} else {
		$("#Freedom1").hide();
		$("#Freedom2").hide();
		$("#Freedom3").hide();
		$("#freedomFighterDt").val('');
		$("#freedomFighterSerial").val('');
		$("#freedomFighterAuthority").val('');
	}
}

function showGovernmentEmp() {
	var yesnoval = $("#governmentEmp").val();
	if (yesnoval == 6) {
		$("#governmentEmp1").show();
		$("#governmentEmp2").show();
		$("#governmentEmp3").show();
	} else {
		$("#governmentEmp1").hide();
		$("#governmentEmp2").hide();
		$("#governmentEmp3").hide();
		$("#governmentEmpDt").val('');
		/*
		 * $("#empYear").val(''); $("#empMonth").val(''); $("#empDays").val('');
		 */
		$("#nocDt").val('');
		$("#nocSerial").val('');
		$("#nocAuthority").val('');
	}
} 
// eligibility end

function populateState() {
	var countryId = $("#countryId").val();
	var countryName = $("#countryId option:selected").text();
	dataString = "countryVal=" + countryId
	if (countryName == "INDIA" || countryId == "") {
		$("#stateDropdown").show();
		$("#otherStateVal").hide();
		$("#districtId").show();
		$("#otherDistrict").hide();
		$.ajax({
			url : "CandidateAction_getStateList.action",
			async : true,
			data : dataString,
			type : 'POST',
			beforeSend : function() {
				$('#stateList').empty();
				$("#stateList").append($('<option></option>').val("").html("--Select State--"));
			},
			error : function(ajaxrequest) {
				window.reload();
			},
			success : function(responseText) {
				responseText = $.trim(responseText);
				if (responseText.length > 0) {
					element = responseText.split(",");
					message = responseText.substring(2, responseText.length);
					if (element[0] == "9") {
						// alert(message);
						// return false;
					} else {
						$.each(element, function(val) {
							var nameAndIDArr = element[val].split("#");
							$("#stateList").append($('<option></option>').val(nameAndIDArr[1]).html(nameAndIDArr[0]));
						});
					}
				}
			},
			complete : function() {
				populateUT();
			}
		});
	} else {
		$("#stateDropdown").hide();
		$("#otherStateVal").show();
		$("#districtId").hide();
		$("#otherDistrict").show();
	}
}

function populateUT() {
	var countryId = $("#countryId").val();
	var countryName = $("#countryId option:selected").text();
	dataString = "countryVal=" + countryId
	if (countryName == "INDIA" || countryId == "") {
		$("#stateDropdown").show();
		$("#otherStateVal").hide();
		$("#districtId").show();
		$("#otherDistrict").hide();
		$.ajax({
			url : "CandidateAction_getUTList.action",
			async : true,
			data : dataString,
			type : 'POST',
			beforeSend : function() {
				$('#utList').empty();
				$("#utList").append($('<option></option>').val("").html("--Select Union Territory--"));
			},
			error : function(ajaxrequest) {
				window.reload();
			},
			success : function(responseText) {
				responseText = $.trim(responseText);
				if (responseText.length > 0) {
					element = responseText.split(",");
					message = responseText.substring(2, responseText.length);
					if (element[0] == "9") {
						// alert(message);
						// return false;
					} else {
						$.each(element, function(val) {
							var nameAndIDArr = element[val].split("#");
							$("#utList").append($('<option></option>').val(nameAndIDArr[1]).html(nameAndIDArr[0]));
						});
					}
				}
			}
		});
	} else {
		$("#stateDropdown").hide();
		$("#otherStateVal").show();
		$("#districtId").hide();
		$("#otherDistrict").show();
	}
}

function populateAltUT() {

	var altcountryId = $("#altCountryId").val();
	var altcountryName = $("#altCountryId option:selected").text();
	dataString = "countryVal=" + altcountryId
	if (altcountryName == "INDIA" || altcountryId == "") {
		$("#stateDropdown").show();
		$("#otherStateVal").hide();
		$("#districtId").show();
		$("#otherDistrict").hide();

		$.ajax({
			url : "CandidateAction_getUTList.action",
			async : true,
			data : dataString,
			type : 'POST',
			beforeSend : function() {
				$('#altUtList').empty();
				$("#altUtList").append($('<option></option>').val("").html("--Select Union Territory--"));
			},
			error : function(ajaxrequest) {
				window.reload();
			},
			success : function(responseText1) {
				responseText = $.trim(responseText1);
				if (responseText1.length > 0) {
					element = responseText1.split(",");
					message = responseText1.substring(2, responseText1.length);
					if (element[0] == "9") {
						// alert(message);
						// return false;
					} else {
						$.each(element, function(val) {
							var nameAndIDArr = element[val].split("#");
							$("#altUtList").append($('<option></option>').val(nameAndIDArr[1]).html(nameAndIDArr[0]));
						});
					}
				}
			}
		});
	} else {
		$("#stateDropdown").hide();
		$("#otherStateVal").show();
		$("#districtId").hide();
		$("#otherDistrict").show();
	}
}

var currUTStateId = "";
var currAltUTStateId = "";

function populateDistrictForState() {
	$('#utList').val("");
	currUTStateId = "#stateList";
	populateDistrict();
	// $('#pinCode').val("");
}

function populateAltDistrictForState() {
	$('#utList').val("");
	currUTStateId = "#altStateList";
	populateAltDistrict();
	// $('#alternatePinCode').val("");
}

function populateAltDistrict() {
	var state = $("" + currUTStateId).val();
	if (state == "") {
		$("#altDistrictValOthers").val("");
		$("#otherAlternateStateField").val("");
	}
	if(state== 1)
	{
		$("#districtSelect1").show();
		$("#districtTextarea1").hide();
		$("#policeTextarea1").show();
		$("#altDistrictValOthers").val("");
		$("#otherAlternateStateField").val("");
		
	dataString = "stateVal=" + state
	$.ajax({
		url : "CandidateAction_getAltDistrictList.action",
		async : true,
		data : dataString,
		type : 'POST',
		beforeSend : function() {
			$('#altDistrictList').empty();
			$("#altDistrictList").append($('<option></option>').val("").html("Select District"));
		},
		error : function(ajaxrequest) {
			window.reload();
		},
		success : function(responseText) {
			responseText = $.trim(responseText);
			if (responseText.length > 0) {
				element = responseText.split(",");
				message = responseText.substring(2, responseText.length);
				if (element[0] == "9") {
					// alert(message);
					// return false;
				} else {
					$.each(element, function(val) {
						var nameAndIDArr = element[val].split("#");

						$("#altDistrictList").append($('<option></option>').val(nameAndIDArr[1]).html(nameAndIDArr[0]));
					});
				}
			}
			//var altDistrictValBean = '<s:property value="altDistrictVal"/>';
			// alert(altDistrictValBean);
			//$("#altDistrictList").val(altDistrictValBean);
		}
	});
	}else
	{
		//$("#altDistrictValOthers").val("");
		//$("#alternateCityother").val("");
		$("#districtSelect1").hide();
		$("#districtTextarea1").show();
		//$("#otherStateTextarea1").show();
		$("#policeSelect1").hide();
		$("#policeTextarea1").show();
		$("#altDistrictList").val("");
		$("#alternateCity").val("");
		   
	}
}

function populateDistrict() {
	var state = $("" + currUTStateId).val();
	if (state == "") {
		$("#districtValother").val("");
	}
	if(state== 1)
	{
		$("#districtSelect").show();
		$("#districtTextarea").hide();
		$("#districtValother").val("");
		
		dataString = "stateVal=" + state
		$.ajax({
			url : "CandidateAction_getDistrictList.action",
			async : true,
			data : dataString,
			type : 'POST',
			beforeSend : function() {
				$('#districtList').empty();
				$("#districtList").append($('<option></option>').val("").html("Select District"));
			},
			error : function(ajaxrequest) {
				window.reload();
			},
			success : function(responseText) {
				responseText = $.trim(responseText);
				if (responseText.length > 0) {
					element = responseText.split(",");
					message = responseText.substring(2, responseText.length);
					if (element[0] == "9") {
						// alert(message);
						// return false;
					} else {
						$.each(element, function(val) {
							var nameAndIDArr = element[val].split("#");
							$("#districtList").append($('<option></option>').val(nameAndIDArr[1]).html(nameAndIDArr[0]));
						});
					}
				}
				//var districtValBean = '<s:property value="districtVal"/>';
				// alert(districtValBean);
				//$("#districtList").val(districtValBean);
			}
		});
	}
	else
		{
			$("#districtSelect").hide();
			$("#districtTextarea").show();
			$("#districtList").val("");
			
		}
}

function resetAlternateAddress() {
	$("#alternateAddressFiled1").val("");
	/*$("#alternateAddressFiled2").val("");
	$("#addressFiled3").val("");
	$("#addressFiled4").val("");
	$("#altTelephoneNo1").val("");
	$("#altTelephoneNo2").val("");*/
	$('#altStateList').val("");
	/*$('#altDistrictList').empty();
	$("#altDistrictList").append($('<option></option>').val("").html("Select District / City"));*/
	$('#alternateCityother').val("");
	$('#altDistrictVal').val("");
	$('#altDistrictValOthers').val("");
	$('#altDistrictList').val("");
	$("#alternatePinCode").val("");
}

function copyPermenantAddress() {
	if ($('#addressChkBox').prop('checked')) {
		$(".communicationAddress").each(function() {
			$(this).slideUp();
		});
	} else {
		$(".communicationAddress").each(function() {
			if ($(this).hasClass('otherField') == false) {
				$(this).slideDown();
			}
		});
	}
}

function hideShowCatFields() {
	var categoryval = $("#categoryValDesc").val();
	if (categoryval != null && categoryval != "" && categoryval != undefined && categoryval != "1") {
		$("#catefileds").show();
		$('.clear1').show();
		// $('.category_cast label').removeClass('mb20');
		$("#catevalfileds").show();
		$("#catappno").show();
	} else {
		$("#catefileds").hide();
		$('.clear1').hide();
		// $('.category_cast label').addClass('mb20');
		$("#catevalfileds").hide();
		$("#catappno").hide();
	}
}

function clearCategoryFields() {
	$("#categoryCertNo").val('');
	$("#categoryCertIssDt").val('');
	$("#catSerial").val('');
	$("#appNoForCat").val('');
}

function numberswithdot(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode
	if (unicode == 39) {
		return true;
	}

	if (unicode != 8) {
		if ((unicode < 48 || unicode > 57) && unicode != 9 && unicode != 46) // if not a number
			return false // disable key press
	}
}

function alphanumeric(e) {
	var k = e.charCode ? e.charCode : e.keyCode;
	// document.all ? k = e.keyCode : k = e.which;
	return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || (k >= 48 && k <= 57) || k == 46);
}

function alphanumericSlash(e) {
	var k;
	document.all ? k = e.keyCode : k = e.which;
	return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || (k >= 48 && k <= 57) || k == 47 || k == 92 || k == 45 || k == 46 || k != 32);
}

function alphanumericwithSomechar(e) {
	var k;
	document.all ? k = e.keyCode : k = e.which;
	return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || (k >= 48 && k <= 57) || k == 32 || k == 40 || k == 41 || k == 46 || k != 127);
}

function alphaNumericwithSplChar(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 97 || unicode > 122) && (unicode < 65 || unicode > 90) && (unicode < 48 || unicode > 57) && unicode != 8 && unicode != 9 && unicode != 32 && unicode != 46
			&& unicode != 40 && unicode != 32 && unicode != 41 && unicode != 47 && unicode != 92 && unicode != 95 && unicode != 45)
		return false;
}

function alphabetsWithSpacenChars(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 97 || unicode > 122) && (unicode < 48 || unicode > 57) && (unicode < 65 || unicode > 90) && unicode != 32 && unicode != 38 && unicode != 8 && unicode != 9
			&& unicode != 46 && unicode != 39 && unicode != 44 && unicode != 45 && unicode != 47 && unicode != 92 && unicode != 13 || unicode == 45 || unicode == 39)
		return false;
}

function alphabetsWithSpacenCharsForCert(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 97 || unicode > 122) && (unicode < 48 || unicode > 57) && (unicode < 65 || unicode > 90) && unicode != 32 && unicode != 8 && unicode != 9 && unicode != 46
			&& unicode != 45 && unicode != 47 && unicode != 13)
		return false;
}

function alphaNumericwithSplCharNew(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 97 || unicode > 122) && (unicode < 65 || unicode > 90) && (unicode < 48 || unicode > 57) && unicode != 32 && unicode != 38 && unicode != 92 && unicode != 47 && unicode != 44 && unicode != 46)
		return false;
}

function alphaNumSpaceComAmberHyphenFbSlashDot(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 97 || unicode > 122) && (unicode < 65 || unicode > 90) && (unicode < 48 || unicode > 57) && unicode != 32 && unicode != 38 && unicode != 92 && unicode != 47
			&& unicode != 44 && unicode != 46 && unicode != 45)
		return false;
}

function noEnter(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if (unicode == 13)
		return false;
}

function lettersOnly(e) {
	var charCode = e.charCode ? e.charCode : e.keyCode;
	if ((charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123) || charCode == 8 || charCode == 32)
		return true;
	else
		return false;
}

function alphaNumericwithSplCharEmail(e) {
	var unicode=e.charCode? e.charCode : e.keyCode;
	if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && (unicode<48 || unicode>57)&& unicode != 46  && unicode != 95 && unicode != 64)
		return false;
}

function alphanumericWithSpace(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 97 || unicode > 122) && (unicode < 48 || unicode > 57) && (unicode < 65 || unicode > 90) && unicode != 32 && unicode != 8 && unicode != 9)
		return false;
}

function alphanumericWithSpaceAndDot(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 97 || unicode > 122) && (unicode < 48 || unicode > 57) && (unicode < 65 || unicode > 90) && unicode != 32 && unicode != 8 && unicode != 9 && unicode != 46)
		return false;
}

function alphabetsWithSpaceForName(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 97 || unicode > 122) && (unicode < 65 || unicode > 90) && unicode != 32 && unicode != 8 && unicode != 9)
		return false;
}

function alphabetsWithSpaceDotApostrophe(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 97 || unicode > 122) && (unicode < 65 || unicode > 90) && unicode != 32 && unicode != 39  && unicode != 46 && unicode != 8 && unicode != 9)
		return false;
}

function alphabetsWithSpaceAndDotForName(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 97 || unicode > 122) && (unicode < 65 || unicode > 90) && unicode != 32 && unicode != 8 && unicode != 9 && unicode != 46)
		return false;
}

function alphabetsWithSpaceForCity(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if ((unicode < 97 || unicode > 122) && (unicode < 65 || unicode > 90) && unicode != 32 && unicode != 8 && unicode != 9)
		return false;
}

function maxLengthValidation(textBox, e, maxLength) {
	var unicode = e.charCode ? e.charCode : e.keyCode
	if (unicode == 13) {
		return false;
	} else {
		if (textBox.value.length > maxLength - 1) {
			alert("More than 1000 Characters are not allowed in the Project description");
			textBox.value = textBox.value.substr(0, maxLength);
		}
	}
}

function checkSpecialKeys(e) {
	if (e.keyCode != 8 && e.keyCode != 46 && e.keyCode != 33 && e.keyCode != 34 && e.keyCode != 35 && e.keyCode != 37 && e.keyCode != 38 && e.keyCode != 39 && e.keyCode != 40
			&& e.keyCode != 36) {
		return false;
	} else {
		return true;
	}
}

function maxLengthLimit(field, maxChars, event) {
	if (event.keyCode == 13) {
		return false;
	}
	if (!checkSpecialKeys(event)) {
		if (field.value.length >= maxChars) {
			return false;
		} else {
			return true;
		}
	}
}  

function maxLengthValidationOnPaste(field, maxChars) {
	if (field.value.length > maxChars) {
		return false;
	} else {
		return true;
	}
} 

function dateFormatCheck(e) {
    var k = e.charCode ? e.charCode : e.keyCode;
    return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 45 || (k >= 48 && k <= 57));
}

function disableCategoryField() {
	var communityCertQuest = $('#commCertYesNo').val();
	if (communityCertQuest == 'No') {
		$("#chechCommunity").hide();
		$("#category").val("");
		$("#generalCommunity").show();
		// ShowDialogCommunity(true);
	} else if (communityCertQuest == 'Yes') {
		$("#chechCommunity").show();
		$("#generalCommunity").hide();
	} else {
		$("#chechCommunity").hide();
		$("#generalCommunity").hide();
	}
	// datePickerConditions();
	// ageMatrix();
}

// @disable pincode dragdrop
function disableDragDrop(text, id) {
	var dropdownVal = text.value;
	var allowedVal = dropdownVal.replace(/[^0-9]/g, '');
	document.getElementById(id).value = allowedVal;
}
	
function alphaNumericSh(e) {
	var k;
	document.all ? k = e.keyCode : k = e.which;
	// alert(k);
	return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 32);
}

function clearWidowField() {
	$("#isWidow").val("");
	$("#isWidowDiv").val("");
	/*
	 * $("#divPreference1").val(""); $("#divPreference2").val("");
	 */
}

function clearExServiceMenFields() {
	$("#esmDateOfEnlistment").val("");
	$("#esmDateOfDischarge").val("");
	$("#defPersonDisabled").val("");
	$("#durationOfYears").val("");
	$("#durationOfMonths").val("");
	$("#durationOfDays").val("");
}

function clearDateOfDischarge() {
	$("#esmDateOfDischarge").val("");
}

function hideShowExServFields() {
	var isExServiceMen = $("#isExServicemen").val();
	if (isExServiceMen == "6") {
		$("#esmDateOfEnlistmentDiv").show();
		$("#esmDateOfDischargeDiv").show();
		$("#esmDurationOfServiceDiv").show();
		$("#defPersonDisabledDiv").hide();
	} else {
		$("#esmDateOfEnlistmentDiv").hide();
		$("#esmDateOfDischargeDiv").hide();
		$("#esmDurationOfServiceDiv").hide();
		$("#defPersonDisabledDiv").show();
	}
}

function resetDateAgeAsOn() {
	$("#dateOfBirth").val("");
	$("#ageInYears").val("");
	$("#ageInMonths").val("");
	$("#ageInDays").val("");
}

/*
 * function resetDisabilityFields() { $("#pwdCategory").val(""); $("#pwdSubCategory").val(""); $("#pwdSubCategory1").val(""); $("#disabilityType").val("");
 * $("#pwdPercentage").val(""); $("#disablityCertNo").val(""); $("#disabilityCertIssDt").val(""); $("#scribe").val(""); $("#scribeFields").hide(); $("#scribeName").val("");
 * $("#scribeDt").val(""); $("#scribeQualification").val(""); $("#skillTest").val(""); }
*/
	
// Age Matrix implementation starts
function ageMatrix() {
	// alert("ageMatrix");
	var categoryVal = $("#categoryValDesc").val();
	var exServiceMen = $("#isExServicemen").val();
	var dateOfEnlistment = $("#esmDateOfEnlistment").val();
	var dateOfDischarge = $("#esmDateOfDischarge").val();
	var isGovernmentEmp = $("#governmentEmp").val();
	var dataString = "categoryValDesc=" + categoryVal + "&isExServicemen=" + exServiceMen + "&enlistmentDate=" + dateOfEnlistment + "&dischargeDate=" + dateOfDischarge
			+ "&governmentEmp=" + isGovernmentEmp;

	if ((categoryVal != null && categoryVal != "" && categoryVal != undefined && categoryVal != "Select Category")
			&& (exServiceMen != null && exServiceMen != "" && exServiceMen != undefined && exServiceMen != "Select")
			&& (isGovernmentEmp != null && isGovernmentEmp != "" && isGovernmentEmp != undefined && isGovernmentEmp != "Select")) {
		
		$.ajax({
			type : 'POST',
			url : "CandidateAction_getAgeMatrixDetails.action",
			async : false,
			data : dataString,
			error : function(ajaxrequest) {
				console.log('Error refreshing. Server ageMatrix Response: ' + ajaxrequest.responseText);
			},
			success : function(responseText) {
				responseText = $.trim(responseText);
				if (responseText != "null" && responseText.length > 0) {
					console.log("ageMatrix responseText minDate:" + responseText);
					var minDate = responseText;
					setMinDateIntoDatePicker(minDate)
				}
			}
		});
	}
}
	

function setMinDateIntoDatePicker(newDate) {
	var dates = {
		convert : function(d) {
			// Converts the date in d to a date-object. The input can be:
			// a date object: returned without modification
			// an array : Interpreted as [year,month,day]. NOTE: month is 0-11.
			// a number : Interpreted as number of milliseconds
			// since 1 Jan 1970 (a timestamp)
			// a string : Any format supported by the javascript engine, like
			// "YYYY/MM/DD", "MM/DD/YYYY", "Jan 31 2009" etc.
			// an object : Interpreted as an object with year, month and date
			// attributes. **NOTE** month is 0-11.
			return (d.constructor === Date ? d : d.constructor === Array ? new Date(d[0], d[1], d[2]) : d.constructor === Number ? new Date(d)
					: d.constructor === String ? new Date(d) : typeof d === "object" ? new Date(d.year, d.month, d.date) : NaN);
		},
		compare : function(a, b) {
			// Compare two dates (could be of any type supported by the convert
			// function above) and returns:
			// -1 : if a < b
			// 0 : if a = b
			// 1 : if a > b
			// NaN : if a or b is an illegal date
			// NOTE: The code inside isFinite does an assignment (=).
			return (isFinite(a = this.convert(a).valueOf()) && isFinite(b = this.convert(b).valueOf()) ? (a > b) - (a < b) : NaN);
		},
		inRange : function(d, start, end) {
			// Checks if date in d is between dates in start and end.
			// Returns a boolean or NaN:
			// true : if d is between start and end (inclusive)
			// false : if d is before start or after end
			// NaN : if one or more of the dates is illegal.
			// NOTE: The code inside isFinite does an assignment (=).
			return (isFinite(d = this.convert(d).valueOf()) && isFinite(start = this.convert(start).valueOf()) && isFinite(end = this.convert(end).valueOf()) ? start <= d
					&& d <= end : NaN);
		}
	}
	
	var dob = $("#dateOfBirth").val();
	var oldMinDate = $("#dateOfBirth").datepicker("option", "minDate");
	$("#dateOfBirth").datepicker("option", "minDate", new Date(newDate));
	var newMinDate = $("#dateOfBirth").datepicker("option", "minDate");
	console.log("ageMatrix oldMinDate:" + oldMinDate + " newMinDate:" + newMinDate);

	if (dob == null || dob == "" || dob == undefined) {

	} else {
		var diff = dates.compare(newMinDate, oldMinDate);
		if (diff > 0) {
			console.log("ageMatrix reset dob diff:" + diff);
			$("#dateOfBirth").datepicker("option", "minDate", newMinDate);
			/*
			 * $("#dateOfBirth").val(""); $("#ageInYear").val(""); $("#ageInMonths").val(""); $("#ageInDays").val("");
			 */
		} else {
			$("#dateOfBirth").datepicker("option", "minDate", newMinDate);
		}
	}
}	

function calculateAge() {
	var dateString1 = '<s:property value="ageAsOn"/>';
	var momentObj1 = moment(dateString1, 'DD-MM-YYYY');
	var ageAsOn = momentObj1.format('YYYY-MM-DD');
	var startDate1 = ageAsOn.split("-").map(Number);
	if (startDate1[1] != "" && startDate1[1] != null) {
		var startDate = [ startDate1[0], startDate1[1] - 1, startDate1[2] ];
	}

	var dateString2 = $("#dateOfBirth").val();
	var momentObj2 = moment(dateString2, 'DD-MM-YYYY');
	var dateOfBirth = momentObj2.format('YYYY-MM-DD');
	var endDate = dateOfBirth.split("-").map(Number);
	if (endDate[1] != "" && endDate[1] != null) {
		var endDate2 = [ endDate[0], endDate[1] - 1, endDate[2] ];
	}
	var ageAsOn = moment(startDate);
	var dateOfBirth = moment(endDate2);
	var diff = moment.preciseDiff(ageAsOn, dateOfBirth, true);

	if (dateString1 != "" && dateString2 != "") {
		$("#ageInYears").val(diff.years);
		$("#ageInMonths").val(diff.months);
		$("#ageInDays").val(diff.days);
	}
}	  
// Age matrix implementation ends

function calculateDuration() {
	var dateOfDischarge = $("#esmDateOfDischarge").val();
	var momentObj1 = moment(dateOfDischarge, 'DD-MM-YYYY');
	var dodMoment = momentObj1.format('YYYY-MM-DD');
	var splitDate = dodMoment.split("-").map(Number);
	if (splitDate[1] != "" && splitDate[1] != null) {
		var startDate = [ splitDate[0], splitDate[1], splitDate[2] ];
	}

	var dateString2 = $("#esmDateOfEnlistment").val();
	var momentObj2 = moment(dateString2, 'DD-MM-YYYY');
	var dateOfBirth = momentObj2.format('YYYY-MM-DD');
	var endDate = dateOfBirth.split("-").map(Number);
	if (endDate[1] != "" && endDate[1] != null) {
		var endDate2 = [ endDate[0], endDate[1], endDate[2] ];
	}

	var ageAsOn = moment(startDate);
	var dateOfBirth = moment(endDate2);
	var diff = moment.preciseDiff(ageAsOn, dateOfBirth, true);
	if (dateOfDischarge != "" && dateString2 != "") {
		$("#durationOfYears").val(diff.years);
		$("#durationOfMonths").val(diff.months);
		// below changes for one extra day for same date but diff years
		$("#durationOfDays").val(diff.days);
		/*
		 * if($("#durationOfDays").val()==-1) { $("#durationOfDays").val("0"); }
		 */
	}
	// return ageString;
}

function validatePersonalDetails() {
	document.applicationForm.action = "CandidateAction_saveCandidateDetails.action";
	enableLoadingAnimation();
	document.applicationForm.submit();
}

function showHideDisability() {
	var optionSelected = $("#benchmarkDisability").val();
	if (optionSelected == 6) {
		$("#disabilitydiv").show();
		$("#disabilityNote").show();
		$("#disabilityType").show();
	} else {
		$("#disabilityType").val("");
		$("#disabilitydiv").hide();
		$("#disabilityNote").hide();
	}
}

function showHideCerebralpalsy() {
	var optionSelected = $("#cerebralPalsy").val();
	if (optionSelected == 6) {
		$("#CompensatoryDiv").show();
	} else {
		$("#compensatorytime").val("");
		$("#CompensatoryDiv").hide();
	}
}

function showHideDominantwriting() {
	var optionSelected = $("#dominantwriting").val();
	if (optionSelected == 6) {
		$("#DominantWritingdivDiv").show();
	} else {
		$("#needTime").val("");
		$("#DominantWritingdivDiv").hide();
	}
}

function showHideSpouseDetails() {
	var optionSelected = $("#mariatalStatus").val();
	if (optionSelected == 38 || optionSelected == '') {
		$("#spouseName").val("");
		$("#spouseEducation").val("");
		$("#spouseOccupation").val("");
		$("#spouseDesignation").val("");
		$("#spouseOrganization").val("");
		$("#SpouseNameDiv").hide();
		$("#SpouseEducationDiv").hide();
		$("#spouseOccupationDiv").hide();
		$("#spouseDesignationDiv").hide();
		$("#spouseOrganizationDiv").hide();
	} else {
		$("#SpouseNameDiv").show();
		$("#SpouseEducationDiv").show();
		$("#spouseOccupationDiv").show();
		$("#spouseDesignationDiv").show();
		$("#spouseOrganizationDiv").show();
	}
}

function showHideTwinsCheck() {
	var optionSelected = $("#twinsibilings").val();
	if (optionSelected == 6) {
		$("#nameoftwinDiv").show();
		$("#genderoftwinDiv").show();
	} else {
		$("#nameoftwin").val("");
		$("#genderoftwin").val("");
		$("#nameoftwinDiv").hide();
		$("#genderoftwinDiv").hide();
	}
}

function showPopUpForMobile(id, innerValue) {
	$(id).val('');
	$("#overlay").show();
	$("#dialog").fadeIn(300);
	document.getElementById("dynamicContent").innerHTML = innerValue;
}

function HideDialog() {
	document.getElementById("dynamicContent").innerHTML = "";
	$("#overlay").hide();
	$("#dialog").fadeOut(300);
}

function showPopUpForGovt()
{
	var isGovtService = $("#isGovtService").val();
	if(isGovtService==6)
	{
		$("#chkBoxDiv").show();
	}else{
		$("#chkBoxDiv").hide();	
		$('#govtServChkBox').prop('checked', false);
	}
}
/*function hidePopUpForGovt() {
	$("#overlay5").hide();
	$("#dialog5").fadeOut(300);
}*/

/*function showPopUpForConsecutiveDots(id) {
	var value = $(id).val();
	if (value.includes("..") || value.includes("--")) {
		$(id).val('');
		$("#overlay2").show();
		$("#dialog1").fadeIn(300);
	}
}

function HideDialog1() {
	$("#overlay2").hide();
	$("#dialog1").fadeOut(300);
}*/

var flag = false;
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

// For Gender Audit Trail
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

function showOtherReligion() {
	var religion = $("#religionBelief").val();
	if (religion == 153) {
		$("#otherReligionDiv").show();
	} else {
		$("#otherReligionDiv").hide();
		$("#religionBeliefOthers").val('');
	}
}

function showSpouseName()
{
	var mariatalStatusVal = $("#mariatalStatus").val();
	if (mariatalStatusVal == 6) {
		$("#spouseNameDiv").show();
	} else {
		$("#spouseNameDiv").hide();
		$("#spouseName").val('');
	}
}

function enableotherNativity()
{
	var nativId=$("#nativity").val();
	if(nativId=="39"){
		$("#otherNativityDiv").show();
	}
	else{
		$("#otherNativityDiv").hide();
		$("#otherNativity").val('');
	}
}
function showGovtFields()
{
	var isGovtService = $("#isGovtService").val();
	if (isGovtService == 6) {
		$("#orgNameDiv").show();
		$("#currentDesigDiv").show();
		$("#placeOfWorkeDiv").show();
		$("#govtDateDiv").show();
	} else {
		$("#orgName").val('');
		$("#currentDesig").val('');
		$("#placeOfWork").val('');
		
		$("#govtDate").val('');
		$("#ageInYears").val('');
		$("#ageInMonths").val('');
		$("#ageInDays").val('');
		$("#orgNameDiv").hide();
		$("#currentDesigDiv").hide();
		$("#placeOfWorkeDiv").hide();
		$("#govtDateDiv").hide();
	}
}

function showParentOrGuardian() {
	var parentAndGuardian = $("#parentAndGuardian").val();
	if (parentAndGuardian == 220) {
		$("#guardianName").val("");
		$("#guardianDiv").hide();
		$("#parentDiv").show();
	} else if (parentAndGuardian == 221) {
		$("#mothersName").val("");
		$("#fathersName").val("");
		$("#parentDiv").hide();
		$("#guardianDiv").show();
	} else {
		$("#mothersName").val("");
		$("#fathersName").val("");
		$("#parentDiv").hide();
		$("#guardianName").val("");
		$("#guardianDiv").hide();
	}
}

function clearCatVal() {
	var comCert = $("#categorycertificate").val();
	if (comCert == 6) {
		$("#categoryValDesc").val('');
	}
}

function setCategoryValues() {
	var religion = $("#religionBelief").val();
	var comCert = $("#categorycertificate").val();
	
	if (religion == 151) {
		$("#categoryValDesc option[value='2']").show();
		$("#categoryValDesc option[value='1']").hide();
		$("#categoryValDesc option[value='3']").hide();
		$("#categoryValDesc option[value='4']").hide();
		$("#categoryValDesc option[value='5']").hide();
		$("#categoryValDesc option[value='6']").hide();
		$("#categoryValDesc option[value='7']").hide();
	} else {
		$("#categoryValDesc option[value='2']").hide();
		$("#categoryValDesc option[value='1']").show();
		$("#categoryValDesc option[value='3']").show();
		$("#categoryValDesc option[value='4']").show();
		$("#categoryValDesc option[value='5']").show();
		$("#categoryValDesc option[value='6']").show();
		$("#categoryValDesc option[value='7']").show();

	}
}