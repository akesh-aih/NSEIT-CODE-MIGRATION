<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>Health Check</title>
</head>
<body>
    <h1>Health Check</h1>
    <s:form action="health" method="post">
        <s:textfield name="testName" label="Test Name" />
        <s:submit />
    </s:form>
    <p>Status: <s:property value="status"/></p>
</body>
</html>