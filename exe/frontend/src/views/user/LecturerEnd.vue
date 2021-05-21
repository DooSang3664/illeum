<template>
  <div class="lec-ready overflow-y-auto" style="height:43rem">
    <div v-if="learnerEnd == true">
      <!-- card -->
      <v-row no-gutters>
        <template v-for="(item, idx) in classLiFilter">
          <v-col :key="idx" xl="6" lg="12">
            <v-card flat color="#f9f9f9" width="700">
              <div class="blog-card">
                <div class="inner-part">
                  <label for="imgTap" class="img">
                    <v-img :src="url" id="preview" alt=""></v-img>
                  </label>
                  <div class="content">
                    <div class="class-name">{{ item.room_name }}</div>
                    <div class="title">
                      <v-icon>mdi-account-circle</v-icon>
                      {{ item.name }}
                    </div>
                    <div class="text">
                      <v-icon>mdi-clock-start</v-icon>
                      <span class="detail-label">
                        진행여부
                      </span>
                      <span>{{ item.room_state }}</span>
                    </div>
                    <div class="text">
                      <v-icon>mdi-lock-open-outline</v-icon>
                      <span class="detail-label">
                        공개여부
                      </span>
                      <span>
                        {{ item.room_type }}
                      </span>
                    </div>
                    <div class="text">
                      <v-icon>mdi-arrow-right-bold-hexagon-outline</v-icon>
                      <span class="detail-label">
                        강의시작
                      </span>
                      {{ item.start_time }}
                    </div>
                    <div class="text">
                      <v-icon>mdi-exit-to-app</v-icon>
                      <span class="detail-label">
                        강의종료
                      </span>
                      <span>
                        {{ item.end_time }}
                      </span>
                    </div>
                    <v-btn class="button" large color="#FF625C" depressed @click="learnerDataGo(item.rid)">학생평가보기</v-btn>
                  </div>
                </div>
              </div>
            </v-card>
          </v-col>
          <v-responsive v-if="idx % 2 == 1" :key="`width-${idx}`" width="100%"></v-responsive>
        </template>
      </v-row>
    </div>
    <div v-else>
      <p>강의종료된 강좌가 없음</p>
    </div>
  </div>
</template>

<script>
import { userClasslist } from '@/api/auth.js';
export default {
  data() {
    return {
      learnerEnd: false,
      classLi: [],
      url: null,
    };
  },
  computed: {
    classLiFilter: function() {
      return this.classLi.filter((i) => i.room_state === '완료');
    },
  },
  async created() {
    this.url = '/profile/' + this.$store.state.uuid + '/256';
    const { data } = await userClasslist(this.$store.state.uuid);
    if (data) {
      this.learnerEnd = true;
      this.classLi = data;
    }
  },
  methods: {
    learnerDataGo(value) {
      this.$router.push({ name: 'LecSelectedEval', query: { rid: value } });
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
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.blog-card {
  display: grid;
  height: 280px;
  width: 100%;
  max-width: 650px;
  margin: 30px auto;
  border-radius: 15px;
  background: white;
  box-shadow: 0px 5px 10px rgba(0, 0, 0, 0.5);
}
.inner-part {
  display: flex;
  height: 280px;
  align-items: center;
  /* justify-content: flex-end; */
  padding: 20px;
}

.inner-part .img {
  height: 240px;
  width: 240px;
  flex-shrink: 0;
  overflow: hidden;
  border-radius: 20px;
  box-shadow: 2px 3px 15px rgba(0, 0, 0, 0.3);
}

.content {
  display: flex;
  flex-direction: column;
  /* align-items: center; */
  padding: 30px;
  width: 380px;
  margin-top: 4rem;
  /* margin-left: 40px; */

  transition: 0.6s;
}

.content .class-name {
  position: absolute !important;
  font-family: 'GongGothicLight';
  color: #2e79ff;
  font-size: 2rem;
  /* right: 50px; */
  top: 20px;
  text-align: left;
  font-weight: bolder;
}
.content .title {
  position: absolute !important;
  font-size: 30px;
  font-weight: 700;
  color: #0d0925;
  right: 60px;
  top: 60px;
  text-align: right;
}
.content .text {
  color: #4e4a67;
  margin: 2px 5px;
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
  margin-top: 10px;
  border: 1px solid #1976d2;
  background: #1976d2;
}
.content .button:hover {
  background: #1976d2;
}
.content .text .detail-label {
  font-weight: bold;
  font-size: 1rem;

  border-radius: 50px;

  padding: 4px 10px;
}
input[type='radio'],
input[type='checkbox'] {
  display: none;
}
</style>
