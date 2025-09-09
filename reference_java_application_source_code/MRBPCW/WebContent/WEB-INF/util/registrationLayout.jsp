<%@page import="com.nseit.generic.util.GenericConstants"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" >
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
	<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
	<script type='text/javascript'>
		
			var dataFormat = "<%= GenericConstants.DATE_FORMAT_DEFAULT_JAVASCRIPT %>";
			var range = "c-60";
			var maxDateDOB = "-1D";
			var maxDateOther = "";
	</script>
	</head>
	<body class="loginBg registrationBg">
	<tiles:importAttribute ignore="true" name="menuKey" />
	<tiles:importAttribute ignore="true" name="title" />


			<!-- Header Start -->
			<tiles:insertAttribute name="header">
				<tiles:putAttribute name="menuKey" value="${menuKey}" />
				<tiles:putAttribute name="title" value="${title}" />
			</tiles:insertAttribute>
			<!-- Header End -->

			<tiles:insertAttribute name="body" />

			<tiles:insertAttribute name="footer" />

			<jsp:include page="../changePassword.jsp"></jsp:include>

		<div class="fade" id="block7"></div>
	</body>
</html>