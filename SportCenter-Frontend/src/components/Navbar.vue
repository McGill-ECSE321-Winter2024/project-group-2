<template>
    <nav>
        <router-link to='/'>
            <img src='@/assets/logo.png' alt="Logo" class="logo" />
        </router-link>
        <router-link to='/Sessions'>Register for a session</router-link>
        <router-link to='/MyAccount'>My Account</router-link>
        <router-link to='/Login'>Login</router-link>
    </nav>
</template>

<script>
export default {
    name: 'Navbar'
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
