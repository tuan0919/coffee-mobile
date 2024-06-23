package nlu.hcmuaf.android_coffee_app.mapper.response.order;

import nlu.hcmuaf.android_coffee_app.dto.request.order.CreateOrderRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.order.OrderResponseDTO;
import nlu.hcmuaf.android_coffee_app.entities.*;
import nlu.hcmuaf.android_coffee_app.enums.EOrderStatus;
import nlu.hcmuaf.android_coffee_app.enums.EPaymentMethod;
import nlu.hcmuaf.android_coffee_app.enums.EProductSize;
import nlu.hcmuaf.android_coffee_app.exceptions.CartException;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;
import nlu.hcmuaf.android_coffee_app.exceptions.StoreException;
import nlu.hcmuaf.android_coffee_app.repositories.CartRepository;
import nlu.hcmuaf.android_coffee_app.repositories.OrderItemRepository;
import nlu.hcmuaf.android_coffee_app.repositories.StoreRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public abstract class OrderDTOMapper {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    StoreRepository storeRepository;

    public Orders updateOrder(Orders order, CreateOrderRequestDTO dto) throws CustomException {
        Users user = order.getUser();
        Set<CartItems> cartItemsSet = user.getCart().getCartItemsSet();
        Map<Long, CartItems> mapProductsInCart = cartItemsSet.stream()
                .collect(Collectors.toMap(cItem -> cItem.getProducts().getProductId(), cItem -> cItem));

        // Build Set of Order Items
        Set<OrderItems> orderItemsSet = new HashSet<>();
        CartItems cartItem = null;
        Products product = null;
        Set<OrderAddIngredients> ingredientsSet = null;
        Double price = 0d;
        Integer quantity = 0;
        EProductSize size = null;
        for (Long productId : dto.getChosenProductIds()) {
            if (!mapProductsInCart.containsKey(productId))
                throw new CartException(String.format("Item with id :%d is not in your cart", productId), 404);
            cartItem = mapProductsInCart.get(productId);

            // Create OrderItem
            OrderItems orderItem = new OrderItems();
            product = cartItem.getProducts();
            ingredientsSet = cartItem.getIngredientsSet().stream()
                    .map(cItem -> new OrderAddIngredients(orderItem, cItem.getIngredients()))
                    .collect(Collectors.toSet());
            size = cartItem.getSize();
            price = cartItem.getPrice();
            quantity = cartItem.getQuantity();

            // Build orderItem
            orderItem.setOrders(order);
            orderItem.setProducts(product);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(price);
            orderItem.setSize(size);
            orderItem.setIngredientsSet(ingredientsSet);

            // Add to orderSet
            orderItemsSet.add(orderItem);
        }

        // Get Store
        Long storeId = dto.getStoreId();
        Optional<Stores> oStore = storeRepository.findById(storeId);
        if (!oStore.isPresent())
            throw new StoreException(String.format("Store with this id: %d is not exists", storeId), 404);
        Stores store = oStore.get();
        EPaymentMethod method = dto.getMethod();

        // Get Total Price of this Order;
        Double totalPrice = dto.getChosenProductIds().stream()
                .map(mapProductsInCart::get)
                .mapToDouble(CartItems::getPrice)
                .sum();

        // Build Order
        order.setOrderItemsSet(orderItemsSet);
        order.setStatus(EOrderStatus.CONFIRMING);
        order.setPayment(method);
        order.setTotalPrice(totalPrice);
        order.setStores(store);

        return order;
    }

    @Mapping(target = "total", source = "order.totalPrice")
    @Mapping(target = "count", expression = "java(order.getOrderItemsSet().size())")
    @Mapping(target = "list", source = "order.orderItemsSet", qualifiedByName = "mapOrderItemsDTO")
    @Mapping(target = "store", source = "order.stores", qualifiedByName = "mapStoreDTO")
    public abstract OrderResponseDTO mapToDTO(Orders order);

    @Named("mapOrderItemsDTO")
    abstract List<OrderResponseDTO.OrderItemDTO> mapOrderItemsDTO(Set<OrderItems> set);

    @Mapping(target = "product", source = "item.products", qualifiedByName = "mapProductDTO")
    @Mapping(target = "size", source = "item.size", qualifiedByName = "mapSizeEnumDTO")
    @Mapping(target = "ingredients", source = "item.ingredientsSet", qualifiedByName = "mapIngredientsSetDTO")
    public abstract OrderResponseDTO.OrderItemDTO mapOrderItemDTO(OrderItems item);

    @Named("mapProductDTO")
    abstract OrderResponseDTO.ProductDTO mapProductDTO(Products product);

    @Named("mapSizeEnumDTO")
    @Mapping(target = "sizeEnum", source = "sizeEnum")
    @Mapping(target = "multiplier", source = "sizeEnum.multipler")
    abstract OrderResponseDTO.ProductSizeDTO mapSizeEnumDTO(EProductSize sizeEnum);

    @Named("mapIngredientsSetDTO")
    abstract List<OrderResponseDTO.IngredientDTO> mapIngredientsSetDTO(Set<OrderAddIngredients> set);

    abstract OrderResponseDTO.IngredientDTO mapIngredientDTO(Ingredients ingredient);
    Ingredients mapAddIngredientToIngredient(OrderAddIngredients addIngredient) {
        return addIngredient.getIngredients();
    }

    @Named("mapStoreDTO")
    @Mapping(target = "address", source = "store.address", qualifiedByName = "mapAddressToString")
    abstract OrderResponseDTO.StoreDTO mapStoreDTO(Stores store);

    @Named("mapAddressToString")
    String mapAddressToString(Addresses address) {
        return String.format("%s, %s, %s, %s", address.getCity(), address.getDistrict(), address.getWard(), address.getStreet());
    }
}
