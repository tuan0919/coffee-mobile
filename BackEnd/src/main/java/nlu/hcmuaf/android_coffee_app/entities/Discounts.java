package nlu.hcmuaf.android_coffee_app.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "discounts")
public class Discounts implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "discountId")
  private Long discountId;

  @Column(name = "percent")
  private Double percent;

  @Column(name = "expireDate")
  private LocalDate expireDate;

  @Column(name = "description")
  private String description;

  @Column(name = "conditionDescription")
  private String conditionDescription;

  @Column(name = "status")
  private String status;

  @OneToMany(mappedBy = "discount")
  private Set<Products> products;

}
