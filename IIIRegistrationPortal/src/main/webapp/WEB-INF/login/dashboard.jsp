<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
    <h2>Welcome, <s:property value="#session.user.userName"/>!</h2>
    <p>Your role is: <s:property value="#session.user.roleName"/></p>
    
    <s:form action="LoginAction_logout" method="post">
        <s:submit value="Logout" />
    </s:form>
</body>
</html>
