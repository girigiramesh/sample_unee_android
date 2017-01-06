package com.sample_unee_android.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sample_unee_android.App;
import com.sample_unee_android.R;
import com.sample_unee_android.activity.HomeActivity;
import com.sample_unee_android.activity.ResetPasswordActivity;
import com.sample_unee_android.listeners.PostListener;
import com.sample_unee_android.util.Util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ramesh on 1/6/17.
 */

public class HomePageFragment extends Fragment implements PostListener {
    private View v;
    private LayoutInflater inflater;
    private GridView postsGV;
    private Button latestBTN;
    private SwipeRefreshLayout swipeRefreshLayout;
    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> categoriesCollection;
    ExpandableListView postCategoriesLV;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        v = inflater.inflate(R.layout.activity_home_page_fragment, container, false);
        latestBTN = ((Button) v.findViewById(R.id.latestBTN));
        final Button categoriesBTN = ((Button) v.findViewById(R.id.categoriesBTN));
        final LinearLayout latestLL = ((LinearLayout) v.findViewById(R.id.latestLL));
        final LinearLayout categoryLL = ((LinearLayout) v.findViewById(R.id.categoryLL));
        postsGV = (GridView) v.findViewById(R.id.latestGrid);
        postCategoriesLV = (ExpandableListView) v.findViewById(R.id.category_list);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        final PostListener postListener = this;
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.appColor));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        Util.setTypefaces(App.latoLightTypeface, latestBTN, categoriesBTN);

        createGroupList();
        createCollection();

        latestBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoriesBTN.setTextColor(getResources().getColor(R.color.appColor));
                categoriesBTN.setBackgroundResource(R.drawable.button_white_right);
                latestBTN.setTextColor(getResources().getColor(R.color.white));
                latestBTN.setBackgroundResource(R.drawable.button_colored_left);
                latestLL.setVisibility(View.VISIBLE);
                categoryLL.setVisibility(View.GONE);

            }
        });
        categoriesBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latestBTN.setTextColor(getResources().getColor(R.color.appColor));
                latestBTN.setBackgroundResource(R.drawable.button_white_left);
                categoriesBTN.setTextColor(getResources().getColor(R.color.white));
                categoriesBTN.setBackgroundResource(R.drawable.button_colored_right);
                categoryLL.setVisibility(View.VISIBLE);
                latestLL.setVisibility(View.GONE);
            }
        });

        postsGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ResetPasswordActivity.class);
                intent.putExtra("object", "objectID");
                getActivity().startActivity(intent);
            }
        });
        postCategoriesLV.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getActivity(), "selected", Toast.LENGTH_LONG).show();
                return true;
            }
        });

        ((HomeActivity) getActivity()).configureToolBar();
        ((HomeActivity) getActivity()).deselectAll();
        return v;
    }

    @Override
    public void afterRetrievingPostsFromParse() {
        latestBTN.performClick();
        ((HomeActivity) getActivity()).dismissProgressDialog();
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private void createGroupList() {
        groupList = new ArrayList<String>();
        groupList.add("Housing");
        groupList.add("Books");
        groupList.add("Outlines");
        groupList.add("Buy/Sell");
        groupList.add("Coupons");
        groupList.add("Free Stuff");
        groupList.add("Social");
        groupList.add("Services");
        groupList.add("Everything else");
    }

    private void createCollection() {
        // preparing categories collection(child)
        String[] house_categories = {"Single Bedroom", "Single Apartment", "Shared bedroom", "House", "Sublet", "Garage"};
        String[] buySell_categories = {"Electronics", "Furniture", "For Home", "Clothes and accessories", "Pets and accessories", "Sports Equipment", "Vehicles", "Free Stuff"};
        String[] social_categories = {"Events and Parties", "Looking for a companion", "Rideshare", "Fraternities"};
        String[] service_categories = {"Freelancers", "Internship/Job Offers", "Lost and Found"};
        String[] empty_categories = {};
        categoriesCollection = new LinkedHashMap<String, List<String>>();

        for (String category : groupList) {
            if (category.equals("Housing"))
                loadChild(house_categories);
            else if (category.equals("Buy/Sell"))
                loadChild(buySell_categories);
            else if (category.equals("Social"))
                loadChild(social_categories);
            else if (category.equals("Services"))
                loadChild(service_categories);
            else
                loadChild(empty_categories);
            categoriesCollection.put(category, childList);
        }
    }

    private void loadChild(String[] laptopModels) {
        childList = new ArrayList<String>();
        for (String model : laptopModels)
            childList.add(model);
    }
}
