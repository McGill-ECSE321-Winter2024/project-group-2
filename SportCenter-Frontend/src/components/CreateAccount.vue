<template>
    <div class="login-container">
      <div class="login-card">
        <h1>Create Account</h1>
        <p class="login-subtitle">Join us and stay updated with your training!</p>
        <form @submit.prevent="createAccount">
          <div class="form-group">
            <input type="text" id="email" v-model="accountDetails.email" placeholder="Email" required>
          </div>
          <div class="form-group">
            <input :type="passwordType" id="password" v-model="accountDetails.password" placeholder="Password" required>
            <button type="button" class="password-toggle" @click="togglePassword">{{ showPassword ? 'hide' : 'show' }}</button>
          </div>
          <button type="submit" class="btn-login">Create Account</button>
        </form>
        <div class="signup-prompt">
          Already have an account? <a href="#" @click="goToLogin" class="signup-link">Login</a>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'CreateAccount',
    data() {
      return {
        accountDetails: {
          email: '',
          password: ''
        },
        passwordType: 'password',
        showPassword: false
      };
    },
    methods: {
      createAccount() {
        this.$router.push('/login');
      },
      togglePassword() {
        this.showPassword = !this.showPassword;
        this.passwordType = this.showPassword ? 'text' : 'password';
      },
      goToLogin() {
        this.$router.push('/login');
      }
    }
  }
  </script>
  
  <style scoped>
    /* Styles */
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
  