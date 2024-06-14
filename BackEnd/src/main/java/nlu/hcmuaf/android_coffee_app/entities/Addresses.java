package nlu.hcmuaf.android_coffee_app.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Addresses")
public class Addresses implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "addressId")
  private long addressId;

  @Column(name = "city")
  private String city;

  @Column(name = "district")
  private String district;

  @Column(name = "ward")
  private String ward;

  @Column(name = "street")
  private String street;

  @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
  private Set<OwnAddress> userAddresses;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (getClass() != o.getClass()) {
      return false;
    }

    return false;
  }
}
