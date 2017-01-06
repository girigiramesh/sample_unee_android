package com.sample_unee_android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sample_unee_android.App;
import com.sample_unee_android.R;
import com.sample_unee_android.util.Util;

/**
 * Created by Ramesh on 1/5/17.
 */

public class MailConfirmationActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, MailConfirmationActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_confirmation);
        Util.setTypefaces(App.latoLightTypeface, ((TextView) findViewById(R.id.sentVerificationTV)), ((TextView) findViewById(R.id.dontGetTV)), ((TextView) findViewById(R.id.verifiedTV)));
        Util.setTypefaces(App.latoBoldTypeface, ((TextView) findViewById(R.id.resendTV)), ((TextView) findViewById(R.id.verifyEmailTV)), ((TextView) findViewById(R.id.welcomeTV)));
        Util.setTypefaces(App.latoBoldTypeface, ((Button) findViewById(R.id.nextBTN)));

        ((TextView) findViewById(R.id.resendTV)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ((Button) findViewById(R.id.nextBTN)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(MailConfirmationActivity.this, CreateProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
