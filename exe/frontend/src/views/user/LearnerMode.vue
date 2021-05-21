<template>
  <div class="learner">
    <v-tabs v-model="tab" slider-size="2" background-color="#f9f9f9">
      <v-tabs-slider></v-tabs-slider>
      <v-tab>
        진행
      </v-tab>
      <v-tab>
        완료
      </v-tab>
      <v-tab>
        종료
      </v-tab>
    </v-tabs>

    <v-tabs-items v-model="tab" width="1500" color="#f9f9f9" style="background-color:#f9f9f9;">
      <v-tab-item>
        <v-card flat color="#f9f9f9">
          <LearnerDoing>진행</LearnerDoing>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <LearnerCompleted>완료</LearnerCompleted>
      </v-tab-item>
      <v-tab-item>
        <v-card flat color="#f9f9f9">
          <LearnerEnd>종료</LearnerEnd>
        </v-card>
      </v-tab-item>
    </v-tabs-items>
  </div>
</template>

<script>
import LearnerDoing from '@/views/user/LearnerDoing.vue';
import LearnerCompleted from '@/views/user/LearnerCompleted.vue';
import LearnerEnd from '@/views/user/LearnerEnd.vue';
export default {
  components: { LearnerDoing, LearnerCompleted, LearnerEnd },
  data() {
    return {
      tab: null,
    };
  },
};
</script>

<style scoped>
.learner {
  width: 1400px;
  font-family: 'GongGothicLight';
}
.v-tab {
  font-size: 1.2rem;
  font-weight: 900;
}
.v-tab-item {
  background-color: #000;
}
</style>
