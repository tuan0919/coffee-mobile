package nlu.hcmuaf.android_coffee_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "stores")
public class Stores implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "storeId")
  private long storeId;

  @Column(name = "storeName")
  private String storeName;

  @OneToOne
  @JoinColumn(name = "storeAddress")
  private Addresses address;

  @Column(name = "avatar")
  private String avatar;

  @Column(name = "active")
  private boolean active;
}
