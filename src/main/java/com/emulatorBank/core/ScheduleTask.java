package com.emulatorBank.core;

import com.emulatorBank.requestresponse.EmulatorResponse;
import com.emulatorBank.requestresponse.Crutch;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Random;

@Component
public class ScheduleTask {

    private final static int FIXEDRATE = 10000; //10 секунд

    private MessageServerAPI messageServerAPI;
    private Random random = new Random();

    @Scheduled(fixedRate = FIXEDRATE)
    public void reportCurrentTime() {
        if (Crutch.er != null) {
            messageServerAPI = RetrofitBuilder.getInstance().create(MessageServerAPI.class);
            Call<EmulatorResponse> sendMess = messageServerAPI.sendNotif(Crutch.er);
            sendMess.enqueue(new Callback<EmulatorResponse>() {
                @Override
                public void onResponse(Call<EmulatorResponse> call, Response<EmulatorResponse> response) {
                    if (response.isSuccessful()) {
                        System.out.println(response.body().answer);
                        Crutch.er = null;
                    } else {
                        System.out.println("RESPONSE CODE " + response.code());
                        System.out.println("Сервер не отвечает");
                    }
                }

                @Override
                public void onFailure(Call<EmulatorResponse> call, Throwable t) {
                    System.out.println("FAILURE " + t);
                    System.out.println("Сервер не отвечает");
                }
            });
        }
    }
}