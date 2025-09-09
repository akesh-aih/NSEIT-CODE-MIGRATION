<%
//	response.setHeader("Pragma","No-cache");
//	response.setHeader("Cache-Control","no-cache");
//	response.setDateHeader("Expires",0);
	
%>

<% 
response.addHeader("Cache-Control", "max-age = 0, s-maxage = 0,must-revalidate, proxy-revalidate, no-cache, no-store ");
response.addHeader("Pragma", "no-cache");
response.setDateHeader("Expires",0);
%>  
