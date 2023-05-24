package com.ssafy.trip.hotplace.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.trip.hotplace.model.dto.GoodHotplaceDto;
import com.ssafy.trip.hotplace.model.dto.HotplaceJoinGoodDto;
import com.ssafy.trip.hotplace.model.dto.TopTenHotplaceDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssafy.trip.hotplace.model.dto.HotplaceDto;
import com.ssafy.trip.hotplace.model.mapper.HotplaceMapper;
import com.ssafy.trip.util.PageNavigation;
import com.ssafy.trip.util.SizeConstant;

@Service
public class HotplaceServiceImpl implements HotplaceService {
	
	private HotplaceMapper hotplaceMapper;
	
	public HotplaceServiceImpl(HotplaceMapper hotplaceMapper) {
		super();
		this.hotplaceMapper = hotplaceMapper;
	}

	@Override
	public List<HotplaceDto> listHotplace(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		int page = Integer.parseInt(map.get("pgno"));
		int start = page * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);
		return hotplaceMapper.listHotplace(param);
	}

	@Override
	public PageNavigation makePageNavigation(Map<String, String> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = SizeConstant.NAVIGATION_SIZE;
		int sizePerPage = SizeConstant.LIST_SIZE;
		int currentPage = Integer.parseInt(map.get("pgno"));

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		Map<String, Object> param = new HashMap<String, Object>();
		int totalCount = hotplaceMapper.getTotalHotplaceCount(param);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = currentPage <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();

		return pageNavigation;
	}

	@Override
	public int findLatestNum() throws Exception {
		return hotplaceMapper.findLatestNum();
	}

	@Override
	public void insertHotplace(HotplaceDto hotplaceDto) throws Exception {
		System.out.println("글입력 전 dto : " + hotplaceDto);
		hotplaceMapper.insertHotplace(hotplaceDto);
		System.out.println("글입력 후 dto : " + hotplaceDto);
	}

//	@Override
//	public void deleteHotplace(int num) throws Exception {
//		hotplaceMapper.deleteHotplace(num);
//	}

	// **********************************************************************
	// api
	// **********************************************************************
	
	/**
	 * 핫플레이스 리스트
	 * @return List<HotplaceDto>
	 * @throws Exception
	 */
	@Override
	public List<HotplaceDto> getHotplaceList() throws Exception {
		return hotplaceMapper.selectHotplaceList();
	}

	/**
	 * 핫플레이스 등록하기
	 */
	@Override
	public void registHotplace(HotplaceDto hotplaceDto) throws Exception {
		hotplaceMapper.insertHotplace(hotplaceDto);
	}

	/**
	 * id에 해당하는 핫플레이스 글 불러오기
	 */
	@Override
	public HotplaceDto getHotplace(int hotplaceId) throws Exception {
		return hotplaceMapper.selectHotplace(hotplaceId);
	}

	/**
	 * 핫플레이스 수정하기
	 */
	@Override
	public void modifyHotplace1(HotplaceDto hotplaceDto) throws Exception {
		hotplaceMapper.modifyHotplace1(hotplaceDto);
	}
	
	@Override
	public void modifyHotplace2(HotplaceDto hotplaceDto) throws Exception {
		hotplaceMapper.modifyHotplace2(hotplaceDto);
		
	}
	

	/**
	 * id에 해당하는 핫플레이스 삭제하기
	 */
	@Override
	public void deleteHotplace(int hotplaceId) throws Exception {
		hotplaceMapper.deleteHotplace(hotplaceId);
	}

	@Override
	public int getGoodHotplaceNum(Map<String, Object> map) throws Exception {
		return hotplaceMapper.getGoodHotplaceNum(map);
	}

	@Override
	public List<HotplaceJoinGoodDto> getGoodHotplace(String userId) throws Exception {
		return hotplaceMapper.getGoodHotplace(userId);
	}

	@Override
	public void registGoodHotplace(GoodHotplaceDto goodHotplaceDto) throws Exception {
		hotplaceMapper.insertGoodHotplace(goodHotplaceDto);
	}

	@Override
	public void deleteGoodHotplace(Map<String, Object> map) throws Exception {
		hotplaceMapper.deleteGoodHotplace(map);
	}

	@Override
	public List<TopTenHotplaceDto> getTopTenHot() throws SQLException {
		return hotplaceMapper.getTopTenHot();
	}

}
