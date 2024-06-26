package nlu.hcmuaf.android_coffee_app.dto.json.categories;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryJSON {
    @JsonProperty("id")
    private long categoryId;
    @JsonProperty("name")
    private String categoryName;
    @JsonProperty("active")
    private boolean active;
    @JsonProperty("avatar")
    private String avatar;
}
