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
@Entity(name = "own_address")
public class OwnAddress implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @ManyToOne
  @JoinColumn(name = "userId")
  private UserDetails userDetails;

  @Id
  @ManyToOne
  @JoinColumn(name = "addressId")
  private Addresses address;

  @Column(name = "mainAddress")
  private boolean mainAddress;

}
