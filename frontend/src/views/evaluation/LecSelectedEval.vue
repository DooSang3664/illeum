<template>
  <div class="seletedManage">
    <div
      style="display:flex; flex-direction:column; align-items:center; margin-left:-40%; 
    "
    >
      <h2>myPage에서 넘긴 수강데이터</h2>
      <h4>{{ this.$store.state.name }}의 학생관리</h4>
      <h4>넘겨준 RID:{{ this.$route.query.roomData.rid }}</h4>
      <h4>클래스명:{{ this.$route.query.roomData.room_name }}의 평가데이터</h4>
    </div>
    <v-row>
      <v-date-picker v-model="date" @click:date="showManage" :landscape="landscape" locale="ko-kr" :allowed-dates="allowedDates" class="mt-4" min="1900-04-01" max="2100-10-30"></v-date-picker>
    </v-row>

    <v-row>
      <div>
        <h2>{{ date }}</h2>
      </div>
    </v-row>
    <!-- <v-row>
      <v-col cols="12" sm="6">
        <v-select :items="items" :label="date" solo @change="showManage"></v-select>
      </v-col>
    </v-row> -->
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
                  <th scope="col">TOP</th>
                </tr>
              </thead>

              <tbody>
                <tr v-for="(each, idx) in UsersEval" :key="idx">
                  <td>{{ idx + 1 }}</td>
                  <td><v-img :src="'https://k4d106.p.ssafy.io/profile/' + each.uid + '/256'" id="preview" style="width:50px; height:50px; " alt=""></v-img></td>
                  <td>
                    {{ each.name }}
                  </td>
                  <td>
                    {{ each.email }}
                  </td>
                  <td>
                    <v-btn color="primary" dark>
                      DETAIL
                    </v-btn>
                  </td>
                  <td>
                    {{ each.attend }}
                  </td>
                  <template v-if="each.ranking == 1">
                    <td>
                      <h4>채팅참여도1등</h4>
                    </td>
                  </template>
                  <template v-else>
                    <td>
                      <h4></h4>
                    </td>
                  </template>
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
      <v-col cols="12" sm="4"></v-col>
    </v-row>
  </div>
</template>

<script>
import { fetchCondition } from '@/api/evaluation.js';
export default {
  data() {
    return {
      selectedeval: [],
      Dates: [],
      date: '',
      roomDTO: null,
      UsersEval: [],
      url: null,
      landscape: true,
      chipCheck: {
        isLate: false,
        isAbsent: false,
        isAttendFirst: false,
        isChatFirst: false,
      },
      search: '',
      selected: [],
      loading: false,
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
  async created() {
    this.roomDTO = this.$route.query.roomData;
    const rid = this.$route.query.roomData.rid;
    const roomData = {
      isAbsent: 0,
      isAttendFist: 0,
      isChatFirst: 0,
      isLate: 0,
      rid,
    };
    console.log(this.roomDTO);
    const { data } = await fetchCondition(roomData);
    if (data) {
      this.selectedeval = data;
      for (var i = 0; i < data.length; i++) {
        var dates = data[i].eval_date.slice(0, 10);
        this.Dates.push(dates);
      }
    }
    console.log('selectedeval', this.selectedeval);
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
  },
  methods: {
    allowedDates(val) {
      for (var i = 0; i < this.Dates.length; i++) {
        if (this.Dates[i] == val) {
          return true;
        }
      }
    },
    async showManage() {
      console.log('selected', this.date);
      this.manageClasscheck = true;
      this.manageUsers = [];
      this.UsersEval = [];
      for (var i = 0; i < this.selectedeval.length; i++) {
        if (this.date === this.selectedeval[i].eval_date.slice(0, 10)) {
          this.url = 'https://k4d106.p.ssafy.io/profile/' + this.selectedeval.uid + '/256';
          this.UsersEval.push(this.selectedeval[i]);
        }
      }
      console.log(this.UsersEval);
    },
  },
};
</script>

<style scoped>
.seletedManage {
  width: 100%;
  margin-left: 30%;
  padding: 25px 30px 30px 30px;
  border-radius: 5px;
  background: #fff;
}
/* table */

* {
  box-sizing: border-box;
}

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
