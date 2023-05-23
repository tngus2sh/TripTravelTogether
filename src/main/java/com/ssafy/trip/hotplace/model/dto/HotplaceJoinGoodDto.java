package com.ssafy.trip.hotplace.model.dto;

public class HotplaceJoinGoodDto {
    private int id;
    private String image;
    private String title;
    private String joinDate;
    private String description;
    private String tag1;
    private String tag2;
    private double latitude;
    private double longitude;
    private String mapUrl;
    private String ghCreatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getGhCreatedAt() {
        return ghCreatedAt;
    }

    public void setGhCreatedAt(String ghCreatedAt) {
        this.ghCreatedAt = ghCreatedAt;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HotplaceJoinGoodDto{");
        sb.append("id=").append(id);
        sb.append(", image='").append(image).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", joinDate='").append(joinDate).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", tag1='").append(tag1).append('\'');
        sb.append(", tag2='").append(tag2).append('\'');
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", mapUrl='").append(mapUrl).append('\'');
        sb.append(", ghCreatedAt='").append(ghCreatedAt).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
