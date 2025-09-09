<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script>
	function callMethod(){
	var ulID = "error-ulAppForm";
	var divID = "error-massageAppForm";
	var d = $("#disciplineType").val();
	var d2 = $("#testMasterValue").val();
	var subject = $("#subject").val();
	var mailContent = $("#mailContent").val();
	var smsContent = $("#smsContent").val();
	
	
	var testFromDate = $("#testFromDate").val();
	var testToDate = $("#testToDate").val();
	
	
	
	
	
	var message = "";
	
	if (d == 'null'||d=="null" || d == null){
		message = message+"Please select atleast one Discipline."+"##";
	}
	if (d2 == null ||d2=="null" || d2 == 'null'){
		message = message+"Please select atleast one Test Center ."+"##";
	}
	if (subject == "" || subject == '' ){
		message = message+"Please enter Subject."+"##";
	}
	if (mailContent == "" || mailContent == '' ){
			message = message+"Please enter Mail content."+"##";
	}
	
		var fromDatWithSplit = testFromDate.split("-");		
		var frmDate =  new Date(fromDatWithSplit[2],getMonthNoByMonthName(fromDatWithSplit[1]),fromDatWithSplit[0]);



		var toDatWithSplit = testToDate.split("-");		
		var toDate =  new Date(toDatWithSplit[2],getMonthNoByMonthName(toDatWithSplit[1]),toDatWithSplit[0]);
		
		if(frmDate.getTime() > toDate.getTime()){
			message = message + " From Date cannot be greater than To Date."+"##";
	}
			
			
	if (Date.parse(testFromDate) > Date.parse(testToDate)) {
	      message = message+"Test From Date cannot be greater than Test To Date";
     }
	
	
	
	
			if(message != ""){
					createErrorList(message, ulID, divID); 
					$("#error-massageAppForm").focus();
					$('html, body').animate({ scrollTop: 0 }, 0);
					//$('html, body').animate({ scrollTop: 0 }, 'slow');
					return false;
			}else{
			return true;
			}
			
	
	}
	function imposeMaxLength(Event, Object, MaxLen)
	{
	    return (Object.value.length <= MaxLen)||(Event.keyCode == 8 ||Event.keyCode==46||(Event.keyCode>=35&&Event.keyCode<=40))
	}
</script>
<div class="container">
<div id="dashboard">
<div style="text-align:left; clear:both;"><h1 class="pageTitle" title="Mail / SMS">Mail / SMS</h1></div>
<div class="hr-underline2"></div>

  <s:form action="AgencyAction">
  <div id="error-massageAppForm" style="display:none" class="error-massage">
      	<div class="error-massage-text">
      	<ul style="margin:0; margin-left:20px; padding:0;" id="error-ulAppForm">
      	
      	</ul>
      	 </div>
      </div>
  <table cellpadding="5">
		  <tr>
			      <td valign="top"> 
			      <div class="field-label"><strong>Discipline</strong></div>
			      <s:select list = "disciplineList" name = "disciplineType" label = "Name" multiple="true" required="true"
								headerKey="" id = "disciplineType" value="%{disciplineType}" style="width:100px;" /></td>
			      <td>
				      <div class="field-label"><strong>Test Center</strong></div>
				      <s:select list = "testCenterMasterDetails" name = "testMasterValue" label = "Name"  multiple="true" required="true"
								headerKey=""  id = "testMasterValue" value="%{testMasterValue}" style="width:420px;" />  
				  </td>
				  </tr>
		  </tr>
		  
		   
		  <tr><td></td>
      <td>
      <table>
      <tr>
      <td width="214"><div class="field-label"><strong>Test From Date</strong></div>
       <div><s:select list = "testDateListForAdminList" name="startDate" label = "Name"   
						 id="testFromDate"  value="%{startDate}" style="width:200px;" /></td>
      <td><div class="field-label"><strong>Test To Date</strong></div>
						 <div><s:select list = "testDateListForAdminList" name="endDate" label = "Name"    
						 id="testToDate"  value="%{endDate}" style="width:200px;" /></div>
						 
						 
						 </td>
      </tr>
      </table>
      
		</td>
        </tr>
		  
		  <tr>
		  	<td></td>
		  	
		  	<td>
		  	<div class="field-label"><strong>Subject :</strong></div>
				      <s:textfield name="subject" id = "subject" style="width:420px;" maxlength="500"></s:textfield>
		  	</td>
		  
		  </tr>
		  
		 
		  
		 <tr>
			      <td valign="top">
				      
			      </td>
			      
			      <td >
			         <div class="field-label"><strong>Mail Content:</strong></div>
			      	<s:textarea name="mailContent"  id = "mailContent" rows = "5" cols="50" style="border:#CFCCC9 1px solid;"  onkeypress="return imposeMaxLength(event,this,3000);"></s:textarea>
				 </td>
				 
		 
    	</tr>
    	
    	
    	<tr>
			      <td valign="top">
				      
			      </td>
			      
			      <td >
			         <div class="field-label"><strong>SMS Content:</strong></div>
			      	<s:textarea name="smsContent"  id = "smsContent" rows = "5" cols="50" style="border:#CFCCC9 1px solid;"  onkeypress="return imposeMaxLength(event,this,150);"></s:textarea>
				 </td>
				 
		 
    	</tr>
  
  	
        
  
  <tr><td></td>
  	<td >
	  <s:submit value="Submit" method="sendMail" cssClass="submitBtn button-gradient" onclick ="return callMethod();"></s:submit>
	  
  	</td>
  </tr>
  </table>
  </s:form></div>
  </div>