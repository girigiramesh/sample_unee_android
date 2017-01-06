package com.sample_unee_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sample_unee_android.App;
import com.sample_unee_android.R;
import com.sample_unee_android.network.NetworkHandler;
import com.sample_unee_android.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ramesh on 1/5/17.
 */

public class SignInActivity extends BaseActivity {
    private boolean signUPFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        Util.setTypefaces(App.latoBoldTypeface, (((TextView) findViewById(R.id.signUpTV))));
        Util.setTypefaces(App.latoLightTypeface, ((TextView) findViewById(R.id.newUserTV)), ((TextView) findViewById(R.id.forgot_pwd)));
        Util.setTypefaces(app.latoLightTypeface, ((EditText) findViewById(R.id.usernameET)), ((EditText) findViewById(R.id.passwordET)));
        ((Button) findViewById(R.id.nextBTN)).setTypeface(app.latoBoldTypeface);


        ((Button) findViewById(R.id.nextBTN)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (((EditText) findViewById(R.id.usernameET)).getText().toString().length() == 0) {
                    ((EditText) findViewById(R.id.usernameET)).setError("Please Enter Email.");
                    signUPFlag = false;
                } else if (!validateEmail(((EditText) findViewById(R.id.usernameET)).getText().toString())) {
                    ((EditText) findViewById(R.id.usernameET)).setError("Sorry, but UNEE is an exclusive marketplace for students. Please include your university email.");
                    signUPFlag = false;
                }

                if (((EditText) findViewById(R.id.passwordET)).getText().toString().length() < 6) {
                    ((EditText) findViewById(R.id.passwordET)).setError("Password is too short (minimum is 6 characters.");
                    signUPFlag = false;
                }

                if (signUPFlag) {

                    showProgressDialog("Please Wait..!");
                    NetworkHandler.getInstance().signIn(((EditText) findViewById(R.id.usernameET)).getText().toString(), ((EditText) findViewById(R.id.passwordET)).getText().toString()).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            dismissProgressDialog();
                            if (response.isSuccessful()) {
                                Log.d(TAG, "onResponse: " + response.raw());
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body());
                                    if (jsonObject.optString("status").equals("success")) {
                                        HomeActivity.start(SignInActivity.this);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), jsonObject.optString("error"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e(TAG, "onResponse: " + response.raw());
                                showAlert("Sign In !", "Email and/or Password doesn't match our records. Please try again.", "Ok", null, null, null, false);
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            dismissProgressDialog();
                            Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                            showAlert("Sign In !", t.getLocalizedMessage(), "Ok", null, null, null, false);
                        }
                    });

                } else {
                    signUPFlag = true;
                }
            }
        });
        ((TextView) findViewById(R.id.signUpTV)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        ((TextView) findViewById(R.id.forgot_pwd)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(SignInActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    View.OnClickListener homeIconListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
