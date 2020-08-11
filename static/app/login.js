$(document).ready(function()
{
    let forma = $("#login-form");

    forma.submit(function(){
        let userData = getFormData(forma);

        event.preventDefault();

        var s = JSON.stringify(userData);
        if(validateData(userData))
        {
            $.ajax({
                url: "rest/vazduhbnb/login",
                type:"POST",
                data: s,
                contentType:"application/json",
                dataType:"json",
                complete: function(data) {
                    let responseData = JSON.parse(data.responseText);
                    switch(data.status){
                        case 200: {
                            window.localStorage.setItem('jwt', responseData.token);
                            window.localStorage.setItem('username', responseData.username);
                            window.localStorage.setItem('usertype', responseData.userType);
                            window.location.href = "index.html";
                        } break;
                        case 400: alert(responseData.message); break;
                        case 500: alert("Server error. Try again later."); break;
                    }
                    //console.log(data);
                }
            });
        }
    });
});

function getFormData(forma)
{
    var unindexed_array = forma.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}


function validateData(data)
{
    let forma = $("#login-form");
    
    let isUsernameOk = checkUsername(data.username);
    let isPasswordOk = checkPassword(data.password);

    if(isUsernameOk && isPasswordOk)
    {
        return true;
    }
    else
    {
        return false;
    }
}


function checkUser(username, password)
{
    var s = JSON.stringify()

    $.ajax({
        url: "rest/vazduhbnb/login",
        type: "GET",
        data: s,
    })
}

function checkUsername(username)
{
    let usernameInput = $("#username-input");
    removeValidationClass(usernameInput);

    if(username.isEmpty())
    {
        addInvalidClass(usernameInput);
        addErrorMessage(usernameInput, "Please enter your username.");
        return false;
    }
    else
    {
        return true;
    }
}

function checkPassword(password)
{
    let passwordInput = $("#password-input");
    removeValidationClass(passwordInput);

    if(password.isEmpty())
    {
        addInvalidClass(passwordInput);
        addErrorMessage(passwordInput, "Please enter your password.");
        return false;
    }
    else
    {   
        return true;
    }
}

function removeValidationClass(element)
{
    element.removeClass("is-valid");
    element.removeClass("is-invalid");
}

function addValidClass(element)
{
    element.addClass("is-valid");
}

function addInvalidClass(element)
{
    element.addClass("is-invalid");
}

function addErrorMessage(element, message)
{
    element.siblings(".invalid-feedback").html(message);
}

String.prototype.isEmpty = function()
{
    return(this.length === 0 || !this.trim());
};

