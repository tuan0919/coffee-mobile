package nlu.hcmuaf.android_coffee_app.dto.json.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlu.hcmuaf.android_coffee_app.enums.EGender;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressJSON {
    @JsonProperty("main_address")
    private boolean mainAddress;
    @JsonProperty("city")
    private String city;
    @JsonProperty("district")
    private String district;
    @JsonProperty("street")
    private String street;
    @JsonProperty("ward")
    private String ward;
}
