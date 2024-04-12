<template>
   <!-- Main container for the Login page -->
    <div class="login-container">
      <!-- Navbar component -->
      <Navbar />
      <!-- Card for the login form -->
      <div class="login-card">
        <!-- Form title -->
        <h1>Login</h1>
        <!-- Form subtitle -->
        <p class="login-subtitle">Stay updated with your training!</p>
        <!-- Form for logging in. The login method is called when the form is submitted -->
        <form @submit.prevent="login" >
          <!-- Form group for the email input -->
          <div class="form-group">
            <!-- Email input. The v-model directive binds the input to the email property of the credentials object -->
            <input type="text" id="email" v-model="credentials.email" placeholder="Email" required>
          </div>
          <!-- Form group for the password input -->
          <div class="form-group">
            <!-- Password input. The v-model directive binds the input to the password property of the credentials object -->
            <input :type="passwordType" id="password" v-model="credentials.password" placeholder="Password" required>
            <!-- Button for toggling the visibility of the password -->
            <button type="button" class="password-toggle" @click="togglePassword">{{ showPassword ? 'hide' : 'show' }}</button>
          </div>
          <!-- Login button -->
          <button type="submit" class="btn-login">Login</button>
           <!-- Error message for login -->
          <a class="error" v-if="loginError">Invalid email or password! </a>
        </form>
        <!-- Prompt for users who don't have an account -->
        <div class="signup-prompt">
            New to Montreal Sport? <a href="#" @click.prevent="goToCreateAccount" class="signup-link">Join now</a>
        </div>
      </div>
    </div>
  </template>
  
<script>
// Importing the Navbar and Footer components
import axios, { Axios } from 'axios' // Axios for making HTTP requests
import config from "../../config" // Configuration file
import Navbar from './Navbar.vue' // Navbar component
import Footer from './Footer.vue' // Footer component

// Constructing URLs for frontend and backend from config
const frontendUrl = 'http://'+config.dev.host+':'+config.dev.port
const backendUrl = 'http://'+config.dev.backendHost+':'+config.dev.backendPort

const AXIOS = axios.create({
    baseURL: backendUrl,
    headers: {'Access-Control-Allow-Origin':frontendUrl}
})

export default {
  components: {
    Navbar,
    Footer
  },
    name: 'Login',
    data() {
      return {
        credentials: {
          email: '',
          password: ''
        },
        passwordType: 'password',
        showPassword: false,
        loginError: false
      };
    },
    methods: {
      login() {
        let myMap = new Map();

        // Adding key-value pairs to the map
        myMap.set("email", this.credentials.email);
        myMap.set("password", this.credentials.password);
        AXIOS.post('/login',Object.fromEntries(myMap)).then(response => {
            this.credentials.email = '';
        this.credentials.password = '';
            if (response.data !== -1 && response.status===200){
              localStorage.setItem('customerVsInstructor', response.data)
              AXIOS.get('/personslogin/'.concat(myMap.get('email'))).then(person =>{
                let id = person.data;
                localStorage.setItem('personId',id);
                console.log(id);
                if (response.data == 2 ){
                AXIOS.get('/instructors/'.concat(id)).then(ins => {
                  localStorage.setItem('roleId',ins.data);
                })
                AXIOS.get('/customers/'.concat(id)).then(response => {
                  localStorage.setItem('instructorsCustomersId', response.data);
                })
              }
              else if (response.data==3){
                AXIOS.get('/customers/'.concat(id)).then(cust => {
                  
                  localStorage.setItem('roleId',cust.data);
                })
                localStorage.setItem('instructorsCustomersId', -1);
              }
              else if (response.data == 1){
                localStorage.setItem('roleId',0);
                localStorage.setItem('instructorsCustomersId', -1);
              }
              this.$router.push('/');
              })
              this.loginError = false;
              
              }
            else{
              
              this.loginError = true;
              //this.$router.push('/Login');
            }
            
        }).catch(e =>{
            this.loginError = true;
            console.log(e)
            console.log(e.response)
            const errorMsg = e.response.data.message
            console.log(errorMsg)
            this.instructorErrorMessage = errorMsg
        })
       
      },
      togglePassword() {
        this.showPassword = !this.showPassword;
        this.passwordType = this.showPassword ? 'text' : 'password';
      },
      goToCreateAccount() {
      this.$router.push('/CreateAccount');
      }
    }
  }
  </script>
  
  <style scoped>
   /* Styles for the forgot password link */
  .login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f3f2ef;
}

.login-card {
  background: #fff;
  padding: 2rem;
  border-radius: 8px;
  width: 350px;
  box-shadow: 0 4px 10px rgba(0,0,0,0.1);
}

.login-card h1 {
  margin-bottom: 0.5rem;
}

.login-subtitle {
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
.error {
    color: #a94442; /* red color */
    background-color: #f2dede; /* light red background */
    border: 1px solid #ebccd1; /* red border */
    padding: 15px; /* space inside the box */
    margin-bottom: 20px; /* space below the box */
    border-radius: 5px; /* rounded corners */
    text-align: center; /* center the text */
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
  
  /* Adjust the .btn-login margin if needed */
  .btn-login {
    margin-top: 2rem; /* Add margin on top since the forgot password link is removed */
  }
  </style>
  