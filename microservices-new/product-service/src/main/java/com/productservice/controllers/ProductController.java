package com.productservice.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.productservice.dto.ProductRequest;
import com.productservice.dto.ProductResponse;
import com.productservice.models.Product;
import com.productservice.services.ProductService;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

	public static String UPLOAD_DIRECTORY = System.getProperty("upload-dir") + "/products";

	@Value("${file.upload-dir}")
	private String uploadDir;

	@Autowired
	private ProductService productService;

	@CrossOrigin
	@PostMapping("/add")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Map<String, Object>> addProduct(@RequestParam String productName,
			@RequestParam Double price,
			@RequestParam Long categoryId,
			@RequestParam MultipartFile image) throws IOException {

		ProductRequest request = ProductRequest.builder()
				.name(productName)
				.price(price)
				.categoryId(categoryId)
				.image(image)
				.build();

		Product product = productService.addProduct(request);

		Map<String, Object> resp = new HashMap<String, Object>();
		resp.put("message", "Product added successfully");
		resp.put("data", product);
		resp.put("status", true);

		return ResponseEntity.status(HttpStatus.OK).body(resp);
	}



	@GetMapping("/get-product-list")
	public ResponseEntity<Map<String, Object>> getProductList() {
		Map<String, Object> resp = new HashMap<String, Object>();
		resp.put("data", productService.getAllProducts());
		resp.put("status", true);
		resp.put("message", "Retrieved All Products");

		return ResponseEntity.status(HttpStatus.OK).body(resp);
	}



	public String storeFile(MultipartFile file) throws IOException {

		Path directoryPath = Paths.get(uploadDir);
		Files.createDirectories(directoryPath);

		String fileName = generateFileName(file.getOriginalFilename());

		Path destinationPath = directoryPath.resolve(fileName);

		Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

		return destinationPath.toString();
	}

	private String generateFileName(String originalFileName) {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String formattedDateTime = now.format(formatter);
		return formattedDateTime + "-" + originalFileName;
	}


	@GetMapping("/get-product-by-id")
	public ResponseEntity<Map<String, Object>> getProductById(@RequestParam("productId") Long id) {

		ProductResponse product = productService.getProductById(id);

		Map<String, Object> resp = new HashMap<String, Object>();
		resp.put("data", product);
		resp.put("status", true);
		resp.put("message", "Retrieved All Products");

		return ResponseEntity.status(HttpStatus.OK).body(resp);
	}

	@GetMapping("/images")
    public byte[] getImage() {

		ProductResponse product = productService.getProductById(1L);

        // Decode Base64 string to get image bytes
        byte[] imageBytes = Base64.getDecoder().decode(product.getImage());

        // Assuming PNG format, change MediaType accordingly if needed
        return imageBytes;
    }


	@DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<Object, Object>> deleteProductById(@PathVariable Long id) {

        Boolean isdeleted = productService.deleteProductById(id);
        Map<Object, Object> resp = new HashMap<Object, Object>();

        if (isdeleted) {
            resp.put("message", "Deleted Successfully");
            resp.put("data", id);
            resp.put("status", true);
        } else {
            resp.put("status", false);
            resp.put("message", "Something went wrong !!");
            resp.put("data", id);
        }

        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

}
