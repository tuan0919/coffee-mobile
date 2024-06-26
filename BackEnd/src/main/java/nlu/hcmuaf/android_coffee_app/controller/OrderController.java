package nlu.hcmuaf.android_coffee_app.controller;

import lombok.RequiredArgsConstructor;
import nlu.hcmuaf.android_coffee_app.config.JwtService;
import nlu.hcmuaf.android_coffee_app.dto.request.cart.CartItemRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.request.order.CreateOrderRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.MessageResponseDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.cart.CartResponseDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.order.OrderResponseDTO;
import nlu.hcmuaf.android_coffee_app.entities.Users;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;
import nlu.hcmuaf.android_coffee_app.service.templates.ICartService;
import nlu.hcmuaf.android_coffee_app.service.templates.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/")
@RequiredArgsConstructor
public class OrderController {
  private final IOrderService orderService;

  @PostMapping("dat-hang")
  public MessageResponseDTO putItem(
          @RequestBody CreateOrderRequestDTO requestDTO,
          @RequestAttribute("user") Users user) throws CustomException {
    orderService.createOrder(user.getUsername(), requestDTO);
    return MessageResponseDTO.builder().message("OK").build();
  }

  @GetMapping("don-hang")
  public List<OrderResponseDTO> getOrders(@RequestAttribute("user") Users user) throws CustomException {
    return orderService.getOrders(user.getUsername());
  }
}
