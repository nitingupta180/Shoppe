package com.example.shoppe.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppe.R;
import com.example.shoppe.dataadapter.ContentAdapter;
import com.example.shoppe.datamodel.Item;
import com.example.shoppe.datamodel.ProductTemplate;
import com.example.shoppe.utilities.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by nitingup on 7/15/15.
 */
public class MyContentFragment extends Fragment {

    private static final String TAG = "MyContentFragment";

    ArrayList<ProductTemplate> mProducts;

    RecyclerView mRootView;
    RecyclerView.Adapter mAdapter;
    LinearLayoutManager mLayoutManager;
    boolean mScrolling;

    String mFileToLoad;
    Context mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mProducts = new ArrayList<>();
        int position = getArguments().getInt(Constants.DRAWER_POSITION);
        if(position == 0) {
            mFileToLoad = Constants.FILE_TITLE_1;
        } else if(position == 1) {
            mFileToLoad = Constants.FILE_TITLE_2;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_content, container, false);

        mRootView = (RecyclerView) v.findViewById(R.id.rvRootView);

        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);

        mRootView.setLayoutManager(mLayoutManager);

        mRootView.setHasFixedSize(false);

        mAdapter = new ContentAdapter(mContext, mProducts);

        //mRootView.setAdapter(mAdapter);

        new JSONLoader().execute();

        return v;
    }

    class JSONLoader extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            loadFile();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(mAdapter != null) {
                mRootView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        }

        private void loadFile() {

            JsonReader reader = null;
            try {
                InputStream inputStream = mContext.getAssets().open(mFileToLoad);
                reader = new JsonReader(new InputStreamReader(inputStream));
                reader.beginArray();
                while(reader.hasNext()) {
                    reader.beginObject();
                    String label = "", image = "", template = "";
                    ArrayList<Item> items = new ArrayList<>();
                    while(reader.hasNext()) {
                        String name = reader.nextName();
                        if(name.equals(Constants.JSON_LABEL)) {
                            label = reader.nextString();
                        } else if(name.equals(Constants.JSON_IMAGE) || name.equals(Constants.JSON_IMAGE_URL)) {
                            image = reader.nextString();
                        } else if(name.equals(Constants.JSON_TEMPLATE)) {
                            template = reader.nextString();
                        } else if(name.equals(Constants.JSON_ITEMS)) {
                            reader.beginArray();
                            while (reader.hasNext()) {
                                reader.beginObject();
                                String itemLabel = "", itemImage = "", itemWebUrl = "";
                                while (reader.hasNext()) {
                                    String itemName = reader.nextName();
                                    if(itemName.equals(Constants.JSON_LABEL)) {
                                        itemLabel = reader.nextString();
                                    } else if (itemName.equals(Constants.JSON_IMAGE) || itemName.equals(Constants.JSON_IMAGE_URL)) {
                                        itemImage = reader.nextString();
                                    } else if (itemName.equals(Constants.JSON_WEB_URL)) {
                                        itemWebUrl = reader.nextString();
                                    } else {
                                        reader.skipValue();
                                    }
                                }
                                reader.endObject();
                                Item item = new Item(itemLabel, itemImage, itemWebUrl);
                                items.add(item);
                            }
                            reader.endArray();
                        } else {
                            reader.skipValue();
                        }
                    }
                    ProductTemplate productTemplate = new ProductTemplate(label, image, template, items);
                    mProducts.add(productTemplate);
                    reader.endObject();
                }
                reader.endArray();
            } catch (IOException e) {
                Log.e(TAG, "Error while opening the file " + mFileToLoad);
            } finally {
                if(reader != null) {
                    try {
                        reader.close();
                    } catch (IOException ex) {
                        Log.e(TAG, " Error closing reader");
                    }
                }
            }
        }
    }
}
