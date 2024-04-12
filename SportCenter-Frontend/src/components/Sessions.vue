<template>
    <div class="scrollable-content">
        <Navbar />
        <div class="description">
            <h3> Scheduled sessions </h3>
            <h4> We offer a wide range of activities. Use the filter tool to find specific class types! </h4>
        </div>
      <div class="filter">
        <h4>Filter results</h4>
        <table>
          <tr>
            <td> Select class type:</td>
            <td>
              <select v-model="filters.classType">
                <option value="">All</option>
                <option v-for="classType in uniqueClassTypes" :value="classType"> {{ classType }}</option>
              </select>
            </td>
          </tr>
          <tr>
            <td>Sessions on or after:</td>
            <td>
              <input type="date" v-model="filters.startDate" placeholder="End date">
            </td>

          </tr>
          <tr>
            <td>Sessions on or before:</td>
            <td>
              <input type="date" v-model="filters.endDate" placeholder="Start date">
            </td>
          </tr>
        </table>
      </div>
        <div className="Sessions-grid-content" class="session-grid">
          <h2>Sign up for a session</h2>
            <table v-if="assignedSessions.length!=0">
              <thead>
                <tr>
                  <th>Session ID</th>
                  <th>Class Type</th>
                  <th>Date</th>
                  <th>Time</th>
                  <th>Duration</th>
                  <th>Repeating sessions</th>
                  <th>Max Capacity</th>
                  <th v-if="isLoggedIn">Sign up</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="session in assignedSessions" :key="session.id">
                  <td>{{ session.id }}</td>
                  <td>{{ session.classType.classType }}</td>
                  <td>{{ session.date }}</td>
                  <td>{{ session.startTime }}</td>
                  <td>{{ session.length }}</td>
                  <td>{{ session.isRepeating ? 'Yes' : 'No' }}</td>
                  <td>{{ session.maxParticipants }}</td>
                  <td v-if="isLoggedIn">
                    <button class="promote" v-if="isLoggedIn" type="button" @click="register(session.id)">Register</button>
                  </td>
                </tr>
              </tbody>
            </table>
            <h2 v-if="loadRegisterToTeach">Register to Teach</h2>
            <h5 class='error' v-if="loadRegisterToTeach && unassignedSessions.length==0">No unassigned sessions found. Adjust filters or contact management to create new sessions</h5>
            <table v-if="loadRegisterToTeach && unassignedSessions.length>0">
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
                <tr v-for="session in unassignedSessions" :key="session.id">
                  <td>{{ session.id }}</td>
                  <td>{{ session.classType.classType }}</td>
                  <td>{{ session.date }}</td>
                  <td>{{ session.startTime }}</td>
                  <td>{{ session.length }}</td>
                  <td>{{ session.isRepeating ? 'Yes' : 'No' }}</td>
                  <td>{{ session.maxParticipants }}</td>
                  <td>
                    <button v-if="loadRegisterToTeach" class="promote" type="button" @click="registerToTeachSession(session.date, session.startTime, session.endTime, session.id, session.length, session.isRepeating, session.maxParticipants, session.classType.classType)">
                      Register To Teach
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
        </div>
        <Footer />
    </div>
</template>

<script>
import axios from 'axios'
var config = require('../../config')
import Navbar from './Navbar'
import Footer from './Footer'

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

const client = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  name: 'Sessions',
  components: {
    Navbar,
    Footer
  },
  data () {
    return {
      isLoggedIn: false,
      isOwner: false,

      sessions: [],
      filters: {
        classType: '',
        startDate: '',
        endDate: ''
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
    console.log('In created...'); // Add this line
    this.filters.classType = this.$route.params.classType || '';
    this.updateFilteredSessions(); // Move this to the top
    const userRole = localStorage.getItem('customerVsInstructor');
    this.loadRegisterToTeach = userRole === '2' || userRole === '1';
    this.checkLoginStatus();

    client.get('/sessions')
      .then(response => {
        console.log('Fetched sessions:', response.data); // Add this line
        this.sessions = response.data.sort((a, b) => {
          return new Date(a.date) - new Date(b.date)
        })
        //this.updateFilteredSessions(); // Call this after fetching the sessions data
        this.uniqueClassTypes = [...new Set(this.sessions.map(session => session.classType.classType))]
      })
      .catch(e => {
        console.log(e)
      })
  },
  methods: {
    checkLoginStatus() {
      this.isLoggedIn = localStorage.getItem('personId') !== '-1';
      this.isOwner = localStorage.getItem('roleId') === '0';
    },
    filterSessions () {
      this.filters.classType = this.$route.params.classType || '';
    },
    updateFilteredSessions() {
      console.log('Updating filtered sessions...'); // Add this line
      this.filteredSessions = [];
      for (let session of this.sessions) {
        if(this.filters.classType && session.classType.classType != this.filters.classType){
            continue;
        }
        if(this.filters.startDate && new Date(session.date) < new Date(this.filters.startDate)){
            continue;
        }
        if(this.filters.endDate && new Date(session.date) > new Date(this.filters.endDate)){
            continue;
        }
        this.filteredSessions.push(session);
      }
      /*this.filteredSessions = this.sessions.filter(session => {
        if (!this.filters.classType) return true;
        if (!session.classType) return false; // Add this line
        return session.classType.classType === this.filters.classType;
      });*/
      console.log('Filtered sessions:', this.filteredSessions); // Add this line
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
  computed: {
    unassignedSessions() {
      return this.filteredSessions.filter(session => session.instructorId === 1);
    },
    assignedSessions() {
      return this.filteredSessions.filter(session => session.instructorId !== 1);
    }
  },
  watch: {
    'sessions': function () {
      this.updateFilteredSessions();
    },
    'filters.classType': function () {
      this.updateFilteredSessions();
    },
    '$route.params.classType': function () {
      this.filterSessions();
    },
    'filters.startDate': function () {
      this.updateFilteredSessions();
    },
    'filters.endDate': function () {
      this.updateFilteredSessions();
    }
  }
}
</script>


<style scoped>
table{
  margin-right: auto;

}
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
input[type='date']{
  width: 100%;
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


</style>
