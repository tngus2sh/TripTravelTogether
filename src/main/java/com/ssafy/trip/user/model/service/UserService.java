package com.ssafy.trip.user.model.service;

import java.util.Map;

import com.ssafy.trip.user.model.dto.UserDto;

public interface UserService {
	
	int idCheck(String id) throws Exception;
	void joinUser(UserDto userDto) throws Exception;
	void modifyUser(UserDto userDto) throws Exception;
	void modifyUserPw(Map<String, String> map) throws Exception;
	void deleteUser(String id) throws Exception;
	int getPassword(Map<String, String> map) throws Exception;
	
	
	// new
	UserDto login(UserDto userDto) throws Exception;
	UserDto userTotalInfo(String userId) throws Exception;
	UserDto userInfo(String userId) throws Exception;
	void saveRefreshToken(String userId, String refreshToken) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleteRefreshToken(String userId) throws Exception;
	String findId(Map<String, String> nameEmail) throws Exception;
}
