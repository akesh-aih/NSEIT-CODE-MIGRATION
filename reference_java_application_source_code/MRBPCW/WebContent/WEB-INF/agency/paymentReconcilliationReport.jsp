<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<script type="text/javascript" src="js/AES.js"></script>
<script type="text/javascript">
var clickedDisciplineType;
var selectedFromDate;
var selectedToDate;
var selectedStatus;

	$(document).ready(function() 
	{
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
	        onSelect: function(selected) {
	          $("#datepicker4").datepicker("option","minDate", selected)
	        }
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
	        onSelect: function(selected) {
	           $("#datepicker3").datepicker("option","maxDate", selected)
	        }
	    });
	    
	    $(".disableEnroll").change(function(){
	    });
	    
	    			$("#datepicker3").attr("disabled", "disabled"); 
					$("#datepicker4").attr("disabled", "disabled");
					$("#datepicker3" ).datepicker('disable');
					$("#datepicker4" ).datepicker('disable');
					
				var paymentMode=$("#paymentReconcStatusVal").val();
			
				if(paymentMode == "" || paymentMode == '' || paymentMode == "3" || paymentMode == '3')
				{
					$("#datepicker3").attr("disabled", "disabled"); 
					$("#datepicker4").attr("disabled", "disabled");
					$("#datepicker3" ).datepicker('disable');
					$("#datepicker4" ).datepicker('disable');
					
				}
				else
				{
					$("#datepicker3").removeAttr("disabled");
					$("#datepicker4").removeAttr("disabled");
					$("#datepicker3" ).datepicker('enable');
					$("#datepicker4" ).datepicker('enable');
				}
					
	});
	
	function disableEnrollmentId()
	{
	    	var disableEnrollId = "false";
	    	$(".disableEnroll").each(function(index, object){
	    		if($(object).val()!="")
	    		{
	    			disableEnrollId="true";
	    		}
	    	});
	    	
	    	if(disableEnrollId=="true")
	    	{
	    		$("#userId").attr("disabled", "disabled");
	    		
	    		
	    	}
	    	else
	    	{
	    		$("#userId").removeAttr("disabled");
	    	}
	    	
	    		var paymentMode=$("#paymentReconcStatusVal").val();
			
				if(paymentMode == "" || paymentMode == '' || paymentMode == "3" || paymentMode == '3')
				{
					$("#datepicker3").attr("disabled", "disabled"); 
					$("#datepicker4").attr("disabled", "disabled");
					$("#datepicker3" ).datepicker('disable');
					$("#datepicker4" ).datepicker('disable');
					
				}
				else
				{
					$("#datepicker3").removeAttr("disabled");
					$("#datepicker4").removeAttr("disabled");
					$("#datepicker3" ).datepicker('enable');
					$("#datepicker4" ).datepicker('enable');
				}
	}

	function clearSearch()
	{
		$("#error-massage3").hide();
		$("#error-massage_user").hide();
		$("#gridDiv1").hide();
		$("#gridDiv2").hide();
		$("#paginationForm").hide();
		$("#userId").val("");
		$("#disciplineVal").val("");
		$("#paymentReconcStatusVal").val("");
		
		$("#datepicker3").val("");
		$("#datepicker4").val("");
		
		$("#userId").removeAttr("disabled");
		$("#disciplineVal").removeAttr("disabled");
		$("#paymentReconcStatusVal").removeAttr("disabled");
		$("#datepicker3").attr("disabled", "disabled"); 
		$("#datepicker4").attr("disabled", "disabled");
		$("#datepicker3" ).datepicker('disable');
		$("#datepicker4" ).datepicker('disable');
	}
	
	
	function getSearchResult(){
		var fromDate = $("#datepicker3").val();
		var toDate =  $("#datepicker4").val();
		
		var message = "";
		if (fromDate!="" && toDate=="" ){
			message = "Please select To Date."+"##";
		}
		if (fromDate=="" && toDate!="" ){
			message = message+"Please select From Date."+"##";
		}
		if (fromDate!=""&& toDate!=""){
			if ($("#datepicker3").datepicker( "getDate" ) > $("#datepicker4").datepicker( "getDate" ) ) {
			  message = message+"From date cannot be greater than To date."+"##";
			 }
		}
		 
		if (message!=""){
				var ulID = "error-ul_user";
				var divID = "error-massage_user";
				createErrorList(message, ulID, divID);
				return false;
		}else{
			$("#paymentReconcilliation").attr('action',"ReportAction_showPaymentReconcilliationResult.action");
			$("#paymentReconcilliation").submit();
		}
	}
	
	function disableDropDowns()
	{
		var enrlmntId=$("#userId").val();
		
		if(enrlmntId == "")
		{
			$("#disciplineVal").removeAttr("disabled");
			$("#paymentReconcStatusVal").removeAttr("disabled");
		}
		else
		{
			$("#disciplineVal").attr("disabled", "disabled");
			$("#paymentReconcStatusVal").attr("disabled", "disabled"); 
			$("#datepicker3").attr("disabled", "disabled"); 
			$("#datepicker4").attr("disabled", "disabled");
			$("#datepicker3" ).datepicker('disable');
			$("#datepicker4" ).datepicker('disable');
		}
	}
	
	
