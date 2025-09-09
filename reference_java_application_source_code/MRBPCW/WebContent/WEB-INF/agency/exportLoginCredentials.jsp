<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>

	<script type="text/javascript">

	function passRequestID(trID){
		var tdID = trID + "0";
		var requestID = $("#"+tdID).html();
		//alert(requestID);
		$("#requestID").val(requestID);
		document.generateLoginCredentials.action = "AgencyAction_exportDataForRequestID.action?";
		document.generateLoginCredentials.submit();
	}	
	</script>

<div class="container">

<s:form action="AgencyAction" id="generateLoginCredentials" name ="generateLoginCredentials">
	<s:hidden id="requestID" name="requestID" value=""></s:hidden>
	<div class="fade" id="pop3"></div>

<div id="dashboard">
<h1 class="pageTitle" title="Dashboard">Export Credentials</h1>
<div class="hr-underline2"></div>

<!-- Box Container Start -->
<div style="display:block; min-height:300px; height:auto;">
<div style="text-align:right; height:35px;">
    <s:submit cssStyle="width:120px" method="exportAllDataToExcel" value="Export All" cssClass="submitBtn button-gradient"></s:submit></div>
   <table cellpadding="0" cellspacing="0" class="table_2">
    <thead>
    <tr>
      <th width="176">Request No.<span class="manadetory-fields"></span></th>
      <th width="194" >No of Candidates</th>
      <th width="185" >Date</th>
      <th width="173" >Time</th>
      <th width="190" >&nbsp;</th>
    </tr>
    </thead>
    <tbody>
    	<%int i = 0; %>
    	<s:iterator value="excelList" status="stat">
    		<tr id="<%=i %>">
    		<%int j=0; %>
    		<s:iterator>
    			
    				<td id="<%=i%><%=j %>"><s:property /></td>
    		<%j++; %>	
    		</s:iterator>
    				<td align="center"><input type="button" id="export<%=i %>" onclick="passRequestID(<%=i %>);" Style="width:60px"  value="Export" Class="submitBtn news-gradient"/></td>
    		</tr>
    		<%i++; %>
    	</s:iterator>
    </tbody>
    
    </table>
  
</div>



<div class="fade" id="block7"></div>

</div>
</s:form>

</div>
