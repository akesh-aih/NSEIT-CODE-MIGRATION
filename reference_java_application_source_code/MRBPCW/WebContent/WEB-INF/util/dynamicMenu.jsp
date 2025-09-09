<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>

<div class="main-body" style="height:380px;" id="dashboard">
	<div>
	<s:form action="AgencyAction" id="dashboarddetails" name="dashboarddetails">
	  <s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
	<div align="center" id="gridDiv">
			<table width="920" cellpadding="5">
 			<tr>
 			<td valign="top" width="300">
					 <div class="AgencyPayDrid" id="gridDiv1">
					 <table width="100%" class="dashboard-table">
					 	<tr>
					 		<th>
					 			Application Form Management
					 		</th>
					 	</tr>
					 	<tr>
					 		<td>
					 			<table class="insidetbl" width="100%"  >
								<tr>
									<th width="70%" >
										Application Form Status
									</th>
									<th width="30%" >
										Total Count
									</th>
								</tr>
								<tr>
									<td align="left">
										Total Candidates
									</td>
									<td align="center">
										<s:property value="totalNoOfCandidate"/> 
									</td>
								</tr>
								<tr>
									<td align="left">
										Initiated
									</td>
									<td align="center">
										<s:property value="noOfInitiatedCand"/> 
									</td>
								</tr>
								<tr>
									<td align="left">
										Submitted
									</td>
									<td align="center">
										<s:property value="noOfCandidatesApplicationSubmitted"/>
									</td>
								</tr>
								
									<tr>
										<td align="left">
										Approved
										</td>
										<td align="center">
											<s:property value="noOfApprovedCand"/>
										</td>
									</tr>
									<tr>
										<td align="left">
										Rejected
										</td>
										<td align="center">
											<s:property value="noOfRejectedCand"/>
										</td>
									</tr>
								<tr>
									<td align="left">
										Pending For Approval
									</td>
									<td align="center">
										<s:property value="noOfPendingCand"/>
									</td>
								</tr>
							</table>
					 		
					 		</td>
					 	</tr>
					</table>
					</div>
 			</td>
 			<s:if test='%{paymentSubmittedStatus=="A"}'>
 			<td valign="top" width="300">
					<div class="AgencyPayDrid" id="gridDiv2">
						<table width="100%" class="dashboard-table">
					 	<tr>
					 		<th>
					 			Payment Management
					 		</th>
					 	</tr>
					 	<tr>
					 		<td>
								<table class="insidetbl" width="100%" >
								<tr>
									<th width="70%" >
										Payment Status
									</th>
									<th width="30%" >
										Total Count
									</th>
								</tr>
								
							<s:if test='%{paymentSubmittedStatus=="A"}'>
								<tr>
									<td align="left">
										Submitted
									</td>
									<td align="center">
										<s:property value="noOfCandPaymentSubmitted"/>
									</td>
								</tr>
								</s:if>
								
							<s:if test='%{paymentApproveStatus=="A"}'>
								<s:if test='%{nbPaymentStatusFlag=="A"}'>
								<tr>
									<td align="left">
										Successful For Net Banking / Debit Card
									</td>
									<td align="center">
										<s:property value="noOfCandPaymentApprovedForNB"/>
									</td>
								</tr>
								<tr>
									<td align="left">
										Unsuccessful For Net Banking / Debit Card
									</td>
									<td align="center">
										<s:property value="noOfCandPaymentRejectedForNB"/>
									</td>
								</tr>
								</s:if>
								<s:if test='%{crPaymentStatusFlag=="A"}'>
								<tr>
									<td align="left">
										Successful For Credit Card
									</td>
									<td align="center">
										<s:property value="noOfCandPaymentApprovedForCR"/>
									</td>
								</tr>
								<tr>
									<td align="left">
										Unsuccessful For Credit Card
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
								<tr>
									<td align="left">
										Pending For Approval For Challan
									</td>
									<td align="center">
										<s:property value="noOfPaymentPendingForApprovalCandForCH"/>
									</td>
								</tr>
								</s:if>
								</s:if>
								</table>
							</td>
						</tr>
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
										<s:property value="noOfNonScheduledCand"/>
									</td>
								</tr>
								<tr>
									<td align="left">
										Number of Candidate Scheduled for exam
									</td>
									<td align="center">
										<s:property value="noOfScheduledCand"/>
									</td>
								</tr>
								<tr>
									<td align="left">
										Number of Candidate Pending For Scheduling
									</td>
									<td align="center">
										<s:property value="noOfPendingForSchedulingCand"/>
									</td>
								</tr>
								
					
						<s:if test='%{generateAdmitCardStatus=="A"}'>
									<tr>
										<td align="left">
											Admit Card Generated
										</td>
										<td align="center">
											<s:property value="noOfAdmitCardGenCand"/>
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
 			--></tr>
 			</table>
 		</div>
		
<div class="clear" style="text-align:right;">
			<br/>
			<s:submit value="Click here To Refresh" method="goToActiveMenu" cssClass="submitBtn button-gradient"></s:submit>
</div>
</s:form>
</div>
</div>