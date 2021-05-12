<template>
  <div>
    <h2>강의자-완료(수업시간종료)</h2>
    <div v-if="lecturerReady == true">
      <p>강의완료중인 강좌가 있음</p>
      <div v-for="(item, idx) in classLi" :key="idx">
        <div v-if="new Date(item.end_time) < Date.now() && item.room_state == '준비'">
          <h3>RID:{{ item.rid }}</h3>
          <h3>{{ $store.state.name }}</h3>
          <h3>{{ item.room_name }}</h3>
          <h3>{{ item.room_state }}</h3>
          <h3>{{ item.room_type }}</h3>
          <h3>{{ item.start_time }}</h3>
          <v-img :src="url" id="preview" alt="" style="width:100px; height:100px; left:45%;"></v-img>
          <v-btn color="primary" @click="learnerDataGo(item)">학생평가보기</v-btn>
        </div>
      </div>
    </div>
    <div v-else>
      <p>강의완료중인 강좌가 없음</p>
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
    }
  },
  methods: {
    learnerDataGo(value) {
      const roomData = value;
      console.log(roomData);
      this.$router.push({ name: 'LecSelectedEval', query: { roomData: roomData } });
    },
  },
};
</script>

<style></style>
