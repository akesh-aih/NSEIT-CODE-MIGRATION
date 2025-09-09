<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Security Check</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="css/common.css">
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
</head>

<body bgcolor="#ffefde">
<s:form action="LoginAction_signout.action">
	<div class="container">
		<div class="row">
			<div class="col-sm-12 text-center" style="margin-top: 80px;">
				<p class="font16"> Session Invalid. User logged out successfully. Kindly login again. </p>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-2 col-sm-offset-5 mt20 col-xs-4 col-xs-offset-4 mt20">
				<s:submit  value="Back" cssClass="btn btn-warning btn-block" id="back" title="Back" />
			</div>
		</div>
	</div>
	<s:token/>
</s:form>
</body>
</html>
