package nlu.hcmuaf.android_coffee_app.service.impl;

import jakarta.transaction.Transactional;
import nlu.hcmuaf.android_coffee_app.dto.request.cart.CartItemRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.cart.CartResponseDTO;
import nlu.hcmuaf.android_coffee_app.entities.*;
import nlu.hcmuaf.android_coffee_app.enums.EIngredient;
import nlu.hcmuaf.android_coffee_app.enums.EProductSize;
import nlu.hcmuaf.android_coffee_app.exceptions.*;
import nlu.hcmuaf.android_coffee_app.mapper.response.cart.CartDTOMapper;
import nlu.hcmuaf.android_coffee_app.repositories.*;
import nlu.hcmuaf.android_coffee_app.service.templates.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartServiceIml implements ICartService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
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
    public void updateCart(String username, CartItemRequestDTO requestDTO) throws CustomException {
        var oCart = cartRepository.findCartByUsername(username);
        if (!oCart.isPresent())
            throw new CartException("Cart doesn't exist.");
        Cart cart = oCart.get();
        CartItems item = cartDTOMapper.mapToModel(cart, requestDTO);
        var updateSet = cart.getCartItemsSet();
        updateSet.remove(item);
        if (item.getQuantity() != 0) updateSet.add(item);
        cartRepository.save(cart);
    }

    @Override
    public CartResponseDTO findCart(String username) throws CustomException {
        if (!checkUserExists(username))
            throw new UserInfoException(String.format("User with username: %s doesn't exist.", username));
        var cart = cartRepository.findCartByUsername(username).get();
        return cartDTOMapper.mapSetCartItemToDTO(cart.getCartItemsSet());
    }
}
