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
	        numberOfMonths: 1
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
	        numberOfMonths: 1
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
	
	function callClearFunc(){
		$("#error-massage3").hide();
		$("#error-massage_user").hide();
		$("#gridDiv1").hide();
		//$("#gridDiv2").hide();
		//$("#actionMsgError").hide();
		//$("#userId").val("");
		$("#paymentStatusId").val("-1");
		$("#disciplineId").val("0");
		$("#datepicker3").val("");
		$("#datepicker4").val("");
		$("#paymentModeId").val("0");
		//$("#labelId").removeAttr("disabled");
		$("#datepicker3").removeAttr("disabled");
		$("#datepicker4").removeAttr("disabled");
	}
	

function changeAction(){

	var fromDate = $("#datepicker3").val();
	var toDate =  $("#datepicker4").val();
	var message = "";
	if (fromDate!="" && toDate=="" ){
		message = "Please select To Date ";
	}
	if (fromDate=="" && toDate!="" ){
		message = message+"Please select From Date ";
	}
	if (fromDate!="" && toDate!=""){
		if($("#datepicker3").datepicker( "getDate" ) > $("#datepicker4").datepicker( "getDate" )){
			message = "From Date cannot be greater To Date";
		}
	}
	if (message!=""){
			var ulID = "error-ul_user";
			var divID = "error-massage_user";
			createErrorList(message, ulID, divID);
			return false;
	}else{
		$("#paymentCollectionReport").attr('action',"ReportAction_getPaymentCollectionReport.action");
		$("#paymentCollectionReport").submit();
		return true;
	}

}
function genrateExcelReport(){
	$("#paymentCollectionReport").attr('action',"ReportAction_exportPaymentCollectionReportToExcel.action");
	$("#paymentCollectionReport").submit();
}
</script>
<style>
.onetime{
  	display: none;
  }
  .container .container { width:100%; float:left; }
  .ui-datepicker-trigger  {margin-top:8px; }
  </style>

<style>
.ui-datepicker-trigger { margin-top:8px; }
</style>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="container">
<div class="fade" id="pop3"></div>
 
<div id="SelectTest" class="container common_dashboard" >

<h1 class="pageTitle" title="Payment Collection Report">Payment Collection Report</h1>
<div class="hr-underline2"></div>
<div id="error-massage_user" style="display:none" class="error-massage" >
				 <div class="error-massage-text" style="margin:0; margin-left:-40px; margin-top:30px; padding:0;">
			      	<ul style="margin:0; margin-left:20px; padding:0;" id="error-ul_user">
			      	</ul>
      		</div>
      			</div>

<!-- Box Container Start -->


<!-- Payment Fields Start -->
<div class="clear">
	
<s:if test='%{paymentCollReportFlag=="true"}'>
	<div style="border:#999 1px solid;padding:3px;color:green;" id="successMessage">
	<s:property value="paymentCollReportMsg"/>
	</div>
</s:if>
<s:form action="ReportAction" id="paymentCollectionReport">
<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>

<div class="row">
	<div class="col-md-4">
		<div class="form-group">
			<label class="control-label">Mode Of Payment</label>
			<s:select label="Status" name="paymentModeId" 
			headerKey="0" headerValue="All" list="paymentModeMap" id="paymentModeId" value="%{paymentModeId}" cssClass="form-control" />
		</div>
	</div>
	<div class="col-md-4">
		<div class="form-group dateInput">
			<label class="control-label">Payment From Date</label>
			<s:textfield name="fromDate" id="datepicker3" value="%{fromDate}" disabled="disabled" readonly="true" cssClass="disableEnroll form-control"></s:textfield>
		</div>
	</div>
	<div class="col-md-4">
		<div class="form-group dateInput">
			<label class="control-label">Payment To Date</label>
			<s:textfield name="toDate" id="datepicker4" readonly="true" cssClass="disableEnroll form-control"></s:textfield>
		</div>
	</div>
</div>
<div class="row mt10">
	<div class="col-md-2 mt10">
		<input type="button" value="Search" class="submitBtn button-gradient btn-block" onclick="changeAction();"/>
	</div>
	<div class="col-md-2 mt10">
		<input type="button" value="Clear" title="Clear" class="submitBtn button-gradient btn-block" onclick="callClearFunc();"/>
	</div>
</div>

