<template>
  <div class="charts-container">
    <div class="chart-card">
      <canvas id="monthlyIncomesChart"></canvas>
      <canvas id="monthlyExpensesChart"></canvas>
    </div>
  </div>
</template>

<script>
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

export default {
  name: 'ChartsContainer',
  props: {
    expenses: Array,
    incomes: Array
  },
  mounted() {
    this.updateMonthlyExpensesChart();
    this.updateMonthlyIncomesChart();
  },
  watch: {
    expenses: 'updateMonthlyExpensesChart',
    incomes: 'updateMonthlyIncomesChart'
  },
  methods: {
    updateMonthlyExpensesChart() {
      const ctx = document.getElementById('monthlyExpensesChart').getContext('2d');

      // Aggregate expenses data by month
      const monthData = this.expenses.reduce((acc, expense) => {
        const month = new Date(expense.date).getMonth();
        acc[month] = (acc[month] || 0) + parseFloat(expense.amount);
        return acc;
      }, {});

      const months = Object.keys(monthData).map(month => new Date(0, month).toLocaleString('default', {month: 'long'}));
      const amounts = Object.values(monthData);

      const gradient = ctx.createLinearGradient(0, 0, 0, 400);
      gradient.addColorStop(0, 'rgba(0, 98, 112, 0.5)');
      gradient.addColorStop(1, 'rgba(0, 98, 112, 0)');

      // Destroy the previous chart if it exists
      if (this.monthlyExpensesChart) {
        this.monthlyExpensesChart.destroy();
      }

      // Create the chart
      this.monthlyExpensesChart = new Chart(ctx, {
        type: 'line',
        data: {
          labels: months,
          datasets: [{
            label: 'Expenses',
            data: amounts,
            borderColor: '#006270',
            backgroundColor: gradient,
            fill: true,
            tension: 0.4,
            pointBackgroundColor: '#006270',
            pointBorderColor: 'white',
            pointHoverBackgroundColor: 'white',
            pointHoverBorderColor: '#006270',
            pointRadius: 3,
            pointHitRadius: 10
          }]
        },
        options: {
          scales: {
            x: { grid: { display: false } },
            y: {
              beginAtZero: true,
              grid: { color: 'rgba(200, 200, 200, 0.2)' }
            }
          },
          responsive: true,
          plugins: {
            legend: {
              display: true,
              position: 'top',
              labels: { color: '#006270' }
            },
            title: {
              display: true,
              text: 'Monthly Expenses by Month',
              color: '#006270',
              font: { size: 18 }
            },
            tooltip: {
              backgroundColor: 'rgba(0, 98, 112, 0.8)',
              titleColor: 'white',
              bodyColor: 'white',
              callbacks: {
                label: function(tooltipItem) {
                  return tooltipItem.label + ': $' + tooltipItem.raw.toFixed(2);
                }
              }
            }
          }
        }
      });
    },
    updateMonthlyIncomesChart() {
      const ctx = document.getElementById('monthlyIncomesChart').getContext('2d');

      // Aggregate income data by month
      const monthData = this.incomes.reduce((acc, income) => {
        const month = new Date(income.date).getMonth();
        acc[month] = (acc[month] || 0) + parseFloat(income.amount);
        return acc;
      }, {});

      const months = Object.keys(monthData).map(month => new Date(0, month).toLocaleString('default', {month: 'long'}));
      const amounts = Object.values(monthData);

      const gradient = ctx.createLinearGradient(0, 0, 0, 400);
      gradient.addColorStop(0, 'rgba(0, 98, 112, 0.5)');
      gradient.addColorStop(1, 'rgba(0, 98, 112, 0)');

      // Destroy the previous chart if it exists
      if (this.monthlyIncomesChart) {
        this.monthlyIncomesChart.destroy();
      }

      // Create the chart
      this.monthlyIncomesChart = new Chart(ctx, {
        type: 'line',
        data: {
          labels: months,
          datasets: [{
            label: 'Incomes',
            data: amounts,
            borderColor: '#006270',
            backgroundColor: gradient,
            fill: true,
            tension: 0.4,
            pointBackgroundColor: '#006270',
            pointBorderColor: 'white',
            pointHoverBackgroundColor: 'white',
            pointHoverBorderColor: '#006270',
            pointRadius: 3,
            pointHitRadius: 10
          }]
        },
        options: {
          scales: {
            x: { grid: { display: false } },
            y: {
              beginAtZero: true,
              grid: { color: 'rgba(200, 200, 200, 0.2)' }
            }
          },
          responsive: true,
          plugins: {
            legend: {
              display: true,
              position: 'top',
              labels: { color: '#006270' }
            },
            title: {
              display: true,
              text: 'Monthly Incomes by Month',
              color: '#006270',
              font: { size: 18 }
            },
            tooltip: {
              backgroundColor: 'rgba(0, 98, 112, 0.8)',
              titleColor: 'white',
              bodyColor: 'white',
              callbacks: {
                label: function(tooltipItem) {
                  return tooltipItem.label + ': $' + tooltipItem.raw.toFixed(2);
                }
              }
            }
          }
        }
      });
    }
  }
}
</script>

<style scoped>
.charts-container {
  width: 80%; /* Reduced width for better alignment */
  margin: 0 auto;
}

.chart-card {
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
  padding: 20px;
}

.chart-card:hover {
  transform: translateY(-5px);
}

/* Dark mode styles */
.dark .chart-card {
  background-color: #2e2e2e;
  color: #fff;
}

.dark canvas {
  background-color: #2e2e2e;
}

@media (max-width: 768px) {
  .charts-container {
    flex-direction: column;
    align-items: center;
  }

  .chart-card {
    width: 100%;
  }
}
</style>
