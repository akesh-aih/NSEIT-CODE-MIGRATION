<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>:: POWERED BY TECHPROCESS SOLUTIONS LTD [PAYMENT GATEWAY] ::</title>
<link href="css/payment.css" rel="stylesheet" />
<script type="text/javascript" src="js/app2.js"> </script>
<script type="text/javascript" src="js/RGB2Hex.js"> </script>
<script language ='javascript'>
function CCPopUp(SEALURL, cId)
{
    window.open(""+SEALURL+"index.php?page=showCert&cId="+cId+"", "win",'toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=0,resizable=0,width=700,height=585');
    self.name = "mainWin";
}

function ajaxFileUpload(fileToUpload)
{
	$("#doOnlinePayment").ajaxForm({
		beforeSend: function() {
		$('#block9').show();;
	},
		target: '#divImageUploadResponse',
		
		complete: function() {
		$('#block9').hide();
		}
	}).submit();
}

function getCreditCardType(type) 
{

	//alert('credit card type is ' + type);
	if (type == 1)
	{
	   //window.open('http://cool.com')
	   document.write("select is" + creditCardType);
	}

	document.forms['doOnlinePaymentViaCreditCard'].submit();

}

</script>

<label id="divImageUploadResponse" style="display: none;"></label>
<script type="text/javascript">
            <!--
            function MM_swapImgRestore() { //v3.0
                var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
            }
            function MM_preloadImages() { //v3.0
                var d=document; if(d.Images){ if(!d.MM_p) d.MM_p=new Array();
                    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
                        if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
                }

                function MM_findObj(n, d) { //v4.01
                    var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
                        d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
                    if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
                    for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
                    if(!x && d.getElementById) x=d.getElementById(n); return x;
                }

                function MM_swapImage() { //v3.0
                    var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
                        if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
                }
                function validation(str)
                {
                    var bank_code;
                    var bankSize = '160';
                    for (var i=0; i < document.chk.RadioGroup1.length; i++)
                    {
                        if (document.chk.RadioGroup1[i].checked)
                        {
                            bank_code = document.chk.RadioGroup1[i].value;
                        }
                    }
                    if(bank_code == '10')
                    {
                    	alert('bank code is ' + bank_code);
                        document.chk.abc.value = bank_code;
                        document.chk.SRCSITEID.value = 'L1265';
                        document.chk.action="candidate/icicibank-s1.jsp";
                        document.chk.method="POST";
                        document.chk.submit();
                        return true;
                    }
                    else
                    {
                        alert("Please select a bank");
                        return false;
                    }
                }

                function cancelTran()
                {
                    document.chk.xyz.value = "zzz";
                    document.chk.SRCSITEID.value = 'L1265';
                    document.chk.action="PaymentGateway.jsp";
                    document.chk.method="POST";
                    document.chk.submit();
                    return true;
                }

                function openWindow()
                {
                    window.open('http://www.techprocess.co.in:8080','new','top=20,left=50,width=1024,height=768,toolbar=yes, location=yes,directories=yes,status=yes,menubar=yes,scrollbars=yes,copyhistory=yes,resizable=yes')
                }
                //-->
        </script>
<script language="JavaScript">
                javascript:window.history.forward(1);
        </script>
        
<script type="text/javascript">
function setBankPageType(){
	//alert('Hi');
	document.forms['doOnlinePayment'].submit();
}
</script>

<SCRIPT language="JavaScript" src="js/security.js"></SCRIPT>
<style type="text/css">
<!--
body {
	background-color: #ffffff;
}
.header_banner {
	background-image: url(../images/top_header_white.jpg);
	width: 1006px;
}
.disclaimer_banner {
	background-image: url(images/disclaimer_white.jpg);
	background-repeat: no-repeat;
	background-position: 0px 0px;
}
.logo_Left {
	background-image: url(images/top_banner_center_white.gif);
	background-repeat: repeat-x;
	background-position: 0px 0px;
	text-align:#000000
}
.middle_space {
	background-image: url(images/top_banner_center_white.gif);
	background-repeat: no-repeat;
	background-position: 0px 0px;
}
.logo_Right {
	background-image: url(images/top_banner_right_white.gif);
	background-repeat: no-repeat;
	background-position: 0px 0px;
}
.h3pay {
	background-color: #ffffff;
	background-image: url(images/tab_head.gif);
	background-repeat: no-repeat;
	background-position: left top;
}
.h3type {
	background-image: url(images/tab_head_r.gif);
	background-repeat: no-repeat;
	background-position: 0px 0px;
	background-color: #ffffff;
}
.header_font_color {
	color: #000000;
	background-color: #ffffff
}
#footer a {
	color: #000000;
}
-->
</style>
</head>
<body onload="MM_preloadImages('../images/cont_payment_2.gif')" >
  <table width="100%" border="0" cellspacing="0"  align="center"  cellpadding="0" id="main">
    <tr>
      <td  class="header_bg"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
          <tr bgcolor="#ffffff">
            <td width="230" height="88" class="logo_Left">&nbsp;</td>
            <td width="532" height="88" class="middle_space">&nbsp;</td>
            <td width="233" height="88" class="logo_Right">&nbsp;</td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td height="30" class="header_banner">&nbsp;</td>
    </tr>
    <tr>
    
    <td valign="top">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" id="content">
      <tr>
        <td height="0" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="brd3">
            <tr>
              <td valign="top" height="42" class="h3pay"><h3>Pay By</h3></td>
              <td valign="top" height="42" class="h3type"><h3 id="title">Welcome!</h3></td>
            </tr>
            <tr>
              <td valign="top"><div id="l_side_links"> 
              <a href="javascript:;" id="a1" onclick="showme(1)" onmouseover="MouseOver(1)" onmouseout="MouseOut(1)">Credit Card</a></div></td>
              <td valign="top"><div id="r_side_link">
                  <div id="n0">
                    <p>TechPro Instant Pay shall assist you in facilitating <br />
                      your online transaction.</p>
                    <br />
                    <p>Please select your mode of payment.</p>
                  </div>
                  <s:form name='doOnlinePaymentViaCreditCard' id ='doOnlinePaymentViaCreditCard' action="PaymentOnlineAction_doOnlinePaymentViaCreditCard.action"> 
            		<input type="hidden" name="creditCardTypes" value="O"/>
                  <div id="n1">
                    <h1>Please Select Your Credit Card</h1>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td width="20%">
                          <%--<input type="radio" name="creditCardType" value="1" />
                          Indian Overseas Bank Debit Card<br>
                          --%>
                          <input type="radio" name="creditCardType" value="2" checked="checked"/>
                          VISA<br>
                          <input type="radio" name="creditCardType" value="3" />
                          VISA MASTER MAESTRO CARD<br></td>
                      </tr>
                    </table>
                    <%--<img src="images/cont_payment_11.gif" alt="Continue to Payment" name="c_pay" width="139" height="23" border="0" id="c_pay" onclick="return validation('C')" style="cursor:pointer;"/> 
                    --%>
                    <img src="images/cont_payment_11.gif" alt="Continue to Payment" name="c_pay" width="139" height="23" border="0" id="c_pay" onclick="getCreditCardType('creditCardType');" style="cursor:pointer;"/>
                    &nbsp;&nbsp;
                    <img src="images/cancel2.gif" alt="Cancel Payment" name="cancel_1" width="90" height="23" border="0" id="cancel_1" style="cursor:pointer;" onclick="window.location='PaymentOnlineAction_showPaymentScreen.action'"/> 
                    </div>
                    </s:form>
                    <s:form name='doOnlinePayment' id ='doOnlinePayment' action="PaymentOnlineAction_doOnlinePayment.action"> 
            		<input type="hidden" name="bankPageType" value="1"/>
                  <div id="n2">
                    <h1>Please Select Your Bank Account </h1>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="20%"><input type="radio" name="bankPageType"/>
                          ICICI Bank<br>
                          <input type="radio" name="bankPageType"/>
                          Vijaya Bank
                          </td>
                      </tr>
                    </table>
                    <%--<img src="images/cont_payment_11.gif" alt="Continue to Payment" name="c_pay2" width="139" height="23" border="0" id="c_pay2" onclick="return validation('N')" style="cursor:pointer;"/> 
                    --%>
                    <img src="images/cont_payment_11.gif" alt="Continue to Payment" name="c_pay2" width="139" height="23" border="0" id="c_pay2" onclick="setBankPageType();" style="cursor:pointer;"/>
                    &nbsp;&nbsp;
                    <img src="images/cancel2.gif" alt="Cancel Payment" name="cancel_2" width="90" height="23" border="0" id="cancel_2" style="cursor:pointer;" onclick="window.location='PaymentOnlineAction_showPaymentScreen.action'"/> 
                    </div>
                  </s:form>
                  <div id = 'block9' style="display: none; padding-left:350px;">
  				  <img src="images/Loading.gif" width="66" height="78" alt="GCET " />
  	</div>
                </div></td>
            </tr>
            
          </table></td>
      </tr>
    </table>
    <table cellpadding="0" cellspacing="0" id="footer">
      <tr>
        <td height="33" class="disclaimer_banner" colspan="3">&nbsp;</td>
      </td>
      
      </tr>
      
      <tr>
        <td><a href="javascript:CCPopUp('http://seal.controlcase.com/', 3660411984);"><img src='images/PCI_logo.gif' oncontextmenu='return false;' border='0'  width='80' height='50'></a></td>
        <td><img src="images/Verisign_logo_32.gif" alt="Verisign" name="Ver_logo"></td>
        <td align="left" class="header_font_color" >This webpage is maintained by TechProcess Solutions Limited.Visit us at <a  href="javascript:openWindow()">www.techprocess.co.in</a><br/>
          This site is best viewed with Internet Explorer 6.0 or higher, or Firefox 2.0 or higher, at a screen resolution of 1024x768.</td>
      </tr>
    </table>
    </td>
    
    </tr>
    
    <tr>
      <td valign="top">&nbsp;</td>
    </tr>
  </table>
  <input type="hidden" name="abc" value="">
  <input type="hidden" name="xyz" value="">
  <input type="hidden" name="sss" value="">
  <input type="hidden" name="SRCSITEID" value="">

<script type="text/javascript">
            tmp_titles = document.getElementById("l_side_links").getElementsByTagName("a");
            if(tmp_titles[0].innerHTML !=null)
            {
                var idleft = new String();
                idleft = tmp_titles[0].id;
                showme(idleft.substr(1));
            }
        </script>
</body>
</html>