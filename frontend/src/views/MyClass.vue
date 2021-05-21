<template>
  <div class="my-class">
    <v-alert
      class="text-start font-weight-black"
      border="left"
      color="#2E95FF"
      dark
      id="alert"
      style="margin-top:6%; font-size: 1.5rem; letter-spacing: 2.3px; line-height: 45px;"
      elevation="3"
      height="70"
      width="1400"
    >
      # 내가 수강/강의하고 있는 클래스
    </v-alert>
    <input type="checkbox" id="switch1" name="switch1" class="input__on-off" v-model="checked" />
    <label for="switch1" class="label__on-off">
      <span class="marble"></span>
      <span class="on">강의자</span>
      <span class="off">수강자</span>
    </label>
    <!-- <div>
      <input type="checkbox" class="checkbox" v-model="checked" />
    </div>
    <div class="toggle-text">
      <div id="learner">
        수강생
      </div>
      <div id="lecturer">강의자</div>
    </div> -->

    <LearnerMode v-if="checked == false"></LearnerMode>
    <LecturerMode v-else></LecturerMode>
  </div>
</template>

<script>
import LecturerMode from '@/views/user/LecturerMode';
import LearnerMode from '@/views/user/LearnerMode';
export default {
  components: {
    LecturerMode,
    LearnerMode,
  },
  data() {
    return {
      checked: false,
    };
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
.my-class {
  margin: 10% 2%;
  font-family: 'NEXON Lv1 Gothic OTF';
}

.checkbox {
  position: absolute;
  top: 5%;
  right: 10%;
  -webkit-appearance: none;
  width: 14rem;
  height: 3.5rem;
  background-color: #41ea93;
  border-radius: 50px;
  cursor: pointer;
  outline: none;
  z-index: 2;
}

.checkbox:checked {
  background-color: #41ea93;
  outline: none;
  cursor: pointer;
}

.checkbox::before {
  content: '';
  position: absolute;
  width: 7.2rem;
  height: 3.5rem;
  border-radius: 50px;
  background-color: #2e95ff;

  cursor: pointer;

  /* transition: 0.6s linear; */
}

.checkbox:checked::before {
  left: 7rem;
  content: '';
}
.toggle-text {
  top: 5rem;
}

#learner {
  position: absolute;
  top: 4.2rem;
  right: 21%;
  color: #fff;
  font-size: 20px;
  font-weight: 400;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  /* outline: none; */
  z-index: 3;
}
#lecturer {
  position: absolute;
  /* outline: none; */
  top: 4.2rem;
  right: 11.5%;
  color: #fff;
  font-size: 20px;
  font-weight: 400;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  z-index: 3;
}
/* toggle */
input[type='checkbox'] {
  display: none;
}

.label__on-off {
  overflow: hidden;
  position: relative;
  display: inline-block;

  width: 10rem;
  height: 3rem;
  -webkit-border-radius: 13px;
  -moz-border-radius: 13px;
  border-radius: 50px;
  background-color: #ff625c;
  color: #fff;
  font-weight: bold;
  cursor: pointer;
  -webkit-transition: all 1s;
  -moz-transition: all 0.3s;
  -ms-transition: all 0.3s;
  -o-transition: all 0.3s;
  transition: all 0.3s;
}

.label__on-off > * {
  vertical-align: sub;
  -webkit-transition: all 0.3s;
  -moz-transition: all 0.3s;
  -ms-transition: all 0.3s;
  -o-transition: all 0.3s;
  transition: all 0.3s;
  font-size: 1.3rem;
  letter-spacing: 2px;
}

.label__on-off .marble {
  position: absolute;
  /* top: 1px; */
  /* left: -0.1rem; */
  display: block;
  width: 3rem;
  height: 3rem;
  background-color: #fff;
  -webkit-border-radius: 50%;
  -moz-border-radius: 50%;
  border-radius: 50%;
  -webkit-box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
  -moz-box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
}

.label__on-off .on {
  display: none;
  padding-right: 3rem;
  line-height: 3rem;
}

.label__on-off .off {
  padding-left: 3rem;
  line-height: 3rem;
}

.input__on-off:checked + .label__on-off {
  background-color: #2e95ff;
}

.input__on-off:checked + .label__on-off .on {
  display: inline-block;
}

.input__on-off:checked + .label__on-off .off {
  display: none;
}

.input__on-off:checked + .label__on-off .marble {
  left: 7rem;
}
</style>
