Vue.component("users-view",{
    props:['userType'],
    data: function() {
        return {
            items:[],
            filter: {
                username: '',
                gender: null,
                userType: null
            },
            fields:[
                {key: 'account.username', label: 'Username', sortable: true },
                {key: 'name', sortable: true},
                {key: 'surname', sortable: true},
                {key: 'gender', sortable: true,
                class:'d-none d-md-table-cell',
                formatter: value=>{
                    return value.charAt(0).toUpperCase() + value.slice(1);
                }},
                {key: 'userType', sortable: true,
                class:'d-none d-md-table-cell',
                formatter: value=>{
                    return value.charAt(0).toUpperCase() + value.slice(1);
                }},
                { key: 'block', label: '' }
            ],
            selectedUser: {},
            loggedInUser: null,
            transProps:{
                name: 'flip-list'
            }
        }
    },
    template:`
<div>
    <b-container fluid>
        <b-table bordered 
            v-if='userType == "admin" || userType == "host"'
            striped hover
            :items="filteredUsers"
            :fields="fields"
            @row-clicked="rowClicked"
            select-mode="single"
        >
            <template v-slot:cell(block)="data">
                <template v-if="userType == 'admin'">
                    <b-button
                        align="center"
                        @click='blockUser(data.item)'
                        v-if="!data.item.blocked"
                        variant='danger'>Block</b-button>
                    <b-button
                        align="center"
                        @click='unblockUser(data.item)'
                        v-if="data.item.blocked"
                        variant='success'>Unblock</b-button>
                </template>
                <template v-if="userType == 'host'">
                    <b-button
                        disabled
                        v-if="data.item.blocked"
                        variant='danger'>Blocked</b-button>
                </template>
            </template>
        </b-table>
        <b-row align-h="center">
            <b-col cols="auto">
                <h5 v-if="filteredUsers.length == 0">No users found</h5>
            </b-col>
        </b-ro>
    </b-container>
</div>
    `,
    mounted() {
        let jwt = window.localStorage.getItem('jwt');
        if(!jwt)
            jwt='';

        const vm = this;

        axios
            .get("rest/vazduhbnb/getLoggedInUser", {
                headers:{
                    "Authorization" : "Bearer " + jwt
                }
            })
            .then(function(response){
                var loggedInUser = response.data;

                axios
                    .get('rest/vazduhbnb/getAllUsers',{
                        headers: {'Authorization': 'Bearer '+jwt}
                    })
                    .then(response =>{
                       vm.items = vm.removeMyself(response.data, loggedInUser);
                    })
                    .catch(function(error){
                        switch(error.response.status) {
                            case 401: alert(unauthorizedErrorMessage); signOut(); break;
                            case 403: alert(forbiddenErrorMessage); signOut(); break;
                            case 500: pushInternalServerError(); break;
                        }
            });
            });

        this.$root.$on('filterChanged', (wholeFilter) => {
            this.filter = wholeFilter;
        });     
    },
    methods: {
        rowClicked(record, index) {
            this.selectedUser = record;
        },
        removeMyself(userList, myself){
            let index = userList.findIndex(user => user.id == myself.id);
            if(index != -1){
                userList.splice(index, 1);
            }
            return userList;
        },
        blockUser(user){
            let jwt = window.localStorage.getItem('jwt');
            if(!jwt)
                jwt = '';

            axios 
                .put('rest/vazduhbnb/blockUser', user, {
                    headers:{
                        "Authorization" : "Bearer " + jwt
                    }
                })
                .then(response => {
                    pushSuccessNotification('Success!', user.account.username + ' blocked successfully!');
                    user.blocked = true;
                })
                .catch(function(error){
                    switch(error.response.status) {
                        case 400: pushErrorNotification('Error', "Selected user doesn't exist"); break;
                        case 401: alert(unauthorizedErrorMessage); signOut(); break;
                        case 403: alert(forbiddenErrorMessage); signOut(); break;
                        case 500: pushInternalServerError(); break;
                    }
                });
        },
        unblockUser(user){
            let jwt = window.localStorage.getItem('jwt');
            if(!jwt)
                jwt = '';

            axios 
                .put('rest/vazduhbnb/unblockUser', user, {
                    headers:{
                        "Authorization" : "Bearer " + jwt
                    }
                })
                .then(response => {
                    pushSuccessNotification('Success!', user.account.username + ' unblocked successfully!');
                    user.blocked = false;
                })
                .catch(function(error){
                    switch(error.response.status) {
                        case 400: pushErrorNotification('Error', "Selected user doesn't exist"); break;
                        case 401: alert(unauthorizedErrorMessage); signOut(); break;
                        case 403: alert(forbiddenErrorMessage); signOut(); break;
                        case 500: pushInternalServerError(); break;
                    }
                });
        }
    },
    computed: {
       filteredUsers(){
            let selectedGender = this.filter.gender;
            let selectedType = this.filter.userType;
            let selectedUsername = this.filter.username;
            let blocked = this.filter.status;

            let hasGender = this.filter.gender != null;
            let hasUserType = this.filter.userType != null;
            let hasUsername = this.filter.username != '';
            let hasStatus = this.filter.status != null;
            
            return this.items.filter(user => (hasGender ? (user.gender == selectedGender) : true) && (hasUserType ? (user.userType == selectedType) : true) && (hasUsername ? (user.account.username.toUpperCase().indexOf(selectedUsername.trim().toUpperCase()) != -1) : true) && (hasStatus ? (user.blocked == blocked) : true));
       } 
    }
});

