import { instance, posts } from './index';

//입장시 rabbit
function entrance(uid, rid) {
  return posts.get(`/rtc/entrance?uid=${uid}&rid=${rid}`);
}

//퇴장시 rabbit
function exit(uid, rid) {
  return posts.get(`/rtc/exit?uid=${uid}&rid=${rid}`);
}

//시작시 rabbit
function start(rid) {
  return posts.get(`/rtc/start?rid=${rid}`);
}

export { entrance, exit, start };
