package com.example.myapplication.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpClientHelper {
    private static final String TAG = OkHttpClientHelper.class.getSimpleName();

    private static OkHttpClient sClient;

    public static OkHttpClient getClient () {
        if(sClient == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.level(HttpLoggingInterceptor.Level.BASIC);
            sClient = new OkHttpClient.Builder() .addInterceptor(logging).build();

        }
        return sClient;
    }
}
