<template>
    <div class="scrollable-content">
        <Navbar />
        <router-link to='/'>
            <img src='@/assets/logo.png' alt="Logo" class="logo" />
        </router-link>
        <div class="description">
            <h3> Scheduled sessions </h3>
            <p> We offer a wide range of activities, use the filter tool to find specific class types! </p>
        </div>
      <div class="filter">
        <table>
          <tr>
            <td> Select class type:</td>
            <td>
              <select v-model="filters.classType">
                <option value="">All</option>
                <option v-for="type in uniqueClassTypes" :value="type">{{ type }}</option>
              </select>
            </td>
          </tr>
        </table>
      </div>
        <div className="Sessions-grid-content" class="session-grid">
            <table>
              <thead>
                <tr>
                  <th>Session ID</th>
                  <th>Class Type</th>
                  <th>Date</th>
                  <th>Time</th>
                  <th>Duration</th>
                  <th>Repeating</th>
                  <th>Max Capacity</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="session in filteredSessions">
                  <td>{{ session.id }}</td>
                  <td>{{ session.classType.classType }}</td>
                  <td>{{ session.date }}</td>
                  <td>{{ session.startTime }}</td>
                  <td>{{ session.length }}</td>
                  <td>{{ session.isRepeating ? 'Yes' : 'No' }}</td>
                  <td>{{ session.maxParticipants }}</td>
                  <td>
                    <button type="button" @click="register(session.id)">Register</button>
                  </td>
                  <td>
                    <button v-if="loadRegisterToTeach" class="center" type="button" @click="registerToTeachSession(session.date, session.startTime, session.endTime, session.id, session.length, session.isRepeating, session.maxParticipants, session.classType.classType)">Register To Teach</button>
                  </td>
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
import axios from 'axios'
var config = require('../../config')
import Navbar from './Navbar'

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

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
      filters: {
        classType: ''
      },
      loadRegisterToTeach:false,
      filteredSessions: [],
      uniqueClassTypes: [],
      customer: {
        id: '',
        person_id: ''
      }
    }
  },
  created: function () {
    if (localStorage.getItem('customerVsInstructor')==2){
      this.loadRegisterToTeach=true;
    }
    client.get('/sessions')
      .then(response => {
        this.sessions = response.data.sort((a, b) => {
          return new Date(a.date) - new Date(b.date)
        })
        this.filteredSessions = [...this.sessions]
        this.uniqueClassTypes = [...new Set(this.sessions.map(session => session.classType.classType))]
      })
      .catch(e => {
        console.log(e)
      })
  },
  methods: {
    filterSessions () {
      this.filteredSessions = this.sessions.filter(session => {
        if (!this.filters.classType) return true
        return session.classType.classType.includes(this.filters.classType)
      })
    },
    getId (customerId) {
      client.get('/customers/'.concat(customerId)).then(result => {
          console.log(result.data);
          return result.data;
        })
    },
    createSessionRegistrationDTO (sessionId) {
      if (localStorage.getItem('customerVsInstructor')==3){
        console.log('entering customer');
        let customerId = localStorage.getItem('roleId');
      const sessionRegistrationDTO = {
        sessionId: sessionId,
        customerId: Number(localStorage.getItem('roleId'))
      }
      return sessionRegistrationDTO;
    }
      else if (localStorage.getItem('customerVsInstructor')==2){
        const sessionRegistrationDTO = {
        sessionId: sessionId,
        customerId: localStorage.getItem('instructorsCustomersId')
      }
      return sessionRegistrationDTO;
      }

      else if (localStorage.getItem('customerVsInstructor')==1){
      const sessionRegistrationDTO = {
        sessionId: sessionId,
        customerId: 0
      }
      return sessionRegistrationDTO;
    }

    },
    register (sessionId) {
      try {
        const newSessionRegistration = this.createSessionRegistrationDTO(sessionId);
        console.log(newSessionRegistration);
        client.post('/sessionRegistrations', newSessionRegistration).then(registered => {
          if (registered.status == 200){
            console.log('hello');
          }
        });
      } catch (e) {
        console.log(e)
      }
    },
    createUpdateSessionDto(sessionDate, startTime, endTime, id, length, isRepeating, maxParticipants, classType){
      const date = new Date(sessionDate);
      const formattedDate = date.toISOString().split('T')[0]; // Format to 'YYYY-MM-DD'
    console.log(formattedDate);
    console.log(startTime);
    console.log(endTime);
    console.log(parseInt(length));
    console.log(id);
    console.log(isRepeating);
    console.log(parseInt(maxParticipants));
    console.log(classType);
    console.log(parseInt(localStorage.getItem('roleId')));
    const sessionDto = {
                id: id,
                length: parseInt(length),
                startTime: startTime,
                endTime: endTime,
                date: formattedDate,
                isRepeating: isRepeating,
                maxParticipants: parseInt(maxParticipants),
                classType: {
                    classType: classType,
                    isApproved: true // Assuming all classes are approved by default
                },
                instructorId: parseInt(localStorage.getItem('roleId')) // Assuming the instructor ID is 1
            };
            return sessionDto;
  },
  registerToTeachSession: async function (sessionDate, startTime, endTime, id, length, isRepeating, maxParticipants, classType) {
            console.log('Updating session...');
            try {
                const updatedSession = this.createUpdateSessionDto(sessionDate, startTime, endTime, id, length, isRepeating, maxParticipants, classType);
                console.log('Created update session DTO:', updatedSession);
                const response = await client.put(`/sessions/${id}`, updatedSession);
                console.log(response.status);
                const index = this.sessions.findIndex(s => s.id == id);
                this.sessions.splice(index, 1, response.data); // Replace the old session with the updated one
            }
            catch (e) {
                console.log('Error updating session:', e.message);
            }
        }
  },
  watch: {
    'filters.classType': function () {
      this.filterSessions()
    }
  }
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
.filter {
    float: right;
    padding: 30px 20px 0px 0px;
}

.scrollable-content {
    width: 100%;
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
  padding: 20px 50px 50px;
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
