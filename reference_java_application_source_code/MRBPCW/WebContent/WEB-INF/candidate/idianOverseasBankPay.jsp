<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!--CFT - Starts-->
<!--CFT - Ends-->
<!--Added for PCI starts here-->
<!--Added for PCI Ends here-->
<!-- Added by ram kumar-->
<!-- end here-->
<html><head><meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="refresh" content="18; URL="#" />
<!--CFT - Starts-->
<!--CFT - Ends-->
<script language="JavaScript" src="js/clienthint.js">

</script>
<script>
function callCC(val){
	if(val.length == 23){
		val[8].checked=false;
		val[0].checked=true;
	}
}
function callDC(val){
	if(val.length == 23){
		val[0].checked=false;
		val[8].checked=true;
	}
}

function showIciciBankPage()
{
    
		document.getElementById("block9").style.display = "block";
    	document.getElementById("fade").style.display = "block";
    	document.forms['ccForm'].action='PaymentOfflineAction_insertOnlinepaymentDetails.action';
		document.forms['ccForm'].submit();
}
//Added for CVV-Help
//

function openCapWin()
{
	window.open('captcha.jsp','_blank','toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, copyhistory=yes, width=520, height=320')
}
function openCvvWin()
{
	window.open('cvv.jsp','_blank','toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, copyhistory=yes, width=520, height=370')
}
function openPinWin()
{
	window.open('pin.jsp','_blank','toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, copyhistory=yes, width=520, height=300')
}
function openExpWin()
{
	window.open('expDt.jsp','_blank','toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, copyhistory=yes, width=520, height=250')
}
function openEcomPinWin()
{
	window.open('ECOMPIN.jsp','_blank','toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, copyhistory=yes, width=520, height=400')
}
function openATMECOMWin()
{
	window.open('ATMECOMPIN.jsp','_blank','toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, copyhistory=yes, width=520, height=400')
}

//Ends

// CSM done PCI LOGO on 08-07-2008 - START 
function CCPopUp(SEALURL, cId){
window.open(""+SEALURL+"index.php?page=showCert&cId="+cId+"", "win",'toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=0,resizable=0,width=700,height=585');
self.name = "mainWin";
}

// CSM done PCI LOGO on 08-07-2008 - ENDS 
/*code for ecom pin CSM Start Here 29-07-2009*/

function callChangeUrl()
{
	//document.form1.action="https://securepgtest.fssnet.co.in/pgway/gateway/checkEcomPin.jsp";
	//document.form1.submit();
	//var reqURL = document.form1.reqURL.value;
/*window.opener = self

window.close()*/

document.form1.action="cancel.jsp";
document.form1.submit();
window.open ('https://securepg.fssnet.co.in/pgway/gateway/checkEcomPin.jsp?reqURL='+'https://securepg.fssnet.co.in'+'&uniqueId='+15,'win1','width=950,height=575,location=0,menubar=0,scrollbars=0,status=0,toolbar=0,resizable=0,titlebar=no');
}

function RegisterEpin()
{
	//var reqURL = document.form1.reqURL.value;

/*window.opener = self
window.close()	*/
document.form1.action="cancel.jsp";
document.form1.submit();
window.open ('https://securepg.fssnet.co.in/pgway/gateway/checkRegisterEcomPin.jsp?reqURL='+'https://securepg.fssnet.co.in'+'&uniqueId='+15,'win1','width=950,height=575,location=0,menubar=0,scrollbars=0,status=0,toolbar=0,resizable=0,titlebar=no');
}

function ForgotEpin()
{
document.form1.action="cancel.jsp";
document.form1.submit();
window.open ('https://securepg.fssnet.co.in/pgway/gateway/checkForgotEcomPin.jsp?reqURL='+'https://securepg.fssnet.co.in'+'&uniqueId='+15,'win1','width=950,height=575,location=0,menubar=0,scrollbars=0,status=0,toolbar=0,resizable=0,titlebar=no');
}
function DeregisterEpin()
{
document.form1.action="cancel.jsp";
document.form1.submit();
window.open ('https://securepg.fssnet.co.in/pgway/gateway/checkDeregisterEcomPin.jsp?reqURL='+'https://securepg.fssnet.co.in'+'&uniqueId='+15,'win1','width=950,height=575,location=0,menubar=0,scrollbars=0,status=0,toolbar=0,resizable=0,titlebar=no');
}
/*code for ecom pin CSM ends here 29-07-2009*/
</script>

<title>Portal Payment</title>

        <style type="text/css">
<!--

body {
background: white;
}


/*Buttons BG color - BGround style - Font can be changed from here */
.button { 
color:white;
border-color: 00008B;
border-style: ;
visibility: none;
font-style: bold;
font: bold 14px    /* Button text BOLD from here || We can use this Tag also........font-weight: bold;*/
cursor: url;
background: 00008B;
}

/* Billing Information and Payment Information Table Border Color Changed from here */
.paymentinfotable {
border-collapse: collapse;
border:2px solid #0074bd;
/*border-style: none; */
border-bottom-color: 778899;
border-left-color: 778899;
border-top-color: 778899;
border-right-color: 778899;
width: 410px;
}


.paymentheader{
border-color: none;
border-style: none;
color: FFFFFF;
font-style: bold;
visibility: show;

/* Billing Information and Payment Information BG color changes from here */
background: FFFFFF;

table-layout: auto;
width: 350px;
margin-top: 0px;
margin-right: 0px;
margin-bottom: 0px;
margin-left: 0px;
}

.paymentchoiceradiohide{
border-color: #000000;
border-style: none;
color: #FFFFFF;
visibility: visible;
}

.paymentchoiceradioshow{
border-color: #000000;
border-style: none;
color: #FFFFFF;
visibility: visible;
}

.paymentboxes{
border-color: #0066CC;
border-style: none;
color: #FFFFFF;
visibility: visible;
font-size: 0.0em;
}

