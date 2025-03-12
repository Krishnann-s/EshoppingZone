package com.eshopingzone.productservice.service;

import java.io.IOException;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eshopingzone.productservice.exception.APIException;
import com.eshopingzone.productservice.exception.ResourceNotFoundException;
import com.eshopingzone.productservice.model.Category;
import com.eshopingzone.productservice.model.Products;
import com.eshopingzone.productservice.payload.ProductDTO;
import com.eshopingzone.productservice.payload.ProductResponse;
import com.eshopingzone.productservice.repository.CategoryRepository;
import com.eshopingzone.productservice.repository.ProductsRepository;

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
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

		boolean isProductNotPresent = false;
		List<Products> prods = category.getProducts();
		for (Products value : prods) {
			if (value.getProductName().equals(productDto.getProductName())) {
				isProductNotPresent = true;
				break;
			}
		}
		if (isProductNotPresent) {
			Products products = modelMapper.map(productDto, Products.class);
			products.setImage("default.png");
			products.setCategory(category);
			double specialPrice = products.getPrice() - ((products.getDiscount() * 0.01) * products.getPrice());
			products.setSpecialPrice(specialPrice);

			Products savedProduct = prodRepo.save(products);
			return modelMapper.map(savedProduct, ProductDTO.class);
		} else {
			throw new APIException("Product already exist!!");
		}

	}

	@Override
	public ProductResponse viewAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
		
		Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
		Page<Products> pageProducts = prodRepo.findAll(pageDetails);
		
		List<Products> products = pageProducts.getContent();
		List<ProductDTO> productsDto = products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
				.toList();

		ProductResponse prodResponse = new ProductResponse();
		prodResponse.setContent(productsDto);
		prodResponse.setPageNumber(pageProducts.getNumber());
		prodResponse.setPageSize(pageProducts.getSize());
		prodResponse.setTotalElements(pageProducts.getTotalElements());
		prodResponse.setTotalPages(pageProducts.getTotalPages());
		prodResponse.setLastPage(pageProducts.isLast());
		return prodResponse;
	}

	@Override
	public ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
		
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		
		Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
		Page<Products> pageProducts = prodRepo.findByCategoryOrderByPriceAsc(category, pageDetails);
		
		List<Products> products = pageProducts.getContent();
		List<ProductDTO> productsDto = products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
				.toList();

		ProductResponse prodResponse = new ProductResponse();
		prodResponse.setContent(productsDto);
		prodResponse.setPageNumber(pageProducts.getNumber());
		prodResponse.setPageSize(pageProducts.getSize());
		prodResponse.setTotalElements(pageProducts.getTotalElements());
		prodResponse.setTotalPages(pageProducts.getTotalPages());
		prodResponse.setLastPage(pageProducts.isLast());
		return prodResponse;
	}

	@Override
	public ProductResponse searchProductsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

		Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
		Page<Products> pageProducts = prodRepo.findByProductNameLikeIgnoreCase('%' + keyword + '%', pageDetails);
		
		List<Products> products = pageProducts.getContent();
		List<ProductDTO> productsDto = products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
				.toList();

		ProductResponse prodResponse = new ProductResponse();
		prodResponse.setContent(productsDto);
		prodResponse.setPageNumber(pageProducts.getNumber());
		prodResponse.setPageSize(pageProducts.getSize());
		prodResponse.setTotalElements(pageProducts.getTotalElements());
		prodResponse.setTotalPages(pageProducts.getTotalPages());
		prodResponse.setLastPage(pageProducts.isLast());
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
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

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
	
	@Override
	public ProductDTO getProductById(Long productId) {
        Products product = prodRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        return new ProductDTO(product.getProductId(), product.getProductName(), product.getImage(),
                product.getQuantity(), product.getDescription(), product.getPrice(),
                product.getDiscount(), product.getSpecialPrice());
    }
}
