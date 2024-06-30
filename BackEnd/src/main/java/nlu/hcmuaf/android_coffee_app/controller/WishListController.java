package nlu.hcmuaf.android_coffee_app.controller;

import lombok.RequiredArgsConstructor;
import nlu.hcmuaf.android_coffee_app.dto.request.wishlist.WishlistRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.MessageResponseDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.category.CategoriesResponseDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.product.ProductResponseDTO;
import nlu.hcmuaf.android_coffee_app.entities.Users;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;
import nlu.hcmuaf.android_coffee_app.service.templates.ICategoryService;
import nlu.hcmuaf.android_coffee_app.service.templates.IWishlistService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
@RequiredArgsConstructor
public class WishListController {
  private final IWishlistService wishlistService;
  @PostMapping("yeu-thich")
  public MessageResponseDTO create(@RequestAttribute("user") Users user, @RequestBody WishlistRequestDTO dto) throws CustomException {
    wishlistService.createWishList(user.getUsername(), dto);
    return MessageResponseDTO.builder().message("OK").build();
  }

  @GetMapping("yeu-thich")
  public List<ProductResponseDTO> create(@RequestAttribute("user")Users user) throws CustomException {
    return wishlistService.getProductFromWishList(user.getUsername());
  }

  @DeleteMapping("yeu-thich")
  public MessageResponseDTO delete(@RequestAttribute("user")Users user, @RequestBody WishlistRequestDTO dto) throws CustomException {
    for (var i : dto.getProductIds()) {
      wishlistService.deleteWishList(user.getUsername(), i);
    }
    return MessageResponseDTO.builder().message("OK").build();
  }
}
