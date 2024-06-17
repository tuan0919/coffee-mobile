package nlu.hcmuaf.android_coffee_app.controller;

import nlu.hcmuaf.android_coffee_app.config.JwtService;
import nlu.hcmuaf.android_coffee_app.dto.request.LoginRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.request.RegisterRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.request.VerifyRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.request.cart_controller.CartItemRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.MessageResponseDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.TokenResponseDTO;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;
import nlu.hcmuaf.android_coffee_app.service.templates.ICartService;
import nlu.hcmuaf.android_coffee_app.service.templates.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class CartController {

  @Autowired
  private ICartService cartService;

  @PostMapping("cart")
  public ResponseEntity<MessageResponseDTO> putItem(@RequestBody CartItemRequestDTO requestDTO) {
    MessageResponseDTO response = new MessageResponseDTO();
    var status = HttpStatus.OK;
    try {
      cartService.putItem(
              requestDTO.getUsername(),
              requestDTO.getProductId(),
              requestDTO.getQuantity(),
              requestDTO.getIngredients());
    } catch (CustomException e) {
      status = HttpStatus.BAD_REQUEST;
      response.setMessage(e.getMessage());
    }
    return new ResponseEntity<>(response, status);
  }

}
