package com.ssafy.trip.plan.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ssafy.trip.plan.model.dto.PlaceDto;
import com.ssafy.trip.plan.model.dto.PlaceDtoList;
import com.ssafy.trip.plan.model.dto.PlanDto;
import com.ssafy.trip.plan.model.service.PlanService;
import com.ssafy.trip.user.model.dto.UserDto;
import com.ssafy.trip.util.PageNavigation;

@Controller
@RequestMapping("/plan")
public class PlanController {
	
	private final Logger logger = LoggerFactory.getLogger(PlanController.class);
	
	private PlanService planService;

	public PlanController(PlanService planService) {
		super();
		this.planService = planService;
	}
	
	@GetMapping("/list")
	public ModelAndView listPlan(@RequestParam Map<String, String> map) throws Exception {
		logger.debug("listPlan map : {}", map.get("pgno"));
		ModelAndView mav = new ModelAndView();
		List<PlanDto> list = planService.listPlan(map);
		PageNavigation pageNavigation = planService.makePageNavigation(map);
		mav.addObject("plans", list);
		mav.addObject("navigation", pageNavigation);
		mav.addObject("pgno", map.get("pgno"));
		mav.addObject("key", map.get("key"));
		mav.addObject("word", map.get("word"));
		mav.setViewName("plan/list");
		return mav;
	}
	
	@GetMapping("/write")
	public String writePlan(@RequestParam Map<String, String> map, Model model) {
		logger.debug("plan write parameter : {}", map);
		model.addAttribute("pgno", map.get("pgno"));
		model.addAttribute("key", map.get("key"));
		model.addAttribute("word", map.get("word"));
		return "plan/write";
	}
	
	@PostMapping("/write")
	public String writePlan(PlanDto planDto, @ModelAttribute PlaceDtoList placeDtoList, HttpSession session, RedirectAttributes redirectAttributes) throws SQLException {
		logger.debug("writePlan Post parameter : {} {}", planDto, placeDtoList);
		
		UserDto userDto = (UserDto) session.getAttribute("userinfo");
		System.out.println("sjfkd");
		planDto.setUserId(userDto.getId());
		planService.insertPlan(planDto);
		
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userDto.getId());
		map.put("title", planDto.getTitle());		
		int planId = planService.getPlanId(map);

		List<PlaceDto> list = placeDtoList.getPlaceList();
		logger.debug("test {}", list);
		for (PlaceDto placeDto : list) {
			logger.debug("placeDto : {}", placeDto);
			placeDto.setPlanId(planId);
			planService.insertPlace(placeDto);
		}
		
		redirectAttributes.addAttribute("pgno", "1");
		redirectAttributes.addAttribute("key", "");
		redirectAttributes.addAttribute("word", "");
		return "redirect:/plan/list";
	}
	
	@GetMapping("/view")
	public String view(@RequestParam("planid") int planId, @RequestParam Map<String, String> map, Model model) throws SQLException {
		logger.debug("veiw articleno : {}", planId);
		planService.updateHit(planId);
		PlanDto planDto = planService.listPlanOne(planId);
		List<PlaceDto> list = planService.getPlace(planId);
		List<PlaceDto> fastDistanceList = planService.selectFastDistancePlace(planId);
		planService.updateHit(planId);
		model.addAttribute("plan", planDto);
		model.addAttribute("places", list);
		model.addAttribute("fastPlaces", fastDistanceList);
		model.addAttribute("pgno", map.get("pgno"));
		model.addAttribute("key", map.get("key"));
		model.addAttribute("word", map.get("word"));
		return "plan/view";
	}

}
