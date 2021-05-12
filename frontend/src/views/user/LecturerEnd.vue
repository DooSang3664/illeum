<template>
  <div>
    <h2>강의자-클래스종료</h2>
    <div v-if="learnerDoing == true">
      <p>강의행중인 강좌가 있음</p>
      <div v-for="(item, idx) in classLi" :key="idx">
        <div v-if="new Date(item.end_time) < Date.now() && item.room_state == '완료'">
          <p>{{ item.rid }}</p>
          <p>{{ item.founder }}</p>
          <p>{{ item.room_name }}</p>

          <p>{{ item.room_type }}</p>
          <p>{{ item.start_time }}</p>
          <p>{{ item.end_time }}</p>
          <v-img :src="url" id="preview" alt="" style="width:100px; height:100px; left:45%;"></v-img>
        </div>
      </div>
    </div>
    <div v-else>
      <p>수강진행중인 강좌가 없음</p>
    </div>
  </div>
</template>

<script>
import { userClasslist } from '@/api/auth.js';
export default {
  data() {
    return {
      learnerDoing: false,
      classLi: [],
      url: null,
    };
  },
  async created() {
    this.url = 'https://k4d106.p.ssafy.io/profile/' + this.$store.state.uuid + '/256';
    const { data } = await userClasslist(this.$store.state.uuid);
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
