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
        <title>Contact List</title>
    </head>
    <body>
        <div class="container">
            <h1>Contact List</h1>
            <div class="table-responsive">
                <table class="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <td>Name</td>
                            <td>Email</td>
                            <td>Address</td>
                            <td>Birth Date</td>
                        </tr>
                    </thead>
                    <%
                        ArrayList<Contact> list = ContactDAO.getList();

                        for (Contact c : list) {
                    %>
                    <tr>
                        <td><%= c.getFirstName()%></td>
                        <td><%= c.getEmail()%></td>
                        <td><%= c.getAddr()%></td>
                        <td><%= new SimpleDateFormat("dd-MM-yyyy").format(c.getBirthDate())%></td>
                    </tr>
                    <%
                        }
                    %>
                </table>
            </div>
        </div>
    </body>
</html>
