<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<style>
.onetime{
  	display: none;
  }
.ui-datepicker-trigger {margin-top:8px; }
</style>


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
	
	function alphaNumericOnly(e){
		var unicode=e.charCode? e.charCode : e.keyCode
		if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && (unicode<48||unicode>57) && unicode != 8 && unicode != 9  && unicode!= 39){
			
			return false;
		}else if(unicode== 39){
			return false;
		}
	}
	
	function validate(){   
		$("#ModuleAddId").attr('action',"ModuleMasterAction_addModuleDetails.action");
		$("#ModuleAddId").submit();
	}	
	
    function numbersonly(e){
		var unicode=e.charCode? e.charCode : e.keyCode
		if (unicode!=8 ){
		if ((unicode<48||unicode>57)) //if not a number
			return false //disable key press
		}
	}

	function deleteRow(){
		$("#ModuleAddId").attr('action',"ModuleMasterAction_deletemoduleRowAdd.action");
		$("#ModuleAddId").submit();
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
   function decimal(num)
	{
		var finalValue = num.value;
		if(finalValue == '')
		{
			//finalValue = 0.00;
		}else
		{
			document.getElementById(num.id).value=parseFloat(finalValue).toFixed(2);
		}		
	}
	
$(document).ready(function() {
	$("#fromDate").datepicker({
			showOn: "button",
			changeMonth: true,
			changeYear: true,
			minDate:0,
			yearRange: range,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy'
	    });
	 
	    
	    $("#toDate").datepicker({
			showOn: "button",
			changeMonth: true,
			changeYear: true,
			minDate:0,
			yearRange: range,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy'
	    });
	    
	    $("#paymentStartDate").datepicker({
			showOn: "button",
			changeMonth: true,
			changeYear: true,
			minDate:0,
			yearRange: range,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy'
	    });
	    
	    $("#paymentEndDate").datepicker({
			showOn: "button",
			changeMonth: true,
			changeYear: true,
			minDate:0,
			yearRange: range,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy'
	    });
	    $("#dateOfNotification").datepicker({
			showOn: "button",
			changeMonth: true,
			changeYear: true,
			minDate:0,
			yearRange: range,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy'
	    });
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
    	//loadModule();
    	<s:iterator value="errorField">
		$("select[id='<s:property />']").addClass('red-border');
		$("input[id='<s:property />']").addClass('red-border');			
	</s:iterator>
});
	
</script>

<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="container">
<div class="container common_dashboard">
<div class="fade" id="pop3"></div>

<div id="SelectTest">

<h1 class="pageTitle" title="Module Master">Module Master</h1>
<div class="hr-underline2"></div>

<!-- Box Container Start -->


<div class="clear">

<s:form action="ModuleMasterAction" id="ModuleAddId" name="ModuleAddId">
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
<table width="100%"  >
	<tr>
		 <s:hidden name="setFlag" value="%{setFlag}" id = "setFlag"/>
	<s:if test='%{setFlag == "true"}'>
	<td class="field-label">Module Name <span class="manadetory-fields">*</span></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<s:textfield name="moduleName" id="moduleName" value="%{moduleName}" size="20" maxlength="50" onpaste="return false;"></s:textfield>
		</td>
	</s:if>
	<s:else>
	<td class="field-label">Module Name <span class="manadetory-fields">*</span></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<s:textfield name="moduleName" id="moduleName" value="%{moduleName}" size="20" maxlength="50" onpaste="return false;"></s:textfield>
		</td>
	</s:else>
	<s:if test='%{showModuleDetails == "true"}'>
	<td class="field-label">Module ID</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<s:textfield name="moduleId" id="moduleId" value="%{moduleId}" size="20" maxlength="50" onpaste="return false;" readonly="true"></s:textfield>
	</td>
	</s:if>
	</tr>
	<tr>
	<td class="field-label">Module Description<span class="manadetory-fields">*</span></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<s:textfield name="moduleDescription" id="moduleDescription" value="%{moduleDescription}" size="20" maxlength="100" onpaste="return false;"></s:textfield>
		</td>
	</tr>
	<tr>
		<td class="field-label" style='vertical-align:top'>Application Start Date <span class="manadetory-fields">*</span></td>
		<td style='vertical-align:top'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<s:textfield id="fromDate" name="fromDate" readonly="true"> </s:textfield>
		</td>
		<td class="field-label" style='vertical-align:top'>Application End Date	<span class="manadetory-fields">*</span></td>
		<td>
			<s:textfield id="toDate" name="toDate" readonly="true"> </s:textfield>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
	</tr>
	<tr>
		<td class="field-label" style='vertical-align:top'>Payment Start Date <span class="manadetory-fields">*</span></td>
		<td style='vertical-align:top'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<s:textfield id="paymentStartDate" name="paymentStartDate" readonly="true"> </s:textfield>
		</td>
		<td class="field-label" style='vertical-align:top'>Payment End Date	<span class="manadetory-fields">*</span></td>
		<td>
			<s:textfield id="paymentEndDate" name="paymentEndDate" readonly="true"> </s:textfield>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
	</tr>
	
	<tr>
		<td class="field-label" style='vertical-align:top'>Date of Notification <span class="manadetory-fields">*</span></td>
		<td style='vertical-align:top'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<s:textfield id="dateOfNotification" name="dateOfNotification"> </s:textfield>
		</td>
		
		
		<td class="field-label">Verification Post <span class="manadetory-fields">*</span></td><td>		<s:select  name="testGroup" id="testGroup"  value="%{testGroup}"list="%{testGroupList}" listKey="%{otg_test_pk}"  listValue="%{otg_test_name}" style="width:185px;" />&nbsp;&nbsp;&nbsp;
		</td>
		
	</tr>
	
	<tr>
		<td class="field-label">Status</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<s:iterator value="selectList">
			<s:radio  name="status" list="{selectList}" listKey="labelId" listValue="labelValue"/>&nbsp;&nbsp;&nbsp;
    	</s:iterator>	</td>
    	
    	<td class="field-label">Advertisement Number <span class="manadetory-fields">*</span></td>
    	<td> 
		<s:textfield name="advertisementnumber" id="advertisementnumber" value="%{advertisementnumber}" size="20" maxlength="50" onpaste="return false;"></s:textfield>
		</td>
    	
	</tr>	
	<tr>
	<br/>
	<td>
	</td>
	</tr>
</table>
<br/>
<font color="red"><s:property value="deleteCountMsg"/></font>
<div style="display:none">
<table>
<tr>
<s:if test='%{showModuleDetails == "true"}'>
	<td><input type="button" value="Add Module" id="AddSection" class="submitBtn button-gradient" onclick="validate();"/></td>
	</s:if>
	<s:else>
	<td><input type="button" value="Add Module" id="AddSection" class="submitBtn button-gradient" onclick="validate();" disabled/></td>
	</s:else>
	<td>
		<s:if test="%{searchDetailsList.size()>0}">
			<input type="button" value="Delete Section" id="delete" class="submitBtn button-gradient" onclick="deleteRow();" cssStyle="width:100px;"/>
		</s:if><s:else>
			<input type="button" value="Delete Section" id="delete" class="submitBtn button-gradient" onclick="deleteRow();" cssStyle="width:100px;" disabled/>
		</s:else>
	</td>

</tr>
</table>
</div>
<div style="display: none">
<table cellspacing="0" border="1" width="100%" class="table_2" bordercolor="#CCC">
<thead>
<tr width="100%" >
    <th style="width: 25%;">Paper Code</th>
    <th style="width: 25%;">Paper Name</th>
    <th style="width: 25%;">Paper Short Name</th>
    <th style="width: 25%;"></th>
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
				</td>
		</tr>
	</s:iterator>
</tbody>
</table>
</div>


<h1 class="pageTitle" title="Add Module Fees">Add Module Fees</h1>
<div class="hr-underline2"></div>
<div>

<table cellspacing="0" border="1" width="100%" class="table_2" bordercolor="#CCC"  >
	<thead>
		<tr>
			<th style="width: 20%" class="tCenter">
				Category
			</th>
			<th style="width: 30%" class="tCenter">
				Fees
			</th>
			<th style="width: 50%" class="tCenter">
				Fees in Words
			</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="searchCategoryList" status="stat" var="currentObject">
			<tr>
				<td style="width: 20%"  align="left">
					<s:property value="OCTM_CATEGORY_CODE" />
					<s:hidden name="searchCategoryList[%{#stat.index}].OCTM_CATEGORY_CODE" value="%{OCTM_CATEGORY_CODE}"/>
				</td>
				<td style="width: 30%" align="center">
					<s:textfield cssStyle="text-align: right" name="searchCategoryList[%{#stat.index}].fees" id="fees%{OCTM_CATEGORY_CODE}" value="%{fees}" size="20" maxlength="9" onpaste="return false;" onkeypress="return numbersonly(event);" onblur="decimal(this);"></s:textfield>
				</td>
				<td style="width: 50%" align="center">
					<s:textfield name="searchCategoryList[%{#stat.index}].feesInWords" id="feesInWords%{OCTM_CATEGORY_CODE}" value="%{feesInWords}" size="30" maxlength="200" style="width:500px" onpaste="return false;" onkeypress=" return alphabetsWithSpace(event);"></s:textfield>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>

</div>
<br/>
<div class="clear">
<div class="fl-left fifty" style="width:100%;">
<div align="center">
<br/>
	<s:if test='%{showModuleDetails == "true"}'>
	<input type="button" value="Update" title="Update" class="submitBtn button-gradient" onclick="validate();"/>
	</s:if>
	<s:else>
	<input type="button" value="Submit" title="Add" class="submitBtn button-gradient" onclick="validate();"/>
	</s:else>

	<input type="button" value="Cancel" title="Cancel" class="submitBtn button-gradient" onclick="changeActionCancel();"/>
</div>
</div>
<div class="fl-rigt fifty">&nbsp;</div>
</div>
<!-- Row Four End -->

<s:token />
</s:form>



<div class="height20"></div>




<br/>




</div>

</div>
</div>
</div>


