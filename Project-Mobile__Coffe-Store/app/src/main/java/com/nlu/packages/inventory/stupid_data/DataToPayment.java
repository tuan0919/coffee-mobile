package com.nlu.packages.inventory.stupid_data;

import com.nlu.packages.enums.EIngredient;
import com.nlu.packages.enums.EIngredientType;
import com.nlu.packages.enums.EProductSize;
import com.nlu.packages.response_dto.cart.CartResponseDTO;
import com.nlu.packages.response_dto.product.ProductResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class DataToPayment {
    List<CartResponseDTO.CartItemDTO> list = new ArrayList<>();

    public DataToPayment(List<CartResponseDTO.CartItemDTO> list) {
        this.list = list;
    }

    public DataToPayment() {

        CartResponseDTO.ProductDTO pd1 = new CartResponseDTO.ProductDTO(1,"Đường Đen Sữa Đá",45,"https://product.hstatic.net/1000075078/product/1686716532_dd-suada_c180c6187e644babbac7019a2070231e_large.jpg");
        CartResponseDTO.ProductDTO pd2 = new CartResponseDTO.ProductDTO(2,"The Coffee House Sữa Đá",39,"https://product.hstatic.net/1000075078/product/1675355354_bg-tch-sua-da-no_4fbf208885ed464ab4b5e145336d42a2_large.jpg");
        CartResponseDTO.ProductDTO pd3 = new CartResponseDTO.ProductDTO(3,"Cà Phê Sữa Đá",29,"https://product.hstatic.net/1000075078/product/1669736835_ca-phe-sua-da_e6168b6a38ec45d2b4854d2708b5d542.png");
        CartResponseDTO.ProductDTO pd4 = new CartResponseDTO.ProductDTO(4,"Cà Phê Sữa Nóng",39,"https://product.hstatic.net/1000075078/product/1639377770_cfsua-nong_9a47f58888e7444a9979e0d117d49ad3.jpg");
        CartResponseDTO.ProductDTO pd5 = new CartResponseDTO.ProductDTO(5,"Bạc Sỉu",39,"https://product.hstatic.net/1000075078/product/1639377904_bac-siu_525b9fa5055b41f183088c8e479a9472.jpg");

        ProductResponseDTO.IngredientDTO igd1 = new ProductResponseDTO.IngredientDTO(1,"Sữa tươi", EIngredientType.MILKS, EIngredient.SUA_TUOI,5);
        ProductResponseDTO.IngredientDTO igd2 = new ProductResponseDTO.IngredientDTO(2,"Chocolate",EIngredientType.SWEETENERS,EIngredient.CHOCOLATE,10);
        ProductResponseDTO.IngredientDTO igd3 = new ProductResponseDTO.IngredientDTO(3,"Thạch dừa",EIngredientType.TOPPINGS,EIngredient.THACH_DUA,10);
        ProductResponseDTO.IngredientDTO igd4 = new ProductResponseDTO.IngredientDTO(4,"Nhiều đường",EIngredientType.SWEETENERS,EIngredient.NHIEU_DUONG,0);
        ProductResponseDTO.IngredientDTO igd5 = new ProductResponseDTO.IngredientDTO(5,"Ít đường",EIngredientType.SWEETENERS,EIngredient.IT_DUONG,0);
        ProductResponseDTO.IngredientDTO igd6 = new ProductResponseDTO.IngredientDTO(6,"Trân châu",EIngredientType.TOPPINGS,EIngredient.TRAN_CHAU,10);
        ProductResponseDTO.IngredientDTO igd7 = new ProductResponseDTO.IngredientDTO(7,"Kem tươi",EIngredientType.TOPPINGS,EIngredient.KEM_TUOI,10);
        ProductResponseDTO.IngredientDTO igd8 = new ProductResponseDTO.IngredientDTO(8,"Shot Expresso",EIngredientType.TOPPINGS,EIngredient.SHOT_EXPRESSO,10);

        List<EIngredient> ingredients1 = new ArrayList<>();
        ingredients1.add(EIngredient.SUA_TUOI);
        ingredients1.add(EIngredient.TRAN_CHAU);
        List<EIngredient> ingredients2 = new ArrayList<>();
        ingredients2.add(EIngredient.SUA_TUOI);
        ingredients2.add(EIngredient.SHOT_EXPRESSO);
        List<EIngredient> ingredients3 = new ArrayList<>();
        ingredients3.add(EIngredient.SUA_TUOI);
        ingredients3.add(EIngredient.TRAN_CHAU);
        ingredients3.add(EIngredient.SHOT_EXPRESSO);


        ProductResponseDTO.ProductSizeDTO s1 = new ProductResponseDTO.ProductSizeDTO(EProductSize.SMALL,1);
        ProductResponseDTO.ProductSizeDTO s2 = new ProductResponseDTO.ProductSizeDTO(EProductSize.MEDIUM,1);
        ProductResponseDTO.ProductSizeDTO s3 = new ProductResponseDTO.ProductSizeDTO(EProductSize.LARGE,2);

        CartResponseDTO.CartItemDTO cid1 = new CartResponseDTO.CartItemDTO(pd1,2,ingredients1,EProductSize.SMALL,80);
        CartResponseDTO.CartItemDTO cid2 = new CartResponseDTO.CartItemDTO(pd2,1,ingredients2,EProductSize.MEDIUM,120);
        CartResponseDTO.CartItemDTO cid3 = new CartResponseDTO.CartItemDTO(pd3,4,ingredients3,EProductSize.LARGE,700);

        this.list.add(cid1);
        this.list.add(cid2);
        this.list.add(cid3);
    }

    public List<CartResponseDTO.CartItemDTO> getList() {
        return list;
    }
}
