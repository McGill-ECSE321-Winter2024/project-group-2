<template>
  <div>
    <h1>Event Registration</h1>
    <h2>Users</h2>
    <div>
      <table>
        <tbody>
          <tr>
            <th>Name</th>
            <th>Verified</th>
          </tr>
          <tr v-for="p in people">
            <td>{{ p.name }}</td>
            <td>{{ p.verified ? "Yes" : "No" }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div>
      <h2>New User</h2>
      <input placeholder="Name" v-model="newPersonName"/>
      <input placeholder="Password" v-model="newPersonPassword"/>
      <button id="create-person-btn" @click="createUser()" v-bind:disabled="isCreateButtonDisabled">Create</button>
    </div>
    <p class="error-msg">{{ errorMessage }}</p>
  </div>
</template>

<script>
export default{
  name: 'Users',
  data () {
    return {
      people: [
        {name: 'Alice', verified: true},
        {name: 'Bob', verified: false}
      ],
      newPersonName: '',
      newPersonPassword: '',
      errorMessage: ''
    }
  },
  methods: {
    createUser () {
      this.errorMessage = ''
      if (!this.newPersonName || !this.newPersonName.trim()) {
        this.errorMessage += 'Missing name. '
      }
      if (!this.newPersonPassword || !this.newPersonPassword.trim()) {
        this.errorMessage += 'Missing password. '
      }
      // Use
      if (this.errorMessage !== '') {
        return
      }
      this.people.push({name: this.newPersonName, verified: false})
      this.newPersonName = ''
      this.newPersonPassword = ''
      this.errorMessage = ''
    }
  },
  computed: {
    isCreateButtonDisabled () {
      return (
        !this.newPersonName ||
        !this.newPersonName.trim() ||
        !this.newPersonPassword ||
        !this.newPersonPassword.trim()
      )
    }
  }
}
</script>

<style>
/* select by id*/
/* #create-person-btn {
    color: darkgreen;
    background-color: lightgreen;
    /*border-color: darkgreen;
    border-style: dotted; 
    border: 1px dotted darkgreen;
} */

#users-component {
  display: flex;
  flex-direction: column;
  align-items: baseline;
  padding: 5em;
}

h1, h2 { /* comma lets you apply the same style to multiple selectors */
    text-decoration: 1px solid underline;
}

table {
    width: 100%;
    border: 1px solid gray;
}

th, td {
    text-align: center;
    border : 1px solid black;
    padding : 5px;
}

.error-msg { /* class selector */
    color : red;
}


body {
    display : flex;
    flex-direction: column;
    align-items: stretch;
}
</style>