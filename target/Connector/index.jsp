<%--
  Created by IntelliJ IDEA.
  User: Sharief
  Date: 11/26/2019
  Time: 7:32 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <link rel="stylesheet" href="stylesheet.css">
  <title>Connector</title>
</head>
<body>
<div class="container">
  <div class="animate four">
    <span>C</span>
    <span>O</span>
    <span>N</span>
    <span>N</span>
    <span>E</span>
    <span>C</span>
    <span>T</span>
    <span>O</span>
    <span>R</span>
  </div>
  <span class="span1"></span>
  <span class="span2"></span>
  <span class="span3"></span>
  <span class="span4"></span>
</div>
<div id="id01" class="modal">

  <form class="modal-content animate1" action="LoginServlet" method="get">
    <div class="imgcontainer">
      <span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>
<%--      <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQfo7YrVOAraLO0YXyeWPr3oPLXiy3JzCPI7dYH-bk6J6blZHqQ" alt="Avatar" class="avatar">--%>
<%--    --%>
    </div>

    <div class="contain">
      <label><b>Username</b></label>
      <input type="text" placeholder="Enter Username" name="uname" required>

      <label for="psw"><b>Password</b></label>
      <input type="password" placeholder="Enter Password" name="psw" required>

      <button type="submit" class="login-main">Login</button>

    </div>

    <div class="contain" style="background-color:#f1f1f1">
      <button type="button" onclick="document.getElementById('id01').style.display='none'" class="cancelbtn">Cancel</button>
      <span class="psw">Forgot <a href="#">password?</a></span>
    </div>
  </form>
</div>


<div id="id02" class="modal">
  <span onclick="document.getElementById('id02').style.display='none'" class="close" title="Close Modal">&times;</span>
  <form class="modal-content animate1" action="Controller" method="post">
    <div class="contain">
      <h1>Sign Up</h1>
      <p>Please fill in this form to create an account.</p>
      <hr>
      <label for="email"><b>Email</b></label>
      <input type="email" placeholder="Enter Email" name="email" required>

      <label for="psw"><b>Password</b></label>
      <input type="password" placeholder="Enter Password" name="psw" id="psw" required>

      <label for="psw-repeat"><b>Repeat Password</b></label>
      <input type="password" placeholder="Repeat Password" name="psw-repeat" id="psw-repeat" required>


      <p>By creating an account you agree to our <a href="#" style="color:dodgerblue">Terms & Privacy</a>.</p>

      <div class="clearfix">
        <button type="button" onclick="document.getElementById('id02').style.display='none'" class="cancelbtn2">Cancel</button>
        <button type="submit" class="signup2" onclick="return passwordChecker()">Sign Up</button>
      </div>
    </div>
  </form>
</div>


<div class="myclass">
  <button class="log" onclick="document.getElementById('id01').style.display='block'">Log In</button>
  <button class="sign" onclick="document.getElementById('id02').style.display='block'">Sign Up</button>
</div>
</body>
<script>
  var modal1 = document.getElementById('id01');
  var modal2 = document.getElementById('id02');
  window.onclick = function(event) {
    if (event.target === modal1) {
      modal1.style.display = "none";
    }
    else if(event.target === modal2)
    {
      modal2.style.display = 'none';
    }
  }
  function passwordChecker()
  {
    var psw = document.getElementById('psw');
    var psw2 = document.getElementById('psw-repeat');
    if (psw.value == psw2.value)
    {
      return true;
    }
    else {
      alert("Please repeat the same password.")
      return false;
    }
  }
</script>
</html>
