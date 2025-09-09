
<%@page import="com.nseit.generic.util.GenericConstants"%>
<%
	response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	response.setDateHeader("Expires", 0); // Proxies.
	
	response.setContentType("text/plain");
	String message = (String)request.getAttribute(GenericConstants.GLOBAL_PLAIN_TEXT_MESSAGE);
	out.print(message);
	response.flushBuffer();
	return;
%>