<%@ page import="com.User" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.util.Stack" %>
<%@ page import="com.Album" %>
<%@ page import="java.util.Arrays" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f" %>
<%@page errorPage="ErrorPage.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <script src="https://kit.fontawesome.com/3c21dacc40.js" crossorigin="anonymous"></script>
    <title>Connector</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="Home.css">
    <link rel="stylesheet" href="slideshow.css" type="text/css">
    <link rel="stylesheet" href="Notification.css">
    <link rel="stylesheet" href="Sidenav.css">
    <link rel="stylesheet" href="Messenger.css">
    <link rel="stylesheet" href="NavBar.css">
    <link rel="stylesheet" href="Post.css">

    <style>
        #repair :hover{
            color: #333333;
        }
    </style>
</head>
<%
    response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
    if (request.getSession().getAttribute("user")==null)
    {
        response.sendRedirect("index.jsp");
    }
    User user = (User) request.getSession().getAttribute("user");
    Stack<Album> myAlbums = user.getAlbums();
    pageContext.setAttribute("myAlbums",myAlbums);
    pageContext.setAttribute("coverType",user.getData().isVideoAsCover());
%>
<div class="header" id="HEADER">
<%--    <c:if test="${coverType}">--%>
<%--        <video id="myVideo" style="min-width: 100%" autoplay muted loop>--%>
<%--            <source src="<%=request.getContextPath()%>/images/<%=user.getPersonalData().getUsername()%>/<%=user.getCvr_picture()%>" type="video/mp4">--%>
<%--            Your browser does not support HTML5 video.--%>
<%--        </video>--%>
<%--    </c:if>--%>
    <div class="greeting">
        <h3>Hello! <%=user.getPersonalData().getUsername()%></h3>
        <h1 id="wish">Good Morning</h1>
    </div>
    <div class="clock">
        <div id="time"></div>
        <div id="date"></div>
    </div>
</div>

<div class="topnav" id="myTopnav">
    <a href="#myTopnav" class="active"><i class="fas fa-home"></i>Home</a>
    <a href="<%=request.getContextPath()%>/Explore.jsp"><i class="fas fa-search"></i>Explore</a>
    <a onclick="openNav()" style="color: white;" id="repair"><i class="fas fa-comments"></i>Message</a>
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




<div class="row">
    <div class="side">
        <div class="prf-img">
            <img src="<%=request.getContextPath()%>/images/<%=user.getPersonalData().getUsername()%>/<%=user.getPrf_picture()%>" alt="profile picture" id="prf-pic">
        </div>

        <div class="details" id="DETAILS">
            <h1><%=user.getPersonalData().getName()%></h1>
            <div class="profession"><p><i class="fas fa-user-tie"></i><%=user.getData().getAdrs().getProfession()%> at <%=user.getData().getAdrs().getOrg()%></p></div>
            <div class="profession"><p><i class="fas fa-birthday-cake"></i><%=user.getPersonalData().getDob()%></p></div>
            <div class="profession"><p><i class="fas fa-map-marker-alt"></i>lives in <b><%=user.getData().getAdrs().getHome()%></b> </p></div>
<%--            <c:set var="Q" value="<%=user.getData().getEdu().getQ4()%>"/>--%>
<%--            <c:if test="${!(Q == '')}">--%>
<%--                <div class="profession"><p><i class="fas fa-user-graduate"></i>studied <c:out value="${Q}"/> at <b><%=user.getData().getEdu().getC4()%></b></p></div>--%>
<%--            </c:if>--%>
<%--            <c:set var="Q" value="<%=user.getData().getEdu().getQ3()%>"/>--%>
<%--            <c:if test="${!(Q == '')}">--%>
<%--                <div class="profession"><p><i class="fas fa-graduation-cap"></i>studied <c:out value="${Q}"/> at <b><%=user.getData().getEdu().getC3()%></b></p></div>--%>
<%--            </c:if>--%>
<%--            <c:set var="Q" value="<%=user.getData().getEdu().getQ2()%>"/>--%>
<%--            <c:if test="${!(Q == '')}">--%>
<%--                <div class="profession"><p><i class="fas fa-university"></i>studied <c:out value="${Q}"/> at <b><%=user.getData().getEdu().getC2()%></b></p></div>--%>
<%--            </c:if>--%>
<%--            <c:set var="Q" value="<%=user.getData().getEdu().getQ1()%>"/>--%>
<%--            <c:if test="${!(Q == '')}">--%>
<%--                <div class="profession"><p><i class="fas fa-school"></i>studied <c:out value="${Q}"/> at <b><%=user.getData().getEdu().getC1()%></b></p></div>--%>
<%--            </c:if>--%>
        </div>


        <div class="post-bar">
            <div id="frnds-div">
                <a id="friends" href="Explore.jsp" style="text-decoration: none;color: black">
                    <h1 style="margin: 3px;"><i class="fas fa-users"></i></h1>
                    <h5 style="margin: 3px;text-align: center;"><%=user.getMyFriends().size()%></h5>
                </a>
            </div>
            <div id="post-div">
                <button id="post-btn" onclick="PostOpener()">upload new post</button>
            </div>
        </div>

    </div>

    <div class="main" id="MAIN">
        <div id="filter-tab" style="background-color: white;box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);padding: 10px;">
            <h1 style="margin-top: 5px;"><b>My Portfolio</b></h1>
            <span style="margin-right: 10px;">View:</span>
            <button class="active-filter" id="all">Album</button>
