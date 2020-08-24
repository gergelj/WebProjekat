function testAuthorization1(type){
    let jwt = window.localStorage.getItem('jwt');
    if(!jwt)
        jwt='';

    axios
        .get('rest/vazduhbnb/userType',{
            headers:{
                'Authorization': 'Bearer ' + jwt
            }
        })
        .then(function(response){
            let userType = response.data;
            if(type != userType){
                window.location.replace("login.html");
            }
        });

}

function testAuthorization2(type1, type2){
    let jwt = window.localStorage.getItem('jwt');
    if(!jwt)
        jwt='';

    axios
        .get('rest/vazduhbnb/userType',{
            headers:{
                'Authorization': 'Bearer ' + jwt
            }
        })
        .then(function(response){
            let userType = response.data;

            if(type1 != userType && type2 != userType){
                window.location.replace("login.html");
            }
        });

}

function testAuthorization3(type1, type2, type3){
    let jwt = window.localStorage.getItem('jwt');
    if(!jwt)
        jwt='';

    axios
        .get('rest/vazduhbnb/userType',{
            headers:{
                'Authorization': 'Bearer ' + jwt
            }
        })
        .then(function(response){
            let userType = response.data;

            if(type1 != userType && type2 != userType && type3 != userType){
                window.location.replace("login.html");
            }
        });

}