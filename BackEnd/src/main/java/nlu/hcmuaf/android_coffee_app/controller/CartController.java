package nlu.hcmuaf.android_coffee_app.controller;

import lombok.RequiredArgsConstructor;
import nlu.hcmuaf.android_coffee_app.config.JwtService;
import nlu.hcmuaf.android_coffee_app.dto.request.cart.CartItemRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.MessageResponseDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.cart.CartResponseDTO;
import nlu.hcmuaf.android_coffee_app.entities.Users;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;
import nlu.hcmuaf.android_coffee_app.service.templates.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2")
@RequiredArgsConstructor
public class CartController {
  private final ICartService cartService;

  @PutMapping(path = "gio-hang")
  public ResponseEntity<MessageResponseDTO> putItem(
          @RequestAttribute(name = "user") Users user,
          @RequestBody CartItemRequestDTO requestDTO) throws CustomException {
    MessageResponseDTO response = new MessageResponseDTO("OK");
    cartService.updateCart(user.getUsername(), requestDTO);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping(path = "gio-hang")
  @ResponseBody
  public CartResponseDTO getCart(@RequestAttribute(name = "user") Users user) throws CustomException {
    return cartService.findCart(user.getUsername());
  }

}
