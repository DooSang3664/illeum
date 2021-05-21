package com.ssafy.pjt.service;

import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pjt.dto.Room;
import com.ssafy.pjt.dto.response.findRoom;
import com.ssafy.pjt.repository.MemberRepository;

@Service
public class RoomService {

    @Autowired
    private MemberRepository repository;
	
	public findRoom conversion(Room room) throws SQLException{
		findRoom findroom = new findRoom();
		findroom.setRid(room.getRid());
		findroom.setUid(room.getUid());
		if (room.getRoomName() != null)
			findroom.setRoom_name(room.getRoomName());
		if (room.getRoomName() != null)
			findroom.setRoom_name(room.getRoomName());
		if (room.getRoomPassword() != null)
			findroom.setRoom_password(room.getRoomPassword());
		if (room.getRoomState() != null)
			findroom.setRoom_state(room.getRoomState());
		if (room.getRoomType() != null)
			findroom.setRoom_type(room.getRoomType());
		if (room.getStartTime() != null)
			findroom.setStart_time(Date.from(room.getStartTime().atZone(ZoneId.systemDefault()).toInstant()));
		if (room.getEndTime() != null)
			findroom.setEnd_time(Date.from(room.getEndTime().atZone(ZoneId.systemDefault()).toInstant()));

		findroom.setFounder(repository.findByUid(room.getUid()).getName());
		
		
		return findroom;
	}
}
