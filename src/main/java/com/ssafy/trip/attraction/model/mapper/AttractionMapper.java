package com.ssafy.trip.attraction.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.attraction.model.dto.AttractionInfoDto;
import com.ssafy.trip.attraction.model.dto.GunguDto;
import com.ssafy.trip.attraction.model.dto.SidoDto;

@Mapper
public interface AttractionMapper {

    List<SidoDto> listSido() throws SQLException;
	int getSidoCode(String sidoName) throws SQLException;
    List<GunguDto> listGungu(int sidoCode) throws SQLException;
    // String gugunName, int sidoCode
    int getGunguCode(Map<String, Object> map) throws SQLException;
    List<AttractionInfoDto> listAttractionAll(Map<String, Object> map) throws SQLException;
    // int sidoCode, int gugunCode, int contentTypeId
    List<AttractionInfoDto> listAttraction(Map<String, Object> map) throws SQLException;
}
