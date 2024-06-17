package nlu.hcmuaf.android_coffee_app.dto.json.carts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartJSON {
    @JsonProperty("username")
    private String username;
    @JsonProperty(value = "details")
    private Map<Long, CartItemJSON> details;
}
