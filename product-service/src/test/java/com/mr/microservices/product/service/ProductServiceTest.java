package com.mr.microservices.product.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.mr.microservices.product.dto.ProductRequest;
import com.mr.microservices.product.dto.ProductResponse;
import com.mr.microservices.product.model.Product;
import com.mr.microservices.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct() {
        ProductRequest request = new ProductRequest("1", "Product X", "Description", "SKU123", BigDecimal.TEN);
        Product product = Product.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .skuCode(request.skuCode())
                .price(request.price())
                .build();

        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse response = productService.createProduct(request);

        assertNotNull(response);
        assertEquals(product.getId(), response.id());
        assertEquals(product.getName(), response.name());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void getAllProducts() {

    //given
        List<Product> products = Arrays.asList(
                Product.builder().id("1").name("Product 1").build(),
                Product.builder().id("2").name("Product 2").build()
        );

    //when

        when(productRepository.findAll()).thenReturn(products);

        List<ProductResponse> responses = productService.getAllProducts();

        //then
        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals(products.get(0).getId(), responses.get(0).id());
        assertEquals(products.get(1).getName(), responses.get(1).name());
    }

    @Test
    void updateProduct() {
        String productId = "1";
        ProductRequest request = new ProductRequest("Test Product", "Product X", "Description", "SKU123", BigDecimal.TEN);
        Product existingProduct = Product.builder()
                .id(productId)
                .name("Old Product")
                .description("Old Description")
                .skuCode("SKU123")
                .price(BigDecimal.TEN)
               .build();
        Product updatedProduct = Product.builder()
               .id(productId)
               .name(request.name())
               .description(request.description())
               .skuCode(request.skuCode())
               .price(request.price())
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);
        ProductResponse response = productService.updateProduct(productId, request);
        assertNotNull(response);
        assertEquals(updatedProduct.getId(), response.id());
        assertEquals(updatedProduct.getName(), response.name());
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(any(Product.class));
    }
    }
