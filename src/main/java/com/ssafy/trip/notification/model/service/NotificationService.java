package com.ssafy.trip.notification.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.trip.notification.model.dto.NotificationDto;
import com.ssafy.trip.util.PageNavigation;

public interface NotificationService {
	
	List<NotificationDto> listNotification(Map<String, String> map) throws Exception;
	NotificationDto viewNotification(int id) throws Exception;
	void updateHit(int id) throws Exception;
	void writeNotification(NotificationDto notificationDto) throws Exception;
	void modifyNotification(NotificationDto notificationDto) throws Exception;
	void deleteNotification(int id) throws Exception;
	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
}
