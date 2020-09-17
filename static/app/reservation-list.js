var app = new Vue({
    el:"#app",
    data:{
        userType: 'undefined',
        reservations:[],

        username: '',
        status: null,
        statusEnum:[
            {value: null, text:"All reservations"},
            {value: 'created', text:"Created"},
            {value: 'rejected', text:"Rejected"},
            {value: "cancelled", text:"Cancelled"},
            {value: 'accepted', text:"Accepted"},
            {value: 'finished', text:"Finished"}
        ],
        apartment: null,
        apartments: [
            {value: null, text:'All apartments'}
        ],
        sorting: null,
        sortOptions:[
            {value:'ascending', text:'Ascending'},
            {value:'descending', text:'Descending'}
        ]
    },
    mounted(){
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
                vm.createApartmentList();
            })
            .catch(function(error){
                let response = error.response;
                switch(response.status){
                    case 401: alert(unauthorizedErrorMessage); signOut(); break;
                    case 403: alert(forbiddenErrorMessage); signOut(); break;
                    case 500: pushInternalServerError(); break;
                }
            })
    },
    methods:{
        fixDate(reservations){
            for(let reservation of reservations){
                reservation.checkIn = new Date(parseInt(reservation.checkIn));
            }
            return reservations;
        },
        createApartmentList(){
            let apartmentMap = {};

            for(let apartment of this.reservations.map(r => r.apartment)){
                apartmentMap[apartment.id] = apartment.name;
            }

            for(let id in apartmentMap){
                this.apartments.push({value: id, text: apartmentMap[id]});
            }
        }
    },
    watch:{
        sorting(sortMode){
            if(sortMode == 'ascending'){
                this.reservations.sort(function(a, b){
                    if(a.totalPrice < b.totalPrice) return -1;
                    if(a.totalPrice > b.totalPrice) return 1;
                    return 0;
                });
            }
            else{ //descending
                this.reservations.sort(function(a, b){
                    if(a.totalPrice > b.totalPrice) return -1;
                    if(a.totalPrice < b.totalPrice) return 1;
                    return 0;
                });
            }
        }
    },
    computed:{
        filteredReservations(){
            let hasUsername = this.username.length > 0;
            let hasStatus = this.status != null;
            let hasApartment = this.apartment != null;

            return this.reservations.filter(res => (hasUsername ? (res.guest.account.username.toLowerCase().indexOf(this.username.toLowerCase()) != -1) : true) && (hasStatus ? (res.reservationStatus == this.status) : true) && (hasApartment ? (res.apartment.id == this.apartment) : true));
        }

    }
})