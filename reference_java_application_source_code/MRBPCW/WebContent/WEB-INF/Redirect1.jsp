<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<%
	session.setAttribute(GenericConstants.GLOBAL_PLAIN_TEXT_MESSAGE, request.getAttribute(GenericConstants.GLOBAL_PLAIN_TEXT_MESSAGE));
	session.setAttribute(GenericConstants.DESTINATION_PATH, request.getAttribute(GenericConstants.DESTINATION_PATH));
	session.setAttribute(GenericConstants.MESSAGE_TYPE, request.getAttribute(GenericConstants.MESSAGE_TYPE));
%>

<html>
	<script src="js/jquery-3.6.3.min.js"></script>
	<script>
		function submit_onload()
		{
			$("#formRedirect").submit();
		}
	</script>
	<body onload="submit_onload();">
		<form id="formRedirect" action="Redirect2.jsp">
			<b>Please Wait...</b>
		</form>
	</body>
</html>