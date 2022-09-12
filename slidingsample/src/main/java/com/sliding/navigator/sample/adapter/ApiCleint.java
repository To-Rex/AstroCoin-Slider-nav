package com.sliding.navigator.sample.adapter;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiCleint {
    private static Retrofit getretrofit() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://astrum.uubek.com/api/")
                .client(okHttpClient)
                .build();
        //.baseUrl("http://astrum.uubek.com/api/")
    }
    public static UserService getUserService() {
        return getretrofit().create(UserService.class);
    }
}