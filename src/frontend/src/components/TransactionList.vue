<template>
  <div :class="['transaction-list', { dark: isDark }]">
    <h2>{{ title }}</h2>
    <input v-model="filterText" placeholder="Filter transactions" />
    <input type="date" v-model="startDate" placeholder="Start date" />
    <input type="date" v-model="endDate" placeholder="End date" />
    <ul>
      <li v-for="transaction in filteredTransactions" :key="transaction.id">
        <span>{{ transaction.date }} - {{ transaction.category || transaction.source }}: {{ transaction.amount }}</span>
        <div class="buttons">
          <button @click="deleteTransaction(transaction.id)">Delete</button>
          <button @click="toggleEditTransaction(transaction)">Edit</button>
        </div>
        <div v-if="selectedTransaction && selectedTransaction.id === transaction.id">
          <h3>Edit Transaction</h3>
          <form @submit.prevent="updateTransaction">
            <input v-if="isExpense" v-model="selectedTransaction.description" placeholder="Description" />
            <input v-model="selectedTransaction.amount" placeholder="Amount" />
            <input v-if="!isExpense" v-model="selectedTransaction.source" placeholder="Source" />
            <button type="submit">Update</button>
          </form>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapActions } from 'vuex';

export default {
  name: 'TransactionList',
  props: {
    title: String,
    transactions: Array,
    deleteTransaction: Function,
    isExpense: Boolean
  },
  data() {
    return {
      filterText: '',
      startDate: '',
      endDate: '',
      selectedTransaction: null
    };
  },
  computed: {
    filteredTransactions() {
      return this.transactions.filter(transaction => {
        const searchText = this.filterText.toLowerCase();
        const transactionDate = new Date(transaction.date);
        const start = this.startDate ? new Date(this.startDate) : null;
        const end = this.endDate ? new Date(this.endDate) : null;

        const matchesText = (transaction.category && transaction.category.toLowerCase().includes(searchText)) ||
            (transaction.source && transaction.source.toLowerCase().includes(searchText));
        const matchesDate = (!start || transactionDate >= start) && (!end || transactionDate <= end);

        return matchesText && matchesDate;
      });
    }
  },
  methods: {
    ...mapActions({
      updateExpense: 'expenses/updateExpense',
      updateIncome: 'incomes/updateIncome'
    }),
    toggleEditTransaction(transaction) {
      if (this.selectedTransaction && this.selectedTransaction.id === transaction.id) {
        this.selectedTransaction = null;
      } else {
        this.selectedTransaction = {...transaction};
      }
    },
    async updateTransaction() {
      if (this.isExpense) {
        await this.updateExpense(this.selectedTransaction);
      } else {
        await this.updateIncome(this.selectedTransaction);
      }
      this.selectedTransaction = null;
      this.$emit('update');
    }
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap');

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;

}
.transaction-list input {
  width: 100%;
  padding: 10px;
  margin-bottom: 15px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.transaction-list ul {
  list-style-type: none;
  padding: 0;
}

.transaction-list li {
  display: flex;
  flex-direction: column;
  padding: 10px 0;
  border-bottom: 1px solid #ddd;
}

.transaction-list li:last-child {
  border-bottom: none;
}

.transaction-list span {
  flex-grow: 1;
  text-align: left;
}

.transaction-list .buttons {
  display: flex;
  gap: 10px;
}

.transaction-list button {
  background-color: #ff4d4d;
  color: white;
  border: none;
  border-radius: 4px;
  padding: 5px 10px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.transaction-list button:hover {
  background-color: #cc0000;
}

/* Dark mode styles */
.dark .transaction-list {
  background-color: #2e3a46;
  color: #fff;
}

.dark .transaction-list input {
  background-color: #444;
  border: 1px solid #555;
  color: #fff;
}

.dark .transaction-list li {
  border-bottom: 1px solid #555;
}

.dark .transaction-list button {
  background-color: #444;
  color: #fff;
}

.dark .transaction-list button:hover {
  background-color: #009394;
}
</style>