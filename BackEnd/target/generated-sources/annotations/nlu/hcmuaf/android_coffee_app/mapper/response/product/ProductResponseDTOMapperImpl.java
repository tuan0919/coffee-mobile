package nlu.hcmuaf.android_coffee_app.mapper.response.product;

import javax.annotation.processing.Generated;
import nlu.hcmuaf.android_coffee_app.dto.response.product.ProductResponseDTO;
import nlu.hcmuaf.android_coffee_app.entities.Categories;
import nlu.hcmuaf.android_coffee_app.entities.Discounts;
import nlu.hcmuaf.android_coffee_app.entities.Ingredients;
import nlu.hcmuaf.android_coffee_app.entities.Products;
import nlu.hcmuaf.android_coffee_app.enums.EProductSize;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-29T14:39:18+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class ProductResponseDTOMapperImpl extends ProductResponseDTOMapper {

    @Override
    public ProductResponseDTO mapToDTO(Products product) {
        if ( product == null ) {
            return null;
        }

        ProductResponseDTO.ProductResponseDTOBuilder productResponseDTO = ProductResponseDTO.builder();

        productResponseDTO.categoryId( productCategoriesCategoryId( product ) );
        productResponseDTO.availableSizes( findSizes( product ) );
        productResponseDTO.availableIngredients( findIngredients( product ) );
        productResponseDTO.categoryName( productCategoriesCategoryName( product ) );
        Double percent = productDiscountPercent( product );
        if ( percent != null ) {
            productResponseDTO.discountPercent( percent );
        }
        else {
            productResponseDTO.discountPercent( 0 );
        }
        productResponseDTO.productId( product.getProductId() );
        productResponseDTO.productName( product.getProductName() );
        productResponseDTO.basePrice( product.getBasePrice() );
        productResponseDTO.avatar( product.getAvatar() );
        productResponseDTO.productType( product.getProductType() );

        return productResponseDTO.build();
    }

    @Override
    ProductResponseDTO.IngredientDTO mapToDTO(Ingredients ingredient) {
        if ( ingredient == null ) {
            return null;
        }

        ProductResponseDTO.IngredientDTO ingredientDTO = new ProductResponseDTO.IngredientDTO();

        if ( ingredient.getIngredientId() != null ) {
            ingredientDTO.setIngredientId( ingredient.getIngredientId() );
        }
        ingredientDTO.setIngredientName( ingredient.getIngredientName() );
        ingredientDTO.setIngredientType( ingredient.getIngredientType() );
        ingredientDTO.setIngredientEnum( ingredient.getIngredientEnum() );
        if ( ingredient.getAddPrice() != null ) {
            ingredientDTO.setAddPrice( ingredient.getAddPrice() );
        }

        return ingredientDTO;
    }

    @Override
    ProductResponseDTO.ProductSizeDTO mapToDTO(EProductSize size) {
        if ( size == null ) {
            return null;
        }

        ProductResponseDTO.ProductSizeDTO productSizeDTO = new ProductResponseDTO.ProductSizeDTO();

        productSizeDTO.setSizeEnum( size );
        productSizeDTO.setMultipler( size.getMultipler() );

        return productSizeDTO;
    }

    private long productCategoriesCategoryId(Products products) {
        if ( products == null ) {
            return 0L;
        }
        Categories categories = products.getCategories();
        if ( categories == null ) {
            return 0L;
        }
        long categoryId = categories.getCategoryId();
        return categoryId;
    }

    private String productCategoriesCategoryName(Products products) {
        if ( products == null ) {
            return null;
        }
        Categories categories = products.getCategories();
        if ( categories == null ) {
            return null;
        }
        String categoryName = categories.getCategoryName();
        if ( categoryName == null ) {
            return null;
        }
        return categoryName;
    }

    private Double productDiscountPercent(Products products) {
        if ( products == null ) {
            return null;
        }
        Discounts discount = products.getDiscount();
        if ( discount == null ) {
            return null;
        }
        Double percent = discount.getPercent();
        if ( percent == null ) {
            return null;
        }
        return percent;
    }
}
