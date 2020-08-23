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

