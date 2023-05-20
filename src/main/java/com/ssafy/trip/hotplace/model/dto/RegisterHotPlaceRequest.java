package com.ssafy.trip.hotplace.model.dto;

import org.springframework.web.multipart.MultipartFile;

public class RegisterHotPlaceRequest {

    private HotplaceDto hotplaceDto;
    private MultipartFile file;

    public HotplaceDto getHotplaceDto() {
        return hotplaceDto;
    }

    public void setHotplaceDto(HotplaceDto hotplaceDto) {
        this.hotplaceDto = hotplaceDto;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
