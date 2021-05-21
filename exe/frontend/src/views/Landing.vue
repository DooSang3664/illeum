<template>
  <div id="landing2">
    <div class="section1">
      <img src="@/assets/img/finallogo_2.png" class="logo" alt="logo" />

      <h1>
        일름에서 원격수업의 해답을 찾다.
      </h1>
      <div class="text-box">
        <p style="color: #4e4e4e;">
          온라인 수업에 적극적으로 참여할 수 있는 다양한 학습지원기능을 제공하여,
          <br />
          어디서든 교실과 차이 없는 온라인 수업이 가능한 교육환경을 제공합니다.
        </p>
      </div>
      <div class="buttons">
        <button class="button-guide" @click="homeGo">GUIDE</button>
        <button class="button-start" @click="startGo">START</button>
      </div>
    </div>
  </div>
</template>

<script>
import LandingComp from '@/views/LandingComp.vue';
export default {
  components: {
    LandingComp,
  },

  methods: {
    homeGo() {
      this.$router.push('/guide');
    },
    startGo() {
      if (this.$store.getters.isLogin) {
        this.$router.push({ name: 'MyClass' });
      } else {
        this.$router.push({ name: 'Sign' });
      }
    },
  },
};
</script>

<style scoped>
@font-face {
  font-family: 'NEXON Lv2 Gothic Bold';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv2 Gothic Bold.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}
@font-face {
  font-family: 'S-CoreDream-3Light';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/S-CoreDream-3Light.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}
:root {
  --cyan: hsl(171, 66%, 44%);
  --lightblue: hsl(233, 100%, 69%);
  --dk-grey-blue: hsl(210, 10%, 33%);
  --grey-blue: hsl(201, 11%, 66%);
}

#landing2 {
  font-family: 'NEXON Lv2 Gothic Bold', 'Bai Jamjuree', sans-serif;
  margin: 0;
  padding: 0;
  background-color: #fff;
  font-size: 15px;
}

.content {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  text-align: center;
}

.section1 {
  background-image: url('../assets/img/teacher_edit.png');
  background-size: cover;
  background-color: rgba(255, 255, 255, 0.1);

  background-size: cover;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  padding-top: 100px;
  padding-bottom: 50px;
}

.logo {
  position: relative;
  width: 25%;
  /* height: 125px; */
  margin-bottom: 20px;
}

.text-box {
  width: 50%;
  margin-top: 1rem;
  font-family: 'S-CoreDream-3Light';
  color: #2c2b2b;
}

.section1 h1 {
  color: #4e4e4e;
  margin-top: 1rem;
  font-size: 2.5em;
  letter-spacing: 2px;
}

.section1 p {
  color: hsl(201, 11%, 66%);
  font-size: 1.15em;
  line-height: 1.5em;
}

.buttons {
  display: flex;
  flex-direction: row;
  margin-top: 1%;
  margin-bottom: 10.5%;
  column-gap: 15px;
  padding-top: 15px;
}

.button-guide {
  background-color: #756bff;
  color: white;
  border-radius: 30px;
  letter-spacing: 3px;
  border: none;
  width: 175px;
  height: 40px;
  font-size: 1em;
  font-family: 'Bai Jamjuree', sans-serif;
  cursor: pointer;
  box-shadow: 0px 2px 0px #756bff;
}

.button-guide:hover {
  box-shadow: 0px 2px 5px #756bff;
  letter-spacing: 3px;
}

.button-start {
  background-color: #ff625c;
  color: white;
  border-radius: 30px;
  border: none;
  width: 175px;
  height: 40px;
  font-size: 1em;
  font-family: 'Bai Jamjuree', sans-serif;
  cursor: pointer;
  box-shadow: 0px 2px 0px #ff625c;
  letter-spacing: 3px;
}

.button-start:hover {
  box-shadow: 0px 2px 5px #ff625c;
  letter-spacing: 3px;
}
</style>
