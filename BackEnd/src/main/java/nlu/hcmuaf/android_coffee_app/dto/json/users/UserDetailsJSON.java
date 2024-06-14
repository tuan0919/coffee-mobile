package nlu.hcmuaf.android_coffee_app.dto.json.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlu.hcmuaf.android_coffee_app.enums.EGender;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsJSON {
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("verified")
    private boolean verified;
    @JsonProperty("email")
    private String email;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("img")
    private String img;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("gender")
    private EGender gender;
}
