package com.eshopingzone.productservice.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eshopingzone.productservice.exception.ResourceNotFoundException;
import com.eshopingzone.productservice.model.Category;
import com.eshopingzone.productservice.model.Products;
import com.eshopingzone.productservice.payload.ProductDTO;
import com.eshopingzone.productservice.payload.ProductResponse;
import com.eshopingzone.productservice.repository.CategoryRepository;
import com.eshopingzone.productservice.repository.ProductsRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductsServiceImpl implements ProductsService {

	@Autowired
	private ProductsRepository prodRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;
	
	@Override
	public ProductDTO addProducts(Long categoryId, ProductDTO productDto) {
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category" , "categoryId", categoryId));
		
		Products products = modelMapper.map(productDto, Products.class);
		products.setImage("default.png");
		products.setCategory(category);
		double specialPrice = products.getPrice() - ((products.getDiscount() * 0.01) * products.getPrice());
		products.setSpecialPrice(specialPrice);
		
		Products savedProduct = prodRepo.save(products);
		return modelMapper.map(savedProduct, ProductDTO.class);
	}

	@Override
	public ProductResponse viewAllProducts() {
		List<Products> products = prodRepo.findAll();
		List<ProductDTO> productsDto = products.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class))
				.toList();
		
		ProductResponse prodResponse = new ProductResponse();
		prodResponse.setContent(productsDto);
		return prodResponse;
	}
	
	@Override
	public ProductResponse searchByCategory(Long categoryId) {
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category" , "categoryId", categoryId));
		List<Products> products = prodRepo.findByCategoryOrderByPriceAsc(category);
		List<ProductDTO> productsDto = products.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class))
				.toList();
		
		ProductResponse prodResponse = new ProductResponse();
		prodResponse.setContent(productsDto);
		return prodResponse;
	}

	@Override
	public ProductResponse searchProductsByKeyword(String keyword) {
		
		List<Products> products = prodRepo.findByProductNameLikeIgnoreCase('%'+keyword+'%');
		List<ProductDTO> productsDto = products.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class))
				.toList();
		
		ProductResponse prodResponse = new ProductResponse();
		prodResponse.setContent(productsDto);
		return prodResponse;
	}

	@Override
	public Products viewProductsById(Long productId) {
		Products prod = prodRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
		return prod;
	}

	@Override
	public ProductDTO updateProducts(ProductDTO productDto, Long productId) {
		Products existingProduct = prodRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

		Products products = modelMapper.map(productDto, Products.class);
		existingProduct.setProductName(products.getProductName());
		existingProduct.setDescription(products.getDescription());
		existingProduct.setQuantity(products.getQuantity());
		existingProduct.setPrice(products.getPrice());
		existingProduct.setDiscount(products.getDiscount());
		double specialPrice = products.getPrice() - ((products.getDiscount() * 0.01) * products.getPrice());
		products.setSpecialPrice(specialPrice);

		Products savedProduct = prodRepo.save(existingProduct);
		
		return modelMapper.map(savedProduct, ProductDTO.class);
	}

	@Override
	public ProductDTO deleteProducts(Long productId) {
		Products product = prodRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId ));

		prodRepo.delete(product);
		return modelMapper.map(product, ProductDTO.class);
	}

	@Override
	public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
		Products existingProduct = prodRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
		
		// upload image to server and get file name of uploaded image
		String fileName = fileService.uploadImage(path, image);
		
		existingProduct.setImage(fileName);
		Products updatedProduct = prodRepo.save(existingProduct);
				
		return modelMapper.map(updatedProduct, ProductDTO.class);
	}
}
