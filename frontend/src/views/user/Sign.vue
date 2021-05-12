<template>
  <div class="sign">
    <img src="@/assets/img/textlogo.png" alt="logo" />
    <p>서비스 이용을 위해 해당 양식을 입력해주세요.</p>
    <div class="form-modal">
      <div class="form-toggle">
        <button id="login-toggle" @click="toggleLogin">LOGIN</button>
        <button id="signup-toggle" @click="toggleSignup">SIGNUP</button>
      </div>

      <div id="login-form">
        <validation-observer ref="loginObserver">
          <form @submit.prevent="submit">
            <!-- email -->
            <validation-provider v-slot="{ errors }" name="이메일" rules="required|max:30">
              <v-text-field ref="loginId" class="input" hide-details="auto" :error-messages="errors" label="E-MAIL" v-model="Lemail" @keyup.enter="$refs.loginPassword.focus()" />
            </validation-provider>

            <!-- password -->
            <validation-provider v-slot="{ errors }" name="비밀번호" rules="required|max:30">
              <v-text-field
                ref="loginPassword"
                class="input"
                :error-messages="errors"
                :append-icon="showLoginPwd ? 'mdi-eye' : 'mdi-eye-off'"
                @click:append="showLoginPwd = !showLoginPwd"
                :type="showLoginPwd ? 'text' : 'password'"
                label="PASSWORD"
                hide-details="auto"
                v-model="Lpassword"
                required
                @keyup.enter="signinUser"
              />
            </validation-provider>
            <button type="button" class="btn login" @click="signinUser">LOGIN</button>
          </form>
        </validation-observer>
      </div>

      <div id="signup-form">
        <validation-observer ref="signupObserver">
          <form @submit.prevent="submit">
            <!-- email -->
            <validation-provider v-slot="{ errors }" name="이메일" rules="required|max:30|email">
              <v-text-field
                ref="signupInput1"
                class="input email"
                :append-icon="isDuplicateEmail ? 'mdi-cancel' : ''"
                @click="isDuplicateEmail = false"
                hide-details="auto"
                v-model="email"
                :error-messages="errors"
                label="E-MAIL"
                @keyup.enter="$refs.signupInput2.focus()"
              ></v-text-field>
            </validation-provider>

            <!-- name -->
            <validation-provider v-slot="{ errors }" name="이름" rules="required|max:20">
              <v-text-field ref="signupInput2" class="input" hide-details="auto" v-model="name" :error-messages="errors" label="NAME" @keyup.enter="$refs.signupInput3.focus()"></v-text-field>
            </validation-provider>

            <!-- password -->
            <validation-provider v-slot="{ errors }" name="비밀번호" rules="required|max:30|password" vid="password">
              <v-text-field
                ref="signupInput3"
                :append-icon="showPwd ? 'mdi-eye' : 'mdi-eye-off'"
                @click:append="showPwd = !showPwd"
                :type="showPwd ? 'text' : 'password'"
                class="input password"
                hide-details="auto"
                v-model="password"
                :error-messages="errors"
                label="PASSWORD"
                @keyup.enter="$refs.signupInput4.focus()"
              ></v-text-field>
            </validation-provider>

            <!-- passwordchk -->
            <validation-provider v-slot="{ errors }" name="비밀번호확인" rules="required|max:30|confirmed:password">
              <v-text-field
                ref="signupInput4"
                :append-icon="showPwdChk ? 'mdi-eye' : 'mdi-eye-off'"
                @click:append="showPwdChk = !showPwdChk"
                :type="showPwdChk ? 'text' : 'password'"
                class="input password"
                hide-details="auto"
                v-model="pwdcheck"
                :error-messages="errors"
                label="CONFIRM PASSWORD"
              ></v-text-field>
            </validation-provider>

            <button type="button" class="btn signup" @click="signup">SIGN UP</button>
          </form>
        </validation-observer>
      </div>
    </div>
  </div>
