<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function() {
	$(".subNavBg").hide();
});
</script>


<!-- Change Password Box Start -->
<div class="dash_linkexpeire">

<s:form id = "frmFirstLoginChangePassword" action="">
<input type="hidden" name="IsRegistered" value="<%=request.getAttribute("IsRegistered")%>"/>
	
		<h1 class="pageTitle" title="Reset Password">Reset Password</h1>
		<div class="hr-underline2"></div>
		This Link has already been used, kindly click on Forgot Password link of Login Page to generate new link.</b>
		<br />
		<br />
		<br />
		
</s:form>
</div>

<!-- Change Password Box End -->




