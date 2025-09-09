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

<h1 class="pageTitle" title="Generate Admit Card"><s:text name="agencygenerateadmitcard.title"/></h1>
<div class="hr-underline2"></div>

<!-- Box Container Start -->
<div style="display:none;">
<s:form action = "CandidateMgmtAction">
<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
<s:hidden name="attempt" id="attempt"></s:hidden>
<div  id="admitCardError" style = "color:red;">		
				<s:actionmessage/>
			</div>
<div>


<div>

<div>
<div class="field-label"><s:text name="agencygenerateadmitcard.enrollmentID"/>&nbsp;</div>
<div>
	<s:textfield name="userId" id="userId" maxlength="15" cssClass="inputField" onblur="disableDropDownsForSearch()"/>
</div>
</div>

<div class="height10"></div>
<div class="WeightBold"><s:text name="agencygenerateadmitcard.or"/></div>
<div class="height10"></div>

<!-- Row One Start -->
<div class="clear"></div>

<div>
<div style="float:left; width:500px; height:50px;">
<div class="field-label">Test Center&nbsp;</div>

<s:select label="Discipline" name="testCenterVal"   id = "testCenterVal" onblur="disableUserId()"
		headerKey="" headerValue="All" list="testCenterListMap" value="%{testCenterVal}"/>
	<!--<s:textfield name="enrollmentDate" id = "datepicker1" readonly="true"  onblur="disableDropDownsForSearch()" />
-->
</div>
</div>
<s:if test='%{attemptDropdownFlag=="true"}'>
	<div style="float:left; width:300px; height:50px;">
	<div class="field-label ">Attempts</div>
	<div>
	<s:select label="Discipline" name="attemptVal"    id = "attemptVal"  onblur="disableUserId()"
			headerKey="" headerValue="Both" list="attemptsListMap" value="%{attemptVal}"/>
	
	</div>
</div>
</s:if>
<div id="fnameID" class="errorMessage"></div>



<!-- Row One End -->

<!-- Row Two Start -->
<div class="clear">
<div class="fl-left fifty">
<div>
	
	<s:submit key="agencygenerateadmitcard.submit" cssClass="submitBtn button-gradient" method="getAdmitCardList"></s:submit>
	<input type="button" value="<s:text name="agencygenerateadmitcard.clear"/>" title="Clear" class="submitBtn button-gradient" onclick="resetAdmitSearch();"/>
	
</div>
</div>
<div class="fl-rigt fifty">&nbsp;</div>
</div>
<!-- Row Two End -->

<div class="height20"></div>

</div>

</div>

<!-- Dash Line Start -->
<div class="clear hr-dashline"></div>
<!-- Dash Line End -->
</s:form>
</div>
<input type="button" onclick="open_win(1)" value="Generate Admit Card for Written Test" class="submitBtn button-gradient"/>
<br/>
<br/>
<input type="button" onclick="open_win(2)" value="Generate Call Letter for Field Test" class="submitBtn button-gradient"/>
<br/>
<br/>
<input type="button" onclick="open_win(3)" value="Generate Call Letter for Interview" class="submitBtn button-gradient"/>
<br/>
<br/>
<input type="button" onclick="open_win(4)" value="Generate Call Letter for Medical Test" class="submitBtn button-gradient"/>
<!-- Box Container End -->
</div>

</div>
<s:if test='%{admitCardDisplayFlag=="true"}'>
<s:form action="CandidateMgmtAction_getAdmitCardList.action" name="paginationForm">

