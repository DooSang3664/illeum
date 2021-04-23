package com.ssafy.pjt.dto.request;

import lombok.Data;

@Data
public class SignUpDto {
	private String email;
	private String password;
	private String name;
	private String role;
	private String thumbnail;
}
