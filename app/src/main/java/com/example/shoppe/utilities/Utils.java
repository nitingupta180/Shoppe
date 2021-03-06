package com.example.shoppe.utilities;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

import com.example.shoppe.R;

/**
 * Created by nitingup on 7/15/15.
 */
public class Utils {

    public static void configureActionBarStyle(Activity context) {
        ActionBar bar = context.getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.BLUE));
        int titleId = context.getResources().getIdentifier("action_bar_title",
                "id", "android");
        TextView actionBarTitle = (TextView) context.findViewById(titleId);
        actionBarTitle.setTextAppearance(context, R.style.ActionBarTitleStyle);
    }
}
