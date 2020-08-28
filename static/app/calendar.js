new Vue({
    el: "#app",
    data:{
        pricingCalendar : {
            priceByWeekday:{}
        },

        holidayPrice : 1,
        mondayPrice: 1,
        tuesdayPrice: 1,
        wednesdayPrice: 1,
        thursdayPrice: 1,
        fridayPrice: 1,
        saturdayPrice: 1,
        sundayPrice: 1,

        isHolidayValid: false,
        isMondayValid: false,
        isTuesdayValid: false,
        isWednesdayValid: false,
        isThursdayValid: false,
        isFridayValid: false,
        isSaturdayValid: false,
        isSundayValid: false

    },
    computed:{
        holidayPercentage : {
            get(){
                return (this.holidayPrice * 100 - 100).toFixed(2);
            },
            set(val){
                this.pricingCalendar.holidayPercentage = (parseFloat(val) + 100) / 100;
            }
        },
        mondayPercentage:{
            get(){
                return (this.mondayPrice * 100 - 100).toFixed(2);
            },
            set(val){
                this.pricingCalendar.priceByWeekday.monday = (parseFloat(val) + 100) / 100;
            }
        },
        tuesdayPercentage:{
            get(){
                return (this.tuesdayPrice * 100 - 100).toFixed(2);
            },
            set(val){
                this.pricingCalendar.priceByWeekday.tuesday = (parseFloat(val) + 100) / 100;
            }
        },
        wednesdayPercentage:{
            get(){
                return (this.wednesdayPrice * 100 - 100).toFixed(2);
            },
            set(val){
                this.pricingCalendar.priceByWeekday.wednesday = (parseFloat(val) + 100) / 100;
            }
        },
        thursdayPercentage:{
            get(){
                return (this.thursdayPrice * 100 - 100).toFixed(2);
            },
            set(val){
                this.pricingCalendar.priceByWeekday.thursday = (parseFloat(val) + 100) / 100;
            }
        },
        fridayPercentage:{
            get(){
                return (this.fridayPrice * 100 - 100).toFixed(2);
            },
            set(val){
                this.pricingCalendar.priceByWeekday.friday = (parseFloat(val) + 100) / 100;
            }
        },
        saturdayPercentage:{
            get(){
                return (this.saturdayPrice * 100 - 100).toFixed(2);
            },
            set(val){
                this.pricingCalendar.priceByWeekday.saturday = (parseFloat(val) + 100) / 100;
            }
        },
        sundayPercentage:{
            get(){
                return (this.sundayPrice * 100 - 100).toFixed(2);
            },
            set(val){
                this.pricingCalendar.priceByWeekday.sunday = (parseFloat(val) + 100) / 100;
            }
        },
        holidayState(){
            let num = parseFloat(this.pricingCalendar.holidayPercentage);
            if(!num){
                if(num === 0){
                    this.isHolidayValid = true;
                    return true;
                }
                this.isHolidayValid = false;
                return false;
            }

            if(num < 0){
                this.isHolidayValid = false;
                return false; 
            }

            this.isHolidayValid = true;
            return true;
        },
        mondayState(){
            let num = parseFloat(this.pricingCalendar.priceByWeekday.monday);
            if(!num){
                if(num === 0){
                    this.isMondayValid = true;
                    return true;
                }
                this.isMondayValid = false;
                return false;
            }

            if(num < 0){
                this.isMondayValid = false;
                return false; 
            }

            this.isMondayValid = true;
            return true;
        },
        tuesdayState(){
            let num = parseFloat(this.pricingCalendar.priceByWeekday.tuesday);
            if(!num){
                if(num === 0){
                    this.isTuesdayValid = true;
                    return true;
                }
                this.isTuesdayValid = false;
                return false;
            }

            if(num < 0){
                this.isTuesdayValid = false;
                return false; 
            }

            this.isTuesdayValid = true;
            return true;
        },
        wednesdayState(){
            let num = parseFloat(this.pricingCalendar.priceByWeekday.wednesday);
            if(!num){
                if(num === 0){
                    this.isWednesdayValid = true;
                    return true;
                }
                this.isWednesdayValid = false;
                return false;
            }

            if(num < 0){
                this.isWednesdayValid = false;
                return false; 
            }

            this.isWednesdayValid = true;
            return true;
        },
        thursdayState(){
            let num = parseFloat(this.pricingCalendar.priceByWeekday.thursday);
            if(!num){
                if(num === 0){
                    this.isThursdayValid = true;
                    return true;
                }
                this.isThursdayValid = false;
                return false;
            }

            if(num < 0){
                this.isThursdayValid = false;
                return false; 
            }

            this.isThursdayValid = true;
            return true;
        },
        fridayState(){
            let num = parseFloat(this.pricingCalendar.priceByWeekday.friday);
            if(!num){
                if(num === 0){
                    this.isFridayValid = true;
                    return true;
                }
                this.isFridayValid = false;
                return false;
            }

            if(num < 0){
                this.isFridayValid = false;
                return false; 
            }

            this.isFridayValid = true;
            return true;
        },
        saturdayState(){
            let num = parseFloat(this.pricingCalendar.priceByWeekday.saturday);
            if(!num){
                if(num === 0){
                    this.isSaturdayValid = true;
                    return true;
                }
                this.isSaturdayValid = false;
                return false;
            }

            if(num < 0){
                this.isSaturdayValid = false;
                return false; 
            }

            this.isSaturdayValid = true;
            return true;
        },
        sundayState(){
            let num = parseFloat(this.pricingCalendar.priceByWeekday.sunday);
            if(!num){
                if(num === 0){
                    this.isSundayValid = true;
                    return true;
                }
                this.isSundayValid = false;
                return false;
            }

            if(num < 0){
                this.isSundayValid = false;
                return false; 
            }

            this.isSundayValid = true;
            return true;
        },
        isDataValid(){
            return this.isHolidayValid && this.isMondayValid && this.isTuesdayValid && this.isWednesdayValid && this.isThursdayValid && this.isFridayValid && this.isSaturdayValid && this.isSundayValid;
        }
    },
    mounted(){
        let jwt = window.localStorage.getItem('jwt');
        if(!jwt)
            jwt = '';

        const vm = this;

        axios
            .get("rest/vazduhbnb/pricingCalendar", {
                headers:{
                    "Authorization" : "Bearer " + jwt
                }
            })
            .then(function(response){
                let calendar = response.data;
                vm.fixDates(calendar.holidays);
                vm.pricingCalendar = calendar;

                vm.holidayPrice = calendar.holidayPercentage;
                vm.mondayPrice = calendar.priceByWeekday.monday;
                vm.tuesdayPrice = calendar.priceByWeekday.tuesday;
                vm.wednesdayPrice = calendar.priceByWeekday.wednesday;
                vm.thursdayPrice = calendar.priceByWeekday.thursday;
                vm.fridayPrice = calendar.priceByWeekday.friday;
                vm.saturdayPrice = calendar.priceByWeekday.saturday;
                vm.sundayPrice = calendar.priceByWeekday.sunday;
            })
            .catch(function(error){
                let response = error.response;
                switch(response.status){
                    case 401: alert(unauthorizedErrorMessage); signOut(); break;
                    case 403: alert(forbiddenErrorMessage); signOut(); break;
                    case 500: pushInternalServerError(); break;
                }
            })
    },
    methods:{
        fixDates(listDate){
            for(let i in listDate)
                listDate[i] = new Date(parseInt(listDate[i]));
        },
        onSave(){
            let jwt = window.localStorage.getItem('jwt');
            if(!jwt)
                jwt = '';

            this.pricingCalendar.holidays = this.pricingCalendar.holidays.map(d => d.getTime());

            if(this.isDataValid){
                axios
                    .put("rest/vazduhbnb/pricingCalendar", this.pricingCalendar, {
                        headers:{
                            "Authorization" : "Bearer " + jwt
                        }
                    })
                    .then(function(response){
                        pushSuccessNotification("Success!", "Calendar pricing saved successfully");
                    })
                    .catch(function(error){
                        let response = error.response;
                        switch(response.status){
                            case 401: alert(unauthorizedErrorMessage); signOut(); break;
                            case 403: alert(forbiddenErrorMessage); signOut(); break;
                            case 500: pushInternalServerError(); break;
                        }
                    })
            }
        }
    }
})