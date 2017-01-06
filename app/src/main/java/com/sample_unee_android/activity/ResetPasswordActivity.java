package com.sample_unee_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sample_unee_android.App;
import com.sample_unee_android.R;
import com.sample_unee_android.util.Util;

/**
 * Created by Ramesh on 1/5/17.
 */

public class ResetPasswordActivity extends BaseActivity {
    private LinearLayout pwd_reset_doneLL, resetPwdLL;
    private boolean signUPFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        Util.setTypefaces(App.latoBoldTypeface, ((Button) findViewById(R.id.reset_pwdBTN)));
        Util.setTypefaces(App.latoLightTypeface, ((EditText) findViewById(R.id.emailET)));
        Util.setTypefaces(App.latoLightTypeface, ((TextView) findViewById(R.id.emailSentTV)), (TextView) findViewById(R.id.resentPasswordInfoTV), ((TextView) findViewById(R.id.errorMsgTV)), ((TextView) findViewById(R.id.orTV)));
        Util.setTypefaces(App.latoBoldTypeface, ((TextView) findViewById(R.id.resetPwdTV)), (TextView) findViewById(R.id.resetPasswordTV), ((TextView) findViewById(R.id.signInTV)), ((TextView) findViewById(R.id.signUpTV)));

        pwd_reset_doneLL = (LinearLayout) findViewById(R.id.pwd_reset_doneLL);
        resetPwdLL = (LinearLayout) findViewById(R.id.reset_pwdLL);
        ((TextView) findViewById(R.id.signInTV)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(ResetPasswordActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
        ((TextView) findViewById(R.id.signUpTV)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(ResetPasswordActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        ((Button) findViewById(R.id.reset_pwdBTN)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView) findViewById(R.id.errorMsgTV)).setVisibility(View.INVISIBLE);
                showProgressDialog("Please Wait ..!");
                String email = ((EditText) findViewById(R.id.emailET)).getText().toString();

                if (((EditText) findViewById(R.id.emailET)).getText().toString().length() == 0) {
                    ((EditText) findViewById(R.id.emailET)).setError("Please Enter Email.");
                    signUPFlag = false;
                } else if (!validateEmail(((EditText) findViewById(R.id.emailET)).getText().toString())) {
                    ((EditText) findViewById(R.id.emailET)).setError("Sorry, but UNEE is an exclusive marketplace for students. Please include your university email.");
                    signUPFlag = false;
                }
            }
        });
    }
}