$(document).ready(function() 
{ 
    	$("a:contains('Payment Reconcilliation Report')").html('<span class="fadeSubmenu">Payment Reconcilliation Report</span>')}
); 

function validatePaymentReconcillationSearch(){

	var enrlmntId=$("#userId").val();
	
	var labelId=$("#labelId").val();
	
	var paymentMode=$("#paymentMode").val();
	var datepicker3=$("#datepicker3").val();
	var datepicker4=$("#datepicker4").val();
	
	var message = "";

	if(paymentMode=="" && enrlmntId == ""){
		message = message + "Please select Mode of Payment"+"##";
	}
	else
	{
		$("#error-massage3").hide();
	}

	if(message != ""){
		var ulID = "error-ul3";
		var divID = "error-massage3";
		createErrorList(message, ulID, divID); 
		return false;
	}
	else
		return true;
}

function getPaymentReconcilliationDetails(discipline, status, fromDate, toDate,totalCount)
{
  	  		var url = "ReportAction_showPaymentReconcilliationCandidateWiseData.action?statusVal="+status+"&disciplineScriptVal="+discipline+"&startDate="+fromDate+"&endDate="+toDate+"&totalCount="+totalCount;
  	  		$("#summary").attr('action',url);
  	  		$("#summary").submit();
}
function exportToExcel(discipline, status, fromDate, toDate)
{
		window.open("ReportAction_generatePaymentReconcilliationExcelReport.action?statusVal="+status+"&disciplineScriptVal="+discipline+"&startDate="+fromDate+"&endDate="+toDate);
		return false;
} 

function exportToExcelforUserID(userId)
{
	window.open("ReportAction_generatePaymentReconcilliationExcelReport.action?userId="+userId);
		return false;
}
</script>

<div class="main-body">
<div class="fade" id="pop3"></div>

<div id="SelectTest">
<h1 class="pageTitle" title="Payment Reconcilliation Report">Payment Reconciliation Report - Challan</h1>
<div class="hr-underline2"></div>
<div id="error-massage3" style="display:none" class="error-massage">
      		<div class="error-massage-text">
      		<ul style="margin:0; margin-left:20px; padding:0;" id="error-ul3">
      	
      		</ul>      	
      	 </div>
</div>

<div class="SelectContainer">
<div class="clear">
<s:form method="post" action="ReportAction" name="paymentReconcilliation" id="paymentReconcilliation">
<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
<div id="error-massage_user" style="display:none" class="error-massage" >
				 <div class="error-massage-text" style="margin:0; margin-left:-40px; margin-top:30px; padding:0;">
			      	<ul style="margin:0; margin-left:20px; padding:0;" id="error-ul_user">
			      	</ul>
      		</div>
      			</div>

