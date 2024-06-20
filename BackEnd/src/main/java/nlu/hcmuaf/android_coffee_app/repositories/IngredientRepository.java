package nlu.hcmuaf.android_coffee_app.repositories;

import nlu.hcmuaf.android_coffee_app.entities.Ingredients;
import nlu.hcmuaf.android_coffee_app.enums.EIngredient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredients, Long> {
  @Query("SELECT i FROM ingredients i")
  Optional<Ingredients> findAllBy();

  @Query("SELECT i FROM ingredients i WHERE i.ingredientEnum = :e")
  Optional<Ingredients> findByEnum(@Param("e") EIngredient e);

  @Query("SELECT ie FROM ingredients ie JOIN having_ingredients hv" +
          " ON ie.ingredientId = hv.ingredients.ingredientId" +
          " WHERE hv.products.productId = :id")
  List<Ingredients> findByProductId(@Param("id") Long productId);
}
