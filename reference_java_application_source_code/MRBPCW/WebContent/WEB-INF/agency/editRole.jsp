<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">

//$(document).ready(function() {
//	<s:if test='%{popUpFlag=="Y"}'>
		 //var x;
		    //if (confirm("Role Updated successfully!!") == true) {
		      //  x = "OK";
		  //      $("#ModuleAddIdPopup").attr('action', "RoleMasterAction_searchRole.action?menuKey="+<%=request.getAttribute("menuKey")%>+"&syllabusValue=-1&status=B");
			//	$("#ModuleAddIdPopup").submit();
		//    }
	//</s:if>	
//});
</script>

<script type="text/javascript" src="js/AES.js"></script>
<script type="text/javascript">
	
	function changeActionCancel(){
		
		$("#ModuleAddId").attr('action',"RoleMasterAction_roleMasterHome.action");
		$("#ModuleAddId").submit();
	}

	function validate(){   
	//alert("add");
	/*var ulID = "error-ul_user";
	var divID = "error-massage_user";
	var d = $("#cityZoneCode").val();
	var d2 = $("#cityZoneName").val();
	var alphaExp = /^[a-zA-Z0-9\s]+$/;
	var state = $('#stateValue option:selected').text();
	var message = "";
	if (d == "" || d == ''){
		message = message+"City Zone Code cannot be blank."+"##";
	}
	if (d2 == "" || d2 == ''){
		message = message+"City Zone Name cannot be blank."+"##";
	}
	
	if(!isNaN(d2)){
     message = message+"Only numeric values are not allowed in City Name."+"##";
	}

	if (state == null ||state=="Select State" || state == 'null'){
		message = message+"Please select state ."+"##";
	}
	
	if(message != ""){
		createErrorList(message, ulID, divID); 
		$("#error-massage_user").focus();
		$('html, body').animate({ scrollTop: 0 }, 0);
		return false;
	}else{*/
		$("#ModuleAddId").attr('action',"RoleMasterAction_updateRoleDetails.action");
		$("#ModuleAddId").submit();
	/*}*/

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

<s:form action="RoleMasterAction" id="ModuleAddId" name="ModuleAddId">
<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
<div id="message" style="border: none">
<s:if test='%{showModuleDetails == "true"}'>
	<span style="color:green"><strong><s:property value="sucMsg"/></strong></span>
</s:if>
			
			<s:if test="errMsg!=null">
	 			<div id="error-massage4" class="error-massage">
		      		<div class="error-massage-text">
		      			<ul style="margin:0; margin-left:20px; padding:0;" id="error-ul3">
		      			<s:property value="errMsg" escape="false"/>
		      			</ul>
		      	 	</div>
	      		</div>
			</s:if>
</div>

<div id="error-massage_user" style="display:none" class="error-massage" >
				 <div class="error-massage-text" style="margin:0; margin-left:-40px; margin-top:30px; padding:0;">
			      	<ul style="margin:0; margin-left:20px; padding:0;" id="error-ul_user">
			      	</ul>
      		</div>
      			</div>
      			<br/>
<table width="100%" align="center" border="0" cellspacing="1" cellpadding="3">
<tr>
						<td class="field-label" width="10%"  >
							Role Type <span class="manadetory-fields">*</span>
						</td>
						<td>

							<s:select name="roleTypeA" id="roleType" headerKey="0"
								headerValue="Select" list="#{'S':'Admin'}"
								 disabled="true" value="%{roleType}"/>
								 <s:hidden name="roleType" value="%{roleType}"/>
						</td>
					</tr>
	<tr>
	<td class="field-label">Role Code</td><td>
		<s:textfield name="roleCode" id="roleCode" value="%{roleCode}" size="10" maxlength="20" readonly="true" onkeypress="return alphaNumericOnly(event);"></s:textfield>
		 <s:hidden name="moduleId" value="%{moduleId}" id = "moduleId"/>
		 <s:hidden name="editFlag" value="1" id = "editFlag"/>
		</td>
	
	</tr>
	<tr>
	<td class="field-label" style='vertical-align:top'>Role Name<span class="manadetory-fields">*</span></td><td>
		<s:textarea name="moduleName" id="moduleName" value="%{moduleName}" onpaste="return false;" maxlength="100" cssStyle="resize:none; width:300px; height:50px;" onkeypress="return alphaNumericWithSpace(event);"></s:textarea>
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
	<td>
<input type="button" value="Update" title="Update" class="submitBtn button-gradient" onclick="validate();"/>
<input type="button" value="Cancel" title="Cancel" class="submitBtn button-gradient" onclick="changeActionCancel();"/>
	</td>
	</tr>
	
</table>
<br/>
<font color="red"><s:property value="deleteCountMsg"/></font>
<br/>

<div class="clear">
<div class="fl-left fifty">
<div>
</div>
</div>
<div class="fl-rigt fifty">&nbsp;</div>
</div>
<!-- Row Four End -->
<s:token/>
</s:form>

<div class="height20"></div>
<br/>
</div>

</div>
</div>


