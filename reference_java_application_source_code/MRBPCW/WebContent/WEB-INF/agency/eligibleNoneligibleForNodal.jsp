<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<script type="text/javascript" src="js/AES.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#datepicker3").datepicker({
			showOn: "button",
			buttonImageOnly: true,
			changeMonth: true,
			changeYear: true,
			yearRange: range,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
			maxDate: 0,
	        numberOfMonths: 1,
	        onSelect: function(selected) {
	          $("#datepicker4").datepicker("option","minDate", selected)
	        }
	    });
	    $("#datepicker4").datepicker({
	    	showOn: "button",
			buttonImageOnly: true,
			changeMonth: true,
			changeYear: true,
			yearRange: range,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
			maxDate: 0,
	        numberOfMonths: 1,
	        onSelect: function(selected) {
	           $("#datepicker3").datepicker("option","maxDate", selected)
	        }
	    });
	    
	    $(".disableEnroll").change(function(){
	    });
	    
	});
	
	function imposeMaxLength(Event, Object, MaxLen)
	{
		return (Object.value.length <= MaxLen)||(Event.keyCode == 8 ||Event.keyCode==46||(Event.keyCode>=35&&Event.keyCode<=40))
	}
	function numbersonly(e){
		var unicode=e.charCode? e.charCode : e.keyCode
		if (unicode!=8 ){
		if ((unicode<48||unicode>57) && unicode != 46) //if not a number
			return false //disable key press
		}
	}
	
	function resetPaySearch()
	{
		$("#error-massage3").hide();
		$("#gridDiv1").hide();
		$("#gridDiv2").hide();
		$("#actionMsgError").hide();
		$("#userId").val("");
		$("#candidateStatusId").val("-1");
		$("#disciplineId").val("0");
		$("#datepicker3").val("");
		$("#datepicker4").val("");
		$("#labelId").removeAttr("disabled");
		$("#datepicker3").removeAttr("disabled");
		$("#datepicker4").removeAttr("disabled");
	}
	
$(function(){
	// add multiple select / deselect functionality
    $("#checkMe").click(function () {
          $('.checkBoxClass').attr('checked', this.checked);
    });
 
    // if all checkbox are selected, check the selectall checkbox
    // and viceversa
    $(".checkBoxClass").click(function(){
 
        if($(".checkBoxClass").length == $(".checkBoxClass:checked").length) {
            $("#selectall").attr("checked", "checked");
        } else {
            $("#selectall").removeAttr("checked");
        }
 
    });
});


function validateForApproval() {
	var selected="false";	
	var message = "";
	
	$(document).find(".checkBoxClass").each(function(index, curr){
		
		if(($(curr).is(":checked")+"")=="true")
		{
		
			selected="true";
		}
	});

	if(selected=="false")
	{
		message = message + "Please Select Atleast One Checkbox for Candidate Approval";
		var ulID = "error-ul3";
		var divID = "error-massage3";
		createErrorList(message, ulID, divID); 
		$("#actionMsgError").hide(); 
		return false;
	}
	return true;
}

function validateForRejection() {
	var selected="false";	
	var message = "";
	$(document).find(".checkBoxClass").each(function(index, curr){
		
		if(($(curr).is(":checked")+"")=="true")
		{
		
			selected="true";
		}
	});

	if(selected=="false")
	{
		message = message + "Please Select Atleast One Checkbox for Candidate Rejection";
		var ulID = "error-ul3";
		var divID = "error-massage3";
		createErrorList(message, ulID, divID);
		$("#actionMsgError").hide(); 
		return false;
	}
	return true;
}

function changeAction(){
	$("#eligible").attr('action',"CandidateAction_searchCandidate.action");
	$("#eligible").submit();
}
function gotoUserDetail(param){
	window.open("CandidateAction_getCandidateDetailsForNodal.action?userId="+param);
}
function approvalReceipt(param){
	window.open("CandidateAction_approvalReceipt.action?userId="+param);
}
function selectAllChkBoxes(){
	if ($('#selectAllChkBox').attr('checked')){
		$(".checkBoxClass").attr('checked','checked');
	}
	if (!($('#selectAllChkBox').attr('checked'))){
		$(".checkBoxClass").removeAttr('checked');
	}
}
function deSelectAllChkBoxes(){
 var flag = true;
		$(".checkBoxClass").each(function(index, value) {
		if (flag){
			 if ($(this).attr('checked')){
	 		  	$('#selectAllChkBox').attr('checked','checked');
	 		  }else{
	 		  	$('#selectAllChkBox').removeAttr('checked');
	 		  	flag = false;
	 		  }
			}
		});
}
</script>

