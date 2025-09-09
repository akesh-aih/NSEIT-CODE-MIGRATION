<meta name="viewport" content="width=device-width, initial-scale=1.0">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ page language="java"
	import="com.nseit.generic.util.ConfigurationConstants"%>
<%@ page import="com.nseit.generic.util.GenericConstants"%>
<%@ page language="java" import="java.time.ZonedDateTime"%>
<%@ page language="java" import="com.nseit.generic.util.CommonUtil"%>
<script src="js/jquery-3.6.3.min.js"></script>
<script src="js/jquery-ui.js"></script>
<link rel="stylesheet"
	href="css/ui-lightness/jquery-ui-1.8.16.custom.css">
<link rel="stylesheet" href="css/1bootstrap.min.css">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/style2.css">
<link rel="stylesheet" href="css/all.min.css">
<link rel="stylesheet" href="css/font-awesome.min.css">

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

h2 {
	color: #fff;
	padding: 20px 0;
}

h4 span {
	font-size: 15px;
}

.footer-site {
	margin-top: -2px !important;
	height: auto;
	background: #f1f1f1 !important;
	position: relative;
	border-bottom: 2px solid #999999 !important;
}

.header_index .nav-links a {
	text-decoration: none;
	color: #fff;
	font-size: 11px;
	font-family: 'Roboto';
	font-weight: 600;
}
</style>
</head>

<div class="loading-container" id="loadingDialog"></div>
<body class="indexBackground">
	<%
		String prodServer = ConfigurationConstants.getInstance()
				.getPropertyVal(GenericConstants.Is_this_Live_Server);
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
	<nav class="navbar navbar-expand-lg navbar-light  m-l-n-10 h-80 m-b-n-8">
		<div class="container-fluid justify-content-end pe-0">
			<article class="logo">
				<a class="navbar-brand"><img src="images/logo.png" alt="" /></a>
			</article>
			<button class="navbar-toggler mb-1" type="button" data-bs-toggle="collapse"
				data-bs-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse topnav margin-mob justify-content-end"
				id="navbarTogglerDemo03">
				<div class="d-flex">
					<section class="header_index"> 
						<article class="nav-links scrollmenu"> 
								
								<a href="../MRBPCW/"><nav id="home">
									<i class="fa-solid fa-home fa-2xl mt30 d-block mobile-hidden"></i>
									<span>Home</span>
								</nav></a> 
								
								<a href="#"><nav id="contact-us" class="nav-selected">
									<i class="fa-solid fa-envelope fa-2xl mt25 d-block mobile-hidden"></i>
									<span>Contact Us</span>
								</nav></a>
						</article>
					</section>
				</div>
			</div>
		</div>
	</nav>
	<div class="clear"></div>
	<div class="clear"></div>
	<section class="home">
		<div class="lower-header">
			<div class=" highlight-con">
				<div class="gridContainer clearfix ">
					<!-- <h3>ANNOUNCEMENTS </h3> -->
					<div id="demo2" class="scroll-text">
						<font color'="black"> <span><b> Recruitment of Prosthetic Craftsman 2025 Online Application Registration</b></span>
						</font>
					</div>
				</div>
			</div>
			<div class="clear"></div>
			<div class="headerEndBg footer-site"
				style="background: white !important">
				<div class="container">
					<div class="row">
						<div class="col-md-6 col-xs-6 title">
							<img src="images/contact-us.png" width="20" class="mb-1"> <span>Contact
								Us</span>
						</div>
					</div>
				</div>
			</div>
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
							<li><span><b>Mail Id -&nbsp;</b><a
									style="color: blue;"
									href="mailto:mrbpcw2025@onlineregistrationform.org"
									target="_blank">mrbpcw2025@onlineregistrationform.org</a></span></li>
							<li data-disabled="false"><span><b>Technical
										Queries -</b> 022 62507743 (Monday to Saturday 9 AM to 6 PM - Except Public Holidays)</span></li>
							<li data-disabled="false"><span><b>Eligibility
										Queries -</b> 044 24355757 (Monday to Friday 10 AM to 5 PM)</span></li>
						</ul>
					</section>
				</section>

			</div>
		</div>
	</div>
	<div class="container-fluid" style="background-color: #333">
		<footer>
			<div class="gridContainer clearfix">
				<article class="footer-text">
					<span class="copyrght-text">© Copyright MRB - Prosthetic
						Cratsman 2025</span> <span class="terms-condition-text">Site best
						viewed in Chrome 100+, Edge 100+, Firefox 100+, Safari 14+ at 1024
						x 768 pixels resolution and JavaScript should be enabled.</span>
					<%-- <span
				class="terms-condition-text"> <a target="_blank" href="">terms
					and conditions.</a>
			</span> --%>
				</article>
			</div>
		</footer>
	</div>
</body>
</html>