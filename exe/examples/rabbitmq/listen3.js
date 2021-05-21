//#!/usr/bin/env node

// import Stomp from 'stomp-websocket';
// import Stomp from 'webstomp-client'
// import SockJS from 'sockjs-client';

// const socket = new SockJS('http://k4d106.p.ssafy.io:15674');
// var client = Stomp.over(socket);

var ws = new WebSocket('ws://k4d106.p.ssafy.io:15674/ws');
var client = Stomp.over(ws);

var connectCallback = function () {
  client.subscribe('/amq/queue/member.10', (res) => {
    console.log("구독으로 받은 메시지", res.body);
  }); //큐명을 지정한경우 시 사용
};

var errorCallback = function (error) {
  console.log('소켓 연결 실패', error);
  //alert(error.headers.message);
};

var id = 'illeum';
var pass = 'illeum123';

client.connect(id, pass, connectCallback, errorCallback, '/');

// client.connect(
//   'illeum',
//   'illeum123',
//   (frame) => {
//     // 소켓 연결 성공
//     console.log('소켓 연결 성공', frame);
//     // 서버의 메시지 전송 endpoint를 구독합니다.
//     // 이런형태를 pub sub 구조라고 합니다.
//     client.subscribe('/amq/queue/member.10', (res) => {
//       console.log('구독으로 받은 메시지 입니다.', res.body);
//       //   console.log('구독으로 받은 메시지 입니다.', res.body);

//       // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
//       //   this.recvList.push(JSON.parse(res.body));
//     });
//   },
//   (error) => {
//     // 소켓 연결 실패
//     console.log('소켓 연결 실패', error);
//   }
// );
