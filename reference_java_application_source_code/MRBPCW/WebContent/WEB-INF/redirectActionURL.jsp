
<%@page import="com.nseit.generic.util.GenericConstants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%
	String destinationUrl = (String)request.getAttribute("DESTINATION_URL");
	//System.out.println("destinationUrl "+destinationUrl);
	String dataNotFound = (String)request.getAttribute("dataNotFound");
	String regSbmtMsg = (String)request.getAttribute("regSbmtMsg");
	
%>

<html>
  <body onLoad="document.cheatForm.submit()">  
    <form action="<%=destinationUrl%>" method="post" name="cheatForm">    
    <s:token/>
      <input type="hidden" name="dataNotFound" value="<%=dataNotFound%>"/>
       <input type="hidden" name="regSbmtMsg" value="<%=regSbmtMsg%>"/>
    </form>
  </body>
</html>