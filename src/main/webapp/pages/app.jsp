<%-- 
    Document   : createapp
    Created on : 21.01.2016, 16:24:46
    Author     : a.amanzhol
--%>
<%@page import="kz.gcvp.app.util.AccessManager" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" type="image/ico" sizes="16x16" href="../images/favicon.ico">
        <title>Заявления</title>
        <script src="/aoz/plugin/jquery/jquery-1.11.3.min.js" type="text/javascript"></script>
        <link href="/aoz/plugin/font-awesome-4.6.3/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
        <script src="/app/plugin/webix/codebase/webix.js" type="text/javascript"></script>
        <link href="/app/plugin/webix/codebase/webix.css" rel="stylesheet" type="text/css"/>
        <script src="/app/plugin/webix/codebase/sidebar.js" type="text/javascript"></script>
        <link href="/app/plugin/webix/codebase/sidebar.css" rel="stylesheet" type="text/css"/>
        <script src="/app/js/createapp.js" type="text/javascript"></script>
        <script src="/app/js/app.js" type="text/javascript"></script>
        <link href="/app/css/app.css" rel="stylesheet" type="text/css"/>
        <script src="/app/js/appfields.js" type="text/javascript"></script>
    </head>
    <body>
        <div id="menu">
            <%@include file='/incloudes/menu.jsp' %>
        </div>

        <div class="middlecontent">
            <div id="mainlayot"></div>

            <div id="appsTablePaging"></div>
        </div>
    </body>
</html>
