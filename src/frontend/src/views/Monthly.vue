<template>
  <div :class="['dashboard', { dark: isDark }]">
    <LineChart :incomes="incomes" :expenses="expenses"></LineChart>
  </div>
</template>

<script>
import {mapActions, mapState} from 'vuex';
import LineChart from "@/components/LineChart.vue";
import {useDark} from "@vueuse/core";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Dashboard',
  components: {
    LineChart
  },
  setup() {
    const isDark = useDark();
    return { isDark };
  },
  computed: {
    ...mapState({
      expenses: state => state.expenses.expenses,
      incomes: state => state.incomes.incomes,
      loading: state => state.expenses.loading || state.incomes.loading,
      error: state => state.expenses.error || state.incomes.error,
      username: state => state.user.username,
      id: state => state.user.id,
      token: state => state.user.token
    })
  },
  mounted() {
    this.fetchData();
  },
  methods: {
    ...mapActions({
      fetchExpenses: 'expenses/fetchExpenses',
      fetchIncomes: 'incomes/fetchIncomes',

    }),
    async fetchData() {
      try {
        await Promise.all([this.fetchExpenses(), this.fetchIncomes()]);
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
  gap: 20px;
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
.dark {
  background-color: #2e3a46;
  color: #fff;
}


.dark h1 {
  color: #fff;
}

.dark input {
  background-color: #444;
  border-color: red;
  color: #fff;
}

.dark input:focus {
  border-color: #009394;
}

.dark button {
  background-color: #444;
  color: #fff;
}

.dark button:hover {
  background-color: #009394;
}

.dark button:active {
  background-color: #006270;
}
</style>