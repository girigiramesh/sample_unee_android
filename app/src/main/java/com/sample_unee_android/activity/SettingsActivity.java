package com.sample_unee_android.activity;

import android.os.Bundle;
import android.view.ViewGroup;

import com.sample_unee_android.R;

/**
 * Created by Ramesh on 1/5/17.
 */

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        customToolbar((ViewGroup) findViewById(R.id.toolbar));
    }
}
