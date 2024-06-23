package nlu.hcmuaf.android_coffee_app.controller;

import nlu.hcmuaf.android_coffee_app.dto.response.category.CategoriesResponseDTO;
import nlu.hcmuaf.android_coffee_app.service.templates.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
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
