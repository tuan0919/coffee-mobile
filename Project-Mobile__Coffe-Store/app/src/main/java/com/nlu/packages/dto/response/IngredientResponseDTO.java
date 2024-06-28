package nlu.hcmuaf.android_coffee_app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientResponseDTO {
    private long ingredientId;
    private String name;
    private double addPrice;
}
