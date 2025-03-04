<template>
  <div :class="['dashboard', { dark: isDark }]">
    <TransactionList title="Expenses" :transactions="expenses" :isExpense="true" :delete-transaction="deleteExpense" @update="fetchExpenses"></TransactionList>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex';
import TransactionList from "@/components/TransactionList.vue";
import { useDark } from "@vueuse/core";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Dashboard',
  components: {
    TransactionList,
  },
  setup() {
    const isDark = useDark();
    return { isDark };
  },
  computed: {
    ...mapState({
      expenses: state => state.expenses.expenses,
      loading: state => state.expenses.loading,
      error: state => state.expenses.error,
      username: state => state.user.username,
      token: state => state.user.token
    })
  },
  mounted() {
    this.fetchData();
  },
  methods: {
    ...mapActions({
      fetchExpenses: 'expenses/fetchExpenses',
      addExpense: 'expenses/addExpense',
      deleteExpense: 'expenses/deleteExpense',
    }),
    async fetchData() {
      try {
        await Promise.all([this.fetchExpenses()]);
      } catch (error) {
        console.error('Failed to fetch data:', error);
      }
    }
  }
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap');

* {
  margin: 0;
  padding: 0;
  height:100vh;
  box-sizing: border-box;
}

.dashboard {
  background-color: lightblue;
  text-align: center;
  font-family: 'Roboto', sans-serif;
  padding: 20px;
  color: #333;
  height:100%;

}
@Media(min-width: 768px) {
  .dashboard {
    margin-left: 200px;
  }
}
h1 {
  margin-bottom: 2rem;
  color: #006270;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
}

input {
  display: block;
  width: 100%;
  height:100%;
  margin-bottom: 1rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  transition: border-color 0.3s ease;
}

input:focus {
  border-color: #009394;
}

button {
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease;
}

button:hover {
  background-color: #009394;
  transform: translateY(-2px);
}

button:active {
  background-color: #006270;
  transform: translateY(0);
}

/* Dark mode styles */
.dark .dashboard {
  background-color: #2e3a46;
  color: #fff;
}

.dark h1 {
  color: #fff;
  text-shadow: 1px 1px 2px rgba(255, 255, 255, 0.1);
}

.dark input {
  border: 1px solid #555;
  background-color: #444;
  color: #fff;
}

.dark input:focus {
  border-color: #009394;
}

.dark button {
  background-color: #444;
  color: red;
}

.dark button:hover {
  background-color: #009394;
}

.dark button:active {
  background-color: #006270;
}
</style>