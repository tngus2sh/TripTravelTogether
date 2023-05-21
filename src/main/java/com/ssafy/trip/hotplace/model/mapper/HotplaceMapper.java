package com.ssafy.trip.hotplace.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.hotplace.model.dto.HotplaceDto;

@Mapper
public interface HotplaceMapper {
	List<HotplaceDto> listHotplace(Map<String, Object> param) throws Exception;
	int getTotalHotplaceCount(Map<String, Object> param) throws Exception;
	int findLatestNum() throws Exception;
//	void insertHotplace(HotplaceDto hotplaceDto) throws Exception;
//	void registerFile(HotplaceDto hotplaceDto) throws Exception;
//	void modifyHotplace(HotplaceDto hotplaceDto) throws Exception;
//	void deleteHotplace(int num) throws Exception;
	
	// api
	List<HotplaceDto> selectHotplaceList() throws Exception;
	void insertHotplace(HotplaceDto hotplaceDto) throws Exception;
	HotplaceDto selectHotplace(int hotplaceId) throws Exception;
	void modifyHotplace1(HotplaceDto hotplaceDto) throws Exception;
	void modifyHotplace2(HotplaceDto hotplaceDto) throws Exception;
	void deleteHotplace(int num) throws Exception;
}	
