package com.ssafy.trip.user.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.user.model.dto.UserDto;

@Mapper
public interface UserMapper {
	
	int idCheck(String userId) throws SQLException;
	void joinUser(UserDto userDto) throws SQLException;
	void modifyUser(UserDto userDto) throws SQLException;
	void modifyUserPw(Map<String, String> map) throws SQLException;
	void deleteUser(String userId) throws SQLException;
	int getPassword(Map<String, String> map) throws SQLException;
	
	
	// new
	public UserDto login(UserDto userDto) throws SQLException;
	public UserDto userInfo(String userId) throws SQLException;
	public UserDto userTotalInfo(String userId) throws SQLException;
	public void saveRefreshToken(Map<String, String> map) throws SQLException;
	public Object getRefreshToken(String userId) throws SQLException;
	public void deleteRefreshToken(Map<String, String> map) throws SQLException;
	public String findId(Map<String, String> nameEmail) throws SQLException;
}
