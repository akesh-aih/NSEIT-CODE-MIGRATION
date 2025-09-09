<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<script src="js/jquery-3.6.3.min.js"></script>
	<script>
		function submit_onload()
		{
			$("#formRedirect").submit();
		}
	</script>
	<body onload="submit_onload();" >
		<form id="formRedirect" action="RedirectFinal.action">
			<b>Please Wait...</b>
		</form>
	</body>
</html>