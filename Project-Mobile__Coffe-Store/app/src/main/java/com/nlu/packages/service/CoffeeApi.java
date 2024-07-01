package com.nlu.packages.service;

import com.nlu.packages.dto.request.VerifyRequestDTO;
import com.nlu.packages.dto.request.cart.CartItemRequestDTO;
import com.nlu.packages.dto.request.wishlist.WishlistRequestDTO;
import com.nlu.packages.dto.response.cart.CartResponseDTO;
import com.nlu.packages.dto.response.product.ProductResponseDTO;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import nlu.hcmuaf.android_coffee_app.dto.response.MessageResponseDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.TokenResponseDTO;
import nlu.hcmuaf.android_coffee_app.dto.request.LoginRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.request.RegisterRequestDTO;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

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
    Call<List<ProductResponseDTO>> getProductWithCate(String typePathName,
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

    @GET("api/v1/san-pham")
    Call<List<ProductResponseDTO>> searchProduct(@Query("ten") String name);

    @GET("api/v2/yeu-thich")
    Call<List<ProductResponseDTO>> getWishList();

    @POST("api/v2/yeu-thich")
    Call<MessageResponseDTO> addToWishList(@Body WishlistRequestDTO wishlistRequestDTO);

    @DELETE("api/v2/yeu-thich/{productId}")
    Call<MessageResponseDTO> removeFromWishList(@Path("productId") Long productId);
}
