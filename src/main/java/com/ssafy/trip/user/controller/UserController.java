package com.ssafy.trip.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ssafy.trip.attraction.controller.AttractionApiController;
import com.ssafy.trip.user.model.dto.UserDto;
import com.ssafy.trip.user.model.service.UserService;
import com.ssafy.trip.util.TempKey;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(AttractionApiController.class);

	private UserService userService;
	
	@Autowired
	public JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String from;

	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@GetMapping("join")
	public String join() {
		return "user/join";
	}
	
	@GetMapping("/{userid}")
	@ResponseBody
	public String idCheck(@PathVariable("userid") String userId) throws Exception {
		int cnt = userService.idCheck(userId);
		return cnt + "";
	}
	
	@PostMapping("join")
	public String join(UserDto userDto, RedirectAttributes redirect) throws Exception {
		userService.joinUser(userDto);
		redirect.addFlashAttribute("msg", "회원 가입이 완료되었습니다.");
		return "redirect:/user/login";
	}

	@GetMapping("/login")
	public String login() {
		return "user/login";
	}

	@PostMapping("/login")
	public String login(@RequestParam Map<String, String> map,
			@RequestParam(name = "saveid", required = false) String saveid, Model model, HttpSession session,
			HttpServletResponse response) throws Exception {
		UserDto userDto = userService.loginUser(map);
		if (userDto != null) {
			session.setAttribute("userinfo", userDto);

			Cookie cookie = new Cookie("user_id", map.get("id"));
			cookie.setPath("/");
			if ("ok".equals(saveid)) {
				cookie.setMaxAge(60 * 60 * 24 * 365 * 40);
			} else {
				cookie.setMaxAge(0);
			}

			response.addCookie(cookie);
			return "redirect:/";
		} else {
			model.addAttribute("msg", "아이디 또는 비밀번호 확인 후 다시 로그인하세요!");
			return "user/login";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@PostMapping("/modify")
	@Transactional
	public String modifyUser(@RequestParam Map<String, String> map, RedirectAttributes rttr) throws Exception {
		logger.debug("user modify map : {}", map);
		UserDto userDto  = new UserDto();
		userDto.setId(map.get("update-id"));
		userDto.setPassword(map.get("update-password"));
		userDto.setName(map.get("update-name"));
		userDto.setGrade(map.get("update-grade"));
		userDto.setEmailId(map.get("update-email-id"));
		userDto.setEmailDomain(map.get("update-email-domain"));
		logger.debug("user modify userDto {}", userDto);

		userService.modifyUser(userDto);
		
		rttr.addFlashAttribute("msg", "회원정보가 수정 되었습니다.");
		return "redirect:/";
	}
	
	@GetMapping("/delete")
	public String deleteUser(HttpSession session) throws Exception {
		UserDto userDto = (UserDto) session.getAttribute("userinfo");
		session.invalidate();
		userService.deleteUser(userDto.getId());
		return "redirect:/";
	}
	
	@PostMapping("/mail")
	@Transactional
	public String sendMail(@RequestParam Map<String, String> map, RedirectAttributes redirectAttributes) throws Exception {
		logger.debug("sendmail parameter : {}", map);
		System.out.println(map.toString());
		String userId = map.get("search-id");
		String emailId = map.get("search-email-id");
		String emailDomain = map.get("search-email-domain");
		String to = emailId + "@" + emailDomain;
		
		String password = userService.getPassword(userId);
		
		if(password != null) {
			String tempPw = new TempKey().getKey(10, false); // 임시 비밀번호 발급
			Map<String, String> userMap = new HashMap<>();
			userMap.put("id", userId);
			userMap.put("password", tempPw);
			userService.modifyUserPw(userMap);
			
			SimpleMailMessage simpleMessage = new SimpleMailMessage();
			simpleMessage.setFrom(from);
			simpleMessage.setTo(to);
			simpleMessage.setSubject(" [TTT] 비밀번호 발급 ");			
			simpleMessage.setText(" 임시 비밀번호 : " + tempPw);
			javaMailSender.send(simpleMessage);
		} else {
			redirectAttributes.addFlashAttribute("msg", "아이디를 다시 설정해주세요.");
		}
		
		return "redirect:/user/login";
	}
	
}
