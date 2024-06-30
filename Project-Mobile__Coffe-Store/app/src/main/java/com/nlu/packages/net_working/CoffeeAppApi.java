package com.nlu.packages.net_working;

import com.nlu.packages.response_dto.cart.CartResponseDTO;
import com.nlu.packages.response_dto.product.ProductResponseDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import java.util.List;
import java.util.Map;

public interface CoffeeAppApi {
    @GET("api/v1/san-pham/{productTypePath}/{categoryTypePath}")
    Call<List<ProductResponseDTO>> getProduct(@Path("productTypePath") String productTypePath,
                                              @Path("categoryTypePath") String categoryTypePath,
                                              @QueryMap Map<String, String> options);

    @GET("api/v2/gio-hang")
    Call<CartResponseDTO> getCart();
}
