<%--
  Created by IntelliJ IDEA.
  User: Sharief
  Date: 11/26/2019
  Time: 7:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="styling.css">
    <title>Details</title>
</head>
<body>
<form action="SignerServlet" method="post" enctype="multipart/form-data">
    <div class="Main-block">
        <h1>Create an Account</h1>
        <fieldset class="personal">
            <legend>&nbsp Personal details &nbsp</legend>
            <div class="column1">
                <div><label for="name">Full Name</label><input type="text" name="Name" id="name" required></div>
                <div><label for="uname">UserName</label><input type="text" name="uname" id="uname" required></div>
                <div>
                    <label>Gender : </label>
                    <input type="radio" name="gender" value="male" id="male" required>
                    <label for="male">Male</label>
                    <input type="radio" name="gender" value="female" id="female" required>
                    <label for="female">Female</label>
                </div>
                <div><label for="dob">Date of birth*</label><input type="date" name="dob" id="dob" value="date picker" required></div>
            </div>
            <div class="column2">
                <div><label for="email">E-mail</label><input type="email" name="email" value="<%=request.getParameter("email")%>" id="email" readonly></div>
                <div>
                    <label for="profession">Profession</label>
                    <select name="profession" id="profession" required>
                        <option value=""></option>
                        <option value="IT employee">IT Employee</option>
                        <option value="computer engineer">Computer Engineer</option>
                        <option value="teacher">Teacher</option>
                        <option value="student">Student</option>
                        <option value="others">others</option>
                    </select>
                </div>
                <div><label for="company">Organisation</label><input type="text" name="company" id="company" required></div>
                <div><label for="city">City</label><input type="text" name="city" id="city" required></div>
            </div>
        </fieldset>
        <fieldset class="education">
            <legend>&nbsp Education &nbsp</legend>
            <div class="e1">
                <div>
                    <label for="q1">Qualification</label>
                    <select name="q1" id="q1">
                        <option value=""></option>
                        <option value="primary education">Primary School</option>
                        <option value="secondary education">Secondary School</option>
                        <option value="graduation">Graduation</option>
                        <option value="masters">Masters</option>
                        <option value="ph.d/Higher studies">PH.D / or any other equivalent</option>
                    </select>
                </div>
                <div><label for="c1">College</label><input type="text" name="c1" id="c1"></div>
            </div>
            <div class="e2">
                <div>
                    <label for="q2">Qualification</label>
                    <select name="q2" id="q2">
                        <option value=""></option>
                        <option value="primary education">Primary School</option>
                        <option value="secondary education">Secondary School</option>
                        <option value="graduation">Graduation</option>
                        <option value="masters">Masters</option>
                        <option value="ph.d/Higher studies">PH.D / or any other equivalent</option>
                    </select>
                </div>
                <div>
                    <label for="c2">College</label><input type="text" name="c2" id="c2">
                    <input type="button" onclick="addEducation()" id="add">
                </div>
            </div>
            <div class="e3" id="e3">
                <div>
                    <label for="q3">Qualification</label>
                    <select name="q3" id="q3">
                        <option value=""></option>
                        <option value="primary education">Primary School</option>
                        <option value="secondary education">Secondary School</option>
                        <option value="graduation">Graduation</option>
                        <option value="masters">Masters</option>
                        <option value="ph.d/Higher studies">PH.D / or any other equivalent</option>
                    </select>
                </div>
                <div><label for="c3">College</label><input type="text" name="c3" id="c3"></div>
            </div>
            <div class="e4" id="e4">
                <div>
                    <label for="q4">Qualification</label>
                    <select name="q4" id="q4">
                        <option value=""></option>
                        <option value="primary education">Primary School</option>
                        <option value="secondary education">Secondary School</option>
                        <option value="graduation">Graduation</option>
                        <option value="masters">Masters</option>
                        <option value="ph.d/Higher studies">PH.D / or any other equivalent</option>
                    </select>
                </div>
                <div>
                    <label for="c4">College</label><input type="text" name="c4" id="c4">
                </div>
            </div>
        </fieldset>
        <fieldset class="address">
            <legend>&nbsp Address Details &nbsp</legend>
            <div class="adrs1">
                <div><label for="homecity">City</label><input type="text" name="homecity" id="homecity" required></div>
                <div><label for="state">State</label><input type="text" name="state" id="state" required></div>
            </div>
            <div class="adrs2">
                <div><label for="country">Country</label><input type="text" name="country" id="country" required></div>
                <div><label for="phone">phone</label><input type="text" name="phone" id="phone" required></div>
            </div>
        </fieldset>
        <fieldset class="social">
            <legend>&nbsp Social Media &nbsp</legend>
            <div class="s1">
                <div><label for="facebook">Facebook</label><input type="url" name="facebook" id="facebook" placeholder="Paste your facebook profile url"></div>
                <div><label for="twitter">Twitter</label><input type="url" name="twitter" id="twitter" placeholder="Paste your twitter profile url"></div>
                <div><label for="quora">Quora</label><input type="url" name="quora" id="quora" placeholder="Paste your quora profile url"></div>
                <div><label for="instagram">Instagram</label><input type="url" name="instagram" id="instagram" placeholder="Paste your Instagram profile url"></div>
            </div>
            <div class="s2">
                <div><label for="linkedin">Linked In</label><input type="url" name="linkedin" id="linkedin" placeholder="Paste your linkedin profile url"></div>
                <div><label for="youtube">youtube</label><input type="url" name="youtube" id="youtube" placeholder="paste your youtube channel url"></div>
                <div><label for="stackoverflow">StackOverFlow</label><input type="url" name="stackoverflow" id="stackoverflow" placeholder="Paste your stackoverflow profile url"></div>
            </div>
        </fieldset>
        <fieldset class="image-files">
            <legend>&nbsp Upload Images &nbsp</legend>
            <div class="preview1">
                <div><span>Select profile picture</span> <label id="labelp" for="prf-pic">Browse</label><input class="in" type="file" id="prf-pic" name="prf-pic" accept="image/*" onchange="profile(event)" required></div>
                <div class="previews"><img id="prf-preview" src="http://icons.iconarchive.com/icons/ccard3dev/dynamic-yosemite/1024/Preview-icon.png" alt="" width="50%"></div>
            </div>
            <div class="preview2">
                <div><span>Select Cover picture</span> <label id="labelc" for="cvr-pic">Browse</label><input class="in" type="file" id="cvr-pic" name="cvr-pic" accept="video/*,image/*" onchange="cover(event)" required></div>
                <div class="previews"><img id="cvr-preview" src="http://icons.iconarchive.com/icons/ccard3dev/dynamic-yosemite/1024/Preview-icon.png" alt="" width="50%"><input type="hidden" name="coverType" id="coverType"></div>
            </div>
        </fieldset>
        <div class="butt">
            <input type="submit" name="submit" id="submit" value="start" class="submit">
        </div>
    </div>
</form>
</body>
<script>
    function addEducation(event)
    {
        var e3 = document.getElementById('e3');
        var e4 = document.getElementById('e4');
        var add = document.getElementById('add');

        e3.style.display='inline-block';
        e4.style.display='block';
        add.style.display='none';
    }
    function profile(event)
    {
        var label = document.getElementById('labelp');
        var preview = document.getElementById('prf-preview');
        preview.src = URL.createObjectURL(event.target.files[0]);
        if(event.target.files[0].name.length >=13)
        {
            label.innerText = "1 Selected";
        }
        else {
            label.innerText = event.target.files[0].name;
        }
    }

    function cover(event)
    {
        var label = document.getElementById('labelc');
        var preview = document.getElementById('cvr-preview');

        if (document.getElementById('cvr-pic').files[0].type.toString().includes('image'))
        {
            preview.src = URL.createObjectURL(event.target.files[0]);
            document.getElementById('coverType').value = 'image';
        }
        else {
            document.getElementById('coverType').value = 'video';
        }
        if(event.target.files[0].name.length >=13)
        {
            label.innerText = "1 Selected";
        }
        else {
            label.innerText = event.target.files[0].name;
        }
    }
</script>
</html>
