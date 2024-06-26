package nlu.hcmuaf.android_coffee_app.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlu.hcmuaf.android_coffee_app.enums.ERole;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Roles")
public class Roles implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "roleId")
  private long roleId;

  @Column(name = "roleName")
  @Enumerated(EnumType.STRING)
  private ERole roleName;

  public Roles(ERole roleName) {
    this.roleName = roleName;
  }

  public String getRoleName() {
    return roleName.name();
  }
}
