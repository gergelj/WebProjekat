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
                            case 400: pushErrorNotification("Error", response.data.message); break; //invalid data
                            case 409: pushErrorNotification("Error", "This username is taken. Try something else."); break; // username not unique
                            case 500: pushInternalServerError(); break; // server-side error
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