<script type="text/javascript">
	$(document).ready(function() {
		$("#register").validationEngine({promptPosition : "centerRight", scroll: false});
	});
			
	$(function() {
		$( "#datepicker1" ).datepicker({
			showOn: "button",
			changeMonth: true,
			changeYear: true,
			yearRange: range,
			minDate:0,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy'
		});
	});
 $(document).ready(function() { 
 $("a:contains('Generate Admit Card')").html('<span class="fadeSubmenu">Generate Admit Card</span>')}); 

	function selectAllChkBoxes(){
		if ($('#selectAllChkBox').attr('checked')){
			$(".checkBoxClass").attr('checked','checked');
		}
		
		if (!($('#selectAllChkBox').attr('checked'))){
			$(".checkBoxClass").removeAttr('checked');
		}
	}
 
 
 
 function deSelectAllChkBoxes(){
 var flag = true;
		$(".checkBoxClass").each(function(index, value) {
		if (flag){
			 if ($(this).attr('checked')){
	 		  	$('#selectAllChkBox').attr('checked','checked');
	 		  }else{
	 		  	$('#selectAllChkBox').removeAttr('checked');
	 		  	flag = false;
	 		  }
			}
		});
	}
 
 
 function numbersonly(e){
		var unicode=e.charCode? e.charCode : e.keyCode
		if (unicode!=8){
		if ((unicode<48||unicode>57) && unicode != 9 && unicode != 46) //if not a number
			return false //disable key press
		}
	}


	function validate() {
		$("#testCenterValNew").val($("#testCenterVal").val());
		$("#attemptValNew").val($("#attemptVal").val());
		$("#userIdNew").val($("#userId").val());
	}


	
	function resetAdmitSearch(){
		$("#userId").val("");
		$("#testCenterVal").val("");
		$("#attemptVal").val("");
		
		if ($('#testCenterVal').is(':disabled') == true) {
            $("#testCenterVal").removeAttr("disabled");
        } 
        
        if ($('#attemptVal').is(':disabled') == true) {
            $("#attemptVal").removeAttr("disabled");
        }
        
        if ($('#userId').is(':disabled') == true) {
            $("#userId").removeAttr("disabled");
        } 

        $("#enrollmentDiv").hide();
		
	}
	
	function disableUserId(){
		var testCenterVal = $("#testCenterVal").val();
		var attemptVal = $("#attemptVal").val();
		
		if (testCenterVal!="" || attemptVal!=""){
			$("#userId").val("");
			$("#userId").attr("disabled", "disabled");
		}
	}
	
	function disableDropDownsForSearch(){
		var userId=$("#userId").val();

		if (userId!=""){
			$("#testCenterVal").attr("disabled", "disabled");
			$("#attemptVal").attr("disabled", "disabled");
		}else{
			$("#testCenterVal").removeAttr("disabled");
			$("#attemptVal").removeAttr("disabled");
		}
	}

function open_win(number)
	{
	//window.open("ReportAction_downloadCallLetterMedicalTest.action")
	if(number==1)
	{
	window.open("ReportAction_downloadAdmitCardWrittenTest.action");
	}else if(number==2)
	{
	window.open("ReportAction_downloadCallLetterFieldTest.action");
	}else if(number==3)
	{
	window.open("ReportAction_downloadCallLetterInterview.action");
	}else if(number==4)
	{
		window.open("ReportAction_downloadCallLetterMedicalTest.action");
	}
		//window.open("CandidateAction_printHallTicket.action")
	}
	</script>

<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="main-body">
<div class="fade" id="pop3"></div>

<div id="AgencyAdmitCard">

<h1 class="pageTitle" title="Generate Admit Card for Field Test">Generate Admit Card for Field Test</h1>
<div class="hr-underline2"></div>
<s:actionmessage escape="false" cssClass="msgg"/>
 
<s:form action="ReportAction_downloadCallLetterFieldTest.action">
<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
<input type="submit" value="Generate Call Letter for Field Test" class="submitBtn button-gradient"/>
<s:token />
</s:form>
</div>

</div>
