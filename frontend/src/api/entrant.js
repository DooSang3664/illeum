import { instance, posts } from './index';

// 평가기록이 있는 청강자 전체조회
function partinAll() {
  return posts.get('/entrant/findAll');
}

//참여자 테이블 참여여부 조회
function findUidAndRid(uid, rid) {
  return posts.get(`/entrant/findUidAndRid?uid=${uid}&rid=${rid}`);
}
//방 참가한 사람 조회
function findCount(rid) {
  return posts.get(`/entrant/findCount?rid=${rid}`);
}
export { partinAll, findUidAndRid, findCount };
