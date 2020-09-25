package com.twuc.shopping.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twuc.shopping.Entity.ProductEntity;
import com.twuc.shopping.Repository.ProductRepository;
import com.twuc.shopping.dto.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void startUp() {
        ProductEntity apple = ProductEntity.builder().imgUrl("1").name("apple").price(1).unit("个").build();
        productRepository.save(apple);
    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    void should_get_product_list() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("apple")));
    }

    @Test
    void should_create_product_if_pass_validation() throws Exception {
        Product banana = Product.builder().name("banana").price(1).imgUrl("1").unit("个").build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(banana);
        mockMvc.perform(post("/products")
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        List<ProductEntity> productEntities = productRepository.findAll();
        assertEquals(productEntities.size(), 2);
        assertEquals(productEntities.get(1).getName(),"banana");
    }

    @Test
    void should_fail_if_product_name_exist() throws Exception {
        Product apple = Product.builder().name("apple").price(1).imgUrl("1").unit("个").build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(apple);
        mockMvc.perform(post("/products")
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
