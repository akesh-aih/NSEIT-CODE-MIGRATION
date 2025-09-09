<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<script type="text/javascript" src="js/AES.js"></script>
<script type="text/javascript">

 function gotoReportPage(param)
{
	var programName = "All";
	var category = "All"; 
	var specialCategory = "All"; 
	var disabilityVal = "All"; 
	var registrationStatus = "All";
	var interviewVal = "All";
	var fromDate = "";
	var toDate = "";
	var menuKey = "<%=ConfigurationConstants.getInstance().getMenuKeyForUrl("AgencyAction_getReportPage.action")%>";
	var reportHtml = '<s:hidden name="programName" value="'+programName+'"></s:hidden>'+
								'<s:hidden name="category" value="'+category+'"></s:hidden>'+
								'<s:hidden name="specialCategory" value="'+specialCategory+'"></s:hidden>'+
								'<s:hidden name="disabilityVal" value="'+disabilityVal+'"></s:hidden>'+
								'<s:hidden name="registrationStatus" value="'+param+'"></s:hidden>'+
								'<s:hidden name="registrationFromDate" value="'+fromDate+'"></s:hidden>'+
								'<s:hidden name="registrationToDate" value="'+toDate+'"></s:hidden>'+
								'<s:hidden name="interviewVal" value="'+interviewVal+'"></s:hidden>';
	$("input[name='menuKey']").val(menuKey);
	$("#formDiv").html(reportHtml);
	
	$("#dashboarddetails").attr('action',"AgencyAction_generateHTMLReportForCandidateDetails.action");
	$("#dashboarddetails").submit();
}

<%-- function gotoReportPage(param)
{
	/* var pondiReports='City wise report'; */
	var pondiReports='2';
	var menuKey = "<%=ConfigurationConstants.getInstance().getMenuKeyForUrl("AgencyAction_pondiReports.action")%>";
	var reportHtml = '<s:hidden name="pondiReports" value="'+pondiReports+'"></s:hidden>';
	$("input[name='menuKey']").val(menuKey);
	$("#formDiv").html(reportHtml);
	
	$("#dashboarddetails").attr('action',"AgencyAction_generateHTMLReportForPondi.action");
	$("#dashboarddetails").submit();
} --%>



function gotoPaymentDetailsPage(paymentType, paymentStatus)
{
	var menuKey = "<%=ConfigurationConstants.getInstance().getMenuKeyForUrl("CandidateMgmtAction_papulatePaymentApproval.action")%>";
	var reportHtml = '<s:hidden name="paymentMode" value="'+paymentType+'"></s:hidden>'+
					 '<s:hidden name="paymentStatusValue" value="'+paymentStatus+'"></s:hidden>';
	$("input[name='menuKey']").val(menuKey);
	$("#formDiv").html(reportHtml);
	
	$("#dashboarddetails").attr('action',"CandidateMgmtAction_getDDDetailsForPaymentApproval.action");
	$("#dashboarddetails").submit();
	
}

function gotoPaymentReportScreen(paymentType, paymentStatus)
{
		var menuKey = "<%=ConfigurationConstants.getInstance().getMenuKeyForUrl("CandidateMgmtAction_showPaymentReportScreen.action")%>";
		var fromDate = "";
		var toDate = "";
		var disciplineId = "0";
		var category="0";
		var specialCategory="0";
		var disability="0";
		var reportHtml = '<s:hidden name="disciplineId" value="'+disciplineId+'"></s:hidden>'+
								'<s:hidden name="category" value="'+category+'"></s:hidden>'+
								'<s:hidden name="disability" value="'+disability+'"></s:hidden>'+
								'<s:hidden name="specialCategory" value="'+specialCategory+'"></s:hidden>'+
								'<s:hidden name="paymentMode" value="'+paymentType+'"></s:hidden>'+
								'<s:hidden name="paymentStatus" value="'+paymentStatus+'"></s:hidden>'+
								'<s:hidden name="fromDate" value="'+fromDate+'"></s:hidden>'+
								'<s:hidden name="toDate" value="'+toDate+'"></s:hidden>';
								
		$("input[name='menuKey']").val(menuKey);
		$("#formDiv").html(reportHtml);
		
		$("#dashboarddetails").attr('action',"CandidateMgmtAction_showPaymentReportSearchResult.action");
		$("#dashboarddetails").submit();
}

