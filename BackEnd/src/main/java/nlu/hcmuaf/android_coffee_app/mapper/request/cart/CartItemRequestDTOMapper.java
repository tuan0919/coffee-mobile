package nlu.hcmuaf.android_coffee_app.mapper.request.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import nlu.hcmuaf.android_coffee_app.dto.json.carts.CartItemJSON;
import nlu.hcmuaf.android_coffee_app.dto.json.carts.CartJSON;
import nlu.hcmuaf.android_coffee_app.dto.request.cart_controller.CartItemRequestDTO;
import nlu.hcmuaf.android_coffee_app.entities.Cart;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.Optional;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class CartItemRequestDTOMapper {
    public abstract CartItemJSON mapToJSON(CartItemRequestDTO cartItemRequestDTO);
}
