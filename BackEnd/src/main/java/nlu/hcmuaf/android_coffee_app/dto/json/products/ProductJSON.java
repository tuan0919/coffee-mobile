package nlu.hcmuaf.android_coffee_app.dto.json.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlu.hcmuaf.android_coffee_app.enums.EIngredient;
import nlu.hcmuaf.android_coffee_app.enums.EProductSize;
import nlu.hcmuaf.android_coffee_app.enums.EProductType;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductJSON {
    @JsonProperty("id")
    private long productId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("avatar")
    private String avatar;
    @JsonProperty("active")
    private boolean active;
    @JsonProperty("base_price")
    private double basePrice;
    @JsonProperty("category_id")
    private long categoryId;
    @JsonProperty("type")
    private EProductType productType;
    @JsonProperty("sizes")
    private Set<EProductSize> productSizes;
    @JsonProperty("ingredients")
    private Set<EIngredient> productIngredients;
}
