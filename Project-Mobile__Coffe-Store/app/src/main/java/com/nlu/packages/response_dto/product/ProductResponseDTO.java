package com.nlu.packages.response_dto.product;

import com.nlu.packages.enums.EIngredient;
import com.nlu.packages.enums.EIngredientType;
import com.nlu.packages.enums.EProductSize;
import com.nlu.packages.enums.EProductType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponseDTO {
    long productId;
    String productName;
    double basePrice;
    String avatar;
    long categoryId;
    String categoryName;
    List<ProductSizeDTO> availableSizes;
    List<IngredientDTO> availableIngredients;
    EProductType productType;
    double discountPercent;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class IngredientDTO {
        long ingredientId;
        String ingredientName;
        EIngredientType ingredientType;
        EIngredient ingredientEnum;
        double addPrice;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ProductSizeDTO {
        EProductSize sizeEnum;
        double multipler;
    }
}
