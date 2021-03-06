package com.twuc.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @NotEmpty
    private String name;
    @NotNull
    private Integer price;
    @NotEmpty
    private String unit;
    @NotEmpty
    private String imgUrl;
    private Integer id;
    private int num;
}
