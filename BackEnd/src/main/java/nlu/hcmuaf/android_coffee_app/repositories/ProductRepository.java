package nlu.hcmuaf.android_coffee_app.repositories;

import nlu.hcmuaf.android_coffee_app.entities.Products;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Products, Long> {
  @Query("SELECT p FROM products p JOIN FETCH p.categories WHERE p.name like %:name%")
  List<Products> findProductByName(@Param("name") String name);
  @Query("SELECT p FROM products p")
  List<Products> findAll();
}
