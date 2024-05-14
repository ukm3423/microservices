package com.productservice.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.productservice.dto.ProductRequest;
import com.productservice.dto.ProductResponse;
import com.productservice.models.Category;
import com.productservice.models.Product;
import com.productservice.repository.CategoryRepository;
import com.productservice.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private CategoryRepository catRepo;

	@Autowired
	private FileStorageService fileStorageService;

	public Product addProduct(ProductRequest req) throws IOException {

		String imagePath = fileStorageService.storeFile(req.getImage());

		Product product = Product.builder()
				.name(req.getName())
				.categoryId(req.getCategoryId())
				.price(req.getPrice())
				.createdAt(new Date())
				.updatedAt(new Date())
				.imagePath(imagePath)
				.build();

		productRepo.save(product);
		log.info("Product is saved successfully");
		return product;
	}

	public List<ProductResponse> getAllProducts() {
		List<Product> products = productRepo.findAll();

		// products.stream().map(product -> mapToProductResponse(product));
		List<ProductResponse> resp = products.stream().map(this::mapToProductResponse).toList();

		return resp;
	}

	private ProductResponse mapToProductResponse(Product product) {

		Category category = catRepo.findById(product.getCategoryId()).get();
		

		ProductResponse response = new ProductResponse();
		response.setId(product.getId());
		response.setCategoryName(category.getCategoryName());
		response.setName(product.getName());
		response.setPrice(product.getPrice());
		response.setCreatedAt(product.getCreatedAt());
		response.setUpdatedAt(product.getUpdatedAt());

		// Convert image to Base64 string and include it in the response
		String imagePath = product.getImagePath();
		if (imagePath != null && !imagePath.isEmpty()) {
			try {
				byte[] imageData = Files.readAllBytes(Paths.get(imagePath));
				String base64Image = Base64.getEncoder().encodeToString(imageData);
				response.setImage(base64Image);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return response;
	}

	public ProductResponse getProductById(Long id){
		Product product = productRepo.findById(id).get(); 
		ProductResponse resp = mapToProductResponse(product);

		return resp;
	}

	public Boolean deleteProductById(Long id){
		try{
			productRepo.deleteById(id);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}


}
