<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	</head>
	<br/>
	<div class="container">
	<h1 class="pageTitle">Seat Utilization Report</h1>
  <div class="hr-underline2"></div>
	<div class="main-body">
  <div id="dashboard">
<form action="seatUtilizationReportExport.jsp" method="get">
	<table align="center">
		<tr align="center">
			<%
				session.setAttribute("reportList", request.getAttribute("reportList"));
			%>
			
			<td>
				<input type="submit" value="Export To Excel" class="submitBtn button-gradient" align="middle">
			</td>
		</tr>
	</table>
</form>
				</div>
				</div>
				</div>
</html>
