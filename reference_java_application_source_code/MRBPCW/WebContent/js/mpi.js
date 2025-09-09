function chkFill(config,isCredCrdAssinged,isDbtCrdAssinged,isNetBnkAssigned){
	var creditAssigned = parseInt(config.substring(0,1)) == 1;
	var debitAssigned = parseInt(config.substring(1,2)) == 1;
	var netBankAssigned = parseInt(config.substring(2,3)) == 1;
	var isCredCrdAssinged = parseInt(isCredCrdAssinged) == 1;
	var isDbtCrdAssinged = parseInt(isDbtCrdAssinged) == 1;
	var isNetBnkAssigned = parseInt(isNetBnkAssigned) == 1;
    var displayCaptcha = "yes" ;
	if(document.frmPG.isPayClicked.value == "enabled"){
			var frm = document.frmPG ;
			var radioChecked = false;
			var arr;
			var strCType;
			var strMandatory;
			var radio;
			var curRadio;
		if(creditAssigned && isCredCrdAssinged && document.getElementById('creditCrdRadio').checked){
			radio = document.getElementsByName('RadioGroupCrd');
			for(i = 0; i < radio.length; i++){
				curRadio = radio[i];
				if(radio[i].checked){
					arr = radio[i].value.split('|');
					strCType = arr[0];
					strMandatory = arr[1];
					
					document.frmPG.CardNumCrd.value = document.frmPG.CardNumCrd1.value+document.frmPG.CardNumCrd2.value+document.frmPG.CardNumCrd3.value
	                +document.frmPG.CardNumCrd4.value ;

					if( strCType == "MAEST") {
						document.frmPG.CardNumCrd.value = document.frmPG.CardNumCrd.value + document.frmPG.CardNumCrd5.value ;
					}
					if(validateCardNum(strCType,document.frmPG.CardNumCrd,displayCaptcha)){
						if(validateExpiry(strCType,document.frmPG.creditExpDtMon,document.frmPG.creditExpDtYr,displayCaptcha)){
							if(parseInt(strMandatory) == 1){      
								if(!validateCAVV(document.frmPG.creditCVV,displayCaptcha)){
									document.frmPG.creditCrdRadio.checked = true;
									curRadio.checked = true;
									return false;
								}
							}
							if(document.frmPG.creditCVV.value!=""){
								document.frmPG.CVV2FieldPresentIndicator.value='1';
							}else{
								document.frmPG.CVV2FieldPresentIndicator.value='0';
							}
						}else{
							document.frmPG.creditCrdRadio.checked = true;
							curRadio.checked = true;
							//document.getElementById('creditExpiryDiv2').style.display='none';
							//document.getElementById('creditExpiry1').style.display='';
							//document.frmPG.creditExpiry1.focus();
							return false;
						}
					}else{
						document.frmPG.creditCrdRadio.checked = true;
						curRadio.checked = true;
						return false;
					}
					radioChecked = true;
				}
			}
			if(!radioChecked){
				alert("Please select a Credit Card type to proceed.");
				document.frmPG.isDisplayCaptcha.value = displayCaptcha ;
				showCaptcha();
				return false;
			}
			document.frmPG.InstrType.value='CREDI';
			document.frmPG.CardType.value=strCType;
        }else if(debitAssigned && isDbtCrdAssinged && document.getElementById('debitCrdRadio').checked){
			radio =  document.getElementsByName('RadioGroupDbt');
			for(i = 0; i < radio.length; i++){
				curRadio = radio[i];
				if(radio[i].checked){
					arr = radio[i].value.split('|');
					strCType = arr[0];
					strMandatory = arr[1];
					
					document.frmPG.CardNumDbt.value = document.frmPG.CardNumDbt1.value+document.frmPG.CardNumDbt2.value+document.frmPG.CardNumDbt3.value
	                +document.frmPG.CardNumDbt4.value ;

					if( strCType == "MAEST") {
						document.frmPG.CardNumDbt.value = document.frmPG.CardNumDbt.value+document.frmPG.CardNumDbt5.value ;
					}
					if(validateCardNum(strCType,document.frmPG.CardNumDbt,displayCaptcha)){
						if(validateExpiry(strCType,document.frmPG.debitExpDtMon,document.frmPG.debitExpDtYr,displayCaptcha)){
							if(parseInt(strMandatory) == 1){      
								if(!validateCAVV(document.frmPG.debitCVV,displayCaptcha)){
									document.frmPG.debitCrdRadio.checked = true;
									curRadio.checked = true;
									return false;
								}
							}
							if(document.frmPG.debitCVV.value!=""){
								document.frmPG.CVV2FieldPresentIndicator.value='1';
							}else{
								document.frmPG.CVV2FieldPresentIndicator.value='0';
							}
						}else{
							document.frmPG.debitCrdRadio.checked = true;
							curRadio.checked = true;
							//document.getElementById('debitExpiryDiv2').style.display='none';
							//document.getElementById('debitExpiry1').style.display='';
							//document.frmPG.debitExpiry1.focus();
							return false;
						}
					}else{
						document.frmPG.debitCrdRadio.checked = true;
						curRadio.checked = true;
						return false;
					}
					radioChecked = true;
				}
				
			}
			if(!radioChecked){
				alert("Please select a Credit Card type to proceed.");
				document.frmPG.isDisplayCaptcha.value = displayCaptcha ;
				showCaptcha();
				return false;
			}
			document.frmPG.InstrType.value='DEBIT';
			document.frmPG.CardType.value=strCType;
		}else if(netBankAssigned && isNetBnkAssigned && document.getElementById('netBnkRadio').checked){
			radio = document.getElementsByName('RadioGroupIssuer');
			for(i = 0; i < radio.length; i++){
				curRadio = radio[i];
				if(radio[i].checked){
					document.frmPG.InstrType.value='NTBNK';
					arr = radio[i].value.split('|');
					document.frmPG.NBIssuerId.value = arr[0];
					document.frmPG.NBIssuerName.value = arr[1];
					radioChecked = true;
				}
			}
			if(!radioChecked){
				alert("Please select a Bank option to proceed.");
				document.frmPG.isDisplayCaptcha.value = displayCaptcha ;
				showCaptcha();
				return false;
			}			
		}else{
        	alert("Please select a Payment mode to proceed.");
			document.frmPG.isDisplayCaptcha.value = displayCaptcha ;
			showCaptcha();
            return false;
        }

        if(document.frmPG.isDisplayCaptcha.value=="yes" && document.frmPG.CaptchaValue.value==""){
			alert("Please enter word verification string. "); 
			return false;
        
        }

        if(document.getElementById('redirectedSSLCaptchaValue')!=null) {
    		if(document.getElementById('redirectedSSLCaptchaValue').value==""){
    			alert("Please enter word verification string.");
    			return false ;
    		}
    	}
        document.frmPG.isPayClicked.value="disabled";
        var hidePay="";
        hidePay.innerHTML = "Your Transaction is being processed by PaySeal. &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<br>You will  be redirected to merchant site for confirmation. Please wait...";
        document.frmPG.btnPay.disabled = true;
        return true;
    }else{
        alert("cannot click the button again transaction is in progress");
        return false;
    }
}
        
        //Changes made by Sanjiv for ELTOPUSPRD-18498
        function ValidateNum(input,evt){
            var keyCode = evt.which ? evt.which : evt.keyCode;
            var lisShiftkeypressed = evt.shiftKey;
            if(lisShiftkeypressed && parseInt(keyCode) != 9) {
            return false ;
            }
            if((parseInt(keyCode)>=48 && parseInt(keyCode)<=57) || keyCode==37/*LFT ARROW*/ || keyCode==39/*RGT ARROW*/ || keyCode==8/*BCKSPC*/ || keyCode==46/*DEL*/ || keyCode==9/*TAB*/){
            return true;
            }
            return false;
        }
        
        function ValidateName(input,event){
            var keyCode = event.which ? event.which : event.keyCode;
            //Small Alphabets
            if(parseInt(keyCode)>=97 && parseInt(keyCode)<=122){
                return true;
            }
            //Caps Alphabets
            if(parseInt(keyCode)>=65 && parseInt(keyCode)<=90){
                return true;
            }
            //Space-Return-Dot<!-- Changes made by Sanjiv for ELTOPUSPRD-18498 -->
            if(parseInt(keyCode)==32 || parseInt(keyCode)==13 || parseInt(keyCode)==46 || keyCode==9/*TAB*/ || keyCode==8/*BCKSPC*/ || keyCode==37/*LFT ARROW*/ || keyCode==39/*RGT ARROW*/ ){
                return true;
            }
            input.focus();
            return false;
            //alert(keyCode);
        }
        
        function isCardTypeValid(astrCardNum, astrCardType){
            var cardNumLength = astrCardNum.length;
			var isValidCardType = false;
            if( astrCardType == "MC") {
                var strMasterCard = astrCardNum.substring(0,2);
                if(((strMasterCard == "50") || (strMasterCard == "51") || (strMasterCard == "52") || (strMasterCard == "53") ||
                    (strMasterCard == "54") || (strMasterCard == "55")) &&  cardNumLength ==16 ) {
                        isValidCardType = true;
                    }
            } else if( astrCardType == "VISA") {
                var strVisa = astrCardNum.substring(0,1);
                if ((strVisa == "4") && ((cardNumLength == 13) ||(cardNumLength ==16))) {
                    isValidCardType = true;
                }
            } else {
                isValidCardType = true;
            }
			return isValidCardType;
        }


       function showCaptcha(){
		   // Below if condition will display Redirected Captcha.
		   if(document.getElementById('redirectedCaptcha')!=null){
				var varRedirectedCaptcha = document.getElementById('redirectedCaptcha');
				if(varRedirectedCaptcha.value=="yes"){
					return false ;
				}
			}
		   // Below code will display Default Captcha.
		   document.getElementById('captchaCaption1').style.display = '';
		   document.getElementById('captchaCaption2').style.display = '';  
		   document.getElementById('captchaCaption3').style.display = '';  
		   document.getElementById('captchaValue1').style.display = '';
		   document.getElementById('captchaValue2').style.display = '';
		   return false ;
	   }

		// Below functions added for Displaying Surcharge Amount on basis of card Type and Number selected.

		function showSurcharge(config,isCredCrdAssinged,isDbtCrdAssinged){
			var creditAssigned = parseInt(config.substring(0,1)) == 1;
			var debitAssigned = parseInt(config.substring(1,2)) == 1;
			var isCredCrdAssinged = parseInt(isCredCrdAssinged) == 1;
			var isDbtCrdAssinged = parseInt(isDbtCrdAssinged) == 1;
			var cardtypeselected = '';
			var varCardNumber = '' ;
			var strCType;
			var instrType;
			var isCVVMandatory;
			var isSurchargeZero = document.getElementById('IsSurchargeZero');
			if(creditAssigned && isCredCrdAssinged && document.getElementById('creditCrdRadio').checked){
				radio = document.getElementsByName('RadioGroupCrd');
				for(i = 0; i < radio.length; i++){
					var curRadio = radio[i];
					if(radio[i].checked){
						var arr = radio[i].value.split('|');
						strCType = arr[0];
						isCVVMandatory = arr[1];
					}
				}
				instrType = 'CREDIT';
				document.frmPG.CardNumCrd.value = document.frmPG.CardNumCrd1.value+document.frmPG.CardNumCrd2.value+document.frmPG.CardNumCrd3.value
                +document.frmPG.CardNumCrd4.value ;

				if( strCType == "MAEST") {
					document.frmPG.CardNumCrd.value = document.frmPG.CardNumCrd.value+document.frmPG.CardNumCrd5.value ;
				}
				varCardNumber = document.frmPG.CardNumCrd;
			}else if(debitAssigned && isDbtCrdAssinged && document.getElementById('debitCrdRadio').checked){
				radio = document.getElementsByName('RadioGroupDbt');
				for(i = 0; i < radio.length; i++){
					var curRadio = radio[i];
					if(radio[i].checked){
						var arr = radio[i].value.split('|');
						strCType = arr[0];
						isCVVMandatory = arr[1];
					}
				}
				document.frmPG.CardNumDbt.value = document.frmPG.CardNumDbt1.value+document.frmPG.CardNumDbt2.value+document.frmPG.CardNumDbt3.value
                +document.frmPG.CardNumDbt4.value ;

				if( strCType == "MAEST") {
					document.frmPG.CardNumDbt.value = document.frmPG.CardNumDbt.value+document.frmPG.CardNumDbt5.value ;
				}
				varCardNumber = document.frmPG.CardNumDbt;
				instrType = 'DEBIT';
			}
			
			var varValidateMasterMaestroCard = validateMasterMaestroCardWitkExpDateCvv(strCType,varCardNumber.value,instrType,isCVVMandatory);
			if(varValidateMasterMaestroCard == true && isSurchargeZero.value=="no" ){
				document.getElementById('hideSurcharge').style.display = '';
				document.getElementById('hideTotalAmt').style.display = '';
			}else{
				document.getElementById('hideSurcharge').style.display = "none";
				document.getElementById('hideTotalAmt').style.display = "none";
			}

 		}

		function validateMasterMaestroCardWitkExpDateCvv(varCardType,varCardNumber,instrType,isCVVMandatory){
			
			var isValidMasterMaestroCard = false ;
			var isExpPresent = false ;
			var isCvvPresent = false ;
			var strExpiry;
			if(instrType == "CREDIT"){
				if( varCardType == "MC" || varCardType == "VISA" ) {
					for(var i=0 ; i < document.frmPG.creditExpDtYr.length ; i++  ){ 
						if(document.frmPG.creditExpDtYr.options[i].selected == true ){
							if( document.frmPG.creditExpDtYr.options[i].value != "") {
								isExpYearPresent = true ;
							}
						}
				    }
					for(var i=0 ; i < document.frmPG.creditExpDtMon.length ; i++  ){ 
						if(document.frmPG.creditExpDtMon.options[i].selected == true ){
							if( document.frmPG.creditExpDtMon.options[i].value != "") {
								isExpMonPresent = true ;
							}
						}
					}
					if(parseInt(isCVVMandatory) == 1){
						if(document.frmPG.creditCVV.value != "" && document.frmPG.creditCVV.value.length==3){
							isCvvPresent = true ;
						}
					}else{
						isCvvPresent = true ;
					}

					var strMasterCard = varCardNumber.substring(0,2);
					if(((strMasterCard == "50") || (strMasterCard == "51") || (strMasterCard == "52") || (strMasterCard == "53") ||
						(strMasterCard == "54") || (strMasterCard == "55")) &&  varCardNumber.length ==16 && isExpYearPresent && isExpMonPresent && isCvvPresent) {
							isValidMasterMaestroCard = true;
					}
				}
				
				if( varCardType == "MAEST") {
					if(varCardNumber.length == 19 || varCardNumber.length == 16){
						isValidMasterMaestroCard = true;
					}
				}
			}else if(instrType == "DEBIT"){
				if( varCardType == "MC" || varCardType == "VISA" ) {
					for(var i=0 ; i < document.frmPG.debitExpDtYr.length ; i++  ){ 
						if(document.frmPG.debitExpDtYr.options[i].selected == true ){
							if( document.frmPG.debitExpDtYr.options[i].value != "") {
								isExpYearPresent = true ;
							}
						}
				    }
					for(var i=0 ; i < document.frmPG.debitExpDtMon.length ; i++  ){ 
						if(document.frmPG.debitExpDtMon.options[i].selected == true ){
							if( document.frmPG.debitExpDtMon.options[i].value != "") {
								isExpMonPresent = true ;
							}
						}
					}
					if(document.frmPG.debitCVV.value != "" && document.frmPG.debitCVV.value.length==3){
						isCvvPresent = true ;
					}

					var strMasterCard = varCardNumber.substring(0,2);
					if(((strMasterCard == "50") || (strMasterCard == "51") || (strMasterCard == "52") || (strMasterCard == "53") ||
						(strMasterCard == "54") || (strMasterCard == "55")) &&  varCardNumber.length ==16 && isExpYearPresent && isExpMonPresent && isCvvPresent) {
							isValidMasterMaestroCard = true;
					}
				}
				
				if( varCardType == "MAEST") {
					if(varCardNumber.length == 19 || varCardNumber.length == 16){
						isValidMasterMaestroCard = true;
					}
				}
			}	
				return isValidMasterMaestroCard ;
		}
	  
	  /*function showIssuers(mrtId,txnCurr){
	   if(document.getElementById('childDiv') == null){
			if(mrtId.value!="-1" & txnCurr.value!="-1"){
				xmlHttp=GetXmlHttpObject();
				if (xmlHttp==null){
				alert ("Browser does not support HTTP Request");
				return;
				}
				var url="https://ranjeet-na:448/mpi/NBISOServlet";
				//alert("mrtId:"+mrtId);
				//alert("txnCurr:"+txnCurr);
				url=url+"?RequestType=IssListSSL&MerchantID="+mrtId+"&CurrCode="+txnCurr+"&MrtISOId=ISS001";
				xmlHttp.onreadystatechange = function(){fillDiv(); 
				};
				//xmlHttp.open("GET",url,true)
				xmlHttp.open("POST",url,true);
				xmlHttp.send(null);
			}else{
		 		alert("Invalid mrtId "+mrtId+" or TxnCurrency Received"+ txnCurr);
			 }
		 }	
	 }

	function fillDiv() {
	if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
		var showdata = xmlHttp.responseText;
		if(showdata.length != 0 && showdata.length < 200 ){
		 	alert('showdata'+showdata);
		 	var dataArr = new Array();
		 	dataArr = showdata.split('|');
		 	var newDiv = document.createElement('div');
		 	newDiv.id = 'childDiv';
			var str="";
		 	str = "<span>Please select the bank with whom you have an account</span>";
			var x = 0;
		 	for (; x <dataArr.length; x++){ 
				if(x == 0){
					str =str +  "<table width='100%' border='0' cellspacing='0' cellpadding='0'> <tbody>";
				}
  				var issCode = new Array(); 
  				issCode = dataArr[x].split(",");
  				if(issCode.length > 1){
  					if(x % 2 == 0){
  						str = str + "<tr>";
  					}
  					str = str + "<td><label> <input type='radio' id='"+issCode[0]+"'  value='"+issCode[0]+"|" +issCode[1]+"' name='RadioGroupIssuer'>" +issCode[1]+"</label> </td>";
  					if(x % 2 != 0){
  						str = str + "</tr>";
  					}
  				}
    		}
	  		if(x % 2 == 0){
				str = str + "</tr>";
			}
			str = str +  "</tbody>";
    		newDiv.innerHTML = str + "</table>";
		 	document.getElementById("netBnking").appendChild(newDiv);
		 }else{
		 	alert('Error Occured Please try again!!');
		 }
		}
	}
   function GetXmlHttpObject(){
		var xmlHttp=null;
		try{
			// Firefox, Opera 8.0+, Safari
			xmlHttp=new XMLHttpRequest();
		}catch (e){
			//Internet Explorer
			try{
			 	xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
			}catch (e){
			 	xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
			}
		}
		return xmlHttp;
	}*/

	  	
