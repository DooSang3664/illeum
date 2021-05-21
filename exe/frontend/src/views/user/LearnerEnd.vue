<template>
  <div class="lec-ready overflow-y-auto" style="height:43rem">
    <div v-if="learnerDoing == true">
      <!-- 1 -->
      <!-- <div v-for="(item, idx) in classLi" :key="idx">
        <div v-if="new Date(item.end_time) < Date.now() && item.room_state == '완료'">
          <div class="blog-card">
            <input type="radio" name="select" id="tap-1" checked />
            <input type="checkbox" id="imgTap" />

            <div class="inner-part">
              <label for="imgTap" class="img">
                <v-img :src="'/profile/' + item.founderUid + '/256'" id="preview" alt=""></v-img>
              </label>
              <div class="content content-1">
                <span>클래스명:{{ item.room_name }}</span>

                <div class="title">강의자:{{ item.founder }}</div>
                <div class="text">진행단계:{{ item.room_state }}</div>
                <div class="text">공개여부:{{ item.room_type }}</div>
                <div class="text">시작시간:{{ item.start_time }}</div>
                <div class="text">종료시간:{{ item.end_time }}</div>
                <v-btn class="button" x-large style=" padding: 20px 30px; font-size: 1.2rem; margin-left: 200px;" color="#6173FF" @click="startRTC(item)">강의시작</v-btn>
              </div>
            </div>
          </div>
        </div>
      </div> -->
      <v-row no-gutters>
        <template v-for="(item, idx) in classLiFilter">
          <v-col :key="idx" xl="6" lg="12">
            <v-card flat color="#f9f9f9" width="700">
              <div class="blog-card">
                <div class="inner-part">
                  <label for="imgTap" class="img">
                    <v-img :src="'/profile/' + item.founderUid + '/256'" id="preview" alt=""></v-img>
                  </label>
                  <div class="content">
                    <div class="class-name">{{ item.room_name }}</div>

                    <div class="title">
                      <v-icon>mdi-account-circle</v-icon>
                      {{ item.founder }}
                    </div>
                    <div class="text">
                      <v-icon>mdi-clock-start</v-icon>
                      <span class="detail-label">
                        진행여부
                      </span>
                      <span>{{ item.room_state }}</span>
                    </div>
                    <div class="text">
                      <v-icon>mdi-lock-open-outline</v-icon>
                      <span class="detail-label">
                        공개여부
                      </span>
                      <span>
                        {{ item.room_type }}
                      </span>
                    </div>
                    <div class="text">
                      <v-icon>mdi-arrow-right-bold-hexagon-outline</v-icon>
                      <span class="detail-label">
                        강의시작
                      </span>
                      {{ item.start_time }}
                    </div>
                    <div class="text">
                      <v-icon>mdi-exit-to-app</v-icon>
                      <span class="detail-label">
                        강의종료
                      </span>
                      <span>
                        {{ item.end_time }}
                      </span>
                    </div>
                    <!-- 버튼 -->
                    <v-btn class="button" large color="#FF625C" depressed @click="evalShow(item)">
                      내평가보기
                    </v-btn>
                  </div>
                </div>
              </div>
            </v-card>
          </v-col>
          <v-responsive v-if="idx % 2 == 1" :key="`width-${idx}`" width="100%"></v-responsive>
        </template>
      </v-row>
    </div>
    <div v-else>
      <p>수강진행중인 강좌가 없음</p>
    </div>
    <v-dialog v-if="$store.state.token" v-model="dialog" hide-overlay max-width="900px">
      <v-card flat>
        <v-card>
          <v-toolbar flat color="#6173FF" dark>
            <v-toolbar-title>
              <span>{{ modalEach.name }}님의</span>
              평가조회
            </v-toolbar-title>
          </v-toolbar>
          <v-tabs vertical color="#6173FF">
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
                <div v-if="modalEach.attend != '결석'" style="margin:1rem 0; ">
                  <v-icon left large style="margin-bottom:1rem;" color="#2E95FF">
                    mdi-alarm
                  </v-icon>
                  <span style="font-size:2rem; color:#2E95FF ;">{{ modalEach.attend_time }}</span>
                  에 출석하셨습니다!
                </div>

                <div style=" margin: 2rem 1rem;">
                  출결상태는
                  <span :class="{ normal: modalEach.attend == '정상', late: modalEach.attend == '지각', afk: modalEach.attend == '결석' }" style="font-size:2rem;">
                    {{ modalEach.attend }}
                  </span>
                  입니다.
                </div>
              </v-card>
            </v-tab-item>
            <v-tab-item>
              <v-card flat style="width:30%; margin: auto;">
                <div v-if="modalEach.attend != '결석'">
                  <MyPagePieChart :learnData="learnData" :key="modalEach.vid + 'D'" />
                  <MyPageRadarChart :learnData="learnData" :averageData="averageData" :key="modalEach.vid + 'E'" />
                </div>
                <div v-else>
                  결석한 수업이므로 평가가 조회되지 않습니다.
                </div>
              </v-card>
            </v-tab-item>
            <v-tab-item>
              <v-card flat>
                <div v-if="modalEach.attend != '결석'">
                  <LecUserPartin :each="modalEach" :roomData="modalEach" :evalUserCnt="evalUserCnt" :rid="ridSelected" :key="modalEach.vid + 'F'"></LecUserPartin>
                </div>
                <div v-else>
                  결석한 수업이므로 평가가 조회되지 않습니다.
                </div>
              </v-card>
            </v-tab-item>
          </v-tabs>
        </v-card>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn large color="#6173FF" dark @click="dialog = false">
            Close
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import { userEvalList } from '@/api/auth.js';
import { fetchRoomname, evaluateList } from '@/api/class';
import { findCount } from '@/api/entrant';
import MyPagePieChart from '@/views/components/MyPagePieChart';
import MyPageRadarChart from '@/views/components/MyPageRadarChart';
import LecUserPartin from '@/views/evaluation/LecUserPartin';
export default {
  components: { MyPagePieChart, MyPageRadarChart, LecUserPartin },
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
      evalcheck: true,
      zeroPartinchk: false,
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
      modalEach: {},
      ridSelected: '',
      change: 0,
      renderKey: 1,
      attendchk: false,
    };
  },
  computed: {
    classLiFilter: function() {
      return this.classLi.filter((i) => new Date(i.end_time) < Date.now() && i.room_state == '완료');
    },
  },
  async created() {
    const { data } = await userEvalList(this.$store.state.uuid);
    if (data) {
      this.learnerDoing = true;
      this.classLi = data;
    }
  },
  methods: {
    async evalShow(value) {
      this.modalEach = value;
      this.modalEach.uid = this.$store.state.uuid;
      this.dialog = true;
      //출결
      this.attend_time = value.attend_time;
      this.attend = value.attend;
      this.attendchk = false;
      //평가초기화
      this.roomName = '';
      this.partinRank = [];
      this.attendRank = [];
      this.partuidRank = '';
      this.attenduidRank = '';
      this.evalUserCnt = '';
      this.fetchRoomlen = 0;
      //평가
      this.learnData[0].per = value.attention;
      this.learnData[1].per = value.distracted;
      this.learnData[2].per = value.asleep;
      this.learnData[3].per = value.afk;
      this.learnData[4].per = value.participation;
      if (value.attend != '결석') {
        this.attendchk = true;
      }
      const { data } = await fetchRoomname(value.room_name);
      const roomPartinUser = data[0].rid;
      this.ridSelected = data[0].rid;
      const res = await evaluateList(roomPartinUser);
      const res2 = await findCount(roomPartinUser);

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

      this.roomName = value.room_name;
      this.partinRank = [];
      this.attendRank = [];

      this.evalUserCnt = String(res2.data);
    },
  },
};
</script>

