package com.ssafy.pjt.controller;

import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pjt.dto.Entrant;
import com.ssafy.pjt.dto.Evaluation;
import com.ssafy.pjt.dto.Room;
import com.ssafy.pjt.dto.request.insertEvaluationDto;
import com.ssafy.pjt.dto.request.insertRoomEvaluationDto;
import com.ssafy.pjt.dto.request.updateEvaluationDto;
import com.ssafy.pjt.repository.EntrantRepository;
import com.ssafy.pjt.repository.EvaluationRepository;
import com.ssafy.pjt.repository.RoomRepository;
import com.ssafy.pjt.repository.mapper.EvaluationMapper;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping("/api/evaluation")
public class EvaluationController {
	@Autowired
	private EvaluationRepository evaluationRepository;

	@Autowired
	private EvaluationMapper evaluationMapper;

	@Autowired
	private EntrantRepository entrantRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	@ApiOperation(value = "평가 전체조회")
	@GetMapping(path = "/findAll")
	public Iterable<Evaluation> getAllEvaluation() {
		return evaluationRepository.findAll();
	}

	@ApiOperation(value = "평가 조회")
	@GetMapping(path = "/findByVid")
	public Evaluation getByVid(@RequestParam int vid) {
		return evaluationRepository.findByVid(vid);
	}
		
	@ApiOperation(value = "조건으로 평가 조회")
	@PostMapping(path = "/roomEntrantInfo")
	public ResponseEntity<Object> roomEntrantInfo(@RequestBody insertRoomEvaluationDto dto) {
		try {
			List<Map<String, Object>> evaluation = evaluationMapper.roomEntrantInfo(dto);
			if (evaluation.isEmpty())
				return new ResponseEntity<>("없음", HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(evaluation, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = "평가 생성(출석 체크)")
	@Transactional
	@PostMapping(path = "/insert")
	public ResponseEntity<Object> insert(@RequestBody insertEvaluationDto insertDto) {
		try {
			Entrant entrant = entrantRepository.findByUidAndRid(insertDto.getUid(), insertDto.getRid());
			if (entrant == null)
				return new ResponseEntity<>("방에 참가한 사람이 아닙니다.", HttpStatus.NO_CONTENT);
			if(roomRepository.findByRid(insertDto.getRid()).getUid() == insertDto.getUid()) {
				return new ResponseEntity<>("개설자 입니다.", HttpStatus.NO_CONTENT);
			}
			if (evaluationMapper.seachEvaluation(entrant.getEid()) == null) {
				Room room = roomRepository.findByRid(insertDto.getRid());
				Evaluation evaluation = new Evaluation();
				if (room.getStartTime().plusMinutes(10).isAfter(insertDto.getNow())) {
					evaluation.setAttend("정상");
				} else {
					evaluation.setAttend("지각");
				}
				evaluation.setEid(entrant.getEid());
				evaluation.setRanking(1000);
				evaluation = evaluationRepository.save(evaluation);
				return new ResponseEntity<>(evaluation, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("이미 출석하였습니다.", HttpStatus.OK);
			}
		} catch (SQLException e1) {
			return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "결석 체크")
	@Transactional
	@PostMapping(path = "/insertAbsent")
	public ResponseEntity<Object> inserAbsent(@RequestBody insertEvaluationDto insertDto) {
		try {
			Entrant entrant = entrantRepository.findByUidAndRid(insertDto.getUid(), insertDto.getRid());
			if (entrant == null)
				return new ResponseEntity<>("방에 참가한 사람이 아닙니다.", HttpStatus.NO_CONTENT);
			if(roomRepository.findByRid(insertDto.getRid()).getUid() == insertDto.getUid()) {
				return new ResponseEntity<>("개설자 입니다.", HttpStatus.NO_CONTENT);
			}
			Evaluation evaluation = new Evaluation();
			evaluation.setAttend("결석");
			evaluation.setEid(entrant.getEid());
			evaluation.setRanking(1000);
			evaluation = evaluationRepository.save(evaluation);
			return new ResponseEntity<>(evaluation, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "평가 삭제")
	@Transactional
	@DeleteMapping(path = "/deleteByVid")
	public ResponseEntity<Object> deleteByVid(@RequestParam int vid) {
		if (evaluationRepository.findByVid(vid) != null) {
			try {
				evaluationRepository.deleteByVid(vid);
			} catch (Exception e) {
				return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>("success", HttpStatus.OK);
	}

	@ApiOperation(value = "평가 수정(vid만 필수)")
	@Transactional
	@PutMapping(path = "/updateByVid")
	public ResponseEntity<Object> update(@RequestBody updateEvaluationDto evaluationDto) {
		Evaluation evaluation = evaluationRepository.findByVid(evaluationDto.getVid());

		if (evaluation == null)
			return new ResponseEntity<>("평가가 없습니다.", HttpStatus.NO_CONTENT);

		if (evaluationDto.getEid() != null)
			evaluation.setEid(evaluationDto.getEid());
		if (evaluationDto.getEval_date() != null)
			evaluation.setEvalDate(Date.from(evaluationDto.getEval_date().atZone(ZoneId.systemDefault()).toInstant()));
		if (evaluationDto.getAttention() != null)
			evaluation.setAttention(evaluationDto.getAttention());
		if (evaluationDto.getParticipation() != null)
			evaluation.setParticipation(evaluationDto.getParticipation());
		if (evaluationDto.getDistracted() != null)
			evaluation.setDistracted(evaluationDto.getDistracted());
		if (evaluationDto.getAsleep() != null)
			evaluation.setAsleep(evaluationDto.getAsleep());
		if (evaluationDto.getAfk() != null)
			evaluation.setAfk(evaluationDto.getAfk());
		if (evaluationDto.getAttend_time() != null)
			evaluation.setAttendTime(
					Date.from(evaluationDto.getAttend_time().atZone(ZoneId.systemDefault()).toInstant()));
		if (evaluationDto.getRanking() != null)
			evaluation.setRanking(evaluationDto.getRanking());
		if (evaluationDto.getAttend() != null)
			evaluation.setAttend(evaluationDto.getAttend());
		try {
			evaluationRepository.save(evaluation);
		} catch (Exception e) {
			return new ResponseEntity<>("fail", HttpStatus.BAD_GATEWAY);
		}
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
}
