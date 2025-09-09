<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<script type="text/javascript" src="js/AES.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
	<s:if test='%{paymentStatus=="2"}'>
		$("#paymentMode").val("");
        $("#datepicker3").val("");
		$("#datepicker4").val("");
		$("#paymentModeDisp").hide();
		$("#paymentFromDate").hide();
		$("#paymentToDate").hide();
	</s:if>
	<s:if test='%{paymentDetailsDisplayFlag=="true"}'>
		$("#paymentMode").val("");
        $("#datepicker3").val("");
		$("#datepicker4").val("");
		$("#paymentModeDisp").hide();
		$("#paymentFromDate").hide();
		$("#paymentToDate").hide();
	</s:if>
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
	});
	
	
function validateSearchCriteria(){
	var paymentMode = $("#paymentMode").val();
	var paymentStatus = $("#paymentStatus option:selected").text();
	var datepicker3=$("#datepicker3").val();
	var datepicker4=$("#datepicker4").val();
	
	$("#paymentModeValue").val(paymentStatus);
	
	
	
	if (paymentStatus!="Payment Not Submitted"){
		var message = "";
		if(paymentMode==""){
			message = message + "Please select Mode of Payment"+"##";
		}
		else{
			$("#error-massage3").hide();
		}
	}
	
	if(datepicker3=="" && datepicker4 !=""){
		message = message + "Please select From Date"+"##";
	}
	
	if(datepicker3!="" && datepicker4 == ""){
		message = message + "Please select To Date "+"##";
	}
	if(message != ""){
		var ulID = "error-ul3";
		var divID = "error-massage3";
		createErrorList(message, ulID, divID); 
		return false;
	}
	else{
		return true;
	}
}

	function callFunc(){
		$("#paymentModeDisp").show();
		$("#paymentFromDate").show();
		$("#paymentToDate").show();
        $("#paymentMode").val("");
        $("#disciplineId").val("0");
        $("#paymentStatus").val("0");
        $("#datepicker3").val("");
		$("#datepicker4").val("");
		$("#error-massage3").hide();
		$("#resultcontainer").hide();
		
        
    } 
    
    
 function setPaymentMode() {
	var paymentMode = $("#paymentMode option:selected").text();
	var paymentStatus = $("#paymentStatus").val();
	var fromDate = $("#datepicker3").val();
	var toDate = $("#datepicker4").val();
	
	if (paymentMode=="Select Payment Mode"){
		paymentMode=""
	}
	$("#paymentModeValue2").val(paymentMode);
	$("#paymentStatus").val(paymentStatus);
	$("#fromDate").val(fromDate);
	$("#toDate").val(toDate);
	
	
}   
function exportToExcel(){
		//window.open("CandidateMgmtAction_generatePaymentReport.action");
		window.open("CandidateMgmtAction_generatePaymentReport.action?disciplineId="+$("#disciplineId").val()+
		"&paymentMode="+$("#paymentMode").val()+"&paymentStatus="+$("#paymentStatus").val()+
		"&fromDate="+$("#datepicker3").val()+"&toDate="+$("#datepicker4").val());
		return false;
}    


function hideDetails(){
	var paymentStatus = $("#paymentStatus option:selected").text();
	if (paymentStatus=="Payment Not Submitted"){
		$("#paymentMode").val("");
        $("#datepicker3").val("");
		$("#datepicker4").val("");
		$("#paymentModeDisp").hide();
		$("#paymentFromDate").hide();
		$("#paymentToDate").hide();
	}else{
		$("#paymentModeDisp").show();
		$("#paymentFromDate").show();
		$("#paymentToDate").show();
	}
	
}


</script>
<style>
.table_2 { font-size:12px;}
</style>
<div class="main-body">
<div class="fade" id="pop3"></div>
<div id="dashboard">
<s:form action="CandidateMgmtAction" id="preferredTestCentreReport" name="preferredTestCentreReportPage">
	<s:hidden name="paymentModeValue" id="paymentModeValue"/>
	<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
			<h1 class="pageTitle" title="Payment Details Report">Preferred Test Centre Report</h1>
			<div class="hr-underline2"></div>
			<div id="error-massage3" style="display:none" class="error-massage">
      				<div class="error-massage-text">
      						<ul style="margin:0; margin-left:20px; padding:0;" id="error-ul3">
      						</ul>      	
      	 			</div>
			</div>
