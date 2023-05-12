package com.ssafy.trip.controller;

import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ssafy.trip.notification.model.dto.NotificationDto;
import com.ssafy.trip.notification.model.service.NotificationService;
import com.ssafy.trip.user.model.dto.UserDto;
import com.ssafy.trip.util.PageNavigation;

@Controller
@RequestMapping("/notification")
public class NotificationController {
	
	private NotificationService notificationService;

	public NotificationController(NotificationService notificationService) {
		super();
		this.notificationService = notificationService;
	}
	
	@GetMapping("/list")
	public ModelAndView list(@RequestParam Map<String, String> map) throws Exception {
		ModelAndView mav = new ModelAndView();
		List<NotificationDto> list = notificationService.listNotification(map);
		PageNavigation pageNavigation = notificationService.makePageNavigation(map);
		
		mav.addObject("notifications", list);
		mav.addObject("navigation", pageNavigation);
		mav.addObject("pgno", map.get("pgno"));
		mav.addObject("key", map.get("key"));
		mav.addObject("word", map.get("word"));
		mav.setViewName("notification/list");
		return mav;
	}
	
	@GetMapping("/view")
	public String view(@RequestParam("id") int id, @RequestParam Map<String, String> map, Model model) throws Exception {
		NotificationDto notificationDto = notificationService.viewNotification(id);
		notificationService.updateHit(id);
		model.addAttribute("notification", notificationDto);
		model.addAttribute("pgno", map.get("pgno"));
		model.addAttribute("key", map.get("key"));
		model.addAttribute("word", map.get("word"));
		return "/notification/view";
	}
	@GetMapping("/write")
	public String write(@RequestParam Map<String, String> map, Model model) {
		model.addAttribute("pgno", map.get("pgno"));
		model.addAttribute("key", map.get("key"));
		model.addAttribute("word", map.get("word"));
		return "notification/write";
	}
	
	@PostMapping("/write")
	public String write(NotificationDto notificationDto, HttpSession session, RedirectAttributes redirectAttributes) throws Exception {
		UserDto userDto = (UserDto) session.getAttribute("userinfo");
		notificationDto.setUserId(userDto.getId());
		
		notificationService.writeNotification(notificationDto);
		redirectAttributes.addAttribute("pgno", "1");
		redirectAttributes.addAttribute("key", "");
		redirectAttributes.addAttribute("word", "");
		return "redirect:/notification/list";
	}
	
	@GetMapping("/modify")
	public String modify(@RequestParam("id") int id, HttpSession session, @RequestParam Map<String, String> map, Model model) throws Exception {
		NotificationDto notificationDto = notificationService.viewNotification(id);
		model.addAttribute("notification", notificationDto);
		model.addAttribute("pgno", map.get("pgno"));
		model.addAttribute("key", map.get("key"));
		model.addAttribute("word", map.get("word"));
		return "notification/modify";
	}
	
	@PostMapping("/modify")
	public String modify(NotificationDto notificationDto, @RequestParam Map<String, String> map,
			RedirectAttributes redirectAttributes) throws Exception {
		notificationService.modifyNotification(notificationDto);
		redirectAttributes.addAttribute("pgno", map.get("pgno"));
		redirectAttributes.addAttribute("key", map.get("key"));
		redirectAttributes.addAttribute("word", map.get("word"));
		return "redirect:/notification/list";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("id") int id, @RequestParam Map<String, String> map,
			RedirectAttributes redirectAttributes) throws Exception {
		notificationService.deleteNotification(id);
		redirectAttributes.addAttribute("pgno", map.get("pgno"));
		redirectAttributes.addAttribute("key", map.get("key"));
		redirectAttributes.addAttribute("word", map.get("word"));
		return "redirect:/notification/list";
	}
}
