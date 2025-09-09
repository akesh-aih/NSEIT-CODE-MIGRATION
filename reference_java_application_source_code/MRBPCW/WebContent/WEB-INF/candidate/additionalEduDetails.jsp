<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<script type="text/javascript" src="js/moment.min.js"></script>
<link rel="stylesheet" href="../css/jquery-ui.css">
<link rel="stylesheet" href="../css/bootstrap.min.css">
<link rel="stylesheet" href="../css/common.css">
<link rel="stylesheet" href="../css/style.css">
<script src="../js/jquery-1.12.4.js"></script>
<script src="../js/jquery-ui.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	showGeCerti();
	showStateUni();
	showStateUniNET();
	ShowPgEquivalent();
	ShowPgEquivalentQ();
	ShowPhdDetails();
	showAppropriate();
	showEduQst1Other();
	showOtherSubject();
<s:iterator value="errorField">
	
	$("input[name='<s:property />']").addClass('red-border');
	$("select[name='<s:property />']").addClass('red-border');
	$("#<s:property />").addClass('red-border');			
	</s:iterator>
	
	$('#dateOfReg').datepicker('destroy');
	$("#dateOfReg").datepicker({
		showOn: "button",
		changeMonth: true,
		changeYear: true,
		yearRange: range,
		minDate: new Date("07/01/1962"),
		buttonImageOnly: true,
		buttonImage: "images/cale-img.png",
		dateFormat: 'dd-M-yy',
		onSelect:function(dateText, inst)
		{
			ShowPhdDetails();
			$("#dateOfviva").val("");
		}
	});
	
	$('#dateOfviva').datepicker('destroy');
	$("#dateOfviva").datepicker({
		showOn: "button",
		changeMonth: true,
		changeYear: true,
		yearRange: range,
		minDate: new Date("07/01/1962"),
		buttonImageOnly: true,
		buttonImage: "images/cale-img.png",
		dateFormat: 'dd-M-yy',
		onSelect:function(dateText, inst)
		{
			//calculateAge();   
		}
	});
	$('#yearOfPass').datepicker('destroy');
   	$("#yearOfPass").datepicker({
    	maxDate:0,
    	showOn: "button",
		changeYear: true,
		showButtonPanel: true,
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		dateFormat: 'yy',
		yearRange: '1980:2019',
	
	}).focus(function() {
		var thisCalendar = $(this);
		  $(".ui-datepicker-month").hide();
		$('.ui-datepicker-calendar').detach();
		$('.ui-datepicker-close').click(function() {
		var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
		thisCalendar.datepicker('setDate', new Date(year, 1));

		});
	});
	
	
	$('#yearOfPassNET').datepicker('destroy');
	$("#yearOfPassNET").datepicker({
		maxDate:0,
    	showOn: "button",
		changeYear: true,
		showButtonPanel: true,
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		dateFormat: 'yy',
		yearRange: '1980:2019',
	
	}).focus(function() {
		var thisCalendar = $(this);
		  $(".ui-datepicker-month").hide();
		$('.ui-datepicker-calendar').detach();
		$('.ui-datepicker-close').click(function() {
		var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
		thisCalendar.datepicker('setDate', new Date(year, 1));

		});
	});
	
 	$(isPhdChecked).click(function() {
	    if ($('#isPhdChecked').is(':checked')) {
	    	showAppropriate();
	    	ShowPgEquivalent();
	    } else{
	    	$("#isPhdChecked").prop("checked", false);
	    	showAppropriate();
	    	ShowPgEquivalent();
	    	 clearPHD();
	    }
	  });
 	$(isSletChecked).click(function() {
	    if ($('#isSletChecked').is(':checked')) {
	    	showAppropriate();
	    	ShowPgEquivalent();
	    } else{
	    	$("#isSletChecked").prop("checked", false);
	    	showAppropriate();
	    	ShowPgEquivalent();
	    	clearSLET();
	    }
	  }); $(isNetChecked).click(function() {
		    if ($('#isNetChecked').is(':checked')) {
		    	showAppropriate();
		    	ShowPgEquivalent();
		    } else{
		    	$("#isNetChecked").prop("checked", false);
		    	showAppropriate();
		    	ShowPgEquivalent();
		    	clearNET();
		    }
		  }); 
	  $("#perdOfStudyFrm").datepicker({
			maxDate:0,
	    	showOn: "button",
			changeYear: true,
			showButtonPanel: true,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			dateFormat: 'yy',
			yearRange: '1980:2019',
		
		}).focus(function() {
			var thisCalendar = $(this);
			  $(".ui-datepicker-month").hide();
			$('.ui-datepicker-calendar').detach();
			$('.ui-datepicker-close').click(function() {
			var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
			thisCalendar.datepicker('setDate', new Date(year, 1));

			});
		});
	    
	    $("#perdOfStudyTo").datepicker({
	    	maxDate:0,
	    	showOn: "button",
			changeYear: true,
			showButtonPanel: true,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			dateFormat: 'yy',
			yearRange: '1980:2019',
		
		}).focus(function() {
			var thisCalendar = $(this);
			  $(".ui-datepicker-month").hide();
			$('.ui-datepicker-calendar').detach();
			$('.ui-datepicker-close').click(function() {
			var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
			thisCalendar.datepicker('setDate', new Date(year, 1));

			});
		});
});
//End of ready
function lettersOnly(e) 
{
            var charCode = e.charCode? e.charCode : e.keyCode;
			//alert(charCode);
            if ((charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123) || charCode == 8 || charCode == 32)

                return true;
            else
                return false;
}

function alphabetsWithSpacenChars(e)
{
	var unicode=e.charCode? e.charCode : e.keyCode;
//	alert("unicode"+unicode);
	if((unicode < 97 || unicode > 122 ) &&  (unicode < 48 || unicode > 57) && (unicode < 65 || unicode > 90) && unicode != 32 && unicode != 38 && unicode != 8 && unicode != 9 && unicode != 46  && unicode != 39 && unicode != 44 && unicode != 45 && unicode != 47 && unicode != 92 && unicode != 13 )
		return false;
}
function alphanumericCollege(e)
{
	var unicode=e.charCode? e.charCode : e.keyCode;
	//alert(unicode);
	if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && (unicode<48 || unicode>57) && unicode != 8 && unicode != 9 && unicode != 32 && unicode != 44 && unicode != 46 && unicode != 47 && unicode != 92 && unicode != 38 )
		return false;
}

