import Vue from 'vue';
import App from './App.vue';
import router from './router';
import Vuetify from 'vuetify';
import 'vuetify/dist/vuetify.min.css';
import store from './store';
import VueSweetalert2 from 'vue-sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css';
import VueMoment from 'vue-moment';

import { EmojiPickerPlugin } from 'vue-emoji-picker';
import LoadScript from 'vue-plugin-load-script';
import Toast from 'vue-toastification';
import 'vue-toastification/dist/index.css';
import moment from 'moment';

Vue.config.productionTip = false;

const sweetalert2_options = {
  confirmButtonColor: '#41b882',
  cancelButtonColor: '#ff7674',
};

Vue.use(VueSweetalert2, sweetalert2_options); // alert API

moment.locale('ko');
Vue.use(VueMoment, { moment }); // vue-moment

export default new Vuetify({
  breakpoint: {
    thresholds: {
      xs: 340,
      sm: 540,
      md: 800,
      lg: 1280,
    },
    scrollBarWidth: 24,
  },
});

Vue.use(Vuetify); // Vuetify

Vue.use(EmojiPickerPlugin); // emoji

Vue.use(LoadScript); // Vue.loadScript, Vue.unloadScript
//Toast
Vue.use(Toast, {
  transition: 'Vue-Toastification__bounce',
  maxToasts: 3,
  newestOnTop: true,
  filterBeforeCreate: (toast, toasts) => {
    if (toasts.filter((t) => t.type === toast.type).length !== 0) {
      // Returning false discards the toast
      return false;
    }
    // You can modify the toast if you want
    return toast;
  },
});

new Vue({
  router,
  store,
  vuetify: new Vuetify(),
  render: (h) => h(App),
}).$mount('#app');
