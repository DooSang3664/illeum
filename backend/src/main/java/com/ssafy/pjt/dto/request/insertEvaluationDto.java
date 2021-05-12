package com.ssafy.pjt.dto.request;

import java.time.LocalDateTime;

import lombok.Data;

@Data
@SuppressWarnings("all")
public class insertEvaluationDto {
	private int uid;
	private int rid;
	private LocalDateTime now;
}
