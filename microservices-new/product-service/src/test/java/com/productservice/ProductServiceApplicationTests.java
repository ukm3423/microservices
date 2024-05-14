package com.productservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.productservice.dto.ProductRequest;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Container
	static PostgreSQLContainer<?> pgContainer = new PostgreSQLContainer<>("pgvector/pgvector:pg16");

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", pgContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", pgContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", pgContainer::getPassword);
    }

	@Autowired
	private MockMvc mocMvc;

	// @Test
	// void shouldCreateProduct() throws Exception{

	// 	getProductRequest();

	// 	mocMvc.perform(MockMvcRequestBuilders.post("products")
	// 			.contentType(MediaType.APPLICATION_JSON)
	// 			.content(null);
		
	// 	);
	// }

	// private ProductRequest getProductRequest() {
	// 	return ProductRequest.builder()
	// 				.name("Watch")
	// 				.description("This is an apple watch product")
	// 				.price(2500.2342364)
	// 				.build();
	// }

	@Test
	void contextLoads() {
	}

}
