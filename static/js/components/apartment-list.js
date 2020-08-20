Vue.component('apartment-list',{
    props:['mode', 'items'],    //value - selected apartment, mode - user type, items - list of apartments
    data: function(){
        return {

        }
    },
    template:`
<div>
    <template v-if="items.length == 0">
        <b-row align-h="center">
            <h5>No apartment found. Please search for something.<h5>
        <b-row>
    </template>
    <b-list-group>
        <b-list-group-item href="#" v-for="apartment in items" v-bind:key="apartment.id" @click="selectApartment(apartment)">
            <b-card bg-variant="transparent" border-variant="primary" no-body class="overflow-hidden" style="max-height: 300px;">
                <b-row no-gutters>
                    <b-col sm="5">
                        <b-card-img :src="apartment.pictures[0].name" alt="Image" class="rounded-0"></b-card-img>
                    </b-col>
                    <b-col sm="7">
                        <b-card-body>
                            <b-container>
                                <b-row align-h="between">
                                    <b-col cols="auto">
                                        <h4>{{apartment.name}}</h4>
                                        <h5>{{apartment.location.address.street}} {{apartment.location.address.houseNumber}}</h5>
                                        <h6>{{apartment.location.address.postalCode}} {{apartment.location.address.city}}</h6>
                                        <div class="mt-3" style="font-size:20px">
                                            <span>
                                                <b-badge variant="primary">{{apartment.numberOfRooms}}</b-badge>
                                            </span> {{getRoomLabel(apartment)}} <br>
                                            <span>
                                                <b-badge variant="primary">{{apartment.numberOfGuests}}</b-badge>
                                            </span> {{getGuestLabel(apartment)}}<br>
                                            <b-badge variant="primary">
                                                {{getApartmentType(apartment)}}
                                            </b-badge>
                                        </div>
                                        
                                    </b-col>

                                    <b-col cols="auto">
                                        <template v-if="mode == 'admin' || mode == 'host'">
                                            <b-badge v-if="apartment.active" variant="success">Active</b-badge>
                                            <b-badge v-if="!apartment.active" variant="danger">Inactive</b-badge>
                                        </template>
                                    </b-col>

                                </b-row>
                        
                                <b-row align-h="end">
                                    <b-col cols="auto">
                                        <b-badge variant="dark" style="font-size:48px">{{apartment.pricePerNight}} €</b-badge>
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
    mounted(){
        //console.log(this.items);
    },
    methods:{
        selectApartment(apartment){
            this.$root.$emit('apartment-selected-event', apartment);
        },
        getApartmentType(apartment){
            if(apartment.apartmentType == 'room')
                return "Room";
            else if(apartment.apartmentType == 'fullApartment')
                return "Full Apartment";
            else
                return '';
        },
        getRoomLabel(apartment){
            if(apartment.numberOfRooms > 1) return "rooms";
            else if(apartment.numberOfRooms == 1) return "room";
            else return '';
        },
        getGuestLabel(apartment){
            if(apartment.numberOfGuests > 1) return "guests";
            else if(apartment.numberOfGuests == 1) return "guest";
            else return '';
        }
    }
})