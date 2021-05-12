<template>
  <div id="app">
    <full-page ref="fullpage" :options="options" id="fullpage">
      <div class="section section1" :style="image_1">
        <div class="">
          <div class="content">
            <h3>원격수업의 질을 높여드리는 '일름'서비스</h3>
            <p class="section1-p">언택트시대 온라인 학습 일름이 해답입니다.</p>
          </div>
        </div>

        <button @click="$refs.fullpage.api.moveSectionDown()">Move Down</button>
      </div>
      <div class="section section2" :style="image_2">
        <!-- 화상플랫폼(webRTC) 기능

        쌍방향 음성, 영상, 화이트보드, 화면공유, 채팅, 실시간알림 , -->
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
      </div>
      <div class="section section3" :style="image_3">
        <h2>Lorem ipsum dolor sit amet consectetur adipisicing elit</h2>
        <div class="text-box">
          <p class="section3-p">
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Officiis ipsum adipisci fugit delectus voluptatum? Distinctio nihil eveniet corrupti vitae ex nemo dolore. Unde saepe,
          </p>
        </div>
        <LandingComp></LandingComp>
      </div>
      <div class=" section section4" :style="image_4">
        <!-- 데이터 시각화 도구 
        - 출결
        - 학습태도 데이터
        - 참여도 -->
        <div class="slide">
          <h3>Slide 4.1</h3>
        </div>
        <div class="slide">
          <h3>Slide 4.2</h3>
        </div>
        <div class="slide">
          <h3>Slide 4.3</h3>
        </div>
      </div>
    </full-page>
  </div>
</template>

<script>
import LandingComp from '@/views/LandingComp.vue';
export default {
  components: {
    LandingComp,
  },
  name: 'app',
  data() {
    return {
      options: {
        navigation: true,
        sectionsColor: ['require(', '#ff5f45', '#0798ec', '#fec401', '#1bcee6', '#ee1a59', '#2c3e4f', '#ba5be9', '#b4b8ab'],
      },
    };
  },
  props: {
    image1: {
      type: String,
      default: require('@/assets/img/bg3.jpg'),
    },
    image2: {
      type: String,
      default: require('@/assets/img/bg2.jpg'),
    },
    image3: {
      type: String,
      default: require('@/assets/img/bg4.jpg'),
    },
    image4: {
      type: String,
      default: require('@/assets/img/bg1.jpg'),
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
};
</script>
<style scoped>
@font-face {
  font-family: 'GongGothicMedium';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-10@1.0/GongGothicMedium.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}
@font-face {
  font-family: 'GongGothicLight';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-10@1.0/GongGothicLight.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}
.section {
  text-align: center;
  font-size: 2em;
}
.section1 {
  z-index: -1;
  opacity: 0.9;
  background-size: cover;
}
.section2 {
  opacity: 0.4;
}
.section3 {
  /* opacity: 0.3; */
}
/* .section4 {
} */

/* .backdrop {
  background: url('https://i.imgbox.com/JGXLxgQb.jpg');
  background-size: contain;
  height: 320px;
  margin-bottom: 40px;
  margin-top: -30px;
} */

.content {
  position: relative;
  top: 30%;
  padding: 20px 15px;
  margin: 40px auto;
  background: rgba(80, 80, 83, 0.8);
  backdrop-filter: hue-rotate(180deg);
  z-index: 1;
}

.section1-p {
  color: white;
  font-size: 1.25em;
  font-family: 'GongGothicMedium', 'Serif';
}

h3 {
  color: rgb(250, 248, 248);
  margin-bottom: 15px;
  font-size: 2em;
  font-family: 'GongGothicLight', 'Serif';
}
.section3-p {
  margin-top: 0px;
  color: hsl(210, 2%, 22%);
  font-size: 0.8em;
  line-height: 1em;
  padding: 20px 20px;
}
.text-box {
  width: 100%;
  display: flex;
}
/* .fp-controlArrow {
  position: absolute;
  z-index: 4;
  top: 50%;
  cursor: pointer;
  width: 0;
  height: 0;
  border-style: solid;
  margin-top: -38px;
}
.fp-controlArrow.fp-prev {
  left: 30;
  border: none;
  width: 50px;
  height: 101px;

  cursor: pointer;
}
.fp-controlArrow.fp-prev {
  right: 30;
  border: none;
  width: 50px;
  height: 101px;

  cursor: pointer;
} */
</style>
