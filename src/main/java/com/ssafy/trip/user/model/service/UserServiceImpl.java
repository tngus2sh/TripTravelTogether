package com.ssafy.trip.user.model.service;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.trip.user.model.dto.UserDto;
import com.ssafy.trip.user.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

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
		if (userDto.getUserid() == null || userDto.getUserpwd() == null) {
			return null;
		}
		return sqlSession.getMapper(UserMapper.class).login(userDto);
	}

	@Override
	public UserDto userInfo(String userid) throws Exception {
		return sqlSession.getMapper(UserMapper.class).userInfo(userid);
	}

}
