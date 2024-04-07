<template>
    <div id="sportcenter">
        <h2>Promote/Demote Instructors</h2>
        <table>
            <tr>
                <th>Name</th>
                <th>Instructor Id</th>
                <th>Person Id</th>
            </tr>
            <tr v-for="person in currentView" :key="person.name">
                <td>{{ person.name  }}</td>
                <td>
                    {{ person.instructorId }}
                </td>
                <td>
                    {{ person.personId }}
                </td>
                <td>
                    <button v-if="person.instructorId=='N/A'" @click="promoteToInstructor(Number(person.personId))">Promote to Instructor</button>
                    <button v-if="person.instructorId!='N/A'" @click="demoteInstructor(person.instructorId)">Demote Instructor</button>
                </td>
            </tr>
            <tr>
                <td>
                    <input v-model="newPerson" type="text" placeholder="Person Name">
                </td>
                <td>
                    <button @click="getPersons()">Show all people</button>
                    <button @click="getInstructors()">Show all instructors</button>
                </td>
                <td>
                    <input v-model="newInstructor" type="number" placeholder="Person ID to promote">
                </td>
                <td>
                    <button v-bind:disabled="!newInstructor" @click='promoteToInstructor(newInstructor)'>Promote</button>
                </td>
            </tr>
        </table>
        <p>
            <span v-if="successMessage" style="color:green">{{ successMessage }}</span> 
        </p>
        <p>
            <span v-if="errorMessage" style="color:red">Error: {{ errorPerson }}</span>
        </p>
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
</style>