package com.ssafy.trip.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.user.model.dto.UserDto;
import com.ssafy.trip.user.model.service.JwtServiceImpl;
import com.ssafy.trip.user.model.service.UserService;
import com.ssafy.trip.util.TempKey;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = { "*" })
@Api("사용자 컨트롤러 API V1")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private JwtServiceImpl jwtService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	public JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String from;
	
	@ApiOperation(value = "아이디 찾기", response = Map.class)
	@GetMapping("/{userId}")
	public ResponseEntity<String> idCheck(
			@PathVariable("userId") @ApiParam(value = "사용자 아이디") String userId
			) throws Exception {
		int cnt = userService.idCheck(userId);
		return new ResponseEntity<String>(cnt + "", HttpStatus.OK);
	}
	
	@ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody @ApiParam(value="로그인 시 필요한 회원정보(아이디, 비밀번호).", required=true) UserDto userDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		UserDto loginUser = null;
		try {
			loginUser = userService.login(userDto);
			logger.debug("loginUser : {}", loginUser);
			if (loginUser != null) {
				String accessToken = jwtService.createAccessToken("userId", loginUser.getUserId());
				String refreshToken = jwtService.createRefreshToken("userId", loginUser.getUserId());
				userService.saveRefreshToken(userDto.getUserId(), refreshToken);
				logger.debug("로그인 accessToken 토큰정보 : {}", accessToken);
				logger.debug("로그인 refreshToken 토큰정보 : {}", refreshToken);
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}			
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping(value = "info/{userId}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userId") @ApiParam(value="인증할 회원의 아이디.", required = true) String userId,
			HttpServletRequest request) {
		logger.debug("userid: {}", userId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtService.checkToken(request.getHeader("access-token"))) { // header에서 access-token 추출
			logger.info("사용 가능한 토큰");
			try {
				// 로그인 사용자 정보
				UserDto userDto = userService.userInfo(userId);
				resultMap.put("userInfo", userDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다.", response = Map.class)
	@GetMapping("/logout/{userId}")
	public ResponseEntity<?> removeToken(@PathVariable("userId") String userId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			userService.deleteRefreshToken(userId);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "회원 탈퇴", notes = "회원 정보를 담은 Token과 DB를 제거한다.", response = Map.class)
	@GetMapping("/resign/{userId}")
	public ResponseEntity<?> removeUser(@PathVariable("userId") String userId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			userService.deleteRefreshToken(userId);
			userService.deleteUser(userId);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody UserDto userDto, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refresh-token");
		logger.debug("token : {}, userDto : {}", token, userDto);
		if (jwtService.checkToken(token)) { // 리프레시 토큰이 남아있는 상태
			if (token.equals(userService.getRefreshToken(userDto.getUserId()))) { // 근데 데이터베이스에 있는 값과 같다면 이 사람은 로그인 한 사람
				String accessToken = jwtService.createAccessToken("userId", userDto.getUserId());
				logger.debug("token : {}", accessToken);
				logger.debug("정상적으로 액세스토큰 재발급!!");
				resultMap.put("access-token", accessToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			}
		} else {
			logger.debug("리프레쉬토큰도 사용불가");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "회원정보 수정", notes = "회원 정보 수정 후 결과 메세지를 반환한다.", response = Map.class)
	@PutMapping("/modify")
	public ResponseEntity<?> modifyUser(
			@RequestBody @ApiParam(value = "수정할 회원 정보", required = true) UserDto userDto
			) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		
		try {
			userService.modifyUser(userDto);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.getMessage();
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "회원가입", notes = "회원 정보 등록 후 결과 메세지를 반환한다.", response = Map.class)
	@PostMapping("/join")
	@Transactional
	public ResponseEntity<Map<String, Object>> join(
			@RequestBody @ApiParam(value = "가입할 회원 정보", required = true) UserDto userDto
			) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		
		logger.debug("join called : {}", userDto);
		
		try {
			userService.joinUser(userDto);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.getMessage();
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.debug("회원 가입 완료");
		
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "비밀번호 찾기", notes = "아이디에 해당되는 이메일로 임시 비밀번호를 발급하고, 결과 메세지를 반환한다.", response = Map.class)
	@PostMapping("/find")
	@Transactional
	public ResponseEntity<Map<String, Object>> sendMail(
			@RequestBody @ApiParam(value = "찾을 아이디와 이메일", required = true) Map<String, String> map
			) {
		logger.debug("sendmail parameter : {}", map);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		
		String userId = map.get("userId");
		String emailId = map.get("emailId");
		String emailDomain = map.get("emailDomain");
		String to = emailId + "@" + emailDomain;

		logger.debug("userid : {}", userId);
		int password = -1;
		try {
			password = userService.getPassword(map);

			logger.debug("password {}", password);

			if(password > 0) {
				String tempPw = new TempKey().getKey(10, false); // 임시 비밀번호 발급
				Map<String, String> userMap = new HashMap<>();
				userMap.put("userId", userId);
				userMap.put("userPwd", tempPw);
				userService.modifyUserPw(userMap);

				SimpleMailMessage simpleMessage = new SimpleMailMessage();
				simpleMessage.setFrom(from);
				simpleMessage.setTo(to);
				simpleMessage.setSubject(" [TTT] 비밀번호 발급 ");
				simpleMessage.setText(" 임시 비밀번호 : " + tempPw + "\n"
						+ "*로그인 후 비밀번호 변경 필수*");
//				javaMailSender.send(simpleMessage);

				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			e.getMessage();
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
}
