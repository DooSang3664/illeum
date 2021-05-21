<template>
  <div class="lecturer">
    <v-tabs v-model="tab" slider-size="2" background-color="#f9f9f9">
      <v-tabs-slider></v-tabs-slider>
      <v-tab>
        준비
      </v-tab>
      <v-tab>
        완료
      </v-tab>
      <v-tab>
        종료
      </v-tab>
    </v-tabs>
    <v-tabs-items v-model="tab" width="1500" color="f4f4f4" style="background-color:#f9f9f9;">
      <v-tab-item>
        <v-card flat color="#f9f9f9">
          <LecturerReady>준비</LecturerReady>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat color="#f9f9f9">
          <LecturerCompleted>완료</LecturerCompleted>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat color="#f9f9f9">
          <LecturerEnd>종료</LecturerEnd>
        </v-card>
      </v-tab-item>
    </v-tabs-items>
  </div>
</template>

<script>
import LecturerReady from '@/views/user/LecturerReady.vue';

import LecturerCompleted from '@/views/user/LecturerCompleted.vue';
import LecturerEnd from '@/views/user/LecturerEnd.vue';

export default {
  components: { LecturerReady, LecturerCompleted, LecturerEnd },
  data() {
    return {
      tab: null,
    };
  },
};
</script>

<style scoped>
.lecturer {
  width: 1400px;
}
.v-tab {
  font-size: 1.2rem;
  font-weight: 900;
}
.v-tab-item {
  background-color: #000;
}
</style>
