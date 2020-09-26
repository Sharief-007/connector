<%@page import="java.util.ArrayList"%>
<%@ page import="com.User" %>
<%@ page import="com.google.gson.Gson" %>
<%--
  Created by IntelliJ IDEA.
  User: Sharief
  Date: 1/1/2020
  Time: 2:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%--<%@page errorPage="ErrorPage.jsp" %>--%>
<!DOCTYPE html>
<html>
<head>
    <title>Explore-Connector</title>
    <link rel="stylesheet" href="Explore.css">
    <link rel="stylesheet" href="NavBar.css">
    <link rel="stylesheet" href="Notification.css">
    <link rel="stylesheet" href="Messenger.css">
    <link rel="stylesheet" href="Sidenav.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://kit.fontawesome.com/3c21dacc40.js"></script>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
</head>
<body>
<%
    response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
    if (request.getSession().getAttribute("user")==null)
    {
        response.sendRedirect("index.jsp");
    }
    User user = (User) request.getSession().getAttribute("user");
%>
<div class="topnav" id="myTopnav">
    <a href="<%=request.getContextPath()%>/Home.jsp" ><i class="fas fa-home"></i>Home</a>
    <a href="#myTopnav" class="active"><i class="fas fa-search"></i>Explore</a>
    <a onclick="openNav()"><i class="fas fa-comments"></i>Message</a>
    <div class="dropdown">
        <button class="dropbtn" onclick="getNotifications()"><i class="fas fa-bell"></i>Notification</button>
        <div class="dropdown-content" id="dropdown-content">
            <a href="#" id="opener"><i class="fas fa-user-friends"></i>Friend-requests</a>
            <a href="#"><i class="fas fa-comment-dots"></i>New messages</a>
            <a href="#"><i class="far fa-bell"></i>Others</a>
        </div>
    </div>
    <div class="topnav-right">
        <a href="LogoutServlet"><i class="fas fa-sign-out-alt"></i>Log Out</a>
    </div>
    <a href="javascript:void(0);" style="font-size:15px;" class="icon" onclick="myFunction()">&#9776;</a>
</div>




<div class="content">
    <div class="left">
        <div class="left-header">
            <h1>Find Connections</h1>
            <div class="search">
                <input type="search" placeholder="Search..." id="mySearch" title="Search for people" onkeyup="filter()">
            </div>
        </div>
        <div class="list">
<%--            <c:import url="UsersList" var="xmlList"/>--%>
<%--            <x:parse xml="${xmlList}" var="List" scope="page"/>--%>
<%--            <x:forEach select="$List/users/user">--%>
<%--                <div class="person">--%>
<%--                    <div class="image">--%>
<%--                        <img src="<%=request.getContextPath()%>/images/<x:out select="personalData/username"/>/<x:out select="prf_picture"/>" alt="">--%>
<%--                    </div>--%>
<%--                    <div class="names">--%>
<%--                        <h1 class="UserName"><x:out select="personalData/username"/></h1>--%>
<%--                        <p class="FullName"><x:out select="personalData/name"/></p>--%>
<%--                    </div>--%>
<%--                    <div class="butns">--%>
<%--                        <button type="button" name="button" id="<x:out select="@id"/>" href="#card" class="button">view profile</button>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </x:forEach>--%>
        </div>
    </div>
    <div class="right">
<%--        <sql:setDataSource var="db" driver="com.mysql.cj.jdbc.Driver" url="jdbc:mysql://localhost:3306/Connector?verifyServerCertificate=false&useSSL=true" user="sharief" password="sharief" />--%>
<%--        <sql:query dataSource="${db}" var="rs">--%>
<%--            select count(id) from credentials;--%>
<%--        </sql:query>--%>
        <div class="number">
<%--            <h1 style="text-align: center"><p style="font-size: 5em;margin: auto;color: #3498db;text-align: center"><c:forEach items="${rs.rows}" var="num"><c:out value='${num["count(id)"]}'/></c:forEach></p>People had joined the connector</h1>--%>
<%--        --%>
        </div>
<%--        <div class="BIGloader"></div>--%>
        <div class="card" id="card">
            <div class="card-header">
                <img id="profile-picture" src="https://scontent.fvga2-1.fna.fbcdn.net/v/t1.0-1/p160x160/44213211_2233084710299650_6668424085569011712_o.jpg?_nc_cat=103&_nc_ohc=5_IWomy8h2MAQmw-Rz-UtbN2xVTP2NfWij1UX72Ao19XqdHxW8GAi30wQ&_nc_ht=scontent.fvga2-1.fna&oh=31fe6e51ef0ecc9a52d47f1d87399ce2&oe=5E7504C6" alt="">
            </div>
            <div class="card-body">
                <div class="card-names">
                    <h1>Mohammad Sharief Baig</h1>
                    <p>@sharief</p>
                </div>
                <div class="card-details">
                    <div class="details"><p><i class="fas fa-user-tie"></i><span id="professsion">&nbsp &nbsp&nbspStudent</span></p></div>
                    <div class="details"><p><i class="fas fa-birthday-cake"></i><span id="dob">&nbsp &nbsp&nbsp 04 Nov 1999</span></p></div>
                    <div class="details"><p><i class="fas fa-map-marker-alt"></i><span id="place">&nbsp &nbsp&nbsp lives in <b>Vijayawada </b></span></p></div>
                    <div class="details"><p><i class="fas fa-long-arrow-alt-right"></i><span id="bio">&nbsp &nbsp&nbsp Sunt in culpa qui officia deserunt mollit anim id est laborum consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.</span></p></div>
                </div>
                <div class="card-social">
                    <a href="" id="facebook" target="_blank"><i class="fab fa-facebook-square" title="facebook"></i></a>
                    <a href="" id="twitter" target="_blank"><i class="fab fa-twitter" title="twitter"></i></a>
                    <a href="" id="youtube" target="_blank"><i class="fab fa-youtube" title="youtube"></i></a>
                    <a href="" id="linkedin" target="_blank"><i class="fab fa-linkedin" title="linkedin"></i></a>
                </div>
                <div class="card-footer">
                    <a href="#" id="request" onclick="sendRequest(this)" class="oldrequest">send request</a>
                    <a href="index.html">view full profile</a>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="Notifications.jsp"%>

<%@ include file="Sidenav.jsp"%>

<%@include file="Messenger.html" %>

</body>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
    <%@include file="Explore.js"%>
    <%@include file="Notification.js"%>
    <%@include file="Messenger.js"%>

    <%@include file="lastFile.js"%>
</script>
</html>
