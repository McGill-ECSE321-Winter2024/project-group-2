
<template>
    <div id="sportcenter">
        <Navbar />
        <div id="grantAccountPermissions">
            <h2>Promote/Demote Instructors</h2>
            <button @click="getPersons()">View non-instructor accounts</button>
            <button @click="getInstructors()">View instructors accounts</button>
            <br>
            <input v-model="newInstructor" type="number" placeholder="Customer's personID to promote">
            <button v-bind:disabled="!newInstructor" @click='promoteToInstructor(newInstructor)'>Promote</button>
            <br>
            <h4 class="error" v-if="currentView.length==0">No people found!</h4>
            <table v-if="currentView.length!=0" class="center">
                <tr>
                    <th class="rowName">Name</th>
                    <th>Instructor id number</th>
                    <th>Person account number</th>
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
                        <button v-if="person.instructorId=='N/A'" @click="promoteToInstructor(Number(person.personId))">Promote to Instructor</button>
                        <button v-if="person.instructorId!='N/A'" @click="demoteInstructor(person.instructorId)">Demote Instructor</button>
                    </td>
                </tr>
            </table>
            <p>
                <span v-if="instructorSuccessMessage" style="color:green">{{ instructorSuccessMessage }}</span> 
            </p>
            <p>
                <span v-if="instructorErrorMessage" style="color:red">Error: {{ instructorErrorMessage }}</span>
            </p>
        </div>
        <div class="center">
            <h2>Manage Class Types</h2>
            <input v-model="newClassType" type="text" placeholder="New Class Type">
            <button @click="suggestClassType(newClassType)">Suggest New ClassType</button>
            <h4 class="error" v-if="suggestedClassTypes.length==0"> No suggested class types to review!</h4>
            <table v-else class="center">

                <tr>
                    <th>Suggested Class Type</th>
                </tr>
                <tr v-for="classType in suggestedClassTypes">
                    <td>
                        {{ classType.name }}
                    </td>
                    <td>
                        <button @click="approveClassType(classType.name)">Approve</button>
                        <button @click="rejectClassType(classType.name)">Disapprove</button>
                    </td>
                </tr>
            </table>

            <p>
                <span v-if="typeSuccessMessage" style="color:green">{{ typeSuccessMessage }}</span>
            </p>
            <p>
                <span v-if="typeErrorMessage" style="color:red">Error: {{ typeErrorMessage }}</span>
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
            <button @click="createSession()" :disabled="isCreatebtnDisabled">Create Session</button>
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
            <button @click="updateSessionData()">Update</button>
            <button @click="isUpdateModalOpen = false">Cancel</button>
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
                        <button @click="openUpdateModal(session)">Update</button>
                        <button @click="deleteSession(session.id)">Cancel</button>
                    </td>
                </tr>
            </table>
        
        </div>
    </div>
</template>

<script src="./registration.js">

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
  th, td{
    padding-right:12px;
  }
  .error{
    color:red;
  }
  .center{
    margin-left: auto;
    margin-right: auto;
  }
</style>
