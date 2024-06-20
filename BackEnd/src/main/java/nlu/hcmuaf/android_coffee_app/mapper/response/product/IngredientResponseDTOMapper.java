package nlu.hcmuaf.android_coffee_app.mapper.response.product;

import nlu.hcmuaf.android_coffee_app.dto.response.product_controller.IngredientResponseDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.product_controller.ProductResponseDTO;
import nlu.hcmuaf.android_coffee_app.entities.Ingredients;
import nlu.hcmuaf.android_coffee_app.entities.Products;
import nlu.hcmuaf.android_coffee_app.enums.EProductSize;
import nlu.hcmuaf.android_coffee_app.repositories.CategoryRepository;
import nlu.hcmuaf.android_coffee_app.repositories.DiscountRepository;
import nlu.hcmuaf.android_coffee_app.repositories.IngredientRepository;
import nlu.hcmuaf.android_coffee_app.repositories.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class IngredientResponseDTOMapper {
    public abstract IngredientResponseDTO mapToDTO(Ingredients ingredients);
}
