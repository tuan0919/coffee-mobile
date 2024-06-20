package nlu.hcmuaf.android_coffee_app.controller;

import nlu.hcmuaf.android_coffee_app.config.JwtService;
import nlu.hcmuaf.android_coffee_app.dto.request.cart_controller.CartItemRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.request.cart_controller.CreateOrderRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.MessageResponseDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.cart_controller.CartResponseDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.categories_controller.CategoriesResponseDTO;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;
import nlu.hcmuaf.android_coffee_app.service.templates.ICartService;
import nlu.hcmuaf.android_coffee_app.service.templates.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

  @Autowired
  private ICategoryService categoryService;

  @GetMapping("all")
  public List<CategoriesResponseDTO> getAll() {
    return categoryService.getAllCategories();
  }

}
