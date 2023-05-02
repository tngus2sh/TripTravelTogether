package com.ssafy.trip.hotplace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.trip.hotplace.model.service.HotplaceService;

@Controller
@RequestMapping("/hotplace")
public class HotplaceController {
	
	private HotplaceService hotplaceService;

	public HotplaceController(HotplaceService hotplaceService) {
		super();
		this.hotplaceService = hotplaceService;
	}

	@GetMapping("/list")
	public String listHotplace() {
		return "hotplace/list";
	}
	
}
