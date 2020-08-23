var app = new Vue({
    el:"#app",
    data:{
        userType: 'undefined',
        reservations:[]
    },
    mounted(){
        //this.$root.$on("send-comment", (comment) => {this.onCommentSend(comment);});
        let jwt = window.localStorage.getItem('jwt');
        if(!jwt)
            jwt = '';

        const vm = this;

        axios
            .get('rest/vazduhbnb/userType',{
                headers:{
                    "Authorization" : "Bearer " + jwt
                }
            })
            .then(function(response){
                vm.userType = response.data;
            });

        axios
            .get('rest/vazduhbnb/reservations',{
                headers:{
                    "Authorization" : "Bearer " + jwt
                }
            })
            .then(function(response){
                vm.reservations = vm.fixDate(response.data);
            })
            .catch(function(error){
                let response = error.response;
                switch(response.status){
                    case 401:
                    case 403: alert("User not logged in"); signOut(); break;
                    case 500: pushErrorNotification("Internal Server Error", "Please try again later"); break;
                }
            })
    },
    methods:{
        fixDate(reservations){
            for(let reservation of reservations){
                reservation.checkIn = new Date(parseInt(reservation.checkIn));
            }
            return reservations;
        }
    }
})