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
    </div>
</template>

<script>
import axios from "axios";
import config from "../../config";

const client = axios.create({
    baseURL: config.dev.backendBaseUrl
})

export default {
    data() {
        return {
            newSessionLength: null,
            newSessionStartTime: null,
            newSessionEndTime: null,
            newSessionDate: null,
            newSessionRepeatsWeekly: false,
            newSessionMaxParticipants: null
        };
    },
    methods: {
        async createSession() {
            const newSession = {
                length: this.newSessionLength,
                startTime: this.newSessionStartTime,
                endTime: this.newSessionEndTime,
                date: this.newSessionDate,
                repeatsWeekly: this.newSessionRepeatsWeekly,
                maxParticipants: this.newSessionMaxParticipants
            };
            try {
                const response = await client.post("/Sessions", newSession);
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
        }
    };
    computed: {
        isCreatebtnDisabled() {
            return !this.newSessionLength 
            || !this.newSessionStartTime 
            || !this.newSessionEndTime 
            || !this.newSessionDate 
            || !this.newSessionMaxParticipants;
        }
    }
};
</script>