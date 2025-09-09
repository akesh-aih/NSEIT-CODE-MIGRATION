<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ page language="java"
	import="com.nseit.generic.util.ConfigurationConstants"%>
<%@ page language="java" import="com.nseit.generic.util.CommonUtil"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<%@ page language="java" import="java.time.ZonedDateTime"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>

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
<link rel="stylesheet" href="css/Flexgrid.css">
<link rel="stylesheet" href="css/validationEngine.jquery.css">
<script src="js/bootstrap.min.js"></script>

<style type="text/css">
.header {
	background: url(../images/topbg.jpg) repeat-x top;
	height: 177px;
	color: #fff;
}

.pad-neg {
	padding-right: 0px !important;
	padding-left: 0px !important;
	margin-right: auto;
	margin-left: auto;
}
</style>
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
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
	<div class="container-fluid pad-neg">
		<!-- <div class="header">
			<div class="padding0"> -->
				<%
					Map<Integer, List<Long>> dateWindowMap = ConfigurationConstants.getInstance().getDateWindowMap();
					List<Long> dateList = dateWindowMap.get(7);
					Long startDate = dateList.get(0);
					Long endDate = dateList.get(1);
					long today = ZonedDateTime.now().toInstant().toEpochMilli();
				%>
				<nav
					class="navbar navbar-expand-lg navbar-light  m-l-n-10 h-80 m-b-n-8">
					<div class="container-fluid justify-content-end pe-0">
						<article class="logo">
							<a class="navbar-brand"><img src="images/logo.png" alt="" /></a>
						</article>
						<%-- <button class="navbar-toggler mb-1" type="button"
							data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo03"
							aria-controls="navbarTogglerDemo03" aria-expanded="false"
							aria-label="Toggle navigation">
							<span class="navbar-toggler-icon"></span>
						</button> --%>
						<div
							class="collapse navbar-collapse topnav margin-mob justify-content-end"
							id="navbarTogglerDemo03">
							<div class="d-flex">
								<section class="header_index">
									<article class="nav-links scrollmenu">
										<a href="#"><nav id="home" class="nav-selected">
												<i class="fa-solid fa-home fa-2xl mt25 d-block mobile-hidden"></i>
												<span>Home</span>
											</nav></a> <a href="../MRBPCW/contact-us.jsp"><nav id="contact-us">
												<i class="fa-solid fa-envelope fa-2xl mt30 d-block mobile-hidden"></i>
												<span>Contact Us</span>
											</nav></a>
									</article>
								</section>
							</div>
						</div>
					</div>
				</nav>
			</div>
		<!-- </div>
	</div> -->
	<div class="highlight">
		<div class="clearfix pad-neg">
			<!-- <h3>ANNOUNCEMENTS </h3> -->
			<div id="demo2" class="scroll-text">
				<!-- <marquee> -->
				<font color'="black"> <span><b> Recruitment of Prosthetic Craftsman 2025 Online Application Registration</b></span>
				</font>
				<!-- </marquee> -->
			</div>
		</div>
	</div>
	<div class=" container-fluid clearfix"
		style="background: transparent linear-gradient(226deg, #7685ac 10%, #1b3476 90%) 0% 0% no-repeat padding-box;">

		<section class="lower-header-left">
			<h1>Important Dates</h1>
			<div class="process-name">
				<a target="_self" href="#!">
					<div class="check-img">
						<img src="images/check-icon.png">
					</div>
					<div class="process-details">
						<h3>
							<%
								Date d = new Date(startDate);
								DateFormat outputFormat = new SimpleDateFormat("MMM d, yyyy, EEEE");
								String formattedDate = outputFormat.format(d);
								out.print(formattedDate);
							%>
						</h3>
						<span>Commencement of on-line submission of application
							form</span>
					</div>
				</a>
			</div>
			<div class="process-name">
				<a target="_self" href="#!">
					<div class="check-img">
						<img src="images/check-icon.png">
					</div>
					<div class="process-details">
						<h3>
							<%
								d = new Date(endDate);
								DateFormat outputFormat1 = new SimpleDateFormat("MMM d, yyyy, EEEE");
								String formattedDate1 = outputFormat1.format(d);
								out.print(formattedDate1);
							%>
						</h3>

						<span>Last Date of submitting the Application form </span>
					</div>
				</a>
			</div>
			<div class="process-name">
				<a target="_self" href="#!">
					<div class="check-img">
						<img src="images/check-icon.png">
					</div>
					<div class="process-details">
						<h3>
							<%
								Map<Integer, List<Long>> dateWindowMapForExam = ConfigurationConstants.getInstance().getDateWindowMap();
								List<Long> dateListforExam = dateWindowMapForExam.get(4);
								Long startDateforExam = dateListforExam.get(0);
								DateFormat outputFormat2 = new SimpleDateFormat("MMM d, yyyy, EEEE");
								String formattedDate2 = outputFormat2.format(startDateforExam);
								if (startDateforExam.toString().equals("978287400000")) {
									out.print("TBD");
								} else
									out.print(formattedDate2);
							%>
						</h3>
						<span>Date of Computer Based Examination</span>
					</div>
				</a>
			</div>
			<div class="process-name">
				<a target="_self" href="#!">
					<div class="check-img">
						<img src="images/check-icon.png">
					</div>
					<div class="process-details">
						<h3>
							<%
								Map<Integer, List<Long>> dateWindowMapForCallLetter = ConfigurationConstants.getInstance()
										.getDateWindowMap();
								List<Long> dateListforCall = dateWindowMapForCallLetter.get(5);
								Long startDateforCall = dateListforCall.get(0);
								DateFormat outputFormat3 = new SimpleDateFormat("MMM d, yyyy, EEEE");
								String formattedDate3 = outputFormat3.format(startDateforCall);
								if (startDateforCall.toString().equals("978287400000")) {
									out.print("TBD");
								} else
									out.print(formattedDate3);
							%>
						</h3>
						<span>Call Letter can be downloaded (Tentative) </span>
					</div>
				</a>
			</div>


		</section>
		<section class="lower-header-center"></section>
		<section class="lower-header-right">
			<%
				if ((today == startDate || today > startDate) && (today == endDate || today < endDate)) {
			%>
			<div class="no-of-people">
				<br>Registration of New User <br>for MRB
				<div class="bigbtn">
					<a href="../MRBPCW/instructions.jsp">New User</a>
				</div>
				<div class="video-links1">
					<a target="_blank" href="http://youtu.be/0PRXhMdwZrs"></a>
				</div>
				<div class="video-links1">
					<a target="_self" href="#!"></a>
				</div>
			</div>
			<%
				}
			%>
			<div class="no-of-people">
				<br>Candidate login<br>of MRB for Registered Candidate
				<div class="bigbtn">
					<a href="LoginAction_input.action">Registered Candidate</a>
				</div>
				<div class="video-links1">
					<a target="_blank" href="http://youtu.be/0PRXhMdwZrs"></a>
				</div>
				<div class="video-links1">
					<a target="_self" href="#!"></a>
				</div>
			</div>


		</section>
	</div>


	<div class="clear"></div>

	<div class="clear"></div>


	<style type="text/css">
