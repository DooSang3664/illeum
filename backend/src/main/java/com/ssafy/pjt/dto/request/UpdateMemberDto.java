package com.ssafy.pjt.dto.request;

import lombok.Data;

@Data
public class UpdateMemberDto {
	private String email;
	private String password;
	private String name;
	private String thumbnail;
}
