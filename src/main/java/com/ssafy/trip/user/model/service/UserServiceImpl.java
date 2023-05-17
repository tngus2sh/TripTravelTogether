package com.ssafy.trip.user.model.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.trip.controller.UserAPIController;
import com.ssafy.trip.user.model.dto.UserDto;
import com.ssafy.trip.user.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private SqlSession sqlSession;
	
	public UserServiceImpl(UserMapper userMapper) {
		super();
		this.userMapper = userMapper;
	}

	@Override
	public int idCheck(String id) throws Exception {
		return userMapper.idCheck(id);
	}

	@Override
	@Transactional
	public void joinUser(UserDto userDto) throws Exception {
		userMapper.joinUser(userDto);
	}

	@Override
	public UserDto loginUser(Map<String, String> map) throws Exception {
		return userMapper.loginUser(map);
	}

	@Override
	public void modifyUser(UserDto userDto) throws Exception {
		userMapper.modifyUser(userDto);
	}

	@Override
	public void modifyUserPw(Map<String, String> map) throws Exception {
		userMapper.modifyUserPw(map);
	}
	
	@Override
	public void deleteUser(String id) throws Exception {
		userMapper.deleteUser(id);
	}

	@Override
	public String getPassword(String id) throws Exception {
		return userMapper.getPassword(id);
	}

	@Override
	public UserDto login(UserDto userDto) throws Exception {
		if (userDto.getUserId() == null || userDto.getUserPwd() == null) {
			return null;
		}
		return sqlSession.getMapper(UserMapper.class).login(userDto);
	}

	@Override
	public UserDto userInfo(String userId) throws Exception {
		return sqlSession.getMapper(UserMapper.class).userInfo(userId);
	}

	@Override
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", refreshToken);
		sqlSession.getMapper(UserMapper.class).saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userId) throws Exception {
		return sqlSession.getMapper(UserMapper.class).getRefreshToken(userId);
	}

	@Override
	public void deleRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		sqlSession.getMapper(UserMapper.class).deleteRefreshToken(map);
	}

}
