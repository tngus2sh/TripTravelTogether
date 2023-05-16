package com.ssafy.trip.notice.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.notice.model.dto.NoticeDto;

@Mapper
public interface NoticeMapper {
	
	List<NoticeDto> listNotice(Map<String, Object> map) throws SQLException;
	List<NoticeDto> listNotice() throws SQLException;
	NoticeDto viewNotice(int id) throws SQLException;
	void updateHit(int id) throws SQLException;
	int writeNotice(NoticeDto noticeDto) throws SQLException;
	int modifyNotice(NoticeDto noticeDto) throws SQLException;
	int deleteNotice(int id) throws SQLException;
	int getTotalNoticeCount(Map<String, Object> param) throws SQLException;
}
