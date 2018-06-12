package com.emulatorBank.core;

import com.emulatorBank.requestresponse.EmulatorResponse;
import com.emulatorBank.requestresponse.Crutch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class ScheduleTask {

    private final static int FIXEDRATE = 5000;
    private final static int FIXEDRATE2 = 5000;

    @Scheduled(fixedRate = FIXEDRATE)
    public void reportCurrentTime() {
        if (Crutch.flagSender) return;      //Флаг отправки сообщения
        if (Crutch.er != null && Crutch.urladdress != null) {
            if (!Crutch.urladdress.equals("http://90.188.7.43:8080/")) {
                System.out.println("Этот адрес не принадлежит веб-серверу");
                return;
            }
            if (!Crutch.er.notif.isEmpty() && !Crutch.er.phone.isEmpty()) {
                MessageServerAPI messageServerAPI = RetrofitBuilder.getInstance(Crutch.urladdress).create(MessageServerAPI.class);        //"http://90.188.7.43:8080/"
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
                Crutch.flagSender = true;   //Пометели сообщение как отправленное
            }
        }
    }


    //Ваш логин: z1528424553368
    //Ваш пароль: 525553
    public final static String LOGIN_IQSMS = "";
    public final static String PASSWORD_IQSMS = "";
    public final static String PLUS7 = "+7";

    @Scheduled(fixedRate = FIXEDRATE2)
    public void printPush() {
        Date date = new Date();
        if (Crutch.flagSender) return;      //Флаг отправки сообщения
        if (Crutch.er != null && Crutch.urladdress != null) {
            if (!Crutch.urladdress.equals("http://api.iqsms.ru/messages/v2/")) {
                System.out.println("Этот адрес не принадлежит смс-агрегатору");
                return;
            }
            if (!Crutch.er.notif.isEmpty() && !Crutch.er.phone.isEmpty()) {
                MessageServerAPI messageServerAPI = RetrofitBuilderForSMS.getInstance(Crutch.urladdress).create(MessageServerAPI.class);
                Call<String> sendMessageBySMS = messageServerAPI.sendMessageBySMS(LOGIN_IQSMS, PASSWORD_IQSMS, PLUS7 + Crutch.er.phone, Crutch.er.notif);
                sendMessageBySMS.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            System.out.println(date.toString());
                            System.out.println("На номер " + Crutch.er.phone + " было отправлено СМС-сообщение \"" + Crutch.er.notif + "\"");
                            System.out.println(response.body());
                            Crutch.er = null;
                        } else {
                            System.out.println("RESPONSE CODE " + response.code());
                            System.out.println("СМС-Агрегатор не отвечает. " + date.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        System.out.println("FAILURE " + t);
                        System.out.println("СМС-Агрегатор не отвечает. " + date.toString());
                    }
                });
                Crutch.flagSender = true;   //Пометели сообщение как отправленное
            }
        }
    }

}