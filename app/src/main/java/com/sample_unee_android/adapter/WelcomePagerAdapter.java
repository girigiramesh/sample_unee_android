package com.sample_unee_android.adapter;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sample_unee_android.App;
import com.sample_unee_android.AppSingleton;
import com.sample_unee_android.R;
import com.sample_unee_android.util.Util;

public class WelcomePagerAdapter extends PagerAdapter {
    protected App app = AppSingleton.getInstance().getApplication();

    private int[] mImages = new int[]{R.drawable.home_image_one, R.drawable.home_image_three, R.drawable.home_image_two, R.drawable.home_image_four, R.drawable.home_image_five};

    private int[] displayInfo = {R.string.info_one, R.string.info_two, R.string.info_three, R.string.info_four, R.string.info_five};

    private Context context;
    private LayoutInflater mLayoutInflater;

    public WelcomePagerAdapter(Context context) {
        this.context = context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
        ImageView dotZero = (ImageView) itemView.findViewById(R.id.dotZero);
        ImageView dotOne = (ImageView) itemView.findViewById(R.id.dotOne);
        ImageView dotTwo = (ImageView) itemView.findViewById(R.id.dotTwo);
        ImageView dotThree = (ImageView) itemView.findViewById(R.id.dotThree);
        ImageView dotFour = (ImageView) itemView.findViewById(R.id.dotFour);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        imageView.setImageResource(mImages[position]);
        TextView displayInfoTV = (TextView) itemView.findViewById(R.id.displayInfoTV);
        Util.setTypefaces(App.latoLightTypeface, displayInfoTV);
        displayInfoTV.setText(displayInfo[position]);

        if (position == 0)
            dotZero.setImageResource(R.drawable.dot_active);
        else if (position == 1)
            dotOne.setImageResource(R.drawable.dot_active);
        else if (position == 2)
            dotTwo.setImageResource(R.drawable.dot_active);
        else if (position == 3)
            dotThree.setImageResource(R.drawable.dot_active);
        else if (position == 4)
            dotFour.setImageResource(R.drawable.dot_active);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

}
