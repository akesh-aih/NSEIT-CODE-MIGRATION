<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

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
            <label for="userLoginId">Login ID</label>
            <s:textfield name="userLoginId" cssClass="form-control" placeholder="Enter Login ID" required="true" />
        </div>
        <div class="form-group">
            <label for="userEmailId">Email ID</label>
            <s:textfield name="userEmailId" cssClass="form-control" placeholder="Enter Registered Email ID" required="true" />
        </div>
        <s:submit value="Reset Password" cssClass="btn btn-primary btn-block" />
        <p class="bottomLink"><a href="LoginAction_input.action">Back to Login</a></p>
    </s:form>
</div>