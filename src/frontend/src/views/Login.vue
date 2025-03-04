<template>
  <div class="form-container">
    <div class="form-card">
      <h2>Login</h2>
      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label for="username">Username:</label>
          <input type="text" v-model="username" id="username" required />
        </div>
        <div class="form-group">
          <label for="password">Password:</label>
          <div class="password-input">
            <input :type="passwordFieldType" v-model="password" id="password" required />
            <span @click="togglePasswordVisibility" class="toggle-password">
              {{ passwordFieldType === 'password' ? 'Show' : 'Hide' }}
            </span>
          </div>
        </div>
        <button type="submit" :disabled="loading">
          <span v-if="loading" class="spinner"></span>
          <span v-else>Login</span>
        </button>
      </form>
      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
      <button class="secondary-button" @click="goToRegister">Register</button>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex';

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Login',
  data() {
    return {
      username: '',
      password: '',
      passwordFieldType: 'password',
    };
  },
  computed: {
    ...mapState('user', {
      loading: state => state.loading,
      errorMessage: state => state.errorMessage,
    }),
  },
  methods: {
    ...mapActions('user', ['login']),

    async handleLogin() {
      try {
        await this.login({username: this.username, password: this.password});
        await this.$router.push('/dashboard');
      } catch (error) {
        console.error('Login failed:', error);
      }
    },

    togglePasswordVisibility() {
      this.passwordFieldType = this.passwordFieldType === 'password' ? 'text' : 'password';
    },

    goToRegister() {
      this.$router.push('/register');
    }
  }
};
</script>

<style scoped>
.form-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f0f2f5;
}

.form-card {
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  padding: 30px;
  width: 100%;
  max-width: 400px;
  text-align: center;
  transition: transform 0.3s ease;
}

.form-card:hover {
  transform: translateY(-5px);
}

h2 {
  margin-bottom: 20px;
  color: #333;
}

.form-group {
  margin-bottom: 1rem;
}

label {
  display: block;
  margin-bottom: 5px;
  color: #555;
}

input {
  display: block;
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  margin-bottom: 1rem;
}

.password-input {
  position: relative;
}

.toggle-password {
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
  color: #007bff;
}

button {
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: 4px;
  background-color: #007bff;
  color: white;
  cursor: pointer;
  width: 100%;
  margin-bottom: 1rem;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

button:hover:not(:disabled) {
  background-color: #0056b3;
}

.secondary-button {
  background-color: #6c757d;
}

.secondary-button:hover {
  background-color: #5a6268;
}

.spinner {
  border: 2px solid #f3f3f3;
  border-top: 2px solid #007bff;
  border-radius: 50%;
  width: 14px;
  height: 14px;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

.error-message {
  color: #dc3545;
  margin-bottom: 1rem;
}
</style>