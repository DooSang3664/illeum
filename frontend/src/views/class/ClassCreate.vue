<template>
  <div class="classCreate">
    <v-alert
      class="text-start font-weight-black"
      border="left"
      dark
      color="#2E95FF"
      id="alert"
      style="font-size: 1.5rem; letter-spacing: 2.3px; padding-left:20px; line-height: 45px;"
      elevation="3"
      height="70"
    >
      <span>
        <v-icon>mdi-account-check</v-icon>
        클래스만들기
      </span>
    </v-alert>
    <validation-observer ref="observer">
      <form @submit.prevent="submit">
        <!-- room_name -->
        <validation-provider v-slot="{ errors }" name="방 제목" rules="required|max:10">
          <v-text-field v-model="room_name" :counter="10" :error-messages="errors" label="방 제목"></v-text-field>
        </validation-provider>

        <!-- start date/time -->
        <v-row>
          <v-col cols="12" sm="6" md="6">
            <v-menu v-model="start_date_menu" :close-on-content-click="false" :nudge-right="40" transition="scale-transition" offset-y min-width="auto">
              <template v-slot:activator="{ on }">
                <validation-provider v-slot="{ errors }" name="시작날짜" rules="required">
                  <v-text-field :value="start_room_date | dayFilter" label="시작날짜" prepend-icon="mdi-calendar" readonly v-on="on" :error-messages="errors"></v-text-field>
                </validation-provider>
              </template>
              <v-date-picker
                v-model="start_room_date"
                no-title
                scrollable
                color="green lighten-1"
                :weekday-format="getDay"
                :month-format="getMonth"
                :header-date-format="headerDate"
                @input="start_date_menu = false"
              ></v-date-picker>
            </v-menu>
          </v-col>
          <v-col cols="12" sm="6" md="6">
            <v-menu
              ref="start_time_menu"
              v-model="start_time_menu"
              :close-on-content-click="false"
              :nudge-right="40"
              :return-value.sync="start_room_time"
              transition="scale-transition"
              offset-y
              max-width="290px"
              min-width="290px"
            >
              <template v-slot:activator="{ on }">
                <validation-provider v-slot="{ errors }" name="시작시간" rules="required">
                  <v-text-field :value="start_room_time | timeFilter" label="시작시간" prepend-icon="mdi-clock-time-four-outline" readonly v-on="on" :error-messages="errors"></v-text-field>
                </validation-provider>
              </template>
              <v-time-picker scrollable color="#2E95FF" v-if="start_time_menu" v-model="start_room_time" @click:minute="$refs.start_time_menu.save(start_room_time)"></v-time-picker>
            </v-menu>
          </v-col>
        </v-row>

        <!-- hours add buttons -->
        <div v-if="start_room_time != '' && start_room_date != ''">
          <v-btn class="mr-4" @click="addHour(1)" color="#41EA93" dark>
            1시간 추가
          </v-btn>
          <v-btn class="mr-4" @click="addHour(2)" color="#41EA93" dark>
            2시간 추가
          </v-btn>
          <v-btn class="mr-4" @click="addHour(3)" color="#41EA93" dark>
            3시간 추가
          </v-btn>
        </div>

        <!-- end date/time -->
        <v-row>
          <v-col cols="12" sm="6" md="6">
            <v-menu v-model="end_date_menu" :close-on-content-click="false" :nudge-right="40" transition="scale-transition" offset-y min-width="auto">
              <template v-slot:activator="{ on }">
                <validation-provider v-slot="{ errors }" name="종료날짜" rules="required">
                  <v-text-field :value="end_room_date | dayFilter" label="종료날짜" prepend-icon="mdi-calendar" readonly v-on="on" :error-messages="errors"></v-text-field>
                </validation-provider>
              </template>
              <v-date-picker
                v-model="end_room_date"
                no-title
                scrollable
                :weekday-format="getDay"
                :month-format="getMonth"
                :header-date-format="headerDate"
                @input="end_date_menu = false"
              ></v-date-picker>
            </v-menu>
          </v-col>
          <v-col cols="12" sm="6" md="6">
            <v-menu
              ref="end_time_menu"
              v-model="end_time_menu"
              :close-on-content-click="false"
              :nudge-right="40"
              :return-value.sync="end_room_time"
              transition="scale-transition"
              offset-y
              max-width="290px"
              min-width="290px"
            >
              <template v-slot:activator="{ on }">
                <validation-provider v-slot="{ errors }" name="종료시간" rules="required">
                  <v-text-field :value="end_room_time | timeFilter" label="종료시간" prepend-icon="mdi-clock-time-four-outline" readonly v-on="on" :error-messages="errors"></v-text-field>
                </validation-provider>
              </template>
              <v-time-picker color="#2E95FF" scrollable v-if="end_time_menu" v-model="end_room_time" full-width @click:minute="$refs.end_time_menu.save(end_room_time)"></v-time-picker>
            </v-menu>
          </v-col>
        </v-row>

        <!-- room_type / room_password -->
        <validation-provider v-slot="{ errors }" name="공개방/암호방" rules="required">
          <v-radio-group v-model="room_type" row :error-messages="errors">
            <v-radio label="클래스 공개" value="공개"></v-radio>
            <v-radio label="클래스 비공개" value="비공개"></v-radio>
          </v-radio-group>
        </validation-provider>

        <validation-provider v-if="room_type == '비공개'" v-slot="{ errors }" name="방 비밀번호" rules="required|max:10">
          <v-text-field v-model="room_password" :counter="10" :error-messages="errors" label="방 비밀번호"></v-text-field>
        </validation-provider>

        <v-btn class="mr-4" color="#2E95FF" dark type="submit">
          submit
        </v-btn>
        <v-btn class="mr-4" color="#2E95FF" dark @click="clear">
          clear
        </v-btn>
      </form>
    </validation-observer>
  </div>
