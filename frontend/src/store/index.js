import Vue from 'vue';
import Vuex from 'vuex';
import createPersistedState from 'vuex-persistedstate';
// import { loginUser, fetchUser } from '@/api/auth';
import router from '../router/index';
Vue.use(Vuex);

export default new Vuex.Store({
  plugins: [createPersistedState()],
  state: {
    token: '',
    email: '',
    password: '',
    nickname: '',
    uuid: '',
    type: '',
  },
  //   getters: {
  //     isLogin(state) {
  //       return state.token !== '';
  //     },
  //   },
  //   mutations: {
  //     //토큰
  //     setToken(state, token) {
  //       state.token = token;
  //     },
  //     clearToken(state) {
  //       state.token = '';
  //     },
  //     //이메일
  //     setEmail(state, email) {
  //       state.email = email;
  //     },
  //     clearEmail(state) {
  //       state.email = '';
  //     },
  //     setPassword(state, password) {
  //       state.password = password;
  //     },
  //     clearPassword(state) {
  //       state.password = '';
  //     },
  //     setNickname(state, nickname) {
  //       state.nickname = nickname;
  //     },
  //     clearNickname(state) {
  //       state.nickname = '';
  //     },

  //     //일반유저
  //     setUuid(state, uuid) {
  //       state.uuid = uuid;
  //     },
  //     clearUuid(state) {
  //       state.uuid = '';
  //     },
  //     setType(state, type) {
  //       state.type = type;
  //     },
  //     clearType(state) {
  //       state.type = '';
  //     },
  //   },

  //   actions: {
  //     async LOGIN({ commit }, userData) {
  //       const data = await loginUser(userData);
  //       // console.log(data);
  //       if (data.data.message == 'SUCCESS') {
  //         commit('setToken', data.data['access-token']);
  //         commit('setEmail', userData.email);
  //         commit('setPassword', userData.password);
  //         const response = await fetchUser(userData.email);
  //         // console.log(response.data);
  //         commit('setUuid', response.data.uuid);
  //         commit('setType', response.data.type);
  //         if (response.data.type == 1) {
  //           commit('setNickname', response.data.nickname);
  //         }
  //         router.push('/main');
  //       } else {
  //         Vue.swal({
  //           icon: 'error',
  //           title: '로그인 실패! 이메일 및 비밀번호를 확인해 주세요!',
  //         });
  //       }
  //     },
  //   },
});
