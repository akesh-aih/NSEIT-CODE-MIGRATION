<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="change-password-container">
    <h2>Change Password</h2>

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

    <s:form action="ChangePasswordAction_changePassword" method="post">
        <div class="form-group">
            <label for="userLoginId">Login ID</label>
            <s:textfield name="userLoginId" cssClass="form-control" placeholder="Enter Login ID" required="true" />
        </div>
        <div class="form-group">
            <label for="oldPassword">Old Password</label>
            <s:password name="oldPassword" cssClass="form-control" placeholder="Enter Old Password" required="true" />
        </div>
        <div class="form-group">
            <label for="password">New Password</label>
            <s:password name="password" cssClass="form-control" placeholder="Enter New Password" required="true" />
        </div>
        <div class="form-group">
            <label for="confirmNewPassword">Confirm New Password</label>
            <s:password name="confirmNewPassword" cssClass="form-control" placeholder="Confirm New Password" required="true" />
        </div>
        <s:submit value="Change Password" cssClass="btn btn-primary btn-block" />
        <p class="bottomLink"><a href="LoginAction_input.action">Back to Login</a></p>
    </s:form>
</div>