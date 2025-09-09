<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page language="java"
	import="com.nseit.generic.util.ConfigurationConstants"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<%@ page language="java" import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="css/common.css" rel="stylesheet" type="text/css"
	media="screen" />
<link href="css/style.css" rel="stylesheet" type="text/css"
	media="screen" />
<link rel="stylesheet" href="css/font-awesome.min.css">
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"
	media="screen" />
<title>Instructions</title>
<link rel="stylesheet"
	href="css/font-awesome.min.css"
	type="text/css" media="screen">
<link href="css/Flexgrid.css" rel="stylesheet" type="text/css"
	media="screen" />
<link type="text/css"
	href="css/ui-lightness/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
<link rel="stylesheet" href="css/validationEngine.jquery.css"
	type="text/css" />

<!--   <script src="js/jquery.min.js"></script>-->
<script type="text/javascript" src="js/login.js"></script>
<script type="text/javascript" src="js/paging.js"></script>
<script type="text/javascript" src="js/pagingForPayment.js"></script>
<script src="js/jquery-3.6.3.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/jcarousellite_1.0.1c4.js"></script>
<script src="js/languages/jquery.validationEngine-en.js"
	type="text/javascript" charset="utf-8"></script>
<script src="js/jquery.validationEngine.js" type="text/javascript"
	charset="utf-8"></script>
<script type="text/javascript" src="js/jquery.form.js"></script>
<script type="text/javascript" src="js/jquery.clientsidecaptcha.js"></script>
<script>
	function continueRegPage() {
		$("#instructionForm").attr('action',
				"LoginAction_registerCandidate.action");
		$("#instructionForm").submit();
	}

	$(function() {
		$('#changeLanguageDrop').change(function() {
			var x = $(this).val();
			/* alert(x); */
			if (x == "English") {
				$('.english_ins').show();
				$('.hindi_ins').hide();
			} else if (x == "Hindi") {
				$('.english_ins').hide();
				$('.hindi_ins').show();
			}
		});
	});

	//  Accordion Panels
	$(document).ready(function() {

		$("#checkbox").click(function() {
			$("#continue").attr("disabled", !this.checked);
		});

		$('input:checkbox').removeAttr('checked');
		$("#InstructionsDiv .accordion").show();
		//$('#HowtoApply .accordion').slideToggle('slow');
		$('#InstructionsDiv h3').addClass("removeCurrent");

		//.....
		$('ol, ul').addClass('instructionsUl');

	});
</script>
<style type="text/css">
.tamil {
	display: none;
}

.pageTitle {
	background: transparent !important;
	font-family: 'Roboto Medium';
	font-size: 19px;
	color: #ba9709;
	border: 0px solid #00836c;
}

b, strong {
	font-weight: bold !important;
}

.hindi_ins h2 {
	font-size: 16px;
	font-weight: bold;
}

.hindi_ins .instructionsUl li {
	font-size: 14px;
	margin-bottom: 7px;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#englishDiv").show();
		$("#hindiDiv").hide();
		$("#english").click(function() {
			$("#hindiDiv").fadeOut();
			$("#englishDiv").fadeIn();
		});
		$("#hindi").click(function() {
			$("#englishDiv").fadeOut();
			$("#hindiDiv").fadeIn();
		});

		/* ............. */
		/* var x = document.getElementById("changeLanguageDrop").value;
		if (x == "English") {
			$('.english_ins').show();
			$('.hindi_ins').hide();
		} else {
			$('.english_ins').hide();
			$('.hindi_ins').show();
		} */

	});
</script>
</head>

<body>
	<div id="" class="container common_dashboard instructions_dashboard">
		<form id="instructionForm">
			<%
				Map<String, String> staticDataMap = ConfigurationConstants.getInstance().getStaticDataMap();
				out.println(staticDataMap.get("INSTRUCTION_FORM"));
			%>
					
					</form>
	</div>

</body>
</html>