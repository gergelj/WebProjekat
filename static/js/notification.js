$(document).ready(function(){
    let config = 
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

function pushInternalServerError(){
    pushErrorNotification("Internal Server Error", "Please try again later");
}

var unauthorizedErrorMessage = "Your login session has expired. Please login.";
var forbiddenErrorMessage = "Please login with privileges";