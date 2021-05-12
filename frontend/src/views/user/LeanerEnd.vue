<template>
  <div>
    <h2>수강자-클래스종료</h2>
    <div v-if="learnerDoing == true">
      <p>수강진행중인 강좌가 있음</p>
      <div v-for="(item, idx) in classLi" :key="idx">
        <div v-if="new Date(item.end_time) < Date.now() && item.room_state == '완료'">
          <h3>vid:{{ item.vid }}</h3>
          <h3>강의자:{{ item.founder }}</h3>
          <h3>강의명:{{ item.room_name }}</h3>
          <v-img :src="'https://k4d106.p.ssafy.io/profile/' + item.founder_uid + '/256'" id="preview" alt="" style="width:100px; height:100px; left:45%;"></v-img>
          <h3>공개/비공개:{{ item.room_type }}</h3>
          <h3>진행여부:{{ item.room_state }}</h3>
          <h3>시작시간{{ item.start_time }}</h3>
          <h3>종료시간{{ item.end_time }}</h3>
          <hr />
        </div>
      </div>
    </div>
    <div v-else>
      <p>수강진행중인 강좌가 없음</p>
    </div>
  </div>
</template>

<script>
import { usePartinClass } from '@/api/auth.js';
export default {
  data() {
    return {
      learnerDoing: false,
      classLi: [],
    };
  },
  async created() {
    const { data } = await usePartinClass(this.$store.state.uuid);
    console.log(data);
    if (data) {
      this.learnerDoing = true;
      this.classLi = data;
    } else {
    }
  },
};
</script>

<style></style>
