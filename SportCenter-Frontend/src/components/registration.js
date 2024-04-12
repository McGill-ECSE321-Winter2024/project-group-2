import axios, { Axios } from 'axios'
import config from "../../config"
import Navbar from './Navbar.vue'

const frontendUrl = 'http://'+config.dev.host+':'+config.dev.port
const backendUrl = 'http://'+config.dev.backendHost+':'+config.dev.backendPort

const AXIOS = axios.create({
    baseURL: backendUrl,
    headers: {'Access-Control-Allow-Origin':frontendUrl}
})

function PersonDTO(name) {
    this.name = name;
    this.events = [],
    this.personId;
}

function InstructorDTO(instructorId, personName, personId){
    this.name = personName;
    this.instructorId = instructorId;
    this.personId = personId;
}

function classType(name){
    this.name = name;
}

function sessionDTO(session){
    this.id = session.id;
    this.date = session.date;
    this.startTime = session.startTime;
    this.endTime = session.endTime;
    this.capacity = session.maxParticipants;
    this.instructorId = session.instructorId;
    this.classType = session.classType.classType;
    this.length = session.length;

}

let customers = [];
export default {
    components: {
        Navbar
    },
    name: 'eventregistration',
    data() {
        return {
            //
            customers: [],
            instructors: [],
            currentView: [],
            newPerson: '',
            newInstructor:'',
            instructorErrorMessage: '',
            instructorSuccessMessage:'',

            approvedClassTypes: [],
            suggestedClassTypes: [],
            newClassType: '',
            typeSuccessMessage: '',
            typeErrorMessage: '',

            sessions:[],
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
        this.currentView = []
        this.instructors = []
        AXIOS.get('/instructors')
        .then(response => {
            for(let i=0; i<response.data.length; i++) {
                console.log(response.data)
                AXIOS.get('/persons/'.concat(response.data[i].personId)).then(person => {
                    this.instructors.push(new InstructorDTO(response.data[i].instructorId,person.data.name, person.data.personId))
                    for(let j=0; j<this.customers.length; j++){
                        if(this.customers[j].personId == person.data.personId){
                            this.customers.splice(j,1)
                            break;
                        }
                    }
                })
            }
        })

        this.customers = []
        AXIOS.get('/persons')
        .then(response => {
            for(let i=0; i<response.data.length; i++) {
                this.customers.push(new InstructorDTO('N/A', response.data[i].name, response.data[i].personId))
            }
            for(let i=0; i<this.customers.length; i++){
                for(let j=0; j<this.instructors.length; j++){
                    if(this.customers[i].personId == this.instructors[j].personId){
                        this.customers.splice(i,1)
                        break;
                    }
                }
            }
            this.currentView = this.customers
            this.instructorSuccessMessage = 'Persons loaded successfully'
        }).catch(e => {
            this.instructorErrorMessage = e
        })

        AXIOS.get('/classTypes/false')
        .then(response => {
            for(let i=0; i<response.data.length; i++) {
                this.suggestedClassTypes.push(new classType(response.data[i].name))
            }
        })
        .catch(e => {
            const errorMsg = e.response.data.message
            this.instructorErrorMessage = errorMsg
            console.log(errorMsg)
        })

        AXIOS.get("/sessions")
        .then(response => {
            console.log(response)
            for(let i=0; i<response.data.length; i++){
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

        console.log('Fetching class types...');

        try {
            const response = await AXIOS.get("/classTypes/true"); // Replace "/classTypes" with the actual endpoint
            this.classTypes = response.data;
            console.log('Fetched class types:', this.classTypes);
        }
        catch (e) {
            console.log('Error fetching class types:', e);
        }


    },
    methods: {
        getPersons: function() {
            this.customers = []
            AXIOS.get('/persons')
            .then(response => {
                for(let i=0; i<response.data.length; i++) {
                    this.customers.push(new InstructorDTO('N/A', response.data[i].name, response.data[i].personId))
                }
                for(let i=0; i<this.customers.length; i++){
                    for(let j=0; j<this.instructors.length; j++){
                        if(this.customers[i].personId == this.instructors[j].personId){
                            this.customers.splice(i,1)
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

        getInstructors: function(){
            this.instructors = []
            AXIOS.get('/instructors')
            .then(response => {
                for(let i=0; i<response.data.length; i++) {
                    console.log(response.data)
                    AXIOS.get('/persons/'.concat(response.data[i].personId)).then(person => {
                        this.instructors.push(new InstructorDTO(response.data[i].instructorId,person.data.name, person.data.personId))
                    })
                }
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

        promoteToInstructor: function (personId) {
            console.log(typeof personId)
            AXIOS.post('/instructors',Number(personId))
            .then(response => {
                console.log(response.data)
                this.newInstructor = ''
                this.instructorSuccessMessage = 'Instructor added successfully'
                for(let i=0; i<this.customers.length; i++){
                    if(this.customers[i].personId == personId){
                        this.instructors.push(new InstructorDTO(response.data.instructorId, this.customers[i].name, personId))
                        this.customers.splice(i,1)
                        break;
                    }
                }
                this.currentView = this.customers
            }).catch(e =>{
                console.log(e)
                console.log(e.response)
                const errorMsg = e.response.data.message
                console.log(errorMsg)
                this.instructorErrorMessage = errorMsg
            })
        },

        demoteInstructor: function (instructorId){
            
            AXIOS.delete('/instructors/'.concat(instructorId))
            .then(response => {
                console.log(response.data)
                this.instructorSuccessMessage = 'Instructor demoted successfully'
                this.instructorErrorMessage = ''
                for(let i=0; i<this.instructors.length; i++){
                    if(this.instructors[i].instructorId == instructorId){
                        this.instructors.splice(i,1)
                    }
                }
                for(let i=this.sessions.length-1; i>=0; i--){
                    if(this.sessions[i].instructorId === instructorId){
                        this.sessions.splice(i, 1);
                    }
                }
            }).catch(e =>{
                const errorMsg = e.response.data.message
                console.log(e)
                this.instructorErrorMessage = errorMsg
                console.log(errorMsg)
            })
        },
        getClassTypes: function () {
            this.classTypes = []
            AXIOS.get('/classTypes/true')
            .then(response => {
                for(let i=0; i<response.data.length; i++){
                    this.classTypes.push(response.data[i])
                }
            })
            .catch(e => {
                const errorMsg = e.response.data.message
                this.typeErrorMessage = errorMsg
                console.log(errorMsg)
            })
        },
        
        approveClassType: function (classTypeName){
            AXIOS.put('/classTypes/'.concat(classTypeName).concat('/true'))
            .then(response =>{
                console.log(response.data)
                for(let i=0; i<this.suggestedClassTypes.length; i++){
                    if(this.suggestedClassTypes[i].name == classTypeName){
                        this.approvedClassTypes.push(this.suggestedClassTypes[i])
                        this.suggestedClassTypes.splice(i,1)
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

        rejectClassType: function (classTypeName){
            AXIOS.put('/classTypes/'.concat(classTypeName).concat('/false'))
            .then(response =>{
                console.log(response.data)
                this.typeSuccessMessage = 'Class type rejected successfully'
                this.typeErrorMessage = ''
                for(let i=0; i<this.suggestedClassTypes.length; i++){
                    if(this.suggestedClassTypes[i].name == classTypeName){
                        this.suggestedClassTypes.splice(i,1)
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

        getSessions: function(){
            this.sessions = []
            AXIOS.get("/sessions")
            .then(response => {
                for(let i=0; i<response.data.length; i++){
                    this.sessions.push(response.data[i])
                }
            })
            .catch(e => {
                const errorMsg = e.response.data.message
                this.sessionErrorMessage = errorMsg
                console.log(errorMsg)
            })
        },

        deleteSession: function(sessionId){
            AXIOS.delete("/sessions/".concat(sessionId))
            .then(response => {
                for(let i=0; i<this.sessions.length; i++){
                    if(this.sessions[i].id == sessionId){
                        this.sessions.splice(i,1)
                    }
                }
            })
            .catch(e => {
                const errorMsg = e.response.data.message
                this.sessionErrorMessage = errorMsg
                console.log(errorMsg)
            })
        },

        suggestClassType: function(classTypeName){
            if(classTypeName == ''){
                this.typeErrorMessage = 'Please enter a class type name'
                return
            }
            AXIOS.put('/classTypes/'.concat(classTypeName))
            .then(response =>{
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
        createSessionDto () {
            console.log('Creating session DTO...');
            const date = new Date(this.newSessionDate);
            const formattedDate = date.toISOString().split('T')[0]; // Format to 'YYYY-MM-DD'

            let startTime = this.newSessionStartTime;
            let endTime = this.newSessionEndTime;

            // Append ':00' only if the time doesn't already include seconds
            if (startTime.split(':').length === 2) {
                startTime += ':00';
            }
            if (endTime.split(':').length === 2) {
                endTime += ':00';
            }

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
        openUpdateModal(session) {
            console.log('Opening update modal for session:', session);
            this.updateSession = Object.assign({}, session);
            this.sessionIdToUpdate = session.id;
            this.isUpdateModalOpen = true;
        },
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
