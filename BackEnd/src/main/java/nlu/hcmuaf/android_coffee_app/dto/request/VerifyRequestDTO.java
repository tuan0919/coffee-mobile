package nlu.hcmuaf.android_coffee_app.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyRequestDTO {

  @Email
  private String email;
  @NotNull(message = "not null")
  private String otp;
}
