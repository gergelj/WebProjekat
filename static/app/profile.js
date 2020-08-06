$(document).ready(function(){
    let forma = $("#update-form");
    
    forma.submit(function(){
        let userData = getFormData(forma);

        let validData = checkData(userData);

        event.preventDefault();
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

function checkData(data)
{
    let passwordOK = checkPassword(data.password, data.controlPassword, data.oldPassword);
    let nameOK = checkBasicData(data.name, $("#name-input"), "name");
    let surnameOK = checkBasicData(data.surname, $("#surname-input"), "surname");

    return passwordOK && nameOK && surnameOK;
}

function checkPassword(password, controlPassowrd, oldPassword)
{
    let passwordInput = $("#password-input");
    let controlPasswordInput = $("#control-password-input");
    let oldPasswordInput = $("#old-password-input");

    removeValidationClass(passwordInput);
    removeValidationClass(controlPasswordInput);
    removeValidationClass(oldPasswordInput);

    if(oldPassword.isEmpty())
    {
        if(password.isEmpty() && controlPassowrd.isEmpty())
        {
            addAllValidClasses(oldPasswordInput, passwordInput, controlPasswordInput);

            return true;
        }
        addAllInvalidClasses(oldPasswordInput, passwordInput, controlPasswordInput);
        addErrorMessage(oldPasswordInput, "Please enter both, old and new passowords.");
        
        return false;
    }
    else
    {
        if(oldPasswordCorrect(oldPasswordInput))
        {
            if(!checkPasswordLength(password))
            {
                addInvalidClass(passwordInput);
                addErrorMessage(passwordInput, "Your password must be 8-20 characters.");

                return false;
            }
            if(controlPassowrd.isEmpty())
            {
                addInvalidClass(controlPasswordInput);
                addErrorMessage(controlPasswordInput, "Please enter your password again.")
            
                return false;
            }
            else if(controlPassowrd == password && password == oldPassword)
            {
                addInvalidClass(oldPasswordInput);
                addErrorMessage(controlPasswordInput, "New password must be diffrent from the old one.");

                return false;
            }
            else if(controlPassowrd == password)
            {
                addAllValidClasses(oldPasswordInput, passwordInput, controlPasswordInput);

                return true;
            }
            else
            {
                addInvalidClass(passwordInput);
                addInvalidClass(controlPasswordInput);

                addErrorMessage(passwordInput, "Passwords need to match.");
                addErrorMessage(controlPasswordInput, "Passwords need to match.");

                return false;
            }
        }
        else
        {
            addInvalidClass(oldPasswordInput);

            addErrorMessage(oldPasswordInput, "Invalid old password.");

            return false;
        }
    }
}

function checkBasicData(name, element, dataName)
{
    removeValidationClass(element)

    if(name.isEmpty())
    {
        addInvalidClass(element);

        return false;
    }
    addValidClass(element);

    return true;
}

function addAllInvalidClasses(oldPasswordInput, passwordInput, controlPasswordInput)
{
    addInvalidClass(oldPasswordInput);
    addInvalidClass(passwordInput);
    addInvalidClass(controlPasswordInput);
}

function addAllValidClasses(oldPasswordInput, passwordInput, controlPasswordInput)
{
    addValidClass(oldPasswordInput);
    addValidClass(passwordInput);
    addValidClass(controlPasswordInput);
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

String.prototype.isEmpty = function()
{
    return (this.length === 0 || !this.trim());
}

function addErrorMessage(element, message)
{
    element.siblings(".invalid-feedback").html(message);
}

function oldPasswordCorrect(oldPassword)
{
    return true;
}

function checkPasswordLength(password)
{
    return password.length >= 8 && password.length<=20;
}

var profile = new Vue({
    el:'#container',
    data:
    {
        user: {account: {}}
    },
    mounted()
    {
        let jwt = window.localStorage.getItem('jwt');
        if (!jwt)
            jwt = '';

        axios
            .get('rest/vazduhbnb/profile', {
                headers:{
                    'Authorization': 'Bearer ' + jwt
                }
            })
            .then(response => (this.user = response.data))
    }
});