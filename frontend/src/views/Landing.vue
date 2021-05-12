<template>
  <div id="landing2">
    <div class="content">
      <div class="section1">
        <img src="@/assets/img/finallogo.png" class="logo" alt="logo" />

        <h1>
          일름에서 원격수업의 해답을 찾다.
        </h1>
        <div class="text-box">
          <p>
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Beatae qui accusamus tempora esse aliquam in impedit aut placeat sunt cumque animi nemo sapiente fuga, sed obcaecati ducimus nisi
            sit veritatis?
          </p>
        </div>
        <div class="buttons">
          <button class="button-guide" @click="homeGo">TEST</button>
          <button class="button-start" @click="startGo">START</button>
        </div>
      </div>
      <div class="section2">
        <h2>Lorem ipsum dolor sit, amet consectetur</h2>
        <div class="text-box">
          <p>
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum velit iste voluptatum, maiores dicta doloribus eius consectetur neque deleniti praesentium voluptates placeat repudiandae
            assumenda vel sunt reprehenderit veritatis repellat numquam.
          </p>
        </div>
        <div class="section2a">
          <img src="@/assets/img/onlinestudy.jpeg" class="monitor" alt="computer" />
          <div class="explanation">
            <div>
              <h3>출결관리</h3>
              <p>
                Lorem ipsum dolor sit amet consectetur adipisicing elit. Enim magni veritatis, expedita maiores error.
              </p>
            </div>
            <div>
              <h3>학습관리</h3>
              <p>
                Lorem ipsum dolor sit amet consectetur adipisicing elit. Enim magni veritatis, expedita maiores error.
              </p>
            </div>
            <div>
              <h3>참여도</h3>
              <p>
                Lorem ipsum dolor sit amet consectetur adipisicing elit. Enim magni veritatis, expedita maiores error.
              </p>
            </div>
          </div>
        </div>
      </div>
      <div class="section3">
        <h2>Lorem ipsum dolor sit amet consectetur adipisicing elit</h2>
        <div class="text-box">
          <p>
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Officiis ipsum adipisci fugit delectus voluptatum? Distinctio nihil eveniet corrupti vitae ex nemo dolore. Unde saepe,
            necessitatibus ab iure voluptatum nemo molestiae?
          </p>
        </div>
        <LandingComp></LandingComp>
      </div>
      <div class="section4">
        <h2>AI학습관리</h2>
        <div class="text-box"><p class="subtitle">Lorem ipsum dolor sit amet consectetur adipisicing elit. Asperiores ut eveniet ab veniam hic et, distinctio reprehenderit</p></div>
        <ul class="features">
          <li>
            <em class="fa fa-3x fa-address-book"></em>
            <h3>집중</h3>
            <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Laudantium aut,</p>
          </li>
          <li>
            <em class="fa fa-3x fa-address-book"></em>
            <h3>딴짓</h3>
            <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Laudantium aut,</p>
          </li>
          <li>
            <em class="fa fa-3x fa-address-book"></em>
            <h3>졸음</h3>
            <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Laudantium aut,</p>
          </li>
          <li>
            <em class="fa fa-3x fa-address-book"></em>
            <h3>자리비움</h3>
            <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Laudantium aut,</p>
          </li>
        </ul>
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
      this.$router.push('/home');
    },
    startGo() {
      this.$router.push('/sign');
    },
  },
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Bai+Jamjuree:wght@400;600&display=swap');

:root {
  --cyan: hsl(171, 66%, 44%);
  --lightblue: hsl(233, 100%, 69%);
  --dk-grey-blue: hsl(210, 10%, 33%);
  --grey-blue: hsl(201, 11%, 66%);
}

#landing2 {
  font-family: 'Bai Jamjuree', sans-serif;
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
  background-color: rgba(255, 255, 255, 0.1);
  /* z-index: -1; */
  /* opacity: 0.5; */
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
  width: 600px;
}

