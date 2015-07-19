package com.example.shoppe.utilities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.shoppe.R;

/**
 * Created by nitingup on 7/18/15.
 */
public class DynamicImageView extends ImageView {

    public DynamicImageView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        final Drawable d = this.getDrawable();

        if (d != null) {
            int offset = getResources().getDimensionPixelSize(R.dimen.offset);

            int height = MeasureSpec.getSize(heightMeasureSpec);
            int width = MeasureSpec.getSize(widthMeasureSpec)  - (offset * 2);

            if (width >= height)
                height = (int) Math.ceil(width * (float) d.getIntrinsicHeight() / d.getIntrinsicWidth());
            else
                width = (int) Math.ceil(height * (float) d.getIntrinsicWidth() / d.getIntrinsicHeight());

            this.setMeasuredDimension(width, height);

        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
