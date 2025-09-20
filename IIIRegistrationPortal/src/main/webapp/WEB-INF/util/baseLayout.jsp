<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <tiles:insertAttribute name="headContent"/>
    <title><tiles:getAsString name="title"/></title>
</head>
<body>
    <tiles:insertAttribute name="header"/>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-2">
                <tiles:insertAttribute name="menu" ignore="true"/>
            </div>
            <div class="col-md-10">
                <tiles:insertAttribute name="body"/>
            </div>
        </div>
    </div>
    <tiles:insertAttribute name="footer"/>
    <tiles:insertAttribute name="jsIncludes"/>
</body>
</html>