package com.ssafy.trip.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.trip.attraction.model.dto.SidoDto;
import com.ssafy.trip.attraction.model.service.AttractionService;

@Controller
@RequestMapping("/region")
public class AttractionController {
	
	private AttractionService attractionService;

	public AttractionController(AttractionService attractionService) {
		super();
		this.attractionService = attractionService;
	}
	
	@GetMapping("/attraction")
	public String region(Model model) {
		List<SidoDto> list;
		try {
			list = attractionService.listSido();
			model.addAttribute("sidos", list);
			return "attraction/region";
		} catch (SQLException e) {
			e.printStackTrace();
			model.addAttribute("msg", "시도 불러오기 실패");
			return "redirect:/";
		}
	}
}
