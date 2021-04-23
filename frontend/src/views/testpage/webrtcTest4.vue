<template>
  <div>
    <div class="drag-container">
      <div class="panel-one" id="drag-left">
        <div class="videos-container"></div>
        <v-text-field v-model="roomid" label="방번호 입력"></v-text-field>
        <br />
        <v-text-field v-model="userName" label="닉네임 입력"></v-text-field>
        <v-btn depressed color="primary" @click="openRoom">open or join</v-btn>
        <v-btn depressed color="warning" @click="outRoom">퇴장</v-btn>
      </div>
      <div class="dragbar" id="dragbar"></div>
      <div class="panel-two" id="drag-right">
        <h2>Panel 2</h2>
        <div id="conversation-panel"></div>
        <div id="key-press" style="text-align: right; display: none; font-size: 11px;">
          <span style="vertical-align: middle;"></span>
          <img src="https://www.webrtc-experiment.com/images/key-press.gif" style="height: 12px; vertical-align: middle;" />
        </div>
        <textarea id="txt-chat-message" v-model="message"></textarea>
        <button class="btn btn-primary" id="btn-chat-message" @click="chat">Send</button>
      </div>
    </div>
    <div>
      <h2>Panel 3</h2>
      <img class="img" src="https://i0.wp.com/kiramonthly.com/wp-content/uploads/2020/02/1.jpg?fit=1000%2C1429" />
      <img class="img" src="https://img.huffingtonpost.com/asset/5d80b5133b00002efad5453a.jpeg?ops=scalefit_630_noupscale" />
    </div>
  </div>
</template>
<script src="https://cdn.jsdelivr.net/npm/rtcmulticonnection@latest/dist/RTCMultiConnection.min.js"></script>
<script src="https://rtcmulticonnection.herokuapp.com/socket.io/socket.io.js"></script>

<script>
import push from 'push.js';

export default {
  mounted() {
    let cdn1 = document.createElement('script');
    cdn1.setAttribute('src', 'https://cdn.jsdelivr.net/npm/rtcmulticonnection@latest/dist/RTCMultiConnection.min.js');
    cdn1.setAttribute('id', 'cdn1');
    document.body.appendChild(cdn1);
    let cdn2 = document.createElement('script');
    cdn2.setAttribute('src', 'https://rtcmulticonnection.herokuapp.com/socket.io/socket.io.js');
    cdn2.setAttribute('id', 'cdn2');
    document.body.appendChild(cdn2);

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
  },
  data() {
    return {
      roomid: '',
      userName: '',
      connection: null,
      message: '',
    };
  },
  methods: {
    chat() {
      var chatMessage = this.message;
      console.log(this.userName);

      if (!chatMessage || !chatMessage.replace(/ /g, '').length) return;

      this.appendChatMessage(chatMessage, this.userName);

      this.connection.send({
        chatMessage: chatMessage,
      });

      this.connection.send({
        typing: false,
      });
    },
    appendChatMessage(event, userName) {
      var conversationPanel = document.getElementById('conversation-panel');
      var div = document.createElement('div');

      div.className = 'message';

      if (event.data) {
        div.innerHTML = '<b>' + userName + ':</b><br>' + event.data.chatMessage;
      } else {
        div.innerHTML = '<b>' + this.userName + '(당신)</b> <br>' + event;
        div.style.background = '#cbffcb';
      }

      conversationPanel.appendChild(div);

      conversationPanel.scrollTop = conversationPanel.clientHeight;
      conversationPanel.scrollTop = conversationPanel.scrollHeight - conversationPanel.scrollTop;
    },
    openRoom() {
      this.connection = new RTCMultiConnection();

      this.connection.session = {
        audio: true,
        video: true,
        data: true,
      };

      this.connection.socketURL = 'https://rtcmulticonnection.herokuapp.com:443/';
      this.connection.extra.userFullName = this.userName;
      this.connection.sdpConstraints.mandatory = {
        OfferToReceiveAudio: true,
        OfferToReceiveVideo: true,
      };

      this.connection.openOrJoin(this.roomid);
      this.connection.videosContainer = document.querySelector('.videos-container');

      push.create(this.connection.extra.userFullName + '님이 ' + this.roomid + '방에 입장했습니다');

      // 채팅부분영역 시작
      var ref = this;

      this.connection.onmessage = function(event) {
        // 현재 타이핑 중인 이벤트처리 미구현
        // if (event.data.typing === true) {
        //   var key = document.getElementById('key-press');
        //   key.style.display = 'block';
        //   console.log(event.extra.userFullName + ' is typing');
        //   key.querySelector('span').innerHTML(event.extra.userFullName + ' is typing');
        //   return;
        // }

        // if (event.data.typing === false) {
        //   var key = document.getElementById('key-press');
        //   key.style.display = 'none';
        //   key.querySelector('span').innerHTML('');
        //   return;
        // }

        if (event.data.chatMessage) {
          ref.appendChatMessage(event, event.extra.userFullName);
          return;
        }
      };
    },
    outRoom() {
      this.connection.getAllParticipants().forEach((participantId) => {
        this.connection.disconnectWith(participantId);
      });

      this.connection.attachStreams.forEach(function(localStream) {
        localStream.stop();
      });

      this.connection.closeSocket();
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
.drag-container {
  display: flex;
  min-height: 100vh;
}

.panel-one {
  width: 80%;
}

.panel-two {
  flex: 1;
  width: 20%;
}

.dragbar {
  padding: 2px;
  cursor: col-resize;
  background-color: black;
}
.img {
  width: inherit;
}

.videos-container video {
  display: inline-block;
  width: 23.7vw;
  /* width: 30vw; */
  /* width: calc((100vw - 400px) / 2.5); */
  border: 1px solid;
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
}

#conversation-panel {
  margin-bottom: 20px;
  text-align: left;
  max-height: 200px;
  overflow: auto;
  border-top: 1px solid #e5e5e5;
  width: 106%;
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
</style>
