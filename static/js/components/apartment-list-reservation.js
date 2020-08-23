Vue.component('apartment-list-reservation',{
    props:['mode','items'],
    data: function()
    {
        return{}
    },
    template:`
    <div>
        <template v-if='items.length == 0'>
            <b-row align-h='center'>
                <h5>No reservations found.</h5>
            </b-row>
        </template>
        <b-list-group>
            <b-list-group-item href='#' v-for='apartment in items' class='mb-2' v-bind:key='apartment.id' @click='selectApartment(apartment)'>
                <b-card bg-variant='transparent' border-variant='primary' no-body class='overflow-hidden' style='max-height: auto;'>
                    <b-row no-gutters>
                        <b-col sm='3'>
                            <b-card-img :src='apartment.pictures[0].name'  alt='image' class='rounded-0'></b-card-img>
                        </b-col>
                    
                        <b-col sm='4'>
                            <b-card-body>
                                <b-container>
                                    <b-row align-h='between'>
                                        <b-col cols='auto'>
                                            <h4>
                                                {{apartment.location.address.street}} {{apartment.location.address.houseNumber}}
                                            </h4>
                                            <h6>
                                                {{apartment.location.address.postalCode}} {{apartment.location.address.city}}
                                            </h6>
                                            <div class='mt-3' style='font-size: 20px'>
                                                <span>
                                                <b-badge variant='primary'>{{apartment.numberOfRooms}}</b-badge>{{getRoomLabel(apartment)}}<br>         
                                                </span>
                                                <span>
                                                    <b-badge variant='primary'>{{apartment.numberOfGuests}}</b-badge>{{getGuestLabel(apartment)}}<br>
                                                </span>
                                                <b-badge variant='primary'>{{getApartmentType(apartment)}}</b-badge>
                                            </div>
                                        </b-col>
                                    </b-row>
                                </b-container>
                            </b-card-body>
                        </b-col>

                        <b-col sm='5'>
                            <b-card-body>
                                <b-container>
                                    <b-row align-h='between'>
                                        <b-col cols='auto'>
                                            <div class='mt-3' style='font-size: 20px'>
                                                <b-badge variant='dark'>{{apartment.host.name+' '+apartment.host.surname}}</b-badge>
                                            </div>
                                        </b-col>
                                    </b-row>
                                </b-container>
                            </b-card-body>
                        </b-col>
                    </b-row>
                </b-card>
            </b-list-group-item> 
        </b-list-group>
    </div>
    `,
    mounted(){},
    methods:{
        getApartmentType(apartment)
        {
            if(apartment.apartmentType == 'room')
                return 'Room';
            else if(apartment.apartmentType == 'fullApartment')
                return 'Full Apartment';

            return '';
        },
        getRoomLabel(apartment)
        {
            if(apartment.numberOfRooms > 1)
                return 'rooms';
            else if(apartment.numberOfRooms == 1)
                return 'room';
            
            return '';
        },
        getGuestLabel(apartment)
        {
            if(apartment.numberOfGuests > 1)
                return 'guests';
            else if(apartment.numberOfGuests == 1)
                return 'guest';
            
            return '';
        },
        selectApartment(apartment)
        {
            this.$root.$emit('apartment-selected', apartment);
        }
    }
})

