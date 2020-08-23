String.prototype.isEmpty = function()
{
    return(this.length === 0 || !this.trim());
};

var app = new Vue({
    el: "#app",
    data:{
        username: '',
        usernameErrorMessage: '',
        usernameValid:false,

        password: '',
        passwordErrorMessage:'',
        passwordValid: false
    },
    methods:{
        submit(){
            if(this.isDataValid){
                axios
                    .post("rest/vazduhbnb/login", {username: this.username, password: this.password})
                    .then(function(response){
                        window.localStorage.setItem('jwt', response.data.token);
                        window.localStorage.setItem('username', response.data.username);
                        window.localStorage.setItem('usertype', response.data.userType);
                        window.location.href = "index.html";
                    })
                    .catch(function(error){
                        let response = error.response;
                        switch(response.status){
                            case 400: pushErrorNotification("Error", "Invalid username/password"); break;
                            case 403: pushErrorNotification("Access denied", "You account has been blocked"); break;
                            case 500: pushErrorNotification("Internal Server Error", "Please try again later."); break;
                        }
                    })
            }
        }
    },
    computed:{
        usernameValidation(){
            if(this.username.isEmpty()){
                this.usernameErrorMessage = 'Please enter your username';
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
                this.passwordErrorMessage = "Please enter your password";
                this.passwordValid = false;
                return false;
            }
            else{
                this.passwordValid = true;
                return null;
            }
        },
        isDataValid(){
            return this.usernameValid && this.passwordValid;
        }
    }
});
