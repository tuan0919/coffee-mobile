package nlu.hcmuaf.android_coffee_app.repositories;

import nlu.hcmuaf.android_coffee_app.entities.HavingSizes;
import nlu.hcmuaf.android_coffee_app.entities.Ingredients;
import nlu.hcmuaf.android_coffee_app.entities.Products;
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
}
