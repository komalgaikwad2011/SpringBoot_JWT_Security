package com.examples.springSecurity.dto;

public class LoginResponseDto {

	
	private String accessToken;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public LoginResponseDto(String accessToken) {
		super();
		this.accessToken = accessToken;
	}
	
	
	
}
