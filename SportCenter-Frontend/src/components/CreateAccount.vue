<template>
  <!-- Container for the create account form -->
  <div class="createAccount-container">
    <Navbar />
    <!-- Card for the create account form -->
    <div class="createAccount-card">
       <!-- Form title -->
      <h1>Create Account</h1>
      <!-- Form subtitle -->
      <p class="createAccount-subtitle">Join us and stay updated with your training!</p>
      <!-- Form for creating an account. The createAccount method is called when the form is submitted -->
      <form @submit.prevent="createAccount">
        <!-- Form group for the name input -->
        <div class="form-group">
          <!-- Name input. The v-model directive binds the input to the name property of the accountDetails object -->
          <input type="text" id="name" v-model="accountDetails.name" placeholder="Name" required>
        </div>
        <!-- Form group for the email input -->
        <div class="form-group">
          <!-- Email input. The v-model directive binds the input to the email property of the accountDetails object -->
          <input type="text" id="email" v-model="accountDetails.email" placeholder="Email" required>
        </div>
        <!-- Form group for the password input -->
        <div class="form-group">
          <!-- Password input. The v-model directive binds the input to the password property of the accountDetails object -->
          <input :type="passwordType" id="password" v-model="accountDetails.password" placeholder="Password" required>
          <!-- Button for toggling the visibility of the password -->
          <button type="button" class="password-toggle" @click="togglePassword">{{ showPassword ? 'hide' : 'show' }}</button>
        </div>
        <!-- Password requirements -->
        <div>
          <p>Password Requirements</p>
          <ul>
            <li>At least 8 characters</li>
            <li>At least one uppercase letter</li>
            <li>At least one lowercase letter</li>
            <li>At least one number</li>
          </ul>
        </div>
        <!-- Create account button. It's only visible if all the form fields are filled -->
        <button v-if="accountDetails.email!=='' && accountDetails.name!=='' && accountDetails.password!==''"@click='createAccount()'>Create Account</button>
      </form>
      <!-- Error message for signup -->
      <h5 class="error" v-if="signupErrorMessage!=''">{{ signupErrorMessage }}</h5>
      <!-- Prompt for users who already have an account -->
      <div class="signup-prompt">
        Already have an account? <a href="#" @click="goToLogin" class="signup-link">Login</a>
      </div>
    </div>
  </div>
</template>

<script>
// Importing necessary modules and components
import axios, { Axios } from 'axios' // Axios for making HTTP requests
import config from "../../config" // Configuration file
import Navbar from './Navbar.vue' // Navbar component

// Constructing URLs for frontend and backend from config
const frontendUrl = 'http://'+config.dev.host+':'+config.dev.port
const backendUrl = 'http://'+config.dev.backendHost+':'+config.dev.backendPort

// Creating an instance of Axios with the backend URL as the base URL
// and allowing cross-origin requests from the frontend URL
const AXIOS = axios.create({
    baseURL: backendUrl,
    headers: {'Access-Control-Allow-Origin':frontendUrl}
})

// Exporting the Vue component
export default {
  // Registering the Navbar component
  components: {
    Navbar
  },
  // Naming the component
    name: 'CreateAccount',
    // Defining the component's data
    data() {
      return {
        accountDetails: {
          email: '',
          password: '',
          name:''
        },
        error:'',
        passwordType: 'password',
        showPassword: false,
        signupErrorMessage: ''
      };
    },
    methods: {
      createPersonDto(){
        const PersonDTO = {
          personId: null,
          password: this.accountDetails.password,
          email: this.accountDetails.email,
          name: this.accountDetails.name
        };
        this.accountDetails.email='';
        this.accountDetails.name='';
        this.accountDetails.password='';
        console.log(PersonDTO);
        return PersonDTO;
      },
        createAccount() {
          const newAccount = this.createPersonDto();
          console.log(newAccount);
          AXIOS.post('/customers',newAccount).then(response => {
            if (response.status==201){
                this.$router.push('/Login')
            }
            
        }).catch(e =>{
            console.log(e)
            console.log(e.response)
            const errorMsg = e.response.data
            console.log(errorMsg)
            this.signupErrorMessage = errorMsg
        })
       
      },
      togglePassword() {
        this.showPassword = !this.showPassword;
        this.passwordType = this.showPassword ? 'text' : 'password';
      },
      goToLogin() {
      this.$router.push({ name: 'Login' });
      }
    }
  }
</script>

<style scoped>
   /* Styles for the CreateAccount component */
  div > p {
    font-size: 16px;
    font-weight: bold;
    color: #333;
    margin-bottom: 10px;
}

ul {
    list-style-type: none;
    padding: 0;
}

ul > li {
    font-size: 14px;
    color: #666;
    margin-bottom: 5px;
}

ul > li:before {
    content: "• ";
    color: #360805; /* Red */
}
  .createAccount-container {
display: flex;
justify-content: center;
align-items: center;
height: 100vh;
background-color: #f3f2ef;
}

.createAccount-card {
background: #fff;
padding: 2rem;
border-radius: 8px;
width: 350px;
box-shadow: 0 4px 10px rgba(0,0,0,0.1);
}

.createAccount-card h1 {
margin-bottom: 0.5rem;
}

.createAccount-subtitle {
margin-bottom: 2rem;
color: #666;
}

.form-group {
margin-bottom: 1rem;
}

.form-group input {
width: 100%;
padding: 10px;
border: 1px solid #ccc;
border-radius: 5px;
margin-bottom: 0.5rem;
}

.password-toggle {
background: none;
border: none;
color: #0073b1;
cursor: pointer;
text-align: left;
}

.btn-login {
width: 100%;
padding: 10px;
border: none;
border-radius: 20px;
background-color: #0073b1;
color: white;
margin-bottom: 1rem;
cursor: pointer;
}

.btn-login.apple {
background-color: #000;
color: white;
}

.divider {
display: flex;
align-items: center;
text-align: center;
margin: 2rem 0;
}

.divider-line {
flex: 1;
border-top: 1px solid #ccc;
}

.divider-text {
margin: 0 1rem;
color: #666;
}

.forgot-password {
color: #0073b1;
display: block;
text-align: right;
margin-bottom: 2rem;
}

.signup-prompt {
text-align: center;
margin-top: 2rem;
}

.signup-link {
color: #0073b1;
}

/* Adjust the .form-group style if needed to account for the removal of the forgot password link */
.form-group {
  margin-bottom: 1rem;
  position: relative; /* Needed for absolute positioning of the toggle button */
}

/* You may want to add padding to the input to make room for the toggle button */
.form-group input {
  padding-right: 60px; /* Adjust as needed */
}

/* Ensure the .password-toggle button doesn't overlap the text when the password is visible */
.password-toggle {
  background: none;
  border: none;
  color: #0073b1;
  cursor: pointer;
  position: absolute;
  right: 10px;
  top: 10px;
}
.error {
    color: #a94442; /* red color */
    background-color: #f2dede; /* light red background */
    border: 1px solid #ebccd1; /* red border */
    padding: 5px; /* space inside the box */
    margin-bottom: 10px; /* space below the box */
    border-radius: 5px; /* rounded corners */
    text-align: center; /* center the text */

    transition-duration: 0.4s;
}
/* Adjust the .btn-login margin if needed */
.btn-login {
  margin-top: 2rem; /* Add margin on top since the forgot password link is removed */
}
</style>
