<template>
    <!-- Main container for the MyAccount page -->
    <div id="sportcenter">
        <!-- Navbar component -->
        <Navbar />
        <!-- Container for the user's information -->
        <div id="yourInfo">
            <!-- Section title -->
            <h2>Your Information</h2>
            <!-- Table for displaying the user's information -->
            <br>
            <table class="center">
                <!-- Table header -->
                <tr>
                    <th>Role Id</th>
                    <th>Name</th>
                    <th>Email</th>
                </tr>
                <!-- Table row for displaying the user's information -->
                <tr>
                    <!-- The user's role id -->
                    <td>{{ user.id }}</td>
                    <!-- The user's name -->
                    <td>{{ user.name }}</td>
                    <!-- The user's email -->
                    <td>{{ user.email }}</td>
                </tr>
            </table>
        </div>
        <!-- Container for the user's registrations -->
        <div id="grantAccountPermissions">
            <!-- Section title -->
            <h2>Your Registrations</h2>
            <!-- Error message if the user has no registrations -->
            <br>
            <h4 class="error" v-if="currentRegistrations.length==0">No registrations found!</h4>
            <!-- Table for displaying the user's registrations -->
            <table v-if="currentRegistrations.length!=0" class="center">
                <!-- Table header -->
                <tr>
                    <th class="rowName">Registration Id</th>
                    <th>Session Id</th>
                    <th>Length</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                    <th>Date</th>
                    <th>Class Type</th>
                    <th>Instructor</th>
                    <th>Cancel Registration</th>
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
            <h4 v-if="loadSecondTable && currentRegistrationsToTeach.length==0" class="center error">No registrations found!</h4>
            <p v-if="loadSecondTable">To cancel a session you're registered to teach, please contact management</p>
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
            <div v-if="loadSecondTable">
                <h2>Suggest a new class type</h2>
                <p>Feeling innovative? Suggest a new sport or new type of class to teach. Management will review it before approving!</p>
                <input class="suggest" type="text" v-model="suggestedClassType" placeholder="Suggest a new class type">
                <button class="suggest" @click="suggestClassType(suggestedClassType)">Suggest</button>
                <h5 v-if="typeSuccessMessage!=''" class="success">{{ typeSuccessMessage }}</h5>
                <h5 v-if="typeErrorMessage!=''" class="error">{{ typeErrorMessage }}</h5>
            </div>
        </div>
        <Footer />
    </div>
</template>

<script>
// Importing the Navbar and Footer components
import axios, { Axios } from 'axios' // Axios for making HTTP requests
import config from "../../config" // Configuration file
import Navbar from './Navbar' // Navbar component
import Footer from './Footer' // Footer component

// Constructing URLs for frontend and backend from config
const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

// Creating an  instance of axios with custom configuration
const AXIOS = axios.create({
    baseURL: backendUrl, // Base URL for the backend
    headers: { 'Access-Control-Allow-Origin': frontendUrl } // CORS header
})

// Constructor function for creating a new PersonDTO object
function PersonDTO(name) {
    this.name = name;
    this.events = [],
        this.personId;
}

// Constructor function for creating a new InstructorDTO object
function InstructorDTO(instructorId, personName, personId) {
    this.name = personName;
    this.instructorId = instructorId;
    this.personId = personId;
}

// Constructor function for creating a new SessionDTO object
function SessionDto(sessionId, length, startTime, endTime, date, classType, instructor){
    this.sessionId = sessionId;
    this.length = length;
    this.startTime = startTime;
    this.endTime = endTime;
    this.date = date;
    this.classType = classType;
    this.instructor = instructor;
}

// Constructor function for creating a new SessionRegistrationDTO object
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

// Constructor function for creating a new classType object
function classType(name) {
    this.name = name;
}

