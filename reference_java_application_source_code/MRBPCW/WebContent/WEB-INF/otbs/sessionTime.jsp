
<%@page import="com.nseit.generic.util.GenericConstants"%>
<%@page import="com.nseit.generic.models.Users"%>

<%
Users sessionUser=(Users)  session.getAttribute(GenericConstants.SESSION_USER);
String responseText= "";
if(sessionUser == null)
{
	responseText = "0";
}
else
{
	responseText = "1";
}
response.resetBuffer();
	out.println(responseText);
	response.flushBuffer();
%>
