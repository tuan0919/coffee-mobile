package nlu.hcmuaf.android_coffee_app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenResponseDTO {

  private String token;
  private String role;
  private String message;
}
