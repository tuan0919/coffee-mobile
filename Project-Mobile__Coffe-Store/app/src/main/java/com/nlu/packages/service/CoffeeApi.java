package com.nlu.packages.service;

import com.nlu.packages.dto.json.users.UserJSON;
import com.nlu.packages.dto.request.LoginRequestDTO;
import com.nlu.packages.dto.request.RegisterRequestDTO;
import com.nlu.packages.dto.request.VerifyRequestDTO;
import com.nlu.packages.dto.response.cart.CartResponseDTO;
import com.nlu.packages.dto.response.product.ProductResponseDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.MessageResponseDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.TokenResponseDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CoffeeApi {
    // Link API:localhost:8888/api
    @POST("api/v1/dang-nhap")
    Call<TokenResponseDTO> login(@Body LoginRequestDTO loginRequestDTO);

    @POST("api/v1/dang-ky")
    Call<MessageResponseDTO> register(@Body RegisterRequestDTO loginRequestDTO);

    @POST("api/v1/xac-minh")
    Call<MessageResponseDTO> verifyAccount(@Body VerifyRequestDTO requestDTO);

    @GET("api/v1/san-pham/nuoc-uong")
    Call<List<ProductResponseDTO>> getProductWithType(String typePathName, String name, Long id);

    @GET("api/v1/san-pham/nuoc-uong/{categoryPathName}")
    Call<List<ProductResponseDTO>> getProductWithCate (String typePathName,
                                                       @Path("categoryPathName") String categoryPathName,
                                                       String name, Long id);
    @GET("api/v2/gio-hang")
    Call<CartResponseDTO> getCart();
}
