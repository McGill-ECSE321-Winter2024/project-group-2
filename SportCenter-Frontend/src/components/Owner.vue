<template>
    <div id="Owner">
        <!-- Section header for managing sessions -->
        <h1>Sessions</h1>
        <!-- Subsection for scheduling new sessions -->
        <h2>Schedule New Session</h2>
        <div>
            <!-- Form for inputting new session details: time, date, repetition, class type, and max participants -->
            <input type="time" placeholder="Start Time" v-model="newSessionStartTime" />
            <input type="time" placeholder="End Time" v-model="newSessionEndTime" />
            <input type="date" placeholder="Date" v-model="newSessionDate" />
            <input type="checkbox" v-model="newSessionRepeatsWeekly" /> Repeats Weekly
            <select v-model="newSessionClassType">
                <option v-for="classType in classTypes" :value="classType">{{ classType.name }}</option>
            </select>
            <input type="text" placeholder="Length" v-model="newSessionLength" />
            <input type="text" placeholder="Max Participants" v-model="newSessionMaxParticipants" />
            <!-- Remaining form fields for scheduling a session -->
            <button @click="createSession()" :disabled="isCreatebtnDisabled">Create Session</button>

        </div>
        <!-- Section for displaying current sessions -->
        <div>
            <h2>Current Sessions</h2>
            <table>
                <tbody id="sessions-tbody">
                    <tr>
                        <th>Time</th>
                        <th>ClassType</th>
                        <th>MaxParticipants</th>
                    </tr>
                    <!-- Table headers for session details -->
                    <tr v-for="s in sessions">
                        <!-- Template for displaying each session with an update option -->
                        <td>{{ s.date }} - {{ s.startTime }} - {{ s.endTime }}</td>
                        <td>{{ s.classType.classType }} ({{ s.classType.isApproved ? 'Approved' : 'Not Approved' }})</td>
                        <td>{{ s.maxParticipants }}</td>
                        <td><button @click="openUpdateModal(s)">Update</button></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <!-- Modal for updating a session, shown conditionally -->
        <div v-show="isUpdateModalOpen">
            <h2>Update Session</h2>
            <input type="time" v-model="updateSession.startTime" placeholder="Start Time" />
            <input type="time" v-model="updateSession.endTime" placeholder="End Time" />
            <input type="date" v-model="updateSession.date" placeholder="Date" />
            <input type="checkbox" v-model="updateSession.repeatsWeekly" /> Repeats Weekly
            <select v-model="updateSession.classType">
                <option v-for="classType in classTypes" :value="classType">{{ classType.name }}</option>
            </select>
            <input type="text" v-model="updateSession.length" placeholder="Length" />
            <input type="text" v-model="updateSession.maxParticipants" placeholder="Max Participants" />
            <!-- Form for updating session details -->
            <button @click="updateSessionData()">Update</button>
            <button @click="isUpdateModalOpen = false">Cancel</button>
        </div>
    </div>
</template>

<script>
import axios, { Axios } from "axios";
import config from "../../config";

const frontendUrl = `http://${config.dev.host}:${config.dev.port}`;
const backendUrl = `http://${config.dev.backendHost}:${config.dev.backendPort}`;

// Configuration for API requests
const client = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    data() {
        return {
            // creating sessions and displaying
            sessions: [],
            classTypes: [],
            newSessionLength: null,
            newSessionStartTime: '',
            newSessionEndTime: '',
            newSessionDate: '',
            newSessionRepeatsWeekly: false,
            newSessionMaxParticipants: null,
            newSessionClassType: null,
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
            sessionIdToUpdate: null
        };
    },
    created: async function () {
        // Fetching sessions and class types upon component creation
        console.log('Fetching sessions...');
        try {
            const response = await client.get("/sessions");
            this.sessions = response.data;
            console.log('Fetched sessions:', this.sessions);
        }
        catch (e) {
            console.log('Error fetching sessions:', e);
        }

        console.log('Fetching class types...');

        try {
            const response = await client.get("/classTypes/true"); // Replace "/classTypes" with the actual endpoint
            this.classTypes = response.data;
            console.log('Fetched class types:', this.classTypes);
        }
        catch (e) {
            console.log('Error fetching class types:', e);
        }
    },

    methods: {
        createSessionDto () {
            // Helper function to create a DTO for new sessions
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
            // Helper function to create a DTO for updating sessions
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
            // Function to create a new session
            console.log('Creating session...');
            const newSession = this.createSessionDto();
            try {
                const response = await client.post("/sessions", newSession);
                this.sessions.push(response.data);
                this.clearInputs();
                console.log('Created session:', response.data);
            }
            catch (e) {
                console.log('Error creating session:', e.message);
            }
        },
        openUpdateModal(session) {
            // Opens the update modal with session details for editing
            console.log('Opening update modal for session:', session);
            this.updateSession = Object.assign({}, session);
            this.sessionIdToUpdate = session.id;
            this.isUpdateModalOpen = true;
        },
        updateSessionData: async function () {
            // Updates the session with new details
            console.log('Updating session...');
            try {
                const updatedSession = this.createUpdateSessionDto();
                const response = await client.put(`/sessions/${this.updateSession.id}`, updatedSession);
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
            // Clears form inputs after creating/updating a session
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
            // Computed property to disable the create button if necessary fields are empty
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
</script>
