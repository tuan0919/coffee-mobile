package nlu.hcmuaf.android_coffee_app.dto.json.ingredient;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlu.hcmuaf.android_coffee_app.enums.EIngredient;
import nlu.hcmuaf.android_coffee_app.enums.EIngredientType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientJSON {
    @JsonProperty("id")
    private long ingredientId;
    @JsonProperty("name")
    private String ingredientName;
    @JsonProperty("add_price")
    private double addPrice;
    @JsonProperty("type")
    private EIngredientType ingredientType;
    @JsonProperty("enum")
    private EIngredient ingredientEnum;
}
