package com.ssafy.trip.hotplace.model.dto;

public class TopTenHotplaceDto extends HotplaceDto{
    private int placeCnt;

    public int getPlaceCnt() {
        return placeCnt;
    }

    public void setPlaceCnt(int placeCnt) {
        this.placeCnt = placeCnt;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TopTenHotplaceDto{");
        sb.append("placeCnt=").append(placeCnt);
        sb.append('}');
        return sb.toString();
    }
}
