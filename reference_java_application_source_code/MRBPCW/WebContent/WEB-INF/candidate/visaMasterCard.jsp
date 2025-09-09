<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"><head>
<meta http-equiv="content-type" content="text/html; charset=ISO-8859-1">
        <title> State Bank of India Secure Payment Gateway</title>

    <script type="text/javascript" src="js/mpi1.js"></script><script type="text/javascript">

 // Added for browser close event Start
	// version 0.1 Start// 
		//javascript:window.history.forward(1);
		//var temp = true;
		//document.onkeydown = keyDownPress;
		//document.onmousedown = keyDownPress;
		//window.onbeforeunload = confirmExit;		
	
	function keyDownPress(e)
	{
		var evt = e || window.event;
		var keyPressed = evt.which || evt.keyCode;
		
		if (keyPressed==116) {
			temp=false;
			return false;
		}
		
		if(evt.button==2) {
			alert("For security reasons, Right click has been disabled!");
			return false;
		}
	}
	
	function confirmExit(e){
		if(temp==true ){
			alert("You are going to cancel the transaction");
			document.frmPG.isCancelClicked.value = "cancel";				
			document.frmPG.submit();	
		}
	}

	// version 0.1 End// 
    

    function mouseOver()
    {
        document.b1.src ="images/bob_images/pay_mouseover.gif";
    }
    function mouseOut()
    {
        document.b1.src ="images/bob_images/pay_mouseout.gif";
    }
    function cancel_mouseOver()
    {
        document.b2.src ="images/bob_images/cancel_mouseover.gif";
    }
    function cancel_mouseOut()
    {
        document.b2.src ="images/bob_images/cancel_mouseout.gif";
    }
    </script><script type="text/javascript">
	// Added for log Txn Cancel clicked button.
	function cancelPage()
	{
		temp = false;
		document.frmPG.isCancelClicked.value = "cancel";
		document.frmPG.submit();
	}
	function submitPage()
	{
		temp = false;	
  	     if ( chkFill())  {
  	     		document.frmPG.btnPay.disabled = true; 	//Version 0.3
           		document.frmPG.submit();
   		 }
   		 else{
			temp = true;
		}
    }
	function showIciciBankPage()
	 {
   
		document.getElementById("block9").style.display = "block";
        document.getElementById("fade").style.display = "block";
        
   		document.forms['ccForm'].action='PaymentOnlineAction_insertOnlinepaymentDetails.action';
		document.forms['ccForm'].submit();
	 }
    </script><script language="Javascript">
        // version 0.3 Start (new functionality added)//
        	var countdown;
        	var countdown_number;
        
        	function countdown_init() {
        	    countdown_number = 60000;
        	    countdown_trigger();
        
        	}
        
        	function countdown_trigger() {
        	    if(countdown_number > 0) {
        	        countdown_number--;
        	        document.getElementById('countdown_text').innerHTML = countdown_number;
        	        if(document.getElementById('countdown_text').innerHTML==0){
        	        	temp=false;
        	        	document.frmPG.isCancelClicked.value = "cancel";
    			document.frmPG.submit();
               	 }
        	        if(countdown_number > 0) {
        	            countdown = setTimeout('countdown_trigger()', 60000);
           	        }
        	    }
        	}
        
        	function countdown_clear() {
        	    clearTimeout(countdown);
    	}
    	// 0.3 End//
    </script></head>
    
    

    
     
    <body onload="showSurcharge(),InitializeTimer(600),countdown_init()"><div id="countdown_text" style="color: #FFFFFF;">583</div>
    <div id="fade" style="display:none; top:0; left:0; z-index:900; width:100%; height:100%; position:absolute; background:url(images/bg-fade-div.png) repeat top left;"></div>
	<div id = 'block9' style=" display:none; top:305px; left:525px; z-index:1000; position:absolute;" align="center">
  				  <img src="images/progress_bar.gif"  alt="GCET " />
  				  <br /><br />  Please wait... Your Request is processing...
	</div>
	<FORM name="ccForm" id="ccForm">
	<input type="hidden" name="onlinePaymentType" value="2"/>
	<input type="hidden" name="amount" value="<%=request.getAttribute("amount")%>"/>
	<input type="hidden" name="creditCardType" value="<%=request.getAttribute("creditCardType")%>"/>
    <script language="javascript">

        bname=navigator.appName

        if (bname.indexOf("Microsoft")!=-1)
        {
            document.write("<STYLE type=text/css>");
            document.write(" input  ");
            document.write(" {  ");
            document.write("         font-family:    verdana,times new roman;");
            document.write("          font-size:      9pt;");
            document.write("         border-color:   #990000;");
            document.write("         font-weight:    none;");
            document.write("         border-style: solid;");
            document.write("         border-top-width: 1px;");
            document.write("         border-right-width: 1px;");
            document.write("         border-bottom-width: 1px;");
            document.write("         border-left-width: 1px;");
            document.write(" }");
            document.write(" select ");
            document.write(" {  ");
            document.write("         font-family:    verdana,times new roman;");
            document.write("         font-size:      8pt;");
            document.write("         border-color:   #990000;");
            document.write("         font-weight:    none;");
            document.write("         border-style: solid;");
            document.write("         border-top-width: 1px;");
            document.write("         border-right-width: 1px;");
            document.write("         border-bottom-width: 1px;");
            document.write("         border-left-width: 1px;");
            document.write(" } </STYLE>");
        }
