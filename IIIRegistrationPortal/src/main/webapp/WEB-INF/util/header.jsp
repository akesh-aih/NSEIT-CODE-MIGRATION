<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<header class="main-header">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="<s:url action="dashboard"/>">
                <img src="<s:url value="/images/NSE_III_logo.png"/>" alt="Logo" height="40">
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <!-- Dynamic menu items will be loaded here from menu.jsp -->
                    <li class="nav-item">
                        <a class="nav-link" href="<s:url action="dashboard"/>">Home</a>
                    </li>
                </ul>
                <div class="d-flex">
                    <s:if test="#session.user != null">
                        <span class="navbar-text me-3">
                            Welcome, <s:property value="#session.user.userName"/>
                        </span>
                        <a href="<s:url action="LoginAction_logout"/>" class="btn btn-outline-danger">Logout</a>
                    </s:if>
                    <s:else>
                        <a href="<s:url action="LoginAction_input"/>" class="btn btn-outline-primary">Login</a>
                    </s:else>
                </div>
            </div>
        </div>
    </nav>
</header>