<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="main-body">
<div class="fade" id="pop3"></div>

<div id="SelectTest">

<h1 class="pageTitle" title="Candidate Approval">Candidate Approval</h1>
<div class="hr-underline2"></div>
<div id="error-massage3" style="display:none" class="error-massage">
      		<div class="error-massage-text">
      		<ul style="margin:0; margin-left:20px; padding:0;" id="error-ul3">
      		</ul>      	
      	 </div>
</div>
<s:if test="hasActionMessages()">
  <div class="error-massage-text"  id="actionMsgError">
    <ul style="margin:0; margin-left:20px; padding:0;">
    	 <s:actionmessage/>
    </ul>      	
 </div> 
</s:if>
<s:if test='%{candidateApproveRejectMessage == "Y"}'>
<div style="border:#999 1px solid;padding:3px;color:green;">
 		<s:property value="#attr['successMsg']" escape="false"/>
</div>
</s:if>

<!-- Box Container Start -->


<!-- Payment Fields Start -->
<div class="clear">
<s:form action="CandidateMgmtAction" id="eligible">
<input type="hidden" name="menuKey" value='<%=request.getAttribute("menuKey")%>'/>
<s:token></s:token>
<table width="100%">
	<tr>
		<td class="field-label">User ID&nbsp;</td>
		<td><s:textfield name="userId" id="userId" onkeypress="return alphaNumeric(event)" onpaste="return false;"></s:textfield></td>
	</tr>
	<tr>
		<td class="field-label">Course&nbsp;</td>
		<td><s:select label="Discipline" name="disciplineId" 
		headerKey="0" headerValue="All" list="discliplineList" id="disciplineId" value="%{disciplineId}"/></td>
		
		<td class="field-label">Status&nbsp;</td>
		<td><s:select label="Status" name="candidateStatusId" 
		headerKey="-1" headerValue="All" list="candidateStatusList" id="candidateStatusId" value="%{candidateStatusId}"/></td>
	</tr>
	<tr>
		<td class="field-label">From Date&nbsp;</td>
		<td><s:textfield name="fromDate" id="datepicker3" disabled="disabled" readonly="true" cssStyle="width:116px;" cssClass="inputDate disableEnroll"></s:textfield></td>
		
		<td class="field-label">To Date&nbsp;</td>
		<td><s:textfield name="toDate" id="datepicker4" disabled="disabled" readonly="true" cssStyle="width:116px;" cssClass="inputDate disableEnroll"></s:textfield></td>
	</tr>
</table>
<br/>

<div class="clear">
<div class="fl-left fifty">
<div>
<input type="button" value="Search" class="submitBtn button-gradient" onclick="changeAction();"/>&nbsp;&nbsp;
<input type="button" value="Clear" title="Clear" class="submitBtn button-gradient" onclick="resetPaySearch();"/>
</div>
</div>
<div class="fl-rigt fifty">&nbsp;</div>
</div>
<!-- Row Four End -->


</s:form>

<div class="height20"></div>


<!-- Dash Line Start -->
<div class="clear hr-dashline"></div>
<!-- Dash Line End -->
<div class="AgencyPayDrid" id="gridDiv1" >
<s:form method="post" action="CandidateAction_searchCandidate.action" name="paginationForm">
<input type="hidden" name="menuKey" value='<%=request.getAttribute("menuKey")%>'/>
<input type="hidden" name="disciplineId" value='<s:property value="disciplineId"/>'/>
<input type="hidden" name="candidateStatusId" value='<s:property value="candidateStatusId"/>'/>
<input type="hidden" name="userId" value='<s:property value="userId"/>'/>
<input type="hidden" name="fromDate" value='<s:property value="fromDate"/>'/>
<input type="hidden" name="toDate" value='<s:property value="toDate"/>'/>

<s:if test='%{flag == "true"}'>
<table width="100%">
	<tr class="box-header">
		<td align="left" width="30%" >Candidate Details</td>
		<td align="center" width="30%" class="pagination-label">Total <font color="red"><s:property value="candidateListSize"/></font> Candidates Found </td>
		<td width="40%">
			<s:if test='%{candidateListSize=="0"}'>
			</s:if><s:else><%@ include file="../pagination.jsp" %></s:else>
		</td>
	</tr>
</table>

