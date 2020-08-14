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
        
    });

function testAuthorization(type){
    if(type != userType){
        window.location.replace("login.html");
    }
}

function testAuthorization(type1, type2){
    if(type1 != userType || type2 != userType){
        window.location.replace("login.html");
    }
}

function testAuthorization(type1, type2, type3){
    if(type1 != userType || type2 != userType || type3 != userType){
        window.location.replace("login.html");
    }
}