package com.emulatorBank.core;

import com.emulatorBank.requestresponse.EmulatorRequest;
import com.emulatorBank.requestresponse.EmulatorResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MessageServerAPI {

    @POST("/push/add")
    Call<EmulatorResponse> sendNotif(@Body EmulatorRequest emulatorRequest);
}
