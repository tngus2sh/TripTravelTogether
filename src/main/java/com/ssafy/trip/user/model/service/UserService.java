package com.ssafy.trip.user.model.service;

import java.util.Map;

import com.ssafy.trip.user.model.dto.UserDto;

public interface UserService {
	
	int idCheck(String id) throws Exception;
	void joinUser(UserDto userDto) throws Exception;
	UserDto loginUser(Map<String, String> map) throws Exception;
	void modifyUser(UserDto userDto) throws Exception;
	void modifyUserPw(Map<String, String> map) throws Exception;
	void deleteUser(String id) throws Exception;
	int getPassword(Map<String, String> map) throws Exception;
	
	
	// new
	public UserDto login(UserDto userDto) throws Exception;
	public UserDto userInfo(String userId) throws Exception;
	public void saveRefreshToken(String userId, String refreshToken) throws Exception;
	public Object getRefreshToken(String userId) throws Exception;
	public void deleRefreshToken(String userId) throws Exception;
	
}
