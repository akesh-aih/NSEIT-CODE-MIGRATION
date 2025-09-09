<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function() {
	$(".subNavBg").hide();
});
</script>

<!-- Change Password Box Start -->

<div id="dashboard">
	<div class="accordions">
		<div  class="borderBoxDiv dash_forgetpass" >
			<h1 class="pageTitle" title="Reset Password">Reset Password</h1>
			<div class="accordion">
				<s:form id = "frmFirstLoginChangePassword" action="">
					<input type="hidden" name="IsRegistered" value="<%=request.getAttribute("IsRegistered")%>"/>
					<div class="row">
						<div class="col-sm-12">
							<p class="font16">Congratulations!!! You have successfully reset password with User ID <span class="weightBold greenTxt"><%=request.getAttribute("SysGenUserID")%></span></p>
						</div>
					</div>
					<div class="row mt20">
						<div class="col-sm-12"><a href="LoginAction_input.action" title="Go to Login Page" class="weightBold font16">
							<s:text name="Please click here to Login"/>
							</a></div>
					</div>
					<div class="clear mt50"></div>
				</s:form>
			</div>
		</div>
	</div>
</div>
<!-- Change Password Box End --> 

