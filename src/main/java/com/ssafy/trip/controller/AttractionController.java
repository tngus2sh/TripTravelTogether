package com.ssafy.trip.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.attraction.model.dto.AttractionInfoDto;
import com.ssafy.trip.attraction.model.dto.GunguDto;
import com.ssafy.trip.attraction.model.dto.SidoDto;
import com.ssafy.trip.attraction.model.service.AttractionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/attraction")
@CrossOrigin(origins = { "*" })
@Api("attraction 컨트롤러 API")
public class AttractionController {
	
	private static final Logger logger = LoggerFactory.getLogger(AttractionController.class);

	private AttractionService attractionService;

	public AttractionController(AttractionService attractionService) {
		super();
		this.attractionService = attractionService;
	}
	
	@ApiOperation(value = "시도 불러오기", notes = "시도 리스트를 반환한다.", response = List.class)
	@GetMapping("/sido")
	public ResponseEntity<?> sidoList() throws SQLException {
		logger.debug("sidoList call");
		List<SidoDto> list = attractionService.listSido();
		logger.debug("sido list : {}", list);
		return new ResponseEntity<List<SidoDto>>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "군구 불러오기", notes = "구군 리스트를 반환한다", response = List.class)
	@GetMapping(value="/gugun")
	public ResponseEntity<?> gunguList(@RequestParam("sido") @ApiParam(value = "선택한 시도.", required = true)  int sidoCode) {
		logger.debug("gungu list, sidoCode : {}", sidoCode);
		try {
			List<GunguDto> list = attractionService.listGungu(sidoCode);
			logger.debug("gungu list : {}", list);
			return new ResponseEntity<List<GunguDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
//	@GetMapping(value = "/list/{sidoCode}/{gugunCode}/{contentTypeId}")
//	@ApiOperation(value = "관광지 검색", notes = "관광지 리스트를 불러온다", response = List.class)
//	@GetMapping(value = "/{sidoCode}/{gugunCode}/{contentTypeId}")
//	public ResponseEntity<?> attractionList(@PathVariable("sidoCode") @ApiParam(value = "선택한 시도.", required = true) int sidoCode, 
//			@PathVariable("gugunCode") @ApiParam(value = "구군 리스트.", required = true) int gugunCode, 
//			@PathVariable("contentTypeId") @ApiParam(value = "관광지 유형.", required = true) int contentTypeId) {
//		logger.debug("attractionList map : {} {} {}", sidoCode, gugunCode, contentTypeId);
//		Map<String, Object> map = new HashMap<>();
//		map.put("sidoCode", sidoCode);
//		map.put("gugunCode", gugunCode);
//		map.put("contentTypeId", contentTypeId);
//		try {
//			if(contentTypeId == 0) {
//				List<AttractionInfoDto> list = attractionService.listAttractionAll(map);
//				logger.debug("attractionList listAll : {}", list);
//				return new ResponseEntity<List<AttractionInfoDto>>(list, HttpStatus.OK);
//			} else {
//				List<AttractionInfoDto> list = attractionService.listAttraction(map);
//				logger.debug("attractionList list : {}", list);
//				return new ResponseEntity<List<AttractionInfoDto>>(list, HttpStatus.OK);
//			}
//		} catch (SQLException e) {
//			return exceptionHandling(e);
//		}
//	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
