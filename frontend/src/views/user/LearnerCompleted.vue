<template>
  <div>
    <h2>수강자-수업시간종료</h2>
    <div v-if="learnerDoing == true">
      <p>수강진행중인 강좌가 있음</p>
      <div v-for="(item, idx) in classLi" :key="idx">
        <div v-if="new Date(item.end_time) < Date.now() && item.room_state == '준비'">
          <h3>VID:{{ item.vid }}</h3>
          <h3>강의자:{{ item.founder }}</h3>
          <v-img :src="'https://k4d106.p.ssafy.io/profile/' + item.founder_uid + '/256'" id="preview" alt="" style="width:100px; height:100px; left:45%;"></v-img>
          <h3>클래스명:{{ item.room_name }}</h3>

          <h3>공개여부:{{ item.room_type }}</h3>
          <h3>시작시간:{{ item.start_time }}</h3>
          <h3>종료시간:{{ item.end_time }}</h3>
          <!-- <v-btn color="primary" @click="evalShow(item)">평가보기</v-btn>
           -->
          <v-dialog v-if="$store.state.token" v-model="dialog" persistent max-width="1200px" :retain-focus="false">
            <template v-slot:activator="{ on, attrs }">
              <v-btn color="primary" dark v-bind="attrs" v-on="on" @click="evalShow(item)">
                내평가보기
              </v-btn>
            </template>
            <v-card>
              <v-card-title>
                <span class="headline">
                  <span>{{ name }}님의</span>
                  Evaluation
                </span>
              </v-card-title>
              <v-card-text>
                <v-container>
                  <v-card>
                    <v-toolbar flat color="primary" dark>
                      <v-toolbar-title>
                        <span>{{ name }}님의</span>
                        Evaluation
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
                          <h2>출결</h2>
                          <h3>출석시간:{{ attend_time }}</h3>
                          <h3>지각여부:{{ attend }}</h3>
                        </v-card>
                      </v-tab-item>
                      <v-tab-item>
                        <v-card flat>
                          <MyPagePieChart :learnData="learnData" :key="change" />
                          <MyPageRadarChart :learnData="learnData" :averageData="averageData" :key="renderKey" />
                        </v-card>
                      </v-tab-item>
                      <v-tab-item>
                        <v-card flat>
                          <div>{{ name }}님</div>
                          <div>{{ roomName }} 수업에서 수업참여도</div>
                          <div>총 수강생 {{ evalUserCnt }}명중에 {{ partuidRank }}위입니다</div>
                          <div>{{ roomName }} 수업에서 출석을</div>
                          <div>총 수강생 {{ evalUserCnt }}명중에 {{ attenduidRank }}위입니다</div>
                        </v-card>
                      </v-tab-item>
                    </v-tabs>
                  </v-card>
                </v-container>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="dialog = false">
                  Close
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
          <hr />
        </div>
      </div>
    </div>
    <div v-else>
      <p>수강진행중인 강좌가 없음</p>
    </div>
  </div>
</template>

<script>
import { userEvalList } from '@/api/auth.js';
import { fetchRoomname, evaluateList } from '@/api/class';
import MyPagePieChart from '@/views/components/MyPagePieChart';
import MyPageRadarChart from '@/views/components/MyPageRadarChart';
export default {
  components: { MyPagePieChart, MyPageRadarChart },
  data() {
    return {
      learnerDoing: false,
      classLi: [],
      dialog: false,
      name: this.$store.state.name,
      attend_time: '',
      attend: '',
      roomName: '',
      change: 0,
      renderKey: 1,
      evalUserCnt: '',
      partinRank: [],
      partuidRank: '',
      attendRank: [],
      attenduidRank: '',
      fetchRoomlen: 0,
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
      resDatalen: 0,
    };
  },
  async created() {
    this.classLi = [];
    this.partinRank = [];
    this.attendRank = [];

    const { data } = await userEvalList(this.$store.state.uuid);

    console.log(this.$store.state.name);
    if (data) {
      this.learnerDoing = true;
      this.classLi = data;
    }
  },

  methods: {
    async evalShow(value) {
      console.log('value', value);
      //출결
      this.attend_time = value.attend_time;
      this.attend = value.attend;
      //평가
      this.learnData[0].per = value.attention;
      this.learnData[1].per = value.distracted;
      this.learnData[2].per = value.asleep;
      this.learnData[3].per = value.afk;
      this.learnData[4].per = value.participation;
      const { data } = await fetchRoomname(value.room_name);
      const roomPartinUser = data[0].rid;
      const res = await evaluateList(roomPartinUser);

      for (var i = 0; i < res.data.length; i++) {
        this.per1 += res.data[i].attention;
        this.per2 += res.data[i].distracted;
        this.per3 += res.data[i].asleep;
        this.per4 += res.data[i].afk;
        this.per5 += res.data[i].participation;
      }

      //소수둘째자리에서 반올림해서 소수첫째자리까지 보여줌
      this.averageData[0].per = (this.per1 / res.data.length).toFixed(1);
      this.averageData[1].per = (this.per2 / res.data.length).toFixed(1);
      this.averageData[2].per = (this.per3 / res.data.length).toFixed(1);
      this.averageData[3].per = (this.per4 / res.data.length).toFixed(1);
      this.averageData[4].per = (this.per5 / res.data.length).toFixed(1);

      //-------------참여도구하기
      // this.evalcheck = true;

      this.roomName = value.room_name;
      this.partinRank = [];
      this.attendRank = [];
      this.fetchRoomlen = 0;

      var maxPartin = 0;
      var first = 100000;
      //채팅참여도1등, 출석1등 구하기
      for (var j = 0; j < res.data.length; j++) {
        this.partinRank.push({ uid: res.data[j].uid, participation: res.data[j].participation });
        // attendRank push 메소드 시간순으로 정렬됨
        this.attendRank.push({ uid: res.data[j].uid, attend_time: res.data[j].attend_time });
        this.attendRank.sort(function(a, b) {
          return a.attend_time < b.attend_time ? -1 : a.attend_time > b.attend_time ? 1 : 0;
        });
        if (maxPartin < res.data[j].participation) {
          var maxPartin = res.data[j].participation;
          this.maxUser = res.data[j].name;
        }

        if (first > res.data[j].ranking) {
          var first = res.data[j].ranking;
          this.firstUser = res.data[j].name;
        }
        if (this.$store.state.uuid === res.data[j].uid) {
          this.name = this.$store.state.name;
          this.fetchRoomlen = res.data.length;
        }
      }
      //채팅참여도 배열 제일 많은순으로 정렬하기
      this.partinRank.sort(function(a, b) {
        if (a.participation < b.participation) {
          return 1;
        }
        if (a.participation > b.participation) {
          return -1;
        }

        return 0;
      });

      //로그인한 청강자 채팅참여도 순위 구하기
      for (var k = 0; k < this.partinRank.length; k++) {
        if (this.partinRank[k].uid === this.$store.state.uuid) {
          this.partuidRank = this.partinRank.indexOf(this.partinRank[k]) + 1;
        }
      }
      //로그인한 청강자 출석시간 순위 구하기

      for (var m = 0; m < this.attendRank.length; m++) {
        if (this.attendRank[m].uid === this.$store.state.uuid) {
          this.attenduidRank = this.attendRank.indexOf(this.attendRank[m]) + 1;
        }
      }
      this.evalUserCnt = this.attendRank.length;
      this.change++;
      this.renderKey++;
    },
  },
};
</script>

<style></style>
