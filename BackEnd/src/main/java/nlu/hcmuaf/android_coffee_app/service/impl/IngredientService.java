package nlu.hcmuaf.android_coffee_app.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import nlu.hcmuaf.android_coffee_app.dto.json.ingredient.IngredientJSON;
import nlu.hcmuaf.android_coffee_app.entities.Ingredients;
import nlu.hcmuaf.android_coffee_app.enums.EIngredient;
import nlu.hcmuaf.android_coffee_app.repositories.IngredientRepository;
import nlu.hcmuaf.android_coffee_app.service.templates.IIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService extends AService implements IIngredientService {
    @Autowired
    private IngredientRepository repository;
    @Override
    public void initData() {
        if (repository.findAllBy().isEmpty()) {
            try {
                ClassPathResource resource = new ClassPathResource("ingredients.json");
                List<IngredientJSON> ingredientJSONS = objectMapper.readValue(resource.getInputStream(),
                        new TypeReference<List<IngredientJSON>>() {});
                var ingredients = ingredientJSONS.stream().map(json -> {
                    return modelMapper.map(json, Ingredients.class);
                }).toList();
                repository.saveAll(ingredients);
            } catch (Exception e) {
                throw new RuntimeException("Error loading ingredients.json", e);
            }
        }
    }

    @Override
    public Optional<Ingredients> findByEnum(EIngredient e) {
        return repository.findByEnum(e);
    }
}
