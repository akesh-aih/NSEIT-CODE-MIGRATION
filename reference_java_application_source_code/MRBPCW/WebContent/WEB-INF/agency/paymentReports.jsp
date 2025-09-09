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
			maxDate: 0,
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
			maxDate: 0,
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
	
	function resetPaySearch(){
		$("#error-massage3").hide();
		$("#gridDiv1").hide();
		$("#gridDiv2").hide();
		$("#actionMsgError").hide();
		$("#userId").val("");
		$("#paymentStatusId").val("-1");
		$("#disciplineId").val("0");
		$("#datepicker3").val("");
		$("#datepicker4").val("");
		$("#paymentModeId").val("0");
		$("#labelId").removeAttr("disabled");
		$("#datepicker3").removeAttr("disabled");
		$("#datepicker4").removeAttr("disabled");
	}
	
function changeAction(){
	$("#paymentReport").attr('action',"ReportAction_getPaymentReport.action");
	$("#paymentReport").submit();
}
</script>

<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="main-body">
<div class="fade" id="pop3"></div>

<div id="SelectTest">

<h1 class="pageTitle" title="Payment Report">Payment Report</h1>
<div class="hr-underline2"></div>
<div id="error-massage3" style="display:none" class="error-massage">
      		<div class="error-massage-text">
      		<ul style="margin:0; margin-left:20px; padding:0;" id="error-ul3">
      		</ul>      	
      	 </div>
</div>

<!-- Box Container Start -->


<!-- Payment Fields Start -->
<div class="clear">
<s:form action="ReportAction" id="paymentReport">
<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
<table width="100%">
	<tr>
		<td class="field-label">User ID&nbsp;</td>
		<td><s:textfield name="userId" id="userId" onkeypress="return alphaNumeric(event)" ></s:textfield></td>
	</tr>
	<tr>
		<td class="field-label">Discipline&nbsp;</td>
		<td><s:select label="Discipline" name="disciplineId" 
		headerKey="0" headerValue="All" list="disciplineListMap" id="disciplineId" value="%{disciplineId}"/></td>
		
		<td class="field-label">Mode Of Payment&nbsp;</td>
		<td><s:select label="Status" name="paymentModeId" 
		headerKey="0" headerValue="All" list="paymentModeMap" id="paymentModeId" value="%{paymentModeId}"/></td>
		
		<td class="field-label">Status&nbsp;</td>
		<td><s:select label="Status" name="paymentStatusId" 
		headerKey="0" headerValue="All" list="paymentStatusList" id="paymentStatusId" value="%{paymentStatusId}"/></td>
	</tr>
	<tr>
		<td class="field-label">From Date&nbsp;</td>
		<td><s:textfield name="fromDate" id="datepicker3" disabled="disabled" readonly="true" cssStyle="width:116px;" cssClass="inputDate disableEnroll"></s:textfield></td>
		
		<td class="field-label">To Date&nbsp;</td>
		<td><s:textfield name="toDate" id="datepicker4" disabled="disabled" readonly="true" cssStyle="width:116px;" cssClass="inputDate disableEnroll"></s:textfield></td>
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
<s:form method="post" action="ReportAction">
<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
<s:hidden name="disciplineId" value="%{disciplineId}"></s:hidden>
<s:hidden name="paymentStatusId" value="%{paymentStatusId}"></s:hidden>
<s:hidden name="paymentModeId" value="%{paymentModeId}"></s:hidden>
<s:hidden name="userId" value="%{userId}"></s:hidden>
<s:hidden name="fromDate" value="%{fromDate}"></s:hidden>
<s:hidden name="toDate" value="%{toDate}"></s:hidden>

<s:if test='%{showDataGridFlag == "true"}'>
<table width="100%">
	<tr class="box-header">
		<td align="left" >Payment Report</td>
		<td align="right">Total <font color="red"><s:property value="paymentReportDetailListSize"/></font> Record[s] Found </td>
	</tr>
</table>
<br/>
<table cellspacing="0" border="1" width="100%" class="table_2" bordercolor="#CCC">
<thead>
    <tr>
    <th style="width: 37px;">Sr. No</th>
    <th style="width: 79px;">User ID</th>
    <th style="width: 100px;">Name</th>
    <th style="width: 65px;">Discipline</th>
    <th style="width: 61px;">Payment Mode</th>
    <th style="width: 78px;">Payment Date</th>
    <th style="width: 78px;">Amount</th>
    <th style="width: 78px;">Bank Name</th>
    <th style="width: 78px;">Status</th>
    <th style="width: 78px;">Remarks</th>
    </tr>
</thead>
<tbody>
<s:iterator value="paymentReportDetailList" status="stat" var="currentObject">
		<tr>
			<td align="center" style="width: 37px;">
				<s:property value="#stat.count" />
			</td>
			<td align="center" style="width: 37px;">
				<s:property value="OUM_USER_ID" />
			</td>
			<td align="center" style="width: 37px;">
				<s:property value="CND_NAME" />
			</td>
			<td align="center" style="width: 37px;">
				<s:property value="OTM_TEST_NAME" />
			</td>
			<td align="center" style="width: 37px;">
				<s:property value="OPTM_PAYMENT_CODE" />
			</td>
			<td align="center" style="width: 37px;">
				<s:property value="OPD_CREATED_DATE" />
			</td>
			<td align="center" style="width: 37px;">
				<s:property value="OPD_AMOUNT" />
			</td>
			<td align="center" style="width: 37px;">
					<s:property value="OPD_BANK_NAME" />
			</td>
			<td align="center" style="width: 37px;">
					<s:property value="OPD_VALIDATED_STATUS" />
			</td>
			<td align="center" style="width: 37px;">
					<s:property value="OPD_REMARKS" />
			</td>
		</tr>
</s:iterator>
<s:if test='%{availableRecordFlag == "false"}'>
	<tr>
		<td colspan="10" align="center">
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