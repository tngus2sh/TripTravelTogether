package com.ssafy.trip.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.trip.plan.model.dto.GoodPlanDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.board.model.dto.BoardParameterDto;
import com.ssafy.trip.plan.model.dto.PlaceDto;
import com.ssafy.trip.plan.model.dto.PlanDto;
import com.ssafy.trip.plan.model.dto.RegisterPlanRequest;
import com.ssafy.trip.plan.model.service.PlanService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/plan")
@Api
public class PlanController {
	
	private static final Logger logger = LoggerFactory.getLogger(PlanController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private PlanService planService;

	public PlanController(PlanService planService) {
		super();
		this.planService = planService;
	}
	
	@ApiOperation(value = "여행계획 목록", notes = "여행 계획 목록을 반환한다", response = List.class)
	@GetMapping("/list")
	public ResponseEntity<List<PlanDto>> listPlan(
			@ApiParam(value = "여행게획 목록을 얻기위한 부가정보.", required = true) BoardParameterDto boardParameterDto
			) {
		HttpStatus status = null;
		List<PlanDto> list = null;
		try {
			list = planService.getPlanList();
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.getMessage();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<List<PlanDto>>(list, status);
	}
	
	@ApiOperation(value = "여행계획 작성", notes = "여행계획과 관광지를 데이터베이스에 넣는다.")
	@PostMapping
	@Transactional
	public ResponseEntity<Map<String, Object>> registPlan(
			@RequestBody RegisterPlanRequest request
			) {

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		
		logger.debug("planDto : {}", request.getPlanDto());
		logger.debug("placeDtoList: {}", request.getPlaceDtoList());
		
		try {
			planService.registPlan(request.getPlanDto());
			Map<String, Object> map = new HashMap<>();
			map.put("userId", request.getPlanDto().getUserId());
			map.put("title", request.getPlanDto().getTitle());
			int planId = 0;
			planId = planService.getPlanId(map);
			List<PlaceDto> list = request.getPlaceDtoList();
			logger.debug("test {}", list);
			for (PlaceDto placeDto : list) {
				logger.debug("placeDto : {}", placeDto);
				placeDto.setPlanId(planId);
				planService.registPlace(placeDto);
			}
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.getMessage();
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}


		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "한 개의 여행계획", notes = "해당하는 id의 여행 계획을 반환한다")
	@GetMapping("/{planId}")
	public ResponseEntity<?> listPlanOne(
			@PathVariable("planId") @ApiParam(value = "여행계획의 id", required = true) int planId
			) {
		Map<String, Object> planMap = new HashMap<>();
		HttpStatus status = null;
		try {
			planService.updateHit(planId);
			PlanDto planDto = planService.getPlanOne(planId);
			List<PlaceDto> list = planService.getPlaceList(planId);
			List<PlaceDto> fastDistanceList = planService.getFastDistancePlace(planId);
			planMap.put("plan", planDto);
			planMap.put("places", list);
			planMap.put("fastPlaces", fastDistanceList);
			planMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.getMessage();
			planMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(planMap, status);
	}
	
	@ApiOperation(value = "여행 계획 수정", notes = "해당하는 id의 여행 계획을 수정한다")
	@PutMapping
	@Transactional
	public ResponseEntity<Map<String, Object>> modifyPlan(
			@RequestBody RegisterPlanRequest request
			) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			planService.modifyPlan(request.getPlanDto());
			int planId = request.getPlanDto().getId();

			List<PlaceDto> list = request.getPlaceDtoList();
			logger.debug("test {}", list);

			planService.deletePlace(planId);
			for (PlaceDto placeDto : list) {
				logger.debug("placeDto : {}", placeDto);
				placeDto.setPlanId(planId);
				planService.registPlace(placeDto);
			}

			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.getMessage();
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "여행 계획 삭제", notes = "해당하는 id의 여행 계획을 삭제한다")
	@DeleteMapping("/{planId}")
	@Transactional
	public ResponseEntity<?> deletePlan(
			@PathVariable("planId") @ApiParam(value = "삭제할 plan의 id", required = true) int planId
			)  {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			planService.deletePlan(planId);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.getMessage();
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "여행 계획 좋아요 추가", notes = "사용자가 좋아요 누른 해당 여행 계획 id를 넣는다.")
	@PostMapping("/good")
	@Transactional
	public ResponseEntity<Map<String, Object>> registGoodPlan (
			@RequestBody @ApiParam(value = "저장할 userId 와 planId", required = true)
	GoodPlanDto goodPlanDto
			) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			Map<String , Object> param = new HashMap<>();
			param.put("userId", goodPlanDto.getUserId());
			param.put("planId", goodPlanDto.getPlanId());
			int cnt = planService.getGoodPlanNum(param);

			if (cnt == 0)
				planService.registGoodPlan(goodPlanDto);

			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (SQLException e) {
			e.getMessage();
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String , Object>>(resultMap, status);
	}

	@ApiOperation(value = "사용자에게 해당 여행 계획이 있는지 확인", notes = "사용자가 해당 여행계획에 좋아요를 눌렀는지 확인한다.")
	@GetMapping("/good/{userId}/{planId}")
	public ResponseEntity<Map<String , Object>> getGoodPlanNum (
		@PathVariable @ApiParam(value = "사용자 id") String userId,
		@PathVariable @ApiParam(value = "여행 계획 id") int planId
	) {

		logger.debug("getGoodPlan Num params : {} {}", userId, planId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		param.put("planId", planId);
		try {
			int count = planService.getGoodPlanNum(param);
			if (count > 0) {
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (SQLException e) {
			e.getMessage();
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "사용자의 여행 계획 목록 불러오기", notes = "사용자의 좋아요한 여행 계획 목록 반환.")
	@GetMapping("/good/{userId}")
	public ResponseEntity<List<Map<String , Object>>> getGoodPlan (
			@PathVariable @ApiParam(value = "사용자 id") String userId
	) {

		logger.debug("getGoodPlan params : {} {}", userId);
		List<Map<String , Object>> map = new ArrayList<>();
		HttpStatus status = null;

		try {
			map = planService.getGoodPlan(userId);
			if (map != null) {
				status = HttpStatus.ACCEPTED;
			} else {
				status = HttpStatus.ACCEPTED;
			}
		} catch (SQLException e) {
			e.getMessage();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<List<Map<String , Object>>>(map, status);
	}

	@ApiOperation(value = "등록된 좋아요 여행 계획 삭제", notes = "사용자가 해당 여행계획의 좋아요를 취소하면 삭제한다.")
	@DeleteMapping("/good/{userId}/{planId}")
	@Transactional
	public ResponseEntity<Map<String , Object>> deleteGoodPlan (
			@PathVariable @ApiParam(value = "사용자 id", required = true) String userId,
			@PathVariable @ApiParam(value = "여행계획 id", required = true) int planId
	) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		logger.debug("deleteGoodPlan param : {} {}", userId, planId);

		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		param.put("planId", planId);
		try {
			planService.deleteGoodPlan(param);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (SQLException e) {
			e.getMessage();
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String , Object>>(resultMap, status);
	}
}
