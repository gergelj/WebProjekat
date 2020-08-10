Vue.component("picture-viewer", {
    // value - list of pictures
    // emptymessage - text displayed if no picture is shown
    // height - height of the pictures in px
    props: ['value', 'emptymessage', 'height'],
    data: function(){
        return {
            slide: 0
        }
    },
    template: `
<div>
    <b-carousel
        id="carousel123"
        v-model="slide"
        :interval="0"
        :controls="value.length > 1"
        :indicators="value.length > 1"
        background="#ababab"
        v-bind:style="carouselStyle"
        style="text-shadow: 1px 1px 2px #333;"
    >

        <b-carousel-slide v-for="p in value" :img-src="p"></b-carousel-slide>

        <b-carousel-slide v-if="value.length == 0" :caption="emptymessage" img-blank img-alt="No pictures">
        </b-carousel-slide>
    </b-carousel>
</div>
`,
    methods:{

    },
    mounted(){
        
    },
    computed:{
        carouselStyle: function(){
            return {
                "text-shadow" : "1px 1px 2px #333",
                "height" : this.height + "px",
                "width" : "100%",
                "overflow" : "hidden"
            }
        }
    }
});