<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="<s:url value="/css/bootstrap.min.css" />" type="text/css"/>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background-color: #f8f9fa;
        }
        .login-container {
            width: 100%;
            max-width: 400px;
            padding: 15px;
            margin: auto;
            border: 1px solid #dee2e6;
            border-radius: 0.25rem;
            background-color: #fff;
            box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
        }
        .login-container h2 {
            margin-bottom: 1.5rem;
            text-align: center;
        }
        .form-group {
            margin-bottom: 1rem;
        }
        .bottomLink {
            text-align: center;
            margin-top: 1rem;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h2>Login</h2>

        <s:if test="hasActionErrors()">
            <div class="alert alert-danger" role="alert">
                <s:actionerror/>
            </div>
        </s:if>

        <s:form action="LoginAction_authenticateUser" method="post">
            <div class="form-group">
                <label for="userLoginId">Username</label>
                <s:textfield name="userLoginId" cssClass="form-control" placeholder="Enter Username" />
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <s:password name="password" cssClass="form-control" placeholder="Enter Password" />
            </div>
            <div class="form-group form-check">
                <s:checkbox name="rememberMe" cssClass="form-check-input" fieldValue="true" />
                <label class="form-check-label" for="rememberMe">Remember Me</label>
            </div>
            <s:submit value="Login" cssClass="btn btn-primary btn-block" />
            <p class="bottomLink"><a href="ForgotPasswordAction_input.action">Forgot Password?</a></p>
            <p class="bottomLink"><a href="ChangePasswordAction_input.action">Change Password?</a></p>
        </s:form>
    </div>
</body>
</html>
