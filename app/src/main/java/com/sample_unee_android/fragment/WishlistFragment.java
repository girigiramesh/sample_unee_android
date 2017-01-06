package com.sample_unee_android.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sample_unee_android.App;
import com.sample_unee_android.R;
import com.sample_unee_android.activity.HomeActivity;
import com.sample_unee_android.activity.ResetPasswordActivity;
import com.sample_unee_android.listeners.PostListener;
import com.sample_unee_android.util.Util;

/**
 * Created by Ramesh on 1/6/17.
 */

public class WishlistFragment extends Fragment implements PostListener {
    private View v;
    private LayoutInflater inflater;
    private ListView wishlist;
    private boolean updatesList = true;
    private GridView latestGrid;
    private EditText addET;
    private Button likeBTN, needBTN, addBTN;
    private LinearLayout likeLL, needLL;
    private SwipeRefreshLayout swipeRefreshLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        v = inflater.inflate(R.layout.activity_wishlist, container, false);

        likeBTN = ((Button) v.findViewById(R.id.likeBTN));
        needBTN = ((Button) v.findViewById(R.id.needBTN));
        addBTN = ((Button) v.findViewById(R.id.addBTN));
        addET = ((EditText) v.findViewById(R.id.addET));
        likeLL = ((LinearLayout) v.findViewById(R.id.likeLL));
        needLL = ((LinearLayout) v.findViewById(R.id.needLL));
        wishlist = (ListView) v.findViewById(R.id.itemsListLV);
        latestGrid = (GridView) v.findViewById(R.id.latestGrid);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);

        Util.setTypefaces(App.latoBlackTypeface, ((Button) v.findViewById(R.id.needBTN)), ((Button) v.findViewById(R.id.likeBTN)));
        Util.setTypefaces(App.latoBoldTypeface, ((Button) v.findViewById(R.id.addBTN)));
        Util.setTypefaces(App.latoBlackTypeface, ((TextView) v.findViewById(R.id.wishlistTV)));

        final PostListener postListener = this;
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.appColor));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        ((HomeActivity)getActivity()).showProgressDialog("Please Wait..!");
        setListeners();
        ((HomeActivity)getActivity()).configureToolBar();
        return v;
    }

    @Override
    public void afterRetrievingPostsFromParse() {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void setListeners() {
        likeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                needBTN.setTextColor(getResources().getColor(R.color.appColor));
                needBTN.setBackgroundResource(R.drawable.button_white_left);
                likeBTN.setTextColor(getResources().getColor(R.color.white));
                likeBTN.setBackgroundResource(R.drawable.button_colored_right);
                likeLL.setVisibility(View.VISIBLE);
                needLL.setVisibility(View.GONE);
            }
        });

        needBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeBTN.setTextColor(getResources().getColor(R.color.appColor));
                likeBTN.setBackgroundResource(R.drawable.button_white_right);
                needBTN.setTextColor(getResources().getColor(R.color.white));
                needBTN.setBackgroundResource(R.drawable.button_colored_left);
                needLL.setVisibility(View.VISIBLE);
                likeLL.setVisibility(View.GONE);

            }
        });

        ((HomeActivity)getActivity()).showProgressDialog("Please Wait..!");

        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((addET.getText().toString().length() == 0)) {
                    addET.setError("Please enter something to add.");
                    updatesList = false;
                    return;
                }
                if (updatesList) {
                    ((HomeActivity)getActivity()).showProgressDialog("Please Wait ..!");
                } else {
                    updatesList = true;
                }
            }
        });


        latestGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ResetPasswordActivity.class);
                intent.putExtra("OBJECT_ID", "object");
                getActivity().startActivity(intent);
            }
        });

    }
}
