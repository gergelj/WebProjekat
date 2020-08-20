var app = new Vue({
    el: '#app',
    data:
    {
        apartments: [],
        selectedApartment: {},
        showModal: false
    },
    mounted()
    {
        this.$root.$on('apartment-selected', (selectedApartment)=>
        {
            this.selectedApartment = selectedApartment;
            this.showModal = true;
        });

        const vm = this;

        let jwt = window.localStorage.getItem('jwt');
        if(!jwt)
            jwt='';

        axios
            .get('rest/vazduhbnb/apartmentsByUsertype',{
                headers:{
                    'Authorization': 'Bearer ' + jwt
                }
            })
            .then(function(response){
                vm.apartments = response.data;
            })
            .catch(function(error){
                switch(error.response.status)
                {
                    case 403:
                        window.location.replace('index.html');
                        break;
                }
            })
    },
    computed:{
        apartmentsComputed: function(){
            return this.apartments;
        }
    }
});