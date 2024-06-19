package nlu.hcmuaf.android_coffee_app.service.templates;

import nlu.hcmuaf.android_coffee_app.dto.request.cart_controller.CartItemRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.cart_controller.CartResponseDTO;
import nlu.hcmuaf.android_coffee_app.entities.Cart;
import nlu.hcmuaf.android_coffee_app.entities.Categories;
import nlu.hcmuaf.android_coffee_app.entities.Products;
import nlu.hcmuaf.android_coffee_app.enums.EIngredient;
import nlu.hcmuaf.android_coffee_app.enums.EPaymentMethod;
import nlu.hcmuaf.android_coffee_app.enums.EProductSize;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;

import java.util.List;
import java.util.Optional;

public interface ICartService {
    void order(String username, long storeId, EPaymentMethod payment) throws CustomException;
    CartResponseDTO getCartDTO(String username) throws CustomException;
    void putItem(String username, CartItemRequestDTO requestDTO) throws CustomException;
}
