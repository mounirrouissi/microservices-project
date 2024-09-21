package com.mr.microservices.product.service;

import com.mr.microservices.product.dto.ProductRequest;
import com.mr.microservices.product.dto.ProductResponse;
import com.mr.microservices.product.model.Product;
import com.mr.microservices.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .id(productRequest.id())
                .name(productRequest.name())
                .description(productRequest.description())
                .skuCode(productRequest.skuCode())
                .price(productRequest.price())
                .build();
        productRepository.save(product);
        log.info("Product created successfully");
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(),
                product.getSkuCode(),
                product.getPrice());
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(),
                        product.getSkuCode(),
                        product.getPrice()))
                .toList();
    }

    public ProductResponse updateProduct(String id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .map(
                        product1 -> {
                            product1.setName(productRequest.name());
                            product1.setDescription(productRequest.description());
                            product1.setSkuCode(productRequest.skuCode());
                            product1.setPrice(productRequest.price());
                            return product1;
                        }).orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.save(product);
        log.info("Product updated successfully");
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(),
                product.getSkuCode(),
                product.getPrice());
    }
    }


