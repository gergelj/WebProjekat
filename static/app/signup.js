String.prototype.isEmpty = function() {
    return (this.length === 0 || !this.trim());
};

var signupApp = new Vue({
    el: "#app",
    data: {
        username : "",
        usernameErrorMessage: "",
        usernameValid : false,
        usernameUnique : true,

        password: "",
        passwordErrorMessage : "",
        passwordValid: false,

        controlPassword: "",
        controlPasswordErrorMessage: "",
        controlPasswordValid: false,

        name: "",
        nameErrorMessage: "",
        nameValid: false,

        surname: "",
        surnameErrorMessage: "",
        surnameValid: false,

        gender: "male",
        genderOptions: [
            {value: "male", text: "Male"},
            {value: "female", text: "Female"},
            {value: "other", text: "Other"}
        ]
        
    },
    methods:{
        submit(){
            if(this.isDataValid){
                let data = {
                    username: this.username,
                    password: this.password,
                    controlPassword: this.controlPassword,
                    name: this.name,
                    surname: this.surname,
                    gender: this.gender
                }
                axios.post("rest/vazduhbnb/register", data)
                    .then(function(response){
                        console.log(response);
                        let resData = response.data;
                        window.localStorage.setItem('jwt', resData.token);
                        window.localStorage.setItem('username', resData.username);
                        window.localStorage.setItem('usertype', resData.userType);
                        window.location.replace("index.html");
                        }
                    )
                    .catch(function(error){
                        let response = error.response;
                        switch(response.status){
                            case 400: pushErrorNotification("An error occured", response.data.message); break; //invalid data
                            case 409: pushErrorNotification("This username is taken", "Try something else."); break; // username not unique
                            case 500: pushErrorNotification("An error occured", "Please try again later."); break; // server-side error
                        }
                    });
            }
        },
        checkPasswordLength: function(){
            return this.password.length >= 8 && this.password.length <= 20;
        }
    },
    mounted() {

    },
    computed:{
        isDataValid: function(){
            return this.usernameValid && this.passwordValid && this.controlPasswordValid && this.nameValid && this.surnameValid;
        },
        usernameValidation(){
            if(this.username.isEmpty()){
                this.usernameErrorMessage = "Please enter your username.";
                this.usernameValid = false;
                return false;
            }
            else{
                this.usernameValid = true;
                return null;
            }
        },
        passwordValidation(){
            if(this.password.isEmpty()){
                this.passwordErrorMessage = "Please enter your password.";
                this.passwordValid = false;
                return false;
            }
            else if(this.checkPasswordLength()){
                this.passwordValid = true;
                return true;
            }
            else{
                this.passwordErrorMessage = "Your password must be 8-20 characters long.";
                this.passwordValid = false;
                return false;
            }
        },
        controlPasswordValidation(){
            if(this.passwordValid){
                if(this.password == this.controlPassword){
                    this.controlPasswordValid = true;
                    return true;
                }
                else{
                    this.controlPasswordErrorMessage = "Passwords need to match.";
                    this.controlPasswordValid = false;
                    return false;
                }
            }
            else{
                this.controlPasswordValid = false;
                return null;
            }
        },
        nameValidation(){
            if(this.name.isEmpty()){
                this.nameErrorMessage = "Please enter your first name.";
                this.nameValid = false;
                return false;
            }
            else{
                this.nameValid = true;
                return true;
            }
        },
        surnameValidation(){
            if(this.surname.isEmpty()){
                this.surnameErrorMessage = "Please enter your last name.";
                this.surnameValid = false;
                return false;
            }
            else{
                this.surnameValid = true;
                return true;
            }
        }
    }
});


/*
$(document).ready(function(){
    let forma = $("#registration-form");
    
    forma.submit(function(){
        let userData = getFormData(forma);

        let validData = checkData(userData);

        //console.log(userData);
        event.preventDefault();

        var s = JSON.stringify(userData);
        if(validData){
            $.ajax({
                url: "rest/vazduhbnb/register",
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
                        case 400: pushErrorNotification("An error occured", responseData.message); break; //invalid data
                        case 409: notUniqueUsernameError(); break; // username not unique
                        case 500: pushErrorNotification("An error occured", "Please try again later."); break; // server-side error
                    }
                    //console.log(data);
                }
            });
        }

    });
});

function notUniqueUsernameError(){
    let usernameInput = $("#username-input");
    removeValidationClass(usernameInput);

    addInvalidClass(usernameInput);
    addErrorMessage(usernameInput, "This username is taken. Try something else.");
}

function getFormData(forma){
    var unindexed_array = forma.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}

function checkData(data){
    let usernameOK = checkUsername(data.username);
    let passwordOK = checkPassword(data.password, data.controlPassword);
    let nameOK = checkBasicData(data.name, $("#name-input"), "name");
    let surnameOK = checkBasicData(data.surname, $("#surname-input"), "surname");

    return usernameOK && passwordOK && nameOK && surnameOK;
}

function checkUsername(username){
    let usernameInput = $("#username-input");
    removeValidationClass(usernameInput);

    if(username.isEmpty()){
        addInvalidClass(usernameInput);
        addErrorMessage(usernameInput, "Please enter your username.");
        return false;
    }
    else{
        return true;
    }
}

function checkPassword(password, controlPassword){
    let passwordInput = $("#password-input");
    removeValidationClass(passwordInput);
    let controlPasswordInput = $("#control-password-input");
    removeValidationClass(controlPasswordInput);

    if(password.isEmpty()){
        addInvalidClass(passwordInput);
        addErrorMessage(passwordInput, "Please enter your password.");
        return false;
    }
    else{
        if(!checkPasswordLength(password)){
            addInvalidClass(passwordInput);
            addErrorMessage(passwordInput, "Your password must be 8-20 characters long.");
            return false;
        }

        if(controlPassword.isEmpty()){
            addInvalidClass(controlPasswordInput);
            addErrorMessage(controlPasswordInput, "Please enter your password again.");
            return false;
        }
        else if(controlPassword == password){
            addValidClass(passwordInput);
            addValidClass(controlPasswordInput);
            return true;
        }
        else{
            addInvalidClass(passwordInput);
            addInvalidClass(controlPasswordInput);
            addErrorMessage(passwordInput, "Passwords need to match.");
            addErrorMessage(controlPasswordInput, "Passwords need to match.");
            return false;
        }
    }
}

function checkBasicData(data, element, dataName){
    removeValidationClass(element);

    if(data.isEmpty()){
        addInvalidClass(element);
        return false;
    }
    else{
        addValidClass(element);
        return true;
    }
}

function checkPasswordLength(password){
    return password.length >= 8 && password.length <= 20;
}

function addErrorMessage(element, message){
    element.siblings(".invalid-feedback").html(message);
}

function removeValidationClass(element){
    element.removeClass("is-valid");
    element.removeClass("is-invalid");
}

function addValidClass(element){
    element.addClass("is-valid");
}

function addInvalidClass(element){
    element.addClass("is-invalid");
}
*/