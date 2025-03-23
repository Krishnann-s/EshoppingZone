package com.eshopingzone.productservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Products {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
	
	@NotNull(message = "Product name cannot be null")
	@Size(min = 3, message = "Product name must contain atleast 3 characters")
    private String productName;
    
    private double price;
    private double discount;
    private double specialPrice;
    
    @Column(columnDefinition = "TEXT")
    @NotBlank
    @Size(min = 12, message = "Description must contain atleast 12 characters")
    private String description;
    
    @Min(value = 1, message = "quantity must be atleast 1")
    private Integer quantity;
    
    @Column(columnDefinition = "LONGTEXT")
    private String imageId;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