.paymentradio{
border-color: #000000;
border-style: solid;
color: #FFFFFF;
visibility: hidden;
}

.paymentbuttons {
height:"22";
width:"150";
FONT-FAMILY:arial;
FONT-SIZE:12px;
}

.paymentdata {
font-family:Arial, Helvetica, sans-serif;
font-size:12px;
border-color: #000000;
border-style: none;
color: #000000;
visibility: visible;
margin-top: 0px;
margin-right: 0px;
margin-bottom: 0px;
margin-left: 0px;
}

.paymententrytable {
border-color: #000000;
border-style: none;
color: #FFFFFF;
visibility: visible;
margin-top: 0px;
margin-right: 0px;
margin-bottom: 0px;
margin-left: 0px;
}


.paymentinput_new
{
border-top:#7E9DB9 1px solid;
border-bottom:#7E9DB9 1px solid;
border-right:#7E9DB9 1px solid;
border-left:#7E9DB9 1px solid;
height:"20";
width:"50%";
COLOR:"#000000";
FONT-FAMILY:arial;
FONT-SIZE:12px;
}

.paymentinput_small
{
    border-top:#7E9DB9 1px solid;
	border-bottom:#7E9DB9 1px solid;
	border-right:#7E9DB9 1px solid;
	border-left:#7E9DB9 1px solid;
	height:"20";
	width:"30%";
	COLOR:"#000000";
	FONT-FAMILY:arial;
	FONT-SIZE:12px;
}

.paymentinput { 
border-color: #F5F8FA;
border-style: thin;
color: #333333;
visibility: visible;
margin-top: 0px;
margin-right: 0px;
margin-bottom: 0px;
margin-left: 0px;
}

.paymentinputtd { 
border-color: #F5F8FA;
border-style: none;
color: #333333;
visibility: visible;
margin-top: 0px;
margin-right: 0px;
margin-bottom: 0px;
margin-left: 0px;
}

.paymentlabel {
visibility: visible;
font-weight: bold;
margin-top: 0px;
margin-right: 0px;
margin-bottom: 10px;
margin-left: 0px;

/* Billing and Payment Information Table Label's  Fore color can be changed from here  */
COLOR: 00008B;

font-family:Arial, Helvetica, sans-serif;
font-size:12px;
}

.paymenttable {  
border-color: #000000;
border-style: none;
visibility: visible;
}

.paymentselect { 
border-color: #F5F8FA;
border-style: thin;
color: #333333;
visibility: visible;
border-left: 1px thin #F5F8FA;
border-right: thin #F5F8FA;
border-top: 1px thin #F5F8FA;
border-bottom: thin #F5F8FA;
}

.paymententerdetails {  
visibility: visible;
border-color: #000000;
border-style: none;
color: #FFFFFF;
}

