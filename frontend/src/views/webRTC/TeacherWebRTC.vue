<template>
  <div>
    <div class="class-name">
      <h2>{{ this.$route.query.room_name }}</h2>
    </div>

    <div class="drag-container">
      <div class="panel-one" id="drag-left">
        <div class="videos-container"></div>
        <div class="share-videos-container"></div>
      </div>
      <div class="dragbar" id="dragbar"></div>
      <div class="panel-two" id="drag-right">
        <div id="onUserStatusChanged">
          참여자 목록
          <template v-for="name in names">
            {{ name }}
          </template>
        </div>
        <div id="conversation-panel"></div>
        <div id="key-press" style="text-align: right; display: none; font-size: 11px;">
          <span style="vertical-align: middle;"></span>
          <img alt="" src="https://www.webrtc-experiment.com/images/key-press.gif" style="height: 12px; vertical-align: middle;" />
        </div>
        <div class="wrapper">
          <v-textarea id="txt-chat-message" class="regular-input" rows="1" auto-grow single-line outlined style="border-color: white;" v-model="message" @keyup.enter="chat" label=""></v-textarea>

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
        </div>
        <button class="btn btn-primary" id="btn-chat-message" @click="chat">Send</button>
      </div>
    </div>
    <div class="panel-three">
      <template v-if="isAudio">
        <v-btn class="mr-4" color="error" @click="offAudio">오디오 끄기</v-btn>
      </template>
      <template v-else>
        <v-btn class="mr-4" color="primary" @click="onAudio">오디오 켜기</v-btn>
      </template>
      <template v-if="isVideo">
        <v-btn class="mr-4" color="error" @click="offVideo">비디오 끄기</v-btn>
      </template>
      <template v-else>
        <v-btn class="mr-4" color="primary" @click="onVideo">비디오 켜기</v-btn>
      </template>

      <v-btn class="mr-4" color="cyan" @click="screen">화면공유</v-btn>
      <v-btn class="mr-4" color="cyan" @click="openWhiteBoard">화이트보드</v-btn>
      <v-btn class="mr-4" color="cyan" @click="saveMessageLog">채팅기록저장</v-btn>
      <v-btn class="mr-4" color="cyan" @click="chatTest">채팅콘솔테스트</v-btn>
      <v-btn class="mr-4" color="error" @click="outRoom">종료</v-btn>
    </div>

    <!-- 화이트보드 모달 영역 -->
    <div id="modal">
      <div class="modal_content text-center">
        <v-btn class="mr-4 mb-4" color="error" @click="closeWhiteBoard">닫기</v-btn>
        <div id="widget-container" style="height: 80%; width: 80%; border: 1px solid black; border-top:0; border-bottom: 0;"></div>
      </div>
    </div>
    <div class="modal_layer"></div>
  </div>
</template>

