<template>
  <div class="learner">
    <v-card width="900">
      <v-toolbar color="cyan" dark flat>
        <v-toolbar-title>내가 강의하고 있는 강좌</v-toolbar-title>

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
          <v-card flat v-if="item == '준비'">
            <LecturerReady>준비</LecturerReady>
          </v-card>
          <v-card flat v-else-if="item == '진행'">
            <LecturerDoing>진행</LecturerDoing>
          </v-card>
          <v-card flat v-else-if="item == '완료'">
            <LecturerCompleted>완료</LecturerCompleted>
          </v-card>
          <v-card flat v-else>
            <LecturerEnd>종료</LecturerEnd>
          </v-card>
        </v-tab-item>
      </v-tabs-items>
    </v-card>
  </div>
</template>

<script>
import LecturerReady from '@/views/user/LecturerReady.vue';
import LecturerDoing from '@/views/user/LecturerDoing.vue';
import LecturerCompleted from '@/views/user/LecturerCompleted.vue';
import LecturerEnd from '@/views/user/LecturerEnd.vue';

export default {
  components: { LecturerReady, LecturerDoing, LecturerCompleted, LecturerEnd },
  data() {
    return {
      tab: null,
      items: ['준비', '진행', '완료', '종료'],
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
