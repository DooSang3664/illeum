<template>
  <div class="manage">
    <v-row>
      <v-container>{{ this.$store.state.uuid }}의 학생관리</v-container>
      <v-date-picker v-model="date" @click:date="classNameFetch" :landscape="true" locale="ko-kr" :allowed-dates="allowedDates" class="mt-4" min="1900-04-01" max="2100-10-30"></v-date-picker>
      <!-- <v-date-picker v-model="date" :landscape="true" locale="ko-kr" :allowed-dates="allowedDates" class="mt-4" min="1900-04-01" max="2100-10-30"></v-date-picker> -->
    </v-row>
    <v-row>
      <div style="margin-top: 100px;">
        <h2>show Management</h2>
        <h2>{{ date }}</h2>
      </div>
    </v-row>
    <v-row>
      <v-col cols="12" sm="6">
        <v-select :items="roomNameList" :value="roomNameList[0]" solo @input="showAll"></v-select>
        <!-- <v-select :items="roomNameList" :label="date" solo></v-select> -->
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12" sm="4">
        <div class="table-body">
          <div class="table_responsive">
            <table>
              <thead>
                <tr>
                  <th scope="col">No</th>
                  <th scope="col">PROFILE</th>
                  <th scope="col">NAME</th>
                  <th scope="col">E-MAIL</th>
                  <th scope="col">Evaluation</th>
                  <th scope="col">Attendance</th>
                  <th scope="col">date</th>
                  <th scope="col">attend</th>
                  <th scope="col">ranking</th>
                  <th scope="col">participation</th>
                  <th scope="col">모달</th>
                </tr>
              </thead>

              <tbody>
                <tr v-for="(each, idx) in userEval" :key="idx">
                  <td>{{ idx + 1 }}</td>
                  <td><v-img :src="`https://k4d106.p.ssafy.io/profile/${each.uid}/256`" id="preview" style="width:50px; height:50px;" alt=""></v-img></td>
                  <td>{{ each.name }}</td>
                  <td>{{ each.email }}</td>
                  <td>Evaluation</td>
                  <td>Attendance</td>
                  <td>{{ each.attend_time }}</td>
                  <td>{{ each.attend }}</td>
                  <td>{{ each.ranking | checkChatRanking }}</td>
                  <td>{{ each.participation }}회 채팅</td>

                  <td>
                    <v-dialog v-model="dialog" persistent max-width="1200px">
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn color="primary" dark v-bind="attrs" v-on="on">
                          DETAIL
                        </v-btn>
                      </template>
                      <v-card>
                        <v-card-title>
                          <span class="headline">
                            <span>{{ each.name }}님의</span>
                            Evaluation
                          </span>
                        </v-card-title>
                        <v-card-text>
                          <v-container>
                            <v-card>
                              <v-toolbar flat color="primary" dark>
                                <v-toolbar-title>
                                  <span>{{ each.name }}님의</span>
                                  Evaluation
                                </v-toolbar-title>
                              </v-toolbar>
                              <v-tabs vertical>
                                <v-tab>
                                  <v-icon left>
                                    mdi-account
                                  </v-icon>
                                  출결
                                </v-tab>
                                <v-tab>
                                  <v-icon left>
                                    mdi-lock
                                  </v-icon>
                                  학습태도
                                </v-tab>
                                <v-tab>
                                  <v-icon left>
                                    mdi-access-point
                                  </v-icon>
                                  참여현황
                                </v-tab>

                                <v-tab-item>
                                  <v-card flat>
                                    <h2>출결</h2>
                                    <h3>출석시간:{{ each.attend_time }}</h3>
                                    <h3>지각여부:{{ each.attend }}</h3>
                                  </v-card>
                                </v-tab-item>
                                <v-tab-item>
                                  <v-card flat>
                                    <LecUserEval :each="each" :rid="ridSelected"></LecUserEval>
                                  </v-card>
                                </v-tab-item>
                                <v-tab-item>
                                  <v-card flat>
                                    <LecUserPartin :each="each" :roomData="roomData" :evalUserCnt="userEvalLength" :rid="ridSelected"></LecUserPartin>
                                  </v-card>
                                </v-tab-item>
                              </v-tabs>
                            </v-card>
                          </v-container>
                        </v-card-text>
                        <v-card-actions>
                          <v-spacer></v-spacer>
                          <v-btn color="blue darken-1" text @click="dialog = false">
                            Close
                          </v-btn>
                        </v-card-actions>
                      </v-card>
                    </v-dialog>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </v-col>
      <!-- 필터검색 -->
      <v-col cols="12" sm="4" class="chip-search">
        <v-card class="mx-auto" max-width="300">
          <v-toolbar flat color="transparent">
            <v-toolbar-title>빠른검색</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-btn icon @click="$refs.search.focus()">
              <v-icon>mdi-magnify</v-icon>
            </v-btn>
          </v-toolbar>

          <v-container class="py-0">
            <v-row align="center" justify="start">
              <v-col v-for="(selection, i) in selections" :key="selection.text" class="shrink">
                <!-- <v-chip :disabled="loading" close @click:close="selected.splice(i, 1)"> -->
                <v-chip :disabled="loading" close @click:close="chipClose(selection, i)">
                  <v-icon left v-text="selection.icon"></v-icon>
                  {{ selection.text }}
                </v-chip>
              </v-col>
            </v-row>
          </v-container>

          <v-divider v-if="!allSelected"></v-divider>

          <v-list>
            <template v-for="item in categories">
              <v-list-item v-if="!selected.includes(item)" :key="item.text" :disabled="loading" @click="showSelected(item)">
                <v-list-item-avatar>
                  <v-icon :disabled="loading" v-text="item.icon"></v-icon>
                </v-list-item-avatar>
                <v-list-item-title v-text="item.text"></v-list-item-title>
              </v-list-item>
            </template>
          </v-list>

          <v-divider></v-divider>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import LecUserEval from '@/views/evaluation/LecUserEval';
