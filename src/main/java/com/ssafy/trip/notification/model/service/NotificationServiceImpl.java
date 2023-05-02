package com.ssafy.trip.notification.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.trip.notification.model.dto.NotificationDto;
import com.ssafy.trip.notification.model.mapper.NotificationMapper;
import com.ssafy.trip.util.PageNavigation;
import com.ssafy.trip.util.SizeConstant;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	private NotificationMapper notificationMapper;

	public NotificationServiceImpl(NotificationMapper notificationMapper) {
		super();
		this.notificationMapper = notificationMapper;
	}

	@Override
	public List<NotificationDto> listNotification(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		if("userid".equals(key))
			key = "b.user_id";
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int pgNo = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int start = pgNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);
		
		return notificationMapper.listNotification(param);
	}

	@Override
	public NotificationDto viewNotification(int id) throws Exception {
		return notificationMapper.viewNotification(id);
	}

	@Override
	public void updateHit(int id) throws Exception {
		notificationMapper.updateHit(id);
	}

	@Override
	public void writeNotification(NotificationDto notificationDto) throws Exception {
		notificationMapper.writeNotification(notificationDto);
	}

	@Override
	public void modifyNotification(NotificationDto notificationDto) throws Exception {
		notificationMapper.modifyNotification(notificationDto);
	}

	@Override
	public void deleteNotification(int id) throws Exception {
		notificationMapper.deleteNotification(id);
	}

	@Override
	public PageNavigation makePageNavigation(Map<String, String> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = SizeConstant.NAVIGATION_SIZE;
		int sizePerPage = SizeConstant.LIST_SIZE;
		int currentPage = Integer.parseInt(map.get("pgno"));
		System.out.println(currentPage  +  " currentPage");

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		if ("userid".equals(key))
			key = "user_id";
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int totalCount = notificationMapper.getTotalNotificationCount(param);
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
	
}
