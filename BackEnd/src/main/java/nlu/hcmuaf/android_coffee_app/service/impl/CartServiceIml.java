package nlu.hcmuaf.android_coffee_app.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import nlu.hcmuaf.android_coffee_app.dto.json.carts.CartJSON;
import nlu.hcmuaf.android_coffee_app.dto.request.cart_controller.CartItemRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.cart_controller.CartResponseDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.cart_controller.CartResponseDTO2;
import nlu.hcmuaf.android_coffee_app.entities.*;
import nlu.hcmuaf.android_coffee_app.enums.EIngredient;
import nlu.hcmuaf.android_coffee_app.enums.EOrderStatus;
import nlu.hcmuaf.android_coffee_app.enums.EPaymentMethod;
import nlu.hcmuaf.android_coffee_app.enums.EProductSize;
import nlu.hcmuaf.android_coffee_app.exceptions.*;
import nlu.hcmuaf.android_coffee_app.mapper.request.cart.CartItemRequestDTOMapper;
import nlu.hcmuaf.android_coffee_app.mapper.response.cart.CartDTOMapper;
import nlu.hcmuaf.android_coffee_app.mapper.response.cart.CartResponseDTOMapper;
import nlu.hcmuaf.android_coffee_app.repositories.*;
import nlu.hcmuaf.android_coffee_app.service.templates.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartServiceIml implements ICartService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private CartResponseDTOMapper cartResponseDTOMapper;
    @Autowired
    private CartItemRequestDTOMapper cartItemRequestDTOMapper;
    @Autowired
    private CartDTOMapper cartDTOMapper;

    public boolean checkHavingSize(long productId, EProductSize size) throws CustomException {
        var sizeSet = productRepository.findProductSizes(productId);
        if (sizeSet.isEmpty())
            throw new ProductSizeException(String.format("Product with id: %d has no size.", productId));
        return sizeSet.contains(size);
    }

    public boolean checkHavingIngredients(long productId, List<EIngredient> list) throws CustomException {
        var ingredientSet = productRepository.findProductIngredients(productId);
        if (ingredientSet.isEmpty())
            throw new IngredientException(String.format("Product with id: %d has no ingredient.", productId));
        for (var ie : list) if (!ingredientSet.contains(ie)) return false;
        return true;
    }

    public boolean checkUserExists(String username) {
        var user = userRepository.findUsersByUsername(username);
        return user.isPresent();
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void putItem(String username, CartItemRequestDTO requestDTO) throws CustomException {
        if (!checkUserExists(username))
            throw new CustomException(String.format("User with username: %s doesn't exist.", username));
        long productId = requestDTO.getProductId();
        EProductSize productSize = requestDTO.getSize();
        if (!checkHavingSize(productId, productSize))
            throw new ProductSizeException(String.format("Product with id: %d doesn't have this size: %s.", productId, productSize));
        List<EIngredient> ingredients = requestDTO.getIngredients();
        if (!checkHavingIngredients(productId, ingredients))
            throw new IngredientException(String.format("Product with id: %d doesn't have some of ingredients.", productId));
        var cart = cartRepository.findCartByUsername(username).get();
        cartItemRequestDTOMapper.updateFromDTO(cart, requestDTO);
        cartRepository.save(cart);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void putItem2(String username, CartItemRequestDTO requestDTO) throws CustomException {
        var oCart = cartRepository.findCartByUsername(username);
        if (!oCart.isPresent())
            throw new CartException("Cart doesn't exist.");
        Cart cart = oCart.get();
        CartItems item = cartDTOMapper.mapToModel(cart, requestDTO);
        var updateSet = cart.getCartItemsSet();
        System.out.println(updateSet.remove(item));
        if (item.getQuantity() != 0) updateSet.add(item);
        cartRepository.save(cart);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void order(String username, long storeId, EPaymentMethod payment) throws CustomException {
        var user = userRepository.findUsersByUsername(username);
        if (user.isEmpty()) throw new UserInfoException("User with this username is not found.");
        var cart = cartRepository.findCartByUserId(user.get().getUserId());
        ObjectMapper mapper = new ObjectMapper();
        try {
//            Cart's JSON
            var cartJSON = mapper.readValue(cart.get().getCartJSON(), CartJSON.class);
            if (cartJSON.getDetails().entrySet().size() == 0) throw new CartException("User's cart is empty!");
//            create Order
            var order = new Orders();
            orderRepository.save(order);
            Set<OrderItems> items = new HashSet<>();
            for (var entry : cartJSON.getDetails().entrySet()) {
//                create Order Items
                var orderItems = new OrderItems();
                var oProduct = productRepository.findById(entry.getKey());
                if (oProduct.isEmpty()) throw new ProductException("Product with this productId is not found.");
                orderItems.setSize(entry.getValue().getSize());
                orderItems.setQuantity(entry.getValue().getQuantity());
                orderItems.setProducts(oProduct.get());
                orderItems.setPrice(oProduct.get().getBasePrice() * entry.getValue().getQuantity());
                orderItems.setOrders(order);
//                Create Ingredient Set for Order items
                Set<OrderAddIngredients> addSet = new HashSet<>();
                for (var ie : entry.getValue().getIngredients()) {
                    var oIE = ingredientRepository.findByEnum(ie);
                    if (oIE.isEmpty()) throw new IngredientException("Ingredient is not found.");
                    var add = new OrderAddIngredients();
                    add.setOrderItems(orderItems);
                    add.setIngredients(oIE.get());
                    addSet.add(add);
                }
                orderItems.setIngredientsSet(addSet);
                orderItemRepository.save(orderItems);
                items.add(orderItems);
            }
            order.setStatus(EOrderStatus.CONFIRMING);
            order.setTotalPrice(items.stream().mapToDouble(o -> o.getPrice())
                    .reduce(0f, (item1, item2) -> item1 + item2));
            order.setOrderItemsSet(items);
            var oStore = storeRepository.findById(storeId);
            if (oStore.isEmpty()) throw new StoreException("Store with this storeId is not found.");
            order.setStores(oStore.get());
            order.setPayments(payment);
            orderRepository.save(order);
        } catch (JsonProcessingException e) {
            throw new CustomException("Error while parsing JSON");
        }
    }

    @Override
    public CartResponseDTO getCartDTO(String username) throws CustomException {
        if (!checkUserExists(username))
            throw new UserInfoException(String.format("User with username: %s doesn't exist.", username));
        var cart = cartRepository.findCartByUsername(username).get();
        return cartResponseDTOMapper.mapToDTO(cart);
    }

    @Override
    public CartResponseDTO2 getCartDTO2(String username) throws CustomException {
        if (!checkUserExists(username))
            throw new UserInfoException(String.format("User with username: %s doesn't exist.", username));
        var cart = cartRepository.findCartByUsername(username).get();
        return cartDTOMapper.mapToDTO(cart);
    }
}
