
<template>
    <div id="sportcenter">
        <Navbar />
        <h1>Manager Dashboard</h1>
        <div id="grantAccountPermissions">
            <h2>Promote/Demote Instructors</h2>
            <button class="view" @click="getPersons()">View non-instructor accounts</button>
            <button class="view" @click="getInstructors()">View instructors accounts</button>
            <br>
            <input v-model="newInstructor" type="number" placeholder="#PersonID">
            <button class="promote" v-bind:disabled="!newInstructor" @click='promoteToInstructor(newInstructor)'>Promote</button>
            <br>
            <h4 class="error" v-if="currentView.length==0">No people found!</h4>
            <table v-if="currentView.length!=0" class="center">
                <tr>
                    <th class="rowName">Name</th>
                    <th>Instructor id number</th>
                    <th>Person account number</th>
                    <th>Action</th>
                </tr>
                <tr v-for="person in currentView" :key="person.name">
                    <td>{{ person.name  }}</td>
                    <td>
                        #{{ person.instructorId }}
                    </td>
                    <td>
                        #{{ person.personId }}
                  </td>
                    <td>
                        <button class="promote" v-if="person.instructorId=='N/A'" @click="promoteToInstructor(Number(person.personId))">Promote to Instructor</button>
                        <button class="demote" v-if="person.instructorId!='N/A'" @click="demoteInstructor(person.instructorId)">Demote Instructor</button>
                    </td>
                </tr>
            </table>
            <p>
                <span class="success" v-if="instructorSuccessMessage" style="color:green">{{ instructorSuccessMessage }}</span> 
            </p>
            <p>
                <span class="error" v-if="instructorErrorMessage" style="color:red">Error: {{ instructorErrorMessage }}</span>
            </p>
        </div>
        <div class="center">
            <h2>Manage Class Types</h2>
            <input class="suggest" v-model="newClassType" type="text" placeholder="New Class Type">
            <button class="suggest" @click="suggestClassType(newClassType)">Suggest New ClassType</button>
            <h4 class="error" v-if="suggestedClassTypes.length==0"> No suggested class types to review!</h4>
            <table v-else class="center">

                <tr>
                    <th>Suggested Class Type</th>
                    <th>Approve or Disapprove</th>
                </tr>
                <tr v-for="classType in suggestedClassTypes">
                    <td>
                        {{ classType.name }}
                    </td>
                    <td>
                        <button class="promote" @click="approveClassType(classType.name)">Approve</button>
                        <button class="demote" @click="rejectClassType(classType.name)">Disapprove</button>
                    </td>
                </tr>
            </table>

            <p>
                <span class="success" v-if="typeSuccessMessage" style="color:green">{{ typeSuccessMessage }}</span>
            </p>
            <p>
                <span class="error" v-if="typeErrorMessage" style="color:red">Error: {{ typeErrorMessage }}</span>
            </p>
        </div>
        <h2>Schedule New Session</h2>
        <div>
            <input type="time" placeholder="Start Time" v-model="newSessionStartTime" />
            <input type="time" placeholder="End Time" v-model="newSessionEndTime" />
            <input type="date" placeholder="Date" v-model="newSessionDate" />
            <input type="checkbox" v-model="newSessionRepeatsWeekly" /> Repeats Weekly
            <select v-model="newSessionClassType">
                <option v-for="classType in classTypes" :value="classType">{{ classType.name }}</option>
            </select>
            <input type="text" placeholder="Length" v-model="newSessionLength" />
            <input type="text" placeholder="Max Participants" v-model="newSessionMaxParticipants" />
            <button class="suggest" @click="createSession()" :disabled="isCreatebtnDisabled">Create Session</button>
            <h5 class="success" v-if="createSessionSuccess!=''">{{ createSessionSuccess }}</h5>
            <h4 class="error" v-if="createSessionError!=''">{{ createSessionError }}</h4>
        </div>
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
            <button class=" confirm" @click="updateSessionData()">Update</button>
            <button class="demote" @click="isUpdateModalOpen = false">Cancel</button>
        </div>
        <div class="center">
            <h2>Manage sessions</h2>
            <h4 class="error" v-if="sessions.length==0">No sessions exist in the system. Create one using the form below</h4>
            <table class="center" v-else>
                <tr>
                    <th>Session ID</th>
                    <th>Class Type</th>
                    <th>Date</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                    <th>Session Duration</th>
                    <th>Session Capacity</th>
                    <th>Instructor's ID</th>
                    <th>Update or Cancel</th>
                </tr>
                <tr v-for="session in sessions">
                    <td>{{ session.id }}</td>
                    <td>{{ session.classType.classType }}</td>
                    <td>{{ session.date }}</td>
                    <td>{{ session.startTime }}</td>
                    <td>{{ session.endTime }}</td>
                    <td>{{ session.length }} minutes</td>
                    <td>{{ session.maxParticipants }} </td>
                    <td>{{ session.instructorId }}</td>
                    <td>
                        <button class="confirm" @click="openUpdateModal(session)">Update</button>
                        <button class="demote" @click="deleteSession(session.id)">Cancel</button>
                    </td>
                </tr>
            </table>
        
        </div>
    </div>
</template>

<script src="./registration.js">

</script>
<style>
 h1 {
    color: #333;
    text-decoration: underline;
    font-size: 48px;
    font-weight: bold;
    text-align: center;
    margin-bottom: 20px;
}
h2 {
    color: #333;
    font-size: 24px;
    font-weight: bold;
    text-align: left;
    margin: 20px 0;
    padding: 10px;
    background-color: #f2f2f2;
    border-bottom: 2px solid #4CAF50;
}
  span {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
  }
  .rowName{
    margin-right: 300px;
  }
  th, td{
    padding-right:12px;
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
input{
    width:15%;
    padding:3px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
    margin-bottom: 5px;
    margin-top: 5px;
}
input.suggest {
    width: 40%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
    margin-bottom: 10px;
}
button.promote {
    background-color: #4CAF50; /* Green */
    border: none;
    color: white;
    padding: 10px 20px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 14px;
    margin: 4px 2px;
    cursor: pointer;
    transition-duration: 0.4s;
}

button.promote:hover {
    background-color: #45a049;
    color: white;
}

button.confirm{
    border: none;
    color: white;
    padding: 10px 20px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 14px;
    margin: 4px 2px;
    cursor: pointer;
    transition-duration: 0.4s;
    background-color: #f29b05;
}
button.confirm:hover{
    background-color: #ff8001;

}
button.demote {
    background-color: #f44336; /* Red */
    border: none;
    color: white;
    padding: 10px 20px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 14px;
    margin: 4px 2px;
    cursor: pointer;
    transition-duration: 0.4s;
}
button.demote:hover {
    background-color: #da190b;
    color: white;
}
button.view{
    background-color: #4250be;
    border: none;
    color: white;
    padding: 10px 20px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 14px;
    margin: 4px 2px;
    cursor: pointer;
    transition-duration: 0.4s;
}

button.view:hover {
    background-color: #27b1b8;
}

table {
    border-collapse: collapse;
    margin-bottom: 20px;
}

table, th, td {
    border: 1px solid #ddd;
}

th, td {
    text-align: center;
    padding: 8px;
}

th {
    background-color: #6b589c;
    color: white;
}

tr:nth-child(even) {
    background-color: #f2f2f2;
}
</style>