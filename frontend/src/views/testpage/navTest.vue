<template>
  <v-app id="inspire">
    <v-navigation-drawer v-model="drawer" app>
      <img src="../../assets/img/textlogo.png" class="Navlogo" alt="" style="width:200px; height:80px;" />
      <hr />

      <v-sheet color="" class="pa-4">
        <v-avatar class="mb-4" color="grey darken-1" size="64">
          <v-img :src="'https://k4d106.p.ssafy.io/profile/' + this.$store.state.uuid + '/256'" id="preview" alt=""></v-img>
        </v-avatar>
        <div>{{ this.$store.state.email }}</div>
      </v-sheet>
      <!-- 프로필 모달 -->
      <v-dialog v-if="this.$store.state.token" v-model="dialog" persistent max-width="800px">
        <template v-slot:activator="{ on, attrs }">
          <v-btn color="primary" dark v-bind="attrs" v-on="on">
            PROFILE
          </v-btn>
        </template>
        <v-card>
          <v-card-title>
            <span class="headline">
              <span>{{ this.$store.state.name }}님의</span>
              Profile
            </span>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12">
                  <!-- <v-col cols="12" sm="6" md="6"> -->
                  <div>PROFILE IMAGE</div>
                  <!-- <img src="../../assets/img/greenlogo.png" class="Navlogo" alt="" /> -->
                  <div class="formdata">
                    <v-file-input
                      id="thumbnail"
                      name="thumbnail"
                      v-model="image"
                      show-size
                      label="프로필 이미지 수정을 위해 입력창을 클릭해주세요"
                      @change="Preview_image($event)"
                      style=" cursor : pointer;"
                    ></v-file-input>
                    <v-img :src="url" id="preview" style="width:100px; height:100px;"></v-img>
                  </div>
                </v-col>
                <v-col cols="12">
                  <label for="">계정:</label>
                  {{ this.$store.state.email }}
                </v-col>
                <v-col cols="12">
                  <label for="">이름:</label>
                  {{ this.$store.state.name }}
                </v-col>
                <v-col cols="12" sm="6">
                  <v-row>
                    <label for="password">비밀번호:</label>
                    <input type="password" id="password" v-model="password" disabled placeholder="*********" />
                  </v-row>
                  <v-row>
                    <label for="passwordcheck">비밀번호확인:</label>
                    <input type="password" id="passwordcheck" v-model="passwordchk" disabled value="" />
                  </v-row>
                  <v-row>
                    <template v-if="correspond">
                      <v-btn id="completed" @click="editPassword">수정완료</v-btn>
                    </template>
                    <template v-else>
                      <v-btn @click="updatePassword">수정하기</v-btn>
                    </template>
                  </v-row>
                </v-col>
                <v-col cols="12" sm="6"></v-col>
              </v-row>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="dialog = false">
              Close
            </v-btn>
            <v-btn color="blue darken-1" text @click="userUpdate">
              Save
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <v-btn v-if="this.$store.state.token" @click="signoutUser">LOGOUT</v-btn>
      <v-divider></v-divider>

      <v-list>
        <v-list-item>
          <router-link to="/home">Home</router-link>
        </v-list-item>
        <v-list-item v-if="loginchk === null || loginchk === undefined || loginchk === ''">
          <router-link to="/sign">Sign</router-link>
        </v-list-item>
        <v-list-item>
          <router-link to="/myclass">MY CLASS</router-link>
        </v-list-item>
        <v-list-item>
          <router-link to="/classlist">class</router-link>
        </v-list-item>
        <v-list-item>
          <router-link to="/classcreate">클래스 오픈(강의자)</router-link>
        </v-list-item>
        <v-list-item>
          <router-link to="/classjoin">클래스 목록페이지(수강자)</router-link>
        </v-list-item>
        <v-list-item>
          <router-link to="/webrtclist">화상수업 목록페이지</router-link>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-main></v-main>
  </v-app>
</template>

<script>
import { logoutUser, editUser, createThumbnails } from '@/api/auth';
export default {
  data() {
    return {
      cards: ['Today', 'Yesterday'],
      drawer: null,
      links: [
        ['mdi-inbox-arrow-down', 'HOME'],
        ['mdi-send', 'MY CLASS'],
        ['mdi-delete', 'Class Open(강의자)'],
        ['mdi-alert-octagon', 'Class List(수강자)'],
        ['mdi-alert-octagon', '화상수업목록'],
        ['mdi-alert-octagon', 'EVALUATION'],
      ],
      dialog: false,
      image: null,
      url: null,
      loginchk: this.$store.state.token,
      password: '',
      passwordchk: '',
      correspond: false,
    };
  },
  created() {
    this.url = `/profile/${this.$store.state.uuid}/256?t=${Date.now()}`;
    console.log(this.loginchk);
  },
  watch: {
    passwordchk(value) {
      this.passwordchk = value;
      if (this.password == this.passwordchk && this.password != '' && this.passwordchk != '') {
        this.correspond = true;
      }
    },
  },
  methods: {
    Preview_image() {
      if (this.image != null) this.url = URL.createObjectURL(this.image);
    },
    async userUpdate() {
      const uuid = this.$store.state.uuid;
      var frm = new FormData();
      frm.append('file', this.image);
      const { data } = await createThumbnails(uuid, frm);
      this.dialog = false;
    },
    async signoutUser() {
      const userData = this.$store.state.token;
      const { data } = await logoutUser(userData);
      localStorage.clear();
      sessionStorage.clear();
      this.$router.push('/sign');

      if (data == 'success') {
        this.$store.commit('clearToken');
        this.$store.commit('clearUuid');
        this.$store.commit('clearEmail');
        this.$store.commit('clearRole');
        this.$store.commit('clearName');
        console.log('로그아웃 성공');

        this.$store.state.alertSocket.disconnect();
      } else {
        console.log('로그아웃실패');
      }
    },
    updatePassword() {
      const password = document.querySelector('#password');
      const passwordcheck = document.querySelector('#passwordcheck');
      password.disabled = false;
      password.focus();
      passwordcheck.disabled = false;
      passwordcheck.classList.add('pwdchk-input');
    },
    async editPassword() {
      const userData = {
        email: this.$store.state.email,
        name: this.$store.state.name,
        paswword: this.password,
      };
      const { data } = await editUser(userData);
      if (data == 'success') {
        this.$swal({
          icon: 'success',
          title: '수정완료되었습니다',
        });
      }
    },
  },
};
</script>
