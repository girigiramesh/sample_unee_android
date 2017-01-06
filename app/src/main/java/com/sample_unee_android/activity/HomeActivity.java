package com.sample_unee_android.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sample_unee_android.App;
import com.sample_unee_android.R;
import com.sample_unee_android.fragment.ActivityFragment;
import com.sample_unee_android.fragment.HomePageFragment;
import com.sample_unee_android.fragment.MyItemsFragment;
import com.sample_unee_android.fragment.WishlistFragment;
import com.sample_unee_android.listeners.SideMenuListener;
import com.sample_unee_android.manager.SharedPreferenceManager;
import com.sample_unee_android.util.Constants;
import com.sample_unee_android.util.Util;
import com.sample_unee_android.views.SlideView;

/**
 * Created by Ramesh on 1/5/17.
 */

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class HomeActivity extends BaseActivity implements SideMenuListener {
    private View[] children;
    private View main;
    private View menu;
    private ImageView homeIcon;
    private ImageView searchIcon;
    private SlideView scrollView;

    private static boolean menuOut = false;
    private int scrollToViewIdx = 1;
    private LinearLayout homeIconLL;

    private ViewGroup toolbar;
    LinearLayout wishListRL, messagesRL, postRL, activitirsRL, myItemsRL;
    ImageView wishListIV, messagesIV, postIV, activitirsIV, myItemsIV;
    TextView wishListTV, messagesTV, postTV, activitirsTV, myItemsTV;

    public static void start(Context context) {
        Intent starter = new Intent(context, HomeActivity.class);
        context.startActivity(starter);
    }

    // used to hide warnings when we write code that is higher than the minsdk mentioned in the manifest.
    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SideMenu
        if (Build.VERSION.SDK_INT >= 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final LayoutInflater inflater = LayoutInflater.from(this);
        scrollView = (SlideView) inflater.inflate(R.layout.activity_home, null);
        setContentView(scrollView);

        menu = inflater.inflate(R.layout.side_menu, null);
        main = inflater.inflate(R.layout.slider, null);

        children = new View[]{menu, main};

        //Toolbar
        toolbar = (ViewGroup) main.findViewById(R.id.toolbar);
        homeIcon = (ImageView) toolbar.findViewById(R.id.homeIcon);
        homeIcon.setBackgroundResource(R.drawable.sidemenu);
        searchIcon = (ImageView) toolbar.findViewById(R.id.searchIcon);
        searchIcon.setBackgroundResource(R.drawable.find_icon);
        homeIconLL = (LinearLayout) toolbar.findViewById(R.id.homeIconLL);
        homeIconLL.setOnClickListener(new ClickListenerForScrolling(scrollView, main, this));
        scrollView.initViews(children, scrollToViewIdx, new SizeCallbackForMenu(homeIconLL));
        pullTabBarViews();
        sideMenuListeners();
        setTabbarListeners();

        Util.setTypefaces(App.latoLightTypeface, ((TextView) findViewById(R.id.userNameTV)), ((TextView) findViewById(R.id.userEmailTV)), ((TextView) findViewById(R.id.myProfileTV)), ((TextView) findViewById(R.id.settingsTV)), ((TextView) findViewById(R.id.helpTV)), ((TextView) findViewById(R.id.logOutTV)));
        Util.setTypefaces(App.latoLightTypeface, wishListTV, messagesTV, postTV, activitirsTV, myItemsTV);
        attachHomeFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void populateData() {
//        getting username and user email when the user Login
    }

    private void sideMenuListeners() {
        ((TextView) findViewById(R.id.myProfileTV)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeIconLL.callOnClick();
                Intent intent = new Intent(HomeActivity.this, EditProfile.class);
                startActivity(intent);
            }
        });
        ((TextView) findViewById(R.id.settingsTV)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeIconLL.callOnClick();
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
        ((TextView) findViewById(R.id.helpTV)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeIconLL.callOnClick();
            }
        });
        ((TextView) findViewById(R.id.logOutTV)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeIconLL.callOnClick();
                showAlert("", "Are you sure you want to leave UNEE", "Logout", logoutListener, "Cancel", null, true);
            }
        });
    }

    DialogInterface.OnClickListener logoutListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            finish();
        }
    };

    private void setTabbarListeners() {
        wishListRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deselectAll();

                wishListTV.setTextColor(getResources().getColor(R.color.appColor));
                wishListIV.setImageResource(R.drawable.wishlist_active);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                WishlistFragment wishlistFragment = new WishlistFragment();
                ft.replace(R.id.content_frame, wishlistFragment);
                ft.commit();
                SharedPreferenceManager.getInstance().putString(Constants.ATTACHED_FRAGMENT, Constants.FRAGMENT_NOT_POSTS);
                SharedPreferenceManager.getInstance().putString(Constants.ATTACHED_FRAGMENT, Constants.FRAGMENT_NOT_POSTS);
            }
        });

        messagesRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deselectAll();
                messagesTV.setTextColor(getResources().getColor(R.color.appColor));
                messagesIV.setImageResource(R.drawable.message_active);
            }
        });

        postRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deselectAll();

                postTV.setTextColor(getResources().getColor(R.color.appColor));
                postIV.setImageResource(R.drawable.post_active);

                Intent intent = new Intent(HomeActivity.this, CamActivity.class);
                intent.putExtra(Constants.FROM_TO_CAM, Constants.FROM_POST);
                intent.putExtra(Constants.URL_NUMBER, Constants.FIRST_IV_URL);
                startActivityForResult(intent, 1);

                SharedPreferenceManager.getInstance().putString(Constants.ATTACHED_FRAGMENT, Constants.FRAGMENT_NOT_POSTS);
            }
        });

        activitirsRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deselectAll();

                activitirsTV.setTextColor(getResources().getColor(R.color.appColor));
                activitirsIV.setImageResource(R.drawable.activity_active);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ActivityFragment activityFragment = new ActivityFragment();
                ft.replace(R.id.content_frame, activityFragment);
                ft.commit();

                SharedPreferenceManager.getInstance().putString(Constants.ATTACHED_FRAGMENT, Constants.FRAGMENT_NOT_POSTS);
            }
        });

        myItemsRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deselectAll();
                myItemsTV.setTextColor(getResources().getColor(R.color.appColor));
                myItemsIV.setImageResource(R.drawable.sandwichmenu_active);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                MyItemsFragment myItemsFragment = new MyItemsFragment();
                ft.replace(R.id.content_frame, myItemsFragment);
                ft.commit();
                SharedPreferenceManager.getInstance().putString(Constants.ATTACHED_FRAGMENT, Constants.FRAGMENT_NOT_POSTS);
            }
        });

    }

    private void pullTabBarViews() {
        ViewGroup tabBar = (ViewGroup) main.findViewById(R.id.tabBar);
        wishListRL = (LinearLayout) tabBar.findViewById(R.id.wishListRL);
        messagesRL = (LinearLayout) tabBar.findViewById(R.id.messagesRL);
        postRL = (LinearLayout) tabBar.findViewById(R.id.postRL);
        activitirsRL = (LinearLayout) tabBar.findViewById(R.id.activitiesRL);
        myItemsRL = (LinearLayout) tabBar.findViewById(R.id.myItemsRL);

        wishListIV = (ImageView) tabBar.findViewById(R.id.wishlistIV);
        messagesIV = (ImageView) tabBar.findViewById(R.id.messagesIV);
        postIV = (ImageView) tabBar.findViewById(R.id.postIV);
        activitirsIV = (ImageView) tabBar.findViewById(R.id.activitiesIV);
        myItemsIV = (ImageView) tabBar.findViewById(R.id.myItemsIV);

        wishListTV = (TextView) tabBar.findViewById(R.id.wishlistTV);
        messagesTV = (TextView) tabBar.findViewById(R.id.messagesTV);
        postTV = (TextView) tabBar.findViewById(R.id.postTV);
        activitirsTV = (TextView) tabBar.findViewById(R.id.activiesTV);
        myItemsTV = (TextView) tabBar.findViewById(R.id.myItemsTV);
    }

    public void deselectAll() {
        wishListTV.setTextColor(getResources().getColor(R.color.deselectedFont));
        messagesTV.setTextColor(getResources().getColor(R.color.deselectedFont));
        postTV.setTextColor(getResources().getColor(R.color.deselectedFont));
        activitirsTV.setTextColor(getResources().getColor(R.color.deselectedFont));
        myItemsTV.setTextColor(getResources().getColor(R.color.deselectedFont));

        wishListIV.setImageResource(R.drawable.wishlist_grey);
        messagesIV.setImageResource(R.drawable.messages_grey);
        postIV.setImageResource(R.drawable.post_grey);
        activitirsIV.setImageResource(R.drawable.activity_grey);
        myItemsIV.setImageResource(R.drawable.sandwich_menu_grey);
    }

    private void attachHomeFragment() {
        HomePageFragment homePageFragment = new HomePageFragment();
        ((getFragmentManager()).beginTransaction()).replace(R.id.content_frame, homePageFragment).commit();
        SharedPreferenceManager.getInstance().putString(Constants.ATTACHED_FRAGMENT, Constants.FRAGMENT_POSTS);
    }

    public void configureToolBar() {
        String attachedFragment = SharedPreferenceManager.getInstance().getString(Constants.ATTACHED_FRAGMENT, "");

        if (attachedFragment != null && (!attachedFragment.equals("")) && attachedFragment.equals(Constants.FRAGMENT_NOT_POSTS)) {
            homeIcon.setBackgroundResource(R.drawable.back_arrow);

            homeIconLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    attachHomeFragment();
                }
            });
        } else {
            deselectAll();
            homeIcon.setBackgroundResource(R.drawable.sidemenu);
            homeIconLL.setOnClickListener(new ClickListenerForScrolling(scrollView, main, this));
        }
    }

    static class ClickListenerForScrolling implements View.OnClickListener {
        SlideView scrollView;
        View menu;
        SideMenuListener sideMenuListener;

        public ClickListenerForScrolling(SlideView scrollView, View menu, SideMenuListener sideMenuListener) {
            super();
            this.scrollView = scrollView;
            this.menu = menu;
            this.sideMenuListener = sideMenuListener;
        }

        @Override
        public void onClick(View v) {
            Context context = menu.getContext();
            sideMenuListener.populateData();
            int menuWidth = menu.getMeasuredWidth();
            // Ensure menu is visible
            menu.setVisibility(View.VISIBLE);

            if (!menuOut) {
                // Scroll to 0 to reveal menu
                int left = 20;
                scrollView.smoothScrollTo(left, 0);
            } else {
                // Scroll to menuWidth so menu isn't on screen.
                int left = menuWidth;
                scrollView.smoothScrollTo(left, 0);
            }
            menuOut = !menuOut;
        }
    }

    static class SizeCallbackForMenu implements SlideView.SizeCallback {
        int btnWidth;
        View btnSlide;

        public SizeCallbackForMenu(View btnSlide) {
            super();
            this.btnSlide = btnSlide;
        }

        @Override
        public void onGlobalLayout() {
            btnWidth = btnSlide.getMeasuredWidth();
            System.out.println("btnWidth=" + btnWidth);
        }

        @Override
        public void getViewSize(int idx, int w, int h, int[] dims) {
            dims[0] = w;
            dims[1] = h;
            final int menuIdx = 0;
            if (idx == menuIdx) {
                dims[0] = w - btnWidth;
            }
        }
    }
}


