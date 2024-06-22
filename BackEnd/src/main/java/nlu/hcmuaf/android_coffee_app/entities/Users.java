package nlu.hcmuaf.android_coffee_app.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Users")
public class Users implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "userId")
  private long userId;

  @ManyToOne
  @JoinColumn(name = "roleId", referencedColumnName = "roleId")
  private Roles roles;

  @Column(name = "username", unique = true)
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "createdDate")
  private LocalDate createdDate;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private UserDetails userDetails;

  @OneToOne
  @JoinColumn(name = "cartId")
  private Cart cart;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private Set<WishList> wishLists;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Users users)) return false;
      return userId == users.userId;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(userId);
  }
}
