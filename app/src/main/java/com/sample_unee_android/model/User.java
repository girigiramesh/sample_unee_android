package com.sample_unee_android.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Ramesh on 1/5/17.
 */

public class User {
    private static final String TAG = "User";
    public static final String EMAIL="email";
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("connected_user")
    @Expose
    private List<String> connectedUser;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("usertypereg")
    @Expose
    private String usertypereg;
    @SerializedName("isSubscribed")
    @Expose
    private String isSubscribed;

    public String getIsSubscribed() {
        return isSubscribed;
    }

    public void setIsSubscribed(String isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

    /**
     * @return The lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname The lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return The firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname The firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The connectedUser
     */
    public List<String> getConnectedUser() {
        return connectedUser;
    }

    /**
     * @param connectedUser The connected_user
     */
    public void setConnectedUser(List<String> connectedUser) {
        this.connectedUser = connectedUser;
    }

    /**
     * @return The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return The usertypereg
     */
    public String getUsertypereg() {
        return usertypereg;
    }

    /**
     * @param usertypereg The usertypereg
     */
    public void setUsertypereg(String usertypereg) {
        this.usertypereg = usertypereg;
    }

    public static User fromJson(String jsonString) {
        return new Gson().fromJson(jsonString, User.class);
    }

    public JSONObject toJson() {
        String jsonRepresentation = new Gson().toJson(this, User.class);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonRepresentation);
        } catch (JSONException e) {
            Log.d(TAG, "toJson: " + e.getLocalizedMessage());
        }
        return jsonObject;
    }

    public String getFullName() {
        return this.firstname + " " + this.lastname;
    }
}

