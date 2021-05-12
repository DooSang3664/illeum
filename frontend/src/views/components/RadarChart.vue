<script>
import { Radar } from 'vue-chartjs'; // npm vue-chart.js 기반 차트 컴포넌트

export default {
  extends: Radar,
  props: ['sampleData'],
  data() {
    return {
      chartData: {
        hoverBackgroundColor: 'red',
        hoverBorderWidth: 10,
        labels: [],
        datasets: [
          {
            label: 'Data One',
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
    for (let i = 0; i < this.sampleData.length; i++) {
      this.chartData.labels.push(this.sampleData[i].data);
      this.chartData.datasets[0].data.push(this.sampleData[i].score);
    }
  },
  mounted() {
    // 실제 차트 랜더링 부분
    this.renderChart(this.chartData, {
      borderWidth: '10px',
      hoverBackgroundColor: 'red',
      hoverBorderWidth: '10px',
      maintainAspectRatio: false, // false: 상위 div를 무시한 채 창 크기에 따라 크기가 크기가 마음대로 바뀌던 것이 상위 div에 구속된다
      responsive: true,
      width: '50%',
      height: '50%',
    });
  },
};
</script>
