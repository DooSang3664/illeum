<template>
  <div>
    <h2>클래스 리스트 목록</h2>
    <v-data-table
      :headers="headers"
      :items="rooms"
      :items-per-page="10"
      item-key="rid"
      class="elevation-1"
      @click:row="detailClass"
      :footer-props="{
        showFirstLastPage: true,
        firstIcon: 'mdi-arrow-collapse-left',
        lastIcon: 'mdi-arrow-collapse-right',
        prevIcon: 'mdi-minus',
        nextIcon: 'mdi-plus',
        'items-per-page-text': '페이지당 클래스수',
      }"
    ></v-data-table>
    <v-btn @click="main">
      홈으로
    </v-btn>
  </div>
</template>
<script>
import { classAll, insertRoom, updateClass } from '@/api/class';
export default {
  data() {
    return {
      headers: [
        {
          text: '방제목',
          align: 'start',
          value: 'room_name',
        },
        { text: '시작시간', value: 'start_time' },
        { text: '방상태', value: 'room_state' },
        { text: '공개/비공개', value: 'room_type' },
      ],
      rooms: [],
    };
  },
  async created() {
    const { data } = await classAll();
    for (let index = 0; index < data.length; index++) {
      if (data[index].room_state != '완료') {
        this.rooms.push(data[index]);
      }
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
    main() {
      this.$router.push({ name: 'Home' });
    },
    async detailClass(value) {
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
            if (value.uid == this.$store.state.uuid) {
              try {
                const { data } = await updateClass({ rid: value.rid, room_state: '진행' });
                if (data == 'success') this.$router.push({ name: 'ClassMaster', query: { room_name: value.room_name, rid: value.rid } });
              } catch {
                this.$swal({
                  icon: 'error',
                  title: '클래스 참여 오류.!!',
                });
              }
            } else {
              this.$router.push({ name: 'Class', query: { room_name: value.room_name, rid: value.rid } });
            }
          }
        }
      } else {
        if (value.uid == this.$store.state.uuid) {
          try {
            const { data } = await updateClass({ rid: value.rid, room_state: '진행' });
            if (data == 'success') this.$router.push({ name: 'ClassMaster', query: { room_name: value.room_name, rid: value.rid } });
          } catch {
            this.$swal({
              icon: 'error',
              title: '클래스 참여 오류.!!',
            });
          }
        } else {
          this.$router.push({ name: 'Class', query: { room_name: value.room_name, rid: value.rid } });
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
