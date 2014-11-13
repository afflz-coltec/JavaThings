<%-- 
    Document   : ScriptletContactList
    Created on : Oct 16, 2014, 8:01:47 AM
    Author     : Sammy Guergachi <sguergachi at gmail.com>
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="servlet.Contact"%>
<%@page import="servlet.ContactDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <title>Contact List</title>
    </head>
    <body>
        <div class="container">
            <h1 class="text-center">Contact List</h1>
            <div class="table-responsive">
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <td>Name</td>
                            <td>Email</td>
                            <td>Address</td>
                            <td>Birth Date</td>
                        </tr>
                    </thead>
                    
                    <jsp:useBean id="dao" class="servlet.ContactDAO" />
                    <c:forEach var="contact" items="${dao.list}" varStatus="id">
                        <tr>
                            <td>${id.count}</td>
                            <td>${contact.firstname}</td>
                            <td>
                                <c:if test="${not empty contact.email}">
                                    <a href="mailto:${contact.email}">${contact.email}</a>
                                </c:if>
                                <c:if test="${empty contact.email}">
                                    Email nao informado.
                                </c:if>
                            </td>
                            <td>${contact.addr}</td>
                        </tr>
                    </c:forEach>

                </table>
                <h4 class="text-center">Copyright 2014 - Aula de TP</h4>
            </div>
        </div>
    </body>
</html>
