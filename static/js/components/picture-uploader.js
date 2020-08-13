Vue.component("picture-uploader", {
    // value - pictures to upload
    props: ['value'],
    data: function(){
        return {
            slide: 0,
            errorMessage : '',
            files: []
        }
    },
    template: `
<div>
    <b-form inline>
        <b-form-file
        v-model="files"
        :state="validation"
        accept="image/*"
                placeholder="Choose a file or drop it here..."
                drop-placeholder="Drop file here..."
                :file-name-formatter="formatNames"
                v-on:change="onFileChange"
                multiple>
        </b-form-file>
        <b-form-invalid-feedback :state="validation">
        {{errorMessage}}
        </b-form-invalid-feedback>

        <b-button variant="danger" v-on:click="removeImage" :disabled="value.length==0">Remove pictures</b-button>
    <b-form>   
</div>
`
    ,
    methods: {
        onFileChange(e) {
            this.value = [];
            var files = e.target.files || e.dataTransfer.files;
            if (!files.length)
                return;
    
            for(file of files){
                this.createImage(file);
            }

            this.$emit('input', this.value);
        },
        createImage(file) {
            var vm = this;

            const fileType = file.type
            const reader = new FileReader();
            reader.onload = function(event){
                var img = new Image();
                img.src = event.target.result;

                img.onload = function() {
                    var elem = document.createElement('canvas');
                    var ctx = elem.getContext('2d');
                    
                    let height = 0;
                    let width = 0;

                    if(img.height < 600){
                        height = img.height;
                        width = img.width;
                    }
                    else{ //resize
                        height = 600;
                        width = img.width * (height / img.height);
                    }
                    
                    elem.width = width;
                    elem.height = height;

                    ctx.drawImage(img, 0, 0, width, height);
                    let data = elem.toDataURL(fileType);
                    vm.value.push(data);
                }
            }
            reader.readAsDataURL(file);
        },
        removeImage: function (e) {
            this.value = [];
            this.files = [];
            this.$emit('input', this.value);
        },
        formatNames(f){
            if (f.length === 1) {
                return f[0].name
            } else {
                return `${f.length} files selected`
            }
        }/*,
        upload() {
            d = {pictures : this.value,
                numberOfRooms : "13"};
            data = JSON.stringify(d);

            axios.post("/rest/vazduhbnb/apartment", data)
                .then(res => {
                    console.log(res);
                });
        }*/
    },
    computed: {
        validation() {
            if(this.files.length == 0){
                this.errorMessage = "No file selected";
                return false;
            }
            else if(this.files.length > 5){
                this.errorMessage = "More than 5 files are selected";
                return false;
            }

            this.errorMessage = "";
            return true;
        }
    }
});