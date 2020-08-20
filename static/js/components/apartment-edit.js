// emits:
//      
// dependency: cyrillic, leaflet-map

Vue.component('apartment-edit',{
    props:['value'],
    data:function(){
        return{
            apartmentTypeEnum:[
                {value: 'fullApartment', text: "Full Apartment"},
                {value: 'room', text: "Room"}
            ],

            availableDates: [],
            bookedDates: [],
            priceErrorMessage: '',
            priceValid : false,
            positionErrorMessage: '',
            positionValid: false,
            nameErrorMessage:'',
            nameValid:false
        }
    },
    template:`
<div>
    <b-form novalidate>

        <b-card bg-variant="light">
            <template v-slot:header>
                <h4 class="mb-0" align="center">Location</h4>
            </template>
            <b-container fluid>
                <b-row>
                    <b-col align-self="center">
                        <b-form-group
                            label-cols-sm="5"
                            label="Street"
                            label-align-sm="right"
                            label-for="nested-street">
                            <b-form-input id="nested-street" v-model="value.location.address.street"></b-form-input>
                        </b-form-group>

                        <b-form-group
                            label-cols-sm="5"
                            label="House number"
                            label-align-sm="right"
                            label-for="nested-house">
                            <b-form-input id="nested-house" v-model="value.location.address.houseNumber"></b-form-input>
                        </b-form-group>

                        <b-form-group
                            label-cols-sm="5"
                            label="Postal code"
                            label-align-sm="right"
                            label-for="nested-postal">
                            <b-form-input id="nested-postal" v-model="value.location.address.postalCode"></b-form-input>
                        </b-form-group>

                        <b-form-group
                            label-cols-sm="5"
                            label="City"
                            label-align-sm="right"
                            label-for="nested-city">
                            <b-form-input id="nested-city" v-model="value.location.address.city"></b-form-input>
                        </b-form-group>

                        <b-form-group
                            label-cols-sm="5"
                            label="Latitude *"
                            label-align-sm="right"
                            label-for="nested-latitude">
                            <b-form-input id="nested-latitude" v-model="value.location.latitude" :state="positionValidation" disabled></b-form-input>
                            <b-form-invalid-feedback :state="positionValidation">
                            </b-form-invalid-feedback>
                        </b-form-group>

                        <b-form-group
                            label-cols-sm="5"
                            label="Longitude *"
                            label-align-sm="right"
                            label-for="nested-longitude">
                            <b-form-input id="nested-longitude" v-model="value.location.longitude" :state="positionValidation" disabled></b-form-input>
                            <b-form-invalid-feedback :state="positionValidation">
                                {{positionErrorMessage}}
                            </b-form-invalid-feedback>
                        </b-form-group>

                    </b-col>
                    <b-col>
                        <leaflet-map mapid="mapid-edit" mode="input-display" v-model="position" height="450"></leaflet-map>
                    </b-col>
                </b-row>
            </b-container>
        </b-card>

        <b-card bg-variant="light" class="mt-3">
            <template v-slot:header>
                <h4 class="mb-0" align="center">Details</h4>
            </template>
        
            <b-container fluid>
                <b-row>
                    <b-col>
                        <b-container fluid>

                            <b-form-group
                                label-cols-sm="5"
                                label="Name *"
                                label-align-sm="right"
                                label-for="nested-name">

                                <b-form-input id="nested-name" v-model="value.name" :state="nameValidation"></b-form-input>
                                    <b-form-invalid-feedback :state="nameValidation">
                                        {{nameErrorMessage}}
                                    </b-form-invalid-feedback>
                            </b-form-group>

                            <b-form-group
                                label-cols-sm="5"
                                label="Type"
                                label-align-sm="right"
                                label-for="nested-apartment-type">
                                <b-form-select id="nested-apartment-type" v-model="value.apartmentType" :options="apartmentTypeEnum"></b-form-select>
                            </b-form-group>

                            <b-form-group
                                label-cols-sm="5"
                                label="Price per night *"
                                label-align-sm="right"
                                label-for="nested-price">
                                <b-input-group prepend="€">
                                    <b-form-input id="nested-price" v-model="value.pricePerNight" :state="priceValidation"></b-form-input>
                                </b-input-group>
                                        <b-form-invalid-feedback :state="priceValidation">
                                            {{priceErrorMessage}}
                                        </b-form-invalid-feedback>
                            </b-form-group>

                            <b-form-group
                                label-cols-sm="5"
                                label="Rooms"
                                label-align-sm="right"
                                label-for="nested-rooms">
                                <b-form-spinbutton id="nested-rooms" v-model="value.numberOfRooms" min="1" max="100" :disabled="value.apartmentType == 'room'"></b-form-spinbutton>
                            </b-form-group>

                            <b-form-group
                            label-cols-sm="5"
                            label="Guests"
                            label-align-sm="right"
                            label-for="nested-guests">
                                <b-form-spinbutton id="nested-guests" v-model="value.numberOfGuests" min="1" max="100"></b-form-spinbutton>
                            </b-form-group>

                            <b-form-group
                            label-cols-sm="5"
                            label="Check In Hour"
                            label-align-sm="right"
                            label-for="nested-checkin">
                                <b-form-spinbutton id="nested-checkin" v-model="value.checkInHour" min="12" max="20"></b-form-spinbutton>
                            </b-form-group>

                            <b-form-group
                            label-cols-sm="5"
                            label="Check Out Hour"
                            label-align-sm="right"
                            label-for="nested-checkout">
                                <b-form-spinbutton id="nested-checkout" v-model="value.checkOutHour" min="8" max="11"></b-form-spinbutton>
                            </b-form-group>

                        </b-container>
                        <b-container fluid>
                            <!-- booking dates -->
                            <b-form-group
                                label="Booking Dates"
                                description="Selected dates will be available for your clients' booking.">
                                <v-date-picker v-model="availableDates" :disabled-dates="unavailableDates" :attributes='datePickerAttributes' is-inline :columns="$screens({ default: 1, lg: 2 })" mode="multiple"/>
                            </b-form-group>
                        </b-container>
                    </b-col>
                    <b-col>
                        <b-container fluid>
                            <!-- amenity list -->
                            <b-form-group
                                label="Amenities">
                                <amenity-list v-model="value.amenities" align="center"></amenity-list>
                            </b-form-group>
                        </b-container>
                    </b-col>
                </b-row>
            </b-container>
        </b-card>

        <!--
        <b-card bg-variant="light" class="mt-3">
            <template v-slot:header>
                <h4 class="mb-0" align="center">Pictures</h4>
            </template>
            <b-container>
                <picture-viewer v-model="pictures" v-if="pictures.length > 0" emptymessage="No pictures added" height="600"></picture-viewer>
                <picture-uploader v-model="pictures"></picture-uploader>
            </b-container>
        </b-card>
        -->

    </b-form>

    <b-row align-h="center">
        <b-col cols="auto">
            <b-button @click="onEditApartment" variant="warning" :disabled="!isDataValid" align="center" class="mt-3">Save Apartment</b-button>
        </b-col>
    </b-row>


</div> 
    `,
    methods:{
        onEditApartment(){
            let jwt = window.localStorage.getItem("jwt");
            if(!jwt)
                jwt = '';

            const vm = this;

            axios
                .put("rest/vazduhbnb/apartment", {
                    apartment: this.value,
                    dates: this.availableDates.map(d => d.getTime())
                },
                {
                    headers:{
                        "Authorization" : "Bearer " + jwt
                    }
                })
                .then(function(response){
                    pushSuccessNotification("Success", "Apartment updated");
                    vm.$root.$emit("apartment-updated-event");
                })
                .catch(function(error){
                    let response = error.response;
                    switch(response.status){
                        case 400: pushErrorNotification("An error occured", response.data.message); break;
                        case 401: alert("User not logged in."); signOut(); break;
                        case 403: alert("Please login with privileges."); signOut(); break;
                        case 500: pushErrorNotification("Internal Server Error", "Please try again later."); break;
                    }
                })
        },
        fixDate(dateList){
            for(let i in dateList){
                dateList[i] = new Date(parseInt(dateList[i]));
            }
            return dateList;
        }
    },
    mounted(){
        let jwt = window.localStorage.getItem("jwt");
        if(!jwt)
            jwt = '';

        const vm = this;

        axios
            .get("rest/vazduhbnb/availabledates", {
                params:{
                    apartment: this.value.id
                },
                headers:{
                    "Authorization" : "Bearer " + jwt
                }
            })
            .then(function(response){
                vm.availableDates = vm.fixDate(response.data);
            })
            .catch(function(error){
                let response = error.response;
                switch(response.status){
                    case 401: alert("User not logged in."); signOut(); break;
                    case 500: pushErrorNotification("Internal Server Error", "Please try again later."); break;
                }
            });

        
        axios
            .get("rest/vazduhbnb/unavailabledates",{
                params:{
                    apartment: this.value.id
                },
                headers:{
                    "Authorization" : "Bearer " + jwt
                }
            })
            .then(function(response){
                vm.bookedDates = vm.fixDate(response.data);
            })
            .catch(function(error){
                let response = error.response;
                switch(response.status){
                    case 401: alert("User not logged in."); signOut(); break;
                    case 500: pushErrorNotification("Internal Server Error", "Please try again later."); break;
                }
            })
    },
    watch:{
        apartmentType:function(newValue){
            if(newValue == 'room')
                this.value.numberOfRooms = 1;
        }
    },
    computed:{
        position:{
            get(){
                return {lat: this.value.location.latitude, lng: this.value.location.longitude};
            },
            set(val){
                this.value.location.latitude = val.lat;
                this.value.location.longitude = val.lng;

                const vm = this;

                axios
                    .get("https://us1.locationiq.com/v1/reverse.php?key=d9ed4cd3090a09&lat=" + this.value.location.latitude + "&lon=" + this.value.location.longitude + "&format=json")
                    .then(function(response){
                        let address = response.data.address;
                        vm.value.location.address.city = transliterate(address.city_district ? address.city_district : address.city);
                        vm.value.location.address.postalCode = transliterate(address.postcode);
                        vm.value.location.address.street = transliterate(address.road);
                        vm.value.location.address.houseNumber = transliterate(address.house_number);
                    })
                    .catch(function(error){
                        console.log(error);
                    });

            }
        },
        apartmentType:function(){
            return this.value.apartmentType;
        },
        priceValidation(){
            let price = parseFloat(this.value.pricePerNight);

            if(!price){
                this.priceErrorMessage = "Please enter a number.";
                this.priceValid = false;
                return false;
            }

            if(price <= 0){
                this.priceValid = false;
                this.priceErrorMessage = "Please enter a positive number.";
                return false;
            }
            
            this.priceValid = true;
            return true;
        },
        positionValidation:function(){
            if(this.value.location.latitude && this.value.location.longitude){
                this.positionErrorMessage = '';
                this.positionValid = true;
                return true;
            }
            else{
                this.positionErrorMessage = "Please select a location by clicking on the map.";
                this.positionValid = false;
                return false;
            }
        },
        nameValidation:function(){
            if(this.value.name.trim() == ''){
                this.nameErrorMessage = "Please enter apartment name";
                this.nameValid = false;
                return false;
            }
            else{
                this.nameValid = true;
                return true;
            }
        },
        isDataValid:function(){
            return this.positionValid && this.priceValid && this.nameValid;
        },
        unavailableDates: function(){
            return [{
                start: null,
                end: new Date()
              }].concat(this.bookedDates);
        },
        datePickerAttributes:function(){
            return [{
                key: "bookedDates",
                dot: true,
                content: 'red',
                dates: this.bookedDates,
                popover: {
                    label: "Booked date",
                    visibility: 'click',
                    hideIndicator: true,
                  }
            }];
        }
    }
})