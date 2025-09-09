<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="com.nseit.generic.util.ConfigurationConstants"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>

<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link type="text/css" href="css/ui-lightness/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="css/common.css">
	<link rel="stylesheet" href="css/style.css">
	<link rel="stylesheet" href="css/style2.css">
	<link rel="stylesheet" href="css/font-awesome.min.css">
</head>
<script type="text/javascript">
	$(document).ready(function() {
		$(".subNavBg").hide();
	});
</script>
<style>
.header {
	height: 108px;
}

.banner {
	height: 87px;
}

.accordions .borderBoxDiv {
	padding: 25px !important;
}
/* .... */
 .headerMiddleBg img {
	text-align: center;
} 

.navbar-header.page-scroll {
	width: 100%;
}
</style>
<%
	String prodServer = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.Is_this_Live_Server);
	if (prodServer != null && !prodServer.equals("")) {
		if (prodServer.equals("N")) {
%>
			<div class="clear"></div>
			<div class="text-center">
				<img src="images/demo_test.gif" alt="This is Demo/Test Instance" />
			</div>
<%
		}
	}
%>
<div class="container">
	<div id="" class="welcome_dashboard">
		<div class="accordions">
			<div class="borderBoxDiv">
				<h1 class="pageTitle" title="SignUp Confirmation">Signup
					Confirmation</h1>
				<div class="accordion">
					<s:form id="frmFirstLoginChangePassword">
						<input type="hidden" name="IsRegistered"
							value="<%=request.getAttribute("IsRegistered")%>" />
						<p>
							You have registered successfully for the post Prosthetic
							Craftsman with User ID <b id="user"><%=request.getAttribute("SysGenUserID")%></b>.
							<%-- and Password is <b id="pass"><%=request.getAttribute("randomStr")%></b>. --%>
						</p>
						<p>Candidates should remember and preserve the User ID and
							Password for future reference and access to the registration
							portal. The same should not be shared with anyone. The User ID
							and Password has been sent to your registered email and mobile
							number for your future reference.</p>
						<p>Kindly proceed further to complete your application.</p>

						<br />
						<br />
						<p>
							<a href="LoginAction_input.action" title="Go to Login Page"
								class="WeightBold"> Please click here to Login </a>
						</p>
					</s:form>
					<s:token />
				</div>
			</div>
		</div>
	</div>
</div>