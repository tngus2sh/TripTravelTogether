package com.ssafy.trip.notification.model.service;

import org.springframework.stereotype.Service;

import com.ssafy.trip.notification.model.mapper.NotificationMapper;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	private NotificationMapper notificationMapper;

	public NotificationServiceImpl(NotificationMapper notificationMapper) {
		super();
		this.notificationMapper = notificationMapper;
	}
	
	
}
