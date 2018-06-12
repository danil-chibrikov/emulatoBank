package com.emulatorBank.core;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitBuilderForSMS {

    private static Retrofit INSTANCE;

    public static Retrofit getInstance(String urladdress) {
        if (INSTANCE == null) {
            synchronized (Retrofit.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Retrofit.Builder()
                            .baseUrl(urladdress)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
