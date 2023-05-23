package com.ssafy.trip.hotplace.model.dto;

public class GoodHotplaceDto {
    private int id;
    private String userId;
    private int hotplaceId;
    private String createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getHotplaceId() {
        return hotplaceId;
    }

    public void setHotplaceId(int hotplaceId) {
        this.hotplaceId = hotplaceId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GoodHotplaceDto{");
        sb.append("id=").append(id);
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", hotplaceId=").append(hotplaceId);
        sb.append(", createdAt='").append(createdAt).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
