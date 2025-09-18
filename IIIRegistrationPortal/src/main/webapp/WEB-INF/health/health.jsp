<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>Health Check</title>
</head>
<body>
    <h1>Application Health Status</h1>
    <p>Status: <s:property value="status"/></p>
    <s:if test="hasActionErrors()">
        <div style="color: red;">
            <s:actionerror/>
        </div>
    </s:if>
</body>
</html>
