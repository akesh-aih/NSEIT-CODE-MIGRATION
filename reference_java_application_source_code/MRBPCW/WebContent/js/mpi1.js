function chkFill(){
    if(document.frmPG.isPayClicked.value == "enabled"){
        var frm = document.frmPG ;
        var cardtypeselected = '';
        for(var i=0 ; i < document.frmPG.CardTypeSelectBox.length ; i++  ){ 
            if(document.frmPG.CardTypeSelectBox.options[i].selected == true ){
                cardtypeselected = document.frmPG.CardTypeSelectBox.options[i].value ;

            }
        }
        if(cardtypeselected =="SELECTED"){
            alert("Please select a Card type to proceed.");
            return false;
        }
        
        var arr = cardtypeselected.split('|');
        var strCType = arr[0];
        var strMandatory = arr[1];        
                if(strCType == "MAEST" || strCType == "SBIME" ){
                   strMandatory = 0;	    
                    
                }
                
                if(document.frmPG.CardNum.value==""){
                    alert("Card Number is incorrect or  empty!");
                    return false;
                }                    
                
               
                
                if( strCType == "MAEST" || strCType == "SBIME" ){                    
                    if(document.frmPG.CardNum.value.length >19 || document.frmPG.CardNum.value.length<13){
                        alert("Card number can be of length 13 to 19.");
                        document.frmPG.CardNum.focus();
                        return false;
                    }
                }
                if( strCType != "MAEST" && strCType != "SBIME"){  
			 if(document.frmPG.CardNum.value.length!=16){
					    alert("Card number has to be 16 digits.");
					    document.frmPG.CardNum.focus();
					    return false;
			}
		}			
        	if( strCType != "SBIME"){  
			for(var a=0 ; a < document.frmPG.ExpDtYr.length ; a++  ){ 
			    if(document.frmPG.ExpDtYr.options[a].selected == true ){
				if( document.frmPG.ExpDtYr.options[a].value == "") {
				    alert("Please select a Expiry date to proceed.");
				    return false;
				}
			    }

			}

			for(var b=0 ; b < document.frmPG.ExpDtMon.length ; b++  ){ 
			    if(document.frmPG.ExpDtMon.options[b].selected == true ){
				if( document.frmPG.ExpDtMon.options[b].value == "") {
				    alert("Please select a Expiry date to proceed.");
				    return false;
				}
			    }
			}
        
        	}
        	
        	if( strCType == "MAEST"){  
			var i;
			var k;
			for(i=0 ; i < document.frmPG.ExpDtYr.length ; i++  ){ 
			    if(document.frmPG.ExpDtYr.options[i].selected == true ){
				break;
			    }

			}

			for( k=0 ; k < document.frmPG.ExpDtMon.length ; k++  ){ 
			    if(document.frmPG.ExpDtMon.options[k].selected == true ){
				break;
			    }
			}
			

			if(( document.frmPG.ExpDtYr.options[i].value == "" && document.frmPG.ExpDtMon.options[k].value =="" ) || (document.frmPG.ExpDtYr.options[i].value != "" && document.frmPG.ExpDtMon.options[k].value !="" )){
			   
			}else{
			   alert("Please select a Expiry date to proceed.");
		           return false;
			}
			

        	}
        	
        if(parseInt(strMandatory) == 1){        
            var cvv = parseInt(document.frmPG.CVVNum.value);
            if (isNaN(cvv)){
                alert("CVV2/CVC2 is mandatory for the card type you have selected. Please enter the CVV2/CVC2 number."); 
                return false;
            }
            if(document.frmPG.CVVNum.value==""){
                var message = "CVV2/CVC2 is mandatory for the card type you have selected. Please enter the CVV2/CVC2 value.";
                alert(message);
                document.frmPG.CVVNum.focus();
                return false;
            }

            if(document.frmPG.CVVNum.value.length!=3){
                alert("CVV2/CVC2 has to be 3 digits.");
                document.frmPG.CVVNum.focus();
                return false;
            }
        }
        if(document.frmPG.CVVNum.value.length!='' && document.frmPG.CVVNum.value.length!=3){
                        alert("CVV2/CVC2 has to be 3 digits.");
                        document.frmPG.CVVNum.focus();
                        return false;
        }
        document.frmPG.CardType.value = strCType;
               
        
        if(document.frmPG.CVVNum.value!=""){
            document.frmPG.CVV2FieldPresentIndicator.value='1';
        }else{
            document.frmPG.CVV2FieldPresentIndicator.value='0';
        }
        
        var strCardNum = document.frmPG.CardNum.value;

        if(!isCardTypeValid(strCardNum,strCType))
        {
            alert("Please select valid card type");            
            return false;            
        }       
        
        


        var cardnum = parseInt(document.frmPG.CardNum.value);

        if(isNaN(cardnum)){
            alert("Please enter correct card number"); 
            return false;
        }
               
                if(document.frmPG.CaptchaValue.value==""){
        alert("Please enter word verification string "); 
            return false;
        
        }
        document.frmPG.isPayClicked.value="disabled";


        //hidePay.innerHTML   =   "Your Transaction is being processed by SBI. &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<br>You will  be redirected to merchant site for confirmation. Please wait...";
        document.getElementById( 'hidePay' ).innerHTML   =   "Your Transaction is being processed by SBI. &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<br>You will  be redirected to merchant site for confirmation. Please wait...";
		
        /*document.frmPG.btnPay.disabled    =   true; */
        
	if( strCType == "MAEST" && (document.frmPG.ExpDtYr.options[i].value == "" && document.frmPG.ExpDtMon.options[k].value =="") ){
		    document.frmPG.ExpDtYr.options[i].value="2049";
		    document.frmPG.ExpDtMon.options[k].value= "12";

	}

        return true;
    }else{
        alert("cannot click the button again transaction is in progress");
        return false;
    }
}
        
       /** Changes made by Sanjiv for ELTOPUSPRD-18498 */
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
            } else if(astrCardType == "MAEST") {
                var strMaestro = astrCardNum.substring(0,1);
                if ((cardNumLength >= 13) &&(cardNumLength <=19)) {
                    isValidCardType = true;
                }                
            } else if(astrCardType == "SBIME") {
                var strMaestro = astrCardNum.substring(0,1);
                if (cardNumLength ==19) {
                    isValidCardType = true;
               }                
            }else {
                isValidCardType = true;
            }
	    return isValidCardType;
        }
        

     // Below functions added for Displaying Surcharge Amount on basis of card Type and Number selected.
		function showSurcharge(){
			
			/*var cardtypeselected = '';
			var varCardNumber = '' ;
			var CaptchaValue = '';*/
			var isSurchargeZero = document.getElementById('IsSurchargeZero');

			/*var cardtypeselected = '';
			for(var i=0 ; i < document.frmPG.CardTypeSelectBox.length ; i++  ){ 
				if(document.frmPG.CardTypeSelectBox.options[i].selected == true ){
					cardtypeselected = document.frmPG.CardTypeSelectBox.options[i].value ;
				}
			}
			varCardNumber = document.frmPG.CardNum.value ;
			CaptchaValue = document.frmPG.CaptchaValue.value ;
			alert('Card Type::::'+cardtypeselected);
			var isCardWithExpDateCvvPresent = isCardWithExpDateCvvPresented(cardtypeselected);*/
			
			//if(cardtypeselected !="SELECTED" && isSurchargeZero.value== "no" && isCardWithExpDateCvvPresent == true && varCardNumber!="" && CaptchaValue!=""){
			if(isSurchargeZero.value== "no"){
				document.getElementById('hideSurcharge').style.display = '';
				document.getElementById('hideTotalAmt').style.display = '';
			}else{
				document.getElementById('hideSurcharge').style.display = "none";
				document.getElementById('hideTotalAmt').style.display = "none";
			}
 		}
		
       /* function isCardWithExpDateCvvPresented(cardtypeselected){
			
        	var isExpYearPresent = false ;
			var isExpMonPresent = false ;
			var isCvvPresent = false ;
			var isCardWithExpDateCvvPresent = false ;
			var cardType = cardtypeselected;
			alert('Card Type::::'+cardType);
			
				for(var i=0 ; i < document.frmPG.ExpDtYr.length ; i++  ){ 
					if(document.frmPG.ExpDtYr.options[i].selected == true ){
						if( document.frmPG.ExpDtYr.options[i].value != "") {
							isExpYearPresent = true ;
						}
					}
			    }
				for(var i=0 ; i < document.frmPG.ExpDtMon.length ; i++  ){ 
					if(document.frmPG.ExpDtMon.options[i].selected == true ){
						if( document.frmPG.ExpDtMon.options[i].value != "") {
							isExpMonPresent = true ;
						}
					}
				}
				
				if(document.frmPG.CVVNum.value != "" && document.frmPG.CVVNum.value.length==3){
					isCvvPresent = true ;
				}

			    if(isExpYearPresent == true && isExpMonPresent == true && isCvvPresent == true){
			    	isCardWithExpDateCvvPresent = true ;
			    }else{
			    	//In case of masetro card cvv and expiry date is not mandatory .Hence directly setting the value to 'True' for displaying surcharge
			    	if(cardType == 'MAEST'){
						isCardWithExpDateCvvPresent = true;
					}else{
						isCardWithExpDateCvvPresent = false ;
					}
			    }
				return isCardWithExpDateCvvPresent ;
		}*/
