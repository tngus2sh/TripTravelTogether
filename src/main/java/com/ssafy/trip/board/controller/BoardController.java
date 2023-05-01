package com.ssafy.trip.board.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ssafy.trip.board.model.dto.BoardDto;
import com.ssafy.trip.board.model.service.BoardService;
import com.ssafy.trip.user.model.dto.UserDto;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class); 
	
	private BoardService boardService;

	public BoardController(BoardService boardService) {
		super();
		this.boardService = boardService;
	}
	
	@GetMapping("/list")
	public ModelAndView list(@RequestParam Map<String, Object> map) throws Exception {
		logger.debug("board list map : {}", map);
		ModelAndView mav = new ModelAndView();
		List<BoardDto> list = boardService.listBoard(map);
		
		logger.debug("board list list : {}", list);
		mav.addObject("boards", list);
		mav.setViewName("board/list");
		return mav;
	}
	
	@GetMapping("/view")
	public String view(@RequestParam("id") int id,  @RequestParam Map<String, String> map, Model model) throws Exception {
		BoardDto boardDto = boardService.viewBoard(id);
		boardService.updateHit(id);
		model.addAttribute("board", boardDto);
		return "/board/view";
	}
	
	@GetMapping("/write")
	public String write() {
		return "board/write";
	}
	
	@PostMapping("/write")
	public String write(BoardDto boardDto, HttpSession session, RedirectAttributes redirectAttributes) throws Exception {
		UserDto userDto = (UserDto) session.getAttribute("userinfo");
		boardDto.setUserId(userDto.getId());
		
		boardService.writeBoard(boardDto);
		redirectAttributes.addAttribute("pgno", "1");
		redirectAttributes.addAttribute("key", "");
		redirectAttributes.addAttribute("word", "");
		return "redirect:/board/list";
	}
	
	@GetMapping("/modify")
	public String modify(@RequestParam("id") int id, HttpSession session, @RequestParam Map<String, String> map, Model model) throws Exception {
		BoardDto boardDto = boardService.viewBoard(id);
		System.out.println(11);
		System.out.println(id);
		System.out.println(boardDto.getId());
		model.addAttribute("board", boardDto);
		return "board/modify";
	}
	
	@PostMapping("/modify")
	public String modify(BoardDto boardDto, @RequestParam Map<String, String> map,
			RedirectAttributes redirectAttributes) throws Exception {
		boardService.modifyBoard(boardDto);
		redirectAttributes.addAttribute("pgno", map.get("pgno"));
		redirectAttributes.addAttribute("key", map.get("key"));
		redirectAttributes.addAttribute("word", map.get("word"));
		return "redirect:/board/list";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("id") int id, @RequestParam Map<String, String> map,
			RedirectAttributes redirectAttributes) throws Exception {
		boardService.deleteBoard(id);
		return "redirect:/board/list";
	}
}
