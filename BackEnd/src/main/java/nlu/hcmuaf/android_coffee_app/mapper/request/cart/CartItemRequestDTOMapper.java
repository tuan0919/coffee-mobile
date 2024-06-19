package nlu.hcmuaf.android_coffee_app.mapper.request.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import nlu.hcmuaf.android_coffee_app.dto.json.carts.CartItemJSON;
import nlu.hcmuaf.android_coffee_app.dto.json.carts.CartJSON;
import nlu.hcmuaf.android_coffee_app.dto.request.cart_controller.CartItemRequestDTO;
import nlu.hcmuaf.android_coffee_app.entities.Cart;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;
import nlu.hcmuaf.android_coffee_app.mapper.response.cart.CartResponseDTOMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class CartItemRequestDTOMapper {
    public abstract CartItemJSON mapToJSON(CartItemRequestDTO cartItemRequestDTO);
    public void updateFromDTO(@MappingTarget Cart cart, CartItemRequestDTO cartItemRequestDTO) throws CustomException {
        int quantity = cartItemRequestDTO.getQuantity();
        long productId = cartItemRequestDTO.getProductId();
        var mapper = new ObjectMapper();
        try {
            var cartJSON = mapper.readValue(cart.getCartJSON(), CartJSON.class);
            if (quantity == 0)
                cartJSON.getDetails().remove(productId);
            else {
                var oItem = Optional.ofNullable(cartJSON.getDetails().get(productId));
                CartItemJSON item = oItem.orElse(mapToJSON(cartItemRequestDTO));
                cartJSON.getDetails().put(productId, item);
            }
            cart.setCartJSON(mapper.writeValueAsString(cartJSON));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
