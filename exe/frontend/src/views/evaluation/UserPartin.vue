<template>
  <div class="user-partin">
    <v-row>
      <p class="guide-text">
        상대평가 데이터 확인을 위해
        <strong style="letter-spacing:1px">①날짜를 체크하고 ②클래스를 선택</strong>
        해주세요.
      </p>
    </v-row>
    <v-row justify="center">
      <v-col style="" cols="6" sm="6">
        <div class="class-label">
          <v-icon color="white">mdi-calendar-today</v-icon>
          날짜선택
        </div>
        <v-date-picker
          width="540"
          color="#FF625C"
          v-model="date"
          @click:date="classNameFetch"
          :landscape="landscape"
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
      <v-col cols="6" sm="6">
        <div class="class-label class-choice">
          <v-icon color="white">mdi-school-outline</v-icon>
          수업선택
        </div>
        <v-select :items="items" :label="date" solo @input="showPartin" placeholder="클래스를 선택해주세요" style="max-width:85%; margin-left:0.8rem; "></v-select>
        <div v-if="uidcheck" class="alert-contain">
          <v-alert v-if="UserAttendRank != 1000" style="width:80%;  margin-top:2%; margin-left:5%;" color="#2e95ff" border="left" dark>
            해당 수업 총 수강생 {{ fetchRoomlen }}명중에 출석순위는
            <span style="font-size:1.5rem;">{{ UserAttendRank }}위</span>
            입니다
          </v-alert>
          <v-alert v-else border="left" style="width:80%; margin:auto;" color="#2e95ff" dark>수업에 참여하지 않았습니다.</v-alert>
          <v-alert v-if="zeroPartinchk == false" border="left" style="width:80%; margin-top:2%; margin-left:5%; " color="#FF625C " dark>
            해당 수업 총 수강생 {{ fetchRoomlen }}명중에 채팅참여도는
            <span style="font-size:1.5rem;">{{ UserPartinRank }}위</span>
            입니다
          </v-alert>
          <v-alert v-else border="left" style="width:80%; margin-top:2%; margin-left:5%; " color="#FF625C " dark>채팅에 참여하지 않았습니다.</v-alert>
        </div>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12" sm="12">
        <fieldset class="partin-contain">
          <legend>우등생</legend>
          <div>
            <v-avatar class="mb-3" color="grey darken-1" size="150">
              <v-img :src="'/profile/' + attendFirstUid + '/256'" id="preview" alt=""></v-img>
            </v-avatar>
            <div class="attend-first">
              <v-icon color="white">mdi-alarm-check</v-icon>
              출석왕
            </div>
            <p style="font-size:1.7rem; font-weight:bold;  color: #505050;  text-decoration: underline;text-underline-position:under; ">
              {{ attendFirst }}
            </p>
          </div>
          <div style="margin-left: 10%">
            <div>
              <v-avatar class="mb-3" color="grey darken-1" size="150">
                <v-img :src="'/profile/' + partinFirstUid + '/256'" id="preview" alt=""></v-img>
              </v-avatar>
              <div class="chat-first">
                <v-icon color="white">mdi-forum-outline</v-icon>
                채팅참여왕
              </div>
              <p style="font-size:1.7rem; color: #505050; font-weight:bold; text-decoration: underline; text-underline-position:under; ">
                {{ partinFirst }}
              </p>
            </div>
          </div>
        </fieldset>
      </v-col>
    </v-row>
    <!-- <h2>{{ date }}</h2> -->
  </div>
</template>

<script>
import { userEvalList } from '@/api/auth';
import { fetchRoomname, evaluateList } from '@/api/class';
import { findCount } from '@/api/entrant';

const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];
const monthsOfYear = ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'];

