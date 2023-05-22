package com.ssafy.trip.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ssafy.trip.board.model.dto.BoardDto;
import com.ssafy.trip.board.model.dto.BoardParameterDto;
import com.ssafy.trip.board.model.service.BoardService;
import com.ssafy.trip.notice.model.dto.NoticeDto;
import com.ssafy.trip.user.model.dto.UserDto;
import com.ssafy.trip.util.PageNavigation;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/board")
@CrossOrigin("*")
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	private BoardService boardService;

	@Autowired
	public BoardController(BoardService boardService) {
		super();
		this.boardService = boardService;
	}
	
	
	@ApiOperation(value="자유 게시판 목록 ", notes="자유 게시판 목록을 반환 햔다.", response=List.class)
	@GetMapping("/list")
	public ResponseEntity<?> list(@ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) BoardParameterDto boardParameterDto) throws Exception{
		logger.debug("board list call");
		List<BoardDto> list = boardService.listBoard(boardParameterDto);
		logger.debug("boardList {}", list);
		return new ResponseEntity<List<BoardDto>>(list, HttpStatus.OK);
	}
	
	
	@ApiOperation(value="자유 게시판 글 상세 보기", notes="해당 id의 글 상세보기")
	@GetMapping(value = "/{boardId}")
	public ResponseEntity<?> view(@PathVariable @ApiParam(value = "자유 게시판 게시글 id") int boardId) throws Exception{
		logger.debug("board view id : {}", boardId);
		boardService.updateHit(boardId);
		BoardDto boardDto = boardService.viewBoard(boardId);
		logger.debug("board {}", boardDto);
		return new ResponseEntity<BoardDto>(boardDto, HttpStatus.OK);
	}

	
	@ApiOperation(value="자유 게시판 글 작성", notes ="새로운 게시글 작성한다.")
	@PostMapping
	public ResponseEntity<?> write(@RequestBody BoardDto boardDto) throws Exception{
		logger.debug("write board call : {}", boardDto);
		
		if(boardService.writeBoard(boardDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}


	
	@ApiOperation(value ="자유 게시판 글 수정", notes="해당 id의 글 수정")
	@PutMapping
	public ResponseEntity<?> modify(@RequestBody BoardDto boardDto) throws Exception{
		logger.debug("modify board : {}", boardDto);
		
		if(boardService.modifyBoard(boardDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	
	@ApiOperation(value ="자유 게시판 글 삭제", notes="해당 id의 게시판 글 삭제")
	@DeleteMapping(value = "/{boardId}")
	public ResponseEntity<?> delete(@PathVariable @ApiParam(value = "자유 게시판 게시글 id") int boardId) throws Exception {
		logger.debug("delete board call");
		
		if(boardService.deleteBoard(boardId)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
}
