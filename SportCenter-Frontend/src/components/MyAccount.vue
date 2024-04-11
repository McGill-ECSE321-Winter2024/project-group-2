<template>
    <div id="sportcenter">
        <Navbar />
        <div id="yourInfo">
            <h2>Your Information</h2>
            <br>
            <table class="center">
                <tr>
                    <th>Role Id</th>
                    <th>Name</th>
                    <th>Email</th>
                </tr>
                <tr>
                    <td>{{ user.id }}</td>
                    <td>{{ user.name }}</td>
                    <td>{{ user.email }}</td>
                </tr>
            </table>
        </div>
        <div id="grantAccountPermissions">
            <h2>Your Registrations</h2>
            <br>
            <h4 class="error" v-if="currentRegistrations.length==0">No registrations found!</h4>
            <table v-if="currentRegistrations.length!=0" class="center">
                <tr>
                    <th class="rowName">Registration Id</th>
                    <th>Session Id</th>
                    <th>Length</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                    <th>Date</th>
                    <th>Class Type</th>
                    <th>Instructor</th>
                </tr>
                <tr v-for="registration in currentRegistrations" :key="registration.registrationId">
                    <td>{{ registration.registrationId }}</td>
                    <td>
                        {{ registration.sessionId }}
                    </td>
                    <td>
                        {{ registration.length }}
                  </td>
                  <td>
                        {{ registration.startTime }}
                  </td>
                  <td>
                        {{ registration.endTime }}
                  </td>
                  <td>
                        {{ registration.date }}
                  </td>
                  <td>
                        {{ registration.classType }}
                  </td>
                  <td>
                        {{ registration.instructor }}
                  </td>
                    <td>
                        <button @click="CancelRegistration(String(registration.registrationId))">Cancel Registration</button>
                    </td>
                </tr>
            </table>
            <h2 v-if="loadSecondTable" class="center">Your Registrations to Teach</h2>
            <br>
            <h4 v-if="loadSecondTable && currentRegistrationsToTeach.length==0" class="center">No registrations found!</h4>
            <table v-if="loadSecondTable && currentRegistrationsToTeach.length!=0" class="center">
                <tr>
                    <th>Session Id</th>
                    <th>Length</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                    <th>Date</th>
                    <th>Class Type</th>
                    <th>Instructor</th>
                </tr>
                <tr v-for="registration in currentRegistrationsToTeach" :key="registration.sessionId">
                    <td>
                        {{ registration.sessionId }}
                    </td>
                    <td>
                        {{ registration.length }}
                  </td>
                  <td>
                        {{ registration.startTime }}
                  </td>
                  <td>
                        {{ registration.endTime }}
                  </td>
                  <td>
                        {{ registration.date }}
                  </td>
                  <td>
                        {{ registration.classType }}
                  </td>
                  <td>
                        {{ registration.instructor }}
                  </td>
                </tr>
            </table>
            <p>
                <span v-if="instructorSuccessMessage" style="color:green">{{ instructorSuccessMessage }}</span> 
            </p>
            <p>
                <span v-if="instructorErrorMessage" style="color:red">Error: {{ instructorErrorMessage }}</span>
            </p>
        </div>
    </div>
</template>

<script>
import axios, { Axios } from 'axios'
import config from "../../config"
import Navbar from './Navbar'


const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

const AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function PersonDTO(name) {
    this.name = name;
    this.events = [],
        this.personId;
}

function InstructorDTO(instructorId, personName, personId) {
    this.name = personName;
    this.instructorId = instructorId;
    this.personId = personId;
}

function SessionDto(sessionId, length, startTime, endTime, date, classType, instructor){
    this.sessionId = sessionId;
    this.length = length;
    this.startTime = startTime;
    this.endTime = endTime;
    this.date = date;
    this.classType = classType;
    this.instructor = instructor;
}

function SessionRegistrationDTO(registrationId, sessionId, length, startTime, endTime, date, classType, instructor) {
    this.registrationId = registrationId;
    this.sessionId = sessionId;
    this.length = length;
    this.startTime = startTime;
    this.endTime = endTime;
    this.date = date;
    this.classType = classType;
    this.instructor = instructor;
}

function classType(name) {
    this.name = name;
}