<script>
import push from 'push.js';
import { updateClass, getStudents } from '@/api/class';
import { insertAbsent } from '@/api/evaluation';
export default {
  data() {
    return {
      roomid: '',
      userName: '',
      connection: null,
      designer: null,
      message: '',
      chatLog: '',
      chatResult: [],
      isAudio: true,
      isVideo: true,
      userUIDList: [],
      search: '',
      names: [], // name list
    };
  },
  created() {
    const name = this.$store.state.name;
    const room_name = this.$route.query.room_name;
    this.roomid = room_name;
    this.userName = name;
    this.getStudentList();
  },

  async mounted() {
    await this.$loadScript('https://cdn.jsdelivr.net/npm/rtcmulticonnection@latest/dist/RTCMultiConnection.min.js')
      .then(() => {
        console.log('RTCMultiConnection Load...');
      })
      .catch(() => {
        console.log('RTCMultiConnection failed...');
      });
    await this.$loadScript('https://rtcmulticonnection.herokuapp.com/socket.io/socket.io.js')
      .then(() => {
        console.log('socket.io Load...');
      })
      .catch(() => {
        console.log('socket.io failed...');
      });

    var left = document.getElementById('drag-left');
    var right = document.getElementById('drag-right');
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
    var ref = this;
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
        pdf: true,
        eraser: true,
        line: true,
        arrow: true,
        dragSingle: true,
        dragMultiple: true,
        arc: true,
        rectangle: true,
        quadratic: false,
        bezier: true,
        marker: true,
        zoom: false,
        lineWidth: false,
        colorsPicker: false,
        extraOptions: false,
        code: false,
        undo: true,
        isLoad: false,
      });
      this.designer.appendTo(document.getElementById('widget-container'));
      this.designer.addSyncListener(function(data) {
        console.log('sync canvas');
        ref.connection.send(data);
      });
    });

    window.onload = function() {
      document.querySelector('#modal').style.display = 'none';
      document.querySelector('#modal').style.top = 0;
    };

    this.openRoom();
  },

  methods: {
    offVideo() {
      let localStream = this.connection.attachStreams[0];
      localStream.mute('video');
      this.isVideo = false;
    },
    onVideo() {
      this.connection.session.video = true;
      let localStream = this.connection.attachStreams[0];
      localStream.unmute('video');
      this.isVideo = true;
    },
    onAudio() {
      let localStream = this.connection.attachStreams[0];
      localStream.unmute('audio');
      this.isAudio = true;
    },
    offAudio() {
      let localStream = this.connection.attachStreams[0];
      localStream.unmute('audio');
      localStream.mute('audio');
      this.connection.streamEvents.selectFirst('local').mediaElement.muted = true;
      this.isAudio = false;
    },
    openWhiteBoard() {
      document.querySelector('#modal').style.top = 0;
      document.querySelector('#modal').style.display = 'block';
    },
    closeWhiteBoard() {
      console.log(document.querySelector('#modal').style.display);
      document.querySelector('#modal').style.display = 'none';
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
        console.log(userName + ' ' + uuid + ' ' + event.data.chatMessage + ' ' + timestamp);
        this.chatResult.push({ uuid: uuid, userName: userName, chatMessage: event.data.chatMessage, timestamp: timestamp });
        this.chatLog += userName + ' ' + uuid + ' ' + event.data.chatMessage.replaceAll('\n', '') + ' ' + timestamp + '\r\n';
      } else {
        div.innerHTML = '<b>' + this.userName + '(당신)&nbsp;' + timestamp + '</b> <br>' + event;
        this.chatLog += userName + ' ' + uuid + ' ' + event.replaceAll('\n', '') + ' ' + timestamp + '\r\n';
        this.chatResult.push({ uid: uuid, userName: userName, chatMessage: event, timestamp: timestamp });
        div.style.background = '#cbffcb';
      }

      conversationPanel.appendChild(div);

      conversationPanel.scrollTop = conversationPanel.clientHeight;
      conversationPanel.scrollTop = conversationPanel.scrollHeight - conversationPanel.scrollTop;
    },
    openRoom() {
      this.connection = new RTCMultiConnection();
      var ref = this;

      this.connection.extra.userFullName = this.$store.state.name;
      this.connection.extra.userUUID = this.$store.state.uuid;
      this.connection.extra.type = 'cam';
      this.connection.extra.isMaster = true;

      this.connection.session = {
        audio: true,
        video: true,
        data: true,
        // screen: true,
        // oneway: true,
      };

      this.connection.beforeAddingStream = function(stream, peer) {
        ref.connection.extra.userFullName = ref.$store.state.name;
        ref.connection.extra.userUUID = ref.$store.state.uuid;
        ref.connection.extra.type = 'cam';
        ref.connection.extra.isMaster = true;
        return stream;
      };

      this.connection.autoCloseEntireSession = true;

      this.connection.socketURL = 'https://rtcmulticonnection.herokuapp.com:443/';
      // this.connection.socketURL = 'https://illeum-webRTC:9001/';

      this.connection.sdpConstraints.mandatory = {
        OfferToReceiveAudio: true,
        OfferToReceiveVideo: false,
      };

      // 콘솔로그 출력 해제
      this.connection.enableLogs = false; // to disable logs
      // this.connection.enableLogs = true; // to enable logs

      this.connection.checkPresence(this.roomid, function(isRoomExist, roomid) {
        if (isRoomExist === true) {
          ref.connection.join(roomid);
        } else {
          ref.connection.onUserStatusChanged();

          push.create(ref.connection.extra.userFullName + '님이 ' + ref.roomid + '화상수업을 개설하였습니다');
          ref.connection.open(roomid);
        }
      });

      // 리스너 영역
      this.connection.onopen = function() {
        if (ref.designer.pointsLength <= 0) {
          // you seems having data to be synced with new user!
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
          console.log(ref.designer);
          ref.designer.sync();
          return;
        }

        ref.designer.syncData(event.data);
      };

      this.connection.onstream = function(event) {
        var video = event.mediaElement;
        if (event.extra.type == 'cam') {
          document.querySelector('.videos-container').appendChild(video);
          video.removeAttribute('controls');
        } else {
          document.querySelector('.share-videos-container').appendChild(video);
        }
      };

      this.connection.onUserStatusChanged = function(event) {
        ref.names = [];
        ref.connection.getAllParticipants().forEach(function(participantId) {
          var user = ref.connection.peers[participantId];
          ref.names.push(user.extra.userFullName);
          if (ref.userUIDList.includes(event.extra.userUUID)) {
            const index = ref.userUIDList.indexOf(event.extra.userUUID);
            if (index > -1) {
              ref.userUIDList.splice(index, 1);
            }
          }
        });
        if (!ref.names.length) {
          ref.names = ['Only You'];
        } else {
          ref.names.push(ref.connection.extra.userFullName);
        }
      };

      this.connection.onleave = function(event) {
        const idx = ref.names.indexOf(event.extra.userFullName);
        console.log(idx);
        if (idx > -1) {
          ref.names.splice(idx, 1);
        }
        var temp = ref.names;

        console.log(event);
        ref.connection.extra.status = '퇴장';
        ref.connection.onUserStatusChanged(event);
        if (temp.length == 1) ref.names = ['Only You'];
        else ref.names = temp;
      };

      this.connection.onclose = function(event) {
        ref.names = [];
        console.log('close');
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
    },

    async getStudentList() {
      await getStudents(this.$route.query.rid).then(({ data }) => {
        for (let index = 0; index < data.length; index++) {
          this.userUIDList.push(data[index].uid);
        }
      });
    },

    async outRoom() {
      var ref = this;

      if (!this.userUIDList.empty) {
        for (let index = 0; index < this.userUIDList.length; index++) {
          await insertAbsent({ rid: this.$route.query.rid, uid: this.userUIDList[index] }).then(({ data }) => {
            if (data != null) {
              console.log(data.eid + ' 결석처리완료');
            }
          });
        }
      }

      var end_time = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('.')[0] + '.000Z';
      await updateClass({ rid: this.$route.query.rid, room_state: '준비', end_time })
        .then(({ data }) => {
          ref.chatTest();

          if (data == 'success') {
            this.$swal({
              icon: 'success',
              title: '<h2>화상수업이 종료되었습니다.!!</h2>',
              toast: true,
              width: 600,
              padding: '2em',
              position: 'top-end',
              showConfirmButton: false,
              timer: 5000,
              timerProgressBar: true,
              didOpen: (toast) => {
                toast.addEventListener('mouseenter', ref.$swal.stopTimer);
                toast.addEventListener('mouseleave', ref.$swal.resumeTimer);
              },
            });

            ref.connection.autoCloseEntireSession = true;

            ref.connection.getAllParticipants().forEach((participantId) => {
              ref.connection.disconnectWith(participantId);
            });

            ref.connection.attachStreams.forEach(function(localStream) {
              localStream.stop();
            });

            ref.connection.closeSocket();
            ref.connection.disconnect();

            ref.$router.push({ name: 'WebRTCList' });
          }
        })
        .catch((err) => {
          this.$swal({
            icon: 'error',
            title: '화상수업 종료 오류.!!',
          });
        });
    },
    saveMessageLog() {
      var fileName = this.roomid;
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
    chatTest() {
      var rankArr = [];
      var rank = 1;
      for (let index = 0; index < this.chatResult.length; index++) {
        var indexOfchatResult = rankArr.findIndex((i) => i.uuid == this.chatResult[index].uuid);
        if (indexOfchatResult == -1) {
          rankArr.push({ uuid: this.chatResult[index].uuid, participation: 1, rank: rank });
          rank++;
        } else {
          var indexOfObj = rankArr.findIndex((i) => i.uuid == this.chatResult[index].uuid);
          rankArr[indexOfObj].participation++;
        }
      }

      console.log(rankArr);
    },
    append(emoji) {
      this.message += emoji;
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
body {
  -ms-overflow-style: none;
  overflow-y: hidden;
}
body::-webkit-scrollbar {
  display: none;
}

.class-name {
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
}
.panel-three {
  border-top: 2px solid black;
  padding-top: 5px;
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
  min-height: 30px;
}

#btn-chat-message {
  margin: 5px;
  display: block;
  float: right;
}
#btn-chat-message::after {
  clear: both;
}

#conversation-panel {
  margin-bottom: 20px;
  text-align: left;
  min-height: 700px;
  overflow: scroll;
  overflow-x: hidden;
  /* border-top: 1px solid #e5e5e5; */
  width: 100%;
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

.videos-container >>> video {
  display: inline;
  width: -webkit-fill-available;
  width: 30%;
  border: 1px solid;
  /* pointer-events: none; */
}
.share-videos-container >>> video {
  display: inline;
  width: -webkit-fill-available;
  width: 80%;
  border: 1px solid;
  /* pointer-events: none; */
}

/* mordal */
#modal {
  position: fixed;
  top: 200%;
  width: 100%;
  height: 100%;
  z-index: 9999;
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
  padding: 20px 10px;
  background: #fff;
  border: 2px solid #666;
}

#modal .modal_layer {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  z-index: -1;
}
.wrapper {
  position: relative;
  display: inline-block;
}

.regular-input {
  padding: 0.5rem 1rem;
  /* border-radius: 3px; */
  /* border: 1px solid #ccc; */
  width: 22rem;
  /* height: 6rem; */
  /* outline: none; */
}

.regular-input:focus {
  box-shadow: 0 0 0 3px rgba(66, 153, 225, 0.5);
}

.emoji-invoker {
  position: absolute;
  top: 0.5rem;
  right: 0.5rem;
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
  width: 15rem;
  height: 20rem;
  overflow: scroll;
  padding: 1rem;
  box-sizing: border-box;
  border-radius: 0.5rem;
  background: #fff;
  box-shadow: 1px 1px 8px #c7dbe6;
  top: 0 !important;
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
</style>