body {
	background: #fafafa !important;
}

.container-fluid.header, .footer {
	display: none;
}

.indexBackground .boxContainer {
	background-color: #fff !important;
	border-radius: 5px;
	/* border: 1px solid #a8a8a8; */
	-webkit-box-shadow: 0 0px 10px rgba(0, 0, 0, 0.11);
	-moz-box-shadow: 0 0px 10px rgba(0, 0, 0, 0.11);
	box-shadow: 0 0px 10px rgba(0, 0, 0, 0.11);
}

.mt20 {
	margin-top: 20px !important;
}

.mb10 {
	margin-bottom: 10px !important;
}

.marquee1, .marquee1 span, p span {
	background: #85be00 !important;
	font-family: 'Roboto Medium';
	font-size: 14px;
	color: #fff !important;
	line-height: 20px;
	/* cursor: pointer; */
	margin: 0 0 10px 0;
	padding: 10px;
	border-radius: 5px;
	border: 1px solid #85be00 !important;
	display: inline-block;
	width: 100%;
}

.blueTable td {
	font-family: RalewayThin, Helvetica, sans-serif;
	font-size: 14px;
	color: #444;
	border: 1px solid #cccccc;
	background: #fff;
	padding: 5px;
}

.pad15 {
	padding: 15px;
}

