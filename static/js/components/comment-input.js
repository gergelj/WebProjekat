Vue.component('comment-input', {
    props:['value', 'id'],
    data:function(){
        return {
            text: '',
            rating: 0,

            textValid:false,
            ratingValid:false
        }
    },
    methods:{
        onSend(){
            if(this.dataValid){
                let jwt = window.localStorage.getItem('jwt');
                if(!jwt)
                    jwt = '';

                const vm = this;

                axios
                    .post('rest/vazduhbnb/comment', {reservationId: this.value.id, rating: this.rating, text: this.text}, {
                        headers:{
                            "Authorization" : "Bearer " + jwt
                        }
                    })
                    .then(function(response){
                        pushSuccessNotification("Success", "Comment posted successfully");
                        vm.value.comment = response.data;
                    })
                    .catch(function(error){
                        let response = error.response;
                        switch(response.status){
                            case 400: pushErrorNotification("Error", response.data.message); break;
                            case 401: alert(unauthorizedErrorMessage); signOut(); break;
                            case 403: alert(forbiddenErrorMessage); signOut(); break;
                            case 500: pushInternalServerError(); break;
                        }
                    })
            }
        },
        getTextAreaId(){
            return "comment-text-" + this.id;
        },
        getRatingId(){
            return "rating-input-" + this.id;
        }
    },
    computed:{
        textValidation(){
            if(this.text.trim() == ''){
                this.textValid = false;
                return false;
            }
            else{
                this.textValid = true;
                return true;
            }
        },
        ratingValidation(){
            if(this.rating == 0){
                this.ratingValid = false;
                return false;
            }
            else{
                this.ratingValid = true;
                return true;
            }
        },
        dataValid(){
            return this.ratingValid && this.textValid;
        }
    },
    template:`
<div>
    <b-form-group
        label-cols-sm="2"
        label-cols-lg="2"
        label="Comment"
        :label-for="getTextAreaId()"
        :state="textValidation">
        
        <b-form-textarea :id="getTextAreaId()"
            v-model="text"
            placeholder="Enter comment..."

            :state="textValidation">
        </b-form-textarea>
    </b-form-group>
  
    <b-form-group
        label-cols-sm="2"
        label-cols-lg="2"
        label="Rating"
        :label-for="getRatingId()"
        :state="ratingValidation">
      <b-form-rating :id="getRatingId()" v-model="rating" variant="warning" :state="ratingValidation" inline></b-form-rating>
	</b-form-group>
  
    <b-row align-h="end" class="mt-2">
        <b-col cols="auto">
            <b-button :disabled="!dataValid" @click="onSend" variant="primary">Send</b-button>
        </b-col>
	</b-row>
 
</div>`
});