package nlu.hcmuaf.android_coffee_app.service.templates;

import nlu.hcmuaf.android_coffee_app.dto.response.categories_controller.CategoriesResponseDTO;
import nlu.hcmuaf.android_coffee_app.entities.Categories;

import java.util.List;
import java.util.Optional;

public interface ICategoryService extends IInitializerData {
    Optional<Categories> findById(long id);
    List<CategoriesResponseDTO> getAllCategories();
}
