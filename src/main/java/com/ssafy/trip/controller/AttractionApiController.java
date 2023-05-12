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
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.attraction.model.dto.AttractionInfoDto;
import com.ssafy.trip.attraction.model.dto.GunguDto;
import com.ssafy.trip.attraction.model.service.AttractionService;

@RestController
@RequestMapping("/attraction")
@CrossOrigin("*")
public class AttractionApiController {
	
	private static final Logger logger = LoggerFactory.getLogger(AttractionApiController.class);

	private AttractionService attractionService;

	public AttractionApiController(AttractionService attractionService) {
		super();
		this.attractionService = attractionService;
	}
	
	@GetMapping(value="/gungu/{sidoCode}")
	public ResponseEntity<?> gunguList(@PathVariable("sidoCode") int sidoCode) {
		logger.debug("gungu list, sidoCode : {}", sidoCode);
		try {
			List<GunguDto> list = attractionService.listGungu(sidoCode);
			logger.debug("gungu list : {}", list);
			return new ResponseEntity<List<GunguDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping(value = "/list/{sidoCode}/{gugunCode}/{contentTypeId}")
	public ResponseEntity<?> attractionList(@PathVariable("sidoCode") int sidoCode, @PathVariable("gugunCode") int gugunCode, @PathVariable("contentTypeId") int contentTypeId) {
		logger.debug("attractionList map : {} {} {}", sidoCode, gugunCode, contentTypeId);
		Map<String, Object> map = new HashMap<>();
		map.put("sidoCode", sidoCode);
		map.put("gugunCode", gugunCode);
		map.put("contentTypeId", contentTypeId);
		try {
			if(contentTypeId == 0) {
				List<AttractionInfoDto> list = attractionService.listAttractionAll(map);
				logger.debug("attractionList listAll : {}", list);
				return new ResponseEntity<List<AttractionInfoDto>>(list, HttpStatus.OK);
			} else {
				List<AttractionInfoDto> list = attractionService.listAttraction(map);
				logger.debug("attractionList list : {}", list);
				return new ResponseEntity<List<AttractionInfoDto>>(list, HttpStatus.OK);
			}
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
