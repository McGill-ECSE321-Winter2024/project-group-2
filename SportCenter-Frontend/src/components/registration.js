// import axios 
// import Navbar component

import axios, { Axios } from 'axios'
import config from "../../config"
import Navbar from './Navbar.vue'

// construct frontend and backend URLs using configuration settings
const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

// create axios instance configured to communicate with the backend
const AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

// DTOs for person, instructor, class type, session
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

function classType(name) {
    this.name = name;
}

function sessionDTO(session) {
    this.id = session.id;
    this.date = session.date;
    this.startTime = session.startTime;
    this.endTime = session.endTime;
    this.capacity = session.maxParticipants;
    this.instructorId = session.instructorId;
    this.classType = session.classType.classType;
    this.length = session.length;

}
// initial state variables
let customers = [];


// register Navbar component 
export default {
    components: {
        Navbar
    },
    name: 'eventregistration',
    data() {
        return {
            // state variables 
            customers: [],
            instructors: [],
            currentView: [],
            newPerson: '',
            newInstructor: '',
            instructorErrorMessage: '',
            instructorSuccessMessage: '',

            approvedClassTypes: [],
            suggestedClassTypes: [],
            newClassType: '',
            typeSuccessMessage: '',
            typeErrorMessage: '',

            sessions: [],
            classTypes: [],

            // creating session
            newSessionLength: null,
            newSessionStartTime: '',
            newSessionEndTime: '',
            newSessionDate: '',
            newSessionRepeatsWeekly: false,
            newSessionMaxParticipants: null,
            newSessionClassType: null,
            createSessionSuccess: '',
            createSessionError: '',

            // updating session
            updateSession: {
                length: null,
                startTime: '',
                endTime: '',
                date: '',
                repeatsWeekly: false,
                maxParticipants: null,
                classType: null,
            },
            isUpdateModalOpen: false,
            sessionIdToUpdate: null,
            sessionErrorMessage: '',

            response: []
        }
    },
    created: async function () {
        // update session data when page loads
        this.currentView = []
        this.instructors = []

        // fetch instructor data
        AXIOS.get('/instructors')
            .then(response => {
                for (let i = 0; i < response.data.length; i++) {
                    console.log(response.data)
                    AXIOS.get('/persons/'.concat(response.data[i].personId)).then(person => {
                        // update instructor data
                        this.instructors.push(new InstructorDTO(response.data[i].instructorId, person.data.name, person.data.personId))
                        for (let j = 0; j < this.customers.length; j++) {
                            if (this.customers[j].personId == person.data.personId) {
                                this.customers.splice(j, 1)
                                break;
                            }
                        }
                    })
                }
            })

        this.customers = []

        // fetch customer data 
        AXIOS.get('/persons')
            .then(response => {
                for (let i = 0; i < response.data.length; i++) {
                    this.customers.push(new InstructorDTO('N/A', response.data[i].name, response.data[i].personId))
                }
                for (let i = 0; i < this.customers.length; i++) {
                    for (let j = 0; j < this.instructors.length; j++) {
                        if (this.customers[i].personId == this.instructors[j].personId) {
                            this.customers.splice(i, 1)
                            break;
                        }
                    }
                }
                this.currentView = this.customers
                this.instructorSuccessMessage = 'Persons loaded successfully'
            }).catch(e => {
                this.instructorErrorMessage = e
            })

        // fetch class type data
        AXIOS.get('/classTypes/false')
            .then(response => {
                // save class type data if it exists
                for (let i = 0; i < response.data.length; i++) {
                    this.suggestedClassTypes.push(new classType(response.data[i].name))
                }
            })
            .catch(e => {
                const errorMsg = e.response.data.message
                this.instructorErrorMessage = errorMsg
                console.log(errorMsg)
            })

        // fetch session data
        AXIOS.get("/sessions")
            .then(response => {
                console.log(response)
                for (let i = 0; i < response.data.length; i++) {
                    this.sessions.push(new sessionDTO(response.data[i]))
                }
            })
            .catch(e => {
                console.log(e)
                //this.sessionErrorMessage = errorMsg
            })


        console.log('Fetching sessions...');
        try {
            const response = await AXIOS.get("/sessions");
            this.sessions = response.data;
            console.log('Fetched sessions:', this.sessions);
        }
        catch (e) {
            console.log('Error fetching sessions:', e);
        }

        // fetch class type data
        console.log('Fetching class types...');

        try {
            const response = await AXIOS.get("/classTypes/true"); // replace "/classTypes" with the actual endpoint
            this.classTypes = response.data;
            console.log('Fetched class types:', this.classTypes);
        }
        catch (e) {
            console.log('Error fetching class types:', e);
        }


    },

    // methods for instructor, class type, session
    methods: {
        // get all persons from backend and add them to the list of available
        getPersons: function () {
            this.customers = []
            AXIOS.get('/persons')
                .then(response => {
                    for (let i = 0; i < response.data.length; i++) {
                        this.customers.push(new InstructorDTO('N/A', response.data[i].name, response.data[i].personId))
                    }
                    for (let i = 0; i < this.customers.length; i++) {
                        for (let j = 0; j < this.instructors.length; j++) {
                            if (this.customers[i].personId == this.instructors[j].personId) {
                                this.customers.splice(i, 1)
                                break;
                            }
                        }
                    }
                    this.currentView = this.customers
                    this.instructorSuccessMessage = 'Persons loaded successfully'
                }).catch(e => {
                    this.instructorErrorMessage = e
                })
        },

        // get all instructors from backend and add them to the list of available
        getInstructors: function () {
            this.instructors = []
            AXIOS.get('/instructors')
                .then(response => {
                    for (let i = 0; i < response.data.length; i++) {
                        console.log(response.data)
                        AXIOS.get('/persons/'.concat(response.data[i].personId)).then(person => {
                            // create a new InstructorDTO object and push it to the instructors array
                            this.instructors.push(new InstructorDTO(response.data[i].instructorId, person.data.name, person.data.personId))
                        })
                    }
                    // set the currentView to the instructors array and display a success message
                    this.currentView = this.instructors
                    this.instructorSuccessMessage = 'Instructors loaded successfully'
                    console.log(response.data)
                })
                .catch(e => {
                    const errorMsg = e.response.data.message
                    this.instructorErrorMessage = errorMsg
                    console.log(errorMsg)
                })
        },

        // promote a user to an instructor
        promoteToInstructor: function (personId) {
            console.log(typeof personId)
            // POST request to the '/instructors' endpoint with personId as the request body
            AXIOS.post('/instructors', Number(personId))
                .then(response => {
                    console.log(response.data)
                    this.newInstructor = ''
                    this.instructorSuccessMessage = 'Instructor added successfully'

                    // loop through the customers array, remove person from "customers" if promoted to "instructor"
                    for (let i = 0; i < this.customers.length; i++) {
                        if (this.customers[i].personId == personId) {
                            this.instructors.push(new InstructorDTO(response.data.instructorId, this.customers[i].name, personId))
                            this.customers.splice(i, 1)
                            break;
                        }
                    }
                    this.currentView = this.customers
                }).catch(e => {
                    console.log(e)
                    console.log(e.response)
                    const errorMsg = e.response.data.message
                    console.log(errorMsg)
                    this.instructorErrorMessage = errorMsg
                })
        },

        // demote an instructor
        demoteInstructor: function (instructorId) {
            // DELETE request to '/instructors/:instructorId' endpoint
            AXIOS.delete('/instructors/'.concat(instructorId))
                .then(response => {
                    console.log(response.data)
                    this.instructorSuccessMessage = 'Instructor demoted successfully'
                    this.instructorErrorMessage = ''

                    // loop through the customers array, remove person from "instructors" list
                    for (let i = 0; i < this.instructors.length; i++) {
                        if (this.instructors[i].instructorId == instructorId) {
                            this.instructors.splice(i, 1)
                        }
                    }
                    //remove session taught by the instructor if demoted
                    for (let i = this.sessions.length - 1; i >= 0; i--) {
                        if (this.sessions[i].instructorId === instructorId) {
                            this.sessions.splice(i, 1);
                        }
                    }
                }).catch(e => {
                    const errorMsg = e.response.data.message
                    console.log(e)
                    this.instructorErrorMessage = errorMsg
                    console.log(errorMsg)
                })
        },

        //  fetch class types from the backend
        getClassTypes: function () {
            this.classTypes = []
            AXIOS.get('/classTypes/true')
                .then(response => {
                    for (let i = 0; i < response.data.length; i++) {
                        // push each class type to the classTypes array
                        this.classTypes.push(response.data[i])
                    }
                })
                .catch(e => {
                    const errorMsg = e.response.data.message
                    this.typeErrorMessage = errorMsg
                    console.log(errorMsg)
                })
        },

        // approve a class type
        approveClassType: function (classTypeName) {
            // PUT request to the '/classTypes/:classTypeName/true' endpoint
            AXIOS.put('/classTypes/'.concat(classTypeName).concat('/true'))
                .then(response => {
                    console.log(response.data)
                    // loop through the suggestedClassTypes array
                    for (let i = 0; i < this.suggestedClassTypes.length; i++) {
                        if (this.suggestedClassTypes[i].name == classTypeName) {
                            //if classTypeName matches, push the class type to the approvedClassTypes array
                            this.approvedClassTypes.push(this.suggestedClassTypes[i])
                            // remove class fro suggestedClassTypes array
                            this.suggestedClassTypes.splice(i, 1)
                        }
                    }
                    this.typeSuccessMessage = 'Class type approved successfully'
                    this.typeErrorMessage = ''
                    this.getClassTypes();
                })
                .catch(e => {
                    const errorMsg = e.response.data.message
                    this.typeErrorMessage = errorMsg
                    this.typeSuccessMessage = ''
                    console.log(errorMsg)
                })
        },

        //  reject a class type
        rejectClassType: function (classTypeName) {
            // PUT request to the '/classTypes/:classTypeName/false' endpoint
            AXIOS.put('/classTypes/'.concat(classTypeName).concat('/false'))
                .then(response => {
                    console.log(response.data)
                    this.typeSuccessMessage = 'Class type rejected successfully'
                    this.typeErrorMessage = ''
                    // loop through the suggestedClassTypes array
                    for (let i = 0; i < this.suggestedClassTypes.length; i++) {
                        if (this.suggestedClassTypes[i].name == classTypeName) {
                            // remove class from suggestedClassTypes array
                            this.suggestedClassTypes.splice(i, 1)
                        }
                    }
                })
                .catch(e => {
                    const errorMsg = e.response.data.message
                    this.typeErrorMessage = errorMsg
                    this.typeSuccessMessage = ''
                    console.log(errorMsg)
                })
        },

        // gets all sessions from the backend and updates the local 'sessions' array
        getSessions: function () {
            this.sessions = []
            AXIOS.get("/sessions")
                .then(response => {
                    for (let i = 0; i < response.data.length; i++) {
                        this.sessions.push(response.data[i])
                    }
                })
                .catch(e => {
                    const errorMsg = e.response.data.message
                    this.sessionErrorMessage = errorMsg
                    console.log(errorMsg)
                })
        },

        // deletes a session by its ID and updates the local sessions list
        deleteSession: function (sessionId) {
            AXIOS.delete("/sessions/".concat(sessionId))
                .then(response => {
                    for (let i = 0; i < this.sessions.length; i++) {
                        if (this.sessions[i].id == sessionId) {
                            this.sessions.splice(i, 1)
                        }
                    }
                })
                .catch(e => {
                    const errorMsg = e.response.data.message
                    this.sessionErrorMessage = errorMsg
                    console.log(errorMsg)
                })
        },

        // suggest a class type
        suggestClassType: function (classTypeName) {
            if (classTypeName == '') {
                this.typeErrorMessage = 'Please enter a class type name'
                return
            }
            AXIOS.put('/classTypes/'.concat(classTypeName))
                .then(response => {
                    // successfully suggested a class type, push to suggested types list
                    this.typeSuccessMessage = 'Class type suggested successfully, review to approve it below'
                    this.suggestedClassTypes.push(new classType(classTypeName))
                    this.newClassType = ''
                    this.typeErrorMessage = ''
                })
                .catch(e => {
                    const errorMsg = e.response.data.message
                    this.typeErrorMessage = errorMsg
                    this.typeSuccessMessage = ''
                    console.log(errorMsg)
                })
        },

        // constructs a session DTO for creating or updating a session
        createSessionDto() {
            console.log('Creating session DTO...');
            const date = new Date(this.newSessionDate);
            const formattedDate = date.toISOString().split('T')[0]; // Format to 'YYYY-MM-DD'

            // ensure time format includes seconds
            let startTime = this.newSessionStartTime;
            let endTime = this.newSessionEndTime;

            // Append ':00' only if the time doesn't already include seconds
            if (startTime.split(':').length === 2) {
                startTime += ':00';
            }
            if (endTime.split(':').length === 2) {
                endTime += ':00';
            }

            // build session DTO
            const sessionDto = {
                id: null, // Assuming this is generated by the backend
                length: parseInt(this.newSessionLength),
                startTime: startTime,
                endTime: endTime,
                date: formattedDate,
                isRepeating: this.newSessionRepeatsWeekly,
                maxParticipants: parseInt(this.newSessionMaxParticipants),
                classType: {
                    classType: this.newSessionClassType.name,
                    isApproved: true // Assuming all classes are approved by default
                },
                instructorId: 1
            };

            console.log('Created session DTO:', sessionDto);
            return sessionDto;
        },

        // similar to createSessionDto, but for updating existing sessions
        createUpdateSessionDto() {
            console.log('Creating update session DTO...');
            const date = new Date(this.updateSession.date);
            const formattedDate = date.toISOString().split('T')[0]; // Format to 'YYYY-MM-DD'

            let startTime = this.updateSession.startTime;
            let endTime = this.updateSession.endTime;

            // Append ':00' only if the time doesn't already include seconds
            if (startTime.split(':').length === 2) {
                startTime += ':00';
            }
            if (endTime.split(':').length === 2) {
                endTime += ':00';
            }

            const sessionDto = {
                id: this.sessionIdToUpdate,
                length: parseInt(this.updateSession.length),
                startTime: startTime,
                endTime: endTime,
                date: formattedDate,
                isRepeating: this.updateSession.repeatsWeekly,
                maxParticipants: parseInt(this.updateSession.maxParticipants),
                classType: {
                    classType: this.updateSession.classType.name,
                    isApproved: true // Assuming all classes are approved by default
                },
                instructorId: 1 // Assuming the instructor ID is 1
            };

            console.log('Created update session DTO:', sessionDto);
            return sessionDto;
        },

        // creates a new session based on user input
        createSession: async function () {
            console.log('Creating session...');
            const newSession = this.createSessionDto();
            try {
                const response = await AXIOS.post("/sessions", newSession);
                this.sessions.push(response.data);
                this.clearInputs();
                console.log('Created session:', response.data);
                this.createSessionSuccess = "Session created successfully!"
                this.createSessionError = ''
            }
            catch (e) {
                this.createSessionError = e.response.data;
                this.createSessionSuccess = ''
                console.log('Error creating session:', e.response);
            }
        },

        // open the update modal for a session
        openUpdateModal(session) {
            console.log('Opening update modal for session:', session);
            this.updateSession = Object.assign({}, session);
            this.sessionIdToUpdate = session.id;
            this.isUpdateModalOpen = true;
        },

        // update session data
        updateSessionData: async function () {
            console.log('Updating session...');
            try {
                const updatedSession = this.createUpdateSessionDto();
                const response = await AXIOS.put(`/sessions/${this.updateSession.id}`, updatedSession);
                const index = this.sessions.findIndex(s => s.id === this.updateSession.id);
                this.sessions.splice(index, 1, response.data); // Replace the old session with the updated one
                this.isUpdateModalOpen = false; // Close the update modal
                console.log('Updated session:', response.data);
            }
            catch (e) {
                console.log('Error updating session:', e.message);
            }
        },

        // clear inputs
        clearInputs() {
            console.log('Clearing inputs...');
            this.newSessionLength = null;
            this.newSessionStartTime = null;
            this.newSessionEndTime = null;
            this.newSessionDate = null;
            this.newSessionRepeatsWeekly = false;
            this.newSessionMaxParticipants = null;
            console.log('Cleared inputs');
        }
    },
    computed: {
        // computed property to check if the create button is disabled
        isCreatebtnDisabled() {
            const disabled = !this.newSessionLength
                || !this.newSessionStartTime
                || !this.newSessionEndTime
                || !this.newSessionDate
                || !this.newSessionMaxParticipants
                || !this.newSessionClassType;
            console.log('Create button disabled:', disabled);
            return disabled;
        }
    }

}
