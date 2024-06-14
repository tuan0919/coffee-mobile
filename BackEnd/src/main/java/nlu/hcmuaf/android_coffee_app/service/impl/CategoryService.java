package nlu.hcmuaf.android_coffee_app.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import nlu.hcmuaf.android_coffee_app.dto.json.categories.CategoryJSON;
import nlu.hcmuaf.android_coffee_app.entities.Categories;
import nlu.hcmuaf.android_coffee_app.repositories.CategoryRepository;
import nlu.hcmuaf.android_coffee_app.service.templates.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService extends AService implements ICategoryService {
    @Autowired
    private CategoryRepository repository;
    @Override
    public void initData() {
        if (repository.findAllBy().isEmpty()) {
            try {
                ClassPathResource resource = new ClassPathResource("categories.json");
                List<CategoryJSON> ingredientJSONS = objectMapper.readValue(resource.getInputStream(),
                        new TypeReference<List<CategoryJSON>>() {});
                var categories = ingredientJSONS.stream().map(json -> {
                    return modelMapper.map(json, Categories.class);
                }).toList();
                repository.saveAll(categories);
            } catch (Exception e) {
                throw new RuntimeException("Error loading categories.json", e);
            }
        }
    }

    @Override
    public Optional<Categories> findById(long id) {
        return repository.findById(id);
    }
}
