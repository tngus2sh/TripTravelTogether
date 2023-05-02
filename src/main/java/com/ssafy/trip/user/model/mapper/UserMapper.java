package com.ssafy.trip.user.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.user.model.dto.UserDto;

@Mapper
public interface UserMapper {
	
	int idCheck(String id) throws SQLException;
	void joinUser(UserDto userDto) throws SQLException;
	UserDto loginUser(Map<String, String> map) throws SQLException;
	void modifyUser(UserDto userDto) throws SQLException;
	void deleteUser(String id) throws SQLException;
	
}
