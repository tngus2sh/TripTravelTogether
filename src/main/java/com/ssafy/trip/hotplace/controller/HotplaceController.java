package com.ssafy.trip.hotplace.controller;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ssafy.trip.hotplace.model.dto.HotplaceDto;
import com.ssafy.trip.hotplace.model.service.HotplaceService;
import com.ssafy.trip.user.model.dto.UserDto;
import com.ssafy.trip.util.PageNavigation;

@Controller
@RequestMapping("/hotplace")
public class HotplaceController {
	
	private final Logger logger = LoggerFactory.getLogger(HotplaceController.class);
	
	@Value("${file.path}/hotplace/image")
	private String uploadPath;
	
	@Value("${file.imgPath}")
	private String uploadImgPath;
	
	private HotplaceService hotplaceService;

	public HotplaceController(HotplaceService hotplaceService) {
		super();
		this.hotplaceService = hotplaceService;
	}

	@GetMapping("/list")
	public String listHotplace(@RequestParam Map<String, String> map, Model model) throws Exception {
		
		List<HotplaceDto> list = hotplaceService.listHotplace(map);
		
		PageNavigation pageNavigation = hotplaceService.makePageNavigation(map);
		
		model.addAttribute("hotplaces", list);
		
		model.addAttribute("navigation", pageNavigation);
		return "hotplace/list";
	}
	
	@PostMapping("/write")
	public String writeHotplace(HotplaceDto hotplaceDto, @RequestParam("upfile") MultipartFile file, HttpSession session, RedirectAttributes redirectAttributes) throws Exception {
		logger.debug("writeHotplace parameter : {}", hotplaceDto);
		logger.debug("MultipartFile.isEmpty : {}", file.isEmpty());
		UserDto userDto = (UserDto) session.getAttribute("userinfo");
		hotplaceDto.setUserId(userDto.getId());
		
		// FileUpload
		if(!file.isEmpty()) {
//			String today = new SimpleDateFormat("yyMMdd").format(new Date());
//			String saveFolder = uploadPath + File.separator + today;
			String saveFolder = uploadPath;
			logger.debug("저장 폴더 : {}", saveFolder);
			File folder = new File(saveFolder);
			if (!folder.exists())
				folder.mkdirs();
			String originalFileName = file.getOriginalFilename();
			if (!originalFileName.isEmpty()) {
				String saveFileName = UUID.randomUUID().toString()
						+ originalFileName.substring(originalFileName.lastIndexOf('.'));
				// 파일 저장하기
				file.transferTo(new File(folder, saveFileName));
				hotplaceDto.setImage(saveFileName);				
			}
		}
		
		hotplaceService.insertHotplace(hotplaceDto);
		
		redirectAttributes.addAttribute("pgno", "1");
		return "redirect:/hotplace/list";
	}
	
	@GetMapping("/keyword")
	public String showKeyword() {
		return "hotplace/keyword";
	}
	
}
