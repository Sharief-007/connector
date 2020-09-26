
var Details,Notifs_JSON;

$(document).ready(function () {
    $.ajax({
        type : 'GET',
        url : 'UsersList?want=json',
        success : function (output) {
            Details = JSON.parse(output);
            console.log(Details);
        }
    });
})

function openNotifications() {
    $("#clear").hide();
    $("#msg-rqst-Notificaitons").hide();
    $("#all-Notifications").hide();
    $("#frnd-rqst-Notificaitons").show();

    $(".MyModal #LoadingAnimation").hide();
    $("#Notification-Modal").fadeIn();
    document.getElementById('frnd-rqst').className = 'activeTab';
    document.getElementById('msg-rqst').classList.remove('activeTab');
    document.getElementById('all-notifs').classList.remove('activeTab');
}

$("#frnd-rqst").click(function () {
    $("#all-Notifications").hide();
    $("#msg-rqst-Notificaitons").hide();
    $("#clear").hide();

    document.getElementById('frnd-rqst').className = 'activeTab';
    document.getElementById('msg-rqst').classList.remove('activeTab');
    document.getElementById('all-notifs').classList.remove('activeTab');

    $("#frnd-rqst-Notificaitons").show();
});

$("#msg-rqst").click(function () {
    $("#frnd-rqst-Notificaitons").hide();
    $("#all-Notifications").hide();
    $("#clear").hide();

    document.getElementById('msg-rqst').className = 'activeTab';
    document.getElementById('frnd-rqst').classList.remove('activeTab');
    document.getElementById('all-notifs').classList.remove('activeTab');

    $("#msg-rqst-Notificaitons").show();
});

$("#all-notifs").click(function () {
    $("#frnd-rqst-Notificaitons").hide();
    $("#msg-rqst-Notificaitons").hide();
    $("#clear").show();

    document.getElementById('all-notifs').className = 'activeTab';
    document.getElementById('frnd-rqst').classList.remove('activeTab');
    document.getElementById('msg-rqst').classList.remove('activeTab');


    $("#all-Notifications").show();
});

function closeNotificationModal() {
    $("#Notification-Modal").fadeOut();
    $(".Notification-body").html(" ");

    $(".MyModal").fadeOut();
}
$(".Notification-header>span").click(closeNotificationModal);


function getNotifications() {
    $(".MyModal").show();
    $(".MyModal #LoadingAnimation").show();


    $.ajax({
        type : 'GET',
        url : 'NotificationParser',
        success : function (result) {
            Notifs_JSON = JSON.parse(result);
            console.log(Notifs_JSON);


            setNotifications(Notifs_JSON);
            openNotifications();
        }
    });
}

function setNotifications(jsonData)
{
    for (i=0;i<jsonData.length;i++)
    {
        if (jsonData[i].seen == false)
        {
            if (jsonData[i].TYPE == 'friend-request' || jsonData[i].TYPE == 'request-accept')
            {
                notif = new Notification(jsonData[i].TYPE,jsonData[i].from_id, Details);
                notif = notif.getNotification();
                document.getElementById('frnd-rqst-Notificaitons').appendChild(notif);
            }
            else {
                notif = new Notification(jsonData[i].TYPE,jsonData[i].from_id, Details);
                notif = notif.getNotification();
                document.getElementById('msg-rqst-Notificaitons').appendChild(notif);
            }
        }
        notif = new Notification(jsonData[i].TYPE,jsonData[i].from_id, Details);
        notif = notif.getNotification();
        document.getElementById('all-Notifications').appendChild(notif);
    }
}


function deleteNotification(btn) {
    $.ajax({
        type : 'POST',
        url: 'NotificationParser',
        data: {from_id: btn.getAttribute("number"),TYPE: btn.getAttribute('type'), action: "delete"},
        success: function (result) {
            alert(result);
            btn.parentNode.parentNode.parentNode.remove();
        },
        error: function (err) {
            alert(err);
        }
    });
}

function acceptRequest(btn) {
    $.ajax({
        type : 'POST',
        url: 'NotificationParser',
        data: {from_id: btn.getAttribute("number"),TYPE: btn.getAttribute('type'), action: "accept"},
        success: function (result) {
            alert(result);
            btn.parentNode.parentNode.parentNode.remove();
        },
        error: function (err) {
            alert(err);
        }
    });
}

