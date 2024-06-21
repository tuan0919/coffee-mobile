package nlu.hcmuaf.android_coffee_app.repositories;

import nlu.hcmuaf.android_coffee_app.entities.Categories;
import nlu.hcmuaf.android_coffee_app.enums.ECategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Categories, Long> {
  @Query("SELECT c FROM categories c")
  Optional<Categories> findAllBy();
}
