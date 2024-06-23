package nlu.hcmuaf.android_coffee_app.dto.response.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlu.hcmuaf.android_coffee_app.enums.EProductType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriesResponseDTO {
    private int categoryId;
    private String categoryName;
    private String avatar;
    private EProductType type;
}
