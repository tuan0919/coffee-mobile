package nlu.hcmuaf.android_coffee_app.service.templates;

import nlu.hcmuaf.android_coffee_app.entities.Cart;
import nlu.hcmuaf.android_coffee_app.entities.Categories;
import nlu.hcmuaf.android_coffee_app.entities.Products;
import nlu.hcmuaf.android_coffee_app.enums.EIngredient;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;

import java.util.List;
import java.util.Optional;

public interface ICartService {
    Optional<Cart> findByUserId(long id);
    void putItem(String username, long productId, int quantity, List<EIngredient> ingredients) throws CustomException;
}
