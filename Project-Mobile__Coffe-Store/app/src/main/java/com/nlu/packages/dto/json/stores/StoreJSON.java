package nlu.hcmuaf.android_coffee_app.dto.json.stores;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreJSON {
    @JsonProperty("active")
    private boolean active;
    @JsonProperty("avatar")
    private String avatar;
    @JsonProperty("name")
    private String storeName;
    @JsonProperty("address")
    private AddressJSON storeAddress;
}