</template>

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
          push.create(res.body);
        }); //큐명을 지정한경우 시 사용
      },
      (error) => {
        console.log('소켓 연결 실패', error);
        console.dir(error);
      },
      '/'
    );
  }
  disconnect() {
    this.client.disconnect();
    // this.client.close();
  }
}
import { registerUser } from '@/api/auth';
import { required, max, email, confirmed } from 'vee-validate/dist/rules';
import { extend, ValidationObserver, ValidationProvider, setInteractionMode } from 'vee-validate';

setInteractionMode('eager');
extend('max', {
  ...max,
  message: '{_field_} 길이는 {length}자 이하만 가능합니다',
});
extend('required', {
  ...required,
  message: '{_field_} 필수항목 입니다.',
});
extend('email', {
  ...email,
  message: '{_field_} 이메일형식 user@illeum.com 을 지켜주세요.',
});
extend('confirmed', {
  ...confirmed,
  message: '비밀번호가 일치하지 않습니다.!!',
});
extend('password', {
  validate: (value) => {
    let regex = /^(?=.{0,30}\d)(?=.{0,30}[a-z])(?=.{0,30}[^\w\d\s:])([^\s]){8,16}$/.test(value);
    if (!regex) {
      return false;
    } else {
      return true;
    }
  },
  message: '8자이상, 영어/숫자/특수문자 사용해주세요.',
});

import push from 'push.js';

export default {
  name: 'Sign',
  components: {
    ValidationObserver,
    ValidationProvider,
  },
  data() {
    return {
      Lemail: '',
      Lpassword: '',
      name: '',
      email: '',
      password: '',
      pwdcheck: '',
      showLoginPwd: false,
      showPwd: false,
      showPwdChk: false,
      isDuplicateEmail: false,
      msg: [],
    };
  },
  methods: {
    toggleSignup() {
      document.getElementById('login-toggle').style.backgroundColor = '#fff';
      document.getElementById('login-toggle').style.color = '#222';
      document.getElementById('signup-toggle').style.backgroundColor = '#38A897';
      document.getElementById('signup-toggle').style.color = '#fff';
      document.getElementById('login-form').style.display = 'none';
      document.getElementById('signup-form').style.display = 'block';
    },

    toggleLogin() {
      document.getElementById('login-toggle').style.backgroundColor = '#38A897';
      document.getElementById('login-toggle').style.color = '#fff';
      document.getElementById('signup-toggle').style.backgroundColor = '#fff';
      document.getElementById('signup-toggle').style.color = '#222';
      document.getElementById('signup-form').style.display = 'none';
      document.getElementById('login-form').style.display = 'block';
    },

    async signup() {
      this.$refs.signupObserver.validate().then(async (result) => {
        if (result) {
          const userData = {
            email: this.email,
            name: this.name,
            thumbnail: 'string',
            password: this.password,
          };
          try {
            const { data } = await registerUser(userData);
            if (data.success === true) {
              this.$swal({
                icon: 'success',
                title: '회원가입성공!!',
                showConfirmButton: false,
                timer: 1500,
              });
              this.$router.go();
            } else if ((data.message = 'duplicated email')) {
              this.isDuplicateEmail = true;
              this.$swal({
                icon: 'error',
                title: '이메일이 중복되었습니다.!!',
              });
            } else {
              this.$swal({
                icon: 'error',
                title: '회원가입 실패 관리자에게 문의해주세요',
              });
            }
          } catch (error) {
            this.$swal({
              icon: 'error',
              title: '회원가입 실패 관리자에게 문의해주세요',
            });
          }
        }
      });
    },

    async signinUser() {
      this.$refs.loginObserver.validate().then(async (result) => {
        if (result) {
          const userData = {
            email: this.Lemail,
            password: this.Lpassword,
          };
          await this.$store.dispatch('LOGIN', userData);
          const mqSocket = new AlertRabbitMQSocket(this.$store.state.uuid);
          this.$store.commit('setAlertSocket', mqSocket);
          mqSocket.connect();
        }
      });
    },
  },
};
</script>

