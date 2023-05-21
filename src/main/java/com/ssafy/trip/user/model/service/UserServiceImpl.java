package com.ssafy.trip.user.model.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.trip.controller.UserController;
import com.ssafy.trip.user.model.dto.UserDto;
import com.ssafy.trip.user.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private SqlSession sqlSession;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
		// 비밀번호 인코딩 후 회원가입
		userDto.setUserPwd(passwordEncoder.encode(userDto.getUserPwd()));
		userMapper.joinUser(userDto);
	}

	@Override
	public void modifyUser(UserDto userDto) throws Exception {
		// dto 비밀번호가 null값이 아니라면 인코딩해서 새로 저장
		if (userDto.getUserPwd() != null) userDto.setUserPwd(passwordEncoder.encode(userDto.getUserPwd()));
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
	public int getPassword(Map<String, String> map) throws Exception {
		return userMapper.getPassword(map);
	}

	@Override
	public UserDto login(UserDto userDto) throws Exception {
		if (userDto.getUserId() == null || userDto.getUserPwd() == null) {
			return null;
		}
//		return sqlSession.getMapper(UserMapper.class).login(userDto);
		// userInfo에서 가져온 비밀번호(암호화됨)와 지금 입력받은 비밀번호가 맞는지 확인
		UserDto checkUser = userMapper.userTotalInfo(userDto.getUserId());
		if (checkUser != null) {
			String encodePwd = checkUser.getUserPwd();
			if (passwordEncoder.matches(userDto.getUserPwd(), encodePwd)) {
				// 암호화된 비밀번호로 pw 정보 변경 후 로그인
				userDto.setUserPwd(encodePwd);
				return userMapper.login(userDto);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public UserDto userInfo(String userId) throws Exception {
		return sqlSession.getMapper(UserMapper.class).userInfo(userId);
	}

	@Override
	public UserDto userTotalInfo(String userId) throws Exception {
		return sqlSession.getMapper(UserMapper.class).userTotalInfo(userId);
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
	public void deleteRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		sqlSession.getMapper(UserMapper.class).deleteRefreshToken(map);
	}

}
