package com.twuc.shopping.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.twuc.shopping.Entity.ProductEntity;
import com.twuc.shopping.Service.ProductService;
import com.twuc.shopping.dto.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {

    final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> productList = productService.getProducts();
        return ResponseEntity.ok().body(productList);
    }

    @Transactional
    @PostMapping("/products")
    public ResponseEntity createProduct(@Valid @RequestBody Product product,
                                        BindingResult bindingResult)
            throws JsonProcessingException {
        if (bindingResult.getFieldErrors().size() > 0) {
            return ResponseEntity.badRequest().body("参数不合法");
        }
        ProductEntity productEntity = productService.createProduct(product);
        if (productEntity == null) {
            return ResponseEntity.badRequest().body("商品名称已存在，请输入新的商品名称");
        }
        return ResponseEntity.status(201)
                .header("index", String.valueOf(productEntity.getId())).build();
    }

}