//*****************************************************************
// Function Name: ValidateEmail
// Description	: Validates the correctness of an EmailID
//*****************************************************************

	/*function ValidateEmail(lsEmail){
		lsMailVal = lsEmail.value;
		CountAtSign = 0;
		CountPeriod = 0;
		CountSpecial = 0;
		
		if(lsMailVal.length != 0 ){
			for (i=0; i < lsMailVal.length; i++){
				if (lsMailVal.charAt(i) == "@")
					CountAtSign = CountAtSign + 1; 
				if((lsMailVal.charAt(i) == "'") ||(lsMailVal.charAt(i) == "\""))
					CountSpecial = CountSpecial + 1;
			}
	
			if ((CountSpecial == 0) && (CountAtSign == 1) && (lsMailVal.indexOf('@') != 0)){
				lsMailDotStr = lsMailVal.substring(lsMailVal.indexOf('@'),lsMailVal.length);
				for (i=0; i < lsMailDotStr.length; i++){
					if (lsMailDotStr.charAt(i) == ".")
						CountPeriod = CountPeriod + 1; 
				}	
			
				if ((CountPeriod > 0) && (lsMailDotStr.charAt(lsMailDotStr.length-1) != '.') && (lsMailDotStr.indexOf('.') - lsMailDotStr.indexOf('@') > 1))
				{}
				else{
					alert ("Please enter valid E-mail ID");
					lsEmail.value = '';
					lsEmail.focus();
				}
			}	
			else{
				alert ("Please enter valid E-mail ID");
				lsEmail.value = '';
				lsEmail.focus();
			}
		}
	}*/
  	
  	function changeName(){
	   document.getElementById('name').style.display='none';
	   document.getElementById('nameDiv2').style.display='';
	   document.getElementById('custName').value="";
	   document.getElementById('custName').focus();
	}
		
	function restoreName(){
	   if(document.getElementById('custName').value==''){
	     document.getElementById('name').style.display='';
	     document.getElementById('nameDiv2').style.display='none';
	   }
	}
	
	/*function changeEmail(){
	   document.getElementById('email').style.display='none';
	   document.getElementById('emailDiv2').style.display='';
	   document.getElementById('custEmail').value="";
	   document.getElementById('custEmail').focus();
	}
	
	function restoreEmail(){
	   if(document.getElementById('custEmail').value==''){
	     document.getElementById('email').style.display='';
	     document.getElementById('emailDiv2').style.display='none';
	   }
	}
	
	function changePhone(){
	   document.getElementById('phone').style.display='none';
	   document.getElementById('phoneDiv2').style.display='';
	   document.getElementById('custPhone').value="";
	   document.getElementById('custPhone').focus();
	}
	
	function restorePhone(){
	   if(document.getElementById('custPhone').value==''){
	     document.getElementById('phone').style.display='';
	     document.getElementById('phoneDiv2').style.display='none';
	   }
	}
	
	function changeCardNumber(){
	   document.getElementById('CardNumCrd1').style.display='none';
	   document.getElementById('CardNumCrdDiv2').style.display='';
	   document.getElementById('CardNumCrd').value="";
	   document.getElementById('CardNumCrd').focus();
	}
		
	function restoreCardNumber(){
	   if(document.getElementById('CardNumCrd').value==''){
	     document.getElementById('CardNumCrd1').style.display='';
	     document.getElementById('CardNumCrdDiv2').style.display='none';
	   }
	}	
		
	function changeCardNumberDbt(){
	   document.getElementById('CardNumDbt1').style.display='none';
	   document.getElementById('CardNumDbtDiv2').style.display='';
	   document.getElementById('CardNumDbt').value="";
	   document.getElementById('CardNumDbt').focus();
	}
		
	function restoreCardNumberDbt(){
	   if(document.getElementById('CardNumDbt').value==''){
	     document.getElementById('CardNumDbt1').style.display='';
	     document.getElementById('CardNumDbtDiv2').style.display='none';
	   }
	}
		
	function changeCreditExpiry(){
	   document.getElementById('creditExpiry').style.display='none';
	   document.getElementById('creditExpiryDiv2').style.display='';
	   document.getElementById('creditExpDate').value="";
	   document.getElementById('creditExpDate').focus();
	}
	
	function restoreCreditExpiry(){
	   if(document.getElementById('creditExpDate').value==''){
	     document.getElementById('creditExpiry').style.display='';
	     document.getElementById('creditExpiryDiv2').style.display='none';
	   }
	}
	
	function changeDebitExpiry(){
	   document.getElementById('debitExpiry').style.display='none';
	   document.getElementById('debitExpiryDiv2').style.display='';
	   document.getElementById('debitExpDate').value="";
	   document.getElementById('debitExpDate').focus();
	}
	
	function restoreDebitExpiry(){
	   if(document.getElementById('debitExpDate').value==''){
	     document.getElementById('debitExpiry').style.display='';
	     document.getElementById('debitExpiryDiv2').style.display='none';
	   }
	}*/
	
	function disableDiv(strType,config,isCredCrdAssinged,isDbtCrdAssinged,isNetBnkAssigned){
		if(config != null){
			var creditAssigned = parseInt(config.substring(0,1)) == 1;
			var debitAssigned = parseInt(config.substring(1,2)) == 1;
			var netBankAssigned = parseInt(config.substring(2,3)) == 1;
			var isCredCrdAssinged = parseInt(isCredCrdAssinged) == 1;
			var isDbtCrdAssinged = parseInt(isDbtCrdAssinged) == 1;
			var isNetBnkAssigned = parseInt(isNetBnkAssigned) == 1;
			if(creditAssigned && isCredCrdAssinged && document.getElementById('creditCrdRadio').checked){
				if(debitAssigned && isDbtCrdAssinged)
					document.getElementById('debitCrd').style.display='none';
				if(document.getElementById('showCRDExpiry').style.display == 'block'){
					document.getElementById('showCRDExpiry').style.display='block';
					document.getElementById('showCRDCVV').style.display='block';
					document.getElementById('hideCRDExpiry').style.display='none';
					document.getElementById('hideCRDCVV').style.display='none';
				}else{
					document.getElementById('showCRDExpiry').style.display='none';
					document.getElementById('showCRDCVV').style.display='none';
					document.getElementById('hideCRDExpiry').style.display='block';
					document.getElementById('hideCRDCVV').style.display='block';
				}
				document.getElementById('creditCrd').style.display='block';
				if(strType == "Sale" && netBankAssigned && isNetBnkAssigned){
					document.getElementById('netBnking').style.display='none';
				}	
			}else if(debitAssigned && isDbtCrdAssinged && document.getElementById('debitCrdRadio').checked){
				document.getElementById('debitCrd').style.display='block';
				if(creditAssigned && isCredCrdAssinged)
					document.getElementById('creditCrd').style.display='none';
				if(document.getElementById('showDBTExpiry').style.display == 'block'){
					document.getElementById('showDBTExpiry').style.display='block';
					document.getElementById('showDBTCVV').style.display='block';
					document.getElementById('hideDBTExpiry').style.display='none';
					document.getElementById('hideDBTCVV').style.display='none';
				}else{
					document.getElementById('showDBTExpiry').style.display='none';
					document.getElementById('showDBTCVV').style.display='none';
					document.getElementById('hideDBTExpiry').style.display='block';
					document.getElementById('hideDBTCVV').style.display='block';
				}
				if(strType == "Sale" && netBankAssigned && isNetBnkAssigned){
					document.getElementById('netBnking').style.display='none';
				}	
			}else if(strType == "Sale" && netBankAssigned && isNetBnkAssigned && document.getElementById('netBnkRadio').checked){
					if(debitAssigned && isDbtCrdAssinged)
						document.getElementById('debitCrd').style.display='none';
					if(creditAssigned && isCredCrdAssinged)
						document.getElementById('creditCrd').style.display='none';
					document.getElementById('netBnking').style.display='block';	
			}else{
				if(debitAssigned && isDbtCrdAssinged)
					document.getElementById('debitCrd').style.display='none';
				if(creditAssigned && isCredCrdAssinged)
					document.getElementById('creditCrd').style.display='none';
				if(strType == "Sale" && netBankAssigned && isNetBnkAssigned)
					document.getElementById('netBnking').style.display='none';
			}
		}
	}
	
	function validateCardNum(strCType,crdNum,displayCaptcha){
		if(strCType !="MAEST"){
			if(crdNum.value==""){
			    alert("Card Number is incorrect or empty!");
				document.frmPG.isDisplayCaptcha.value = displayCaptcha ;
				showCaptcha();
				crdNum.focus();
			    return false;
		    }
		    if(crdNum.value.length!=16){
				alert("Card number has to be 16 digits.");	
				document.frmPG.isDisplayCaptcha.value = displayCaptcha ;
				showCaptcha();
				crdNum.focus();
			    return false;
			}
		}else{
			if(crdNum.value==""){
			    alert("Card Number is incorrect or  empty!");
				document.frmPG.isDisplayCaptcha.value = displayCaptcha ;
				showCaptcha();
			    crdNum.focus();
			    return false;
			}
			if((crdNum.value.length < 16) || (crdNum.value.length > 19)){
			    alert("Card number has to be 16 to 19 digits.");
				document.frmPG.isDisplayCaptcha.value = displayCaptcha ;
				showCaptcha();
			    crdNum.focus();
			    return false;
			}
       	}
        if(!isCardTypeValid(crdNum.value,strCType)){
            alert("Please select valid card type"); 
			document.frmPG.isDisplayCaptcha.value = displayCaptcha ;
			showCaptcha();
            return false;            
        }
        if(isNaN(parseInt(crdNum.value))){
            alert("Please enter correct card number"); 
			document.frmPG.isDisplayCaptcha.value = displayCaptcha ;
			showCaptcha();
            return false;
        }
        return true;
	}
	
	function validateCAVV(strCAVV,displayCaptcha){
		var cvv = parseInt(strCAVV.value);
           if (isNaN(cvv)){
	            alert("CVV2/CVC2 is mandatory for the card type you have selected. Please enter the CVV2/CVC2 number."); 
				document.frmPG.isDisplayCaptcha.value = displayCaptcha ;
				showCaptcha();
	            return false;
           }
           if(strCAVV.value==""){
				var message = "CVV2/CVC2 is mandatory for the card type you have selected. Please enter the CVV2/CVC2 value.";
				alert(message);
				document.frmPG.isDisplayCaptcha.value = displayCaptcha ;
				showCaptcha();
				strCAVV.focus();
	            return false;
           }

           if(strCAVV.value.length!=3){
               	alert("CVV2/CVC2 has to be 3 digits.");
				document.frmPG.isDisplayCaptcha.value = displayCaptcha ;
				showCaptcha();
              	strCAVV.focus();
               	return false;
           }
           return true;
	}
	
	function validateExpiry(strCType,strMonth,strYear,displayCaptcha){
		if(strCType !="MAEST"){
			for(var i=0 ; i < strYear.length ; i++ ){ 
			    if(strYear.options[i].selected == true){
					if(strYear.options[i].value == ""){
					    alert("Please select a Expiry date to proceed.");
					    document.frmPG.isDisplayCaptcha.value = displayCaptcha ;
						showCaptcha();
					    return false;
					}
			    }
		   }
		   for(var i=0 ; i < strMonth.length ; i++){ 
			    if(strMonth.options[i].selected == true ){
					if( strMonth.options[i].value == "") {
					    alert("Please select a Expiry date to proceed.");
					    document.frmPG.isDisplayCaptcha.value = displayCaptcha ;
						showCaptcha();
					    return false;
					}
			    }
		   }
        }
        if(strCType=="MAEST"){
        	var expYr ;
        	var expMon;
        	for(var i=0 ; i < strYear.length ; i++  ){ 
			    if(strYear.options[i].selected == true ){
			    	expYr=  strYear.options[i].value;
			    }
        	}
			for(var i=0 ; i < strMonth.length ; i++  ){ 
			    if(strMonth.options[i].selected == true ){
			    	expMon = strMonth.options[i].value;
			    }
			}
        	if((expYr == 0 && expMon!=0) || (expYr != 0 && expMon==0) ){// if only one is selected
        	    alert("Either expiry year or month is not selected.");
				document.frmPG.isDisplayCaptcha.value = displayCaptcha ;
				showCaptcha();
        	    return false;
        	}
	     }
        return true;
		/*if(strCType !="MAEST"){
			if(isInteger(strExpiry.value)== false){
				alert('Please enter proper card expiry date');
				document.frmPG.isDisplayCaptcha.value = displayCaptcha ;
				showCaptcha();
				return false;
			}
		    var strMonth=strExpiry.value.substring(4,6);
		    var strYear=strExpiry.value.substring(0,4);
		    //strYr=strYear;
		    //month=parseInt(strMonth);
		    //year=parseInt(strYr);
		    if (strMonth.length != 2 || strMonth<1 || strMonth>12){
		        alert("Please enter a valid month");
		        document.frmPG.isDisplayCaptcha.value = displayCaptcha ;
				strExpiry.value = '';
				showCaptcha();
		        return false;
		    }
		    if (strYear.length != 4 || strYear==0 ){
		        alert("Please enter a valid year.");
		        document.frmPG.isDisplayCaptcha.value = displayCaptcha ;
				strExpiry.value = '';
				showCaptcha();
		        return false;
		    }
		   return true;
		}else{
			return true;
		}*/
	}
	
	/*function isInteger (s){   
		var i;
	    // Search through string's characters one by one
	    // until we find a non-numeric character.
	    // When we do, return false; if we don't, return true.
	    if (s.length == 0)
	    	return false;
	    for (i = 0; i < s.length; i++){   
	        // Check that current character is number.
	        var c = s.charAt(i);
	        if (((c < "0") || (c > "9"))) return false;
	    }
	    // All characters are numbers.
	    return true;
	}*/
	
	function disableMaestroExpiry(config,isDbtCrdAssinged,isCredCrdAssinged){
	var debitAssigned = parseInt(config.substring(1,2)) == 1;
	var isDbtCrdAssinged = parseInt(isDbtCrdAssinged) == 1;
	var creditAssigned = parseInt(config.substring(0,1)) == 1;
	var isCredCrdAssinged = parseInt(isCredCrdAssinged) == 1;
		if(debitAssigned && isDbtCrdAssinged && document.getElementById('debitCrdRadio').checked){
			var radio = document.getElementsByName('RadioGroupDbt');
			for(i = 0; i < radio.length; i++){
				var curRadio = radio[i];
				if(radio[i].checked){
					var arr = radio[i].value.split('|');
					var strCType = arr[0];
					var strMandatory = arr[1];
					if(strCType =="MAEST"){
						document.getElementById('hideDBTExpiry').style.display='none';
						document.getElementById('hideDBTCVV').style.display='none';
						document.getElementById('showDBTExpiry').style.display='block';
						document.getElementById('showDBTCVV').style.display='block';
					}
					else{
						document.getElementById('hideDBTExpiry').style.display='block';
						document.getElementById('hideDBTCVV').style.display='block';
						document.getElementById('showDBTExpiry').style.display='none';
						document.getElementById('showDBTCVV').style.display='none';
					}
				}
			}
		}else if(creditAssigned && isCredCrdAssinged && document.getElementById('creditCrdRadio').checked){
			var radio = document.getElementsByName('RadioGroupCrd'); 
			for(i = 0; i < radio.length; i++){
				var curRadio = radio[i];
				if(radio[i].checked){
					var arr = radio[i].value.split('|');
					var strCType = arr[0];
					var strMandatory = arr[1];
					if(strCType =="MAEST"){
						document.getElementById('hideCRDExpiry').style.display='none';
						document.getElementById('hideCRDCVV').style.display='none';
						document.getElementById('showCRDExpiry').style.display='block';
						document.getElementById('showCRDCVV').style.display='block';
					}
					else{
						document.getElementById('hideCRDExpiry').style.display='block';
						document.getElementById('hideCRDCVV').style.display='block';
						document.getElementById('showCRDExpiry').style.display='none';
						document.getElementById('showCRDCVV').style.display='none';
					}
				}
			}
		}
	}

	function resizeCrdNumTextBox(cardType){
		if(document.getElementById('CardNumCrd')!=null){
			document.getElementById('CardNumCrd1').value = "" ;
			document.getElementById('CardNumCrd2').value = "" ;
			document.getElementById('CardNumCrd3').value = "" ;
			document.getElementById('CardNumCrd4').value = "" ;
		}
		if(document.getElementById('CardNumDbt')!=null){
			document.getElementById('CardNumDbt').value = "" ;
			document.getElementById('CardNumDbt1').value = "" ;
			document.getElementById('CardNumDbt2').value = "" ;
			document.getElementById('CardNumDbt3').value = "" ;
			document.getElementById('CardNumDbt4').value = "" ;
		}
		var varCardType = new Array();
		varCardType = cardType.split('|');
		if(varCardType[0]=='MAEST'){
			if(document.getElementById('CardNumCrd5')!=null){
				document.getElementById('CardNumCrd5').value="";
				document.getElementById('CardNumCrd5').style.display='';
			}
			if(document.getElementById('CardNumDbt5')!=null){
				document.getElementById('CardNumDbt5').value="";
				document.getElementById('CardNumDbt5').style.display='';
			}
		}else{
			if(document.getElementById('CardNumCrd5')!=null){
				document.getElementById('CardNumCrd5').style.display='none';
			}
			if(document.getElementById('CardNumDbt5')!=null){
				document.getElementById('CardNumDbt5').style.display='none';
			}
		}
		
	}