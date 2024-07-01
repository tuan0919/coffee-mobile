package com.nlu.packages.service;

import com.nlu.packages.request_dto.VerifyRequestDTO;

import java.util.List;
import java.util.Map;

import com.nlu.packages.response_dto.MessageResponseDTO;
import com.nlu.packages.response_dto.TokenResponseDTO;
import com.nlu.packages.response_dto.cart.CartItemRequestDTO;
import com.nlu.packages.response_dto.cart.CartResponseDTO;
import com.nlu.packages.response_dto.product.ProductResponseDTO;
import com.nlu.packages.response_dto.user.UserDTO;
import retrofit2.Call;
import retrofit2.http.*;
import com.nlu.packages.request_dto.LoginRequestDTO;
import com.nlu.packages.request_dto.RegisterRequestDTO;

public interface CoffeeApi {
    @POST("api/v1/dang-nhap")
    Call<TokenResponseDTO> login(@Body LoginRequestDTO loginRequestDTO);

    @POST("api/v1/dang-ky")
    Call<MessageResponseDTO> register(@Body RegisterRequestDTO loginRequestDTO);

    @POST("api/v1/xac-minh")
    Call<MessageResponseDTO> verifyAccount(@Body VerifyRequestDTO requestDTO);

    @GET("api/v1/san-pham")
    Call<List<ProductResponseDTO>> getAllProduct();

    @GET("api/v1/san-pham/nuoc-uong")
    Call<List<ProductResponseDTO>> getProductWithType(String typePathName, String name, Long id);

    @GET("api/v1/san-pham/nuoc-uong/{categoryPathName}")
    Call<List<ProductResponseDTO>> getProductWithCate (String typePathName,
                                                       @Path("categoryPathName") String categoryPathName,
                                                       String name, Long id);
    @GET("api/v1/san-pham/{productTypePath}/{categoryTypePath}")
    Call<List<ProductResponseDTO>> getProduct(@Path("productTypePath") String productTypePath,
                                                                                    @Path("categoryTypePath") String categoryTypePath,
                                                                                    @QueryMap Map<String, String> options);

    @GET("api/v2/gio-hang")
    Call<CartResponseDTO> getCart();

    @PUT("api/v2/gio-hang")
    Call<MessageResponseDTO> putItem(@Body CartItemRequestDTO dto);

    @GET("api/v2/nguoi-dung")
    Call<UserDTO> getUserInfo();
}
