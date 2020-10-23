package com.twuc.shopping.Service;


import com.twuc.shopping.Entity.ProductEntity;
import com.twuc.shopping.Repository.ProductRepository;
import com.twuc.shopping.dto.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    final
    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        List<ProductEntity> productList = productRepository.findAll();
        return productList.stream().map(e -> Product.builder()
                .name(e.getName()).price(e.getPrice())
                .unit(e.getUnit()).imgUrl(e.getImgUrl())
                .build()).collect(Collectors.toList());
    }

    public ProductEntity createProduct(Product product) {
        String name = product.getName();
        Optional<ProductEntity> result = productRepository.findByName(name);
        if (result.isPresent()) {
            return null;
        }
        ProductEntity productEntity = ProductEntity.builder()
                .name(product.getName())
                .price(product.getPrice())
                .unit(product.getUnit())
                .imgUrl(product.getImgUrl()).build();
        productRepository.save(productEntity);
        return productEntity;
    }
}