<!-- Changes made by Sanjiv for ELTOPUSPRD-18498 -->
function shiftfocus(currenttextbox, evt, nexttextbox)
{
    var lkey = evt.which ? evt.which : evt.keyCode;
    if(parseInt(lkey) >= 48)
    if(currenttextbox.value.length==currenttextbox.getAttribute("maxlength"))
    nexttextbox.focus();

}
    </script>

    <link rel="stylesheet" type="text/css" href="css/forms.css">
    <link rel="stylesheet" type="text/css" href="css/fonts.css">

    <script language="JavaScript">

     var secs
     var timerID = null
     var timerRunning = false
     var delay = 1000

     function InitializeTimer( s)
     {
         // Set the length of the timer, in seconds
        // secs = document.sTime.value
         secs=s
         StopTheClock()
         StartTheTimer()
     }

     function StopTheClock()
     {
         if(timerRunning)
             clearTimeout(timerID)
         timerRunning = false
     }

     function StartTheTimer()
     {
         if (secs==0)
         {
             StopTheClock()
              history.go(-1);
         }
         else
         {
            // self.status = secs
             secs = secs - 1
             timerRunning = true
             timerID = self.setTimeout("StartTheTimer()", delay)
         }
     }
	 
	 // Added By Nilesh Kinholkar
	function on_cardTypeSelect(){
	        
	           // version 0.2 Start//  
	          document.frmPG.CardNum.value="";
		  document.frmPG.ExpDtMon.value="";
		  document.frmPG.ExpDtYr.value="";
		  document.frmPG.CVVNum.value="";
		  document.frmPG.NameOnCard.value="";
	          document.frmPG.CaptchaValue.value="";
	           
	           
         	   var visibilityStatus = 'visible';
		   var cardtypeselected = document.frmPG.CardTypeSelectBox.value;
		   var arr = cardtypeselected.split('|');
		   var cardType = arr[0];

		  if(cardType=='SBIME'){
			
			visibilityStatus = "none";
			
		  }else{			
			 visibilityStatus = '';
			
		  }
		   if (document.getElementById) { // DOM3 = IE5, NS6 	   
		              
				document.getElementById('hideExp1').style.display = visibilityStatus;
				document.getElementById('hideExp2').style.display = visibilityStatus;
				document.getElementById('hideExp3').style.display = visibilityStatus;
					
			} 
			else { 
				if (document.layers) { // Netscape 4 
				
					document.hideExp1.style.display = visibilityStatus; 
					document.hideExp2.style.display = visibilityStatus; 
					document.hideExp3.style.display = visibilityStatus; 
				} 
				else { // IE 4 
				
					document.all.hideExp1.style.display =visibilityStatus; 
					document.all.hideExp2.style.display =visibilityStatus; 
					document.all.hideExp3.style.display =visibilityStatus; 
				} 
			} 
	 }
     
 	 // version 0.2 End//
     </script>

<style>
p.picaone {
  font-size: 1.5em;
}
</style>



    

    

