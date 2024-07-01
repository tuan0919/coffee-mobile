package com.nlu.packages.response.product;

import com.nlu.packages.enums.*;

import lombok.*;
import lombok.experimental.FieldDefaults;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponseDTO {
    long productId;
    String productName;
    double basePrice;
    String avatar;
    long categoryId;
    String categoryName;
    List<ProductSizeDTO> availableSizes;
    List<IngredientDTO> availableIngredients;
    EProductType productType;
    double discountPercent;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public EProductType getProductType() {
        return productType;
    }

    public void setProductType(EProductType productType) {
        this.productType = productType;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public List<ProductSizeDTO> getAvailableSizes() {
        return availableSizes;
    }

    public void setAvailableSizes(List<ProductSizeDTO> availableSizes) {
        this.availableSizes = availableSizes;
    }

    public List<IngredientDTO> getAvailableIngredients() {
        return availableIngredients;
    }

    public void setAvailableIngredients(List<IngredientDTO> availableIngredients) {
        this.availableIngredients = availableIngredients;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class IngredientDTO {
        long ingredientId;
        String ingredientName;
        EIngredientType ingredientType;
        EIngredient ingredientEnum;
        double addPrice;

        public long getIngredientId() {
            return ingredientId;
        }

        public void setIngredientId(long ingredientId) {
            this.ingredientId = ingredientId;
        }

        public String getIngredientName() {
            return ingredientName;
        }

        public void setIngredientName(String ingredientName) {
            this.ingredientName = ingredientName;
        }

        public EIngredientType getIngredientType() {
            return ingredientType;
        }

        public void setIngredientType(EIngredientType ingredientType) {
            this.ingredientType = ingredientType;
        }

        public EIngredient getIngredientEnum() {
            return ingredientEnum;
        }

        public void setIngredientEnum(EIngredient ingredientEnum) {
            this.ingredientEnum = ingredientEnum;
        }

        public double getAddPrice() {
            return addPrice;
        }

        public void setAddPrice(double addPrice) {
            this.addPrice = addPrice;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ProductSizeDTO {
        EProductSize sizeEnum;
        double multipler; //1.5
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public EProductSize getSizeEnum() {
            return sizeEnum;
        }

        public void setSizeEnum(EProductSize sizeEnum) {
            this.sizeEnum = sizeEnum;
        }

        public double getMultipler() {
            return multipler;
        }

        public void setMultipler(double multipler) {
            this.multipler = multipler;
        }
    }
}
