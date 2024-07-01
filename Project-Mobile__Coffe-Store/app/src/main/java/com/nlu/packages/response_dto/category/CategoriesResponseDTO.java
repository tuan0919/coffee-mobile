package com.nlu.packages.response_dto.category;

import com.nlu.packages.enums.EProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
