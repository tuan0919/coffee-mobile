package com.nlu.packages.service;

import com.nlu.packages.request_dto.LoginRequestDTO;
import com.nlu.packages.request_dto.RegisterRequestDTO;
import com.nlu.packages.request_dto.VerifyRequestDTO;
import com.nlu.packages.request_dto.cart.CartItemRequestDTO;
import com.nlu.packages.request_dto.order.CreateOrderRequestDTO;
import com.nlu.packages.response_dto.MessageResponseDTO;
import com.nlu.packages.response_dto.TokenResponseDTO;
import com.nlu.packages.response_dto.cart.CartResponseDTO;
import com.nlu.packages.response_dto.product.ProductResponseDTO;
import com.nlu.packages.response_dto.user.UserDTO;
import com.nlu.packages.response_dto.wishlist.WishlistRequestDTO;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

public interface CoffeeApi {
    // Link API:localhost:8888/api
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

    @POST("api/v2/dat-hang")
    Call<MessageResponseDTO> createOrder(@Body CreateOrderRequestDTO dto);
    @GET("api/v1/san-pham")
    Call<List<ProductResponseDTO>> searchProduct(@Query("ten") String name);

    @GET("api/v2/yeu-thich")
    Call<List<ProductResponseDTO>> getWishList();

    @POST("api/v2/yeu-thich")
    Call<MessageResponseDTO> addToWishList(@Body WishlistRequestDTO wishlistRequestDTO);

    @DELETE("api/v2/yeu-thich/{productId}")
    Call<MessageResponseDTO> removeFromWishList(@Path("productId") Long productId);
}
