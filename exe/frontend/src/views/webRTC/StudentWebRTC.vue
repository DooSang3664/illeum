<template>
  <div style="background-color:white; height: auto">
    <div class="class-name">
      <v-row class="mb-6" no-gutters>
        <v-col class="text-left pl-10"><img src="../../assets/img/textlogo_2.png" alt="" style="height:36px; " /></v-col>
        <v-col :cols="6">
          <h2>{{ this.$route.query.room_name }}</h2>
        </v-col>
        <v-col></v-col>
      </v-row>
    </div>
    <div class="drag-container">
      <div class="panel-one" id="drag-left">
        <div class="hoverEffect"></div>
        <div class="videos-container"></div>
        <div class="share-videos-container"></div>
      </div>
      <div class="dragbar" id="dragbar"></div>
      <div class="panel-two" id="drag-right">
        <v-tabs v-model="tab" background-color="#2E95FF" hide-slider centered dark icons-and-text>
          <v-tabs-slider></v-tabs-slider>

          <v-tab href="#tab-1">
            채팅
            <v-icon>mdi-chat-outline</v-icon>
          </v-tab>

          <v-tab href="#tab-2">
            참여자
            <v-icon>mdi-account-multiple</v-icon>
          </v-tab>
        </v-tabs>

        <v-tabs-items v-model="tab">
          <v-tab-item :value="'tab-1'">
            <div class="chat-area">
              <div id="conversation-panel"></div>
            </div>
            <div class="wrapper">
              <v-textarea id="txt-chat-message" class="regular-input" rows="1" no-resize outlined style="border-color: white;" v-model="message" @keyup.enter="chat" label="채팅 입력"></v-textarea>

              <emoji-picker @emoji="append" :search="search">
                <div class="emoji-invoker" slot="emoji-invoker" slot-scope="{ events: { click: clickEvent } }" @click.stop="clickEvent">
                  <svg height="24" viewBox="0 0 24 24" width="24" xmlns="http://www.w3.org/2000/svg">
                    <path d="M0 0h24v24H0z" fill="none" />
                    <path
                      d="M11.99 2C6.47 2 2 6.48 2 12s4.47 10 9.99 10C17.52 22 22 17.52 22 12S17.52 2 11.99 2zM12 20c-4.42 0-8-3.58-8-8s3.58-8 8-8 8 3.58 8 8-3.58 8-8 8zm3.5-9c.83 0 1.5-.67 1.5-1.5S16.33 8 15.5 8 14 8.67 14 9.5s.67 1.5 1.5 1.5zm-7 0c.83 0 1.5-.67 1.5-1.5S9.33 8 8.5 8 7 8.67 7 9.5 7.67 11 8.5 11zm3.5 6.5c2.33 0 4.31-1.46 5.11-3.5H6.89c.8 2.04 2.78 3.5 5.11 3.5z"
                    />
                  </svg>
                </div>
                <div slot="emoji-picker" slot-scope="{ emojis, insert, display }">
                  <div class="emoji-picker" :style="{ top: display.y + 'px', left: display.x + 'px' }">
                    <div class="emoji-picker__search">
                      <input type="text" v-model="search" v-focus />
                    </div>
                    <div>
                      <div v-for="(emojiGroup, category) in emojis" :key="category">
                        <h5>{{ category }}</h5>
                        <div class="emojis">
                          <span v-for="(emoji, emojiName) in emojiGroup" :key="emojiName" @click="insert(emoji)" :title="emojiName">{{ emoji }}</span>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </emoji-picker>
              <v-btn id="btn-chat-message" height="3.5rem" large color="primary" @click="chat">입력</v-btn>
            </div>
          </v-tab-item>
          <v-tab-item :value="'tab-2'">
            <div id="onUserStatusChanged">
              <v-list class="name-list">
                <v-list-item v-for="(name, idx) in names" :key="idx">
                  <v-list-item-content class="name-list-item">
                    <v-list-item-title class="name-list-item-content" v-text="name.name"></v-list-item-title>
                  </v-list-item-content>
                  <v-avatar class="ma-3" size="50" tile>
                    <v-img :src="name.avatar"></v-img>
                  </v-avatar>
                </v-list-item>
              </v-list>
            </div>
          </v-tab-item>
        </v-tabs-items>
      </div>
    </div>
    <div class="panel-three">
      <template v-if="isAudio">
        <v-btn class="mr-4 btn" color="#FF625C" @click="offAudio">
          <v-icon left>
            mdi-volume-off
          </v-icon>
          오디오 끄기
        </v-btn>
      </template>
      <template v-else>
        <v-btn class="mr-4 btn" color="#41EA93" @click="onAudio">
          <v-icon left>
            mdi-volume-high
          </v-icon>
          오디오 켜기
        </v-btn>
      </template>
      <template v-if="isVideo">
        <v-btn class="mr-4 btn" color="#FF625C" @click="offVideo">
          <v-icon left>
            mdi-monitor-off
          </v-icon>
          비디오 끄기
        </v-btn>
      </template>
      <template v-else>
        <v-btn class="mr-4 btn" color="#41EA93" @click="onVideo">
          <v-icon left>
            mdi-monitor
          </v-icon>
          비디오 켜기
        </v-btn>
      </template>

      <v-btn class="mr-4 btn" color="#2E95FF" @click="screen">
        <v-icon left>
          mdi-monitor-multiple
        </v-icon>
        화면공유
      </v-btn>
      <v-btn class="mr-4 btn" color="#2E95FF" @click="openWhiteBoard">
        <v-icon left>
          mdi-billboard
        </v-icon>
        화이트보드
      </v-btn>
      <v-btn class="mr-4 btn" color="#2E95FF" @click="saveMessageLog">
        <v-icon left>
          mdi-chat-plus
        </v-icon>
        채팅기록저장
      </v-btn>
      <v-btn class="mr-4 btn" color="#FF625C" @click="outRoom">
        <v-icon left>
          mdi-exit-to-app
        </v-icon>
        종료
      </v-btn>
    </div>

    <!-- 화이트보드 모달 영역 -->
    <div id="modal">
      <div class="modal_content ">
        <h3>화이트보드</h3>
        <div id="widget-container"></div>
        <v-row no-gutters>
          <v-col></v-col>
        </v-row>
        <div class="modal-btn">
          <v-btn class="" color="error" @click="clearCanvas">
            <v-icon left>
              mdi-eraser
            </v-icon>
            전체삭제
          </v-btn>
          <v-btn class="ml-5" color="#41EA93" @click="closeWhiteBoard">
            <v-icon left>
              mdi-close-box-multiple-outline
            </v-icon>
            닫기
          </v-btn>
        </div>
      </div>
    </div>
    <div class="modal_layer"></div>
  </div>
