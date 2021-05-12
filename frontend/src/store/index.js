import Vue from 'vue';
import Vuex from 'vuex';
import createPersistedState from 'vuex-persistedstate';
import { loginUser } from '@/api/auth';
import router from '@/router/index';
Vue.use(Vuex);

export default new Vuex.Store({
  plugins: [createPersistedState()],
  state: {
    token: '',
    email: '',
    name: '',
    uuid: '',
    role: '',
    alertSocket: '',
  },
  getters: {
    isLogin(state) {
      return state.token !== '';
    },
  },
  mutations: {
    // allert rabbitmq socket
    setAlertSocket(state, alertSocket) {
      state.alertSocket = alertSocket;
    },
    clearAlertSocket(state) {
      state.alertSocket.close();
    },
    //토큰
    setToken(state, token) {
      state.token = token;
    },
    clearToken(state) {
      state.token = '';
    },

    //이메일
    setEmail(state, email) {
      state.email = email;
    },
    clearEmail(state) {
      state.email = '';
    },

    //이름
    setName(state, name) {
      state.name = name;
    },
    clearName(state) {
      state.name = '';
    },

    //uuid
    setUuid(state, uuid) {
      state.uuid = uuid;
    },
    clearUuid(state) {
      state.uuid = '';
    },
    //role
    setRole(state, role) {
      state.role = role;
    },
    clearRole(state) {
      state.role = '';
    },
  },

  actions: {
    async LOGIN({ commit }, userData) {
      await loginUser(userData)
        .then(({ data }) => {
          if (data == 'wrong password') {
            Vue.swal({
              icon: 'error',
              title: '비밀번호가 틀렸습니다.!!',
            });
          } else if (data == 'not register') {
            Vue.swal({
              icon: 'error',
              title: '등록되지 않은 사용자입니다.!!',
            });
          } else if (data.accessToken !== '') {
            commit('setToken', data.accessToken);
            commit('setUuid', data.member.uid);
            commit('setEmail', data.member.email);
            commit('setName', data.member.name);
            commit('setRole', data.member.role);
            router.push('/myclass');
          }
        })
        .catch(() => {
          Vue.swal({
            icon: 'error',
            title: '로그인 실패! 이메일 및 비밀번호를 확인해 주세요!',
          });
        });
    },
    async TEST({ commit }, test) {
      const data = test;
      console.log(date);
      commit('setTest', data);
    },
  },
});
