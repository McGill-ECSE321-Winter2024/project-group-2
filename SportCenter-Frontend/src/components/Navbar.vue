<template>
    <!-- Navigation bar container -->
    <nav>
        <!-- Home link with logo image -->
        <router-link to='/'>
            <img src='@/assets/logo.png' alt="Logo" class="logo" />
        </router-link>
        <!-- Conditionally rendered links based on user's login status -->
        <!-- Displays 'View Sessions' for logged-out users and 'Register for a Session' for logged-in users -->
        <router-link v-if="!isLoggedIn" to='/Sessions'>View Sessions</router-link>
        <!-- Login/Signup link for logged-out users -->
        <router-link v-else to='/Sessions'>Register for a Session</router-link>
        <router-link v-if="!isLoggedIn" to='/Login'>Login or Sign up</router-link>
        <!-- 'My Account' link for logged-in users -->
        <router-link v-if="isLoggedIn" to='/MyAccount'>My Account</router-link>
        <<!-- Owner Dashboard link visible only to owners -->
        <router-link v-if="isOwner" to='/Owner'>Owner Dashboard</router-link>
        <!-- Logout button for logged-in users -->
        <button class="logout" v-if="isLoggedIn" @click="logout()">Logout</button>
    </nav>
</template>

<script>
export default {
    name: 'Navbar',
    data() {
        return {
            isLoggedIn: false, // Tracks if a user is logged in
            isOwner: false // Tracks if the logged-in user is an owner
        };
    },
    created() {
        // Initial check for login status when component is created
        this.checkLoginStatus();
    },
    mounted() {
        // Recheck login status when component is mounted to DOM
        this.checkLoginStatus();
    },
    watch: {
        // Watcher on Vue Router route changes to recheck login status
        '$route': 'checkLoginStatus'
    },
    methods: {
        checkLoginStatus() {
            // Updates isLoggedIn and isOwner based on sessionStorage values
            let personId = sessionStorage.getItem('personId');
            if (personId === null) {
                this.loggedIn = false;
            }
            else {
            this.isLoggedIn = sessionStorage.getItem('personId')!== '-1';
            }
            this.isOwner = sessionStorage.getItem('roleId') === '0';
        },
        logout() {
            console.log("BEFORE")
            console.log(sessionStorage.getItem('personId'));
            console.log(sessionStorage.getItem('customerVsInstructor'));
            console.log(sessionStorage.getItem('roleId'));
            // Clears sessionStorage items related to user status and navigates to home page
            sessionStorage.setItem('personId',-1);
            sessionStorage.setItem('customerVsInstructor',-1);
            sessionStorage.setItem('roleId',-1);
            // Update component data to reflect logged-out status
            console.log("AFTER")
            console.log(sessionStorage.getItem('personId'));
            console.log(sessionStorage.getItem('customerVsInstructor'));
            console.log(sessionStorage.getItem('roleId'));
            this.isLoggedIn = false;
            this.isOwner = false;
            // Redirect to home page
            this.$router.push('/');
        }
    }
}
</script>

<style scoped>
/* Styles specific to the Navbar component */

.logo {
    height: 25px;
}
nav {
    display: flex;
    justify-content: space-between;
    position: fixed;
    top: 0;
    width: 100%;
    background-color: #616160;
    padding: 10px 10px 10px;
    z-index: 1000;
}

.nav-links {
    display: flex;
    gap: 10px;
}

nav a {
    color: #e0e0e0;
}

nav a:hover {
    color: #85b3c9;
}
.login-link {
    margin-left: auto;
}
button.logout {
    background-color: #f44336; /* Red */
    border: none;
    color: white;
    text-align: center;
    text-decoration: none;
    display: inline-block;

    cursor: pointer;
    transition-duration: 0.4s;
}

button.logout:hover {
    background-color: #da190b;
    color: white;
}
</style>
