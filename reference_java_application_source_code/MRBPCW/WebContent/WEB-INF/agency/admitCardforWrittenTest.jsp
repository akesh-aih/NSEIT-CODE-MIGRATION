<script type="text/javascript">
	$(document).ready(function() {
		$("#register").validationEngine({promptPosition : "centerRight", scroll: false});
	});
			

 $(document).ready(function() { 
 $("a:contains('Generate Admit Card')").html('<span class="fadeSubmenu">Generate Admit Card</span>')}); 



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

<h1 class="pageTitle" title="Generate Admit Card for Written Test">Generate Admit Card for Written Test</h1>
<div class="hr-underline2"></div>
<s:actionmessage escape="false" cssClass="msgg"/>
 
<s:form action="ReportAction_downloadAdmitCardWrittenTest.action">
<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
<input type="submit" value="Generate Admit Card for Written Test" class="submitBtn button-gradient"/>
<s:token />
</s:form>
</div>

</div>
