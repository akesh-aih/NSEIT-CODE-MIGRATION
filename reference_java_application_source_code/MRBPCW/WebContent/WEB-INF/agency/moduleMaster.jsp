<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<script src="js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/AES.js"></script>
<script type="text/javascript">

	function changeAction() {
		$("#ModuleMasterId").attr('action',
				"ModuleMasterAction_searchModule.action");
		$("#ModuleMasterId").submit();
	}

	function changeActionAdd() {
		$("#ModuleMasterId").attr('action', "ModuleMasterAction_addModule.action");
		$("#ModuleMasterId").submit();
	}
	
	function resetFieldValue() {
		$("#ModuleMasterId")
				.attr('action', "ModuleMasterAction_resetModule.action");
		$("#ModuleMasterId").submit();
	}
	function editCity(testName) {
		var moduleName = document.createElement("input");
		//var Syllabus_PK = document.createElement("input");
		moduleName.setAttribute("type", "hidden");
		moduleName.setAttribute("name", "moduleName");
		moduleName.setAttribute("value", testName);
		
		//Syllabus_PK.setAttribute("type", "hidden");
		//Syllabus_PK.setAttribute("name", "Syllabus_PK");
		//Syllabus_PK.setAttribute("value", syllabusPk);
		document.getElementById("ModuleMasterId").appendChild(moduleName);
		//document.getElementById("ModuleMasterId").appendChild(Syllabus_PK);

		$("#ModuleMasterId").attr('action', "ModuleMasterAction_editModuleDetails.action");
		$("#ModuleMasterId").submit();
	}
</script>

<%@ taglib prefix="s" uri="/struts-tags"%>
<style>
.onetime{
  	display: none;
  }