</template>

<script>
import push from 'push.js';
import { faceAI } from '@/api/faceAI';
import { findUidAndRid } from '@/api/entrant';
import { exit, entrance } from '@/api/rabbit';
import { insertEvaluation, updateByVid } from '@/api/evaluation';
import { notification } from '@/api/alert';

export default {
  data() {
    return {
      roomid: '',
      userName: '',
      connection: null,
      message: '',
      chatLog: '',
      chatResult: [],
      rid: '',
      vid: '',
      isOutClicked: true,
      isAudio: true,
      isVideo: true,
      isLive: false, // for AI server interval vue watch value
      time: null, // for AI server interval
      search: '',
      names: [], // name list
      tab: null,
      videoId: [],
    };
  },
  created() {
    this.roomid = this.$route.query.room_name;
    this.rid = this.$route.query.rid;
    this.userName = this.$store.state.name;
  },
  async mounted() {
    var ref = this;
    notification(this.$store.state.uuid, (msg) => {
      ref.$toast(msg, {
        position: 'bottom-right',
        timeout: 4990,
        closeOnClick: true,
        pauseOnFocusLoss: true,
        pauseOnHover: true,
        draggable: true,
        draggablePercent: 0.3,
        showCloseButtonOnHover: false,
        hideProgressBar: true,
        closeButton: 'button',
        icon: true,
        rtl: false,
      });
    });
    await this.$loadScript('https://cdn.jsdelivr.net/npm/rtcmulticonnection@latest/dist/RTCMultiConnection.min.js')
      .then(() => {})
      .catch(() => {});
    await this.$loadScript('https://rtcmulticonnection.herokuapp.com/socket.io/socket.io.js')
      .then(() => {})
      .catch(() => {});

    var left = document.getElementById('drag-left');
    var bar = document.getElementById('dragbar');

    const drag = (e) => {
      document.selection ? document.selection.empty() : window.getSelection().removeAllRanges();
      left.style.width = e.pageX - bar.offsetWidth / 2 + 'px';
    };

    bar.addEventListener('mousedown', () => {
      document.addEventListener('mousemove', drag);
    });

    bar.addEventListener('mouseup', () => {
      document.removeEventListener('mousemove', drag);
    });
    ref = this;
    // canvas
    this.$nextTick(function() {
      this.designer = new window.CanvasDesigner();
      this.designer.widgetHtmlURL = 'https://www.webrtc-experiment.com/Canvas-Designer/widget.html'; // you can place this file anywhere
      this.designer.widgetJsURL = 'https://www.webrtc-experiment.com/Canvas-Designer/widget.js';

      this.designer.setSelected('pencil');

      this.designer.setTools({
        pencil: true,
        text: true,
        image: true,
        pdf: false,
        eraser: true,
        line: true,
        arrow: true,
        dragSingle: true,
        dragMultiple: true,
        arc: true,
        rectangle: true,
        quadratic: false,
        bezier: false,
        marker: true,
        zoom: false,
        lineWidth: false,
        colorsPicker: false,
        extraOptions: false,
        code: false,
        undo: true,
      });
      this.designer.appendTo(document.getElementById('widget-container'));
      this.designer.addSyncListener(function(data) {
        ref.connection.send(data);
      });
    });

    window.onload = function() {
      document.querySelector('#modal').style.display = 'none';
      document.querySelector('#modal').style.top = 0;
      ref.designer.iframe.style.border = '2px solid black';
    };

    this.openRoom();
  },
  watch: {
    isLive: function() {
      var ref = this;
      setTimeout(function() {
        ref.time = setInterval(ref.screenCapture, 1000);
      }, 4000);
    },
  },

  methods: {
    offVideo() {
      let localStream = this.connection.attachStreams[0];
      localStream.mute('video');
      clearInterval(this.time);
      this.isVideo = false;
    },
    onVideo() {
      let localStream = this.connection.attachStreams[0];
      localStream.unmute('video');
      this.time = setInterval(this.screenCapture, 5000);
      this.isVideo = true;
    },
    onAudio() {
      let localStream = this.connection.attachStreams[0];
      localStream.unmute('audio');
      this.isAudio = true;
    },
    offAudio() {
      let localStream = this.connection.attachStreams[0];
      localStream.mute('audio');
      this.connection.streamEvents.selectFirst('local').mediaElement.muted = true;
      this.isAudio = false;
    },
    openWhiteBoard() {
      document.querySelector('#modal').style.top = 0;
      document.querySelector('#modal').style.display = 'block';
    },
    closeWhiteBoard() {
      document.querySelector('#modal').style.display = 'none';
    },
    async screenCapture() {
      let video = document.querySelector('video');
      let canvas = document.createElement('canvas');
      canvas.width = video.videoWidth || video.clientWidth;
      canvas.height = video.videoHeight || video.clientHeight;

      var context = canvas.getContext('2d');
      context.drawImage(video, 0, 0, canvas.width, canvas.height);

      let img = canvas.toDataURL('image/png');

      let userData = {
        uid: this.$store.state.uuid,
        rid: this.$route.query.rid,
        snapshot: img,
      };

      const { data } = await faceAI(userData);
    },
    chat() {
      var chatMessage = this.message;

      if (!chatMessage || !chatMessage.replace(/ /g, '').length) return;

      this.appendChatMessage(chatMessage, this.userName, this.$store.state.uuid);

      this.connection.send({
        chatMessage: chatMessage,
      });

      this.connection.send({
        typing: false,
      });

      this.message = '';
    },
    appendChatMessage(event, userName, uuid) {
      var conversationPanel = document.getElementById('conversation-panel');
      var div = document.createElement('div');
      var date = new Date();
      var timestamp = date.toLocaleTimeString();
      div.className = 'message';

      if (event.data) {
        div.innerHTML = '<b>' + userName + '&nbsp;' + timestamp + ':</b><br>' + event.data.chatMessage;
        this.chatLog += '[' + userName + '][' + timestamp + '] ' + event.data.chatMessage.replaceAll('\n', '') + '\r\n';
        div.style.borderBottom = '0.5px groove #B2EBF2';
      } else {
        div.innerHTML = '<b>' + this.userName + '(당신)&nbsp;' + timestamp + '</b> <br>' + event;
        this.chatLog += '[' + userName + '][' + timestamp + '] ' + event.replaceAll('\n', '') + '\r\n';
        this.chatResult.push({ uid: uuid, userName: userName, chatMessage: event, timestamp: timestamp });
        div.style.background = '#E3F2FD';
        div.style.borderBottom = '0.5px groove #B2EBF2';
      }

      conversationPanel.appendChild(div);

      conversationPanel.scrollTop = conversationPanel.clientHeight;
      conversationPanel.scrollTop = conversationPanel.scrollHeight - conversationPanel.scrollTop;
    },
    openRoom() {
      this.connection = new RTCMultiConnection();
      var ref = this;

      this.connection.session = {
        audio: true,
        video: true,
        data: true,
      };

      // Student에서는 beforeAdding 적용안됨
      // this.connection.beforeAddingStream = function(stream, peer) {
      //   ref.connection.extra.userFullName = ref.$store.state.name;
      //   ref.connection.extra.userUUID = ref.$store.state.uuid;
      //   ref.connection.extra.type = 'cam';
      //   ref.connection.extra.isMaster = true;
      //   return stream;
      // };

      this.connection.socketURL = 'https://rtcmulticonnection.herokuapp.com:443/';
      // this.connection.socketURL = 'https://illeum-webRTC:9001/';

      this.connection.extra.userFullName = this.$store.state.name;
      this.connection.extra.userUUID = this.$store.state.uuid;
      this.connection.extra.type = 'cam';
      this.connection.extra.status = '입장';

      this.connection.sdpConstraints.mandatory = {
        OfferToReceiveAudio: true,
        OfferToReceiveVideo: true,
      };

      // 콘솔로그 출력 해제
      this.connection.enableLogs = false; // to disable logs
      // this.connection.enableLogs = true; // to enable logs

      this.connection.autoCloseEntireSession = true;

      this.connection.checkPresence(this.roomid, function(isRoomExist, roomid) {
        if (isRoomExist === true) {
          ref.checkEntrant().then((result) => {
            if (!result) {
              ref.$swal({
                icon: 'error',
                title: '참여할 수 없는 화상수업입니다.!!',
              });
              ref.$router.push({ name: 'WebRTCListStudent' });
              return;
            }
            ref.connection.onUserStatusChanged();
            push.create(ref.connection.extra.userFullName + '님이 ' + ref.roomid + '수업에 입장했습니다');
            ref.isLive = true;
            ref.connection.join(roomid);
          });
        } else {
          ref.$swal({
            icon: 'error',
            title: '개설되지 않는 화상수업입니다.!!',
          });
          ref.$router.push({ name: 'WebRTCListStudent' });
        }
      });

      // 리스너 영역 시작

      this.connection.onopen = function(event) {
        if (ref.designer.pointsLength <= 0) {
          setTimeout(function() {
            ref.connection.send('plz-sync-points');
          }, 1000);
        }
      };

      this.connection.onmessage = function(event) {
        // chatting
        if (event.data.chatMessage) {
          ref.appendChatMessage(event, event.extra.userFullName, event.extra.userUUID);
          return;
        }

        // canvas
        if (event.data === 'plz-sync-points') {
          ref.designer.sync();
          return;
        }

        // canvas clear
        if (event.data === 'plz-sync-points-for-clear') {
          ref.designer.clearCanvas();
          ref.designer.sync();
          return;
        }

        ref.designer.syncData(event.data);
      };

      this.connection.onstream = function(event) {
        var video = event.mediaElement;
        if (event.extra.type == 'cam') {
          ref.videoId.push({ userFullName: event.extra.userFullName, id: video.id });
          document.querySelector('.videos-container').appendChild(video);
          video.removeAttribute('controls');
          video.onmouseover = function() {
            document.querySelector('.hoverEffect').innerHTML = '<span class="material-icons">face</span>' + event.extra.userFullName;
          };
          video.onmouseout = function() {
            document.querySelector('.hoverEffect').innerHTML = '';
          };
        } else if (event.extra.type == 'share' || event.extra.typeAlpha == 'share') {
          document.querySelectorAll('.videos-container > video').forEach((elem) => {
            elem.style.width = '10%';
          });
          document.querySelector('.share-videos-container').appendChild(video);
        }
      };

      this.connection.onUserStatusChanged = function(event) {
        try {
          if (typeof event.extra !== 'undefined') {
            if (event.extra.isMaster && event.status == 'offline') {
              ref.$swal({
                icon: 'warning',
                title: '현재 화상수업이 종료 되었습니다.!!',
              });

              ref.convertChat();

              ref.connection.getAllParticipants().forEach((participantId) => {
                ref.connection.disconnectWith(participantId);
              });

              ref.connection.attachStreams.forEach(function(localStream) {
                localStream.stop();
              });

              ref.isLive = false;
              clearInterval(ref.time);

              ref.connection.closeSocket();

              ref.$router.push({ name: 'WebRTCListStudent' });
            }
          }
        } catch {
          // ref.$swal({
          //   icon: 'error',
          //   title: '화상수업 정상 종료 오류.!!',
          // });
        }

        ref.names = [];
        ref.connection.getAllParticipants().forEach(function(participantId) {
          var user = ref.connection.peers[participantId];
          ref.names.push({ name: user.extra.userFullName, avatar: '/profile/' + user.extra.userUUID + '/256' });
        });

        ref.names.push({ name: ref.connection.extra.userFullName, avatar: '/profile/' + ref.connection.extra.userUUID + '/256' });
      };

      this.connection.onleave = function(event) {
        var ref = this;
      };

      this.connection.onEntireSessionClosed = function(event) {
        this.$swal({
          icon: 'error',
          title: '전체 세션 종료 ' + event.sessionid,
        });
      };

      this.connection.onmute = function(e) {
        var posterImg = require('@/assets/img/poster.png');
        if (e.session.video) {
          e.mediaElement.setAttribute('poster', posterImg);
          e.mediaElement.srcObject = null;
        }
      };

      this.connection.onunmute = function(e) {
        if (e.session.video) {
          e.mediaElement.removeAttribute('poster');
          e.mediaElement.srcObject = e.stream;
        }
      };

      this.connection.onstreamended = function(event) {
        ref.connection.onleave = function(e) {
          ref.connection.getAllParticipants().forEach((participantId) => {
            if (e.userid == participantId) {
              for (let index = 0; index < ref.videoId.length; index++) {
                if (ref.videoId[index].userFullName == e.extra.userFullName) {
                  if (document.querySelector('#' + ref.videoId[index].id) != null) {
                    document.querySelector('#' + ref.videoId[index].id).remove();
                  }
                }
              }
            }
          });
          // if (document.querySelector('#' + screenId) != null) document.querySelector('#' + screenId).remove();
        };

        if (event.extra.type == 'share' || event.extra.typeAlpha == 'share') {
          var share = document.querySelector('.share-videos-container');
          if (share != null) {
            while (share.hasChildNodes()) {
              share.removeChild(share.firstChild);
            }
          }

          document.querySelectorAll('.videos-container > video').forEach((elem) => {
            elem.style.width = '30%';
          });
        } else if (event.extra.userUUID != ref.$store.state.uuid) {
          const idx = ref.names.findIndex(function(item) {
            return item.name == event.extra.userFullName;
          });
          if (idx > -1) {
            ref.names.splice(idx, 1);
          }
          var screenId = event.mediaElement.id;
          if (document.querySelector('#' + screenId) != null) document.querySelector('#' + screenId).remove();
        }
      };
    },

    screen() {
      this.connection.extra.type = 'share';
      this.connection.extra.typeAlpha = 'share';

      this.connection.updateExtraData();

      this.connection.addStream({
        screen: true,
      });

      this.connection.videosContainer = document.querySelector('.share-videos-container');
      document.querySelectorAll('.videos-container > video').forEach((elem) => {
        elem.style.width = '10%';
      });
    },
    async outRoom() {
      var uid = this.$store.state.uuid;
      var rid = this.rid;
      this.connection.extra.status = '퇴장';
      this.convertChat();
      await exit(uid, rid) // GET: /api/rtc/exit (rabbitMQ)
        .then(({ data }) => {
          if (data == 'success') {
            this.isOutClicked = true;
            this.connection.closeSocket();

            this.connection.attachStreams.forEach(function(localStream) {
              localStream.stop();
            });

            this.$swal({
              icon: 'warning',
              title: '화상수업 나가기.!!',
            });

            this.isLive = false;
            clearInterval(this.time);

            this.$router.push({ name: 'WebRTCListStudent' });
          }
        })
        .catch((err) => {
          this.$swal({
            icon: 'error',
            title: '화상수업 나가기 오류.!!',
          });
        });
    },
    saveMessageLog() {
      var date = new Date();
      var timestamp = date.toLocaleTimeString();
      var fileName = this.roomid + ' ' + timestamp;
      var content = this.chatLog;
      var blob = new Blob([content], { type: 'text/plain' });
      var objURL = window.URL.createObjectURL(blob);

      // 이전에 생성된 메모리 해제
      if (window.__Xr_objURL_forCreatingFile__) {
        window.URL.revokeObjectURL(window.__Xr_objURL_forCreatingFile__);
      }

      window.__Xr_objURL_forCreatingFile__ = objURL;
      // a링크로 다운로드 바로실행
      var a = document.createElement('a');
      a.download = fileName;
      a.href = objURL;
      a.click();
    },
    async convertChat() {
      var rankArr = [];
      var ranking = 1;
      for (let index = 0; index < this.chatResult.length; index++) {
        var indexOfchatResult = rankArr.findIndex((i) => i.uid == this.chatResult[index].uid);
        if (indexOfchatResult == -1) {
          rankArr.push({ uid: this.chatResult[index].uid, participation: 1, ranking: ranking });
          ranking++;
        } else {
          var indexOfObj = rankArr.findIndex((i) => i.uid == this.chatResult[index].uid);
          rankArr[indexOfObj].participation++;
        }
      }

      for (let index = 0; index < rankArr.length; index++) {
        if (rankArr[index].uid == this.$store.state.uuid) {
          var evaluationData = {
            participation: rankArr[index].participation,
            ranking: rankArr[index].ranking,
            vid: this.vid,
          };
          await updateByVid(evaluationData)
            .then(({ data }) => {
              if (data == 'success') {
              }
            })
            .catch((err) => {});
        }
        break;
      }
    },
    async checkEntrant() {
      var result = false;

      var uid = this.$store.state.uuid;
      var rid = this.rid;

      var ref = this;

      var now = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('.')[0] + '.000Z';

      // GET: /api/entrant/findUidAndRid
      await findUidAndRid(uid, rid).then(async ({ data }) => {
        if (data != '') {
          // POST: /api/evaluation/insert
          await insertEvaluation({ uid, rid, now }).then(async ({ data }) => {
            ref.vid = data.vid;
            if (data != '') {
              // GET: /api/rtc/entrance (rabbitMQ)
              await entrance(uid, rid).then(async ({ data }) => {
                if (data != '') {
                  result = true;
                }
              });
            }
          });
        }
      });

      return result;
    },
    clearCanvas() {
      var ref = this;
      this.designer.clearCanvas();
      this.designer.sync();
      setTimeout(function() {
        ref.connection.send('plz-sync-points-for-clear');
      }, 300);
    },
    append(emoji) {
      this.message += emoji;
    },
    preventNav(event) {
      if (!this.isEditing) return;
      event.preventDefault();
      event.returnValue = '';
      this.outRoom().then();
    },
  },
  directives: {
    focus: {
      inserted(el) {
        el.focus();
      },
    },
  },
  destroyed() {
    // cdn 제거
    this.$unloadScript('https://cdn.jsdelivr.net/npm/rtcmulticonnection@latest/dist/RTCMultiConnection.min.js');
    this.$unloadScript('https://rtcmulticonnection.herokuapp.com/socket.io/socket.io.js');
  },
};
</script>