function onlyAlphabets(evt) {
	 var charCode = (evt.which) ? evt.which : window.event.keyCode;

	    if (charCode <= 13) {
	        return true;
	    }
	    else {
	        var keyChar = String.fromCharCode(charCode);
	        var re = /^[a-zA-Z ]+$/
	        return re.test(keyChar);
	    }
}
function showOtherSubject(){
	var subjectapplied = $("#mainSubjectApplied").val();
	if(subjectapplied == "138"){
		$("#showOtherSubject").show();
	}else{
		$("#showOtherSubject").hide();
		$("#AdditionSubjectName").val("");
		
	}
}
function showPopUp1()
{
	var pgequivVal = $("#pgequivVal").val(); 
		if(pgequivVal == "228") {
			//console.log("In UG Show Alert Non-AICTE Recognized ::::::: ");
			//$("#pgequivVal").val('');
			$("#overlay10").show();
			$("#dialog10").fadeIn(300);	
		}
		
}//end of showpopup
function showpopup2(){
	var sletnetVal = $("#sletnetVal").val();
	if(sletnetVal == "228"){

		//console.log("In UG Show Alert Non-AICTE Recognized ::::::: ");
		//$("#sletnetVal").val('');
		$("#overlay10").show();
		$("#dialog10").fadeIn(300);	
	}
}
function showpopup3()
{
	var mediumnetVal = $("#mediumnetVal").val();
	if(mediumnetVal == "228"){

		//console.log("In UG Show Alert Non-AICTE Recognized ::::::: ");
		//$("#mediumnetVal").val('');
		$("#overlay10").show();
		$("#dialog10").fadeIn(300);	
	}
}
function showpopup4()
{
	var phdTNVal = $("#phdTNVal").val();
	if(phdTNVal == "228"){

		//console.log("In UG Show Alert Non-AICTE Recognized ::::::: ");
		//$("#phdTNVal").val('');
		$("#overlay10").show();
		$("#dialog10").fadeIn(300);	
	}
	}

function showEduQst1Other(){
	var eduQst1 = $("#eduQst1").val();
	var eduQst2 = $("#eduQst2").val();
	var eduQst3 = $("#eduQst3").val();
	if(eduQst1 == "227"){
		$("#EduQst1otherdiv").show(); 
	}
	else
		{
		$("#EduQst1otherdiv").hide(); 
		$("#EduQst1other").val("");
		}
	if(eduQst2 == "227"){
		$("#EduQst2otherdiv").show(); 
		$("#EduQst2univdiv").show();
		$("#perdOfStudyFrmdiv").show();
		$("#perdOfStudyTodiv").show();
	}
	else
		{
		$("#EduQst2otherdiv").hide(); 
		$("#EduQst2univdiv").hide();
		$("#perdOfStudyFrmdiv").hide();
		$("#perdOfStudyTodiv").hide();
		$("#EduQst2other").val("");
		$("#EduQst2univ").val("");
		$("#perdOfStudyFrm").val("");
		$("#perdOfStudyTo").val("");
		}
	if(eduQst3 == "227"){
		$("#EduQst3otherdiv").show(); 
	}
	else
		{
		$("#EduQst3otherdiv").hide(); 
		$("#EduQst3other").val("");
		}
	
}


function showAppropriate(){
	var OptionSelected1 = $('input[name=isPhdChecked]:checked').val();
	var OptionSelected2 = $( 'input[name=isSletChecked]:checked' ).val();
	var OptionSelected3 = $( 'input[name=isNetChecked]:checked' ).val();
	if(OptionSelected1 == "true" || OptionSelected2 == "true" || OptionSelected3 == "true" ){
		if(OptionSelected1 == "true"){
			$("#PhdDiv").show();
			$("#PSTMDiv").show();
			$("#SletDiv").hide();
			 $("#NetDiv").hide();
			 //$("#mediumSLETNETDIV").hide();
			// $("#mediumNETDIV").hide();
			// $("#PSTMPHD").show();
			/*  clearSLET();
			 clearNET(); */
		}
		if(OptionSelected2 == "true"){
			$("#PhdDiv").hide();
			$("#PSTMDiv").show();
			$("#SletDiv").show();
			//$("#mediumSLETNETDIV").show();
			 $("#NetDiv").hide();
			// $("#PSTMPHD").hide();
			/*  clearPHD();
			 clearNET(); */
		}
		if(OptionSelected3 == "true"){
			$("#PhdDiv").hide();
			$("#PSTMDiv").show();
			$("#SletDiv").hide();
			 $("#NetDiv").show();
			// $("#mediumNETDIV").show();
			 
			// $("#PSTMPHD").hide();
			/*  clearPHD();
			 clearSLET(); */
		}
		 if(OptionSelected1 == "true" && OptionSelected2 == "true" ){
			$("#PhdDiv").show();
			$("#PSTMDiv").show();
			$("#SletDiv").show();
			//$("#mediumSLETNETDIV").show();
			 $("#NetDiv").hide();
			 //$("#mediumNETDIV").hide();
			// $("#PSTMPHD").show();
			// clearNET();
		}
		 if(OptionSelected1 == "true" && OptionSelected3 == "true"){
			$("#PhdDiv").show();
			$("#PSTMDiv").show();
			$("#SletDiv").hide();
			 $("#NetDiv").show();
			// $("#PSTMPHD").show();
			 //clearSLET();
		}
		 if(OptionSelected2 == "true" && OptionSelected3 == "true"){
			$("#PhdDiv").hide();
			$("#PSTMDiv").show();
			$("#SletDiv").show();
			 $("#NetDiv").show();
			 //$("#mediumSLETNETDIV").show();
			// $("#mediumNETDIV").show();
			// $("#PSTMPHD").hide();
			// clearPHD();
		}
		 if(OptionSelected1 == "true" && OptionSelected2 == "true" && OptionSelected3 == "true"){
			$("#PhdDiv").show();
			$("#PSTMDiv").show();
			$("#SletDiv").show();
			 $("#NetDiv").show();
			 //$("#mediumSLETNETDIV").show();
			// $("#mediumNETDIV").show();
			// $("#PSTMPHD").show();
		}
	}else{
		$("#PhdDiv").hide();
		$("#PSTMDiv").show();
		$("#SletDiv").hide();
		 $("#NetDiv").hide();
		// $("#mediumSLETNETDIV").hide();
		// $("#mediumNETDIV").hide();
		// $("#PSTMPHD").hide();
		 clearPHD();
		 clearSLET();
		 clearNET();
	}
}
	
	function clearPHD(){
		$("#phdStatus").val("");
		$("#geCertificate").val("");
		$("#phdMode").val("");
		$("#phdQst1").val("");
		$("#phdQst2").val("");
		$("#phdQst3").val("");
		$("#phdQst4").val("");
		$("#phdQst5").val("");
		$("#dateOfReg").val("");
		$("#dateOfviva").val("");
		$("#AdditionalUniversityName").val("");
		$("#phdTNVal").val("");
		$("#mainSubjectApplied").val("");
	}
	function clearSLET(){
		$("#registrationNumber").val("");
		//$("#subjectName").val("");
		$("#agencyVal").val("");
		$("#stateUniName").val("");
		$("#yearOfPass").val("");
		$("#sletnetVal").val("");
		
	}
	function clearNET(){
		$("#registrationNumberNET").val("");
		//$("#subjectNameNET").val("");
		$("#agencyValNET").val("");
		$("#stateUniNameNET").val("");
		$("#yearOfPassNET").val("");
		$("#mediumnetVal").val("");
	}


