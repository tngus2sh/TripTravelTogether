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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ssafy.trip.notice.model.dto.NoticeDto;
import com.ssafy.trip.notice.model.service.NoticeService;
import com.ssafy.trip.user.model.dto.UserDto;
import com.ssafy.trip.util.PageNavigation;

import io.swagger.annotations.ApiOperation;

@RestController
//@Controller
@RequestMapping("/notice")
@CrossOrigin("*")
public class NoticeController {
	
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class); 
	
	private NoticeService noticeService;

	@Autowired
	public NoticeController(NoticeService noticeService) {
		super();
		this.noticeService = noticeService;
	}
	
//	@GetMapping("/list")
//	public ModelAndView list(@RequestParam Map<String, String> map) throws Exception {
//		ModelAndView mav = new ModelAndView();
//		List<NoticeDto> list = noticeService.listNotice(map);
//		PageNavigation pageNavigation = noticeService.makePageNavigation(map);
//		
//		mav.addObject("notices", list);
//		mav.addObject("navigation", pageNavigation);
//		mav.addObject("pgno", map.get("pgno"));
//		mav.addObject("key", map.get("key"));
//		mav.addObject("word", map.get("word"));
//		mav.setViewName("notice/list");
//		return mav;
//	}
	
	@ApiOperation(value="공지사항 목록 ", notes="공지 사항 글 목록을 반환 햔다.", response=List.class)
	@GetMapping(value = "/list")
	public ResponseEntity<?> list(@RequestBody Map<String, String> map) {
		logger.debug("notice list call : {}", map);
		Map<String, Object> result = new HashMap<>();
		try {
			List<NoticeDto> list = noticeService.listNotice(map);
			PageNavigation pageNavigation = noticeService.makePageNavigation(map);
			result.put("notice", list);
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
//	public String view(@RequestParam("id") int id, @RequestParam Map<String, String> map, Model model) throws Exception {
//		NoticeDto noticeDto = noticeService.viewNotice(id);
//		noticeService.updateHit(id);
//		model.addAttribute("notice", noticeDto);
//		model.addAttribute("pgno", map.get("pgno"));
//		model.addAttribute("key", map.get("key"));
//		model.addAttribute("word", map.get("word"));
//		return "/notice/view";
//	}
	
	@ApiOperation(value="공지사항 글 상세 보기", notes="해당 id의 글 상세보기")
	@GetMapping(value = "/view")
	public ResponseEntity<?> view(@RequestBody int id, Map<String, String> map){
		logger.debug("notice view id : {}", id);
		Map<String, Object> result = new HashMap<>();
		try {
			noticeService.updateHit(id);
			NoticeDto noticeDto = noticeService.viewNotice(id);
			result.put("notice", noticeDto);
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
//		return "notice/write";
//	}
	
//	@GetMapping(value ="/write")
//	public ResponseEntity<?> write(@RequestBody Map<String, String> map) {
//		logger.debug("notice write call ");
//		Map<String, Object> result = new HashMap<>();
//		try {
//			result.put("pgno", map.get("pgno"));
//			result.put("key", map.get("key"));
//			result.put("word", map.get("word"));
//			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
//		} catch (Exception e) {
//			return exceptionHandling(e);
//		}
//	}
	
//	@PostMapping("/write")
//	public String write(NoticeDto noticeDto, HttpSession session, RedirectAttributes redirectAttributes) throws Exception {
//		UserDto userDto = (UserDto) session.getAttribute("userinfo");
//		noticeDto.setUserId(userDto.getUserid());
//		
//		noticeService.writeNotice(noticeDto);
//		redirectAttributes.addAttribute("pgno", "1");
//		redirectAttributes.addAttribute("key", "");
//		redirectAttributes.addAttribute("word", "");
//		return "redirect:/notice/list";
//	}
	
	@ApiOperation(value="공지사항 글 작성", notes ="새로운 공지글 작성한다.")
	@PostMapping(value = "/write")
	public ResponseEntity<?> write(NoticeDto noticeDto, HttpSession session){
		logger.debug("notice write call : {} ", noticeDto);
		Map<String, Object> result = new HashMap<>();
		try {
			UserDto userDto = (UserDto) session.getAttribute("userinfo");
			noticeDto.setUserId(userDto.getUserId());
			
			noticeService.writeNotice(noticeDto);
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
//		NoticeDto noticeDto = noticeService.viewNotice(id);
//		model.addAttribute("notice", noticeDto);
//		model.addAttribute("pgno", map.get("pgno"));
//		model.addAttribute("key", map.get("key"));
//		model.addAttribute("word", map.get("word"));
//		return "notice/modify";
//	}
	
//	@GetMapping(value = "/modify")
//	public ResponseEntity<?> modify(@RequestBody int id, HttpSession session, @RequestBody Map<String, String> map){
//		logger.debug("notice modify call {} ", id);
//		Map<String, Object> result = new HashMap<>();
//		
//		try {
//			NoticeDto noticeDto = noticeService.viewNotice(id);
//			result.put("noticeDto", noticeDto);
//			result.put("pgno", map.get("pgno"));
//			result.put("key", map.get("key"));
//			result.put("word", map.get("word"));
//			
//			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
//		} catch(Exception e) {
//			return exceptionHandling(e);
//		}
//	}
	
//	@PostMapping("/modify")
//	public String modify(NoticeDto noticeDto, @RequestParam Map<String, String> map,
//			RedirectAttributes redirectAttributes) throws Exception {
//		noticeService.modifyNotice(noticeDto);
//		redirectAttributes.addAttribute("pgno", map.get("pgno"));
//		redirectAttributes.addAttribute("key", map.get("key"));
//		redirectAttributes.addAttribute("word", map.get("word"));
//		return "redirect:/notice/list";
//	}
	
	@ApiOperation(value ="공지사항 글 수정", notes="해당 id의 글 수정")
	@PutMapping(value="/modify")
	public ResponseEntity<?> modify(NoticeDto noticeDto, @RequestBody Map<String,String> map){
		logger.debug("modify notice : {}", noticeDto);
		Map<String, Object> result = new HashMap<>();
		try {
			noticeService.modifyNotice(noticeDto);
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
//		noticeService.deleteNotice(id);
//		redirectAttributes.addAttribute("pgno", map.get("pgno"));
//		redirectAttributes.addAttribute("key", map.get("key"));
//		redirectAttributes.addAttribute("word", map.get("word"));
//		return "redirect:/notice/list";
//	}
	
	@ApiOperation(value ="공지사항 글 삭제", notes="해당 id의 게시판 글 삭제")
	@DeleteMapping(value = "/delete")
	public ResponseEntity<?> delete(@RequestBody int id, @RequestBody Map<String,String> map){
		logger.debug("delete notice : {}", id);
		Map<String, Object> result = new HashMap<>();
		try {
			noticeService.deleteNotice(id);
			result.put("pgno",1);
			result.put("key", 1);
			result.put("word", 1);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

//	@PostMapping("/write")
//	public String write(NoticeDto noticeDto, HttpSession session, RedirectAttributes redirectAttributes) throws Exception {
//		UserDto userDto = (UserDto) session.getAttribute("userInfo");
//		noticeDto.setUserId(userDto.getUserId());
//		
//		noticeService.writeNotice(noticeDto);
//		redirectAttributes.addAttribute("pgno", "1");
//		redirectAttributes.addAttribute("key", "");
//		redirectAttributes.addAttribute("word", "");
//		return "redirect:/notice/list";
//	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
