package com.ssafy.trip.hotplace.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.trip.hotplace.model.dto.HotplaceDto;
import com.ssafy.trip.util.PageNavigation;

public interface HotplaceService {
	List<HotplaceDto> listHotplace(Map<String, String> map) throws Exception;
	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	int findLatestNum() throws Exception;
	void insertHotplace(HotplaceDto hotplaceDto) throws Exception;
}
