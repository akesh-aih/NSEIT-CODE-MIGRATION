<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<link href="css/common.css" rel="stylesheet" type="text/css" media="screen"/>
<link type="text/css" href="css/ui-lightness/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
<script src="js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/AES.js"></script>

<script type="text/javascript" src="js/login.js"></script>
<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
    <script>
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
    
    function callFunc()
    {
    	$("#error-massage_user").hide();
        $("#SearchResult").hide();
        $("#datepicker4").val("");
        $("#datepicker3").val("");
        $("#programName").val("0");
        $("#category").val("All");
        $("#registrationStatus").val("All");
        $("#interviewVal").val("All");
        
    }
    function validateForm(){
	    var ulID = "error-ulAppForm";
		var divID = "error-massageAppForm";
		
		var message = "";
		
			
		var isCheckedVal = false;
		$(".degreeTypeClass").each(function(){
			if($(this).is(':checked'))
			{
				isCheckedVal = true;
			}
		});

		if(isCheckedVal == false)
		{
			message = message + "Please select Export Format Type."+"##";
		}
			
			if(message != ""){
				createErrorList(message, ulID, divID); 
				$("#error-massageAppForm").focus();
				$('html, body').animate({ scrollTop: 0 }, 0);
				//$('html, body').animate({ scrollTop: 0 }, 'slow');
			}else{
				//$("#summaryForm").attr('action',"AgencyAction_generateReport.action");
				//$("#summaryForm").submit();
				open_win();
			}

}

function on_click()
{
	 $("#error-massageAppForm").hide();

	var resultStatus = "";
	$(".degreeTypeClass").each(function(){
		if($(this).is(':checked'))
		{
			//isCheckedVal = true;
			 var id = $(this).attr("id");
			 resultStatus = $('label[for='+id+']').text();
			 $("#format").val(resultStatus);
			 
		}
	});				 
}

function open_win()
{
		var menuKey = "<%=ConfigurationConstants.getInstance().getMenuKeyForUrl("AgencyAction_getReportPage.action")%>";
		location.href="AgencyAction_generateHTMLReportForCandidateDetails.action?programName="+$("#programName").val()+
		"&category="+$("#category").val()+"&registrationStatus="+$("#registrationStatus").val()+
		"&registrationFromDate="+$("#datepicker3").val()+"&registrationToDate="+$("#datepicker4").val()+ "&menuKey="+menuKey+ "&interviewVal="+$("#interviewVal").val();
		return false;
}

function exportToExcel()
{
		window.open("AgencyAction_generateReport.action?programName="+$("#programName").val()+
		"&category="+$("#category").val()+"&registrationStatus="+$("#registrationStatus").val()+
		"&registrationFromDate="+$("#datepicker3").val()+"&registrationToDate="+$("#datepicker4").val());
		return false;
} 
function changeAction()
{
	
	
	$("#summaryForm").attr('action',"AgencyAction_generateHTMLReportForCandidateDetails.action");
	$("#summaryForm").submit();
}


function validateInput() {
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
		//open_win();
		changeAction();
		
	}
}
</script>
<style>
.onetime{
  	display: none;
  }
.personsl-dtl { font-size:12px;}
.ui-datepicker-trigger {margin-top:8px !important; }
</style>
<div class="container">
<div class="fade" id="pop3"></div>
<div id="dashboard" class="container common_dashboard">
<s:form action="AgencyAction" id = "summaryForm" >
<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>

<h1 class="pageTitle" title="Candidate Detail Report">Candidate Detail Report</h1>
<div class="hr-underline2"></div>
<div id="error-massage_user" style="display:none" class="error-massage" >
				 <div class="error-massage-text" style="margin:0; margin-left:-40px; margin-top:30px; padding:0;">
			      	<ul style="margin:0; margin-left:20px; padding:0;" id="error-ul_user">
			      	</ul>
      		</div>
      			</div>
