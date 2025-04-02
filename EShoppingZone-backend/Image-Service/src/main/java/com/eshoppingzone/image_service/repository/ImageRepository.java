package com.eshoppingzone.image_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eshoppingzone.image_service.modal.ImageEntity;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, String>{

	List<ImageEntity> findByType(String type);
}
