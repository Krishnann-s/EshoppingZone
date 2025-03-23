package com.eshoppingzone.image_service.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.eshoppingzone.image_service.payload.ImageResponse;

public interface ImageService {

	ImageResponse storeImage(MultipartFile file, String type) throws IOException;
	byte[] retrieveImage(String id) throws IOException;
	void deleteImage(String id);
}
