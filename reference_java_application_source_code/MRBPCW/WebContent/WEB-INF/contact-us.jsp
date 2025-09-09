<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" >
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="CACHE-CONTROL" content="NO-CACHE" />
<meta http-equiv="EXPIRES" content="0" />
<meta http-equiv="PRAGMA" content="NO-CACHE" />
<meta http-equiv="X-UA-Compatible"
	content="IE=10; IE=9; IE=8; IE=7; IE=EDGE" />
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ page language="java"
	import="com.nseit.generic.util.ConfigurationConstants"%>
<%@ page language="java" import="com.nseit.generic.util.CommonUtil"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<%@ page language="java"
	import="com.nseit.generic.util.ConfigurationConstants"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<link href="css/bootstrap.min.css" rel="stylesheet" />
<link href="css/common.css" rel="stylesheet" type="text/css"
	media="screen" />
<link rel="stylesheet" href="css/font-awesome.min.css" type="text/css"
	media="screen">
<link rel="stylesheet" href="css/font-awesome.min.css" type="text/css"
	media="screen">
<link href="css/style.css" rel="stylesheet" type="text/css"
	media="screen" />
<link href="css/style2.css" rel="stylesheet" type="text/css"
	media="screen" />
<link href="css/Flexgrid.css" rel="stylesheet" type="text/css"
	media="screen" />
<link type="text/css"
	href="css/ui-lightness/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
<link rel="stylesheet" href="css/validationEngine.jquery.css"
	type="text/css" />
<script src="js/jquery-3.6.3.min.js"></script>
<script src="js/jquery-ui.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js"></script>

<style type="text/css">
.container-fluid.header, .footer {
	display: none;
}

.indexFooter p {
	margin: 0px !important;
}

body {
	background: #fafafa !important;
}

h1 {
	color: #fff;
}
</style>
</head>

<body class="indexBackground">
	<div class="container-fluid pad-neg">
		<div class="gridContainer clearfix ">
			<section class="header"> <article class="logo ">
			<%
				String prodServer = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.Is_this_Live_Server);
				if (prodServer != null && !prodServer.equals("")) {
					if (prodServer.equals("N")) {
			%>
			<div class="clear"></div>
			<div class="text-center mt20">
				<img src="images/demo_test.gif" alt="This is Demo/Test Instance" />
			</div>
			<%
				}
				}
			%> <br>

			</article>
			<div class=" clearfix">
				<section class="header_index"> <article class="logo ">
				<div class="banner1 img-responsive"></div>
				</article> <article class="nav-links"> <a href="../MRBPCW/"><nav
						id="home"> <span>Home</span></nav></a> <a href="../MRBPCW/contact-us.jsp"><nav
						id="contact-us" class="nav-selected"> <span>Contact
						Us</span></nav></a> </article> </section>
			</div>
			</section>
		</div>
	</div>
	<div class="clear"></div>
	<div class="clear"></div>
	<section class="home">
	<div class="lower-header">
		<div class=" highlight-con">
			<div class="gridContainer clearfix ">
				<!-- <h3>ANNOUNCEMENTS </h3> -->
				<div id="demo2" class="scroll-text">
					<font color'="black"> <span><b> Recruitment of Assistant Surgeon (Dental) 2025 Online Application Registration</b></span>
							</font>
				</div>
			</div>
		</div>
		<section class="contact-us display-none" style="display: block;">
		<div class="page-header clearfix padTopBot">
			<div class="image-div">
				<img src="images/contact-us.png" height=50 width=50>
			</div>
			<div class="page-heading">
				<h1>Contact Us</h1>
			</div>
		</div>
		</section> 
		<section class="contect-us-content"></section>
	</div>
	</section>
	<div class="container-fluid pad-neg" style="background-color: #fff">
		<div class="container">
			<div class=" clearfix">
				<section class="home-content"> 
					<section class="home-page-links">
					<div class="section-images certi"></div>
					<div class="section-headings">
						<img src="images/table3_icon_circle.png"
							style="height: 27px !important"
							class="fl-left img-responsive mr10" />
						<h1>Assistance</h1>
						<h3>Help Desk</h3>
					</div>
					<ul>
					<li><span><b>Mail Id - &nbsp;</b><a style="color: blue;" href="mailto:MRBASD2024@onlineregistrationform.org" target="_blank"> 
								mrbasd2024@onlineregistrationform.org</a></span></li>			
					<li data-disabled="false"><span><b>Technical Queries - 022 62507738 (Monday to Saturday 9 AM to 6 PM)</b></span></li>
					<li data-disabled="false"><span><b>Eligibility Queries - 044 24355757 (Monday to Friday 10 AM to 5 PM)</b></span></li>
					</ul>
					</section>
				</section>
				
			</div>
		</div>
	</div>
	<div class="container-fluid" style="background-color: #333">
		<footer>
		<div class="gridContainer clearfix">
			<article class="footer-text"> <span class="copyrght-text">©
				Copyright MRB - Assistant Surgeon (Dental) 2025</span> <span class="terms-condition-text">Site
				best viewed in Chrome 100+, Edge 100+, Firefox 100+, Safari 14+ at
				1024 x 768 pixels resolution and JavaScript should be enabled.</span> <%-- <span
				class="terms-condition-text"> <a target="_blank" href="">terms
					and conditions.</a>
			</span> --%></article>
		</div>
		</footer>
	</div>
</body>
</html>