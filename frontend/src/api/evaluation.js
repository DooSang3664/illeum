import { instance, posts } from './index';

//평가조회
function fetchEval() {
  return posts.get('/evaluation/findAll');
}

//평가 생성
function insertEvaluation(evaluationData) {
  return posts.post('/evaluation/insert', evaluationData);
}

//평가 수정(채팅 정보 보내기용:participation,ranking)
function updateByVid(evaluationData) {
  return posts.put('/evaluation/updateByVid', evaluationData);
}
//조건으로 평가조회
function fetchCondition(roomData) {
  return posts.post('/evaluation/roomEntrantInfo', roomData);
}

// POST / api / evaluation / insertAbsent
//결석체크(request:rid,uid)
function insertAbsent(evaluationData) {
  return posts.post('/evaluation/insertAbsent', evaluationData);
}
export { fetchEval, insertEvaluation, updateByVid, fetchCondition, insertAbsent };
