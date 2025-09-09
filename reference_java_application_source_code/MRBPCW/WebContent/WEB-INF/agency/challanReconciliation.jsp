<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="container">
<script type="text/javascript">

	function hideGuideLines(){
	HideAll();
		$("#guidelines").hide();
		$("#pop3").hide();
		
	}
	function frm_onsubmit(){
		var uploadType = $("#uploadType").val();
		var uploadFile = $("#uploadFile").val();
		var message = "";

		if(uploadFile == ""){
			message = message + "Please select a File to Upload."+"##";
		}else{
			var extension = uploadFile.substr( (uploadFile.lastIndexOf('.') +1) );
			extension = extension.toLowerCase();
			if(extension!="csv"){
				message = message + "Please select a CSV file for upload."+"##";
			}
		}
		
		if(message != ""){
			var ulID = "error-ul1";
			var divID = "error-massage1";
			createErrorList(message, ulID, divID);
			return false;
		}else{
			return true;
		}
	}

	function showDiv(){
		$("#unscrecDataDiv").show();
		$("#unscrecDataTable").show();
		$("#showHideData").html("<a href='#' onclick='hideDiv()'>Hide Details</a>");
	}
	function hideDiv(){
		$("#unscrecDataDiv").hide();
		$("#unscrecDataTable").hide();
		$("#showHideData").html("<a href='#' onclick='showDiv()'>Show Details</a>");
	}
</script>

<s:form action="CandidateMgmtAction_uploadBankChallanFile.action" enctype="multipart/form-data" method="post" id="challanReconcile">
<div class="fade" id="pop3"></div>
<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
<div id="dashboard" style="display:block; min-height:300px; height:auto;">
<h1 class="pageTitle" title="Challan Reconciliation"><s:text name="reconciliation.challan"/></h1>
<div class="hr-underline2"></div>
<s:if test="hasActionMessages()">
   <div id="error-massage1">
	<div class="error-massage-text">
		<ul style="margin:0; margin-left:-20px; padding:0;" id="error-ul1">
			<s:actionmessage cssStyle="color:red" escape="false"/>	
		</ul>
	</div>
	</div>
</s:if>
<br/>
<!-- Box Container Start -->
<div style="display:block; min-height:300px; height:auto;" >
	<table class="contenttable">
		<tr>
		<td><s:text name="reconciliation.challanStatement"/></td>
		<td>
			<s:file  name="fileUpload" id = "uploadFile"></s:file>
			<s:submit type="submit" value="Reconcile" cssClass="submitBtn button-gradient"/>
		</td>
		<td>
			<a href="TemplateDownloadAction_downloadTemplate.action?templateDownloadType=TEMPLATE_CHALLAN_RECONCILIATION" class="LinkAlignMiddle"><s:text name="reconciliation.downloadChallanFormat"/></a>
		</td>
		<td>
<!--			<a href="#" onclick="ShowBlock('guidelines')"><s:text name="reconciliation.guidelinesForChallan"/></a>-->
		</td>
		</tr>
		 
		 <tr>
		 <s:if test='%{flag == "true"}'>
	    	<table width="100%">
	    		<tr><td colspan="2"><div class="hr-underline2"></div></td></tr>
	    		<tr>
			    	<td style="background:#56b4a2; color:#fff; padding:5px;"><strong><s:text name="reconciliation.successful"/></strong></td>
			    	<td style="background:#56b4a2; color:#fff; padding:5px;"><strong><s:text name="reconciliation.unsuccessful"/></strong></td>
			    </tr>
			    <tr>
			    	<td style="background:#effbf9;  padding:5px;" width="50%">
			    		<div style="height:50px; overflow:auto;"><s:text name="reconciliation.successfulCount"/> :: <s:property value="successfulReconcileCount"/></br>
			    		</div></td>
			    	<td style="background:#effbf9;  padding:5px;" width="50%">
			    		<div style="height:50px; overflow:auto;"><s:text name="reconciliation.unsuccessfulCount"/> :: 
			    			<s:property value="unSuccessfulReconcileCount"/>&nbsp;&nbsp;&nbsp;<!-- <span id="showHideData"><a href="#" onclick="showDiv()"><s:text name="reconciliation.showDetails"/></a></span> -->
			    		</div></td>