.borderRight {
	border-right: 1px solid #afafaf;
}

.col {
	display: table-cell !important;
	float: none !important;
}

.col-container {
	display: table !important;
	width: auto;
}

.col-lg-1, .col-lg-10, .col-lg-11, .col-lg-12, .col-lg-2, .col-lg-3,
	.col-lg-4, .col-lg-5, .col-lg-6, .col-lg-7, .col-lg-8, .col-lg-9,
	.col-md-1, .col-md-10, .col-md-11, .col-md-12, .col-md-2, .col-md-3,
	.col-md-4, .col-md-5, .col-md-6, .col-md-7, .col-md-8, .col-md-9,
	.col-sm-1, .col-sm-10, .col-sm-11, .col-sm-12, .col-sm-2, .col-sm-3,
	.col-sm-4, .col-sm-5, .col-sm-6, .col-sm-7, .col-sm-8, .col-sm-9,
	.col-xs-1, .col-xs-10, .col-xs-11, .col-xs-12, .col-xs-2, .col-xs-3,
	.col-xs-4, .col-xs-5, .col-xs-6, .col-xs-7, .col-xs-8, .col-xs-9 {
	position: relative;
	min-height: 1px;
	padding-right: 15px;
	padding-left: 15px;
}

.borderBottom {
	border-bottom: 1px solid #afafaf;
}

.indexBackground .boxContainer h4 {
	font-family: RalewayThin, Helvetica, sans-serif;
	font-size: 18px;
	color: #333;
	margin-top: 4px;
	margin-bottom: 15px;
}

.indexUl {
	padding: 0 10px 0 10px;
	color: #333333;
	font-size: 14px;
	line-height: 1.8em;
	margin-top: 0px;
	margin-bottom: 0px;
}

.indexUl li {
	text-align: left;
	padding-left: 15px;
	font-family: RalewayThin, Helvetica, sans-serif;
	font-size: 14px;
	color: #333333;
	background-position-y: 10px;
}

@media ( min-width : 1200px) {
	.container {
		width: 1170px !important;
		padding-right: 15px;
		padding-left: 15px;
		margin-right: auto;
		margin-left: auto;
	}
	.col-sm-6 {
		width: 50%
	}
}

@media screen and (min-width: 320px) and (max-width: 600px) {
	.indexBackground .boxContainer {
		background-color: #fff !important;
		border-radius: 0px;
		border: 1px solid #a8a8a8;
	}
	.col {
		display: block !important;
		float: none !important;
	}
	.col-container {
		display: block !important;
		width: auto;
	}
	.borderRight {
		border-bottom: 1px solid #afafaf;
	}
}

@media ( min-width : 992px) {
	.container {
		width: 970px;
		padding-right: 15px;
		padding-left: 15px;
		margin-right: auto;
		margin-left: auto;
	}
}

@media ( min-width : 768px) {
	.container {
		width: 750px;
		padding-right: 15px;
		padding-left: 15px;
		margin-right: auto;
		margin-left: auto;
	}
}
</style>
<body class="indexBackground">

	<%
		Map<String, String> staticDataMap = ConfigurationConstants.getInstance().getStaticDataMap();
		out.println(staticDataMap.get("INDEX_FORM"));
	%>

<body>
</body>

</html>