<!-- Box Container Start -->
<div class="container">
	<div class="row">
		<div class="col-md-4">
			<div class="form-group">
				<label class="control-label">Name of Program</label>
				<s:select list = "programDetailsList" name="programName" label = "Name"    
							headerKey="0" headerValue = "All" id="programName"  value="%{programName}" cssClass="form-control" />
			</div>
		</div>
		<div class="col-md-4">
			<div class="form-group">
				<label class="control-label">Category</label>
				<s:select list = "categoryList" name="category" label = "Name"    
						headerKey="0" headerValue = "All" id="category"  value="%{category}" cssClass="form-control" />
			</div>
		</div>
		<div class="col-md-4">
			<div class="form-group">
				<label class="control-label">Registration Status</label>
				<s:select list = "registrationStatusList" name="registrationStatus" label = "Name"    
						headerKey="0" headerValue = "All" id="registrationStatus"  value="%{registrationStatus}" cssClass="form-control" />
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="form-group dateInput">
				<label class="control-label">Registration From Date</label>
				<s:textfield name="registrationFromDate" value="%{registrationFromDate}" id="datepicker3" disabled="disabled" readonly="true" cssClass="disableEnroll form-control"></s:textfield>
			</div>
		</div>
		<div class="col-md-4">
			<div class="form-group dateInput">
				<label class="control-label">Registration To Date</label>
				<s:textfield name="registrationToDate" value="%{registrationToDate}" id="datepicker4" disabled="disabled" readonly="true" cssClass="disableEnroll form-control"></s:textfield>
			</div>
		</div>
	</div>
	<div class="row mt20 mb10">
		<div class="col-md-2 mt10">
			<input type = "button" value = "Search"  class="submitBtn button-gradient btn-block" onclick = "return validateInput()"/>
		</div>
		<div class="col-md-2 mt10 mb10">
			<input type="button" name="button2" id="button2" value="Clear" class="submitBtn button-gradient btn-block" onclick="callFunc();"/>
		</div>
	</div>
</div>
  <label for="select"></label>
<s:token />
</s:form>
 

<div id="SearchResult">
<s:if test='%{showCandidateDetailsReport=="Y"}'>
<!-- Dash Line Start -->
<div class="clear hr-dashline"></div>
<!-- Dash Line End -->
 
<div class="AgencyPayDrid" id="gridDiv1">
<s:form method="post" action="AgencyAction_generateHTMLReportForCandidateDetails.action" name="paginationForm">
<s:hidden name="registrationStatus"/>
<s:hidden name="category"/>
<s:hidden name="programName"/>
<s:hidden name="registrationFromDate"/>
<s:hidden name="registrationToDate"/>
<!-- DD Payment Start -->
<div id="option1">
<s:token/>
<table width="100%">
	<tr class="box-header">
		<td align="left" width="30%" >Candidate Details</td>
		<td align="center" width="30%" class="pagination-label">Total <font color="red"><s:property value="candidateDetailsListSize"/></font> Candidates Found 
		<s:hidden name="candidateDetailsListSize"></s:hidden>
		</td>
		<td width="40%">
			<s:if test='%{candidateDetailsListSize=="0"}'> 
			</s:if><s:else><%@ include file="../pagination.jsp" %></s:else>
			
		</td>
	</tr>
</table>
<br/>
<div style="width:1101px; height: 500px; overflow: scroll;" class="table-responsive tableFixHead"> 
<table cellspacing="0" width="100%"  border="1" class="personsl-dtl table" bordercolor="#CCC">
<thead>
    <tr>
    <th>Sr.No.</th>
    <th>User ID</th>
	<th>Post Applied for</th>
	<th>Applicant Name & Initial</th>
	<th>Gender</th>
	<th>Date of Birth</th>
	<th>Community</th>
	<th>Nativity</th>
	<th>Other Nativity</th>
	<th>Differently Abled (Yes / No)</th>
	<th>Whether Scribe Required</th>
	<th>Graduation Degree Name</th>
	<th>Graduation Main Subject</th>
	<th>Post Graduation Degree Name</th>
	<th>Post Graduation Main Subject</th>
	<th>Degree in Education Degree Name</th>
	<th>City</th>
	<th>State</th>
	<th>E-Mail ID</th>
	<th>Mobile Number</th>
	<th>Preferred Test City 1</th>
	<th>Preferred Test City 2</th>
	<th>Preferred Test City 3</th>
	<th>Admit Card Downloaded (Yes / No)</th>
	<th>Date of Admit Card Download (Latest)</th>
	<th>Score Card Downloaded (Yes / No)</th>
	<th>Date of Download Score Card (Latest)</th>
	<th>Call Letter Downloaded (Yes / No)</th>
	<th>Date of Download Call Letter (Latest)</th>
	<th>Payment Status</th>
	<th>Payment Amount</th>
	<th>Basic Registration Date</th>
	<th>Application Form Submission Date</th>
	<th>Successful Payment Date</th>
	    
    </tr></thead>
    <tbody>
