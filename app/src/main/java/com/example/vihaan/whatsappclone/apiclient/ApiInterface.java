package com.example.vihaan.whatsappclone.apiclient;

import com.example.vihaan.whatsappclone.ui.models.Chat;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by vihaan on 21/05/17.
 */

public interface ApiInterface {

    @GET("https://gist.githubusercontent.com/VihaanVerma89/b2a305cc3558298214fb516777cc0472/raw/b1fe6bff0f9d1a560a68d2f6c95ba3123e709c0a/chat.json")
    Call<ArrayList<Chat>> getChats();
}
