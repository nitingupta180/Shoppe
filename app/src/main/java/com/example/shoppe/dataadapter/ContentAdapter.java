package com.example.shoppe.dataadapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shoppe.R;
import com.example.shoppe.datamodel.ProductTemplate;
import com.example.shoppe.utilities.Constants;

import java.util.ArrayList;

/**
 * Created by nitingup on 7/18/15.
 */
public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {

    private static final String TAG = "ContentAdapter";

    Context mContext;

    ArrayList<ProductTemplate> mProducts;

    public ContentAdapter(Context context, ArrayList<ProductTemplate> products) {
        mContext = context;
        mProducts = products;
    }

    @Override
    public ContentAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.product_template_row, viewGroup, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ContentAdapter.ViewHolder viewHolder, int position) {
        ProductTemplate productTemplate = mProducts.get(position);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        viewHolder.mRecyclerViewRow.setLayoutManager(layoutManager);
        viewHolder.mRecyclerViewRow.setHasFixedSize(false);

        if(productTemplate.getTemplate().equals(Constants.JSON_PRODUCT_TEMPLATE_2)) {
            viewHolder.mTitle.setVisibility(View.VISIBLE);
            viewHolder.mTitle.setText(productTemplate.getLabel());
        } else {
            viewHolder.mTitle.setVisibility(View.GONE);
        }

        RowDataAdapter rowDataAdapter = new RowDataAdapter(mContext, productTemplate.getTemplate(), productTemplate.getItemList());
        viewHolder.mRecyclerViewRow.setAdapter(rowDataAdapter);
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public RecyclerView mRecyclerViewRow;
        public TextView mTitle;

        public ViewHolder (View itemView) {
            super(itemView);
            mRecyclerViewRow = (RecyclerView) itemView.findViewById(R.id.rvRow);
            mTitle = (TextView) itemView.findViewById(R.id.tvRowTitle);
        }
    }
}
