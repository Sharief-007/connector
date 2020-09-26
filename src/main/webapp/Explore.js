
var ajaxData,identity;

$(document).ready(function () {

    fetchData();

    fetch("fetcher").then(data => {
        return data.text()
    }).then((res) => {
        counter();
        HolyFork(JSON.parse(res))
    }).catch((err) => {
        console.log(err)
    })

    showNumber();
});

function HolyFork(res) {

    for (i=0;i<res.length;i++){
        console.log("aaaaa"+i)
        let div1 = document.createElement("div");
        div1.className = "person"

        let div2 = document.createElement("div")
        div2.className = "image"

        let img = document.createElement("img")
        let usrnme = ((res[i]).personalData).username;
        img.src = "images/"+usrnme+"/"+res[i].prf_picture
        div2.appendChild(img)
        console.log(img.src)

        let div3 = document.createElement("div")
        div3.className = "names"
        let h1 = document.createElement("h1")
        h1.className = "UserName"
        h1.innerText = usrnme
        let p = document.createElement("p")
        p.className = "FullName"
        p.innerText = res[i].personalData.name
        div3.append(h1,p)

        let div4 = document.createElement("div")
        div4.className = "butns"
        let btn = document.createElement("button")
        btn.setAttribute("id",res[i].id)
        btn.setAttribute("class","button")
        btn.addEventListener("click",()=>{
            console.log("clickeddddddddddddddd ::::::::::"+res[i].id)
            showLoader();
            identity = res[i].id;

            console.log(ajaxData)
            user = ajaxData.getElementById(res[i].id);

            changeImage(user);
            changeNames(user);
            changeDetails(user);
            changeSocial(user);

            FriendRequestEnquiry(user);

            showCard();
        });
        btn.innerText = "view profile";
        div4.appendChild(btn)

        div1.appendChild(div2);
        div1.appendChild(div3);
        div1.appendChild(div4);

        (document.getElementsByClassName("list")[0]).append(div1)
    }

}

function counter(){
    fetch("counter").then(d => {
        return d.text()
    }).then(res => {
        let string = `<h1 style=\"text-align: center\"><p style=\"font-size: 5em;margin: auto;color: #3498db;text-align: center\">${res}</p>People had joined the connector</h1>`
        console.log("Count ::::::::::::" +res)
        document.getElementsByClassName("number")[0].innerHTML = string;
    }).catch(console.error)
}

function fetchData()
{
    ajax = new XMLHttpRequest();
    ajax.open("GET","UsersList");
    ajax.onload = function () {
        ajaxData = ajax.responseXML;
    }
    ajax.send();
    //fetch("UsersList").then(r => r.text()).then(d=> {ajaxData = new DOMParser();ajaxData.parseFromString(d,"text/xml")}).catch(console.error)
}
function HolyCard(symbol) {
    console.log("clickeddddddddddddddd ::::::::::"+symbol)
    showLoader();
    //id=$(this).attr("id");
    identity = symbol;

    console.log(ajaxData)
    user = ajaxData.getElementById(symbol);

    changeImage(user);
    changeNames(user);
    changeDetails(user);
    changeSocial(user);

    FriendRequestEnquiry(user);

    showCard();
}

function changeImage(user) {
    uname = user.getElementsByTagName("username")[0].innerHTML;
    prf = user.getElementsByTagName("prf_picture")[0].innerHTML;
    console.log("Image :"+"<%=request.getContextPath()%>/images/"+uname+"/"+prf);
    $("#profile-picture").attr("src","<%=request.getContextPath()%>/images/"+uname+"/"+prf);
}

function changeNames(user) {
    names = $(".card-names").children();
    names[0].innerHTML = user.getElementsByTagName("name")[0].innerHTML;
    names[1].innerText = "@"+user.getElementsByTagName("username")[0].innerHTML;
}

function changeDetails(user) {
    profession = user.getElementsByTagName("profession")[0].innerHTML;
    dob = user.getElementsByTagName("dob")[0].innerHTML;
    city = user.getElementsByTagName("city")[0].innerHTML;
    dob = new Date(dob).toDateString();
    dob = dob.substring(3);
    console.log("prof :"+profession);
    console.log("dob :"+dob);
    console.log("city :"+city);
    $("#professsion").html(profession);
    $("#dob").html(dob);
    $("#place").html(city);
}

function changeSocial(user) {
    fb = user.getElementsByTagName("facebook")[0].innerHTML;
    twt = user.getElementsByTagName("twitter")[0].innerHTML;
    yt = user.getElementsByTagName("youtube")[0].innerHTML;
    lin = user.getElementsByTagName("linkedin")[0].innerHTML;
    document.getElementById("facebook").setAttribute("href",fb);
    document.getElementById("twitter").setAttribute("href",twt);
    document.getElementById("youtube").setAttribute("href",yt);
    document.getElementById("linkedin").setAttribute("href",lin);
}

function FriendRequestEnquiry(user) {
    MyUserId = '<%=user.getId()%>';
    found = false;
    rqsts = user.getElementsByTagName("requests");
    btn = document.getElementById('request');
    for (i=0;i<rqsts.length;i++)
    {
        console.log(MyUserId+"..........."+rqsts[i].innerHTML);
        if (MyUserId == rqsts[i].innerHTML)
        {
            found = true;
            break;
        }
    }
    if (found)
    {
        $('#request').html('Request sent <i class="far fa-check-circle"></i>');
        btn.className = 'already';
    }
    else {
        btn.className ='oldrequest';
        btn.innerHTML = 'send request';
    }
}


function showCard()
{
    $(".card").show();
    $(".right").css("display","block");
    $(".number").hide();
    $(".BIGloader").hide();
}
function showNumber(){
    $(".card").hide();
    $(".right").css("display","flex");
    $(".number").show();
    $(".BIGloader").hide();
}
function showLoader() {
    $(".card").hide();
    $(".right").css("display","flex");
    $(".number").hide();
    $(".BIGloader").show();
}

function filter() {
    value=document.getElementById("mySearch").value.toLowerCase();
    List = document.getElementsByClassName("person");
    fullnames = document.getElementsByClassName("FullName");
    usernames = document.getElementsByClassName("UserName");
    for (i=0;i<List.length;i++)
    {
        if (usernames[i].innerHTML.toLowerCase().indexOf(value)>-1 || fullnames[i].innerHTML.toLowerCase().indexOf(value)>-1){
            List[i].style.display = "flex";
        }
        else {
            List[i].style.display = "none";
        }
    }
}
function myFunction() {
    var x = document.getElementById("myTopnav");
    if (x.className === "topnav") {
        x.className += " responsive";
    } else {
        x.className = "topnav";
    }
}

function sendRequest(btn)
{
    if (btn.className == 'already')
    {
        alert("Friend request alredy sent");
    }
    else {
        to_id = identity;
        from_id = "<%=user.getId()%>";
        $.ajax({
            type : 'POST',
            data : {from_id : from_id, to_id :to_id },
            url : 'RequestManager',
            success : function (result) {
                console.log(result);
                btn.className = 'already';
                $('#request').html('Request sent <i class="far fa-check-circle"></i>');
            }
        });
    }
}