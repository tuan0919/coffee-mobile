package com.nlu.packages.response_dto.cart;

import com.nlu.packages.enums.EIngredient;
import com.nlu.packages.enums.EProductSize;
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
