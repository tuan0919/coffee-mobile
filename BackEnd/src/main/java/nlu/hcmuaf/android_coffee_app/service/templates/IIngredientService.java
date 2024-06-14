package nlu.hcmuaf.android_coffee_app.service.templates;

import nlu.hcmuaf.android_coffee_app.entities.Ingredients;
import nlu.hcmuaf.android_coffee_app.enums.EIngredient;

import java.util.Optional;

public interface IIngredientService extends IInitializerData {
    Optional<Ingredients> findByEnum(EIngredient e);
}