<!-- Box Container Start -->
<div style="display:block; height:auto;">
  <table class="contenttable">
    <tr>
      <td width="180">Name of Program</td>
      <td   >
        <s:select label="Discipline" name="disciplineId"  cssClass="disableEnroll" 
		headerKey="0" headerValue="All" list="disciplineList" id="disciplineId" listValue="labelValue" listKey="labelId" value="%{disciplineId}"/>
        </td>
     </tr>
     
    
    
   
      
    
     <tr >
      <td> </td>
      <td >
<s:submit type="submit" key="Search" title="Search"  cssClass="submitBtn button-gradient" id="paymentSearchSubmit" method="showpreferredTestCentreReportSearchResult" onclick="return validateSearchCriteria();"></s:submit>&nbsp;&nbsp;
    <input type="button" name="button2" id="button2" value="Clear" class="submitBtn button-gradient" onclick="callFunc();"/>
      </td>
    </tr>
    </table>
  
</div>
 
<s:token />
</s:form>
<s:if test='%{showPaymentReportSearchFlag=="TRUE"}'>
<div id="resultcontainer" >
<!-- Dash Line Start -->
<div class="clear hr-dashline"></div>
<!-- Dash Line End -->
<s:form action="CandidateMgmtAction_showPaymentReportSearchResult.action" id="paymentreportresult" name="paginationForm">
<s:hidden name="paymentModeValue" id="paymentModeValue2"/>
<s:hidden name="paymentMode" id="paymentMode"/>
<s:hidden name="paymentStatus" id="paymentStatus"/>
<s:hidden name="toDate" id="toDate"/>
<s:hidden name="fromDate" id="fromDate"/>
<s:hidden name="disciplineId" id="disciplineId"/>

<table width="100%">
	<tr class="box-header">
		<td align="left" width="30%" >Payment Details</td>
		<td align="center" width="30%" class="pagination-label">Total <font color="red"><s:property value="paymentReportResultBeanListSize"/></font> Candidates Found </td>
		<td width="40%">
			<s:if test='%{paymentReportResultBeanListSize=="0"}'>
			</s:if><s:else><%@ include file="../pagination.jsp" %></s:else>
		</td>
	</tr>
</table>

