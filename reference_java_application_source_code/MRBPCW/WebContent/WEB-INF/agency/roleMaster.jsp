<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<script src="js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/AES.js"></script>
<script type="text/javascript">

	function changeAction() {
		var roleType=$("#roleType").val();
		var ulID = "error-ul_user";
				var divID = "error-massage_user";
		var message="";
		if(roleType==0){
			message = message + "Please select Role Type."+"##";
			
		}else{
			$("#RoleMasterId").attr('action',
					"RoleMasterAction_searchRole.action");
			$("#RoleMasterId").submit();
		}
		
		if(message != ""){
					createErrorList(message, ulID, divID); 
					$("#error-massage_user").focus();
					$('html, body').animate({ scrollTop: 0 }, 0);
					//$('html, body').animate({ scrollTop: 0 }, 'slow');
					return false;
				}else{
				
					$("#error-massage_user").hide();
				
					  return true;
				}
	}

	function changeActionAdd() {
		$("#RoleMasterId").attr('action', "RoleMasterAction_addRole.action");
		$("#RoleMasterId").submit();
	}

	function resetFieldValue() {
		$("#RoleMasterId")
				.attr('action', "RoleMasterAction_resetRole.action");
		$("#RoleMasterId").submit();
	}
	function editCity(testPk) {
		var moduleId = document.createElement("input");
		
		moduleId.setAttribute("type", "hidden");
		moduleId.setAttribute("name", "moduleId");
		moduleId.setAttribute("value", testPk);
		
		document.getElementById("RoleMasterId").appendChild(
				moduleId);

		$("#RoleMasterId").attr('action', "RoleMasterAction_editRoleDetails.action");
		$("#RoleMasterId").submit();
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

		<h1 class="pageTitle" title="Role Master">
			Role Master
		</h1>
		<div class="hr-underline2"></div>

		<!-- Box Container Start -->


		<div class="clear">
			<s:form action="RoleMasterAction" id="RoleMasterId"
				name="RoleMasterId">
				<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
				<div id="error-massage_user" style="display: none"
					class="error-massage">
					<div class="error-massage-text"
						style="margin: 0;  margin-top: 30px; padding: 0;">
						<ul style="margin: 0; margin-left: 20px; padding: 0;"
							id="error-ul_user">
						</ul>
					</div>
				</div>
				<table width="100%" align="left"  class="contenttableNew" border="0" cellspacing="1" cellpadding="3">
					<tr>
						<td class="field-label" width="15%">
							Role Type <span class="manadetory-fields">*</span>
						</td>
						<td>

							<s:select name="roleType" id="roleType" headerKey="0"
								headerValue="Select" list="#{'S':'Admin'}"
								onchange="getRole()"/>
						</td>
					</tr>
					<tr>
						<td class="field-label">
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
						<td align="left">
										<input type="button" value="Search"
											class="submitBtn button-gradient" onclick="changeAction();" />&nbsp;&nbsp;
									 
										<input type="reset" name="button2" id="button2" value="Reset"
											class="submitBtn button-gradient"
											onclick="resetFieldValue();" />&nbsp;&nbsp;
									 
										<input type="button" value="Add " title="Add "
											class="submitBtn button-gradient"
											onclick="changeActionAdd();" />
									</td>
					</tr>
					
				</table>
				
				<br/>
<div class="clear"></div>
				<br/> 

				<!-- Row Four End -->
		 
			<s:token />
			</s:form>
<div class="clear hr-dashline"></div>
<!--			<div class="height20"></div>-->
			<s:if test="%{showGrid=='true'}">
				<!-- Dash Line Start -->
<!--				<div class="clear hr-dashline"></div>-->
				<!-- Dash Line End -->

				<div class="AgencyPayDrid" id="gridDiv1">
					<s:form method="post" action="RoleMasterAction"  id="moduleDetails" name="paginationForm">
						<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
						<s:hidden name="syllabusValue"  />
						<s:hidden name="status"  />
						 
						<table width="100%">
							<tr class="box-header">
								<td align="left" width="30%">
									Role Details
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
						<table cellspacing="0" border="1" width="100%" class="table_2"
							bordercolor="#CCC" >
							<thead>
								<tr>
									<th style="width: 1.6%">
										Sr. No.
									</th>
									<th style="width: 10.6%">
										Role Code
									</th>
									
									<th style="width: 16.6%">
										Role Name
									</th>
									
									<th style="width: 16.6%">
										Status
									</th>

								</tr>
							</thead>
							<tbody>

								<s:iterator value="searchDetailsList" status="stat"
									var="currentObject">
									<tr>
										<td style="width: 1.6%" align="center">
											<s:property value="#stat.index + 1"/>
											<s:hidden name="searchDetailsList[%{#stat.index}].ORM_ROLE_PK" value="%{ORM_ROLE_PK}"/>
										</td>
										
										<td style="width: 10.6%" align="center">
											<a href="#"
												onclick="editCity('<s:property value="ORM_ROLE_PK" />');">
												<s:property value="ORM_ROLE_CODE" />
												<s:hidden name="searchDetailsList[%{#stat.index}].ORM_ROLE_CODE" value="%{ORM_ROLE_CODE}"/>
											</a>
										</td>
										<td style="width: 16.6%" align="center">
											<s:property value="ORM_ROLE_DESC" />
											<s:hidden name="searchDetailsList[%{#stat.index}].ORM_ROLE_DESC" value="%{ORM_ROLE_DESC}"/>
										</td>
										
										
										<s:hidden name="searchDetailsList[%{#stat.index}].ORM_STATUS" value="%{ORM_STATUS}"/>
										<s:if test='%{ORM_STATUS=="A"}'>
											<td style="width: 16.6%" align="center">
												Active
											</td>
										</s:if>
										<s:if test='%{ORM_STATUS=="D"}'>
											<td style="width: 16.6%" align="center">
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
						<s:token />
					</s:form>
				</div>
			</s:if>
		</div>
	</div>
</div>

</div>
</div>
