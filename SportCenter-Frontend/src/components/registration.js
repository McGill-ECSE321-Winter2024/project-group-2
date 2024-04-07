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

function EventDTO(name,date,start,end){
    this.name = name;
    this.eventDate = date;
    this.startTime = start;
    this.endTime = end;
}
let tempArr =[];
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
            errorMessage: '',
            successMessage:'',
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
                    }
                }
            }
            this.currentView = this.customers
            this.successMessage = 'Persons loaded successfully'
        }).catch(e => {
            this.errorMessage = e
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
                        }
                    }
                }
                this.currentView = this.customers
                this.successMessage = 'Persons loaded successfully'
            }).catch(e => {
                this.errorMessage = e
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
                this.successMessage = 'Instructors loaded successfully'
                console.log(response.data)
            })
            .catch(e => {
                const errorMsg = e.response.data.message
                this.errorMessage = errorMsg
                console.log(errorMsg)
            })
        },
        promoteToInstructor: function (personId) {
            console.log(typeof personId)
            AXIOS.post('/instructors',personId)
            .then(response => {
                console.log(response.data)
                this.newInstructor = ''
                this.successMessage = 'Instructor added successfully'
                for(let i=0; i<this.customers.length; i++){
                    if(this.customers[i].personId == personId){
                        this.instructors.push(this.customers[i])
                        this.customers.splice(i,1)
                    }
                }
            }).catch(e =>{
                console.log(e)
                const errorMsg = e.response.data.message
                console.log(errorMsg)
                this.errorMessage = errorMsg
            })
        },
        demoteInstructor: function (instructorId){
            for(let i=0; i<this.instructors.length; i++){
                if(this.instructors[i].instructorId == instructorId){
                    this.instructors.splice(i,1)
                }
            }
            AXIOS.delete('/instructors/'.concat(instructorId))
            .then(response => {
                console.log(response.data)
                this.successMessage = 'Instructor removed successfully'
                if(response.status!=200){
                    this.errorMessage = response.data.message
                    console.log("hellooooo")
                }
            }).catch(e =>{
                const errorMsg = e.response.data.message
                this.errorMessage = errorMsg
                console.log(errorMsg)
            })
        }

    }
}