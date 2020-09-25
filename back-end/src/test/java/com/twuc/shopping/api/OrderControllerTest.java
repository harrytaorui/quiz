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
        ProductEntity apple = ProductEntity.builder().imgUrl("1").name("apple").price(1).unit("个").build();
        productRepository.save(apple);
        OrderEntity order1 = OrderEntity.builder().amount(4).productEntity(apple).build();
        orderRepository.save(order1);
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
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].product.name", is("apple")));
    }

    @Test
    void should_delete_order_if_exist() throws Exception {
        mockMvc.perform(delete("/orders/1")).andExpect(status().isNoContent());
        List<OrderEntity> entities = orderRepository.findAll();
        assertEquals(entities.size(),0);
    }

    @Test
    void should_fail_delete_if_order_not_exist() throws Exception {
        mockMvc.perform(delete("/orders/2")).andExpect(status().isBadRequest());
        List<OrderEntity> entities = orderRepository.findAll();
        assertEquals(entities.size(),1);
    }

    @Test
    void should_add_order() throws Exception {
        Product banana = Product.builder().name("banana").price(1).imgUrl("1").unit("个").build();
        ProductEntity bananaEntity = ProductEntity.builder().imgUrl("1").name("banana").price(1).unit("个").build();
        productRepository.save(bananaEntity);
        Order order = Order.builder().amount(4).product(banana).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(order);
        mockMvc.perform(post("/orders")
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        List<OrderEntity> entities = orderRepository.findAll();
        assertEquals(entities.size(),2);
        assertEquals(entities.get(1).getProductEntity().getName(),"banana");
    }
}
