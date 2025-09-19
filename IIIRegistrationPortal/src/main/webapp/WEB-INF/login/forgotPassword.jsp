<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Forgot Password</title>
    <link rel="stylesheet" href="<s:url value="/css/bootstrap.min.css" />" type="text/css"/>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background-color: #f8f9fa;
        }
        .forgot-password-container {
            width: 100%;
            max-width: 400px;
            padding: 15px;
            margin: auto;
            border: 1px solid #dee2e6;
            border-radius: 0.25rem;
            background-color: #fff;
            box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
        }
        .forgot-password-container h2 {
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
    <div class="forgot-password-container">
        <h2>Forgot Password</h2>

        <s:if test="hasActionErrors()">
            <div class="alert alert-danger" role="alert">
                <s:actionerror/>
            </div>
        </s:if>
        <s:if test="hasActionMessages()">
            <div class="alert alert-success" role="alert">
                <s:actionmessage/>
            </div>
        </s:if>

        <s:form action="ForgotPasswordAction_resetPassword" method="post">
            <div class="form-group">
                <label for="loginId">Login ID</label>
                <s:textfield name="loginId" cssClass="form-control" placeholder="Enter Login ID" required="true" />
            </div>
            <div class="form-group">
                <label for="emailId">Email ID</label>
                <s:textfield name="emailId" cssClass="form-control" placeholder="Enter Registered Email ID" required="true" />
            </div>
            <s:submit value="Reset Password" cssClass="btn btn-primary btn-block" />
            <p class="bottomLink"><a href="LoginAction_input.action">Back to Login</a></p>
        </s:form>
    </div>
</body>
</html>