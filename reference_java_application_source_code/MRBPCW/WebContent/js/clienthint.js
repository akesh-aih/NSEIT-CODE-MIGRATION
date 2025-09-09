var xmlHttp;
var reqId; 
var boolvalid;
// Added on 19-07-2011 for Maestro CSM displaying popup message ::by SRE starts here
var i=1;
var j;
var p;
var jp;
var k;
// Added on 19-07-2011 for Maestro CSM displaying popup message ::by SRE ends here

function showHint(str)
{
// Added on 19-07-2011 for Maestro CSM displaying popup message ::by SRE starts here
if(str.indexOf("j=") !=-1)
	{
		p=str.substring(str.length-1,str.length);
		k="true";
	}
str= str.substring(str,str.length-4);
// Added on 19-07-2011 for Maestro CSM displaying popup message ::by SRE ends here

if (str.length==0)
    {
        document.getElementById("txtHint").innerHTML="";
        return;
    }
    xmlHttp=GetXmlHttpObject();
    if (xmlHttp==null)
    {
        alert ("Browser does not support HTTP Request");
        return;
    } 
    var url="gethint.jsp"
    url=url+"?q="+str;
	
    xmlHttp.onreadystatechange=stateChanged;
    xmlHttp.open("GET",url,true);
	xmlHttp.send(null);
    reqId = 0;
} 

function stateChanged() 
{ 
    if (reqId == 0 && (xmlHttp.readyState==4 || xmlHttp.readyState=="complete"))
    { 	

		document.getElementById("txtHint").innerHTML=xmlHttp.responseText.slice(0, xmlHttp.responseText.indexOf(";"));
		//document.write("txtHint value"+document.getElementById("txtHint").innerHTML);
		var showdata = xmlHttp.responseText.slice(0, xmlHttp.responseText.indexOf(";"));
		//document.write("showdata value"+showdata);
		if (showdata.indexOf('CARD_NUMBER')!=-1)
		{
			document.getElementById("texthints").style.visibility='visible';
			document.getElementById("txtHint").value == "CARD_NUMBER";
			document.getElementById("CVVValue").style.visibility='hidden';
			document.getElementById("CVVDisplay").style.visibility='visible';
		}
		else if (showdata.indexOf('INVALID_BRAND')!=-1)
		{
			document.getElementById("texthints").style.visibility='visible';
			document.getElementById("txtHint").value == "INVALID_BRAND_ID";
			document.getElementById("CVVValue").style.visibility='hidden';
			document.getElementById("CVVDisplay").style.visibility='visible';
		}
		else if (showdata.indexOf('MAESTRO')!=-1)
		{
			document.getElementById("texthints").style.visibility='hidden';
			document.getElementById("texthints").value == '';
			// Added on 19-07-2011 for Maestro CSM displaying popup message ::by SRE starts here
			if( (i==1 || p==1) && k=="true" )
			{
				alert ("You have entered a Maestro Card. So the transaction will be processed without CVV.."+"\n"+ "If your Maestro Card does not have Expiry Date then request to you please select Month as 12 and Year as 2049");
				i++;
				p++;
			}
			// Added on 19-07-2011 for Maestro CSM displaying popup message ::by SRE ends here

			document.getElementById("CVVValue").style.visibility='visible';
			document.getElementById("CVVDisplay").style.visibility='hidden';
		}
		else{
			document.getElementById("texthints").style.visibility='visible';
			document.getElementById("CVVValue").style.visibility='hidden';
			document.getElementById("CVVDisplay").style.visibility='visible';
			}
	    //boolvalid = xmlHttp.responseText.slice(xmlHttp.responseText.indexOf(";") + 1, xmlHttp.responseText.indexOf(";") + 2);
    } 
} 
function GetXmlHttpObject()
{ 
    var objXMLHttp=null;
    if (window.XMLHttpRequest)
    {
        objXMLHttp=new XMLHttpRequest();
    }
    else if (window.ActiveXObject)
    {
    objXMLHttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    return objXMLHttp;
}