<template>
  <div id="app">
    <full-page ref="fullpage" :options="options" id="fullpage">
      <div class="section section1" :style="image_1">
        <div class="">
          <div class="content" v-html="serviceText"></div>
        </div>

        <button style="margin-bottom:0;" @click="moveScroll">NEXT ▼</button>
      </div>
      <!-- 강의자 -->
      <div class="section section2" :style="image_2">
        <div class="mode-label">강의자 메뉴얼</div>
        <div class="slide">
          <h4>
            <span class="step-label" style="font-weight:bold; font-size:1.5rem; background:#2E95FF;">
              1. 클래스만들기
            </span>
          </h4>
          <div>
            <div class="lec-detail">
              강의자는 클래스만들기를 통해 강의 이름, 기간, 시간, 공개여부를 선택하여 클래스를 개설 할 수 있습니다.
            </div>
            <img src="@/assets/img/lecturer1.png" />
          </div>
        </div>
        <div class="slide">
          <h4>
            <span class="step-label" style="font-weight:bold; font-size:1.5rem;  background:#2E95FF;">
              2. 클래스리스트
            </span>
          </h4>
          <div>
            <div class="lec-detail">
              강의자는 클래스 목록에서 생성된 클래스 확인, 수업을 시작 할 수 있습니다.
            </div>
            <img src="@/assets/img/lecturer2.png" />
          </div>
        </div>
        <div class="slide">
          <h4>
            <span class="step-label" style="font-weight:bold; font-size:1.5rem;  background:#2E95FF;">
              3. 클래스시작
            </span>
          </h4>
          <div>
            <div class="lec-detail">
              시작 후 화면 공유, 채팅, 화이트보드를 이용해 수업을 진행하고, AI로 수업 태도 알림 을 받을 수 있습니다.
            </div>
            <img src="@/assets/img/lecturer3.png" />
          </div>
        </div>
        <div class="slide">
          <h4>
            <span class="step-label" style="font-weight:bold; font-size:1.5rem;  background:#2E95FF;">
              4. 클래스평가관리
            </span>
          </h4>
          <div>
            <div class="lec-detail">
              날짜, 강의, 조건 을 선택하면 참여한 청강자의 평가를 조회 할 수 있습니다.
            </div>
            <img src="@/assets/img/lecturer4.png" />
          </div>
        </div>
      </div>
      <!-- 청강자 -->
      <div class="section section3" :style="image_3">
        <div class="mode-label" style="background: #FF625C; letter-spacing:2px;">수강자 메뉴얼</div>
        <div class="slide">
          <h4>
            <span class="step-label" style="font-weight:bold; font-size:1.5rem; background:#FF625C ;">
              1. 수강신청
            </span>
          </h4>
          <div>
            <div class="lec-detail">
              수강해야하는 강의를 신청합니다 비공개방이라면 비밀번호를 입력해야 신청이 완료됩니다.
            </div>
            <img src="@/assets/img/learner1.png" />
          </div>
        </div>
        <div class="slide">
          <h4>
            <span class="step-label" style="font-weight:bold; font-size:1.5rem;background:#FF625C ;">
              2. 수업참여
            </span>
          </h4>
          <div>
            <div class="lec-detail">
              강의시간이되면 수업에 참여할 수 있습니다.
            </div>
            <img src="@/assets/img/learner2.png" />
          </div>
        </div>
        <div class="slide">
          <h4>
            <span class="step-label" style="font-weight:bold; font-size:1.5rem; background:#FF625C ;">
              3. 수업수강
            </span>
          </h4>
          <div>
            <div class="lec-detail">
              시작 후 화면 공유, 채팅, 화이트보드를 이용해 수업을 참여하고, AI로 수업 태도 알림 을 받을 수 있습니다.
            </div>
            <img src="@/assets/img/learner3.png" />
          </div>
        </div>
        <div class="slide">
          <h4>
            <span class="step-label" style="font-weight:bold; font-size:1.5rem; background:#FF625C ;">
              4.평가조회
            </span>
          </h4>
          <div>
            <div class="lec-detail">
              집중, 딴짓, 졸음, 자리 비움에 대한 학습 관리, 출결 및 참여도 관리를 통해 자신의 수업태도를 확인 할 수 있습니다.
            </div>
            <img src="@/assets/img/learner4.png" style="height:70%;" />
          </div>
        </div>
      </div>
      <div class="section section4" :style="image_4">
        <div class="RTC-title">
          <div class="title1">
            <span><img src="@/assets/img/textlogo_2.png" style="dislpaly:inline-block; width:40%; height:100%; margin-bottom:-1rem;" alt="" /></span>
            이 제공하는
            <br />
            화상플랫폼 지원기능
          </div>
          <div class="title2">음성,영상지원뿐만 아니라 다양한 기능을 제공하고 있습니다.</div>
        </div>
        <!-- 화상플랫폼(webRTC) 기능 쌍방향 음성, 영상, 화이트보드, 화면공유, 채팅, 실시간알림, 화면끄기 , -->
        <div class="slide">
          <img src="@/assets/img/emoji.gif" class="g1" style="" />
          <div class="g1-detail">
            <div style="margin-bottom:1.6rem;font-size:3rem; border:0px solid #000; background:#2E95FF; color:white; padding:0.2rem 0.5rem;">1.채팅 및 이모지기능</div>
            <div>해당 기능을 통해 다양한 소통을 해보세요</div>
          </div>
        </div>
        <div class="slide">
          <img src="@/assets/img/whiteboard.gif" class="g2" style="" />
          <div class="g2-detail">
            <div style="margin-bottom:1.6rem;font-size:3rem; border:0px solid #000; background:#2E95FF; color:white; padding:0.2rem 0.5rem; width:62%;">2.화이트보드기능</div>
            <div>
              해당 기능을 활용해서 말하고자 하는 내용을 다양하게 표현해보세요.
              <br />
              손글씨 이외에도, 도형, 이미지 파일을 불러올 수 있습니다.
            </div>
          </div>
        </div>
        <div class="slide">
          <img src="@/assets/img/share.gif" class="g3" style="" />
          <div class="g3-detail">
            <div style="margin-bottom:1.6rem;font-size:3rem; border:0px solid #000; background:#2E95FF; color:white; padding:0.2rem 0.5rem; width:61%;">3.화면공유기능</div>
            <div>
              화상수업플랫폼에 꼭~필요한 기능,
              <br />
              화면공유기능은 학생과 강의자의 양방향 교육이 가능케합니다.
            </div>
          </div>
        </div>
        <div class="slide">
          <img src="@/assets/img/alarm.gif" class="g4" style="" />
          <div class="g4-detail">
            <div style="margin-bottom:1.6rem;font-size:3rem; border:0px solid #000; background:#2E95FF; color:white; padding:0.2rem 0.5rem; width:38%;">4.알람기능</div>
            <div>
              일름은 유저의 편의를 위해
              <br />
              청강생의 학습태도와, 서비스내 활동을 실시간 감지하고 이를 알려줍니다.
            </div>
          </div>
        </div>
      </div>
      <div class="section section5" :style="image_5">
        <!-- <h4 style="color: #535353;  letter-spacing:4px; font-size:2rem;">실시간 학습태도관리</h4> -->
        <ul class="features">
          <div style="display:flex; flex-direction:column;  margin-left:2rem; width:40%">
            <h1 class="AI-title">AI격차가 곧, 공부격차</h1>
            <div style="margin-left:5rem; margin-top:2rem; color:#000; text-align:left;">
              <strong>집중, 딴짓, 졸음, 자리비움</strong>
              총 4가지 태도를 AI로 실시간 감지하여 학습습관을 스마트하게 관리할 수 있습니다.
            </div>
          </div>
        </ul>
        <div>
          <LandingComp></LandingComp>
        </div>
      </div>
      <div class="section section6" :style="image_6">
        <div class="back">
          <div class="section2a">
            <div class="explanation">
              <v-row>
                <v-col cols="10">
                  <img src="@/assets/img/onlinestudy.jpeg" class="monitor" alt="computer" />
                </v-col>
              </v-row>
              <v-row class="section4-content">
                <v-col cols="4">
                  <div>
                    <h1 class="last-h1">출결현황</h1>
                    <p style="font-size:1.2rem; ling-height:3rem;">
                      <strong>illeum</strong>
                      은 수강자가 강의에 입장한 시간을 기준으로 출결기록을 실시간으로 체크하기 때문에 수동으로 출결관리를 하지 않아도 됩니다.
                    </p>
                  </div>
                </v-col>
                <v-col cols="4">
                  <div>
                    <h1 class="last-h1">평가데이터</h1>
                    <p style="font-size:1.2rem; ling-height:3rem;">
                      <strong>illeum</strong>
                      은 수강자, 강의자 모두 자신이 참여/강의한 클래스에 관한 평가데이터를 제공합니다.
                    </p>
                  </div>
                </v-col>
                <v-col cols="4">
                  <div>
                    <h1 class="last-h1">참여도</h1>
                    <p style="font-size:1.2rem; ling-height:3rem;">
                      <strong>illeum</strong>
                      은 수강생의 학습의욕을 고취하고자, 채팅참여기록과 출석기록를 기반한 상대평가 데이터를 제공합니다.
                    </p>
                  </div>
                </v-col>
              </v-row>
            </div>
          </div>
        </div>
      </div>
    </full-page>
  </div>
