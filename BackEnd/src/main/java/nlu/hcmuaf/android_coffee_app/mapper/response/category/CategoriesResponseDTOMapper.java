package nlu.hcmuaf.android_coffee_app.mapper.response.category;

import nlu.hcmuaf.android_coffee_app.dto.response.category.CategoriesResponseDTO;
import nlu.hcmuaf.android_coffee_app.entities.Categories;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class CategoriesResponseDTOMapper {
    public abstract CategoriesResponseDTO mapToDTO(Categories category);
}
