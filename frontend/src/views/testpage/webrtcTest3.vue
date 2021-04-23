<template>
  <div>
    <div class="drag-container">
      <div class="panel-one" id="drag-left">
        <div class="videos-container"></div>
        <input v-model="roomid" placeholder="Unique room ID" />
        <v-btn depressed color="primary" @click="openRoom">open or join</v-btn>
        <v-btn depressed color="warning" @click="outRoom">퇴장</v-btn>
      </div>
      <div class="dragbar" id="dragbar"></div>
      <div class="panel-two" id="drag-right">
        <h2>Panel 2</h2>
        <img class="img" src="https://i0.wp.com/kiramonthly.com/wp-content/uploads/2020/02/1.jpg?fit=1000%2C1429" />
        <img class="img" src="https://img.huffingtonpost.com/asset/5d80b5133b00002efad5453a.jpeg?ops=scalefit_630_noupscale" />
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
      connection: null,
    };
  },
  methods: {
    openRoom() {
      this.connection = new RTCMultiConnection();

      this.connection.session = {
        audio: true,
        video: true,
        data: true,
      };

      this.connection.socketURL = 'https://rtcmulticonnection.herokuapp.com:443/';

      this.connection.sdpConstraints.mandatory = {
        OfferToReceiveAudio: true,
        OfferToReceiveVideo: true,
      };
      this.connection.openOrJoin(this.roomid);
      this.connection.videosContainer = document.querySelector('.videos-container');
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
</style>
