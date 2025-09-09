<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head runat="server">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="CACHE-CONTROL" content="NO-CACHE">
    <meta http-equiv="EXPIRES" content="0">
    <meta http-equiv="PRAGMA" content="NO-CACHE">
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
    <title>Registration Acknowledgement Receipt</title>
    <link href="css/common.css" rel="stylesheet" type="text/css" media="screen"/>
    <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
<script src="js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>

<script type="text/javascript" src="js/login.js"></script>
<script type="text/javascript">


function goToApprovalPage(){
 		document.getElementById("approvalReceiptForm").target = "_parent";
		$("#approvalReceiptForm").attr('action',"CandidateMgmtAction_getEligibleAndNonEligibleCandidateData.action");
		$("#approvalReceiptForm").submit(); 
}

function setReceiptType(type){
	$("#receiptType").val(type);
	document.getElementById("approvalReceiptForm").target = "_blank";
	$('#approvalReceiptForm').attr('action','CandidateAction_printAcknowledgeReceipt.action' );
	$('#approvalReceiptForm').submit();
}

</script>
    
</head>
<body>
<div class="container">





<!-- Navigation Manu Start -->


<div class="main-body">



<div class="fade" id="pop3"></div>



<s:form action = "CandidateAction" id="approvalReceiptForm">
<s:hidden name="userId" id="userId" value="%{userId}"  />
<s:hidden name="receiptType" id = "receiptType"></s:hidden>
<s:hidden name = "regApprovalDate" id = "regApprovalDate" value="%{regApprovalDate}" ></s:hidden>
<s:hidden name = "candidateName" id = "candidateName" value="%{candidateName}"></s:hidden>
<s:hidden name = "candidateAddr1" id = "candidateAddr1" value="%{candidateAddr1}"></s:hidden>
<s:hidden name = "candidateAddr2" id = "candidateAddr2" value="%{candidateAddr2}"></s:hidden>
<s:hidden name = "candidateAddr3" id = "candidateAddr3" value="%{candidateAddr3}"></s:hidden>
<s:hidden name = "candidateAddr4" id = "candidateAddr4" value="%{candidateAddr4}"></s:hidden>
<s:hidden name = "helpCenterAddr" id = "helpCenterAddr" value="%{helpCenterAddr}"></s:hidden>
<s:hidden name = "helpCenterDesc" id = "helpCenterDesc" value="%{helpCenterDesc}"></s:hidden>
<s:hidden name = "categoryVal" id = "categoryVal" value="%{categoryVal}"></s:hidden>
<s:hidden name = "disciplinName" id = "disciplinName" value="%{disciplinName}"></s:hidden>

<s:hidden name = "commCity" id = "commCity" value="%{commCity}"></s:hidden>

<s:hidden name = "commDistrict" id = "commDistrict" value="%{commDistrict}"></s:hidden>
<s:hidden name = "commState" id = "commState" value="%{commState}"></s:hidden>
<s:hidden name = "commCountry" id = "commCountry" value="%{commCountry}"></s:hidden>
<s:hidden name = "commPinCode" id = "commPinCode" value="%{commPinCode}"></s:hidden>
            

<div id="dashboard">

<h1 class="pageTitle" title="Registration Acknowledgement Receipt">Registration Acknowledgement Receipt</h1>
<div class="hr-underline2"></div>

<!-- Box Container Start -->
<div style="display:block; min-height:300px; height:auto;">
  <table width="700" align="center" cellpadding="7" style="border:#333 3px double; line-height:140%;" >
    <tr>
      <td align="center" ><div class="lighttext" style="text-align:right; height:25px;">HELP CENTER COPY</div>       
          <strong>Registration Acknowledgement Receipt</strong>
        </td>
      </tr>
    <tr>
      <td ><table width="100%" border="0" cellspacing="0" cellpadding="7">
        <tr >
          <td width="13%" style="border-top:#666 1px solid; border-bottom:#666 1px solid;"><strong>Unique ID No <br />
            Current Date</strong></td>
          <td colspan="2" style="border-top:#666 1px solid; border-bottom:#666 1px solid;"><strong>: </strong>&nbsp;<s:property value="userId"/><br />
            <strong>:</strong>&nbsp;<s:property value="newDate"/> </td>
        </tr>
        <tr>
          <td colspan="2" valign="top"><strong>Verified On : <s:property value="regApprovalDate"/><br />
            Dear <s:property value="candidateName"/></strong><br />
            <strong>Address : </strong>
            <s:property value="candidateAddr1"/><br />
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="candidateAddr2"/><br />
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <s:property value="candidateAddr3"/><br />
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <s:property value="candidateAddr4"/><br />
            <strong>City / Town : </strong><s:property value="commCity"/>
            <strong>State / Union Territory : </strong><s:property value="commState"/><br />
            <strong>Country     : </strong><s:property value="commCountry"/>
            <strong>Pin Code    : </strong><s:property value="commPinCode"/><br /><br />
            
           
            </strong><s:property value="helpCenterDesc"/>  <s:property value="helpCenterAddr"/> </td>
          <td width="30%" valign="top"><strong>User Id &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;</strong> <s:property value="userId"/><br />
            <strong>Course&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : &nbsp;</strong><s:property value="disciplinName"/><br />
            <strong>Category&nbsp;&nbsp; :</strong> &nbsp;<s:property value="categoryVal"/></td>
        </tr>
        <tr>
          <td height="50" colspan="2" align="center" valign="bottom">&nbsp;</td>
          <td align="center" valign="bottom">&nbsp;</td>
          </tr>
        <tr>
          <td colspan="2"><span style="border-top:#999 1px solid;">Signature of Candidate</span></td>
          <td align="center"><span style="border-top:#999 1px solid;">Signature of Co-ordinator</span></td>
          </tr>
        <tr>
          <td colspan="3" valign="bottom"><strong>Note: Registration Slip to be signed by the Candidate as  well as the Help Center Co-ordinator. </strong>            <label for="textfield"></label></td>
          </tr>
        <tr>
          <td colspan="3" align="right">
        <!--   <s:submit title="Submit" cssClass="submitBtn button-gradient" value="Print" method = "printAcknowledgeReceipt" onclick="setReceiptType('CANDIDATE COPY');"></s:submit> -->
          
		<input type='button' id="HelpCentreSubmit" Class="submitBtn button-gradient" value = 'Print' onclick="setReceiptType('HELP CENTRE COPY');"/>
		  
          </td>
        </tr>
        </table></td>
    </tr>
  </table>
  <br />
  
  
  
