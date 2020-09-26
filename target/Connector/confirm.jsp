<%--
  Created by IntelliJ IDEA.
  User: Sharief
  Date: 11/22/2019
  Time: 9:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%! int count =180; %>
<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Lato:400,900&display=swap" rel="stylesheet">
    <style>
        * {
            font-family: 'Lato', sans-serif;
            font-size: 1.05em;
        }
        .content {
            position: absolute;
            width: auto;
            top: 50%;
            left: 50%;
            transform: translate(-50%,-50%);
            background-color: rgba(221,221,221,0.8);
            padding: 0 2%;
            border-radius: 10px;
        }

        input[type=text] {
            width: 100%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }
        input[type=text]:focus {
            outline: none;
        }
        .butt {
            background: #1aa3ff;
            border: none;
            font-size: large;
            margin: 0 10px;
            padding:10px 12px;
            color: white;
            border-radius: 10px;
        }
        .butt:hover {
            background-color:rgba(26, 163, 255, 0.8);
            cursor: pointer;
        }
        .buttons {
            position: absolute;
            bottom: -25%;
            right: 1%;
        }
        .h {
            width: 100%;
            background-color: #1aa3ff;
            text-align: center;
            font-size: 5vw;
            color: white;
        }
    </style>
    <title>Connector</title>
</head>
<body>
<div>
    <div class="h">
        <h1 id="display"></h1>
    </div>
    <div class="content">
        <p>Our team <b style="color: #1aa3ff;">Connector</b> has sent an One Time Password (OTP) to the <i><%=request.getParameter("email")%>&nbsp</i>which is valid for 5 mins. This OTP is used only to make ensure that the provided email address is really maintained by you.</p>
        <p>Please check your e-mail address and enter the OTP and submit.</p>
        <p>Click on Resend to receive another OTP to your e-mail.</p>
        <div class="forms">
            <form action="AddUser" onsubmit="return validateOTP()" class="myform" method="post">
                <label style="font-weight: 900;">Enter Your OTP here</label>
                <input type="text" id="text" autocomplete="off" required>
                <input type="hidden" name="psw" value="<%=request.getAttribute("psw")%>">
                <input type="hidden" name="email" value="<%= request.getAttribute("email")%>">
                <div class="buttons">
                    <input type="submit" class="butt" value="Submit" id="submit-button">
            </form>
            <form action="Controller" style="float: right;" method="post">
                <input type="hidden" name="psw" value="<%=request.getAttribute("psw")%>">
                <input type="hidden" name="email" value="<%= request.getAttribute("email")%>">
<%--                <input type="hidden" name="psw-repeat" value="<%= request.getAttribute("psw2")%>">--%>
                <input type="submit" value="Resend" class="butt">
            </form>
        </div>
    </div>
    </div>
</div>
<%--<b><%=request.getAttribute("OTP")%></b>--%>
</body>
<script>
    var sec = <%=count%>;
    var s,m,ID;
    var display = document.getElementById("display");
    window.onload = timer;

    function timer() {
        ID = setInterval(decreaseTime,1000);
    }

    function decreaseTime() {
        if (sec >=1)
        {
            sec--;
            display.innerText = format(sec);
        }
        else {
            var submit= document.getElementById("submit-button");
            submit.style.display = "none";
            display.innerText = 'Time Out';
            clearInterval(ID);
        }
    }
    function format(seconds) {
        m = Math.trunc(seconds/60);
        s = seconds % 60;
        if(s<10 && m<10)
        {
            return "0"+m+" : 0"+s;
        }
        else if(m<10)
        {
            return "0"+m+" : "+s;
        }
        else if(s<10)
        {
            return m+" : 0"+s;
        }
        else {
            return m+" : "+s;
        }
    }
    function validateOTP() {
        var text = document.getElementById("text").value;
        var sentOTP = <%=request.getAttribute("OTP")%>;
        if (text==sentOTP)
        {
            return true;
        }
        else {
            alert("Invalid OTP");
            document.getElementById("text").style.border = '1px solid red';
            return false;
        }
    }
</script>
</html>
