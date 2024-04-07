<template>
    <div id="Owner">
        <h1>Sessions</h1>
        <h2>Schedule New Session</h2>
        <div>
            <input type="text" placeholder="Length" v-model="newSessionLength" />
            <input type="time" placeholder="Start Time" v-model="newSessionStartTime" />
            <input type="time" placeholder="End Time" v-model="newSessionEndTime" />
            <input type="date" placeholder="Date" v-model="newSessionDate" />
            <input type="checkbox" v-model="newSessionRepeatsWeekly" /> Repeats Weekly
            <input type="number" placeholder="Max Participants" v-model="newSessionMaxParticipants" />
            <button @click="createSession()" :disabled="isCreatebtnDisabled">Create Session</button>
        </div>
        <div>
            <h2>Current Sessions</h2>
            <table>
                <tbody id="sessions-tbody">
                    <tr>
                        <th>Time</th>
                        <th>ClassType</th>
                        <th>Instructor</th>
                        <th>RepeatsWeekly</th>
                        <th>MaxParticipants</th>
                    </tr>
                    <tr v-for="s in sessions">
                        <td>{{ s.date }} - {{ s.startTime }} - {{ s.endTime }}</td>
                        <td>{{ s.classType }}</td>
                        <td>{{ s.instructor }}</td>
                        <td>{{ s.repeatsWeekly }}</td>
                        <td>{{ s.maxParticipants }}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
import axios from "axios";
import config from "../../config";

const frontendUrl = `http://${config.dev.host}:${config.dev.port}`;
const backendUrl = `http://${config.dev.backendHost}:${config.dev.backendPort}`;

const client = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    data() {
        return {
            sessions: [],
            newSessionLength: '',
            newSessionStartTime: '',
            newSessionEndTime: '',
            newSessionDate: '',
            newSessionRepeatsWeekly: false,
            newSessionMaxParticipants: ''
        };
    },
    created: async function () {
        try {
            const response = await client.get("/sessions");
            this.events = response.data;
        }
        catch (e) {
            console.log(e);
        }
    },
    
    methods: {
        createSession: async function () {
            const newSession = {
                length: this.newSessionLength,
                startTime: this.newSessionStartTime,
                endTime: this.newSessionEndTime,
                date: this.newSessionDate,
                repeatsWeekly: this.newSessionRepeatsWeekly,
                maxParticipants: this.newSessionMaxParticipants,
                classType: "dummyClassType",
                instructor: "1"
            };
            try {
                const response = await client.post("/sessions", newSession);
                this.events.push(response.data);
                this.clearInputs();
            }
            catch (e) {
                console.log(e);
            }
            },
            clearInputs() {
                this.newSessionLength = null;
                this.newSessionStartTime = null;
                this.newSessionEndTime = null;
                this.newSessionDate = null;
                this.newSessionRepeatsWeekly = false;
                this.newSessionMaxParticipants = null;
            }
        },
    computed: {
        isCreatebtnDisabled() {
            return !this.newSessionLength 
            || !this.newSessionStartTime 
            || !this.newSessionEndTime 
            || !this.newSessionDate 
            || !this.newSessionMaxParticipants;
        }
    }
}
</script>