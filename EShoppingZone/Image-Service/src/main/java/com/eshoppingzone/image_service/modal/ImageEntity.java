package com.eshoppingzone.image_service.modal;

import java.time.LocalDateTime;

import org.hibernate.annotations.CurrentTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "images")
public class ImageEntity {

	@Id
    private String id;
    
    private String name;
    private String type; // product, profile, etc.
    private String contentType;
    
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] compressedData;
    
    private long size; // original size in bytes
    private long compressedSize; // compressed size in bytes
    
    @CurrentTimestamp
    private LocalDateTime uploadedAt;
}
