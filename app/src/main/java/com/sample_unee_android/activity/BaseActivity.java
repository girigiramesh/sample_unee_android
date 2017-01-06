package com.sample_unee_android.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sample_unee_android.App;
import com.sample_unee_android.AppSingleton;
import com.sample_unee_android.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ramesh on 1/5/17.
 */

public class BaseActivity extends AppCompatActivity {

    public static final String TAG = BaseActivity.class.getSimpleName();
    public ProgressDialog dialog = null;
    protected App app = AppSingleton.getInstance().getApplication();
    private AlertDialog alertDialog;

    public void showProgressDialog() {
        showProgressDialog("please wait..");
    }

    public void showProgressDialog(String message) {
        if (dialog == null)
            dialog = new ProgressDialog(this);
        dialog.setMessage(message);

        dialog.setCancelable(false);
        try {
            if (!dialog.isShowing())
                dialog.show();
        } catch (Exception e) {
            showLog(true, TAG, e.getLocalizedMessage());

        }
    }

    public void dismissProgressDialog() {
        try {
            if (dialog.isShowing())
                dialog.dismiss();
        } catch (Exception e) {
            showLog(true, TAG, e.getLocalizedMessage());
        }
    }

    public void showAlert(String title, String alertMsg, String positiveActionName, DialogInterface.OnClickListener positiveAction, String negativeActionName, DialogInterface.OnClickListener negativeAction, boolean dual) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(alertMsg);
        builder.setCancelable(false);

        if (positiveActionName != null && positiveAction != null) {
            builder.setPositiveButton(positiveActionName, positiveAction);
        } else {

            if (positiveActionName != null) {
                builder.setPositiveButton(positiveActionName, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
            } else {
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
        }


        if (dual) {
            if (negativeActionName != null && negativeActionName == null) {
                builder.setNegativeButton(negativeActionName, negativeAction);
            } else {
                if (negativeActionName != null) {
                    builder.setNegativeButton(negativeActionName, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                } else {
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                }
            }
        }

        alertDialog = builder.create();
        alertDialog.show();
    }

    protected void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected void showLog(boolean print, String tag, String string) {
        if (print)
            Log.d(tag, string);

    }

    protected boolean isNetworkAvailable() {
        ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cn.getActiveNetworkInfo();
        if (nf != null && nf.isConnected() == true) {
            return true;
        } else {
            return false;
        }
    }

    protected void dismissKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    protected boolean validateEmail(String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        Boolean validity = matcher.matches();
        if (email.length() >= 7) {
            String eduCheck = email.substring(email.length() - 3);
//            if (!eduCheck.equals("edu"))
//                validity = false;
        }
        return validity;
    }


    protected Dialog customDialog(int layout) {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setTitle("");
        dialog.setContentView(layout);
        dialog.setCancelable(true);
        dialog.show();
        return dialog;
    }

    // Network BroadcastReceiver
    Dialog noNetworkDialog;
    IntentFilter mIntentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!isNetworkAvailable()) {
                noNetworkDialog = customDialog(R.layout.no_network_dialog);
                noNetworkDialog.setCancelable(false);
                if (!noNetworkDialog.isShowing())
                    noNetworkDialog.show();
            } else {
                if (noNetworkDialog != null)
                    if (noNetworkDialog.isShowing())
                        noNetworkDialog.dismiss();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    public void customToolbar(ViewGroup toolbar) {
        ImageView homeIcon = (ImageView) toolbar.findViewById(R.id.homeIcon);
        homeIcon.setBackgroundResource(R.drawable.back_arrow);
        LinearLayout homeIconLL = (LinearLayout) toolbar.findViewById(R.id.homeIconLL);
        homeIconLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
