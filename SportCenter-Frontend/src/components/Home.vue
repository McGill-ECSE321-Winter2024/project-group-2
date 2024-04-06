<template>
    <div class="scrollable-content">
        <Navbar />
        <img <img src='@/assets/logo.png' alt="Logo" class="logo" />
        <p>Montreal based sports center offering a variety of activities</p>
        <div class="grid">
            <router-link to='/sessions' class="grid-item">
                <div class="image-container">
                    <img src='../assets/pilatesClass.jpg' alt='Class 1'>
                    <div class="overlay">
                        <p>Pilates</p>
                    </div>
                </div>  
            </router-link>
            <router-link to='/sessions' class="grid-item">
                <div class="image-container">
                    <img src='../assets/resistanceTrainingClass.jpg' alt='Class 2'>
                    <div class="overlay">
                        <p>Resistance Training</p>
                    </div>
                </div>  
            </router-link>
            <router-link to='/sessions' class="grid-item">
                <div class="image-container">
                    <img src='../assets/spinClass.png' alt='Class 3'>
                    <div class="overlay">
                        <p>Spin</p>
                    </div>
                </div>  
            </router-link>
            <router-link to='/sessions' class="grid-item">
                <div class="image-container">
                    <img src='../assets/weightTrainingClass.jpg' alt='Class 4'>
                    <div class="overlay">
                        <p>Weight Training</p>
                    </div>
                </div>  
            </router-link>
            <router-link to='/sessions' class="grid-item">
                <div class="image-container">
                    <img src='../assets/yogaClass.jpg' alt='Class 5'>
                    <div class="overlay">
                        <p>Yoga</p>
                    </div>
                </div>  
            </router-link>
            <router-link to='/sessions' class="grid-item">
                <div class="image-container">
                    <img src='../assets/zumbaClass.jpg' alt='Class 6'>
                    <div class="overlay">
                        <p>Zumba</p>
                    </div>
                </div>  
            </router-link>
        </div>
    </div>
</template>

<script>
import Navbar from './Navbar'

export default {
    name: 'Home',
    components: {
        Navbar
    }
}
</script>


<style scoped>
.logo {
    width: 400px;
    height: relative;
}

.scrollable-content {
    width: 100%; /* or any specific width */
    overflow: visible;
    position: static;
}

.grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-template-rows: repeat(2, 1fr);
    gap: 10px;
}

.grid-item img {
    width: 100%;
    height: 260px;
    object-fit: cover;
}

.grid-item .image-container {
    position: relative;
    width: 100%;
    height: 260px;
}

.overlay {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    background: linear-gradient(to top, black, transparent);
    color: white;
    padding: 50px 10px 10px;
    font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
}
</style>