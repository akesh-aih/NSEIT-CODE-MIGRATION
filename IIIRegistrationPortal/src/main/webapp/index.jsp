<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
    window.location.replace("<%= request.getContextPath() %>/LoginAction_input.action");
</script>
<noscript>
    <meta http-equiv="refresh" content="0;url=<%= request.getContextPath() %>/LoginAction_input.action" />
</noscript>
