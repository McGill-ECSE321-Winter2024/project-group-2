<template>
    <nav>
        <router-link to='/Home'>Home</router-link>
        <router-link to='/Users'>Users</router-link>
        <router-link to='/Login'>Login</router-link>
    </nav>
</template>

<script>
export default {
    name: 'Navbar'
}
</script>

<style scoped>
nav {
    display: flex;
    justify-content: space-between;
    position: fixed;
    top: 0;
    width: 100%;
    background-color: lightgray;
    padding: 10px;
    z-index: 1000;
}

.nav-links {
    display: flex;
    gap: 10px;
}

.nav-links a {
    color: white !important;
}

.login-link {
    margin-left: auto;
}

.login-link a {
    color: white !important;
}
</style>