<br/>
<div style="width:100%;  ">
	<s:if test='%{paymentMode=="1" || paymentMode=="2"}'>
			<table cellspacing="0" border="1" width="100%" height="100%" class="table_2" bordercolor="#CCC">
			<thead>
			    <tr>
			    <th style="text-align: left;">Sr.No.</th>
			    <th style="text-align: left;">Registration ID</th>
			    <th style="text-align: left;">Candidate Name</th>
			    <th style="text-align: left;">Level Of Exam</th>
			    <th style="text-align: left;">Category</th>
			    <th style="text-align: left;">Payment Mode</th>
			    <th style="text-align: center;">Applicable Fee</th>
			    <th style="text-align: left;">Transaction Number</th>
			    <th style="text-align: left;">Transaction Date</th>
			    <th style="text-align: left;">Mobile Number</th>
			    <th style="text-align: left;">Email ID</th>
			    <th style="text-align: left;">Status Date</th>
			    <th style="text-align: left;">Status</th>
			    </tr></thead>
			    <tbody>
			    <s:if test='%{paymentReportResultBeanListSize != 0}'>
			    <s:iterator value="paymentReportResultBeanList" var="bean" status="status">
			
			<tr>
				<td align="center">
					<s:property value="#status.index + 1"/>
				</td>
				<td align="center">
					<s:property value="enrollmentId"/>
				</td>
				<td>
					<s:property value="firstName"/>
				</td>
				<td>
					<s:property value="disciplineType"/>
				</td>
				<td>
					<s:property value="candidateCategory"/>
				</td>
				<td>
					<s:property value="paymentMode"/>
				</td>
				<td align="right">
					<s:property value="applicableFees"/>
				</td>
				<td>
					<s:property value="onlineTransactionNo"/>
				</td>
				<td>
					<s:property value="onlineTransactionDate"/>
				</td>
				<td>
					
					<s:property value="mobileNO"/>
						
				</td>
				<td>
					
					<s:property value="emailAddress"/>
						
				</td>
					<td>
					
						<s:property value="paymentSubmittedDate"/>
						
					</td>
				<td>
					
						<s:property value="paymentStatus"/>
						
					</td>
			</tr>
			</s:iterator>
			    </s:if>
			    <s:else>
				<tr>
					<td colspan="13" align="center">
						<div>
							<b>No record Found</b>
						</div>
					</td>
				</tr>
				</s:else> 
			    </tbody>
			</table>
	</s:if>
	<s:if test='%{paymentMode=="3"}'>
	<table cellspacing="0" border="1" height="100%"  width="100%" class="table_2" bordercolor="#CCC">
			<thead>
			    <tr>
			    <th style="text-align: left;">Sr.No.</th>
			    <th style="text-align: left;">Registration ID</th>
			    <th style="text-align: left;">Candidate Name</th>
			    <th style="text-align: left;">Level Of Exam</th>
			    <th style="text-align: left;">Category</th>
			    <th style="text-align: left;">Payment Mode</th>
			    <th style="text-align: left;">DD Number</th>
			    <th style="text-align: left;">DD Date</th>
			    <th style="text-align: left;">Applicable Fee</th>
			    <th style="text-align: left;">Bank Name</th>
			    <th style="text-align: left;">City</th>
			    <th style="text-align: left;">Mobile Number</th>
			    <th style="text-align: left;">Email ID</th>
			    <th style="text-align: left;">Status Date</th>
			    <th style="text-align: left;">Status</th>
			    </tr></thead>
			    <tbody>
			    <s:if test='%{paymentReportResultBeanListSize != 0}'>
			    <s:iterator value="paymentReportResultBeanList" var="bean" status="status">
			
			<tr>
				<td align="center" class="WrapWord">
					<s:property value="#status.index + 1"/>
				</td>
				<td align="center" class="WrapWord">
					<s:property value="enrollmentId"/>
				</td>
				<td class="WrapWord">
					<s:property value="firstName"/>
				</td>
				<td class="WrapWord">
					<s:property value="disciplineType"/>
				</td>
				<td class="WrapWord">
					<s:property value="candidateCategory"/>
				</td>
				<td class="WrapWord">
					<s:property value="paymentMode"/>
				</td>
				<td class="WrapWord">
					<s:property value="ddChallanReceiptNo"/>
				</td>
				<td class="WrapWord">
					<s:property value="ddChallanDate"/>
				</td>
				<td align="right" class="WrapWord">
					<s:property value="applicableFees"/>
				</td>
				<td class="WrapWord">
					<s:property value="ddBankName"/>
				</td>
				<td class="WrapWord">
					<s:property value="ddCityName"/>
				</td>
				<td class="WrapWord">
					
					<s:property value="mobileNO"/>
						
				</td>
				<td class="WrapWord">
					
					<s:property value="emailAddress"/>
						
				</td>
				<td class="WrapWord">
					
					<s:property value="paymentSubmittedDate"/>
						
				</td>
				<td class="WrapWord">
						<s:property value="paymentStatus"/>
				</td>
			</tr>
			</s:iterator>
			    </s:if>
			    <s:else>
				<tr>
					<td colspan="15" align="center">
						<div>
							<b>No record Found</b>
						</div>
					</td>
				</tr>
				</s:else> 
			    </tbody>
			</table>
	</s:if>
	<s:if test='%{paymentMode=="4" || paymentMode=="5"}'>
	<table cellspacing="0" border="1" height="100%"  width="100%" class="table_2" bordercolor="#CCC">
			<thead>
			    <tr>
			    <th style="text-align: left; width:2%; " >Sr.<br>No.</th>
			    <th style="text-align: left; width:7%;">Registration ID</th>
			    <th style="text-align: left; width:12%;">Candidate Name</th>
			    <th style="text-align: left; width:12%;">Level Of Exam</th>
			    <th style="text-align: left; width:5%;">Category</th>
			    <th style="text-align: left; width:5%;">Payment Mode</th>
			    <th style="text-align: left; width:5%;">Journal / <br>Reference Number</th>
			    <th style="text-align: left; width:8%;">Payment Date</th>
			    <th style="text-align: left; width:5%;">Applicable Fee</th>
			    <th style="text-align: left; width:7%;">Branch Name</th>
			    <th style="text-align: left; width:5%;">Branch Code</th>
			    <th style="text-align: left; width:7%;">Mobile Number</th>
			    <th style="text-align: left; width:7%;">Email ID</th>
			    <th style="text-align: left; width:8%;">Status Date</th>
			    <th style="text-align: left; width:5%;">Status</th>
			    </tr></thead>
			    <tbody>
			    <s:if test='%{paymentReportResultBeanListSize != 0}'>
			    <s:iterator value="paymentReportResultBeanList" var="bean" status="status">
			
			<tr>
				<td align="center" class="WrapWord">
					<s:property value="#status.index + 1"/>
				</td>
				<td align="center" class="WrapWord">
					<s:property value="enrollmentId"/>
				</td>
				<td class="WrapWord">
					<s:property value="firstName"/>
				</td>
				<td class="WrapWord">
					<s:property value="disciplineType"/>
				</td>
				<td class="WrapWord">
					<s:property value="candidateCategory"/>
				</td>
				<td class="WrapWord">
					<s:property value="paymentMode"/>
				</td>
				<td class="WrapWord">
					<s:property value="ddChallanReceiptNo"/>
				</td>
				<td class="WrapWord">
					<s:property value="ddChallanDate"/>
				</td>
				<td align="right" class="WrapWord">
					<s:property value="applicableFees"/>
				</td>
				<td class="WrapWord">
					<s:property value="challanBranchName"/>
				</td>
				<td class="WrapWord">
					<s:property value="challanBranchCode"/>
				</td>
				<td class="WrapWord">
					<s:property value="mobileNO"/>
				</td>
				<td class="WrapWord">
					<s:property value="emailAddress"/>
				</td>
				<td class="WrapWord">
						<s:property value="paymentSubmittedDate"/>
				</td>
				
				<td class="WrapWord">
						<s:property value="paymentStatus"/>
				</td>
				
			</tr>
			</s:iterator>
			    </s:if>
			    <s:else>
				<tr>
					<td colspan="15" align="center">
						<div>
							<b>No record Found</b>
						</div>
					</td>
				</tr>
				</s:else> 
			    </tbody>
			</table>
	</s:if>
	
	<s:if test='%{paymentDetailsDisplayFlag=="true"}'>
	<table cellspacing="0" border="1" height="100%"  width="100%" class="table_2" bordercolor="#CCC">
			<thead>
			    <tr>
			    <th style="text-align: left;">Sr.No.</th>
			    <th style="text-align: left;">Registration ID</th>
			    <th style="text-align: left;">Candidate Name</th>
			    <th style="text-align: left;">Course</th>
			    <th style="text-align: left;">Category</th>
			    <th style="text-align: left;">Mobile Number</th>
			    <th style="text-align: left;">Email ID</th>
			    <th style="text-align: left;">Status</th>
			    </tr></thead>
			   <s:if test='%{paymentReportResultBeanList!=null}'>
			    <tbody>
			    <s:iterator value="paymentReportResultBeanList" var="bean" status="status">
			<tr>
				<td align="center" class="WrapWord">
					<s:property value="#status.index + 1"/>
				</td>
				<td align="center" class="WrapWord">
					<s:property value="userId"/>
				</td>
				<td class="WrapWord">
					<s:property value="candidateName"/>
				</td>
				<td class="WrapWord">
					<s:property value="disciplineType"/>
				</td>
				<td class="WrapWord">
					<s:property value="candidateCategory"/>
				</td>
				<td class="WrapWord">
					<s:property value="mobileNO"/>
				</td>
				<td class="WrapWord">
					<s:property value="emailAddress"/>
				</td>
				<td class="WrapWord">
						<s:property value="paymentStatus"/>
				</td>
			</tr>
			</s:iterator>
			 </tbody>
			 </s:if><s:else>
			 	<tr>
				<td align = "center" colspan="8"> 
					<b>No record Found</b>
				</td>
			</tr>  
			 </s:else>
			 
			</table>
	</s:if>
</div>
<s:if test='%{paymentReportResultBeanListSize != 0}'> 
<!-- Button Start -->
<div class="height20"></div>
<div class="clear">
<s:submit value = "Generate EXCEL Report"  cssClass="submitBtn button-gradient" method="generatePaymentReport" onclick="setPaymentMode()"/>
</div>
<!-- Button End -->
</s:if>
<s:token />
</s:form>
</div>
</s:if>
</div>
</div>
