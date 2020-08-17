var app = new Vue({
    el: "#app",
    data:{
        filter: {},
        apartments: [],
        selectedApartment:{},
        userType: 'undefined',
        selectedType: null,
        apartmentTypes:[
            {value: null, text: "All Apartments"},
            {value: "active", text: "Active Apartments"},
            {value: "inactive", text: "Inactive Apartments"}
        ],


        apartmentModalShow: false
    },
    mounted(){
        this.$root.$on("apartment-selected-event", (selectedApartment) => {this.onApartmentSelected(selectedApartment);});
        this.$root.$on('disapprove-comment-event', (comment) => {this.onApproveComment(comment, false);});
        this.$root.$on('approve-comment-event', (comment) => {this.onApproveComment(comment, true);});

        const vm = this;

        let jwt = window.localStorage.getItem('jwt');
        if(!jwt)
            jwt = '';

        axios
            .get("rest/vazduhbnb/userType",{
                headers:{
                    'Authorization': 'Bearer ' + jwt
                }
            })
            .then(function(response){
                vm.userType = response.data;
            });
    },
    methods:{
        onApartmentSelected(selectedApartment){
            this.selectedApartment = selectedApartment;
            this.apartmentModalShow = true;
        },
        sortAscending : function(){
            this.apartments.sort(function(a, b){
                if(a.pricePerNight < b.pricePerNight) return -1;
                if(a.pricePerNight > b.pricePerNight) return 1;
                return 0;
            });
        },
        sortDescending : function(){
            this.apartments.sort(function(a, b){
                if(a.pricePerNight > b.pricePerNight) return -1;
                if(a.pricePerNight < b.pricePerNight) return 1;
                return 0;
            });
        },
        onApproveComment(comment, toApprove){
            let jwt = window.localStorage.getItem('jwt');
            if(!jwt)
                jwt = '';

            axios
                .put("rest/vazduhbnb/approve", comment, {
                    params: {
                        approve: toApprove,
                        apartment: this.selectedApartment.id
                    },
                    headers:{
                        'Authorization': 'Bearer ' + jwt
                    }
                })
                .then(function(response){
                    pushSuccessNotification("Success", comment.user.name + " " + comment.user.surname + "'s comment is " + (toApprove ? "approved" : "disapproved") + ".");
                    comment.approved = toApprove;
                })
                .catch(function(error){
                    let response = error.response;
                    switch(response.status){
                        case 401: alert("Error. Not logged in."); signOut(); break;
                        case 403: alert("Access denied. Please login with privileges."); signOut(); break; 
                        case 500: pushErrorNotification("Internal Server Error", "Please try again later."); break;
                    }
                });
        },
        activateApartment(apartment, toActivate){
            let jwt = window.localStorage.getItem('jwt');
            if(!jwt)
                jwt = '';

            axios
                .put("rest/vazduhbnb/activate", apartment,{
                    params: {
                        activate: toActivate
                    },
                    headers:{
                        'Authorization': 'Bearer ' + jwt
                    }
                })
                .then(function(response){
                    pushSuccessNotification('Success', "Apartment " + (toActivate ? 'activated' : 'deactivated') + '.');
                    apartment.active = toActivate;
                })
                .catch(function(error){
                    let response = error.response;
                    switch(response.status){
                        case 401: alert("Error. Not logged in."); signOut(); break;
                        case 403: alert("Access denied. Please login with privileges."); signOut(); break; 
                        case 500: pushErrorNotification("Internal Server Error", "Please try again later."); break;
                    }
                });
        },
        deleteApartment(apartment){
            const vm = this;

            this.$bvModal.msgBoxConfirm('Do you want to delete this apartment?', {
                title: 'Delete',
                size: 'sm',
                buttonSize: 'sm',
                okVariant: 'danger',
                okTitle: 'YES',
                cancelTitle: 'NO',
                footerClass: 'p-2',
                hideHeaderClose: false,
                centered: true
            })
            .then(value => {
                let toDelete = value ? true : false;
                
                if(toDelete){
                    let jwt = window.localStorage.getItem('jwt');
                    if(!jwt)
                        jwt = '';
    
                    axios
                        .delete("rest/vazduhbnb/apartment", {
                            headers:{
                                'Authorization': 'Bearer ' + jwt
                            },
                            data: apartment
                        })
                        .then(function(response){
                            let index = vm.apartments.findIndex(a => a.id == apartment.id);
                            vm.apartments.splice(index, 1);
                            pushSuccessNotification("Success", "Apartment deleted");
                            vm.apartmentModalShow = false;
                        })
                        .catch(function(error){
                            let response = error.response;
                            switch(response.status){
                                case 400: pushErrorNotification("Error occured.", response.data.message);
                                case 401: alert("Error. Not logged in."); signOut(); break;
                                case 403: alert("Access denied. Please login with privileges."); signOut(); break; 
                                case 500: pushErrorNotification("Internal Server Error", "Please try again later."); break;
                            }
                        });
                }

            })

            return;

            if(toDelete){
                let jwt = window.localStorage.getItem('jwt');
                if(!jwt)
                    jwt = '';
    
                const vm = this;
    
                axios
                    .delete("rest/vazduhbnb/apartment", {
                        headers:{
                            'Authorization': 'Bearer ' + jwt
                        },
                        data: apartment
                    })
                    .then(function(response){
                        let index = vm.apartments.findIndex(a => a.id == apartment.id);
                        vm.apartments.splice(index, 1);
                        pushSuccessNotification("Success", "Apartment deleted");
                    })
                    .catch(function(error){
                        let response = error.response;
                        switch(response.status){
                            case 400: pushErrorNotification("Error occured.", response.data.message);
                            case 401: alert("Error. Not logged in."); signOut(); break;
                            case 403: alert("Access denied. Please login with privileges."); signOut(); break; 
                            case 500: pushErrorNotification("Internal Server Error", "Please try again later."); break;
                        }
                    });
            }
        }
    },
    watch:{
        filter:function(){
            //TODO: search
            if(this.filter.dateRange){
                this.filter.dateRange.start = this.filter.dateRange.start.getTime();
                this.filter.dateRange.end = this.filter.dateRange.end.getTime();
            }
            
            let jwt = window.localStorage.getItem('jwt');
            if (!jwt)
                jwt = '';

            const vm = this;

            axios
                .get("rest/vazduhbnb/apartments",{
                    params:{
                        filter : this.filter
                    },
                    headers:{
                        'Authorization': 'Bearer ' + jwt
                    }
                })
                .then(function(response){
                    vm.apartments = response.data;
                })
                .catch(function(error){
                    let response = error.response;
                    alert(response.data.message);
                });

        }
    },
    computed:{
        filteredApartments: function(){
            if(this.selectedType == null) return this.apartments;
            if(this.selectedType == 'active') return this.apartments.filter(a => a.active);
            if(this.selectedType == 'inactive') return this.apartments.filter(a => !a.active);
        }
    }
});