function markNotificationsAsViewed()
{
    var rqsts =[], acpts =[], msgs =[];
    for (i=0;i<Notifs_JSON.length;i++)
    {
        if (Notifs_JSON[i].seen == false)
        {
            switch (Notifs_JSON[i].TYPE) {
                case "friend-request": {
                    rqsts.push(Notifs_JSON[i].from_id);
                    break;
                }
                case "request-accept" : {
                    acpts.push(Notifs_JSON[i].from_id);
                    break;
                }
                case "message": {
                    msgs.push(Notifs_JSON[i].from_id);
                    break
                }
            }
        }
    }

    closeNotificationModal();

    var myJSON ={};
    myJSON["friend-request"] = rqsts;
    myJSON["request-accept"] = acpts;
    myJSON["message"] = msgs;

    console.log(myJSON);
    $.ajax({
        type : 'POST',
        url : 'NotificationParser',
        data: {jsonData:JSON.stringify(myJSON),action: "marker"},
        success: function (result) {
            alert(result);
        },
        error: function (err) {
            alert(err);
        }
    });
}























class Notification {
    constructor(type, userID, data) {
        this.type = type;
        this.userID = userID;
        this.data = data;
    }
    Build()
    {
        this.notification = document.createElement('div');
        this.notification.className = 'Notification';

        this.divForImage = document.createElement('div');
        this.divForImage.className = 'notif-image';

        this.prfImage = document.createElement('img');
        this.path = "<%=request.getContextPath()%>/images/"+(this.data[this.userID.toString()])[0]+"/"+(this.data[this.userID.toString()])[2];
        this.prfImage.setAttribute('src',this.path);
        this.prfImage.setAttribute('alt','profile picture');
        this.prfImage.style.width = '60px';
        this.prfImage.style.height = '60px';

        this.divForImage.appendChild(this.prfImage);
        this.notification.appendChild(this.divForImage);

        this.matter = document.createElement('div');
        this.matter.className = 'notif-matter';

        switch (this.type) {
            case 'friend-request' : {
                    this.p = document.createElement('p');

                    this.a = document.createElement('a');
                    this.a.innerHTML = (this.data[this.userID.toString()])[0];
                    console.log((this.data[this.userID.toString()])[0]);
                    this.a.setAttribute('href','#');

                    this.p.appendChild(this.a);
                    this.p.innerHTML += '  has sent you a friend request';
                    console.log(this.p.toString());

                    this.matter.appendChild(this.p);

                    this.buttons = document.createElement('div');
                    this.buttons.className = 'notif-btns';

                    this.acpt = document.createElement('button');
                    this.acpt.className = 'accept-btn';
                    this.acpt.innerHTML = '<i class="fas fa-user-check"></i>accept';
                    this.acpt.setAttribute('number',this.userID);
                    this.acpt.setAttribute('type',this.type);
                    this.acpt.setAttribute('onclick','acceptRequest(this)');


                    this.dlt = document.createElement('button');
                    this.dlt.className = 'delete-btn';
                    this.dlt.innerHTML = '<i class="fas fa-user-times"></i>delete';
                    this.dlt.setAttribute('number',this.userID);
                    this.dlt.setAttribute('type',this.type);
                    this.dlt.setAttribute('onclick','deleteNotification(this)');

                    this.buttons.appendChild(this.acpt);
                    this.buttons.appendChild(this.dlt);


                    this.matter.appendChild(this.buttons);

                    break;
            }
            case 'request-accept' : {
                this.p = document.createElement('p');

                this.a = document.createElement('a');
                this.a.innerHTML = (this.data[this.userID.toString()])[0];
                console.log((this.data[this.userID.toString()])[0]);
                this.a.setAttribute('href','#');

                this.p.appendChild(this.a);
                this.p.innerHTML += '  has approved your friend request';
                console.log(this.p.toString());

                this.matter.appendChild(this.p);

                break;
            }
            case 'message' : {
                this.p = document.createElement('p');

                this.a = document.createElement('a');
                this.a.innerHTML = (this.data[this.userID.toString()])[0];
                console.log((this.data[this.userID.toString()])[0]);
                this.a.setAttribute('href','#');

                this.p.appendChild(this.a);
                this.p.innerHTML += '  has sent you a message';
                console.log(this.p.toString());

                this.matter.appendChild(this.p);

                this.buttons = document.createElement('div');
                this.buttons.className = 'notif-btns';

                this.chatBTN = document.createElement('button');
                this.chatBTN.className = 'chat-btn';
                this.chatBTN.innerHTML = '<i class="far fa-comments"></i>open chat';

                this.buttons.appendChild(this.chatBTN);
                this.matter.appendChild(this.buttons);

                break;
            }
        }

        this.notification.appendChild(this.matter);
    }

    getNotification(){
        this.Build();
        return this.notification;
    }
}

