// JavaScript Document
//initialise
	//noof = 4;
	initialised = false
	skip=0;
	var current_link = 0;
	var normal_bgcolor = "#E2E8EF";
	var hover_bgcolor = "#4f81ac";
	var current_bgcolor = "#023A75";
	var normal_color = "#636363"
	var current_color = "#fff"
	var tmp_titles = new Array;
	/*scheme 2*/
	/*var normal_bgcolor = "#E2E8EF";
	var hover_bgcolor = "#4f81ac";
	var current_bgcolor = "#3877ae";
	var normal_color = "#636363"
	var current_color = "#fff"
	*/
	
///
window.onload = function(){ init();}
/*Rrgbtohex*/
function RGBtoHex(R,G,B) {return toHex(R)+toHex(G)+toHex(B)}

function toHex(N)
{
 if (N==null) return "00";
 N=parseInt(N);
 if (N==0 || isNaN(N)) return "00";
 N=Math.max(0,N);
 N=Math.min(N,255);
 N=Math.round(N);
 return ("0123456789ABCDEF".charAt((N-N%16)/16) + "0123456789ABCDEF".charAt(N%16) );
}

function RGBstrToHex(str)
{
	if (str.indexOf('rgb') == -1) return str;
	str = str.replace("rgb", '');
	str = str.replace("(", '');
	str = str.replace(")", '');
	str = str.replace(" ", '');
	str = str.replace(" ", '');
	var temp = new Array();
	temp = str.split(',');
	var r = temp[0];
	var g = temp[1];
	var b = temp[2];
	return "#" + RGBtoHex(r,g,b);
}

/*/*/
//var titles = new Array("","Credit Card", "Net Banking", "Cash Card", "Debit Card");
var titles = new Array("");

function init(){
	//noof = 4;
	tmp_titles = document.getElementById("l_side_links").getElementsByTagName("a");
	
	//noof = tmp_titles.length
	hidall(1);  // shows only  the welcome page content in on landing
	/* fetching titles from liks*/
	//alert(tmp_titles.length)
	for(x=0; x< tmp_titles.length; x++){
		if(tmp_titles[x].innerHTML !=null) titles.push(tmp_titles[x].innerHTML);
	}
	initialised = true
	
	
}

function hidall(start, skip){
	skip = skip

	//10 is the no of right panel divs [n1, n2, n3, .....]
	for(c=start; c<=5; c++){
		//alert(document.getElementById(c))
		if(document.getElementById("n"+c)!=null){
		me = "n"+c; // boxes on r
		document.getElementById(me).style.display = "none";
		}else{ //alert("null");
		}
	}
	/*for(c=0; c<tmp_titles.length; c++){
		//alert (tmp_titles[c].id)
		if(tmp_titles[c]!=null){
		tmp_titles[c].style.style.display = "none";
		}
	}*/
}

function showme(idno){
	if(!initialised){
		init();
	}
	
	hidall(0);  // hide everything on the right side box 
	
	//active box 
	me = "n"+idno;
	var xref = document.getElementById(me);
	xref.style.display = "block";
	
	//links appearance
	// set the colors for the link clicked.
	document.getElementById("a"+idno).style.backgroundColor = current_bgcolor;
	document.getElementById("a"+idno).style.color = "#fff";
	current_link = idno;
	//document.getElementById("title").innerHTML = titles[idno];
	// show the title, title = text part of the left side link 
	document.getElementById("title").innerHTML = document.getElementById("a"+idno).innerHTML;
	
	// set the colors for rest of links to normal.
	for(c=0; c<tmp_titles.length; c++){
		if(tmp_titles[c].id != "a"+current_link ){
			if(tmp_titles[c]!=null){
			tmp_titles[c].style.backgroundColor = normal_bgcolor;
			tmp_titles[c].style.color = "#636363";
			}
		}
	}
	for (var i=0; i < document.chk.RadioGroup1.length; i++)
    {
		if (document.chk.RadioGroup1[i].checked)
		{
			document.chk.RadioGroup1[i].checked=false;
		}		
	}
	//document.getElementById("pagetop").focus();
}

function toHex(N) {
 if (N==null) return "00";
 N=parseInt(N); if (N==0 || isNaN(N)) return "00";
 N=Math.max(0,N); N=Math.min(N,255); N=Math.round(N);
 return "0123456789ABCDEF".charAt((N-N%16)/16)
      + "0123456789ABCDEF".charAt(N%16);
}

function MouseOver(idno){
	col = document.getElementById("a"+idno).style.backgroundColor;
	if(col.charAt(0)=="r"){
		col = (RGBstrToHex(document.getElementById("a"+idno).style.backgroundColor))
	}
	//alert(col.toUpperCase())
	if(col.toUpperCase()==current_bgcolor){
		
	}else{
		document.getElementById("a"+idno).style.backgroundColor = hover_bgcolor;  //#3877ae
		document.getElementById("a"+idno).style.color = "#fff";
	}
}

function MouseOut(idno){
	if(idno != current_link){
		document.getElementById("a"+idno).style.backgroundColor = normal_bgcolor;
		 document.getElementById("a"+idno).style.color = "#636363";
	}
}

//shows the selected link table
