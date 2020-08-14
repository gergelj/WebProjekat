Vue.component("admin-view",{
    data: function()
    {
        return{
            items:[],
            fields:[
                {key: 'account.username', label: 'Username', sortable: true },
                {key: 'name', sortable: true},
                {key: 'surname', sortable: true},
                {key: 'gender', sortable: true,
                formatter: value=>{
                    return value.charAt(0).toUpperCase() + value.slice(1);
                }},
                {key: 'userType', sortable: true,
                formatter: value=>{
                    return value.charAt(0).toUpperCase() + value.slice(1);
                }},
                {key: 'blocked', sortable: true,
                formatter: value=>{
                    return value? 'Yes':'No';
                }}
            ],
            selectedUser: {},
            loggedinUser: {}
        }
    },
    template:`
    <div>
    <b-table bordered 
        v-if='this.loggedinUser.userType == "admin" || this.loggedinUser.userType == "host"'
        striped hover
        :items="items"
        :fields="fields"
        @row-clicked="rowClicked"
        select-mode="single"
    >  
    </b-table>
    </div>
    `,
    mounted()
    {
        let jwt = window.localStorage.getItem('jwt');
        if(!jwt)
            jwt='';

        axios
            .get('rest/vazduhbnb/getAllUsers',{
                headers: {'Authorization': 'Bearer '+jwt}
            })
            .then(response =>{
                this.items = response.data;
            })
            .catch(function(error){
                switch(error.response.status)
                {
                    case 500:
                        pushErrorNotification('Interval server error','Please try again later')
                        break;
                }
            })
        
        axios
            .get('rest/vazduhbnb/getLoggedinUser',{
                headers: {'Authorization': 'Bearer '+jwt}
            })
            .then(response =>{
                this.loggedinUser = response.data;
                if(this.loggedinUser == null)
                {
                    window.location.replace('login.html');

                }
                if(this.loggedinUser.userType == 'guest' || this.loggedinUser.userType == 'undefined')
                {
                    window.location.replace('login.html');
                }
            })

        this.$root.$on('deleteUser', (deletedUserId)=>{
            let index = this.items.findIndex(i => i.id == deletedUserId);
            if(index > -1)
            {
                this.items.splice(index, 1);
            }
            app.$forceUpdate();
        });

        this.$root.$on('blockUser',(blockedUser)=>{
            let index = this.items.findIndex(i => i.id == blockedUser.id);
            if(index > -1)
            {
                this.items[index].blocked = true;
            }

            app.$forceUpdate();
        });

        this.$root.$on('unblockUser', (unblockedUser)=>{
            let index = this.items.findIndex(i => i.id == unblockedUser.id);
            if(index > -1)
            {
                this.items[index].blocked = false;
            }

            app.$forceUpdate();
        });
            
    },
    methods:
    {
        rowClicked(record, index) {
            this.selectedUser = record;
            this.$root.$emit('selectedUser', this.selectedUser);
          }
    }
});

Vue.component('name-form',{
    data: function(){
        return{
            selectedUserAccount: {},
            selectedUser: {},
            loggedinUser: {}
        }
    },
    template:`
    <div id="selected-form">
    <b-form v-if='this.loggedinUser.userType == "admin" || this.loggedinUser.userType == "host"'>
        <b-form-group
            id="input-group-1"
            label="Selected user:"
            label-for="input-1">
            <b-form-input
                id="input-1"
                type="text"
                v-model='selectedUserAccount.username'
                placeholder="Selected user will be shown here">
            </b-form-input>
        </b-form-group>
        <div v-if='this.loggedinUser.userType == "admin"'>
            <b-button
            @click='deleteUser'
            class='mr-1'
            pill
            variant='danger'>Delete</b-button>

            <b-button
            @click='blockUser'
            class='mr-1'
            pill
            variant='danger'>Block</b-button>

            <b-button
            @click='unblockUser'
            class='mr-1'
            pill
            variant='success'>Unblock</b-button>
        </div>
    </b-form>
    </div>
    `,
    mounted(){
        this.$root.$on('selectedUser', (selectedUser)=>{
            this.selectedUserAccount = selectedUser.account;
            this.selectedUser = selectedUser;
        });

        let jwt = window.localStorage.getItem('jwt');
        if(!jwt)
            jwt='';
        
        axios
            .get('rest/vazduhbnb/getLoggedinUser',{
                headers: {'Authorization': 'Bearer '+jwt}
            })
            .then(response =>{
                this.loggedinUser = response.data;
            })


    },
    methods:
    {
        deleteUser: function()
        {
            if(this.selectedUser == null)
            {
                pushErrorNotification('Error!','You must select user first!');
                return;
            }

            let deletedUserId = this.selectedUser.id;

            axios 
                .put('rest/vazduhbnb/deleteUser', deletedUserId)
                .then(response =>{
                    pushSuccessNotification('Success!', 'User deleted successfully!');
                    this.$root.$emit('deleteUser', deletedUserId);
                    this.selectedUser = {};
                })
        },
        blockUser: function()
        {
            if(this.selectedUser == null)
            {
                pushErrorNotification('Error!','You must select user first!');
                return;
            }

            axios 
                .put('rest/vazduhbnb/blockUser', this.selectedUser)
                .then(response => {
                    if(!this.selectedUser.blocked)
                    {
                        pushSuccessNotification('Success!', 'User blocked successfully!');
                        this.$root.$emit('blockUser', this.selectedUser);
                    }
                    else
                    {
                        pushErrorNotification('Error!','This user is already blocked!');
                    }
                    
                })
                .catch(function(error){
                    switch(error.response.status)
                    {
                        case 500:
                            pushErrorNotification('Interval server error','Please try again later')
                            break; 
                    }
                })

        },
        unblockUser: function()
        {
            if(this.selectedUser == null)
            {
                pushErrorNotification('Error!','You must select user first!');
                return;
            }

            axios
                .put('rest/vazduhbnb/unblockUser', this.selectedUser)
                .then(response =>{
                    if(this.selectedUser.blocked)
                    {
                        pushSuccessNotification('Success!', 'User unblocked successfully!');
                        this.$root.$emit('unblockUser', this.selectedUser);
                    }
                    else
                    {
                        pushErrorNotification('Error!','This user is not blocked!');
                    }
                })
                .catch(function(error){
                    switch(error.response.status)
                    {
                        case 500:
                            pushErrorNotification('Interval server error','Please try again later')
                            break; 
                    }
                })
        }
    }
})

var app = new Vue({
    el: '#users'
});