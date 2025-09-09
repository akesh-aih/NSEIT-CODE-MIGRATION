<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">

$(document).ready(function() {
	<s:if test='%{popUpFlag=="Y"}'>
		 var x;
		    if (confirm("Module Updated successfully!!") == true) {
		        x = "OK";
		        $("#ModuleAddIdPopup").attr('action', "ModuleMasterAction_searchModule.action?menuKey="+<%=request.getAttribute("menuKey")%>+"&syllabusValue=-1&status=B");
				$("#ModuleAddIdPopup").submit();
		    }
	</s:if>	
	
	 var dataString = "cache=Y";
		function loadModule(){
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
    	loadModule();
});
</script>

<script type="text/javascript" src="js/AES.js"></script>
<script type="text/javascript">
	
	function addSection(){
	
	$("#ModuleAddId").attr('action',"ModuleMasterAction_addSection.action");
	$("#ModuleAddId").submit();
}	
	function changeActionCancel(){
		
		$("#ModuleAddId").attr('action',"ModuleMasterAction_moduleMasterHome.action");
		$("#ModuleAddId").submit();
	}
	
	function deleteRow(){
	$("#ModuleAddId").attr('action',"ModuleMasterAction_deletemoduleRowEdit.action");
	$("#ModuleAddId").submit();
}

	function alphaNumericOnly(e){
		var unicode=e.charCode? e.charCode : e.keyCode
		if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && (unicode<48||unicode>57) && unicode != 8 && unicode != 9  && unicode!= 39){
			
			return false;
		}else if(unicode== 39){
			return false;
		}
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
		$("#ModuleAddId").attr('action',"ModuleMasterAction_updateModuleDetails.action");
		$("#ModuleAddId").submit();
	/*}*/

	}	
	
    function numbersonly(e){
		var unicode=e.charCode? e.charCode : e.keyCode
		if (unicode!=8 ){
		if ((unicode<48||unicode>57)) //if not a number
			return false //disable key press
		}
	}

    function alphaNumericOnly(e){
		var unicode=e.charCode? e.charCode : e.keyCode
		if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && (unicode<48||unicode>57) && unicode != 8 && unicode != 9  && unicode!= 39){
		
			return false;
		}else if(unicode== 39){
			return false;
		}
	}

    function setExamName(objValue) {
		dataString = "examCodeValue="+objValue
			$.ajax({
				url: "ModuleMasterAction_getExamName.action",
				async: true,
				data: dataString,
				beforeSend: function()
				{
						$("examName").val("");
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
						 $("#examName").val(responseText);
					}else{
						$("#examName").val("");
					}
				},
				complete:function()
				{
					// nothing to do
				}
			});
	}

</script>



<div class="main-body">
<div class="fade" id="pop3"></div>

<div id="SelectTest">

<h1 class="pageTitle" title="Edit Module">Edit Module</h1>
<div class="hr-underline2"></div>

<!-- Box Container Start -->


<div class="clear">

<s:form action="ModuleMasterAction" id="ModuleAddId" name="ModuleAddId">
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
<table width="100%" align="center">
	<tr>
	<td class="field-label">Module ID <span class="manadetory-fields">*</span></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<s:textfield name="moduleCode" id="moduleCode" value="%{moduleCode}" size="10" maxlength="10" onpaste="return false;" onkeypress="return alphaNumericOnly(event);"></s:textfield>
		 <s:hidden name="moduleId" value="%{moduleId}" id = "moduleId"/>
		 <s:hidden name="editFlag" value="1" id = "editFlag"/>
		</td>
	<td class="field-label">Module Name<span class="manadetory-fields">*</span></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<s:textfield name="moduleName" id="moduleName" value="%{moduleName}" size="20" maxlength="50" onpaste="return false;"></s:textfield>
		</td>
	</tr>
	<tr>
		<td class="field-label" style='vertical-align:top'>Syllabus</td>
		<td style='vertical-align:top'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<s:textfield name="syllabusValue" id="syllabusValue" value="%{syllabusValue}" size="20" maxlength="25" onpaste="return false;"></s:textfield>
		</td>
		<td class="field-label" style='vertical-align:top'>Description	<span class="manadetory-fields">*</span></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<s:textarea name="moduleDescription" maxlength="100" id="moduleDescription" cssStyle="resize:none; width:300px; height:50px;"></s:textarea>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
		</tr>
		
		<tr>
			<td class="field-label" style='vertical-align:top'>Exam Code</td>
			<td style='vertical-align:top'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<s:select name="examCodeValue" id="examCodeValue" headerKey="-1" headerValue="Select" list="examMasterList"
							listValue="EXAM_CODE" listKey="EXAM_PK" value="%{examCodeValue}" cssStyle="width:75%;" onchange="setExamName(this.value)"/>
			</td>
			<td class="field-label" style='vertical-align:top'>Exam Name</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<s:textfield name="examName" id="examName" value="%{examName}" size="20" maxlength="25"  readonly="true"></s:textfield>
			</td>
		</tr>	
		
		
		<tr>
		<td class="field-label">Status
		
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td><td>
		<s:iterator value="selectList">
			
				<s:radio name="status" list="{selectList}" listKey="labelId" listValue="labelValue"/>&nbsp;&nbsp;&nbsp;
       	
    	</s:iterator>	</td>
	</tr>	
