<template>
  <div>
    <v-row>
      <v-col cols="12" sm="6" md="8">
        <Clock></Clock>
        <section class="time-contain">
          <p class="attend-time time-shadow" v-text="currentTime"></p>
        </section>
        <v-col cols="6" md="4">
          <v-date-picker
            v-model="date"
            width="500"
            @click:date="classNameFetch"
            :landscape="landscape"
            locale="ko-kr"
            :allowed-dates="allowedDates"
            class="mt-4"
            min="1900-04-01"
            max="2100-10-30"
          ></v-date-picker>
        </v-col>
      </v-col>
    </v-row>
    <span>{{ this.$store.state.uuid }}의출결현황</span>
    <v-col cols="12" sm="5">
      <v-select :items="items" :label="date" solo @input="showPartin"></v-select>
    </v-col>

    <div style="margin-top: 100px;">
      <h2>show attendence</h2>
      <h2>eval_date:{{ date }}</h2>
      <h2>{{ dayLabel }}</h2>
    </div>

    <div>
      <div>유저이름:{{ this.$store.state.name }}님</div>
      <div>출석시간:{{ attend_time }}</div>
      <div>지각여부:{{ attend }}</div>
    </div>
  </div>
</template>

<script>
import { userEvalList } from '@/api/auth';
import Clock from '@/views/components/Clock.vue';
export default {
  components: {
    Clock,
  },
  data() {
    return {
      uuid: this.$store.state.uuid,
      name: '',
      eval: [],
      items: [],
      week: new Array('일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'),
      landscape: true,
      date: '',
      menu: false,
      mindate: '2021-04-01',
      maxdate: '2021-04-30',
      lateCheck: false,
      attend: '',
      attend_time: '',
      arrayDates: [],
      roomName: [],
      partData: [],
      attendData: [],
      selectedRoomName: '',
      maxUser: '',
      firstUser: '',
      fetchRoomlen: 0,
      evalcheck: false,
      selected: true,
      // digital clock
      currentTime: null,
    };
  },

  computed: {
    dayLabel: function(date) {
      return this.week[new Date(this.date).getDay()];
    },
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
    // digital clock
    this.currentTime = this.$moment().format('LTS');
    setInterval(() => this.updateCurrentTime(), 1 * 1000);
  },
  methods: {
    updateCurrentTime() {
      this.currentTime = this.$moment().format('LTS');
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
    async showPartin(selected) {
      console.log('this eval', this.eval);
      this.evalcheck = true;
      this.partinRank = [];
      this.attendRank = [];
      this.fetchRoomlen = 0;
      this.saveTime = '';
      this.attend_time = '';
      this.attend = '';
      for (var i = 0; i < this.eval.length; i++) {
        if (this.eval[i].room_name == selected && this.date == this.eval[i].eval_date.slice(0, 10)) {
          console.log(this.date);
          console.log(this.eval[i].eval_date.slice(0, 10));
          this.attend_time = this.eval[i].attend_time;
          this.attend = this.eval[i].attend;
          break;
        }
      }
    },
  },
};
</script>

<style lang="scss">
/* digital clock */
$top-color: LightSteelBlue;
$bottom-color: LightSalmon;

section.time-contain {
  // display: flex;
  flex-direction: row;
  // align-items: center;
  padding-top: 140px;
  background: transparent;
}

.attend-time {
  color: #304ffe;
  font-size: 3em;
}

h3.is-3:not(:last-child) {
  margin: 0;
  padding: 0;
}

.time-shadow {
  text-shadow: 0 0 15px rgba(100, 100, 100, 0.35);
}
</style>