// Vue component definition
let customers = [];
export default {
    components: {
        Navbar,
        Footer
    },
    name: 'eventregistration',
    data() {
        // Reactive data properties for this component
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

            suggestedClassType: '',
            typeErrorMessage: '',
            typeSuccessMessage: '',

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
        // Lifecycle hook for initialization logic
        let whatToLoad = sessionStorage.getItem('customerVsInstructor');
        if (whatToLoad == 3){
            this.loadSecondTable = false;
        }
        console.log(this.loadSecondTable);
        console.log(whatToLoad);
        console.log(sessionStorage.getItem('personId'));
        if (whatToLoad === null || whatToLoad == -1 || whatToLoad == 0){this.$router.push('/');}

        if (whatToLoad ==2 || whatToLoad == 1){
            let personId = sessionStorage.getItem('personId');
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
                    console.log(sessionStorage.getItem('roleId'));
                    console.log(sessions.data.length);
                        for (let i = 0; i<sessions.data.length; i++){
                            console.log(sessions.data[i].instructorId == sessionStorage.getItem('roleId'));
                            if (sessions.data[i].instructorId == sessionStorage.getItem('roleId')){
                                this.currentRegistrationsToTeach.push(new SessionDto(sessions.data[i].id, sessions.data[i].length, sessions.data[i].startTime, sessions.data[i].endTime, sessions.data[i].date, sessions.data[i].classType.classType, sessions.data[i].instructorId));
                            }
                        }
                        console.log(this.currentRegistrationsToTeach.length);
                    })
              }
        }
        else{
        this.currentRegistrations = []
        AXIOS.get('/sessionRegistrations/customers/'.concat(sessionStorage.getItem('roleId')))
            .then(response => {
                for (let i = 0; i < response.data.length; i++) {
                    this.currentRegistrations.push(new SessionRegistrationDTO(response.data[i].id, response.data[i].session.id, response.data[i].session.length, response.data[i].session.startTime, response.data[i].session.endTime, response.data[i].session.date, response.data[i].session.classType.classType, sessionStorage.getItem('roleId')))
                }
                this.currentRegistrationsToTeach=[];
            }).catch(e => {
                this.instructorErrorMessage = e
            })
            .catch(e => {
                const errorMsg = e.response.data.message
                this.instructorErrorMessage = errorMsg
                console.log(errorMsg)
            })}

            AXIOS.get('/persons/'.concat(sessionStorage.getItem('personId'))).then(person => {
                let retrievedPerson = person.data;
                console.log(retrievedPerson);
                this.user.email = retrievedPerson.email;
                this.user.name = retrievedPerson.name;
                this.user.id = sessionStorage.getItem('roleId');
                console.log(this.user.email);
            })

    },
    methods: {
        CancelRegistration: function (registrationId) {
            // Method to cancel a registration
            console.log(typeof registrationId)
            AXIOS.delete('/sessionRegistrations/'.concat(registrationId))
                .then(response => {
                    this.fetchRegistrations();
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
                    console.log(errorMsg)
                })
        },
        suggestClassType: function () {
            // Method to suggest a new class type
            AXIOS.put('/classTypes/'.concat(this.suggestedClassType))
                .then(response => {
                    this.typeSuccessMessage = 'Class type suggested successfully'
                    this.typeErrorMessage = ''
                    this.suggestedClassType = ''
                    this.suggestedClassTypes.push(new classType(this.suggestedClassType))
                    console.log(response)
                }).catch(e => {
                    console.log(e.response.data)
                    const errorMsg = e.response.data
                    this.typeSuccessMessage = ''
                    this.typeErrorMessage = errorMsg
                    console.log(errorMsg)
                })
        },
        fetchRegistrations() {
            // Method to fetch and update current registrations
            let personId = sessionStorage.getItem('personId');
            AXIOS.get('/customers/'.concat(personId)).then(ins => {
            let customerId = ins.data;
            this.currentRegistrations = [];
            AXIOS.get('/sessionRegistrations/customers/'.concat(customerId))
                .then(response => {
                for (let i = 0; i < response.data.length; i++) {
                    this.currentRegistrations.push(new SessionRegistrationDTO(response.data[i].id, response.data[i].session.id, response.data[i].session.length, response.data[i].session.startTime, response.data[i].session.endTime, response.data[i].session.date, response.data[i].session.classType.classType, response.data[i].session.instructor.id));
                }
                })
                .catch(e => {
                const errorMsg = e.response.data.message;
                console.log(errorMsg);
                });
            });
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
  table{
    margin: auto;
    text-align: center;
    widows: 80%;
    margin-bottom: 20px;
  }
  th, tr{
    padding-left: 25px;
    padding-right: 25px;
    text-align: center;
  }
  .error {
    color: #a94442; /* red color */
    background-color: #f2dede; /* light red background */
    border: 1px solid #ebccd1; /* red border */
    padding: 15px; /* space inside the box */
    margin-bottom: 20px; /* space below the box */
    border-radius: 5px; /* rounded corners */
    text-align: center; /* center the text */
}
.success{
    color: #3c763d; /* green color */
    background-color: #dff0d8; /* light green background */
    border: 1px solid #d6e9c6; /* green border */
    padding: 15px; /* space inside the box */
    margin-bottom: 20px; /* space below the box */
    border-radius: 5px; /* rounded corners */
    text-align: center; /* center the text */
}
  .center{
    margin-left: auto;
    margin-right: auto;
  }
  button.suggest {
    background-color: #4CAF50;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
}
input.suggest {
    width: 40%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
    margin-bottom: 10px;
}
</style>