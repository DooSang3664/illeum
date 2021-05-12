package com.ssafy.pjt.dto.request;

import lombok.Data;

@Data
@SuppressWarnings("all")
public class LoginDto {
	private String email;
	private String password;
}
