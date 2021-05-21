<template>
  <div class="user-eval">
    <v-row>
      <p class="guide-text">
        평가조회를 위해
        <strong style="letter-spacing:1px">①날짜를 체크하고 ②클래스를 선택</strong>
        해주세요.
      </p>
    </v-row>
    <v-row style="margin-bottom:1rem;">
      <v-col cols="6">
        <div class="class-label">
          <v-icon color="white">mdi-calendar-today</v-icon>
          날짜선택
        </div>
        <v-date-picker
          v-model="date"
          width="520"
          @click:date="classNameFetch"
          :landscape="landscape"
          color="#FF625C"
          :allowed-dates="allowedDates"
          :weekday-format="getDay"
          :month-format="getMonth"
          :header-date-format="headerDate"
          :title-date-format="titleDate"
          class="mt-4"
          style="margin-left:-1.5rem;"
          min="1900-04-01"
          max="2100-10-30"
        ></v-date-picker>
      </v-col>
      <v-col cols="6" sm="6">
        <div class="class-label">
          <v-icon color="white">mdi-school-outline</v-icon>
          수업선택
        </div>
        <v-select style="margin-top: 2%;" :items="items" :label="date" solo @input="showEvaluation"></v-select>
      </v-col>
    </v-row>

    <v-row style="">
      <fieldset class="chart-contain">
        <legend>평가조회</legend>
        <div v-if="evalcheck" style="margin-top: 3%; display:flex; flex-direction: row;  flex-wrap: wrap; justify-content: flex-start; justify-content: space-around;">
          <div v-if="attendchk" style="margin-right:10vw;">
            <div class="label-eval">개인평가</div>
            <EvalPieChart :learnData="learnData" :key="change" />
          </div>
          <div v-else>
            <h3 style="">결석한 수업입니다. 평균데이터만 확인할 수 있습니다.</h3>
          </div>
          <div>
            <div class="label-eval">해당클래스의 평균 및 개인평가</div>
            <EvalRadarChart :learnData="learnData" :averageData="averageData" :key="renderKey" />
          </div>
        </div>
      </fieldset>
    </v-row>
  </div>
</template>

<script>
import { userEvalList } from '@/api/auth';
import { fetchRoomname, evaluateList } from '@/api/class';
import EvalPieChart from '@/views/components/EvalPieChart';
import EvalRadarChart from '@/views/components/EvalRadarChart';

const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];
const monthsOfYear = ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'];

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

    for (var i = 0; i < data.length; i++) {
      var eval_date = data[i].eval_date.slice(0, 10);
      this.arrayDates.push(eval_date);
    }
    this.eval = data;
    this.items = [];
    this.evalcheck = false;
  },
  methods: {
    titleDate(date) {
      return monthsOfYear[new Date(date).getMonth(date)] + ' ' + new Date(date).getDate(date) + '일 <br/>' + daysOfWeek[new Date(date).getDay(date)] + '요일';
    },
    getDay(date) {
      return daysOfWeek[new Date(date).getDay(date)];
    },
    getMonth(date) {
      return monthsOfYear[new Date(date).getMonth(date)];
    },
    headerDate(date) {
      return new Date(date).getFullYear(date) + ' ' + monthsOfYear[new Date(date).getMonth(date)];
    },
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
      this.evalcheck = false;
      this.selectedRoomName = '';
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
      for (var i = 0; i < this.eval.length; i++) {
        if (this.eval[i].room_name === selected && this.date == this.eval[i].eval_date.slice(0, 10)) {
          this.selectedRoomName = selected;
          const { data } = await fetchRoomname(this.eval[i].room_name);
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
          if (this.eval[i].attend != '결석' && this.date == this.eval[i].eval_date.slice(0, 10)) {
            this.evalcheck = true;
            this.attendchk = true;
            if (this.eval[i].room_name == selected && this.date == this.eval[i].eval_date.slice(0, 10)) {
              this.learnData[0].per = this.eval[i].attention;
              this.learnData[1].per = this.eval[i].distracted;
              this.learnData[2].per = this.eval[i].asleep;
              this.learnData[3].per = this.eval[i].afk;
              this.learnData[4].per = this.eval[i].participation;
            }
            break;
          }
        }
        this.change++;
        this.renderKey++;
      }
    },
  },
};
</script>

<style scoped>
.user-eval {
  margin: 3% 2%;
  background: #f9f9f9;
}
.label-eval {
  /* border: 1px solid #41ea93; */
  background: rgb(255, 98, 92);
  color: #fff;
  padding: 0.5rem 0rem;
  font-size: 1.3rem;
  border-top-left-radius: 20px;
  border-top-right-radius: 20px;
  /* box-shadow: 2px 3px 15px rgba(0, 0, 0, 0.2); */
}
.class-label {
  width: 30%;
  height: 3rem;
  font-size: 1.2rem;
  letter-spacing: 2px;
  background: rgb(255, 98, 92);
  border: 0px solid black;
  border-radius: 50px;

  padding: 12px 1rem;
  margin-right: 1rem;
  color: white;
  border-top-left-radius: 0px;
  border-bottom-left-radius: 0px;
}

fieldset {
  width: 100%;
  display: flex;
  justify-content: center;
  border: 0px solid black;
  background: #fff;

  border-radius: 20px;
  border-top: #2e95ff solid 1rem;
  padding-bottom: 2rem;
  margin-left: 0.8rem;
  box-shadow: 2px 3px 15px rgba(0, 0, 0, 0.2);
}
legend {
  background: #2e95ff;

  padding-bottom: 0.5rem;
  padding-top: -3rem;
  font-size: 1.5rem;
  box-sizing: border-box;

  width: inherit;
  color: #fff;
  border-top: #2e95ff solid 0.5rem;
}
.guide-text {
  margin: 0.5rem auto;
}
</style>
