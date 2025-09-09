<%@page import="com.nseit.generic.util.GenericConstants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	function goToDestination()
	{
		window.location = $("#destinationURL").val();
	}
</script>
<div id="dashboard">
<div id="Container" style="height:330px;">
<h1 class="pageTitle" title="Massage" >Home</h1>
<div class="hr-underline2"></div>

<div >
	<input type="hidden" id="destinationURL" value="<s:text name="%{#request[\'DESTINATION_PATH\\']}" />" />
	
	
		<s:if test='%{#request[\'MESSAGE_TYPE\']} == "1"'>
<!--			Information-->
		</s:if>
		
		<s:if test='%{#request[\'MESSAGE_TYPE\']} == "2"'>
			Error
		</s:if>
	
	
	<%
		String message = (String)request.getAttribute(GenericConstants.GLOBAL_PLAIN_TEXT_MESSAGE);
		
		String path = (String)request.getAttribute(GenericConstants.DESTINATION_PATH);
		
		String messageType = (String)request.getAttribute(GenericConstants.MESSAGE_TYPE);
	%>
	
	<br/>
	<s:text  name="%{#request[\'GLOBAL_PLAIN_TEXT_MESSAGE\']}"/>
	
	<br/>
	<br/>
	<s:if test='%{#request["MESSAGE_TYPE"] == "1"}'>
		<a href="CandidateAction_showInstructionPage.action"><s:text name="login.redirecttoaapform"/></a>
	</s:if>
	
</div>
</div>
</div>