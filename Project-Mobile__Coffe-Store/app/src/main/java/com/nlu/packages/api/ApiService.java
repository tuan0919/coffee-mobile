package com.nlu.packages.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nlu.packages.response.product.ProductResponseDTO;

import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService api = new Retrofit.Builder()
            .baseUrl("http://192.168.14.3:8888/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    // khong dinh dạng ngày
    Interceptor intercept= chain -> {
        Request request = chain.request();
        Request.Builder builder= request.newBuilder();
        builder.addHeader("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJucWF0MDkxOSIsImlhdCI6MTcxOTczNzY2OCwiZXhwIjoxNzE5ODI0MDY4fQ.HO7D1s1hrLjY50-UdH0Hfu3aJUfyqT1HavVHsk7Fgdo");
        return chain.proceed(builder.build());
    };
    OkHttpClient.Builder okBuilder= new OkHttpClient.Builder().addInterceptor(intercept);
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.14.3:8888/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okBuilder.build())
            .build()
            .create(ApiService.class);
    @GET("v1/san-pham/nuoc-uong")
    Call<List<ProductResponseDTO>> getProduct(@Query("id")int idProduct);






}
