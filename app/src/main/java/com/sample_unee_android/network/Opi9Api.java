package com.sample_unee_android.network;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Ramesh on 1/5/17.
 */

public interface Opi9Api {

    @GET("/users/signin.json")
    Call<String> signIn(@Query("email") String email, @Query("password") String password);

    @POST("/users/signup.json")
    Call<String> signUp(@Query("firstname") String firstname,
                        @Query("lastname") String lastname,
                        @Query("email") String email,
                        @Query("password") String password,
                        @Query("password_confirmation") String password_confirmation,
                        @Query("phone") String phone,
                        @Query("usertypereg") String usertypereg,
                        @Query("gender") String gender);

    @GET("/users.json")
    Call<String> usersList(@Query("accesstoken") String accesstoken);

    @FormUrlEncoded
    @POST("/charges.json")
    Call<String> stripePayment(@Field("email") String email, @Field("stripeToken") String stripeToken, @Field("accesstoken") String accesstoken);
}