</table>
<br/>
<font color="red"><s:property value="deleteCountMsg"/></font>
</tr>
<table>
<tr><td align="left">
	<td><input type="button" value="Add Section" id="AddSection" class="submitBtn button-gradient" onclick="addSection();"/></td>
	<td>
		<s:if test="%{searchDetailsList.size()>0}">
			<input type="button" value="Delete Section" id="delete" class="submitBtn button-gradient" onclick="deleteRow();" cssStyle="width:100px;"/>
		</s:if><s:else>
			<input type="button" value="Delete Section" id="delete" class="submitBtn button-gradient" onclick="deleteRow();" cssStyle="width:100px;" disabled/>
		</s:else>
	</td>
	</tr>
</table>
<table cellspacing="0" border="1" width="100%" class="table_2" bordercolor="#CCC">
<thead>
<tr width="100%" >
    <th style="width: 25%;">Paper Code</th>
    <th style="width: 25%;">Paper Name</th>
    <th style="width: 25%;">Paper Short Name</th>
    <th style="width: 25%;">Select</th>
    </tr>
</thead>
<tbody>
	<s:iterator value="searchDetailsList" status="stat" var="currentObject">
		<tr>
			<td style="width: 25%;">
				
		 <s:hidden name="searchDetailsList[%{#stat.index}].moduleId" value="%{moduleId}" id = "moduleId"/>
		 <s:hidden name="searchDetailsList[%{#stat.index}].OEM_EXEMPTION_MASTER_PK" value="%{OEM_EXEMPTION_MASTER_PK}" id = "OEM_EXEMPTION_MASTER_PK"/>
				<s:textfield name="searchDetailsList[%{#stat.index}].OEM_PAPER_CODE" value="%{OEM_PAPER_CODE}" id = "OEM_PAPER_CODE" onkeypress="return alphaNumericOnly(event);" onpaste="return false;"/>
			</td>
			<td style="width: 25%;">
					<s:textfield name="searchDetailsList[%{#stat.index}].OEM_PAPER_NAME" value="%{OEM_PAPER_NAME}" id = "OEM_PAPER_NAME"/>	
			</td>
			<td style="width: 25%;">
				<s:textfield name="searchDetailsList[%{#stat.index}].OES_PAPER_SHORT_NAME" value="%{OES_PAPER_SHORT_NAME}" id = "OES_PAPER_SHORT_NAME"/>			
			</td>
			<td style="width: 25%;">
				<s:checkbox name="searchDetailsList[%{#stat.index}].resultChkBox" id="resultChkBox%{#stat.count}" cssStyle="width:50px;"></s:checkbox>
		</tr>
	</s:iterator>
	
</tbody>
</table>

<br/>

<div class="clear">
<div class="fl-left fifty">
<div align="center">

<input type="button" value="Update" title="Update" class="submitBtn button-gradient" onclick="validate();"/>
<input type="button" value="Cancel" title="Cancel" class="submitBtn button-gradient" onclick="changeActionCancel();"/>
</div>
</div>
<div class="fl-rigt fifty">&nbsp;</div>
</div>
<!-- Row Four End -->


</s:form>
<s:form id="ModuleAddIdPopup">

</s:form>
<div class="height20"></div>
<br/>
</div>

</div>
</div>


