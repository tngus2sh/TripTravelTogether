package com.ssafy.trip.user.model.service;

import java.sql.SQLException;
import java.util.Map;

import com.ssafy.trip.user.model.dto.UserDto;

public interface UserService {
	
	int idCheck(String id) throws Exception;
	void joinUser(UserDto userDto) throws Exception;
	UserDto loginUser(Map<String, String> map) throws Exception;
	void modifyUser(UserDto userDto) throws Exception;
	void deleteUser(String id) throws Exception;
}
