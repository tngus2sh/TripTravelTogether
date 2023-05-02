package com.ssafy.trip.notification.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.trip.notification.model.service.NotificationService;

@Controller
@RequestMapping("/notification")
public class NotificationController {
	
	private NotificationService notificationService;

	public NotificationController(NotificationService notificationService) {
		super();
		this.notificationService = notificationService;
	}
	
	@GetMapping("/list")
	public String list() {
		return "notification/list";
	}
	
}
