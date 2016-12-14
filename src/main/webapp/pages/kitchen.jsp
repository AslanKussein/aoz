<%@ page import="java.util.Date" %>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Кухня</title>
    <link rel="icon" type="image/ico" sizes="16x16" href="../images/favicon.ico">
    <script src="/aoz/plugin/jquery/jquery-1.11.3.min.js" type="text/javascript"></script>
    <link href="/aoz/plugin/font-awesome-4.6.3/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <script src="/aoz/plugin/webix/codebase/webix.js" type="text/javascript"></script>

    <%--
        <link href="/aoz/plugin/webix/codebase/skins/air.css" rel="stylesheet" type="text/css"/>
    --%>

    <link href="/aoz/plugin/webix/codebase/webix.css" rel="stylesheet" type="text/css"/>
    <script src="/aoz/plugin/webix/codebase/sidebar.js" type="text/javascript"></script>
    <link href="/aoz/plugin/webix/codebase/sidebar.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="//cdn.webix.com/edge/i18n/ru.js"></script>
    <script src="/aoz/js/newutils.js?version=<%= new Date()%>" type="text/javascript"></script>
    <script src="/aoz/js/kitchen.js?version=<%= new Date()%>" type="text/javascript"></script>
    <link href="/aoz/css/app.css" rel="stylesheet" type="text/css"/>
    <link href="/aoz/plugin/bootstrap-notify/animate.css" rel="stylesheet" type="text/css"/>
    <script src="/aoz/plugin/bootstrap-notify/bootstrap-notify.min.js" type="text/javascript"></script>
</head>
<body  >
<div id="menu" >
    <%@include file='/incloudes/menu.jsp' %>
</div>

<div class="pagetitle">
    <ul class="breadcrumbs">
        <li><a onclick="mainContainerShow()" href="#">Главная страница</a></li>
        <li id="createApp" style="display: none;"><a href="#">Новая заявка</a></li>
        <li id="editApp" style="display: none;"><a href="#">Редактирование заявки</a></li>
        <li id="viewApp" style="display: none;"><a href="#">Просмотр заявки</a></li>
    </ul>
</div>
<br>
<div id="middlecontent" class="middlecontent">

    <div id="mainContainer" class="container"></div>
    <div id="editOrderContainer" ></div>

    <div id="ordersTablePaging"></div>

    <div id="viewOrderTablePaging"></div>
</div>
</body>
</html>