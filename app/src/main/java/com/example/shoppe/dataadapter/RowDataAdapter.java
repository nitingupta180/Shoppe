package com.example.shoppe.dataadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.shoppe.R;
import com.example.shoppe.datamodel.Item;

import java.util.ArrayList;

/**
 * Created by nitingup on 7/18/15.
 */
public class RowDataAdapter extends RecyclerView.Adapter<RowDataAdapter.ViewHolder> {

    private ArrayList<Item> mItems;
    Context mContext;

    public RowDataAdapter(Context context, ArrayList<Item> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.product_item, viewGroup, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Item item = mItems.get(position);
        viewHolder.mImageView.setImageResource(R.mipmap.ic_tab_photo);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public View rootView;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            mImageView =(ImageView) itemView.findViewById(R.id.image);
        }
    }
}
