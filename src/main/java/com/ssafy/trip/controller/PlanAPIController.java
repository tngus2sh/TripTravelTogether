package com.ssafy.trip.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.plan.model.dto.PlaceDto;
import com.ssafy.trip.plan.model.dto.PlaceDtoList;
import com.ssafy.trip.plan.model.dto.PlanDto;
import com.ssafy.trip.plan.model.service.PlanService;
import com.ssafy.trip.user.model.dto.UserDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/plan")
@Api
public class PlanAPIController {
	
	private static final Logger logger = LoggerFactory.getLogger(PlanAPIController.class);
	private static final String SUCCESS = "success";
	
	@Autowired
	private PlanService planService;

	public PlanAPIController(PlanService planService) {
		super();
		this.planService = planService;
	}
	
	@ApiOperation(value = "여행계획 목록", notes = "여행 계획 목록을 반환한다", response = List.class)
	@GetMapping
	public ResponseEntity<?> listPlan() throws Exception{
		List<PlanDto> list = planService.getPlanList();
		return new ResponseEntity<List<PlanDto>>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "여행계획 작성", notes = "여행계획과 관광지를 데이터베이스에 넣는다.")
	@PostMapping
	public ResponseEntity<Map<String, Object>> registPlan(
			@RequestBody @ApiParam(value = "여행계획", required = true) PlanDto planDto, 
			@RequestBody @ApiParam(value = "여행할 관광지 리스트", required = true) PlaceDtoList placeDtoList
			) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		// 사용자 id를 plan에 넣어주기
		UserDto userDto = new UserDto(); // jwt로 수정
		userDto.setUserId("ssafy");
		planDto.setUserId(userDto.getUserId());
		
		planService.registPlan(planDto);
		Map<String, Object> map = new HashMap<>();
		map.put("userId", planDto.getUserId());
		map.put("title", planDto.getTitle());
		int planId = planService.getPlanId(map);
		
		List<PlaceDto> list = placeDtoList.getPlaceList();
		logger.debug("test {}", list);
		for (PlaceDto placeDto : list) {
			logger.debug("placeDto : {}", placeDto);
			placeDto.setPlanId(planId);
			planService.registPlace(placeDto);
		}
		
		resultMap.put("message", SUCCESS);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
	
	@ApiOperation(value = "한 개의 여행계획", notes = "해당하는 id의 여행 계획을 반환한다")
	@GetMapping("/{planId}")
	public ResponseEntity<?> listPlanOne(
			@PathVariable("planId") @ApiParam(value = "여행계획의 id", required = true) int planId
			) throws Exception {
		Map<String, Object> planMap = new HashMap<>();
		planService.updateHit(planId);
		PlanDto planDto = planService.getPlanOne(planId);
		List<PlaceDto> list = planService.getPlaceList(planId);
		List<PlaceDto> fastDistanceList = planService.getFastDistancePlace(planId);
		planMap.put("plan", planDto);
		planMap.put("places", list);
		planMap.put("fastPlaces", fastDistanceList);
		return new ResponseEntity<Map<String, Object>>(planMap, HttpStatus.OK);
	}
	
	@ApiOperation(value = "여행 계획 수정", notes = "해당하는 id의 여행 계획을 수정한다")
	@PutMapping
	public ResponseEntity<?> modifyPlan(
			@RequestBody @ApiParam(value = "여행계획", required = true) PlanDto planDto,
			@RequestBody @ApiParam(value = "여행할 관광지 리스트", required = true) PlaceDtoList placeDtoList
			) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		planService.modifyPlan(planDto);
		
		List<PlaceDto> list = placeDtoList.getPlaceList();
		logger.debug("test {}", list);
		for (PlaceDto placeDto : list) {
			logger.debug("placeDto : {}", placeDto);
			planService.modifyPlace(placeDto);
		}
		
		resultMap.put("message", SUCCESS);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
	
	@ApiOperation(value = "여행 계획 삭제", notes = "해당하는 id의 여행 계획을 삭제한다")
	@DeleteMapping("/{planId}")
	public ResponseEntity<?> deletePlan(
			@PathVariable("planId") @ApiParam(value = "삭제할 plan의 id", required = true) int planId
			) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		planService.deletePlan(planId);
		resultMap.put("message", SUCCESS);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
	
}
