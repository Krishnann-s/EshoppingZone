package com.eshopingzone.profileservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.eshopingzone.profileservice.Dto.ImageResponse;

@FeignClient(name = "image-service", fallback = ImageFallback.class)
public interface ImageClient {

	@PostMapping(value = "/api/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<ImageResponse> uploadImage(
            @RequestPart("image") MultipartFile image,
            @RequestParam("type") String type);
    
    @GetMapping(value = "/api/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    ResponseEntity<byte[]> getImage(@PathVariable String imageId);
    
    @DeleteMapping("/api/{imageId}")
    ResponseEntity<Void> deleteImage(@PathVariable String imageId);
}