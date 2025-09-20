<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="login-container">
    <h2>Login</h2>

    <s:if test="hasActionErrors()">
        <div class="alert alert-danger" role="alert">
            <s:actionerror/>
        </div>
    </s:if>

    <s:form action="login_execute" method="post">
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