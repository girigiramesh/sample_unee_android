package com.sample_unee_android.network;


import com.sample_unee_android.util.StringConverterFactory;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ramesh on 1/5/17.
 */

public class NetworkHandler {
    private static NetworkHandler ourInstance = new NetworkHandler();

    public static NetworkHandler getInstance() {
        return ourInstance;
    }

    private NetworkHandler() {
    }

    private Retrofit opi9Retrofit = new Retrofit.Builder()
            .baseUrl("https://quiet-reef-36650.herokuapp.com")
            .addConverterFactory(StringConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private Opi9Api opi9Api = opi9Retrofit.create(Opi9Api.class);

    public Call<String> signIn(String email, String password) {
        return opi9Api.signIn(email, password);
    }

    public Call<String> signUp(String firstname, String lastname, String email, String password, String password_confirmation, String phone, String usertypereg, String gender) {
        return opi9Api.signUp(firstname, lastname, email, password, password_confirmation, phone, usertypereg, gender);
    }

    public Call<String> usersList(String accesstoken) {
        return opi9Api.usersList(accesstoken);
    }

    public Call<String> stripePayment(String email, String stripeToken, String accesstoken) {
        return opi9Api.stripePayment(email, stripeToken, accesstoken);
    }
}