.section1 h1 {
  color: hsl(210, 10%, 33%);
  margin-bottom: 5px;
  font-size: 2.5em;
}

.section1 p {
  margin-top: 10px;
  color: hsl(201, 11%, 66%);
  font-size: 1.15em;
  line-height: 1.5em;
}

.buttons {
  display: flex;
  flex-direction: row;
  column-gap: 15px;
  padding-top: 15px;
}

.button-guide {
  background-color: hsl(171, 66%, 44%);
  color: white;
  border-radius: 30px;
  letter-spacing: 3px;
  border: none;
  width: 175px;
  height: 40px;
  font-size: 1em;
  font-family: 'Bai Jamjuree', sans-serif;
  cursor: pointer;
  box-shadow: 0px 2px 0px hsl(171, 66%, 35%);
}

.button-guide:hover {
  box-shadow: 0px 2px 5px hsl(171, 66%, 35%);
  letter-spacing: 3px;
}

.button-start {
  background-color: hsl(233, 100%, 69%);
  color: white;
  border-radius: 30px;
  border: none;
  width: 175px;
  height: 40px;
  font-size: 1em;
  font-family: 'Bai Jamjuree', sans-serif;
  cursor: pointer;
  box-shadow: 0px 2px 0px hsl(233, 45%, 43%);
  letter-spacing: 3px;
}

.button-start:hover {
  box-shadow: 0px 2px 5px hsl(233, 45%, 43%);
  letter-spacing: 3px;
}

.section2 {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  padding-top: 50px;
}

.section2a {
  text-align: left;
  line-height: 1.5em;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 30px;
  margin-right: 350px;
}

.monitor {
  height: 550px;
  margin-top: 30px;
}

.explanation {
  margin-left: 100px;
  margin-top: -30px;
  width: 325px;
}

h2 {
  color: hsl(210, 10%, 33%);
  font-size: 2em;
}

.section2 h3 {
  color: hsl(210, 10%, 33%);
  font-size: 1.5em;
  padding-top: 20px;
}

.section2 p {
  margin-top: 10px;
  color: hsl(201, 11%, 66%);
  font-size: 1em;
  line-height: 2em;
}

.section3 {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  margin-top: 100px;
}

.section3 p {
  margin-top: 0px;
  color: hsl(201, 11%, 66%);
  font-size: 1em;
  line-height: 2em;
}

.tablet {
  margin-top: 70px;
  width: 800px;
}

.section4 {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  margin-top: 50px;
}

.subtitle {
  margin-top: -10px;
  color: var(--grey-blue);
  font-size: 1em;
  line-height: 2em;
}

.features {
  padding: 0;
  margin: 0;
  display: flex;
  align-items: flex-start;
  margin-top: 30px;
  justify-content: center;
}

.features h3 {
  color: hsl(210, 10%, 33%);
  font-size: 1.5em;
  padding-top: 20px;
}
.features li {
  list-style-type: none;
  width: 330px;
  margin: 0 15px;
  position: relative;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  flex-direction: column;
}

.blacklist {
  height: 35px;
}

.fa .fa-address-book {
  height: 50px;
}

.text {
  height: 35px;
}

.features p {
  line-height: 2em;
  margin-top: 0px;
  color: hsl(201, 11%, 66%);
  font-size: 1em;
}

.companies {
  flex-wrap: wrap;
  justify-content: center;
  padding: 0;
  margin: 0;
  display: flex;
  margin-top: 70px;
}

.companies li {
  list-style-type: none;
  width: 130px;
  height: 100px;
  margin: 0 50px;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
}

.companies img {
  position: relative;
}

.section5 {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  flex-wrap: wrap;
  margin-top: 50px;
  margin-bottom: 100px;
}

.section5 p {
  margin-top: 0px;
  color: hsl(201, 11%, 66%);
  font-size: 1em;
  line-height: 2em;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  flex-wrap: wrap;
}

.section5 div {
  margin-top: 30px;
}
</style>
