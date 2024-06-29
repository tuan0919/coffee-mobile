package com.nlu.packages.dto.json.ingredient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nlu.packages.enums.EIngredient;
import com.nlu.packages.enums.EIngredientType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientJSON {
    @JsonProperty("id")
    private long ingredientId;
    @JsonProperty("name")
    private String ingredientName;
    @JsonProperty("add_price")
    private double addPrice;
    @JsonProperty("type")
    private EIngredientType ingredientType;
    @JsonProperty("enum")
    private EIngredient ingredientEnum;
}
