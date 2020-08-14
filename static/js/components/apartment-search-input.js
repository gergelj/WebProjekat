// dependency: price-range-input, v-calendar

Vue.component('apartment-search-input',{
    props:['value'],    //value - ApartmentFilterDTO, on search button click a new object is emitted
    data: function(){
        return {
            city: '',

            numberOfRoomsStatus: 'unchecked',
            numberOfRooms: 0,
    
            numberOfGuestsStatus: 'unchecked',
            numberOfGuests: 0,
    
            dateRangeStatus: 'unchecked',
            dateRange: {
                start: '',
                end: ''
            },

            priceStatus: 'unchecked',
            priceRange: null
        }
    },
    template:`
<div>
    <b-card no-body>
        <template v-slot:header>
            <h4 class="mb-0">Search</h4>
        </template>

        <b-container>
            <b-row align-h="center" class="mt-2">
                <b-form inline>
                    <b-form-input v-model="city" placeholder="Enter city name" class="mr-3"></b-form-input>
                    <b-button @click="onSearch" variant="primary">Search</b-button>
                </b-form>
            </b-row>

            <b-row class="mt-2" align-h="center">
                <b-form inline>
                    <b-form-checkbox
                        class="mr-3"
                        id="checkbox-date-range"
                        v-model="dateRangeStatus"
                        name="checkbox-1"
                        value="checked"
                        unchecked-value="unchecked"
                        button button-variant="outline-info">
                        Dates
                    </b-form-checkbox>
                    <v-date-picker v-model="dateRange" :min-date='new Date()' is-inline :columns="$screens({ default: 1, lg: 3 })" mode="range"/>

                </b-form>
            </b-row>

            <b-row align-h="center" class="mt-2">
                <b-form inline>
                        <b-form-checkbox
                            class="mr-3"
                            id="checkbox-price-range"
                            v-model="priceStatus"
                            name="checkbox-2"
                            value="checked"
                            unchecked-value="unchecked"
                            button button-variant="outline-info">
                            Price
                        </b-form-checkbox>
                        <price-range-input v-model="priceRange" :disabled="priceStatus == 'unchecked'"></price-range-input>
                </b-form>
            </b-row>

            <b-row align-h="center" class="mt-2 mb-2">
                <b-form inline >
                    <b-form-checkbox
                        class="mr-3"
                        id="checkbox-rooms"
                        v-model="numberOfRoomsStatus"
                        name="checkbox-3"
                        value="checked"
                        unchecked-value="unchecked"
                        button button-variant="outline-info">
                        Number of Rooms
                    </b-form-checkbox>
                    
                    <b-form-spinbutton v-model="numberOfRooms" min="0" max="100" :disabled="numberOfRoomsStatus == 'unchecked'"></b-form-spinbutton>
                </b-form>

                <b-form inline>
                    <b-form-checkbox
                        class="ml-5 mr-3"

                        id="checkbox-guests"
                        v-model="numberOfGuestsStatus"
                        name="checkbox-4"
                        value="checked"
                        unchecked-value="unchecked"
                        button button-variant="outline-info">
                        Number of Guests
                    </b-form-checkbox>
                    <b-form-spinbutton v-model="numberOfGuests" min="0" max="100" :disabled="numberOfGuestsStatus == 'unchecked'"></b-form-spinbutton>
                </b-form>
            </b-row>

        </b-container>
    </b-card>
</div>
    `,
    methods:{
        onSearch: function(){
            this.value = {
                city: this.city.trim(),
                numberOfRooms: (this.numberOfRoomsStatus == 'checked' ? this.numberOfRooms : 0),
                numberOfGuests : (this.numberOfGuestsStatus == 'checked' ? this.numberOfGuests : 0),
                dateRange: (this.dateRangeStatus == 'checked' ? {start: this.dateRange.start, end: this.dateRange.end} : null),
                priceRange: (this.priceStatus == 'checked' ? this.priceRange : null)
            }

            this.$emit('input', this.value);
        }
    }
});