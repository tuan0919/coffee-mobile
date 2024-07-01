package com.nlu.packages.response.cart;

import com.nlu.packages.enums.*;

import lombok.*;
import lombok.experimental.FieldDefaults;


import java.util.List;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponseDTO {
    List<CartItemDTO> list;
    double total;
    long count;
    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CartItemDTO {
        CartResponseDTO.ProductDTO product;
        int quantity;
        List<EIngredient> ingredients;
        EProductSize size;
        double price;
    }
    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ProductDTO {
        long id;
        String name;
        double price;
        String avatar;
    }
}
