<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'generateRollNumber.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  	<div id="dashboard">
		  <s:form id="instructionForm" action="CandidateMgmtAction_generateRollNumbers">
		  <input type="hidden" name="menuKey" value='<%=request.getAttribute("menuKey")%>'/>
		  <h1 class="pageTitle" title="Generate Roll Numbers">Generate Roll Numbers</h1>
				<div class="hr-underline2"></div>
				<div>
					Please click on Generate Roll Number button for generating unique roll number for all candidates who have successfully booked seat(s). 
				</div>
				<br/>
				
				<div style="display:block; min-height:300px; height:auto;">
		  			<s:submit value="Generate Roll Number" cssClass="submitBtn button-gradient"></s:submit>
		  		
		  		<div>	
		  			<br/>
		  			<br/>
			  		 <s:if test='%{rollNumberSuccFlag == "true"}'>
			  		 	<s:property value = "rollNumberSuccMsg"/>
			  		 </s:if>
		  		 </div>
		  				
		  		</div>	
		  		
		  		
		  		
		  		
		  </s:form>
	</div>		  
</html>
