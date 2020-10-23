package com.twuc.shopping.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twuc.shopping.Entity.OrderEntity;
import com.twuc.shopping.Entity.ProductEntity;
import com.twuc.shopping.Repository.OrderRepository;
import com.twuc.shopping.Repository.ProductRepository;
import com.twuc.shopping.dto.Order;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void startUp() {

    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
        productRepository.deleteAll();

    }

    @Test
    void should_return_order_list() throws Exception {
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].product.name", is("apple")));
    }

    @Test
    void should_delete_order_if_exist() throws Exception {
        mockMvc.perform(delete("/orders/1")).andExpect(status().isNoContent());
        List<OrderEntity> entities = orderRepository.findAll();
        assertEquals(entities.size(),1);
    }

    @Test
    void should_fail_delete_if_order_not_exist() throws Exception {
        mockMvc.perform(delete("/orders/3")).andExpect(status().isBadRequest());
        List<OrderEntity> entities = orderRepository.findAll();
        assertEquals(entities.size(),2);
    }

    @Test
    void should_add_order() throws Exception {

    }
}
