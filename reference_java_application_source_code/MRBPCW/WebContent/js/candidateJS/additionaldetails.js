var disciplineId = '<s:property value="disciplineId" />';

$(document).ready(function() {
	showPastFields();
	//$('.selectpicker').selectpicker('setStyle', 'btn btn-default', 'remove');
	//$('.selectpicker').selectpicker('setStyle', 'form-control', 'add');
	
	if(disciplineId != null && disciplineId != "" && disciplineId == 1){
		populateRef1DistrictForState();
		populateRef2DistrictForState();
	}
	errorField();
//for mobile keyboard starts here
	
	//other deatils starts here
	var msg1 = "Please enter only Alphanumeric values and spaces with max 50 characters";
	
	function regexforDetls(value) {
		var res = /^[A-Za-z0-9 ]*$/i.test(value);
		return res
	}
	
	jQuery('#academicAward').on('change', function(event) {
		var result = regexforDetls(this.value);
		if (!result) {
			showPopUpForMobile("#academicAward", msg1);
		}
	});
	
	
var msg2 = "Please enter only Alphanumeric values and spaces with max 1000 characters. Special characters of comma, ampersand, back and forward slash dot to be allowed";
	
	function regexforDetls1(value) {                        
		var res = /^[A-Za-z0-9,&\\/. ]*$/i.test(value);  
		return res
	}
	
	jQuery('#reasonForNotJoining').on('change', function(event) {
		var result = regexforDetls1(this.value);
		if (!result) {
			showPopUpForMobile("#reasonForNotJoining", msg2);
		}
	});
	jQuery('#appliedInPast').on('change', function(event) {
		var result = regexforDetls1(this.value);
		if (!result) {
			showPopUpForMobile("#appliedInPast", msg2);
		}
	});

	//statement of purpose starts here
	jQuery('#stmtOfPurpose').on('change', function (event) {
		var result = regexforDetls1(this.value);
		if(!result)
		{
			showPopUpForMobile("#stmtOfPurpose", msg2);
		}
	});
	jQuery('#otherInfo').on('change', function (event) {
		var result = regexforDetls1(this.value);
		if(!result)
		{
			showPopUpForMobile("#otherInfo", msg2);
		}
	});
	//other details ends here
	
	//referee details starts here
	
	var msg3 = "Please enter only alphabets and spaces and Max. 50 Characters,";
	
	function regxforalphabetsonly(value){
		var alphabetsonly = /^[A-Za-z ]*$/i.test(value);
		return alphabetsonly
	}
	
	jQuery('#ref1Name').on('change', function(event) {
		var result = regxforalphabetsonly(this.value);
		if (!result) {
			showPopUpForMobile("#ref1Name", msg3);
		}
	});
	
	jQuery('#ref1Desig').on('change', function(event) {
		var result = regxforalphabetsonly(this.value);
		if (!result) {
			showPopUpForMobile("#ref1Desig", msg3);
		}
	});
	
	jQuery('#ref2Name').on('change', function(event) {
		var result = regxforalphabetsonly(this.value);
		if (!result) {
			showPopUpForMobile("#ref2Name", msg3);
		}
	});
	
	jQuery('#ref2Desig').on('change', function(event) {
		var result = regxforalphabetsonly(this.value);
		if (!result) {
			showPopUpForMobile("#ref2Desig", msg3);
		}
	});
	
	var msg4 = "Please enter only Max 75 Characters, numbers/ spaces/special characters of comma, ampersand, back and forward slash dot to be allowed";
	var msg5 = "Please enter only Max 50 Characters, numbers/ spaces/special characters of comma, ampersand, back and forward slash dot to be allowed";
	
	function regxforalphabetsandSpecialChar(value){
		var res = /^[A-Za-z0-9,&\\/. ]*$/i.test(value);
		return res
	}
	//for referee 1
	jQuery('#ref1Add1').on('change', function(event) {
		var result = regxforalphabetsandSpecialChar(this.value);
		if (!result) {
			showPopUpForMobile("#ref1Add1", msg4);
		}
	});
	
	jQuery('#ref1Add2').on('change', function(event) {
		var result = regxforalphabetsandSpecialChar(this.value);
		if (!result) {
			showPopUpForMobile("#ref1Add2", msg4);
		}
	});
	
	jQuery('#cityNameother1').on('change', function(event) {
		var result = regxforalphabetsandSpecialChar(this.value);
		if (!result) {
			showPopUpForMobile("#cityNameother1", msg5);
		}
	});
	//For referee 2
	jQuery('#ref2Add1').on('change', function(event) {
		var result = regxforalphabetsandSpecialChar(this.value);
		if (!result) {
			showPopUpForMobile("#ref2Add1", msg4);
		}
	});
	
	jQuery('#ref2Add2').on('change', function(event) {
		var result = regxforalphabetsandSpecialChar(this.value);
		if (!result) {
			showPopUpForMobile("#ref2Add2", msg4);
		}
	});
	
	jQuery('#cityNameother2').on('change', function(event) {
		var result = regxforalphabetsandSpecialChar(this.value);
		if (!result) {
			showPopUpForMobile("#cityNameother2", msg5);
		}
	});
	
	
	//for pincode only
	var msgForPincode = "Please enter only numbers, must be 6 digits.";

	function regxfornumberonly(value){ //character allowed are '0-9'
		var pincode = /^[0-9]*$/i.test(value);
			return pincode
		}

	jQuery('#pinCode').on('change', function (event) {
		var result = regxfornumberonly(this.value);
		if(!result)
		{
			showPopUpForMobile("#pinCode", msgForPincode);
		}
	});

	jQuery('#pinCode2').on('change', function (event) {
		var result = regxfornumberonly(this.value);
		if(!result)
		{
			showPopUpForMobile("#pinCode2", msgForPincode);
		}
	});
	//referee details ends here
	
	//email and mobile 
	function regexforEmail(value) {
        var email = /^[a-zA-Z0-9@_.]*$/i.test(value);
        return email
    }
    jQuery('#ref1EmailAddress').on('change', function(event) {
        var result = regexforEmail(this.value);
        if (!result) {
            var msgForEmail = "Please enter only alphabets, numbers and special characters such as @_. maximum 50 characters.";   
            showPopUpForMobile("#ref1EmailAddress", msgForEmail);
            }
    });
    jQuery('#ref2EmailAddress').on('change', function(event) {
        var result = regexforEmail(this.value);
        if (!result) {
            var msgForEmail = "Please enter only alphabets, numbers and special characters such as @_. maximum 50 characters.";   
            showPopUpForMobile("#ref2EmailAddress", msgForEmail);
            }
    });
    function regexforMob(value) {
        var mobNo = /^[[0-9]{2,4}[-\s\.]?[0-9]{8,10}]*$/i.test(value); 
        return mobNo
    }
    jQuery('#ref1Contact').on('change', function(event) {
        var result = regexforMob(this.value);
        if (!result) {
            var msg = "Please enter only numbers, Country code and mobile number to be separated by hyphen.";
            showPopUpForMobile("#ref1Contact", msg);
        }
    });
    jQuery('#ref2Contact').on('change', function(event) {
        var result = regexforMob(this.value);
        if (!result) {
            var msg = "Please enter only numbers, Country code and mobile number to be separated by hyphen.";
            showPopUpForMobile("#ref2Contact", msg);
        }
    });
	
});



