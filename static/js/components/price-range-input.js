Vue.component("price-range-input", {
    props: ['value', 'disabled'], // value - {startPrice, endPrice}
    data: function(){
        return{
            startPriceString: 0,
            startPrice: '',
            endPriceString: 0,
            endPrice: '',
            priceValid : false
        }
    },
    template:`
<div>
    <b-form inline>
        <span class="mr-2">from: </span>
        <b-input-group prepend="€">
            <b-form-input v-model="startPriceString" :state="priceValidation" :disabled="disabled"></b-form-input>
        </b-input-group>
        <span class="mr-2 ml-2">to: </span>
        <b-input-group prepend="€">
            <b-form-input v-model="endPriceString" :state="priceValidation" :disabled="disabled"></b-form-input>
        </b-input-group>
    <b-form>
</div>
    `   ,
    methods:{

    },
    watch:{
        value: function(){
            this.$emit('input', this.value);
        },
        priceValid:function(){
            if(!this.priceValid)
            this.value = null;
        }
    },
    computed:{
        priceValidation : function(){
            if(this.disabled){
                return null;
            }
            
            this.startPrice = parseFloat(this.startPriceString);
            this.endPrice = parseFloat(this.endPriceString);

            if(!this.startPrice){
                if(this.startPrice === 0){

                }else{
                    this.priceValid = false;
                    return false;
                }
            }

            if(!this.endPrice){
                //this.priceErrorMessage = "Please enter a number.";
                this.priceValid = false;
                return false;
            }

            if(this.startPrice < 0){
                this.priceValid = false;
                //this.priceErrorMessage = "Please enter a positive number.";
                return false;
            }

            if(this.endPrice < this.startPrice){
                this.priceValid = false;
                //this.priceErrorMessage = "Please enter a positive number.";
                return false;
            }

            this.priceValid = true;
            this.value = {
                startPrice: this.startPrice,
                endPrice: this.endPrice
            }
            return true;
        }
    }
});