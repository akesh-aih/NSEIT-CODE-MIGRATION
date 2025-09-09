<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<script>
	function callSubmit(){
	var csvFile = $("#csvFile").val();
	var message = "";
	if (csvFile == "" || csvFile ==''){
		message = message + "Please select a File for Upload."+"##";
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

</script>

  <div class="main-body" style="height:380px;">
  <div id="dashboard">
  <h1 class="pageTitle" title="Upload Credentials">Upload Credentials</h1>
  <div class="hr-underline2"></div>
  <s:form action="AgencyAction" enctype="multipart/form-data" onsubmit="return callSubmit();">
  <input type="hidden" name="menuKey" value='<%=request.getAttribute("menuKey")%>'/>
  <div id="error-massage3" style="display:none" class="error-massage">
      		<div class="error-massage-text">
      		<ul style="margin:0; margin-left:20px; padding:0;" id="error-ul3">
      	
      		</ul>      	
      	 </div>
      </div>
  <s:if test="hasActionMessages()">
		   <div id="error-massage4" class="error-massage">
	      		<div class="error-massage-text">
	      		<ul style="margin:0; margin-left:20px; padding:0;" id="error-ul3">
	      			<s:actionmessage escape="false"/>
	      		</ul>      	
	      	 	</div>
      		</div>
	</s:if>    
  <div align="center"  >
  		<s:file name="csvFile" id="csvFile"></s:file>
  		<br/><br/><br/>

  		<s:submit method="insertCsvFileData" cssClass="submitBtn button-gradient" ></s:submit>
  		
  		 <s:if test='%{flagForDisplay=="true"}'>
  		 <br/>
  		 <br/>
  		 
  		 <div align="left"><b>Credentials are uploaded successfully.</b></div>
  		 <div class="hr-underline2"></div>
  		 <div style="height:200px; overflow:auto;">
  		 	<table width="100%" align="left" style="background:#eee;" align="left">
  		 		 <tr>
  		 		 <td align="left">
		    		Total import candidate count :: <s:property value="importTotalCount"/>
		    	 </td>
		  		 </tr>
  		 		 <tr>
  		 		 <td align="left">
		    		Successfully imported candidate count :: <s:property value="importCredentialSuccessCount"/>
		    	 </td>
		  		 </tr>
		  		 <tr>
		  		 <td align="left">
		    		Failed to import candidate count :: <s:property value="importCredentialFailCount"/>
		    	 </td>
		  		 </tr>
		  		 <tr><td align="left"><s:property value="importSummary" escape="false"/></td></tr>
	  		 </table>
	  		 </div>
	  	 </s:if>
  		 
  		 <s:if test='%{flagForDisplay=="false"}'>
  		 <br/>
  		 <br/>
  		 <div align="left" ><b>Upload Credentials failed.</b></div>
  		 	<!--<table width="500" border="1" align="left" cellpadding="0" cellspacing="0" class="table">
  		 	-->
  		 	<div class="hr-underline2"></div>
  		 	<div style="height:200px; overflow:auto;">
  		 	<table width="100%" align="left" style="background:#eee;" align="left">
  		 		 <tr>
  		 		 <td align="left">
		    		Total import candidate count :: <s:property value="importTotalCount"/>
		    	 </td>
		  		 </tr>
  		 		 <tr>
  		 		 <td align="left">
		    		Successfully imported candidate count :: <s:property value="importCredentialSuccessCount"/>
		    	 </td>
		  		 </tr>
		  		 <tr>
		  		 <td align="left">
		    		Failed to import candidate count :: <s:property value="importCredentialFailCount"/>
		    	 </td>
		  		 </tr>
		  		 <tr><td align="left"><s:property value="importSummary" escape="false"/></td></tr>
	  		 </table>
	  		 </div>
  		 </s:if>
  </div>
  </s:form>
  </div>
  </div>