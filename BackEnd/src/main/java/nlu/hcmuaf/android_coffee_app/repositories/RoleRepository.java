package nlu.hcmuaf.android_coffee_app.repositories;

import java.util.List;
import java.util.Optional;
import nlu.hcmuaf.android_coffee_app.entities.Roles;
import nlu.hcmuaf.android_coffee_app.enums.ERole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Roles, Long> {

  @Query("SELECT R FROM Roles R")
  List<Roles> getAllBy();

  Optional<Roles> getRolesByRoleName(ERole role);
}
