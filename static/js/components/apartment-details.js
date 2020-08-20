//dependencies: picture-viewer, leaflet-map
//events: 
//      disapprove-comment-event
//      approve-comment-event

Vue.component("apartment-details", {
    props:['apartment', 'mode'],    //apartment - selected Apartment, mode - userType [undefined, guest, host, admin]
    data: function(){
        return {
            noCommentsMessage: "No comments to show."
        }
    },
    template:`
<div>
    <b-row class="mt-2">
        <b-col md="6">
            <!-- map of the location -->
            <b-container>
                <leaflet-map mapid="mapid-details" mode="display" v-model="latlng" height="300"></leaflet-map>
            </b-container>
        </b-col>
        <b-col md="6">
            <!-- picture-viewer -->
            <b-container>
                <picture-viewer v-model="pictures" height="300" emptymessage="No pictures"></picture-viewer>
            </b-container>
        </b-col>
    </b-row>

    <b-row class="mt-2">
        <b-col md="6">
            <!-- Apartment info -->
            <h3>{{apartment.name}}</h3>
            <h4>{{apartment.location.address.street}} {{apartment.location.address.houseNumber}}</h4>
            <h5>{{apartment.location.address.postalCode}} {{apartment.location.address.city}}</h5>
            <em>Lat: {{apartment.location.latitude.toFixed(6)}}, Long: {{apartment.location.longitude.toFixed(6)}}</em>
            <h4 class="mt-3">Host: <span><b-badge variant="dark">{{apartment.host.name}} {{apartment.host.surname}}</b-badge></span></h4>
      
            <div class="mt-3" style="font-size:20px">
                <span>
                    <b-badge variant="primary">{{apartment.numberOfRooms}}</b-badge>
                </span> {{ roomLabel }} <br>
                <span>
                    <b-badge variant="primary">{{apartment.numberOfGuests}}</b-badge>
                </span> {{ guestLabel }} <br>
                <b-badge variant="primary">{{ apartmentTypeLabel }}</b-badge>
                <b-badge variant="primary">{{ apartment.pricePerNight }}€ / night</b-badge>
            </div>
        </b-col>
        
        <b-col md="6">
            <!-- amenity list -->
            <div style="max-height: 300px;
                        overflow-y:auto;
                        -webkit-overflow-scrolling: touch;">
                <b-list-group v-for="amenity in apartment.amenities" v-bind:key="amenity.id">
                    <b-list-group-item> {{ amenity.name }} </b-list-group-item>
                </b-list-group>
            </div> 
        </b-col>
    </b-row>

    <b-row class="mt-3">

        <b-col md="12">
            <h4>Comments ({{comments.length}})</h4>
            <div v-if="comments.length == 0">
                {{noCommentsMessage}}
            </div>

            <div style="max-height: 300px;
                        overflow-y:auto;
                        -webkit-overflow-scrolling: touch;">
                <!-- for comment in comments -->
                <template v-for="comment in comments" v-bind:key="comment.id">
                    <b-card header-tag="header" footer-tag="footer" class="mb-2">
                        <template v-slot:header>
                            <b-row align-h="between" align-v="center">
                                <b-col cols="auto">
                                    {{comment.user.name}} {{comment.user.surname}} <br>
                                </b-col>
                                <b-col cols="auto">
                                    <b-form-rating v-bind:value="comment.rating" variant="warning" class="mb-2 mt-2" inline readonly></b-form-rating>
                                
                                    <template v-if="mode == 'admin'">
                                        <b-badge v-if="!comment.approved" variant="danger">Disapproved</b-badge>
                                        <b-badge v-if="comment.approved" variant="success">Approved</b-badge>
                                    </template>
                                    
                                    <template v-if="mode == 'host'">
                                        <b-button v-if="comment.approved" variant="danger" @click="approveComment(comment, false)">Disapprove</b-button>
                                        <b-button v-if="!comment.approved" variant="success" @click="approveComment(comment, true)">Approve</b-button>
                                    </template>
                                </b-col>
                            </b-row>
                        </template>
                      
                        {{comment.text}}
        
                    </b-card> 
                </template>
            </div>
        </b-col>
    </b-row>

</div>
    `,
    methods:{
        approveComment(comment, toApprove){
            let jwt = window.localStorage.getItem('jwt');
            if(!jwt)
                jwt = '';

            axios
                .put("rest/vazduhbnb/approve", comment, {
                    params: {
                        approve: toApprove,
                        apartment: this.apartment.id
                    },
                    headers:{
                        'Authorization': 'Bearer ' + jwt
                    }
                })
                .then(function(response){
                    pushSuccessNotification("Success", comment.user.name + " " + comment.user.surname + "'s comment is " + (toApprove ? "approved" : "disapproved") + ".");
                    comment.approved = toApprove;
                })
                .catch(function(error){
                    let response = error.response;
                    switch(response.status){
                        case 401: alert("Error. Not logged in."); signOut(); break;
                        case 403: alert("Access denied. Please login with privileges."); signOut(); break; 
                        case 500: pushErrorNotification("Internal Server Error", "Please try again later."); break;
                    }
                });
        }
    },
    computed:{
        pictures:function(){
            if(this.apartment){
                let pics = this.apartment.pictures.map(p => p.name);
                return pics;
            }
            else{
                return [];
            }
        },
        comments:function(){
            if(this.mode == 'admin' || this.mode == 'host')
                return this.apartment.comments;
            else{
                return this.apartment.comments.filter(c => c.approved);
            }
        },
        guestLabel:function(){
            if(this.apartment.numberOfGuests > 1) return "guests";
            else if(this.apartment.numberOfGuests == 1) return "guest";
            else return '';
        },
        roomLabel:function(){
            if(this.apartment.numberOfRooms > 1) return "rooms";
            else if(this.apartment.numberOfRooms == 1) return "room";
            else return '';
        },
        apartmentTypeLabel:function(){
            if(this.apartment.apartmentType == 'fullApartment') return "Full Apartment";
            else return "Room";
        },
        latlng:function(){
            return {lat: this.apartment.location.latitude, lng: this.apartment.location.longitude};
        }
    }
});