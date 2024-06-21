package nlu.hcmuaf.android_coffee_app.dto.response.product_controller;

import lombok.*;
import lombok.experimental.FieldDefaults;
import nlu.hcmuaf.android_coffee_app.enums.EIngredient;
import nlu.hcmuaf.android_coffee_app.enums.EIngredientType;
import nlu.hcmuaf.android_coffee_app.enums.EProductSize;
import nlu.hcmuaf.android_coffee_app.enums.EProductType;

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
