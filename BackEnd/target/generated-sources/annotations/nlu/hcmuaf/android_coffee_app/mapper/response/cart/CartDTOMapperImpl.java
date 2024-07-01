package nlu.hcmuaf.android_coffee_app.mapper.response.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import nlu.hcmuaf.android_coffee_app.dto.response.cart.CartResponseDTO;
import nlu.hcmuaf.android_coffee_app.entities.Cart;
import nlu.hcmuaf.android_coffee_app.entities.CartItems;
import nlu.hcmuaf.android_coffee_app.entities.Products;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-30T16:05:31+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class CartDTOMapperImpl extends CartDTOMapper {

    @Override
    public CartResponseDTO mapCartToDTO(Cart cart) {
        if ( cart == null ) {
            return null;
        }

        CartResponseDTO cartResponseDTO = new CartResponseDTO();

        cartResponseDTO.setList( findCartItems( cart.getCartItemsSet() ) );

        afterMapCartDTO( cartResponseDTO );

        return cartResponseDTO;
    }

    @Override
    CartResponseDTO.ProductDTO findProduct(Products product) {
        if ( product == null ) {
            return null;
        }

        CartResponseDTO.ProductDTO productDTO = new CartResponseDTO.ProductDTO();

        productDTO.setId( product.getProductId() );
        productDTO.setName( product.getProductName() );
        productDTO.setPrice( product.getBasePrice() );
        productDTO.setAvatar( product.getAvatar() );

        return productDTO;
    }

    @Override
    CartResponseDTO.CartItemDTO findCartItem(CartItems item) {
        if ( item == null ) {
            return null;
        }

        CartResponseDTO.CartItemDTO cartItemDTO = new CartResponseDTO.CartItemDTO();

        cartItemDTO.setIngredients( findIngredientEnums( item.getIngredientsSet() ) );
        cartItemDTO.setProduct( findProduct( item.getProducts() ) );
        if ( item.getQuantity() != null ) {
            cartItemDTO.setQuantity( item.getQuantity() );
        }
        cartItemDTO.setSize( item.getSize() );
        if ( item.getPrice() != null ) {
            cartItemDTO.setPrice( item.getPrice() );
        }

        return cartItemDTO;
    }

    @Override
    List<CartResponseDTO.CartItemDTO> findCartItems(Set<CartItems> items) {
        if ( items == null ) {
            return null;
        }

        List<CartResponseDTO.CartItemDTO> list = new ArrayList<CartResponseDTO.CartItemDTO>( items.size() );
        for ( CartItems cartItems : items ) {
            list.add( findCartItem( cartItems ) );
        }

        return list;
    }
}
