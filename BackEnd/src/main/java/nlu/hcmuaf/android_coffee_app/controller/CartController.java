package nlu.hcmuaf.android_coffee_app.controller;

import nlu.hcmuaf.android_coffee_app.config.JwtService;
import nlu.hcmuaf.android_coffee_app.dto.request.cart_controller.CartItemRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.request.cart_controller.CreateOrderRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.MessageResponseDTO;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;
import nlu.hcmuaf.android_coffee_app.service.templates.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class CartController {

  @Autowired
  private ICartService cartService;
  @Autowired
  private JwtService jwtService;

  @PutMapping("cart")
  public ResponseEntity<MessageResponseDTO> putItem(
          @RequestBody CartItemRequestDTO requestDTO,
          @RequestHeader(value = "Authorization") String authHeader) {
    MessageResponseDTO response = new MessageResponseDTO("OK");
    var status = HttpStatus.OK;
    String username = jwtService.extractUsername(authHeader.substring(7));
    try {
      cartService.putItem(
              username,
              requestDTO.getProductId(),
              requestDTO.getQuantity(),
              requestDTO.getSize(),
              requestDTO.getIngredients());
    } catch (CustomException e) {
      status = HttpStatus.BAD_REQUEST;
      response.setMessage(e.getMessage());
    }
    return new ResponseEntity<>(response, status);
  }

  @PostMapping("cart")
  public ResponseEntity<MessageResponseDTO> order(
          @RequestBody CreateOrderRequestDTO requestDTO,
          @RequestHeader(value = "Authorization") String authHeader) {
    MessageResponseDTO response = new MessageResponseDTO("OK");
    var status = HttpStatus.OK;
    String username = jwtService.extractUsername(authHeader.substring(7));
    try {
      cartService.order(
              username,
              requestDTO.getStoreId(),
              requestDTO.getMethod());
    } catch (CustomException e) {
      status = HttpStatus.BAD_REQUEST;
      response.setMessage(e.getMessage());
    }
    return new ResponseEntity<>(response, status);
  }

}
