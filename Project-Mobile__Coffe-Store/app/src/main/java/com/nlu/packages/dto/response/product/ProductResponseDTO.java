package com.nlu.packages.dto.response.product;

import android.os.Parcel;
import android.os.Parcelable;

import com.nlu.packages.enums.EIngredient;
import com.nlu.packages.enums.EIngredientType;
import com.nlu.packages.enums.EProductSize;
import com.nlu.packages.enums.EProductType;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponseDTO implements Parcelable {
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

    protected ProductResponseDTO(Parcel in) {
        productId = in.readLong();
        productName = in.readString();
        basePrice = in.readDouble();
        avatar = in.readString();
        categoryId = in.readLong();
        categoryName = in.readString();
        availableSizes = in.createTypedArrayList(ProductSizeDTO.CREATOR);
        availableIngredients = in.createTypedArrayList(IngredientDTO.CREATOR);
        productType = EProductType.valueOf(in.readString());
        discountPercent = in.readDouble();
    }

    public static final Creator<ProductResponseDTO> CREATOR = new Creator<ProductResponseDTO>() {
        @Override
        public ProductResponseDTO createFromParcel(Parcel in) {
            return new ProductResponseDTO(in);
        }

        @Override
        public ProductResponseDTO[] newArray(int size) {
            return new ProductResponseDTO[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(productId);
        dest.writeString(productName);
        dest.writeDouble(basePrice);
        dest.writeString(avatar);
        dest.writeLong(categoryId);
        dest.writeString(categoryName);
        dest.writeTypedList(availableSizes);
        dest.writeTypedList(availableIngredients);
        dest.writeString(productType.name());
        dest.writeDouble(discountPercent);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class IngredientDTO implements Parcelable {
        long ingredientId;
        String ingredientName;
        EIngredientType ingredientType;
        EIngredient ingredientEnum;
        double addPrice;

        protected IngredientDTO(Parcel in) {
            ingredientId = in.readLong();
            ingredientName = in.readString();
            ingredientType = EIngredientType.valueOf(in.readString());
            ingredientEnum = EIngredient.valueOf(in.readString());
            addPrice = in.readDouble();
        }

        public static final Creator<IngredientDTO> CREATOR = new Creator<IngredientDTO>() {
            @Override
            public IngredientDTO createFromParcel(Parcel in) {
                return new IngredientDTO(in);
            }

            @Override
            public IngredientDTO[] newArray(int size) {
                return new IngredientDTO[size];
            }
        };

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(ingredientId);
            dest.writeString(ingredientName);
            dest.writeString(ingredientType.name());
            dest.writeString(ingredientEnum.name());
            dest.writeDouble(addPrice);
        }

        @Override
        public int describeContents() {
            return 0;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ProductSizeDTO implements Parcelable {
        EProductSize sizeEnum;
        double multipler;

        protected ProductSizeDTO(Parcel in) {
            sizeEnum = EProductSize.valueOf(in.readString());
            multipler = in.readDouble();
        }

        public static final Creator<ProductSizeDTO> CREATOR = new Creator<ProductSizeDTO>() {
            @Override
            public ProductSizeDTO createFromParcel(Parcel in) {
                return new ProductSizeDTO(in);
            }

            @Override
            public ProductSizeDTO[] newArray(int size) {
                return new ProductSizeDTO[size];
            }
        };

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(sizeEnum.name());
            dest.writeDouble(multipler);
        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}
