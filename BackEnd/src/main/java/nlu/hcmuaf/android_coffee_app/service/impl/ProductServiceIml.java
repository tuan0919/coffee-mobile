package nlu.hcmuaf.android_coffee_app.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import nlu.hcmuaf.android_coffee_app.dto.json.products.ProductJSON;
import nlu.hcmuaf.android_coffee_app.dto.response.product_controller.ProductResponseDTO;
import nlu.hcmuaf.android_coffee_app.entities.HavingIngredients;
import nlu.hcmuaf.android_coffee_app.entities.HavingSizes;
import nlu.hcmuaf.android_coffee_app.entities.Products;
import nlu.hcmuaf.android_coffee_app.enums.ECategory;
import nlu.hcmuaf.android_coffee_app.enums.EProductType;
import nlu.hcmuaf.android_coffee_app.exceptions.CategoryException;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;
import nlu.hcmuaf.android_coffee_app.exceptions.ProductException;
import nlu.hcmuaf.android_coffee_app.mapper.response.product.ProductResponseDTOMapper;
import nlu.hcmuaf.android_coffee_app.repositories.CategoryRepository;
import nlu.hcmuaf.android_coffee_app.repositories.ProductRepository;
import nlu.hcmuaf.android_coffee_app.service.templates.IProductService;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
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
    @Autowired
    private ProductResponseDTOMapper productResponseDTOMapper;

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
                    product.setProductName(json.getName());
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

    @Override
    public List<ProductResponseDTO> findProducts(String typePathName, String categoryPathName, String name, Long id) throws CustomException {
        List<Products> products;
        if (null != categoryPathName && !categoryPathName.isEmpty())
            products = queryProductsFromCategory(typePathName, categoryPathName);
        else if (null != typePathName && !typePathName.isEmpty())
            products = queryProductsFromType(typePathName);
        else
            products = queryProductsFromAll();
        if (null != name && !name.isEmpty())
            products = products.stream()
                    .filter(p -> p.getProductName().toLowerCase().contains(name.toLowerCase()))
                    .toList();
        if (null != id)
            products = products.stream()
                    .filter(p -> p.getProductId() == id)
                    .toList();
        return products.stream().map(productResponseDTOMapper::mapToDTO).toList();
    }

    List<Products> queryProductsFromAll() {
        return repository.findAll();
    }

    List<Products> queryProductsFromType(String typePathName) throws ProductException {
        var map = EProductType.MAP_TO_PATHNAME;
        if (!map.containsKey(typePathName))
            throw new ProductException("Product's type is not exists", HttpStatus.NOT_FOUND.value());
        return repository.findAllByProductType(map.get(typePathName));
    }

    List<Products> queryProductsFromCategory(String typePathName, String categoryPathName) throws ProductException, CategoryException {
        if (!EProductType.MAP_TO_PATHNAME.containsKey(typePathName))
            throw new ProductException("Product's type is not exists", HttpStatus.NOT_FOUND.value());
        if (!ECategory.MAP_TO_PATHNAME.containsKey(categoryPathName))
            throw new CategoryException("Category is not exists", HttpStatus.NOT_FOUND.value());
        EProductType type = EProductType.MAP_TO_PATHNAME.get(typePathName);
        ECategory category = ECategory.MAP_TO_PATHNAME.get(categoryPathName);
        return repository.findALlByProductTypeAndCategoriesCategoryEnum(type, category);
    }
}
