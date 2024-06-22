package nlu.hcmuaf.android_coffee_app.dto.response.cart_controller;

import lombok.*;
import lombok.experimental.FieldDefaults;
import nlu.hcmuaf.android_coffee_app.enums.EIngredient;
import nlu.hcmuaf.android_coffee_app.enums.EProductSize;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponseDTO2 {
    List<CartItemDTO> list;
    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CartItemDTO {
        CartResponseDTO2.ProductDTO product;
        int quantity;
        private List<EIngredient> ingredients;
        private EProductSize size;
    }
    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ProductDTO {
        private long id;
        private String name;
        private double price;
        private String avatar;
    }
}
