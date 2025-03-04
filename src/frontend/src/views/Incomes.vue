<template>
  <div :class="['dashboard', { dark: isDark }]">
    <TransactionList title="Incomes" :transactions="incomes" :isExpense="false" :delete-transaction="deleteIncome" @update="fetchIncomes"></TransactionList>
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
      incomes: state => state.incomes.incomes,
      loading: state => state.incomes.loading,
      error: state => state.incomes.error,
      username: state => state.user.username,
      token: state => state.user.token
    })
  },
  mounted() {
    this.fetchData();
  },
  methods: {
    ...mapActions({
      fetchIncomes: 'incomes/fetchIncomes',
      addIncome: 'incomes/addIncome',
      deleteIncome: 'incomes/deleteIncome',
    }),
    async fetchData() {
      try {
        await Promise.all([this.fetchIncomes()]);
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
  box-sizing: border-box;
  height:100vh;
}

.dashboard {
  background-color: lightblue;
  text-align: center;
  margin-left: 200px;
  font-family: 'Roboto', sans-serif;
  color: #333;
  padding: 20px;
  height:100%;
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