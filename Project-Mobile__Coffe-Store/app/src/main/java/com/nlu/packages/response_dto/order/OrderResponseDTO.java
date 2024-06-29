package com.nlu.packages.response_dto.order;

import com.nlu.packages.enums.*;
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