</style>
<div class="container">
<div class="container common_dashboard">
<div class="main-body">
	<div class="fade" id="pop3"></div>

	<div id="SelectTest">

		<h1 class="pageTitle" title="Module Master">
			Module Master
		</h1>
		<div class="hr-underline2"></div>

		<!-- Box Container Start -->


		<div class="clear">
			<s:form action="ModuleMasterAction" id="ModuleMasterId">
				<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
				<div id="error-massage_user" style="display: none"
					class="error-massage">
					<div class="error-massage-text"
						style="margin: 0; margin-left: -40px; margin-top: 30px; padding: 0;">
						<ul style="margin: 0; margin-left: 20px; padding: 0;"
							id="error-ul_user">
						</ul>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="for-group">
							<label class="control-label">Status</label>
							<s:select name="status" id="status" headerKey="B"
								headerValue="Both" list="selectList" listValue="labelValue"
								listKey="labelId" value="%{status}" cssClass="form-control" />
						</div>
					</div>
				</div>
				<div class="row mt20">
					<div class="col-md-2 mt10">
						<input type="button" value="Search" class="submitBtn button-gradient btn-block" onclick="changeAction();" />
					</div>
					<div class="col-md-2 mt10">
						<input type="reset" name="button2" id="button2" value="Reset" class="submitBtn button-gradient btn-block" onclick="resetFieldValue();" />
					</div>
					<div class="col-md-2 mt10">
						<input type="button" value="Add " title="Add " class="submitBtn button-gradient btn-block" onclick="changeActionAdd();" /> 
					</div>
				</div>
				<!--<table width="100%" cellspacing="1" cellpadding="3" > 
					 <tr>
						<td class="field-label" width="10%">
							Status
						</td>
						<td> 
							<s:select name="status" id="status" headerKey="B"
								headerValue="Both" list="selectList" listValue="labelValue"
								listKey="labelId" value="%{status}" />
						</td>
					</tr> 
					<tr>
					<td>&nbsp;</td>
					<td>

		<input type="button" value="Search" class="submitBtn button-gradient" onclick="changeAction();" />
											
		<input type="reset" name="button2" id="button2" value="Reset" class="submitBtn button-gradient" onclick="resetFieldValue();" />

		<input type="button" value="Add " title="Add " class="submitBtn button-gradient" onclick="changeActionAdd();" /> 

										</td>
					</tr>
					
				</table>-->
				<br />


				 

				<!-- Row Four End -->

			<s:token />
			</s:form>

			<div class="height20"></div>
			<s:if test="%{showGrid=='true'}">
				<!-- Dash Line Start -->
				<div class="clear hr-dashline"></div>
				<!-- Dash Line End -->

				<div class="AgencyPayDrid" id="gridDiv1">
					<s:form method="post" action="ModuleMasterAction_searchModule.action"  id="moduleDetails" name="paginationForm">
						<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
						<!-- <input type="hidden" name="syllabusValue"  /> -->
						<s:hidden name="status" value="%{status}" />
						<table width="100%">
							<tr class="box-header">
								<td align="left" width="30%">
									Module Details
								</td>
								<td align="center" width="30%" class="pagination-label">
									Total
									<font color="red"><s:property value="totalRecord" />
									</font> Records Found
								</td>
								<td width="40%">
								<s:if test='%{totalRecord=="0"}'> 
								</s:if><s:else><%@ include file="../pagination.jsp" %></s:else>
								</td>
							</tr>
						</table>

						<br />
					<div class="table-responsive tableFixHead">
						<table cellspacing="0" border="1" width="100%" class="table_2 table" bordercolor="#CCC">
							<thead>
								<tr>
									<th  align="left">
										Module ID
									</th>
									<th  align="left">
										Module Name
									</th>
									<th  align="left">
										Description
									</th>
									<th  align="left">
										Application Start Date
									</th>
									<th  align="left">
										Application End Date
									</th>
									<th  align="left">
										Payment Start Date
									</th>
									<th  align="left">
										Payment End Date
									</th>
									<th  align="left">
										Date of Notification
									</th>
									<th  align="left">
										Verification Post
									</th>
									<th  align="left">
										Advertisement Number
									</th>
									
									<th  align="left">
										Status
									</th>

								</tr>
							</thead>
							<tbody>

								<s:iterator value="searchDetailsList" status="stat"
									var="currentObject">
									<tr>
										<td align="left">
											<a href="#"
												onclick="editCity('<s:property value="OTM_TEST_NAME" />');">
												<s:property value="OTM_TEST_PK" />
												<s:hidden name="searchDetailsList[%{#stat.index}].OTM_TEST_PK" value="%{OTM_TEST_PK}"/>
											</a>
										</td>
										<td align="left">
											<s:property value="OTM_TEST_NAME" />
											<s:hidden name="searchDetailsList[%{#stat.index}].OTM_TEST_NAME" value="%{OTM_TEST_NAME}"/>
										</td>
										<td align="left">
											<s:property value="OTM_DESCRIPTION" />
											<s:hidden name="searchDetailsList[%{#stat.index}].OTM_DESCRIPTION" value="%{OTM_DESCRIPTION}"/>
										</td>
										<td align="left">
											<s:property value="OTM_FROM_DATE" />
											<s:hidden name="searchDetailsList[%{#stat.index}].OTM_FROM_DATE" value="%{OTM_FROM_DATE}"/>
										</td>
										<td align="left">
											<s:property value="OTM_TO_DATE" />
											<s:hidden name="searchDetailsList[%{#stat.index}].OTM_TO_DATE" value="%{OTM_TO_DATE}"/>
										</td>
										<td align="left">
											<s:property value="OTM_PAYMENT_START_DATE" />
											<s:hidden name="searchDetailsList[%{#stat.index}].OTM_PAYMENT_START_DATE" value="%{OTM_PAYMENT_START_DATE}"/>
										</td>
										<td align="left">
											<s:property value="OTM_PAYMENT_END_DATE" />
											<s:hidden name="searchDetailsList[%{#stat.index}].OTM_PAYMENT_END_DATE" value="%{OTM_PAYMENT_END_DATE}"/>
										</td>
										<td align="left">
											<s:property value="dateOfNotification" />
											<s:hidden name="searchDetailsList[%{#stat.index}].dateOfNotification" value="%{dateOfNotification}"/>
										</td>
										
										<td align="left">
											<s:property value="examName" />					
										
											<s:hidden name="searchDetailsList[%{#stat.index}].examName" value="%{examName}"/>
												
										</td>
										
										<td align="left">
											<s:property value="advertisementnumber" />					
										
											<s:hidden name="searchDetailsList[%{#stat.index}].advertisementnumber" value="%{advertisementnumber}"/>
												
										</td>
										
										
										
										
										<s:hidden name="searchDetailsList[%{#stat.index}].OTM_STATUS" value="%{OTM_STATUS}"/>
										<s:if test='%{OTM_STATUS=="A"}'>
											<td align="left">
												Active
											</td>
										</s:if>
										<s:if test='%{OTM_STATUS=="D"}'>
											<td align="left">
												Inactive
											</td>
										</s:if>
										
								

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
								<s:else>
									<table>
										<tr>
											<td>
												&nbsp;
											</td>
										</tr>
									</table>
								</s:else>
							</tbody>
						</table>
					</div>
						<s:token />
					</s:form>
				</div>
			</s:if>
		</div>
	</div>
	</div>
</div>
</div>


