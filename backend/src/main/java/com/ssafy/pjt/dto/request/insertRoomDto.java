package com.ssafy.pjt.dto.request;

import java.time.LocalDateTime;

import lombok.Data;

@Data
@SuppressWarnings("all")
public class insertRoomDto {
	private int uid;
	private String room_name;
	private String room_password;
	private LocalDateTime start_time;
	private LocalDateTime end_time;
	private String room_state;
	private String room_type;
}
