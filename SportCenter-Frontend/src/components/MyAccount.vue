<template>
    <div id="sportcenter">
        <div id="yourInfo">
            <h2>Your Information</h2>
            <br>
            <table class="center">
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Email</th>
                </tr>
                <tr>
                    <td>{{ user.person.id }}</td>
                    <td>{{ user.person.name }}</td>
                    <td>{{ user.person.email }}</td>
                </tr>
            </table>
        </div>
        <div id="grantAccountPermissions">
            <h2>Your Registrations</h2>
            <br>
            <h4 class="error" v-if="currentRegistrations.length==0">No registrations found!</h4>
            <table v-if="currentRegistrations.length!=0" class="center">
                <tr>
                    <th class="rowName">Registration Id</th>
                    <th>Session Id</th>
                    <th>Length</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                    <th>Date</th>
                    <th>Class Type</th>
                    <th>Instructor</th>
                    <th></th>
                </tr>
                <tr v-for="registration in currentRegistrations" :key="registration.registrationId">
                    <td>{{ registration.registrationId }}</td>
                    <td>
                        {{ registration.sessionId }}
                    </td>
                    <td>
                        {{ registration.length }}
                  </td>
                  <td>
                        {{ registration.startTime }}
                  </td>
                  <td>
                        {{ registration.endTime }}
                  </td>
                  <td>
                        {{ registration.date }}
                  </td>
                  <td>
                        {{ registration.classType }}
                  </td>
                  <td>
                        {{ registration.instructor }}
                  </td>
                    <td>
                        <button @click="CancelRegistration(String(registration.registrationId))">Cancel Registration</button>
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
    </div>
</template>

<script src="./myaccount.js">

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