//sets date of phd reg and question related to it
 function ShowPhdDetails(){

	var d1 = new Date("07/11/2009");
	var d2 = $("#dateOfReg").val();
	var d3 = new Date(d2);
	if(d1>d3)
		{
		$("#phdQst1div").show();
		$("#phdQst2div").show();
		$("#phdQst3div").show();
		$("#phdQst4div").show();
		$("#phdQst5div").show();
		}
	else {
		$("#phdQst1div").hide();
		$("#phdQst2div").hide();
		$("#phdQst3div").hide();
		$("#phdQst4div").hide();
		$("#phdQst5div").hide();
		$("#phdQst1").val("");
		$("#phdQst2").val("");
		$("#phdQst3").val("");
		$("#phdQst4").val("");
		$("#phdQst5").val("");
	}
	// add a day
	d3.setDate(d3.getDate() + 1);
	$("#dateOfviva").datepicker("option","minDate", new Date(d3));
	
} 

function ShowPgEquivalentQ(){
	var PGEquivalent = $("#pstmSelected").val();
	if(PGEquivalent == "227"){
		$("#PGEquivalentDiv").show();
	}else{
		$("#PGEquivalentDiv").hide();
		$("#pgequivVal").val("");
	}
}


	function ShowPgEquivalent() {
	
		var PGEquivalent = $("#pstmSelected").val();
		var phdsletQstn = $('#isPhdChecked').is(":checked");
		var sletchecked = $('#isSletChecked').is(":checked");
		var netchecked = $('#isNetChecked').is(":checked");

		if (phdsletQstn == true && sletchecked == false && netchecked == false) {
			if (PGEquivalent == "227") {
				$("#PSTMPHD").show();
				$("#mediumSLETNETDIV").hide();
				$("#sletnetVal").val("");
				$("#mediumNETDIV").hide();
				$("#mediumnetVal").val("");
			} else {
				$("#PSTMPHD").hide();
				$("#phdTNVal").val("");
				$("#mediumSLETNETDIV").hide();
				$("#sletnetVal").val("");
				$("#mediumNETDIV").hide();
				$("#mediumnetVal").val("");
			}
		}
		if (phdsletQstn == true && sletchecked == true && netchecked == false) {
			if (PGEquivalent == "227") {
				$("#PSTMPHD").show();
				$("#mediumSLETNETDIV").show();
				$("#mediumNETDIV").hide();
				$("#mediumnetVal").val("");
			} else {
				$("#PSTMPHD").hide();
				$("#phdTNVal").val("");
				$("#mediumSLETNETDIV").hide();
				$("#sletnetVal").val("");
				$("#mediumNETDIV").hide();
				$("#mediumnetVal").val("");
			}
		}
		
		if (phdsletQstn == true && sletchecked == false && netchecked == true) {
			if (PGEquivalent == "227") {
				$("#PSTMPHD").show();
				$("#mediumSLETNETDIV").hide();
				$("#mediumNETDIV").show();
				$("#sletnetVal").val("");
			} else {
				$("#PSTMPHD").hide();
				$("#phdTNVal").val("");
				$("#mediumSLETNETDIV").hide();
				$("#sletnetVal").val("");
				$("#mediumNETDIV").hide();
				$("#mediumnetVal").val("");
			}
		}
		
		if (phdsletQstn == false && sletchecked == true && netchecked == true) {
			if (PGEquivalent == "227") {
				$("#mediumSLETNETDIV").show();
				$("#PSTMPHD").hide();
				$("#phdTNVal").val("");
				$("#mediumNETDIV").show();
			} else {
				$("#PSTMPHD").hide();
				$("#phdTNVal").val("");
				$("#mediumSLETNETDIV").hide();
				$("#sletnetVal").val("");
				$("#mediumNETDIV").hide();
				$("#mediumnetVal").val("");
			}

		}
		if (phdsletQstn == false && netchecked == false && sletchecked == false) {
			if (PGEquivalent == "227") {
				$("#mediumSLETNETDIV").hide();
				$("#sletnetVal").val("");
				$("#PSTMPHD").hide();
				$("#phdTNVal").val("");
				$("#mediumNETDIV").hide();
				$("#mediumnetVal").val("");
			}
			if (PGEquivalent == "228" || PGEquivalent == "") {
				$("#PSTMPHD").hide();
				$("#phdTNVal").val("");
				$("#mediumSLETNETDIV").hide();
				$("#mediumNETDIV").hide();
				$("#mediumnetVal").val("");
				$("#sletnetVal").val("");
			}

		}
		if (phdsletQstn == true && netchecked == true && sletchecked == true) {
			if (PGEquivalent == "227") {
				$("#mediumSLETNETDIV").show();
				$("#PSTMPHD").show();
				$("#mediumNETDIV").show();
			} else {
				$("#mediumSLETNETDIV").hide();
				$("#sletnetVal").val("");
				$("#PSTMPHD").hide();
				$("#phdTNVal").val("");
				$("#mediumNETDIV").hide();
				$("#mediumnetVal").val("");
			}

		}
		if (phdsletQstn == false && netchecked == false && sletchecked == true) {
			if (PGEquivalent == "227") {
				$("#mediumSLETNETDIV").show();
				$("#PSTMPHD").hide();
				$("#phdTNVal").val("");
				$("#mediumNETDIV").hide();
				$("#mediumnetVal").val("");
			} else {
				$("#mediumSLETNETDIV").hide();
				$("#sletnetVal").val("");
				$("#PSTMPHD").hide();
				$("#phdTNVal").val("");
				$("#mediumNETDIV").hide();
				$("#mediumnetVal").val("");
			
			}
		}
		if (phdsletQstn == false && sletchecked == false && netchecked == true) {
			if (PGEquivalent == "227") {
				$("#mediumSLETNETDIV").hide();
				$("#PSTMPHD").hide();
				$("#phdTNVal").val("");
				$("#mediumNETDIV").show();
				$("#sletnetVal").val("");
			}else{
				$("#mediumSLETNETDIV").hide();
				$("#sletnetVal").val("");
				$("#PSTMPHD").hide();
				$("#phdTNVal").val("");
				$("#mediumNETDIV").hide();
				$("#mediumnetVal").val("");
			}
			
		}
		

	}
	//Showing option when State university is selected
	function showStateUni() {
		var stateUni = $("#agencyVal").val();
		if (stateUni == 400) {
			$("#StateUni").show();
		} else {
			$("#stateUniName").val("");
			$("#StateUni").hide();
		}
	}
	function showStateUniNET() {
		var stateUni = $("#agencyValNET").val();
		if (stateUni == 368) {
			$("#StateUniNET").show();
			$("#StateOthersNameNET").val("");
			$("#StateOthersNET").hide();
		} 
		else if (stateUni == 399){
			$("#StateOthersNET").show();
			$("#stateUniNameNET").val("");
			$("#StateUniNET").hide();
		}
		else {
			$("#stateUniNameNET").val("");
			$("#StateUniNET").hide();
			$("#stateOthersNameNET").val("");
			$("#StateOthersNET").hide();
		}
	}
	//showing PHD status
	function showGeCerti() {
		var certNo = $("#phdStatus").val();
		if (certNo == 355) {
			$("#GECERTIDIV").show();
		} else {
			$("#geCertificate").val("");
			$("#GECERTIDIV").hide();
		}
	}
	//show phd final question msg
	function ShowPhdPopUp() {
		var optionSelected = $("#phdQst5").val();
		if (optionSelected == 227 || optionSelected == 228) {
			ShowDialog5();
		}
	}
	function ShowDialog5(modal) {
		$("#overlay5").show();
		$("#dialog5").fadeIn(300);

		if (modal) {
			$("#overlay5").unbind("click");
		} else {
			$("#overlay5").click(function(e) {
				HideDialog5();
			});
		}
	}

	function okFunction5() {
		HideDialog5();
	}

	function HideDialog5() {
		$("#overlay5").hide();
		$("#dialog5").fadeOut(300);
	}
	//showing Certificate selection popup
	function ShowCertPopUp() {
		var gecertNo = $("#geCertificate").val();
		if (gecertNo == 228) {
			ShowDialog();
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
		$("#overlay10").hide();
		$("#dialog").fadeOut(300);
	}
	function HideDialog10() {
		$("#overlay10").hide();
		$("#dialog10").fadeOut(300);
	}
	function okFunction() {
		HideDialog();
	}
</script>
<style type="text/css">
.msgg li:first-child {
	list-style: none;
}
.contenttableNew td {
	padding-top: 15px;
}
.contenttableNew td table td {
	padding-top: 2px;
}
#msgg li {
	float: left;
}
#msgg br {
	height: 1px;
	float: left;
}
.postPrefDisabled {
	pointer-events: none;
	opacity: 0.56;
}
</style>
<div class="row">
		<div class="col-sm-12">
				<s:if test="%{#attr.dataNotFound!=''}">
						<div class="error-massage-text">
								<s:property value="#attr.dataNotFound" />
						</div>
				</s:if>
		</div>
