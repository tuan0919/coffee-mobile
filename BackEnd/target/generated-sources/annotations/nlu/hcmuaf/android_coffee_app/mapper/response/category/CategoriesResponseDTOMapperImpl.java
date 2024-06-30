package nlu.hcmuaf.android_coffee_app.mapper.response.category;

import javax.annotation.processing.Generated;
import nlu.hcmuaf.android_coffee_app.dto.response.category.CategoriesResponseDTO;
import nlu.hcmuaf.android_coffee_app.entities.Categories;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-30T16:05:30+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class CategoriesResponseDTOMapperImpl extends CategoriesResponseDTOMapper {

    @Override
    public CategoriesResponseDTO mapToDTO(Categories category) {
        if ( category == null ) {
            return null;
        }

        CategoriesResponseDTO.CategoriesResponseDTOBuilder categoriesResponseDTO = CategoriesResponseDTO.builder();

        categoriesResponseDTO.categoryId( (int) category.getCategoryId() );
        categoriesResponseDTO.categoryName( category.getCategoryName() );
        categoriesResponseDTO.avatar( category.getAvatar() );
        categoriesResponseDTO.type( category.getType() );

        return categoriesResponseDTO.build();
    }
}