<style scoped>
@font-face {
  font-family: 'NEXON Lv1 Gothic OTF';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv1 Gothic OTF.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}
* {
  font-family: 'NEXON Lv1 Gothic OTF';
}
body {
  -ms-overflow-style: none;
  /* overflow-y: hidden; */
}
body::-webkit-scrollbar {
  display: none;
}

.class-name {
  height: 36px;
  text-align: center;
  border-bottom: 1px solid #e5e5e5;
}
.drag-container {
  display: flex;
  min-height: 85vh;
}

.panel-one {
  width: 80%;
  min-width: 500px;
}

.panel-two {
  flex: 1;
  width: 20%;
  height: 800px;
  padding-bottom: 10px;
}

.panel-three {
  border-top: 2px solid black;
  margin-top: 1px;
  padding-top: 5px;
  height: 6rem;
}

.dragbar {
  padding: 2px;
  cursor: col-resize;
  background-color: black;
}

.img {
  width: inherit;
}

#txt-chat-message {
  width: 100%;
  resize: vertical;
  margin: 5px;
  margin-right: 0;
}

#btn-chat-message {
  margin-bottom: 3rem !important;
  float: right;
}

#btn-chat-message::after {
  clear: both;
}

#conversation-panel {
  margin-bottom: 20px;
  text-align: left;
  min-height: 37rem;
  width: 100%;
  overflow: auto;
}

