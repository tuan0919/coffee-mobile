package nlu.hcmuaf.android_coffee_app.dto.response.product_controller;

import lombok.*;
import lombok.experimental.FieldDefaults;
import nlu.hcmuaf.android_coffee_app.entities.Ingredients;
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
public class IngredientResponseDTO {
    long ingredientId;
    String ingredientName;
    EIngredientType ingredientType;
    EIngredient ingredientEnum;
}