<%--            <button><i class="fa fa-diamond w3-margin-right"></i>Design</button>--%>
<%--            <button><i class="fa fa-photo w3-margin-right"></i>Photos</button>--%>
<%--            <button><i class="fa fa-map-pin w3-margin-right"></i>Art</button>--%>
        </div>


<%--        <c:forEach var="albums" items="${myAlbums}">--%>
<%--            <div class="SlideShow" style="position: relative;" id="SLIDESHOW">--%>
<%--                <c:forEach var="medias" items="${albums.media}">--%>
<%--                    <c:set var="filetype" value="${medias.value}"/>--%>
<%--                    <c:choose>--%>
<%--                        <c:when test="${f:contains(filetype, 'video')}">--%>
<%--                            <video class ="${albums.className}" src="<%=request.getContextPath()%>/images/<%=user.getPersonalData().getUsername()%>/${albums.album_ID}/${medias.key}" style="width: 100%;" controls></video>--%>
<%--                        </c:when>--%>
<%--                        <c:when test="${f:contains(filetype, 'image')}">--%>
<%--                            <img class="${albums.className}" src="<%=request.getContextPath()%>/images/<%=user.getPersonalData().getUsername()%>/${albums.album_ID}/${medias.key}" style="width:100%">--%>
<%--                        </c:when>--%>
<%--                    </c:choose>--%>
<%--                </c:forEach>--%>
<%--                <button class="left-button" onclick="plusDivs(-1, ${albums.albumNumber-1})">&#10094;</button>--%>
<%--                <button class="right-button" onclick="plusDivs(1, ${albums.albumNumber-1})">&#10095;</button>--%>
<%--            </div>--%>
<%--        </c:forEach>--%>


<%--        <div class="SlideShow" style="position: relative;">--%>
<%--            <img class="mySlides1" src="https://images.pexels.com/photos/547114/pexels-photo-547114.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500" style="width:100%">--%>
<%--            <img class="mySlides1" src="https://images.pexels.com/photos/255441/pexels-photo-255441.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500" style="width:100%">--%>
<%--            <img class="mySlides1" src="https://images.pexels.com/photos/719597/pexels-photo-719597.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500" style="width:100%">--%>
<%--            <img class="mySlides1" src="https://images.pexels.com/photos/1106452/pexels-photo-1106452.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500" style="width:100%">--%>
<%--            <video class ="mySlides1" src="https://www.w3schools.com/html/mov_bbb.mp4" style="width: 100%;" controls></video>--%>
<%--            <button class="left-button" onclick="plusDivs(-1, 0)">&#10094;</button>--%>
<%--            <button class="right-button" onclick="plusDivs(1, 0)">&#10095;</button>--%>
<%--            <span class="slideNumber">1/4</span>--%>
<%--            <span class="slideDate">09 Feb 2020</span>--%>
<%--        </div>--%>



<%--        <div class="SlideShow" style="position: relative;">--%>
<%--            <img class="mySlides2" src="https://images.pexels.com/photos/733174/pexels-photo-733174.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500" style="width:100%">--%>
<%--            <img class="mySlides2" src="https://images.pexels.com/photos/194096/pexels-photo-194096.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500" style="width:100%">--%>
<%--            <img class="mySlides2" src="https://images.pexels.com/photos/333850/pexels-photo-333850.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500" style="width:100%">--%>
<%--            <button class="left-button" onclick="plusDivs(-1, 1)">&#10094;</button>--%>
<%--            <button class="right-button" onclick="plusDivs(1, 1)">&#10095;</button>--%>
<%--        </div>--%>


