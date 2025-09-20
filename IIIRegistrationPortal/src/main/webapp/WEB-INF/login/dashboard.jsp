<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="dashboard-container">
    <h2>Welcome to Dashboard</h2>

    <s:if test="#session.user != null">
        <p>Hello, <s:property value="#session.user.userName"/>!</p>
        <p>Your Role: <s:property value="#session.user.roleName"/></p>
        <p>Last Logged In: <s:property value="#session.user.lastLoggedInDateTime"/></p>
    </s:if>
    <s:else>
        <p>Please log in to view the dashboard.</p>
    </s:else>

    <!-- Add more dashboard content here -->
</div>