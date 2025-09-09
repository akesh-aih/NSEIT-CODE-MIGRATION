<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
<meta http-equiv="refresh" content="18; URL="#" />
<script type="text/javascript">
function showIciciBankPage()
{
                    
                    document.getElementById("block9").style.display = "block";
                    document.getElementById("fade").style.display = "block";
                    
                    document.forms['ccForm'].action='PaymentOnlineAction_insertOnlinepaymentDetails.action';
					document.forms['ccForm'].submit();
}

function onlyNumbers(evt) 
{ 
var nbr; 
var nbr = (window.event) ? event.keyCode : evt.which; 
 
if ((nbr >= 48 && nbr <= 57) || nbr == 8)  
{ 
    return true; 
} 
else  
{ 
    return false; 
} 
} 

                
</script>
</head>

<body style="background:url(<%=request.getContextPath()%>/images/icicibank-s2.gif) no-repeat top left; font-family:Arial, Helvetica, sans-serif; font-size:12px;" >
<div id="fade" style="display:none; top:0; left:0; z-index:900; width:100%; height:100%; position:absolute; background:url(images/bg-fade-div.png) repeat top left;"></div>
<div id = 'block9' style=" display:none; top:305px; left:525px; z-index:1000; position:absolute;" align="center">
  				  <img src="images/progress_bar.gif"  alt="GCET " />
  				  <br />Please wait... Your Request is processing...
</div>
<s:form name="ccForm" id="ccForm">
<input type="hidden" name="bankPageType" value="3"/>
<input type="hidden" name="onlinePaymentType" value="1"/>
<input type="hidden" name="amount" value="<%=request.getAttribute("amount")%>"/>
<table width="800" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="622" height="150">&nbsp;</td>
  </tr>
  <tr>
    <td><table width="700" border="0" cellspacing="1" cellpadding="5" style="background:#ded7bd;">
      <tr>
        <td width="195" bgcolor="#efefe0">Pay To</td>
        <td bgcolor="#FFFFFF">Gujarat Technology University</td>
        </tr>
      <tr>
        <td bgcolor="#efefe0">Payment Amount</td>
        <td bgcolor="#FFFFFF"><%=request.getAttribute("amount")%></td>
        <!--<s:textfield  name="textfield2" id="textfield2" type="text" value="%{onlinePaymentBean.amount}"  
			size="35" bgcolor="#FFFFFF"/>
        --></tr>
      <tr>
        <td bgcolor="#efefe0">Payment Remarks</td>
        <td bgcolor="#FFFFFF"><input name="textfield4" type="text" id="textfield4" size="55" /></td>
      </tr>
      <tr>
        <td bgcolor="#efefe0">Enter your Internet Banking Transaction Password </td>
        <td bgcolor="#FFFFFF"><input name="textfield5" type="password" id="textfield5" size="35" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="750" align="left" cellpadding="3" cellspacing="0">
      <tr>
        <td width="286" colspan="2" align="right" style="background-color:#ec870e; color:#FFF; font-family:Arial, Helvetica, sans-serif; font-size:12px; font-weight:bold;" />Terms and Conditions</td>
      </tr>
      <tr>
        <td colspan="2" style="color:#900"><p>I hereby authorize ICICI Bank Ltd to make payment to BILL JUNCTION PAYMENT LIMITED for this transaction</p>
          <p>Note :</p>
          <ol>
            <li> By clicking on the 'Pay' button, You are agreeing to the terms and conditions as given above<br />
              </li>
            <li>After clicking the 'Pay' button, please wait for sometime while we pass on your payment details to the Biller.<br />
              </li>
            <li>Please do not refresh or close the browser window.<br />
              </li>
            <li>Please check the status of the payment with the Merchant after 7 days of the transaction date. In case the Merchant does not update the payment within 7 days, please contact our 24-hour Customer Care for further assistance.</li>
          </ol></td>
      </tr>
      <tr>
        <td colspan="2" align="right">
        <input type="button" name="button2" id="button2" value="Pay" style="background-color:#ec870e; color:#FFF; font-family:Arial, Helvetica, sans-serif; font-size:12px; font-weight:bold;" onclick="showIciciBankPage();"/>
        <input type="button" name="button3" id="button3" value="Cancel" style="background-color:#ec870e; color:#FFF; font-family:Arial, Helvetica, sans-serif; font-size:12px; font-weight:bold;" onclick="window.location='PaymentOnlineAction_showPaymentScreen.action'" />
          &nbsp;&nbsp;&nbsp;
          </td>
      </tr>
    </table></td>
  </tr>
</table>

</s:form>
</body>
</html>
