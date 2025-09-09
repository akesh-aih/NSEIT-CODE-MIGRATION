<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<script type="text/javascript" src="js/AES.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#paymentApproval").validationEngine({promptPosition : "bottomLeft", scroll: false});
<%--		$("#paymentApprovalSubmit").click(function()--%>
<%--		{--%>
<%--			--%>
<%--		)};--%>
		

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
	    	//disableEnrollmentId();
	    });
	    
	    //disableDropDowns();
	    //disableEnrollmentId();
	});
	
	/*$(function() {
		$( "#datepicker3" ).datepicker({
			showOn: "button",
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy'
		});
	});
	
	$(function() {
		$( "#datepicker4" ).datepicker({
			showOn: "button",
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy'
		});
	});


	$(function() {
		$( ".datePicker" ).datepicker({
			showOn: "button",
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd/mm/yy'
		});
	});

	function dateCompare(){
		var startDt = $("#datepicker3").val();
		var endDt = $("#datepicker4").val();
		alert(startDt);
		alert(new Date(startDt));
		if((new Date(startDt) > new Date(endDt)))
		{
			alert("To date can not exceed from date");
		      return false;
		}
	}
	*/

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
	
	function disableEnrollmentId()
	{
	    	var disableEnrollId = "false";
	    	//alert('1');
	    	$(".disableEnroll").each(function(index, object){
	    		//alert('2--------- ' + $(object).val());
	    		if($(object).val()!="")
	    		{
	    			disableEnrollId="true";
	    		}
	    	});
	    	
	    	if(disableEnrollId=="true")
	    	{
	    		$("#enrollmentId").attr("disabled", "disabled");
	    	}
	    	else
	    	{
	    		$("#enrollmentId").removeAttr("disabled");
	    	}
	    }

	function resetPaySearch()
	{
		$("#error-massage3").hide();
		
		$("#gridDiv1").hide();
		$("#gridDiv2").hide();
		$("#blankone").hide();
		$("#enrollmentId").val("");
		$("#disciplineId").val("");
		$("#paymentStatusId").val("");
		
		$("#labelId").val("");
		$("#paymentMode").val("");
		$("#datepicker3").val("");
		$("#datepicker4").val("");
		$("#enrollmentId").removeAttr("disabled");
		$("#labelId").removeAttr("disabled");
		$("#paymentMode").removeAttr("disabled");
		$("#datepicker3").removeAttr("disabled");
		$("#datepicker4").removeAttr("disabled");
	}
	
	function disableDropDowns()
	{
		var enrlmntId=$("#enrollmentId").val();
		
		if(enrlmntId == "")
		{
			$("#labelId").removeAttr("disabled");
			$("#paymentMode").removeAttr("disabled");
			$("#datepicker3").removeAttr("disabled");
			$("#datepicker4").removeAttr("disabled");
		}
		else
		{
			$("#labelId").attr("disabled", "disabled");
			$("#paymentMode").attr("disabled", "disabled"); 
			$("#datepicker3").attr("disabled", "disabled"); 
			$("#datepicker4").attr("disabled", "disabled");
		}
	}
	
$(document).ready(function() { 
    	$("a:contains('Payment Approval')").html('<span class="fadeSubmenu">Payment Approval</span>')}); 

