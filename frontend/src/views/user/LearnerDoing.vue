<template>
  <div id="learner-doing">
    <div v-if="learnerDoing == true">
      <div v-for="(item, idx) in classLi" :key="idx">
        <div v-if="item.room_state == '진행'">
          <div class="blog-card">
            <input type="radio" name="select" id="tap-1" checked />
            <input type="checkbox" id="imgTap" />

            <div class="inner-part">
              <label for="imgTap" class="img">
                <!-- founderUid -->
                <v-img :src="'https://k4d106.p.ssafy.io/profile/' + item.founder_uid + '/256'" id="preview" alt="" style="width:100px; height:100px; left:45%;"></v-img>
              </label>
              <div class="content content-1">
                <span>클래스명:{{ item.room_name }}</span>

                <div class="title">강의자:{{ item.founder }}</div>
                <div class="text">공개여부:{{ item.room_type }}</div>
                <div class="text">시작시간:{{ item.start_time }}</div>
                <div class="text">종료시간:{{ item.end_time }}</div>
                <v-btn class="button" x-large style=" padding: 20px 30px; font-size: 1.2rem; margin-left: 200px;" color="primary" @click="attendClass(item)">수업참여</v-btn>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-else>
      <p>수강진행중인 강좌가 없음</p>
    </div>
  </div>
</template>

<script>
import { usePartinClass } from '@/api/auth.js';
// import { findByRidClass } from '@/api/class.js';
export default {
  data() {
    return {
      learnerDoing: false,
      classLi: [],
      founderUid: '',
    };
  },
  async created() {
    const { data } = await usePartinClass(this.$store.state.uuid);
    console.log(data);
    if (data) {
      this.learnerDoing = true;
      this.classLi = data;
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
    async attendClass(value) {
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
  destroyed() {
    // cdn 제거
    var el1 = document.querySelector('#cdn1');
    el1.remove();
    var el2 = document.querySelector('#cdn2');
    el2.remove();
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.blog-card {
  /* position: absolute; */
  height: 270px;
  width: 75%;
  max-width: 850px;
  margin: 20px auto;
  border-radius: 25px;
  background: white;
  box-shadow: 0px 10px 50px rgba(0, 0, 0, 0.5);
}
.inner-part {
  position: absolute;
  display: flex;
  height: 280px;
  align-items: center;
  justify-content: center;
  padding: 0 25px;
}
#imgTap:checked ~ .inner-part {
  padding: 0;
  transition: 0.1s ease-in;
}
.inner-part .img {
  height: 220px;
  width: 220px;
  flex-shrink: 0;
  overflow: hidden;
  border-radius: 20px;
  box-shadow: 2px 3px 15px rgba(0, 0, 0, 0.1);
}
#imgTap:checked ~ .inner-part .img {
  height: 370px;
  width: 850px;
  z-index: 99;
  margin-top: 10px;
  transition: 0.3s 0.2s ease-in;
}
.img img {
  height: 100%;
  width: 100%;
  object-fit: cover;
  cursor: pointer;
  opacity: 0;
  transition: 0.6s;
}
#tap-1:checked ~ .inner-part .img-1 {
  opacity: 1;
  transition-delay: 0.2s;
}
.content {
  padding: 0 10px 0 10px;
  width: 380px;
  margin-left: 40px;
  opacity: 0;
  transition: 0.6s;
}
#imgTap:checked ~ .inner-part .content {
  display: none;
}
#tap-1:checked ~ .inner-part .content-1 {
  opacity: 1;
  margin-left: 20px;
  z-index: 100;
  transition-delay: 0.3s;
}
.content span {
  display: block;
  color: #1976d2;
  margin-bottom: 10px;
  font-size: 22px;
  font-weight: 900;
}
.content .title {
  font-size: 30px;
  font-weight: 700;
  color: #0d0925;
  margin-bottom: 10px;
}
.content .text {
  color: #4e4a67;
  font-size: 15px;
  margin-bottom: 10px;
  line-height: 1.2em;
  text-align: justify;
  overflow: hidden;
}
.content .button {
  display: inline-flex;
  padding: 35px 40px;
  font-size: 16px;
  text-transform: uppercase;
  color: #fff0e6;
  font-weight: 600;
  letter-spacing: 1px;
  border-radius: 50px;
  cursor: pointer;
  outline: none;
  border: 1px solid #1976d2;
  background: #1976d2;
}
.content .button:hover {
  background: #1976d2;
}

input[type='radio'],
input[type='checkbox'] {
  display: none;
}
</style>
