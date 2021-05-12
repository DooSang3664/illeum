<template>
  <div>
    <span>{{ this.$store.state.uuid }}의 평가목록</span>
    <v-row justify="center">
      <v-date-picker v-model="date" @click:date="classNameFetch" :landscape="landscape" locale="ko-kr" :allowed-dates="allowedDates" class="mt-4" min="1900-04-01" max="2100-10-30"></v-date-picker>
      <v-col class="d-flex" cols="12" sm="6">
        <v-select :items="items" :label="date" solo @input="showEvaluation"></v-select>
      </v-col>
    </v-row>
    <v-row>
      <h2>show evaluation</h2>
      <h2>{{ date }}</h2>
      <div v-if="evalcheck">
        <div v-if="attendchk">
          <EvalPieChart :learnData="learnData" :key="change" />
        </div>
        <div v-else>
          <h3>결석한 수업이므로 데이터가 없습니다.</h3>
        </div>
        <EvalRadarChart :learnData="learnData" :averageData="averageData" :key="renderKey" />
      </div>
    </v-row>
  </div>
</template>

<script>
import { userEvalList } from '@/api/auth';
import { fetchRoomname, evaluateList } from '@/api/class';
import EvalPieChart from '@/views/components/EvalPieChart';
import EvalRadarChart from '@/views/components/EvalRadarChart';
export default {
  components: {
    EvalPieChart,
    EvalRadarChart,
  },

  data() {
    return {
      uuid: this.$store.state.uuid,
      eval: [],
      items: [],
      date: '',
      landscape: true,
      menu: false,
      arrayDates: [],
      roomName: [],
      evalcheck: false,
      averageData: [
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

      learnData: [
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
      renderKey: 1,
      attendchk: false,
      choiceClass: '',
    };
  },

  async created() {
    const { data } = await userEvalList(this.uuid);
    console.log(data);
    for (var i = 0; i < data.length; i++) {
      var eval_date = data[i].eval_date.slice(0, 10);
      this.arrayDates.push(eval_date);
    }
    this.eval = data;
    this.items = [];
    this.evalcheck = false;
    this.attendchk = false;
  },
  methods: {
    classNameFetch() {
      this.items = [];
      for (var i = 0; i < this.eval.length; i++) {
        if (this.eval[i].eval_date.slice(0, 10) === this.date && !this.items.includes(this.eval[i].room_name)) {
          this.items.push(this.eval[i].room_name);
        }
      }
    },
    allowedDates(val) {
      for (var i = 0; i < this.arrayDates.length; i++) {
        if (this.arrayDates[i] == val) {
          return true;
        }
      }
    },
    async showEvaluation(selected) {
      this.per1 = 0;
      this.per2 = 0;
      this.per3 = 0;
      this.per4 = 0;
      this.per5 = 0;
      this.attendchk = false;
      this.learnData = [
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
      ];
      this.averageData = [
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
      ];
      const { data } = await fetchRoomname(selected);
      const roomPartinUser = data[0].rid;
      const res = await evaluateList(roomPartinUser);

      for (var j = 0; j < res.data.length; j++) {
        this.per1 += res.data[j].attention;
        this.per2 += res.data[j].distracted;
        this.per3 += res.data[j].asleep;
        this.per4 += res.data[j].afk;
        this.per5 += res.data[j].participation;
      }
      // 소수둘째자리에서 반올림해서 소수첫째자리까지 보여줌
      this.averageData[0].per = (this.per1 / res.data.length).toFixed(1);
      this.averageData[1].per = (this.per2 / res.data.length).toFixed(1);
      this.averageData[2].per = (this.per3 / res.data.length).toFixed(1);
      this.averageData[3].per = (this.per4 / res.data.length).toFixed(1);
      this.averageData[4].per = (this.per5 / res.data.length).toFixed(1);
      for (var i = 0; i < this.eval.length; i++) {
        if (this.eval[i].attend == '정상' && this.date == this.eval[i].eval_date.slice(0, 10)) {
          console.log(this.eval[i].attend);
          console.log(this.eval[i].eval_date.slice(0, 10));
          console.log(this.date);
          this.evalcheck = true;
          this.attendchk = true;
          if (this.eval[i].room_name == selected && this.date == this.eval[i].eval_date.slice(0, 10)) {
            this.learnData[0].per = this.eval[i].attention;
            this.learnData[1].per = this.eval[i].distracted;
            this.learnData[2].per = this.eval[i].asleep;
            this.learnData[3].per = this.eval[i].afk;
            this.learnData[4].per = this.eval[i].participation;
            console.log(this.learnData);
          }
          break;
        } else {
          this.attendchk = false;
        }
        this.change++;
        this.renderKey++;
      }
    },
  },
};
</script>

<style></style>
