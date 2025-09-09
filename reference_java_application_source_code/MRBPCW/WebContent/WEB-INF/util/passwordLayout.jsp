<%@page import="com.nseit.generic.util.GenericConstants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" >
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<link href="css/common.css" rel="stylesheet" type="text/css" media="screen" />
<link rel="stylesheet" href="css/font-awesome.min.css" type="text/css" media="screen">

	<link href="css/Flexgrid.css" rel="stylesheet" type="text/css" media="screen"/>
	<link type="text/css" href="css/ui-lightness/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
	<link rel="stylesheet" href="css/validationEngine.jquery.css" type="text/css"/>
      
    <!--   <script src="js/jquery.min.js"></script>-->
	<script type="text/javascript" src="js/login.js"></script>
	<script type="text/javascript" src="js/paging.js"></script>
	<script type="text/javascript" src="js/pagingForPayment.js"></script> 
	<script src="js/jquery-3.6.3.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="js/jcarousellite_1.0.1c4.js"></script>
    <script src="js/languages/jquery.validationEngine-en.js" type="text/javascript" charset="utf-8"></script>
	<script src="js/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript" src="js/jquery.form.js"></script>
	<script type="text/javascript" src="js/jquery.clientsidecaptcha.js"></script>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<style>
.container .container {
    width: 1170px;
    float: none;
    margin: 0px auto;
}
	
</style>

</head>
<body class="loginBg registrationBg" style="background: #f5eadf; margin: 0px">
<div class="headerMiddleBg">
<div class="container">

  			<div class="navbar-header page-scroll" style="background: #fff;padding: 6px 84px;margin: 0px auto;">
                
                <a class="navbar-brand page-scroll" href="#page-top"><img src="images/logo.png" class="img-responsive" /></a>
            </div>
            
	<tiles:importAttribute ignore="true" name="title" />
	<!-- Header End -->
	<tiles:insertAttribute name="body" />
	<div class="fade" id="block7"></div>
	
	</div>
	</div>
</body>
</html>