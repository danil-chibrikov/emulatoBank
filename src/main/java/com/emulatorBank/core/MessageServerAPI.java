package com.emulatorBank.core;

import com.emulatorBank.requestresponse.EmulatorRequest;
import com.emulatorBank.requestresponse.EmulatorResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MessageServerAPI {

    //Запрос к веб-серверу
    @POST("/push/add")
    Call<EmulatorResponse> sendNotif(@Body EmulatorRequest emulatorRequest);

    //Запрос к смс-агрегатору
    @GET("/send/?")
    Call<String> sendMessageBySMS(@Query("login") String login, @Query("password") String password,
                                @Query("phone") String phone, @Query("text") String accept_code);
}
