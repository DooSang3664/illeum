import Push from 'push.js';

// this.$toast("I'm a toast!", {
//   position: 'bottom-right',
//   timeout: 5000,
//   closeOnClick: true,
//   pauseOnFocusLoss: true,
//   pauseOnHover: true,
//   draggable: true,
//   draggablePercent: 0.3,
//   showCloseButtonOnHover: false,
//   hideProgressBar: true,
//   closeButton: 'button',
//   icon: true,
//   rtl: false,
// });

class AlertRabbitMQSocket {
  constructor(uid, msgCallback) {
    console.log(msgCallback);
    this.uid = uid;
    let ws = new WebSocket(`${window.location.protocol.replace('http', 'ws')}//${window.location.host}/api/rabbitmq/`);
    this.client = Stomp.over(ws);
    this.client.debug = () => {};
    this.login = 'illeum-guest';
    this.passcode = 'illeum-guest';
    this.msgCallback = msgCallback;
  }

  connect() {
    this.client.connect(
      this.login,
      this.passcode,
      () => {
        this.client.subscribe(`/amq/queue/member.${this.uid}`, (res) => {
          console.log('큐 메세지', res);
          let message;
          if (Push.Permission.has()) {
            if (res.headers['content-type'] == 'application/json') {
              message = JSON.parse(res.body)['message'];
            } else {
              message = res.body;
            }
            Push.create(message);
          }
          if (this.msgCallback) this.msgCallback(message);
        }); //큐명을 지정한경우 시 사용
      },
      (error) => {
        console.dir(error);
      },
      '/'
    );
  }
  disconnect() {
    this.client.disconnect();
  }
}

let instance = null;
let isConnected = false;
class Notification {
  constructor(uid, msgCallback) {
    if (instance) {
      return instance;
    }
    if (!uid) return null;
    this.socket = new AlertRabbitMQSocket(uid, msgCallback);
    this.connect();
    instance = this;
  }

  connect() {
    if (isConnected == false) {
      console.log('rabbitmq 연결');
      this.socket.connect();
      isConnected = true;
    }
  }

  disconnect() {
    if (isConnected == true) {
      console.log('rabbitmq 연결중단');
      this.socket.disconnect();
      instance = null;
      isConnected = false;
    }
  }
}

function notification(uid, msgCallback) {
  return new Notification(uid, msgCallback);
}

export { notification };
