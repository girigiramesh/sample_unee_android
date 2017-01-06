package com.sample_unee_android.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sample_unee_android.App;
import com.sample_unee_android.R;
import com.sample_unee_android.activity.BaseActivity;
import com.sample_unee_android.adapter.WelcomePagerAdapter;
import com.sample_unee_android.util.Util;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Util.setTypefaces(App.latoLightTypeface, ((TextView) findViewById(R.id.haveAccount)));
        ((Button) findViewById(R.id.getStartedBTN)).setTypeface(app.latoBoldTypeface);
        ((TextView) findViewById(R.id.signInTV)).setTypeface(app.latoBoldTypeface);


        ViewPager viewPager = (ViewPager) this.findViewById(R.id.view_pager);
        WelcomePagerAdapter adapter = new WelcomePagerAdapter(this);
        viewPager.setAdapter(adapter);

        ((Button) findViewById(R.id.getStartedBTN)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }

        });
        ((TextView) findViewById(R.id.signInTV)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}