#conversation-panel .message {
  border-bottom: 1px solid #e5e5e5;
  padding: 5px 10px;
}

#conversation-panel .message img,
#conversation-panel .message video,
#conversation-panel .message iframe {
  max-width: 100%;
}

.videos-container {
  display: contents;
}

.videos-container >>> video {
  display: inline;
  width: -webkit-fill-available;
  width: 30%;
  border: 1px solid;
}
.share-videos-container >>> video {
  display: inline;
  width: -webkit-fill-available;
  width: 80%;
  border: 1px solid;
}

/* modal */
#modal {
  position: fixed;
  top: 200%;
  width: 80%;
  height: 80%;
  z-index: 9999;
  left: 5%;
  padding-bottom: 0px;
}
.modal-btn {
  display: flex;
  margin: 1rem 10rem 0 0;
}
#modal h2 {
  margin: 0;
}

#modal button {
  display: inline-block;
  width: 100px;
  margin-left: calc(100% - 100px - 10px);
}

#modal .modal_content {
  width: 100%;
  height: 80%;
  margin: 100px auto;
  padding-top: 20px;
  background: #fff;
  border: 2px solid #666;
}

#modal .modal_layer {
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  z-index: -1;
}
.wrapper {
  position: relative;
  display: flex;
  height: 60px;
  margin-top: 5rem;
}
.name-list-item {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  font-size: 24px;
  text-decoration: none;
  width: 250px;
  padding: 0px;
  margin: 0px;
}
.name-list-item-content {
  background-color: #e6e6e6;
  background-image: url('//www.kirupa.com/images/gray_smiley.png');
  background-position: 7px 7px;
  background-repeat: no-repeat;
  color: #666;
  list-style: none outside none;
  padding-left: 29px;
  padding-top: 10px;
  height: 50px;
}

