<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<script type="text/javascript" src="js/moment.min.js"></script>
<script type="text/javascript" src="js/moment-precise-range.js"></script>
<link rel="stylesheet" href="css/font-awesome.min.css">
<html>
<head>
<script type="text/javascript">
	var maxRowLength = 1;
	var dates = {
		convert : function(d) {
			// Converts the date in d to a date-object. The input can be:
			//   a date object: returned without modification
			//  an array      : Interpreted as [year,month,day]. NOTE: month is 0-11.
			//   a number     : Interpreted as number of milliseconds
			//                  since 1 Jan 1970 (a timestamp) 
			//   a string     : Any format supported by the javascript engine, like
			//                  "YYYY/MM/DD", "MM/DD/YYYY", "Jan 31 2009" etc.
			//  an object     : Interpreted as an object with year, month and date
			//                  attributes.  **NOTE** month is 0-11.
			return (d.constructor === Date ? d
					: d.constructor === Array ? new Date(d[0], d[1], d[2])
							: d.constructor === Number ? new Date(d)
									: d.constructor === String ? new Date(d)
											: typeof d === "object" ? new Date(
													d.year, d.month, d.date)
													: NaN);
		},
		compare : function(a, b) {
			// Compare two dates (could be of any type supported by the convert
			// function above) and returns:
			//  -1 : if a < b
		        //   0 : if a = b
		        //   1 : if a > b
			// NaN : if a or b is an illegal date
			// NOTE: The code inside isFinite does an assignment (=).
			return (isFinite(a = this.convert(a).valueOf())
					&& isFinite(b = this.convert(b).valueOf()) ? (a > b)
					- (a < b) : NaN);
		}
	}

	function showPopUpForMobile(id,innerValue) {
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
	/* function showPopUpForConsecutiveDots(id) {
		var value = $(id).val();
		if (value.includes("..")) {
			$(id).val('');
			$("#overlay2").show();
			$("#dialog2").fadeIn(300);
			document.getElementById("dynamicContent2").innerHTML = "";
		}
	}

	function HideDialog3() {
		document.getElementById("dynamicContent2").innerHTML = "";
		$("#overlay2").hide();
		$("#dialog2").fadeOut(300);
	} */
	
	function checkSpecialKeys(e) {
		if (e.keyCode != 8 && e.keyCode != 46 && e.keyCode != 33
				&& e.keyCode != 34 && e.keyCode != 35 && e.keyCode != 37
				&& e.keyCode != 38 && e.keyCode != 39 && e.keyCode != 40
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

	function numbersonly(e) {
		var unicode = e.charCode ? e.charCode : e.keyCode
		if (unicode != 8) {
			if ((unicode<48||unicode>57) && unicode != 9 && unicode != 46) //if not a number
				return false //disable key press
		}
	}

	function numberswithdot(e) {
		var unicode = e.charCode ? e.charCode : e.keyCode
		if (unicode == 39) {
			return true;
		}
		if (unicode != 8) {
			if ((unicode<48||unicode>57) && unicode != 9 && unicode != 46) //if not a number
				return false //disable key press
		}
	}

	function frmSubmit() {
		$("#covidDutyCertificateActionID").attr('action', 'CovidDutyCertificateAction_updateCandidateStage.action');
		$("#covidDutyCertificateActionID").submit();
	}
	
	var msgForInstName = "Please enter only alphabets, numbers, spaces and dot, maximum 100 characters.";
	
	var msgForInstAddr = "Please enter only alphabets, numbers, spaces and special characters such as forward & backward slash, dot, " +
	"ambercent and comma, maximum 100 characters.";
	
	function regexforAlphanumericSpaceDot(value){ 
		var instname = /^[a-zA-Z0-9. ]*$/i.test(value);
		return instname
	}
	
	function regexforAlphanumericSplChar(value){ 
		var addr = /^[A-Za-z0-9 ,&.\\/]*$/i.test(value);
		return addr
	}
	
	$(document).ready(function() {
		errorField();
		
		var disciplineId = '<s:property value="disciplineId" />';
		var yesNo = $("#yesNo option:selected").text();
		var counter = $("tr[id^='adrow']").last();
		var rowID = counter.attr('id').split('row');
		var rowCount = parseInt(rowID[1]);
		
		<s:iterator value="covidDutyCertDetailsList" status="stat">
			mulval = '<s:property value="category" />';
			var Count = '<s:property value="%{#stat.count}" />';
			var obj = $('#category' + Count);
			var mulval;
			var selected = mulval.split(",");
			for ( var i in selected) {
				var val = selected[i];
				obj.find('option:[value=' + val + ']').attr('selected', 1).css("color", "orange");
			}
		</s:iterator>

		if (Count == 0) {
			$('#remove').prop("disabled", true);
		}

		if (Count > 14) {
			$('#add').prop("disabled", true);
		}

		$('#add').click(function() {
			$('#remove').prop("disabled", false);
		});

		var text = $("input[name='workExp']:checked").val();
		if (text == "") {
			text = "Y";
		}
		
		getDiff();
		var count = 1;
		var startYrRangeFrm = '2020';  
		var eduDate =  '<s:property value="eduDate"/>';
		var eduDatePlus = new Date(eduDate);
		eduDatePlus.setDate(eduDatePlus.getDate()+1);
		
		<s:iterator value="covidDutyCertDetailsList" status="stat">
			$("#fromYear_" + count).datepicker({
				showOn : "button",
				changeMonth : true,
				changeYear : true,
				yearRange : '2020:2023',
				minDate : new Date('Mar 01,2020'),
				maxDate : new Date('May 05,2023'),
				buttonImageOnly : true,
				buttonImage : "images/cale-img.png",
				buttonText : "Select Period of Work-From",
				dateFormat : 'dd-M-yy',
				numberOfMonths : 1,
				onSelect : function(dateText, instance) {
					 var fromYrId = $(this).attr('id').split("_")[1];
					date = $.datepicker.parseDate(instance.settings.dateFormat, dateText, instance.settings);
					$("#toYear_" + fromYrId).val("");
					date.setDate(date.getDate());
					$("#toYear_" + fromYrId).datepicker("option","minDate",	new Date(date));
				}
			});
	
			var fromDate = new Date($("#fromYear_" + count).val());
			fromDate.setDate(fromDate.getDate());
			$("#toYear_" + count).datepicker({
				showOn : "button",
				changeMonth : true,
				changeYear : true,
				yearRange : '2020:2023',
				minDate : new Date('Mar 01,2020'),
				maxDate : new Date('May 05,2023'),
				buttonImageOnly : true,
				buttonImage : "images/cale-img.png",
				buttonText : "Select Period of Work-To",
				dateFormat : 'dd-M-yy',
				numberOfMonths : 1,
				onSelect : function(selected) {
					var toYrId = $(this).attr('id').split("_")[1];
					var todate = $("#toYear_" + toYrId).datepicker("getDate");
					var todateObj = new Date(todate.getFullYear(), todate.getMonth(), todate.getDate()); // to date datepicker
					var todaydateObj ='05-May-2023';
					var diff = dates.compare(todateObj,	todaydateObj);

					if (diff >= 0) {
						$("#fromYear_" + toYrId).datepicker(
								"option", "maxDate", 0);
					} else {
						$("#fromYear_" + toYrId).datepicker(
								"option", "maxDate", selected);
					}
				}
			});

			count++;
		</s:iterator> 

	});
	
	function displaySignedBy(e) {
		var id = e.split('institutionType')[1];
		var optionSelected = $('#institutionType' +id).val();
		if (optionSelected != null && optionSelected != "" && optionSelected != undefined && optionSelected != "Select Institution Type") {
			var dataString = "institutionType=" + optionSelected;
			$.ajax({
				url : "CovidDutyCertificateAction_displaySignedBy.action",
				async : false,
				data : dataString,
				error : function(ajaxrequest) {
					console.log('DisplaySignedBy : ' + ajaxrequest.responseText);
				},
				success : function(responseText) {
					
					responseText = $.trim(responseText);
					if (responseText != "null" && responseText.length > 0) {
						$('#certificateSignedBy' +id).val(responseText.split(',')[0]);
						$('#certiCounterSignedBy' +id).val(responseText.split(',')[1]);
					}
				}
			});
		} else {
			$('#certificateSignedBy' +id).val('');
			$('#certiCounterSignedBy' +id).val('');
		}
	}
	
	function checkCharsForInstName(id) {
		var result = regexforAlphanumericSpaceDot($('#nameOfMedInstitution' + id).val());
		if(!result)	{
			showPopUpForMobile('#nameOfMedInstitution' + id, msgForInstName);
		}
		/* else
			showPopUpForConsecutiveDots('#nameOfMedInstitution' + id); */
	}
	
	function checkCharsForInstAddr(id) {
		var result = regexforAlphanumericSplChar($('#addressOfInstitute' + id).val());
		if(!result)	{
			showPopUpForMobile('#addressOfInstitute' + id, msgForInstAddr);
		}
		/* else
			showPopUpForConsecutiveDots('#addressOfInstitute' + id); */
	}
	
	function errorField() {
		<s:iterator value="errorField">
			jQuery("select[id='<s:property />']").addClass('red-border');
			jQuery("input[id='<s:property />']").addClass('red-border');
		</s:iterator>
	}
	
	function getDiff() {
		window.setTimeout(dateCalculate, 300);
	}
	
	function dateCalculate()
	{
		var totalExpYears = 0;
		var totalExpMonths = 0;
		var totalExpDays = 0;
		var countExp = 0;
		var tableRows = $('#covidDutyCertformDetails tr').length;
		
		for (var count = 1; count <= 51; count++) {
			var startDate1 ="";
			var endDate="";
		var dateString1 = $("#toYear_" + count).val();
		var dateString2 = $("#fromYear_" + count).val();
			if(dateString1 != undefined || dateString2 != undefined)
		{
			var momentObj1 = moment(dateString1, 'DD-MMM-YYYY');
			var toDate = momentObj1.format('YYYY-MM-DD');
			startDate1 = toDate.split("-").map(Number);
			if (startDate1[1] != "" && startDate1[1] != null) {
				var startDate = [ startDate1[0], startDate1[1] - 1, startDate1[2] ];
			}
		
			
		var momentObj2 = moment(dateString2, 'DD-MMM-YYYY');
		var fromDate = momentObj2.format('YYYY-MM-DD');
		endDate = fromDate.split("-").map(Number);
		if (endDate[1] != "" && endDate[1] != null) {
			var endDate2 = [ endDate[0], endDate[1] - 1, endDate[2] ];
		}
		
		if(startDate !=null && startDate !=undefined && startDate!="")
		{
			var toDate = moment(startDate).add(1,'days');
		}else
			{
				var toDate = moment(startDate).add(0,'days');
			}
		var fromDate = moment(endDate2);
		var diff = moment.preciseDiff(toDate, fromDate, true);
		if (dateString1 != "" && dateString2 != "") {
			$("#periodAsOnYear").val(diff.years);
			$("#periodAsOnMonth").val(diff.months);
			$("#periodAsOnDays").val(diff.days);
			$("#durationOfCovidService_" + count).val(diff.years + ' years ' + diff.months + ' months '+ diff.days + ' days ');
			}
		
		totalExpYears = totalExpYears + diff.years;
		totalExpMonths = totalExpMonths + diff.months;
		totalExpDays = totalExpDays + diff.days;
		
		countExp++;
		
		if (countExp > 1) {
			while (totalExpDays > 29) {
				totalExpDays = totalExpDays - 30;
				totalExpMonths = totalExpMonths + 1;
			}

			while (totalExpMonths > 11) {
				totalExpMonths = totalExpMonths - 12;
				totalExpYears = totalExpYears + 1;
			}

		}
		
		console.log(diff.years+" years" + diff.months+" months"+ diff.days+" days");
		$("#yearOfTotalService").val(totalExpYears);
		$("#monthOfTotalService").val(totalExpMonths);
		$("#dayOfTotalService").val(totalExpDays);

		//For validation
		$("#yearOfTotalServiceHidden").val(totalExpYears);
		$("#monthOfTotalServiceHidden").val(totalExpMonths);
		$("#dayOfTotalServiceHidden").val(totalExpDays);
		}
		}
	}

	function addRow() {
		maxRowLength++;
		var lLastRow = $("tr[id^='adrow']").last();
		var $clone = $(lLastRow).clone();
		var rowID = $clone.attr('id').split('row');

		var trID = rowID[1];
		var otherId = (parseInt(trID) + 2);
		$clone.attr('id', "adrow" + (parseInt(trID) + 1));
		$clone.attr('class', "adrow" + (parseInt(trID) + 1));
		if (otherId >= 15) {
			$('#add').prop("disabled", true);
		} else {
			$('#add').prop("disabled", false);
		}

		$clone.find('td').each(
			function() {
				var el = $(this).find(':nth-child(1):not("option")');
				var id = el.attr('id') || null;

				var name = el.attr('name') || null;

				if (id && name) {

					if (id.indexOf("districtVal") != -1 ) {
						var el1 = el.next();
						var id1 = el1.attr('id') || null;
						var name1 = el1.attr('name') || null;
						var j1 = id1.match(/\d+/);
						var newID1 = id1.replace(j1, parseInt(j1) + 1);
						el1.attr('id', newID1);
						var k1 = name1.match(/\d+/);
						var newName1 = name1.replace(k1, parseInt(k1) + 1)
						el1.attr('name', newName1);
					}

					var j = id.match(/\d+/);
					var newID = id.replace(j, parseInt(j) + 1);
					el.attr('id', newID);
					var k = name.match(/\d+/);
					var newName = name.replace(k, parseInt(k) + 1)
					el.attr('name', newName);
				}
			});
		$clone.find('input:text').val('');
		$clone.find('img').each(function() {
			$(this).remove();
		});

		lLastRow.closest('table').append($clone);

		var otherId = (parseInt(trID) + 2);
		$('#institutionType' +otherId).val('');
		$('#districtList' +otherId).val('');

		$('#institutionType' +otherId).removeClass('red-border');
		$("#nameOfMedInstitution"+ otherId).removeClass('red-border');
		$('#districtList' +otherId).removeClass('red-border');
		$("#addressOfInstitute"+ otherId).removeClass('red-border');
		$("#fromYear_"+ otherId).removeClass('red-border');
		$("#toYear_"+ otherId).removeClass('red-border');
		$("#durationOfCovidService"+ otherId).removeClass('red-border');
		
		var nextCheckBox = parseInt(trID) + 1;

		$("#check" + nextCheckBox).prop("checked", false);

		addDatepicker(parseInt(trID) + 2);
		
		jQuery('#nameOfMedInstitution' + otherId).on('change', function (event) {
			
			var result = regexforAlphanumericSpaceDot(this.value);
			if(!result)	{
				showPopUpForMobile('#nameOfMedInstitution' + otherId, msgForInstName);
			}
			/* else{
				showPopUpForConsecutiveDots('#nameOfMedInstitution' + otherId);
			} */
		});
		
		jQuery('#addressOfInstitute' + otherId).on('change', function (event) {
			
			var result = regexforAlphanumericSplChar(this.value);
			if(!result)	{
				showPopUpForMobile('#addressOfInstitute' + otherId, msgForInstAddr);
			}
			/* else{
				showPopUpForConsecutiveDots('#addressOfInstitute' + otherId);
			} */
		});
		
	}
	
	function removeRow() {

		if (!validateRemoveRow())
			return false;
		var otherId;
		var flag = false;
		$("input[id^='check']").each(function() {

			if ($(this).prop("checked") == true) {
				flag = true;

				var rowID = $(this).attr('id').split('check');
				var trID = rowID[1];
				otherId = (parseInt(trID) + 2);
				var parent = $('#' + "adrow" + trID);

				parent.remove("");

				if (otherId > 15) {
					$('#add').prop("disabled", true);
				} else {
					$('#add').prop("disabled", false);
				}
			}
		});
		dateCalculate();
		if (!flag) {
			alert("Please select atleast one check box.");
		}
	}

	function validateRemoveRow() {

		var checkCount = 0;
		var rowId = 0;
		$("input[id^='check']").each(function() {
			if ($(this).prop("checked") == true) {
				checkCount++;
			}
		});

		var tableRows = 0;
		$("#covidDutyCertformDetails").find('tr').each(function(index, value) {
			rowId = $(this).attr('id');
			if (rowId != null && rowId != undefined && rowId != "") {
				tableRows++;
			}
		});

		if (checkCount == tableRows) {
			alert('You cannot remove all rows. Atleast One row should be unticked!');
			return false;
		}
		
		if ((tableRows - checkCount) == 1) {
			$('#remove').prop("disabled", true);
		}
		
		return true;
	}

	function addDatepicker(rowId) {
		var fromYearDP = "#fromYear_" + rowId;
		var toYearDP = "#toYear_" + rowId

		$(toYearDP).removeClass('hasDatepicker');
		$(fromYearDP).removeClass('hasDatepicker');
		var startYrRangeFrm = '2020'; //<s:property value="firstDate"/>
		var startYrRangeTo = "2023";
		
		$(fromYearDP).datepicker({
			showOn : "button",
			buttonImageOnly : true,
			changeMonth : true,
			changeYear : true,
			yearRange : '2020:2023',
			minDate : new Date('Mar 01,2020'),
			maxDate : new Date('May 05,2023'),
			buttonImage : "images/cale-img.png",
			buttonImageOnly : true,
			dateFormat : 'dd-M-yy',
			buttonText:"Select Period of Work-From",
			numberOfMonths : 1,
			onSelect : function(dateText, instance) {

			var fromYrId = $(this).attr('id').split("_")[1];
			date = $.datepicker.parseDate(
					instance.settings.dateFormat, dateText,
					instance.settings);
			$(toYearDP +fromYrId).val("");
			date.setDate(date.getDate());
			$(toYearDP + fromYrId).datepicker("option", "minDate",
			new Date(date));		 
			}
		});

		$(toYearDP).datepicker(
				{
					showOn : "button",
					buttonImageOnly : true,
					changeMonth : true,
					changeYear : true,
					yearRange : '2020:2023',
					minDate : new Date('Mar 01,2020'),
					maxDate : new Date('May 05,2023'),
					buttonImage : "images/cale-img.png",
					buttonImageOnly : true,
					dateFormat : 'dd-M-yy',
					buttonText:"Select Period of Work-To",
					numberOfMonths : 1,
					onSelect : function(selected) {						
						var toYrId = $(this).attr('id').split("_")[1];
						var todate = $("#toYear_" + toYrId).datepicker(
								"getDate");
						var todateObj = new Date(todate.getFullYear(), todate
								.getMonth(), todate.getDate()); // to date datepicker
						var todaydateObj = '05-May-2023'; //new Date(); //current date
						var diff = dates.compare(todateObj, todaydateObj);

						if (diff >= 0) {
							$(fromYearDP + toYrId).datepicker("option", "maxDate", 0);
						} else {
							$(fromYearDP + toYrId).datepicker("option", "maxDate",
									selected);
						}
					}
				});
	}

	function lettersOnly(e) {
		var charCode = e.charCode ? e.charCode : e.keyCode;
		if ((charCode > 64 && charCode < 91)
				|| (charCode > 96 && charCode < 123) || charCode == 8
				|| charCode == 32)

			return true;
		else
			return false;
	}

	function alphaNumericWithSpace(event) {
		return /[A-Za-z0-9 ]/g.test(event.key);
	}
	
	function alphaNumericwithSplChar(event) {
		return /[A-Za-z0-9 ,&.\\/]/g.test(event.key);
	}
	
	function alphaNumericWithSpaceDot(event) {
		return /[A-Za-z0-9. ]/g.test(event.key);
	}

	function salaryValidator(event) {
		return /[0-9\.]/g.test(event.key);
	}
	
	function isNumber(evt) {
	    evt = (evt) ? evt : window.event;
	    var charCode = (evt.which) ? evt.which : evt.keyCode;
	    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
	        return false;
	    }
	    return true;
	}

	function numericOnly(e) 
	{
	     var charCode = e.charCode? e.charCode : e.keyCode;
	     if ((charCode > 47 && charCode < 58) || charCode == 8)
	         return true;
	     else
	         return false;
	}
	
	function yesorNo(){
		var yesorNo=$("#yesNo").val();
		if(yesorNo== "6"){
			$("#covidDutyCertformDetails").show();
			$("#addRemoveRows").show();
			$("#totalDurationOfService").show();
		} else if (yesorNo== "7"){
			$("#covidDutyCertformDetails").hide();
			$("#addRemoveRows").hide();
			$("#totalDurationOfService").hide();
		} else{
			$("#covidDutyCertformDetails").hide();
			$("#addRemoveRows").hide();
			$("#totalDurationOfService").hide();
		}
		//Code added to clear the data entered on change of Yes/No selection of WorkExp starts
		<s:iterator value="covidDutyCertDetailsList" status="stat" >
			var Count='<s:property value="%{#stat.count}" />';
			if(yesorNo=="7")
			{
				for (var i=0;i<=Count;i++) {
					$("#institutionType"+ i).val("");
					$("#nameOfMedInstitution"+ i).val("");
					$("#districtList"+ i).val("");
					$("#addressOfInstitute"+ i).val("");
					$("#fromYear_"+ i).val("");
					$("#toYear_"+ i).val("");
					$("#durationOfCovidService_"+ i).val("");		
					$("#certificateSignedBy"+ i).val("");
					$("#certiCounterSignedBy"+ i).val("");
					
					$("#adrow"+(i+1)).remove();	
				}
					$("#yearOfTotalService").val("");
					$("#monthOfTotalService").val(""); 
					$("#dayOfTotalService").val(""); 	
			}
		 </s:iterator>
		//Code added to clear the data entered on change of Yes/No selection of WorkExp ends
	}
	
	function yesorNoOnload(){
		var yesorNo=$("#yesNo").val();
		if(yesorNo== "6"){
			$("#covidDutyCertformDetails").show();
			$("#addRemoveRows").show();
			$("#totalDurationOfService").show();
		} else{
			$("#covidDutyCertformDetails").hide();
			$("#addRemoveRows").hide();
			$("#totalDurationOfService").hide();
		}
		//Code added to clear the data entered on change of Yes/No selection of WorkExp starts
		<s:iterator value="covidDutyCertDetailsList" status="stat" >
			var Count='<s:property value="%{#stat.count}" />';
			if(yesorNo=="7")
			{
				for (var i=0;i<=Count;i++) {
					$("#institutionType"+ i).val("");
					$("#nameOfMedInstitution"+ i).val("");
					$("#districtList"+ i).val("");
					$("#addressOfInstitute"+ i).val("");
					$("#fromYear_"+ i).val("");
					$("#toYear_"+ i).val("");
					$("#durationOfCovidService_"+ i).val("");	
					$("#certificateSignedBy"+ i).val("");	
					$("#certiCounterSignedBy"+ i).val("");
					
					$("#adrow"+(i+1)).remove();	
				}
					$("#yearOfTotalService").val("");
					$("#monthOfTotalService").val(""); 
					$("#dayOfTotalService").val(""); 	
			}
		 </s:iterator>
		//Code added to clear the data entered on change of Yes/No selection of WorkExp ends
	}
	
	function validate(evt) {
		var theEvent = evt || window.event;
		var key = theEvent.keyCode || theEvent.which;
		key = String.fromCharCode(key);
		var regex = /[0-9]|\./;
		if (evt.which == 8) {
			return true;

		}
		if (!regex.test(key)) {

			theEvent.returnValue = false;
			if (theEvent.preventDefault)
				theEvent.preventDefault();
		}
	}

	$(document).ready(
			function() {
				yesorNoOnload();
			//	displaySignedBy(id);
				$("#CandidateDiv .accordion").show();
				$('#AddressDiv .accordion').slideToggle('slow');
				$('#AddressDiv h3').addClass("current");
				$(".accordions h3").click(
						function() {
							$(this).next(".accordion").slideToggle("slow")
									.siblings(".accordion:visible").slideUp(
											"slow");
							$(this).toggleClass("current");
							$(this).siblings("h3").removeClass("current");
						});
			});
	$(document).ready(
			
			function() {

				$(".menuActive").closest(".submenu-item").addClass(
						"submenu-itemActive");
			});

	function isOndropContainsSpecialCharRestrict(str, id) {
		var re = /[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/]/gi;
		var eee = str.value;
		var xyz = eee
				.replace(/[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/]/gi, '');
		document.getElementById(id).value = xyz;
	}
	
</script>
<style type="text/css">
ul.actionMessage {
	margin: 0px;
}

.actionMessage li:first-child {
	display: none;
}

.actionMessage li {
	float: left;
	margin-left: -20px;
	width: 100%;
}

.tabDiv {
	width: 92% !important;
}

.innserContainer span.note {
	display: block !important;
}
</style>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
</head>
<body>
	<div class="container titlebg">
		<h1 class="pageTitle">
			Covid Duty Certificate <span class="userid_txt"> <s:if
					test="%{#session['SESSION_USER'] != null}">
					<strong>User ID</strong> -
										<s:label value="%{#session['SESSION_USER'].username}" />
				</s:if>
			</span>
		</h1>
	</div>
	<div>
		<div class="main-body">
			<div id="dashboard" class="tabDiv effect2">
				<s:form
					action="CovidDutyCertificateAction_saveCovidDutyCertificateDetails"
					name="covidDutyCertificateAction" id="covidDutyCertificateActionID">
					<div class="container common_dashboard effect2">
						<span style="color: red"><strong><s:property
									value="errMsg" /></strong></span>
						<s:if test='%{serverSideErrorMessage == "true"}'>
							<div style="border: #999 0px solid; padding: 3px; color: red"
								id="serverSideErrorMessage">
								<s:actionmessage escape="false" />
							</div>
							<div class="clear">&nbsp;</div>
						</s:if>
						<s:if test='%{saveFlag == "true"}'>
							<div style="border: #999 1px solid; padding: 3px; color: green;"
								id="successMessage">Covid Duty Certificate Details saved
								successfully.</div>
							<div class="clear">&nbsp;</div>
						</s:if>
						<s:hidden name="menuKey" value="%{#session['menuKey']}"
							id="menuKey"></s:hidden>
						<s:hidden name="disciplineId" id="disciplineIdHidden" />
						<s:hidden name="yearOfTotalServiceHidden"
							id="yearOfTotalServiceHidden" />
						<s:hidden name="monthOfTotalServiceHidden"
							id="monthOfTotalServiceHidden" />
						<s:hidden name="dayOfTotalServiceHidden"
							id="dayOfTotalServiceHidden" />

						<div class="accordions">
							<div id="CandidateDiv">
								<input type="hidden" name="isDataFound"
									value='<s:property value="dataFound"/>' />
								<div class="accordion">
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label">Have you worked in
													covid period? <span class="manadetory-fields">*</span>
												</label>
												<div class="row">
													<div class="col-sm-4">
														<s:select list="yesNoMap" name="yesNo"
															cssClass="form-control" headerKey=""
															headerValue="Select Have you worked in covid period?"
															id="yesNo" value="%{yesNo}" onchange="yesorNo()" />
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="table-responsive">
										<table class="table table-striped table-bordered personsl-dtl"
											id="covidDutyCertformDetails" width="110%">
											<tr>
												<th width="3%"></th>
												<th width="8%">Institution Type<span
													class="manadetory-fields">*</span></th>
												<th width="8%">Name of the Medical Institution<span
													class="manadetory-fields">*</span></th>
												<th width="8%">District<span class="manadetory-fields">*</span></th>
												<th width="8%">Address of the Institution<span
													class="manadetory-fields">*</span>
												</th>
												<th width="10%">Period of Work-From<span
													class="manadetory-fields">*</span>
												</th>
												<th width="10%">Period of Work-To<span
													class="manadetory-fields">*</span>
												</th>
												<th width="10%">Duration of Covid Service<span
													class="manadetory-fields">*</span>
												</th>
												<th width="10%">Certificate Signed by<span
													class="manadetory-fields">*</span>
												</th>
												<th width="10%">Certificate Counter Signed by<span
													class="manadetory-fields">*</span>
												</th>
											</tr>

											<s:iterator value="covidDutyCertDetailsList" status="stat"
												var="currentObject">
												<tr class="adrow<s:property value='%{#stat.index}'/>"
													id="adrow<s:property value='%{#stat.index}'/>">
													<td width="160"><input type="checkbox"
														name="check<s:property value='%{#stat.index}'/>"
														id="check<s:property value='%{#stat.index}'/>"
														class="fl-left"></td>
													<td width="160" class=institutionType><s:select
															headerKey="" headerValue="Select Institution Type"
															list="institutionTypeMap"
															name="covidDutyCertDetailsList[%{#stat.index}].institutionType"
															cssClass="form-control"
															id="institutionType%{#stat.count}"
															value="%{institutionType}"
															onchange="displaySignedBy(this.id)" /></td>
													<td><s:textfield
															name="covidDutyCertDetailsList[%{#stat.index}].nameOfMedInstitution"
															value="%{nameOfMedInstitution}"
															label="nameOfMedInstitution" maxlength="100"
															onpaste="return false;" autocomplete="off"
															class="form-control"
															id="nameOfMedInstitution%{#stat.count}"
															onkeypress="return alphaNumericWithSpaceDot(event);"
															onchange="checkCharsForInstName(%{#stat.count})"
															onmouseenter="isOndropContainsSpecialCharRestrict(this,this.id)" />
													</td>
													<td width="160" class=districtVal><s:select
															headerKey="" headerValue="Select District"
															list="districtList"
															name="covidDutyCertDetailsList[%{#stat.index}].districtVal"
															cssClass="form-control" id="districtList%{#stat.count}"
															value="%{districtVal}" /></td>
													<td width="200"><s:textfield
															name="covidDutyCertDetailsList[%{#stat.index}].addressOfInstitute"
															value="%{addressOfInstitute}" label="addressOfInstitute"
															maxlength="100" onpaste="return false;"
															autocomplete="off" class="form-control"
															onkeypress="return alphaNumericwithSplChar(event);"
															onchange="checkCharsForInstAddr(%{#stat.count})"
															onmouseenter="isOndropContainsSpecialCharRestrict(this,this.id)"
															id="addressOfInstitute%{#stat.count}" /></td>
													<td width="220" class="dateInput"><s:textfield
															id="fromYear_%{#stat.count}"
															name="covidDutyCertDetailsList[%{#stat.index}].fromYear"
															readonly="true" onkeypress="return restrictEnter(event);"
															onblur="return getDiff();"
															cssClass="form-control disableEnroll"
															cssStyle="padding:4px" /></td>
													<td width="220" class="dateInput"><s:textfield
															id="toYear_%{#stat.count}"
															name="covidDutyCertDetailsList[%{#stat.index}].toYear"
															onkeypress="return restrictEnter(event);"
															onblur="return getDiff();" readonly="true"
															cssClass="form-control disableEnroll"
															cssStyle="padding:4px" /></td>
													<td><s:textfield
															name="covidDutyCertDetailsList[%{#stat.index}].durationOfCovidService"
															value="%{durationOfCovidService}" readonly="true"
															label="durationOfCovidService" maxlength="50"
															onpaste="return false;"
															id="durationOfCovidService_%{#stat.count}"
															onkeypress="return restrictEnter(event);"
															class="form-control" /></td>
													<td width="5"><s:textfield
															name="covidDutyCertDetailsList[%{#stat.index}].certificateSignedBy"
															value="%{certificateSignedBy}"
															label="certificateSignedBy" class="form-control"
															readonly="true" id="certificateSignedBy%{#stat.count}" /></td>
													<td width="7"><s:textfield
															name="covidDutyCertDetailsList[%{#stat.index}].certiCounterSignedBy"
															value="%{certiCounterSignedBy}"
															label="certiCounterSignedBy" maxlength="25"
															onpaste="return false;" class="form-control"
															readonly="true" onkeypress="return restrictEnter(event);"
															onmouseenter="isOndropContainsSpecialCharRestrict(this,this.id)"
															id="certiCounterSignedBy%{#stat.count}" /></td>
												</tr>
											</s:iterator>
										</table>
									</div>
									<div class="row" id="addRemoveRows">
										<div
											class="col-md-2 col-md-offset-0 col-xs-10 col-xs-offset-1 mt10">
											<input type="button" value="Add" id="add"
												class="ripple1 btn btn-warning btn-block"
												onClick="addRow();">
										</div>
										<div
											class="col-md-2 col-md-offset-0 col-xs-10 col-xs-offset-1 mt10">
											<input type="button" value="Remove" id="remove"
												class="ripple1 btn btn-warning btn-block"
												onClick="removeRow();getDiff();">
										</div>
									</div>
									<div class="row" id="totalDurationOfService">
										<hr>
										<div class="col-sm-3">
											<div class="form-group">
												<label>Total Duration of Covid Service</label>
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<div class="col-sm-4 col-xs-4 padleft0">
													<s:textfield name="yearOfTotalService"
														value="%{yearOfTotalService}" readonly="true"
														label="yearOfTotalService" maxlength="2"
														onpaste="return false;" id="yearOfTotalService"
														onkeypress="return restrictEnter(event);"
														cssClass="form-control"></s:textfield>
													<span class="note">Years</span>
												</div>
												<div class="col-sm-4 col-xs-4 padleft0">
													<s:textfield name="monthOfTotalService"
														value="%{monthOfTotalService}" readonly="true"
														label="monthOfTotalService" maxlength="2"
														onpaste="return false;" id="monthOfTotalService"
														onkeypress="return restrictEnter(event);"
														cssClass="form-control"></s:textfield>
													<span class="note">Months</span>
												</div>
												<div class="col-sm-4 col-xs-4 padleft0">
													<s:textfield name="dayOfTotalService"
														value="%{dayOfTotalService}" readonly="true"
														label="dayOfTotalService" maxlength="2"
														onpaste="return false;" id="dayOfTotalService"
														onkeypress="return restrictEnter(event);"
														cssClass="form-control"></s:textfield>
													<span class="note">Days </span>
												</div>
											</div>
										</div>
									</div>
									<p class="orgNote">
										<strong>Note:</strong> Use Add/Remove Button to Enter/Delete
										two or more covid duty work details.<br />
									</p>
								</div>
								<s:token />
							</div>
						</div>
					</div>
					<div class="countinuebg" id="saveBtn">
						<div class="container">
							<div class="row">
								<div
									class="col-sm-2 col-sm-offset-10 col-xs-6 col-xs-offset-3 text-right padding0">
									<s:submit value="Save & Continue"
										cssClass="ripple1 btn btn-warning btn-block"
										id="saveUpdateBtn" method="saveCovidDutyCertificateDetails" />
								</div>
							</div>
						</div>
					</div>
				</s:form>
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
	<!-- <div class="clear"></div>
	<div id="overlay2" class="web_dialog_overlay_declr"></div>
	<div class="fullscreen-container" id="dialog2">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onclick="HideDialog3()">&times;</button>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-lg-12 text-center">
							<h4 class="pageTitle" id="dynamicContent2">Consecutive dots
								are not allowed.</h4>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="row">
						<div class="col-sm-6 col-sm-offset-3">
							<input type="button" name="btnCancel" value="OK"
								onclick="HideDialog3()"
								class="ripple1 btn btn-warning btn-block mt10" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div> -->
</body>
</html>