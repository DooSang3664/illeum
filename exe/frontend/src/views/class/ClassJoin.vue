<template>
  <div class="classJoin">
    <v-alert class="text-start font-weight-black" border="left" dark color="#2E95FF" style="font-size: 1.5rem; letter-spacing: 2.3px; padding-left:20px; line-height: 45px;" elevation="3" height="70">
      <span>
        <v-icon>mdi-account-check</v-icon>
        수강신청 리스트
      </span>
    </v-alert>
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
        { text: '프로필', value: 'founder_uid' },
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

        this.$swal({
          icon: 'warning',
          showCancelButton: true,
          title: '해당 수업을 신청을 하겠습니까?',
          cancelButtonColor: '#d33',
          confirmButtonText: '확인',
          cancelButtonText: '취소',
        }).then(async (result) => {
          if (result.isConfirmed) {
            await insertRoom(insertInfo);
          }
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

.classJoin {
  margin: 3% 2%;
  font-family: 'GongGothicLight';
}
</style>
