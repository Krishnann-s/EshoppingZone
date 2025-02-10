package com.eshopingzone.productservice.modal;



import java.math.BigDecimal;
import java.util.List;



import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Products {

	@Id
    private int productId;
	
    private String title;
    
    private BigDecimal price;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    private String category;
    
    @Column(columnDefinition = "LONGTEXT")
    private String image;
    
    @OneToOne(mappedBy = "products", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Rating rating;

}
