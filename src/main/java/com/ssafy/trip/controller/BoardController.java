package com.ssafy.trip.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import org.springframework.web.multipart.MultipartFile;
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
	
	@Value("${file.path}/hotplace/image")
	private String uploadPath;
	
	@Value("${file.imgPath}")
	private String uploadImgPath;
	
	private BoardService boardService;

	@Autowired
	public BoardController(BoardService boardService) {
		super();
		this.boardService = boardService;
	}
	
	
	@ApiOperation(value="자유 게시판 목록 ", notes="자유 게시판 목록을 반환 햔다.", response=List.class)
	@GetMapping(value = "/list")
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
	@Transactional
	public ResponseEntity<?> write(
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam("userId") String userId,
			@RequestParam("userName") String userName,
			@RequestParam(value = "image", required = false) MultipartFile file
			) throws Exception{
		logger.debug("write board call : {} {} {} {} {}", title, content, userId, userName, file);
		
		BoardDto boardDto = new BoardDto(title, content, userId, userName);
		
		// FileUpload
		if(file != null && !file.isEmpty()) {
			String saveFolder = uploadPath;
			logger.debug("저장 폴더 : {}", saveFolder);
			File folder = new File(saveFolder);
			
			if(!folder.exists()) {
				folder.mkdirs();
			}
			String originalFileName = file.getOriginalFilename();
			System.out.println(originalFileName);
			if(!originalFileName.isEmpty()) {
				String saveFileName = UUID.randomUUID().toString()
						+ originalFileName.substring(originalFileName.lastIndexOf('.'));
				// 파일 저장하기
				file.transferTo(new File(folder, saveFileName));
				boardDto.setImage(saveFileName);
			}
			
		}
			
		if(boardService.writeBoard(boardDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		
	}


	// 이미지를 변경하는 경우
	@ApiOperation(value ="자유 게시판 글 수정", notes="해당 id의 글 수정")
	@PutMapping
	@Transactional
	public ResponseEntity<?> modify1(
			@RequestParam("title") String title,
			@RequestParam("id") int id, 
			@RequestParam("content") String content,
			@RequestParam("userId") String userId,
			@RequestParam("userName") String userName,
			@RequestParam("image") MultipartFile file
			) throws Exception{
		logger.debug("modify board : {} {} {} {} {}", title, content, userId, userName, file);
		
		BoardDto boardDto = new BoardDto(title, content, userId, userName);
		boardDto.setId(id);
		
		if(file != null && !file.isEmpty()) {
			String saveFolder = uploadPath;
			logger.debug("저장 폴더 : {}", saveFolder);
			File folder = new File(saveFolder);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			String originalFileName = file.getOriginalFilename();
			if (!originalFileName.isEmpty()) {
				String saveFileName = UUID.randomUUID().toString()
						+ originalFileName.substring(originalFileName.lastIndexOf('.'));
				file.transferTo(new File(folder, saveFileName));
				boardDto.setImage(saveFileName);
			}
		}
		
		if(boardService.modifyBoard1(boardDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	
	// 이미지를 받지 않는 경우
	@ApiOperation(value ="자유 게시판 글 수정", notes="해당 id의 글 수정")
	@PutMapping(value = "/{boardId}")
	@Transactional
	public ResponseEntity<?> modify2(@RequestBody BoardDto boardDto) throws Exception {
		System.out.println("come in");
		logger.debug("modify board {}", boardDto);
		
		if(boardService.modifyBoard2(boardDto)) {
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
