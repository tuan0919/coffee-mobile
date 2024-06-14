package nlu.hcmuaf.android_coffee_app.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Cart_Users")
public class CartUsers implements Serializable {
  @Id
  @ManyToOne
  @JoinColumn(name = "cartId")
  private Cart cart;

  @Id
  @ManyToOne
  @JoinColumn(name = "bookId")
  private Products product;

  @Column(name = "quantity")
  private int quantity;
}