Vue.component('apartment-details',{
    props:['apartment'],
    data: function(){
        return{
            reservations: [],
            userType: '',
            transProps:{
                name: 'flip-list'
            }
        }
    },
    template:`
    <div>  
        <b-row class='mt-2'>
            <b-col md='6'>
                <b-container>
                    <leaflet-map mapid='mapid-details' mode='display' v-model='latlng' height='300'></leaflet-map>
                </b-container>
            </b-col>

            <b-col md='6'>
                <b-container>
                    <picture-viewer v-model='pictures' height='250px' emptymessage='No pictures'></picture-viewer>
                </b-container>
            </b-col>
        </b-row>

        <b-row class='mt-2'>
            <b-col md='6'>
                <h3>{{apartment.location.address.street + ' ' + apartment.location.address.houseNumber}}</h3>
                <h4>{{apartment.location.address.postalCode + ' ' + apartment.location.address.city}}</h4>
                <em>Lat: {{apartment.location.latitude.toFixed(6)}}, Long: {{apartment.location.longitude.toFixed(6)}}</em>
                <h4 class="mt-3">Host: <span><b-badge variant="dark">{{apartment.host.name}} {{apartment.host.surname}}</b-badge></span></h4>

                <div class="mt-3" style="font-size:20px">
                    <span>
                        <b-badge variant="primary">{{apartment.numberOfRooms}}</b-badge>
                    </span> {{ roomLabel }} <br>
                    <span>
                        <b-badge variant="primary">{{apartment.numberOfGuests}}</b-badge>
                    </span> {{ guestLabel }} <br>
                    <b-badge variant="primary">{{ apartmentTypeLabel }}</b-badge>
                    <b-badge variant="primary">{{ apartment.pricePerNight }}€ / night</b-badge>
                </div>
                </b-col>

                <b-col md="6">
                    <div style="max-height: 300px; overflow-y:auto; -webkit-overflow-scrolling: touch;">
                        <b-list-group v-for="amenity in apartment.amenities" v-bind:key="amenity.id">
                            <b-list-group-item> {{ amenity.name }} 
                            </b-list-group-item>
                        </b-list-group>
                    </div> 
                </b-col>
        </b-row>


        <b-row class='mt-2' align-h='center'>
            <template v-if="this.reservations.length == 0">
                <b-row align-h="center">
                    <h5>No reservations found.</h5>
                </b-row>
            </template>    

            <b-table hover striped :fields='fields' :items='this.reservations'>    
                <template v-slot:cell(actions)="data">
                    <label>dsadas</label>
                </template>
            </b-table>
        </b-row>
    </div>
    `,
    mounted() {
        const vm = this;

        let jwt = window.localStorage.getItem('jwt');
        if(!jwt)
            jwt='';

        let apartment = this.apartment;
        axios
            .put('rest/vazduhbnb/reservationsByApartment', apartment,{
                headers:{
                    'Authorization': 'Bearer ' + jwt
                }
            })
            .then(function(response){
                vm.reservations = response.data;
                vm.fixDate(vm.reservations);
            })

        axios
            .get('rest/vazduhbnb/getLoggedinUser',{
                headers:{
                    'Authorization': 'Bearer ' + jwt
                }
            })
            .then(function(response){
                vm.userType = response.data.userType;
            })
    },
    methods:{
        fixDate: function(reservations){
            for(let reservation of reservations)
            {
                reservation.checkIn = new Date(parseInt(reservation.checkIn));
            }
        }
    },
    computed:{
        pictures: function(){
            if(this.apartment)
            {
                let pics = this.apartment.pictures.map(p => p.name);
                return pics;
            }
            return [];
        },
        latlng:function(){
            return {lat: this.apartment.location.latitude, lng: this.apartment.location.longitude};
        },
        guestLabel:function(){
            if(this.apartment.numberOfGuests > 1) return "guests";
            else if(this.apartment.numberOfGuests == 1) return "guest";
            else return '';
        },
        roomLabel:function(){
            if(this.apartment.numberOfRooms > 1) return "rooms";
            else if(this.apartment.numberOfRooms == 1) return "room";
            else return '';
        },
        apartmentTypeLabel:function(){
            if(this.apartment.apartmentType == 'fullApartment') return "Full Apartment";
            else return "Room";
        },
        fields: function()
        {
            fields = [];
            const vm=this;
            if(this.userType == 'admin')
            {
                fields=[
                    {key:'apartment.host.account.username', label:'Host', sortable: true},
                    {key:'guest.account.username', label:'Guest', sortable: true},
                    {key:'checkIn', sortable: true, sortByFormatted: true,
                     formatter: value=>{
                        let format='DD.MM.YYYY.'
                        var parsed = moment(value);
                        return parsed.format(format);
                     }},
                    {key: 'nights', label:'Nights', sortable: true},
                    {key: 'totalPrice', label:'Total price', sortable: true},
                    {key: 'reservationStatus', sortable: true, sortByFormatted: true,
                     formatter: value=>{
                         return value.charAt(0).toUpperCase()+value.slice(1);
                     }}
                ]
            }
            else if(this.userType == 'host')
            {
                fields=[
                    {key:'checkIn', sortable: true, sortByFormatted: true,
                     formatter: value=>{
                        let format='DD.MM.YYYY.'
                        var parsed = moment(value);
                        return parsed.format(format);
                     }},
                    {key: 'nights', label:'Nights', sortable: true},
                    {key: 'totalPrice', label:'Total price', sortable: true},
                    {key: 'reservationStatus', sortable: true, sortByFormatted: true,
                     formatter: value=>{
                         return value.charAt(0).toUpperCase()+value.slice(1);
                     }},
                     {key: 'actions', label: 'Actions'}
                ]
            }
            else if(this.userType == 'guest')
            {
                fields=[
                    {key:'apartment.host.account.username', label:'Host', sortable: true},
                    {key:'checkIn', sortable: true, sortByFormatted: true,
                     formatter: value=>{
                        let format='DD.MM.YYYY.'
                        var parsed = moment(value);
                        return parsed.format(format);
                     }},
                    {key: 'nights', label:'Nights', sortable: true},
                    {key: 'totalPrice', label:'Total price', sortable: true},
                    {key: 'reservationStatus', sortable: true, sortByFormatted: true,
                     formatter: value=>{
                         return value.charAt(0).toUpperCase()+value.slice(1);
                     }},
                    {key: 'actions', label: 'Actions'}
                    ]
            }
            return fields;
        }
    }


})