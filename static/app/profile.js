String.prototype.isEmpty = function() {
    return (this.length === 0 || !this.trim());
}

var profile = new Vue({
    el:'#app',
    data: {
        user: {account: {}, name:'', surname:''},
        gender: null,
        genderOptions: [
           { value: 'male', text: 'Male' },
           { value: 'female', text: 'Female' },
           { value: 'other', text: 'Other' }
        ],

        oldPassword: '',
        password: '',
        controlPassword: '',

        nameValid: false,
        surnameValid: false,
        passwordValid: false,
        controlPasswordValid: false,

        nameErrorMessage: '',
        surnameErrorMessage: '',
        passwordErrorMessage:'',
        controlPasswordErrorMessage:''
    },
    mounted() {
        let jwt = window.localStorage.getItem('jwt');
        if (!jwt)
            jwt = '';


        const vm = this;

        axios
            .get('rest/vazduhbnb/profile', {
                headers:{
                    'Authorization': 'Bearer ' + jwt
                }
            })
            .then(response => {
                vm.user = response.data;
                vm.gender = this.user.gender;
            })
            .catch(function(error){
                console.log(error.response)
                    switch(error.response.status) {
                        case 400:
                            pushErrorNotification("Bad request",'Bad request sent');
                            break;    
                        case 500:
                            pushErrorNotification('Internal Server Error','Please try again later.');
                            break;
                    }
            })
    },
    methods: {
        update: function() {
            if(this.isDataValid){

                let userDto={
                    username: this.user.account.username,
                    name: this.user.name,
                    surname: this.user.surname,
                    gender: this.gender,
                    oldPassword: this.oldPassword,
                    password: this.password,
                    controlPassword: this.controlPassword
                }

                const vm = this;
    
                axios
                    .put("rest/vazduhbnb/updateUser", userDto)
                    .then(response => {
                        pushSuccessNotification('Success!','User updated successfully!');
                        vm.oldPassword = '';
                        vm.password = '';
                        vm.controlPassword = '';
                    })
                    .catch(function(error){
                        console.log(error.response)
                        switch(error.response.status)
                        {
                            case 400:
                                pushErrorNotification("An error occured",error.response.data.message);
                                break;    
                            case 409:
                                pushErrorNotification("Invalid Password",'You entered wrong old password!');
                                break;    
                            case 500:
                                pushErrorNotification('Internal server error','Please try again later.');
                                break;
                        }
                    });
            }
        },
        checkPasswordLength: function(){
            return this.password.length >= 8 && this.password.length <= 20;
        }
    },
    computed:{
        nameValidation(){
            if(this.user.name.isEmpty()){
                this.nameValid = false;
                this.nameErrorMessage = 'Please enter your first name';
                return false;
            }
            else{
                this.nameValid = true;
                return true;
            }
        },
        surnameValidation(){
            if(this.user.surname.isEmpty()){
                this.surnameValid = false;
                this.surnameErrorMessage = 'Please enter your last name';
                return false;
            }
            else{
                this.surnameValid = true;
                return true;
            }
        },
        passwordValidation(){
            if(this.oldPassword.isEmpty()){
                this.passwordValid = true;
                return null;
            }
            else{
                if(this.password.isEmpty()){
                    this.passwordValid = false;
                    this.passwordErrorMessage = 'Please enter a new password';
                    return false;
                }
                else if(this.checkPasswordLength()){
                    this.passwordValid = true;
                    return true;
                }
                else{
                    this.passwordValid = false;
                    this.passwordErrorMessage = 'Password must contain 8-20 characters';
                    return false;
                }
            }
        },
        controlPasswordValidation(){
            if(this.oldPassword.isEmpty()){
                this.controlPasswordValid = true;
                return null;
            }
            else{
                if(this.controlPassword.isEmpty()){
                    this.controlPasswordValid = false;
                    this.controlPasswordErrorMessage = 'Please confirm your new password';
                    return false;
                }
                else if(this.password == this.controlPassword){
                    this.controlPasswordValid = true;
                    return true;
                }
                else{
                    this.controlPasswordValid = false;
                    this.controlPasswordErrorMessage = 'Passwords must match';
                    return false;
                }
            }
        },
        isDataValid(){
            return this.nameValid && this.surnameValid && this.passwordValid && this.controlPasswordValid;
        },
        oldPasswordEmpty(){
            return this.oldPassword == 0;
        }
    }
});