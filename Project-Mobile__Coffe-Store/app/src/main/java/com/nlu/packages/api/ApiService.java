package com.nlu.packages.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nlu.packages.model.Login;
import com.nlu.packages.model.Product;
import com.nlu.packages.model.Section;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    @POST("v1/dang-nhap")
    Call<Section> loginUser(@Body Login user) ;
    // khng ingj dạng ngày
    Interceptor intercept= chain -> {
        Request request = chain.request();
        Request.Builder builder= request.newBuilder();
        builder.addHeader("Authorization", "Bearer YOUR_TOKEN_HERE");
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
    Call<Product> getProduct(@Query("id")int idProduct);



}
