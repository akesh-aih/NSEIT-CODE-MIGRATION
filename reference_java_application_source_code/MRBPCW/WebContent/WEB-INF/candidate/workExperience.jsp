<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
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

	function ShowDialog(modal) {
		$("#overlay").show();
		$("#dialog").fadeIn(300);
		if (modal) {
			$("#overlay").unbind("click");
		} else {
			$("#overlay").click(function(e) {
				HideDialog();
			});
		}
	}

	function HideDialog() {
		$("#overlay").hide();
		$("#dialog").fadeOut(300);
	}

	function okFunction() {
		HideDialog();
	}

	function ShowDialog2(modal) {
		$("#overlay2").show();
		$("#dialog2").fadeIn(300);
		if (modal) {
			$("#overlay2").unbind("click");
		} else {
			$("#overlay2").click(function(e) {
				HideDialog2();
			});
		}
	}

	function HideDialog2() {
		$("#overlay2").hide();
		$("#dialog2").fadeOut(300);
	}

	function okFunction2() {
		HideDialog2();
	}
	
	function showPopUpForMobile(id,innerValue) {
		$(id).val('');
		$("#overlay3").show();
		$("#dialog3").fadeIn(300);
		document.getElementById("dynamicContent").innerHTML = innerValue;
	}

	function HideDialog3() {
		document.getElementById("dynamicContent").innerHTML = "";
		$("#overlay3").hide();
		$("#dialog3").fadeOut(300);
		
	}
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
		$("#workExperienceActionID").attr('action', 'WorkExperienceAction_updateCandidateStage.action');
		$("#workExperienceActionID").submit();
	}
	
	var msgForOrgName = "Please enter only alphabets, numbers and spaces, maximum 100 characters.";
	
	var msgForJobRole = "Please enter only alphabets, numbers and spaces, maximum 300 characters.";
	
	var msgForDesignation = "Please enter only alphabets, numbers and spaces, maximum 50 characters.";
	
	function regexforAlphanumericSpace(value){ 
		var jobRol = /^[a-zA-Z0-9 ]*$/i.test(value);
		return jobRol
	}
	
	var msgForSal = "Please enter only numbers.";
	
	function regexforNumber(value){ 
		var lastSal = /^[0-9]*$/i.test(value);
		return lastSal
	}
	$(document).ready(function() {
		errorField();
	
		var disciplineId = '<s:property value="disciplineId" />';
		var yesNo = $("#yesNo option:selected").text();
		var counter = $("tr[id^='adrow']").last();
		var rowID = counter.attr('id').split('row');
		var rowCount = parseInt(rowID[1]);
		
		<s:iterator value="workExperienceDetailsList" status="stat">
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

		if (Count > 49) {
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
		var startYrRangeFrm = '<s:property value="firstDate"/>';
		var eduDate =  '<s:property value="eduDate"/>';
		var eduDatePlus = new Date(eduDate);
		eduDatePlus.setDate(eduDatePlus.getDate()+1);
		
		<s:iterator value="workExperienceDetailsList" status="stat">
			var startYrRangeTo = "2023";
			$("#fromYear_" + count).datepicker({
				showOn : "button",
				buttonImageOnly : true,
				changeMonth : true,
				changeYear : true,
				yearRange : startYrRangeFrm + ':' + startYrRangeTo,
				buttonImage : "images/cale-img.png",
				buttonImageOnly : true,
				dateFormat : 'dd-M-yy',
				buttonText:"Select Date of Joining",
				minDate:new Date(eduDatePlus),
				maxDate : new Date(),
				numberOfMonths : 1,
				onSelect : function(dateText, instance) {
					var fromYrId = $(this).attr('id').split("_")[1];
					date = $.datepicker.parseDate(instance.settings.dateFormat, dateText, instance.settings);
					//  date.setMonth(date.getMonth() + 6); 
					$("#toYear_" + fromYrId).val("");
					date.setDate(date.getDate() + 1);
					$("#toYear_" + fromYrId).datepicker("option","minDate",	new Date(date));
					var yesnovalue = $("#yesNoA" + fromYrId).val();
					if (yesnovalue == "6" || !(yesnovalue == "7")) {
						$("#toYear_" + fromYrId).hide();
						$("#toYear_" + fromYrId).val('');
						$("#toYear_" + fromYrId).next("img").hide();
						dateCalculate();
					}
				}
			});
	
			var fromDate = new Date($("#fromYear_" + count).val());
			fromDate.setDate(fromDate.getDate() + 1);
			$("#toYear_" + count).datepicker({
				showOn : "button",
				buttonImageOnly : true,
				changeMonth : true,
				changeYear : true,
				yearRange : startYrRangeFrm + ':' + startYrRangeTo,
				buttonImage : "images/cale-img.png",
				buttonImageOnly : true,
				buttonText:"Select Date of Leaving",
				dateFormat : 'dd-M-yy',
				maxDate : new Date('Jun 20,2023'),
				minDate : new Date(fromDate),
				numberOfMonths : 1,
				onSelect : function(selected) {
					var toYrId = $(this).attr('id').split("_")[1];
					var todate = $("#toYear_" + toYrId).datepicker("getDate");
					var todateObj = new Date(todate.getFullYear(), todate.getMonth(), todate.getDate()); // to date datepicker
					var todaydateObj ='31-Mar-2023';
					var diff = dates.compare(todateObj,	todaydateObj);
					dateCalculate();
				}
			});

			count++;
		</s:iterator>

		<s:iterator value="workExperienceDetailsList" status="stat" >
			var count = '<s:property value="yesNoA" />';
			var loop = '<s:property value="%{#stat.count}" />';
			if (count != "7") {
				$("#toYear_" + loop).hide();
				$("#toYear_" + loop).next("img").hide();
			}
		</s:iterator>
	});
	
	function checkCharsForMobOrgName(id) {
		var result = regexforAlphanumericSpace($('#areaArpSop' + id).val());
		if(!result)	{
			showPopUpForMobile('#areaArpSop' + id, msgForOrgName);
		}
	}
	
	function checkCharsForMobJobRole(id) {
		var result = regexforAlphanumericSpace($('#jobRole' + id).val());
		if(!result)	{
			showPopUpForMobile('#jobRole' + id, msgForJobRole);
		}
	}
	
	function checkCharsForMobDesignation(id) {
		var result = regexforAlphanumericSpace($('#organizationOthers' + id).val());
		if(!result)	{
			showPopUpForMobile('#organizationOtherss' + id, msgForDesignation);
		}
	}
	
	
	
	function checkCharsForMobSalaryInLakh(id) {
		var result = regexforNumber($('#maxPayLakh' + id).val());
		if(!result)	{
			showPopUpForMobile('#maxPayLakh' + id, msgForSal);
		}
	}
	
	function checkCharsForMobSalaryInThousand(id) {
		var result = regexforNumber($('#maxPayThousnd' + id).val());
		if(!result)	{
			showPopUpForMobile('#maxPayThousnd' + id, msgForSal);
		}
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

	function dateCalculate() {
		var individualDuration = 0;
		var tableRows = $('#educationformDetailss tr').length;
		tableRows = 50; //  max row count taken for loop iteration  

		var totalExpYears = 0;
		var totalExpMonths = 0;
		var totalExpDays = 0;
		var countExp = 0;
		/* var count =0; */
		var totalOtherExpYears = 0;
		var totalOtherExpMonths = 0;
		var totalOtherExpDays = 0;
		var trID;
		var relevantExpYears = 0;
		var relevantExpMonths = 0;
		var relevantExpDays = 0;

		var countOther = 0;
		var countRelevant = 0;
		var disciplineId = '<s:property value="disciplineId" />';

		var monthList = [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec' ];

		for (var count = 1; count < tableRows; count++) {
			if (count != trID + 1) {
				var considerOtherExperience = false;

				var yesNo2 = $("#yesNoA" + count).val();

				var fromDt = $("#fromYear_" + count).val();
				var toDt = $("#toYear_" + count).val();

				var fromYear = $("#fromYear_" + count).datepicker("getDate");
				var todate = $("#toYear_" + count).datepicker("getDate");

				if (yesNo2 != null && yesNo2 != '' && yesNo2 == '6') { //Yes
					todate = new Date(2023, 05, 20); //'20-June-2023';
				} else if (yesNo2 != null && yesNo2 != '' && yesNo2 == '7' && (todate == 'undefined' || todate == '')) { //No
					todate = null;
				}

				if (fromYear != null && todate != null && fromDt != null && fromDt != 'undefined' && fromDt != '') {

					var d1 = new Date(fromYear.getFullYear(), fromYear.getMonth(), fromYear.getDate());
					var d2 = new Date(todate.getFullYear(), todate.getMonth(), todate.getDate());
					
					//below added for calclating experience only till cutoff date incase DOL is after cutoff date. 
					//incase DOJ after cutoff date, experience will be 0 and not considered.
					var cutoffDate = new Date(2023, 05, 20);
					if (d1 > cutoffDate){
						d1 = cutoffDate;
					}
					if (d2 > cutoffDate){
						d2 = cutoffDate;
					}
					//end
					
					var yearTo = d2.getYear();
					var monthTo = d2.getMonth();
					var dateTo = d2.getDate();

					var yearFrom = d1.getYear();
					var monthFrom = d1.getMonth();
					var dateFrom = d1.getDate();

					var age = {};
					var ageString = "";
					var yearString = "";
					var monthString = "";
					var dayString = "";

					if (yearTo >= yearFrom) {
						var yearAge = yearTo - yearFrom;

						if (monthTo >= monthFrom) {
							var monthAge = monthTo - monthFrom;
						} else {
							yearAge--;
							var monthAge = 12 + monthTo - monthFrom;
						}
						if (dateTo >= dateFrom) {
							var dateAge = dateTo - dateFrom;
						} else {
							monthAge--;
							var dateAge;
							var monthFromValue = monthList[monthFrom];
							if (monthFromValue == 'Jan') {
								var yearFromValue = yearFrom + 1900;
								if (!isNaN(yearFromValue) && yearFromValue % 4 == 0) {
									dateAge = 29 + dateTo - dateFrom;
								} else {
									dateAge = 28 + dateTo - dateFrom;
								}
							} else if (monthFromValue == 'Apr' || monthFromValue == 'Jun' || monthFromValue == 'Sep' || monthFromValue == 'Nov') {
								dateAge = 30 + dateTo - dateFrom;
							} else {
								dateAge = 31 + dateTo - dateFrom;
							}

							if (monthAge < 0) {
								monthAge = 11;
								yearAge--;
							}
						}
						age = {
							years : yearAge,
							months : monthAge,
							days : dateAge
						};
					}
					countExp++;
					totalExpYears = totalExpYears + age.years;
					totalExpMonths = totalExpMonths + age.months;
					totalExpDays = totalExpDays + age.days;
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
					if (!considerOtherExperience) {
						countRelevant++;
						relevantExpYears = relevantExpYears + age.years;
						relevantExpMonths = relevantExpMonths + age.months;
						relevantExpDays = relevantExpDays + age.days;
						if (countRelevant > 1) {
							while (relevantExpDays > 29) {
								relevantExpDays = relevantExpDays - 30;
								relevantExpMonths = relevantExpMonths + 1;
							}

							while (relevantExpMonths > 11) {
								relevantExpMonths = relevantExpMonths - 12;
								relevantExpYears = relevantExpYears + 1;
							}
						}
					}
					$("#workduration_" + count).val(yearAge + ' years ' + monthAge + ' months ' + dateAge + ' days ');
				}	//extra
				else if (fromYear != null && fromDt != null && fromDt != 'undefined' && fromDt != '' && todate == null ) {
					var yearAge = 0;
					var monthAge = 0;
					var dateAge = 0;
					$("#workduration_" + count).val(yearAge + ' years ' + monthAge + ' months ' + dateAge + ' days ');
				}
			}//if
		}//For 
		
		totalExpMonths = totalExpMonths + (totalExpYears*12);
		$("#yearOfExperience").val(totalExpYears);
		$("#monthOfExperience").val(totalExpMonths);
		$("#dayOfExperience").val(totalExpDays);

		//For validation
		$("#yearOfExperienceHidden").val(totalExpYears);
		$("#monthOfExperienceHidden").val(totalExpMonths);
		$("#dayOfExperienceHidden").val(totalExpDays);

		$("#relevantYearOfExperience").val(relevantExpYears);
		$("#relevantMonthOfExperience").val(relevantExpMonths);
		$("#relevantDayOfExperience").val(relevantExpDays);
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

		if (otherId > 49) {
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

					if (id.indexOf("districtVal") != -1 || id.indexOf("policeStation") != -1 
							|| id.indexOf("organizationNatureArpSop") != -1	|| id.indexOf("natureOfDutiesArpSop") != -1) {
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
		$("#yesNoA" + otherId).val("");

		var nextCheckBox = parseInt(trID) + 1;

		$("#check" + nextCheckBox).prop("checked", false);

		addDatepicker(parseInt(trID) + 2);
	}
	var disciplineId = '<s:property value="disciplineId" />';
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

				if (otherId > 51) {
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
		$("#educationformDetailss").find('tr').each(function(index, value) {
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
		var startYrRangeFrm = '<s:property value="firstDate"/>';
		var startYrRangeTo = "2023";
		var eduDate =  '<s:property value="eduDate"/>';
		var eduDatePlus = new Date(eduDate);
		eduDatePlus.setDate(eduDatePlus.getDate()+1);
		
		$(fromYearDP).datepicker({
			showOn : "button",
			buttonImageOnly : true,
			changeMonth : true,
			changeYear : true,
			yearRange : startYrRangeFrm + ':' + startYrRangeTo,
			buttonImage : "images/cale-img.png",
			buttonImageOnly : true,
			dateFormat : 'dd-M-yy',
			minDate:new Date(eduDatePlus),
			maxDate : new Date(),
			buttonText:"Select Date of Joining",
			numberOfMonths : 1,
			onSelect : function(dateText, instance) {

				date = $.datepicker.parseDate(
						instance.settings.dateFormat, dateText,
						instance.settings);
				//date.setMonth(date.getMonth() + 6);
				//  $(toYearDP).datepicker("setDate", date);
				//  date.setMonth(date.getMonth() + 6); 
				$(toYearDP).val("");
				date.setDate(date.getDate() + 1);
				$(toYearDP).datepicker("option", "minDate",
						new Date(date));
				var yesnovalue = $("#yesNoA" + rowId).val();
				if (yesnovalue == "6" || !(yesnovalue == "7")) {
					$("#toYear_" + rowId).hide();
					$("#toYear_" + rowId).val('');
					$("#toYear_" + rowId).next("img").hide();
				}
			}
		});

		$(toYearDP).datepicker(
				{
					showOn : "button",
					buttonImageOnly : true,
					changeMonth : true,
					changeYear : true,
					yearRange : startYrRangeFrm + ':' + startYrRangeTo,
					buttonImage : "images/cale-img.png",
					buttonImageOnly : true,
					dateFormat : 'dd-M-yy',
					maxDate : new Date('Jun 20,2023'),
					buttonText:"Select Date of Leaving",
					numberOfMonths : 1,
					onSelect : function(selected) {
						var toYrId = $(this).attr('id').split("_")[1];
						var todate = $("#toYear_" + toYrId).datepicker(
								"getDate");
						var todateObj = new Date(todate.getFullYear(), todate
								.getMonth(), todate.getDate()); // to date datepicker
						var todaydateObj = new Date(); //current date
						var diff = dates.compare(todateObj, todaydateObj);

						if (diff >= 0) {
							$(fromYearDP).datepicker("option", "maxDate", 0);
						} else {
							$(fromYearDP).datepicker("option", "maxDate",
									selected);
						}

					}
				});
		var yesnovalue = $("#yesNoA" + rowId).val();
		if (yesnovalue == "6" || !(yesnovalue == "7")) {
			$("#toYear_" + rowId).hide();
			$("#toYear_" + rowId).val('');
			$("#toYear_" + rowId).next("img").hide();
		}
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

	function alphaNumericWithSpaceComma(event) {
		return /[A-Za-z0-9, ]/g.test(event.key);
	}

	function alphaNumericWithSpecialChar(event) {
		return /[A-Za-z0-9&(),\. ]/g.test(event.key);
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
	
	function yesorNo1(e) {
		if (typeof e !== 'undefined') {
			var xyz = e.id;
			var rowcount = xyz.substring(6, xyz.length);
			var count = e.value;
			//var yesorNo = $('#yesNo2+count').val();
			if (count == "7" && (count != null || count != "")) {
				$("#toYear_" + rowcount).val('');
				$("#toYear_" + rowcount).show();
				$("#toYear_" + rowcount).next("img").show();

			} else {
				$("#toYear_" + rowcount).hide();
				$("#toYear_" + rowcount).val('');
				$("#toYear_" + rowcount).next("img").hide();
			}
			dateCalculate();
		}
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
			Work Experience
			<span class="userid_txt"> <s:if
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
				<s:form action="WorkExperienceAction_saveWorkExperienceDetails" name="workExperienceAction"
					id="workExperienceActionID">
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
								id="successMessage">Work Experience Details saved
								successfully.</div>
							<div class="clear">&nbsp;</div>
						</s:if>
						<s:hidden name="menuKey" value="%{#session['menuKey']}"
							id="menuKey"></s:hidden>
						<s:hidden name="disciplineId" id="disciplineIdHidden" />
						<s:hidden name="yearOfExperienceHidden"
							id="yearOfExperienceHidden" />
						<s:hidden name="monthOfExperienceHidden"
							id="monthOfExperienceHidden" />
						<s:hidden name="dayOfExperienceHidden" id="dayOfExperienceHidden" />

						<s:hidden name="yearOfExperienceOtherHidden"
							id="yearOfExperienceOtherHidden" />
						<s:hidden name="monthOfExperienceOtherHidden"
							id="monthOfExperienceOtherHidden" />
						<s:hidden name="dayOfExperienceOtherHidden"
							id="dayOfExperienceOtherHidden" />

						<s:hidden name="relevantYearOfExperience"
							id="relevantYearOfExperience" />
						<s:hidden name="relevantMonthOfExperience"
							id="relevantMonthOfExperience" />
						<s:hidden name="relevantDayOfExperience"
							id="relevantDayOfExperience" />

						<div class="accordions">
							<div id="CandidateDiv">
								<input type="hidden" name="isDataFound"
									value='<s:property value="dataFound"/>' />
								<%-- <s:if test="%{disciplineId == 2}">
									<!-- MSc Economics -->
									<div class="row">
										<div style="text-align: center;">
											<label class="font16">Work Experience is not required
												for selected programme. Kindly click the 'Continue' button
												and move to next page.</label>
										</div>
									</div>
								</s:if>
								<s:elseif test="%{disciplineId == 1}"> --%>
								<!-- PhD -->
									<h2 title="Work Experience">Work Experience</h2>
									<div class="accordion">
										<div class="table-responsive">
											<table
												class="table table-striped table-bordered personsl-dtl"
												id="educationformDetailss" width="110%">
												<tr>
													<th width="3%"></th>
													<th width="8%">Organisation Name<span class="manadetory-fields">*</span></th>
													<th width="8%">Designation<span class="manadetory-fields">*</span></th>
													<th width="8%">Roles and Responsibility<span class="manadetory-fields">*</span>
													</th>
													<th width="10%">Date of Joining<span class="manadetory-fields">*</span>
													</th>
													<th width="7%">Is this your current organization<span class="manadetory-fields">*</span>
													</th>
													<th width="10%">Date of Leaving<span class="manadetory-fields">*</span>
													</th>
													<th width="15%">Duration of Experience<span class="manadetory-fields">*</span>
													</th>
													<th width="11%" colspan="2">Annual CTC<span class="manadetory-fields">*</span>
													</th>
												</tr>
												<s:iterator value="workExperienceDetailsList" status="stat"
													var="currentObject">
													<tr class="adrow<s:property value='%{#stat.index}'/>"
														id="adrow<s:property value='%{#stat.index}'/>">
														<td width="160"><input type="checkbox"
															name="check<s:property value='%{#stat.index}'/>"
															id="check<s:property value='%{#stat.index}'/>"
															class="fl-left"></td>
														<td><s:textfield
																name="workExperienceDetailsList[%{#stat.index}].area"
																value="%{area}" label="area" maxlength="100"
																onpaste="return false;" autocomplete="off"
																class="form-control" id="areaArpSop%{#stat.count}"																
																onkeypress="return alphaNumericWithSpace(event);"
																onchange="checkCharsForMobOrgName(%{#stat.count})"
																onmouseenter="isOndropContainsSpecialCharRestrict(this,this.id)"></s:textfield></td>
														<td width="200"><s:textfield
																	name="workExperienceDetailsList[%{#stat.index}].organizationOthers"
																	value="%{organizationOthers}"
																	label="organizationOthers" maxlength="50"
																	onpaste="return false;" autocomplete="off"
																	class="form-control"
																	onkeypress="return alphaNumericWithSpace(event);"
																	onmouseenter="isOndropContainsSpecialCharRestrict(this,this.id)"
																	id="organizationOtherss%{#stat.count}"></s:textfield></td>
														<td width="170"><s:textfield
																name="workExperienceDetailsList[%{#stat.index}].jobRole"
																value="%{jobRole}" label="jobRole" maxlength="300"
																onpaste="return false;" autocomplete="off"
																class="form-control"
																onkeypress="return alphaNumericWithSpace(event);"
																onchange="checkCharsForMobJobRole(%{#stat.count})"
																onmouseenter="isOndropContainsSpecialCharRestrict(this,this.id)"
																id="jobRole%{#stat.count}"></s:textfield></td>
														<td width="220" id="fromYearTDArpSop" class="dateInput">
															<s:textfield id="fromYear_%{#stat.count}"
																name="workExperienceDetailsList[%{#stat.index}].fromYear"
																readonly="true"
																onkeypress="return restrictEnter(event);"
																onblur="return getDiff();"
																cssClass="form-control disableEnroll"
																cssStyle="padding:4px">
															</s:textfield>
														</td>
														<td width="160" class=yesNoA><s:select headerKey=""
																headerValue="Select" list="yesNoMap"
																name="workExperienceDetailsList[%{#stat.index}].yesNoA"
																cssClass="form-control" id="yesNoA%{#stat.count}"
																value="%{yesNoA}" onchange="yesorNo1(this)" /></td>
														<td width="220" class="dateInput"><s:textfield
																id="toYear_%{#stat.count}"
																name="workExperienceDetailsList[%{#stat.index}].toYear"
																onkeypress="return restrictEnter(event);"
																onblur="return getDiff();" readonly="true"
																cssClass="form-control disableEnroll"
																cssStyle="padding:4px">
															</s:textfield></td>
														<td><s:textfield
																name="workExperienceDetailsList[%{#stat.index}].workduration"
																value="%{workduration}" readonly="true"
																label="workduration" maxlength="50"
																onpaste="return false;" id="workduration_%{#stat.count}"
																onkeypress="return restrictEnter(event);"
																class="form-control"></s:textfield></td>
														<td width="5" align="center"><s:textfield
																name="workExperienceDetailsList[%{#stat.index}].maxPayLakh"
																value="%{maxPayLakh}" label="maxPayLakh" maxlength="2"
																onpaste="return false;" autocomplete="off"
																class="form-control"
																onkeypress="return isNumber(event);"
																onmouseenter="isOndropContainsSpecialCharRestrict(this,this.id)"
																id="maxPayLakh%{#stat.count}" placeholder="Lakh">
															</s:textfield></td>
														<td width="7"><s:textfield
																name="workExperienceDetailsList[%{#stat.index}].maxPayThousnd"
																value="%{maxPayThousnd}" label="maxPayThousnd"
																maxlength="2" onpaste="return false;" autocomplete="off"
																class="form-control"
																onkeypress="return isNumber(event);"
																onmouseenter="isOndropContainsSpecialCharRestrict(this,this.id)"
																id="maxPayThousnd%{#stat.count}" placeholder="Thousand">
															</s:textfield></td>
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
										<div class="row" id="workExcalc">
											<hr>
											<div class="col-sm-3">
												<div class="form-group">
													<label>Total Work Experience as on 20-June-2023</label>
												</div>
											</div>
											<div class="col-sm-3">
												<div class="form-group">
													<div class="col-sm-4 col-xs-4 padleft0" style="display:none">
														<s:textfield name="yearOfExperience"
															value="%{yearOfExperience}" readonly="true"
															label="yearOfExperience" maxlength="2"
															onpaste="return false;" id="yearOfExperience"
															onkeypress="return restrictEnter(event);"
															cssClass="form-control"></s:textfield>
														<span class="note">Years</span>
													</div>
													<div class="col-sm-4 col-xs-4 padleft0">
														<s:textfield name="monthOfExperience"
															value="%{monthOfExperience}" readonly="true"
															label="monthOfExperience" maxlength="2"
															onpaste="return false;" id="monthOfExperience"
															onkeypress="return restrictEnter(event);"
															cssClass="form-control"></s:textfield>
														<span class="note">Months</span>
													</div>
													<div class="col-sm-4 col-xs-4 padleft0">
														<s:textfield name="dayOfExperience"
															value="%{dayOfExperience}" readonly="true"
															label="dayOfExperience" maxlength="2"
															onpaste="return false;" id="dayOfExperience"
															onkeypress="return restrictEnter(event);"
															cssClass="form-control"></s:textfield>
														<span class="note">Days </span>
													</div>
												</div>
											</div>
										</div>
										<%-- <p class="orgNote">
											<strong>Note:</strong> Duration of Experience will be
											calculated up to 31-March-2022 only<br />
										</p> --%>
										<%-- 	</s:if> --%>
									</div>
								<%-- </s:elseif> --%>
								<s:token />
							</div>
						</div>
					</div>
					<div class="countinuebg" id="saveBtn">
						<div class="container">
							<div class="row">
								<div
									class="col-sm-2 col-sm-offset-10 col-xs-6 col-xs-offset-3 text-right padding0">
									<%-- <s:if test="%{disciplineId == 2}">
										<s:submit value="Continue"
											cssClass="ripple1 btn btn-warning btn-block"
											id="saveUpdateBtn" method="saveWorkExperienceDetails" />
									</s:if>
									<s:elseif test="%{disciplineId == 1}"> --%>
									<s:submit value="Save & Continue"
										cssClass="ripple1 btn btn-warning btn-block"
										id="saveUpdateBtn" method="saveWorkExperienceDetails" />
									<%-- </s:elseif> --%>
								</div>
							</div>
						</div>
					</div>
				</s:form>
			</div>
		</div>
	</div>

	<div class="fullscreen-container" id="dialog">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onClick="okFunction();">X</button>
					<h2 class="modal-title">Note</h2>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-lg-12">
							<div class="msgDiv">
								<ul>
									<li><input type="checkbox" name="declaration"
										id="declaration" style="display: none;" /> You are not
										eligible to apply for the post</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="row">
						<div class="col-sm-6 col-sm-offset-3">
							<input type="button" name="accept" id="accept" value="Ok"
								class="ripple1 btn btn-warning btn-block"
								onClick="okFunction();" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="overlay1" class="web_dialog_overlay_declr"></div>
	<div class="fullscreen-container" id="dialog1">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onClick="okFunction1();">X</button>
					<h2 class="modal-title">Work Experience Confirmation</h2>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-lg-12">
							<div class="msgDiv">
								<ul>
									<li><input type="checkbox" name="declaration"
										id="declaration" style="display: none;" /> Only Full Time
										experience is considered, hence enter only full time
										experience</li>

								</ul>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="row">
						<div class="col-sm-4 col-sm-offset-4 col-xs-6 col-xs-offset-3">
							<input type="button" name="accept" id="accept" value="Ok"
								class="ripple1 btn btn-warning btn-block"
								onClick="okFunction1();" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="overlay2" class="web_dialog_overlay_declr"></div>
	<div class="fullscreen-container" id="dialog2">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onClick="okFunction2();">X</button>
					<h2 class="modal-title">Work Experience Confirmation</h2>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-lg-12">
							<div class="msgDiv">
								<ul>
									<li><input type="checkbox" name="declaration"
										id="declaration" style="display: none;" /> Work Experience
										only as Permanent employee is considered, hence enter only
										relevant experience</li>

								</ul>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="row">
						<div class="col-sm-4 col-sm-offset-4 col-xs-6 col-xs-offset-3">
							<input type="button" name="accept" id="accept" value="Ok"
								class="ripple1 btn btn-warning btn-block"
								onClick="okFunction2();" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clear"></div>
	<div id="overlay3" class="web_dialog_overlay_declr"></div>
	<div class="fullscreen-container" id="dialog3">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onclick="HideDialog3()">&times;</button>
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
								onclick="HideDialog3()"
								class="ripple1 btn btn-default btn-block mt10" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>