let customers = [];
export default {
    components: {
        Navbar
    },
    name: 'eventregistration',
    data() {
        return {
            customers: [],
            instructors: [],
            currentView: [],
            currentRegistrations: [],
            currentRegistrationsToTeach: [],
            newPerson: '',
            newInstructor: '',
            instructorErrorMessage: '',
            instructorSuccessMessage: '',

            user: {
                id:'',
                email: '',
                name: ''
        },
        loadSecondTable: true,

            approvedClassTypes: [],
            suggestedClassTypes: [],
            typeSuccessMessage: '',
            typeErrorMessage: '',

            response: []
        }
    },
    created: function () {
        let whatToLoad = localStorage.getItem('customerVsInstructor');
        if (whatToLoad == 3){
            this.loadSecondTable = false;
        }
        console.log(this.loadSecondTable);
        console.log(whatToLoad);
        console.log(localStorage.getItem('personId'));
        if (whatToLoad === null || whatToLoad == -1 || whatToLoad == 0){this.$router.push('/Home');}

        if (whatToLoad ==2 || whatToLoad == 1){
            let personId = localStorage.getItem('personId');
            console.log(personId);
            AXIOS.get('/customers/'.concat(personId)).then(ins => {
                let customerId = ins.data;
                console.log(ins.data);
                this.currentRegistrations = []
        AXIOS.get('/sessionRegistrations/customers/'.concat(customerId))
            .then(response => {
                for (let i = 0; i < response.data.length; i++) {
                    this.currentRegistrations.push(new SessionRegistrationDTO(response.data[i].id, response.data[i].session.id, response.data[i].session.length, response.data[i].session.startTime, response.data[i].session.endTime, response.data[i].session.date, response.data[i].session.classType.classType, response.data[i].session.instructor.id))
                }
                this.instructorSuccessMessage = 'Persons loaded successfully'
            }).catch(e => {
                this.instructorErrorMessage = e
            })
            .catch(e => {
                const errorMsg = e.response.data.message
                this.instructorErrorMessage = errorMsg
                console.log(errorMsg)
            })
            

              })
              if (whatToLoad == 1){

                AXIOS.get('/sessions').then(sessions => {
                    for (let i = 0; i<sessions.data.length; i++){
                            console.log(sessions.data[i].instructorId == 0);
                            if (sessions.data[i].instructorId == 0){
                                this.currentRegistrationsToTeach.push(new SessionDto(sessions.data[i].id, sessions.data[i].length, sessions.data[i].startTime, sessions.data[i].endTime, sessions.data[i].date, sessions.data[i].classType.classType, sessions.data[i].instructorId));
                            }
                        }
                })
              }
              else if (whatToLoad == 2){
                AXIOS.get('/sessions').then(sessions => {
                    console.log('hello');
                    console.log(localStorage.getItem('roleId'));
                    console.log(sessions.data.length);
                        for (let i = 0; i<sessions.data.length; i++){
                            console.log(sessions.data[i].instructorId == localStorage.getItem('roleId'));
                            if (sessions.data[i].instructorId == localStorage.getItem('roleId')){
                                this.currentRegistrationsToTeach.push(new SessionDto(sessions.data[i].id, sessions.data[i].length, sessions.data[i].startTime, sessions.data[i].endTime, sessions.data[i].date, sessions.data[i].classType.classType, sessions.data[i].instructorId));
                            }
                        }
                        console.log(this.currentRegistrationsToTeach.length);
                    })
              }
        }
        else{
        this.currentRegistrations = []
        AXIOS.get('/sessionRegistrations/customers/'.concat(localStorage.getItem('roleId')))
            .then(response => {
                for (let i = 0; i < response.data.length; i++) {
                    this.currentRegistrations.push(new SessionRegistrationDTO(response.data[i].id, response.data[i].session.id, response.data[i].session.length, response.data[i].session.startTime, response.data[i].session.endTime, response.data[i].session.date, response.data[i].session.classType.classType, localStorage.getItem('roleId')))
                }
                this.instructorSuccessMessage = 'Persons loaded successfully'
                this.currentRegistrationsToTeach=[];
            }).catch(e => {
                this.instructorErrorMessage = e
            })
            .catch(e => {
                const errorMsg = e.response.data.message
                this.instructorErrorMessage = errorMsg
                console.log(errorMsg)
            })}

            AXIOS.get('/persons/'.concat(localStorage.getItem('personId'))).then(person => {
                let retrievedPerson = person.data;
                console.log(retrievedPerson);
                this.user.email = retrievedPerson.email;
                this.user.name = retrievedPerson.name;
                this.user.id = localStorage.getItem('roleId');
                console.log(this.user.email);
            })

    },
    methods: {
        CancelRegistration: function (registrationId) {
            console.log(typeof registrationId)
            AXIOS.delete('/sessionRegistrations/'.concat(registrationId))
                .then(response => {
                    console.log(response.data)
                    this.successMessage = 'Instructor removed successfully'
                    if (response.status != 200) {
                        this.instructorErrorMessage = response.data.message
                        console.log("hellooooo")
                    }for(let i=0; i<this.currentRegistrations.length; i++){
                    if(this.currentRegistrations[i].id == registrationId){
                        this.currentRegistrations.splice(i,1)
                    }
                }
                }).catch(e => {
                    const errorMsg = e.response.data.message
                    this.instructorErrorMessage = errorMsg
                    console.log(errorMsg)
                })
        }
    }
}
</script>
<style>
 
  span {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
  }
  .rowName{
    margin-right: 300px;
  }
  th{
    padding-right:15px;
  }
  .error{
    color:red;
  }
  .center{
    margin-left: auto;
    margin-right: auto;
  }
</style>