function gotoScheduleReportPage(scheduleStatus)
{

	var userId = "";

	var disciplineId = "";

	var fromDate = "";

	var toDate = "";

	var testCenterId = "0";

	var scheduleStatusId = scheduleStatus;
	
	var menuKey = "<%=ConfigurationConstants.getInstance().getMenuKeyForUrl("ReportAction_showScheduleReportView.action")%>";
	
	var reportHtml = '<s:hidden name="disciplineId" value="'+disciplineId+'"></s:hidden>'+
								'<s:hidden name="testCenterId" value="'+testCenterId+'"></s:hidden>'+
								'<s:hidden name="userId" value="'+userId+'"></s:hidden>'+
								'<s:hidden name="fromDate" value="'+fromDate+'"></s:hidden>'+
								'<s:hidden name="toDate" value="'+toDate+'"></s:hidden>'+
								'<s:hidden name="scheduleStatusId" value="'+scheduleStatusId+'"></s:hidden>';
								
		$("input[name='menuKey']").val(menuKey);
		$("#formDiv").html(reportHtml);
		
		$("#dashboarddetails").attr('action',"ReportAction_getSechduleReport.action");
		$("#dashboarddetails").submit();
}



function gotoCandidateApproval()
{
	var discipline = "0";
	var userId = "";
	var datepicker3 = "";
	var datepicker4 = "";
	var candidateStatus = "2";
	var menuKey = "<%=ConfigurationConstants.getInstance().getMenuKeyForUrl("CandidateMgmtAction_getEligibleAndNonEligibleCandidateDataForNodal.action")%>";
	var reportHtml = '<s:hidden name="disciplineId" value="'+discipline+'"></s:hidden>'+
								'<s:hidden name="userId" value="'+userId+'"></s:hidden>'+
								'<s:hidden name="fromDate" value="'+datepicker3+'"></s:hidden>'+
								'<s:hidden name="toDate" value="'+datepicker4+'"></s:hidden>'+
								'<s:hidden name="candidateStatusId" value="'+candidateStatus+'"></s:hidden>';
								
		$("input[name='menuKey']").val(menuKey);
		$("#formDiv").html(reportHtml);
		
		$("#dashboarddetails").attr('action',"CandidateAction_searchCandidate.action");
		$("#dashboarddetails").submit();
	
}

