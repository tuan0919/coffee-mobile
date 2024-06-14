package nlu.hcmuaf.android_coffee_app.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlu.hcmuaf.android_coffee_app.enums.EGender;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "User_Details")
public class UserDetails implements Serializable {

  @Id
  @Column(name = "userId")
  private long userId;

  @OneToOne
  @MapsId
  @JoinColumn(name = "userId")
  private Users user;

  @Column(name = "firstname")
  private String firstname;

  @Column(name = "lastname")
  private String lastname;

  @Column(name = "email", unique = true)
  private String email;

  @Column(name = "dob")
  private LocalDate dob;

  @Column(name = "phoneNum")
  private String phoneNum;

  @Column(name = "gender")
  @Enumerated(EnumType.STRING)
  private EGender gender;

  @Column(name = "img")
  private String img;

  @OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL)
  private Set<OwnAddress> userAddresses;

  @Column(name = "otp")
  private String otp;

  @Column(name = "verified")
  private boolean verified;

  @Column(name = "otpExpiryTime")
  private LocalDateTime otpExpiryTime;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (getClass() != o.getClass()) {
      return false;
    }
    UserDetails userDetails = (UserDetails) o;
    return email.equals(userDetails.getEmail());
  }
}