<table align="center" bgcolor="#FFFFFF" border="1" cellpadding="0" cellspacing="0">
<tbody><tr>
    <td bgcolor="#FFFFFF">

        <table border="0" cellpadding="0" cellspacing="0" width="960">
        <tbody><tr>
            <td>
                <img src="images/sbi_logo_main.gif" alt="State Bank of India" border="0" hspace="15" vspace="0"><br clear="all">
            </td>
            <td align="right" width="456">
                <!-- <IMG SRC="images/bob_images/verisign-logo.gif" WIDTH="111" HEIGHT="87" HSPACE="15" VSPACE="0" BORDER="0" ALIGN="Right"> -->
       <!-- <div id="digicertsitesealcode" style="width: 81px; margin: 20px 0 0 10px;">
                    <script language="javascript" type="text/javascript" src="https://www.digicert.com/custsupport/sealtable.php?order_id=00154148&amp;seal_type=a&amp;seal_size=large&amp;seal_color=green"></script>
                    <span>&nbsp;</span>
                    <script language="javascript" type="text/javascript">coderz();</script>
        </div>-->
        
            </td>
            <br clear="all"></br>
        </tr>
        <tr>
            <td colspan="2" bgcolor="#FFFFFF">
                <img src="images/blank.gif" border="0" height="1" hspace="0" vspace="0" width="1"><br clear="all">
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center" width="960">
                <table align="center" border="0">
                <tbody><tr>
                <td bgcolor="#FFFFFF" width="202"></td>
                <td bgcolor="#FFFFFF" valign="middle" width="535">
                <span class="body">
                    <b> <font color="black"> <p class="picaone">Welcome to State Bank of India's Secure Payment Gateway</p></font> </b>
              </span>
              </td>
            <td bgcolor="#FFFFFF" width="230"></td>
            </tr>
            </tbody></table>
            </td>
        </tr>
        </tbody></table>

        <table align="right" border="0" cellpadding="10" cellspacing="0" width="760">
        <tbody><tr>
            <td>
                <span class="body">
                    Dear Customer,<br>
                    SBI Payment Gateway will secure your payment to <span class="red"><b>Software Testing_TPSL.</b>
                    </span><br clear="all"><br>
                    <table id="ssltab" bgcolor="#FFFFFF" border="0" cellpadding="5" cellspacing="1" width="740">
                    <tbody><tr valign="top">
                         <td bgcolor="#FFFFFF">
                            <span class="blackbody">                               
                             <b>Select the type of card*</b>
                            </span>
                        </td>
                        <td bgcolor="#FFFFFF">
                            <select name="CardTypeSelectBox" class="select" onchange="return on_cardTypeSelect(); ">
                               <option selected="selected" value="SELECTED"> -- Select -- </option>
                                    
                                                <option value="MAEST|1">Other MAESTRO Cards</option>
                                    
                                                <option value="MC|1">Mastercard</option>
                                    
                                                <option value="SBIME|0">State Bank MAESTRO Cards</option>
                                    
                                                <option value="VISA|1">Visa</option>
                                    

                            </select>
                        </td>
                        <td rowspan="8" valign="top" width="211">
                            <img src="images/blank.gif" align="left" border="0" height="130" hspace="0" vspace="0" width="1"><br clear="all">
                            <img id="hideExp3" src="images/cvv.gif" alt="Credit Card CV No." border="0" height="126" hspace="0" vspace="0" width="211"><br clear="all">
                        </td>
                    </tr>
                    <tr valign="top">
                        <td bgcolor="#FFFFFF">
                            <span class="blackbody">
                            	<b>Card Number *</b>
                            </span>
                        </td>
                        <td bgcolor="#FFFFFF">
                         <!-- Changed by Nilesh Kinholkar -->
                         <input size="25" maxlength="19" name="CardNum" onkeypress="return ValidateNum(this,event)" type="text"><br>
                            <span class="sslText">(Please enter your card number without any spaces)</span>
                        </td>
                    </tr>
                    <tr id="hideExp1" valign="top">
                         
                        <td bordercolordark="#016B98" bgcolor="#FFFFFF">
                            <span class="blackbody">
                                <b>Expiry Date *</b>
                            </span>
                        </td>
                        <td bgcolor="#FFFFFF">
                            <select name="ExpDtMon" class="select">
                                <option value="" selected="selected">MM</option>
                                <option value="01">Jan</option>
                                <option value="02">Feb</option>
                                <option value="03">Mar</option>
                                <option value="04">Apr</option>
                                <option value="05">May</option>
                                <option value="06">Jun</option>
                                <option value="07">Jul</option>
                                <option value="08">Aug</option>
                                <option value="09">Sep</option>
                                <option value="10">Oct</option>
                                <option value="11">Nov</option>
                                <option value="12">Dec</option>
                            </select>
                            <select name="ExpDtYr" class="select">
                                 <option value="" selected="selected">YYYY</option>
                            
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
                            
                            </select>
                            <br>
                            <span class="sslText">(Please enter expiry date provided on your card)</span>
                        </td>
                    </tr>
                    <tr id="hideExp2" valign="top">
                        <td bordercolordark="#016B98" bgcolor="#FFFFFF">
                            <span class="blackbody">
                                <b>CVV2 / CVC2 Number *</b>
                            </span>
                        </td>
                        <td bgcolor="#FFFFFF">
                            <input class="inputbox" name="CVVNum" size="3" maxlength="3" value="" autocomplete="off" onkeypress="return ValidateNum(this,event)" type="password">
                            <span class="sslText">
                            <br>(CVV2 / CVC2 is the three digit security code printed on the back of card)</span>
                        </td>
                    </tr>
                    <tr valign="middle">
                        <td bgcolor="#FFFFFF">
                            <span class="blackbody">
                                <b>Name on Card</b>
                            </span>
                        </td>
                        <td bgcolor="#FFFFFF">
                            <input class="inputbox" name="NameOnCard" size="20" maxlength="50" onkeypress="return ValidateName(this,event)" type="text">
                        </td>
                    </tr>
                    <tr valign="middle">
                        <td bgcolor="#FFFFFF">
                            <span class="blackbody">
                                <b>Purchase Amount</b>
                            </span>
                        </td>
                       <td bgcolor="#FFFFFF">
                            <span class="body"> &nbsp;<b>INR &nbsp;&nbsp;<%=request.getAttribute("amount")%></b> </span>
                        </td>
                    </tr>
                    
					
						<input id="IsSurchargeZero" name="IsSurchargeZero" value="yes" type="hidden">
					

				 	<tr id="hideSurcharge" name="hideSurcharge" style="display: none;" valign="middle">
                        <td bgcolor="#FFFFFF">
                            <span class="blackbody">
                                <b>Surcharge Amount</b>
                            </span>
                        </td>
                        <td bgcolor="#FFFFFF">
                            <span class="body"> &nbsp;<b>INR &nbsp;&nbsp;<b>0.0</b> </b></span><b>
                        </b></td>
                    </tr>

					<tr id="hideTotalAmt" name="hideTotalAmt" style="display: none;" valign="middle">
                        <td bgcolor="#FFFFFF">
                            <span class="blackbody">
                                <b>Total Txn Amount</b>
                            </span>
                        </td>
                        <td bgcolor="#FFFFFF">
                            <span class="body"> &nbsp;<b>INR &nbsp;&nbsp;<b>0.0</b> </b></span><b>
                        </b></td>
                    </tr>
					
					<tr valign="top">
                        <td bgcolor="#FFFFFF">
                            <span class="blackbody">
                                <b>Word Verification *</b>
                            </span>
                        </td>
                        <td bgcolor="#FFFFFF">
                    

                             <input class="inputbox" name="CaptchaValue" size="7" maxlength="7" type="text">  <br>
                             <span>Type the characters you see in the picture below</span><br clear="all">
                             <img src="images/Captcha1.png" "width="183" border="0" height="52">
                             <br>

                        </td>

                    </tr>
                    <tr>
                        <td colspan="2" align="right" bgcolor="">
						
                           <!-- <a href="#" onclick="javascript:submitPage();" onmouseover="mouseOver()" onmouseout="mouseOut()">
					
				<!-- <img border="0" alt="Pay"src="images/bob_images/pay_mouseout.gif" name="b1" /></a>  -->
					
					  <input style="margin-top: 25px; margin-bottom: 25px; height: 25px; width: 70px; font-family: 'calibri'; font-size: 17px; background-color: rgb(229, 103, 23); color: white; border-right: 1px solid white; border-left: 1px solid white; border-width: 0px 1px; border-style: solid; border-color: white white red;" value="Pay" name="btnPay" onclick="showIciciBankPage();" type="button"> <!--Version 0.3-->
                          
				<!-- <a href="#" onclick="javascript:cancelPage();"onmouseover="cancel_mouseOver()"onmouseout="cancel_mouseOut()">
					 
                <!-- <img border="0" alt="Cancel"src="images/bob_images/cancel_mouseout.gif" name="b2" /></a> -->
					
					 <input style="margin-top: 25px; margin-bottom: 25px; height: 25px; width: 70px; font-family: 'calibri'; font-size: 17px; background-color: rgb(229, 103, 23); color: white; border-right: 1px solid white; border-left: 1px solid white; border-width: 0px 1px; border-style: solid; border-color: white white red;" value="Cancel" name="btnCancel" onclick="window.location='PaymentOnlineAction_showPaymentScreen.action'" type="button"> <!--Version 0.3-->
					
					  

                        </td>
                    </tr>
                    
      </tbody></table>

                    <p class="redsmall" align="center"><b></b></p><div id="hidePay"><b>AFTER PRESSING PAY, KINDLY DO NOT REFRESH AND DO NOT PRESS THE BACK BUTTON.</b><div><b><br></b>

                    
                    <p class="redsmall" align="center"><b></b></p><div id="hidePay"><b>ALL INTERNET BASED TRANSACTIONS USING STATE BANK DEBIT CARDS WOULD NEED A 3D SECURE PASSWORD.<br>
