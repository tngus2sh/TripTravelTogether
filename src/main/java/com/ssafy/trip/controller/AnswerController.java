package com.ssafy.trip.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.board.model.dto.AnswerDto;
import com.ssafy.trip.board.model.service.AnswerService;

@RestController
@RequestMapping("/board/answer")
@CrossOrigin("*")
public class AnswerController {
	
	private static final Logger logger = LoggerFactory.getLogger(AnswerController.class);
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	private AnswerService  answerService;
	
	@Autowired
	public AnswerController(AnswerService answerService) {
		super();
		this.answerService = answerService;
	}
	
	// 질문 리스트 보여주기
	@GetMapping("/{boardId}")
	public ResponseEntity<?> list(@PathVariable int boardId) throws Exception {
		logger.debug("answer list id : {}", boardId);
		List<AnswerDto> list = answerService.listAnswer(boardId);
		logger.debug("answerlist: {}",list);
		return new ResponseEntity<List<AnswerDto>>(list,HttpStatus.OK);
	}
	
	// 질문 작성
	@PostMapping
	public ResponseEntity<?> write(@RequestBody AnswerDto answerDto) throws Exception{
		logger.debug("write answer call : {}", answerDto);
		
		if(answerService.writeAnswer(answerDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	// 질문 수정
	@PutMapping
	public ResponseEntity<?> modify(@RequestBody AnswerDto answerDto) throws Exception{
		logger.debug("modify answer : {}", answerDto);
		
		if(answerService.modifyAnswer(answerDto)) {
			return new ResponseEntity<String>(SUCCESS,HttpStatus.OK);
		}
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	// 질문 삭제
	@DeleteMapping("/{answerId}")
	public ResponseEntity<?> delete(@PathVariable int answerId) throws Exception {
		logger.debug("delete answer call: {}");
		
		if(answerService.deleteAnswer(answerId)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
}
