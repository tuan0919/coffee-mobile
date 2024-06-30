package com.nlu.packages.net_working;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

public class CoffeeAppService {
    private static final String BASE_URL = "http://192.168.1.2:8888/";
    private static Retrofit instance;

    private static Retrofit create() {
        if (instance == null) {
            instance = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
        }
        return instance;
    }
    public static CoffeeAppApi getInstance(String token) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Authorization", "Bearer " + token)
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                }).build();
        instance = create().newBuilder()
                .client(client).build();
        return instance.create(CoffeeAppApi.class);
    }
    public static CoffeeAppApi getInstance() {
        return create().create(CoffeeAppApi.class);
    }

}
