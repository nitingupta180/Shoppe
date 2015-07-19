package com.example.shoppe.dataadapter;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.shoppe.R;
import com.example.shoppe.cache.ImageLoader;
import com.example.shoppe.datamodel.Item;
import com.example.shoppe.utilities.Constants;

import java.util.ArrayList;

/**
 * Created by nitingup on 7/18/15.
 */
public class RowDataAdapter extends RecyclerView.Adapter<RowDataAdapter.ViewHolder> {

    private ArrayList<Item> mItems;
    Context mContext;
    String mTemplateType;
    ImageLoader mImageLoader;

    public RowDataAdapter(Context context, String templateType, ArrayList<Item> items) {
        mContext = context;
        mTemplateType = templateType;
        mItems = items;
        mImageLoader = new ImageLoader(mContext);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = null;
        if(mTemplateType.equals(Constants.JSON_PRODUCT_TEMPLATE_1)) {
            convertView = inflater.inflate(R.layout.product_template_one, viewGroup, false);
        } else if (mTemplateType.equals(Constants.JSON_PRODUCT_TEMPLATE_2)) {
            convertView = inflater.inflate(R.layout.product_template_two, viewGroup, false);
        }
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Item item = mItems.get(position);

        Point display = new Point();
        ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(display);

        if(mTemplateType.equals(Constants.JSON_PRODUCT_TEMPLATE_1)) {
            int width = display.x;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            viewHolder.mImageView.setLayoutParams(params);
            viewHolder.mImageView.requestLayout();
        }

        if (mImageLoader != null) {
            mImageLoader.displayImage(item.getImage(), viewHolder.mImageView, false, null);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public View rootView;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            mImageView =(ImageView) itemView.findViewById(R.id.image);
        }
    }
}