import LecUserPartin from '@/views/evaluation/LecUserPartin';

import { fetchCondition } from '@/api/evaluation';
import { findByUidClass } from '@/api/class';
export default {
  components: {
    LecUserEval,
    LecUserPartin,
  },
  data() {
    return {
      chipCheck: {
        isLate: false,
        isAbsent: false,
        isAttendFirst: false,
        isChatFirst: false,
      },
      date: '',

      dateList: [], // rid date pair list
      classList: [], // for rid list
      roomNameList: [], // for select box list
      userEval: [], // for table list
      userEvalLength: '', // for table list length

      userEvalAll: [],

      ridSelected: '',
      dialog: false,
      roomData: {},
      loading: false,
      selected: [],
      search: '',
      chips: [
        {
          text: '지각',
          icon: 'mdi-nature',
        },
        {
          text: '결석',
          icon: 'mdi-glass-wine',
        },
        {
          text: '출석1등',
          icon: 'mdi-calendar-range',
        },
        {
          text: '채팅참여1등',
          icon: 'mdi-bike',
        },
      ],
    };
  },
  computed: {
    allSelected() {
      return this.selected.length === this.chips.length;
    },
    categories() {
      const search = this.search.toLowerCase();

      if (!search) return this.chips;

      return this.chips.filter((item) => {
        const text = item.text.toLowerCase();

        return text.indexOf(search) > -1;
      });
    },
    selections() {
      const selections = [];

      for (const selection of this.selected) {
        selections.push(selection);
      }

      return selections;
    },
  },
  watch: {
    selected() {
      this.search = '';
    },
    userEval(val) {
      this.userEval = val;
    },
  },
  filters: {
    checkChatRanking: function(value) {
      if (value == 1000) return '채팅미참여';
      return value + '등';
    },
  },

  async created() {
    const token = this.$store.state.token;
    const uuid = this.$store.state.uuid;
    await findByUidClass(token, uuid).then(({ data }) => {
      for (let index = 0; index < data.length; index++) {
        this.classList.push(data[index]);
      }
    });
    for (let i = 0; i < this.classList.length; i++) {
      await fetchCondition({ rid: this.classList[i].rid }).then(({ data }) => {
        this.userEvalAll.push(data);
        for (let j = 0; j < data.length; j++) {
          if (!this.dateList.some((e) => e.date == data[j].attend_time.slice(0, 10))) {
            this.dateList.push({ date: data[j].eval_date.slice(0, 10), classInfo: this.classList[i], data });
          }
        }
      });
    }
  },
  methods: {
    allowedDates(val) {
      for (var i = 0; i < this.dateList.length; i++) {
        if (this.dateList[i].date == val) {
          return true;
        }
      }
    },

    classNameFetch() {
      this.roomNameList = [];
      this.userEval = [];
      this.userEvalLength = '';
      this.ridSelected = '';
      this.selected = [];

      for (let index = 0; index < this.dateList.length; index++) {
        console.log(this.dateList[index]);
        if (this.dateList[index].date == this.date) {
          this.roomNameList.push(this.dateList[index].classInfo.roomName);
          this.ridSelected = this.dateList[index].classInfo.rid;
        }
      }
    },

    showAll(roomName) {
      this.userEval = [];
      this.userEvalLength = '';
      this.roomData.room_name = roomName;
      for (let i = 0; i < this.dateList.length; i++) {
        console.log(this.dateList[i]);
        if (this.dateList[i].classInfo.roomName == roomName) {
          if (this.dateList[i].date == this.date) {
            for (let j = 0; j < this.dateList[i].data.length; j++) {
              console.log(this.dateList[i].data[j]);
              if (this.dateList[i].data[j].attend_time.slice(0, 10) == this.date) {
                this.userEval.push(this.dateList[i].data[j]);
              }
            }
          }
        }
      }
      this.userEvalLength = this.userEval.length;
    },
    async getList(chipCheck) {
      this.userEval = [];
      this.userEvalLength = '';
      var isAbsent = chipCheck.isAbsent ? 1 : 0;
      var isLate = chipCheck.isLate ? 1 : 0;
      var isAttendFirst = chipCheck.isAttendFirst ? 1 : 0;
      var isChatFirst = chipCheck.isChatFirst ? 1 : 0;
      await fetchCondition({ rid: this.ridSelected, isAbsent, isLate, isAttendFirst, isChatFirst }).then(({ data }) => {
        for (let i = 0; i < data.length; i++) {
          if (this.date == data[i].attend_time.slice(0, 10)) {
            console.log(data[i]);
            this.userEval.push(data[i]);
          }
        }
      });
    },

    next() {
      this.loading = true;

      setTimeout(() => {
        this.search = '';
        this.selected = [];
        this.loading = false;
      }, 1000);
    },
    chipClose(selection, i) {
      this.selected.splice(i, 1);
      if (selection.text == '지각') this.chipCheck.isLate = false;
      if (selection.text == '결석') this.chipCheck.isAbsent = false;
      if (selection.text == '출석1등') this.chipCheck.isAttendFirst = false;
      if (selection.text == '채팅참여1등') this.chipCheck.isChatFirst = false;
      this.getList(this.chipCheck);
    },
    showSelected(item) {
      this.selected.push(item);

      if (item.text == '지각') this.chipCheck.isLate = true;
      if (item.text == '결석') this.chipCheck.isAbsent = true;
      if (item.text == '출석1등') this.chipCheck.isAttendFirst = true;
      if (item.text == '채팅참여1등') this.chipCheck.isChatFirst = true;

      this.getList(this.chipCheck);
      this.$emit('selected', this.selected);
    },
  },
};
</script>
<style scoped>
.manage {
  margin: auto;
  margin-left: 30%;
  /* width: 100%;
  padding: 25px 30px 30px 30px;
  border-radius: 5px;
  background: #fff; */
}
/* table */

