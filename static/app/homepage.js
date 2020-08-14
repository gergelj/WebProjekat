var app = new Vue({
    el: "#app",
    data:{
        filter: {}
    },
    methods:{
        
    },
    watch:{
        filter:function(){
            //TODO: search
            if(this.filter.dateRange){
                this.filter.dateRange.start = this.filter.dateRange.start.getTime();
                this.filter.dateRange.end = this.filter.dateRange.end.getTime();
            }
            console.log(this.filter);

        }
    }
});