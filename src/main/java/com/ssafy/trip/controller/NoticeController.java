package com.ssafy.trip.controller;

import java.util.*;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ssafy.trip.board.model.dto.BoardDto;
import com.ssafy.trip.notice.model.dto.NoticeDto;
import com.ssafy.trip.notice.model.service.NoticeService;
import com.ssafy.trip.user.model.dto.UserDto;
import com.ssafy.trip.util.PageNavigation;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/notice")
@CrossOrigin("*")
public class NoticeController {
	
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class); 
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	private NoticeService noticeService;

	@Autowired
	public NoticeController(NoticeService noticeService) {
		super();
		this.noticeService = noticeService;
	}
	
	
	@ApiOperation(value="공지사항 목록 ", notes="공지 사항 글 목록을 반환 햔다.", response=List.class)
	@GetMapping("/list")
	public ResponseEntity<?> list() throws Exception {
		logger.debug("notice list call");
		List<NoticeDto> list = noticeService.listNotice();
		logger.debug("noticeList {}", list);
		return new ResponseEntity<List<NoticeDto>>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value="공지사항 글 상세 보기", notes="해당 id의 글 상세보기")
	@GetMapping(value = "/{noticeId}")
	public ResponseEntity<?> view(@PathVariable @ApiParam(value = "자유 게시판 게시글 id") int noticeId) throws Exception{
		logger.debug("notice view id : {}", noticeId);
		noticeService.viewNotice(noticeId);
		NoticeDto noticeDto = noticeService.viewNotice(noticeId);
		logger.debug("notice {}", noticeDto);
		return new ResponseEntity<NoticeDto>(noticeDto,HttpStatus.OK);
	}
	
	
	@ApiOperation(value="공지 사항 작성", notes ="새로운 게시글 작성한다.")
	@PostMapping
	public ResponseEntity<?> write(@RequestBody NoticeDto noticeDto) throws Exception{
		logger.debug("write notice call : {}", noticeDto);
		
		if(noticeService.writeNotice(noticeDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}

	
	@ApiOperation(value ="공지 사항 글 수정", notes="해당 id의 글 수정")
	@PutMapping
	public ResponseEntity<?> modify(@RequestBody NoticeDto noticeDto) throws Exception{
		logger.debug("modify notice : {}", noticeDto);
		
		if(noticeService.modifyNotice(noticeDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	
	@ApiOperation(value ="공지 사항 글 삭제", notes="해당 id의 게시판 글 삭제")
	@DeleteMapping(value = "/{noticeId}")
	public ResponseEntity<?> delete(@PathVariable @ApiParam(value = "공지 사항 id") int noticeId) throws Exception {
		logger.debug("delete notice call");
		
		if(noticeService.deleteNotice(noticeId)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
}
