<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<script src="js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/AES.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
		$(".menuActive").closest(".submenu-item").addClass("submenu-itemActive");
	});
      $(function () {
          $('#Table1, #Table2').Scrollable({
              ScrollHeight: 250,
          });
      });
  </script>

<script type="text/javascript">

function validateKey(key) {
    //getting key code of pressed key
    var keycode = (key.which) ? key.which : key.keyCode;
    //comparing pressed keycodes
    if (!(keycode == 8 || keycode == 45)&& (keycode < 65 || keycode > 90) && (keycode < 48 || keycode > 57)) 
    {
        return false;
    }
    else {
		var parts = key.srcElement.value.split('-');
        if (parts.length > 1 && keycode == 45)
            return false;
        return true;
    }
}

	
	function changeAction() {
		$("#TechProcessJob").attr('action', "TechProcessJobsAction_typeOCallToTechProcess.action");
		$("#TechProcessJob").submit();
		$("#divTable").hide();
	}
	
	function clearTransValue() {
		$("#error-massage_user").hide();
		$("#error-ul_user").hide();
		$("#transactionNumber").val("");
		
		$("#error-massage3").hide();
		$("#error-ul3").val("");
	}
	

 function validateApplicantDetails(){
    var message = "";
	var transactionNumber = $("#transactionNumber").val();
			
	if(transactionNumber== null || transactionNumber=="" || (transactionNumber!= null && transactionNumber.trim()=="" )){
		message=message+"Please enter Transaction Number"+"##";
	} 
	if(message != "")
	{
		var ulID = "error-ul3";
		var divID = "error-massage3";
		createErrorList(message, ulID, divID); 

		return false;
		
	} else{
		$("#TechProcessJobSingle").attr('action', "TechProcessJobsAction_typeOCallToTechProcessSingle.action");
		$("#TechProcessJobSingle").submit();
	}
					
} 

</script>
<style>
.onetime{
  	display: none;
  }
.error-massage {  outline:none;
}
  .tabDiv {width: 94.1% !important;}
  </style>
  
<s:if test="%{#attr.dataNotFound!=''}">
	<div class="error-massage-text">
		<s:property value="#attr.dataNotFound" />
	</div>
</s:if>
<div class="container"> 
<div id="dashboard"  class="common_dashboard tabDiv effect2"><div class="accordions"><div id="CandidateDiv"><div class="accordion">
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="main-body">
	<div class="fade" id="pop3"></div>

	<div id="SelectTest">

		<h1 class="pageTitle" title="Type O Call to Tech Process">
			Type 'O' Bulk Call to Tech Process
		</h1>
		
		<div id="error-massage3" style="display:none"  class="error-massage" tabindex="-1">
      		<div class="error-massage-text"  style="border: none;">
      		<ul style="margin:0; margin-left:20px; padding:0;" id="error-ul3">
      	
      		</ul>      	
      	 	</div>
		</div>

		<div class="clear">
			<s:form action="TechProcessJobsAction" id="TechProcessJob">
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
				<div class="row"><div class="col-sm-12">
				<input type="button" value="Generate offline call to TP " title="Generate offline call to TP "
				class="ripple1 submitBtn button-gradient btn-warning"
				onclick="changeAction();" /></div></div>
				<s:token />
			</s:form>
		</div>
	</div>
	<br />	<br />
	 <div id="SelectTest">

		<h1 class="pageTitle" title="Type O Call to Tech Process">
			Type 'O' Single Call to Tech Process
		</h1>
		<div class="clear">
			<s:form action="TechProcessJobsAction" id="TechProcessJobSingle">
			<s:token></s:token>
			<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
				<div id="message" style="border: none">
					<s:if test='%{showModuleDetails == "true"}'>
						<span style="color:green"><strong><s:property value="sucMsg"/></strong></span>
					</s:if>
				</div>
   				<s:if test="errMsgSingle!=null">
	 				<div id="error-massage_user" class="error-massage">
			      		<div class="error-massage-text">
			      			<ul style="margin:0; padding:0;" id="error-ul_user">
			      			<s:property value="errMsgSingle" escape="false"/>
			      			</ul>
			      	 	</div>
	      			</div>
				</s:if> 
				<div class="row">
					<div class="col-sm-4">
						<div class="form-group">
		<label class="control-label">Transaction ID</label>
		 <s:textfield name="transactionNumber" id="transactionNumber" size="20" maxlength="15" onpaste="return false;" cssClass="form-control" onkeypress="return validateKey(event);"></s:textfield>
	</div>
					</div>
				</div>
				<div class="row">
	<div class="col-sm-2 mt10">
	<input type="button" value="validate" title="checking the candidate details befor transaction" class="ripple1 btn btn-warning btn-block btn-warning" onclick="validateApplicantDetails();" />
	</div>
	<div class="col-sm-2  mt10">
	<input type="button" value="Clear" title="Clear" class="btn btn-default btn-block" onclick="clearTransValue()"/>
	</div>
</div>					
			  </s:form>
		</div>
	</div>
	<br/>
	<s:if test='responseOFlag=="true"'>
		<div id="SelectTest">
			<br />
			<table cellspacing="0" border="1" width="100%"  class="table table-striped table-bordered personsl-dtl">
			<thead>
				<tr>
					<th style="width: 4%" align="left">
						Status Code
					</th>
					<th style="width: 20%" align="left">
						Txn Message
					</th>
					<th style="width: 25%" align="left">
						Message Description
					</th>
					<th style="width:10%" align="left">
						Client Txn Number
					</th>
					<th style="width:10%" align="left">
						TPSL Txn Number
					</th>
					<th style="width:10%" align="left">
						Amount
					</th>
					<th style="width: 10%" align="left">
						Txn Date and Time
					</th>
				</tr>
			</thead>
			<tbody>
			<s:iterator value="responseDetailsList" status="stat" var="currentObject">
				<tr>
					<td align="left">
						<s:property value="authStatus" />
					</td>
					<td align="left">
						<s:property value="transactionMessage" />
					</td>
					<td align="left">
						<s:property value="errorDescription" />
					</td>
					<td align="left">
						<s:property value="customerId" />
					</td>
					<td align="left">
						<s:property value="txnReferenceNo" />
					</td>
					<td align="left">
						<s:property value="txnAmount" />
					</td>
					<td align="left">
						<s:property value="txnDate" />
					</td>
				</tr>
			</s:iterator>
			</tbody>
			</table>
		</div>
	</s:if>
</div>

</div>
</div></div></div></div>
