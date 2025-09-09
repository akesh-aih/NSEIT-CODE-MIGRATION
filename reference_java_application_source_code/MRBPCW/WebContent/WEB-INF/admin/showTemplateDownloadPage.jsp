<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'showTemplateDownloadPage.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <div class="fade" id="pop3"></div>
  <div id="dashboard">
  <s:form action="TemplateDownloadAction" id="templateDownloadForm">
  <s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
  
  <div style="display:block; min-height:300px; height:auto;">
	
		  <h1 class="pageTitle" title="Download Template">Download Template</h1>
				<div class="hr-underline2"></div>
	
	  <s:hidden name="templateDownloadType" value="templateDownloadType" ></s:hidden>
  			<s:if test='%{ddChallanTemplateStatus=="A"}'>
  				<div>
    				<s:a href="TemplateDownloadAction_downloadTemplate.action?templateDownloadType=TEMPLATE_DD_CHALLAN" >Download Template For DD-Challan</s:a>
    			</div>
    		</s:if>
    		
    		<s:if test='%{templateUserCredentialsStatus=="A"}'>
    		<br/>
    			<div>
    				<s:a href="TemplateDownloadAction_downloadTemplate.action?templateDownloadType=TEMPLATE_USER_CREDENTIALS" >Download Template For Upload Credentials</s:a>
    			</div>
    		</s:if>
    		
    		<s:if test='%{templateUploadStatus=="A"}'>
    		<br/>
    			<div>
    				<s:a href="TemplateDownloadAction_downloadTemplate.action?templateDownloadType=TEMPLATE_UPLOAD_MARKS" >Download Template For Upload Marks</s:a>
    			</div>
    		</s:if>
    		
    		
  </div>
  </s:form>
  </div>
</html>
