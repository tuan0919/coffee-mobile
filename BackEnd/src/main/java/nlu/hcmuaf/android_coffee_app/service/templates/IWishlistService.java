package nlu.hcmuaf.android_coffee_app.service.templates;

import nlu.hcmuaf.android_coffee_app.dto.request.wishlist.WishlistRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.product.ProductResponseDTO;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;

import java.util.List;

public interface IWishlistService {
    void createWishList(String username, WishlistRequestDTO dto) throws CustomException;
    List<ProductResponseDTO> getProductFromWishList(String username) throws CustomException;
    void deleteWishList(String username, long productId) throws CustomException;
}