</template>

<script>
import LandingComp from '@/views/LandingComp.vue';
import FullPage from 'vue-fullpage.js';
export default {
  components: {
    FullPage,
    LandingComp,
  },
  name: 'app',
  data() {
    return {
      options: {
        navigation: true,
        // sectionsColor: ['require(', '#ff5f45', '#0798ec', '#fec401', '#1bcee6', '#ee1a59', '#2c3e4f', '#ba5be9', '#b4b8ab'],
        responsiveWidth: 900,
      },
      serviceText: `<h3 style="color: #535353;"><span style="color:#2E95FF;">원격수업의 질</span>을 높여드리는<span style="color:#FF625C;"> '일름' </span>서비스</h3>
        <p class="section1-p">언택트시대 온라인 학습, 일름이 해답입니다.</p>`,
    };
  },
  props: {
    image1: {
      type: String,
      default: require('@/assets/img/bg5.jpg'),
    },
    image2: {
      type: String,
      default: require('@/assets/img/bg4.jpg'),
    },
    image3: {
      type: String,
      default: require('@/assets/img/bg4.jpg'),
    },
    image4: {
      type: String,
      // default: require('@/assets/img/bg3.jpg'),
    },
    image5: {
      type: String,
      default: require('@/assets/img/enter.jpg'),
    },
    image6: {
      type: String,
      default: require('@/assets/img/bg1_ver2.png'),
    },
  },
  computed: {
    image_1() {
      return {
        backgroundImage: `url(${this.image1})`,
      };
    },
    image_2() {
      return {
        backgroundImage: `url(${this.image2})`,
      };
    },
    image_3() {
      return {
        backgroundImage: `url(${this.image3})`,
      };
    },
    image_4() {
      return {
        // backgroundImage: `url(${this.image4})`,
        backgroundColor: '#fff',
      };
    },
    image_5() {
      return {
        backgroundImage: `url(${this.image5})`,
      };
    },
    image_6() {
      return {
        backgroundImage: `url(${this.image6})`,
      };
    },
  },
  methods: {
    moveScroll() {
      $.fn.fullpage.moveSectionDown();
    },
  },
  destroyed() {
    $.fn.fullpage.destroy('all');
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
@font-face {
  font-family: 'Recipekorea';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/Recipekorea.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}
* {
  font-family: 'NEXON Lv1 Gothic OTF';
}
.section {
  text-align: center;
  font-size: 1.2rem;
}
.section1 {
  z-index: -1;
  opacity: 0.9;
  background-size: cover;
}
.section4 {
  background-size: cover;
  display: flex;
  align-items: flex-start;
  flex-direction: column;
  justify-content: center;
}
.section5 {
  display: flex;
  flex-direction: row;
  background-size: cover;
}
.section6 {
  background-size: cover;
}
.section2a {
  position: relative;
  text-align: left;
  line-height: 1.4em;
  display: flex;
  justify-content: space-around;
  align-items: center;
  flex-direction: row;
  margin-top: 30px;

  /* margin-right: 350px; */
}

.content {
  position: relative;
  top: 30%;
  padding: 20px 15px;
  margin: 15% auto;
  background: rgba(223, 223, 230, 0.7);

  /* filter: blur(10px); */
}

.content >>> .section1-p {
  color: #535353;
  font-size: 1.25em;
  font-family: 'NEXON Lv1 Gothic OTF', 'Serif';
}
.content >>> h3 {
  color: rgb(250, 248, 248);
  margin-bottom: 15px;
  font-size: 2em;
  font-family: 'NEXON Lv1 Gothic OTF', 'Serif';
}

.section3-p {
  margin-top: 0px;
  color: hsl(210, 2%, 22%);
  font-size: 0.8em;
  line-height: 1em;
  padding: 20px 20px;
  max-width: 44%;
  margin: auto;
  line-height: 32px;
}
.text-box {
  width: 100%;
  display: flex;
}

.features {
  padding: 0;
  margin: 15% auto;
  display: flex;
  align-items: flex-start;
  flex-direction: column;
  justify-content: center;
}

.features h3 {
  color: hsl(210, 10%, 33%);
  font-size: 2rem;

  padding-top: 20px;
}
.features li {
  list-style-type: none;
  width: 330px;
  margin: auto 15px;
  position: relative;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  flex-direction: column;
}

.monitor {
  height: 50vh;
  width: auto;
  /* margin-top: 30px; */
  margin: 2% 30% 2% 30%;
  border-radius: 25px;
  /* box-shadow: 2px 3px 15px rgba(0, 0, 0, 0.3); */
}

.explanation {
  position: absolute;
  margin-left: 100px;
  width: 80%;
}
.section4-content h1 {
  margin: 1.2rem auto;
}
.slide img {
  width: 43%;
  margin-top: 2rem;
}
.slide h4 {
  font-size: 1.5rem;
  margin-top: -2em;
  margin-bottom: 1rem;
}
.lec-detail {
  font-size: 1.3rem;
  /* font-weight: bold; */
}

.step-label {
  border: 0px solid #fff;
  background: #444444;
  padding: 0.6rem 2.2rem;
  color: #fff;
  border-radius: 50px;
  letter-spacing: 1px;
  display: inline-block;
}
.mode-label {
  border: 0px solid #fff;
  background: #2e95ff;
  color: #fff;
  padding: 1rem;
  font-weight: bold;
  font-size: 2.2rem;
}
.last-h1 {
  width: 60%;
  /* float: left; */
  display: flex;
  justify-content: center;
  border: 0px solid #fff;

  background: #3b3b3b;
  padding: 1rem;
  color: white;
  height: 100%;

  font-size: 1.7rem;
  border-top-left-radius: 0px;
  border-bottom-left-radius: 0px;
  border-radius: 50px;
  /* box-shadow: 2px 3px 10px rgba(0, 0, 0, 0.3); */
}

.RTC-title {
  position: relative;
  font-family: 'Recipekorea' !important;
  /* font-size: 2rem; */

  width: 45%;
  margin-top: 1rem;
  margin-left: 60%;
}
.RTC-title .title1 {
  position: absolute;
  font-size: 4rem;
  font-weight: bold;
  text-align: left;
  line-height: 4.5rem;
  letter-spacing: -1px;

  right: 30%;
  margin-top: 15%;
  color: #2e95ff;
}
.RTC-title .title2 {
  position: absolute;
  font-size: 1.2rem;
  /* font-weight: bold; */
  text-align: center;
  line-height: 5rem;
  color: #2e95ff;
  letter-spacing: 0.2px;
  justify-self: center;
  margin-top: 33%;
  margin-left: 0.5rem;
}
.g1 {
  /* margin-top: -3rem; */
  border-radius: 20px;
  margin-bottom: 2rem;
  margin-right: 35%;
  height: 80%;
  width: 25% !important;
  /* margin:
   auto; */
}
.g1-detail {
  position: absolute;
  font-size: 1.3rem;
  bottom: 35%;
  left: 15%;
  text-align: left;
}
.g2 {
  border-radius: 30px;
  margin-right: 35%;
  height: 55%;
  width: 45% !important;
  /* margin-bottom: 20%; */
}
.g2-detail {
  position: absolute;
  font-size: 1.3rem;
  bottom: 35%;
  left: 40%;
  text-align: left;
}

.g3 {
  border-radius: 30px;
  margin-right: 35%;
  height: 55%;
  width: 45% !important;
}
.g3-detail {
  position: absolute;
  font-size: 1.3rem;
  left: 65%;
  bottom: 35%;
  text-align: left;
}

.g4 {
  border-radius: 30px;
  margin-right: 35%;
  height: 55%;
  width: 45% !important;
}
.g4-detail {
  position: absolute;
  font-size: 1.3rem;
  left: 90%;
  bottom: 35%;
  text-align: left;
}
.AI-title {
  border: 0px solid #000;
  background: #61d4ed;
  color: #fff;
  padding: 0.5rem 0.5rem;
  font-size: 4.5rem;
  text-align: center;
  margin-left: 4rem;
}
</style>
<style>
.fp-controlArrow.fp-prev {
  left: 50px;
  width: 0;
  border-width: 38.5px 34px 38.5px 0;
  border-color: transparent #4a4a4a transparent transparent;
}
.fp-controlArrow.fp-next {
  right: 50px;
  border-width: 38.5px 0 38.5px 34px;
  border-color: transparent transparent transparent #4a4a4a;
}
</style>
<style>
.fp-controlArrow.fp-prev {
  left: 50px;
  width: 0;
  border-width: 38.5px 34px 38.5px 0;
  border-color: transparent #4b4b4b transparent transparent;
}
.fp-controlArrow.fp-next {
  right: 50px;
  border-width: 38.5px 0 38.5px 34px;
  border-color: transparent transparent transparent #4b4b4b;
}
</style>
