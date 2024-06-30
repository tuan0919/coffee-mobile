package com.nlu.packages.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nlu.packages.model.Login;
import com.nlu.packages.model.Section;
import com.nlu.packages.model.User;

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
//    @GET("macros/echo")
//    Call<User> getSections(@Query("user_content_key") String contentKey, @Query("lib")String lib);



}
