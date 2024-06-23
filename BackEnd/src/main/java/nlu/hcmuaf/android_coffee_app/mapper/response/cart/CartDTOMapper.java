package nlu.hcmuaf.android_coffee_app.mapper.response.cart;

import nlu.hcmuaf.android_coffee_app.dto.request.cart.CartItemRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.cart.CartResponseDTO;
import nlu.hcmuaf.android_coffee_app.entities.*;
import nlu.hcmuaf.android_coffee_app.enums.EIngredient;
import nlu.hcmuaf.android_coffee_app.enums.EProductSize;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;
import nlu.hcmuaf.android_coffee_app.exceptions.IngredientException;
import nlu.hcmuaf.android_coffee_app.exceptions.ProductException;
import nlu.hcmuaf.android_coffee_app.exceptions.ProductSizeException;
import nlu.hcmuaf.android_coffee_app.repositories.CartItemRepository;
import nlu.hcmuaf.android_coffee_app.repositories.IngredientRepository;
import nlu.hcmuaf.android_coffee_app.repositories.ProductRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
builder = @Builder(disableBuilder = true))
public abstract class CartDTOMapper {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    IngredientRepository ingredientRepository;
    @Autowired
    CartItemRepository cartItemRepository;

    @Mapping(target = "list", source = "cart.cartItemsSet", qualifiedByName = "findCartItems")
    @Mapping(target = "count", ignore = true)
    @Mapping(target = "total", ignore = true)
    public abstract CartResponseDTO mapCartToDTO(Cart cart);
    public CartResponseDTO mapSetCartItemToDTO(Set<CartItems> items) {
        var list = findCartItems(items);
        var instance = CartResponseDTO.builder()
                .list(list)
                .build();
        afterMapCartDTO(instance);
        return instance;
    }
    @Mapping(target = "id", source = "product.productId")
    @Mapping(target = "name", source = "product.productName")
    @Mapping(target = "price", source = "product.basePrice")
    @Named("findProduct")
    abstract CartResponseDTO.ProductDTO findProduct(Products product);

    @Mapping(target = "ingredients", qualifiedByName = "findIngredientEnums", source = "item.ingredientsSet")
    @Mapping(target = "product", source = "item.products", qualifiedByName = "findProduct")
    abstract CartResponseDTO.CartItemDTO findCartItem(CartItems item);

    @Named("findCartItems")
    abstract List<CartResponseDTO.CartItemDTO> findCartItems(Set<CartItems> items);

    @Named("findIngredientEnums")
    List<EIngredient> findIngredientEnums(Set<CartAddIngredients> ingredients) {
        return ingredients.stream()
                .map(CartAddIngredients::getIngredients)
                .map(Ingredients::getIngredientEnum)
                .toList();
    }

    @AfterMapping
    void afterMapCartDTO(@MappingTarget CartResponseDTO cartDTO) {
        double total = cartDTO.getList().stream()
                .mapToDouble(CartResponseDTO.CartItemDTO::getPrice)
                .sum();
        cartDTO.setTotal(total);
        cartDTO.setCount(cartDTO.getList().size());
    }
    public CartItems mapToModel(Cart cart, CartItemRequestDTO cartItemRequestDTO) throws CustomException {
        CartItems item = new CartItems();
        long productId = cartItemRequestDTO.getProductId();
        int quantity = cartItemRequestDTO.getQuantity();
        if (!productRepository.existsById(productId))
            throw new ProductException("Product with id " + productId + " does not exist",
                    HttpStatus.NOT_FOUND.value());
        Products product = productRepository.findById(productId).get();
        if (!product.getSizeSet().stream()
                .map(HavingSizes::getSize)
                .collect(Collectors.toSet())
                .contains(cartItemRequestDTO.getSize()))
            throw new ProductSizeException(String.format("Product with this id %d doesn't have this size",
                    HttpStatus.NOT_FOUND.value()));
        EProductSize size = cartItemRequestDTO.getSize();
        if (!product.getIngredientsSet().stream()
                .map(HavingIngredients::getIngredients)
                .map(Ingredients::getIngredientEnum)
                .collect(Collectors.toSet())
                .containsAll(cartItemRequestDTO.getIngredients()))
            throw new IngredientException(String.format("Product with this id %d doesn't have some of your ingredients",
                    productId), 404);
        Set<CartAddIngredients> set = new HashSet<>();
        set.addAll(cartItemRequestDTO.getIngredients().stream()
                .map(ie -> {
                    Ingredients ingredient = ingredientRepository.findByEnum(ie).get();
                    return CartAddIngredients.builder().cartItems(item).ingredients(ingredient).build();
                }).toList());
        double price = product.getBasePrice() * quantity * size.getMultipler() + set.stream()
                .mapToDouble(hv -> hv.getIngredients().getAddPrice())
                .sum();
        item.setProducts(product);
        item.setSize(size);
        item.setIngredientsSet(set);
        item.setPrice(price);
        item.setQuantity(quantity);
        item.setCart(cart);
        return item;
    }
}

