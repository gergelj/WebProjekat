Vue.component('apartment-details',{
    props:['apartment'],
    data: function(){
        return{
            reservations: [],
            userType: '',
            selectedReservation: {}
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

            <b-container v-if='userType == "host" || userType == "guest"'>
                <b-button
                class='mr-1'
                pill
                variant='danger'
                v-if='userType=="guest" && (selectedReservation.reservationStatus == "accepted" || selectedReservation.reservationStatus == "created")'
                @click='onCancel'
                >Cancel</b-button>

                <b-button
                class='mr-1'
                pill
                variant='danger'
                v-if='userType=="host" && (selectedReservation.reservationStatus == "accepted" || selectedReservation.reservationStatus == "created")'
                @click='onReject'
                >Reject</b-button>

                <b-button
                class='mr-1'
                pill
                variant='success'
                v-if='userType=="host" && selectedReservation.reservationStatus=="created"'
                @click='onAccept'
                >Accept</b-button>

                <b-button
                class='mr-1'
                pill
                variant='success'
                v-if='userType=="host" && (selectedReservation.reservationStatus == "rejected"||selectedReservation.reservationStatus == "accepted"||selectedReservation.reservationStatus == "created") '
                @click='onFinish'
                >Finish</b-button>
                
            </b-container>
            <b-table 
                striped
                hover 
                select-mode='single' 
                selectable
                bordered
                :fields='fieldsChange' 
                :items='reservations'
                @row-clicked='selectReservation'>
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
        },
        selectReservation: function(record, index)
        {
            this.selectedReservation = record;
            pushSuccessNotification('You have selected reservation','');
        },
        onCancel: function()
        {
            //TODO: implement
        },
        onReject: function()
        {
            //TODO: implement
        },
        onAccept: function()
        {
            //TODO: implement
        },
        onFinish: function()
        {
            //TODO: implement
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
        fieldsChange: function()
        {

            if(this.userType == 'admin')
            {
                return [
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
                return [
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
            else if(this.userType == 'guest')
            {
                return [
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
                     }}
                    ]
            }
        }
    }


})