<div>
<div class="field-label"><s:text name="agencypaymentapproval.enrollmentID"/>&nbsp;</div>
	<div>
			<s:textfield name="userId" id="userId" maxlength="15" cssClass="inputField " onblur="disableDropDowns();"></s:textfield>
	</div>
</div>

<div class="height10"></div>
<div class="WeightBold"><s:text name="agencypaymentapproval.or"/></div>
<div class="height10"></div>

<!-- Row One Start -->
<div class="clear">
<div class="fl-left fifty">
<div class="field-label"><s:text name="agencypaymentapproval.discipline"/>&nbsp;</div>
<div>
<s:select label="Discipline" name="disciplineVal"   id = "disciplineVal" cssClass="disableEnroll"
		headerKey=""  headerValue="All" list="disciplineListMap" value="%{disciplineVal}" onchange="disableEnrollmentId();"/>
</div>
</div>
<div class="fl-rigt fifty">
<div class="field-label">Status &nbsp;</div>
<div>
	<s:select label="Status" name="paymentReconcStatusVal"   id = "paymentReconcStatusVal" cssClass="disableEnroll"
		headerKey=""  headerValue="All" list="statusListMap" value="%{paymentReconcStatusVal}" onchange="disableEnrollmentId();"/>
</div>
</div>
</div>
<div class="height10 clear"></div>
<!-- for validation message -->
<br/>

<!-- Row Three Start -->
<div class="clear">
<div>
<div class="fl-left fifty">
<div class="field-label"><s:text name="agencypaymentapproval.paymentfromdate"/>&nbsp;</div>
<div>
	<s:textfield name="fromDate" id="datepicker3" disabled="disabled" readonly="true" cssClass="inputDate disableEnroll" onblur="disableEnrollmentId();"></s:textfield>
</div>
</div>
<div class="fl-rigt fifty">
<div class="field-label"><s:text name="agencypaymentapproval.paymenttodate"/>&nbsp;</div>
<div>
	<s:textfield name="toDate" id="datepicker4" disabled="disabled" readonly="true" cssClass="inputDate disableEnroll" onblur="disableEnrollmentId();"></s:textfield>
</div>
</div>
</div>
</div>
<!-- Row Three End -->
 
<!-- Row Four Start -->
<div class="clear">
<br/>
<br/>
<div class="fl-left fifty">
<div>
<input type="button" value="Search" title="Search" class="submitBtn button-gradient" onclick="getSearchResult();"/>&nbsp;&nbsp;
<input type="button" value="Clear" title="<s:text name="agencypaymentapproval.clear"/>" class="submitBtn button-gradient" onclick="clearSearch();"/>&nbsp;&nbsp;
</div>
</div>
<div class="fl-rigt fifty">&nbsp;</div>
</div>
<!-- Row Four End -->
<div class="height20"></div>

</s:form>
</div>
<!-- Payment Fields End -->
</div>
<div class="AgencyPayDrid" id="gridDiv1">
<s:if test='%{displayMainResult=="Y"}'>
<div class="clear hr-dashline"></div>
<s:form method="post" action="ReportAction" id="summary" name="summary">
<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
<s:hidden name="disciplineVal" value="%{disciplineVal}"></s:hidden>
<s:hidden name="paymentReconcStatusVal" value="%{paymentReconcStatusVal}"></s:hidden>
<s:hidden name="fromDate" value="%{fromDate}"></s:hidden>
<s:hidden name="toDate" value="%{toDate}"></s:hidden>
<table cellspacing="0" border="1" width="500" class="table_2" bordercolor="#CCC">
<thead>
<tr>
<th style="text-align: center;" width="310" >Challan Payment Reconciliation</th>
<th style="text-align: center;" width="190" >Status = <s:property value="totAmtPymtReconcMainResult.status"/></th>
</tr>
</thead>
<tbody>	
<tr>
	<td align="center" colspan="4">
		<table width="100%">
		<thead>
			<tr>
			<th style="text-align: center;" width="50">Sr.No</th>
			<th style="text-align: center;" width="100">Discipline</th>
			<th style="text-align: center;" width="150" >No. of Candidates</th>
			<th style="text-align: center;" width="200" >Applicable Fees Amount</th>
			</tr>
		</thead>
		<tbody>
		<s:if test="%{paymentReconciliationReportData!=null}">
		<s:iterator value="paymentReconciliationReportData" var="bean" status="status">
		<tr>
		<td>
			<s:property value="#status.index + 1"/>
		</td>
		<td>
			<s:property value="discipline"/>
		</td>
		<td align="center">
			<u><a href="#" onclick="getPaymentReconcilliationDetails('<s:property value="discipline"/>', '<s:property value="totAmtPymtReconcMainResult.status"/>', '<s:property value="fromDate"/>', '<s:property value="toDate"/>','<s:property value="noOfCandidates"/>');"><s:property value="noOfCandidates"/></a></u> 
		</td>
		<td align="center">
			<s:property value="applicableFeesAmt"/>
		</td>
		</tr>
		</s:iterator>
		</s:if> 
		<s:else>
			<tr>
				<td colspan="4" align="center">
					<div>
						<b>No record Found</b>
					</div>
				</td>
			</tr>
		</s:else>
		</tbody>
		</table>
	</td>
