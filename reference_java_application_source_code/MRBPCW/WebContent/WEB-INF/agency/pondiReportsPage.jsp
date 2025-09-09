<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<link href="css/common.css" rel="stylesheet" type="text/css" media="screen"/>
<link type="text/css" href="css/ui-lightness/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/AES.js"></script>
<script src="js/ScrollableTablePlugin_1.0_min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/login.js"></script>
<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
    <script>
$(document).ready(function() {
	enableFields();
});

function callFunc1()
{
	$("#error-massage_user").hide();
    $("#SearchResult").hide(); 
}


function validateInput() {
	var reportName=$("#pondiReports").val();
	var testDate=$("#pondiTestDate").val();
	var testBatch=$("#pondiTestBatch").val();
	var message = "";
	if (reportName==""){
		message = message+"Please select Name of Report.";
	}
	else
	{
		if(reportName == '3')
		{
			if (testDate=="")
			{
				message = message+"Please select Test Date.";
			}
			if (testBatch=="")
			{
				message = message+"Please select Test Batch.";
			}
		}
	}
	if (message!=""){
			var ulID = "error-ul_user";
			var divID = "error-massage_user";
			createErrorList(message, ulID, divID);
			return false;
	}else{
		changeAction();
	}
}

function changeAction()
{
	$("#summaryForm").attr('action',"AgencyAction_generateHTMLReportForPondi.action");
	$("#summaryForm").submit();
}

function callFunc()
{
	$("#error-massage_user").hide();
	$("#SearchResult").hide();
    $("#pondiReports").val("");
    $(".testDateTD").hide();
	$(".testBatchTD").hide();
	$("#pondiTestDate").val("");
	$("#pondiTestBatch").val("");
}
function exportToExcel()
{
	window.open("AgencyAction_generateReportForPU.action?pondiReports="+$("#pondiReports").val()+"&pondiTestDate="+$("#pondiTestDate").val()+"&pondiTestBatch="+$("#pondiTestBatch").val());
	return false;
} 
function enableFields()
{
	var reportName=$("#pondiReports").val();
	if(reportName == '')
	{
		callFunc();
	}
	else{
	if(reportName == '3')
	{
		$(".testDateTD").show();
		$(".testBatchTD").show();
	}
	else
	{
		$(".testDateTD").hide();
		$(".testBatchTD").hide();
		$("#pondiTestDate").val("");
		$("#pondiTestBatch").val("");
	}
	}
}
</script>
<script type="text/javascript">
  $(document).ready(function(){
		$(".menuActive").closest(".submenu-item").addClass("submenu-itemActive");
	});
      $(function () {
          $('#Table1, #Table2').Scrollable({
              ScrollHeight: 250,
          });
      });
  </script>
  
  <style type="text/css">
  .tabDiv {width: 92.1% !important;}
	.blue-table {table-layout: fixed;}
	.tableHead {
    padding-right: 16px;
    border: 1px solid #ddd;
}
table.personsl-dtl.table-bordered.blue-table, table.personsl-dtl.table-bordered.blue-table th {
    border-top: 0px;
    border-bottom: 0px;
    border-collapse: collapse !important;
}
	.tableHead table, .tableHead table th:last-child {border-right: 0px !important;}
	table.blue-table td {word-wrap: break-word;}
</style>
<div class="main-body container common_dashboard">
<div class="fade" id="pop3"></div>
<div id="dashboard" class="dashboard tabDiv effect2"><div class="accordions"><div id="CandidateDiv">

<s:form action="AgencyAction" id = "summaryForm" method="post">
<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
<h1 class="pageTitle" title="TRBPGCT Report">TRBAPR Report</h1>
<div class="accordion">
<div id="error-massage_user" style="display:none" class="error-massage" >
	<div class="error-massage-text">
		<ul id="error-ul_user">
		</ul>
	</div>
</div>
<!-- Box Container Start -->
<div class="clear">
<div class="row">
	<div class="col-sm-4">
	<div class="form-group">
		<label class="control-label">Name of Report <span class="manadetory-fields">*</span></label>
		 <s:select list = "pondiReportsList" name="pondiReports" label = "Name"   id="pondiReports" cssClass="form-control"
						headerKey="" headerValue = "Select Report" value="%{pondiReports}" onchange="callFunc1();"/>
	</div>
		
	</div>
	<div class="col-sm-4 testDateTD">
		<div class="form-group">
			<label class="control-label">Test Date</label>
			 <s:select list = "testDatList" name="pondiTestDate" label = "Name"   id="pondiTestDate"  class="form-control"
						value="%{pondiTestDate}" />
		</div>
	</div>
	<div class="col-sm-4 testBatchTD">
		<div class="form-group">
			<label class="control-label">Test Batch</label>
			<s:select list = "testBatchList" name="pondiTestBatch" label = "Name"   id="pondiTestBatch"  class="form-control"
						value="%{pondiTestBatch}" />
		</div>		
	</div>
</div>
<div class="row mt10 mb10">
	<div class="col-sm-2 mt10">
		<input type = "button" value = "Search"  class="ripple1 btn btn-warning btn-block" onclick = "return validateInput()"/>
	</div>
	<div class="col-sm-2 mt10">
		<input type="button" name="button2" id="button2" value="Clear" class="btn btn-default btn-block" onclick="callFunc();"/>
	</div>
</div>
  <label for="select"></label>
</div>
<s:token/>
</s:form>
 

