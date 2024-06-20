package nlu.hcmuaf.android_coffee_app.mapper.response.product;

import nlu.hcmuaf.android_coffee_app.dto.response.product_controller.ProductResponseDTO;
import nlu.hcmuaf.android_coffee_app.entities.Products;
import nlu.hcmuaf.android_coffee_app.enums.EIngredient;
import nlu.hcmuaf.android_coffee_app.enums.EProductSize;
import nlu.hcmuaf.android_coffee_app.repositories.CategoryRepository;
import nlu.hcmuaf.android_coffee_app.repositories.DiscountRepository;
import nlu.hcmuaf.android_coffee_app.repositories.ProductRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ProductResponseDTOMapper {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    DiscountRepository discountRepository;
    @Mapping(target = "categoryId", source = "categories.categoryId")
    @Mapping(target = "availableSizes", source = "productId", qualifiedByName = "findSizesFromId")
    @Mapping(target = "availableIngredients", source = "productId", qualifiedByName = "findIngredientsFromId")
    @Mapping(target = "categoryName", source = "categories.categoryId", qualifiedByName = "findCategoryNameFromId")
    @Mapping(target = "discountPercent",
            defaultValue = "-1",
            source = "discount.discountId",
            qualifiedByName = "findDiscountPercentFromId")
    public abstract ProductResponseDTO mapToDTO(Products product);

    @Named("findSizesFromId")
    List<EProductSize> findSizes(Long productId) {
        return productRepository.findProductSizes(productId).stream().toList();
    }
    @Named("findIngredientsFromId")
    List<EIngredient> findIngredients(Long productId) {
        return productRepository.findProductIngredients(productId).stream().toList();
    }
    @Named("findDiscountPercentFromId")
    double findDiscountPercent(Long discountId) {
        if (discountId == -1) return 0;
        return discountRepository.findById(discountId).get().getPercent();
    }
    @Named("findCategoryNameFromId")
    String findCategoryName(Long categoryId) {
        return categoryRepository.findById(categoryId).get().getCategoryName();
    }
}
