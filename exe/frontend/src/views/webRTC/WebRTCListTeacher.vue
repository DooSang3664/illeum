<template>
  <div class="webRTCList">
    <v-alert
      class="text-start font-weight-black"
      border="left"
      dark
      id="alert"
      color="#2E95FF"
      style="margin-top:6%; font-size: 1.5rem; letter-spacing: 2.3px; padding-left:20px; line-height: 45px;"
      elevation="3"
      height="70"
    >
      <span>
        <v-icon>mdi-account-check</v-icon>
        화상수업리스트
      </span>
    </v-alert>
    <template v-if="!isLoading">
      <v-data-table
        :headers="headers"
        :items="rooms"
        :items-per-page="10"
        item-key="rid"
        no-data-text="개설한 클래스가 없습니다"
        class="elevation-1 table-list"
        :footer-props="{
          showFirstLastPage: false,
          prevIcon: 'mdi-arrow-left',
          nextIcon: 'mdi-arrow-right',
          'page-text': '',
          'items-per-page-text': '페이지당 화상회의수',
        }"
      >
        <template v-slot:[`item.action`]="{ item }">
          <template v-if="checkUser(item) == '강의자'">
            <v-btn color="info" @click="startWebRTC(item)" :width="100" :disabled="!hasWebcam">수업 생성</v-btn>
          </template>
          <template v-else-if="item.room_state == '진행'">
            <v-btn color="success" @click="joinWebRTC(item)" :width="100" :disabled="!hasWebcam">수업 참여</v-btn>
          </template>
          <template v-else>
            <v-btn color="warning" disabled :width="100">준비중</v-btn>
          </template>
        </template>
      </v-data-table>
    </template>
    <template v-else>
      <div class="loading-spin">
        <circle8></circle8>
      </div>
    </template>
  </div>
</template>
<script>
import { Circle8 } from 'vue-loading-spinner';
import { classAll, updateClass } from '@/api/class';
import { start } from '@/api/rabbit';
export default {
  components: { Circle8 },
  data() {
    return {
      headers: [
        {
          text: '방제목',
          align: 'start',
          value: 'room_name',
        },
        { text: '시작시간', value: 'start_time' },
        { text: '진행/준비', value: 'room_state' },
        { text: '공개/비공개', value: 'room_type' },
        { text: '', value: 'action' },
      ],
      rooms: [],
      time: null, // for camera check interval
      hasWebcam: false,
      isLoading: true,
    };
  },
  async created() {
    const { data } = await classAll();
    for (let index = 0; index < data.length; index++) {
      if (data[index].room_state != '종료' && data[index].uid == this.$store.state.uuid) {
        this.rooms.push(data[index]);
      }
    }

    for (let index = 0; index < this.rooms.length; index++) {
      this.rooms[index].start_time = this.$moment(this.rooms[index].start_time).format('llll');
    }
    this.isLoading = false;

    var ref = this;
    navigator.mediaDevices
      .getUserMedia({ video: true })
      .then(function(stream) {
        ref.hasWebcam = true;
        stream.getTracks()[0].stop();
      })
      .catch((err) => {
        ref.$swal({
          icon: 'error',
          title: '웹캠이 존재하지 않거나<br/> 다른 프로그램에서 사용중입니다.!!',
        });
        ref.hasWebcam = false;
      });
  },
  methods: {
    main() {
      this.$router.push({ name: 'Home' });
    },
    checkUser(value) {
      if (value.uid == this.$store.state.uuid) return '강의자';
      else return '참여자';
    },
    async startWebRTC(value) {
      if (value.room_type == '비공개') {
        const { value: room_password } = await this.$swal({
          icon: 'question',
          title: '비밀번호를 입력해 주세요',
          input: 'text',
          showCancelButton: true,
        });

        if (room_password != undefined) {
          if (room_password != value.room_password) {
            this.$swal({
              icon: 'error',
              title: '클래스 비밀번호가 일치하지 않습니다.!!',
            });
          } else {
            try {
              var start_time = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('.')[0] + '.000Z';
              const { data } = await updateClass({ rid: value.rid, room_state: '진행', start_time }); // PUT: /api/room/updateByRid
              if (data == 'success') {
                const { data } = await start(value.rid); // GET: /api/rtc/start (rabbitMQ)
                if (data == 'success') this.$router.push({ name: 'TeacherWebRTC', query: { room_name: value.room_name, rid: value.rid } });
              }
            } catch {
              this.$swal({
                icon: 'error',
                title: '화상수업 생성 오류.!!',
              });
            }
          }
        }
      } else {
        try {
          var start_time = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('.')[0] + '.000Z';
          const { data } = await updateClass({ rid: value.rid, room_state: '진행', start_time }); // PUT: /api/room/updateByRid
          if (data == 'success') {
            const { data } = await start(value.rid); // GET: /api/rtc/start (rabbitMQ)
            if (data == 'success') this.$router.push({ name: 'TeacherWebRTC', query: { room_name: value.room_name, rid: value.rid } });
          }
        } catch {
          this.$swal({
            icon: 'error',
            title: '화상수업 생성 오류.!!',
          });
        }
      }
    },
    async joinWebRTC(value) {
      if (value.room_type == '비공개') {
        const { value: room_password } = await this.$swal({
          icon: 'question',
          title: '비밀번호를 입력해 주세요',
          input: 'text',
          showCancelButton: true,
        });

        if (room_password != undefined) {
          if (room_password != value.room_password) {
            this.$swal({
              icon: 'error',
              title: '클래스 비밀번호가 일치하지 않습니다.!!',
            });
          } else {
            this.$router.push({ name: 'StudentWebRTC', query: { room_name: value.room_name, rid: value.rid } });
          }
        }
      } else {
        this.$router.push({ name: 'StudentWebRTC', query: { room_name: value.room_name, rid: value.rid } });
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
.webRTCList {
  margin: 3% 2%;
  font-family: 'GongGothicLight';
}
.loading-spin {
  display: inline-block;
  justify-content: center;
  margin-top: 10%;
}
/* 테이블 css: WebRTCListStudent.vue css단에서 전역으로 구현 */
</style>
