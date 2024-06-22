package nlu.hcmuaf.android_coffee_app.service.templates;

import nlu.hcmuaf.android_coffee_app.dto.request.cart_controller.CartItemRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.cart_controller.CartResponseDTO;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;

public interface ICartService {
    CartResponseDTO findCart(String username) throws CustomException;
    void updateCart(String username, CartItemRequestDTO requestDTO) throws CustomException;
}
