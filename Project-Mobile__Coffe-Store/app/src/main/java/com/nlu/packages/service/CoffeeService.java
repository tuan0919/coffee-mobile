package com.nlu.packages.service;

import com.nlu.packages.dto.json.carts.CartItemJSON;
import com.nlu.packages.dto.request.LoginRequestDTO;
import com.nlu.packages.dto.response.cart.CartResponseDTO;
import com.nlu.packages.inventory.checkout_recycle.CheckOutSummaryAdapter;

import java.util.ArrayList;
import java.util.List;

import nlu.hcmuaf.android_coffee_app.dto.response.TokenResponseDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class CoffeeService {
    private final String BASE_URL = "http://192.168.88.242:8888";
    private Retrofit retrofit;

    //khởi tạo retrofit singleton
    private Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    //khởi tạo Retrofit với token
    public CoffeeApi getRetrofitInstance(String token) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Authorization", "Bearer " + token)
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                })
                .build();
        retrofit = getRetrofitInstance().newBuilder()
                .client(client)
                .build();
        return retrofit.create(CoffeeApi.class);
    }

    // Khởi tạo Retrofit mặc định (không có token)
    public CoffeeApi getClient() {
        return getRetrofitInstance().create(CoffeeApi.class);
    }
}
