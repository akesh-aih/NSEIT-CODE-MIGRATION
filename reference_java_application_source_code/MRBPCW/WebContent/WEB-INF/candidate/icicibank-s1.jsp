<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
<script type="text/javascript">
function showIciciBankPage()
                {
                    
                    //alert('Hi');
                    
                    var x=document.forms["ccForm"]["textfield"].value;
 					if (x==null || x=="")
   					{
					   alert("Please Enter User ID.");
					   return false;
					}
   
					 var y=document.forms["ccForm"]["textfield2"].value;
					 if (y==null || y=="")
					 {
					 	 alert("Please Enter Your Password.");
					   return false;
					 }
                    	document.forms['ccForm'].action='PaymentOnlineAction_doOnlinePayment.action';
						document.forms['ccForm'].submit();
                }
</script>
</head>

<body style="background:url(<%=request.getContextPath()%>/images/icicibank-s1.gif) no-repeat top left; font-family:Arial, Helvetica, sans-serif; font-size:12px;" >
<FORM name="ccForm" id="ccForm">
<input type="hidden" name="amount" value="<%=request.getAttribute("amount")%>"/>
<input type="hidden" name="bankPageType" value="2"/>
<table width="800" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="239" height="200">&nbsp;</td>
    <td width="561">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><table width="300" border="0" cellspacing="1" cellpadding="3" style="background:#ded7bd;">
      <tr>
        <td width="135" bgcolor="#efefe0">Your User ID</td>
        <td width="150" bgcolor="#FFFFFF"><input name="textfield" type="text" id="textfield" size="25" onChange="validateForm(this.value)"/></td>
      </tr>
      <tr>
        <td bgcolor="#efefe0">Password</td>
        <td bgcolor="#FFFFFF"><label for="textfield2"></label>
          <input name="textfield2" type="password" id="textfield2" size="25" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><table width="300" align="center" cellpadding="3" cellspacing="0">
      <tr>
        <td width="286" colspan="2"><input type="button" name="button" id="button" value="Payment Login" style="background-color:#ec870e; color:#FFF; font-family:Arial, Helvetica, sans-serif; font-size:12px; font-weight:bold;" onclick="showIciciBankPage();"/></td>
        
      </tr>
    </table></td>
  </tr>
</table>

</FORM>
</body>
</html>
