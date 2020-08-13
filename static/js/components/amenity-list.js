Vue.component("amenity-list",{
    props:['value', 'height'],
    data: function(){
        return {
            items: []
        }
    },
    template:`
<div v-bind:style="overScrollStyle">
    <b-form-group>
        <b-form-checkbox-group
            v-model="value"
            :options="items"
            name="flavour-2a"
            stacked
            buttons button-variant="outline-secondary">
        </b-form-checkbox-group>
  </b-form-group>
</div>    
`   ,
    mounted(){
        const vm = this;
        axios
            .get('/rest/vazduhbnb/amenities')
            .then(function(response){
                vm.setAmenities(response.data);
            })
            .catch(function(error){
                let response = error.response;
                alert(response.data.message);
                return;
            });
    },
    methods:{
        onListClick: function(){
            alert('clicked');
        },
        setAmenities: function(amenities){
            for(amenity of amenities){
                this.items.push({text: amenity.name, value: amenity});
            }
        }
    },
    watch:{
        value:function(newSel, oldSel){
            //console.log(newSel);
            this.$emit('input', this.value)
        }
    },
    computed:{
        overScrollStyle : function(){
            return{
                'max-height': this.height ? (this.height + 'px') : ('500px'),
                'margin-bottom': '10px',
                'overflow-y':'auto',
                '-webkit-overflow-scrolling': 'touch'
            }
        }
    }

});