</div>
<div class="container titlebg">
		<h1 class="pageTitle"> Additional Education Details: / <span class="tamil">
				<s:text
				name="applicationForm.additionalEduDetails" />
				</span>
				<div class="userid_txt">
						<s:if test="%{#session['SESSION_USER'] != null}"> <strong>User ID / <span class="tamil">
								<s:text
							name="applicationForm.userId" />
								</span></strong> -
								<s:label value="%{#session['SESSION_USER'].username}" />
						</s:if>
				</div>
		</h1>
</div>
<s:form id="additionalEduForm" action="CandidateAction"
	autocomplete="off">
	<s:hidden name="handicappedValue" id="handicappedValue" />
	<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
	<s:hidden name="isDataFound"
		value='<s:property value="candidateDataFilled"/>' />
	<s:hidden name="degreeVal" id="degreeVal" />
	<s:hidden name="cateoryValue" id="cateoryValue" />
	<s:hidden name="candidateStatusId" id="candidateStatusId"
		value="%{statusID}"></s:hidden>
	<!-- Box Container Start -->

	<div class="container common_dashboard tabDiv effect2">
		<div id="error-massageAppForm" style="display: none; color: red;"
			class="error-massage">
			<ul style="margin-left: -41px;" id="error-ulAppForm">
			</ul>
		</div>
		<s:actionmessage escape="false" cssClass="msgg" />
		<div class="row" id="checkBox">
			<%-- <div class="col-sm-4">
								<div class="form-group">
										<label class="control-label">Have you completed Ph.D <br />
												<span class="tamil">
												<s:text
										name="additionalEdu.phdsletQstn" />
												</span> <span class="manadetory-fields">*</span></label>
										<s:select list="phdsletQstnList" name="phdsletQstn" label="Name"
								headerKey="" headerValue="--Select Appropriate--" id="phdsletQstn" cssClass="form-control"
								value="%{phdsletQstn}" onChange = "ShowAppropriate();"/>
								</div>
						</div> --%>
			<div class="col-sm-12">

				<p>Select your completed education qualification:</p>

			</div>

			<div class="col-sm-4">
				<p>
					<b> <s:checkbox value="%{isPhdChecked}" name="isPhdChecked"
							id="isPhdChecked" />PHD
					</b>
				</p>
			</div>
			<div class="col-sm-4">
				<p>
					<b> <s:checkbox value="%{isSletChecked}" name="isSletChecked"
							id="isSletChecked" />SLET
					</b>
				</p>
			</div>
			<div class="col-sm-4">
				<p>
					<b> <s:checkbox value="%{isNetChecked}" name="isNetChecked"
							id="isNetChecked" />NET
					</b>
				</p>
			</div>
		</div>
		<div class="clear"></div>

		<div class="accordions">
			<div id="PhdDiv">
				<h3 title="Ph.D Details">Ph.D Details</h3>
				<div class="accordion">
					<div class="row">
						<div class="col-sm-4">
							<div class="form-group">
								<label class="control-label">University in which Ph.D completed <br /> <span
									class="tamil"> <s:text
											name="additionalEdu.phdStatusList" />
								</span> <span class="manadetory-fields">*</span></label>
								<s:select list="phdStatusList" name="phdStatus" label="Name"
									headerKey="" headerValue="--Select Appropriate--"
									id="phdStatus" cssClass="form-control" value="%{phdStatus}"
									onChange="showGeCerti();" />
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label class="control-label">Ph.D Mode<br /> <span
									class="tamil"> <s:text name="additionalEdu.phdMode" />
								</span> <span class="manadetory-fields">*</span></label>
								<s:select list="phdModeList" name="phdMode" label="Name"
									cssClass="form-control" headerKey=""
									headerValue="--Select Appropriate--" id="phdMode"
									value="%{phdMode}" />
							</div>
						</div>
						<div class="col-sm-4">
						<div class="form-group">
							<label class="control-label">Subject Applied for<br /> <span
								class="tamil"> <s:text name="additionalEdu.subjectApplied" />
							</span> <span class="manadetory-fields">*</span></label>
							<s:select list="mainSubjectAppliedList" name="mainSubjectApplied" label="Name"
								cssClass="form-control mt20" headerKey="" headerValue="--Select Appropriate--" id="mainSubjectApplied"
								value="%{mainSubjectApplied}" onChange = "showOtherSubject();"/>
						</div>
					</div>	
						
					</div>
					<div class="row">
					<div class="col-sm-4" id="showOtherSubject">
						<div class="form-group">
							<label class="control-label">Other Subject Applied for<br /> <span
								class="tamil"> <s:text name="additionalEdu.subjectApplied" />
							</span> <span class="manadetory-fields">*</span></label>
							<s:textfield id="AdditionSubjectName"
									value="%{AdditionSubjectName}" onpaste="return false;"
									cssClass="form-control mt20" name="AdditionSubjectName"
									maxlength="75" onkeypress="return alphabetsWithSpacenChars(event);"
									ondragstart="return false;" ondrop="return false;"></s:textfield>
						</div>
					</div>
						
						<div class="col-sm-6" id="GECERTIDIV">
							<div class="form-group">
								<label class="control-label">Genuinity and Equality
									Certificate obtained from association of Indian Universities,
									New Delhi <br /> <span class="tamil"> <s:text
											name="additionalEdu.geCertificate" />
								</span> <span class="manadetory-fields">*</span>
								</label>
							<div class="row"><div class="col-sm-6">	<s:select list="geCertificateList" name="geCertificate"
									cssClass="form-control" label="Name" headerKey=""
									headerValue="--Select Appropriate--" id="geCertificate"
									value="%{geCertificate}" onChange="ShowCertPopUp();" /></div></div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-4">
							<div class="form-group dateInput">
								<label class="control-label"> Date of Registration of
									Ph.D <br /><span class="tamil"><s:text name="additionalEdu.dateOfReg" /></span><span class="manadetory-fields">*</span>
								</label>
								<s:textfield id="dateOfReg" name="dateOfReg" readonly="true"
									onkeypress="return restrictEnter(event);"
									cssClass="form-control">
								</s:textfield>
								<div class="clear"></div>
								<s:hidden id="dobHidden" value="%{dateOfReg}"></s:hidden>
							</div>
						</div>
						<div class="col-sm-4" id="phdQst1div">
							<div class="form-group">
								<label class="control-label">Is Ph.D. degree awarded in
									a regular mode<br /> <span class="tamil"> <s:text
											name="additionalEdu.phdQstList1" />
								</span> <span class="manadetory-fields">*</span>
								</label>
								<s:select list="phdQstList1" name="phdQst1" label="Name"
									cssClass="form-control" headerKey=""
									headerValue="--Select Appropriate--" id="phdQst1"
									value="%{phdQst1}" />
							</div>
						</div>
						<div class="col-sm-4" id="phdQst2div">
							<div class="form-group">
								<label class="control-label">Is Ph.D. Thesis evaluated
									by atleast 2 external examiners<br /> <span class="tamil">
										<s:text name="additionalEdu.phdQstList2" />
								</span> <span class="manadetory-fields">*</span>
								</label>
								<s:select list="phdQstList2" name="phdQst2" label="Name"
									cssClass="form-control" headerKey=""
									headerValue="--Select Appropriate--" id="phdQst2"
									value="%{phdQst2}" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-4" id="phdQst3div">
							<div class="form-group">
								<label class="control-label">Is Open Ph.D. Viva Voce has
									been conducted<br /> <span class="tamil"> <s:text
											name="additionalEdu.phdQstList3" />
								</span> <span class="manadetory-fields">*</span>
								</label>
								<s:select list="phdQstList3" name="phdQst3" label="Name"
									cssClass="form-control" headerKey=""
									headerValue="--Select Appropriate--" id="phdQst3"
									value="%{phdQst3}" />
							</div>
						</div>
						<div class="col-sm-8" id="phdQst4div">
							<div class="form-group">
								<label class="control-label">Have you published two
									research papers from your Ph.D work, out of which atleast one
									is in refereed journal<br /> <span class="tamil"> <s:text
											name="additionalEdu.phdQstList4" />
								</span> <span class="manadetory-fields">*</span>
								</label><div class="row"><div class="col-sm-6"><s:select list="phdQstList4" name="phdQst4" label="Name"
											cssClass="form-control" headerKey=""
											headerValue="--Select Appropriate--" id="phdQst4"
											value="%{phdQst4}" /></div></div>
							</div>
						</div>
						<div class="col-sm-12" id="phdQst5div">
							<div class="form-group">
								<label class="control-label">Have you presented at least
									two papers based on your Ph.Dwork in conference/seminars

									sponsored/funded/supported by the UGC/ICSSR/CSIR or any similar
									agency<br /> <span class="tamil"> <s:text
											name="additionalEdu.phdQstList5" />
								</span> <span class="manadetory-fields">*</span>
								</label><div class="row"><div class="col-sm-4"><s:select list="phdQstList5" name="phdQst5" label="Name"
											cssClass="form-control" headerKey=""
											headerValue="--Select Appropriate--" id="phdQst5"
											value="%{phdQst5}" onChange="ShowPhdPopUp();"/></div></div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-4">
							<div class="form-group dateInput">
								<label class="control-label"> Date of Viva- Voce<span
									class="manadetory-fields">*</span> <br /> <span class="tamil">
										<s:text name="additionalEdu.dateOfviva" />
								</span></label>
								<s:textfield id="dateOfviva" name="dateOfviva" readonly="true"
									onkeypress="return restrictEnter(event);"
									cssClass="form-control">
								</s:textfield>
								<div class="clear"></div>
								<s:hidden id="dobHidden" value="%{dateOfviva}"></s:hidden>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label class="control-label">Name of the University<br />
									<span class="tamil"> <s:text
											name="additionalEdu.AdditionalUniversityName" />
								</span> <span class="manadetory-fields">*</span>
								</label>
								<s:textfield id="AdditionalUniversityName"
									value="%{AdditionalUniversityName}" onpaste="return false;"
									cssClass="form-control" name="AdditionalUniversityName"
									maxlength="100" onkeypress="return onlyAlphabets(event);"
									ondragstart="return false;" ondrop="return false;"></s:textfield>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="clear mt10"></div>
		<div class="accordions">
			<div id="SletDiv">
				<h3 title="SLETDetails">SLET Details</h3>
				<div class="accordion">
					<div class="row">
						<div class="col-sm-4">
							<div class="form-group">
								<label class="control-label">Registration number<br />
									<span class="tamil"> <s:text
											name="additionalEdu.registrationNumber" />
								</span> <span class="manadetory-fields">*</span>
								</label>
								<s:textfield id="registrationNumber" onpaste="return false;"
									name="registrationNumber" maxlength="100"
									cssClass="form-control" ondragstart="return false;"
									ondrop="return false;"
									onkeypress=" return alphaNumericOnly(event);"></s:textfield>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label class="control-label">Subject<br /> <span
									class="tamil"> <s:text name="additionalEdu.SubjectName" />
								</span> <span class="manadetory-fields">*</span>
								</label>
								<s:textfield id="subjectName" onpaste="return false;"
									name="subjectName" maxlength="100" cssClass="form-control"
									ondragstart="return false;" ondrop="return false;"
									onkeypress=" return onlyAlphabets(event);" readonly="true"></s:textfield>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label class="control-label">Agency Conducted<br /> <span
									class="tamil"> <s:text name="additionalEdu.AgencyList" />
								</span> <span class="manadetory-fields">*</span></label>
								<s:select list="AgencyNewList" name="agencyVal" label="Name"
									cssClass="form-control" headerKey=""
									headerValue="--Select Appropriate--" id="agencyVal"
									value="%{agencyVal}" onChange="showStateUni();" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-4" id="StateUni">
							<div class="form-group">
								<label class="control-label">Please Specify State
									University<br /> <span class="tamil"> <s:text
											name="additionalEdu.StateUniName" />
								</span> <span class="manadetory-fields">*</span>
								</label>
								<s:textfield id="stateUniName" onpaste="return false;"
									name="stateUniName" value="%{stateUniName}" maxlength="150" cssClass="form-control"
									ondragstart="return false;" ondrop="return false;"
									onkeypress=" return onlyAlphabets(event);"></s:textfield>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group dateInput">
								<label class="control-label">Year of passing  <br /><span class="tamil"><s:text name="additionalEdu.yearOfPass" /><span
									class="manadetory-fields">*</span></label>
								<s:textfield id="yearOfPass" name="yearOfPass" readonly="true"
									onkeypress="return restrictEnter(event);"
									cssClass="form-control">
								</s:textfield>
								<div class="clear"></div>
								<s:hidden id="dobHidden" value="%{yearOfPass}"></s:hidden>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="clear mt10"></div>
		<div class="accordions">
			<div id="NetDiv">
				<h3 title="NETDetails">NET Details</h3>
				<div class="accordion">
					<div class="row">
						<div class="col-sm-4">
							<div class="form-group">
								<label class="control-label">Registration number<br />
									<span class="tamil"> <s:text
											name="additionalEdu.registrationNumberNET" />
								</span> <span class="manadetory-fields">*</span>
								</label>
								<s:textfield id="registrationNumberNET" onpaste="return false;"
									name="registrationNumberNET" maxlength="100"
									cssClass="form-control" ondragstart="return false;"
									ondrop="return false;"
									onkeypress=" return alphaNumericOnly(event);"></s:textfield>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label class="control-label">Subject<br /> <span
									class="tamil"> <s:text
											name="additionalEdu.SubjectNameNET" />
								</span> <span class="manadetory-fields">*</span>
								</label>
								<s:textfield id="subjectNameNET" onpaste="return false;"
									name="subjectNameNET" maxlength="100" cssClass="form-control"
									ondragstart="return false;" ondrop="return false;"
									onkeypress=" return onlyAlphabets(event);" readonly="true"></s:textfield>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label class="control-label">Agency Conducted<br /> <span
									class="tamil"> <s:text name="additionalEdu.AgencyListNET" />
								</span> <span class="manadetory-fields">*</span></label>
								<s:select list="AgencyList" name="agencyValNET" label="Name"
									cssClass="form-control" headerKey=""
									headerValue="--Select Appropriate--" id="agencyValNET"
									value="%{agencyValNET}" onChange="showStateUniNET();" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-4" id="StateUniNET">
							<div class="form-group">
								<label class="control-label">Please Specify State
									University<br /> <span class="tamil"> <s:text
											name="additionalEdu.StateUniNameNET" />
								</span> <span class="manadetory-fields">*</span>
								</label>
								<s:textfield id="stateUniNameNET" onpaste="return false;"
									name="stateUniNameNET" value="%{stateUniNameNET}" maxlength="150" cssClass="form-control"
									ondragstart="return false;" ondrop="return false;"
									onkeypress=" return onlyAlphabets(event);"></s:textfield>
							</div>
						</div>
						<div class="col-sm-4" id="StateOthersNET">
							<div class="form-group">
								<label class="control-label">Please Specify Name of
									Agency<br /> <span class="tamil"> <s:text
											name="additionalEdu.StateOthersNameNET" />
								</span> <span class="manadetory-fields">*</span>
								</label>
								<s:textfield id="stateOthersNameNET" onpaste="return false;"
									name="stateOthersNameNET" value="%{stateOthersNameNET}" maxlength="150" cssClass="form-control"
									ondragstart="return false;" ondrop="return false;"
									onkeypress=" return onlyAlphabets(event);"></s:textfield>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group dateInput">
								<label class="control-label">Year of passing <br />
								<span class="tamil"> <s:text name="additionalEdu.yearOfPassNET" /></span>
								<span class="manadetory-fields">*</span> </label>
								<s:textfield id="yearOfPassNET" name="yearOfPassNET"
									readonly="true" onkeypress="return restrictEnter(event);"
									cssClass="form-control">
								</s:textfield>
								<div class="clear"></div>
								<s:hidden id="dobHidden" value="%{yearOfPassNET}"></s:hidden>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="clear mt10"></div>
		<div class="accordions">
			<s:if test='%{subjectInEducation}'>
			<div id="EduDiv">
				<h3 title="PSTM Details">SPECIAL RECRUITMENT FOR PRESIDENCY COLLEGE (To Teach Deaf candidates in Tamil and Computer Application)</h3>
				<div class="accordion">
						<div class="row">
								<div class="col-sm-8" id="EduQst1div">
									<div class="form-group">
										<label class="control-label">Have you undergone any
											course relating to teaching of Deaf Candidates under
											Rehabilitation Council of India (RCI) <br /> <span
											class="tamil"> <s:text name="additionalEdu.EduQst1" />
										</span> <span class="manadetory-fields">*</span>
										</label>
										<div class="row"><div class="col-sm-6"><s:select list="phdQstList5" name="eduQst1" label="Name"
											cssClass="form-control" headerKey=""
											headerValue="--Select Appropriate--" id="eduQst1"
											value="%{eduQst1}" onchange="showEduQst1Other();"/></div></div>
									</div>
								</div><div class="col-sm-4" id="EduQst1otherdiv">
									<div class="form-group">
										<label class="control-label">Name of the course<br />
											<span class="tamil"> <s:text
													name="additionalEdu.EduQst1other" />
										</span> <span class="manadetory-fields">*</span>
										</label>
										<s:textfield id="EduQst1other" onpaste="return false;"
											name="EduQst1other" maxlength="75" cssClass="form-control"
											ondragstart="return false;" ondrop="return false;"
											onkeypress=" return onlyAlphabets(event);"></s:textfield>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-8" id="EduQst2div">
								<div class="form-group">
									<label class="control-label">Have you possess B.Ed
										special Education with Specialization in Hearing Impaired <br />
										<span class="tamil"> <s:text
												name="additionalEdu.EduQst2" />
									</span> <span class="manadetory-fields">*</span>
									</label>
									<div class="row"><div class="col-sm-6"><s:select list="phdQstList5" name="eduQst2" label="Name"
										cssClass="form-control" headerKey=""
										headerValue="--Select Appropriate--" id="eduQst2"
										value="%{eduQst2}" onchange="showEduQst1Other();"/></div></div>
								</div>
							</div>
						 </div>
						<div class="row">
							<div class="col-sm-3" id="EduQst2otherdiv">
								<div class="form-group">
									<label class="control-label">Institution studied<br />
										<span class="tamil"> <s:text
												name="additionalEdu.EduQst3other" />
									</span> <span class="manadetory-fields">*</span>
									</label>
									<s:textfield id="EduQst2other" onpaste="return false;"
										name="EduQst2other" maxlength="75" cssClass="form-control"
										ondragstart="return false;" ondrop="return false;"
										onkeypress=" return onlyAlphabets(event);"></s:textfield>
								</div>
							</div>
							<div class="col-sm-3" id="EduQst2univdiv">
								<div class="form-group">
									<label class="control-label">Name of University<br />
										<span class="tamil"> <s:text
												name="additionalEdu.EduQst2univ" />
									</span> <span class="manadetory-fields">*</span>
									</label>
									<s:textfield id="EduQst2univ" onpaste="return false;"
										name="EduQst2univ" maxlength="75" cssClass="form-control"
										ondragstart="return false;" ondrop="return false;"
										onkeypress=" return onlyAlphabets(event);"></s:textfield>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="row">
							<div class="col-sm-6" id="perdOfStudyFrmdiv">
							<div class="form-group dateInput">
								<label class="control-label">Period of Study From <span class="tamil"><br /><s:text name="academics.periodofstudy" /></span><span
									class="manadetory-fields">*</span></label>
								<s:textfield id="perdOfStudyFrm" name="perdOfStudyFrm"
									readonly="true" onkeypress="return restrictEnter(event);"
									cssClass="form-control">
								</s:textfield>
								<span class="fl-rigt mt10">To</span>
								<div class="clear"></div>
								<s:hidden id="dobHidden" value="%{perdOfStudyFrm}"></s:hidden>
							</div>
						</div>
							<div class="col-sm-6" id="perdOfStudyTodiv">
								<div class="form-group dateInput">
								<label class="control-label">&nbsp;</label><br /><br />
									<s:textfield id="perdOfStudyTo" name="perdOfStudyTo"
										readonly="true" onkeypress="return restrictEnter(event);"
										cssClass="form-control mb20">
									</s:textfield>
									<div class="clear"></div>
									<s:hidden id="dobHidden" value="%{perdOfStudyTo}"></s:hidden>
								</div>
							</div>
						</div>
							</div>				
						</div>						
						<div class="row">
							<div class="col-sm-6" id="EduQst3div">
										<div class="form-group">
											<label class="control-label">Have you possess Senior Diploma in Teaching Deaf Students <br /> <span class="tamil">
													<s:text name="additionalEdu.EduQst3" />
											</span> <span class="manadetory-fields">*</span>
											</label>
											<div class="row"><div class="col-sm-8"><s:select list="phdQstList5" name="eduQst3" label="Name"
												cssClass="form-control" headerKey=""
												headerValue="--Select Appropriate--" id="eduQst3"
												value="%{eduQst3}" onchange="showEduQst1Other();"/></div></div>
										</div>
									</div>
							<div class="col-sm-4" id="EduQst3otherdiv">
										<div class="form-group">
											<label class="control-label mb20">Institution studied<br />
												<span class="tamil"> <s:text
														name="additionalEdu.EduQst3other" />
											</span> <span class="manadetory-fields">*</span>
											</label>
											<s:textfield id="EduQst3other" onpaste="return false;"
												name="EduQst3other" maxlength="75" cssClass="form-control"
												ondragstart="return false;" ondrop="return false;"
												onkeypress=" return onlyAlphabets(event);"></s:textfield>
										</div>
									</div>
				 	</div>
			</div>
