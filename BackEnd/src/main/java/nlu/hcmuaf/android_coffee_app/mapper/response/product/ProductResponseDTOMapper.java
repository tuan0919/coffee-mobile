package nlu.hcmuaf.android_coffee_app.mapper.response.product;

import nlu.hcmuaf.android_coffee_app.dto.response.product.ProductResponseDTO;
import nlu.hcmuaf.android_coffee_app.entities.HavingIngredients;
import nlu.hcmuaf.android_coffee_app.entities.HavingSizes;
import nlu.hcmuaf.android_coffee_app.entities.Ingredients;
import nlu.hcmuaf.android_coffee_app.entities.Products;
import nlu.hcmuaf.android_coffee_app.enums.EProductSize;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ProductResponseDTOMapper {
    @Mapping(target = "categoryId", source = "categories.categoryId")
    @Mapping(target = "availableSizes", source = "product", qualifiedByName = "findSizes")
    @Mapping(target = "availableIngredients", source = "product", qualifiedByName = "findIngredients")
    @Mapping(target = "categoryName", source = "product.categories.categoryName")
    @Mapping(target = "discountPercent", source = "product.discount.percent", defaultValue = "0")
    public abstract ProductResponseDTO mapToDTO(Products product);
    abstract ProductResponseDTO.IngredientDTO mapToDTO(Ingredients ingredient);
    @Mapping(target = "sizeEnum", source = "size")
    @Mapping(target = "multipler", source = "size.multipler")
    abstract ProductResponseDTO.ProductSizeDTO mapToDTO(EProductSize size);



    @Named("findSizes")
    List<ProductResponseDTO.ProductSizeDTO> findSizes(Products product) {
        return product.getSizeSet().stream()
                .map(HavingSizes::getSize)
                .map(this::mapToDTO)
                .toList();
    }

    @Named("findIngredients")
    List<ProductResponseDTO.IngredientDTO> findIngredients(Products product) {
        return product.getIngredientsSet().stream()
                .map(HavingIngredients::getIngredients)
                .map(this::mapToDTO)
                .toList();
    }
}
