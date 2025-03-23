package com.eshoppingzone.image_service.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.resizers.configurations.ScalingMode;

@Component
public class ImageCompressor {
    
    // Default quality setting (0.0-1.0)
    private static final float DEFAULT_QUALITY = 0.8f;
    
    // Maximum width/height for non-product images
    private static final int MAX_DIMENSION = 1200;
    
    // Product image dimensions
    private static final int PRODUCT_WIDTH = 600;
    private static final int PRODUCT_HEIGHT = 400;

    /**
     * Compresses an image using Thumbnailator
     * @param data Original image data
     * @param imageType The type of image (e.g., "product", "profile")
     * @return Compressed image data
     * @throws IOException if compression fails
     */
    public byte[] compressImage(byte[] data, String imageType) throws IOException {
        if (data == null || data.length == 0) {
            return new byte[0];
        }
        
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(data));
        if (originalImage == null) {
            throw new IOException("Cannot read image data");
        }
        
        // Determine image format
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String formatName = determineImageFormat(data);
        
        // For product images, use exact dimensions
        if ("product".equalsIgnoreCase(imageType)) {
            Thumbnails.of(originalImage)
                .size(PRODUCT_WIDTH, PRODUCT_HEIGHT)
                .crop(Positions.CENTER)
                .outputQuality(DEFAULT_QUALITY)
                .outputFormat(formatName)
                .scalingMode(ScalingMode.BICUBIC)
                .toOutputStream(outputStream);
        } else {
            // For other image types, scale down if needed while maintaining aspect ratio
            int width = originalImage.getWidth();
            int height = originalImage.getHeight();
            
            if (width > MAX_DIMENSION || height > MAX_DIMENSION) {
                if (width > height) {
                    height = (int) ((double) height / width * MAX_DIMENSION);
                    width = MAX_DIMENSION;
                } else {
                    width = (int) ((double) width / height * MAX_DIMENSION);
                    height = MAX_DIMENSION;
                }
            }
            
            Thumbnails.of(originalImage)
                .size(width, height)
                .outputQuality(DEFAULT_QUALITY)
                .outputFormat(formatName)
                .scalingMode(ScalingMode.BICUBIC)
                .toOutputStream(outputStream);
        }
        
        return outputStream.toByteArray();
    }
    
    /**
     * Overloaded method for backward compatibility
     */
    public byte[] compressImage(byte[] data) throws IOException {
        return compressImage(data, "default");
    }
    
    /**
     * Since we're using image-specific compression, decompression
     * is not needed - we just return the data as is
     */
    public byte[] decompressImage(byte[] compressedData) {
        return compressedData;
    }
    
    /**
     * Determine the image format from the input bytes
     */
    private String determineImageFormat(byte[] imageData) {
        // Default to JPEG if we can't determine
        String format = "JPEG";
        
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
            BufferedImage image = ImageIO.read(bis);
            
            // Try to determine from readers
            javax.imageio.ImageReader reader = ImageIO.getImageReaders(
                ImageIO.createImageInputStream(new ByteArrayInputStream(imageData))).next();
            
            if (reader != null) {
                format = reader.getFormatName();
            }
        } catch (Exception e) {
            // Fallback to JPEG on error
        }
        
        // Convert common format names to standard format
        if (format.equalsIgnoreCase("jpg") || format.equalsIgnoreCase("jpeg")) {
            return "JPEG";
        } else if (format.equalsIgnoreCase("png")) {
            return "PNG";
        } else if (format.equalsIgnoreCase("gif")) {
            return "GIF";
        }
        
        return format;
    }
}