import axios from 'axios';

const state = {
    username: localStorage.getItem('username'),
    token: localStorage.getItem('token'),
    loading: false,
    errorMessage: ''
};

const mutations = {
    SET_USER(state, user) {
        state.username = user.username;
        state.token = user.token;
        state.id = user.id;
    },
    CLEAR_USER(state) {
        state.username = '';
        state.token = '';
        state.id = '';
    },
    SET_LOADING(state, loading){
        state.loading = loading;
    },
    SET_ERROR_MESSAGE(state, errorMessage) {
        state.errorMessage = errorMessage;
    }
};

const actions = {
    async register({ commit }, { username, email, password }) {
        commit('SET_LOADING', true);
        try {
            await axios.post('http://localhost:8080/api/users/register', {
                username,
                email,
                password
            });
            console.log('User registered');
        } catch (error) {
            if (error.response && error.response.status === 401) {
                commit('SET_ERROR_MESSAGE', 'Username is already taken');
            } else {
                commit('SET_ERROR_MESSAGE', 'An error occurred during registration');
            }
            throw error;
        } finally {
            commit('SET_LOADING', false);
        }
    },
    async login({ commit }, { username, password }) {
        commit('SET_LOADING', true); // Start loading
        try {
            const response = await axios.post('http://localhost:8080/api/users/login', {
                username,
                password,
            });
            const token = response.data.token;
            localStorage.setItem('username', username);
            localStorage.setItem('token', token);
            commit('SET_USER', { username, token });

        } catch (error) {
            if (error.response && error.response.status === 401) {
                commit('SET_ERROR_MESSAGE', 'Invalid username or password');
            } else {
                commit('SET_ERROR_MESSAGE', 'An error occurred');
            }
            throw error;
        } finally {
            commit('SET_LOADING', false);
        }
    },

    async logout({ commit }) {
        localStorage.removeItem('username');
        localStorage.removeItem('token');
        localStorage.removeItem('id');
        commit('CLEAR_USER');
    },
    async deleteUser({ commit, state }) {
        try {
            const response = await axios.get(`http://localhost:8080/api/users/username/${state.username}`, {
                headers: {
                    'Authorization': `Bearer ${state.token}`
                }
            });
            const userId = response.data.userID;
            await axios.delete(`http://localhost:8080/${userId}`, {
                headers: {
                    'Authorization': `Bearer ${state.token}`
                }
            });
            if (response.status === 200) {
                commit('CLEAR_USER');
                localStorage.removeItem('username');
                localStorage.removeItem('token');
            }
        } catch (error) {
            console.error('Error deleting user:', error);
            throw error;
        }
    }
};
export default {

    namespaced: true,
    state,
    mutations,
    actions
};