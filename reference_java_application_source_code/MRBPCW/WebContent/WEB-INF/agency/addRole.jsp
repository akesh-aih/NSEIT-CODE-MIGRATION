<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<script type="text/javascript" src="js/AES.js"></script>
<script type="text/javascript">
	
	function changeActionCancel(){
		$("#RoleAddId").attr('action',"RoleMasterAction_roleMasterHome.action");
		$("#RoleAddId").submit();
	}
	function validate(){   
		$("#RoleAddId").attr('action',"RoleMasterAction_addRoleDetails.action");
		$("#RoleAddId").submit();
	}	
	
	$(document).ready(function() {
		
		var dataString = "cache=Y";
		function loadRole(){
			$.ajax({
	        type: 'GET',
	        data: dataString,
	        url: 'servlet/StartupServlet.do',
	        error: function (err) {
        		
    		},
	        success: function (data) {
		        
	        }   	 
	    	});
    	}
    	loadRole();
	});
	
</script>

<%@ taglib prefix="s" uri="/struts-tags"%>
<style>
.onetime{
  	display: none;
  }
</style>
<div class="main-body">
<div class="fade" id="pop3"></div>

<div id="SelectTest">

<h1 class="pageTitle" title="Role Master">Role Master</h1>
<div class="hr-underline2"></div>

<!-- Box Container Start -->


<div class="clear">

<s:form action="RoleMasterAction" id="RoleAddId" name="RoleAddId">
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
<table width="100%" align="left" cellspacing="1" cellpadding="3">
<tr>
						<td class="field-label" width="10%"> 
							Role Type <span class="manadetory-fields">*</span>
						</td>
						<td>

							<s:select name="roleType" id="roleType" headerKey="0" cssStyle="width:300px;"  
								headerValue="Select" list="#{'S':'Admin'}"/>
								 
						</td>
					</tr>
	<tr>
		 <s:hidden name="setFlag" value="%{setFlag}" id = "setFlag"/>
	<td class="field-label">Role Code<span class="manadetory-fields">*</span></td><td>
		<s:textfield name="roleCode" id="roleCode" value="%{roleCode}" cssStyle="width:296px;"  size="20" maxlength="20" onpaste="return false;" onkeypress="return alphaNumericOnly(event);"></s:textfield>
		</td>
	</tr>
	<tr>
		<td class="field-label" style='vertical-align:top'>Role Name	<span class="manadetory-fields">*</span></td><td>
			<s:textarea name="moduleName" id="moduleName" maxlength="100" cssStyle="resize:none; width:300px; height:50px;" onkeypress="return alphaNumericWithSpace(event);"></s:textarea>
		
		</td>
		</tr>
		<tr>
		<td class="field-label">Status
		
		</td><td>
		<s:iterator value="selectList">
			
			<s:radio name="status" list="{selectList}" listKey="labelId" listValue="labelValue"/>&nbsp;&nbsp;&nbsp;
			
    	</s:iterator>	</td>
	</tr>	
	<tr>
		<td>&nbsp;</td>
		<td>		<s:if test='%{showModuleDetails == "true"}'>
			<input type="button" value="Update" title="Update" class="submitBtn button-gradient" onclick="validate();"/>
			</s:if>
			<s:else>
				<input type="button" value="Submit" title="Add" class="submitBtn button-gradient" onclick="validate();"/>
			</s:else>
		
				<input type="button" value="Cancel" title="Cancel" class="submitBtn button-gradient" onclick="changeActionCancel();"/></td>
	</tr>
	
</table>
<div class="clear"></div>
<br/><br/><br/>
<font color="red"><s:property value="deleteCountMsg"/></font>

 
<!-- Row Four End -->

<s:token />
</s:form>
<div class="height20"></div>

<br/>
</div>

</div>
</div>


