<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java"
	import="com.nseit.generic.util.ConfigurationConstants"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<script type="text/javascript">
$(document).ready(function() {
	<s:if test='%{displayRegFlag=="true"}'>
	$("#divId").hide();
	$("#divIdReg").show();
	$("#divIdPayment").hide();
	$("#divIdApp").hide();
	$("#h1").hide();
</s:if>

<s:if test='%{displayPaymentFlag=="true"}'>
	$("#divIdPayment").show();
	$("#divIdReg").hide();
	$("#divId").hide();
	$("#h1").hide();
	$("#divIdApp").hide();
</s:if>
	
<s:if test='%{displayAppFlag=="true"}'>
$("#divId").hide();
$("#divIdReg").hide();
$("#divIdApp").show();
$("#divIdPayment").hide();
$("#h1").hide();
</s:if>
		
	});
		</script>

<div class="main-body">
	<div id="dashboard" style="text-align: center; clear: both;">

		<h1 class="pageTitle" title="Application Closed" id="h1">
			Application Closed</h1>
		<div class="hr-underline2"></div>
		<%-- <div id = "dataMsgDiv" style="display:none">
					<p class="font16 WeightBold">
		 			<%=ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.APPLICATION_CLOSED_MSG)%>
		 			<s:property value = "regEndDate"/> 
		 			</p>
				</div> --%>
		<s:form>
			<div id="dateDiv" style="display: block">
				<%-- <s:hidden id = "otbsStartDt" name="s:textfield"></s:hidden>
			<s:hidden id = "otbsEndDt" name="otbsEndDt"></s:hidden> --%>

				<s:hidden id="regStrtDate" name="s:textfield"></s:hidden>
				<s:hidden id="regEndDate" name="regEndDate"></s:hidden>

				<s:hidden id="appFormStrtDate" name="s:textfield"></s:hidden>
				<s:hidden id="appFormEndDate" name="appFormEndDate"></s:hidden>

				<s:hidden id="paymentStartDate" name="paymentStartDate"></s:hidden>
				<s:hidden id="paymentEndDate" name="paymentEndDate"></s:hidden>

				<s:if test='%{appFormStrtDate != null && appFormEndDate != null}'>
					<div id="divIdApp" style="text-align: center;">
						<p class="font16 WeightBold">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cannot login as
							Application window is not open, Application window<STRONG><s:property
									value="appFormEndDate" /></STRONG>
						</p>
					</div>
				</s:if>

				<s:if test='%{regStrtDate != null && regEndDate != null}'>
					<div id="divIdReg" style="text-align: center;">
						<p class="font16 WeightBold">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;New User creation
							window is not open, New User Creation Window <STRONG><s:property
									value="regEndDate" /></STRONG>
						</p>
					</div>
				</s:if>

				<s:if test='%{paymentStartDate != null && paymentEndDate != null}'>
					<div id="divIdPayment" style="text-align: center;">
						<p class="font16 WeightBold">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cannot login as
							Application & Payment window is not open, Application & Payment
							window<STRONG><s:property value="paymentEndDate" /></STRONG>
						</p>
					</div>
				</s:if>


			</div>
		</s:form>
	</div>
</div>