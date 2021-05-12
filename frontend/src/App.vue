<template>
  <v-app id="app">
    <router-view name="header" />

    <router-view />
  </v-app>
</template>

<!--
<script>
  class AlertRabbitMQSocket {
    constructor(uid) {
      this.uid = uid;
      let ws = new WebSocket('ws://k4d106.p.ssafy.io:15674/ws');
      this.client = Stomp.over(ws);
      this.client.debug = () => {};
      this.login = 'illeum-guest';
      this.passcode = 'illeum-guest';
    }

    connect() {
      this.client.connect(
        this.login,
        this.passcode,
        () => {
          this.client.subscribe(`/amq/queue/member.${this.uid}`, (res) => {
            console.log('구독으로 받은 메시지', res.body);
          }); //큐명을 지정한경우 시 사용
        },
        (error) => {
          console.log('소켓 연결 실패', error);
        },
        '/'
      );
    }
  }
  var mqSocket = new AlertRabbitMQSocket(10);
  mqSocket.connect();
</script>
-->

<style>
body {
  -ms-overflow-style: none;
  overflow-y: hidden;
}
body::-webkit-scrollbar {
  display: none;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
/* .main-app {
  margin: 0 15% 0 15%;
} */
</style>
