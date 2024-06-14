package nlu.hcmuaf.android_coffee_app.service.templates;

import nlu.hcmuaf.android_coffee_app.entities.Categories;

import java.util.Optional;

public interface ICategoryService extends IInitializerData {
    Optional<Categories> findById(long id);
}