<style scoped>
@font-face {
  font-family: 'GongGothicLight';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-10@1.0/GongGothicLight.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: 'GongGothicLight';
}
.blog-card {
  display: grid;
  height: 280px;
  width: 100%;
  max-width: 650px;
  margin: 30px 0px;
  border-radius: 15px;
  background: white;
  box-shadow: 0px 5px 10px rgba(0, 0, 0, 0.5);
}
.inner-part {
  display: flex;
  height: 280px;
  align-items: center;
  /* justify-content: flex-end; */
  padding: 20px;
}

.inner-part .img {
  height: 240px;
  width: 240px;
  flex-shrink: 0;
  overflow: hidden;
  border-radius: 20px;
  box-shadow: 2px 3px 15px rgba(0, 0, 0, 0.3);
}

.content {
  display: flex;
  flex-direction: column;
  /* align-items: center; */
  padding: 30px;
  width: 380px;
  margin-top: 4rem;
  /* margin-left: 40px; */

  transition: 0.6s;
}

.content .class-name {
  position: absolute !important;
  font-family: 'GongGothicLight';
  color: #2e79ff;
  font-size: 2rem;
  /* right: 50px; */
  top: 20px;
  text-align: left;
  font-weight: bolder;
}
.content .title {
  position: absolute !important;
  font-size: 30px;
  font-weight: 700;
  color: #0d0925;
  right: 60px;
  top: 60px;
  text-align: right;
}
.content .text {
  color: #4e4a67;
  margin: 2px 5px;
  text-align: justify;
  overflow: hidden;
}
.content .button {
  display: inline-flex;
  padding: 35px 40px;
  font-size: 16px;
  text-transform: uppercase;
  color: #fff0e6;
  font-weight: 600;
  letter-spacing: 1px;
  border-radius: 50px;
  cursor: pointer;
  outline: none;
  margin-top: 10px;
  border: 1px solid #1976d2;
  background: #1976d2;
}
.content .button:hover {
  background: #1976d2;
}
.content .text .detail-label {
  font-weight: bold;
  font-size: 1rem;

  border-radius: 50px;

  padding: 4px 10px;
}
input[type='radio'],
input[type='checkbox'] {
  display: none;
}
.partin-each {
  margin: 0.5rem 0px;
}
#app > div:nth-child(15) {
  box-shadow: none !important;
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
