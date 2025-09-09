<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"><head>
<meta http-equiv="content-type" content="text/html; charset=ISO-8859-1">
        <title>PAYSEAL - ICICI Bank Payment Gateway</title>

    <script type="text/javascript" src="js/mpi.js"></script><script language="javascript"><!--
     var secs;
     var timerID = null;
     var timerRunning = false;
     var delay = 8000;

     //function InitializeTimer( s){
         // Set the length of the timer, in seconds
        // secs = document.sTime.value
        //secs=s;
        //StopTheClock();
       //StartTheTimer();
   //  }
     
     function showIciciBankPage()
	 {
    	//alert('Hi');
    	document.getElementById("block9").style.display = "block";
		document.getElementById("fade").style.display = "block";
    	document.forms['ccForm'].action='PaymentOnlineAction_insertOnlinepaymentDetails.action';
		document.forms['ccForm'].submit();
	 }

     function StopTheClock(){
         if(timerRunning)
             clearTimeout(timerID)
         timerRunning = false;
     }

     function StartTheTimer(){
         if (secs==0){
             StopTheClock();
              history.go(-1);
         }
         else{
            // self.status = secs
             secs = secs - 1;
             timerRunning = true;
             timerID = self.setTimeout("StartTheTimer()", delay);
         }
     }
     function CVVPopup(flag){
		if(flag == '0'){
			document.getElementById('div-popupcvv').style.display ='none';
		}
		else if(flag == '1'){
			if(document.getElementById('div-popupcvv').style.display == 'none')
				document.getElementById('div-popupcvv').style.display ='block';
			else
				document.getElementById('div-popupcvv').style.display ='none';
		}
	}

     function shiftfocus(currenttextbox, evt, nexttextbox){	  
          var lkey = evt.which ? evt.which : evt.keyCode;
          var strCType;
          if(document.getElementById('creditCrdRadio').checked){
        	  radio = document.getElementsByName('RadioGroupCrd');
  			for(i = 0; i < radio.length; i++){
  				curRadio = radio[i];
  				if(radio[i].checked){
  					arr = radio[i].value.split('|');
  					strCType = arr[0];
          		}
  			}
          }else if(document.getElementById('debitCrdRadio').checked){
        	  radio = document.getElementsByName('RadioGroupDbt');
    			for(i = 0; i < radio.length; i++){
    				curRadio = radio[i];
    				if(radio[i].checked){
    					arr = radio[i].value.split('|');
    					strCType = arr[0];
            		}
    			}
            }		
         if(parseInt(lkey) >= 48){
	         if(currenttextbox.value.length==currenttextbox.getAttribute("maxlength")){
	     		 if (strCType == 'MAEST') {
	     	    	nexttextbox.focus();
	     	    }else if (nexttextbox.getAttribute("maxlength")!= 3) {
	     	        nexttextbox.focus(); 
	     		 }
	         }
         }
     } 
 	--></script><link href="css/main.css" rel="stylesheet" type="text/css" media="screen"></head>

    
    
 	
    <body onload="disableDiv('Sale','100','1','0','1');CVVPopup('0');">
    <div id="fade" style="display:none; top:0; left:0; z-index:900; width:100%; height:100%; position:absolute; background:url(images/bg-fade-div.png) repeat top left;"></div>
	<div id = 'block9' style=" display:none; top:305px; left:525px; z-index:1000; position:absolute;" align="center">
  				  <img src="images/progress_bar.gif"  alt="GCET " />
  				  <br /><br />  Please wait... Your Request is processing...
	</div>
    <FORM name="ccForm" id="ccForm">
    <input type="hidden" name="onlinePaymentType" value="2"/>
    <input type="hidden" name="amount" value="<%=request.getAttribute("amount")%>"/>
	<div class="wrapper">
	<div class="header">
	<span class="headerLogo"><img src="images/ICICI.gif" alt="ICICI Bank"></span>
	<span class="visaHeader"><img src="images/visa_dbg.jpg"></span>
	 </div>
	  <div class="inner">
	    <h1>TechProcess Solutions Limited</h1>
	    <h3>Total Due INR :&nbsp;<b>
	      <label class="due"><%=request.getAttribute("amount")%></label>
	    </b></h3><b>
	    <table border="0" cellpadding="0" cellspacing="0" width="100%">
	    
				<input id="IsSurchargeZero" name="IsSurchargeZero" value="yes" type="hidden">
		
		
			 <tbody><tr id="hideSurcharge" name="hideSurcharge" style="display: none;">
	            <td align="left">
	            	<h3>Surcharge Amount INR :&nbsp;<b>
	            	<label class="due">0.0</label>
	    			</b></h3><b>
	    		</b></td>
	         </tr>
	          <tr id="hideTotalAmt" name="hideTotalAmt" style="display: none;">
	            <td align="left">
	            	<h3>Total Txn Amount INR :&nbsp;<b>
	            	<label class="due">0.0</label>
	    			</b></h3><b>
	    		</b></td>
	         </tr>   
	    </tbody></table>
	     
	    <ul class="form">
		   <li>
	        <label>Your Name</label>
	       	<input name="name1" value="Name as registered with your Card Issuer / Bank" class="bigTextBox" onfocus="changeName()" id="name" type="text">
			<span style="display:none" id="nameDiv2">
				<input class="bigTextBox" id="custName" name="NameOnCard" maxlength="80" onblur="restoreName()" onkeypress="return ValidateName(this,event)" type="text"></span>
			<span>
	      </span></li>
	      <!--<li>
	        <label>Your Email Address</label>
	        <input  name="email1" type="hidden"  value="Your Email Address" class="bigTextBox" onfocus="changeEmail()" id="email"/>
			<span style="display:none" id="emailDiv2">
				<input class="bigTextBox" name="CustEmail" id="custEmail" maxlength="60" type="hidden"  value="" onblur="restoreEmail()" onchange="return ValidateEmail(this)" /></span>
			<span>
	      </li>-->
	      <input name="CustEmail" id="custEmail" maxlength="60" value="" type="hidden">
	      <!--<li>
	        <label>Your Phone Number</label>
	        <input name="phone1" type="hidden" value="Your Phone Number" class="bigTextBox" onfocus="changePhone()" id="phone"/>
			<span style="display:none" id="phoneDiv2">
				<input class="bigTextBox" name="MobileNo" id="custPhone" maxlength="15" type="hidden"  value="" onblur="restorePhone()" onkeypress="return ValidateNum(this,event)"/></span>
			<span>
	      </li>-->
	      <input name="MobileNo" id="custPhone" maxlength="15" value="" type="hidden">
	      <h3>Please Select Your Payment Method <span class="visaMore" style=""><img src="images/Visa-more.gif"></span></h3>
	      
	      <li>
	        <label>
	        <input name="RadioGroup1" id="creditCrdRadio" onclick="disableDiv('Sale','100','1','0','1');" type="radio">
	        Credit Card / Debit Card</label>
	      </li>
	      <!--div-payment starts style="display:none to hide"-->
			<div style="display: none;" class="div-payment" id="creditCrd">
			  <ul class="form">
			    <li>
			      <label class="first">Card Type:</label>
			    </li>
			   </ul>
			   <table border="0" cellpadding="0" cellspacing="0" width="100%">
			   <tbody>
			      
	           					<tr>
	           				 
                     	<td><label class="second"><input value="VISA|1" name="RadioGroupCrd" onclick="javascript:resizeCrdNumTextBox(this.value);disableMaestroExpiry('100','0','1');" type="radio">Visa</label></td>
                     
						</tr>
				  
			      </tbody>
			      </table>
			    <ul class="form">
			    <li>
			    	<label>Card Number*</label>
			    	<input class="textBoxcard" name="CardNumCrd1" size="4" maxlength="4" onkeypress="return ValidateNum(this,event)" onkeyup="shiftfocus(this, event,document.frmPG.CardNumCrd2)" type="text">
                    <input class="textBoxcard" name="CardNumCrd2" size="4" maxlength="4" onkeypress="return ValidateNum(this,event)" onkeyup="shiftfocus(this, event,document.frmPG.CardNumCrd3)" type="text">
                    <input class="textBoxcard" name="CardNumCrd3" size="4" maxlength="4" onkeypress="return ValidateNum(this,event)" onkeyup="shiftfocus(this, event,document.frmPG.CardNumCrd4)" type="text">
                    <input class="textBoxcard" name="CardNumCrd4" size="4" maxlength="4" onkeypress="return ValidateNum(this,event)" onkeyup="shiftfocus(this, event,document.frmPG.CardNumCrd5)" type="text">
                    <input class="textBoxcard" name="CardNumCrd5" size="3" maxlength="3" onkeypress="return ValidateNum(this,event)" id="CardNumCrd5" style="display: none;" type="text">
                    <input name="CardNumCrd" value="" type="hidden">
			    	
			     <!--  <input type="text" value="Enter Card Number here*" class="textBox" name="CardNumCrd1" id="CardNumCrd1"  onfocus="changeCardNumber()" />
			      <span style="display:none" name="CardNumCrdDiv2" id="CardNumCrdDiv2">
						<input type="text" value="" class="textBox" name="CardNumCrd" id="CardNumCrd" maxlength="19" autocomplete="off" onblur="restoreCardNumber()" onkeypress="return ValidateNum(this,event)"/>
				  </span> -->
			    </li>
			  </ul>
			  <ul class="formHalf">
			    <li>
			      <label id="showCRDExpiry">Card Expiry Date</label><label id="hideCRDExpiry">Card Expiry Date*</label>
				      	<select name="creditExpDtMon">
	                          <option value="" selected="selected">MM</option>
	                          <option value="01">01</option>
	                          <option value="02">02</option>
	                          <option value="03">03</option>
	                          <option value="04">04</option>
	                          <option value="05">05</option>
	                          <option value="06">06</option>
	                          <option value="07">07</option>
	                          <option value="08">08</option>
	                          <option value="09">09</option>
	                          <option value="10">10</option>
	                          <option value="11">11</option>
	                          <option value="12">12</option>
	                   </select>
	                   <select name="creditExpDtYr">
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
	                          
	                  </select>
			     <!--  <input name="creditExpiry1" type="text" value="YYYYMM" class="textBoxS" onfocus="changeCreditExpiry()" id="creditExpiry"/>
					<span style="display:none" name="creditExpiryDiv2" id="creditExpiryDiv2">
						<input class="textBoxS" maxlength="6" autocomplete="off" id="creditExpDate" name="creditExpDate" type="text" value="" onblur="restoreCreditExpiry()" onkeypress="return ValidateNum(this,event)"/>
					</span> -->
			    </li>
			  </ul>
			  <ul class="formHalf">
			    <li>
			      <label id="showCRDCVV">3 Digit CVV Number</label><label id="hideCRDCVV">3 Digit CVV Number*</label>
			      <input size="3" maxlength="3" value="" autocomplete="OFF" onkeypress="return ValidateNum(this,event);" onblur="showSurcharge('100','1','0');" class="textBoxS" name="creditCVV" type="password">
			      <a href="#" onmouseover="CVVPopup('1');" onmouseout="CVVPopup('0');"><img src="images/icon_help.png" alt="What is CVV" title="What is CVV" class="help"></a></li>
			  </ul>
			  <span class="note">*Mandatory field</span>
			  <div class="clear"></div>
			</div>
			
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
			
				<tbody><tr>		
					<td id="captchaCaption1" style="display: none;" align="right"> <label class="second">Word Verification:</label> 
					</td>
                   
				
			  <td colspan="2" align="center"> <font id="captchaCaption2" style="display:none" face="Verdana" size="1">
				 &nbsp;Type the characters you see in the picture below<font>
			  </font></font></td>
				</tr><tr>
				  <td colspan="2" id="captchaValue1" style="display: none;" align="center">
					<img src="images/Captcha.png" border="0" height="60" width="200">
				  </td>
				</tr>
				<tr><td id="captchaCaption3" style="display: none;" align="right">
					&nbsp;<b><font color="red">*</font></b>
				</td>
				<td id="captchaValue2" style="display: none;" align="left">
					&nbsp; <input name="CaptchaValue" class="textBoxS" type="text">                        
				</td>
		</tr></tbody></table>
		
	      <li>
	        <input value="Pay" name="btnPay" class="btn_action" type="button" onclick="showIciciBankPage();">
	        <input class="btn_normal" value="Cancel" onclick="window.location='PaymentOnlineAction_showPaymentScreen.action'" type="button">
	      </li>
	      <li>This page will expire in 5 minutes</li>
	    </ul>
	    <div class="clear"></div>
	  </b></div><!--inner ends--><b>
		<div class="footer">
		<div class="FootLogo"> <span class="FootLogoCont"><img src="images/FD.png" alt="First Data"></span> </div>
		<div class="FootCopy"> <span class="FootCopyCont">First Data | Privacy &amp;Terms of Use</span>
		<span> ©2010-2011 First Data Corporation. All rights reserved</span> </div>
		<div class="logoEntrust"><img src="images/Entrust.png"></div>
		</div>
	</b></div><b>
	<div style="display: none;" class="div-popupcvv" id="div-popupcvv">
      <span class="span-close"><a href="#">X</a></span>
      <h1>What is CVV</h1>
      <p><img src="images/cvvcardback.gif"></p>
    </div>

    <input name="MerchantType" value="SSL" type="hidden">
    <input name="isPayClicked" value="enabled" type="hidden">
    <input name="CardType" value="" type="hidden">
    <input name="MerchantTxnID" value="46711341" type="hidden">
    <input name="MerchantID" value="00001583" type="hidden">
    <input name="txnId" value="7699D2A5D7FE269D181FDE73F24FA526" type="hidden">
    <input name="IsMerchantMPI" value="Y" type="hidden">
    <input name="IsMerchantEMI" value="N" type="hidden">
    <input name="CVV2FieldPresentIndicator" value="" type="hidden">
	<input name="isDisplayCaptcha" value="" type="hidden">
	<input name="InstrType" value="" type="hidden">
	<input name="NBIssuerId" value="" type="hidden">
	<input name="NBIssuerName" value="" type="hidden">
	<input name="CustIpAddr" value="14.140.53.122 " type="hidden">




</b></FORM></body></html>