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

function SessionRegistrationDTO(registrationId, sessionId, length, startTime, endTime, date, classType, instructor){
    this.registrationId = registrationId;
    this.sessionId = sessionId;
    this.length = length;
    this.startTime = startTime;
    this.endTime = endTime;
    this.date = date;
    this.classType = classType;
    this.instructor = instructor;
}

function classType(name){
    this.name = name;
}

let customers = [];
export default {
    name: 'eventregistration',
    data() {
        return {
            customers: [],
            instructors: [],
            currentView: [],
            currentRegistrations: [],
            newPerson: '',
            newInstructor:'',
            instructorErrorMessage: '',
            instructorSuccessMessage:'',

            approvedClassTypes: [],
            suggestedClassTypes: [],
            typeSuccessMessage: '',
            typeErrorMessage: '',

            response: []
        }
    },
    created: function () {
        this.currentRegistrations = []
        AXIOS.get('/sessionRegistrations/customers/3')
        .then(response => {
            for(let i=0; i<response.data.length; i++) {
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

    },
    methods: {
        CancelRegistration: function (registrationId) {
            console.log(typeof registrationId)
            AXIOS.delete('/sessionRegistrations/'.concat(registrationId))
            .then(response => {
                console.log(response.data)
                this.successMessage = 'Instructor removed successfully'
                if(response.status!=200){
                    this.instructorErrorMessage = response.data.message
                    console.log("hellooooo")
                }
            }).catch(e =>{
                const errorMsg = e.response.data.message
                this.instructorErrorMessage = errorMsg
                console.log(errorMsg)
            })
        }
    }
}