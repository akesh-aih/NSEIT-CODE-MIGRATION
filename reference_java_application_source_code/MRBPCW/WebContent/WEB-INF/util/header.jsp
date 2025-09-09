
<%@page import="com.nseit.generic.models.Users"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>

<%@page import="java.util.Map"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.SortedMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.nseit.generic.util.CommonUtil"%>
<%@page import="org.apache.tiles.jsp.*"%>
<%@page import="org.apache.tiles.factory.*"%>
<%@page import="org.apache.tiles.context.*"%>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ include file="../PreventClickJacking.html"%>
<%@ page language="java" import="com.nseit.generic.util.ConfigurationConstants" %>
<s:if test="%{#session != null}">
	<s:set var="loggedInUser" value="#session['SESSION_USER']"></s:set>
</s:if>
<s:set var="list" value="#session['parentList']" />
<s:set var="parentMenuKey" value="#request.parentMenuKey" />

<!-- Header Start -->
<header>
<div class="headerTopBg">
<s:if test="%{#loggedInUser!=null}">
<div class="container text-right">
 <span class="datetimeinfo"><s:label value="%{#session['SESSION_USER'].currentDateForHeaderDisplay}" />   <div class="btn-group" role="group"></div>
  </span><!--
    <ul class="dropdown-menu dropdown-menu-right">
    <s:if test="%{#session['SESSION_USER'] != null}">
      <li><a href="javascript:void(0);" onclick="ShowPop('pop2'); focusOn();">Change Password</a></li>
      <li><a href="javascript:void(0)" onclick="menuLinks('LoginAction_signout.action')">Sign out</a></li>
      	</s:if>
    </ul>
  -->
  
    <ul class="dropdown1">
    <li>   <a href="#"><b>| Welcome <s:label  id="loginId" value="%{#session['SESSION_USER'].username}" /></b>  <button type="button" class="btn btn-default dropdown-toggle loginDD pull-right" style="padding: 0 5px !important;" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
      <span class="caret"></span>
    </button></a><ul class="dropdown-content">
    <s:if test="%{#session['SESSION_USER'] != null}">
       <li class = "menu-item"><a href="javascript:void(0);" onclick="ShowPop('pop2'); focusOn();">Change Password</a></li>
      <li class = "menu-item"><a href="javascript:void(0)" onclick="menuLinks('LoginAction_signout.action')">Sign out</a></li>
      	</s:if>
      	</ul></li>
    </ul>
  </div>
 </s:if>
</div>

<div class="headerMiddleBg">
 
<div class="container">

               <%
String prodServer = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.Is_this_Live_Server);
if (prodServer != null && !prodServer.equals("")) {
if(prodServer.equals("N")){%>
<div class="clear"></div>
<div class="text-center mt10">
<img src="images/demo_test.gif" alt="This is Demo/Test Instance"/>
</div>
<%} } %> 
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="col-md-3 col-xs-5 m-l-8">
	            <div class=" page-scroll mt10">
	                <%-- <button type="button" class="navbar-toggle collapsed hamberger_click" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
	                    <span class="sr-only">Toggle navigation</span><i class="fa fa-bars"></i>
	                </button> --%>
	                <a class="navbar-brand page-scroll w-48" href="#page-top"><img src="images/logo.png" class="img-responsive fl-left h-100" /></a>
	            </div>
				
			</div> 
			
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="mob_menu_btn visible-xs ">
            	<span class="glyphicon glyphicon-menu-hamburger"></span>
            </div>
            <div class="clear visible-xs"></div>
            <div class="fl-right">
	            <div class="navbar-collapse collapse" id="bs-example-navbar-collapse-1" aria-expanded="false">                
					<s:if test="%{#loggedInUser!=null}">
						<s:if test='%{#loggedInUser.userType == "C"}'>
							<ul class="nav navbar-nav navbar-right"  >
						</s:if>
						<s:else>
							<ul class="nav navbar-nav navbar-right" >
						</s:else>
						 <!-- login user type <s:property value="#loggedInUser.userType"/> --> 
						 	 <s:iterator value="list" status="rowstatus"> 
							 	<li>
							 	<s:if test='%{#loggedInUser.userType == "C"}'>
							 		<s:if test="%{#parentMenuKey == menuKey}">
								 			<a href="javascript:void(0)" id="<s:property value="displayName"/>" style="color: #fff; background: #193576 !important" onclick='menuLinks("<s:url value='%{menuLink}'/>")'><s:property value="displayName"/></a>
								 			<s:hidden value="%{ORM_ROLE_PK}" name="ORM_ROLE_PK"></s:hidden>
								 	</s:if>
								 	<s:else> 
								 		<a href="javascript:void(0)" id="<s:property value="displayName"/>" onclick='menuLinks("<s:url value='%{menuLink}'/>")'><s:property value="displayName"/></a>
							 		</s:else>
							 	</s:if>
							 	<s:else>
							 		<s:if test="%{#parentMenuKey == menuKey}">
								 		<a href="javascript:void(0)" id="<s:property value="displayName"/>" style="color: #fff; background: #193576 !important" onclick='menuLinks("<s:url value='%{menuLink}'/>")'><s:property value="displayName"/></a>
								 	</s:if>
								 	<s:else>
							 			<a href="javascript:void(0)" id="<s:property value="displayName"/>" onclick='menuLinks("<s:url value='%{menuLink}'/>")'><s:property value="displayName"/></a>
							 		</s:else>	
							 	</s:else>
					
								</li>
					 		 </s:iterator>
						</ul>
					</s:if>
					 
	            </div>
	           
			</div> 
			
            <!-- /.navbar-collapse -->
        </div>
 
<div class="container">  
<tiles:importAttribute ignore="true" name="menuKey" />
<div class="fade" id="pop3"></div> 
</div>
<div class="clear"></div>
</div> 
<div class="clear"></div>
</header>
<div class="loading-container" id="loadingDialog"></div>
<!-- <div class="header-txt2">
	Government Of India<br>
	Ministry of Communications<br>
	Department of Posts<br>
	O/o Chief Postmaster General,<br>
	Maharashtra Circle, Mumbai
</div> -->
<!-- Header End -->
<script src="js/jquery-3.6.3.min.js"></script>
<script src="js/jquery-ui.js"></script>

<script  type="text/javascript">
	function enableLoadingAnimation(){
		document.getElementById('loadingDialog').style.display = "block";
	}
	function disabledLoadingAnimation(){
		document.getElementById('loadingDialog').style.display = "none";
	}
</script>