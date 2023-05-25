package com.ssafy.trip.board.model.dto;

public class BoardDto {
	
	private int id;
	private String title;
	private String content;
	private String createdAt;
	private String userId;
	private String userName;
	private int hit;
	private String image;
	
	public BoardDto() {}
	
	public BoardDto(String title, String content, String userId, String userName) {
		super();
		this.title = title;
		this.content = content;
		this.userId = userId;
		this.userName = userName;
	}
	
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
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "BoardDto [id=" + id + ", title=" + title + ", content=" + content + ", createdAt=" + createdAt
				+ ", userId=" + userId + ", userName=" + userName + ", hit=" + hit + ", image=" + image + "]";
	}
	
	
}
