<template>
  <div>
    <div class="videos-container"></div>
    <input v-model="roomid" placeholder="Unique room ID" />
    <v-btn depressed color="primary" @click="openRoom">open or join</v-btn>
    <v-btn depressed color="warning" @click="outRoom">퇴장</v-btn>
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

<style></style>
