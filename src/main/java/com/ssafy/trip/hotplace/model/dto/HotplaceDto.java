package com.ssafy.trip.hotplace.model.dto;

import java.util.List;

public class HotplaceDto {
	private String userId;
	private int num;
	private String image;
	private String title;
	private String joinDate;
	private String description;
	private String tag1;
	private String tag2;
	private double latitude;
	private double longitude;
	private String mapUrl;

	public HotplaceDto() {}

	public HotplaceDto(String userId, String title, String joinDate, String description, String tag1, String tag2, double latitude, double longitude, String mapUrl) {
		this.userId = userId;
		this.title = title;
		this.joinDate = joinDate;
		this.description = description;
		this.tag1 = tag1;
		this.tag2 = tag2;
		this.latitude = latitude;
		this.longitude = longitude;
		this.mapUrl = mapUrl;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTag1() {
		return tag1;
	}
	public void setTag1(String tag1) {
		this.tag1 = tag1;
	}
	public String getTag2() {
		return tag2;
	}
	public void setTag2(String tag2) {
		this.tag2 = tag2;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getMapUrl() {
		return mapUrl;
	}
	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
	}
	@Override
	public String toString() {
		return "HotplaceDto [userId=" + userId + ", num=" + num + ", image=" + image + ", title=" + title
				+ ", joinDate=" + joinDate + ", description=" + description + ", tag1=" + tag1 + ", tag2=" + tag2
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", mapUrl=" + mapUrl + "]";
	}
	
}
