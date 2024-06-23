package nlu.hcmuaf.android_coffee_app.mapper.request.cart;

import nlu.hcmuaf.android_coffee_app.dto.json.carts.CartItemJSON;
import nlu.hcmuaf.android_coffee_app.dto.request.cart.CartItemRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class CartItemRequestDTOMapper {
    public abstract CartItemJSON mapToJSON(CartItemRequestDTO cartItemRequestDTO);
}
