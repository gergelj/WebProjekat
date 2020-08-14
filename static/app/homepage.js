var app = new Vue({
    el: "#app",
    data:{
        status: false,

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
        priceRange: {
            startPrice: 0,
            endPrice: 0
        }
    },
    methods:{
        
    },
    watch:{
        priceRange: function(newP, oldP){
            console.log("changed");
        }
    }
});