<%--        <div class="SlideShow" style="position: relative;">--%>
<%--            <img class="mySlides3" src="https://images.pexels.com/photos/547114/pexels-photo-547114.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500" style="width:100%">--%>
<%--            <img class="mySlides3" src="https://images.pexels.com/photos/255441/pexels-photo-255441.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500" style="width:100%">--%>
<%--            <img class="mySlides3" src="https://images.pexels.com/photos/719597/pexels-photo-719597.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500" style="width:100%">--%>
<%--            <img class="mySlides3" src="https://images.pexels.com/photos/1106452/pexels-photo-1106452.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500" style="width:100%">--%>
<%--            <button class="left-button" onclick="plusDivs(-1, 2)">&#10094;</button>--%>
<%--            <button class="right-button" onclick="plusDivs(1, 2)">&#10095;</button>--%>
<%--        </div>--%>

    </div>


    </div>



</div>

<div class="footer" id="FOOTER">
<%--    <c:set var="social" value="<%=user.getPersonalData().getEmail()%>"/>--%>
<%--    <c:if test="${!(social=='')}">--%>
<%--        <a href="mailto:${social}" target="_blank"><img src="https://image.flaticon.com/icons/png/512/281/281764.png" alt="Gmail" width="30px"></a>--%>
<%--    </c:if>--%>
<%--    <c:set var="social" value="<%=user.getData().getSocial().getFacebook()%>"/>--%>
<%--    <c:if test="${!(social=='')}">--%>
<%--        <a href="${social}" target="_blank"><img src="https://image.flaticon.com/icons/png/512/174/174848.png" alt="Facebook"  width="30px"></a>--%>
<%--    </c:if>--%>
<%--    <c:set var="social" value="<%=user.getData().getSocial().getInstagram()%>"/>--%>
<%--    <c:if test="${!(social=='')}">--%>
<%--        <a href="${social}" target="_blank"><img src="https://image.flaticon.com/icons/png/512/174/174855.png" alt="instagram" width="30px"></a>--%>
<%--    </c:if>--%>
<%--    <c:set var="social" value="<%=user.getData().getSocial().getLinkedin()%>"/>--%>
<%--    <c:if test="${!(social=='')}">--%>
<%--        <a href="${social}" target="_blank"><img src="https://image.flaticon.com/icons/png/512/174/174857.png" alt="linked-in" width="30px"></a>--%>
<%--    </c:if>--%>
<%--    <c:set var="social" value="<%=user.getData().getSocial().getTwitter()%>"/>--%>
<%--    <c:if test="${!(social=='')}">--%>
<%--        <a href="${social}" target="_blank"><img src="https://image.flaticon.com/icons/png/512/733/733579.png" alt="Twitter" width="30px"></a>--%>
<%--    </c:if>--%>
<%--    <c:set var="social" value="<%=user.getData().getSocial().getQuora()%>"/>--%>
<%--    <c:if test="${!(social=='')}">--%>
<%--        <a href="${social}" target="_blank"><img src="https://image.flaticon.com/icons/png/512/174/174865.png" alt="Quora" width="30px"></a>--%>
<%--    </c:if>--%>
<%--    <c:set var="social" value="<%=user.getData().getSocial().getOverflow()%>"/>--%>
<%--    <c:if test="${!(social=='')}">--%>
<%--        <a href="${social}" target="_blank"><img src="https://cdn.sstatic.net/Sites/stackoverflow/company/img/logos/so/so-icon.svg?v=f13ebeedfa9e" alt="Stackoverflow" width="30px"></a>--%>
<%--    </c:if>--%>
<%--    <c:set var="social" value="<%=user.getData().getSocial().getYoutube()%>"/>--%>
<%--    <c:if test="${!(social=='')}">--%>
<%--        <a href="${social}" target="_blank"><img src="https://image.flaticon.com/icons/png/512/174/174883.png" alt="Youtube" width="30px"></a>--%>
<%--    </c:if>--%>

</div>


<%@include file="Post.html"%>

<%@ include file="Notifications.jsp"%>

<%@ include file="Sidenav.jsp"%>

<%@include file="Messenger.html" %>

</body>

<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="HomePageRepair.js"></script>


<script src="Home.js"></script>
<script src="slideshow.js"></script>
<script>
<%--    <%@ include file="Home.js"%>--%>
<%--    <%@include file="slideshow.js"%>--%>
</script>
<script src="Post.js"></script>
<script>
    <%@include file="Notification.js"%>
    <%@include file="Messenger.js"%>
</script>
</html>
