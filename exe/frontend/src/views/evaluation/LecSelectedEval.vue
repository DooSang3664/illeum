<template>
  <div class="mypage-lecselect">
    <v-alert class="text-start font-weight-black" border="left" dark color="#2E95FF" style="font-size: 1.5rem; letter-spacing: 2.3px; padding-left:20px; line-height: 45px;" elevation="3" height="70">
      <span>
        <v-icon>mdi-account-check</v-icon>
        {{ roomName }}
      </span>
      수업 평가관리
    </v-alert>
    <v-row>
      <p class="guide-text">
        날짜를 클릭하면 해당 날짜에 참여한 학생들의 평가데이터를 조회하실 수 있습니다.
      </p>
    </v-row>
    <v-row>
      <v-col cols="6" sm="6">
        <div class="class-label">
          <v-icon color="white">mdi-calendar-today</v-icon>
          날짜선택
        </div>
        <v-date-picker
          v-model="date"
          width="400"
          color="#FF625C"
          @click:date="showAll"
          :landscape="true"
          :allowed-dates="allowedDates"
          :weekday-format="getDay"
          :month-format="getMonth"
          :header-date-format="headerDate"
          :title-date-format="titleDate"
          class="mt-4"
          min="1900-04-01"
          max="2100-10-30"
        ></v-date-picker>
      </v-col>
      <!-- 필터검색 -->
      <v-col cols="6" sm="6" class="chip-search" style="margin:auto;">
        <v-card class="mx-auto" max-width="700">
          <v-toolbar flat color="rgb(255, 98, 92)" class="" style="padding:0.1rem; color:#fff;">
            <v-toolbar-title style="letter-spacing: 1px;">
              <v-icon color="#fff">mdi-account-search-outline</v-icon>
              빠른검색
            </v-toolbar-title>
          </v-toolbar>
          <v-container class="py-1">
            <v-row align="center" justify="start">
              <v-col v-for="(selection, i) in selections" :key="selection.text" class="shrink">
                <v-chip :disabled="loading" close @click:close="chipClose(selection, i)">
                  <v-icon left v-text="selection.icon"></v-icon>
                  {{ selection.text }}
                </v-chip>
              </v-col>
            </v-row>
          </v-container>

          <v-divider v-if="!allSelected"></v-divider>

          <v-list>
            <template v-for="item in categories">
              <v-list-item v-if="!selected.includes(item)" :key="item.text" :disabled="loading" @click="showSelected(item)">
                <v-list-item-avatar>
                  <v-icon :disabled="loading" v-text="item.icon"></v-icon>
                </v-list-item-avatar>
                <v-list-item-title v-text="item.text"></v-list-item-title>
              </v-list-item>
            </template>
          </v-list>

          <v-divider></v-divider>
        </v-card>
      </v-col>
    </v-row>

    <!-- <h2>{{ date }}</h2> -->
    <v-row>
      <div class="table-body">
        <div class="table_responsive">
          <table>
            <thead>
              <tr>
                <th scope="col">No</th>
                <th scope="col">PROFILE</th>
                <th scope="col">NAME</th>
                <th scope="col">E-MAIL</th>

                <th scope="col">date</th>
                <th scope="col">attend</th>
                <th scope="col">ranking</th>
                <th scope="col">participation</th>
                <th scope="col">평가조회</th>
              </tr>
            </thead>

            <tbody>
              <tr v-for="(each, idx) in userEval" :key="idx">
                <td>{{ idx + 1 }}</td>
                <td>
                  <v-avatar class="mb-3" color="grey darken-1" size="50">
                    <v-img :src="`/profile/${each.uid}/256`" id="preview" alt=""></v-img>
                  </v-avatar>
                </td>
                <td>{{ each.name }}</td>
                <td>{{ each.email }}</td>

                <td>{{ each.attend_time }}</td>
                <td>{{ each.attend }}</td>
                <td>{{ each.ranking | checkChatRanking }}</td>
                <td>{{ each.participation }}회 채팅</td>
                <td><v-btn color="#FF625C" depressed dark @click="openModal(each)">평가조회</v-btn></td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </v-row>

    <!-- modal -->
    <v-dialog v-model="dialog" persistent max-width="900">
      <v-card>
        <v-card>
          <v-toolbar flat color="#2E95FF " dark>
            <v-toolbar-title>
              <span>{{ modalEach.name }}님의</span>
              평가조회
            </v-toolbar-title>
          </v-toolbar>
          <v-tabs vertical>
            <v-tab>
              <v-icon left>
                mdi-account
              </v-icon>
              출결
            </v-tab>
            <v-tab>
              <v-icon left>
                mdi-lock
              </v-icon>
              학습태도
            </v-tab>
            <v-tab>
              <v-icon left>
                mdi-access-point
              </v-icon>
              참여현황
            </v-tab>

            <v-tab-item>
              <v-card flat>
                <div style="margin:1rem 0; ">
                  <v-icon left large style="margin-bottom:1rem;" color="#2E95FF">
                    mdi-alarm
                  </v-icon>
                  <span style="font-size:2rem; color:#2E95FF ;">{{ modalEach.attend_time }}</span>
                  에 출석하셨습니다!
                </div>
                <div>
                  출결상태는
                  <span :class="{ normal: modalEach.attend == '정상', late: modalEach.attend == '지각', afk: modalEach.attend == '결석' }" style="font-size:2rem;">{{ modalEach.attend }}</span>
                  입니다.
                </div>
              </v-card>
            </v-tab-item>
            <v-tab-item :key="modalEach.vid + 'A'">
              <v-card flat>
                <LecUserEval :each="modalEach" :rid="rid"></LecUserEval>
              </v-card>
            </v-tab-item>
            <v-tab-item :key="modalEach.vid + 'B'">
              <v-card flat>
                <LecUserPartin :each="modalEach" :roomData="roomData" :evalUserCnt="userEvalLength" :rid="rid"></LecUserPartin>
              </v-card>
            </v-tab-item>
          </v-tabs>
        </v-card>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="dialog = false">
            Close
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import LecUserEval from '@/views/evaluation/LecUserEval';
import LecUserPartin from '@/views/evaluation/LecUserPartin';