</tr>
<tr>
	<td align="center"><b>Total Amount</b></td>
	<td align="center">
		<b>
			<s:property value="totAmtPymtReconcMainResult.totalApplicableFeesAmt"/>
		</b>
	</td>
</tr>
</tbody>
</table>
<br/>
<br/>
</s:form>
</s:if>
</div>
<s:if test="%{paymentReconciliationReportDataWithDetail!=null && paymentReconciliationReportDataWithDetail.size>0}">
<s:form action="ReportAction_showPaymentReconcilliationCandidateWiseData.action" name="paginationForm" id="paginationForm">
<s:hidden name="statusVal" value="%{#request['statusVal']}"></s:hidden>
<s:hidden name="disciplineScriptVal" value="%{#request['disciplineScriptVal']}"></s:hidden>
<s:hidden name="totalCount" value="%{#request['totalCount']}"></s:hidden>
<s:hidden name="disciplineVal" value="%{disciplineVal}"></s:hidden>
<s:hidden name="paymentReconcStatusVal" value="%{#paymentReconcStatusVal}"></s:hidden>
<s:hidden name="fromDate" value="%{fromDate}"></s:hidden>
<s:hidden name="toDate" value="%{toDate}"></s:hidden>
<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
	  <input type = "button" value = "Export To EXCEL"  class="submitBtn button-gradient" onclick = "exportToExcel('<%=request.getParameter("disciplineScriptVal")%>', '<%=request.getParameter("statusVal")%>', '<s:property value="fromDate"/>', '<s:property value="toDate"/>');"/>
  <div style="overflow: auto; ">
   <br/>
<table width="100%">
	<tr class="box-header">
		<td align="left" width="30%" >Candidate Details</td>
		<td align="center" width="30%" class="pagination-label"></td>
		<td width="40%"><%@ include file="../pagination.jsp" %></td>
	</tr>
</table>
  <table cellspacing="0" border="1" width="100%" class="table_2" bordercolor="#CCC">
				    <thead>
