<template>
  <div>
    <div>
      <div>{{ each.name }}님</div>
      <div>{{ roomData.room_name }} 수업에서 수업참여도</div>
      <div>총 수강생 {{ evalUserCnt }}명중에 {{ partuidRank }}위입니다</div>
      <div>{{ roomData.room_name }} 수업에서 출석을</div>
      <div>총 수강생 {{ evalUserCnt }}명중에 {{ attenduidRank }}위입니다</div>
    </div>
  </div>
</template>

<script>
import { evaluateList } from '@/api/class';

export default {
  data() {
    return {
      uuid: this.$store.state.uuid,
      name: '',
      eval: [],
      items: [],
      landscape: true,
      date: '',
      menu: false,

      partinRank: [],
      partuidRank: '',
      attendRank: [],
      attenduidRank: '',
      roomName: [],
      partData: [],
      attendData: [],
      selectedRoomName: '',
      maxUser: '',
      firstUser: '',
      fetchRoomlen: 0,
      evalcheck: false,
    };
  },
  props: {
    each: {
      type: Object,
    },
    roomData: {
      type: Object,
    },
    evalUserCnt: {
      type: Number,
    },
    rid: {
      type: Number,
    },
  },
  async created() {
    console.log(this.evalUserCnt);
    console.log(this.roomData);

    this.evalcheck = true;
    this.partinRank = [];
    this.attendRank = [];
    this.fetchRoomlen = 0;
    const res = await evaluateList(this.rid);

    var maxPartin = 0;
    var first = 100000;
    //채팅참여도1등, 출석1등 구하기
    console.log(res.data);
    for (var j = 0; j < res.data.length; j++) {
      this.partinRank.push({ uid: res.data[j].uid, participation: res.data[j].participation });
      // attendRank push 메소드 시간순으로 정렬됨
      this.attendRank.push({ uid: res.data[j].uid, attend_time: res.data[j].attend_time });
      console.log(this.attendRank);
      this.attendRank.sort(function(a, b) {
        return a.attend_time < b.attend_time ? -1 : a.attend_time > b.attend_time ? 1 : 0;
      });
      if (maxPartin < res.data[j].participation) {
        var maxPartin = res.data[j].participation;
        this.maxUser = res.data[j].name;
      }

      if (first > res.data[j].ranking) {
        var first = res.data[j].ranking;
        this.firstUser = res.data[j].name;
      }
      if (this.each.uid === res.data[j].uid) {
        this.name = this.$store.state.name;
        this.fetchRoomlen = res.data.length;
      }
    }
    //채팅참여도 배열 제일 많은순으로 정렬하기
    this.partinRank.sort(function(a, b) {
      if (a.participation < b.participation) {
        return 1;
      }
      if (a.participation > b.participation) {
        return -1;
      }

      return 0;
    });

    //로그인한 청강자 채팅참여도 순위 구하기
    for (var k = 0; k < this.partinRank.length; k++) {
      if (this.partinRank[k].uid === this.each.uid) {
        this.partuidRank = this.partinRank.indexOf(this.partinRank[k]) + 1;
      }
    }
    //로그인한 청강자 출석시간 순위 구하기
    console.log(this.attendRank);
    for (var m = 0; m < this.attendRank.length; m++) {
      if (this.attendRank[m].uid === this.each.uid) {
        this.attenduidRank = this.attendRank.indexOf(this.attendRank[m]) + 1;
      }
    }
  },
};
</script>

<style></style>
