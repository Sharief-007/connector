
var msgRefresher;
var DIV;
var timeInterval;
$(document).ready(function () {

    $.ajax({
        type : 'GET',
        url: 'UsersList?want=json',
        success: function (result) {
            var onlineUsers = JSON.parse(result);
            getAndSetOnlineUsers(onlineUsers);
            timeInterval = setInterval(getAndSetOnlineUsers,30000,onlineUsers);
        },
        error:function(error) {
            alert(error.toString());
        }
    });

    // getAndSetOnlineUsers();
    // timeInterval = setInterval(getAndSetOnlineUsers,30000);

});

function openNav() {
    document.getElementById("mySidenav").style.width = "100%";
}
function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
}

function getAndSetOnlineUsers(users)
{
    xhr = new XMLHttpRequest();
    xhr.open("get","FriendsList", true);
    xhr.onload = function () {
        //users = JSON.parse(xhr.responseText);
        friends= xhr.responseText;
        friends = JSON.parse(friends);

        $("#mySidenav").html('<div class="closebtn" onclick="closeNav()">&times;</div>');

        for (i=0;i<friends.length;i++)
        {
            x = friends[i].toString();
            check = (users[x])[1];
            console.log(check);
            div = document.createElement("div");
            div.setAttribute("number",friends[i]);
            //div.setAttribute("onclick","functionName(this,users)");
            div.addEventListener("click",function () {
                functionName(div,users);
            });

            span1 = document.createElement("span");
            span1.innerHTML = (users[x])[0];
            span2 = document.createElement("span");
            if (check == 'true')
            {
                span2.style.color = "rgb(73, 226, 78)";
            }
            else {
                span2.style.color = "red";
            }
            span2.innerHTML = "&#10625;";
            span2.style.paddingRight = "15px";
            span2.style.float = "right";
            div.appendChild(span1);
            div.appendChild(span2);
            document.getElementById('mySidenav').appendChild(div);

            console.table(users);
        }
    };
    xhr.send();
}

function functionName(div,data)
{
    showChat(div,data);
    msgRefresher = setInterval(SyncMessages,5000,div);
}
function showChat(div,data)
{
    $(".Messenger").show();
    $(".Messenger #LoadingAnimation").show();

    id = div.getAttribute("number");
    chatBody = document.getElementsByClassName("chat-body")[0];

    $.ajax({
        type : 'GET',
        url: 'MessengerServlet',
        data: {number : id},
        success: function (result) {
            chatBody.innerHTML ="";
            chat = JSON.parse(result);
            for (i=0;i<chat.length;i++)
            {
                var msgDiv = document.createElement("div");
                if (chat[i].type=='s')
                {
                    msgDiv.className = "msg-div sent";
                }
                else {
                    msgDiv.className = "msg-div receive";
                }
                var msg = document.createElement("div");
                msg.className = "message-text";
                msg.innerHTML = chat[i].message;
                msgDiv.appendChild(msg);

                chatBody.appendChild(msgDiv);

            }
            $(".tag-name a").text(div.firstChild.innerHTML);
            path = "<%=request.getContextPath()%>/images/"+(data[id.toString()])[0]+"/"+(data[id.toString()])[2];
            console.log(path);
            $(".tag img").attr("src",path);
            $("#send").attr("number",id);

            // let messenger = document.getElementsByClassName("Messenger")[0];
            // messenger.style.display = "block";

            $(".Messenger #LoadingAnimation").hide();
            $(".Messenger-chatBox").fadeIn();

            console.log("Show chat method called");
        },
        error: function (err) {
            alert(err);
        }
    });
}


$(".chat-exit").click(function(){
    clearInterval(msgRefresher);

    $(".Messenger-chatBox").fadeOut();
    let messenger = document.getElementsByClassName("Messenger")[0];
    messenger.style.display = "none";

});



$("#send").click(function() {
    id = $("#send").attr("number");
    var message = $.trim($("#textarea").val());
    console.log(message);
    if (message!= "") {
        $("#textarea").val("");
        var msgDiv = document.createElement("div");
        msgDiv.className = "msg-div sent";
        var msg = document.createElement("div");
        msg.className = "message-text";
        msg.innerHTML = message;
        msgDiv.appendChild(msg);
        chatBody = document.getElementsByClassName("chat-body")[0];
        chatBody.appendChild(msgDiv);
        $.ajax({
            type : 'POST',
            url: 'MessengerServlet',
            data: {message:message,number:id},
            success: function (result) {
                alert("successfully deleted");
            },
            error: function (err) {
                alert(err);
            }
        });
    }
});

function SyncMessages(div)
{

    id = div.getAttribute("number");
    chatBody = document.getElementsByClassName("chat-body")[0];

    $.ajax({
        type: 'GET',
        url: 'MessengerServlet',
        data: {number: id},
        success: function (result) {
            chatBody.innerHTML = "";
            chat = JSON.parse(result);
            for (i = 0; i < chat.length; i++) {
                var msgDiv = document.createElement("div");
                if (chat[i].type == 's') {
                    msgDiv.className = "msg-div sent";
                } else {
                    msgDiv.className = "msg-div receive";
                }
                var msg = document.createElement("div");
                msg.className = "message-text";
                msg.innerHTML = chat[i].message;
                msgDiv.appendChild(msg);

                chatBody.appendChild(msgDiv);
            }
        }
    });
}