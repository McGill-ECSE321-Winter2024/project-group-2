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
            <h2>Approve/Disapprove Class Types</h2>
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
  th{
    padding-right:15px;
  }
  .error{
    color:red;
  }
  .center{
    margin-left: auto;
    margin-right: auto;
  }
</style>