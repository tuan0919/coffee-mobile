package nlu.hcmuaf.android_coffee_app.mapper.response.cart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nlu.hcmuaf.android_coffee_app.dto.json.carts.CartItemJSON;
import nlu.hcmuaf.android_coffee_app.dto.json.carts.CartJSON;
import nlu.hcmuaf.android_coffee_app.dto.request.cart_controller.CartItemRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.cart_controller.CartItemResponseDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.cart_controller.CartResponseDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.cart_controller.ProductDTO;
import nlu.hcmuaf.android_coffee_app.entities.Cart;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;
import nlu.hcmuaf.android_coffee_app.exceptions.ProductException;
import nlu.hcmuaf.android_coffee_app.repositories.ProductRepository;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class CartResponseDTOMapper {
    protected ProductRepository productRepository;
    @Mapping(target = "items", source = "cartJSON", qualifiedByName = "mapItems")
    public abstract CartResponseDTO mapToDTO(Cart cart) throws CustomException;

    @Autowired
    protected void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Named("mapItems")
    public List<CartItemResponseDTO> mapToItemResponseDTOS(String json) throws CustomException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            var cartJSON = mapper.readValue(json, CartJSON.class);
            List<CartItemResponseDTO> list = new ArrayList<>();
            for (var entry : cartJSON.getDetails().entrySet()) {
                var oProduct = productRepository.findById(entry.getKey());
                if (oProduct.isEmpty())
                    throw new ProductException(String.format("Product with id %d is not found", entry.getKey()));
                var productDTO = ProductDTO.builder()
                        .id(entry.getKey())
                        .name(oProduct.get().getProductName())
                        .avatar(oProduct.get().getAvatar())
                        .price(oProduct.get().getBasePrice()).build();
                var itemDTO = CartItemResponseDTO.builder().size(entry.getValue().getSize())
                        .quantity(entry.getValue().getQuantity())
                        .ingredients(entry.getValue().getIngredients())
                        .product(productDTO).build();
                list.add(itemDTO);
            }
            return list;
        } catch (JsonProcessingException e) {
            throw new CustomException("JSON Parser error!");
        }
    }
}
