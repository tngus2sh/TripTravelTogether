package com.ssafy.trip.notification.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.notification.model.dto.NotificationDto;

@Mapper
public interface NotificationMapper {
	
	List<NotificationDto> listNotification(Map<String, Object> map) throws SQLException;
	NotificationDto viewNotification(int id) throws SQLException;
	void updateHit(int id) throws SQLException;
	void writeNotification(NotificationDto notificationDto) throws SQLException;
	void modifyNotification(NotificationDto notificationDto) throws SQLException;
	void deleteNotification(int id) throws SQLException;
	int getTotalNotificationCount(Map<String, Object> param) throws SQLException;
}