p { font-size: 12px; color: #333333; margin-top: 4px; margin-right: 0px; margin-bottom: 2px; margin-left: 0px; }

a:link {  color: #333399; background: none; text-decoration: underline;}
a:hover { color: black; background: none; text-decoration: none;}
a:active { color: #333399; background: none; text-decoration: underline;}
a:visited { color: #333399; background: none; text-decoration: underline;}

h1 { font-size: 12px; color: #0066CC; font-weight: bold; margin-top: 1px; margin-right: 0px; margin-bottom: 5px; margin-left: 0px; }

h2 { font-size: 12px; color: #0066CC; font-weight: bold; margin-top: 15px; margin-right: 0px; margin-bottom: 3px; margin-left: 0px; }

h3 { font-size: 12px; color: #0066CC; font-weight: bold;}

H4 { FONT-WEIGHT: bold; FONT-SIZE: 12px; font-family:"Comic Sans MS";}

hr { color: #C5C5C5; height: 1px; margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; background: black; }

strong { font-weight: bold; }

form { margin-top: 4px; margin-right: 0px; margin-bottom: 4px; margin-left: 0px; }

th { padding-left: 5px;	text-align: left; color: #333333; background: #0074bd; font-weight: normal; border-color: #CBDFF2; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width:1px; border-left-width: 1px; }

ul { font-size: 0.7em; color: #333333; margin-left: 40px; margin-top: 0px; margin-right: 0px; margin-bottom: 2px; }

ol { color: #333333; margin-left: 40px; margin-top: 2px; margin-right: 0px; margin-bottom: 2px; }


-->
</style>


</head>

<!-- CSM done to prevent the back moment of the page between payment and confirmation page - START -->

<!-- <body scroll> The part Changed as following -->
<!-- Added for card select option for HDFC Bank CSM by sre on 22-08-2011:: starts here -->
<!-- <body scroll onLoad= " if (history.length > 0) history.go(+1)">-->
<FORM name="ccForm" id="ccForm">
<input type="hidden" name="onlinePaymentType" value="2"/>
<input type="hidden" name="amount" value="4000"/>
<body scroll="" onLoad=" if (history.length > 0) history.go(+1);payinfodisp()">
<div id="fade" style="display:none; top:0; left:0; z-index:900; width:100%; height:100%; position:absolute; background:url(images/bg-fade-div.png) repeat top left;"></div>
<div id = 'block9' style=" display:none; top:305px; left:525px; z-index:1000; position:absolute;" align="center">
  				  <img src="images/progress_bar.gif"  alt="GCET " />
  				  <br /><br />  Please wait... Your Request is processing...
</div>
<!-- Added for card select option for HDFC Bank CSM by sre on 22-08-2011:: ends here -->
<!-- CSM done to prevent the back moment of the page between payment and confirmation page - END -->


        
<!--- Copyright 2001 ACI Worldwide. All rights reserved --->



<meta name="GENERATOR" content="IBM WebSphere Studio">
<style> 	.Title {
 		background-color : white;
 		color : black;
 		font-family : Arial, Helvetica, serif;
 		font-size : medium;
 		font-style : normal;
 		font-weight : bold;
 		text-align : left;
 	}
 	TD.titleline {
 		background-color : black;
 		color : black;
 		font-family : Arial, Helvetica, serif;
 		font-size : 1pt;
 	}
</style>


<table border="0" cellpadding="0" cellspacing="0" width="96%">
<tbody><tr>

</tr></tbody></table><table align="center" bgcolor="white" border="0" cellpadding="0" cellspacing="0" width="100%">
	<tbody><tr>
	<td align="left" height="30">
	<img alt="" src="images/iob.bmp" align="left" border="0" height="50" width="220"></td>
<!--	<td  height="30" align="right">
	<IMG alt="" border="0" align="right" width="220" height="50" src="/pgway/brands/hdfc/mylogo/ME_LOGO_1.jpg"></td> -->
	</tr>

</tbody></table><table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
<tbody><tr>
<td bgcolor="778899" height="20" width="90%"></td></tr>
</tbody></table>
<table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
<tbody><tr>
<td align="center" bgcolor="00008B" height="40"><font color="white" face="verdana" size="2"></font></td>
</tr>
</tbody></table>


<!-- Fss Added Line under .gif file   -->
<!-- <table width="96%" border="0" cellspacing="0" cellpadding="0">
	<tr>
        <td height="1" bgcolor="black" class="titleline"></td>
	</tr>

</table> -->

<br>




<!--Welcome -->
<table border="0" width="100%">
	<tbody><tr><td></td><td></td><td></td><td></td><td></td><td></td><td align="center"><h5><font color="#333399" face="Verdana">WELCOME TO </font><font color="#333399" face="Verdana">INDIAN OVERSEAS BANK</font> <font color="#333399" face="Verdana">PAYMENT GATEWAY</font></h5> </td></tr>
</tbody></table>
<!--Ends-->

<!--   Changed By Arul.K -->
<!--   Showing MOD10 error if invalid Card Number Start-->


<!--   Showing MOD10 error if invalid Card Number End-->


<table border="0" width="100%">    <!-- Main Table - FSS -->
<tbody><tr>
<!-- CSM-Alignment - Width changed to 10% from 20% and valign is added additionally	- START --> 
<td valign="top" width="10%"> 
<!-- CSM-Alignment - Width changed to 10% from 20% and valign is added additionally	- END --> 

<!-- CSM done to display the SecureCode and VBV logo - Hard code - START-->

<!--<table align="center" border = 0  >
<br><br><br><br><!--
 <!--<img src="../../brands/hdfc/securelogo.GIF" border=0 width=100 height=40> <br><br><br> <img src="../../brands/hdfc/VbV.GIF" border=0 width=110 height=60>-- <br><br><br><a href="https://digitalid.verisign.com/as2/9ec99120d88e7d9c040341245948ee32 " target="_new" style="text-decoration:none">
 <img src="../../brands/hdfc/VeriSignSealwhite.gif" border=0 width=100 height=100></a>
 </table>-->

<br><br><table border="0" cellpadding="0" cellspacing="0">
<tbody><tr></tr><tr></tr><tr>
<td align="center" valign="center">
<script src="images/getseal"></script></td>
</tr>
</tbody></table>
 <!-- CSM done to display the PCI logo - Hard Code - STARTS 08-07-2008 -->
 
 <br><br><table border="0" cellpadding="0" cellspacing="0">
 <tbody><tr></tr><tr></tr>
 <tr>
 <td align="center" valign="center">
<a href="javascript:CCPopUp('http://seal.controlcase.com/',%203853338200);">
<img src="images/PCI_logo.gif" oncontextmenu="return false;" border="0" height="55"></a><br>

</td>
</tr>
</tbody></table>

 <!-- CSM done to display the PCI logo - Hard Code - END -->


 <!-- CSM done to display the SecureCode and VBV logo - Hard Code - END -->

<!-- CSM0021 done to display the SecureCode and VBV logo using HTML file - START -->



<!-- CSM0021 done to display the SecureCode and VBV logo using HTML file - END -->

</td>
<!-- CSM-Alignment Widtth and align is added - START -->
<td align="center" width="70%"> 
<!-- CSM-Alignment Widtth and align is added - END -->

<table align="center" border="0">   <!-- CSM - Second Main Table Start-->
 
</table><div id="paymentdispdiv" class="paymentdispdivcss">
</div><table class="paymenttable" border="0">

        
		<tbody><tr><td colspan="5">
        <table class="paymentinfotable" align="center" border="0" width="400">
		<tbody><tr><td class="paymentheader" colspan="5" bgcolor="#CCCCCC"><font color="black" size="3"><b>&nbsp;Billing Information</b></font></td></tr>
            <tr>
				<td class="paymentlabel" width="150"><b>&nbsp;Merchant&nbsp;&nbsp;</b></td>
                <td class="paymentdata" width="250">&nbsp;Techprocess</td>
            </tr>
            <tr>
                <td class="paymentlabel"><b>&nbsp;Website  &nbsp;&nbsp;</b></td>
                <td class="paymentdata">&nbsp;http://www.techprocess.co.in</td>
            </tr>
            <tr>
                <td class="paymentlabel"><b>&nbsp;Amount &nbsp;&nbsp;</b></td>
                <td class="paymentdata">&nbsp;RS 4,000.00</td>
	       </tr>

<!-- CSM: CSM024 Displaying the Merchant Track ID in the payment.jsp - START -->
		    <tr>
			   <td class="paymentlabel"><b>&nbsp;Track ID &nbsp;&nbsp;</b></td>
			   <td class="paymentdata">&nbsp;46711153 </td>
		    </tr>
<!-- CSM: CSM024 Displaying the Merchant Track ID in the payment.jsp - END -->

        </tbody></table></td></tr>

	<tr><td>&nbsp;</td></tr>
  
<!-- Added for card select option for HDFC Bank CSM by sre on 22-08-2011:: starts here -->
<style type="text/css">
.cardSelecttable {
border:2px solid black;
border-collapse:collapse;
width:600px;
height:60px;
font-family:Verdana;
font-size:13px;
}
.cardhead{
font-family:Verdana;
font-size:12px;
font-weight:bold;
font-color:blue;
}
</style>


<!-- Added by SRE for card selection option on 03-08-2011 ends here -->
<style type="text/css">
.paymentdispdivcss {
display:none;
}
</style>
</tbody></table><div id="div1" class="paymententrydiv">
<!-- Added for card select option for HDFC Bank CSM by sre on 22-08-2011:: ends here -->

<!--   class="paymentboxes"  deleted due to CSS not working
-->		<!-- Added for card select option for HDFC Bank CSM by sre on 22-08-2011:: starts here -->
		<!--<td ><table border=0 width="400"><tr><td> -->
		</div><table class="paymentinfotable" id="paymentdisptable" align="center" border="0">
<tbody><tr><td class="paymentheader" bgcolor="#CCCCCC"><font color="black" size="3"><b>Payment Information</b></font></td></tr>

<style type="text/css">
.paymententrydiv {
display:none;
}
/*Commented by shiva for Captcha Display for Canara  and PNB on 5th Dec 2011 Starts
.Captchadivcss{
display:none;
}
Commented by shiva for Captcha Display for Canara  and PNB on 5th Dec 2011 Ends*/
</style>
<tr><td><table id="paymentchiocetable" border="0" width="400"><tbody><tr><td>


            <input class="paymentchoiceradiohide" name="paymentChoice" value="debitcardform&amp;postPayment.jsp&amp;0&amp;0" checked="checked" type="radio">
                                      <b>Card Details </b>


        <input name="payment_id_lng" value="987463191621980" type="hidden">
	    <!-- Code for Mod10 checks starts here -->
		<input name="institution_id_int" value="15" type="hidden">
	    <!-- Code for Mod10 checks Ends here -->
        <table class="paymententrytable" border="0">


							<input name="card_type_str" value="DC" type="hidden">


			
						
				<!--  CSM017 - Removal of Street Address, ZIP code for Credit card in payment.jsp - START -->
					

								<tbody><tr><td class="paymentlabel">&nbsp;Card Number</td>
								<td class="paymentinputtd">&nbsp;<input class="paymentinput" name="Ecom_Payment_Card_Number" size="19" maxlength="19" onFocus="select()" onClick="callDC(this.form)" type="Text"></td></tr>
 

 
					<!--  CSM017 - Removal of Street Address, ZIP code for Credit card in payment.jsp - END -->

					<!-- CSM for Captcha -Start-->
					
					<!-- CSM for Captcha -End-->


			
						
				<!--  CSM017 - Removal of Street Address, ZIP code for Credit card in payment.jsp - START -->
					
 

 
					<!--  CSM017 - Removal of Street Address, ZIP code for Credit card in payment.jsp - END -->

					<!-- CSM for Captcha -Start-->
					
					<!-- CSM for Captcha -End-->


			
<tr><td class="paymentlabel">&nbsp;Valid From / Expiry Date</td>
				    <td class="paymentinputtd">&nbsp;<select class="paymentselect" name="Ecom_Payment_Card_ExpDate_Month">
					<option selected="selected" value="0">MM</option>

						<option value="1">1</option>

						<option value="2">2</option>

						<option value="3">3</option>

						<option value="4">4</option>

						<option value="5">5</option>

						<option value="6">6</option>

						<option value="7">7</option>

						<option value="8">8</option>

						<option value="9">9</option>

						<option value="10">10</option>

						<option value="11">11</option>

						<option value="12">12</option>

			        </select>

			
						<select class="paymentselect" name="Ecom_Payment_Card_ExpDate_Year">
						<option selected="selected" value="0">YYYY</option>


							<option value="1995">1995</option>

							<option value="1996">1996</option>

							<option value="1997">1997</option>

							<option value="1998">1998</option>

							<option value="1999">1999</option>

							<option value="2000">2000</option>

							<option value="2001">2001</option>

							<option value="2002">2002</option>

							<option value="2003">2003</option>

							<option value="2004">2004</option>

							<option value="2005">2005</option>

							<option value="2006">2006</option>

							<option value="2007">2007</option>

							<option value="2008">2008</option>

							<option value="2009">2009</option>

							<option value="2010">2010</option>

							<option value="2011">2011</option>

							<option value="2012">2012</option>

							<option value="2013">2013</option>

							<option value="2014">2014</option>

							<option value="2015">2015</option>

							<option value="2016">2016</option>

							<option value="2017">2017</option>

							<option value="2018">2018</option>

							<option value="2019">2019</option>

							<option value="2020">2020</option>

							<option value="2021">2021</option>

							<option value="2022">2022</option>

							<option value="2023">2023</option>

							<option value="2024">2024</option>

							<option value="2025">2025</option>

							<option value="2026">2026</option>

							<option value="2027">2027</option>

							<option value="2028">2028</option>

							<option value="2029">2029</option>

							<option value="2030">2030</option>

							<option value="2031">2031</option>

							<option value="2032">2032</option>

							<option value="2033">2033</option>

							<option value="2034">2034</option>

							<option value="2035">2035</option>

							<option value="2036">2036</option>

							<option value="2037">2037</option>

							<option value="2038">2038</option>

							<option value="2039">2039</option>

							<option value="2040">2040</option>

							<option value="2041">2041</option>

							<option value="2042">2042</option>

							<option value="2043">2043</option>

							<option value="2044">2044</option>

							<option value="2045">2045</option>

							<option value="2046">2046</option>

							<option value="2047">2047</option>

							<option value="2048">2048</option>

							<option value="2049">2049</option>

							<option value="2050">2050</option>

							<option value="2051">2051</option>

							<option value="2052">2052</option>

							<option value="2053">2053</option>

							<option value="2054">2054</option>

							<option value="2055">2055</option>

						</select>&nbsp;<a href="javascript:openExpWin()">Help?</a></td></tr>

			
						
				<!--  CSM017 - Removal of Street Address, ZIP code for Credit card in payment.jsp - START -->
					

								<tr><td class="paymentlabel">&nbsp;Cardholder's Name</td>
								<td class="paymentinputtd">&nbsp;<input class="paymentinput" name="Ecom_Payment_Card_Name" size="30" maxlength="30" onFocus="select()" onClick="callDC(this.form)" type="Text"></td></tr>
 

 
					<!--  CSM017 - Removal of Street Address, ZIP code for Credit card in payment.jsp - END -->

					<!-- CSM for Captcha -Start-->
					
					<!-- CSM for Captcha -End-->


			
						
				<!--  CSM017 - Removal of Street Address, ZIP code for Credit card in payment.jsp - START -->
					
 

 
					<!--  CSM017 - Removal of Street Address, ZIP code for Credit card in payment.jsp - END -->

					<!-- CSM for Captcha -Start-->
					
					<!-- CSM for Captcha -End-->


			
						
				<!--  CSM017 - Removal of Street Address, ZIP code for Credit card in payment.jsp - START -->
					
 

 
					<!--  CSM017 - Removal of Street Address, ZIP code for Credit card in payment.jsp - END -->

					<!-- CSM for Captcha -Start-->
					
					<!-- CSM for Captcha -End-->


			
							<tr>
									<td class="paymentlabel">&nbsp;PIN</td>   <!-- PIN -->
									<td class="paymentinputtd">&nbsp;<input class="paymentinput" name="Ecom_Payment_Pin" size="4" maxlength="4" value="" onFocus="select()" type="password">&nbsp;<a href="javascript:openPinWin()"><font color="#8d8d8d" face="arial" size="-2">What is PIN?</font></a></td>
							</tr>
							
						
			
					
			

			
	</tbody></table>
	</td></tr></tbody></table></td>


	<!--CR Captcha Implementation starts-->
	<!-- Added for card select option for HDFC Bank CSM by sre on 22-08-2011:: starts here -->
	<!--Close tag of id=div1 -->
	<!-- Added for card select option for HDFC Bank CSM by sre on 22-08-2011:: ends here -->
	</tr></tbody></table>
	<!-- Added for card select option for HDFC Bank CSM by sre on 22-08-2011:: starts here -->
	<div id="Captchadiv" class="Captchadivcss">
	<!-- Added for card select option for HDFC Bank CSM by sre on 22-08-2011:: ends here -->
		<table align="center" border="0">

		
			<!--CR Captcha Implementation ends-->
		</table>
	<!-- Added for card select option for HDFC Bank CSM by sre on 22-08-2011:: starts here -->
    </div><!-- close tag of id="captchadiv" -->
	<!-- Added for card select option for HDFC Bank CSM by sre on 22-08-2011:: end here -->
<br>
<div id="ProcessDiv" name="ProcessDiv" style="display :none;" align="center">
		<img alt="" src="images/prcindicator.gif" align="middle" border="0">
		<br><font type="Times New Roman" color="black" size="3px;"><b>Processing&#8230;. Please wait</b>
		</font></div><p align="center"><font type="Times New Roman" color="black" size="3px;">&nbsp;<span id="txtHint" name="txtHint" disabled="disabled" style="display:none;"></span></font></p><table border="0">
<tbody><tr>
    <td>

		<!-- CSM017 - Removal of Street Address, ZIP code for Credit card in payment.jsp - START -->
		<input name="Ecom_BillTo_Postal_Street_Line1" value=" " type="hidden">
		<input name="Ecom_BillTo_Postal_PostalCode" value=" " type="hidden">
		
		<input name="Ecom_BillTo_Postal_Street_Line1" value=" " type="hidden">
		<input name="Ecom_BillTo_Postal_PostalCode" value=" " type="hidden">
		<input name="Ecom_Payment_Card_Verification" value="" type="hidden">
		
		<!-- CSM017 - Removal of Street Address, ZIP code for Credit card in payment.jsp - START -->
        <!-- Displaying Processing Txn Message text Starts here,Added id="SubmitBtn" in the below statment ::changes performed on 08-Jan-2010 -->
		</td><td><div align="center">
		<input class="button" name="paymentChoice" id="SubmitBtn" value="Submit" type="button" onclick="showIciciBankPage();">
		</div> </td>
		<!-- Displaying Processing Txn Message text Ends here ::changes performed on 08-Jan-2010-->
<!--        <input class="button" type="Submit" name=paymentChoice value="Submit"  onclick='validate();'> 
			commanded for script validation on 11-04-07 
-->		
		<!-- Displaying Processing Txn Message text Starts here ::changes performed on 08-Jan-2010 -->
		
		<!-- Displaying Processing Txn Message text Ends here ::changes performed on 08-Jan-2010-->
		
		
    

    <td><div align="center">
		<input class="button" name="cancel" id="CancelBtn" value="Cancel" type="button" onclick="window.location='PaymentOnlineAction_showPaymentScreen.action'">
		<%--<input name="reqURL" value="https://securepg.fssnet.co.in:443/pgway/gateway/payment/payment.jsp" onClick="onclckCancel();" type="hidden">
		--%></div></td>
		<!-- Displaying Processing Txn Message text Ends here ::changes performed on 08-Jan-2010-->
		    
		
	
</tr>
</tbody></table><font type="Times New Roman" color="black" size="3px;">
</font></td></tr></tbody></table>





<!-- // card number, cvv, pin and name validation starts here ON 11-04-07 -->
<script language="javascript">
//Added by SHIVA For Maestro Card Accaptance starts here
function showhint(field)
{
	(document.cardform.Ecom_Payment_Card_Number.value+';' + '15'+';' + '5000005'+';' + '50000051');	
}
if (document.ELEMENT_NODE == null) {
  document.ELEMENT_NODE = 1;
  document.TEXT_NODE = 3;
}

var whtSpEnds = new RegExp("^\\s*|\\s*$", "g");
var whtSpMult = new RegExp("\\s\\s+", "g");
function normalizeString(s) {

  s = s.replace(whtSpMult, " ");  // Collapse any multiple whites space.
  s = s.replace(whtSpEnds, "");   // Remove leading or trailing white space.

  return s;
}

function getTextValue(el) {

  var i;
  var s;

  // Find and concatenate the values of all text nodes contained within the
  // element.
  s = "";
  for (i = 0; i < el.childNodes.length; i++)
    if (el.childNodes[i].nodeType == document.TEXT_NODE)
      s += el.childNodes[i].nodeValue;
    else if (el.childNodes[i].nodeType == document.ELEMENT_NODE &&
             el.childNodes[i].tagName == "BR")
      s += " ";
    else
      // Use recursion to get text within sub-elements.
      s += getTextValue(el.childNodes[i]);

  return normalizeString(s);
}

//Added by SHIVA For Maestro Card Accaptance Ends here

	// ECOM PIN CSM Starts here
	function callATMECOM(valObj)
	{
		if(valObj=="0")
		{
			document.getElementById("nameDiv").innerHTML="&nbsp;"+"ATM PIN";
		}
		else if(valObj=="1")
		{
			document.getElementById("nameDiv").innerHTML="&nbsp;"+"ECOM PIN";
		}
	}
	// ECOM PIN CSM Ends here

function getCheckedValue(radioObj) {
	if(!radioObj)
		return "";
	var radioLength = radioObj.length;
	if(radioLength == undefined)
		if(radioObj.checked)
			return radioObj.value;
		else
			return "";
	for(var i = 0; i < radioLength; i++) {
		if(radioObj[i].checked) {
			return radioObj[i].value;
		}
	}
	return "";
}

function isAlphabetic(str) {
	 
	  var len= str.length;
	  if (len==0) return false;

	  var p=0;
	  var ok= true;
	  var ch= "";
	  while (ok && p<len) {
		ch= str.charAt(p);
		 
		if (  ('0'<=ch && ch<='9') )
		  p++;
		else
		  ok= false;
	  }
	  return ok;
	}

function validate(){
	// ECOM PIN De-Registration and FOrgot PIN Starts here 2-12-2008
	if(document.form1.card_type_str.value!="CC")
	{
		var pinflgchkc = "N";
		// ECOM 03/09/2009 Start
		var mrchpinflgchkc = "N";
		var tempChkecomflg = "0";

		if(pinflgchkc=="R")
		{
			if(mrchpinflgchkc=="N")
			{
				form1.Ecom_Payment_Pin.value = form1.testPin.value;
				tempChkecomflg = "1";
				document.form1.Ecom_Payment_Pin_Tran.value="empty";
			}
			else if(mrchpinflgchkc=="Y")
			{
				document.form1.Ecom_Payment_Pin.value=0;
				form1.Ecom_Payment_Pin_Tran.value = form1.testPin.value;
				tempChkecomflg = "2";
			}
			else if(mrchpinflgchkc=="R")
			{
				if(form1.pinRadio[0].checked)
				{
					form1.Ecom_Payment_Pin.value = form1.testPin.value;
					tempChkecomflg = "1";
				}
				else
				{
					document.form1.Ecom_Payment_Pin.value=0;		
				}
				if(form1.pinRadio[1].checked)
				{
					form1.Ecom_Payment_Pin_Tran.value = form1.testPin.value;
					tempChkecomflg = "2";
				}
				else
				{
					document.form1.Ecom_Payment_Pin_Tran.value="empty";
				}
			}				
		}// ECOM 03/09/2009 End
	}
		/*var tempChkecomflg = "0";

		if(pinflgchkc=="R")
		{
			if(form1.pinRadio[0].checked)
			{
				form1.Ecom_Payment_Pin.value = form1.testPin.value;
				tempChkecomflg = "1";
			}
			else
			{
				document.form1.Ecom_Payment_Pin.value=0;		
			}
			if(form1.pinRadio[1].checked)
			{
				form1.Ecom_Payment_Pin_Tran.value = form1.testPin.value;
				tempChkecomflg = "2";
			}
			else
			{
				document.form1.Ecom_Payment_Pin_Tran.value="empty";
			}
		}
	}*/

		// ECOM PIN De-Registration and FOrgot PIN ENds here 2-12-2008

	
//		var el=document.getElementById('txthint');
//		if (el !=null)
//		{
//			var txthintvalue=getTextValue(el);
//			if (txthintvalue!="MAESTRO")
//			{
			if(form1.Ecom_Payment_Card_Number.value == "" ){
				alert ("Please Enter Card Number");
				form1.Ecom_Payment_Card_Number.focus();
				return false;
			}
			//if(form1.Ecom_Payment_Card_Number.value.length == 16 || form1.Ecom_Payment_Card_Number.value.length == 19  ){
				if(form1.Ecom_Payment_Card_Number.value.length >= 12 && form1.Ecom_Payment_Card_Number.value.length <= 19  ){
				if(!isAlphabetic(form1.Ecom_Payment_Card_Number.value)){
					alert ("Card number should be numeric only");
					form1.Ecom_Payment_Card_Number.focus();
					return false;
				}  
			} else {
				alert ("Please Enter Complete Card number (between 12 to 19 digits only)");
				form1.Ecom_Payment_Card_Number.focus();
				return false;
			//}
			//}
		}
		

		if(form1.Ecom_Payment_Card_ExpDate_Month.value == "0" ){
			alert ("Please Select Month");
			form1.Ecom_Payment_Card_ExpDate_Month.focus();
			return false;
		}
		if(form1.Ecom_Payment_Card_ExpDate_Year.value == "0" ){
			alert ("Please Select Year");
			form1.Ecom_Payment_Card_ExpDate_Year.focus();
			return false;
		}
		
		
		var tempEcomFlagVal = "N";
				if(tempEcomFlagVal=="Y")
					{
						if(form1.Ecom_Payment_Pin_Tran.value == "" )
							{
								alert ("Please Enter Ecom PIN, If you are not registered for ECOM Pin please click Register Ecom PIN in this page");
								form1.Ecom_Payment_Pin_Tran.focus();
								return false;
							}
						if(form1.Ecom_Payment_Pin_Tran.value.length < 8)
							{
								alert ("Ecom PIN Length should be atleast 8, If you are not registered for ECOM Pin please click Register Ecom PIN in this page");
								form1.Ecom_Payment_Pin_Tran.value="";
								form1.Ecom_Payment_Pin_Tran.focus();
								return false;
							}
						else
							{
								var resStr = shouldContainNumbersLettersSpecial(form1.Ecom_Payment_Pin_Tran.value);
									if(resStr!="")
										{
											alert ("Ecom PIN"+resStr+", If you are not registered for ECOM Pin please click Register Ecom PIN in this page");
											form1.Ecom_Payment_Pin_Tran.focus();
											form1.Ecom_Payment_Pin_Tran.value="";
											return false;
										}
							}
					}
			       else if(tempEcomFlagVal=="N") // Added for ECOM DeRegistration and Forgot Ecom Pin Starts here 2-12-2008
				{
						if(form1.Ecom_Payment_Pin.value == "" )
							{
								alert ("Please Enter PIN");
								form1.Ecom_Payment_Pin.focus();
								return false;
							}
						if(form1.Ecom_Payment_Pin.value.length < 4 )
							{
								alert ("Please Enter 4 digit PIN");
								form1.Ecom_Payment_Pin.focus();
								return false;
							}
					}
					// ECOM DeRegistration and Forgot Ecom Pin Starts here 2-12-2008
			else if(tempEcomFlagVal=="R")
			{
				if(tempChkecomflg=="1")
				{
					if(form1.Ecom_Payment_Pin.value == "" )
					{
						alert ("Please Enter PIN");
						form1.testPin.focus();
						return false;
					}
					if(form1.Ecom_Payment_Pin.value.length!=4 )
					{
						alert ("Please Enter 4 digit PIN");
						form1.testPin.focus();
						return false;
					}
				}
				else if(tempChkecomflg=="2")
				{
					if(form1.Ecom_Payment_Pin_Tran.value == "" )
					{
						alert ("Please Enter Ecom PIN");
						form1.testPin.focus();
						return false;
					}
					if((form1.Ecom_Payment_Pin_Tran.value.length<6)&&(form1.Ecom_Payment_Pin_Tran.value.length>12))
					{
						alert ("Ecom PIN Length should be between 6 & 12");
						form1.testPin.value="";
						form1.testPin.focus();
						return false;
					}
					else
					{
						var resStr = shouldContainNumbersLettersSpecial(form1.Ecom_Payment_Pin_Tran.value);
						if(resStr!="")
						{
							alert ("Ecom PIN"+resStr);
							form1.testPin.focus();
							form1.testPin.value="";
							return false;
						}
					}
				}
			}
			// ECOM PIN CSM Ends here
				
		
		//if(form1.Ecom_Payment_Card_Name.value == "" ){
			//alert ("Please Enter Cardholder's Name");
			//form1.Ecom_Payment_Card_Name.focus();
			//return false;
		//}
		var mytextfield=form1.Ecom_Payment_Card_Name.value;
if(form1.Ecom_Payment_Card_Name.value=="" || form1.Ecom_Payment_Card_Name.value==null || mytextfield.charAt(0)==' ')
{
alert ("Please Enter Cardholder's Name");
form1.Ecom_Payment_Card_Name.focus();
return false;
}
else
{
if (form1.Ecom_Payment_Card_Name.value!="")
{
var val=form1.Ecom_Payment_Card_Name.value;
for(i=0;i<val.length;i++)
{
var code=val.charCodeAt(i);
if((code < 65 || code > 90) && (code < 97 || code > 123) && code != 32)
{ 
alert("Please enter cardholder name between [a-z & A-Z] and space.");
form1.Ecom_Payment_Card_Name.focus();
return ;
}    
}
}
}

		
// siva
		//Displaying Processing Txn Message text Starts here ::changes performed on 08-Jan-2010
		document.getElementById("ProcessDiv").style.display="block";
		document.getElementById("SubmitBtn").style.display="none";
		document.getElementById("CancelBtn").style.display="none";
		// Displaying Processing Txn Message text Ends here ::changes performed on 08-Jan-2010
	
	document.form1.action="intermediate.jsp";
	document.form1.submit();

}
/*Added for ECOM Pin change*/
//  Displaying Processing Txn Message text Starts here ::changes performed on 08-Jan-2010 
function onclckCancel()
{
	document.getElementById("ProcessDiv").style.display="block";
	document.getElementById("SubmitBtn").style.display="none";
	document.getElementById("CancelBtn").style.display="none";
	document.formcancel.action="cancel.jsp";
	document.formcancel.submit();
}
//Displaying Processing Txn Message text Ends here ::changes performed on 08-Jan-2010
function openEcomPinhelpWin()
{
	window.open('EcomHelp.jsp','_blank','toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, copyhistory=yes, width=750, height=350')
}
function shouldContainNumbersLettersSpecial(password)
{
	var str = "";
	var passed = validatePassword(password, 
	{
		length:   [6, 12],
		alpha:    1,
		numeric:  1,
		special:  1
	});

	var spclCnt = 0;
	for(var i=0;i<password.length;i++)
	{
		var tempChar = password.charAt(i);
		if(tempChar=="~")
		{
			passed = false;
			spclCnt=spclCnt+1;
		}
		else if(tempChar=="{")
		{
			passed = false;
			spclCnt=spclCnt+1;
		}
		else if(tempChar=="}")
		{
			passed = false;
			spclCnt=spclCnt+1;
		}
		else if(tempChar=="`")
		{
			passed = false;
			spclCnt=spclCnt+1;
		}
	}
	if(passed == true)
	{
		str = "";
	}
	else
	{
		if(spclCnt==0)
		{
			str = " should contain atleast one Alphabet, Number and Special Character \n";
		}
		else
		{
			str = " should not contain any of these Special Characters ~ `{ }  | \n";
		}
	}
	return str;
}


function validatePassword (pw, options) {
	// default options (allows any password)
	var o = {
		lower:    0,
		upper:    0,
		alpha:    0, /* lower + upper */
		numeric:  0,
		special:  0,
		length:   [0, Infinity],
		custom:   [ /* regexes and/or functions */ ],
		badWords: [],
		badSequenceLength: 0,
		noQwertySequences: false,
		noSequential:      false
	};

	for (var property in options)
		o[property] = options[property];

	var	re = {
			lower:   /[a-z]/g,
			upper:   /[A-Z]/g,
			alpha:   /[A-Z]/gi,
			numeric: /[0-9]/g,
			special: /[\W_]/g
		},
		rule, i;

	// enforce min/max length
	if (pw.length < o.length[0] || pw.length > o.length[1])
		return false;

	// enforce lower/upper/alpha/numeric/special rules
	for (rule in re) {
		if ((pw.match(re[rule]) || []).length < o[rule])
			return false;
	}

	// enforce word ban (case insensitive)
	for (i = 0; i < o.badWords.length; i++) {
		if (pw.toLowerCase().indexOf(o.badWords[i].toLowerCase()) > -1)
			return false;
	}

	// enforce the no sequential, identical characters rule
	if (o.noSequential && /([\S\s])\1/.test(pw))
		return false;

	// enforce alphanumeric/qwerty sequence ban rules
	if (o.badSequenceLength) {
		var	lower   = "abcdefghijklmnopqrstuvwxyz",
			upper   = lower.toUpperCase(),
			numbers = "0123456789",
			qwerty  = "qwertyuiopasdfghjklzxcvbnm",
			start   = o.badSequenceLength - 1,
			seq     = "_" + pw.slice(0, start);
		for (i = start; i < pw.length; i++) {
			seq = seq.slice(1) + pw.charAt(i);
			if (
				lower.indexOf(seq)   > -1 ||
				upper.indexOf(seq)   > -1 ||
				numbers.indexOf(seq) > -1 ||
				(o.noQwertySequences && qwerty.indexOf(seq) > -1)
			) {
				return false;
			}
		}
	}

	// enforce custom regex/function rules
	for (i = 0; i < o.custom.length; i++) {
		rule = o.custom[i];
		if (rule instanceof RegExp) {
			if (!rule.test(pw))
				return false;
		} else if (rule instanceof Function) {
			if (!rule(pw))
				return false;
		}
	}

	// great success!
	return true;
}

// added for Ecommerce PIN blocking right click
//starts
var message="asdsad"; 
function clickIE() 
{
	if (document.all) 
	{
		(message);
		return false;
	}
}
function clickNS(e) 
{ 
	if (document.layers||(document.getElementById&&!document.all)) 
	{ 
		if (e.which==2||e.which==3) 
		{
			(message);
			return false;
		}
	}
}

if (document.layers) 
{
	document.captureEvents(Event.MOUSEDOWN);
	document. onmousedown=clickNS;
} 
else 
{
	document.onmouseup=clickNS;
	document.oncontextmenu =clickIE;
} 
document.oncontextmenu=new Function("return false")

/*End here for ECOM Pin change*/
<!-- // card number , pin, cvv and name validation end here  -->
</script>


        
<!--- Copyright 2001 ACI Worldwide. All rights reserved --->


<meta name="GENERATOR" content="IBM WebSphere Studio">


<table align="center" border="0" cellpadding="0" cellspacing="0" width="96%">
<tbody><tr><td align="center"><font color="black" size="3"></font></td></tr>
</tbody></table> 
<center><font color="red" face="verdana" size="2">SERVICE CHARGES FOR ONLINE PAYMENT MADE THROUGH INDIAN OVERSEAS BANK PAYMENT GATEWAY IS Rs.10/- PER TRANSACTION</font></center>

<table rules="NONE" frame="BOX" border="1" cellpadding="0" cellspacing="0" width="100%">
<tbody><tr><td align="center"><font color="black" face="arial" size="2">
<b>Disclaimer:</b>
Dear Customer,
IOB and Tech Process are only facilitators. They shall not be 
responsible for any delay/failure of payments made by the customers or 
for any incorrect/ambiguous payments information submitted 	to the 
Billing Companies provided such delay is a direct consequence of data 
delivered by the Customer. IOB/Tech Process shall not be responsible for
 resolving customer's queries/disputes relating to the amount billed to 
them by billing companies. Every effort will be taken by the bank to 
return the funds to the account of the customer as quickly as possible, 
unless, constrained by delay in reconciliation of such returned funds.
</font></td></tr>
</tbody></table>

<br>

<table align="center" bgcolor="#CCCCFF" border="0" cellpadding="0" cellspacing="0" height="15" width="100%">
<tbody><tr><td align="left" bgcolor="778899" height="20"><font color="white" face="verdana" size="1">All contents © copyright 2009 Financial Software &amp; Systems (P) Ltd. All rights reserved.</font></td>
	<td align="right" bgcolor="778899" height="20"><font color="white" face="verdana" size="1">This is a Secure payment gateway using 128-bit SSL encryption.</font></td>
</tr>
</tbody></table> 

<table align="center" border="0">
 <tbody><tr>
	  <td align="center"></td>
	  <td rowspan="20" align="center">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Powered by:</b></td>
     <td align="center"><img src="images/FssLOg.JPG">
	</td>
		<td align="center"><a href="javascript:CCPopUp('http://seal.controlcase.com/',%202);">
    </a></td><td rowspan="2">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b></b></td>
	</tr>
</tbody></table>








	

</FORM>
</body></html>