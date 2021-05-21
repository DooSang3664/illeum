package com.ssafy.pjt.dto.request;

import lombok.Data;

@Data
@SuppressWarnings("all")
public class UpdateMemberDto {
	private String email;
	private String password;
	private String name;
}
