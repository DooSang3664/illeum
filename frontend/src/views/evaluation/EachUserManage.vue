<template>
  <tr>
    <td>{{ idx + 1 }}</td>
    <td><v-img :src="url" id="preview" style="width:50px; height:50px;" alt=""></v-img></td>
    <td>{{ each.name }}</td>
    <td>{{ each.email }}</td>

    <td>
      <v-dialog v-if="this.$store.state.token" v-model="dialog" persistent max-width="1200px">
        <template v-slot:activator="{ on, attrs }">
          <v-btn color="primary" dark v-bind="attrs" v-on="on">
            DETAIL
          </v-btn>
        </template>
        <v-card>
          <v-card-title>
            <span class="headline">
              <span>{{ each.name }}님의</span>
              Evaluation
            </span>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-card>
                <v-toolbar flat color="primary" dark>
                  <v-toolbar-title>
                    <span>{{ each.name }}님의</span>
                    Evaluation
                  </v-toolbar-title>
                </v-toolbar>
                <v-tabs vertical>
                  <v-tab>
                    <v-icon left>
                      mdi-account
                    </v-icon>
                    출결
                  </v-tab>
                  <v-tab>
                    <v-icon left>
                      mdi-lock
                    </v-icon>
                    학습태도
                  </v-tab>
                  <v-tab>
                    <v-icon left>
                      mdi-access-point
                    </v-icon>
                    참여현황
                  </v-tab>

                  <v-tab-item>
                    <v-card flat>
                      <h2>출결</h2>
                      <h3>출석시간:{{ each.attend_time }}</h3>
                      <h3>지각여부:{{ each.attend }}</h3>
                    </v-card>
                  </v-tab-item>
                  <v-tab-item>
                    <v-card flat>
                      <LecUserEval :each="each" :rid="rid"></LecUserEval>
                    </v-card>
                  </v-tab-item>
                  <v-tab-item>
                    <v-card flat>
                      <LecUserPartin :each="each" :roomData="roomData" :evalUserCnt="evalUserCnt" :rid="rid"></LecUserPartin>
                    </v-card>
                  </v-tab-item>
                </v-tabs>
              </v-card>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="dialog = false">
              Close
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </td>

    <td>{{ each.attend }}</td>
  </tr>
</template>

<script>
import LecUserEval from '@/views/evaluation/LecUserEval';
import LecUserPartin from '@/views/evaluation/LecUserPartin';

import { findByRidClass } from '@/api/class';

export default {
  components: {
    LecUserEval,
    LecUserPartin,
  },
  data() {
    return {
      url: null,

      eachli: [],
      dialog: false,
      attend: '',
      roomData: [],
      saveTime: '',
    };
  },
  props: {
    each: {
      type: Object,
    },
    idx: {
      type: Number,
    },
    rid: {
      type: Number,
    },
    evalUserCnt: {
      type: Number,
    },
    chipCheck: {
      type: Object,
    },
  },
  async created() {
    this.url = 'https://k4d106.p.ssafy.io/profile/' + this.each.uid + '/256';

    const { data } = await findByRidClass(this.rid);
    this.roomData = data;
    this.$emit('goSearch', this.each.attend);
  },
  methods: {},
};
</script>

<style></style>
