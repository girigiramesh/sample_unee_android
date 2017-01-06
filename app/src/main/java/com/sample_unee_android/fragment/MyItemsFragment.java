package com.sample_unee_android.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.sample_unee_android.App;
import com.sample_unee_android.R;
import com.sample_unee_android.activity.HomeActivity;
import com.sample_unee_android.adapter.MyItemsAdapter;
import com.sample_unee_android.listeners.PostListener;
import com.sample_unee_android.util.Util;

/**
 * Created by Ramesh on 1/6/17.
 */

public class MyItemsFragment extends Fragment implements PostListener {
    private View v;
    private LayoutInflater inflater;
    private ListView myitemsListLV;
    private SwipeRefreshLayout swipeRefreshLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        v = inflater.inflate(R.layout.activity_myitems, container, false);
        myitemsListLV = (ListView) v.findViewById(R.id.myitemsListLV);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        Util.setTypefaces(App.latoBoldTypeface, ((TextView) v.findViewById(R.id.myItemsTV)));
        final PostListener postListener = this;
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.appColor));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        ((HomeActivity)getActivity()).configureToolBar();
        buildMyPostsList();

        return v;
    }

    private void buildMyPostsList() {
        MyItemsAdapter membersAdapter = (MyItemsAdapter) myitemsListLV.getAdapter();
        if (membersAdapter != null) {
            membersAdapter.updateList();
        } else {
            membersAdapter = new MyItemsAdapter(getActivity());
            myitemsListLV.setAdapter(membersAdapter);
        }
    }

    @Override
    public void afterRetrievingPostsFromParse() {
        swipeRefreshLayout.setRefreshing(false);
        buildMyPostsList();
    }
}