</div>
			</s:if>
		</div>

		<div class="clear mt10"></div>
		<div class="accordions">
			<div id="PSTMDiv">
				<h3 title="PSTM Details">PSTM Details</h3>
				<div class="accordion">
					<div class="row">
						 <%-- <div class="col-sm-4">
						<div class="form-group">
							<label class="control-label">Subject Applied for<br /> <span
								class="tamil"> <s:text name="additionalEdu.mainSubjectAppliedList" />
							</span> <span class="manadetory-fields">*</span></label>
							<s:select list="mainSubjectAppliedList" name="mainSubjectApplied" label="Name"
								cssClass="form-control" headerKey="" headerValue="--Select Appropriate--" id="mainSubjectApplied"
								value="%{mainSubjectApplied}" />
						</div>
					</div>	  --%>
							<div class="col-sm-4">
								<div class="form-group">
									<label class="control-label">Do you claim PSTM (Persons
										Studied in Tamil Medium) reservation?<br /> <span
										class="tamil"> <s:text name="additionalEdu.PSTMList" />
									</span> <span class="manadetory-fields">*</span>
									</label>
									<s:select list="PSTMList" name="pstmSelected" label="Name"
										cssClass="form-control" headerKey=""
										headerValue="--Select Appropriate--" id="pstmSelected"
										value="%{pstmSelected}" onchange="ShowPgEquivalent();ShowPgEquivalentQ();" />
								</div>
							</div>
							<div class="col-sm-4" id="PGEquivalentDiv">
								<div class="form-group">
									<label class="control-label">PG or its equivalent
										studied in Tamil Medium?<br /> <span class="tamil"> <s:text
												name="additionalEdu.PGequivList" />
									</span> <span class="manadetory-fields">*</span>
									</label>
									<s:select list="PGequivList" name="pgequivVal" label="Name"
										cssClass="form-control" headerKey=""
										headerValue="--Select Appropriate--" id="pgequivVal"
										value="%{pgequivVal}"
										maxlength="50" onkeypress=" return alphabetsSchool(event);" onchange="showPopUp1();"
										/>
								</div>
							</div> 
							<div class="col-sm-4" id="PSTMPHD">
							<div class="form-group">
								<label class="control-label">Is Ph.D undergone in Tamil
									Medium <br /> <span class="tamil"> <s:text
											name="additionalEdu.phdTN" />
								</span> <span class="manadetory-fields">*</span>
								</label> <br />
								<s:select list="phdTNList" name="phdTNVal" label="Name"
									cssClass="form-control" headerKey=""
									headerValue="--Select Appropriate--" id="phdTNVal"
									value="%{phdTNVal}" onchange="showpopup4();"/>
							</div>
						</div>
							<div id="overlay10" class="web_dialog_overlay_declr"> </div>
							<div class="fullscreen-container" id="dialog10">
														<div class="modal-dialog">
																<div class="modal-content">
																		<div class="modal-header"> 
																				<!-- <button type="button" class="close" onClick="clearFields(); HideAll();">X</button> -->
																				<h2 class="modal-title">PSTM Details</h2>
																		</div>
																		<div class="modal-body">
																				<div class="row">
																						<div class="col-lg-12 text-center">
																								<h4 class="form-control" title="Your application is not eligible for PSTM Claim">Your application is not eligible for PSTM Claim</h4>
																						</div>
																				</div>
																		</div>
																		<div class="modal-footer">
																				<div class="row">
																						<div class="col-sm-6 col-sm-offset-3">
																								<input type="button" name="btnOk" value="Ok"
											onClick="HideDialog10();" class="ripple1 btn btn-warning btn-block" />
																						</div>
																				</div>
																		</div>
																</div>
														</div>
								</div>
					</div>
					<div class="row">						
						<div class="col-sm-4" id="mediumNETDIV">
							<div class="form-group">
								<label class="control-label">Is medium of appearance for
									NET in Tamil Medium?<br /> <span class="tamil"> <s:text
											name="additionalEdu.mediumNET" />
								</span> <span class="manadetory-fields">*</span>
								</label>
								<s:select list="SLETNETList" name="mediumnetVal" label="Name"
									cssClass="form-control" headerKey=""
									headerValue="--Select Appropriate--" id="mediumnetVal"
									value="%{mediumnetVal}" 
									onchange="showpopup3();"/>
							</div>
						</div>
						<div class="col-sm-4" id="mediumSLETNETDIV">
							<div class="form-group">
								<label class="control-label">Is medium of appearance for
									SLET in Tamil Medium?<br /> <span class="tamil"> <s:text
											name="additionalEdu.SLET" />
								</span> <span class="manadetory-fields">*</span>
								</label>
								<s:select list="SLETNETList" name="sletnetVal" label="Name"
									cssClass="form-control" headerKey=""
									headerValue="--Select Appropriate--" id="sletnetVal"
									value="%{sletnetVal}"
									 onchange="showpopup2();"
									 />
							</div>
						</div>						
						<div class="col-sm-4">
							<div class="form-group">
								<label class="control-label">Mother Tongue <br /> <span
									class="tamil"> <s:text name="additionalEdu.MotherTongue" />
								</span> <span class="manadetory-fields">*</span></label> <br />
								<s:select list="MotherTongueList" name="motherTongueVal"
									label="Name" headerKey="" headerValue="--Select Appropriate--"
									id="motherTongueVal" cssClass="form-control"
									value="%{motherTongueVal}" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="countinuebg mt20" id="saveBtn">
		<div class="container">
			<div class="row">
				<div class="col-sm-3 col-sm-offset-9 col-xs-6 col-xs-offset-3 text-right padding0">
					<s:submit value="Save & Continue" id="saveaed" cssClass="ripple1 btn btn-warning" method="updateCandidateStage"></s:submit>
				</div>
			</div>
		</div>
	</div>
	<s:token />
