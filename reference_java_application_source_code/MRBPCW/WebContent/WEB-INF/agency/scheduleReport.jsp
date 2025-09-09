<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<script type="text/javascript" src="js/AES.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#datepicker3").datepicker({
			showOn: "button",
			buttonImageOnly: true,
			changeMonth: true,
			changeYear: true,
			yearRange: range,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
	        numberOfMonths: 1,
	    });
	    $("#datepicker4").datepicker({
	    	showOn: "button",
			buttonImageOnly: true,
			changeMonth: true,
			changeYear: true,
			yearRange: range,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
	        numberOfMonths: 1,
	        
	    });
	    
	    $(".disableEnroll").change(function(){
	    });
	    
	});
	
	function imposeMaxLength(Event, Object, MaxLen)
	{
		return (Object.value.length <= MaxLen)||(Event.keyCode == 8 ||Event.keyCode==46||(Event.keyCode>=35&&Event.keyCode<=40))
	}
	function numbersonly(e){
		var unicode=e.charCode? e.charCode : e.keyCode
		if (unicode!=8 ){
		if ((unicode<48||unicode>57) && unicode != 46) //if not a number
			return false //disable key press
		}
	}
	
	function resetPaySearch()
	{
		$("#error-massage3").hide();
		$("#gridDiv1").hide();
		$("#gridDiv2").hide();
		$("#actionMsgError").hide();
		$("#userId").val("");
		$("#scheduleStatusId").val("-1");
		$("#disciplineId").val("");
		$("#datepicker3").val("");
		$("#datepicker4").val("");
		$("#testCenterId").val("0");
		$("#datepicker3").removeAttr("disabled");
		$("#datepicker4").removeAttr("disabled");
	}
	
function changeAction(){
	$("#scheduleReportId").attr('action',"ReportAction_getSechduleReport.action");
	$("#scheduleReportId").submit();
}
</script>

<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="main-body">
<div class="fade" id="pop3"></div>

<div id="SelectTest">

<h1 class="pageTitle" title="Schedule Report">Schedule Report</h1>
<div class="hr-underline2"></div>

<!-- Box Container Start -->


<div class="clear">
<s:form action="ReportAction" id="scheduleReportId" name="scheduleReportId">
<input type="hidden" name="menuKey" value='<%=request.getAttribute("menuKey")%>'/>
<table width="100%">
	<tr>
		<td class="field-label">User ID&nbsp;</td>
		<td><s:textfield name="userId" id="userId" onkeypress="return alphaNumeric(event)" ></s:textfield></td>
	</tr>
	<tr>
		<td class="field-label">Discipline&nbsp;</td>
		<td><s:select label="Discipline" name="disciplineId" 
		headerKey="" headerValue="All" list="disciplineListMap" id="disciplineId" value="%{disciplineId}"/></td>
		
		<td class="field-label">Schedule Status&nbsp;</td>
		<td><s:select label="Status" name="scheduleStatusId" 
		headerKey="-1" headerValue="All" list="scheduleStatusList" id="scheduleStatusId" value="%{scheduleStatusId}"/></td>
	</tr>
	<tr>
		<td class="field-label">From Date(Test Date)&nbsp;</td>
		<td><s:textfield name="fromDate" id="datepicker3" disabled="disabled" readonly="true" cssStyle="width:116px;" cssClass="inputDate disableEnroll"></s:textfield></td>
		
		<td class="field-label">To Date(Test Date)&nbsp;</td>
		<td><s:textfield name="toDate" id="datepicker4" disabled="disabled" readonly="true" cssStyle="width:116px;" cssClass="inputDate disableEnroll"></s:textfield></td>
	</tr>
	<tr>
		<td class="field-label">Test Center</td>
		<td>	<s:select label="TestCenter" name="testCenterId" headerKey="0" headerValue="All" list="testCenterListMap" id="testCenterId" value="%{testCenterId}"/></td>
	</tr>
