<template>
  <div class="about">
    <h1>차트 테스트 페이지</h1>
    <br />
    <pie-chart :sampleData="sampleData" />
    <br />
    <div style="height:50%; width:50%; margin:auto">
      <radar-chart :sampleData="sampleData" />
    </div>
  </div>
</template>

<script>
import PieChart from '@/views/components/PieChart';
import RadarChart from '@/views/components/RadarChart';
export default {
  components: {
    PieChart,
    RadarChart,
  },
  data() {
    return {
      sampleData: [
        {
          data: '수학',
          score: '90',
        },
        {
          data: '과학',
          score: '70',
        },
        {
          data: '국어',
          score: '60',
        },
        {
          data: '영어',
          score: '50',
        },
        {
          data: '음악',
          score: '80',
        },
      ],
    };
  },
  created() {
    console.log(this.sampleData);
  },
};
</script>

<style></style>
