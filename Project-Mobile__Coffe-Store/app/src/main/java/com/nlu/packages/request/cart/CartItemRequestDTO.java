package com.nlu.packages.request.cart;

import com.nlu.packages.enums.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemRequestDTO {
    private Long productId;
    private Integer quantity;
    private EProductSize size;
    private List<EIngredient> ingredients;
}
