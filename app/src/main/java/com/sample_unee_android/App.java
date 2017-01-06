package com.sample_unee_android;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.multidex.MultiDex;

import com.sample_unee_android.manager.SharedPreferenceManager;

/**
 * Created by Ramesh on 1/5/17.
 */

public class App extends Application {


    public static Typeface latoTypeface, latoBlackTypeface, latoBoldTypeface, latoLightTypeface;
    public static String latoFont, latoLightFont;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        AppSingleton.getInstance().setApplication(this);
        SharedPreferenceManager.getInstance().init(this);

        latoFont = "Lato-Regular.ttf";
        latoLightFont = "Lato-Light.ttf";

//        latoTypeface = Typeface.createFromAsset(getAssets(), "Lato-Regular.ttf");
//        latoBlackTypeface = Typeface.createFromAsset(getAssets(), "Lato-Black.ttf");
//        latoBoldTypeface = Typeface.createFromAsset(getAssets(), "Lato-Bold.ttf");
//        latoLightTypeface = Typeface.createFromAsset(getAssets(), "Lato-Light.ttf");
    }

}
