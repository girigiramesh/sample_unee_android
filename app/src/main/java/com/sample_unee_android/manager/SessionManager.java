package com.sample_unee_android.manager;



import com.sample_unee_android.model.User;
import com.sample_unee_android.util.Util;

import org.json.JSONObject;

/**
 * Created by Ramesh on 1/5/17.
 */

public class SessionManager {
    private static final String USER_LOGIN = "com.sample_unee_android.manager.manager.USER_LOGIN";
    private static final String ACCESS_TOKEN = "com.sample_unee_android.manager.manager.ACCESS_TOKEN";
    private static final String STRIPE_TOKEN = "com.sample_unee_android.manager.manager.STRIPE_TOKEN";
    private static SessionManager ourInstance = new SessionManager();

    public static SessionManager getInstance() {
        return ourInstance;
    }

    private SessionManager() {
    }

    public void putSession(String accessToken, JSONObject userJson) {
        SharedPreferenceManager.getInstance().putString(ACCESS_TOKEN, accessToken);
        SharedPreferenceManager.getInstance().putString(USER_LOGIN, userJson.toString());
    }

    public User getSession() {
        String info = SharedPreferenceManager.getInstance().getString(USER_LOGIN, "");
        if (!Util.isNotNullAndNotEmpty(info)) {
            throw new IllegalStateException("User not logged in.");
        }
        return User.fromJson(info);
    }

    public String getAccessToken() {
        return SharedPreferenceManager.getInstance().getString(ACCESS_TOKEN, "");
    }

    public String getStripeToken() {
        return SharedPreferenceManager.getInstance().getString(STRIPE_TOKEN, "");
    }

    public boolean isSessionAvailable() {
        String info = SharedPreferenceManager.getInstance().getString(USER_LOGIN, "");
        if (Util.isNotNullAndNotEmpty(info)) {
            return true;
        }
        return false;
    }

    public void removeSession() {
        SharedPreferenceManager.getInstance().remove(USER_LOGIN);
    }

}
