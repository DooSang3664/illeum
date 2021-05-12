import Vue from 'vue';
import App from './App.vue';
import router from './router';
import Vuetify from 'vuetify';
import 'vuetify/dist/vuetify.min.css';
import store from './store';
import VueSweetalert2 from 'vue-sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css';
import VueMoment from 'vue-moment';
import VueFullPage from 'vue-fullpage.js';
import { EmojiPickerPlugin } from 'vue-emoji-picker';
import LoadScript from 'vue-plugin-load-script';

Vue.config.productionTip = false;

const sweetalert2_options = {
  confirmButtonColor: '#41b882',
  cancelButtonColor: '#ff7674',
};

Vue.use(VueSweetalert2, sweetalert2_options); // alert API

Vue.use(VueMoment); // vue-moment

Vue.use(Vuetify); // Vuetify

Vue.use(VueFullPage); //VueFullPage

Vue.use(EmojiPickerPlugin); // emoji

Vue.use(LoadScript); // Vue.loadScript, Vue.unloadScript

new Vue({
  router,
  store,
  vuetify: new Vuetify(),
  render: (h) => h(App),
}).$mount('#app');
