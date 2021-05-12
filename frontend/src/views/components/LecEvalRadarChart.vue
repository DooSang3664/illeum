<script>
import { Radar } from 'vue-chartjs'; // npm vue-chart.js 기반 차트 컴포넌트

export default {
  extends: Radar,
  props: ['LeclearnData', 'LecAverageData'],
  data() {
    return {
      chartData: {
        hoverBackgroundColor: 'red',
        hoverBorderWidth: 10,
        labels: [],
        beginAtZero: true,
        datasets: [
          {
            label: '내점수',
            data: [],
            stepSize: 1,
            backgroundColor: 'rgba(54, 162, 235, 0.2)',
            borderColor: 'rgb(54, 162, 235)',
            pointBackgroundColor: 'rgb(54, 162, 235)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: '#fff',
            pointHoverBorderColor: 'rgb(54, 162, 235)',
          },
          {
            label: '평균',
            data: [],
            fill: true,
            backgroundColor: 'rgba(255, 99, 132, 0.2)',
            borderColor: 'rgb(255, 99, 132)',
            pointBackgroundColor: 'rgb(255, 99, 132)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: '#fff',
            pointHoverBorderColor: 'rgb(255, 99, 132)',
          },
        ],
      },
    };
  },

  created() {
    for (let i = 0; i < this.LecAverageData.length; i++) {
      this.chartData.labels.push(this.LecAverageData[i].data);
      this.chartData.datasets[1].data.push(this.LecAverageData[i].per);
    }
    for (let i = 0; i < this.LeclearnData.length; i++) {
      // this.chartData.labels.push(this.learnData[i].data);

      this.chartData.datasets[0].data.push(this.LeclearnData[i].per);
    }
  },
  mounted() {
    // 실제 차트 랜더링 부분
    this.renderChart(this.chartData, {
      scale: {
        ticks: {
          max: 100,
          min: 0,
          stepsize: 10,
          beginAtZero: true,
        },
      },
    });
  },
};
</script>
