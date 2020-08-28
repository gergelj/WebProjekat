//props:
//      value - selectedAmenity
//      items - all amenities
Vue.component("tabela-js", {
    props:['value', 'items'],
    data: function(){
        return {
            currentPage:1,
            perPage: 10,
            fields:[
                {key: 'index', label:'#', sortable: true},
                {key: 'name', sortable: true}
            ],
            deletedAmenity: {},
            componentKey:0
        }
    },
    template:`
    <div id>
        <b-table
            ref="amenityTable"
            selectable
            @row-selected="selectAmenity" 
            bordered 
            hover
            :per-page="perPage"
            :current-page="currentPage"
            :items="items" 
            :fields="fields"
            select-mode="single"
            selected-variant="primary"
            id="tabela">
            <template v-slot:cell(index)="data">
                {{ items.findIndex(it => it.id == data.item.id) + 1 }}
            </template>
        </b-table>

        <b-row align-h="center">
            <b-col cols="auto">
                <b-pagination
                    v-model="currentPage"
                    :total-rows="rows"
                    :per-page="perPage"
                    aria-controls="tabela">
                </b-pagination>
            </b-col>
        </b-row>
        
    </div>
    `,
    methods: {
        sendToSibling: function(){
            this.$root.$emit('messageToSibling', this.selectedAmenity);
        },
        selectAmenity: function(amenity){
            this.value = amenity[0] == undefined ? null : amenity[0];
            this.$emit('input', this.value);
        },

    },
    computed:{
        rows(){
            return this.items.length;
        }
    },
    watch: {
        value(newValue, oldValue){
            if(newValue == null){
                this.$refs.amenityTable.clearSelected();
            }
        }
    }
});

Vue.component("add-form", {
    data: function(){
        return{
            name:''
        }
    },
    template:`
    <div>
        <b-form>
            <b-form-group label="New Amenity">
                <b-form-input
                    :formatter="formatter"
                    v-model="name"
                    trim
                    placeholder="Enter name">
                </b-form-input>
            </b-form-group>
            <b-row align-h="end">
                <b-col cols="auto">
                    <b-button :disabled="!nameValid" @click="addNew" variant="success">Add</b-button>
                </b-col>
            </b-row>
        </b-form>
    </div>
    `,
    methods: {
        formatter: function(value){
            if(value.length > 100)
                return value.substring(0,100);
            return value;
        },
        addNew: function(){
            if(this.nameValid){
                let jwt = window.localStorage.getItem('jwt');
                if(!jwt)
                    jwt='';

                const vm = this;
                axios
                    .post('rest/vazduhbnb/amenity', this.name, {
                        headers: {
                            "Authorization" : "Bearer " + jwt
                        }
                    })
                    .then(response =>{
                        pushSuccessNotification('Success!', 'You added new amenity to the list!');
                        this.$root.$emit('addedAmenity', response.data);
                        vm.name = '';
                    })
                    .catch(error => {
                        switch(error.response.status){
                            case 401: alert(unauthorizedErrorMessage); signOut(); break;
                            case 403: alert(forbiddenErrorMessage); signOut(); break;
                            case 500: pushInternalServerError(); break;
                        }
                    });
            }
        }
    },
    computed: {
        nameValid(){
            return this.name != '';
        }
    }
});
 
var app = new Vue({
    el: "#app",
    data:{
        selectedAmenity : null,
        items : [],
        backupName: ''
    },
    mounted(){
        let jwt = window.localStorage.getItem('jwt');
        if(!jwt)
        jwt='';
        
        const vm = this;
        
        this.$root.$on('addedAmenity', (amenity) => {
            vm.items.push(amenity);
        });

        axios
            .get('rest/vazduhbnb/amenities',{
                headers:{
                    'Authorization': 'Bearer ' + jwt
                }
            })
            .then(response =>{
                vm.items = response.data;  
            })
            .catch(function(error){
                switch(error.response.status){
                    case 500:
                        pushInternalServerError();
                        break;
                }
            });
    },
    methods: {
        editAmenity(){
            let jwt = window.localStorage.getItem('jwt');
            if(!jwt)
                jwt='';

            const vm = this;

            axios
                .put('rest/vazduhbnb/amenity', this.selectedAmenity, {
                    headers: {
                        "Authorization" : "Bearer " + jwt
                    }
                })
                .then(response => {
                    pushSuccessNotification('Success!','Amenity saved successfully!');
                    vm.selectedAmenity = null;
                })
                .catch(error => {
                    switch(error.response.status){
                        case 401: alert(unauthorizedErrorMessage); signOut(); break;
                        case 403: alert(forbiddenErrorMessage); signOut(); break;
                        case 500: pushInternalServerError(); break;
                    }
                });
        },
        deleteAmenity(){
            let jwt = window.localStorage.getItem('jwt');
            if(!jwt)
                jwt='';

            const vm = this;

            axios
                .delete('rest/vazduhbnb/amenity', {
                    headers: {
                        "Authorization" : "Bearer " + jwt
                    },
                    data: this.selectedAmenity
                })
                .then(response => {
                    pushSuccessNotification('Success!','Amenity deleted successfully!');
                    let index = vm.items.findIndex(am => am.id == vm.selectedAmenity.id);
                    if(index != -1)
                        vm.items.splice(index, 1);

                    vm.selectedAmenity = null;
                })
                .catch(error => {
                    switch(error.response.status){
                        case 401: alert(unauthorizedErrorMessage); signOut(); break;
                        case 403: alert(forbiddenErrorMessage); signOut(); break;
                        case 500: pushInternalServerError(); break;
                    }
                });
        },
        cancel(){
            this.selectedAmenity.name = this.backupName;
            this.selectedAmenity = null;
        }
    },
    watch: {
        selectedAmenity(newValue, oldValue){
            if(newValue != null){
                this.backupName = newValue.name;
            }
        }
    }
})