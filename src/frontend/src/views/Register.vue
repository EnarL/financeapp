<template>
  <div class="form-container">
    <div class="form-card">
      <h2>Register</h2>
      <p v-if="successMessage" class="success-message">{{ successMessage }}</p>
      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
      <form @submit.prevent="handleRegister">
        <div class="form-group">
          <input type="text" v-model="username" placeholder="Username" required>
        </div>
        <div class="form-group">
          <input type="email" v-model="email" placeholder="Email" required>
        </div>
        <div class="form-group">
          <input type="password" v-model="password" placeholder="Password" required>
        </div>
        <button type="submit" :disabled="loading">Register</button>
      </form>
      <button class="secondary-button" @click="goToLogin">Go to Login</button>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex';

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Register',
  data() {
    return {
      username: '',
      email: '',
      password: '',
      successMessage: '',
    };
  },
  computed: {
    ...mapState('user', {
      loading: state => state.loading,
      errorMessage: state => state.errorMessage,
    }),
  },
  methods: {
    ...mapActions('user', ['register']),

    async handleRegister() {
      try {
        await this.register({
          username: this.username,
          email: this.email,
          password: this.password
        });

        this.successMessage = 'Registration successful! Please log in.';
        this.errorMessage = '';

        this.username = '';
        this.email = '';
        this.password = '';

        setTimeout(() => {
          this.$router.push('/');
        }, 3000);
      } catch (error) {
        console.error('Registration failed:', error);
        this.successMessage = '';
      }
    },

    goToLogin() {
      this.$router.push('/');
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

input {
  display: block;
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  margin-bottom: 1rem;
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

button:hover {
  background-color: #0056b3;
}

.secondary-button {
  background-color: #6c757d;
}

.secondary-button:hover {
  background-color: #5a6268;
}

.success-message {
  color: #28a745;
  margin-bottom: 1rem;
}

.error-message {
  color: #dc3545;
  margin-bottom: 1rem;
}
</style>