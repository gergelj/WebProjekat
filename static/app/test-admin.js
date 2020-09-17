var userType = "";
let jwt = window.localStorage.getItem('jwt');
        if(!jwt)
            jwt='';

axios
        .get('rest/vazduhbnb/userType',{
            headers:{
                'Authorization': 'Bearer ' + jwt
            }
        })
        .then(response =>{
            userType = response.data;
            if(userType != 'admin'){
                window.location.replace('login.html');
            }
        });