export default {
  data() {
    return {
      uuid: this.$store.state.uuid,
      name: '',
      eval: [],
      items: [],
      landscape: true,
      date: '',
      menu: false,

      attendFirst: '',
      attendFirstUid: '',
      attendData: [],

      partinFirst: '',
      partinFirstUid: '',
      partData: [],

      UserPartinRank: '',
      UserAttendRank: '',

      arrayDates: [],

      selectedRoomName: '',
      fetchRoomlen: 0,
      evalcheck: false,
      zeroPartinchk: false,
      uidcheck: false,
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
    async showPartin(selected) {
      this.evalcheck = true;
      this.partData = [];
      this.attendData = [];
      this.fetchRoomlen = 0;
      this.zeroPartinchk = false;
      this.UserAttendRank = '';
      this.selectedRoomName = '';
      for (var i = 0; i < this.eval.length; i++) {
        if (this.eval[i].room_name === selected && this.date == this.eval[i].eval_date.slice(0, 10)) {
          this.selectedRoomName = selected;
          const { data } = await fetchRoomname(this.eval[i].room_name);
          const roomPartinUser = data[0].rid;
          const res = await evaluateList(roomPartinUser);
          const res2 = await findCount(roomPartinUser);

          var maxPartin = 0;
          // this.fetchRoomlen = res.data.length;
          for (var j = 0; j < res.data.length; j++) {
            // 해당 클래스 전체수강생 인원수 구하기 & 배열만들기
            if (res.data[j].eval_date.slice(0, 10) === this.date) {
              this.partData.push({ uid: res.data[j].uid, participation: res.data[j].participation });
              this.attendData.push({ uid: res.data[j].uid, attend_time: res.data[j].attend_time });
              this.fetchRoomlen = res2.data;
              //로그인한 유저 참여도 데이터 있는지 없는지 체크하고 조건부 렌더링 체크
              if (res.data[j].uid != this.$store.state.uuid) {
                this.uidcheck = true;
              }
              //출석1등 청강생 구하기
              if (res.data[j].ranking === 1) {
                this.attendFirst = res.data[j].name;
                this.attendFirstUid = res.data[j].uid;
              }
              //채팅참여도 1위 청강생 구하기
              if (maxPartin < res.data[j].participation) {
                var maxPartin = res.data[j].participation;
                this.partinFirst = res.data[j].name;
                this.partinFirstUid = res.data[j].uid;
              }

              //로그인한 유저 출석 순위 구하기
              if (this.uuid == res.data[j].uid) {
                this.UserAttendRank = res.data[j].ranking;
              }
            }
          }
        }
      }
      console.log(this.partData);
      this.partData.sort(function(a, b) {
        if (a.participation < b.participation) {
          return 1;
        }
        if (a.participation > b.participation) {
          return -1;
        }

        return 0;
      });

      //로그인한 유저 채팅참여도 순위 구하기
      for (var k = 0; k < this.partData.length; k++) {
        if (this.partData[k].uid === this.$store.state.uuid && this.partData[k].participation != 0) {
          this.UserPartinRank = this.partData.indexOf(this.partData[k]) + 1;
          break;
        } else if (this.partData[k].uid === this.$store.state.uuid && this.partData[k].participation == 0) {
          this.zeroPartinchk = true;
        }
      }
    },
  },
};
</script>
<style scoped>
.user-partin {
  margin: 3% 2% 10% 2%;
  background: #f9f9f9;
}
.partin-contain {
  /* display: flex;
  padding: 2rem 5rem 0rem 5rem;
  border: 0px solid rgb(73, 72, 72);

  background: #fff;
  justify-content: space-evenly;
  box-shadow: 0px 3px 15px rgba(0, 0, 0, 0.3);
  margin: 1.5rem 2rem -6rem 1rem;

  border-top: #2e95ff solid 3rem;

  border-top-left-radius: 10px;
  border-top-right-radius: 10px; */
  width: 80%;
  display: flex;
  justify-content: center;
  margin: auto;
  border: 0px solid black;
  background: #fff;
  padding-top: 2rem;
  border-radius: 20px;
  border-top: #2e95ff solid 3.75rem;
  padding-bottom: 2rem;
  box-shadow: 2px 3px 15px rgba(0, 0, 0, 0.2);
}
legend {
  background: #2e95ff;

  padding-bottom: 0.5rem;
  padding-top: -2rem;
  font-size: 1.5rem;
  box-sizing: border-box;

  width: inherit;
  color: #fff;
  border-top: #2e95ff solid 1rem;
}
.alert-contain {
  display: flex;
  width: 100%;
  margin-left: 2rem;
  flex-direction: column;
  padding: 2rem 1rem 0rem rem;
  border: 0px solid rgb(73, 72, 72);
  /* background: #f6f6f6; */
  border-radius: 30px;
  /* box-shadow: 0px 3px 15px rgba(0, 0, 0, 0.3); */
  margin: 1rem auto;
}
.attend-first {
  border: 0px solid #2e95ff;
  background: #2e95ff;
  /* border-top: #2e95ff solid 1rem; */

  /* border-radius: 50px; */
  color: white;
  padding: 0.5rem;
  width: 100%;
  margin: 1rem 1rem 1rem 0rem;
  font-size: 1rem;
  /* margin-bottom: 10%; */
  border-top-left-radius: 0.8rem;
  border-top-right-radius: 0.8rem;
}
.chat-first {
  border: 0px solid #2e95ff;
  background: #2e95ff;

  color: white;
  padding: 0.5rem;
  width: 100%;
  margin: 1rem 1rem 1rem 0rem;
  font-size: 1rem;
  margin-bottom: 10%;
  border-top-left-radius: 0.8rem;
  border-top-right-radius: 0.8rem;
}
.class-label {
  width: 45%;
  height: 3rem;
  font-size: 1.2rem;
  letter-spacing: 2px;
  background: rgb(255, 98, 92);
  border: 0px solid black;
  border-radius: 50px;
  /* margin-right: */
  padding: 12px 2rem;
  margin: 0rem 1rem 0rem 0rem;

  color: white;
  border-top-left-radius: 0px;
  border-bottom-left-radius: 0px;
}
.class-choice {
  margin-left: 0.8rem;
  margin-bottom: 1rem;
}
.guide-text {
  margin: 0.5rem auto;
}
</style>