<!--To register for your 3D Secure password please visit any of the State Bank Group ATM and register your mobile number by choosing &#8220;<FONT COLOR=red> Mobile Registration for SMS / Secure Code / IMPS </FONT>&#8221; on the ATM Screen.<br><br>-->

<!--The second stage of the registration can be completed after 24 hrs. by visiting onlinesbi.com / statebankofindia.com /sbi.com and follow the instructions given in the site.<div><br></B>-->
                    

                
            </b></div></div></div></span></td>
        </tr>
        </tbody></table>
    </td>
</tr>
<tr valign="top">
    <td bgcolor="#FFFFFF">
        <center><br>
            <a href="" target="_new"><img src="images/visa-logo.gif" alt="VISA" border="0" height="33" hspace="0" vspace="10" width="73"></a>
            <a href="" target="_new"><img src="images/mastercard-logo.gif" alt="MasterCard" border="0" height="32" hspace="30" vspace="10" width="83"></a>
            
            <a href="" target="_new"><img src="images/hackersafe-logo.gif" alt="Hacker Safe" border="0" height="39" hspace="0" vspace="4" width="85"></a><br clear="all"><br>
        </center>

        <table border="0" cellpadding="5" cellspacing="0" width="100%">
        <tbody><tr>
            <td align="center" bgcolor="#52AEC6">
                <span class="body">
                    <b>A service brought to you by State Bank of India</b>
                </span>
            </td>
        </tr>
        <tr>
            <td align="left" bgcolor="#FFFFFF">
                <span class="bodysmall">
                    <b>Note:</b> This page will expire in 10 minutes and
 if you fail to complete the transaction in 10 minutes you will be 
redirected to the order page of &nbsp;Software Testing_TPSL<br>

                    <p>If you wish to discontinue with the order, 
request you to click on Cancel button, you will be redirected to the 
order page of &nbsp;Software Testing_TPSL.
                
            </p></span></td>
        </tr>
        </tbody></table>
    </td>
</tr>
</tbody></table>

    <input name="MerchantType" value="SSL" type="hidden">
    <input name="isPayClicked" value="enabled" type="hidden">

    <input name="CardType" value="" type="hidden">
    <input name="MerchantTxnID" value="46712298" type="hidden">
	<!--  Added for log Txn Cancel clicked button. -->
	<input name="isCancelClicked" value="pay" type="hidden">




    <input name="MerchantID" value="00001468" type="hidden">
    <input name="txnId" value="B896F7FDECCD70C596484C8B7FC10612B2A2BEECC764CE95" type="hidden">
    <input name="IsMerchantMPI" value="Y" type="hidden">
    <input name="CVV2FieldPresentIndicator" value="" type="hidden">

</FORM>
</body></html>