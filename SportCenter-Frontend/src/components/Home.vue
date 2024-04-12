<template>
    <!-- Main container for the Home page -->
    <div class="scrollable-content">
        <Navbar />
        <!-- Navbar component -->
        <div class="description">
            <h3 class="white">Montreal based sports center offering a variety of activities</h3>
            <p class="white">4327 Rue de Charleroi, Montréal, QC H1H 1T4 | info@montrealsport.ca | (514) 852-1991</p>
        </div>
        <div class="grid">
          <router-link to='/Sessions/Pilates' class="grid-item">
                <div class="image-container">
                    <img src='../assets/pilatesClass.jpg' alt='Class 1'>
                    <div class="overlay">
                        <p>Pilates</p>
                    </div>
                </div>
            </router-link>
            <router-link to='/Sessions/Resistance training' class="grid-item">
                <div class="image-container">
                    <img src='../assets/resistanceTrainingClass.jpg' alt='Class 2'>
                    <div class="overlay">
                        <p>Resistance Training</p>
                    </div>
                </div>
            </router-link>
            <router-link to='/Sessions/Spin' class="grid-item">
                <div class="image-container">
                    <img src='../assets/spinClass.png' alt='Class 3'>
                    <div class="overlay">
                        <p>Spin</p>
                    </div>
                </div>
            </router-link>
            <router-link to='/Sessions/Weight training' class="grid-item">
                <div class="image-container">
                    <img src='../assets/weightTrainingClass.jpg' alt='Class 4'>
                    <div class="overlay">
                        <p>Weight Training</p>
                    </div>
                </div>
            </router-link>
            <router-link to='/Sessions/Goated yoga' class="grid-item">
                <div class="image-container">
                    <img src='../assets/yogaClass.jpg' alt='Class 5'>
                    <div class="overlay">
                        <p>Yoga</p>
                    </div>
                </div>
            </router-link>
            <router-link to='/Sessions/Zumba' class="grid-item">
                <div class="image-container">
                    <img src='../assets/zumbaClass.jpg' alt='Class 6'>
                    <div class="overlay">
                        <p>Zumba</p>
                    </div>
                </div>
            </router-link>
        </div>
        <Footer/>
    </div>
</template>

<script>
// Importing the Navbar and Footer components
import Navbar from './Navbar'
import Footer from './Footer'

// Exporting the Home component
export default {
    //  Naming the component
    name: 'Home',
    // Registering the components used in this component
    components: {
        Navbar,
        Footer
    }
}
</script>


<style scoped>
 /* Styles for the Home component */
.white{
    color: #ffffffc7;

}
.logo {
    width: 400px;
}
.description {
    background-color: lightslategray;
    padding: 25px 0px 10px;
}

.scrollable-content {
    width: 100%; /* or any specific width */
    overflow: visible;
    position: static;
}

.grid {
    margin-top: 40px;
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-template-rows: repeat(2, 1fr);
    gap: 10px;
}

.grid::after {
    content: "";
    position: absolute;
    top: 100%;
    left: 0;
    width: 100%;
    height: 33%;
    background-color: rgb(33, 33, 33);
    z-index: -1;
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

.overlay p:hover{
    color: #85b3c9;
}

.footer-grid {
    width: 100%;
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 20px;
    background-color: rgb(33, 33, 33);
    color: white;
}

.contact-us, .open-hours {
    padding: 20px;
}

.contact-us h3, .open-hours h3 {
    padding: 0px 10px 0px;
    text-align: left;
    color: darkcyan;
}

.contact-us p, .open-hours p {
    padding: 0px 10px 0px;
    text-align: left;
    color: white;
}
</style>
