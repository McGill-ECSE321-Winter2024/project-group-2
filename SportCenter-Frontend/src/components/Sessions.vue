<template>
    <div class="scrollable-content">
        <Navbar />
        <router-link to='/Home'>
            <img src='@/assets/logo.png' alt="Logo" class="logo" />
        </router-link>
        <div class="description">
            <h3> Scheduled sessions </h3>
            <p> We offer a wide range of activities, use the filter tool to find specific class types! </p>
        </div>
        <div className="Sessions-grid-content" class="session-grid">
            <table>
              <thead>
                <tr>
                  <th>Session ID</th>
                  <th>Duration</th>
                  <th>Class Type</th>
                  <th>Time</th>
                  <th>Date</th>
                  <th>Repeating</th>
                  <th>Max Capacity</th>
                  <th>Instructor</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="session in sessions"> <comment>  :key="session.id" </comment>
                  <td>{{ session.id }}</td>
                  <td>{{ session.duration }}</td>
                  <td>{{ session.classType }}</td>
                  <td>{{ session.time }}</td>
                  <td>{{ session.date }}</td>
                  <td>{{ session.repeating }}</td>
                  <td>{{ session.maxCapacity }}</td>
                  <td>{{ session.instructor }}</td>
                  <td><button type="button" @click="register(session.id)">Register</button></td>
                </tr>
              </tbody>
            </table>
        </div>
        <div class="footer-grid">
                <div class="contact-us">
                    <h3>Contact</h3>
                    <p>Montreal Sports</p>
                    <p>Address</p>
                    <p>Email</p>
                    <p>Phone</p>
                </div>
                <div class="open-hours">
                    <h3>Open Hours</h3>
                    <p>Monday - Friday: 8:00am - 10:00pm</p>
                    <p>Saturday - Sunday: 9:00am - 11:00pm</p>
                </div>
            </div>
    </div>
</template>

<script>
import axios from "axios";
var config = require('../../config')
import Navbar from './Navbar';

const frontendUrl = `http://${config.dev.host}:${config.dev.port}`;
const backendUrl = `http://${config.dev.backendHost}:${config.dev.backendPort}`;

const client = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  name: 'Sessions',
  components: {
    Navbar
  },
  data () {
    return {
      sessions: [],
    }
  },
  created: async function () {
    console.log('Fetching sessions...');
    try {
      const response = await client.get("/Sessions");
      this.sessions = response.data;
      console.log('Fetched sessions:', this.sessions);
    }
    catch (e) {
      console.log('Error fetching sessions:', e);
    }
  },
}
</script>


<style scoped>
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

.session-grid {
  display: grid;
  padding: 50px 50px 50px;
}

.session-grid table {
  width: 100%;
}

.session-grid td, .session-grid th {
  padding: 10px;
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
