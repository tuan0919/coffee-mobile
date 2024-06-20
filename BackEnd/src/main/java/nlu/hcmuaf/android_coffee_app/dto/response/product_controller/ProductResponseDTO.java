package nlu.hcmuaf.android_coffee_app.dto.response.product_controller;

import lombok.*;
import lombok.experimental.FieldDefaults;
import nlu.hcmuaf.android_coffee_app.enums.EIngredient;
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
    List<EProductSize> availableSizes;
    List<EIngredient> availableIngredients;
    EProductType productType;
    double discountPercent;
}
