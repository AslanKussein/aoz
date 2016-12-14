
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%
    if (request.isUserInRole("kitchen_role")) {
%>
<%@include file='kitchen.jsp' %>
<%
    } else {
%>
<%@include file='import.jsp' %>
<%
    }
%>