$(function(){
	 
	// add multiple select / deselect functionality
    $("#checkMe").click(function () {
    if ($("#checkMe").attr('checked')){
    	$(".checkBoxClass").each(function(currIndex, currElement) {
			if ($(currElement).attr('disabled')){
	    	}else{
	         	 $(currElement).attr('checked', 'checked');
	    	}
		});
    }else{
    	$(".checkBoxClass").each(function(currIndex, currElement) {
	         	 $(currElement).removeAttr('checked');
		});
    }
    
    
    	
    });
 
    // if all checkbox are selected, check the selectall checkbox
    // and viceversa
    $(".checkBoxClass").click(function(){
 		 var flag = true;
		$(".checkBoxClass").each(function(index, value) {
		if (flag){
			 if ($(this).attr('checked')){
	 		  	$('#checkMe').attr('checked','checked');
	 		  }else{
	 		  	$('#checkMe').removeAttr('checked');
	 		  	flag = false;
	 		  }
			}
		});
 
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
		message = message + "Please Select Atleast One Checkbox for Payment Approval";
		var ulID = "error-ul3";
		var divID = "error-massage3";
		createErrorList(message, ulID, divID); 
		return false;
	}
	return true;
}

function validateForRejection() {
	
	var selected="false";	

	var selected2="true";
	
	var message = "";

	$(document).find(".checkBoxClass").each(function(index, curr){
		var currVal=$(curr).val();
		var id= currVal.split(',')[0];
		var remark=$('#'+id).val();
		if(($(curr).is(":checked")+"")=="true")
		{
			remark = $.trim(remark);
			if($('#'+id).val() == '' ||  $('#'+id).val()== null)
			{
				selected2="false";
			}
			selected="true";
		}
	});

	if(selected=="false")
	{
		message = message + "Please Select Atleast One Checkbox for Payment Rejection";
	}

	if(selected2=="false")
	{
		message = message + "Please provide remark for Payment Rejection";
	}
	
	if(message != ""){
		var ulID = "error-ul3";
		var divID = "error-massage3";
		createErrorList(message, ulID, divID); 
		return false;
	}
	else
		
		return true;
}

function validatePaymentSearchDetails(){

	var enrlmntId=$("#enrollmentId").val();
	
	var labelId=$("#labelId").val();
	
	var paymentMode=$("#paymentMode").val();
	var datepicker3=$("#datepicker3").val();
	var datepicker4=$("#datepicker4").val();
	
	var message = "";

	if(paymentMode=="" && enrlmntId == ""){
		message = message + "Please select Mode of Payment"+"##";
	}
	
	if(datepicker3=="" && datepicker4 !=""){
		message = message + "Please select from date"+"##";
	}
	
	if(datepicker3!="" && datepicker4 == ""){
		message = message + "Please select to date "+"##";
	}
	
	
	else
	{
		$("#error-massage3").hide();
	}

	if(message != ""){
		var ulID = "error-ul3";
		var divID = "error-massage3";
		createErrorList(message, ulID, divID); 
		return false;
	}
	else
		return true;
}

function gotoUserPaymentDetails(param){
	window.open("CandidateMgmtAction_getPaymentDetailsForRegisteredID.action?enrollmentId="+param);
}

</script>
<style>
.AddressArea {width:150px;}
.table_2 { font-size:12px; }
</style>


<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="main-body">
<div class="fade" id="pop3"></div>

<!-- Change Password Start -->




<div id="SelectTest">

<h1 class="pageTitle" title="Payment Approval"><s:text name="agencypaymentapproval.paymentapproval"/></h1>
<div class="hr-underline2"></div>
<div id="error-massage3" style="display:none" class="error-massage">
      		<div class="error-massage-text">
      		<ul style="margin:0; margin-left:20px; padding:0;" id="error-ul3">
      	
      		</ul>      	
      	 </div>
</div>

<!-- Box Container Start -->

<div class="">

<!-- Payment Fields Start -->
<div class="clear">
<s:form method="post" action="CandidateMgmtAction" name="paymentApproval" id="paymentApproval">
<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
<s:token></s:token>

<div>
<div class="field-label"><s:text name="agencypaymentapproval.enrollmentID"/>&nbsp;</div>
	<div>
			<s:textfield name="enrollmentId" id="enrollmentId" maxlength="15" cssClass="inputField " onblur="disableDropDowns();"></s:textfield>
	</div>
</div>

<div class="height10"></div>
<div class="WeightBold"><s:text name="agencypaymentapproval.or"/></div>
<div class="height10"></div>

<!-- Row One Start -->
<div class="clear">
<div class="fl-left width32">
<div class="field-label">Course&nbsp;</div>
<div>
<s:select label="Discipline" name="disciplineId"  cssClass="disableEnroll" 
		headerKey="" headerValue="All" list="disciplineList" id="disciplineId" listValue="labelValue" listKey="labelId" value="%{disciplineId}" onchange="disableEnrollmentId();"/>
</div>
<div id="fnameID" class="errorMessage"></div>
</div>

<div class="fl-left width32">
<div class="field-label"><s:text name="agencypaymentapproval.modeofpayment"/>&nbsp;</div>
<div>
	<s:select name="paymentMode"  id="paymentMode" 
		cssClass="disableEnroll"
		headerKey="" headerValue="Select Payment Mode "  list="paymentModeList"  onchange="disableEnrollmentId();"/>
		
</div>
<div id="fnameID" class="errorMessage"></div>
</div>



<div class="fl-left width32">
<div class="field-label">Status &nbsp;</div>
<div>
	 <s:select list = "paymentStatusMap" name = "paymentStatusValue" label = "Name" 
			headerKey="" headerValue = "Select Status" id = "paymentStatusId" value="%{paymentStatusValue}"/>
</div>
<div id="lnameID" class="errorMessage"></div>
</div>
</div>

<div class="height10 clear"></div>

<!-- for validation message -->
 

<!-- Row Three Start -->
<div class="clear">
<div>
<div class="fl-left width32">
<div class="field-label"><s:text name="agencypaymentapproval.paymentfromdate"/>&nbsp;</div>
<div>
	<s:textfield name="fromDate" id="datepicker3" disabled="disabled" readonly="true" cssClass="inputDate disableEnroll" onblur="disableEnrollmentId();"></s:textfield>
</div>
<div id="fnameID" class="errorMessage"></div>
</div>


<div class="fl-left width32">
<div class="field-label"><s:text name="agencypaymentapproval.paymenttodate"/>&nbsp;</div>
<div>
	<s:textfield name="toDate" id="datepicker4" disabled="disabled" readonly="true" cssClass="inputDate disableEnroll" onblur="disableEnrollmentId();"></s:textfield>
</div>
<div id="lnameID" class="errorMessage"></div>
</div>

<div class="fl-rigt width32"></div>
</div>
</div>
<!-- Row Three End -->
  
<!-- Row Four Start -->
<div class="clear">



<div class="fl-left fifty">
<div><s:submit type="submit" key="agencypaymentapproval.search" title="Search"  cssClass="submitBtn button-gradient" id="paymentApprovalSubmit" method="getDDDetailsForPaymentApproval" onclick="return validatePaymentSearchDetails();"></s:submit>&nbsp;&nbsp;
<input type="button" value="Clear" title="<s:text name="agencypaymentapproval.clear"/>" class="submitBtn button-gradient" onclick="resetPaySearch();"/>&nbsp;&nbsp;
<!--<s:submit type="submit" key="Refresh" title="Refresh"  cssClass="submitBtn button-gradient" id="paymentApprovalSubmit" method="getDDDetailsForPaymentApproval" onclick="return validatePaymentSearchDetails();"></s:submit>
--></div>
</div>
<div class="fl-rigt fifty">&nbsp;</div>
</div>
<!-- Row Four End -->
<div class="height20"></div>

</s:form>
</div>
<!-- Payment Fields End -->

</div>


<!-- DD Start -->
<br/>
<div class="clear hr-dashline"></div>
<!-- DD Start -->
<s:if test='%{paymentMode=="DD"}'>
<!-- Dash Line Start -->
<div class="clear hr-dashline"></div>
<!-- Dash Line End -->
  
<div class="AgencyPayDrid" id="gridDiv1">
<s:form method="post" action="CandidateMgmtAction_getDDDetailsForPaymentApproval.action" name="ddPaymentForm">
<s:hidden name="paymentMode" value="DD"></s:hidden>
<s:hidden name="fromDate" value="%{fromDate}"></s:hidden>
<s:hidden name="toDate" value="%{toDate}"></s:hidden>
<s:hidden name="paymentStatusValue" value="%{paymentStatusValue}"></s:hidden>

<!-- DD Payment Start -->
<div id="option1">
<s:token/>
<table width="100%">
	<tr class="box-header">
		<td align="left" width="30%" >DD Payment</td>
		<td align="center" width="30%" class="pagination-label">Total <font color="red"><s:property value="totalCandidateCount"/></font> Candidates Found </td>
		<td width="40%">
			<s:if test='%{totalCandidateCount=="0"}'>
			</s:if><s:else>
				<%@ include file="../paginationForPayment.jsp"%>
			</s:else>	
		</td>
	</tr>
</table>
<table cellspacing="0" border="1" width="100%" class="table_2" bordercolor="#CCC">
<thead>
    <tr>
    <th align="Center" style="width: 20px;">
    <s:if test='%{validatedStatus=="A"}'><input type="checkbox"  id = "checkMe" align="left" style="text-align: center;" value="0.000" disabled="disabled"></s:if>
    	<s:else><input type="checkbox"  id = "checkMe" align="left" style="text-align: center;" value="0.000"></s:else>
    </th>
    
    <th style="text-align: center;">Registration ID</th>
    <th style="text-align: center;">Course</th>
    <th style="text-align: left;">Candidate Name</th>
    <th style="text-align: center;">Mobile Number</th>
    <th style="text-align: center;">DD Number</th>
    <th style="text-align: center;">DD Date</th>
    <th style="text-align: left;">Bank Name</th>
    <th style="text-align: left;">City</th>
    <th style="text-align: center;">Applicable Fee</th>
    <th style="text-align: center;">Amount</th>
    <th style="text-align: left;">Remark</th>
    <th style="text-align: left; ">Status</th>
    </tr></thead>
    <tbody>
<s:if test="%{ddPaymentApprovalList!=null}"> 

<s:iterator value="ddPaymentApprovalList" var="bean" status="status">

<tr>
	<td align="center" class="wordWrap">
	<s:if test='%{validatedStatus=="A"}'>
		<s:checkbox name="ddPaymentApprovalList[%{#status.index}].paymentCheckedValue" fieldValue="%{paymentPK},%{userPK}" cssClass="checkBoxClass" disabled="true"></s:checkbox>
	</s:if>
	<s:else>
		<s:checkbox name="ddPaymentApprovalList[%{#status.index}].paymentCheckedValue" fieldValue="%{paymentPK},%{userPK}" cssClass="checkBoxClass"></s:checkbox>
	</s:else>
	
			
		
	</td>
	<td align="center" class="wordWrap">
		
		<s:if test='%{validatedStatus=="A"}'>
			<s:property value="enrollment_pk"/>
		</s:if>
		<s:else>
			<a href="#" onclick="gotoUserPaymentDetails('<s:property value="enrollment_pk"/>')"><s:property value="enrollment_pk"/></a>
		</s:else> 
	</td>
	<td class="wordWrap">
		
			<s:property value="testName"/>
	</td>
	<td class="wordWrap">
		
			<s:property value="firstName"/>
	</td>
	<td class="wordWrap">
		
			<s:property value="contactNumber"/>
	</td>
	<td align="right" class="wordWrap">
		
			<s:property value="ddChalanReciptNO"/>
			
	</td>
	<td class="wordWrap">
		
			<s:property value="ddDate"/>
	</td>
	<td class="wordWrap">
		
			<s:property value="bankName"/>
			
	</td>
	<td class="wordWrap">
		
			<s:property value="bankCity"/>
			
	</td>
	<td align="right" class="wordWrap">
		
			<s:property value="applicableFee"/>
			
	</td>
	<td align="right" class="wordWrap">
		
			<s:property value="amount"/>
			
	</td>
	
	<td>
	<s:if test='%{validatedStatus=="A"}'>
		<s:textarea name="ddPaymentApprovalList[%{#status.index}].remark" value="%{remark}" id='%{paymentPK}' cssClass="AddressArea" cssClass="AddressArea validate[required] maxSize[100]" onkeypress="return imposeMaxLength(event,this,100);" onchange="return imposeMaxLength(event,this,100);" title="Max length is 100 chars" disabled="true"></s:textarea>
	</s:if><s:else>
		<s:textarea name="ddPaymentApprovalList[%{#status.index}].remark" value="%{remark}" id='%{paymentPK}' cssClass="AddressArea" cssClass="AddressArea validate[required] maxSize[100]" onkeypress="return imposeMaxLength(event,this,100);" onchange="return imposeMaxLength(event,this,100);" title="Max length is 100 chars"></s:textarea>
	</s:else>
	</td>
	<td><s:property value="validatedStatus"/></td>
</tr>
</s:iterator>
</s:if> 
<s:else>
	<tr>
		<!-- /**START ISSUE ID = 67256, Fixed By: Amitp DATE = 13-Feb-2012 **/  -->
		<td colspan="13" align="center">
			<div>
			<b>	No record Found </b>
			</div>
		</td>
</tr>
</s:else> 
</tbody>
</table>
<s:if test="%{ddPaymentApprovalList!=null}"> 
<!-- Button Start -->
<div class="height20"></div>
<div class="clear">
<s:submit title="Approve Payment" cssClass="submitBtn button-gradient" value="Approve Payment" method="ddPaymentApproval" onclick="return validateForApproval();"></s:submit>
&nbsp; &nbsp;
<s:submit title="Reject Payment" cssClass="submitBtn button-gradient" value="Reject Payment" method="ddPaymentRejection" onclick="return validateForRejection();"></s:submit>
</div>
<!-- Button End -->
</s:if>
</div>
<!-- DD Payment End -->
</s:form>
</div>
</s:if>
<!-- DD End -->

<!-- Challan Start -->
<s:if test='%{paymentMode=="Challan"}'> 
<div class="AgencyPayDrid" id="gridDiv2">
<s:form method="post" action="CandidateMgmtAction_getDDDetailsForPaymentApproval.action" name="paginationForm">
<s:hidden name="paymentMode" value="Challan"></s:hidden>
<s:hidden name="enrollmentId" value="%{enrollmentId}"></s:hidden>
<s:hidden name="fromDate" value="%{fromDate}"></s:hidden>
<s:hidden name="toDate" value="%{toDate}"></s:hidden>
<s:hidden name="paymentStatusValue" value="%{paymentStatusValue}"></s:hidden>
<s:token/>
<!-- Challan Payment Start -->
<table width="100%">
	<tr class="box-header">
		<td align="left" width="30%" >Challan Payment</td>
		<td align="center" width="30%" class="pagination-label">Total <font color="red"><s:property value="totalCandidateCount"/></font> Candidates Found </td>
		<td width="40%">
			<s:if test='%{totalCandidateCount=="0"}'>
			</s:if><s:else><%@ include file="../pagination.jsp" %></s:else>
		</td>
	</tr>
</table>
<table cellspacing="0" border="1" width="100%" class="table_2" bordercolor="#CCC">
<thead>
    <tr>
    <th align="Center" style="width:2%;">
    	<s:if test='%{validatedStatus=="A"}'>
    		<input type="checkbox"  id = "checkMe" align="left" style="text-align: center;" value="0.000" disabled="disabled">
    	</s:if>
    	<s:else>
     		<input type="checkbox"  id = "checkMe" align="left" style="text-align: center;" value="0.000">
    	</s:else>
    </th>
    <th style="text-align: center; width:10%;">Registration ID</th>
    <th style="text-align: left; width:16%;">Discipline</th>
    <th style="text-align: left; width:14%;">Candidate Name</th>
    <th style="text-align: left; width:8%;">Mobile Number</th>
    <th style="text-align: left; width:4%;">Journal Number</th>
    <th style="text-align: left; width:9%;">Challan Date</th>
    <th style="text-align: left; width:7%;">Branch Name</th>
    <th style="text-align: left; width:6%;">Branch Code</th>
    <th style="text-align: left; width:5%;">Applicable Fee</th>
    <th style="text-align: left; width:5%;">Amount</th>
    <th style="text-align: left; width:6%;">Remark</th>
    <th style="text-align: left; width:3%;">Status</th>
    </tr>
</thead>

<s:if test="%{chalanPaymentApprovalList!=null}"> 

<s:iterator value="chalanPaymentApprovalList" var="bean" status="status">

<tr>
	<td align="center" class="wordWrap">
		<%--<s:checkbox name="paymentCheckedValue" fieldValue="%{paymentPK},%{userPK}" cssClass="checkBoxClass" />
		--%>
		<s:if test='%{validatedStatus=="A"}'>
			<s:checkbox name="chalanPaymentApprovalList[%{#status.index}].paymentCheckedValue" fieldValue="%{paymentPK},%{userPK}" cssClass="checkBoxClass" disabled="true"></s:checkbox>
		</s:if>
		<s:else>
			<s:checkbox name="chalanPaymentApprovalList[%{#status.index}].paymentCheckedValue" fieldValue="%{paymentPK},%{userPK}" cssClass="checkBoxClass"></s:checkbox>
		</s:else>
	</td>
	<td class="wordWrap">
	<s:if test='%{validatedStatus=="A"}'>
			<s:property value="enrollment_pk"/>
		</s:if>
		<s:else>
<!--			<a href="#" onclick="gotoUserPaymentDetails('<s:property value="enrollment_pk"/>')"><s:property value="enrollment_pk"/></a>-->
			<s:property value="enrollment_pk"/>
		</s:else>
	</td>
	<td class="wordWrap"><s:property value="testName"/></td>
	<td class="wordWrap"><s:property value="firstName"/></td>
	<td class="wordWrap"><s:property value="contactNumber"/></td>
	<td class="wordWrap"><s:property value="ddChalanReciptNO"/></td>
	<td class="wordWrap"><s:property value="ddDate"/></td>
	<td class="wordWrap"><s:property value="branchName"/></td>
	<td class="wordWrap"><s:property value="branchCode"/></td>
	
	<td class="wordWrap" align="right"> <s:property value="applicableFee"/></td> 
	<td class="wordWrap" align="right"><s:property value="amount"/></td>
	
	<td class="wordWrap">
	<%--<s:property value="remark"/>--%>
	<%--<s:textfield name="remark" id ="remark"></s:textfield>
	<s:textarea name="chalanPaymentApprovalList[%{#status.index}].remark" value="%{remark}" cssClass="inputField"></s:textarea>--%>
	<s:if test='%{validatedStatus=="A"}'>
		<s:textarea name="chalanPaymentApprovalList[%{#status.index}].remark" value="%{remark}" id='%{paymentPK}' cssClass="AddressArea" cssClass="AddressArea validate[required] maxSize[100]" onkeypress="return imposeMaxLength(event,this,100);" onchange="return imposeMaxLength(event,this,100);" title="Max length is 100 chars" disabled="true"></s:textarea>
	</s:if>
	<s:else>
		<s:textarea name="chalanPaymentApprovalList[%{#status.index}].remark" value="%{remark}" id='%{paymentPK}' cssClass="AddressArea" cssClass="AddressArea validate[required] maxSize[100]" onkeypress="return imposeMaxLength(event,this,100);" onchange="return imposeMaxLength(event,this,100);" title="Max length is 100 chars" ></s:textarea>
	</s:else>
	</td>
	<td align="center"><s:property value="validatedStatus"/></td>
</tr>
</s:iterator>
</s:if> 
<s:else>
	<tr>
		<td colspan="13" align="center">
			
				<b> No record Found</b>
			
		</td>
</tr>
</s:else> 
</table>
<s:if test="%{chalanPaymentApprovalList!=null}"> 
<!-- Button Start -->
<div class="height20"></div>
<div class="clear">
<s:submit title="Approve Payment" cssClass="submitBtn button-gradient" value="Approve Payment" method="ddPaymentApproval" onclick="return validateForApproval();"></s:submit>
&nbsp; &nbsp;
<s:submit title="Reject Payment" cssClass="submitBtn button-gradient" value="Reject Payment" method="ddPaymentRejection" onclick="return validateForRejection();"></s:submit>
</div>
</s:if>
<!-- Button End -->

<!-- Challan Payment End -->
</s:form>
</div>
</s:if>


<!-- EPost Start -->
<s:if test='%{paymentMode=="e-Post"}'> 
<div class="AgencyPayDrid" id="gridDiv2">
<s:form method="post" action="CandidateMgmtAction_getDDDetailsForPaymentApproval.action" name="paginationForm">
<s:hidden name="paymentMode" value="e-Post"></s:hidden>
<s:hidden name="enrollmentId" value="%{enrollmentId}"></s:hidden>
<s:hidden name="fromDate" value="%{fromDate}"></s:hidden>
<s:hidden name="toDate" value="%{toDate}"></s:hidden>
<s:hidden name="paymentStatusValue" value="%{paymentStatusValue}"></s:hidden>
<s:token/>
<!-- Challan Payment Start -->
<table width="100%">
	<tr class="box-header">
		<td align="left" width="30%" >e-Post Payment</td>
		<td align="center" width="30%" class="pagination-label">Total <font color="red"><s:property value="totalCandidateCount"/></font> Candidates Found </td>
		<td width="40%">
			<s:if test='%{totalCandidateCount=="0"}'>
			</s:if><s:else><%@ include file="../pagination.jsp" %></s:else>
		</td>
	</tr>
</table>
<table cellspacing="0" border="1" width="100%" class="table_2" bordercolor="#CCC">
<thead>
    <tr>
    <th align="Center" style="width: 20px;">
    	<s:if test='%{validatedStatus=="A"}'>
    		<input type="checkbox"  id = "checkMe" align="left" style="text-align: center;" value="0.000" disabled="disabled">
    	</s:if>
    	<s:else>
     		<input type="checkbox"  id = "checkMe" align="left" style="text-align: center;" value="0.000">
    	</s:else>
    </th>
    <th style="text-align: center;">Registration ID</th>
    <th style="text-align: left; ">Discipline</th>
    <th style="text-align: center; ">Candidate Name</th>
    <th style="text-align: center; ">Mobile Number</th>
    <th style="text-align: left; ">Reference Number</th>
    <th style="text-align: left; ">e-Post Payment Date</th>
    <th style="text-align: left; ">Post Office Branch Name</th>
    <th style="text-align: left; ">Branch Code</th>
    <th style="text-align: left; ">Applicable Fee</th>
    <th style="text-align: left; ">Amount</th>
    <th style="text-align: left; ">Remark</th>
    <th style="text-align: left; ">Status</th>
    </tr>
</thead>

<s:if test="%{chalanPaymentApprovalList!=null}"> 

<s:iterator value="chalanPaymentApprovalList" var="bean" status="status">

<tr>
	<td align="center" class="wordWrap">
		<%--<s:checkbox name="paymentCheckedValue" fieldValue="%{paymentPK},%{userPK}" cssClass="checkBoxClass" />
		--%>
		<s:if test='%{validatedStatus=="A"}'>
			<s:checkbox name="chalanPaymentApprovalList[%{#status.index}].paymentCheckedValue" fieldValue="%{paymentPK},%{userPK}" cssClass="checkBoxClass" disabled="true"></s:checkbox>
		</s:if>
		<s:else>
			<s:checkbox name="chalanPaymentApprovalList[%{#status.index}].paymentCheckedValue" fieldValue="%{paymentPK},%{userPK}" cssClass="checkBoxClass"></s:checkbox>
		</s:else>
	</td>
	<td class="wordWrap">
	<s:if test='%{validatedStatus=="A"}'>
			<s:property value="enrollment_pk"/>
		</s:if>
		<s:else>
<!--			<a href="#" onclick="gotoUserPaymentDetails('<s:property value="enrollment_pk"/>')"><s:property value="enrollment_pk"/></a>-->
			<s:property value="enrollment_pk"/>
		</s:else>
	</td>
	<td class="wordWrap"><s:property value="testName"/></td>
	<td class="wordWrap"><s:property value="firstName"/></td>
	<td class="wordWrap"><s:property value="contactNumber"/></td>
	<td class="wordWrap"><s:property value="ddChalanReciptNO"/></td>
	<td class="wordWrap"><s:property value="ddDate"/></td>
	<td class="wordWrap"><s:property value="branchName"/></td>
	<td class="wordWrap"><s:property value="branchCode"/></td>
	
	<td class="wordWrap"><s:property value="applicableFee"/></td> 
	<td class="wordWrap"><s:property value="amount"/></td>
	
	<td class="wordWrap">
	<%--<s:property value="remark"/>--%>
	<%--<s:textfield name="remark" id ="remark"></s:textfield>
	<s:textarea name="chalanPaymentApprovalList[%{#status.index}].remark" value="%{remark}" cssClass="inputField"></s:textarea>--%>
	<s:if test='%{validatedStatus=="A"}'>
		<s:textarea name="chalanPaymentApprovalList[%{#status.index}].remark" value="%{remark}" id='%{paymentPK}' cssClass="AddressArea" cssClass="AddressArea validate[required] maxSize[100]" onkeypress="return imposeMaxLength(event,this,100);" onchange="return imposeMaxLength(event,this,100);" title="Max length is 100 chars" disabled="true"></s:textarea>
	</s:if>
	<s:else>
		<s:textarea name="chalanPaymentApprovalList[%{#status.index}].remark" value="%{remark}" id='%{paymentPK}' cssClass="AddressArea" cssClass="AddressArea validate[required] maxSize[100]" onkeypress="return imposeMaxLength(event,this,100);" onchange="return imposeMaxLength(event,this,100);" title="Max length is 100 chars" ></s:textarea>
	</s:else>
	</td>
	<td><s:property value="validatedStatus"/></td>
</tr>
</s:iterator>
</s:if> 
<s:else>
	<tr>
		<td colspan="13" align="center">
			
				<b> No record Found</b>
			
		</td>
</tr>
</s:else> 
</table>
<s:if test="%{chalanPaymentApprovalList!=null}"> 
<!-- Button Start -->
<div class="height20"></div>
<div class="clear">
<s:submit title="Approve Payment" cssClass="submitBtn button-gradient" value="Approve Payment" method="ddPaymentApproval" onclick="return validateForApproval();"></s:submit>
&nbsp; &nbsp;
<s:submit title="Reject Payment" cssClass="submitBtn button-gradient" value="Reject Payment" method="ddPaymentRejection" onclick="return validateForRejection();"></s:submit>
</div>
</s:if>
<!-- Button End -->

<!-- Challan Payment End -->
</s:form>
</div>
</s:if>

<s:if test='%{paymentMode=="Blank"}'> 
<div id="blankone">

<table cellspacing="0" border="1" width="100%" class="table_2" bordercolor="#CCC">

<thead>
    <tr>
    <th align="Center" style="width:2%;">
    	<input type="checkbox"  id = "checkMe" align="left" style="text-align: center;" value="0.000">
    </th>
    <th style="text-align: center; width:14%;">Registration ID</th>
    <th style="text-align: left; width:14%; ">Discipline</th>
    <th style="text-align: center; width:18%; ">Candidate Name</th>
    <th style="text-align: center; width:12%;">Mobile Number</th>
    <th style="text-align: left; width:5%; ">Journal Number</th>
    <th style="text-align: left; width:5%;">Challan Date</th>
    <th style="text-align: left; width:5%;">Branch Name</th>
    <th style="text-align: left; width:5%;">Branch Code</th>
    <th style="text-align: left; width:5%;">Applicable Fee</th>
    <th style="text-align: left; width:5%;">Amount</th>
    <th style="text-align: left; width:5%;">Remark</th>
    <th style="text-align: left; width:5%;">Status</th>
    </tr>
</thead>
	<tr>
		<td colspan="13" align="center">
			
				<b>No record Found</b>
			
		</td>
</tr>
</table>
</div>
</s:if>
</div>

<!-- Challan End -->

<!-- Box Container End -->


</div>
