<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><tiles:getAsString name="title"/></title>
    <link rel="stylesheet" href="<tiles:getAsString name="contextPath"/>/css/bootstrap.min.css">
    <link rel="stylesheet" href="<tiles:getAsString name="contextPath"/>/css/common.css">
    <link rel="stylesheet" href="<tiles:getAsString name="contextPath"/>/css/jquery-ui.min.css">
    <!-- Add any other global CSS here -->
</head>
<body>
    <tiles:insertAttribute name="header"/>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <tiles:insertAttribute name="body"/>
            </div>
        </div>
    </div>
    <tiles:insertAttribute name="footer"/>

    <!-- JavaScript includes -->
    <script src="<tiles:getAsString name="contextPath"/>/js/jquery-3.2.1.min.js"></script>
    <script src="<tiles:getAsString name="contextPath"/>/js/bootstrap.min.js"></script>
    <script src="<tiles:getAsString name="contextPath"/>/js/jquery-ui.min.js"></script>
    <script src="<tiles:getAsString name="contextPath"/>/js/jquery.validate.min.js"></script>
    <script src="<tiles:getAsString name="contextPath"/>/js/additional-methods.min.js"></script>
    <script src="<tiles:getAsString name="contextPath"/>/js/jquery.timepicker.min.js"></script>
    <script src="<tiles:getAsString name="contextPath"/>/js/RegexDef.js"></script>
    <script src="<tiles:getAsString name="contextPath"/>/js/app/commonmethods.js"></script>
    <script src="<tiles:getAsString name="contextPath"/>/js/app/prelogin.js"></script>
    <script src="<tiles:getAsString name="contextPath"/>/js/app/ticker.js"></script>
    <!-- Add any other global JS here -->
</body>
</html>