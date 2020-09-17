Vue.component("navigation-bar", {
    data: function(){
        return {
            usertype : "",
            username : ""
        }
    },
    template: `
<div>
    <b-navbar toggleable="md" type="dark" variant="danger" fixed="top">
      <b-navbar-brand href="index.html">
          <img src="images/logo-white.png" alt="vazduhbnb" height="30px">
      </b-navbar-brand>
  
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
  
      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item href="index.html">Home</b-nav-item>
          <b-nav-item href="about.html">About</b-nav-item>
          <b-nav-item href="contact.html">Contact</b-nav-item>
        </b-navbar-nav>
        
        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
            
            <!-- Not logged in user -->
            <template v-if="usertype == ''">
                <b-nav-item href="login.html">
                    <i class="fas fa-sign-in-alt"></i>
                    Log In
                </b-nav-item>

                <b-nav-item href="signup.html">
                    <i class="fas fa-star"></i>
                    Sign Up
                </b-nav-item>
            </template>

            <!-- Guest user -->
            <template v-if="usertype == 'guest'">
                <b-nav-item href="reservation-list.html">
                    <i class="fas fa-calendar-alt"></i>
                    My Reservations
                </b-nav-item>
            </template>

            <!-- Host user -->
            <template v-if="usertype == 'host'">

                <b-nav-item href="new-apartment.html">
                    <i class="fas fa-home"></i>
                    New Apartment
                </b-nav-item>

                <b-nav-item href="users.html">
                    <i class="fas fa-users"></i>
                    My guests
                </b-nav-item>

                <b-nav-item href="reservation-list.html">
                    <i class="fas fa-calendar-alt"></i>
                    Reservations
                </b-nav-item>
            </template>

            <!-- Admin user -->
            <template v-if="usertype == 'admin'">

                <b-nav-item href="calendar.html">
                    <i class="fas fa-tags"></i>
                    Calendar
                </b-nav-item>

                <b-nav-item href="amenities.html">
                    <i class="fas fa-couch"></i>
                    Amenities
                </b-nav-item>

                <b-nav-item href="users.html">
                    <i class="fas fa-users"></i>
                    Users
                </b-nav-item>

                <b-nav-item href="reservation-list.html">
                    <i class="fas fa-calendar-alt"></i>
                    Reservations
                </b-nav-item>

            </template>
  
            <b-nav-item-dropdown right v-if="usertype == 'guest' || usertype == 'host' || usertype == 'admin'">
                <template v-slot:button-content>
                    <i class="fas fa-user"></i>
                    {{username}}
                </template>
                <b-dropdown-item href="profile.html">Profile</b-dropdown-item>
                <b-dropdown-item href="javascript:signOut();">Sign Out</b-dropdown-item>
            </b-nav-item-dropdown>     

        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
</div>
`
    ,
    methods:{},
    mounted(){

        let username = window.localStorage.getItem("username");
        if(!username)
            username = "";

        this.username = username;

        let usertype = window.localStorage.getItem("usertype");
        if(!usertype)
            usertype = "";

        this.usertype = usertype;

    }
});

function signOut(){
    window.localStorage.removeItem("jwt");
    window.localStorage.removeItem("username");
    window.localStorage.removeItem("usertype");
    window.location.replace("login.html");
}