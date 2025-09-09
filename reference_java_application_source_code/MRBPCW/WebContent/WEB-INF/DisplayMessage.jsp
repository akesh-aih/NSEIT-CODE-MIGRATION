
<%@page import="com.nseit.generic.util.GenericConstants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%
	String message = (String)session.getAttribute(GenericConstants.GLOBAL_PLAIN_TEXT_MESSAGE);
	String path = (String)session.getAttribute(GenericConstants.DESTINATION_PATH);
	String messageType = (String)session.getAttribute(GenericConstants.MESSAGE_TYPE);
%>
<%--
	function goToDestination()
	{
		window.location = $("#destinationURL").val();
	}
	
--%>
<html>
  <body onLoad="submitForm()">  
    <form action="<%=path%>" method="post" id="cheatForm" name="cheatForm">  
      <s:token />
    </form>
  
<%if(message!=null){%>
<script type="text/javascript">
var mesg = '<%=message%>';
alert(mesg);
submitForm();
function submitForm(){	
	$("#cheatForm").submit();
}
</script>	
<%}%>
</body>
</html>
<%--<div id="dashboard" style="min-height:320px;">

<h1 class="pageTitle" title="Book a Seat">Message</h1>
<div class="hr-underline2"></div>
<div >
	<input type="hidden" id="destinationURL" value="<s:text name="%{#session[\'DESTINATION_PATH\']}" />" />
	<h1 class="newsTitle">
		<s:if test='#session["MESSAGE_TYPE"] == "1"'>
			Information
		</s:if>
		<s:elseif test='#session["MESSAGE_TYPE"] == "2"'>
			Error
		</s:elseif>
	</h1>
	
	<br/>
	<br/>
	
	<s:text  name="%{#session['GLOBAL_PLAIN_TEXT_MESSAGE']}"/>
	<br/>
	<br/>
	<s:submit type="button" value="Back" cssClass="submitBtn button-gradient" onclick="goToDestination()"></s:submit>
</div>
</div>
--%>