<!--<table width="100%" cellspacing="1" cellpadding="3" class="contenttable" >
	 <tr>
		<td width="20%">Mode Of Payment&nbsp;</td>
		<td><s:select label="Status" name="paymentModeId" 
		headerKey="0" headerValue="All" list="paymentModeMap" id="paymentModeId" value="%{paymentModeId}"/></td>
	</tr> 
	<tr>
		<td>Payment From Date&nbsp;</td>
		<td><s:textfield name="fromDate" id="datepicker3" value="%{fromDate}" disabled="disabled" readonly="true" cssClass="inputDate disableEnroll"></s:textfield></td>
	
		
	</tr>
	<tr>
	<td>Payment To Date&nbsp;</td>
		<td><s:textfield name="toDate" id="datepicker4" readonly="true" cssClass="inputDate disableEnroll" ></s:textfield></td>
		</tr>
		<tr>
		<td>&nbsp;</td>
		<td><input type="button" value="Search" class="submitBtn button-gradient" onclick="changeAction();"/> 
		<input type="button" value="Clear" title="Clear" class="submitBtn button-gradient" onclick="callClearFunc();"/>
		</td>
		</tr>
		
</table>-->
<br/>

<div class="clear">
 
<div class="fl-rigt fifty">&nbsp;</div>
</div>
<!-- Row Four End -->

<s:token />
</s:form>

<div class="height20"></div>


<!-- Dash Line Start -->
<div class="clear hr-dashline"></div>
<!-- Dash Line End -->
<div class="AgencyPayDrid" id="gridDiv1" >
<s:form method="post" action="ReportAction_getPaymentCollectionReport.action" name="paginationForm">
<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
<s:hidden name="disciplineId" value="%{disciplineId}"></s:hidden>
<s:hidden name="paymentStatusId" value="%{paymentStatusId}"></s:hidden>
<s:hidden name="paymentModeId" value="%{paymentModeId}"></s:hidden>
<s:hidden name="userId" value="%{userId}"></s:hidden>
<s:hidden name="fromDate" value="%{fromDate}"></s:hidden>
<s:hidden name="toDate" value="%{toDate}"></s:hidden>

<s:if test='%{showDataGridFlag == "true"}'>
<table width="100%" align="left">
	<tr class="box-header">
		<td align="left" width="30%" >Payment Collection Report</td>
		<td align="center" width="30%" class="pagination-label">Total <font color="red"><s:property value="paymentCollectionReportDetailListSize"/></font> Record Found </td>
		<td width="40%">
		<s:if test='%{paymentCollectionReportDetailListSize=="0"}'>
			</s:if><s:else><%@ include file="../pagination.jsp" %></s:else>
		</td>
	</tr>
</table>
<div class="clear"></div>
<br/>

<div class="table-responsive tableFixHead">
	
<table cellspacing="0" border="1" width="100%" class="table_2 table" bordercolor="#CCC" align="center" >
<thead>
    <tr>
    <th>Sr. No</th>
    <th>Payment Date</th>
    <th>Payment Mode</th>
    <th>Total Amount Collected(in INR)</th>
    </tr>
</thead>
<tbody>
<s:iterator value="paymentCollectionReportDetailList" status="stat" var="currentObject">
		<tr>
			<td  style="width: 25%;" align="center">
					<s:property value="num" />
			</td>
			<td  style="width: 25%;" align="center">
				<s:property value="OPD_CREATED_DATE" />
			</td>
			<td style="width: 25%;" align="center">
				<s:property value="OPTM_PAYMENT_DESC" />
			</td>
			<td  style="width: 25%;" align="center">
				<s:property value="OPD_AMOUNT" />
			</td>
		</tr>
		</s:iterator>
<s:if test='%{availableRecordFlag == "false"}'>
	<tr>
		<td colspan="4" align="center">
			<div>
				<b>No Record Found</b>
			</div>
		</td>
	</tr>
</s:if>
</table>
</div>
<s:else>
<div class="clear"></div>
<br/> 
<br/>
<div class="row">
	<div class="col-md-3"> 
		<input type="button" value="Generate EXCEL Report" title="Generate EXCEL Report" class="submitBtn button-gradient" onclick="genrateExcelReport();"/>
	</div>
</div>
	 
</s:else>
</tbody>
</s:if>
<s:token />
</s:form>
</div>
</div>
</div>
</div>
 