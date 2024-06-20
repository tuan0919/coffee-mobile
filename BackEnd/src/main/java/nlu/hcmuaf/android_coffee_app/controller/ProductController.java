package nlu.hcmuaf.android_coffee_app.controller;

import nlu.hcmuaf.android_coffee_app.dto.response.categories_controller.CategoriesResponseDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.product_controller.ProductResponseDTO;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;
import nlu.hcmuaf.android_coffee_app.service.templates.ICategoryService;
import nlu.hcmuaf.android_coffee_app.service.templates.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

  @Autowired
  private IProductService productService;

  @GetMapping("{id}")
  @ResponseBody
  public ProductResponseDTO getAll(@PathVariable Long id) throws CustomException {
    return productService.findProductById(id);
  }

}
