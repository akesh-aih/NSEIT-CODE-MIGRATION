<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Instructions</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	
  </head>
  
  <body>
    <div id="dashboard">
	<s:form id="instructionForm" action="CandidateAction_getCandidateDetails">	
	<input type="hidden" name="menuKey" value='<%=request.getAttribute("menuKey")%>'/>
		<h1 class="pageTitle" title="Dashboard">Registration Form</h1>
		<div class="hr-underline2"></div>
		
		<!-- Box Container Start -->
		<div style="display:block; min-height:300px; height:auto;">
  <ol>
    <li> Candidates are advised in their own interest to keep the user-id and password confidential, to avoid misuse by another candidate.<br />
      <br />
    </li>
    <li> Candidates have to fill all the relevant details very carefully, there is provision of edit and submit button. Candidate can edit  his/her registration details as many times as he/she wishes, however once the details filled by him/her are submitted, then no further change (s) shall be permitted.<br />
      <br />
    </li>
    <li> Candidates are advised to take a Print of their registration form.<br />
      <br />
    </li>
  </ol>
		<div style="text-align:right;"><s:submit  value="Continue"  id="button" cssClass="submitBtn button-gradient"></s:submit></div> 
</div>

		</s:form>


	</div>

  </body>
</html>
