package com.eshopingzone.productservice.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Products {

	@Id
    private Long productId;
	
    private String productName;
    
    private double price;
    private double discount;
    private double specialPrice;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    private Integer quantity;
    
    @Column(columnDefinition = "LONGTEXT")
    private String image;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
