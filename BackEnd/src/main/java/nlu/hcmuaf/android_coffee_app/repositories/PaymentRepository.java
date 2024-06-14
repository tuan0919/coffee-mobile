package nlu.hcmuaf.android_coffee_app.repositories;

import java.util.List;
import nlu.hcmuaf.android_coffee_app.entities.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payments, Long> {

  @Query("SELECT p FROM Payments p")
  List<Payments> getAllBy();
}
