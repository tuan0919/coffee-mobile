package nlu.hcmuaf.android_coffee_app.mapper.request.cart;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import nlu.hcmuaf.android_coffee_app.dto.json.carts.CartItemJSON;
import nlu.hcmuaf.android_coffee_app.dto.request.cart.CartItemRequestDTO;
import nlu.hcmuaf.android_coffee_app.enums.EIngredient;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-29T14:39:17+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class CartItemRequestDTOMapperImpl extends CartItemRequestDTOMapper {

    @Override
    public CartItemJSON mapToJSON(CartItemRequestDTO cartItemRequestDTO) {
        if ( cartItemRequestDTO == null ) {
            return null;
        }

        CartItemJSON.CartItemJSONBuilder cartItemJSON = CartItemJSON.builder();

        if ( cartItemRequestDTO.getProductId() != null ) {
            cartItemJSON.productId( cartItemRequestDTO.getProductId() );
        }
        if ( cartItemRequestDTO.getQuantity() != null ) {
            cartItemJSON.quantity( cartItemRequestDTO.getQuantity() );
        }
        cartItemJSON.size( cartItemRequestDTO.getSize() );
        List<EIngredient> list = cartItemRequestDTO.getIngredients();
        if ( list != null ) {
            cartItemJSON.ingredients( new ArrayList<EIngredient>( list ) );
        }

        return cartItemJSON.build();
    }
}
