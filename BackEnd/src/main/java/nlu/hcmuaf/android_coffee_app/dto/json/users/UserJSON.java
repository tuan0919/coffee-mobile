package nlu.hcmuaf.android_coffee_app.dto.json.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlu.hcmuaf.android_coffee_app.enums.EGender;
import nlu.hcmuaf.android_coffee_app.enums.ERole;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserJSON {
    @JsonProperty("id")
    private long userId;
    @JsonProperty("role")
    private ERole role;
    @JsonProperty("password")
    private String password;
    @JsonProperty("username")
    private String username;
    @JsonProperty("details")
    private UserDetailsJSON details;
    @JsonProperty("addresses")
    private List<AddressJSON> addresses;
}
