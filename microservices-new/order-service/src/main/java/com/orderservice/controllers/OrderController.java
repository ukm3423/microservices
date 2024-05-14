package com.orderservice.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.orderservice.dto.OrderRequest;
import com.orderservice.models.Order;
import com.orderservice.services.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    // @Value("${productservice.base.url}")
    // private String productBaseURL;

    @Autowired
    private OrderService orderService;

    @Autowired
    private WebClient webClient;

    private RestTemplate restTemplate;

    public OrderController(@Value("${productservice.base.url}") String productBaseURL, RestTemplateBuilder builder) {
        this.restTemplate = builder.rootUri(productBaseURL).build();
    }

    @GetMapping("/test")
    public Object test() {
        return restTemplate.getForObject("/category/get-category-list", Object.class);
    }

    @PostMapping("/place-order")
    public ResponseEntity<Map<String, Object>> placeOrder(@RequestBody OrderRequest req) {

        Order order = orderService.placeOrder(req);
        Map<String, Object> resp = new HashMap<String, Object>();
        resp.put("status", true);
        resp.put("message", "Order Place Successfully");
        resp.put("data", order);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PostMapping("/upload-file")
    public String getPath(@PathVariable("file") MultipartFile file) throws IOException {

        String UPLOAD_DIR = new ClassPathResource("/resources/static/").getFile().getAbsolutePath();

        // Files.copy(file.getInputStream(), Paths.get(UPLOAD_DIR + File.separator +
        // file.getOriginalFilename()),
        // StandardCopyOption.REPLACE_EXISTING);

        // return
        // ResponseEntity.ok(ServletUriComponentsBuilder.fromCurrentContextPath().path("/images/")
        // .path(file.getOriginalFilename()).toUriString());
        return UPLOAD_DIR;
    }

    @GetMapping("/test2")
    public Object test2() {
        return webClient.get().uri("/category/get-category-list")
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }
}
