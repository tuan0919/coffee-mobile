package nlu.hcmuaf.android_coffee_app.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlu.hcmuaf.android_coffee_app.enums.EPaymentMethod;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Payments")
public class Payments {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "paymentId")
  private long paymentId;

  @Column(name = "paymentMethod")
  @Enumerated(EnumType.STRING)
  private EPaymentMethod paymentMethod;

  public Payments(EPaymentMethod paymentMethod) {
    this.paymentMethod = paymentMethod;
  }
}
