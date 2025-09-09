<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page language="java"
	import="com.nseit.generic.util.ConfigurationConstants"%>
<%@ page import="com.nseit.generic.util.GenericConstants"%>
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
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"
	media="screen" />
<title>Instructions</title>
<link rel="stylesheet" href="css/font-awesome.min.css" type="text/css"
	media="screen">
<link href="css/Flexgrid.css" rel="stylesheet" type="text/css"
	media="screen" />
<link type="text/css"
	href="css/ui-lightness/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
<link rel="stylesheet" href="css/validationEngine.jquery.css"
	type="text/css" />

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

	function enableLoadingAnimation() {
		document.getElementById('loadingDialog').style.display = "block";
	}
	function disabledLoadingAnimation() {
		document.getElementById('loadingDialog').style.display = "none";
	}

	$(function() {
		$('#changeLanguageDrop').on('change', function() {
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

	// Accordion Panels
	$(document).ready(function() {

		$("#checkbox").on('click', function() {
			$("#continue").attr("disabled", !this.checked);
		});

		$('input:checkbox').prop("checked", false)
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

/
*.decimal_number ol {
	counter-reset: item
}

.decimal_number li {
	display: block
}

.decimal_number li:before {
	content: counters(item, ".") " ";
	counter-increment: item
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#englishDiv").show();
		$("#hindiDiv").hide();
		$("#english").on('click', function() {
			$("#hindiDiv").fadeOut();
			$("#englishDiv").fadeIn();
		});
		$("#hindi").on('click', function() {
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

	document.onreadystatechange = function() {
		var state = document.readyState;
		enableLoadingAnimation();
		if (state == 'complete') {
			disabledLoadingAnimation();
		}
	}
</script>
</head>
<div class="loading-container" id="loadingDialog"></div>
<body>
	<div id="" class="container common_dashboard instructions_dashboard">
		<%
			String prodServer = ConfigurationConstants.getInstance()
					.getPropertyVal(GenericConstants.Is_this_Live_Server);
			if (prodServer != null && !prodServer.equals("")) {
				if (prodServer.equals("N")) {
		%>
		<div class="clear"></div>
		<div class="text-center mt10">
			<img src="images/demo_test.gif" alt="This is Demo/Test Instance" />
		</div>
		<%
			}
			}
		%>
		<form id="instructionForm">

			<%
				Map<String, String> staticDataMap = ConfigurationConstants.getInstance().getStaticDataMap();
				out.println(staticDataMap.get("INSTRUCTION_FORM"));
			%>

			<p class="subheading tamil">
				<s:text name="tamilText13" />
				Declaration
			</p>
			<p style="color: #FF0000;">
				<b>DECLARATION :</b>
			</p>
			<div class="row">
				<div class="col-md-10 col-xs-12 mb10">
					<input type="checkbox" id="checkbox">&nbsp;</input> <span
						class="font14 english_ins"> I have fully gone through the
						notification / advertisement, information brochure and
						instructions for Online application for this recruitment before
						filling-up the Online application form and I hereby accept all the
						rules and norms prescribed in it. <br />
						<!-- &#2951;&#2980;&#3021;&#2980;&#3015;&#2992;&#3021;&#2997;&#3009;&#2965;&#3021;&#2965;&#3009;&#2996;&#3009;&#2990;&#2980;&#3021;&#2980;&#3007;&#2985;&#3021;&#32;&#2951;&#2980;&#3021;&#2980;&#3015;&#2992;&#3021;&#2997;&#3007;&#2993;&#3021;&#2965;&#3006;&#2985;&#32;&#2949;&#2993;&#3007;&#2997;&#3007;&#2965;&#3021;&#2965;&#3016;&#32;&#47;&#32;&#2997;&#3007;&#2995;&#2990;&#3021;&#2986;&#2992;&#2990;&#3021;&#44;&#32;&#2980;&#2965;&#2997;&#2994;&#3021;&#32;&#2970;&#3007;&#2993;&#3021;&#2993;&#3015;&#2975;&#3009;&#44;&#32;&#2990;&#2993;&#3021;&#2993;&#3009;&#2990;&#3021;&#32;&#2965;&#2979;&#3007;&#2985;&#3007;&#32;&#2997;&#2996;&#3007;&#2991;&#3006;&#2965;&#32;&#2997;&#3007;&#2979;&#3021;&#2979;&#2986;&#3021;&#2986;&#2990;&#3021;&#32;&#2970;&#2990;&#2992;&#3021;&#2986;&#3021;&#2986;&#3007;&#2986;&#3021;&#2986;&#2980;&#2993;&#3021;&#2965;&#3006;&#2985;&#32;&#2949;&#2993;&#3007;&#2997;&#3009;&#2992;&#3016;&#2965;&#2995;&#3021;&#32;&#2950;&#2965;&#3007;&#2991;&#2985;&#2997;&#2993;&#3021;&#2993;&#3016;&#32;&#2990;&#3009;&#2996;&#3009;&#2990;&#3016;&#2991;&#3006;&#2965;&#32;&#2986;&#2975;&#3007;&#2980;&#3021;&#2980;&#3009;&#32;&#2949;&#2980;&#3007;&#2994;&#3021;&#32;&#2953;&#2995;&#3021;&#2995;&#32;&#2984;&#3007;&#2986;&#2984;&#3021;&#2980;&#2985;&#3016;&#2965;&#2995;&#3009;&#2965;&#3021;&#2965;&#3009;&#2990;&#3021;&#44;&#32;&#2997;&#3007;&#2980;&#3007;&#2990;&#3009;&#2993;&#3016;&#2965;&#2995;&#3009;&#2965;&#3021;&#2965;&#3009;&#2990;&#3021;&#32;&#2953;&#2975;&#3021;&#2986;&#2975;&#3009;&#2965;&#3007;&#2993;&#3015;&#2985;&#3021;&#32;&#2958;&#2985;&#3021;&#2993;&#3009;&#32;&#2953;&#2993;&#3009;&#2980;&#3007;&#32;&#2949;&#2995;&#3007;&#2965;&#3021;&#2965;&#3007;&#2993;&#3015;&#2985;&#3021;.
					</span> -->
					</span>
				</div>
				<div class="col-md-2 col-xs-12 mt20">
					<input type="button" onclick="continueRegPage();" value="Continue"
						id="continue" class="btn btn-warning btn-block" disabled />
				</div>
			</div>
		</form>
	</div>
	<s:token />
</body>
</html>