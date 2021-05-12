<template>
  <div>
    <h2>강의자-진행</h2>
    <div v-if="lecturerReady == true">
      <p>강의진행중인 강좌가 있음</p>
      <div v-for="(item, idx) in classLi" :key="idx">
        <div v-if="item.room_state == '진행'">
          <h3>{{ item.rid }}</h3>
          <h3>{{ $store.state.name }}</h3>
          <h3>{{ item.room_name }}</h3>
          <h3>{{ item.room_state }}</h3>
          <h3>{{ item.room_type }}</h3>
          <h3>{{ item.start_time }}</h3>
          <v-img :src="url" id="preview" alt="" style="width:100px; height:100px; left:45%;"></v-img>
        </div>
      </div>
    </div>
    <div v-else>
      <p>강의진행중인 강좌가 없음</p>
    </div>
  </div>
</template>

<script>
import { userClasslist } from '@/api/auth.js';
export default {
  data() {
    return {
      lecturerReady: false,
      classLi: [],
      url: null,
    };
  },
  async created() {
    this.url = 'https://k4d106.p.ssafy.io/profile/' + this.$store.state.uuid + '/256';
    const { data } = await userClasslist(this.$store.state.uuid);
    console.log(data);
    if (data) {
      this.lecturerReady = true;
      this.classLi = data;
    } else {
    }
  },
};
</script>

<style></style>
