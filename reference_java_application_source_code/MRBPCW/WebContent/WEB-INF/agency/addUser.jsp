<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<script src="js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/AES.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	
		var roleType=$("#roleType").val();
		if(roleType!=0){
			getRole();	
		}
	});
	function getRole()
	{
		var dataString = "roleType="+$("#roleType").val();
	
		$.ajax({
				url: "RoleMasterAction_getRole.action",
				data: dataString,
				beforeSend: function()
				{
						$('#role').empty();
						$("#role").append(
						           $('<option></option>').val("0").html("All")
						     );
					
				},
				error:function(ajaxrequest)
				{
					alert('Error refreshing. Server Response: '+ajaxrequest.responseText);
				},
				success:function(responseText)
				{
					responseText = $.trim(responseText);
					if(responseText.length > 0)
					{
						element = responseText.split(",");  
						
						message = responseText.substring(2, responseText.length);
						if(element[0] == "9")
						{
							//alert(message);
							//return false;
						}
						else
						{
							$.each(element, function(val) {
							
							  var nameAndIDArr = element[val].split("#");
							  
							  $("#role").append(
							           $('<option></option>').val(nameAndIDArr[0]).html(nameAndIDArr[1])
							     );
						 	}); 
						}
					}
				},
				complete:function()
				{
					var role=0;
						role='<s:property value="role"/>';
						if(role!=0){
							$("#role").val(role);
							menu();
						}
				}
			});
	}

	function changeAction() {
		var roleType=$("#roleType").val();
		var ulID = "error-ul_user";
				var divID = "error-massage_user";
		var message="";
		if(roleType==0){
			message = message + "Please select Role Type."+"##";
			
		}else{
			$("#UserMasterId").attr('action',
					"UserMasterAction_searchUser.action");
			$("#UserMasterId").submit();
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
		$("#UserMasterId").attr('action', "UserMasterAction_userMasterHome.action");
		$("#UserMasterId").submit();
	}

	function resetFieldValue() {
		$("#UserMasterId")
				.attr('action', "UserMasterAction_resetUser.action");
		$("#UserMasterId").submit();
	}
	function editCity(testPk) {
		var userId = document.createElement("input");
		
		userId.setAttribute("type", "hidden");
		userId.setAttribute("name", "userPk");
		userId.setAttribute("value", testPk);
		
		document.getElementById("UserMasterId").appendChild(
				userId);

		$("#UserMasterId").attr('action', "UserMasterAction_editUserDetails.action");
		$("#UserMasterId").submit();
	}
</script>
<style>
.onetime{
  	display: none;
  }
</style>
<div class="container"> 
<div class="container common_dashboard">
<%@ taglib prefix="s" uri="/struts-tags"%>


<div class="main-body">
	<div class="fade" id="pop3"></div>

	<div id="SelectTest">

		<h1 class="pageTitle" title="User Master">
			User Master
		</h1>
		<div class="hr-underline2"></div>

		<!-- Box Container Start -->


		<div class="clear">
			<s:form action="UserMasterAction" id="UserMasterId"
				name="UserMasterId">
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
				<table width="100%" align="left"  class="contenttableNew">
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
							Role Name
						</td>
						<td>

							<s:select name="role" id="role" list="#{'0':'All'}"/>
						</td>
					</tr>
					<tr>
						<td class="field-label">
							User ID
						</td>
						<td>

							<s:textfield name="userId" id="userId" maxlength="50" onkeypress="return alphaNumericWithSpace(event);"/>
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
					<td align="left" >
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

				<div class="clear">
					 
							 

 
					<div class="fl-rigt fifty">
						&nbsp;
					</div>
				</div>

				<!-- Row Four End -->
			<br/>
			<s:token />
			</s:form>

<!--			<div class="height20"></div>-->
			<s:if test="%{showGrid=='true'}">
				<!-- Dash Line Start -->
<!--				<div class="clear hr-dashline"></div>-->
				<!-- Dash Line End -->

				<div class="AgencyPayDrid" id="gridDiv1">
				
					<s:form method="post" action="UserMasterAction"  id="moduleDetails" name="paginationForm">
						<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
						<s:hidden name="roleType" value="%{roleType}" />
						<s:hidden name="role"  value="%{role}"/>
						<s:hidden name="userId"  value="%{userId}"/>
						<s:hidden name="status"  value="%{status}"/>
						<div class="clear hr-dashline"></div><br/>
						<br/>
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
							bordercolor="#CCC"
							 >
							<thead>
								<tr>
									<th style="width: 1.6%">
										Sr. No.
									</th>
									<th style="width: 10.6%" align="left">
										User ID
									</th>
									
									<th style="width: 16.6%" align="left">
										User Name
									</th>
									
									<th style="width: 16.6%" align="left">
										Email ID
									</th>
									
									<th style="width: 16.6%" align="left">
										Mobile No.
									</th>
									
									<th style="width: 16.6%" align="left">
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
											<s:hidden name="searchDetailsList[%{#stat.index}].userPk" value="%{userPk}"/>
										</td>
										
										<td style="width: 10.6%" align="left">
											<a href="#"
												onclick="editCity('<s:property value="userPk" />');">
												<s:property value="userId" />
												<s:hidden name="searchDetailsList[%{#stat.index}].userId" value="%{userId}"/>
											</a>
										</td>
										<td style="width: 16.6%" align="left">
											<s:property value="userName" />
											<s:hidden name="searchDetailsList[%{#stat.index}].userName" value="%{userName}"/>
										</td>
										<td style="width: 10.6%" align="left">
											
												<s:property value="email" />
											
										</td>
										<td style="width: 16.6%" align="left">
											<s:property value="mobile" />
											
										</td>
										
										<s:hidden name="searchDetailsList[%{#stat.index}].status" value="%{status}"/>
										<s:if test='%{status=="A"}'>
											<td style="width: 16.6%" align="left">
												Active
											</td>
										</s:if>
										<s:if test='%{status=="D"}'>
											<td style="width: 16.6%" align="left">
												Inactive
											</td>
										</s:if>



									</tr>
								</s:iterator>
								<s:if test='%{availableRecordFlag == "false"}'>
									<tr>
										<td colspan="6" align="center">
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
