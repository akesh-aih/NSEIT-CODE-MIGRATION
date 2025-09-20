<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:set var="contextPath" value="#request.contextPath"/>
<script type="text/javascript">
    window.location.replace("<s:property value="contextPath"/>/LoginAction_input.action");
</script>
<noscript>
    <meta http-equiv="refresh" content="0;url=<s:property value="contextPath"/>/LoginAction_input.action" />
</noscript>
