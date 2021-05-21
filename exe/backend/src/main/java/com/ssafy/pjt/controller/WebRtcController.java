package com.ssafy.pjt.controller;

import com.ssafy.pjt.dto.Member;
import com.ssafy.pjt.dto.Room;
import com.ssafy.pjt.repository.MemberRepository;
import com.ssafy.pjt.repository.RoomRepository;

import io.swagger.annotations.ApiOperation;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/rtc")
public class WebRtcController {
	@Autowired
	private RoomRepository roomRepository;
    
    @Autowired
    private MemberRepository memberRepository;
    
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@ApiOperation(value = "미팅  입장")
	@GetMapping(path = "/entrance")
	public ResponseEntity<Object> entrance(@RequestParam int uid, @RequestParam int rid) {
		
		try {
			Room room = roomRepository.findByRid(rid);
			//
			Member member = memberRepository.findByUid(uid); // 참여자

            String queueName = "member." + Integer.toString(room.getUid());
			String message = room.getRoomName() + "에 " + member.getName() + "님이 들어왔습니다.";

			amqpTemplate.convertAndSend(queueName, message);

			//
			return new ResponseEntity<>(SUCCESS, HttpStatus.OK);			
		} catch (Exception e) {
			return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "미팅 퇴장")
	@GetMapping(path = "/exit")
	public ResponseEntity<Object> exit(@RequestParam int uid, @RequestParam int rid) {
		
		try {
			Room room = roomRepository.findByRid(rid);
			Member member = memberRepository.findByUid(uid); // 참여자

            String queueName = "member." + Integer.toString(room.getUid());
			String message = room.getRoomName() + "에 " + member.getName() + "님이 나갔습니다.";

			amqpTemplate.convertAndSend(queueName, message);
			
			return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "미팅 시작")
	@GetMapping(path = "/start")
	public ResponseEntity<Object> start(@RequestParam int rid) {		
		try {
			Room room = roomRepository.findByRid(rid);

            String roomName = "room." + Integer.toString(rid);
            String message = room.getRoomName() + "의 강의가 시작되었습니다.";
			amqpTemplate.convertAndSend(roomName, "", message);
			
			return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
		}
	}

}
