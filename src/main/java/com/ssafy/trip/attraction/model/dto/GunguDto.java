package com.ssafy.trip.attraction.model.dto;

public class GunguDto extends AttractionInfoDto {
    private String gugunName;
    private int sidoCode;
    private int gugunCode;

	public String getGugunName() {
        return gugunName;
    }

    public void setGugunName(String gugunName) {
        this.gugunName = gugunName;
    }

    @Override
    public int getSidoCode() {
        return sidoCode;
    }

    @Override
    public void setSidoCode(int sidoCode) {
        this.sidoCode = sidoCode;
    }
    
    public int getGugunCode() {
		return gugunCode;
	}

	public void setGugunCode(int gugunCode) {
		this.gugunCode = gugunCode;
	}

	@Override
	public String toString() {
		return "GunguDto [gugunName=" + gugunName + ", sidoCode=" + sidoCode + ", gugunCode=" + gugunCode + "]";
	}

}