import { fetchCondition } from '@/api/evaluation';
import { findByUidClass, findByRidClass } from '@/api/class';

const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];
const monthsOfYear = ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'];

export default {
  components: {
    LecUserEval,
    LecUserPartin,
  },
  data() {
    return {
      chipCheck: {
        isLate: false,
        isAbsent: false,
        isAttendFirst: false,
        isChatFirst: false,
      },
      date: '',
      roomData: null,
      dialog: false,

      rid: null,
      dateList: [], // rid date pair list
      classList: [], // for rid list
      userEval: [], // for table list
      userEvalLength: '', // for table list length
      modalEach: {}, // for modal value
      roomName: '',
      loading: false,
      userEvalAll: [],
      selected: [],
      search: '',
      chips: [
        {
          text: '지각',
          icon: 'mdi-nature',
        },
        {
          text: '결석',
          icon: 'mdi-glass-wine',
        },
        {
          text: '출석1등',
          icon: 'mdi-calendar-range',
        },
        {
          text: '채팅참여1등',
          icon: 'mdi-bike',
        },
      ],
    };
  },
  computed: {
    allSelected() {
      return this.selected.length === this.chips.length;
    },
    categories() {
      const search = this.search.toLowerCase();

      if (!search) return this.chips;

      return this.chips.filter((item) => {
        const text = item.text.toLowerCase();

        return text.indexOf(search) > -1;
      });
    },
    selections() {
      const selections = [];

      for (const selection of this.selected) {
        selections.push(selection);
      }

      return selections;
    },
  },
  watch: {
    selected() {
      this.search = '';
    },
    userEval(val) {
      this.userEval = val;
    },
  },
  filters: {
    checkChatRanking: function(value) {
      if (value == 1000) return '채팅미참여';
      return value + '등';
    },
  },
  async created() {
    this.rid = parseInt(this.$route.query.rid);
    await findByRidClass(this.rid).then(({ data }) => {
      this.roomData = data;
    });
    this.roomName = this.roomData.room_name;
    await fetchCondition({ rid: this.rid }).then(({ data }) => {
      this.userEvalAll = data;
      for (let j = 0; j < data.length; j++) {
        if (!this.dateList.some((e) => e.date == data[j].attend_time.slice(0, 10))) {
          this.dateList.push({ date: data[j].eval_date.slice(0, 10) });
        }
      }
    });
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
    openModal(each) {
      this.modalEach = each;
      this.dialog = true;
    },
    allowedDates(val) {
      for (var i = 0; i < this.dateList.length; i++) {
        if (this.dateList[i].date == val) {
          return true;
        }
      }
    },
    showAll() {
      this.userEval = [];
      this.userEvalLength = '';
      for (let index = 0; index < this.userEvalAll.length; index++) {
        if (this.userEvalAll[index].attend_time.slice(0, 10) == this.date) {
          this.userEval.push(this.userEvalAll[index]);
        }
      }
      this.userEvalLength = String(this.userEval.length);
    },
    async getList(chipCheck) {
      this.userEval = [];
      this.userEvalLength = '';
      var isAbsent = chipCheck.isAbsent ? 1 : 0;
      var isLate = chipCheck.isLate ? 1 : 0;
      var isAttendFirst = chipCheck.isAttendFirst ? 1 : 0;
      var isChatFirst = chipCheck.isChatFirst ? 1 : 0;
      await fetchCondition({ rid: this.rid, isAbsent, isLate, isAttendFirst, isChatFirst }).then(({ data }) => {
        for (let i = 0; i < data.length; i++) {
          if (this.date == data[i].attend_time.slice(0, 10)) {
            this.userEval.push(data[i]);
          }
        }
      });
    },
    next() {
      this.loading = true;

      setTimeout(() => {
        this.search = '';
        this.selected = [];
        this.loading = false;
      }, 1000);
    },
    chipClose(selection, i) {
      this.selected.splice(i, 1);
      if (selection.text == '지각') this.chipCheck.isLate = false;
      if (selection.text == '결석') this.chipCheck.isAbsent = false;
      if (selection.text == '출석1등') this.chipCheck.isAttendFirst = false;
      if (selection.text == '채팅참여1등') this.chipCheck.isChatFirst = false;
      this.getList(this.chipCheck);
    },
    showSelected(item) {
      this.selected.push(item);

      if (item.text == '지각') this.chipCheck.isLate = true;
      if (item.text == '결석') this.chipCheck.isAbsent = true;
      if (item.text == '출석1등') this.chipCheck.isAttendFirst = true;
      if (item.text == '채팅참여1등') this.chipCheck.isChatFirst = true;
      this.getList(this.chipCheck);
      this.$emit('selected', this.selected);
    },
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
.mypage-lecselect {
  margin: 3% 2%;
  font-family: 'NEXON Lv1 Gothic OTF';
}
.table-body {
  min-width: 100%;
}
.table_responsive {
  width: 100%;
  padding: 15px;
  /* overflow: auto; */
  margin: auto;
  border-radius: 4px;
}

table {
  width: 100%;
  font-size: 13px;
  color: #444;
  white-space: nowrap;
  border-collapse: collapse;
}

table > thead {
  background-color: #2e95ff;
  color: #fff;
}

table > thead th {
  padding: 15px;
}

table th,
table td {
  border: 1px solid #0000;
  padding: 10px 5px;
  text-align: center;
}

table > tbody > tr > td > img {
  display: inline-block;
  width: 60px;
  height: 60px;
  object-fit: cover;
  border-radius: 50%;
  border: 4px solid #fff;
  box-shadow: 0 2px 6px #000;
}

.action_btn {
  display: flex;
  justify-content: center;
  gap: 10px;
}

.action_btn > a {
  text-decoration: none;
  color: #444;
  background: #fff;
  border: 1px solid;
  display: inline-block;
  padding: 7px 20px;
  font-weight: bold;
  border-radius: 3px;
  transition: 0.3s ease-in-out;
}

.action_btn > a:nth-child(1) {
  border-color: #26a69a;
}

.action_btn > a:nth-child(2) {
  border-color: orange;
}

.action_btn > a:hover {
  box-shadow: 0 3px 8px #0003;
}

table > tbody > tr {
  background-color: #fff;
  transition: 0.3s ease-in-out;
}

table > tbody > tr:nth-child(even) {
  background-color: rgb(238, 238, 238);
}

table > tbody > tr:hover {
  filter: drop-shadow(0px 5px 10px #0002);
}
.chip-search {
  margin-left: 10em;
}
.action_btn {
  display: flex;
  justify-content: center;
  gap: 10px;
}

.action_btn > a {
  text-decoration: none;
  color: #444;
  background: #fff;
  border: 1px solid;
  display: inline-block;
  padding: 7px 20px;
  font-weight: bold;
  border-radius: 3px;
  transition: 0.3s ease-in-out;
}

.action_btn > a:nth-child(1) {
  border-color: #26a69a;
}

.action_btn > a:nth-child(2) {
  border-color: orange;
}

.action_btn > a:hover {
  box-shadow: 0 3px 8px #0003;
}

table > tbody > tr {
  background-color: #fff;
  transition: 0.3s ease-in-out;
}

table > tbody > tr:nth-child(even) {
  background-color: rgb(238, 238, 238);
}

table > tbody > tr:hover {
  filter: drop-shadow(0px 5px 10px #0002);
}
.chip-search {
  margin-left: 10em;
}
.guide-text {
  margin: 0.5rem auto;
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
.late {
  background: #ff9c6c;
  border: 0px solid black;
  border-radius: 25px;
  font-size: 1rem;
  width: 30%;
  height: 60%;
  padding: 0.5rem 3rem;

  color: white;
}
.afk {
  background: #fc5230;
  border: 0px solid black;
  border-radius: 25px;
  font-size: 1rem;
  width: 30%;
  height: 60%;
  padding: 0.5rem 3rem;

  color: white;
}
.normal {
  background: rgb(46, 149, 255);
  border: 0px solid black;
  border-radius: 25px;
  font-size: 1rem;
  width: 30%;
  height: 60%;
  padding: 0.5rem 3rem;
  color: white;
}
</style>
