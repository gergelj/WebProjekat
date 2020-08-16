var app = new Vue({
    el: "#app",
    data:{
        filter: {},
        apartments: [],
        selectedApartment:{},
        userType: 'admin'
    },
    mounted(){
        this.$root.$on("apartment-selected", (selectedApartment) => {this.onApartmentSelected(selectedApartment);});
    },
    methods:{
        onApartmentSelected(selectedApartment){
            this.$bvModal.show('bv-modal-example');
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

        },
        selectedApartment:function(){
        }
    }
});