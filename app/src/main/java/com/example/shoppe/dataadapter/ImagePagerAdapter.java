package com.example.shoppe.dataadapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.shoppe.R;
import com.example.shoppe.cache.ImageLoader;
import com.example.shoppe.datamodel.Item;
import com.example.shoppe.utilities.DynamicImageView;

import java.util.ArrayList;

/**
 * Created by nitingup on 7/19/15.
 */
public class ImagePagerAdapter extends PagerAdapter {
    Context mContext;
    ArrayList<Item> mItems;
    ImageLoader mImageLoader;

    public ImagePagerAdapter(Context context, ArrayList<Item> items) {
        mContext = context;
        mItems = items;
        mImageLoader = new ImageLoader(mContext);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        boolean t = view == ((ImageView) object);
        return t;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        DynamicImageView imageView = new DynamicImageView(mContext, null);
        imageView.setImageResource(R.mipmap.ic_tab_photo);

        container.addView(imageView);

        if (mImageLoader != null) {
            mImageLoader.displayImage(mItems.get(position).getImage(), imageView, false, null);
        }

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}
