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
        <div class="mode-label">강의자 가이드</div>
        <div class="slide">
          <h4 style="">
            STEP1.
            <span style="font-weight:bold; font-size:2rem;">
              클래스만들기
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
            STEP2.
            <span style="font-weight:bold; font-size:2rem;">
              클래스 리스트
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
            STEP3.
            <span style="font-weight:bold; font-size:2rem;">
              클래스 시작
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
            STEP4.
            <span style="font-weight:bold; font-size:2rem;">
              클래스 평가 관리
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
      <div class="section section2" :style="image_2">
        <div class="mode-label" style="background: #FF625C;">수강자 가이드</div>
        <div class="slide">
          <h4>
            STEP1.
            <span style="font-weight:bold; font-size:2rem;">
              수강신청
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
            STEP2.
            <span style="font-weight:bold; font-size:2rem;">
              수업 참여
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
            STEP3.
            <span style="font-weight:bold; font-size:2rem;">
              수업수강
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
            STEP4.
            <span style="font-weight:bold; font-size:2rem;">
              평가 조회
            </span>
          </h4>
          <div>
            <div class="lec-detail">
              집중, 딴짓, 졸음, 자리 비움에 대한 학습 관리, 출결 및 참여도 관리를 통해 자신의 수업태도를 확인 할 수 있습니다.
            </div>
            <img src="@/assets/img/learner4.png" />
          </div>
        </div>
      </div>
      <!-- <div class="section section2" :style="image_2">
        화상플랫폼(webRTC) 기능 쌍방향 음성, 영상, 화이트보드, 화면공유, 채팅, 실시간알림, 화면끄기 ,
        <div class="slide">
          <h3>Slide 2.1</h3>
        </div>
        <div class="slide">
          <h3>Slide 2.2</h3>
        </div>
        <div class="slide">
          <h3>Slide 2.3</h3>
        </div>
        <div class="slide">
          <h3>Slide 2.4</h3>
        </div>
        <div class="slide">
          <h3>Slide 2.5</h3>
        </div>
      </div> -->
      <div class="section section3" :style="image_3">
        <!-- <h4 style="color: #535353;  letter-spacing:4px; font-size:2rem;">실시간 학습태도관리</h4> -->
        <ul class="features">
          <div style="display:flex; flex-direction:column;  margin-left:2rem; width:40%">
            <h1 style="color:#756BFF; font-size:3rem; text-align:center; margin-left:4rem;">AI격차가 곧, 공부격차</h1>
            <div style="margin-left:5rem; margin-top:2rem;">
              <strong>집중, 딴짓, 졸음, 자리비움</strong>
              총 4가지 태도를 AI로 실시간 감지하여 학습습관을 스마트하게 관리할 수 있습니다.
            </div>
          </div>
        </ul>
        <div>
          <LandingComp></LandingComp>
        </div>
      </div>
      <div class="section section4" :style="image_4">
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
                    <h1>출결현황</h1>
                    <p style="font-size:1rem;">
                      <strong>illeum</strong>
                      은 수강자가 강의에 입장한 시간을 기준으로 출결기록을 실시간으로 체크하기 때문에 수동으로 출결관리를 하지 않아도 됩니다.
                    </p>
                  </div>
                </v-col>
                <v-col cols="4">
                  <div>
                    <h1>평가관리/조회</h1>
                    <p style="font-size:1rem;">
                      <strong>illeum</strong>
                      은 수강자, 강의자 모두 자신이 참여/강의한 클래스에 관한 평가데이터를 제공합니다.
                    </p>
                  </div>
                </v-col>
                <v-col cols="4">
                  <div>
                    <h1>참여도</h1>
                    <p style="font-size:1rem;">
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
        sectionsColor: ['require(', '#ff5f45', '#0798ec', '#fec401', '#1bcee6', '#ee1a59', '#2c3e4f', '#ba5be9', '#b4b8ab'],
        responsiveWidth: 900,
      },
      serviceText: `<h3 style="color: #535353;"><span style="color:#2E95FF;">원격수업의 질</span>을 높여드리는<span style="color:#FF625C;"> '일름' </span>서비스</h3>
        <p class="section1-p">언택트시대 온라인 학습, 일름이 해답입니다.</p>`,
    };
  },
  mounted() {
    console.log(this.$refs.fullpage);
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
        backgroundImage: `url(${this.image4})`,
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
.section2 {
  /* opacity: 0.4; */
}
.section3 {
  /* opacity: 0.5; */
  display: flex;
  flex-direction: row;
}
.section4 {
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
  box-shadow: 2px 3px 15px rgba(0, 0, 0, 0.3);
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
  font-size: 1rem;
  font-weight: bold;
}
.mode-label {
  border: 0px solid #fff;
  background: #2e95ff;
  color: #fff;
  padding: 1rem;
  font-size: 2rem;
}
</style>
<style>
.fp-controlArrow.fp-prev {
  left: 50px;
  width: 0;
  border-width: 38.5px 34px 38.5px 0;
  border-color: transparent #2e95ff transparent transparent;
}
.fp-controlArrow.fp-next {
  right: 50px;
  border-width: 38.5px 0 38.5px 34px;
  border-color: transparent transparent transparent #2e95ff;
}
</style>