</table>
<br/>

<div class="clear">
<div class="fl-left fifty">
<div>
<input type="button" value="Search" class="submitBtn button-gradient" onclick="changeAction();"/>&nbsp;&nbsp;
<input type="button" value="Clear" title="Clear" class="submitBtn button-gradient" onclick="resetPaySearch();"/>
</div>
</div>
<div class="fl-rigt fifty">&nbsp;</div>
</div>
<!-- Row Four End -->


</s:form>

<div class="height20"></div>


<!-- Dash Line Start -->
<div class="clear hr-dashline"></div>
<!-- Dash Line End -->
<div class="AgencyPayDrid" id="gridDiv1" >
<s:form method="post" action="CandidateAction">
<input type="hidden" name="menuKey" value='<%=request.getAttribute("menuKey")%>'/>
<input type="hidden" name="disciplineId" value='<s:property value="disciplineId"/>'/>
<input type="hidden" name="scheduleStatusId" value='<s:property value="scheduleStatusId"/>'/>
<input type="hidden" name="userId" value='<s:property value="userId"/>'/>
<input type="hidden" name="fromDate" value='<s:property value="fromDate"/>'/>
<input type="hidden" name="toDate" value='<s:property value="toDate"/>'/>
<input type="hidden" name="testCenterId" value='<s:property value="testCenterId"/>'/>

<s:if test='%{showDataGridFlag == "true"}'>
<table width="100%">
	<tr class="box-header">
		<td align="left" >Schedule Candidate Details</td>
		<td align="right">Total <font color="red"><s:property value="scheduleReportDetailListSize"/></font> Record(s) Found </td>
	</tr>
</table>
<br/>
<table cellspacing="0" border="1" width="100%" class="table_2" bordercolor="#CCC">
<thead>
    <tr>
    <th style="width: 79px;">User ID</th>
    <th style="width: 100px;">Name</th>
    <th style="width: 65px;">Discipline</th>
    <th style="width: 65px;">Mobile Number</th>
    <th style="width: 65px;">Email Id</th>
    <th style="width: 65px;">Test Date</th>
    <th style="width: 65px;">Test Center</th>
    <th style="width: 65px;">Batch</th>
    <th style="width: 65px;">Candidate Scheduling Date</th>
    <th style="width: 65px;">Admit Card Generated Date</th>
    </tr>
</thead>
<tbody>
<s:iterator value="scheduleReportDetailList" status="stat" var="currentObject">
		<tr>
			<td align="center" style="width: 37px;">
				<s:property value="OUM_USER_ID" />
			</td>
			<td align="center" style="width: 37px;">
				<s:property value="OCD_NAME" />
			</td>
			<td align="center" style="width: 37px;">
				<s:property value="OTM_TEST_NAME" />
			</td>
			<td align="center" style="width: 37px;">
				<s:property value="OUM_MOBILE_NO" />
			</td>
			<td align="center" style="width: 37px;">
				<s:property value="OUM_EMAIL_ID" />
			</td>
			<td align="center" style="width: 37px;">
				<s:property value="TEST_DATE" />
			</td>
			<td align="center" style="width: 37px;">
				<s:property value="OTCM_TEST_CENTRE_NAME" />
			</td>
			<td align="center" style="width: 37px;">
				<s:property value="EXAM_START_TIME" />
			</td>
			<td align="center" style="width: 37px;">
				<s:property value="OUM_EMAIL_ID" />
			</td>
			<td align="center" style="width: 37px;">
				<s:property value="OUM_EMAIL_ID" />
			</td>
			
		</tr>
</s:iterator>
<s:if test='%{availableRecordFlag == "false"}'>
	<tr>
		<td colspan="6" align="center">
			<div>
				<b>No Record Found</b>
			</div>
		</td>
	</tr>
</s:if>
</tbody>
</table>
</s:if>
</s:form>
</div>
</div>
</div>
</div>

