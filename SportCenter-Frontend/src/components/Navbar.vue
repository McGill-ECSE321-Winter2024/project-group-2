<template>
    <nav>
        <router-link to='/'>
            <img src='@/assets/logo.png' alt="Logo" class="logo" />
        </router-link>
        <router-link to='/Sessions' v-if="!isLoggedIn">View Sessions</router-link>
        <router-link to='/RegisterSession' v-else>Register for a Session</router-link>
        <router-link to='/Login' v-if="!isLoggedIn">Login</router-link>
        <router-link to='/CreateAccount' v-if="!isLoggedIn">Create Account</router-link>
        <router-link to='/MyAccount' v-if="isLoggedIn">My Account</router-link>
        <router-link to='/Owner' v-if="isOwner">Owner Dashboard</router-link>
        <router-link to='/' v-if="isLoggedIn" @click.native="logout">Home</router-link>
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
    watch: {
        '$route': 'checkLoginStatus'
    },
    methods: {
        checkLoginStatus() {
            this.isLoggedIn = !!localStorage.getItem('customerVsInstructor');
            this.isOwner = localStorage.getItem('roleId') === '0';
        },
        logout() {
            localStorage.removeItem('customerVsInstructor');
            localStorage.removeItem('roleId');
            this.checkLoginStatus();
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
</style>