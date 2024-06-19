package nlu.hcmuaf.android_coffee_app.repositories;

import nlu.hcmuaf.android_coffee_app.entities.Cart;
import nlu.hcmuaf.android_coffee_app.entities.Products;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
  @Query("SELECT c FROM Cart c WHERE c.user.userId = :id")
  Optional<Cart> findCartByUserId(@Param("id") long id);
  @Query("SELECT c FROM Cart c WHERE c.user.username = :username")
  Optional<Cart> findCartByUsername(@Param("username") String username);
}
