package nlu.hcmuaf.android_coffee_app.service.impl;

import java.util.ArrayList;
import java.util.List;
import nlu.hcmuaf.android_coffee_app.entities.Roles;
import nlu.hcmuaf.android_coffee_app.enums.ERole;
import nlu.hcmuaf.android_coffee_app.repositories.RoleRepository;
import nlu.hcmuaf.android_coffee_app.service.templates.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends AService implements IRoleService {

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public void initData() {
    if (roleRepository.getAllBy().isEmpty()) {
      List<Roles> rolesList = new ArrayList<>();
      rolesList.add(new Roles(ERole.ADMIN));
      rolesList.add(new Roles(ERole.MANAGER));
      rolesList.add(new Roles(ERole.USER));

      roleRepository.saveAll(rolesList);
    }
  }
}
