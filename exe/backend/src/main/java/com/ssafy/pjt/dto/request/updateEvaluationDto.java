package com.ssafy.pjt.dto.request;

import java.time.LocalDateTime;
import lombok.Data;

@Data
@SuppressWarnings("all")
public class updateEvaluationDto {
	private Integer vid;
	private Integer eid;
	private LocalDateTime eval_date;	
	private Integer attention;
	private Integer participation;
	private Integer distracted;
	private Integer asleep;
	private Integer afk;
	private LocalDateTime attend_time;
	private Integer ranking;
	private String attend;
}
