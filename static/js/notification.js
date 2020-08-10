$(document).ready(function(){
    var config = 
    {
        positionX: "center",
        positionY: "top"
    };
    mkNotifications(config);
});

function pushSuccessNotification(title, message){
    mkNoti(title, message, {status : "success"});
}

function pushErrorNotification(title, message){
    mkNoti(title, message, {status : "danger"});
}

function pushNotification(title, message){
    mkNoti(title, message);
}

function pushPrimaryNotification(title, message){
    mkNoti(title, message, {status : "primary"});
}