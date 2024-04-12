<template>
<div class="footer-grid">
    <div class="contact-us">
        <h3>Contact Us</h3>
        <p>Address: 4327 Rue de Charleroi, Montréal, QC H1H 1T4</p>
        <p>Email: info@montrealsport.ca</p>
        <p>Phone: (514) 852-1991</p>
    </div>
    <div class="open-hours">
        <h3>Open Hours</h3>
        <p>Monday - Friday: 8:00am - 10:00pm</p>
        <p>Saturday - Sunday: 9:00am - 11:00pm</p>
    </div>
</div>
</template>
<style>
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