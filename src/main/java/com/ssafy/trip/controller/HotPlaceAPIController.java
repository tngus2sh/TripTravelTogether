package com.ssafy.trip.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssafy.trip.hotplace.model.dto.HotplaceDto;
import com.ssafy.trip.hotplace.model.service.HotplaceService;
import com.ssafy.trip.user.model.service.JwtServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/hotplace")
@Api("핫플레이스 컨트롤러 API")
public class HotPlaceAPIController {
	
	private static final Logger logger = LoggerFactory.getLogger(HotPlaceAPIController.class);
	private static final String SUCCESS = "success";
	
	@Value("${file.path}/hotplace/image")
	private String uploadPath;
	
	@Value("${file.imgPath}")
	private String uploadImgPath;
	
	@Autowired
	private HotplaceService hotplaceService;
	
	@Autowired
	private JwtServiceImpl jwtService;

	public HotPlaceAPIController(HotplaceService hotplaceService) {
		super();
		this.hotplaceService = hotplaceService;
	}
	
	@ApiOperation(value = "핫플레이스 목록", notes = "핫플레이스 글 목록을 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<?> hotplaceList() throws Exception {
		logger.debug("hotplaceList call");
		List<HotplaceDto> list = hotplaceService.getHotplace();
		logger.debug("hotplace list : {}", list);
		return new ResponseEntity<List<HotplaceDto>>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "핫플레이스 글 작성", notes = "사진과 함께 핫플레이스 글 내용을 데이터베이스에 넣는다.")
	@PostMapping
	public ResponseEntity<?> hotplaceWrite(
			@RequestBody @ApiParam(value = "글 내용(이미지, 해시태그, 상세 글).", required = true) HotplaceDto hotplaceDto,
			@ApiParam(value = "이미지 파일.", required = true) MultipartHttpServletRequest request
			) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		hotplaceService.registHotplace(hotplaceDto, request);
		resultMap.put("message", SUCCESS);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
	
	@ApiOperation(value = "핫플레이스 글 수정", notes = "사용자를 제외해 수정되어 있는 핫플레이스 글 내용을 데이터베이스에 넣는다.")
	@PutMapping
	public ResponseEntity<?> hotplaceModify(
			@RequestBody @ApiParam(value = "수정된 글 내용(이미지, 해시태그, 상세 글).", required = true) HotplaceDto hotplaceDto,
			@ApiParam(value = "수정된 이미지 파일.", required = true) MultipartHttpServletRequest request
			) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		hotplaceService.modifyHotplace(hotplaceDto, request);
		resultMap.put("message", SUCCESS);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
	
	@ApiOperation(value = "핫플레이스 글 삭제", notes = "전달받은 핫플레이스 id로 게시글 삭제")
	@DeleteMapping("/{hotplaceId}")
	public ResponseEntity<?> hotplaceDelete(
			@PathVariable("hotplaceId") @ApiParam(value = "삭제해야할 핫플레이스 id.", required = true) int hotplaceId
			) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		hotplaceService.deleteHotplace(hotplaceId);
		resultMap.put("message", SUCCESS);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
}
