package nlu.hcmuaf.android_coffee_app.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import nlu.hcmuaf.android_coffee_app.dto.json.products.ProductJSON;
import nlu.hcmuaf.android_coffee_app.entities.HavingIngredients;
import nlu.hcmuaf.android_coffee_app.entities.HavingSizes;
import nlu.hcmuaf.android_coffee_app.entities.Products;
import nlu.hcmuaf.android_coffee_app.enums.EProductType;
import nlu.hcmuaf.android_coffee_app.repositories.ProductRepository;
import nlu.hcmuaf.android_coffee_app.service.templates.IProductService;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceIml extends AService implements IProductService {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private IngredientService ingredientService;
    @Override
    public void initData() {
        if (repository.findAll().isEmpty()) {
            try {
                ClassPathResource resource = new ClassPathResource("products.json");
                List<ProductJSON> ingredientJSONS = objectMapper.readValue(resource.getInputStream(),
                        new TypeReference<List<ProductJSON>>() {});
                List<Products> products = new ArrayList<>();
                for (var json : ingredientJSONS.stream().toList()) {
                    Products product = new Products();
                    product.setProductId(json.getProductId());
                    product.setName(json.getName());
                    product.setAvatar(json.getAvatar());
                    product.setBasePrice(json.getBasePrice());
                    product.setActive(json.isActive());
                    var category = categoryService.findById(json.getCategoryId());
                    if (category.isEmpty())
                        throw new Exception("category không tồn tại");
                    product.setCategories(category.get());
                    EProductType type = json.getProductType();
                    product.setProductType(type);
                    Set<HavingSizes> sizes = json.getProductSizes().stream()
                            .map(size -> new HavingSizes(product, size))
                            .collect(Collectors.toSet());
                    product.setSizeSet(sizes);
                    Set<HavingIngredients> ingredients = new HashSet<>();
                    for (var ingredient : json.getProductIngredients()) {
                        var ingredientObj = ingredientService.findByEnum(ingredient);
                        if (ingredientObj.isEmpty()) throw new Exception("ingredient không tồn tại!");
                        ingredients.add(new HavingIngredients(product, ingredientObj.get()));
                    }
                    product.setIngredientsSet(ingredients);
                    products.add(product);
                }
                repository.saveAll(products);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }
}
