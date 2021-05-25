import store from '@/store/index';
import Vue from 'vue';

export function setInterceptors(instance) {
  // Add a request interceptor
  instance.interceptors.request.use(
    function(config) {
      // Do something before request is sent
      config.headers['Authorization'] = 'Bearer ' + store.state.token; //요청시 토큰 셋팅
      return config;
    },
    function(error) {
      // Do something with request error
      if (error.response.status === 401) {
        Vue.swal({
          icon: 'warning',
          title: '세션만료',
          text: '다시 로그인 해주세요',
          confirmButtonText: '확인',
        }).then((result) => {
          if (result.isConfirmed) {
            localStorage.clear();
            sessionStorage.clear();
            window.location.href = 'sign';
          }
        });
      }

      return Promise.reject(error);
    }
  );

  // Add a response interceptor
  instance.interceptors.response.use(
    function(response) {
      // Any status code that lie within the range of 2xx cause this function to trigger
      // Do something with response data
      return response;
    },
    function(error) {
      // Any status codes that falls outside the range of 2xx cause this function to trigger
      // Do something with response error
      if (error.response.status === 401) {
        Vue.swal({
          icon: 'warning',
          title: '세션만료',
          text: '다시 로그인 해주세요',
          confirmButtonText: '확인',
        }).then((result) => {
          if (result.isConfirmed) {
            localStorage.clear();
            sessionStorage.clear();
            window.location.href = 'sign';
          }
        });
      }

      return Promise.reject(error);
    }
  );
  return instance;
}
