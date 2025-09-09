
<%@page import="com.nseit.generic.util.GenericConstants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	function goToDestination()
	{
		window.location = $("#destinationURL").val();
	}
	function areYouSyre(){
		$('html, body').animate({ scrollTop: 0 }, 0);
		ShowPop('pop9');
	}
</script>
<div id="dashboard" style="min-height: 320px;">

	<h1 class="pageTitle" title="Book a Seat">
		Message
	</h1>
	<div class="hr-underline2"></div>
	<div>
		<input type="hidden" id="destinationURL"
			value="<s:text name="%{#request[\'DESTINATION_PATH\']}" />" />

		<%
		String message = null;
		String path = null;
		if(request.getAttribute(GenericConstants.GLOBAL_PLAIN_TEXT_MESSAGE)!=null){
			message = request.getAttribute(GenericConstants.GLOBAL_PLAIN_TEXT_MESSAGE).toString();
		}
		if(request.getAttribute(GenericConstants.DESTINATION_PATH)!=null){
			path = request.getAttribute(GenericConstants.DESTINATION_PATH).toString();
		}
	%>

		<br />
		<br />

		<s:text name="%{#request['GLOBAL_PLAIN_TEXT_MESSAGE']}" />

		<br />
		<br />
		<input type="button" name="Confirm" id="true" value="Confirm"
			class="submitBtn button-gradient" onclick="areYouSyre();" />
		
	</div>
	<s:form action="CandidateAction">
	<div class="change-pass box-gradient" id="pop9" style="vertical-align: top">
		<div>
			<a href="javascript:void(0);" onclick="PopHideAll();">
			<img src="images/Close.png" align="right" border="0" title="Close" />
			</a>
		</div>
		<div class="pad12">
			<div class="titleBox fl-left">
				<h1 class="pageTitle" title="Login">
					&nbsp;
					<s:label value="Preference Test Center Confirmation" />
				</h1>
			</div>
			<div class="closebtnBox fl-rigt"></div>
			<div class="hr-underline clear"></div>
			<br />
			<div style="text-align: center">
				Are you sure?
			</div>
			<br />
			<div style="text-align: center">
				<s:submit method="updateCandidateStage" value="Yes" cssClass="submitBtn button-gradient"></s:submit>
				<input type="button" name="No" id="true" value="No" class="submitBtn button-gradient" onclick="PopHideAll()" />
			</div>
		</div>

		<br />
		<br />
	</div>
	</s:form>

</div>