package com.nlu.packages.dto.json.categories;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nlu.packages.enums.EProductType;

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
    @JsonProperty("type")
    private EProductType type;
    @JsonProperty("categoryEnum")
    private String categoryEnum;
}
