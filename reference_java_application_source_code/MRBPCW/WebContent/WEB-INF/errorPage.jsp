<link rel="stylesheet" href="css/font-awesome.min.css" type="text/css"
	media="screen">

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>

<style>
.headers {
	background: url(topbg.jpg) repeat-x top;
	height: 100px;
	color: #fff;
}
</style>
<script>

function RedirectToHome()
{
	<%String currentUrl = request.getRequestURL().toString();
String[] urlParts = currentUrl.split("/WEB");%>
	window.location = '<%=urlParts[0]%>
	';
	}
</script>
<!-- Header Start -->
<div style="background-color: #800000; height: 100px;"></div>
<!-- Header End -->
<form action="LoginAction">
	<div class="container" style="height: 400px;">
		<div class="main-body" style="padding: 0px 20px;">

			<h1 style="font-color: #F00!importent;">ERROR</h1>
			<div class="hr-underline"></div>
			<div class="height10"></div>
			<div style="_width: 600px; float: left;">
				<div class="errorPageMessage">
					<h1>Our apologies...</h1>
					<p>
						<em>The page you requested cannot be displayed</em>
					</p>
				</div>
				<div class="height10"></div>

				<h2>Suggested actions</h2>
				<div class="ibm-container-body">
					<ul class="ibm-bullet-list ibm-no-links">
						<li>If you typed the address, make sure the spelling is
							correct.<br /> Note: Most addresses are also case sensitive.
						</li>
					</ul>
				</div>

				<h2>Get assistance</h2>
				<div class="ibm-container-body">
					<p>
						Please <strong><a
							href="mrbpcw2025@onlineregistrationform.org">Email Us</a></strong> about a
						broken link. You will receive an email from us to help you find
						what you need.
					</p>
				</div>

				<input type="button" value="Back" Class="submitBtn button-gradient"
					onclick="RedirectToHome()"></input>
				<div class="clear"></div>
				<br />
				<br />

			</div>
		</div>
	</div>
</form>
<div class="footer">
	<span></span>
</div>