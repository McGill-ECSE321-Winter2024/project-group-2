<template>
    <div id="sportcenter">
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
        <div>
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
        <div class="center">
            <h2>Manage sessions</h2>
            <h4 class="error" v-if="sessions.length==0">No sessions exist in the system. Create one using the form below</h4>
            <table v-else>
                <tr>
                    <th>Session ID</th>
                    <th>Date</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                    <th>Session Duration</th>
                    <th>Session Capacity</th>
                    <th>Instructor's ID</th>
                    <th>Class Type</th>
                </tr>
                <tr v-for="session in sessions">
                    <td>{{ session.id }}</td>
                    <td>{{ session.classType }}</td>
                    <td>{{ session.date }}</td>
                    <td>{{ session.startTime }}</td>
                    <td>{{ session.endTime }}</td>
                    <td>{{ session.length }} minutes</td>
                    <td>{{ session.capacity }} </td>
                    <td>{{ session.instructorId }}</td>
                    <td>
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