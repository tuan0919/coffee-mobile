package nlu.hcmuaf.android_coffee_app.controller;

import nlu.hcmuaf.android_coffee_app.config.JwtService;
import nlu.hcmuaf.android_coffee_app.dto.request.cart_controller.CartItemRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.MessageResponseDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.cart_controller.CartResponseDTO;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;
import nlu.hcmuaf.android_coffee_app.service.templates.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gio-hang")
public class CartController {
  @Autowired
  private ICartService cartService;
  @Autowired
  private JwtService jwtService;

  @PutMapping
  public ResponseEntity<MessageResponseDTO> putItem(
          @RequestBody CartItemRequestDTO requestDTO,
          @RequestHeader(value = "Authorization") String authHeader) throws CustomException {
    MessageResponseDTO response = new MessageResponseDTO("OK");
    String username = jwtService.extractUsername(authHeader.substring(7));
    cartService.updateCart(username, requestDTO);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping
  @ResponseBody
  public CartResponseDTO getCart(
          @RequestHeader(value = "Authorization") String authHeader) throws CustomException {
    String username = jwtService.extractUsername(authHeader.substring(7));
    return cartService.findCart(username);
  }

}
