// props:
//      value - selected Apartment

// dependencies:
//      v-date-picker
//      moment
//      axios
//      webfonts.css

Vue.component('book-apartment', {
    props:['value'],
    data : function(){
        return{
            selectedDate : null,
            dateErrorMessage: '',
            dateValid: false,

            numberOfNights: 0,
            nightsValid: false,
            nightsErrorMessage:'',

            totalPrice: 0,
            message:'',

            available: false,

            dateFormat: 'DD.MM.YYYY',

            availableDates: [],
            checkInDates:[],
            checkOutDates:[]
        }
    },
    mounted(){
        let jwt = window.localStorage.getItem('jwt');
        if(!jwt)
            jwt = '';

        const vm = this;

        axios
            .get("rest/vazduhbnb/availabledates",{
                params:{
                    apartment : this.value.id
                },
                headers:{
                    "Authorization" : "Bearer " + jwt
                }
            })
            .then(function(response){
                vm.availableDates = vm.fixDate(response.data);
                vm.availableDates = vm.removePastDates(vm.availableDates);
                if(vm.availableDates.length == 0){
                    vm.showCancelBookingDialog();
                }
            })
            .catch(function(error){
                let response = error.response;
                switch(response.status){
                    case 401: alert("User not logged in."); signOut(); break;
                    case 500: pushErrorNotification("Internal Server Error", "Please try again later."); break;
                }
            });

        axios
            .get("rest/vazduhbnb/bookingdatesinfo",{
                params:{
                    apartment : this.value.id
                },
                headers :{
                    "Authorization" : "Bearer " + jwt
                }
            })
            .then(function(response){
                vm.checkInDates = vm.fixDate(response.data.checkInDates);
                vm.checkOutDates = vm.fixDate(response.data.checkOutDates);
            })
            .catch(function(error){
                let response = error.response;
                switch(response.status){
                    case 401: alert("User not logged in."); signOut(); break;
                    case 500: pushErrorNotification("Internal Server Error", "Please try again later."); break;
                }
            });
    },
    methods:{
        fixDate(listDate){
            for(let i in listDate){
                listDate[i] = new Date(parseInt(listDate[i]));
            }
            return listDate;
        },
        removePastDates(listDate){
            let newList = [];
            let today = new Date().getTime();
            for(let date of listDate){
                if(date.getTime() >= today){
                    newList.push(date);
                }
            }
            return newList;
        },
        checkAvailability(){
            if(this.dataValid){
                let jwt = window.localStorage.getItem('jwt');
                if(!jwt)
                    jwt = '';

                const vm = this;

                axios
                    .get("rest/vazduhbnb/totalPrice",{
                        params:{
                            dates: {
                                start: this.selectedDate.getTime(),
                                end: this.checkOutDate.getTime()
                            },
                            apartment: this.value.id
                        },
                        headers:{
                            "Authorization" : "Bearer " + jwt
                        }
                    })
                    .then(function(response){
                        vm.available = true;
                        vm.totalPrice = parseFloat(response.data)
                    })
                    .catch(function(error){
                        let response = error.response;
                        switch(response.status){
                            case 400: vm.available = false; pushErrorNotification("Not available", response.data.message); break;
                            case 401: alert("User not logged in."); signOut(); break;
                            case 403: alert("Access denied. Login with privileges."); signOut(); break;
                            case 500: pushErrorNotification("Internal Server Error", "Please try again later."); break;
                        }
                    });
            }
        },
        showCancelBookingDialog(){
            const vm = this;
            this.$bvModal.msgBoxOk('No available dates found for this apartment', {
                title: 'Unavailable',
                size: 'sm',
                buttonSize: 'sm',
                okVariant: 'secondary',
                headerClass: 'p-2 border-bottom-0',
                footerClass: 'p-2 border-top-0',
                centered: true
              })
                .then(value => {
                    vm.$root.$emit("close-booking-event");
                })
                .catch(err => {
                    vm.$root.$emit("close-booking-event");
                });
        },
        showSuccessMessageDialog(){
            const vm = this;
            this.$bvModal.msgBoxOk('Booking successfully created', {
                title: 'Success',
                size: 'sm',
                buttonSize: 'sm',
                okVariant: 'success',
                headerClass: 'p-2 border-bottom-0',
                footerClass: 'p-2 border-top-0',
                centered: true
              })
                .then(value => {
                    vm.$root.$emit("close-booking-event");
                })
                .catch(err => {
                    vm.$root.$emit("close-booking-event");
                });
        },
        onBookApartment(){
            if(this.available){
                let jwt = window.localStorage.getItem('jwt');
                if(!jwt)
                    jwt = '';

                const vm = this;

                axios
                    .post("rest/vazduhbnb/reservation", {
                        dateRange:{
                            start: this.selectedDate.getTime(),
                            end: this.checkOutDate.getTime()
                        },
                        nights : this.numberOfNights,
                        message : this.message,
                        apartmentId: this.value.id
                    },{
                        headers:{
                            "Authorization" : "Bearer " + jwt
                        }
                    })
                    .then(function(response){
                        vm.showSuccessMessageDialog();
                    })
                    .catch(function(error){
                        let response = error.response;
                        switch(response.status){
                            case 400: vm.available = false; pushErrorNotification("Not available", response.data.message); break;
                            case 401: alert("User not logged in."); signOut(); break;
                            case 403: alert("Access denied. Login with privileges."); signOut(); break;
                            case 500: pushErrorNotification("Internal Server Error", "Please try again later."); break;
                        }
                    });
            }
        }
    },
    filters:{
        dateFilter:function(value, format){
            if(value)
                return moment(value).format(format);
            else
                return  '';
        }
    },
    watch:{
        selectedDate:function(){
            this.available = false;
        },
        numberOfNights:function(){
            this.available = false;
        }
    },
    computed:{
        dateState: function(){
            if(this.selectedDate){
                if(this.checkInDates.map(Number).indexOf(+this.selectedDate) != -1){
                    this.dateErrorMessage = 'Selected date is only available for check out';
                    this.dateValid = false;
                    return false;
                }
                else{
                    this.dateValid = true;
                    return true;
                }
            }
            else{
                this.dateErrorMessage = 'Please select a date';
                this.dateValid = false;
                return false;
            }
        },
        nightState:function(){
            let numOfNights = parseInt(this.numberOfNights);

            if(!numOfNights){
                this.nightsErrorMessage = 'Please enter number of nights';
                this.nightsValid = false;
                return false;
            }
            else{
                if(numOfNights < 0){
                    this.nightsErrorMessage = 'Please enter a positive number';
                    this.nightsValid = false;
                    return false;
                }
                else{
                    this.nightsValid = true;
                    return true;
                }
            }
        },
        dataValid : function(){
            return this.dateValid && this.nightsValid;
        },
        checkOutDate: function(){
            if(this.dataValid){
                return new Date(this.selectedDate.getTime() + 1000*3600*24*this.numberOfNights);
            }
            else{
                return {}
            }
        },
        datePickerAttributes:function(){
            return [
                {
                    key: "checkInDates",
                    dot: {
                        color: 'red',
                        class: 'my-dot-class-checkin',
                    },
                    //content: 'red',
                    dates: this.checkInDates,
                    popover: {
                        label: "You can only check out on this day",
                        visibility: 'hover',
                        hideIndicator: true,
                    }
                },
                {
                    key: "checkOutDates",
                    dot: {
                        color: 'red',
                        class: 'my-dot-class-checkout',
                    },
                    //content: 'red',
                    dates: this.checkOutDates,
                    popover: {
                        label: "You can only check in on this day",
                        visibility: 'hover',
                        hideIndicator: true,
                    }
                },
                {
                    key: 'selectedDates',
                    highlight: true,
                    dates: this.dateValid ? (this.nightsValid ? {start: this.selectedDate, end: this.checkOutDate} : []) : []
                }
            ];
        }
    },
    template:`
<div>

    <b-row>
        <b-col cols="auto">
            <!-- v-date-picker -->
            <b-form-group
                label-for="checkin-date"
                :state="dateState">

                <template v-slot:label>
                    <b-row align-h="between" align-v="center">
                        <b-col cols="auto">
                            Pick your arrival date
                        </b-col>
                        <b-col cols="auto">
                            <b-button @click="selectedDate = null" variant="outline-danger">Clear</b-button>
                        </b-col>
                    </b-row>
                </template>
                <v-date-picker id="checkin-date" v-model="selectedDate" :step="1" :available-dates="availableDates" :attributes='datePickerAttributes' is-inline :columns="$screens({ default: 1, lg: 2 })" mode="single"/>
                
                <b-form-invalid-feedback :state="dateState">
                    {{dateErrorMessage}}
                </b-form-invalid-feedback>
            </b-form-group>
        </b-col>
        <b-col cols="auto">
            <b-form-group
                label="Enter number of nights"
                label-for="nights-input"
                :state="nightState">

                <b-form-input v-model="numberOfNights" id="nights-input" :state="nightState"></b-form-input>
                
                <b-form-invalid-feedback :state="nightState">
                    {{nightsErrorMessage}}
                </b-form-invalid-feedback>
            </b-form-group>

            <b-row>
                <b-col cols="auto">
                    <b-button @click="checkAvailability" variant="warning" :disabled="!dataValid">Check availability</b-button>
                    <span v-if="available" class="ml-3"><i class="fas fa-check"></i> Available</span>
                </b-col>
            </b-row>

            <template v-if="available">
                <b-row class="mt-3">
                    <b-col cols="auto">
                        <h4>
                            <b-badge variant="info">check in</b-badge>
                            <b-badge v-if="dataValid" id="checkin-badge" variant="light">{{selectedDate | dateFilter(dateFormat)}}</b-badge>
                            <b-badge v-if="dataValid" variant="light">{{value.checkInHour}}:00 h</b-badge>
                        </h4>

                        <h4>
                            <b-badge variant="info">check out</b-badge>
                            <b-badge v-if="dataValid" id="checkout-badge" variant="light">{{checkOutDate | dateFilter(dateFormat)}}</b-badge>
                            <b-badge v-if="dataValid" variant="light">{{value.checkOutHour}}:00 h</b-badge>
                        </h4>

                        <div>
                            <h3>
                                <b-badge id="total-price-badge" variant="primary">total</b-badge>
                                <span v-b-tooltip.hover.bottom="'Booking price may be different on holidays and weekends.'" id="total-price-text" v-if="totalPrice != 0">{{totalPrice.toFixed(2)}} €</span>
                            </h3>
                        </div>
                    </b-col>
                </b-row>
            <template>
        <b-col>
    </b-row>
    
    <b-row class="mt-2">
        <b-col>
            <b-form-textarea
                v-model="message"
                placeholder="Message to the host"
                rows="3"
                max-rows="6">
            </b-form-textarea>
        </b-col>
    </b-row>
    
    <b-row class="mt-3" align-h="center">
        <b-col cols="auto">
            <b-button @click="onBookApartment" variant="success" :disabled="!available"><i class="far fa-calendar-check"></i> Book</b-button>
        </b-col>
    </b-row>
</div>
    `
});