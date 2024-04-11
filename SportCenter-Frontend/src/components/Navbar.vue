<template>
    <nav>
        <router-link to='/'>
            <img src='@/assets/logo.png' alt="Logo" class="logo" />
        </router-link>
        <router-link v-if="!isLoggedIn" to='/Sessions'>View Sessions</router-link>
        <router-link v-else to='/Sessions'>Register for a Session</router-link>
        <router-link v-if="!isLoggedIn" to='/Login'>Login or Sign up</router-link>
        <router-link v-if="isLoggedIn" to='/MyAccount'>My Account</router-link>
        <router-link v-if="isOwner" to='/Owner'>Owner Dashboard</router-link>
        <button class="logout" v-if="isLoggedIn" @click="logout()">Logout</button>
    </nav>
</template>

<script>
export default {
    name: 'Navbar',
    data() {
        return {
            isLoggedIn: false,
            isOwner: false
        };
    },
    created() {
        this.checkLoginStatus();
    },
    mounted() {
        this.checkLoginStatus();
    },
    watch: {
        '$route': 'checkLoginStatus'
    },
    methods: {
        checkLoginStatus() {
            this.isLoggedIn = localStorage.getItem('personId')!=-1;
            this.isOwner = localStorage.getItem('roleId') === '0';
        },
        logout() {
            console.log("BEFORE")
            console.log(localStorage.getItem('personId'));
            console.log(localStorage.getItem('customerVsInstructor'));
            console.log(localStorage.getItem('roleId'));
            localStorage.setItem('personId',-1);
            localStorage.setItem('customerVsInstructor',-1);
            localStorage.setItem('roleId',-1);
            console.log("AFTER")
            console.log(localStorage.getItem('personId'));
            console.log(localStorage.getItem('customerVsInstructor'));
            console.log(localStorage.getItem('roleId'));
            this.isLoggedIn = false;
            this.isOwner = false;
            this.$router.push('/');
        }
    }
}
</script>

<style scoped>

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