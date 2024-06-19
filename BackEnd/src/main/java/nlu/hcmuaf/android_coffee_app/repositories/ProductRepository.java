package nlu.hcmuaf.android_coffee_app.repositories;

import nlu.hcmuaf.android_coffee_app.entities.HavingSizes;
import nlu.hcmuaf.android_coffee_app.entities.Ingredients;
import nlu.hcmuaf.android_coffee_app.entities.Products;
import nlu.hcmuaf.android_coffee_app.enums.EIngredient;
import nlu.hcmuaf.android_coffee_app.enums.EProductSize;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends CrudRepository<Products, Long> {
  @Query("SELECT p FROM products p JOIN FETCH p.categories WHERE p.name like %:name%")
  List<Products> findProductByName(@Param("name") String name);
  @Query("SELECT p FROM products p")
  List<Products> findAll();
  @Query("SELECT p FROM products p JOIN FETCH p.sizeSet WHERE p.productId = :id")
  Optional<Products> findSizes(@Param("id") Long id);
  @Query("SELECT p FROM products p JOIN FETCH p.ingredientsSet WHERE p.productId = :id")
  Optional<Products> findIngredients(@Param("id") Long id);
  @Query("SELECT hv.size FROM having_sizes hv WHERE hv.products.productId = :id")
  Set<EProductSize> findProductSizes(@Param("id") Long id);
  @Query("SELECT hv.ingredients.ingredientEnum FROM having_ingredients hv WHERE hv.products.productId = :id")
  Set<EIngredient> findProductIngredients(@Param("id") Long id);
}
