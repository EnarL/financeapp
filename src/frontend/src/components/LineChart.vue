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
import { useDark } from '@vueuse/core';

Chart.register(...registerables);

export default {
  name: 'ChartsContainer',
  props: {
    expenses: Array,
    incomes: Array
  },
  setup() {
    const isDark = useDark();
    return { isDark };
  },
  mounted() {
    this.updateMonthlyExpensesChart();
    this.updateMonthlyIncomesChart();
  },
  watch: {
    expenses: 'updateMonthlyExpensesChart',
    incomes: 'updateMonthlyIncomesChart',
    isDark: 'updateCharts'
  },
  methods: {
    updateCharts() {
      this.updateMonthlyExpensesChart();
      this.updateMonthlyIncomesChart();
    },
    getChartColors() {
      if (this.isDark) {
        return {
          borderColor: '#ffffff',
          backgroundColor: 'rgba(255, 255, 255, 0.5)',
          pointBackgroundColor: '#ffffff',
          pointHoverBorderColor: '#ffffff',
          gridColor: 'rgba(255, 255, 255, 0.2)',
          textColor: '#ffffff',
          tooltipBackgroundColor: 'rgba(255, 255, 255, 0.8)',
          tooltipTitleColor: '#000000',
          tooltipBodyColor: '#000000'
        };
      } else {
        return {
          borderColor: '#006270',
          backgroundColor: 'rgba(0, 98, 112, 0.5)',
          pointBackgroundColor: '#006270',
          pointHoverBorderColor: '#006270',
          gridColor: 'rgba(200, 200, 200, 0.2)',
          textColor: '#006270',
          tooltipBackgroundColor: 'rgba(0, 98, 112, 0.8)',
          tooltipTitleColor: 'white',
          tooltipBodyColor: 'white'
        };
      }
    },
    updateMonthlyExpensesChart() {
      if (this.monthlyExpensesChart) {
        this.monthlyExpensesChart.destroy();
      }
      const ctx = document.getElementById('monthlyExpensesChart').getContext('2d');
      const monthData = this.expenses.reduce((acc, expense) => {
        const month = new Date(expense.date).getMonth();
        if (!acc[month]) {
          acc[month] = 0;
        }
        acc[month] += parseFloat(expense.amount);
        return acc;
      }, {});

      const months = Object.keys(monthData).map(month => new Date(0, month).toLocaleString('default', { month: 'long' }));
      const amounts = Object.values(monthData);

      const colors = this.getChartColors();
      const gradient = ctx.createLinearGradient(0, 0, 0, 400);
      gradient.addColorStop(0, colors.backgroundColor);
      gradient.addColorStop(1, 'rgba(0, 0, 0, 0)');

      this.monthlyExpensesChart = new Chart(ctx, {
        type: 'line',
        data: {
          labels: months,
          datasets: [{
            label: 'Expenses',
            data: amounts,
            borderColor: colors.borderColor,
            backgroundColor: gradient,
            fill: true,
            tension: 0.4,
            pointBackgroundColor: colors.pointBackgroundColor,
            pointBorderColor: 'white',
            pointHoverRadius: 5,
            pointHoverBackgroundColor: 'white',
            pointHoverBorderColor: colors.pointHoverBorderColor,
            pointRadius: 3,
            pointHitRadius: 10
          }]
        },
        options: {
          scales: {
            x: {
              grid: {
                display: false
              },
              ticks: {
                color: colors.textColor
              }
            },
            y: {
              beginAtZero: true,
              grid: {
                color: colors.gridColor
              },
              ticks: {
                color: colors.textColor
              }
            }
          },
          responsive: true,
          plugins: {
            legend: {
              display: true,
              position: 'top',
              labels: {
                color: colors.textColor
              }
            },
            title: {
              display: true,
              text: 'Monthly Expenses by Month',
              color: colors.textColor,
              font: {
                size: 18
              }
            },
            tooltip: {
              backgroundColor: colors.tooltipBackgroundColor,
              titleColor: colors.tooltipTitleColor,
              bodyColor: colors.tooltipBodyColor,
              callbacks: {
                label: function (tooltipItem) {
                  return tooltipItem.label + ': $' + tooltipItem.raw.toFixed(2);
                }
              }
            }
          }
        }
      });
    },
    updateMonthlyIncomesChart() {
      if (this.monthlyIncomesChart) {
        this.monthlyIncomesChart.destroy();
      }
      const ctx = document.getElementById('monthlyIncomesChart').getContext('2d');
      const monthData = this.incomes.reduce((acc, income) => {
        const month = new Date(income.date).getMonth();
        if (!acc[month]) {
          acc[month] = 0;
        }
        acc[month] += parseFloat(income.amount);
        return acc;
      }, {});

      const months = Object.keys(monthData).map(month => new Date(0, month).toLocaleString('default', {month: 'long'}));
      const amounts = Object.values(monthData);

      const colors = this.getChartColors();
      const gradient = ctx.createLinearGradient(0, 0, 0, 400);
      gradient.addColorStop(0, colors.backgroundColor);
      gradient.addColorStop(1, 'rgba(0, 0, 0, 0)');

      this.monthlyIncomesChart = new Chart(ctx, {
        type: 'line',
        data: {
          labels: months,
          datasets: [{
            label: 'Incomes',
            data: amounts,
            borderColor: colors.borderColor,
            backgroundColor: gradient,
            fill: true,
            tension: 0.4,
            pointBackgroundColor: colors.pointBackgroundColor,
            pointBorderColor: 'white',
            pointHoverRadius: 5,
            pointHoverBackgroundColor: 'white',
            pointHoverBorderColor: colors.pointHoverBorderColor,
            pointRadius: 3,
            pointHitRadius: 10
          }]
        },
        options: {
          scales: {
            x: {
              grid: {
                display: false
              },
              ticks: {
                color: colors.textColor
              }
            },
            y: {
              beginAtZero: true,
              grid: {
                color: colors.gridColor
              },
              ticks: {
                color: colors.textColor
              }
            }
          },
          responsive: true,
          plugins: {
            legend: {
              display: true,
              position: 'top',
              labels: {
                color: colors.textColor
              }
            },
            title: {
              display: true,
              text: 'Monthly Incomes by Month',
              color: colors.textColor,
              font: {
                size: 18
              }
            },
            tooltip: {
              backgroundColor: colors.tooltipBackgroundColor,
              titleColor: colors.tooltipTitleColor,
              bodyColor: colors.tooltipBodyColor,
              callbacks: {
                label: function (tooltipItem) {
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
  width: 100%;
  margin: 0 auto;
}

@media (min-width: 768px) {
  .charts-container {
    width: 80%;
    height: 100%;
  }
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