package com.eshopingzone.profileservice.client;

import com.eshopingzone.profileservice.Dto.ImageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageFallback implements ImageClient{
    @Override
    public ResponseEntity<ImageResponse> uploadImage(MultipartFile image, String type) {
        return null;
    }

    @Override
    public ResponseEntity<byte[]> getImage(String imageId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteImage(String imageId) {
        return null;
    }
}