<div id="SearchResult">
<s:if test='%{showCandidateDetailsReport=="Y"}'>
<!-- Dash Line Start -->
<div class="clear hr-dashline"></div>
<!-- Dash Line End -->
 
<div class="AgencyPayDrid" id="gridDiv1">
<s:form method="post" action="AgencyAction_generateHTMLReportForCandidateDetails.action" name="paginationForm">
<!-- DD Payment Start -->
<div id="option1">
<s:token/>
<!-- <table width="100%"> -->
<!-- 	<tr class="box-header"> -->
<!-- 		<td align="left" width="30%" >BSNL Report Details</td> -->
<!-- 		<td align="center" width="30%" class="pagination-label"></td> -->
<!-- 		<td width="40%">-->
<!-- 		</td> -->
<!-- 	</tr> -->
<!-- </table> -->
<!-- <br/> -->

<s:if test='%{pondiReportFlag=="1"}'>
<!-- <table width="100%">
	<tr class="box-header">
		<td align="left" width="30%" >TRBPGCT Category Wise Report Details</td>
		<td align="center" width="30%" class="pagination-label"></td>
		<td width="40%">			
		</td>
	</tr>
</table> -->
<div class="row">
	<div class="col-md-12">
		<p>TRBPGCT Category Wise Report Details</p>
	</div>
</div>

<div class="row">
	<div class="col-sm-12 tableFixHead table-responsive">
<table cellspacing="0" rules="all" border="0" width="100%" class="personsl-dtl table-bordered table">
					<thead>
						<tr>
							<th style="text-align: left; ">Name of Post</th>
							<!-- <th style="text-align: left; ">Specialization</th>  -->
							<!-- <th >General</th>   --> 
							<th>OC</th>
							<th>SC</th>
							<th>ST</th>
							<th>MBC/DNC</th>
							<th>BC</th>
							<th>BCM</th>
							<th>Total</th>
						</tr>
					</thead>
					<tbody>
					<s:if test="%{candidateDetailsList!=null}"> 
						<s:iterator value="candidateDetailsList" var="bean" status="status">
							<tr>							
								<td class="wordWrap" style="text-align: left;">
									<s:property value="otmPostName"/>
								</td>
								
								<%-- <td >
									<s:property value="general_unreserved"/>
								</td>	 --%>
								<td class="wordWrap">
									<s:property value="obc"/>
								</td>
								<td class="wordWrap">
									<s:property value="sc"/>
								</td>
								<td class="wordWrap">
									<s:property value="st"/>
								</td>
								<td class="wordWrap">
									<s:property value="mbc"/>
								</td>
								<td class="wordWrap">
									<s:property value="bc"/>
								</td>
								<td class="wordWrap">
									<s:property value="bcm"/>
								</td>
								<td class="wordWrap">
									<s:property value="total"/>	
								</td>								
							</tr>
						</s:iterator>
					</s:if> 
					<s:if test="%{candidateDetailsList==null}"> 
<tr>
	<td colspan="13" align="center" style="width: 1280px;">
		<b>No record Found</b>	
	</td>
</tr>
</s:if>
				</tbody>
</table>
	
	</div>
</div>
</s:if>
<s:elseif test='%{pondiReportFlag=="2"}'>
<!-- For Test center wise report -->
<!-- <table width="100%">
	<tr class="box-header">
		<td align="left" width="30%" >City Wise Report Details</td>
		<td align="center" width="30%" class="pagination-label"></td>
		<td width="40%">			
		</td>
	</tr>
</table> -->
<div class="row">
	<div class="col-md-12">
		<p>City Wise Report Details</p>
	</div>
</div>
<div class="row">
	<div class="col-sm-12">
<!-- <div class="tableHead">
<table cellspacing="0" border="0" width="100%" class="personsl-dtl table-bordered table-bordered blue-table">
</table>
</div> -->
<div class="scroll tableFixHead table-responsive">
<table cellspacing="0" border="0" width="100%" class="personsl-dtl table-bordered table-bordered table">
<thead>
    <tr>
	   <th>Name of City</th>
	   <th>Post Graduate Assistants</th>
	   <th>Physical Education Director Grade - 1</th>
	   <th>Grand Total</th>
    </tr>
</thead>
<tbody>
	<s:if test="%{candidateDetailsList!=null}"> 
		<s:iterator value="candidateDetailsList" var="bean" status="status">
			<tr>
			    <td class="wordWrap">
					<s:property value="otmPostName"/>
				</td>
				<td class="wordWrap">
					<s:property value="am_java"/>
				</td>
				<td class="wordWrap">
					<s:property value="am_dotnet"/>
				</td>
				<td class="wordWrap">
					<s:property value="total"/>
				</td>
						
			</tr>
		</s:iterator>
	</s:if>
	<s:if test="%{candidateDetailsList==null}"> 
<tr>
	<td colspan="13" align="center">
		<b>No record Found</b>	
	</td>
</tr>
</s:if>
</tbody>

</table>
</div>
	</div>
</div>
</s:elseif>
</div>
<s:if test="%{candidateDetailsList!=null}"> 
<!-- Button Start -->
<div class="row mt10">
<div class="col-sm-3 mt10">
	<input type = "button" value = "Generate EXCEL Report"  class="ripple1 btn btn-block btn-warning" onclick = "exportToExcel();"/>
</div>
</div>
<!-- Button End -->
</s:if>
</div>
</div>
<!-- DD Payment End -->
<s:token/>
</s:form>

</s:if>
</div>
</div>
</div></div></div></div>
<div class="height20"></div>