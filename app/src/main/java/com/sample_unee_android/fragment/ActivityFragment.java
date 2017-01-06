package com.sample_unee_android.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sample_unee_android.R;
import com.sample_unee_android.activity.HomeActivity;

/**
 * Created by Ramesh on 1/6/17.
 */

public class ActivityFragment extends Fragment {
    private View v;
    private LayoutInflater inflater;
    private ListView activityListLV;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        v = inflater.inflate(R.layout.activity_activity, container, false);
        ((HomeActivity)getActivity()).configureToolBar();
        return v;
    }
}
