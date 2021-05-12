<template>
  <div>
    <h2>강의자-준비</h2>
    <div v-if="lecturerReady == true">
      <p>강의준비중인 강좌가 있음</p>
      <div v-for="(item, idx) in classLi" :key="idx">
        <div v-if="item.room_state == '준비'">
          <h3>{{ item.rid }}</h3>
          <h3>{{ $store.state.name }}</h3>
          <h3>{{ item.room_name }}</h3>
          <h3>{{ item.room_state }}</h3>
          <h3>{{ item.room_type }}</h3>
          <h3>{{ item.start_time }}</h3>
          <v-img :src="url" id="preview" alt="" style="width:100px; height:100px; left:45%;"></v-img>
          <v-btn @click="startRTC(item)">수업시작</v-btn>
        </div>
      </div>
    </div>
    <div v-else>
      <p>강의준비중인 강좌가 없음</p>
    </div>
  </div>
</template>

<script>
import { start } from '@/api/rabbit';
import { updateClass } from '@/api/class';
import { userClasslist } from '@/api/auth.js';
export default {
  data() {
    return {
      lecturerReady: false,
      classLi: [],
      url: null,
    };
  },
  async created() {
    this.url = 'https://k4d106.p.ssafy.io/profile/' + this.$store.state.uuid + '/256';
    const { data } = await userClasslist(this.$store.state.uuid);
    console.log(data);
    if (data) {
      this.lecturerReady = true;
      this.classLi = data;
    } else {
    }
  },
  mounted() {
    let cdn1 = document.createElement('script');
    cdn1.setAttribute('src', 'https://cdn.jsdelivr.net/npm/rtcmulticonnection@latest/dist/RTCMultiConnection.min.js');
    cdn1.setAttribute('id', 'cdn1');
    document.body.appendChild(cdn1);

    let cdn2 = document.createElement('script');
    cdn2.setAttribute('src', 'https://rtcmulticonnection.herokuapp.com/socket.io/socket.io.js');
    cdn2.setAttribute('id', 'cdn2');
    document.body.appendChild(cdn2);
  },
  methods: {
    async startRTC(value) {
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
              const { data } = await updateClass({ rid: value.rid, room_state: '진행' }); // PUT: /api/room/updateByRid
              if (data == 'success') {
                const { data } = await start(value.rid); // GET: /api/rtc/start (rabbitMQ)
                console.log(data);
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
          const { data } = await updateClass({ rid: value.rid, room_state: '진행' }); // PUT: /api/room/updateByRid
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
  },
  destroyed() {
    // cdn 제거
    var el1 = document.querySelector('#cdn1');
    el1.remove();
    var el2 = document.querySelector('#cdn2');
    el2.remove();
  },
};
</script>

<style></style>