var currUTStateId = "";
var currAltUTStateId = "";

function populateRef1DistrictForState() {
	currUTStateId = "#ref1StateList";
	populateRef1District();
}

function populateRef1District() {
	var ref1State = $("" + currUTStateId).val();
	
	dataString = "ref1StateVal=" + ref1State
	$.ajax({
		url : "CandidateAction_getRef1DistrictList.action",
		async : true,
		data : dataString,
		type : 'POST',
		beforeSend : function() {
			$('#ref1DistrictList').empty();
			$("#ref1DistrictList").append(
					$('<option></option>').val("").html(
							"--Select District--"));

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
					//alert(message);
					//return false;
				} else {
					$.each(element, function(val) {
						var nameAndIDArr = element[val].split("#");

						$("#ref1DistrictList").append(
								$('<option></option>').val(nameAndIDArr[1])
										.html(nameAndIDArr[0]));
					});
				}
			}

			var ref1DistrictValBean = '<s:property value="additionalDetailsBean.ref1District"/>';
			//alert(ref1DistrictValBean);
			$("#ref1DistrictList").val(ref1DistrictValBean);

		}
	});

}

function populateRef2DistrictForState() {//this method is to be called onchange 
	currAltUTStateId = "#ref2StateList";
	populateRef2District();
}

function populateRef2District() {

	var ref2state = $("" + currAltUTStateId).val();

	dataString = "ref2StateVal=" + ref2state
	$
			.ajax({
				url : "CandidateAction_getRef2DistrictList.action",
				async : true,
				data : dataString,
				type : 'POST',
				beforeSend : function() {
					$('#ref2DistrictList').empty();
					$("#ref2DistrictList").append(
							$('<option></option>').val("").html(
									"--Select District--"));

				},
				error : function(ajaxrequest) {
					window.reload();
				},
				success : function(responseText) {
					responseText = $.trim(responseText);
					if (responseText.length > 0) {
						element = responseText.split(",");

						message = responseText.substring(2,
								responseText.length);
						if (element[0] == "9") {
							//alert(message);
							//return false;
						} else {
							$.each(element, function(val) {
								var nameAndIDArr = element[val].split("#");

								$("#ref2DistrictList").append(
										$('<option></option>').val(
												nameAndIDArr[1]).html(
												nameAndIDArr[0]));
							});
						}
					}
					var ref2DistrictValBean = '<s:property value="additionalDetailsBean.ref2District"/>';
					//alert(ref2DistrictValBean);
					$("#ref2DistrictList").val(ref2DistrictValBean);
				}
			});

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