</template>

<script>
import { required, max } from 'vee-validate/dist/rules';
import { extend, ValidationObserver, ValidationProvider, setInteractionMode } from 'vee-validate';
import { createClass } from '@/api/class';

setInteractionMode('eager');
extend('max', {
  ...max,
  message: '{_field_} 길이는 {length}자 이하만 가능합니다',
});
extend('required', {
  ...required,
  message: '{_field_} 필수항목 입니다.',
});

const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];
const monthsOfYear = ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'];

export default {
  components: {
    ValidationObserver,
    ValidationProvider,
  },
  data() {
    return {
      room_name: '',
      room_type: '',
      room_password: '',
      room_state: '준비',
      start_room_date: '',
      start_room_time: '',
      start_date_menu: false,
      start_time_menu: false,
      end_room_date: '',
      end_room_time: '',
      end_date_menu: false,
      end_time_menu: false,
    };
  },
  filters: {
    timeFilter(value) {
      if (value == '') return '';
      return value.replace(':', '시 ') + ' 분';
    },
    dayFilter(value) {
      if (value == '') return '';
      let korDay = value;
      korDay = korDay.replace(/-/g, '년 ');
      korDay = korDay.replace(/-/g, '월 ');
      return korDay + '일';
    },
  },
  methods: {
    submit() {
      this.$refs.observer.validate().then((result) => {
        if (result) {
          let start_time = this.start_room_date + 'T' + this.start_room_time + ':00Z';
          let end_time = this.end_room_date + 'T' + this.end_room_time + ':00Z';
          let classData = {
            uid: this.$store.state.uuid,
            room_name: this.room_name,
            room_type: this.room_type,
            room_password: this.room_password,
            room_state: this.room_state,
            start_time,
            end_time,
          };

          if (new Date(start_time) < new Date(end_time)) {
            this.classCreate(classData);
          } else {
            this.$swal({
              icon: 'error',
              title: '종료시간이 시작시간보다 앞 시간입니다.!!',
            });
          }
        }
      });
    },
    clear() {
      this.room_name = '';
      this.room_type = '';
      this.start_room_date = '';
      this.start_room_time = '';
      this.end_room_date = '';
      this.end_room_time = '';
      this.$refs.observer.reset();
    },
    back() {
      this.$router.push({ name: 'Home' });
    },
    getDay(date) {
      return daysOfWeek[new Date(date).getDay(date)];
    },
    getMonth(date) {
      return monthsOfYear[new Date(date).getMonth(date)];
    },
    headerDate(date) {
      return new Date(date).getFullYear(date) + ' ' + monthsOfYear[new Date(date).getMonth(date)];
    },
    addHour(hour) {
      this.end_room_date = this.start_room_date;
      let start = parseInt(this.start_room_time.substring(0, 2)) + hour;
      if (start < 10) start = '0' + start;
      if (start >= 24) {
        start = start - 24;
        start = '0' + start;
        this.end_room_date = new Date(new Date(this.end_room_date).setDate(new Date(this.start_room_date).getDate() + 1)).toISOString().split('T')[0];
      }
      this.end_room_time = start + this.start_room_time.substring(2, 5);
    },
    async classCreate(classData) {
      var ref = this;
      try {
        const { data } = await createClass(classData);
        if (data != null) {
          this.$swal({
            icon: 'success',
            title: '<h2>클래스 생성 완료</h2>',
            toast: true,
            width: 600,
            padding: '2em',
            position: 'top-end',
            showConfirmButton: false,
            timer: 5000,
            timerProgressBar: true,
            didOpen: (toast) => {
              toast.addEventListener('mouseenter', ref.$swal.stopTimer);
              toast.addEventListener('mouseleave', ref.$swal.resumeTimer);
            },
          });
          this.$router.push({ name: 'WebRTCListTeacher' });
        }
      } catch {
        this.$swal({
          icon: 'error',
          title: '방 생성 오류.!!',
        });
      }
    },
  },
};
</script>

<style scoped>
@font-face {
  font-family: 'GongGothicLight';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-10@1.0/GongGothicLight.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}
.classCreate {
  margin: 3% 2%;
  font-family: 'GongGothicLight';
}
form {
  border: 2px solid rgb(73, 72, 72);
  border-radius: 20px;
  padding: 1rem 3rem;
  background: #f6f6f6;
}
</style>
