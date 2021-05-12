<template>
  <div class="box">
    <div class="box-inner">
      <div class="slide1">
        <img src="@/assets/img/class3.png" style="width: 495px; height: 500px" alt="" />
        <div class="content">
          <h2>
            집중
            <br />
          </h2>
        </div>
      </div>
      <div class="slide2">
        <img src="@/assets/img/fatigue.png" style="width: 495px; height: 500px" alt="" />
        <div class="content">
          <h2>
            딴짓
            <br />
          </h2>
        </div>
      </div>

      <div class="slide3">
        <img src="@/assets/img/sleep.png" style="width: 495px; height: 500px" alt="" />
        <div class="content">
          <h2>졸음</h2>
        </div>
      </div>

      <div class="slide4">
        <img src="@/assets/img/class5.jpg" style="width: 495px; height: 500px" alt="" />
        <div class="content">
          <h2>자리비움</h2>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src

export default {
  name: 'Landing',
  components: {},
};
</script>
<style scoped>
@font-face {
  font-family: 'GmarketSansMedium';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}
.box {
  width: 95vw;
  height: 50vh;
  /* background-color: #fff; */
  /* float: right; */
  /* margin: 10% 0 10% 20%; */
  margin: 3% 5%;
  perspective: 1700px;
  transform: translateY(10%);
  transition: all ease 300ms;
  cursor: pointer;
}
.box:hover {
  transform: translateY(0%);
}
.box:after {
  position: absolute;
  content: '';
  left: 0;
  bottom: 30%;
  opacity: 0;
  width: 100%;
  height: 20%;
  background-color: gray;
  filter: blur(20px);
  border-radius: 100%;
  z-index: -1;
  transition: all ease 300ms;
}
.box:hover:after {
  bottom: -30%;
  opacity: 0.35;
}
.box-inner {
  position: relative;
  width: 100%;
  height: 100%;
  transition: all 4s linear;
  transform-style: preserve-3d;
}
.box:hover .box-inner {
  transform: rotateY(270deg);
}
.box-inner {
  transform: rotateY(45deg);
}
.box-inner > div {
  position: absolute;
  width: 100%;
  height: 100%;
  font-size: 45px;
  text-align: center;
  border-radius: 4px;
  overflow: hidden;
}
.box .box-inner .content {
  position: absolute;
  bottom: 0;
  height: 80px;
  width: 32.3vw;
  background: hsl(171, 66%, 44%);
  margin-left: 33%;
  text-align: center;
}

.slide1 {
  transform: rotateY(90deg) translateZ(34vh);
}
.slide2 {
  transform: rotateY(180deg) translateZ(34vh);
}
.slide3 {
  transform: rotateY(270deg) translateZ(34vh);
}
.slide4 {
  transform: rotateY(360deg) translateZ(34vh);
}
.box .box-inner .content h2 {
  padding-top: 1.5rem;
  font-size: 2rem;
  font-weight: bold;
  color: #fff;
  font-family: 'GmarketSansMedium', Serif;
}
.box .box-inner .content h2 span {
  font-size: 16px;
  color: #f44336;
}
</style>