<tr>
<th style="text-align: center;">Sr.No.</th>
<th style="text-align: center;">Registration ID</th>
<th style="text-align: center;">Discipline</th>
<th style="text-align: center;">Candidate Name</th>
<th style="text-align: center;">Category</th>
<th style="text-align: center;">Mobile Number</th>
<th style="text-align: center;">Journal No.</th>
<th style="text-align: center;">Challan Date</th>
<th style="text-align: center;">Branch Name</th>
<th style="text-align: center;">Branch Code</th>
<th style="text-align: center;">Candidate Fees</th>
<th style="text-align: center;">Applicable Fees</th>
<th style="text-align: center;">Bank Amount</th>
<th style="text-align: center;">Status</th>
</tr>
</thead>
<tbody>
<s:if test="%{paymentReconciliationReportDataWithDetail!=null}">
		<s:iterator value="paymentReconciliationReportDataWithDetail" var="bean" status="status">
		<tr>
		<td>
			<s:property value="#status.index + 1"/>
		</td>
		<td>
			<s:property value="userID"/>
		</td>
		<td align="center">
			<s:property value="discipline"/>
		</td>
		<td>
			<s:property value="candidateName"/>
		</td>
		<td>
			<s:property value="category"/>
		</td>
		<td>
			<s:property value="mobileNo"/>
		</td>
		<td>
			<s:property value="journalNo"/>
		</td>
		<td>
			<s:property value="challanDate"/>
		</td>
		<td>
			<s:property value="branchName"/>
		</td>
		<td>
			<s:property value="branchCode"/>
		</td>
		<td>
			<s:property value="candidateFees"/>
		</td>
		<td>
			<s:property value="applicableFeesAmt"/>
		</td>
		<td>
			<s:property value="bankAmt"/>
		</td>
		<td>
			<s:property value="status"/>
		</td>
		</tr>
		</s:iterator>
</s:if> 
<s:else>
	<tr>
		<td colspan="14" align="center">
			<div>
				<b>No record Found</b>
			</div>
		</td>
</tr>
</s:else> 
</tbody>
  		</table>
</div>
  </s:form>
</s:if>

<div class="AgencyPayDrid" id="gridDiv2">
<s:if test='%{displayCanidateWiseResult=="Y"}'>
<div class="clear hr-dashline"></div>
<s:form method="post" action="ReportAction">
<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
<s:if test="%{paymentReconcilliationForUserID!=null}">

<div class="AgencyPayDrid" id="gridDiv33">
		<br/>
		<br/>
			<input type = "button" value = "Export To EXCEL"  class="submitBtn button-gradient" onclick = "exportToExcelforUserID('<s:property value="paymentReconcilliationForUserID.userID"/>');"/>
		</div>
</s:if>
<table cellspacing="0" border="1" width="100%" class="table_2" bordercolor="#CCC">
<thead>
<tr>
<th style="text-align: center;">Sr.No.</th>
<th style="text-align: center;">Registration ID</th>
<th style="text-align: center;">Discipline</th>
<th style="text-align: center;">Candidate Name</th>
<th style="text-align: center;">Category</th>
<th style="text-align: center;">Mobile Number</th>
<th style="text-align: center;">Journal No.</th>
<th style="text-align: center;">Challan Date</th>
<th style="text-align: center;">Branch Name</th>
<th style="text-align: center;">Branch Code</th>
<th style="text-align: center;">Candidate Fees</th>
<th style="text-align: center;">Applicable Fees</th>
<th style="text-align: center;">Bank Amount</th>
<th style="text-align: center;">Status</th>
</tr>
</thead>
<tbody>
<s:if test="%{paymentReconcilliationForUserID!=null}">
<tr>
		<s:iterator value="paymentReconcilliationForUserID" var="bean" status="status">
		
		<td>
			<s:property value="#status.index + 1"/>
		</td>
		<td>
			<s:property value="userID"/>
		</td>
		<td align="center">
			<s:property value="discipline"/>
		</td>
		<td>
			<s:property value="candidateName"/>
		</td>
		<td>
			<s:property value="category"/>
		</td>
		<td>
			<s:property value="mobileNo"/>
		</td>
		<td>
			<s:property value="journalNo"/>
		</td>
		<td>
			<s:property value="challanDate"/>
		</td>
		<td>
			<s:property value="branchName"/>
		</td>
		<td>
			<s:property value="branchCode"/>
		</td>
		<td>
			<s:property value="candidateFees"/>
		</td>
		<td>
			<s:property value="applicableFeesAmt"/>
		</td>
		<td>
			<s:property value="bankAmt"/>
		</td>
		<td>
			<s:property value="status"/>
		</td>
		</s:iterator>
		</tr>
</s:if> 
<s:else>
	<tr>
		<td colspan="14" align="center">
			<div>
				<b>No record Found</b>
			</div>
		</td>
</tr>
</s:else> 
</tbody>
</table>
</s:form>
</s:if>
</div>
</div>
</div>