</s:form>
<div id="overlay" class="web_dialog_overlay_declr"></div>
<div class="fullscreen-container"  id="dialog">
		<div class="modal-dialog modal-md">
				<div class="modal-content">
						<div class="modal-header">
								<button type="button" class="close" onClick="okFunction();">X</button>
								<h2 class="modal-title">Certificate Confirmation</h2>
						</div>
						<div class="modal-body">
								<div class="row">
										<div class="col-sm-12">
												<div class="msgDiv">
														<ul>
																<li>
																		<input type="checkbox" name="declaration" id="declaration" style="display:none;"/>
																		The Same has to be provided at the time of Certificate Verification.</li>
																<li>
																		<%-- <s:text name = "additionalEdu.Category"/> --%>
																</li>
														</ul>
												</div>
										</div>
								</div>
						</div>
						<div class="modal-footer">
								<div class="row">
										<div class="col-sm-4 col-sm-offset-4 col-xs-6 col-xs-offset-3">
												<input type="button" name="accept" id="accept" value="Ok" class="ripple1 btn btn-warning btn-block" onClick="okFunction();"/>
										</div>
								</div>
						</div>
				</div>
		</div>
</div>
<div id="overlay5" class="web_dialog_overlay_declr"></div>
<div class="fullscreen-container"  id="dialog5">
		<div class="modal-dialog modal-md">
				<div class="modal-content">
						<div class="modal-header">
								<button type="button" class="close" onClick="okFunction5();">X</button>
								<h2 class="modal-title">Certificate Confirmation</h2>
						</div>
						<div class="modal-body">
								<div class="row">
										<div class="col-sm-12">
												<div class="msgDiv">
														<ul>
																<li>
																		<input type="checkbox" name="declaration" id="declaration" style="display:none;"/>
																		Compliance Certificate certified by the Registrar or the Dean (Academic Affairs) of the University concerned should be submitted at the time of Certificate Verification</li>
																<li>
																		<%-- <s:text name = "additionalEdu.Category"/> --%>
																</li>
														</ul>
												</div>
										</div>
								</div>
						</div>
						<div class="modal-footer">
								<div class="row">
										<div class="col-sm-4 col-sm-offset-4 col-xs-6 col-xs-offset-3">
												<input type="button" name="accept" id="accept" value="Ok" class="ripple1 btn btn-warning btn-block" onClick="okFunction5();"/>
										</div>
								</div>
						</div>
				</div>
		</div>
</div>
