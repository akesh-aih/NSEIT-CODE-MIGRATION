<%@ taglib prefix="s" uri="/struts-tags"%>

    <script type="text/javascript">

    	$(document).ready(function() {
    		$("#candidateCount").focus();
			var flag = $("#showExport").val();
        	if(flag == "Y")
        		$("#xp2exl").show();

    		var flag = $("#buttonFlag").val();

    		if (flag == "true"){
        		$("#block9").show();
        		$("#block7").show();
    		}
    	});
    	
		function validateForm(){
			var candidateCount = $("#candidateCount").val();
			var disc = $("#disciplineType").val();
			var message = "";
			if(candidateCount == "" || candidateCount == 0)
				message = "Please Enter no. of Candidate(s) to generate Credentials."+"##";
			
			if(candidateCount != "" && candidateCount > 65000)
				message = "Upto 65000 login credentials can be generated at a time."+"##";
			
			if(disc == "")
				message = message + "Please Select Discipline.";

			if(message != ""){
				var ulID = "error-ul";
				var divID = "error-massage";
				createErrorList(message, ulID, divID); 
				return false;
			}
			
		}

		function clearElement(){
			$("#candidateCount").val("");
			$("#disciplineType").val("");
		}
    </script>

<s:form action="AgencyAction" id="generateLoginCredentials">
<div id="dashboard">
<s:hidden id="showExport" name="showExport"></s:hidden>
	<s:hidden id="buttonFlag" name="flag"></s:hidden>
					
<h1 class="pageTitle" title="Dashboard">Generate Credentials</h1>
<div class="hr-underline2"></div>

	<!-- Box Container Start -->
<div style="display:block; min-height:300px; height:auto;">
  <table class="contenttable">
	<tr>
	<td colspan="3">
	<div id="error-massage" style="display:none">
      	<div class="error-massage-text">
      	<ul style="margin:0; margin-left:20px; padding:0;" id="error-ul">
      	
      	
      	</ul>
</div>
</div>
	
	
	</td>
	</tr>  
    <tr>
      <td width="226">
      			No. of Candidates <span class="manadetory-fields">*</span><br/>
      			
      </td>
      	
      <td width="" colspan="2">
      			<s:textfield name="candidateCount" id="candidateCount" maxlength="5" onkeypress="return numbersonly(event);"></s:textfield>	
      </td>
     
    </tr>
  	
    <tr>
      <td>
      		Type of Discipline <span class="manadetory-fields">*</span> 
      </td>
      
      <td colspan="2" >
      		<s:select list = "discliplineList" name = "disciplineType" label = "Name"  
					headerKey="" headerValue = "Select Discipline" id = "disciplineType" value="%{disciplineType}"
					/>
      </td>
    </tr>
    
    <tr>
      <td>
      	&nbsp;
      </td>
      
      <td colspan="2" >
      		<s:submit cssStyle="width:150px" method="generateLoginCregentials" value="Generate Credentials" onclick="return validateForm();" cssClass="submitBtn button-gradient"></s:submit>
      </td>
    </tr>
    
    <tr>
      <td>&nbsp;</td>
      <td colspan="2"><div style="display:none" id="xp2exl">
      <s:submit cssStyle="width:150px" method="exportToExcel" onclick="clearElement();" value="Export to Excel" cssClass="submitBtn button-gradient"></s:submit>
      </div>
      </td>
    </tr>
    </table>
    <div align="center" class="errorMessageActive" id="loginError">	
    	<s:if test="hasActionMessages()">	
		<s:actionmessage/>
		</s:if>
	</div>
</div>
	
</div>
</s:form>

<div class="forgot-pass box-gradient" id="block9" style = "display:none; z-index:999;">
<div><a href="javascript:void(0);" onclick="HideAll();"><img src="images/Close.png" align="right" border="0" title="Close"/></a></div>
<div class="pad12">
<div class="titleBox fl-left"><h1 class="pageTitle" title="Login">&nbsp;Message</h1></div>
<div class="closebtnBox fl-rigt"></div>
<div class="hr-underline clear"></div>
<div style="text-align:center" ><br/>
    <strong><s:property value = "candidateCount"/>  Login credentials generated successfully. </strong><br /><br />
    <strong>Your request ID for this operation is :  <s:property value = "requestID"/>  </strong>
    
<br />
<br />
<br />
<br />
   
<input type="button" value="Close" class="submitBtn button-gradient" title="Close" onclick="HideAll();"/>

</div>
</div>
</div>
<div class="fade" id="block7" style = "z-index:9;"></div>