Vue.component('user-filter', {
    props:['userType'],
    data: function() {
        return {
            filter:{},
            selectedGender: null,
            genderOption:[
                {value: null, text: 'Select a gender'},
                {value: 'male', text: 'Male'},
                {value: 'female', text: 'Female'},
                {value: 'other', text: 'Other'}
            ],
            selectedType: null,
            typeOption:[
                {value: null, text:'Select a user type'},
                {value: 'admin', text:'Admin'},
                {value: 'guest', text:'Guest'},
                {value: 'host', text:'Host'}
            ],
            selectedStatus: null,
            statusOption:[
                {value: null, text:'Select user status'},
                {value: true, text:'Blocked'},
                {value: false, text:'Not Blocked'}
            ],
            usernameInput: ''
        }
    },
    template:`
<div id="filter-form">
    <b-row>
        <b-col md="6">
            <b-form-group>
                <b-form-input
                    placeholder='Please enter a username'
                    v-model='usernameInput'>
                </b-form-input>
            </b-form-group>

            <b-form-group>
                <b-form-select 
                    id='gender-input' 
                    v-model='selectedGender' 
                    :options='genderOption'>
                </b-form-select>
            </b-form-group>
        </b-col>
        <b-col md="6">
            <b-form-group v-if="userType == 'admin'">
                <b-form-select
                    id='usertype-input'
                    v-model='selectedType'
                    :options='typeOption'>
                </b-form-select>
            </b-form-group>

            <b-form-group>
                <b-form-select
                    id='userstatus-input'
                    v-model='selectedStatus'
                    :options='statusOption'>
                </b-form-select>
            </b-form-group>
        </b-col>
    </b-row>

    <b-row align-h="end">
        <b-col cols="auto">
            <b-button @click="clearFilter" variant="outline-danger">Clear</b-button>
            <b-button
                @click='filterChanged'
                variant='primary'>Search</b-button>
        <b-col>
    </b-row>
</div>
    `,
   methods: {
        filterChanged: function() {
            let wholeFilter = {
                username: this.usernameInput,
                userType: this.selectedType,
                gender: this.selectedGender,
                status: this.selectedStatus
            };

            this.$root.$emit('filterChanged', wholeFilter);
        },
        clearFilter(){
            this.usernameInput = '';
            this.selectedType = null;
            this.selectedGender = null;
            this.selectedStatus = null;

            let wholeFilter = {
                username: '',
                userType: null,
                gender: null,
                status: null
            };

            this.$root.$emit('filterChanged', wholeFilter);
    }
   }
});

var app = new Vue({
    el: '#app',
    data: {
        userType: ''
    },
    mounted(){
        let jwt = window.localStorage.getItem('jwt');
        if(!jwt)
            jwt = '';

        const vm = this;
        axios
            .get("rest/vazduhbnb/userType",{
                headers:{
                    'Authorization': 'Bearer ' + jwt
                }
            })
            .then(function(response){
                vm.userType = response.data;
            }); 
    }
});