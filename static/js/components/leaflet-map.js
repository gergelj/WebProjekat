// dependency: MKNotification, notification.js

Vue.component("leaflet-map", {
    props: ['mode', 'value', 'height'],    // mode: [input, display], value: Object{lat, lng}, height - map height in px
    data: function(){
        return {
            //latitude : 0,
            //longitude : 0,
            map : {},
            popup : {},
            popupMessage: "Apartment's location"
        }
    },
    template: `
<div>
    <div id="mapid" v-bind:style="mapStyle">

    </div>
    <b-button v-if="mode=='input'" variant="outline-primary" class="mt-2" @click="locateMe">
        <i class="fas fa-map-marker-alt"></i>
        Locate me
    </b-button>
</div>
`
    ,
    methods:{
        onMapClick : function(e) {
            this.changeLocation(e.latlng);
        },
        onLocationFound : function(e) {
            var radius = e.accuracy;

            L.circle(e.latlng, radius).addTo(this.map);

            this.changeLocation(e.latlng);
            this.map.stopLocate();
        },
        onLocationError : function(e) {
            this.map.stopLocate();
            pushErrorNotification("Geolocation error", "Please allow location access in your browser.");
        },
        locateMe : function(){
            this.map.locate({setView: true, maxZoom: 16});
        },
        changeLocation : function(latlng){
            this.value = latlng;
            this.$emit('input', latlng);
        }
    },
    mounted(){
        this.popup = L.popup({closeButton: false});

        if(this.mode == "input"){
            this.map = L.map('mapid').setView([45.2009537, 20.5291344], 8);
        }
        else{ // mode == display
            this.map = L.map('mapid', {closePopupOnClick : false, center:[this.value.lat, this.value.lng], zoom:16}).setView([this.value.lat, this.value.lng], 18);
            this.popup
            .setLatLng(this.value)
            .setContent(this.popupMessage)
            .openOn(this.map);
        }

        L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
            attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
            maxZoom: 18,
            id: 'mapbox/streets-v11',
            tileSize: 512,
            zoomOffset: -1,
            accessToken: 'pk.eyJ1Ijoia2dlcmcxMyIsImEiOiJja2RqZ2FzN2kwZWkyMzJxeGRreGMwa29iIn0.w0UuIKgvCBXBNKZzx4tobg'
        }).addTo(this.map);


        if(this.mode == 'input'){
            this.map.on('click', this.onMapClick);
            this.map.on('locationfound', this.onLocationFound);
            this.map.on('locationerror', this.onLocationError);
        }

    },
    watch: {
        value(newValue, oldValue){
            this.popup
                .setLatLng(newValue)
                .setContent(this.popupMessage)
                .openOn(this.map);
        }
    },
    computed:{
        mapStyle:function(){
            return {"height" : this.height + "px"};
        }
    }
});