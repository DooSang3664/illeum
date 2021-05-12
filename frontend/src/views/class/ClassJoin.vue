<template>
  <div class="classJoin">
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
        { text: '강의자', value: 'founder' },
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
      if (data[index].room_state == '준비') {
        this.rooms.push(data[index]);
      }
    }
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
            var uid = this.$store.state.uuid;
            var rid = value.rid;
            var insertInfo = { uid: uid, rid: rid };
            try {
              const { data } = await insertRoom(insertInfo);
              if (data != null) {
                this.$swal({
                  icon: 'success',
                  title: '클래스 참여 완료.!!',
                  confirmButtonText: `확인`,
                });
              }
            } catch {
              this.$swal({
                icon: 'error',
                title: '클래스 참여 오류.!!',
              });
            }
          }
        }
      } else {
        var uid = this.$store.state.uuid;
        var rid = value.rid;
        var insertInfo = { uid: uid, rid: rid };
        try {
          const { data } = await insertRoom(insertInfo);
          if (data != null) {
            this.$swal({
              icon: 'success',
              title: '클래스 참여 완료.!!',
              confirmButtonText: `확인`,
            });
          }
        } catch {
          this.$swal({
            icon: 'error',
            title: '클래스 참여 오류.!!',
          });
        }
      }
    },
  },
};
</script>
<style scoped>
.classJoin {
  margin: auto;
}
</style>
