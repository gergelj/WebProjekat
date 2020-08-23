Vue.component("reservation-list-item", {
    props:['reservation', 'userType'],
    data:function(){
        return {

        }
    },
    template:`
<div>
    <b-card no-body class="mb-2">
        <b-row no-gutters>
            <b-col cols="auto">
                <b-button variant="transparent"><b-img :src=reservation.apartment.pictures[0].name height="200"></b-img></b-button>
            </b-col>
    
            <b-col>
                <b-card-body>
                    <b-row align-h="between">
                        <b-col md="10">
                            <b-row class="mb-2">
                                <b-col><h3>{{title}}</h3></b-col>
                            </b-row>
                            <b-row class="mb-2" v-if="userType == 'host' || userType == 'admin'">
                                <b-col><h4>{{reservation.apartment.name}}</h4></b-col>
                            </b-row>
                            <b-row class="mb-2">
                                <b-col>Check in: <b-badge v-b-tooltip title="Check in date and time" variant="info">{{reservation.checkIn | formatDate('DD.MM.YYYY')}} {{reservation.apartment.checkInHour}}h</b-badge></b-col>
                            </b-row>
                            <b-row class="mb-2">
                                <b-col>Check out: <b-badge v-b-tooltip title="Check out date and time" variant="info">{{checkOutDate | formatDate('DD.MM.YYYY')}} {{reservation.apartment.checkOutHour}}h</b-badge></b-col>
                            </b-row>
                            <b-row class="mb-2">
                                <b-col><b-badge>{{reservation.nights}} nights</b-badge></b-col>
                            </b-row>
                            <b-row class="mb-2">
                                <b-col><h5><strong>total: </strong> <b-badge>{{reservation.totalPrice}}€</b-badge></h5></b-col>
                            </b-row>

                        </b-col>
                        <b-col md="auto">
                            <b-row class="mb-3" align-h="end">
                                <b-col v-if="reservation.reservationStatus == 'created'" cols="auto"><b-badge variant="primary" pill>CREATED</b-badge></b-col>
                                <b-col v-if="reservation.reservationStatus == 'rejected'" cols="auto"><b-badge variant="danger" pill>REJECTED</b-badge></b-col>
                                <b-col v-if="reservation.reservationStatus == 'cancelled'" cols="auto"><b-badge variant="warning" pill>CANCELLED</b-badge></b-col>
                                <b-col v-if="reservation.reservationStatus == 'accepted'" cols="auto"><b-badge variant="success" pill>ACCEPTED</b-badge></b-col>
                                <b-col v-if="reservation.reservationStatus == 'finished'" cols="auto"><b-badge variant="info" pill>FINISHED</b-badge></b-col>
                            </b-row>
                            <b-row v-if="userType == 'guest' && (reservation.reservationStatus == 'created' || reservation.reservationStatus == 'accepted')" class="mb-2" align-h="end">
                                <b-col cols="auto"><b-button @click="onCancel" variant="danger">Cancel</b-button></b-col>
                            </b-row>
                            <template v-if="userType == 'host'">
                                <b-row v-if="reservation.reservationStatus == 'created' || reservation.reservationStatus == 'accepted'" class="mb-2" align-h="end">
                                    <b-col cols="auto"><b-button @click="onReject" variant="danger">Reject</b-button></b-col>
                                </b-row>
                                <b-row v-if="reservation.reservationStatus == 'created'" class="mb-2" align-h="end">
                                    <b-col cols="auto"><b-button @click="onAccept" variant="success">Accept</b-button></b-col>
                                </b-row>
                                <b-row v-if="(reservation.reservationStatus == 'created' || reservation.reservationStatus == 'accepted') && isCompleted" class="mb-2" align-h="end">
                                    <b-col cols="auto"><b-button @click="onFinish" variant="info">Finish</b-button></b-col>
                                </b-row>
                            </template>
                        </b-col>
                    </b-row>
                    <b-row align-h="end">
                        <b-col cols="auto">
                            <b-button v-if="reservation.comment.id > 0 || reservation.message != ''" v-b-toggle="'collapse-' + reservation.id" variant="light"><i class="fas fa-comment-alt"></i> Notes and comments</b-button>
                        </b-col>
                    </b-row>
                </b-card-body>
            </b-col>
        </b-row>

        <b-container class="mb-2">
            <comment-input v-if="reservation.comment.id == 0 && userType == 'guest' && (reservation.reservationStatus == 'rejected' || reservation.reservationStatus == 'finished')" v-model="reservation"></comment-input>
        </b-container>

        <b-collapse :id="'collapse-' + reservation.id">
            <b-container class="mt-2 mb-2" fluid>
                <template v-if="reservation.message != ''">
                    <b-row class="mb-2">
                        <b-col cols="auto"><h5>Guest's note</h5></b-col>
                    </b-row>
                    <b-row>
                        <b-col><b-form-textarea :value="reservation.message" rows="3" plaintext></b-form-textarea></b-col>
                    </b-row>
                </template>

                <template v-if="reservation.comment.id > 0">
                    <hr>
                    <b-row class="mb-2" align-h="between" align-v="center">
                        <b-col cols="auto"><h5>Comment</h5></b-col>
                        <b-col sm="auto">
                            <b-form-rating variant="warning" readonly inline v-model="reservation.comment.rating"></b-form-rating>
                            <b-badge v-if="reservation.comment.approved" variant="success">Approved</b-badge>
                            <b-badge v-if="!reservation.comment.approved" variant="danger">Not approved</b-badge>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col col>
                            <b-form-textarea :value="reservation.comment.text" rows="3" plaintext></b-form-textarea>
                        </b-col>
                    </b-row>
                </template>
            </b-container>
        </b-collapse>
</b-card>
</div>
    `,
    mounted(){
        console.log(this.userType);
    },
    methods:{
        onCancel(){
            let jwt = window.localStorage.getItem('jwt');
            if(!jwt)
                jwt = '';

            const vm = this;

            axios
                .put('rest/vazduhbnb/cancelReservation', this.reservation.id, {
                    headers:{
                        "Authorization" : "Bearer " + jwt
                    }
                })
                .then(function(response){
                    pushSuccessNotification("Success", "Reservation has been cancelled");
                    vm.reservation.reservationStatus = 'cancelled';
                })
                .catch(function(error){
                    let response = error.response;
                    switch(response.status){
                        case 401: alert("User not logged in"); signOut(); break;
                        case 403: alert("Please login with privileges"); signOut(); break;
                        case 400: pushErrorNotification("Error occured", response.data.message); break;
                        case 500: pushErrorNotification("Internal Server Error", "Please try again later"); break;
                    }
                });
        },
        onReject(){
            let jwt = window.localStorage.getItem('jwt');
            if(!jwt)
                jwt = '';

            const vm = this;

            axios
                .put('rest/vazduhbnb/rejectReservation', this.reservation.id, {
                    headers:{
                        "Authorization" : "Bearer " + jwt
                    }
                })
                .then(function(response){
                    pushSuccessNotification("Success", "Reservation has been rejected");
                    vm.reservation.reservationStatus = 'rejected';
                })
                .catch(function(error){
                    let response = error.response;
                    switch(response.status){
                        case 401: alert("User not logged in"); signOut(); break;
                        case 403: alert("Please login with privileges"); signOut(); break;
                        case 400: pushErrorNotification("Error occured", response.data.message); break;
                        case 500: pushErrorNotification("Internal Server Error", "Please try again later"); break;
                    }
                });
        },
        onAccept(){
            let jwt = window.localStorage.getItem('jwt');
            if(!jwt)
                jwt = '';

            const vm = this;

            axios
                .put('rest/vazduhbnb/acceptReservation', this.reservation.id, {
                    headers:{
                        "Authorization" : "Bearer " + jwt
                    }
                })
                .then(function(response){
                    pushSuccessNotification("Success", "Reservation has been accepted");
                    vm.reservation.reservationStatus = 'accepted';
                })
                .catch(function(error){
                    let response = error.response;
                    switch(response.status){
                        case 401: alert("User not logged in"); signOut(); break;
                        case 403: alert("Please login with privileges"); signOut(); break;
                        case 400: pushErrorNotification("Error occured", response.data.message); break;
                        case 500: pushErrorNotification("Internal Server Error", "Please try again later"); break;
                    }
                });
        },
        onFinish(){
            let jwt = window.localStorage.getItem('jwt');
            if(!jwt)
                jwt = '';

            const vm = this;

            axios
                .put('rest/vazduhbnb/finishReservation', this.reservation.id, {
                    headers:{
                        "Authorization" : "Bearer " + jwt
                    }
                })
                .then(function(response){
                    pushSuccessNotification("Success", "Reservation has been finished");
                    vm.reservation.reservationStatus = 'finished';
                })
                .catch(function(error){
                    let response = error.response;
                    switch(response.status){
                        case 401: alert("User not logged in"); signOut(); break;
                        case 403: alert("Please login with privileges"); signOut(); break;
                        case 400: pushErrorNotification("Error occured", response.data.message); break;
                        case 500: pushErrorNotification("Internal Server Error", "Please try again later"); break;
                    }
                });
        }
    },
    computed:{
        title(){
            return this.userType == 'guest' ? this.reservation.apartment.name : ('@' + this.reservation.guest.account.username);
        },
        checkOutDate(){
            return new Date(this.reservation.checkIn.getTime() + 1000*3600*24*parseInt(this.reservation.nights));
        },
        isCompleted(){
            return this.checkOutDate.getTime() < new Date().getTime();
        }
    },
    filters:{
        formatDate:function(value, format){
            if(value)
                return moment(value).format(format);
            else
                return  '';
        }
    }
});