/* * {
  box-sizing: border-box;
} */

.table_responsive {
  width: 150%;
  padding: 15px;
  overflow: auto;
  margin: auto;
  border-radius: 4px;
}

table {
  width: 100%;
  font-size: 13px;
  color: #444;
  white-space: nowrap;
  border-collapse: collapse;
}

table > thead {
  background-color: #1976d2;
  color: #fff;
}

table > thead th {
  padding: 15px;
}

table th,
table td {
  border: 1px solid #00000017;
  padding: 10px 15px;
}

table > tbody > tr > td > img {
  display: inline-block;
  width: 60px;
  height: 60px;
  object-fit: cover;
  border-radius: 50%;
  border: 4px solid #fff;
  box-shadow: 0 2px 6px #0003;
}

.action_btn {
  display: flex;
  justify-content: center;
  gap: 10px;
}

.action_btn > a {
  text-decoration: none;
  color: #444;
  background: #fff;
  border: 1px solid;
  display: inline-block;
  padding: 7px 20px;
  font-weight: bold;
  border-radius: 3px;
  transition: 0.3s ease-in-out;
}

.action_btn > a:nth-child(1) {
  border-color: #26a69a;
}

.action_btn > a:nth-child(2) {
  border-color: orange;
}

.action_btn > a:hover {
  box-shadow: 0 3px 8px #0003;
}

table > tbody > tr {
  background-color: #fff;
  transition: 0.3s ease-in-out;
}

table > tbody > tr:nth-child(even) {
  background-color: rgb(238, 238, 238);
}

table > tbody > tr:hover {
  filter: drop-shadow(0px 2px 6px #0002);
}
.chip-search {
  margin-left: 10em;
}
</style>
