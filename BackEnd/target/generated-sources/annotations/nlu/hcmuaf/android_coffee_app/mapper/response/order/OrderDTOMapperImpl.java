package nlu.hcmuaf.android_coffee_app.mapper.response.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import nlu.hcmuaf.android_coffee_app.dto.response.order.OrderResponseDTO;
import nlu.hcmuaf.android_coffee_app.entities.Ingredients;
import nlu.hcmuaf.android_coffee_app.entities.OrderAddIngredients;
import nlu.hcmuaf.android_coffee_app.entities.OrderItems;
import nlu.hcmuaf.android_coffee_app.entities.Orders;
import nlu.hcmuaf.android_coffee_app.entities.Products;
import nlu.hcmuaf.android_coffee_app.entities.Stores;
import nlu.hcmuaf.android_coffee_app.enums.EProductSize;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-30T16:05:30+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class OrderDTOMapperImpl extends OrderDTOMapper {

    @Override
    public OrderResponseDTO mapToDTO(Orders order) {
        if ( order == null ) {
            return null;
        }

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();

        orderResponseDTO.setTotal( order.getTotalPrice() );
        orderResponseDTO.setList( mapOrderItemsDTO( order.getOrderItemsSet() ) );
        orderResponseDTO.setStore( mapStoreDTO( order.getStores() ) );
        orderResponseDTO.setPayment( order.getPayment() );
        orderResponseDTO.setStatus( order.getStatus() );
        orderResponseDTO.setOrderId( order.getOrderId() );

        orderResponseDTO.setCount( order.getOrderItemsSet().size() );

        return orderResponseDTO;
    }

    @Override
    List<OrderResponseDTO.OrderItemDTO> mapOrderItemsDTO(Set<OrderItems> set) {
        if ( set == null ) {
            return null;
        }

        List<OrderResponseDTO.OrderItemDTO> list = new ArrayList<OrderResponseDTO.OrderItemDTO>( set.size() );
        for ( OrderItems orderItems : set ) {
            list.add( mapOrderItemDTO( orderItems ) );
        }

        return list;
    }

    @Override
    public OrderResponseDTO.OrderItemDTO mapOrderItemDTO(OrderItems item) {
        if ( item == null ) {
            return null;
        }

        OrderResponseDTO.OrderItemDTO orderItemDTO = new OrderResponseDTO.OrderItemDTO();

        orderItemDTO.setProduct( mapProductDTO( item.getProducts() ) );
        orderItemDTO.setSize( mapSizeEnumDTO( item.getSize() ) );
        orderItemDTO.setIngredients( mapIngredientsSetDTO( item.getIngredientsSet() ) );
        orderItemDTO.setQuantity( item.getQuantity() );
        orderItemDTO.setPrice( item.getPrice() );

        return orderItemDTO;
    }

    @Override
    OrderResponseDTO.ProductDTO mapProductDTO(Products product) {
        if ( product == null ) {
            return null;
        }

        OrderResponseDTO.ProductDTO productDTO = new OrderResponseDTO.ProductDTO();

        productDTO.setProductId( product.getProductId() );
        productDTO.setProductName( product.getProductName() );
        productDTO.setBasePrice( product.getBasePrice() );
        productDTO.setAvatar( product.getAvatar() );
        productDTO.setProductType( product.getProductType() );

        return productDTO;
    }

    @Override
    OrderResponseDTO.ProductSizeDTO mapSizeEnumDTO(EProductSize sizeEnum) {
        if ( sizeEnum == null ) {
            return null;
        }

        OrderResponseDTO.ProductSizeDTO productSizeDTO = new OrderResponseDTO.ProductSizeDTO();

        productSizeDTO.setSizeEnum( sizeEnum );
        productSizeDTO.setMultiplier( (double) sizeEnum.getMultipler() );

        return productSizeDTO;
    }

    @Override
    List<OrderResponseDTO.IngredientDTO> mapIngredientsSetDTO(Set<OrderAddIngredients> set) {
        if ( set == null ) {
            return null;
        }

        List<OrderResponseDTO.IngredientDTO> list = new ArrayList<OrderResponseDTO.IngredientDTO>( set.size() );
        for ( OrderAddIngredients orderAddIngredients : set ) {
            list.add( mapIngredientDTO( mapAddIngredientToIngredient( orderAddIngredients ) ) );
        }

        return list;
    }

    @Override
    OrderResponseDTO.IngredientDTO mapIngredientDTO(Ingredients ingredient) {
        if ( ingredient == null ) {
            return null;
        }

        OrderResponseDTO.IngredientDTO ingredientDTO = new OrderResponseDTO.IngredientDTO();

        ingredientDTO.setIngredientName( ingredient.getIngredientName() );
        ingredientDTO.setAddPrice( ingredient.getAddPrice() );
        ingredientDTO.setIngredientEnum( ingredient.getIngredientEnum() );
        ingredientDTO.setIngredientType( ingredient.getIngredientType() );

        return ingredientDTO;
    }

    @Override
    OrderResponseDTO.StoreDTO mapStoreDTO(Stores store) {
        if ( store == null ) {
            return null;
        }

        OrderResponseDTO.StoreDTO storeDTO = new OrderResponseDTO.StoreDTO();

        storeDTO.setAddress( mapAddressToString( store.getAddress() ) );
        storeDTO.setStoreId( store.getStoreId() );
        storeDTO.setStoreName( store.getStoreName() );
        storeDTO.setAvatar( store.getAvatar() );

        return storeDTO;
    }
}
