<script>
import { Doughnut } from 'vue-chartjs'; // npm vue-chart.js 기반 차트 컴포넌트

export default {
  extends: Doughnut,
  props: ['learnData'],
  data() {
    return {
      chartData: {
        hoverBackgroundColor: 'red',
        hoverBorderWidth: 10,
        labels: [],
        datasets: [
          {
            label: 'Data One',
            backgroundColor: ['#FF625C', '#41EA93', '#61D4ED', '#756BFF', '#2E95FF'],
            data: [],
          },
        ],
      },
    };
  },

  created() {
    for (let i = 0; i < this.learnData.length; i++) {
      this.chartData.labels.push(this.learnData[i].data);
      this.chartData.datasets[0].data.push(this.learnData[i].per);
    }
  },
  mounted() {
    // 실제 차트 랜더링 부분
    this.renderChart(this.chartData, {
      borderWidth: '10px',
      hoverBackgroundColor: 'red',
      hoverBorderWidth: '10px',
      maintainAspectRatio: true,
      responsive: true,
      width: '50%',
      height: '50%',
    });
  },
};
</script>
