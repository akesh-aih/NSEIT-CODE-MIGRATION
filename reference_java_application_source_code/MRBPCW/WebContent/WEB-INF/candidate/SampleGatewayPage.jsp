<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<title>Sample page</title>
	<body>
	
		<h1>Dummy Registration Payment Gateway</h1>
		<s:form id="sample" name="sample" action="PaymentAction_insertpagedetails.action" method="post">
		
			<table class="apptable">
				<tr>
					<th>Parameter</th>
					<th>Description</th>
					<th>Value</th>
				</tr>
				<tr>
					<td>CRN</td>
					<td>Currency</td>
					<td>INR</td>
				</tr>
				<tr>
					<td>PRN</td>
					<td>Unique Id supplied by NSEIT to ITB</td>
					<td><%=request.getParameter("PRN")%></td>
				</tr>
				<tr>
					<td>AMT</td>
					<td>Amount to be received from candidate</td>
					<td><%=request.getParameter("AMT")%></td>
				</tr>
				<tr>
					<td>BID</td>
					<td><%="T-"+request.getParameter("PRN")%></td>
				</tr>
			</table>
			<input type="hidden" name="CRN" value="INR"/>
			<input type="hidden" name="PRN" value="<%=request.getParameter("PRN")%>"/>
			<input type="hidden" name="ITC" value=""/>
			<input type="hidden" name="AMT" value='<%=request.getParameter("AMT")%>'/>
			Bank : <input type="text" name="Bank" value="ICICI Bank Ltd"/><br/>
			<input type="hidden" name="BID" value="<%="T-"+request.getParameter("PRN")%>"/>
			<input type="hidden" name="EEDID" value="<%=request.getParameter("PRN")%>"/>
			Successful : <select name="PAID">
				<option value="Y">Yes</option>
				<option value="N">No</option>
			</select> <br/>
<%--			<input type="text" name="PAID"/>--%>
		<s:submit name="submitpage" value="Submit" action="PaymentAction_insertpagedetails"></s:submit>
	
		</s:form>
		
	</body>
</html>