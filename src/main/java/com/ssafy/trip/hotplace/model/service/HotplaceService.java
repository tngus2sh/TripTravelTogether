package com.ssafy.trip.hotplace.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssafy.trip.hotplace.model.dto.HotplaceDto;
import com.ssafy.trip.util.PageNavigation;

public interface HotplaceService {
	List<HotplaceDto> listHotplace(Map<String, String> map) throws Exception;
	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	int findLatestNum() throws Exception;
	void insertHotplace(HotplaceDto hotplaceDto) throws Exception;
//	void modifyHotplace(HotplaceDto hotplaceDto) throws Exception;
//	void deleteHotplace(int num) throws Exception;
	
	// api
	List<HotplaceDto> getHotplaceList() throws Exception;
	void registHotplace(HotplaceDto hotplaceDto) throws Exception;
	HotplaceDto getHotplace(int hotplaceId) throws Exception;
	void modifyHotplace(HotplaceDto hotplaceDto) throws Exception;
	void deleteHotplace(int hotplaceId) throws Exception;
}
