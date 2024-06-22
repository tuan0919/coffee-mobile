package nlu.hcmuaf.android_coffee_app.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.io.Serializable;
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
@Entity(name = "Cart")
public class Cart implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cartId")
  private long cartId;

  @OneToOne
  @JoinColumn(name = "userId")
  private Users user;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CartItems> cartItemsSet;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Cart cart)) return false;
      return cartId == cart.cartId && Objects.equals(user, cart.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cartId, user);
  }
}