.regular-input {
  width: 22rem;
}

.regular-input:focus {
  box-shadow: 0 0 0 3px rgba(66, 153, 225, 0.5);
}
#widget-container {
  bottom: 0;
  right: 20%;
  left: 20%;
  height: 80%;
  border: 1px solid black;
  border-top: 0;
  border-bottom: 0;
}
.emoji-invoker {
  position: absolute;
  top: 0.5rem;
  right: 5rem;
  width: 1.5rem;
  height: 1.5rem;
  border-radius: 50%;
  cursor: pointer;
  transition: all 0.2s;
}
.emoji-invoker:hover {
  transform: scale(1.1);
}
.emoji-invoker > svg {
  fill: #b1c6d0;
}

.emoji-picker {
  position: absolute;
  z-index: 1;
  font-family: Montserrat, 'Serif';
  border: 1px solid #ccc;
  width: 100%;
  height: 20rem;
  overflow: scroll;
  padding: 1rem;
  box-sizing: border-box;
  border-radius: 0.5rem;
  background: #fff;
  box-shadow: 1px 1px 8px #c7dbe6;
  top: -22rem !important;
  left: 0 !important;
}
.emoji-picker__search {
  display: flex;
}
.emoji-picker__search > input {
  flex: 1;
  border-radius: 10rem;
  border: 1px solid #ccc;
  padding: 0.5rem 1rem;
  outline: none;
}
.emoji-picker h5 {
  margin-bottom: 0;
  color: #b1b1b1;
  text-transform: uppercase;
  font-size: 0.8rem;
  cursor: default;
}
.emoji-picker .emojis {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}
.emoji-picker .emojis:after {
  content: '';
  flex: auto;
}
.emoji-picker .emojis span {
  padding: 0.2rem;
  cursor: pointer;
  border-radius: 5px;
}
.emoji-picker .emojis span:hover {
  background: #ececec;
  cursor: pointer;
}

.hoverEffect {
  min-height: 30px;
  font-size: 15px;
  text-align: end;
  padding-right: 30px;
  color: #111;
  font-family: 'Helvetica Neue', sans-serif;
  font-weight: bold;
  letter-spacing: -1px;
}
</style>
