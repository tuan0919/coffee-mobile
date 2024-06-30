package nlu.hcmuaf.android_coffee_app.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nlu.hcmuaf.android_coffee_app.dto.request.wishlist.WishlistRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.product.ProductResponseDTO;
import nlu.hcmuaf.android_coffee_app.entities.WishList;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;
import nlu.hcmuaf.android_coffee_app.exceptions.UserInfoException;
import nlu.hcmuaf.android_coffee_app.mapper.response.product.ProductResponseDTOMapper;
import nlu.hcmuaf.android_coffee_app.mapper.response.wishlist.WishlistDTOMapper;
import nlu.hcmuaf.android_coffee_app.repositories.ProductRepository;
import nlu.hcmuaf.android_coffee_app.repositories.UserRepository;
import nlu.hcmuaf.android_coffee_app.repositories.WishlistRepository;
import nlu.hcmuaf.android_coffee_app.service.templates.IEmailService;
import nlu.hcmuaf.android_coffee_app.service.templates.IWishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements IWishlistService {
  private final WishlistRepository wishlistRepository;
  private final UserRepository userRepository;
  private final WishlistDTOMapper wishlistDTOMapper;
  private final ProductResponseDTOMapper productResponseDTOMapper;


  @Override
  public void createWishList(String username, WishlistRequestDTO dto) throws CustomException {
    var oUser = userRepository.findUsersByUsername(username);
    var user = oUser.get();
    wishlistDTOMapper.createWishList(user, dto);
  }

  @Override
  public List<ProductResponseDTO> getProductFromWishList(String username) throws CustomException {
    var oUser = userRepository.findUsersByUsername(username);
    var user = oUser.get();
    List<ProductResponseDTO> list = new ArrayList<>();
    list.addAll(wishlistRepository.findAllByUserUserId(user.getUserId()).stream()
            .map(WishList::getProduct)
            .map(productResponseDTOMapper::mapToDTO).toList());
    return list;
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public void deleteWishList(String username, long productId) throws CustomException {
    var oUser = userRepository.findUsersByUsername(username);
    var user = oUser.get();
    wishlistRepository.deleteByProductProductIdAndAndUserUserId(productId, user.getUserId());
  }
}
