package com.ssafy.trip.notice.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.trip.notice.model.dto.NoticeDto;
import com.ssafy.trip.util.PageNavigation;

public interface NoticeService {
	
	List<NoticeDto> listNotice(Map<String, String> map) throws Exception;
	List<NoticeDto> listNotice() throws Exception;
	NoticeDto viewNotice(int id) throws Exception;
	void updateHit(int id) throws Exception;
	boolean writeNotice(NoticeDto noticeDto) throws Exception;
	boolean modifyNotice(NoticeDto noticeDto) throws Exception;
	boolean deleteNotice(int id) throws Exception;
	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
}
