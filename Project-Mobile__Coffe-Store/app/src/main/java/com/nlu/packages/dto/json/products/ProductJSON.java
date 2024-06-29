package com.nlu.packages.dto.json.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nlu.packages.enums.EIngredient;
import com.nlu.packages.enums.EProductSize;
import com.nlu.packages.enums.EProductType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
