package com.ssafy.trip.attraction.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.trip.attraction.model.dto.AttractionInfoDto;
import com.ssafy.trip.attraction.model.dto.GunguDto;
import com.ssafy.trip.attraction.model.dto.SidoDto;

public interface AttractionService {

    List<SidoDto> listSido() throws SQLException;
	int getSidoCode(String sidoName) throws SQLException;
    List<GunguDto> listGungu(int sidoCode) throws SQLException;
    int getGunguCode(Map<String, Object> map) throws SQLException;
    List<AttractionInfoDto> listAttractionAll(Map<String, Object> map) throws SQLException;
    List<AttractionInfoDto> listAttraction(Map<String, Object> map) throws SQLException;

	
}
