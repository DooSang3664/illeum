<template>
  <div class="learner">
    <v-card width="900">
      <v-toolbar color="cyan" dark flat>
        <v-toolbar-title>내가 수강하고 있는 강좌</v-toolbar-title>

        <v-spacer></v-spacer>

        <v-btn icon>
          <v-icon>mdi-magnify</v-icon>
        </v-btn>

        <v-btn icon>
          <v-icon>mdi-dots-vertical</v-icon>
        </v-btn>

        <template v-slot:extension>
          <v-tabs v-model="tab" align-with-title>
            <v-tabs-slider color="yellow"></v-tabs-slider>

            <v-tab v-for="item in items" :key="item">
              {{ item }}
            </v-tab>
          </v-tabs>
        </template>
      </v-toolbar>

      <v-tabs-items v-model="tab">
        <v-tab-item v-for="item in items" :key="item">
          <v-card flat v-if="item == '진행'">
            <LeanerDoing>진행</LeanerDoing>
          </v-card>
          <v-card flat v-else-if="item == '완료'">
            <LearnerCompleted>완료</LearnerCompleted>
          </v-card>
          <v-card flat v-else>
            <LearnerEnd>종료</LearnerEnd>
          </v-card>
        </v-tab-item>
      </v-tabs-items>
    </v-card>
  </div>
</template>

<script>
import LeanerDoing from '@/views/user/LearnerDoing.vue';
import LearnerCompleted from '@/views/user/LearnerCompleted.vue';
import LearnerEnd from '@/views/user/LeanerEnd.vue';
export default {
  components: { LeanerDoing, LearnerCompleted, LearnerEnd },
  data() {
    return {
      tab: null,
      items: ['진행', '완료', '종료'],
      texts:
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
    };
  },
};
</script>

<style scoped>
.learner {
  margin-left: 25%;
  margin-top: 5%;
}
</style>