<br />
  <table width="700" align="center" cellpadding="7" style="border:#333 3px double; line-height:140%;" >
    <tr>
      <td align="center" ><div class="lighttext" style="text-align:right; height:25px;">CANDIDATE COPY</div>
           <strong>Registration Acknowledgement Receipt</strong></td>
    </tr>
    <tr>
      <td ><table width="100%" border="0" cellspacing="0" cellpadding="7">
        <tr >
          <td width="13%" style="border-top:#666 1px solid; border-bottom:#666 1px solid;"><strong>Unique ID No <br />
            Current Date</strong></td>
          <td colspan="2" style="border-top:#666 1px solid; border-bottom:#666 1px solid;"><strong>: </strong>&nbsp;<s:property value="userId"/><br />
            <strong>:</strong>&nbsp; <s:property value="newDate"/> </td>
        </tr>
        <tr>	
          <td colspan="2" valign="top"><strong>Verified On : <s:property value="regApprovalDate"/><br />
            Dear <s:property value="candidateName"/></strong><br />
            <strong>Address : </strong>
            <s:property value="candidateAddr1"/><br />
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="candidateAddr2"/><br />
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <s:property value="candidateAddr3"/><br />
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <s:property value="candidateAddr4"/><br />
            <strong>City / Town : </strong><s:property value="commCity"/>
            <strong>State / Union Territory : </strong><s:property value="commState"/><br />
            <strong>Country     : </strong><s:property value="commCountry"/>
            <strong>Pin Code    : </strong><s:property value="commPinCode"/><br /><br />
            
            
             </strong><s:property value="helpCenterDesc"/>  <s:property value="helpCenterAddr"/> </td>
          <td width="30%" valign="top"><strong>User Id &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;</strong> <s:property value="userId"/><br />
            <strong>Course&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : &nbsp;</strong><s:property value="disciplinName"/><br />
            <strong>Category&nbsp;&nbsp; :</strong> &nbsp;<s:property value="categoryVal"/></td>
        </tr>
        <tr>
          <td height="50" colspan="2" align="center" valign="bottom">&nbsp;</td>
          <td align="center" valign="bottom">&nbsp;</td>
        </tr>
        <tr>
          <td colspan="2"><span style="border-top:#999 1px solid;">Signature of Candidate</span></td>
          <td align="center"><span style="border-top:#999 1px solid;">Signature of Co-ordinator</span></td>
        </tr>
        <tr>
          <td colspan="3" valign="bottom"><strong>Note: Registration Slip to be signed by the Candidate as  well as the Help Center Co-ordinator. </strong>
            <label for="textfield2"></label></td>
        </tr>
        <tr>
          <td colspan="3" align="right">
          
          <%--<s:submit title="Submit" id="CandidateSubmit" cssClass="submitBtn button-gradient" value="Print" method = "printAcknowledgeReceipt" onclick="setReceiptType('CANDIDATE COPY');"></s:submit>
          --%><input type='button' id="CandidateSubmit" class="submitBtn button-gradient" value = 'Print' onclick="setReceiptType('CANDIDATE COPY');"/>
          </td>
        </tr>
      </table></td>
    </tr>
  </table>
  
  <table align="center">
  		<tr>
  				<td>
<!--  						<input type = "button" value = "Back" onclick="goToApprovalPage()"  class="submitBtn button-gradient"/>-->
							<input type = "button" value = "Close" onclick="window.close()"  class="submitBtn button-gradient"/>
  				</td>
  		</tr>
  
  </table>
  
  
</div>
<br />


</div>
</s:form>
</div>
</body>
</html>
