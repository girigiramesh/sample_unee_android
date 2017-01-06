package com.sample_unee_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
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

public class SignUpActivity extends BaseActivity {
    private Toolbar toolbar;
    private boolean signUPFlag = true;
    private RadioGroup gender_rg, intrested_opinions_rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Util.setTypefaces(App.latoBoldTypeface, ((TextView) findViewById(R.id.displaySignUp)), ((TextView) findViewById(R.id.signInTV)));
        Util.setTypefaces(App.latoBoldTypeface, ((TextView) findViewById(R.id.gender_tv)));
        Util.setTypefaces(App.latoLightTypeface, ((TextView) findViewById(R.id.termsTV)), ((TextView) findViewById(R.id.alreadyTV)));
        Util.setTypefaces(app.latoLightTypeface, ((EditText) findViewById(R.id.user_name_et)), ((EditText) findViewById(R.id.first_name_et)));
        Util.setTypefaces(app.latoLightTypeface, ((EditText) findViewById(R.id.last_name_et)), ((EditText) findViewById(R.id.emailET)));
        Util.setTypefaces(app.latoLightTypeface, ((EditText) findViewById(R.id.phone_et)), ((EditText) findViewById(R.id.passwordET)));
        Util.setTypefaces(app.latoLightTypeface, ((EditText) findViewById(R.id.confirm_password_et)));
        ((Button) findViewById(R.id.send_comfrm_linkBTN)).setTypeface(app.latoBoldTypeface);
        gender_rg = (RadioGroup) findViewById(R.id.gender_rg);
        intrested_opinions_rg = (RadioGroup) findViewById(R.id.intrested_opinions_rg);

        // Sign up Button Click Listener
        ((Button) findViewById(R.id.send_comfrm_linkBTN)).setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                if (!isNetworkAvailable()) {
                    showToast("You're not connected to a Network..");
                    return;
                }
                if (((EditText) findViewById(R.id.user_name_et)).getText().toString().length() == 0) {
                    ((EditText) findViewById(R.id.user_name_et)).setError("Please Enter User Name.");
                    signUPFlag = false;
                }
                if (((EditText) findViewById(R.id.first_name_et)).getText().toString().length() == 0) {
                    ((EditText) findViewById(R.id.first_name_et)).setError("Please Enter First Name.");
                    signUPFlag = false;
                }
                if (((EditText) findViewById(R.id.last_name_et)).getText().toString().length() == 0) {
                    ((EditText) findViewById(R.id.last_name_et)).setError("Please Enter Last Name.");
                    signUPFlag = false;
                }
                if (((EditText) findViewById(R.id.emailET)).getText().toString().length() == 0) {
                    ((EditText) findViewById(R.id.emailET)).setError("Please Enter Email.");
                    signUPFlag = false;
                } else if (!validateEmail(((EditText) findViewById(R.id.emailET)).getText().toString())) {
                    ((EditText) findViewById(R.id.emailET)).setError("Sorry, but UNEE is an exclusive marketplace for students. Please include your university email.");
                    signUPFlag = false;
                }
                if (((EditText) findViewById(R.id.phone_et)).getText().toString().length() == 0) {
                    ((EditText) findViewById(R.id.phone_et)).setError("Please Enter Phone Name.");
                    signUPFlag = false;
                }
                if (((EditText) findViewById(R.id.passwordET)).getText().toString().length() < 6) {
                    ((EditText) findViewById(R.id.passwordET)).setError("Password is too short (minimum is 6 characters.");
                    signUPFlag = false;
                }
                if (((EditText) findViewById(R.id.confirm_password_et)).getText().toString().length() < 6) {
                    ((EditText) findViewById(R.id.confirm_password_et)).setError("Confirm Password is too short (minimum is 6 characters.");
                } else if (!((EditText) findViewById(R.id.passwordET)).getText().toString().equals(((EditText) findViewById(R.id.confirm_password_et)).getText().toString())) {
                    ((EditText) findViewById(R.id.confirm_password_et)).setError("Password and Confirm Password aren't Matched..");
                }
                if (intrested_opinions_rg.getCheckedRadioButtonId() == -1) {
                    ((EditText) findViewById(R.id.confirm_password_et)).setError("Select intrested to give opinions!");
                    return;
                }
                if (gender_rg.getCheckedRadioButtonId() == -1) {
                    ((EditText) findViewById(R.id.confirm_password_et)).setError("Select gender!");
                    return;
                }

                if (signUPFlag) {
                    showProgressDialog("Please Wait..!");
                    NetworkHandler.getInstance().signUp(((EditText) findViewById(R.id.first_name_et)).getText().toString(),
                            ((EditText) findViewById(R.id.last_name_et)).getText().toString(),
                            ((EditText) findViewById(R.id.emailET)).getText().toString(),
                            ((EditText) findViewById(R.id.passwordET)).getText().toString(),
                            ((EditText) findViewById(R.id.confirm_password_et)).getText().toString(),
                            ((EditText) findViewById(R.id.phone_et)).getText().toString(),
                            gender_rg.getCheckedRadioButtonId() == R.id.male_rb ? "Male" : "Female",
                            intrested_opinions_rg.getCheckedRadioButtonId() == R.id.rb_intrested_giving_opinions ? "Yes" : "No"
                    ).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            dismissProgressDialog();
                            if (response.isSuccessful()) {
                                Log.d(TAG, "onResponse: " + response.raw());
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body());
                                    if (jsonObject.optString("status").equals("success")) {
                                        MailConfirmationActivity.start(SignUpActivity.this);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), jsonObject.optString("error"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e(TAG, "onResponse: " + response.raw());
                                showAlert("Oops !", "This email is already registered.", null, null, null, null, false);
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            dismissProgressDialog();
                            Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                            showAlert("Oops !", t.getLocalizedMessage(), null, null, null, null, false);
                        }
                    });
                } else {
                    signUPFlag = true;
                }
            }
        });
        ((TextView) findViewById(R.id.signInTV)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
    private void buildToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Activity Icon
        ImageView homeIcon = (ImageView) findViewById(R.id.homeIcon);
        homeIcon.setBackgroundResource(R.drawable.back_arrow);

        LinearLayout activityIconLL = (LinearLayout) findViewById(R.id.homeIconLL);
        activityIconLL.setOnClickListener(homeIconListener);
    }

    View.OnClickListener homeIconListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
