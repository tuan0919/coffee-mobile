package nlu.hcmuaf.android_coffee_app.dto.json.carts;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlu.hcmuaf.android_coffee_app.dto.json.categories.CartItemJSON;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartJSON {
    @JsonProperty("username")
    private String username;
    @JsonProperty(value = "details")
    private List<CartItemJSON> details;
}
