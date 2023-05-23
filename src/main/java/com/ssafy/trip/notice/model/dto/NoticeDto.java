package com.ssafy.trip.notice.model.dto;

public class NoticeDto {
	
	private int id;
	private String title;
	private String content;
	private String createdAt;
	private String userId;
	private String userName;
	private int hit;
	private int pin;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public int getPin() {
		return pin;
	}
	public void setPin(int pin) {
		this.pin = pin;
	}
	@Override
	public String toString() {
		return "NoticeDto [id=" + id + ", title=" + title + ", content=" + content + ", createdAt=" + createdAt
				+ ", userId=" + userId + ", userName=" + userName + ", hit=" + hit + ", pin=" + pin + "]";
	}
}
