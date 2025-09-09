<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/moment.min.js"></script>
<script type="text/javascript" src="js/jquery-3.6.3.min.js"></script>
<link rel="stylesheet" href="css/jquery-ui.css">
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<link rel="stylesheet" href="css/common.css">

<script type="text/javascript">
	$(document).ready(function() {
		$(".subNavBg").hide();
	});
	
	function goToMenu(link) {
		$("#paymentSuccMsg").attr('action', link);
		$("#paymentSuccMsg").submit();
	}
</script>
<div class="clear"></div>
<div class="container common_dashboard">
	<div class="accordions">
		<h1 class="pageTitle" title="Payment Message">Payment Message</h1>
		<div class="accordion">
			<div id="dashboard">
				<s:form id="paymentSuccMsg" action="CandidateAction">
					<div class="ErrorDiv basicDetailsBg">
						<input type="hidden" name="menuKey"
							value='<%=request.getAttribute("menuKey")%>' />
						<div class="payment_success_msg">
							<s:property value="responseMsg"></s:property>
						</div>
					</div>

					<div class="row justify-content-center padTopBottom20">
						<div class="col-sm-offset-5 col-sm-2">
							<s:if test='%{transaction == "success"}'>
								<button type="button"
									class="submitBtn btn btn-warning btn-block"
									onclick="goToMenu('CandidateAction_dashboard.action')">Go
									to Dashboard</button>
							</s:if>
							<s:elseif
								test='%{transaction == "pending" || transaction == "failed"}'>
								<button type="button"
									class="submitBtn btn btn-warning btn-block"
									onclick="goToMenu('PaymentOnlineAction_showPaymentScreen.action')">Retry
									Payment</button>
							</s:elseif>
						</div>
					</div>
					<s:token />
				</s:form>
			</div>
		</div>
	</div>
</div>
