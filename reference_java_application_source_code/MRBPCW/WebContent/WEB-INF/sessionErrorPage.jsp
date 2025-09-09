<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script>
function submitForm()
{
	document.frmSignOut.submit();
}
</script>
<div class="container" id="container" style="Display:inline">
	<div class="main-body">
		<s:form action="LoginAction_signout.action" id="frmSignOut" name="frmSignOut">
		<div class="login-container" style="height:100px">
<%--		An instance of this application is already active on this machine. Please click <a href="LoginAction_signout.action">here</a> to invalidate. --%>
			A session is already active for the user. Please click <a href="javascript:void(0)" onclick="submitForm()">here</a> to sign out and re-login.
		</div>
		<s:token/>
		</s:form>
</div>
</div>