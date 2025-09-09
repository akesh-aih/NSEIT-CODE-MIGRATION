<%@page import="com.nseit.generic.util.GenericConstants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title><tiles:getAsString name="title" ignore="true" /></title>
<link href="css/common.css" rel="stylesheet" type="text/css" media="screen" />
<link rel="stylesheet" href="css/all.min.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/font-awesome.min.css" type="text/css" media="screen">

<link href="css/Flexgrid.css" rel="stylesheet" type="text/css" media="screen" />
<link type="text/css" href="css/ui-lightness/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
<link rel="stylesheet" href="css/validationEngine.jquery.css" type="text/css" />

<!--   <script src="js/jquery.min.js"></script>-->
<script type="text/javascript" src="js/login.js"></script>
<script type="text/javascript" src="js/paging.js"></script>
<script type="text/javascript" src="js/pagingForPayment.js"></script>
<script src="js/jquery-3.6.3.min.js"></script>
<%-- <script type="text/javascript" src="js/jquery-ui.min.js"></script> --%>
<script type="text/javascript" src="js/jcarousellite_1.0.1c4.js"></script>
<script src="js/languages/jquery.validationEngine-en.js" type="text/javascript" charset="utf-8"></script>
<script src="js/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="js/jquery.form.js"></script>
<script type="text/javascript" src="js/jquery.clientsidecaptcha.js"></script>

<script type='text/javascript'>
		
	var dataFormat = "<%=GenericConstants.DATE_FORMAT_DEFAULT_JAVASCRIPT%>";
	var range = "c-60";
	var maxDateDOB = "-1D";
	var maxDateOther = "";

	$(document).ready(function() {

		$(window).scroll(function() {
			if ($(this).scrollTop() > 100) {
				$('.scrollup').fadeIn();
			} else {
				$('.scrollup').fadeOut();
			}
		});

		$('.scrollup').click(function() {
			$("html, body").animate({
				scrollTop : 0
			}, 600);
			return false;
		});

		$("#datepicker3").datepicker({
			showOn : "button",
			buttonImageOnly : true,
			buttonImage : "images/cale-img.gif",
			buttonImageOnly : true,
			dateFormat : 'dd-M-yy'
		});
		//Sumit
		$("#datepicker4").datepicker({
			showOn : "button",
			buttonImageOnly : true,
			buttonImage : "images/cale-img.gif",
			buttonImageOnly : true,
			dateFormat : 'dd-M-yy'
		});

	});

	window.history.forward();
	function noBack() {
		window.history.forward();
	}

	function menuLinks(link) {
		$("#menuForm").attr('action', link);
		enableLoadingAnimation();
		$("#menuForm").submit();
	}
</script>
</head>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();"
	onunload="">
	<div>
		<tiles:importAttribute ignore="true" name="menuKey" />
		<tiles:importAttribute ignore="true" name="title" />


		<!-- Header Start -->
		<tiles:insertAttribute name="header">
			<tiles:putAttribute name="menuKey" value="${menuKey}" />
			<tiles:putAttribute name="title" value="${title}" />
		</tiles:insertAttribute>
		<!-- Header End -->
		<tiles:insertAttribute name="menu">
			<tiles:putAttribute name="menuKey" value="${menuKey}" />
		</tiles:insertAttribute>
		<tiles:insertAttribute name="body" />


		<a href="#" class="scrollup" title="Scroll Back to Top"><i
			class="fa fa-chevron-up"></i></a>
		<tiles:insertAttribute name="footer" />

		<jsp:include page="../changePassword.jsp"></jsp:include>

	</div>
	<div class="fade" id="block7"></div>


	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->

	<!-- Include all compiled plugins (below), or include individual files as needed -->
</body>
</html>