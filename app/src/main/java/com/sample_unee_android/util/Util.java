package com.sample_unee_android.util;

import android.graphics.Typeface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ramesh on 1/5/17.
 */

public class Util {
    public static final String strSeparator = "__,__";
    private static final String TAG = Util.class.getSimpleName();

    public static void setTypefaces(Typeface typeface, TextView... textViews) {
        for (TextView tv : textViews) {
            tv.setTypeface(typeface);
        }
    }

    public static void setTypefaces(Typeface typeface, EditText... editTexts) {
        for (EditText et : editTexts) {
            et.setTypeface(typeface);
        }
    }

    public static void setTypefaces(Typeface typeface, Button... buttons) {
        for (Button btn : buttons) {
            btn.setTypeface(typeface);
        }
    }
    public static boolean isNotNullAndNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }

    public static boolean isNotNullAndNotEmpty(List list) {
        return list != null && !list.isEmpty();
    }
}
