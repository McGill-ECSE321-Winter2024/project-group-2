<template>
  <div class="createAccount-container">
    <Navbar />
    <div class="createAccount-card">
      <h1>Create Account</h1>
      <p class="createAccount-subtitle">Join us and stay updated with your training!</p>
      <form @submit.prevent="createAccount">
        <div class="form-group">
          <input type="text" id="name" v-model="accountDetails.name" placeholder="Name" required>
        </div>
        <div class="form-group">
          <input type="text" id="email" v-model="accountDetails.email" placeholder="Email" required>
        </div>
        <div class="form-group">
          <input :type="passwordType" id="password" v-model="accountDetails.password" placeholder="Password" required>
          <button type="button" class="password-toggle" @click="togglePassword">{{ showPassword ? 'hide' : 'show' }}</button>
        </div>
        <button v-if="accountDetails.email!=='' && accountDetails.name!=='' && accountDetails.password!==''"@click='createAccount()'>Create Account</button>
      </form>
      <h4 class="error" v-if="error!=''">Must put valid information!</h4>
      <div class="signup-prompt">
        Already have an account? <a href="#" @click="goToLogin" class="signup-link">Login</a>
      </div>
    </div>
  </div>
</template>

<script>
import axios, { Axios } from 'axios'
import config from "../../config"
import Navbar from './Navbar.vue'

const frontendUrl = 'http://'+config.dev.host+':'+config.dev.port
const backendUrl = 'http://'+config.dev.backendHost+':'+config.dev.backendPort

const AXIOS = axios.create({
    baseURL: backendUrl,
    headers: {'Access-Control-Allow-Origin':frontendUrl}
})

export default {
  components: {
    Navbar
  },
    name: 'CreateAccount',
    data() {
      return {
        accountDetails: {
          email: '',
          password: '',
          name:''
        },
        error:'',
        passwordType: 'password',
        showPassword: false
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
          if (newAccount == null) {
            this.error = 'not valid';
            this.$router.push('/CreateAccount')
          }
            AXIOS.post('/customers',newAccount).then(response => {
                if (response.status==201){
                    this.$router.push('/Login')
                }
            
        }).catch(e =>{
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
      goToLogin() {
      this.$router.push({ name: 'Login' });
      }
    }
  }
</script>

<style scoped>
  /* Styles */
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

/* Adjust the .btn-login margin if needed */
.btn-login {
  margin-top: 2rem; /* Add margin on top since the forgot password link is removed */
}
</style>