<s:hidden name="testCenterVal" id="testCenterValNew"></s:hidden>
<s:hidden name="userId" id="userIdNew"></s:hidden>
<s:hidden name="attemptVal" id="attemptValNew"></s:hidden>
<s:token/>
<div class="EnrollmentGrid" id = "enrollmentDiv">
<s:if test='%{admitCardHeaderDisplayFlag=="true"}'>
	<table width="100%">
		<tr class="box-header">
			<td align="left" width="30%" >Admit Card Report</td>
			<td align="center" width="30%" class="pagination-label">Total <font color="red"><s:property value="admitCardRecordCount"/></font> Record Found </td>
			<td width="40%">
				<s:if test='%{admitCardRecordCount=="0"}'>
				</s:if><s:else><%@ include file="../pagination.jsp" %></s:else>
			</td>
		</tr>
	</table>
	<br/>
		<table cellspacing="0" cellpadding="3" width="800" border="1" class="table_2" bordercolor="#CCCCCC" >
		    <thead>
			    <tr>
				    <th style="width: 20px;"><input type = "checkbox" id = "selectAllChkBox" onclick = "selectAllChkBoxes()"/></th>
				    <th style="width: 40px;" align="left">User Id</th>
				    <th style="width: 95px;" align="left">Enrollment Id</th>
				    <th style="width: 170px;" align="left">Name</th>
				    <th style="width: 220px;" align="left">Test Center Name</th>
					<th style="width: 95px;" align="left">Test Date</th>
					<th style="width: 80px;" align="left">Test Slot</th>
					<th style="width: 120px;" align="left">Roll Number</th>
					<th style="width: 50px;" align="left">Attempt</th>
				</tr>
		    </thead>
		  
		    <tbody>
				<s:iterator value="candidateListForAdmitCard">
					<tr>
						<td align="center"><s:checkbox name="enrollmentPKForAdmitCard" cssClass="checkBoxClass" fieldValue="%{enrollmetPk}" value="" onclick = "deSelectAllChkBoxes()"></s:checkbox></td>
						<td><s:property value="userId"/></td>
						<td><s:property value="enrollmetPk"/></td>
						<td><s:property value="userName"/></td>
						<td><s:property value="testCenterName"/></td>
						<td><s:property value="testDate"/></td>
						<td><s:property value="testSlot"/></td>
						<td><s:property value="rollNumber"/></td>
						<td><s:property value="bookingAttempt"/></td>
					</tr>
				</s:iterator>
		</tbody>
		
</table>
	<table width="100%" >
	<tr>
		<td width="100%">
			&nbsp;
		</td>
	</tr>
		<tr>
			<td width="100%" align = "center" >
					<s:submit method="generateAdmitCard" key="register.submit" cssClass="submitBtn button-gradient"  onclick="validate();"></s:submit>
			</td>
		</tr>
	</table>	 
</s:if>

<s:else>
	<table cellspacing="0" cellpadding="3" width="800" border="1" class="table_2" bordercolor="#CCCCCC" >
		<tr>
			<td colspan="6" align="center"><b>No Record Found (Either Admit Card is already generated or Roll Number is not generated)</b></td>
		</tr>
	</table>
</s:else>
</div>
</s:form>

<!--<div class="pGroup">-->
<!--<select name="rp"><option selected="selected" value="10">10&nbsp;&nbsp;</option><option value="15">15&nbsp;&nbsp;</option><option value="20">20&nbsp;&nbsp;</option><option value="25">25&nbsp;&nbsp;</option><option value="40">40&nbsp;&nbsp;</option></select></div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pFirst pButton"><span></span></div><div class="pPrev pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"><span class="pcontrol"><s:text name="agencyenrollmentdetails.footerpage"/> <input type="text" value="1" size="4"> <s:text name="agencyenrollmentdetails.footerof"/> <span>29</span></span></div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pNext pButton"><span></span></div><div class="pLast pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pReload pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"><span class="pPageStat"><s:text name="agencyenrollmentdetails.footerdisplaying"/> 1 <s:text name="agencyenrollmentdetails.footerto"/> 10 <s:text name="agencyenrollmentdetails.footerof"/> 290 <s:text name="agencyenrollmentdetails.footeritems"/></span></div></div><div style="clear: both;"></div></div>-->
<!--</div>-->


<!-- Button Start -->
<div class="height20"></div>

<!-- Button End -->

</s:if>

<s:if test='%{admitCardDisplayFlag=="false"}'>
		<s:text name="agencyenrollmentdetails.windownotavailablemessage"/> 
</s:if>

