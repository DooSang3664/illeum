<template>
  <div class="lec-user-eval">
    <v-row v-if="evalcheck">
      <LecEvalPieChart style="width:50%;" :LeclearnData="LeclearnData" :key="change" />
      <LecEvalRadarChart style="width:50%;" :LeclearnData="LeclearnData" :LecAverageData="LecAverageData" :key="renderKey" />
    </v-row>
  </div>
</template>

<script>
import LecEvalPieChart from '@/views/components/LecEvalPieChart';
import LecEvalRadarChart from '@/views/components/LecEvalRadarChart';

import { evaluateList } from '@/api/class';
export default {
  components: {
    LecEvalPieChart,
    LecEvalRadarChart,
  },
  data() {
    return {
      evalcheck: true,
      LecAverageData: [
        {
          data: '집중',
          per: 0,
        },
        {
          data: '딴짓',
          per: 0,
        },
        {
          data: '졸음',
          per: 0,
        },
        {
          data: '자리비움',
          per: 0,
        },
        {
          data: '참여도',
          per: 0,
        },
      ],

      LeclearnData: [
        {
          data: '집중',
          per: 0,
        },
        {
          data: '딴짓',
          per: 0,
        },
        {
          data: '졸음',
          per: 0,
        },
        {
          data: '자리비움',
          per: 0,
        },
        {
          data: '참여도',
          per: 0,
        },
      ],
      per1: 0,
      per2: 0,
      per3: 0,
      per4: 0,
      per5: 0,
      change: 0,
      renderKey: -1,
    };
  },
  props: {
    each: {
      type: Object,
    },
    rid: {
      type: Number,
    },
  },
  async created() {
    this.per1 = 0;
    this.per2 = 0;
    this.per3 = 0;
    this.per4 = 0;
    this.per5 = 0;
    // console.log(selected);
    this.evalcheck = true;

    this.LeclearnData[0].per = this.each.attention;
    this.LeclearnData[1].per = this.each.distracted;
    this.LeclearnData[2].per = this.each.asleep;
    this.LeclearnData[3].per = this.each.afk;
    this.LeclearnData[4].per = this.each.participation;

    const res = await evaluateList(this.rid);
    for (var j = 0; j < res.data.length; j++) {
      this.per1 += res.data[j].attention;
      this.per2 += res.data[j].distracted;
      this.per3 += res.data[j].asleep;
      this.per4 += res.data[j].afk;
      this.per5 += res.data[j].participation;
    }
    //소수둘째자리에서 반올림해서 소수첫째자리까지 보여줌
    this.LecAverageData[0].per = (this.per1 / res.data.length).toFixed(1);
    this.LecAverageData[1].per = (this.per2 / res.data.length).toFixed(1);
    this.LecAverageData[2].per = (this.per3 / res.data.length).toFixed(1);
    this.LecAverageData[3].per = (this.per4 / res.data.length).toFixed(1);
    this.LecAverageData[4].per = (this.per5 / res.data.length).toFixed(1);

    this.change++;
    this.renderKey++;
  },
};
</script>

<style scoped>
@font-face {
  font-family: 'NEXON Lv1 Gothic OTF';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv1 Gothic OTF.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}
* {
  font-family: 'NEXON Lv1 Gothic OTF';
}
.lec-user-eval {
  margin: 3% 2%;

  /* background: #f9f9f9; */
}
</style>
