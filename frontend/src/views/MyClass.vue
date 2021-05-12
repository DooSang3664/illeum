<template>
  <div>
    <p>청강자/강의자 리스트</p>
    <input type="checkbox" class="checkbox" v-model="checked" />
    <div id="learner">
      learner
    </div>
    <div id="lecturer">lecturer</div>
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
.checkbox {
  position: fixed;
  top: 40px;
  right: 10%;
  -webkit-appearance: none;
  width: 10rem;
  height: 40px;
  background-color: #3a7bd5;
  border-radius: 20px;
  cursor: pointer;
  outline: none;
  z-index: 2;
}

.checkbox:checked {
  background-color: #f33311;
  outline: none;
}

.checkbox::before {
  content: '';
  position: absolute;
  width: 3rem;
  height: 32px;
  background-color: #fff;
  border-radius: 20px;
  top: 4px;
  left: 0.5rem;
  transition: 0.3s linear;
}

.checkbox:checked::before {
  left: 6rem;
  content: '';
}
#learner {
  position: fixed;
  top: 3rem;
  right: 20%;
  /* transform: translateY(-50%); */
  color: #000;
  font-size: 20px;
  font-weight: bold;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  margin-left: px;
  outline: none;
  z-index: 3;
}
#lecturer {
  position: fixed;
  outline: none;
  top: 3rem;
  right: 5%;
  /* transform: translateY(-50%); */
  color: #000;
  font-size: 20px;
  font-weight: bold;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  margin-left: px;
  z-index: 3;
}
</style>
