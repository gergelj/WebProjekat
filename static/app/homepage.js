var app = new Vue({
    el: "#app",
    data:{
        filter: {},
        apartments: [],
        selectedApartment:{},
        backup: [],
        userType: 'undefined',
        selectedType: null,
        apartmentTypes:[
            {value: null, text: "All Apartments"},
            {value: "active", text: "Active Apartments"},
            {value: "inactive", text: "Inactive Apartments"}
        ],


        apartmentModalShow: false,
        editApartmentModalShow: false
    },
    mounted(){
        this.$root.$on("apartment-selected-event", (selectedApartment) => {this.onApartmentSelected(selectedApartment);});
        this.$root.$on("apartment-updated-event", () => {this.onApartmentUpdated();});

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
        onApartmentUpdated(){
            this.editApartmentModalShow = false;
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

            });
        },
        onEditSelectedApartment(){
            this.backup = [this.selectedApartment.numberOfRooms, // 0
                            this.selectedApartment.numberOfGuests, //1,
                            this.selectedApartment.pricePerNight, //2
                            this.selectedApartment.checkInHour, //3
                            this.selectedApartment.checkOutHour, //4
                            this.selectedApartment.apartmentType, //5
                            this.selectedApartment.location.latitude, //6
                            this.selectedApartment.location.longitude, //7
                            this.selectedApartment.location.address.street, //8
                            this.selectedApartment.location.address.houseNumber, //9
                            this.selectedApartment.location.address.city, //10
                            this.selectedApartment.location.address.postalCode, //11
                            [...this.selectedApartment.amenities] //12
                        ];

            this.editApartmentModalShow = true;
        },
        onCancelEdit(){
            this.editApartmentModalShow = false;
            
            this.selectedApartment.numberOfRooms = this.backup[0];
            this.selectedApartment.numberOfGuests = this.backup[1];
            this.selectedApartment.pricePerNight = this.backup[2];
            this.selectedApartment.checkInHour = this.backup[3];
            this.selectedApartment.checkOutHour = this.backup[4];
            this.selectedApartment.apartmentType = this.backup[5];
            this.selectedApartment.location.latitude = this.backup[6];
            this.selectedApartment.location.longitude = this.backup[7];
            this.selectedApartment.location.address.street = this.backup[8];
            this.selectedApartment.location.address.houseNumber = this.backup[9];
            this.selectedApartment.location.address.city = this.backup[10];
            this.selectedApartment.location.address.postalCode = this.backup[11];
            this.selectedApartment.amenities = this.backup[12];
        }
    },
    watch:{
        filter:function(){
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