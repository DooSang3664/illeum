package com.ssafy.pjt.dto.request;

import lombok.Data;

@Data
@SuppressWarnings("all")
public class insertRoomEvaluationDto {
	private int rid;
	private int isLate;
	private int isAbsent;
	private int isAttendFirst;
	private int isChatFirst;
}