<br/>
<table cellspacing="0" border="1" width="100%" class="table_2" bordercolor="#CCC">
<thead>
    <tr>
    <th style="width: 5%;"><input type="checkbox" id = "selectAllChkBox" onclick = "selectAllChkBoxes()" align="left" style="text-align: center;" value="0.000"></th>
    <th style="width: 10%;">User ID</th>
    <th style="width: 40%;">Name</th>
    <th style="width: 10%;">Course</th>
    <th style="width: 10%;">Category</th>
    <th style="width: 20%;">Remark</th>
    <th style="width: 5%;">Approval Receipt</th>
    </tr>
</thead>
<tbody>
<s:iterator value="listOfRegisteredCandidate" status="stat" var="currentObject">
		<tr>
			<td align="center">
				<s:if test='%{#currentObject.OCD_VALIDATED_STATUS == 1}'>
					<s:checkbox name="listOfRegisteredCandidate[%{#stat.index}].checkBoxValue" value="%{#currentObject.checkBoxValue}" cssClass="checkBoxClass1" disabled="true"/>	
				</s:if>
				<s:else>
					<s:checkbox name="listOfRegisteredCandidate[%{#stat.index}].checkBoxValue" value="%{#currentObject.checkBoxValue}" cssClass="checkBoxClass" onclick = "deSelectAllChkBoxes()"/>
				</s:else>
				<s:hidden name="listOfRegisteredCandidate[%{#stat.index}].OUM_USER_ID" value="%{OUM_USER_ID}"></s:hidden>
				<s:hidden name="listOfRegisteredCandidate[%{#stat.index}].OUM_USER_PK" value="%{OUM_USER_PK}"></s:hidden>
				<s:hidden name="listOfRegisteredCandidate[%{#stat.index}].OTM_TEST_NAME" value="%{OTM_TEST_NAME}"></s:hidden>
			</td>
			<td align="center">
				<a href="#" onclick="gotoUserDetail('<s:property value="OUM_USER_ID" />')"><s:property value="OUM_USER_ID" /></a> 
			</td>
			<td align="center">
			<s:property value="OCD_CAND_TITLE" /><s:property value="OCD_FIRST_NAME" /> <s:property value="OCD_MIDDLE_NAME" /> <s:property value="OCD_LAST_NAME" />
			</td>
			<td align="center">
			<s:property value="OTM_TEST_NAME" />
			</td>
			<td align="center">
				<s:property value="OCTM_CATEGORY_CODE" />
			</td>
			<td align="center">
			<s:if test='%{#currentObject.OCD_VALIDATED_STATUS == 1}'>
				<s:textarea name="listOfRegisteredCandidate[%{#stat.index}].OCD_REMARKS" value="%{OCD_REMARKS}" label="OCD_REMARKS" cssClass="AddressArea" cssStyle="height:35px;" cssClass="AddressArea validate[required] maxSize[100]" onkeypress="return imposeMaxLength(event,this,100);" onchange="return imposeMaxLength(event,this,100);" title="Max length is 100 chars" readonly="true"></s:textarea>
			</s:if>
			<s:else>
				<s:textarea name="listOfRegisteredCandidate[%{#stat.index}].OCD_REMARKS" value="%{OCD_REMARKS}" label="OCD_REMARKS" cssClass="AddressArea" cssStyle="height:35px;" cssClass="AddressArea validate[required] maxSize[100]" onkeypress="return imposeMaxLength(event,this,100);" onchange="return imposeMaxLength(event,this,100);" title="Max length is 100 chars"> </s:textarea>
			</s:else>
			</td>
			<td align="center">
			<s:if test='%{#currentObject.OCD_VALIDATED_STATUS == 1}'>
					<img src="images/recipt-icon.jpg" onclick="approvalReceipt('<s:property value="OUM_USER_ID" />')" border="0" title="Approval Receipt"/>
				</s:if>
			</td>
			
		</tr>
</s:iterator>
<s:if test='%{availableRecordFlag == "false"}'>
	<tr>
		<td colspan="7" align="center">
			<div>
				<b>No Record Found</b>
			</div>
		</td>
	</tr>
</s:if>
</tbody>
</table>
</s:if>
<!-- Button Start -->
<s:if test='%{availableRecordFlag == "true" && candidateStatusId!= 1}'>
<div class="height20"></div>
<div class="clear">
<s:submit  value="Approve" cssClass="submitBtn button-gradient" method="approveCandidateNodal" onclick="return validateForApproval();"/>
&nbsp; &nbsp;
<s:submit  value="Reject" cssClass="submitBtn button-gradient" method="rejectCandidateNodal" onclick="return validateForRejection();"/>
</div>
</s:if>
<!-- Button End -->
</s:form>
</div>
</div>
</div>
</div>

