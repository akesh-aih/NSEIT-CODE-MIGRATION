var message="Right Click Disabled!";

function clickIE4(){
    if (event.button==2){
        return false;
    }
}

function clickNS4(e)
{
    if (document.layers||document.getElementById&&!document.all){
        if (e.which==2||e.which==3){
            return false;
        }
    }
}
if (document.layers){
    document.captureEvents(Event.MOUSEDOWN);
    document.onmousedown=clickNS4;
}
else if (document.all&&!document.getElementById){
    document.onmousedown=clickIE4;
}
document.oncontextmenu=new Function("alert(message);return false")

document.onkeydown = function(e)
{
    if(!e)
    {
        if (window.event)
        {
            e = window.event;
            var key = e.keyCode;
            if (key == 116) {
                event.keyCode = 0;
                event.returnValue = false;
                return false;
            }
        }
        else
            return;
    }
    if( typeof( e.which ) == 'number' )
    {
        var key = e.keyCode ? e.keyCode : e.which ? e.which : void 0;
        if (key == 116) {
            return false;
        }
    }
}
function echeck(str) 
{
	var at="@"
	var dot="."
	var uscore="_";
	var lat=str.indexOf(at)
	var lstr=str.length
	var ldot=str.indexOf(dot)
	var checkUscore=str.substring(lat+1,lstr);
	if (str.indexOf(at)==-1){
	   alert("Invalid E-mail ID")
	   return false
	}
	if (str.indexOf(at)==-1 || str.indexOf(at)==0 || str.indexOf(at)==lstr){
	   alert("Invalid E-mail ID")
	   return false
	}
	if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 || str.indexOf(dot)==lstr){
		alert("Invalid E-mail ID")
		return false
	}
	if (str.indexOf(at,(lat+1))!=-1){
		alert("Invalid E-mail ID")
		return false
	}
	if (str.substring(lat-1,lat)==dot || str.substring(lat+1,lat+2)==dot){
		alert("Invalid E-mail ID")
		return false
	}
	if (str.indexOf(dot,(lat+2))==-1){
		alert("Invalid E-mail ID")
		return false
	}
	if (str.indexOf(" ")!=-1){
		alert("Invalid E-mail ID")
		return false
	}
	if(!(str.match(/^[a-zA-Z0-9_@.]+$/)))
	{
		alert("Invalid character used in E-mail ID");
		document.frm.email.value = "";
		document.frm.email.focus();
		return false;
	}
	if (checkUscore.indexOf(uscore)>0){
		alert("Invalid E-mail ID")
		return false
	}
	return true
}
function ValidateForm()
{
	var emailID=document.frm.email
	if ((emailID.value==null)||(emailID.value=="")){
		alert("Please Enter your Email ID")
		emailID.focus()
		return false
	}
	if (echeck(emailID.value)==false){
		emailID.value=""
		emailID.focus()
		return false
	}
	return true
}

function isNumberKey(evt) 
{
    var charCode = (evt.which) ? evt.which : event.keyCode
    if (charCode > 31 && (charCode < 48 || charCode > 57))
	{
		alert("Please enter only in numbers");
        return false;
	}
    return true;
}
function onKeyPressBlockNumbers(e)
{
    var key = window.event ? e.keyCode : e.which;
    var keychar = String.fromCharCode(key);
    reg = /\d/;
    return !reg.test(keychar);
}