package com.nlu.packages.response_dto.cart;

import com.nlu.packages.enums.EIngredient;
import com.nlu.packages.enums.EProductSize;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponseDTO implements Serializable {
    List<CartItemDTO> list;
    double total;
    long count;
    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CartItemDTO implements Serializable {
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
    public static class ProductDTO implements Serializable {
        long id;
        String name;
        double price;
        String avatar;
    }
}
