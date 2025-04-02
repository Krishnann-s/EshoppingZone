package com.eshoppingzone.image_service.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eshoppingzone.image_service.exception.ResourceNotFoundException;
import com.eshoppingzone.image_service.modal.ImageEntity;
import com.eshoppingzone.image_service.payload.ImageResponse;
import com.eshoppingzone.image_service.repository.ImageRepository;
import com.eshoppingzone.image_service.util.ImageCompressor;

@Service
public class ImageServiceImpl implements ImageService{

    @Autowired
    private ImageRepository imageRepository;
    
    @Autowired
    private ImageCompressor imageCompressor;
    
    @Value("${server.port}")
    private String serverPort;
    
    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public ImageResponse storeImage(MultipartFile file, String type) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }
        
        // Generate a unique ID for the image
        String id = UUID.randomUUID().toString();
        
        // Get original byte data
        byte[] originalData = file.getBytes();
        
        // Compress the image data using the type parameter
        byte[] compressedData = imageCompressor.compressImage(originalData, type);
        
        // Create and save the image entity
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setId(id);
        imageEntity.setName(file.getOriginalFilename());
        imageEntity.setType(type);
        imageEntity.setContentType(file.getContentType());
        imageEntity.setCompressedData(compressedData);
        imageEntity.setSize(originalData.length);
        imageEntity.setCompressedSize(compressedData.length);
        imageEntity.setUploadedAt(LocalDateTime.now());
        
        imageRepository.save(imageEntity);
        
        // Create the response with image URL
        String imageUrl = "/api/" + id;
        
        ImageResponse response = new ImageResponse();
        response.setId(id);
        response.setName(file.getOriginalFilename());
        response.setType(type);
        response.setOriginalSize(originalData.length);
        response.setCompressedSize(compressedData.length);
        response.setUrl(imageUrl);
        
        // Calculate and add compression ratio to the response
        double compressionRatio = 100.0 - ((double) compressedData.length / originalData.length * 100.0);
        response.setCompressionRatio(Math.round(compressionRatio * 100.0) / 100.0); // Round to 2 decimal places
        
        // Add dimensions to the response
        if ("product".equalsIgnoreCase(type)) {
            response.setWidth(600);
            response.setHeight(400);
        }
        
        return response;
    }

    @Override
    public byte[] retrieveImage(String id) throws IOException {
        ImageEntity imageEntity = imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image", "id", id));
        
        // Since we're using Thumbnailator, we don't need to decompress
        return imageEntity.getCompressedData();
    }

    @Override
    public void deleteImage(String id) {
        ImageEntity imageEntity = imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image", "id", id));
        
        imageRepository.delete(imageEntity);
    }
}