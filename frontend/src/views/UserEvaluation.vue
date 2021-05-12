<template>
  <div id="user-eval">
    <v-alert type="info" elevation="3" color="#304FFE" style="margin-top:10%; width:60vw;">
      <header>{{ this.$store.state.name }}의 평가</header>
    </v-alert>
    <v-tabs v-model="tab" background-color="transparent" color="basil" slider-size="1">
      <v-tab>
        출결현황
      </v-tab>
      <v-tab>
        학습관리
      </v-tab>
      <v-tab>
        참여도
      </v-tab>
    </v-tabs>

    <v-tabs-items v-model="tab">
      <v-tab-item>
        <v-card color="basil" flat>
          <UserAttend></UserAttend>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card color="basil" flat>
          <UserEval></UserEval>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card color="basil" flat>
          <UserPartin></UserPartin>
        </v-card>
      </v-tab-item>
    </v-tabs-items>
  </div>
</template>
<script>
import UserEval from '@/views/evaluation/UserEval';
import UserPartin from '@/views/evaluation/UserPartin';
import UserAttend from './evaluation/UserAttend.vue';
export default {
  components: {
    UserEval,
    UserPartin,
    UserAttend,
  },
  data() {
    return {
      tab: null,
    };
  },
};
</script>

<style scoped>
#user-eval {
  margin: auto;
}
</style>
