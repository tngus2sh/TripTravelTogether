package com.ssafy.trip.hotplace.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.trip.hotplace.model.dto.GoodHotplaceDto;
import com.ssafy.trip.hotplace.model.dto.HotplaceJoinGoodDto;
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.hotplace.model.dto.HotplaceDto;
import org.apache.ibatis.jdbc.SQL;

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
	int getGoodHotplaceNum(Map<String, Object> map) throws SQLException;
	List<HotplaceJoinGoodDto> getGoodHotplace(String userId) throws SQLException;
	void insertGoodHotplace(GoodHotplaceDto goodHotplaceDto) throws SQLException;
	void deleteGoodHotplace(Map<String, Object> map) throws SQLException;
}	
