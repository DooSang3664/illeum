package com.ssafy.pjt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.pjt.dto.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
	Room findByRid(int rid);
	List<Room> findByUid(int uid);
	Room findByRoomName(String email);
	Long deleteByRid(int rid);
}

