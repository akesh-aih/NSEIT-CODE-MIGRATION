<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="com.nseit.generic.util.ConfigurationConstants"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<link rel="stylesheet" href="css/font-awesome.min.css">
<script src="js/jquery-3.6.3.min.js"></script>
<s:if test='%{instrReqd}'>
	<script type="text/javascript">
		function showModal(popID) {
			PopHideAll();
			boxID = document.getElementById(popID);
			jQuery('#pad12').focus();
			FadeboxID = document.getElementById("pop2");
			FadeboxID.style.display = "block";
			boxID.style.display = "block";
			$(".fullscreen-container").fadeTo(200, 1);
			$("pad12").focus();
		}

		/* $(function () {
		 $('#changeLanguageDrop').change(function(){
		 var x = $(this).val();
		 if(x == "English"){
		 $('.english_ins').show();
		 $('.odia_ins').hide();
		 }
		 else if (x == "Odia"){
		 $('.english_ins').hide();
		 $('.odia_ins').show();
		 }
		 });
		 }); */

		$(document).ready(function() {
			var firstLogin = $("#candidateFirstLogin").val();
			if (firstLogin == 'Y') {
				//showModal('pop2');
				//$('#Close').hide();
			}
			var candidateStatusId = $("#candidateStatusId").val();
			if (candidateStatusId != "") {
				if (candidateStatusId < 5) {
					$("#buttonId").show();
				} else if (candidateStatusId >= 5) {
					$("#buttonId").hide();
				}
			}

			/* ......... */

			/* var x = document.getElementById("changeLanguageDrop").value;
			  if(x == "English"){
				  $('.english_ins').show();
				  $('.odia_ins').hide();
			  }
			  else {
				  $('.english_ins').hide();
				  $('.odia_ins').show();
			  } */

			$('.accordion ol, .accordion ul')
					.addClass('instructionsUl');

			//start of hiding Application menu

			var chkDeclaration = $("#declarationhid").val();
			/* console.log("declarationhid : " + chkDeclaration); */
			if (chkDeclaration != null && chkDeclaration != ''
					&& chkDeclaration == 'true') {
				$("#declaration").attr("checked", "checked");
				$("#declaration").attr('disabled', 'disabled');
				$("#declarationhid").val('true');
				$("#button").removeAttr('disabled');
				$("#msgg").hide();
			} else {
				$("#declarationhid").val('false');
				$("#button").attr('disabled', 'disabled');

				var list2 = $('#bs-example-navbar-collapse-1').find(
						'ul')[0];

				//For IE Issues.
				list2.removeChild(list2.children[1]);
				list2.removeChild(list2.children[1]);

				//var list1 = $('#bs-example-navbar-collapse-1').find('ul')[0].children[1];
				//	list1.remove();

			}

			//end of hiding Application menu
			//pop-up for selecting applied subjects
			$(".subNavBg").hide();
			var candidateStatusId = $("#candidateStatusId").val();
			/* console.log("candidateStatusId : " + candidateStatusId); */
			//enableDisableContinue();
		});

		function enableDisableContinue() {

			var enable = $("#declaration").is(":checked");
			if (enable) {
				$("#button").removeAttr('disabled');
				$("#declarationhid").val('true');
			} else {
				$("#button").attr('disabled', 'disabled');
				var arr = $('#bs-example-navbar-collapse-1').find('ul')[0];
				$("#declarationhid").val('false');
				//alert(arr);

			}
		}

		function validateCheckBox() {

			var value = $("#declaration").is(":checked");
			if (!value) {
				alert(" Please check the Declaration check box. ");
				$("#button").attr('disabled', 'disabled');
				return false;
			}
		}
	</script>

	<style>
	#msgg li:first-child {
		display: block;
	}
	
	#msgg br {
		height: 1px;
		float: left;
	}
	
	ol#myList {
		list-style-type: lower-roman;
	}
	
	.pageTitle {
		background: transparent !important;
		font-family: 'Roboto Medium';
		font-size: 14px;
		color: #538447;
		border: 0px solid #478c86;
	}
	
	.tamil {
		display: none;
	}
	
	b, strong {
		font-weight: bold !important;
	}
	
	.odia_ins h2 {
		font-size: 16px;
		font-weight: bold;
	}
	
	.odia_ins .instructionsUl li {
		font-size: 14px;
		margin-bottom: 7px;
	}
	</style>

	<body>
		<div class="clear"></div>
		<s:form id="instructionForm"
			action="CandidateAction_getCandidateDetails">
			<div id="" class="container common_dashboard instructions_dashboard">
				<s:token />
				<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
				<s:hidden id="instruChkData" name="instruChkData"></s:hidden>
				<s:hidden id="candidateFirstLogin" name="candidateFirstLogin" />
				<s:hidden id="candidateStatusId" name="candidateStatusId"
					value="%{#session['SESSION_USER'].candidateStatusId}"></s:hidden>
				<s:hidden id="declarationChecked" name="declarationChecked"
					value="%{#session['SESSION_USER'].declarationChecked}"></s:hidden>
				<s:hidden id="submitStageId" name="submitStageId"
					value="%{#request['submitStage']}"></s:hidden>
				<s:actionmessage escape="false" cssClass="msgg" id="msgg" />

				<div id="error-massage3" style="display: none" class="error-massage">
					<div class="error-massage-text" style="margin: 0; padding: 0;">
						<ul style="margin: 0; margin-left: 20px; padding: 0;"
							id="error-ul3">
						</ul>
					</div>
				</div>

				<%
					Map<String, String> staticDataMap = ConfigurationConstants.getInstance().getStaticDataMap();
							out.println(staticDataMap.get("INSTRUCTION_FORM"));
				%>

				<p class="subheading tamil">
					<s:text name="tamilText13" />
					Declaration
				</p>
				<table>
					<tr>
						<td style="display: inline-block; width: 20px; margin-top: 2px;"><s:checkbox
								name="declaration" id="declaration"
								onclick="enableDisableContinue();" /> <s:hidden
								name="declarationhid" id="declarationhid" /></td>

						<td><span class="font14 english_ins"> I have fully
								gone through the notification / advertisement, information
								brochure and instructions for Online application for this
								recruitment before filling-up the Online application form and I
								hereby accept all the rules and norms prescribed in it.</span> <%-- <span
						class="font14 odia_ins">
							&#2951;&#2980;&#3021;&#2980;&#3015;&#2992;&#3021;&#2997;&#3009;&#2965;&#3021;&#2965;&#3009;&#2996;&#3009;&#2990;&#2980;&#3021;&#2980;&#3007;&#2985;&#3021;&#32;&#2951;&#2980;&#3021;&#2980;&#3015;&#2992;&#3021;&#2997;&#3007;&#2993;&#3021;&#2965;&#3006;&#2985;&#32;&#2949;&#2993;&#3007;&#2997;&#3007;&#2965;&#3021;&#2965;&#3016;&#32;&#47;&#32;&#2997;&#3007;&#2995;&#2990;&#3021;&#2986;&#2992;&#2990;&#3021;&#44;&#32;&#2980;&#2965;&#2997;&#2994;&#3021;&#32;&#2970;&#3007;&#2993;&#3021;&#2993;&#3015;&#2975;&#3009;&#44;&#32;&#2990;&#2993;&#3021;&#2993;&#3009;&#2990;&#3021;&#32;&#2965;&#2979;&#3007;&#2985;&#3007;&#32;&#2997;&#2996;&#3007;&#2991;&#3006;&#2965;&#32;&#2997;&#3007;&#2979;&#3021;&#2979;&#2986;&#3021;&#2986;&#2990;&#3021;&#32;&#2970;&#2990;&#2992;&#3021;&#2986;&#3021;&#2986;&#3007;&#2986;&#3021;&#2986;&#2980;&#2993;&#3021;&#2965;&#3006;&#2985;&#32;&#2949;&#2993;&#3007;&#2997;&#3009;&#2992;&#3016;&#2965;&#2995;&#3021;&#32;&#2950;&#2965;&#3007;&#2991;&#2985;&#2997;&#2993;&#3021;&#2993;&#3016;&#32;&#2990;&#3009;&#2996;&#3009;&#2990;&#3016;&#2991;&#3006;&#2965;&#32;&#2986;&#2975;&#3007;&#2980;&#3021;&#2980;&#3009;&#32;&#2949;&#2980;&#3007;&#2994;&#3021;&#32;&#2953;&#2995;&#3021;&#2995;&#32;&#2984;&#3007;&#2986;&#2984;&#3021;&#2980;&#2985;&#3016;&#2965;&#2995;&#3009;&#2965;&#3021;&#2965;&#3009;&#2990;&#3021;&#44;&#32;&#2997;&#3007;&#2980;&#3007;&#2990;&#3009;&#2993;&#3016;&#2965;&#2995;&#3009;&#2965;&#3021;&#2965;&#3009;&#2990;&#3021;&#32;&#2953;&#2975;&#3021;&#2986;&#2975;&#3009;&#2965;&#3007;&#2993;&#3015;&#2985;&#3021;&#32;&#2958;&#2985;&#3021;&#2993;&#3009;&#32;&#2953;&#2993;&#3009;&#2980;&#3007;&#32;&#2949;&#2995;&#3007;&#2965;&#3021;&#2965;&#3007;&#2993;&#3015;&#2985;&#3021;.
					</span> --%></td>
					</tr>
				</table>
			</div>

			<div class="countinuebg">
				<div class="container padding0">
					<div align="row">
						<div
							class="col-md-2 col-xs-6 col-sm-offset-10 padding0 padRgt15 padLeft15 col-xs-6 col-xs-offset-3">
							<s:if test="%{#session['SESSION_USER'].candidateStatusId<=5}">
								<s:submit value="Continue" id="buttonId"
									cssClass="btn btn-warning btn-block"
									onclick="return validateCheckBox();"></s:submit>
							</s:if>
							<s:else>
								<!-- Continue button Hide After Appln Form Submitted -->
							</s:else>
						</div>
					</div>
				</div>
			</div>
		</s:form>
	</body>
</s:if>
