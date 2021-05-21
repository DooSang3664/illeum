<template>
  <div id="user-eval">
    <v-alert
      class="text-start font-weight-black"
      border="left"
      dark
      color="#2E95FF"
      style="font-size: 1.5rem; letter-spacing: 2.3px; padding-left:20px; line-height: 45px;"
      elevation="3"
      height="70"
      width="1400"
    >
      <span>
        <v-icon>mdi-account-check</v-icon>
        {{ $store.state.name }}
      </span>
      님의 평가
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

    <v-tabs-items v-model="tab" style="background:#f9f9f9;">
      <v-tab-item>
        <v-card flat color="#f9f9f9">
          <UserAttend></UserAttend>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat color="#f9f9f9">
          <UserEval></UserEval>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat color="#f9f9f9">
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
@font-face {
  font-family: 'NEXON Lv1 Gothic OTF';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv1 Gothic OTF.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}

#user-eval {
  margin: 3% 2%;
  font-family: 'NEXON Lv1 Gothic OTF';
}
</style>
