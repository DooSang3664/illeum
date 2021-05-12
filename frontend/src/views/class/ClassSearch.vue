<template>
  <div>
    <div class="class-search" style="overflow: hidden;">
      <!-- search -->
      <div class="box">
        <!-- <h1 class="title-search" style="">클래스명을 검색하세요!</h1> -->
        <v-row class="text-center" style="height: 10px">
          <div class="search-contain">
            <div class="search_box">
              <input type="text" placeholder="클래스명을 입력해주세요" />
              <i class="fa fa-search"></i>
              <!-- <div>SEARCH</div> -->
            </div>
          </div>
        </v-row>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Roboto');

.search-contain {
  width: 100vw;
  height: 100vh;
  display: flex;
  margin-top: 10%;
  margin-left: 20%;
  /* justify-content: center; */
  /* align-items: center; */
}

.search_box {
  width: 60%;
  /* position: relative; */
  display: flex;
  flex-direction: row;
  height: 8vh;
  /* margin: 5px; */
}

.search_box input[type='text'] {
  width: 100%;
  padding: 20px;
  padding-right: 60px;
  box-sizing: border-box;
  background: rgba(255, 255, 255, 0.2);
  border: 2px solid #9b9b9b;
  border-radius: 20px;
  font-size: 18px;
  color: #737373;
  outline: none;
}
.search_box button {
  /* width: 100%; */
  height: 5vh;
  box-sizing: border-box;
  background: rgba(255, 255, 255, 0.2);
  border: 2px solid #000;
  border-radius: 20px;
  font-size: 18px;
  color: #737373;
  padding: 10px 20px;
  outline: none;
}

.fa-search {
  position: relative;
  top: 50%;
  transform: translate(-70%, -25%);
  /* transform: translateX(-50%); */
  right: 25px;
  font-size: 25px;
  color: #9b9b9b;
}

::-webkit-input-placeholder {
  /* Chrome/Opera/Safari */
  color: #9b9b9b;
}
::-moz-placeholder {
  /* Firefox 19+ */
  color: #9b9b9b;
}
:-ms-input-placeholder {
  /* IE 10+ */
  color: #9b9b9b;
}

@media screen and (max-width: 425px) {
  .search_box {
    width: 95%;
  }
}
.class-search {
  margin-left: 10%;
  /* margin-top: 10px; */
  width: 100%;
  height: 100vh;
  background: url(../../assets/img/onlineclass.jpg) no-repeat;
  background-size: cover;
}
</style>
