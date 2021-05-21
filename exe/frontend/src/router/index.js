import Vue from 'vue';
import VueRouter from 'vue-router';
import store from '@/store/index';

import LandingComp from '../views/LandingComp.vue';
import Landing from '../views/Landing.vue';

import Navbar from '../components/layout/Navbar.vue';
import Guide from '../views/components/Guide.vue';
import Clock from '../views/components/Clock.vue';

//user
import Sign from '../views/user/Sign.vue';
import MyClass from '../views/MyClass.vue';

//class

import ClassSearch from '../views/class/ClassSearch.vue';
import ClassCreate from '../views/class/ClassCreate.vue';

import ClassList from '../views/class/ClassList.vue';
import ClassJoin from '../views/class/ClassJoin.vue';

//WebRTC
import WebRTCListAll from '../views/webRTC/WebRTCListAll.vue';
import WebRTCListTeacher from '../views/webRTC/WebRTCListTeacher.vue';
import WebRTCListStudent from '../views/webRTC/WebRTCListStudent.vue';
import TeacherWebRTC from '../views/webRTC/TeacherWebRTC.vue';
import StudentWebRTC from '../views/webRTC/StudentWebRTC.vue';

//evaluation
import UserEval from '../views/evaluation/UserEval.vue';
import UserEvaluation from '../views/UserEvaluation.vue';
import LecturerManage from '../views/evaluation/LecturerManage.vue';
import LecSelectedEval from '../views/evaluation/LecSelectedEval.vue';

// 테스트용 임포트 페이지
import navTest from '../views/testpage/navTest.vue';
import calendarTest from '../views/testpage/calendarTest.vue';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'Landing',
    components: { default: Landing },
  },

  {
    path: '/guide',
    name: 'Guide',
    components: { default: Guide },
  },
  {
    path: '/landingcomp',
    name: 'LandingComp',
    components: { default: LandingComp },
  },
  {
    path: '/clock',
    name: 'Clock',
    components: { default: Clock },
    meta: { auth: true },
  },
  {
    path: '/lecturermanage',
    name: 'LecturerManage',
    components: { default: LecturerManage, header: Navbar },
    meta: { auth: true },
  },

  {
    path: '/lecselectedeval',
    name: 'LecSelectedEval',
    components: { default: LecSelectedEval, header: Navbar },
    meta: { auth: true },
  },
  {
    path: '/sign',
    name: 'Sign',
    components: { default: Sign },
  },
  {
    path: '/myclass',
    name: 'MyClass',
    components: { default: MyClass, header: Navbar },
    meta: { auth: true },
  },
  //class

  {
    path: '/classsearch',
    name: 'ClassSearch',
    components: { default: ClassSearch, header: Navbar },
    meta: { auth: true },
  },
  {
    path: '/classcreate',
    name: 'ClassCreate',
    components: { default: ClassCreate, header: Navbar },
    meta: { auth: true },
  },

  {
    path: '/classlist',
    name: 'ClassList',
    components: { default: ClassList, header: Navbar },
    meta: { auth: true },
  },
  {
    path: '/classjoin',
    name: 'ClassJoin',
    components: { default: ClassJoin, header: Navbar },
    meta: { auth: true },
  },
  //webRTC
  {
    path: '/webrtclistall',
    name: 'WebRTCListAll',
    components: { default: WebRTCListAll, header: Navbar },
    meta: { auth: true },
  },
  {
    path: '/webrtclistteacher',
    name: 'WebRTCListTeacher',
    components: { default: WebRTCListTeacher, header: Navbar },
    meta: { auth: true },
  },
  {
    path: '/webrtcliststudent',
    name: 'WebRTCListStudent',
    components: { default: WebRTCListStudent, header: Navbar },
    meta: { auth: true },
  },
  {
    path: '/teacherwebrtc',
    name: 'TeacherWebRTC',
    components: { default: TeacherWebRTC },
    meta: { auth: true },
  },
  {
    path: '/studentwebrtc',
    name: 'StudentWebRTC',
    components: { default: StudentWebRTC },
    meta: { auth: true },
  },
  //evaluation
  {
    path: '/userevaluation',
    name: 'UserEvaluation',

    components: { default: UserEvaluation, header: Navbar },
    meta: { auth: true },
  },

  // 테스트용 라우터 세팅
  {
    path: '/navTest',
    name: 'navTest',
    component: navTest,
  },
  {
    path: '/calendarTest',
    name: 'calendarTest',
    component: calendarTest,
  },
  // // end
  // {
  //   path: '/about',
  //   name: 'About',
  //   component: () => import('../views/About.vue'),
  //   meta: { auth: true },
  // },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});
router.beforeEach((to, from, next) => {
  if (to.meta.auth && !store.getters.isLogin) {
    Vue.swal({
      icon: 'error',
      title: '로그인후 사용가능한 서비스입니다.',
      showConfirmButton: false,
      timer: 1500,
    });
    next('/sign');
    return;
  }
  next();
});
export default router;
