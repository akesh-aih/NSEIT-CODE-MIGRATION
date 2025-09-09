<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<script type="text/javascript">

function printreceipt(){
	//$('#paymentreceipt').attr('action','CandidateAction_preparePDFForPaymentReceipt.action?' );
	window.open("CandidateAction_preparePDFForPaymentReceipt.action");
	//$('#paymentreceipt').submit();
	
}

</script>
    
<div class="container">

<div id="dashboard" style="display:block; min-height:300px; height:auto;">

<h1 class="pageTitle" title="Payment Receipt">Payment Receipt</h1>
<div class="hr-underline2"></div>
<s:form id="paymentreceipt" name="paymentreceipt">
<a href="javascript:void(0);" onclick="printreceipt();" title="Payment Receipt"><s:text name="Click Here to Download Payment Receipt"/></a>
	
</s:form>
</div>
</div>
