<template>
  <div class="about">
    <h1>컴포넌트테스트페이지</h1>
    <v-btn depressed color="primary" @click="sweetalertTest1">sweetalert test1</v-btn>
    <br />
    <br />
    <v-btn depressed class="btn" color="primary" @click="sweetalertTest1">sweetalert test1</v-btn>
    <br />
    <br />
    <v-btn depressed class="btn" color="primary" @click="sweetalertTest2">sweetalert test2</v-btn>
    <br />
    <br />
    <v-btn depressed class="btn" color="error" @click="sweetalertTest3">sweetalert test3</v-btn>
    <br />
    <br />
    <v-btn depressed class="btn" color="error" @click="pushTest">Push.JS테스트</v-btn>
    <br />
    <br />
    <br />
    <template>
      <v-sheet class="mx-auto" elevation="8" max-width="800">
        <v-slide-group v-model="model" class="pa-4" center-active show-arrows>
          <v-slide-item v-for="n in 15" :key="n" v-slot="{ active, toggle }">
            <v-card :color="active ? 'primary' : 'grey lighten-1'" class="ma-4" height="200" width="100" @click="toggle">
              <v-row class="fill-height" align="center" justify="center">
                <v-scale-transition>
                  <v-icon v-if="active" color="white" size="48" v-text="'mdi-close-circle-outline'"></v-icon>
                </v-scale-transition>
              </v-row>
            </v-card>
          </v-slide-item>
        </v-slide-group>
      </v-sheet>
    </template>
  </div>
</template>
<script>
import push from 'push.js';

export default {
  data() {
    return {
      model: null,
    };
  },
  methods: {
    sweetalertTest1() {
      this.$swal({
        icon: 'success',
        title: '테스트',
        confirmButtonText: `확인`,
      });
    },
    sweetalertTest2() {
      this.$swal({
        icon: 'warning',
        title: '에러테스트!',
        showCancelButton: true,
        confirmButtonText: `확인`,
        cancelButtonText: `취소`,
      });
    },
    async sweetalertTest3() {
      const { value: email } = await this.$swal({
        title: 'Input email address',
        input: 'email',
        inputLabel: 'Your email address',
        inputPlaceholder: 'Enter your email address',
      });

      if (email) {
        this.$swal(`Entered email: ${email}`);
      }
    },
    pushTest() {
      push.create('GoodBye World!');
    },
  },
};
</script>

<style></style>
