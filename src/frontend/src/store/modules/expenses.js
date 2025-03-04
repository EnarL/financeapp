import axios from 'axios';

const state = {
    expenses: [],
    loading: false,
    error: null
};

const mutations = {
    SET_EXPENSES(state, expenses) {
        state.expenses = expenses;
    },
    SET_LOADING(state, loading) {
        state.loading = loading;
    },
    SET_ERROR(state, error) {
        state.error = error;
    },
    DELETE_EXPENSE(state, expenseId) {
        state.expenses = state.expenses.filter(expense => expense.id !== expenseId);
    }
};

const actions = {
    async fetchExpenses({ commit, rootState }) {
        commit('SET_LOADING', true);
        try {
            const token = rootState.user.token; // Ensure token is retrieved correctly
            const response = await axios.get(`http://localhost:8080/api/expenses/findall?username=${rootState.user.username}`, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            commit('SET_EXPENSES', response.data);
        } catch (error) {
            commit('SET_ERROR', 'Failed to fetch expenses');
            console.error('Error fetching expenses:', error);
        } finally {
            commit('SET_LOADING', false);
        }
    },
    async addExpense({ dispatch, rootState }, newExpense) {
        const expenseWithUser = { ...newExpense, username: rootState.user.username };
        try {
            const token = rootState.user.token; // Ensure token is retrieved correctly
            await axios.post('http://localhost:8080/api/expenses/add', expenseWithUser, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            await dispatch('fetchExpenses');
        } catch (error) {
            console.error('Error adding expense:', error);
        }
    },
    async deleteExpense({ commit, rootState }, expenseId) {
        try {
            const token = rootState.user.token; // Ensure token is retrieved correctly
            const response = await axios.delete(`http://localhost:8080/api/expenses/delete/${expenseId}`, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            if (response.status === 204) {
                commit('DELETE_EXPENSE', expenseId);
            }
        } catch (error) {
            console.error('Error deleting expense:', error);
        }
    },
    async updateExpense({ rootState }, expense) {
        try {
            const token = rootState.user.token; // Ensure token is retrieved correctly
            await axios.put(`http://localhost:8080/api/expenses/update/${expense.id}`, expense, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
        } catch (error) {
            console.error('Error updating expense:', error);
        }
    }
};

export default {
    namespaced: true,
    state,
    mutations,
    actions
};