function frmRefresh()
{
	//alert("frmRefresh called");
	document.dashboarddetails.action="AgencyAction_getGcetLoginHome.action";
	document.dashboarddetails.submit();
}
</script>
<style type="text/css">
	div.submenu {display: none;}
	.tabDiv {margin-top: 0px !important;  background: #fff;}	
.container .accordions {
	margin: 0 auto;
}
</style>
<div class="container common_dashboard">
	<div class="main-body tabDiv" id="dashboard">
	<div class="accordions">
			<div class="accordion">
	<s:form action="AgencyAction" id="dashboarddetails" name="dashboarddetails" method="post">
	<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
	<div id="formDiv"></div>
	<!--<input type="hidden" name="disciplineId" value='<s:property value="disciplineType"/>' />
	<input type="hidden" name="candidateStatusId" value="0"/>
	<input type="hidden" name="userId" value=""/>
	<input type="hidden" name="fromDate" value='<s:property value="datepicker3"/>'/>
	<input type="hidden" name="toDate" value='<s:property value="datepicker4"/>'/>
		-->
		
		
		<div class="row"  id="gridDiv">	
		<s:if test="%{#session['SESSION_USER'].userType !='FI'}">		
			<div class="col-sm-6 AgencyPayDrid" id="gridDiv1">
				<h4 class="secondH3">Application Form Management</h4>
				<table class="table table-striped table-bordered personsl-dtl" width="100%">
				
								<tr>
							
									<th width="70%" >
										Application Form Status
									</th>
									<th width="30%" class="text-center">
										Total Count
									</th>
								</tr>
								<tr>
									<td align="left">
										Total Registered Applicant
									</td>
									<td align="center">
										<a href="#" onclick="gotoReportPage('All')"><u><s:property value="totalNoOfCandidate"/></u></a>
										
									</td>
								</tr>
								<tr>
									<td align="left">
										 Not Initiated
									</td>
									<td align="center">
										<a href="#" onclick="gotoReportPage('<%=ConfigurationConstants.getInstance().getStatusKey(GenericConstants.NEW_REGISTERATION)%>')"><u><s:property value="noOfNotInitiatedCand"/></u></a>
										
									</td>
								</tr>
								<tr>
									<td align="left">
										Profile Initiated
									</td>
									<td align="center">
										<a href="#" onclick="gotoReportPage('<%=ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_INTITIATED)%>')"><u><s:property value="noOfInitiatedCand"/></u></a> 
										
									</td>
								</tr>
								<tr>
									<td align="left">
										Profile Submitted
									</td>
									<td align="center">
										<a href="#" onclick="gotoReportPage('<%=ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED)%>')"><u><s:property value="noOfCandidatesApplicationSubmitted"/></u></a>
										
									</td>
								</tr>
								<tr>
									<td align="left">
										Application Submitted
									</td>
									<td align="center">
										<a href="#" onclick="gotoReportPage('<%=ConfigurationConstants.getInstance().getStatusKey(GenericConstants.NEW_REGISTERATION)%>')"><u><s:property value="applicationSubmittedCount"/></u></a>
										
									</td>
								</tr>
								
								<%-- <tr>
									<td align="left">
										Admit Card Download 
									</td>
									<td align="center">
									<u><a href="#" onclick="gotoReportPage('2')"><s:property value="admitCardDownloadCount"/></a></u>
									
										<u><a href="AgencyAction_generateHTMLReportForPondi.action"><s:property value="admitCardDownloadCount"/></a></u>
										<s:property value="admitCardDownloadCount"/>
									</td>
								</tr>
								
								<tr>
									<td align="left">
										Practice Test Attempt 
									</td>
									<td align="center">
										<u><a href="#" onclick="gotoReportPage('2')"><s:property value="pracTestAttemptCount"/></a></u>
										<s:property value="pracTestAttemptCount"/>
									</td>
								</tr> --%>
							</table>
			</div>
			</s:if>
			<s:if test="%{#session['SESSION_USER'].userType !='AC'}">
			<div class="col-sm-6">
				<s:if test='%{paymentSubmittedStatus=="A"}'>
 			<td valign="top" width="300">
					<div class="AgencyPayDrid" id="gridDiv2">
					<h4 class="secondH3">Payment Management</h4>
						<table class="table table-striped table-bordered personsl-dtl" width="100%" >
								<tr>
									<th width="70%" >
										Payment Status
									</th>
									<th width="30%" class="text-center" >
										Total Count
									</th>
								</tr>
								
							<s:if test='%{paymentSubmittedStatus=="A"}'>
								<tr>
									<td align="left">
										Submitted
									</td>
									<td align="center">
										<a href="#" onclick="gotoPaymentReportScreen('1','0')"><u><s:property value="noOfCandPaymentSubmitted"/></u></a>
									</td>
								</tr>
								</s:if>
								
							<s:if test='%{paymentApproveStatus=="A"}'>
								<s:if test='%{nbPaymentStatusFlag=="A"}'>
								<tr>
									<td align="left">
										Successful For Online
									</td>
									<td align="center">
										<a href="#" onclick="gotoPaymentReportScreen('1','2')"><u><s:property value="noOfCandPaymentApprovedForNB"/></u></a>
									</td>
								</tr>
								 <tr>
									<td align="left">
										Unsuccessful For Online
									</td>
									<td align="center">
										<a href="#" onclick="gotoPaymentReportScreen('1','1')"><u><s:property value="noOfCandPaymentRejectedForNB"/></u></a>
									</td>
								</tr>
								<tr>
									<td align="left">
									Pending For Approval
									</td>
									<td align="center">
										<a href="#" onclick="gotoPaymentReportScreen('1','3')"><u><s:property value="noOfPaymentPendingForApprovalCandForNB"/></u></a>
									</td>
								</tr> 
								</s:if>
								<s:if test='%{crPaymentStatusFlag=="A"}'>
								<tr>
									<td align="left">
										Successful For Indian Bank Net Banking
									</td>
									<td align="center">
										<s:property value="noOfCandPaymentApprovedForCR"/>
									</td>
								</tr>
								<tr>
									<td align="left">
										Unsuccessful For Indian Bank Net Banking
									</td>
									<td align="center">
										<s:property value="noOfCandPaymentRejectedForCR"/>
									</td>
								</tr>
								</s:if>
								<s:if test='%{ddPaymentStatusFlag=="A"}'>
								<tr>
									<td align="left">
										Approved For DD
									</td>
									<td align="center">
										<s:property value="noOfCandPaymentApprovedForDD"/>
									</td>
								</tr>
								<tr>
									<td align="left">
										Rejected For DD
									</td>
									<td align="center">
										<s:property value="noOfCandPaymentRejectedForDD"/>
									</td>
								</tr>
								<tr>
									<td align="left">
										Pending For Approval For DD
									</td>
									<td align="center">
										<s:property value="noOfPaymentPendingForApprovalCandForDD"/>
									</td>
								</tr>
								</s:if>
								<s:if test='%{chPaymentStatusFlag=="A"}'>
								<tr>
									<td align="left">
										Approved For Challan
									</td>
									<td align="center">
										<s:property value="noOfCandPaymentApprovedForCH"/>
									</td>
								</tr>
								<tr>
									<td align="left">
										Rejected For Challan
									</td>
									<td align="center">
										<s:property value="noOfCandPaymentRejectedForCH"/>
									</td>
								</tr>
								<tr style="display:none;">
									<td align="left">
										Pending For Approval For Challan
									</td>
									<td align="center">
										<s:property value="noOfPaymentPendingForApprovalCandForCH"/>
									</td>
								</tr>
								</s:if>
								<s:if test='%{cashPaymentStatusFlag=="A"}'>
								<tr>
									<td align="left">
										Approved For Cash
									</td>
									<td align="center">
										<s:property value="noOfCandPaymentApprovedForCASH"/>
									</td>
								</tr>
								<tr>
									<td align="left">
										Rejected For Cash
									</td>
									<td align="center">
										<s:property value="noOfCandPaymentRejectedForCASH"/>
									</td>
								</tr>
								<tr>
									<td align="left">
										Pending For Approval For Cash
									</td>
									<td align="center">
										<!--  <u><a href="#" onclick="gotoPaymentDetailsPage('5', '-1')"><s:property value="noOfPaymentPendingForApprovalCandForCASH"/></a></u> -->
										<s:property value="noOfPaymentPendingForApprovalCandForCASH"/>
									</td>
								</tr>
								</s:if>
								</s:if>
								</table>
					</div>
 			</td>
 			</s:if>
			<!--<s:if test='%{scheduleMgmtStatus=="A"}'>
 			<td valign="top" width="300">
					<div class="AgencyPayDrid" id="gridDiv3">
						<table width="100%" class="dashboard-table">
					 	<tr>
					 		<th>
					 			Schedule Management
					 		</th>
					 	</tr>
					 	<tr>
					 		<td>
								<table class="insidetbl" width="100%" >
								<tr>
									<th width="70%" >
										Schedule Status
									</th>
									<th width="30%" >
										Total Count
									</th>
								</tr>
								<tr>
									<td align="left">
										Number of Candidate not Scheduled for exam
									</td>
									<td align="center">
										<u><a href="#" onclick="gotoScheduleReportPage('2')"><s:property value="noOfNonScheduledCand"/></a></u>
									</td>
								</tr>
								<tr>
									<td align="left">
										Number of Candidate Scheduled for exam
									</td>
									<td align="center">
										<u><a href="#" onclick="gotoScheduleReportPage('1')"><s:property value="noOfScheduledCand"/></a></u>
									</td>
								</tr>
								<tr>
									<td align="left">
										Number of Candidate Pending For Scheduling
									</td>
									<td align="center">
										<u><a href="#" onclick="gotoScheduleReportPage('3')"><s:property value="noOfPendingForSchedulingCand"/></a></u>
									</td>
								</tr>
								
					
						<s:if test='%{generateAdmitCardStatus=="A"}'>
									<tr>
										<td align="left">
											Admit Card Generated
										</td>
										<td align="center">
											<u><a href="#" onclick="gotoScheduleReportPage('4')"><s:property value="noOfAdmitCardGenCand"/></a></u>
										</td>
									</tr>
						</s:if>
							</table>
						</td>
						</tr>
						</table>
					</div>
 			</td>
 			</s:if>
 			-->
			</div>		
		</s:if>
		</div>
		<div class="row">
		<s:if test="%{#session['SESSION_USER'].userType =='FI' || #session['SESSION_USER'].userType =='AC'}">
			<div class="col-sm-12 text-left">
				<s:submit value="Click here To Refresh"  onclick="frmRefresh();" cssClass="ripple1 submitBtn button-gradient"></s:submit>
			</div>
		</s:if>
		<s:else>
		<div class="col-sm-12 text-right mb10">
				<s:submit value="Click here To Refresh"  onclick="frmRefresh();" cssClass="ripple1 submitBtn button-gradient btn-warning"></s:submit>
			</div>
			<div class="clearfix height20"></div>
		</s:else>
		</div>	
<s:token/>
</s:form>
</div>
</div></div>
</div>