package com.Shivesh.AuthService.dto;

import com.Shivesh.AuthService.entities.UserInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserInfoDto extends UserInfo {

	private String userName;
	private String lastName;
	private Long phoneNumber;
	private String email;
}
