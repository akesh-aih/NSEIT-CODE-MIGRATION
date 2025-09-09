<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<script type="text/javascript" src="js/AES.js"></script>
<script type="text/javascript">
</script>
    <title>My JSP 'paymentReconcCandidateWiseReport.jsp' starting page</title>
    
  </head>
  <s:form action="ReportAction" name="paymentRecocileCand" id="paymentRecocileCand">
  <input type="hidden" name="menuKey" value='<%=request.getAttribute("menuKey")%>'/>
  <div style="overflow: auto; ">
  <table cellspacing="0" border="1" width="100%" class="table_2" bordercolor="#CCC">
				    <thead>
<tr>
<th style="text-align: center;">Sr.No.</th>
<th style="text-align: center;">Registration ID</th>
<th style="text-align: center;">Discipline</th>
<th style="text-align: center;">Candidate Name</th>
<th style="text-align: center;">Category</th>
<th style="text-align: center;">Mobile Number</th>
<th style="text-align: center;">Journal No.</th>
<th style="text-align: center;">Challan Date</th>
<th style="text-align: center;">Branch Name</th>
<th style="text-align: center;">Branch Code</th>
<th style="text-align: center;">Candidate Fees</th>
<th style="text-align: center;">Applicable Fees</th>
<th style="text-align: center;">Bank Amount</th>
<th style="text-align: center;">Status</th>
</tr>
</thead>
<tbody>
<s:if test="%{paymentReconciliationReportDataWithDetail!=null}">
		<s:iterator value="paymentReconciliationReportDataWithDetail" var="bean" status="status">
		<tr>
		<td>
			<s:property value="#status.index + 1"/>
		</td>
		<td>
			<s:property value="userID"/>
		</td>
		<td align="center">
			<s:property value="discipline"/>
		</td>
		<td>
			<s:property value="candidateName"/>
		</td>
		<td>
			<s:property value="category"/>
		</td>
		<td>
			<s:property value="mobileNo"/>
		</td>
		<td>
			<s:property value="journalNo"/>
		</td>
		<td>
			<s:property value="challanDate"/>
		</td>
		<td>
			<s:property value="branchName"/>
		</td>
		<td>
			<s:property value="branchCode"/>
		</td>
		<td>
			<s:property value="candidateFees"/>
		</td>
		<td>
			<s:property value="applicableFeesAmt"/>
		</td>
		<td>
			<s:property value="bankAmt"/>
		</td>
		<td>
			<s:property value="status"/>
		</td>
		</tr>
		</s:iterator>
</s:if> 
<s:else>
	<tr>
		<td colspan="14" align="center">
			<div>
				<b>No record Found</b>
			</div>
		</td>
</tr>
</s:else> 
</tbody>
  		</table>
</div>
  </s:form>
</html>