<s:if test="%{candidateDetailsList!=null}"> 


<s:iterator value="candidateDetailsList" var="bean" status="status">


<tr>
	<td style="text-align: center;width: 4px;"><s:property value="Sr_no"/></td>
	<td style="text-align: center;width: 25px;"><s:property value="userId"/></td>
	<td class="wordWrap"><s:property value="postName"/></td>
	<td class="wordWrap"><s:property value="firstName"/></td>
	<td class="wordWrap"><s:property value="personalDetailsBean.gender"/></td>
	<td class="wordWrap"><s:property value="personalDetailsBean.dateOfBirth"/></td>
	<td class="wordWrap"><s:property value="personalDetailsBean.category"/></td>
    <td class="wordWrap"><s:property value="nativity"/></td>
    <td class="wordWrap"><s:property value="otherNativity"/></td>
    <td class="wordWrap"><s:property value="physicalDisability"/></td>
    <td class="wordWrap"><s:property value="scribeRequired"/></td>
    <td class="wordWrap"><s:property value="educationDetailsBean.ugDegreeName"/></td>
    <td class="wordWrap"><s:property value="educationDetailsBean.ugDegreeSubject"/></td>
    <td class="wordWrap"><s:property value="educationDetailsBean.pgDegreeName"/></td>
    <td class="wordWrap"><s:property value="educationDetailsBean.pgDegreeSubject"/></td>
    <td class="wordWrap"><s:property value="educationDetailsBean.eduDegreeName"/></td>
    <td class="wordWrap"><s:property value="cityName"/></td>
    <td class="wordWrap"><s:property value="stateValDesc"/></td>
    <td class="wordWrap"><s:property value="personalDetailsBean.email"/></td>
    <td class="wordWrap"><s:property value="personalDetailsBean.mobileNo"/></td>
    <td class="wordWrap"><s:property value="preferredTestDate1"/></td>	
    <td class="wordWrap"><s:property value="preferredTestDate2"/></td>
    <td class="wordWrap"><s:property value="preferredTestDate3"/></td>
    <td class="wordWrap"><s:property value="admitCardExist"/></td>
    <td class="wordWrap"><s:property value="admitcardDownloadDate"/></td>
    <td class="wordWrap"><s:property value="scoreCardExist"/></td>
    <td class="wordWrap"><s:property value="scoreCardDownloadDate"/></td>
    <td class="wordWrap"><s:property value="callLetterExist"/></td>
    <td class="wordWrap"><s:property value="callLetterDownloadDate"/></td>
    <td class="wordWrap"><s:property value="paymentStatus"/></td>
    <td class="wordWrap"><s:property value="receiptAmount"/></td>
    <td class="wordWrap"><s:property value="regStrtDate"/></td>
    <td class="wordWrap"><s:property value="regFormSubmitedDate"/></td>     
    <td class="wordWrap"><s:property value="paymentTransactionDate"/></td>
                            	
</tr>
</s:iterator>
</s:if> 
<s:if test="%{candidateDetailsList==null}"> 
<tr>
		<td colspan="40" align="center">
			
				<b>No record Found</b>
			
		</td>
</tr>
</s:if>
</tbody>
</table>
</div>

<s:if test="%{candidateDetailsList!=null}"> 
<!-- Button Start -->
<div class="height20"></div>
<div class="clear">
<input type = "button" value = "Generate EXCEL Report"  class="submitBtn button-gradient" onclick = "exportToExcel();"/>
</div>
<!-- Button End -->
</s:if>
</div>
<!-- DD Payment End -->
<s:token />
</s:form>
</div>
</s:if>
</div>
</div>
</div>