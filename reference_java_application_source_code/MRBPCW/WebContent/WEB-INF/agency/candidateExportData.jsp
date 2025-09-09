<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'candidateExportData.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style>
.onetime{
  	display: none;
  }
  .container .container { 
    float: left !important;
}
.common_dashboard {float: left;
    width: 100%;}
</style>
  </head>
  <div class="container">
  <div class=" common_dashboard">
<div class="row">
  <div class="col-md-12">

     <s:form action="TemplateDownloadAction">
     <s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
     
    <h1 class="pageTitle" title="Export Candidate Data">Export Candidate Data</h1>
    <br>
    <div style="display:block;   height:auto;">
       <s:submit value="Export" method="createZipFile" cssClass="submitBtn button-gradient"></s:submit>
    </div>
      
      
       <s:if test='%{successMessageFlag=="true"}'>
       <s:property value = "successMessage"/>
       </s:if>
       <s:token />
     </s:form>

  </div>
</div>
<div class="row">
  <div class="col-md-12 mt30">
    <s:form action="TemplateDownloadAction">
      <s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>

      <h1 class="pageTitle" title="Export Candidate Data">Export Candidate Document</h1>
      <br>
    <div style="display:block;   height:auto;">
      <s:submit value="Export" method="createDocumentZipFile" cssClass="submitBtn button-gradient"></s:submit>
    </div>
    <s:if test='%{successMessageFlag=="true"}'>
      <s:property value = "successMessage"/>
    </s:if>
    <s:token />
   </s:form>

  </div>
</div>


 <!-- <table width="100%" border="0">
 	<tr>
 		 <td width="50%">
     <s:form action="TemplateDownloadAction">
     <s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
     
     <table width="300" border="0">
       <tr>
    <td valign="middle"><h1 class="pageTitle" title="Export Candidate Data">Export Candidate Data</h1></td>
    <td  valign="top"><div style="display:block;   height:auto;">
    <s:submit value="Export" method="createZipFile" cssClass="submitBtn button-gradient"></s:submit>
       </div></td>
       </tr>
     </table>
      
      
       <s:if test='%{successMessageFlag=="true"}'>
       <s:property value = "successMessage"/>
       </s:if>
       <s:token />
     </s:form>
     </td> 
 
 <td>

 
 <s:form action="TemplateDownloadAction">
 <s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>

 <table width="400" border="0">
 	<tr>
 		<td valign="middle">
 <h1 class="pageTitle" title="Export Candidate Data">Export Candidate Document</h1>
 </td>
 <td> 
	<div style="display:block;   height:auto;">
 		<s:submit value="Export" method="createDocumentZipFile" cssClass="submitBtn button-gradient"></s:submit>
 	</div>
 	</td>
 	</tr>
 	</table>
 	<s:if test='%{successMessageFlag=="true"}'>
 	<s:property value = "successMessage"/>
 	</s:if>
 	<s:token />
 </s:form>
 
</td>
</tr>
</table>-->
</div>
</div>

</html>
