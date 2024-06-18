package nlu.hcmuaf.android_coffee_app.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Order;
import jakarta.transaction.Transactional;
import nlu.hcmuaf.android_coffee_app.dto.json.carts.CartJSON;
import nlu.hcmuaf.android_coffee_app.dto.json.carts.CartItemJSON;
import nlu.hcmuaf.android_coffee_app.entities.*;
import nlu.hcmuaf.android_coffee_app.enums.EIngredient;
import nlu.hcmuaf.android_coffee_app.enums.EOrderStatus;
import nlu.hcmuaf.android_coffee_app.enums.EPaymentMethod;
import nlu.hcmuaf.android_coffee_app.enums.EProductSize;
import nlu.hcmuaf.android_coffee_app.exceptions.*;
import nlu.hcmuaf.android_coffee_app.repositories.*;
import nlu.hcmuaf.android_coffee_app.service.templates.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartServiceIml implements ICartService {
    @Autowired
    private CartRepository repository;
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

    @Override
    public Optional<Cart> findByUserId(long id) {
        return repository.findCartByUserId(id);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void putItem(String username, long productId, int quantity, EProductSize size, List<EIngredient> ingredients) throws CustomException {
        ObjectMapper mapper = new ObjectMapper();
        var user = userRepository.findUsersByUsername(username);
        if (user.isEmpty()) throw new UserNotFoundException("User with this username is not found.");
        var product = productRepository.findById(productId);
        if (product.isEmpty()) throw new ProductNotFoundException("Product with this productId is not found.");
        var oSetSize = productRepository.findSizes(productId);
        Set<EProductSize> setSize = null;
        Set<EIngredient> setIngredients = null;
        if (oSetSize.isPresent()) setSize = oSetSize.get().getSizeSet()
                .stream().map(hv -> hv.getSize()).collect(Collectors.toSet());
        if (oSetSize.isEmpty() || !setSize.contains(size))
            throw new ProductSizeNotFoundException("This product doesn't have that size");
        var oSetIngredients = productRepository.findIngredients(productId);
        if (oSetIngredients.isPresent()) setIngredients = oSetIngredients.get().getIngredientsSet()
                .stream().map(hv -> hv.getIngredients().getIngredientEnum()).collect(Collectors.toSet());
        for (var i : setIngredients) {
            if (oSetIngredients.isEmpty() || !setIngredients.contains(i))
                throw new IngredientNotFoundException("This product doesn't have that ingredient");
        }
        var cart = cartRepository.findCartByUserId(user.get().getUserId());
        try {
            var cartJSON = mapper.readValue(cart.get().getCartJSON(), CartJSON.class);
            System.out.println(cartJSON);
            System.out.println(String.format("trong giỏ hàng đang có %d sản phẩm", cartJSON.getDetails().size()));
            if (quantity == 0) cartJSON.getDetails().remove(productId);
            else {
                var oItem = Optional.ofNullable(cartJSON.getDetails().get(productId));
                CartItemJSON item = null;
                if (!oItem.isPresent()) {
                    item = new CartItemJSON();
                }
                else {
                    item = oItem.get();
                }
                item.setProductId(productId);
                item.setQuantity(quantity);
                item.setSize(size);
                item.setIngredients(ingredients);
                cartJSON.getDetails().put(productId, item);
            }
            cart.get().setCartJSON(mapper.writeValueAsString(cartJSON));
            cartRepository.save(cart.get());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void order(String username, long storeId, EPaymentMethod payment) throws CustomException {
        var user = userRepository.findUsersByUsername(username);
        if (user.isEmpty()) throw new UserNotFoundException("User with this username is not found.");
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
                if (oProduct.isEmpty()) throw new ProductNotFoundException("Product with this productId is not found.");
                orderItems.setSize(entry.getValue().getSize());
                orderItems.setQuantity(entry.getValue().getQuantity());
                orderItems.setProducts(oProduct.get());
                orderItems.setPrice(oProduct.get().getBasePrice() * entry.getValue().getQuantity());
                orderItems.setOrders(order);
//                Create Ingredient Set for Order items
                Set<AddIngredients> addSet = new HashSet<>();
                for (var ie : entry.getValue().getIngredients()) {
                    var oIE = ingredientRepository.findByEnum(ie);
                    if (oIE.isEmpty()) throw new IngredientNotFoundException("Ingredient is not found.");
                    var add = new AddIngredients();
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
}
