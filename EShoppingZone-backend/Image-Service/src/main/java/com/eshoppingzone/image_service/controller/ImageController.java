package com.eshoppingzone.image_service.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eshoppingzone.image_service.payload.ImageResponse;
import com.eshoppingzone.image_service.service.ImageService;

@RestController
@RequestMapping("/api")
public class ImageController {

	@Autowired
	private ImageService imgService;
	
	// Upload image and get ID
    @PostMapping("/upload")
    public ResponseEntity<ImageResponse> uploadImage(
            @RequestParam("image") MultipartFile image,
            @RequestParam("type") String type) throws IOException {
        
        ImageResponse response = imgService.storeImage(image, type);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Retrieve image by ID
    @GetMapping(value = "/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable String imageId) throws IOException {
        byte[] imageData = imgService.retrieveImage(imageId);
        
        if (imageData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageData);
    }
    
    // Delete image by ID
    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable String imageId) {
        imgService.deleteImage(imageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}