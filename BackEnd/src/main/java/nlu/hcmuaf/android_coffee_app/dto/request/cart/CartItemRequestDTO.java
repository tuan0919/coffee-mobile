package nlu.hcmuaf.android_coffee_app.dto.request.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlu.hcmuaf.android_coffee_app.enums.EIngredient;
import nlu.hcmuaf.android_coffee_app.enums.EProductSize;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemRequestDTO {
    private Long productId;
    private Integer quantity;
    private EProductSize size;
    private List<EIngredient> ingredients;
}
