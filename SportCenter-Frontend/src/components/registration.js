import axios, { Axios } from 'axios'
import config from "../../config"

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
    name: 'eventregistration',
    data() {
        return {
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
            sessionErrorMessage: '',

            response: []
        }
    },
    created: function () {
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

        this.classTypes = []
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
        }


    }
}