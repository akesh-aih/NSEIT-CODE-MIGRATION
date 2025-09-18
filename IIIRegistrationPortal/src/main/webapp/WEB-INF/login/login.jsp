<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h2>Login</h2>

    <s:if test="hasActionErrors()">
        <div style="color: red;">
            <s:actionerror/>
        </div>
    </s:if>

    <s:form action="LoginAction_authenticateUser" method="post">
        <s:textfield name="userLoginId" label="Username" />
        <s:password name="password" label="Password" />
        <s:submit value="Login" />
    </s:form>

</body>
</html>
