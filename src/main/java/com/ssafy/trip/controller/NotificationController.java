package com.ssafy.trip.controller;

import java.util.*;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ssafy.trip.notification.model.dto.NotificationDto;
import com.ssafy.trip.notification.model.service.NotificationService;
import com.ssafy.trip.user.model.dto.UserDto;
import com.ssafy.trip.util.PageNavigation;

@RestController
//@Controller
@RequestMapping("/notification")
@CrossOrigin("*")
public class NotificationController {
	
	private static final Logger logger = LoggerFactory.getLogger(NotificationController.class); 
	
	private NotificationService notificationService;

	@Autowired
	public NotificationController(NotificationService notificationService) {
		super();
		this.notificationService = notificationService;
	}
	
//	@GetMapping("/list")
//	public ModelAndView list(@RequestParam Map<String, String> map) throws Exception {
//		ModelAndView mav = new ModelAndView();
//		List<NotificationDto> list = notificationService.listNotification(map);
//		PageNavigation pageNavigation = notificationService.makePageNavigation(map);
//		
//		mav.addObject("notifications", list);
//		mav.addObject("navigation", pageNavigation);
//		mav.addObject("pgno", map.get("pgno"));
//		mav.addObject("key", map.get("key"));
//		mav.addObject("word", map.get("word"));
//		mav.setViewName("notification/list");
//		return mav;
//	}
	
	@GetMapping(value = "/list")
	public ResponseEntity<?> list(@RequestBody Map<String, String> map) {
		logger.debug("notification list call : {}", map);
		Map<String, Object> result = new HashMap<>();
		try {
			List<NotificationDto> list = notificationService.listNotification(map);
			PageNavigation pageNavigation = notificationService.makePageNavigation(map);
			result.put("notification", list);
			result.put("navigation", pageNavigation);
			result.put("pgno", map.get("pgno"));
			result.put("key", map.get("key"));
			result.put("word", map.get("word"));
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
//	@GetMapping("/view")
//	public String view(@RequestParam("id") int id, @RequestParam Map<String, String> map, Model model) throws Exception {
//		NotificationDto notificationDto = notificationService.viewNotification(id);
//		notificationService.updateHit(id);
//		model.addAttribute("notification", notificationDto);
//		model.addAttribute("pgno", map.get("pgno"));
//		model.addAttribute("key", map.get("key"));
//		model.addAttribute("word", map.get("word"));
//		return "/notification/view";
//	}
	
	@GetMapping(value = "/view")
	public ResponseEntity<?> view(@RequestBody int id, Map<String, String> map){
		logger.debug("notification view id : {}", id);
		Map<String, Object> result = new HashMap<>();
		try {
			notificationService.updateHit(id);
			NotificationDto notificationDto = notificationService.viewNotification(id);
			result.put("notification", notificationDto);
			result.put("pgno", map.get("pgno"));
			result.put("key", map.get("key"));
			result.put("word", map.get("word"));
			return new  ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	
//	@GetMapping("/write")
//	public String write(@RequestParam Map<String, String> map, Model model) {
//		model.addAttribute("pgno", map.get("pgno"));
//		model.addAttribute("key", map.get("key"));
//		model.addAttribute("word", map.get("word"));
//		return "notification/write";
//	}
	
	@GetMapping(value ="/write")
	public ResponseEntity<?> write(@RequestBody Map<String, String> map) {
		logger.debug("notification write call ");
		Map<String, Object> result = new HashMap<>();
		try {
			result.put("pgno", map.get("pgno"));
			result.put("key", map.get("key"));
			result.put("word", map.get("word"));
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
//	@PostMapping("/write")
//	public String write(NotificationDto notificationDto, HttpSession session, RedirectAttributes redirectAttributes) throws Exception {
//		UserDto userDto = (UserDto) session.getAttribute("userinfo");
//		notificationDto.setUserId(userDto.getUserid());
//		
//		notificationService.writeNotification(notificationDto);
//		redirectAttributes.addAttribute("pgno", "1");
//		redirectAttributes.addAttribute("key", "");
//		redirectAttributes.addAttribute("word", "");
//		return "redirect:/notification/list";
//	}
	
	@PostMapping(value = "/write")
	public ResponseEntity<?> write(NotificationDto notificationDto, HttpSession session){
		logger.debug("notification write call : {} ", notificationDto);
		Map<String, Object> result = new HashMap<>();
		try {
			UserDto userDto = (UserDto) session.getAttribute("userinfo");
			notificationDto.setUserId(userDto.getUserid());
			
			notificationService.writeNotification(notificationDto);
			result.put("pgno",1);
			result.put("key", 1);
			result.put("word", 1);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
//	@GetMapping("/modify")
//	public String modify(@RequestParam("id") int id, HttpSession session, @RequestParam Map<String, String> map, Model model) throws Exception {
//		NotificationDto notificationDto = notificationService.viewNotification(id);
//		model.addAttribute("notification", notificationDto);
//		model.addAttribute("pgno", map.get("pgno"));
//		model.addAttribute("key", map.get("key"));
//		model.addAttribute("word", map.get("word"));
//		return "notification/modify";
//	}
	
	@GetMapping(value = "/modify")
	public ResponseEntity<?> modify(@RequestBody int id, HttpSession session, @RequestBody Map<String, String> map){
		logger.debug("notification modify call {} ", id);
		Map<String, Object> result = new HashMap<>();
		
		try {
			NotificationDto notificationDto = notificationService.viewNotification(id);
			result.put("notificationDto", notificationDto);
			result.put("pgno", map.get("pgno"));
			result.put("key", map.get("key"));
			result.put("word", map.get("word"));
			
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	
//	@PostMapping("/modify")
//	public String modify(NotificationDto notificationDto, @RequestParam Map<String, String> map,
//			RedirectAttributes redirectAttributes) throws Exception {
//		notificationService.modifyNotification(notificationDto);
//		redirectAttributes.addAttribute("pgno", map.get("pgno"));
//		redirectAttributes.addAttribute("key", map.get("key"));
//		redirectAttributes.addAttribute("word", map.get("word"));
//		return "redirect:/notification/list";
//	}
	
	@PutMapping(value="/modify")
	public ResponseEntity<?> modify(NotificationDto notificationDto, @RequestBody Map<String,String> map){
		logger.debug("modify notification : {}", notificationDto);
		Map<String, Object> result = new HashMap<>();
		try {
			notificationService.modifyNotification(notificationDto);
			result.put("pgno",1);
			result.put("key", 1);
			result.put("word", 1);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
//	@GetMapping("/delete")
//	public String delete(@RequestParam("id") int id, @RequestParam Map<String, String> map,
//			RedirectAttributes redirectAttributes) throws Exception {
//		notificationService.deleteNotification(id);
//		redirectAttributes.addAttribute("pgno", map.get("pgno"));
//		redirectAttributes.addAttribute("key", map.get("key"));
//		redirectAttributes.addAttribute("word", map.get("word"));
//		return "redirect:/notification/list";
//	}
	
	@DeleteMapping(value = "/delete")
	public ResponseEntity<?> delete(@RequestBody int id, @RequestBody Map<String,String> map){
		logger.debug("delete notification : {}", id);
		Map<String, Object> result = new HashMap<>();
		try {
			notificationService.deleteNotification(id);
			result.put("pgno",1);
			result.put("key", 1);
			result.put("word", 1);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
