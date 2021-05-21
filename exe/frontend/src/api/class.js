import { instance, posts } from './index';

//방리스트조회 (2. WebRTC 전체 조회)
function classAll(classData) {
  return posts.get('/room/findAll', classData);
}

//room_name으로 방 조회 (3. WebRTC 방조회)
function fetchRoomname(roomName) {
  return posts.get(`/room/findByRoomName?roomName=${roomName}`);
}

//클래스 참여
function insertRoom(insertInfo) {
  return posts.post('/entrant/insert', insertInfo);
}

//방생성
function createClass(classData) {
  return posts.post('/room/insert', classData);
}
//방수정(rid만 필수)
function updateClass(classData) {
  return posts.put('/room/updateByRid', classData);
}

//방 삭제
function deleteClass(classData) {
  return posts.delete('/room/deleteByRid', classData);
}
//방에참여한 멤버목록조회
function getStudents(rid) {
  return posts.get(`/room/member?rid=${rid}`);
}

//방에 참여한 멤버의 평가 목록 조회
function evaluateList(roomId) {
  return posts.get(`/room/evaluation?rid=${roomId}`);
}

//rid로 방조회
function findByRidClass(rid) {
  return posts.get(`/room/findByRid?rid=${rid}`);
}
//개설자 uid로 방조회 (uid)
function findByUidClass(token, uid) {
  return posts.get(`/room/findByUid?accessToken=${token}&uid=${uid}`);
}

export { createClass, updateClass, deleteClass, getStudents, fetchRoomname, evaluateList, classAll, findByRidClass, findByUidClass, insertRoom };
