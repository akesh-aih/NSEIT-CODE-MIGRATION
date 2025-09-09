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

	function changeAction() {
		$("#ModuleMasterId").attr('action',
				"UserMasterAction_addUser.action");
		$("#ModuleMasterId").submit();
	}

	function changeActionAdd() {
		$("#ModuleMasterId").attr('action', "UserMasterAction_addUser.action");
		$("#ModuleMasterId").submit();
	}
	
	function changeActionCancel(){
		$("#ModuleMasterId").attr('action',"UserMasterAction_getUserMaster.action");
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
						           $('<option></option>').val("0").html("Select")
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
			<s:form action="ModuleMasterAction" id="ModuleMasterId">
				<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
				<div id="message" style="border: none">
				<s:if test='%{showModuleDetails == "true"}'>
					<span style="color:green"><strong><s:property value="sucMsg"/></strong></span>
				</s:if>
				</div>
				 <s:if test="errMsg!=null">
	 			<div id="error-massage_user" class="error-massage">
		      		<div class="error-massage-text">
		      			<ul style="margin:0; margin-left:20px; padding:0;" id="error-ul_user">
		      			<s:property value="errMsg" escape="false"/>
		      			</ul>
		      	 	</div>
	      		</div>
				</s:if>
				<br/>
				<table width="80%" align="center" class="contenttableNew" >
				<tr>
						<td class="field-label">
							Role Type <span class="manadetory-fields">*</span>
						</td>
						<td>

							<s:select name="roleType" id="roleType" headerKey="0"
								headerValue="Select" list="#{'S':'Admin'}"
								onchange="getRole()"/>
						</td>
						<td class="field-label">
							Role Name <span class="manadetory-fields">*</span>
						</td>
						<td>

							<s:select name="role" id="role" list="#{'0':'Select'}"
								value="%{role}" onchange="menu()"/>
						</td>
						
					</tr>
					<tr>
					<td class="field-label">
							User ID <span class="manadetory-fields">*</span>
						</td>
						<td>
							<s:textfield name="userId" id="moduleName" size="20" maxlength="50" onpaste="return false;" onkeypress="return alphaNumericOnly(event);" autocomplete="off"></s:textfield>
						</td>
					<td class="field-label">
							User Name <span class="manadetory-fields">*</span>
						</td>
						<td>
							<s:textfield name="userName" id="moduleName" size="20" maxlength="200" onpaste="return false;" onkeypress=" return nameValidator(event);" autocomplete="off"></s:textfield>
						</td>
					</tr>
					<tr>
					<td class="field-label" align="left"> 
							Email ID <span class="manadetory-fields">*</span>
						</td>
						<td>
							<s:textfield name="email" id="moduleName" size="20" maxlength="100" onpaste="return false;"></s:textfield>
						</td>
					<td class="field-label">
							Mobile Number <span class="manadetory-fields">*</span>
						</td>
						<td>
							<s:textfield name="mobile" id="moduleName" size="20" maxlength="10" onpaste="return false;"></s:textfield>
						</td>
					</tr>
					<tr>
					<td class="field-label">
							Password <span class="manadetory-fields">*</span>
						</td>
						<td>
							<s:password name="password" id="passowrd" size="22" maxlength="50" />
						</td>
					<td class="field-label">
							Confirm Password <span class="manadetory-fields">*</span>
						</td>
						<td>
							<s:password name="confirmPassword" id="confirmPassowrd" size="22" maxlength="50" />
						</td>
					</tr>
					<tr>
						<td class="field-label">Status</td><td align="left"> 
						<s:iterator value="selectList">
						<s:radio name="status" list="{selectList}" listKey="labelId" listValue="labelValue"/>&nbsp;&nbsp;&nbsp;
						</s:iterator>
   					</td>
					</tr>	
				</table>
				<br />


				<div class="clear">
					<div class="fl-left fifty">
						<div>
							<table align="right" >
								<tr>
									<td>
										&nbsp;&nbsp;
									</td>
									<td>
										<input type="button" value="Save " title="Save "
											class="submitBtn button-gradient"
											onclick="changeActionAdd();" />
									</td>
									<td>
										<input type="button" value="Cancel " title="Cancel "
											class="submitBtn button-gradient"
											onclick="changeActionCancel();" />
									</td>
								</tr>
							</table>


						</div>
					</div>
					<div class="fl-rigt fifty">
						&nbsp;
					</div>
				</div>

				<!-- Row Four End -->

			<s:token />
			</s:form>

			<div class="height20"></div>
			
		</div>
	</div>
</div>

</div>
</div>