<style scoped>
.input >>> .v-input__slot::before {
  border-style: none !important;
}
.input >>> .v-input__slot::after {
  border-style: none !important;
}
.password >>> .v-icon.v-icon {
  color: rgb(56, 168, 151);
}
.email >>> .v-icon.v-icon {
  color: red;
}

@import url('https://fonts.googleapis.com/css?family=Montserrat|Quicksand');
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: 'quicksand', Arial, Helvetica, sans-serif;
}

.sign {
  /* display: grid; */
  height: 100%;
  width: 100%;
  place-items: center;
  background: no-repeat center url('../../assets/img/bg4.jpg');
  /* background: -webkit-linear-gradient(90deg, #0162c8, #55e7fc); */
}
::selection {
  background: #6173ff;
  color: #fff;
}

img {
  margin-top: 5%;
  width: 15vw;
}
.sign p {
  font-weight: 900;
  margin: 10px 0px;
}
.form-modal {
  position: relative;
  width: 450px;
  height: auto;
  margin-top: 2%;
  left: 50%;
  transform: translateX(-50%);
  background: #fff;
  border-top-right-radius: 20px;
  border-top-left-radius: 20px;
  border-bottom-right-radius: 20px;
  box-shadow: 0 3px 20px 0px rgba(0, 0, 0, 0.1);
}

.form-modal button {
  cursor: pointer;
  position: relative;
  text-transform: capitalize;
  font-size: 1em;
  z-index: 2;
  outline: none;
  background: #fff;
  transition: 0.2s;
}

.form-modal .btn {
  border-radius: 20px;
  font-weight: bold;
  font-size: 1.2em;
  padding: 0.8em 1em 0.8em 1em !important;
  transition: 0.5s;
  border: 1px solid #ebebeb;
  margin-bottom: 0.5em;
  margin-top: 0.5em;
  letter-spacing: 5px;
}

.form-modal .login,
.form-modal .signup {
  background: #38a897;
  color: #fff;
}

.form-modal .login:hover,
.form-modal .signup:hover {
  background: #6173ff;
}

.form-toggle {
  position: relative;
  width: 100%;
  height: auto;
}

.form-toggle button {
  width: 50%;
  float: left;
  padding: 1.5em;
  margin-bottom: 1.5em;
  border: none;
  transition: 0.2s;
  font-size: 1.1em;
  font-weight: bold;
  border-top-right-radius: 20px;
  border-top-left-radius: 20px;
}

.form-toggle button:nth-child(1) {
  border-bottom-right-radius: 20px;
}

.form-toggle button:nth-child(2) {
  border-bottom-left-radius: 20px;
}

#login-toggle {
  background: #38a897;
  color: #ffff;
  letter-spacing: 3px;
}
#signup-toggle {
  /* background: #38a897; */
  color: #000;
  letter-spacing: 3px;
}

.form-modal form {
  position: relative;
  width: 90%;
  height: auto;
  left: 50%;
  transform: translateX(-50%);
}

#login-form,
#signup-form {
  position: relative;
  width: 100%;
  height: auto;
  padding-bottom: 1em;
}

#signup-form {
  display: none;
}

#login-form button,
#signup-form button {
  width: 100%;
  margin-top: 0.5em;
  padding: 0.6em;
}

.form-modal .input {
  position: relative;
  width: 100%;
  height: 70%;
  font-size: 1em;
  padding: 1em 1.7em 1em 1.7em;
  margin-top: 0.6em;
  margin-bottom: 0.6em;
  border-radius: 20px;
  border: none;
  background: #ebebeb;
  outline: none;
  font-weight: bold;
  transition: 0.4s;
}

.form-modal .input:focus,
.form-modal .input:active {
  transform: scaleX(1.02);
}

.form-modal .input::-webkit-input-placeholder {
  color: #777777;
}

@media only screen and (max-width: 500px) {
  .form-modal {
    width: 100%;
  }
}

@media only screen and (max-width: 400px) {
  i {
    display: none !important;
  }
}
</style>
