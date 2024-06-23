package nlu.hcmuaf.android_coffee_app.controller;

import nlu.hcmuaf.android_coffee_app.dto.response.product.ProductResponseDTO;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;
import nlu.hcmuaf.android_coffee_app.service.templates.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/san-pham")
public class ProductController {

  @Autowired
  private IProductService productService;

  @GetMapping({"/", ""})
  @ResponseBody
  public List<ProductResponseDTO> getAllProduct (@RequestParam(value = "ten", required = false) String name,
                                                 @RequestParam(value = "id", required = false) Long id) throws CustomException {
    return productService.findProducts(null, null, name, id);
  }

  @GetMapping("{typePathName}/**")
  @ResponseBody
  public List<ProductResponseDTO> getProductWithType (@PathVariable("typePathName") String typePathName,
                                                      @RequestParam(value = "ten", required = false) String name,
                                                      @RequestParam(value = "id", required = false) Long id) throws CustomException {
    return productService.findProducts(typePathName, null, name, id);
  }

  @GetMapping("{typePathName}/{categoryPathName}/**")
  @ResponseBody
  public List<ProductResponseDTO> getProductWithCate (@PathVariable("typePathName") String typePathName,
                                    @PathVariable("categoryPathName") String categoryPathName,
                                    @RequestParam(value = "ten", required = false) String name,
                                    @RequestParam(value = "id", required = false) Long id) throws CustomException {
    return productService.findProducts(typePathName, categoryPathName, name, id);
  }
}