<%--			    		<s:property value="message" escape="false"/>--%>
			    </tr>
			    <tr>
				    <td colspan="2" style="background:#effbf9;" >
				    <div style="height:50px; overflow:auto;">
				    <s:property value="challanSummary" escape="false"/>
				    </div>	
				    </td>
				</tr>
			    <tr>
			    	<td colspan="2" align="center"><strong><s:text name="reconciliation.totTimeOnExe"/> :: </strong><s:property value="executionTime"/></td>
			    </tr>
	    		</table>
	     </s:if>		
   		    </tr>
    
		<tr>
		<td>
		<div id="unscrecDataDiv" style="display:none;">
		<strong><s:text name="reconciliation.unsuccessfulData"/></strong>
		<table cellspacing="0" border="1" width="100%" class="table_2" bordercolor="#CCC" id="unscrecDataTable" style="display:none"><br/><br/>
		<thead>
		    <tr>
		    <th style="width: 67px;">User ID</th>
		    <th style="width: 67px;">Discipline</th>
		    <th style="width: 100px;">Candidate Name</th>
		    <th style="width: 65px;">Contact Number</th>
		    <th style="width: 61px;">Journal No</th>
		    <th style="width: 78px;">Challan Date</th>
		    <th style="width: 78px;">Branch Name</th>
		    <th style="width: 78px;">Branch Code</th>
		    <th style="width: 78px;">Applicable Fee</th>
		    <th style="width: 78px;">Bank Amount</th>
		     <th style="width: 78px;">Difference Amount</th>
		    </tr>
		</thead>
		<tbody>
			<s:iterator value="unSuccessfulReconcileList" status="stat" var="currentObject">
			<tr>
				<td align="center" style="width: 67px;">
					<s:property value="OBCD_USER_ID"/>
				</td>
				<td align="center" style="width: 67px;">
					<s:property value="discipline"/>
				</td>
				<td align="center" style="width: 37px;">
					<s:property value="OBCD_USER_NAME"/>
				</td>
				<td align="center" style="width: 37px;">
					<s:property value="mobile_number"/>
				</td>
				<td align="center" style="width: 37px;">
					<s:property value="OBCD_CHALLAN_NO"/>
				</td>
				<td align="center" style="width: 100px;">
					<s:property value="challanDate"/>
				</td>
				<td align="center" style="width: 37px;">
					<s:property value="OBCD_BRANCH_NAME"/>
				</td>
				<td align="center" style="width: 37px;">
					<s:property value="OBCD_BRANCH_CODE"/>
				</td>
				<td align="center" style="width: 37px;">
					<s:property value="applicableFees"/>
				</td>
				<td align="center" style="width: 37px;">
					<s:property value="OBCD_FEES"/>
				</td>
				<td align="center" style="width: 50px;">
					<s:property value="differenceAmount"/>
				</td>
			 </tr>
			 </s:iterator>
			</tbody>
		</table>
		</div>
		 </td></tr>
	</table>    
</div>
</div>
<div id="guidelines" style="display:none" class="change-pass box-gradient" >
<table align="center">
<tr>
	<td align="center">
	<h1 class="pageTitle" title="Guidelines For Challan Reconciliation"><s:text name="reconciliation.guidelinesForChallan"/></h1>
		<div class="hr-underline2"></div>
	</td>
</tr>
<tr>
	<td align="center">
		<div style = "text-align:left">
			<s:property value="guidelinesMsg" escape="false"/>
	</div><br/>	
	</td>
</tr>

<tr>
	<td align="center">
		<div>
		<input type="button" value="Close" onclick="hideGuideLines()" class="submitBtn button-gradient" >
	</div>	
	</td>
</tr>

<tr>
	<td>
		&nbsp;
	</td>
</tr>
</table>
</div>
<s:token/>
</s:form>
</div>