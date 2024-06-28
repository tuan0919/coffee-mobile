package com.nlu.packages.dto.json.carts;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.nlu.packages.enums.EIngredient;
import com.nlu.packages.enums.EProductSize;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemJSON {
    @JsonProperty("productId")
    private long productId;
    @JsonProperty("quantity")
    private int quantity;
    @JsonProperty("size")
    private EProductSize size;
    @JsonProperty("ingredients")
    private List<EIngredient> ingredients;
}
