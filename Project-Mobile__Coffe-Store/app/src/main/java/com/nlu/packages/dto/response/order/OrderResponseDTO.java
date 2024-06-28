package com.nlu.packages.dto.response.order;

import com.nlu.packages.enums.EIngredient;
import com.nlu.packages.enums.EIngredientType;
import com.nlu.packages.enums.EOrderStatus;
import com.nlu.packages.enums.EPaymentMethod;
import com.nlu.packages.enums.EProductSize;
import com.nlu.packages.enums.EProductType;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponseDTO {
    List<OrderItemDTO> list;
    Double total;
    Integer count;
    StoreDTO store;
    EPaymentMethod payment;
    EOrderStatus status;
    Long orderId;
    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class OrderItemDTO {
        ProductDTO product;
        Integer quantity;
        List<IngredientDTO> ingredients;
        ProductSizeDTO size;
        Double price;
    }
    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ProductDTO {
        Long productId;
        String productName;
        Double basePrice;
        String avatar;
        EProductType productType;
    }
    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class IngredientDTO {
        String ingredientName;
        Double addPrice;
        EIngredient ingredientEnum;
        EIngredientType ingredientType;
    }
    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ProductSizeDTO {
        EProductSize sizeEnum;
        Double multiplier;
    }
    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class StoreDTO {
        Long storeId;
        String storeName;
        String address;
        String avatar;
    }
}
