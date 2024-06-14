package nlu.hcmuaf.android_coffee_app.data_initializer;

import nlu.hcmuaf.android_coffee_app.service.templates.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

  @Autowired
  private IPaymentService paymentService;
  @Autowired
  private IRoleService roleService;
  @Autowired
  private IUserService userService;
  @Autowired
  private IIngredientService ingredientService;
  @Autowired
  private ICategoryService categoryService;
  @Autowired
  private IProductService productService;

  @Override
  public void run(String... args) throws Exception {
    // Generate default role
    roleService.initData();

    // Generate default payment
    paymentService.initData();

    // Generate admin account
    userService.initData();

    // Generate ingredients
    ingredientService.initData();

    // Generate categories
    categoryService.initData();

    // Generate products
    productService.initData();
  }
}
