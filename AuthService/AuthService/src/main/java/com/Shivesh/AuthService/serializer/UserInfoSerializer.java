package com.Shivesh.AuthService.serializer;

import org.apache.kafka.common.serialization.Serializer;

import com.Shivesh.AuthService.dto.UserInfoDto;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserInfoSerializer implements Serializer<UserInfoDto> {

	@Override
	public byte[] serialize(String arg0, UserInfoDto arg1) {
		// TODO Auto-generated method stub
		byte[] res = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			res = objectMapper.writeValueAsString(arg1).getBytes();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return res;
	}

}
