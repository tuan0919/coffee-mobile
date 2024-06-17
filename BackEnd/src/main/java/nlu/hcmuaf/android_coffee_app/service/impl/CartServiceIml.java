package nlu.hcmuaf.android_coffee_app.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nlu.hcmuaf.android_coffee_app.dto.json.carts.CartJSON;
import nlu.hcmuaf.android_coffee_app.dto.json.categories.CartItemJSON;
import nlu.hcmuaf.android_coffee_app.dto.json.products.ProductJSON;
import nlu.hcmuaf.android_coffee_app.dto.response.MessageResponseDTO;
import nlu.hcmuaf.android_coffee_app.entities.Cart;
import nlu.hcmuaf.android_coffee_app.entities.HavingIngredients;
import nlu.hcmuaf.android_coffee_app.entities.HavingSizes;
import nlu.hcmuaf.android_coffee_app.entities.Products;
import nlu.hcmuaf.android_coffee_app.enums.EIngredient;
import nlu.hcmuaf.android_coffee_app.enums.EProductType;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;
import nlu.hcmuaf.android_coffee_app.exceptions.ProductNotFoundException;
import nlu.hcmuaf.android_coffee_app.exceptions.UserNotFoundException;
import nlu.hcmuaf.android_coffee_app.repositories.CartRepository;
import nlu.hcmuaf.android_coffee_app.repositories.ProductRepository;
import nlu.hcmuaf.android_coffee_app.repositories.UserRepository;
import nlu.hcmuaf.android_coffee_app.service.templates.ICartService;
import nlu.hcmuaf.android_coffee_app.service.templates.IProductService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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

    @Override
    public Optional<Cart> findByUserId(long id) {
        return repository.findCartByUserId(id);
    }

    @Override
    public void putItem(String username, long productId, int quantity, List<EIngredient> ingredients) throws CustomException {
        ObjectMapper mapper = new ObjectMapper();
        var user = userRepository.findUsersByUsername(username);
        if (user.isEmpty()) throw new UserNotFoundException("User with this username is not found.");
        var product = productRepository.findById(productId);
        if (product.isEmpty()) throw new ProductNotFoundException("Product with this productId is not found.");
        var cart = cartRepository.findCartByUserId(user.get().getUserId());
        try {
            var cartJSON = mapper.readValue(cart.get().getCartJSON(), CartJSON.class);
            boolean isNew = false;
            for (var item : cartJSON.getDetails()) {
                if (item.getProductId() == productId && !isNew) {
                    if (quantity != 0) {
                        item.setQuantity(quantity);
                        item.setIngredients(ingredients);
                        isNew = true;
                    }
                }
            }
            if (isNew) {
                var itemJSON = new CartItemJSON();
                itemJSON.setProductId(productId);
                itemJSON.setQuantity(quantity);
                itemJSON.setIngredients(ingredients);
                cartJSON.getDetails().add(itemJSON);
            }
            cart.get().setCartJSON(mapper.writeValueAsString(cartJSON));
            cartRepository.save(cart.get());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
