package nlu.hcmuaf.android_coffee_app.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import nlu.hcmuaf.android_coffee_app.dto.json.carts.CartJSON;
import nlu.hcmuaf.android_coffee_app.dto.json.carts.CartItemJSON;
import nlu.hcmuaf.android_coffee_app.entities.Cart;
import nlu.hcmuaf.android_coffee_app.enums.EIngredient;
import nlu.hcmuaf.android_coffee_app.enums.EProductSize;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;
import nlu.hcmuaf.android_coffee_app.exceptions.ProductNotFoundException;
import nlu.hcmuaf.android_coffee_app.exceptions.UserNotFoundException;
import nlu.hcmuaf.android_coffee_app.repositories.CartRepository;
import nlu.hcmuaf.android_coffee_app.repositories.ProductRepository;
import nlu.hcmuaf.android_coffee_app.repositories.UserRepository;
import nlu.hcmuaf.android_coffee_app.service.templates.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Override
    public Optional<Cart> findByUserId(long id) {
        return repository.findCartByUserId(id);
    }

    @Override
    public void putItem(String username, long productId, int quantity, EProductSize size, List<EIngredient> ingredients) throws CustomException {
        ObjectMapper mapper = new ObjectMapper();
        var user = userRepository.findUsersByUsername(username);
        if (user.isEmpty()) throw new UserNotFoundException("User with this username is not found.");
        var product = productRepository.findById(productId);
        if (product.isEmpty()) throw new ProductNotFoundException("Product with this productId is not found.");
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
}
