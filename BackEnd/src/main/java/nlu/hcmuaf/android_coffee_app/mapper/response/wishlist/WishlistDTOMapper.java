package nlu.hcmuaf.android_coffee_app.mapper.response.wishlist;

import nlu.hcmuaf.android_coffee_app.dto.request.wishlist.WishlistRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.wishlist.WishlistResponseDTO;
import nlu.hcmuaf.android_coffee_app.entities.Users;
import nlu.hcmuaf.android_coffee_app.entities.WishList;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;
import nlu.hcmuaf.android_coffee_app.exceptions.ProductSizeException;
import nlu.hcmuaf.android_coffee_app.repositories.ProductRepository;
import nlu.hcmuaf.android_coffee_app.repositories.WishlistRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public abstract class WishlistDTOMapper {
    @Autowired
    WishlistRepository wishlistRepository;
    @Autowired
    ProductRepository productRepository;

    @Mapping(target = "productId", source = "w.product.productId")
    public abstract WishlistResponseDTO mapToDTO(WishList w);
    public abstract List<WishlistResponseDTO> mapToDTO(List<WishList> wishlists);

    public void createWishList(Users user, WishlistRequestDTO dto) throws CustomException {
        List<WishList> list = new ArrayList<>();
        for (var id : dto.getProductIds()) {
            var oProduct = productRepository.findById(id);
            if (oProduct.isEmpty())
                throw new ProductSizeException(String.format("Product with id %s not found", id));
            var product = oProduct.get();
            var wishList = WishList.builder().user(user).product(product).build();
            list.add(wishList);
        }
        wishlistRepository.saveAll(list);
    }
}
