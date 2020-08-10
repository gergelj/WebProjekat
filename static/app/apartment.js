var app = new Vue({
    el: "#app",
    data: {
        numberOfRooms: 1,
        numberOfGuests: 1,
        pricePerNightString: '',
        pricePerNight : 0,
        apartmentType: 'fullApartment',
        latitude: '',
        longitude: '',
        street: '',
        houseNumber: '',
        city: '',
        postalCode: '',
        position: {lat:0, lng:0},
        
        pictures: [],
        amenitites: [],
        apartmentTypeEnum: [
            { value: 'fullApartment', text: 'Full Apartment' },
            { value: 'room', text: 'Room' }
        ],

        picturesErrorMessage: '',
        priceErrorMessage: '',
        positionErrorMessage: '',
        positionValid: false,
        priceValid : false,
        picturesValid: false
    },
    mounted() {
        /*this.$root.$on('pictures-uploaded', (pictures) => {
            this.pictures = pictures;
            console.log("Got it!");
        });*/
    },
    methods: {
        onSubmit : function(){
            return null;
        }
    },
    watch:{
        position: function(newPosition, oldPosition){
            this.latitude = newPosition.lat;
            this.longitude = newPosition.lng;
        }
    },
    computed:{
        priceValidation(){
            this.pricePerNight = parseFloat(this.pricePerNightString);

            if(!this.pricePerNight){
                this.priceErrorMessage = "Please enter a number.";
                this.priceValid = false;
                return false;
            }

            if(this.pricePerNight <= 0){
                this.priceValid = false;
                this.priceErrorMessage = "Please enter a positive number.";
                return false;
            }
            
            this.priceValid = true;
            return true;
        },
        positionValidation(){
            if(this.latitude && this.longitude){
                this.positionErrorMessage = '';
                this.positionValid = true;
                return true;
            }
            else{
                this.positionErrorMessage = "Please select a location by clicking on the map.";
                this.positionValid = false;
                return false;
            }
        }
    }
});