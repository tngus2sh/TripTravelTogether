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
import com.ssafy.trip.board.model.service.BoardService;
import com.ssafy.trip.user.model.dto.UserDto;
import com.ssafy.trip.util.PageNavigation;

@RestController
//@Controller
@RequestMapping("/board")
@CrossOrigin("*")
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class); 
	
	private BoardService boardService;

	@Autowired
	public BoardController(BoardService boardService) {
		super();
		this.boardService = boardService;
	}
	
//	@GetMapping("/list")
//	public ModelAndView list(@RequestParam Map<String, String> map) throws Exception {
//		logger.debug("board list map : {}", map);
//		ModelAndView mav = new ModelAndView();
//		List<BoardDto> list = boardService.listBoard(map);
//		PageNavigation pageNavigation = boardService.makePageNavigation(map);
//
//		logger.debug("board list list : {}", list);
//		mav.addObject("boards", list);
//		mav.addObject("navigation", pageNavigation);
//		mav.addObject("pgno", map.get("pgno"));
//		mav.addObject("key", map.get("key"));
//		mav.addObject("word", map.get("word"));
//		mav.setViewName("board/list");
//		return mav;
//	}
	
	@GetMapping(value = "/list")
	public ResponseEntity<?> list(@RequestBody Map<String, String> map) {
		logger.debug("board list call : {}", map);
		Map<String, Object> result = new HashMap<>();
		try {
			List<BoardDto> list = boardService.listBoard(map);
			PageNavigation pageNavigation = boardService.makePageNavigation(map);
			logger.debug("board list call : {}", map);
			result.put("boardlist", list);
			result.put("navigation", pageNavigation);
			result.put("pgno", map.get("pgno"));
			result.put("key", map.get("key"));
			result.put("word", map.get("word"));
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
//	@GetMapping("/view")
//	public String view(@RequestParam("id") int id,  @RequestParam Map<String, String> map, Model model) throws Exception {
//		BoardDto boardDto = boardService.viewBoard(id);
//		boardService.updateHit(id);
//		model.addAttribute("board", boardDto);
//		model.addAttribute("pgno", map.get("pgno"));
//		model.addAttribute("key", map.get("key"));
//		model.addAttribute("word", map.get("word"));
//		return "/board/view";
//	}
	
	@GetMapping(value = "/view")
	public ResponseEntity<?> view(@RequestBody int id, Map<String, String> map){
		logger.debug("board view id : {}", id);
		Map<String, Object> result = new HashMap<>();
		try {
			boardService.updateHit(id);
			BoardDto boardDto = boardService.viewBoard(id);
			result.put("board", boardDto);
			result.put("pgno", map.get("pgno"));
			result.put("key", map.get("key"));
			result.put("word", map.get("word"));
			return new  ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	
//	@GetMapping("/write")
//	public String write(@RequestParam Map<String, String> map, Model model) {
//		model.addAttribute("pgno", map.get("pgno"));
//		model.addAttribute("key", map.get("key"));
//		model.addAttribute("word", map.get("word"));
//		return "board/write";
//	}
	
	@GetMapping(value ="/write")
	public ResponseEntity<?> write(@RequestBody Map<String, String> map) {
		logger.debug("board write call ");
		Map<String, Object> result = new HashMap<>();
		try {
			result.put("pgno", map.get("pgno"));
			result.put("key", map.get("key"));
			result.put("word", map.get("word"));
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
//	@PostMapping("/write")
//	public String write(BoardDto boardDto, HttpSession session, RedirectAttributes redirectAttributes) throws Exception {
//		UserDto userDto = (UserDto) session.getAttribute("userinfo");
//		boardDto.setUserId(userDto.getUserid());
//		
//		boardService.writeBoard(boardDto);
//		redirectAttributes.addAttribute("pgno", "1");
//		redirectAttributes.addAttribute("key", "");
//		redirectAttributes.addAttribute("word", "");
//		return "redirect:/board/list";
//	}
	
	@PostMapping(value = "/write")
	public ResponseEntity<?> write(BoardDto boardDto, HttpSession session){
		logger.debug("board write call : {} ", boardDto);
		Map<String, Object> result = new HashMap<>();
		try {
			UserDto userDto = (UserDto) session.getAttribute("userinfo");
			boardDto.setUserId(userDto.getUserid());
			
			boardService.writeBoard(boardDto);
			result.put("pgno",1);
			result.put("key", 1);
			result.put("word", 1);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
//	@GetMapping("/modify")
//	public String modify(@RequestParam("id") int id, HttpSession session, @RequestParam Map<String, String> map, Model model) throws Exception {
//		BoardDto boardDto = boardService.viewBoard(id);
//		model.addAttribute("board", boardDto);
//		model.addAttribute("pgno", map.get("pgno"));
//		model.addAttribute("key", map.get("key"));
//		model.addAttribute("word", map.get("word"));
//		return "board/modify";
//	}
	
	@GetMapping(value = "/modify")
	public ResponseEntity<?> modify(@RequestBody int id, HttpSession session, @RequestBody Map<String, String> map){
		logger.debug("board modify call {} ", id);
		Map<String, Object> result = new HashMap<>();
		
		try {
			BoardDto boardDto = boardService.viewBoard(id);
			result.put("boardDto", boardDto);
			result.put("pgno", map.get("pgno"));
			result.put("key", map.get("key"));
			result.put("word", map.get("word"));
			
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	
//	@PostMapping("/modify")
//	public String modify(BoardDto boardDto, @RequestParam Map<String, String> map,
//			RedirectAttributes redirectAttributes) throws Exception {
//		boardService.modifyBoard(boardDto);
//		redirectAttributes.addAttribute("pgno", map.get("pgno"));
//		redirectAttributes.addAttribute("key", map.get("key"));
//		redirectAttributes.addAttribute("word", map.get("word"));
//		return "redirect:/board/list";
//	}
	
	@PutMapping(value="/modify")
	public ResponseEntity<?> modify(BoardDto boardDto, @RequestBody Map<String,String> map){
		logger.debug("modify board : {}", boardDto);
		Map<String, Object> result = new HashMap<>();
		try {
			boardService.modifyBoard(boardDto);
			result.put("pgno",1);
			result.put("key", 1);
			result.put("word", 1);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
//	@GetMapping("/delete")
//	public String delete(@RequestParam("id") int id, @RequestParam Map<String, String> map,
//			RedirectAttributes redirectAttributes) throws Exception {
//		boardService.deleteBoard(id);
//		redirectAttributes.addAttribute("pgno", map.get("pgno"));
//		redirectAttributes.addAttribute("key", map.get("key"));
//		redirectAttributes.addAttribute("word", map.get("word"));
//		return "redirect:/board/list";
//	}
	
	@DeleteMapping(value = "/delete")
	public ResponseEntity<?> delete(@RequestBody int id, @RequestBody Map<String,String> map){
		logger.debug("delete board : {}", id);
		Map<String, Object> result = new HashMap<>();
		try {
			boardService.deleteBoard(id);
			result.put("pgno",1);
			result.put("key", 1);
			result.put("word", 1);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
